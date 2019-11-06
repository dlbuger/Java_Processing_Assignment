package invadem.models;

public interface Destroyable {
    public boolean isDestroyed();
    public void hit(int attackPoint);
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();
    public int getHealth();
    public int getAttackPoint();
}
