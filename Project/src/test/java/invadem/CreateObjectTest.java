package invadem;

import invadem.models.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateObjectTest {
    @Test
    public void TestCreateBarrierComponent(){
        BarrierComponent b = new BarrierComponent(null);
        assertNotNull(b);
    }

    @Test
    public void TestCreateBarrier(){
        Barrier b = new Barrier(null,null,null,null,0,0);
        assertNotNull(b);

    }

    @Test
    public void TestCreateBarriers(){
        Barriers b = new Barriers(null, null, null, null);
        assertNotNull(b);
    }

    @Test
    public void TestCreateTank(){
        Tank t = new Tank(null, 0, 0);
        assertNotNull(t);
    }

    @Test
    public void TestCreateInvaders(){
        Invaders i = new Invaders(null, null, null,0,0);
        assertNotNull(i);
        assertEquals(40,i.getInvaders().size()); // check if 40 invaders are created
    }

    @Test
    public void TestCreateProjectile(){
        Projectile p = new Projectile(null , 0, 0, 1);
        assertNotNull(p);
    }

    @Test
    public void TestCreateRegularInvader(){
        RegularInvader r = new RegularInvader(null, 0, 0);
        assertNotNull(r);
    }

    @Test
    public void TestCreatePowerInvader(){
        PowerInvader p = new PowerInvader(null, 0, 0);
        assertNotNull(p);
    }

    @Test
    public void TestCreateArmouredInvader(){
        ArmouredInvader a = new ArmouredInvader(null, 0, 0);
        assertNotNull(a);
    }

    @Test
    public void TestCreateJudge(){
        Tank t = new Tank(null, 0, 0);
        Invaders i = new Invaders(null, null, null, 5, 5);
        Barriers b = new Barriers(null, null, null, null);
        Judge j = new Judge(t, i, b);
        assertNotNull(j);
    }
}
