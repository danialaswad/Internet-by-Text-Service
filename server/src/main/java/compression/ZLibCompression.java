package compression;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * ZLibCompression class
 * @Author : ITS Team
 */

public class ZLibCompression {

    /**
     * Compress and encode the given String in a base 64 String
     * @param entry
     * @param encoding
     * @return
     */
    public static String compressToBase64(String entry, String encoding) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            OutputStream out = new DeflaterOutputStream(baos);
            out.write(entry.getBytes(encoding));
            out.close();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        byte[] b64 = Base64.encodeBase64(baos.toByteArray());
        String output = new String(b64);
        return output;
    }

    /**
     * Decode from base 64 and decompress the given String
     * @param entry
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decompressFromBase64(String entry, String encoding) throws UnsupportedEncodingException {

        byte[] dec = Base64.decodeBase64(entry.getBytes(encoding));
        InputStream in = new InflaterInputStream(new ByteArrayInputStream(dec));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while((len = in.read(buffer))>0)
                baos.write(buffer, 0, len);
            return new String(baos.toByteArray(), encoding);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Encode the given byte[] in base64 String
     * @param array
     * @return
     */
    public static String encodeImage(byte[] array){
        return Base64.encodeBase64String(array);
    }
}