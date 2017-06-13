package engine;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SmsCommandTest {


    @Test
    public void errorTest(){
        SmsCommand command = new SmsCommand();

        String s = command.process("hello:null","");
        Assert.assertEquals( "WEB:<h2>Mauvaise commande</h2>",s);

        s = command.process("nothing:null","");

        Assert.assertEquals( "WEB:<h2>Mauvaise commande</h2>",s);

        s = command.process("null","");

        Assert.assertEquals( "WEB:<h2>Mauvaise commande</h2>",s);
    }

    @Test
    public void okTest(){
        SmsCommand command = new SmsCommand();
        Assert.assertEquals("ITS:AVAILABLE", command.process("OK:",""));

    }

    @Test
    public void twitterConfTest(){
        SmsCommand command = new SmsCommand();

        Assert.assertEquals("TWITTERCONF:SUCCESS", command.process("TWITTERCONF:hello,hello,12344",""));


        Assert.assertEquals("TWITTERCONF:FAILURE", command.process("TWITTERCONF:hello,hello",""));
    }

    @Test
    public void twitterHomeFailure(){
        SmsCommand command = new SmsCommand();
        Assert.assertEquals("TWITTERHOME:FAILURE",command.process("TWITTERHOME:1234",""));
    }

    @Test
    public void tweetFailure(){
        SmsCommand command = new SmsCommand();
        Assert.assertEquals("TWEET:FAILURE",command.process("TWEET:1234",""));

        Assert.assertEquals("TWEET:FAILURE",command.process("TWEET:1234,ok",""));
    }

    @Test
    public void getimgTest(){
        SmsCommand command = new SmsCommand();
        String expected = "IMG:iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAIAAAACDbGyAAAALUlEQVR42mPQZWXnZ2J6K6MCQQwcjIzW7JwIPpyFwn8jowJUBdSLkAfyGRgYAGR/FW31B4/YAAAAAElFTkSuQmCC";
        Assert.assertEquals(expected,command.process("GETIMG:http://anasghira.com/its/test.png",""));

        expected = "IMG:FAILURE";
        Assert.assertEquals(expected,command.process("GETIMG:http://anasghira.com/test.jpg",""));
    }
}
