package invadem.models;

import invadem.App;
import processing.core.PImage;

import java.util.LinkedList;

public class Attackable {
    protected LinkedList<Projectile> bullets = new LinkedList<Projectile>();
    protected float x, y;
    protected int attackPoint; // 攻击力
    protected int attackSpeed;
    private PImage bulletImg;


    public Attackable(float x, float y, int attackPoint, int attackSpeed, PImage bulletImg) {
        this.x = x;
        this.y = y;
        this.attackPoint = attackPoint;
        this.attackSpeed = attackSpeed;
        this.bulletImg = bulletImg;
    }

    public void attack() {
            bullets.add(new Projectile(bulletImg,x + 11,y, attackPoint));
    }

    public int getAttackSpeed()
    {
        return attackSpeed;
    }

    public LinkedList<Projectile> getBullets(){
        return bullets;
    }
}
