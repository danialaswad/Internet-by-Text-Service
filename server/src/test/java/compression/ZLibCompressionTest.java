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
        Assert.assertEquals("eJwLSa0oSVVIVEjOzy0oSi0uTi0CAD9QBu8=",ZLibCompression.compressToBase64(test, "UTF-8"));

        String test2 = "é€<!04";
        Assert.assertEquals("eJw7vPJR0xobRQMTABqpBD4=",ZLibCompression.compressToBase64(test2, "UTF-8"));




    }

    @Test
    public void decompressFromBase64Test() throws UnsupportedEncodingException {
        String test = "Texte a compresser";
        String r = ZLibCompression.decompressFromBase64("eJw7vPJR0xobRQMTABqpBD4=", "UTF-8");
        Assert.assertEquals(test,r);

        String test2 = "é€<!04";
        Assert.assertEquals(test2,ZLibCompression.decompressFromBase64("eJw7vPJR0xobRQMTABqpBD4=", "UTF-8"));
    }

    @Test
    public void compressDecompress64Test() throws UnsupportedEncodingException {
        String actual = "Illud tamen te esse admonitum volo, primum ut qualis es talem te esse omnes existiment ut, quantum a rerum turpitudine abes, tantum te a verborum libertate seiungas; deinde ut ea in alterum ne dicas, quae cum tibi falso responsa sint, erubescas. Quis est enim, cui via ista non pateat, qui isti aetati atque etiam isti dignitati non possit quam velit petulanter, etiamsi sine ulla suspicione, at non sine argumento male dicere? Sed istarum partium culpa est eorum, qui te agere voluerunt; laus pudoris tui, quod ea te invitum dicere videbamus, ingenii, quod ornate politeque dixisti.\n" +
                "Ut enim quisque sibi plurimum confidit et ut quisque maxime virtute et sapientia sic munitus est, ut nullo egeat suaque omnia in se ipso posita iudicet, ita in amicitiis expetendis colendisque maxime excellit. Quid enim? Africanus indigens mei? Minime hercule! ac ne ego quidem illius; sed ego admiratione quadam virtutis eius, ille vicissim opinione fortasse non nulla, quam de meis moribus habebat, me dilexit; auxi\n";
        String compress = ZLibCompression.compressToBase64(actual, "UTF8");

        String decompress = ZLibCompression.decompressFromBase64(compress,"UTF-8");

        Assert.assertEquals(decompress,actual);

    }
}
