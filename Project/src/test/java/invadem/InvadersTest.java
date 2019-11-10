package invadem;

import invadem.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class InvadersTest {

    @Test
    public void testInvadersAttack(){
        Invaders i = new Invaders(null,null, null, 0, 0);
        i.groupAttack();
        assertNotNull(i.getBullets());
    }

    @Test
    public void testInvadersMove(){
        Invaders i = new Invaders(null, null, null, 0,0);
        assertEquals(0,i.getInvaders().getFirst().getX());
        System.out.println(i.getInvaders().getFirst().getY());
        assertEquals(30,i.getInvaders().getFirst().getY()); // Check Invaders.setupInvaders()
        i.moveGroup();
        assertNotEquals(0, i.getInvaders().getFirst().getY());
    }

    @Test
    public void testInvadersRemain(){
        Invaders i = new Invaders(null, null, null,0,0);
        i.invaderKilled();
        assertEquals(39,i.getInvadersRemain());
    }
}
