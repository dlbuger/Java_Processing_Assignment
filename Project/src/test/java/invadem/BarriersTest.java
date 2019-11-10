package invadem;

import invadem.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class BarriersTest {
    @Test
    public void TestGetBarriers(){
        Barriers b = new Barriers(null, null, null, null);
        assertNotNull(b);
        assertNotNull(b.getBarriers());
        assertEquals(3, b.getBarriers().length);
    }

    @Test
    public void TestRepairBarrier(){
        Barriers bs = new Barriers(null, null, null, null);
        Barrier b1 = bs.getBarriers()[0];
        for(BarrierComponent o: b1.getBarrierComponents())
            o.hit(o.getHealth());
        assertTrue(b1.isAllDestroyed());
        bs.repairBarrier(0);
        assertFalse(bs.getBarriers()[0].isAllDestroyed());
    }
}
