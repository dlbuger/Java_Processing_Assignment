package invadem.models;

import invadem.App;
import processing.core.PImage;

public class ArmouredInvader extends Invader {
    public ArmouredInvader(PImage[] img, float x, float y)
    {
        super(img, x, y,3, 1,250, App.regularBullet);
    }
}
