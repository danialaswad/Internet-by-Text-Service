/**
 * Created by Antoine on 29/05/2017.
 * Inspired by http://stackoverflow.com/q/6173920/600500.
 */

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class ZLibCompression
{
    public static void compress(String raw, File compressed) throws IOException
    {
        FileUtils.writeStringToFile(new File("codeSource.txt"),raw, Charset.defaultCharset());
        try (InputStream inputStream = new FileInputStream("codeSource.txt");
             OutputStream outputStream = new DeflaterOutputStream(new FileOutputStream(compressed)))
        {
            copy(inputStream, outputStream);
        }
    }

    public static void decompress(File compressed, File raw)
            throws IOException
    {
        try (InputStream inputStream = new InflaterInputStream(new FileInputStream(compressed));
             OutputStream outputStream = new FileOutputStream(raw))
        {
            copy(inputStream, outputStream);
        }
    }

    public static String decompress(File compressed) throws IOException
    {
        try (InputStream inputStream = new InflaterInputStream(new FileInputStream(compressed)))
        {
            return toString(inputStream);
        }
    }

    private static String toString(InputStream inputStream)
    {
        try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A"))
        {
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    private static void copy(InputStream inputStream, OutputStream outputStream)
            throws IOException
    {
        byte[] buffer = new byte[1000];
        int length;

        while ((length = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer, 0, length);
        }
    }
}