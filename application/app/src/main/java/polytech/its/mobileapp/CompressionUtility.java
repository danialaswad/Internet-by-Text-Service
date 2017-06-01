package polytech.its.mobileapp;


import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.InflaterInputStream;

public class CompressionUtility {
    public static String decompressFromBase64(String entry, String encoding) {
        StringBuffer sb = new StringBuffer();
        try {
            byte[] dec = Base64.decodeBase64(entry.getBytes());
            InputStream in = new InflaterInputStream(new ByteArrayInputStream(dec));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];

            int bytesRead; // unused? weird compiler messages...
            while ((bytesRead = in.read(buffer)) != -1) { // InputStream.read() returns -1 at EOF
                sb.append(new String(buffer, 0, bytesRead, encoding));

                buffer = new byte[8192];
                bytesRead = 0;
            }
        } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(sb);
    }
}

