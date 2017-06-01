package compression;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
/**
 * ZLibCompression
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class ZLibCompression {

    public static String compressToBase64(String entry, Charset encoding) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            OutputStream out = new DeflaterOutputStream(baos);
            out.write(entry.getBytes(encoding));
            out.close();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        byte[] b64 = Base64.encodeBase64(baos.toByteArray());
        return new String(b64);
    }

    public static String decompressFromBase64(String entry, String encoding) throws UnsupportedEncodingException {

        byte[] dec = Base64.decodeBase64(entry.getBytes(encoding));
        InputStream in = new InflaterInputStream(new ByteArrayInputStream(dec));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) > 0)
                baos.write(buffer, 0, len);
            return new String(baos.toByteArray(), encoding);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}



