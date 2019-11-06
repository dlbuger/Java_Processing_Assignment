package invadem;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import invadem.models.*;



public class App extends PApplet {
    public static PApplet game;

    //
    public static final int START = 2;
    public static final int ON = 1;
    public static final int END = 0;
    public static final int NEXT = 3;

    public static int gameState = START;
    private final int SIZE_X = 640;
    private final int SIZE_Y = 480;
    private int timeCheck = millis();
    private int timeCheck4Tank = millis();
    private int invadersAttackSpeed = 3000;

    private boolean debug = false;
    // Images
    public static PImage regularBullet;
    public static PImage powerBullet;
    private PImage _gameOver;
    private PImage _nextLevel;
    private PImage tankImage;
    private PImage[] leftBarriers = new PImage[3];
    private PImage[] topBarriers = new PImage[3];
    private PImage[] rightBarriers = new PImage[3];
    private PImage[] solidBarriers = new PImage[3];


    public static PImage[] regularInvaderImg = new PImage[2];
    private PImage[] powerInvaderImg = new PImage[2];
    private PImage[] armouredInvaderImg = new PImage[2];

    // Objects
    private Tank tank;
    private Barriers barriers;
    private Invaders invaders;
    private Judge judge;

    private boolean[] keys;
    private PFont startFont;
    private PFont UIFont;


    public void setup() {
        // Load Images Here
        frameRate(60);
        regularBullet = loadImage("projectile.png");
        powerBullet = loadImage("projectile_lg.png");
        _gameOver = loadImage("gameover.png");
        _nextLevel = loadImage("nextlevel.png");
        tankImage = loadImage("tank1.png");
        // Barriers Images
        leftBarriers[0] = loadImage("barrier_left3.png");
        leftBarriers[1] = loadImage("barrier_left2.png");
        leftBarriers[2] = loadImage("barrier_left1.png");

        topBarriers[0] = loadImage("barrier_top3.png");
        topBarriers[1] = loadImage("barrier_top2.png");
        topBarriers[2] = loadImage("barrier_top1.png");

        rightBarriers[0] = loadImage("barrier_right3.png");
        rightBarriers[1] = loadImage("barrier_right2.png");
        rightBarriers[2] = loadImage("barrier_right1.png");

        solidBarriers[0] = loadImage("barrier_solid3.png");
        solidBarriers[1] = loadImage("barrier_solid2.png");
        solidBarriers[2] = loadImage("barrier_solid1.png");

        // Invaders Images
        regularInvaderImg[0] = loadImage("invader1.png");
        regularInvaderImg[1] = loadImage("invader2.png");
        powerInvaderImg[0] = loadImage("invader1_power.png");
        powerInvaderImg[1] = loadImage("invader2_power.png");
        armouredInvaderImg[0] = loadImage("invader1_armoured.png");
        armouredInvaderImg[1] = loadImage("invader2_armoured.png");
        buildObjects();
    }

    public App() {
        game = this;
    }
    private void buildObjects() {
        tank = new Tank(tankImage);
        if(debug)
            tank.setAttackSpeed(1);
        barriers = new Barriers(leftBarriers, topBarriers, rightBarriers, solidBarriers);
        invaders = new Invaders(regularInvaderImg, powerInvaderImg, armouredInvaderImg, 180, 20);
        judge = new Judge(tank, invaders, barriers);
        keys = new boolean[3];
        startFont = createFont("PressStart2P-Regular.tff", 24, true);
        UIFont = createFont("PressStart2P-Regular.tff", 16, true);
    }

    public void settings() {
        size(SIZE_X, SIZE_Y);
    }

    public void draw() {
        gameStart();
        if (gameState == ON) {
            gameOn();
            if (keyPressed)
                if (keys[0] && tank.getX() >= 180)
                    tank.moveLeft();
                if (keys[1] && tank.getX() <= 460)
                    tank.moveRight();
                if (keys[2] && millis() > timeCheck4Tank + tank.getAttackSpeed()) {
                    tank.attack();
                    timeCheck4Tank = millis();
                }

            if(invaders.getInvadersLeft() == 0)
                timeCheck = millis();
        } else if (gameState == END)
            gameOver();
        else if (gameState == NEXT) {
            nextLevel();
            if(millis() > timeCheck + 2000) {
                gameState = ON;
                if(invadersAttackSpeed >= 2000)
                    invadersAttackSpeed -= 1000;
            }
        }
    }

    // Scene
    private void gameStart() {
        background(255);
        //  按任意键开始游戏
        textAlign(CENTER, TOP);
        textSize(32);
        textFont(startFont);
        fill(0);
        text("Press any key to start Game!", SIZE_X / 2, SIZE_Y - 100);

        textAlign(CENTER, CENTER);
        textSize(20);
        textFont(startFont);
        text("Use <Arrow Key> to move, <SPACE> to shoot!", SIZE_X / 2, SIZE_Y / 2);
        if (keyPressed)
            gameState = ON;
    }

    private void selectMode() {
        // ToDo 选择模式 Duo or Solo
    }


    private void gameOn() {
        background(0);
        drawUI();

        barriers.showGroup();
        invaders.showGroup();
        if(!debug)
            invaders.moveGroup();
        tank.show();
        if (millis() > timeCheck + invadersAttackSpeed) {
            invaders.groupAttack();
            timeCheck = millis();
        }
        judge.check();
    }

    private void nextLevel() {
        System.out.println("[NEXT LEVEL!!!]");
        background(0);
        image(_nextLevel, (SIZE_X - _gameOver.width) / 2, SIZE_Y / 2);
        barriers = new Barriers(leftBarriers, topBarriers, rightBarriers, solidBarriers);
        invaders = new Invaders(regularInvaderImg, powerInvaderImg, armouredInvaderImg, 180, 20);
        tank = new Tank(tankImage);
        judge.update(tank,invaders,barriers);

    }

    private void gameOver() {
        noLoop();
        background(0);
        image(_gameOver, (SIZE_X - _gameOver.width) / 2, SIZE_Y / 2);
    }

    // Control
    public void keyPressed() {
            if (keyCode == LEFT)
                keys[0] = true;
            if (keyCode == RIGHT)
                keys[1] = true;
            if (key == ' ')
                keys[2] = true;
    }

    public void keyReleased() {
            if (keyCode == LEFT)
                keys[0] = false;
            if (keyCode == RIGHT)
                keys[1] = false;
            if (key == ' ')
                keys[2] = false;
    }


    // UI
    private void drawUI() {
        drawInvaderHitCount();
        drawTankInfo();
        drawInvadersLeft();
        if(debug)
            showDebug();
    }

    private void drawTankInfo()
    {
        for (int i = 0; i < tank.getHealth(); i++) {
            fill(255);
            textFont(UIFont);
            text("Tank   HP", 55, 20);
            fill(246, 70, 91);
            textFont(UIFont);
            text("-", 20 + i * 10, 35);
        }
    }
    private void drawInvaderHitCount() {
        fill(255);
        textSize(14);
        textFont(UIFont);
        text("Points Earned:", 555, 20);
        text(judge.getHitCount() + "", 565, 35);
    }

    private void drawInvadersLeft()
    {
        fill(255);
        textSize(14);
        textFont(UIFont);
        text(invaders.getInvadersLeft() + "  Enimes left" ,310 , 20);
    }

    private void showDebug()
    {
        text(invadersAttackSpeed, 50, 400);
        text(tank.getBullets().size() + "", 310,35);
        text(frameCount, 620, 400);
    }

    private void debug()
    {
        if(!debug)
            debug = true;
    }

    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }
}
