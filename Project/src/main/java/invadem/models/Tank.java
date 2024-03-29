package invadem.models;

import invadem.App;
import processing.core.PImage;
import java.util.LinkedList;

import static invadem.App.game;

public class Tank extends Attackable implements Destroyable {

    private int moveSpeed;
    private int health;
    private PImage img;

// 320, 450
    public Tank(PImage img, int x, int y) {
        super(x, y, 1,80, App.regularBullet);
        health = 3;
        this.img = img;
        moveSpeed = 3;
    }

    public void show() {
        if(!isDestroyed()) {
            // Show Tank
            game.image(img,x,y);
            // Show Bullets
            for (Projectile b:bullets)
                b.show();
            // Bullets moving
            for (Projectile b: bullets)
                b.move(true);
        }
    }

    public void moveLeft() {
        x -= moveSpeed;
    }

    public void moveRight() {
        x += moveSpeed;
    }


    public void hit(int attackPoint) {
        health -=  attackPoint;
        System.out.println("[ Tank is hit ]");
    }
    public boolean isDestroyed() {
        if(health <= 0)
            return true;
        else
            return false;
    }

    public void setAttackSpeed(int a){
        attackSpeed = a;
    }

    public void setHealth(int a) {
        health = a;
    }


    public int getX() {
        return (int)x;
    }
    public int getY() {
        return (int)y;
    }
    public int getWidth() {
        return img.width;
    }
    public int getHeight() {
        return (int)y;
    }
    public int getHealth()
    {
        return health;
    }
    public LinkedList<Projectile> getBullets() {
        return bullets;
    }

}