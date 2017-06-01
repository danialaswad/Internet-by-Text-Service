package compression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * ZLibCompressionTest
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class ZLibCompressionTest {

    @Test
    public void compressToBase64Test(){
        String test = "Texte a compresser";
        Assert.assertEquals("eJwLSa0oSVVIVEjOzy0oSi0uTi0CAD9QBu8=",ZLibCompression.compressToBase64(test, StandardCharsets.UTF_8));
    }

    @Test
    public void decompressFromBase64Test() throws UnsupportedEncodingException {
        String test = "Texte a compresser";
        Assert.assertEquals(test,ZLibCompression.decompressFromBase64("eJwLSa0oSVVIVEjOzy0oSi0uTi0CAD9QBu8=", "UTF-8"));
    }
}
