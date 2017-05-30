import web.URLReader;

/**
 * Created by danial on 29/05/2017.
 */
public class Main {

    public static void main(String args[]){
        URLReader w = new URLReader("fr.wikipedia.org/wiki/Vampire");
        System.out.println(w.fetchFile());
    }
}
