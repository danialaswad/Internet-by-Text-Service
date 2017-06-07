package engine;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SmsCommandTest {


    @Test
    public void errorTest(){
        SmsCommand command = new SmsCommand();

        String s = command.process("hello:null");
        Assert.assertEquals( "<h2>Mauvaise commande</h2>",s);

        s = command.process("nothing:null");

        Assert.assertEquals( "<h2>Mauvaise commande</h2>",s);

        s = command.process("null");

        Assert.assertEquals( "<h2>Mauvaise commande</h2>",s);
    }

    @Test
    public void okTest(){
        SmsCommand command = new SmsCommand();
        Assert.assertEquals("ITS:AVAILABLE", command.process("OK:"));

    }

    @Test
    public void twitterConfTest(){
        SmsCommand command = new SmsCommand();

        Assert.assertEquals("TWITTERCONF:SUCCESS", command.process("TWITTERCONF:hello,hello,12344"));


        Assert.assertEquals("TWITTERCONF:FAILURE", command.process("TWITTERCONF:hello,hello"));
    }

    @Test
    public void twitterHomeFailure(){
        /*SmsCommand command = new SmsCommand();
        Assert.assertEquals("TWITTERHOME:FAILURE",command.process("TWITTERHOME:1234"));*/
    }
}
