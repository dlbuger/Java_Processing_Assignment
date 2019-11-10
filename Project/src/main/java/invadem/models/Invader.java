package invadem.models;

import invadem.App;
import processing.core.PImage;


public class Invader extends Attackable implements Destroyable{
    private PImage[] imgs;
    protected int health;
    private float speed = 0.5f;
    private float START_X;
    private int score;

    public Invader(PImage[] imgs, float x, float y, int health, int attackPoint, int score, PImage bulletImg) {
        super(x,y, attackPoint, 90, bulletImg);
        this.START_X = x;
        this.imgs = imgs;
        this.health = health;
        this.score = score;
    }

    public void show() {
        if (y > 480 - imgs[0].height)
            trigerGameOver();
        else if (health  > 0)
            gameOn();
        else if (health <= 0){
            x = 800;
            y = 0;
        }
    }

    public void move() {
        if(!isDestroyed()) {
            super.y += 0.25;
            super.x += speed;
            if (super.x > START_X + 20 || super.x < START_X - 20)
                speed *= -1;
        }
    }

    private void trigerGameOver() {
        App.gameState = App.END;
    }

    private void gameOn() {
        for(Projectile o:bullets) {
            o.show();
            o.move(false);
        }
        if (x % 2 == 0)
            App.game.image(imgs[0], x, y);
        else
            App.game.image(imgs[1], x, y);
    }

    public boolean isDestroyed() {
        if(health == 0)
            return true;
        return false;
    }

    public void hit(int attackPoint)
    {
        health -= attackPoint;
        System.out.println("Invader is hit!");
    }
    public int getScore()
    {
        return score;
    }

    public int getX()
    {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public int getWidth() {
        return imgs[0].width;
    }

    public int getHeight() {
        return imgs[0].width;
    }

    public int getHealth() {
        return health;
    }
}
