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
    private int level = 1;
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

    public void settings() {
        size(SIZE_X, SIZE_Y);
        // debug();
    }

    private void buildObjects() {
        tank = new Tank(tankImage,320,450);
        if(debug) {
            tank.setAttackSpeed(0);
            tank.setHealth(10);
        }
        barriers = new Barriers(leftBarriers, topBarriers, rightBarriers, solidBarriers);
        invaders = new Invaders(regularInvaderImg, powerInvaderImg, armouredInvaderImg, 180, 20);
        judge = new Judge(tank, invaders, barriers);
        keys = new boolean[3];
        startFont = createFont("PressStart2P-Regular.tff", 24, true);
        UIFont = createFont("PressStart2P-Regular.tff", 16, true);
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
        }
        else if (gameState == END)
            gameOver();
        else if (gameState == NEXT) {
            background(0);
            image(_nextLevel, (SIZE_X - _gameOver.width) / 2, 200);
            if(level >= 3)
            {
                fill(255);
                textFont(UIFont);
                text("Your Barriers will NOT be repaired", SIZE_X / 2, 250);
            }
            if(level >= 4){
                fill(255);
                textFont(UIFont);
                text("Your Tank Health will NOT be reset\nBut Will your Level up,\nyou will gain 1 health", SIZE_X / 2, 300);
            }
            if(millis() > timeCheck + 2000) {
                nextLevel();
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
        text("Press <ENTER> to start Game!", SIZE_X / 2, SIZE_Y - 100);
        textAlign(CENTER, CENTER);
        textSize(20);
        textFont(startFont);
        text("Use <Arrow Key> to move, <SPACE> to shoot!", SIZE_X / 2, SIZE_Y / 2);
        if (keyCode == ENTER)
            gameState = ON;
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
        int currentTankAttackSpeed = tank.getAttackSpeed();
        int currentTankHealth = tank.getHealth();
        if(level < 3)
            barriers = new Barriers(leftBarriers, topBarriers, rightBarriers, solidBarriers);
        invaders = new Invaders(regularInvaderImg, powerInvaderImg, armouredInvaderImg, 180, 20);
        tank = new Tank(tankImage, 320, 450);
        if(level >= 4)
            tank.setHealth(currentTankHealth + 1);
        tank.setAttackSpeed(currentTankAttackSpeed);
        if(debug) {
            tank.setAttackSpeed(0);
            tank.setHealth(10);
        }
        judge.update(tank,invaders,barriers);
        level++;
    }
    private void gameOver() {
        noLoop();
        background(0);
        image(_gameOver, (SIZE_X - _gameOver.width) / 2, 200);
        int score = 10000;
        if(judge.getScoreEarned() > score) {
            score = judge.getScoreEarned();
            textAlign(CENTER,CENTER);
            fill(255);
            textFont(UIFont);
            text("New Highest Record !!!", (SIZE_X) / 2, 300);
        }
        textAlign(CENTER,CENTER);
        fill(255);
        textFont(UIFont);
        text("Current Highest Score: " + score, (SIZE_X) / 2, 250);
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
            if(debug)
                if(keyCode == BACKSPACE)
                    judge.nuke();
        skills();
    }

    private void skills()
    {
        if(key == '1')
            judge.repairBarrier(0);
        if(key == '2')
            judge.repairBarrier(1);
        if(key == '3')
            judge.repairBarrier(2);
        if(key == 'n')
            judge.increaseFireSpeed();
    }
    // UI
    private void drawUI() {
        drawInvaderHitCount();
        drawTankInfo();
        drawInvadersLeft();
        drawLevel();
        drawSkills();
        if(judge.getScoreEarned() >= 10000 && judge.getScoreEarned() <= 10500){
            fill(255);
            textFont(UIFont);
            text(" <Chain Reaction> is activated!", SIZE_X/2, 230);
            text("When you kill one invader, 40% another will be destroyed.",SIZE_X/2, 255);
        }
        if(debug)
            showDebug();
    }
    private void drawTankInfo()
    {
        for (int i = 0; i < tank.getHealth(); i++) {
            fill(255);
            textFont(UIFont);
            text("Tank   HP : "+ tank.getHealth(), 60, 30);
            fill(246, 70, 91);
            noStroke();
            rect( 15 + i * 12, 55,10,4,1f);
        }
        fill(255);
        textFont(UIFont);
        text("Fire Gap:\n" + tank.getAttackSpeed(), 560, 450);
    }

    private void drawSkills()
    {
        // Fire Speed
        if(judge.getScore() >= 7000)
            drawGreen_O(473, 303);
        else
            drawRed_O(473,303);
        if(tank.getAttackSpeed() == 0)
            drawBlue_O(473,303);
        text("Upgrade FireRate",560,300);

        //  Repair Barriers
        if(judge.getScore() >= 5000)
            drawGreen_O(473,328);
        else
            drawRed_O(473,328);
        text("<Repair Barriers>", 560,325);

        // Chain Reaction
        if(judge.getScoreEarned() <= 10000)
            drawRed_O(473,353);
        else
            drawBlue_O(473,353);
        text("<Chain Reaction>", 560, 350);
    }

    private void drawRed_O(int x, int y) {
        fill(255,102,102);
        ellipse(x,y,10,10);
        fill(130);
    }

    private void drawGreen_O(int x, int y){
        fill(122,255,122);
        ellipse(x,y,10,10);
        fill(255);
    }

    private void drawBlue_O(int x, int y){
        fill(51,153,255);
        ellipse(x,y,10,10);
        fill(255);

    }

    private void drawInvaderHitCount() {
        int scoreUsed = judge.getScoreEarned() - judge.getScore();
        int x = 5;
        if(judge.getScoreEarned() != 0){
            fill(51,153,255);
            stroke(0,0f);
            rect(525,40,100 * judge.getScore() / judge.getScoreEarned(),20);
            fill(255,102,102);
            rect(525 + 100 * judge.getScore() / judge.getScoreEarned() , 40,100 * scoreUsed / judge.getScoreEarned()  , 20);
        }
        else{
            fill(51,153,255);
            stroke(0,0f);
            rect(525,40,50,20);
            fill(255,102,102);
            rect(575,40,50,20);
        }
        fill(255);
        rect(525,60,100,7);
        textFont(UIFont);
        textSize(16);
        text(judge.getScoreEarned(),575,23);
    }

    private void drawInvadersLeft()
    {
        fill(255);
        textSize(14);
        textFont(UIFont);
        text(invaders.getInvadersLeft() + "\nInvaders left" ,70 , 445);
    }
    private void showDebug()
    {
        fill(255);
        textSize(14);
        textFont(UIFont);
        text("Bullets Count:", 70, 60);
        text(tank.getBullets().size() + "", 65,80);
        text("Invaders Attack \nSpeed: " + invadersAttackSpeed, 70, 115);
        text("Debug Mode", 65,250);
        text("<BackSpace> \nNUKE", 65, 285);
        text("Tick: " + frameCount, 65, 230);
    }
    private void drawLevel()
    {
        fill(255);
        textSize(14);
        textFont(UIFont);
        text("Level " + level ,310 , 20);
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