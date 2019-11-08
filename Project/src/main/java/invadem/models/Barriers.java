package invadem.models;

import processing.core.PImage;

public class Barriers{
    private Barrier[] barriers = new Barrier[3];

    // PImages
    private PImage _left[];
    private PImage _top[];
    private PImage _right[];
    private PImage _solid[];

    public Barriers(PImage left[], PImage top[], PImage right[], PImage solid[]) {
        this._left = left;
        this._top = top;
        this._right = right;
        this._solid = solid;
        setupObjects();
    }

    public void repairBarrier(int index) {
        barriers[index] = new Barrier(_left,_top,_right,_solid, 220 + index * 100,400);
    }

    private void setupObjects() {
        barriers[0] = new Barrier(_left,_top,_right,_solid, 220,400);
        barriers[1] = new Barrier(_left,_top,_right,_solid, 320,400);
        barriers[2] = new Barrier(_left,_top,_right,_solid, 420,400);
    }



    public void showGroup() {
        for (Barrier barrier : barriers)
            barrier.show();
    }
    public Barrier[] getBarriers() {
        return barriers;
    }
}
