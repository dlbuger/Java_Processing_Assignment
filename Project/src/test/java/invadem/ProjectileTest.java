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

    @Test
    public void testProjectileIsDestroyed(){
        Projectile p = new Projectile(null, 0, 0, 1);
        p.hit(p.getHealth());
        assertTrue(p.isDestroyed());
    }

    @Test
    public void testProjectileAttackPoint(){
        Projectile p = new Projectile(null, 0, 0, 3);
        assertEquals(3, p.getHealth());
        p.hit(1);
        assertEquals(2, p.getHealth());
    }

    @Test
    public void testProjectileLocation(){
        Projectile p = new Projectile(null, 0, 0, 1);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    @Test
    public void testInvaderProjectileMove(){
        Invader i = new Invader(null,0,0,1,1,100,null);
        i.attack();
        Projectile p = i.getBullets().getLast();
        p.move(false);
        assertTrue(p.getY() > 0);
    }

    @Test
    public void testTankProjectileMove(){
        Tank t =  new Tank(null, 0, 300);
        t.attack();
        Projectile p = t.getBullets().getLast();
        p.move(true);
        assertTrue(p.getY() < 300);
        p.hit(p.getHealth());
        p.move(true);
    }
}
