package invadem.models;

import invadem.App;
import processing.core.PImage;

public class Projectile implements Destroyable{
    private PImage img;
    private float x, y;
    private int health;
    private float speed = 1.3f;

    public  Projectile(PImage img,float x, float y, int health) {
        this.x = x;
        this.y = y;
        this.health = health; // 子弹的health就是发射对象的攻击力
        this.img = img;
    }

    public boolean isDestroyed() {
        if(health <= 0)
            return true;
        else
            return false;
    }

    public void hit(int attackPoint) {
        health -= attackPoint ;
    }

    public void show() {
        if(!isDestroyed())
            App.game.image(img,x,y);
    }
    public void move(boolean isTank) {
        if(!isDestroyed()) {
            if(isTank)
                y -= speed;
            else
                y += speed;
        }
        else
            x = 700;
    }

    public int getY() {
        return (int)y;
    }

    public int getWidth() {
        return img.width;
    }

    public int getHeight() {
        return img.height;
    }

    public int getX() {
        return (int)x;
    }
    public int getHealth() {
        return health;
    }
    public int getAttackPoint() {
        return health;
    }

    // This is never used in the program, only used by TestCase;
    public boolean intersect(Destroyable target) {
        if(
                x < (target.getX() + 13) &&
                        (x + 1) > target.getX() &&
                        y < (target.getY() + 13) &&
                        (y + 3) > target.getY()
        )
            return true;
        else
            return false;
    }
}
