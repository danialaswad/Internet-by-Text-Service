package polytech.its.mobileapp.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Karim on 11/06/2017.
 */
public class CompressionUtilityTest {
    @Test
    public void decompressFromBase64Normal() throws Exception {
        String base64msg = "eNpzys/Lyi8tUshST8zMTVXISS1WSM7ILCgGAG0bCMg=";
        String decoded =CompressionUtility.decompressFromBase64(base64msg, "UTF-8");
        assertEquals(decoded,"Bonjour j'aime les chips");
} @Test
    public void decompressFromBase64Special() throws Exception {
        String base64msg = "eNrTAwAALwAv";
        String decoded =CompressionUtility.decompressFromBase64(base64msg, "UTF-8");
        assertEquals(decoded,".");
}

}