package engine;


import database.ITSDatabaseSQL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SmsCommandTest {

    @Test
    public void getTest(){
        SmsCommand command = new SmsCommand();
        command.setMsgOriginator("+33668639846");
        List<String> result = command.get("http://anasghira.com/its");
        List<String> expected = new ArrayList<>();
        expected.add("WEB:<body> \n" +
                " <div> \n" +
                "  <div> \n" +
                "   <a href=\"http://anasghira.com/its#.html\" title=\"Home\">Home</a> \n" +
                "   <a href=\"http://anasghira.com/its#.html\" title=\"About Us\">About</a> \n" +
                "   <a href=\"http://anasghira.com/its#.html\" title=\"Products\">Products</a> \n" +
                "   <a href=\"http://anasghira.com/its#.html\" title=\"Services\">Services</a> \n" +
                "   <a href=\"http://anasghira.com/its#.html\" title=\"Contact\">Contact</a> \n" +
                "  </div> \n" +
                "  <div> \n" +
                "   <b>ITS</b> \n" +
                "   <em>Internet by Text Service</em> \n" +
                "  </div> \n" +
                "  <div></div> \n" +
                "  <div> \n" +
                "   <img src=\"http://anasghira.com/its/images_tree.jpg\"> \n" +
                "   <h3>Great Slogan!</h3> Phasellus id est. Quisque blandit eros sed pede. Quisque est. Donec lectus neque, posuere at, adipiscing id, lobortis id, nisl. Vivamus id ante. Aliquam ut augue. Fusce venenatis libero vel urna. Suspendisse quis dui sit amet purus tincidunt facilisis. Sed velit. Sed varius, nibh quis egestas aliquam, eros libero feugiat lorem, eu lobortis \n" +
                "   <a href=\"http://anasghira.com/its/lorem\">urna velit ut turpis. </a> \n" +
                "   ");
        Assert.assertEquals(expected,result);
    }

    /*@Test
    public void nextTest(){
        SmsCommand command = new SmsCommand();
        command.setMsgOriginator("+33668639846");
        List<String> result = command.next("http://anasghira.com/its");
        List<String> expected = new ArrayList<>();
        expected.add("WEBNEXT:<body>\n" +
                " <li>Nullam ut metus ac quam aliquet ullamcorper.</li> \n" +
                " <li>Proin sit amet tortor id risus euismod pretium.</li> \n" +
                " <li>Proin facilisis orci vel purus.</li> \n" +
                " <li>Vivamus eget felis at pede porttitor accumsan.</li> \n" +
                " <li>Suspendisse at lacus eget eros tristique ultrices. </li> \n" +
                " <li>Suspendisse euismod tellus vel purus.</li> \n" +
                " <li>Suspendisse at lacus eget eros tristique ultrices. </li> Donec lectus neque, posuere at, adipiscing id, lobortis id, nisl. Vivamus id ante. Aliquam ut augue. Fusce venenatis libero vel urna. Suspendisse quis dui sit amet purus tincidunt facilisis. Sed velit. Sed varius, nibh quis egestas aliquam, eros libero feugiat lorem, eu lobortis urna velit ut turpis. Nulla vitae lacus. Quisque lectus lorem, cursus auctor, ornare non, dictum in, tortor. Suspendisse gravida. Ut volutpat. Pellentesque \n" +
                " <a href=\"http://anasghira.com/its#\">habitant morbi tristique");
        Assert.assertEquals(expected,result);
    }*/

    @Test
    public  void endwebsiteTest() throws SQLException {

        SmsCommand command = new SmsCommand();
        command.setMsgOriginator("+33668639846");
        List<String> result = command.endwebsite("http://anasghira.com/its");

        Assert.assertEquals("",ITSDatabaseSQL.getPage("+33668639846","http://anasghira.com/its"));
    }

    @Test
    public void errorTest(){
        SmsCommand command = new SmsCommand();

        List<String> s = command.process("hello:null","");
        Assert.assertEquals( "WEB:<h2>Mauvaise commande</h2>",s.get(0));

        s = command.process("nothing:null","");

        Assert.assertEquals( "WEB:<h2>Mauvaise commande</h2>",s.get(0));

        s = command.process("null","");

        Assert.assertEquals( "WEB:<h2>Mauvaise commande</h2>",s.get(0));
    }

    @Test
    public void okTest(){
        SmsCommand command = new SmsCommand();
        Assert.assertEquals("ITS:AVAILABLE", command.process("OK:","").get(0));

    }

    @Test
    public void twitterConfTest(){
        SmsCommand command = new SmsCommand();

        Assert.assertEquals("TWITTERCONF:SUCCESS", command.process("TWITTERCONF:hello,hello,12344","").get(0));


        Assert.assertEquals("TWITTERCONF:FAILURE", command.process("TWITTERCONF:hello,hello","").get(0));
    }

    @Test
    public void twitterHomeFailure(){
        SmsCommand command = new SmsCommand();
        Assert.assertEquals("TWITTERHOME:FAILURE",command.process("TWITTERHOME:1234","").get(0));
    }

    @Test
    public void tweetFailure(){
        SmsCommand command = new SmsCommand();
        Assert.assertEquals("TWEET:FAILURE",command.process("TWEET:1234","").get(0));

        Assert.assertEquals("TWEET:FAILURE",command.process("TWEET:1234,ok","").get(0));
    }

    @Test
    public void getimgTest(){
        SmsCommand command = new SmsCommand();
        String expected = "IMG:/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAFAAUDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDwSR4mgiRIdki53ybyd+Txx2xRRRQNtt3Z/9k=";
        List<String> s = command.process("GETIMG:http://anasghira.com/its/test.png","");
        Assert.assertEquals(expected,s.get(0));
        Assert.assertEquals("IMGEND:YES",s.get(s.size()-1));

        expected = "IMG:FAILURE";
        Assert.assertEquals(expected,command.process("GETIMG:http://anasghira.com/test.jpg","").get(0));
    }
}
