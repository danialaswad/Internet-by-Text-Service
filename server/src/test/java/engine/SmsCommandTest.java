package engine;


import org.junit.Assert;
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
        Assert.assertEquals("OK", command.process("OK:"));

    }

}
