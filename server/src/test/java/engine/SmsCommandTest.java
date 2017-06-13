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
        String expected = "IMG:Qk2GAAAAAAAAADYAAAAoAAAABQAAAAUAAAABABgAAAAAAFAAAAAAAAAAAAAAAAAAAAAAAAAAJBztJBztJBztCQc7AAAAACQc7SQc7SQc7AkHOwcFLQAkHO0kHO0kHO0kHO0kHO0AAQEICQc7JBztJBztJBztAAcFLQICDyQc7SQc7SQc7QA=";
        List<String> s = command.process("GETIMG:http://anasghira.com/its/test.png","");
        Assert.assertEquals(expected,s.get(0));
        Assert.assertEquals("IMGEND:YES",s.get(s.size()-1));

        expected = "IMG:FAILURE";
        Assert.assertEquals(expected,command.process("GETIMG:http://anasghira.com/test.jpg","").get(0));
    }
}
