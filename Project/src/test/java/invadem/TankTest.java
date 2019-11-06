package invadem;

import invadem.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TankTest {

    @Test
    public void testTankConstruction() {
        Tank tank = new Tank(null);
        assertNotNull(tank);
    }

    @Test
    public void testTankProjectile() {
        Tank tank = new Tank(null);
        tank.attack();
        assertNotNull(tank.getBullets().getLast());
    }

    @Test
    public void testTankIsNotDead() {
        Tank tank = new Tank(null);
        assertEquals(false, tank.isDestroyed());
    }

    @Test
    public void testTankIsDead() {
        Tank tank = new Tank(null);
        tank.hit(tank.getHealth());
        assertTrue(tank.isDestroyed());
    }

}
