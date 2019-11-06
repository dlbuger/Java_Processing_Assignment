package invadem;

import invadem.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class BarrierTest {
    @Test
    public void barrierConstruction() {
        BarrierComponent b = new BarrierComponent(null);
        b.hit(1);
        assertNotNull(b);
    }

    @Test
    public void testBarrierNotDestroyed() {
        BarrierComponent b = new BarrierComponent(null);
        assertEquals(false, b.isDestroyed());
    }

    @Test
    public void testBarrierHitPointsMax3() {
        BarrierComponent b = new BarrierComponent(null);
        assertEquals(3, b.getHealth());
    }

    @Test
    public void testBarrierHitPointsMax2() {
        BarrierComponent b = new BarrierComponent(null);
        b.hit(1);
        assertEquals(2, b.getHealth());
    }

    @Test
    public void testBarrierHitPointsMax1() {
        BarrierComponent b = new BarrierComponent(null);
        b.hit(2);
        assertEquals(1, b.getHealth());
    }


    @Test
    public void testBarrierIsDestroyed() {
        BarrierComponent b = new BarrierComponent(null);
        b.hit(3);
        assertEquals(true, b.isDestroyed());
    }

}
