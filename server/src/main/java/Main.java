import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.w3c.dom.Document;
import web.URLReader;
import web.WebPageCleaner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**https://blogserius.blogspot.fr
 * Created by danial on 29/05/2017.
 *http://www.nicematin.com
 */
public class Main {

    public static void main(String args[]){
        URLReader w = new URLReader("https://en.wikipedia.org/wiki/Vampire");
        String text = w.fetchFile();

        //System.out.println(text);
        WebPageCleaner cleaner = new WebPageCleaner(w.getDocument());

        System.out.println(cleaner.cleanWebPage());
        //new Main().exportToPdfBox(text, "Hello.pdf");
    }

    /*public void exportToPdfBox(String url, String out)
    {
        OutputStream os = null;

        try {
            os = new FileOutputStream(out);

            try {
                // There are more options on the builder than shown below.
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withW3cDocument(DOMBuilder.jsoup2DOM(new org.jsoup.nodes.Document(url)),"https://fr.wikipedia.org/wiki/chat");
                builder.toStream(os);
                builder.run();

            } catch (Exception e) {
                e.printStackTrace();
                // LOG exception
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                    // swallow
                }
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
            // LOG exception.
        }
    }*/
}
