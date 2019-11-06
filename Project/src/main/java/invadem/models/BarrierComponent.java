package invadem.models;

import processing.core.PImage;
import invadem.App;

public class BarrierComponent implements Destroyable{
    private int health = 3;
    private PImage imgs[] = new PImage[3];
    private int x;
    private int y;
    // 0 -> 破损不堪
    // 1 -> 久经沙场
    // 2 -> 崭新出场

    public BarrierComponent(PImage[] imgs) {
        this.imgs = imgs;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isDestroyed() {
        if (health <= 0)
            return true;
        return false;
    }

    public void hit(int attackPoint) {
        health -= attackPoint;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return imgs[0].width;
    }

    public int getHeight() {
        return imgs[0].width;
    }
    public void show() {
        if (isDestroyed())
            x = 700;
        else
            if (health >= 0 && health <= 3)
                App.game.image(imgs[health - 1], x, y);
            else
                App.game.image(imgs[0], x, y);
    }
    public int getHealth() {
        return health;
    }

    @Override
    public int getAttackPoint() {
        return 1;
    }
}
