package invadem;

import invadem.models.*;
import java.util.Iterator;
import java.util.Random;

public class Judge {
    private Tank tank;
    private Invaders invaders;
    private Barriers barriers;
    private int score = 0;
    private int scoreEarned = 0;
    private int fullScore = 0;


    public Judge(Tank tank, Invaders invaders, Barriers barriers) {
        this.tank = tank;
        this.invaders = invaders;
        this.barriers = barriers;
        for (Invader o : invaders.getInvaders())
            fullScore += o.getScore();
        System.out.println("[Full Score] --> " + fullScore);
    }

    public void update(Tank tank, Invaders invaders, Barriers barriers) {
        //  在进入下一个level的时候传入新的invaders和barriers
        this.invaders = invaders;
        this.barriers = barriers;
        this.tank = tank;
    }

    public void check() {
        check4Tank();
        checkNextLevel();
        checkTankAlive();
        check4Invader();
        check4Crash();
        checkBulletInside();
    }

    public void nuke() {
        // 用来测试是否能达成胜利条件
        for (Invader o : invaders.getInvaders()) {
            o.hit(o.getHealth());
            invaders.invaderKilled();
        }
    }

    private void check4Crash() {
        for (Invader invader : invaders.getInvaders()) {
            for (int i = 0; i < barriers.getBarriers().length; i++)
                for (BarrierComponent o : barriers.getBarriers()[i].getBarrierComponents())
                    if (_check(invader, o)) {
                        o.hit(invader.getHealth()); // 传入子弹的攻击力
                        invader.hit(1);
                        if (invader.isDestroyed()) {
                            increaseScore(invader.getScore());
                            invaders.invaderKilled();
                        }
                    }
            if (_check(invader, tank)) {
                invader.hit(1);
                tank.hit(invader.getHealth());
                if (invader.isDestroyed()) {
                    increaseScore (invader.getScore());
                    invaders.invaderKilled();
                }
            }
        }
    }

    private void checkBulletInside()
    {
        // 不会报错
        Iterator<Projectile> iterator4Invader = invaders.getBullets().iterator();
        while(iterator4Invader.hasNext()) {
            Projectile o = iterator4Invader.next();
            if(o.getY() >= App.game.height)
                iterator4Invader.remove();
        }
        Iterator<Projectile> iterator4Tank = tank.getBullets().iterator();
        while(iterator4Tank.hasNext()){
            Projectile o = iterator4Tank.next();
            if(o.getY() <= 0)
                iterator4Tank.remove();
        }
    }


    private void check4Tank() {
        Iterator<Projectile> iterator = tank.getBullets().iterator();
        while(iterator.hasNext()) {
            Projectile bullet = iterator.next();
            for(Invader invader: invaders.getInvaders()) {
                if (_check(bullet, invader)) {
                    invader.hit(bullet.getHealth());
                    bullet.hit(bullet.getHealth());
                    iterator.remove();
                    if (invader.isDestroyed()) {
                        increaseScore(invader.getScore());
                        invaders.invaderKilled();
                        Check4chainReaction();
                    }
                }
            }
                if (checkBarrier(bullet)) {
                    bullet.hit(bullet.getHealth());
                    iterator.remove();
            }
        }
    }

    private void check4Invader(){
        Iterator<Projectile> iterator = invaders.getBullets().iterator();
        while(iterator.hasNext()){
            Projectile bullet = iterator.next();
            if(_check(bullet, tank))
            {
                tank.hit(bullet.getHealth());
                bullet.hit(bullet.getHealth());
                iterator.remove();
            }
            if(checkBarrier(bullet)) {
                bullet.hit(bullet.getHealth());
                iterator.remove();
            }
        }
    }

    private boolean _check(Destroyable bullet, Destroyable target) {
        if(
                bullet.getX() < (target.getX() + target.getWidth()) &&
                        (bullet.getX() + bullet.getWidth()) > target.getX() &&
                        bullet.getY() < (target.getY() + target.getHeight()) &&
                        (bullet.getY() + bullet.getHeight()) > target.getY()
        )
            return true;
        else
            return false;
    }

    private boolean checkBarrier(Destroyable bullet) {
        for(int i = 0; i< barriers.getBarriers().length; i++)
            for(BarrierComponent o:barriers.getBarriers()[i].getBarrierComponents())
                if(_check(bullet, o)) {
                    o.hit(bullet.getHealth()); // 传入子弹的攻击力
                    return true;
                }
        return false;
    }

    private void Check4chainReaction()
    {
        Random r = new Random();
        int condition = r.nextInt(10);
        if (condition > 6 && scoreEarned > 10000) {
            //  40% TO Active chain Reaction! Rate will decrease when invaders are killed
            System.out.println("Chain Reaction!");
            r = new Random();
            int _index = r.nextInt(40);
            if(!invaders.getInvaders().get(_index).isDestroyed()){
                invaders.getInvaders().get(_index).hit(invaders.getInvaders().get(_index).getHealth());
                score += invaders.getInvaders().get(_index).getScore();
                invaders.invaderKilled();
            }
        }
    }

    public void repairBarrier(int index)
    {
        if(barriers.getBarriers()[index].isAllDestroyed() && score >= 5000) {
            barriers.repairBarrier(index);
            score -= 5000;
        }
        else
            System.out.println("Insufficient Score!");
        if(!barriers.getBarriers()[index].isAllDestroyed())
            System.out.println("The barrier has not been Destroyed!");

    }

    public void increaseFireSpeed(){
        if(score >= 7000 && tank.getAttackSpeed() != 0) {
            if (tank.getAttackSpeed() - 15 >= 0)
                tank.setAttackSpeed(tank.getAttackSpeed() - 15);
            else
                tank.setAttackSpeed(0);
            score -= 7000;
        }
        else
            System.out.println("Insufficient Score!");
    }

    private void increaseScore(int _score){
        score += _score;
        scoreEarned += _score;
    }

    private void checkTankAlive()
    {
        if(tank.isDestroyed())
            App.gameState = App.END;
    }

    private void checkNextLevel()
    {
        if (invaders.getInvadersRemain() <= 0)
            App.gameState = App.NEXT;
    }

    public int getScoreEarned() {
        return scoreEarned;
    }

    public int getScore(){
        return score;
    }
}