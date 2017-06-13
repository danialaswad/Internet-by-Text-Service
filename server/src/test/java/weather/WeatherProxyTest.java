package weather;

import jdk.internal.org.xml.sax.InputSource;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Test;
import org.jsoup.nodes.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringReader;

/**
 * WeatherProxyTest
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class WeatherProxyTest {

    @Test
    public void processRequestTest(){
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<current><city id=\"6454924\" name=\"Nice\"><coord lon=\"7.25\" lat=\"43.7\"></coord><country>FR</country><sun rise=\"2017-06-12T03:48:44\" set=\"2017-06-12T19:13:37\"></sun></city><temperature value=\"22.63\" min=\"22\" max=\"23\" unit=\"metric\"></temperature><humidity value=\"88\" unit=\"%\"></humidity><pressure value=\"1015\" unit=\"hPa\"></pressure><wind><speed value=\"0.56\" name=\"Calm\"></speed><gusts></gusts><direction value=\"91.0063\" code=\"E\" name=\"East\"></direction></wind><clouds value=\"20\" name=\"few clouds\"></clouds><visibility value=\"10000\"></visibility><precipitation mode=\"no\"></precipitation><weather number=\"801\" value=\"few clouds\" icon=\"02n\"></weather><lastupdate value=\"2017-06-12T21:30:00\"></lastupdate></current>";
        Document document = Jsoup.parse(xmlString);
        String expected = "Nice,22°C,801,few clouds,2 Km/h,88%,03:48:44 (UTC),19:13:37 (UTC)";
        Assert.assertEquals(expected,WeatherProxy.processRequest(document));
    }
}
