package invadem.models;

import invadem.App;
import processing.core.PImage;


public class Barrier {
    // Images
    private PImage _left[];
    private PImage _top[];
    private PImage _right[];
    private PImage _solid[];
    // Objects
    private BarrierComponent[] barrier;
    // Position
    private final int X;
    private final int Y;

    public Barrier(PImage left[], PImage top[], PImage right[], PImage solid[], int X, int Y) {
        this._left = left;
        this._top = top;
        this._right = right;
        this._solid = solid;
        this.X = X;
        this.Y = Y;
        barrier = new BarrierComponent[7];
        setupBarrierComponentsImages();
        setupBarrierComponentsLacation();
    }

    private void setupBarrierComponentsImages() {
        barrier[0] = new BarrierComponent(_left);
        barrier[1] = new BarrierComponent(_top);
        barrier[2] = new BarrierComponent(_right);
        for(int i = 3; i < 7; i++)
            barrier[i] = new BarrierComponent(_solid);
    }

    public void setupBarrierComponentsLacation() {
        for(int i = 0; i< 7; i++) {
            if (i < 3) // 0, 1, 2
                barrier[i].setLocation(X + i * 8, Y);
            else if (i >= 3 && i < 5) // 3, 4
                barrier[i].setLocation(X, Y + (i - 2) * 8);
            else if (i >= 5) // 5, 6
                barrier[i].setLocation(X + 16, Y + (i - 4) * 8);
        }
    }

    public void show() {
        for(BarrierComponent o:barrier)
            o.show();
    }

    public boolean isAllDestroyed()
    {
        boolean _temp = false;
        for(BarrierComponent o: barrier)
            if(o.isDestroyed())
                _temp = true;
            else
                _temp = false;
        return _temp;
    }

    public BarrierComponent[] getBarrierComponents() {
        return barrier;
    }
}
