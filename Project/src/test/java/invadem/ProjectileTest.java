package invadem;

import invadem.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProjectileTest {


    @Test
    public void testProjectileConstruction() {
        Projectile proj = new Projectile(null, 0, 0, 1);
        assertNotNull(proj);
    }

    @Test
    public void testProjectileIntersectFalse() {
        Projectile proj = new Projectile(null,0,0, 1 );
        Invader inv = new RegularInvader(null,50,50);
        Tank tank = new Tank(null,50,50);
        assertFalse(proj.intersect(inv));
        assertFalse(proj.intersect(tank));
    }

    @Test
    public void testProjectileIntersectInvaderTrue(){
        Projectile proj = new Projectile(null,50,50, 1 );
        Invader inv = new RegularInvader(null,50,50);
        assertTrue(proj.intersect(inv));
    }

    @Test
    public void testProjectileIntersectTankTrue(){
        Tank tank = new Tank(null,50,50);
        Projectile proj = new Projectile(null,tank.getX(),tank.getY(), 1 );
        assertTrue(proj.intersect(tank));
    }
}
