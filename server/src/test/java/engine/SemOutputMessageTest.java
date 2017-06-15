package engine;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Antoine on 13/06/2017.
 */
public class SemOutputMessageTest {

    @Test
    public void semaphoreTest(){

        for (int i = 0; i < 1000;i++){
            try {
                SemOutputMessage.getInstance().getSemaphore().acquire();
                Assert.assertTrue(SemOutputMessage.getInstance().getSemaphore().availablePermits() == 0);
                SemOutputMessage.getInstance().getSemaphore().release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assert.assertTrue(SemOutputMessage.getInstance().getSemaphore().availablePermits() == 1);

    }


}
