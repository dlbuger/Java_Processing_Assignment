package invadem;

import invadem.models.*;
import org.junit.Test;
import static org.junit.Assert.*;


public class InvaderTest {
    @Test
    public void testInvaderConstruction() {
        Invader inv = new Invader(null,0,0, 1, 1, 100, null);
        assertNotNull(inv);

    }

    @Test
    public void testInvaderFireProjectile() {
        Invader inv = new Invader(null,0,0, 1, 1, 100, null);
        inv.attack();
        assertNotNull(inv.getBullets());
    }

    @Test
    public void testInvaderIsNotDead() {
        Invader inv = new Invader(null,0,0, 1, 1, 100, null);
        assertEquals(false, inv.isDestroyed());
    }

    @Test
    public void testInvaderIsDead() {
        Invader inv = new Invader(null,0,0, 1, 1, 100, null);
        inv.hit(inv.getHealth());
        assertEquals(true, inv.isDestroyed());
    }

    @Test
    public void testRegularInvaderScore(){
        Invader inv1 = new RegularInvader(null, 0, 0);
        Invader inv2 = new PowerInvader(null, 0, 0);
        Invader inv3 = new ArmouredInvader(null, 0, 0);
        assertEquals(100, inv1.getScore());
        assertEquals(250, inv2.getScore());
        assertEquals(250, inv3.getScore());
    }

    @Test
    public void testInvaderMove(){
        Invader o = new RegularInvader(null, 0, 0);
        assertEquals(0,o.getX());
        assertEquals(0,o.getY());
        for(int i = 0; i < 300; i++)
            o.move();
        assertNotEquals(0,o.getX());
        assertNotEquals(0,o.getY());
    }

}
