package invadem;

import invadem.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TankTest {

    @Test
    public void testTankProjectile() {
        Tank tank = new Tank(null,50,50);
        tank.attack();
        assertNotNull(tank.getBullets().getLast());
    }
    @Test
    public void testTankAttackSpeed(){
        Tank t = new Tank(null, 0, 0);
        assertEquals(80, t.getAttackSpeed());
        t.setAttackSpeed(10);
        assertEquals(10, t.getAttackSpeed());
    }

    @Test
    public void testTankIsNotDead() {
        Tank tank = new Tank(null,50,50);
        assertEquals(false, tank.isDestroyed());
    }

    @Test
    public void testTankHealth3(){
        Tank t = new Tank(null, 0, 0);
        assertEquals(3, t.getHealth());
    }

    @Test
    public void testTankHealth2(){
        Tank t = new Tank(null, 0, 0);
        t.hit(1);
        assertEquals(2, t.getHealth());
    }

    @Test
    public void testTankHealth1(){
        Tank t = new Tank(null, 0, 0);
        t.hit(2);
        assertEquals(1, t.getHealth());
    }

    @Test
    public void testTankIsDead() {
        Tank tank = new Tank(null,50,50);
        tank.setHealth(0);
        assertEquals(0, tank.getHealth());
        assertTrue(tank.isDestroyed());
    }

    @Test
    public void TestTankMoveRight(){
        Tank t = new Tank(null, 50, 50);
        assertTrue(t.getX() == 50);
        t.moveRight();
        assertTrue(t.getX() > 50);
    }

    @Test
    public void TestTankMoveLeft(){
        Tank t = new Tank(null, 50, 50);
        assertTrue(t.getX() == 50);
        t.moveLeft();
        assertTrue(t.getX() < 50);
    }

}
