package invadem.models;

import invadem.App;
import processing.core.PImage;

public class RegularInvader extends Invader {
    public RegularInvader(PImage[] img, float x, float y)
    {
        super(img, x, y,1,1,100, App.regularBullet);
    }
}
