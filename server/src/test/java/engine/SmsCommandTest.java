package engine;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SmsCommandTest {


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
        String expected = "IMG:/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCABEAHgDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1tSRTwxqINTg1dJzkwanBqh3e9G+lYdywGpwNVvMpfMpWHcs5p2aq+bXMeKfHFroFuYrYx3WoMdqQhuEPq5HT6dT7damVoq7KinJ2R18txFbxNLNKkUa8s7sFA+pNYGjeNNK1vVJrK2m/eJlVBH3sHkg+hBGPxrxHxD4k1LWJ";
        List<String> s = command.process("GETIMG:http://anasghira.com/its/test.png","");
        Assert.assertEquals(expected,s.get(0));
        Assert.assertEquals("IMGEND:YES",s.get(s.size()-1));

        expected = "IMG:FAILURE";
        Assert.assertEquals(expected,command.process("GETIMG:http://anasghira.com/test.jpg","").get(0));
    }
}
