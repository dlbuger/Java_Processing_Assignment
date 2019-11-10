package invadem.models;

import invadem.App;
import processing.core.PImage;

import java.util.LinkedList;
import java.util.Random;

public class Invaders{
    // private Invader[] invaders;
    private int startX, startY, invadersLeft;  // (0,0) Invader's position

    private PImage[] armouredImg; // 1
    private PImage[] powerImg; // 2
    private PImage[] regularImg; // 3, 4

    private LinkedList<Invader> invaders;
    private LinkedList<Projectile> bullets;

    public Invaders(PImage[] regularImg, PImage[] powerImg, PImage[] armouredImg, int x, int y) {
        this.startX = x;
        this.startY = y;
        invadersLeft = 40;
        invaders = new LinkedList<Invader>();
        bullets = new LinkedList<Projectile>();
        this.regularImg = regularImg;
        this.powerImg = powerImg;
        this.armouredImg = armouredImg;
        setupInvaders();
    }

    private void setupInvaders() {
        int _tmpX = 0;
        int _tmpY = 0;
        for(int i = 0; i < invadersLeft; i++) {
            if (i % 10 == 0) {
                _tmpY ++;
                _tmpX = 0;
            }
            if(i < 10)
                invaders.add(new ArmouredInvader(armouredImg, startX + _tmpX * 30, startY + _tmpY * 30));
            if (i >= 10 && i < 20)
                invaders.add(new PowerInvader(powerImg, startX + _tmpX * 30, startY + _tmpY * 30));
            if(i >= 20)
                invaders.add(new RegularInvader(regularImg, startX + _tmpX * 30, startY + _tmpY * 30));
            _tmpX++;
        }
    }

    public void groupAttack() {
        Random r = new Random();
        int numOfBullets = r.nextInt(7);
        int number;
        for(int i = 0; i < numOfBullets; i++) {
            number = r.nextInt(40);
            invaders.get(number).attack();
            bullets.add(invaders.get(number).getBullets().getLast());
        }
    }

    public void showGroup() {
        for(Invader o:invaders)
            o.show();
    }

    public void moveGroup() {
        for(Invader o: invaders)
            o.move();
    }

    public LinkedList<Invader> getInvaders() {
        return invaders;
    }
    public LinkedList<Projectile> getBullets() {
        return bullets;
    }

    public void invaderKilled()
    {
        invadersLeft--;
    }

    public int getInvadersRemain()
    {
        return invadersLeft;
    }
}
