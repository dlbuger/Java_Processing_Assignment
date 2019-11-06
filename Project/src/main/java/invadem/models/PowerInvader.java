package invadem.models;

import invadem.App;
import processing.core.PImage;

public class PowerInvader extends Invader {
    public PowerInvader(PImage[] img, float x, float y)
    {
        super(img, x, y,1,3,250, App.powerBullet);
    }
}
