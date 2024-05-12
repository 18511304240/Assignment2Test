import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RunningMario_completed extends GameEngine {
    // Main Function
    public static void main(String args[]) {
        // Warning - don't edit this function.

        // Create a new game.
        createGame(new RunningMario_completed());
    }

    public class Obstacle{
        int x;
        int y;
        int type;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getType() {
            return type;
        }

        public Obstacle(int x, int y, int type){
            this.x = x;
            this.y = y;

            this.type = type;

        }

    }

    public class EnemyTest{
        int x;
        int y;
        int type;
        int v;
        int newX;
        boolean isRight;
        boolean isLive = true;
        public EnemyTest(int x,int y,int type,int v,boolean isRight){
            this.x = x;
            this.y = y;
            this.type = type;
            this.v = v;
            this.isRight = isRight;
            if(!isRight){
                this.v *= -1;
            }
            newX = x;
        }

        public void run(){
//get obstacle type
            if(isLive){
                for(Obstacle o : blockList){
                    if(o.getType() == 3){
                        if(this.isRight){
                            if(newX + 40 == o.getX() ){
                                this.v *= -1;
                                this.isRight = false;
                            }

                        }else {
                            if(newX == o.getX() + 80 ){
                                this.v *= -1;
                                this.isRight = true;
                            }
                        }
                    }
                }
                newX += v;
            }

        }


        public int getY() {
            return y;
        }
        public int getType() {
            return type;
        }

        public int getX() {
            return x;
        }

        public int getNewX() {
            return newX;
        }

        public void setLive(boolean live) {
            isLive = live;
        }

        public boolean isLive() {
            return isLive;
        }
    }

    public void designEnemy(){
        enemyTests.add(new EnemyTest(200,groundPosition,1,2,true));
        enemyTests.add(new EnemyTest(700,groundPosition,1,2,false));
    }

    int dieTime = 40;
    public void drawEnemy(){
        for(int i = 0,len = enemyTests.size();i < len;i++){
            if (enemyTests.get(i).isLive) {
                enemyTests.get(i).run();
                drawImage(Chestnut[getFrame(0.3,2)],enemyTests.get(i).getNewX(),enemyTests.get(i).getY(),40,40);
            }else {
                if(dieTime == 0){
                    enemyTests.remove(enemyTests.get(i));
                    len--;
                    i--;
                    dieTime = 40;
                }else {
                    drawImage(ChestnutDie,enemyTests.get(i).getNewX(),enemyTests.get(i).getY() + 25,40,15);
                    dieTime--;
                }
            }
        }

    }
    //-------------------------------------------------------
    // Your Program
    //-------------------------------------------------------
    Image sheet,sheet2,sheet3_Enemy;
    Image background;
    Image[] frames,blockQuestion,Chestnut;
    Image jumpMario;
    Image ChestnutDie;
    Image block,Tube,block2;
    int groundPosition;
    int currentFrame;
    int upTime;
    double animTime;
    double timeElapsed;
    List<Obstacle> blockList = new ArrayList<>();
    List<EnemyTest> enemyTests = new ArrayList<>();

    Obstacle tempObstacle;
    //-------------------------------------------------------
    // Game
    //-------------------------------------------------------

    //draw and design the level
    public void designObstacle(){
        blockList.add(new Obstacle(100,50,1));
        blockList.add(new Obstacle(350,390,1));
        blockList.add(new Obstacle(390,230,1));
        blockList.add(new Obstacle(430,230,2));
        blockList.add(new Obstacle(470,230,1));
        blockList.add(new Obstacle(510,230,2));
        blockList.add(new Obstacle(550,475,3));
        blockList.add(new Obstacle(650,390,1));
        blockList.add(new Obstacle(690,390,2));
        blockList.add(new Obstacle(730,390,1));
        blockList.add(new Obstacle(770,390,2));
        blockList.add(new Obstacle(810,390,1));

        blockList.add(new Obstacle(890,230,2));
        blockList.add(new Obstacle(970,230,2));
        blockList.add(new Obstacle(1050,230,2));
        blockList.add(new Obstacle(850,390,1));
        blockList.add(new Obstacle(950,475,3));
        blockList.add(new Obstacle(1050,390,1));

        blockList.add(new Obstacle(1140,390,1));
        blockList.add(new Obstacle(1220,390,2));
        blockList.add(new Obstacle(1300,390,2));
    }

    public void drawObstacle(){
        for(Obstacle o : blockList){
            if(o.getType() == 1) {
                drawImage(block,o.getX(),o.getY(),40,40);
            }else if(o.getType() == 2){
                drawImage(blockQuestion[getFrame(1,3)],o.getX(),o.getY(),40,40);
            }else if(o.getType() == 3){
                drawImage(Tube,o.getX(),o.getY(),80,80);
            }else if(o.getType() == 4){
                drawImage(block2,o.getX(),o.getY(),40,40);
            }
        }
    }

    //collision Check
    public void checkCollision(){
        for(Obstacle s:blockList){
            if (s.getType() == 3){
                if(pos.getX() + 40 == s.getX()&& s.getY() < pos.getY() + 40){
                    is_right = false;
                    break;
                }else if(pos.getX() + 40 > s.getX() && pos.getX() < s.getX() + 80 && s.getY() % (pos.getY() + 40) < 10){
                    on_obstacle = true;
                    tempObstacle = s;
                    System.out.println("makabaka");
                    break;
                }else if(pos.getX() == s.getX() + 80 && s.getY() < pos.getY() + 40){
                    is_left = false;
                    break;
                }
            }if(s.getType() == 1 || s.getType() == 2){
                if(pos.getY() < s.getY() + 40 && pos.getY() > s.getY() - 40 && pos.getX() + 40 == s.getX()){
                    is_right = false;
                }else if(pos.getX() + 40 > s.getX() && pos.getX() < s.getX() + 40 && (pos.getY() % (s.getY() + 40) < 5)){
                    is_jump = false;
                    is_Flying = true;
                }else if(pos.getX() + 40 > s.getX() && pos.getX() < s.getX() + 40 && s.getY() %(pos.getY() + 40)<10){
                    on_obstacle = true;
                    tempObstacle = s;
                    break;
                }
            }
        }


        if(on_obstacle && (pos.getX() > tempObstacle.getX() +80)){
            on_obstacle = false;
            is_Flying = true;
        } else if(on_obstacle && (pos.getX()+40) < tempObstacle.getX()){
            on_obstacle = false;
            is_Flying = true;
        }else  if(is_jump){
            on_obstacle = false;
        }


        for(int i = 0, len = enemyTests.size();i < len;i++){
            if(is_Flying && enemyTests.get(i).getY() %(pos.getY() + 40)<15 && pos.getX() + 40 > enemyTests.get(i).getNewX() && pos.getX() < enemyTests.get(i).getNewX() + 40 ){
                if(enemyTests.get(i).isLive){
                    rebound = true;
                    enemyTests.get(i).setLive(false);
                }

            }
        }

    }


    // Initialise the Game
    public void init() {
        setWindowSize(800,645);


        upV = 10;
        frames = new Image[4];
        blockQuestion = new Image[3];
        Chestnut = new Image[2];
        sheet = loadImage("miniMario.png");
        sheet2 = loadImage("Obstacle.png");
        sheet3_Enemy = loadImage("AllCharacter.png");
        background = loadImage("MarioBackground.png");
        Tube = subImage(sheet2,0,145,35,35);
        for (int i = 0; i < 4; i++) {
            frames[i] = subImage(sheet,16*i,0,16,16);
        }
        block = subImage(sheet2,34,0,17,17);
        block2 = subImage(sheet2,0,18,17,17);
        for(int i = 0; i < 3; i++){
            blockQuestion[i] = subImage(sheet2,432 + 18 * i,0,17,17);
        }

        jumpMario = subImage(sheet,80,0,16,16);
        for(int i = 0; i < 2;i++){
            Chestnut[i] = subImage(sheet3_Enemy,227 + i * 16,11,16,17);
        }
        ChestnutDie = subImage(sheet3_Enemy,259,19,15,8);

        groundPosition = 550 - 38;
        pos.setLocation(100,groundPosition);

        designObstacle();
        designEnemy();
    }




    // Updates the display
    @Override
    public void update(double dt) {
        // Update Function
        animTime += dt;

        checkCollision();
        //currentFrame = (currentFrame + 1) % 3;
        if (is_moving) {
            if (is_left) {
                pos.setLocation(pos.getX() - 10, pos.getY());
            }
            if (is_right) {

                pos.setLocation(pos.getX() + 10, pos.getY());
            }


            if (pos.getX() < 0) {
                pos.setLocation(0, pos.getY());
            }

            currentFrame = getFrame(0.3, 4);
        } else {
            currentFrame = 0;
        }

        if (is_jump && !rebound) {
            pos.setLocation(pos.getX(), pos.getY() - upV);
            if (upTime == 16) {
                is_jump = false;
                upTime = 0;
                is_Flying = true;
            } else {
                upTime += 1;
            }
        }else if(!is_jump && rebound){
            pos.setLocation(pos.getX(), pos.getY() - upV);
            if (upTime == 3) {
                is_jump = false;
                upTime = 0;
                is_Flying = true;
                rebound = false;
            } else {
                upTime += 1;
            }
        }

        if (on_obstacle) {
            is_Flying = false;
            pos.setLocation(pos.getX(),tempObstacle.getY()-40);
        }

        if (is_Flying && !is_jump && !rebound) {
            upTime = 0;
            if (groundPosition - pos.getY() < 10) {
                is_Flying = false;
                rebound = false;
                pos.setLocation(pos.getX(),groundPosition);
            } else {
                pos.setLocation(pos.getX(), pos.getY() + upV);
            }

        }

    }



    // Calculates the
    public int getFrame(double d, int num_frames) {
        return (int)Math.floor(((animTime % d) / d) * num_frames);
    }


    // This gets called any time the Operating System
    // tells the program to paint itself
    public void paintComponent() {
        // Clear the background to black
        changeBackgroundColor(Color.white);
        clearBackground(width(),height());



        if (is_left)
        {
            if (!is_center)
            {
                drawImage(background, 0, 0, 10500, 645);
                drawObstacle();
                drawEnemy();
                if(is_Flying || is_jump){
                    drawImage(jumpMario,pos.getX() + 20 * 2 , pos.getY(), -20 * 2, 20 * 2);
                }else {
                    drawImage(frames[currentFrame], pos.getX() + 20 * 2 , pos.getY(), -20 * 2, 20 * 2);
                }



            }
            else
            {
                saveCurrentTransform();
                translate(-pos.getX()+250,0);

                drawImage(background, 0, 0, 10500, 645);
                drawObstacle();
                drawEnemy();
                timeElapsed++;


                restoreLastTransform();
                if(is_Flying || is_jump){
                    drawImage(jumpMario, 250 + 20*2, pos.getY(), -20 * 2, 20 * 2);
                }else {
                    drawImage(frames[currentFrame], 250 + 20*2, pos.getY(), -20 * 2, 20 * 2);
                }

                if(pos.getX() <= 250){
                    is_center = false;
                }

            }
        }
        else
        {
            if (!is_center) {
                drawImage(background, 0, 0, 10500, 645);
                drawImage(block,obstaclePos.getX(),obstaclePos.getY());
                drawObstacle();
                drawEnemy();
                if(is_Flying || is_jump){
                    drawImage(jumpMario, pos.getX(), pos.getY(), 20 * 2, 20 * 2);
                }else {
                    drawImage(frames[currentFrame], pos.getX(), pos.getY(), 20 * 2, 20 * 2);
                }

                if(pos.getX() >= 250)
                {
                    is_center = true;
                }

            }
            else
            {
                saveCurrentTransform();
                translate(-pos.getX()+250,0);
                drawImage(background, 0, 0, 10500, 645);

                drawImage(blockQuestion[getFrame(1,3)],200,50,32,32);
                drawObstacle();
                drawEnemy();

                restoreLastTransform();
                if(is_Flying || is_jump){
                    drawImage(jumpMario,250, pos.getY(), 20 * 2, 20 * 2);
                }else {
                    drawImage(frames[currentFrame], 250, pos.getY(), 20 * 2, 20 * 2);
                }
            }
        }
        V -= 5;

    }
    int V = 500;
    Point2D pos = new Point2D.Double();
    Point2D obstaclePos= new Point2D.Double();
    boolean is_moving = false;
    boolean is_left = false;
    boolean is_right = false;
    boolean is_center = false;
    boolean is_jump = false;
    boolean is_Flying = false;
    boolean on_obstacle = false;
    boolean rebound = false;
    int upV;

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            is_moving = true;
            is_left = false;
            is_right = true;

        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            is_moving = true;
            is_left = true;
            is_right = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP){
            if(!is_Flying){
                is_jump = true;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            is_moving = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            is_moving = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP){
            is_jump = false;
            is_Flying = true;
        }

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
