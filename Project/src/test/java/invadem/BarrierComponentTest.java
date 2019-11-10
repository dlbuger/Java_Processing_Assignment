package invadem;

import invadem.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class BarrierComponentTest {
    @Test
    public void testBarrierComponentNotDestroyed() {
        BarrierComponent b = new BarrierComponent(null);
        assertEquals(false, b.isDestroyed());
    }

    @Test
    public void testBarrierComponentHitPointsMax3() {
        BarrierComponent b = new BarrierComponent(null);
        assertEquals(3, b.getHealth());
    }

    @Test
    public void testBarrierComponentHitPointsMax2() {
        BarrierComponent b = new BarrierComponent(null);
        b.hit(1);
        assertEquals(2, b.getHealth());
    }

    @Test
    public void testBarrierComponentHitPointsMax1() {
        BarrierComponent b = new BarrierComponent(null);
        b.hit(2);
        assertEquals(1, b.getHealth());
    }

    @Test
    public void testBarrierIsDestroyed() {
        BarrierComponent b = new BarrierComponent(null);
        b.hit(b.getHealth());
        assertEquals(true, b.isDestroyed());
    }

    @Test
    public void TestBarrierSetLocation(){
        BarrierComponent b = new BarrierComponent(null);
        b.setLocation(50, 50);
        assertEquals(50, b.getX());
        assertEquals(50, b.getY());
    }

    @Test
    public void TestBarrierComponentHealth3(){
        BarrierComponent b =new BarrierComponent(null);
        b.show();
        assertEquals(3, b.getHealth());
    }

    @Test
    public void TestBarrierComponentHealth2(){
        BarrierComponent b =new BarrierComponent(null);
        b.hit(1);
        b.show();
        assertEquals(2, b.getHealth());
    }

    @Test
    public void TestBarrierComponentHealth1(){
        BarrierComponent b =new BarrierComponent(null);
        b.hit(2);
        b.show();
        assertEquals(1, b.getHealth());
    }

    @Test
    public void TestBarrierComponentDestroyed(){
        BarrierComponent b =new BarrierComponent(null);
        b.hit(b.getHealth());
        assertEquals(0, b.getHealth());
        assertTrue(b.isDestroyed());
        b.show();
    }
}
