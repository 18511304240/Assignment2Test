import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
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

            if(type == 1) {
                drawImage(block,x,y,40,40);
            }else if(type == 2){
                drawImage(blockQuestion[getFrame(1,3)],x,y,40,40);
            }else if(type == 3){
                drawImage(Tube,x,y,80,80);
            }


        }


    }

    //-------------------------------------------------------
    // Your Program
    //-------------------------------------------------------
    Image sheet,sheet2;
    Image background;
    Image mogu1,mogu2,mogu3,bird1,bird2,flower1,flower2;
    Image[] frames,blockQuestion;
    Image jumpMario;
    Image block,Tube;
    int groundPosition;
    int currentFrame;
    int upTime;
    double animTime;
    double timeElapsed;
    List<Obstacle> blockList = new ArrayList<>();
    //    用于存放我们所有的敌人
    private List<Enemy>enemyList=new ArrayList<>();
    Obstacle tempObstacle;
    private int e = 1;
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

    //collision Check
    public void checkCollision(){
        for(Obstacle s:blockList){
            if (s.getType() == 3){
                if(pos.getX() + 40 == s.getX()&& s.getY() < pos.getY() + 40){
                    is_right = false;
                    break;
                }else if(pos.getX() + 40 > s.getX() && pos.getX() < s.getX() + 80 && s.getY() - (pos.getY() + 40) == 3){
                    on_obstacle = true;
                    tempObstacle = s;
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
                }
            }
        }

        if(on_obstacle && (pos.getX() > tempObstacle.getX() +80)){
            on_obstacle = false;
            is_Flying = true;
        } else if(on_obstacle && (pos.getX()+40) < tempObstacle.getX()){
            System.out.println(pos.getX());
            System.out.println(tempObstacle.getX());
            on_obstacle = false;
            is_Flying = true;
        }else  if(is_jump){
            on_obstacle = false;
        }
    }

    public void paintenemy(){
        for (Enemy enemy : enemyList) {
            drawImage(enemy.getShow(), enemy.getX(), enemy.getY());
        }
    }
    // Initialise the Game
    public void init() {
        upV = 10;
        frames = new Image[4];
        blockQuestion = new Image[3];
        sheet = loadImage("miniMario.png");
        sheet2 = loadImage("Obstacle.png");
        background = loadImage("MarioBackground.png");
        Tube = subImage(sheet2,0,145,35,35);
        for (int i = 0; i < 4; i++) {
            frames[i] = subImage(sheet,16*i,0,16,16);
        }
        block = subImage(sheet2,34,0,17,17);
        for(int i = 0; i < 3; i++){
            blockQuestion[i] = subImage(sheet2,432 + 18 * i,0,17,17);
        }

        jumpMario = subImage(sheet,80,0,16,16);
        groundPosition = 550 - 38;
        pos.setLocation(100,groundPosition);

        obstaclePos.setLocation(100,100);
        mogu1 = loadImage("images/fungus1.png");
        mogu2 = loadImage("images/fungus2.png");
        mogu3 = loadImage("images/fungus3.png");
        bird1 = loadImage("images/bird1.png");
        bird2 = loadImage("images/bird2.png");
        flower1 = loadImage("images/flower1.1.png");
        flower2 = loadImage("images/flower1.2.png");
        //加载蘑菇敌人
        for (int i = 1; i <= 3; i++) {
            try {
                Image image;
                if (i == 3) {
                    image = mogu3;
                } else if (i == 1) {
                    image = mogu1;
                } else {
                    image = mogu2;
                }
                Enemy.mogu.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //加载鸟敌人
        for (int i = 1; i <= 2; i++){
            try {
                Image image;
                if (i == 1) {
                    image = bird1;
                } else {
                    image = bird2;
                }
                Enemy.bird.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //        加载食人花敌人
        for (int i = 1; i <= 2; i++) {
            try {
                Image image;
                if (i == 1) {
                    image = flower1;
                } else {
                    image = flower2;
                }
                Enemy.flower.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        enemyList.add(new Enemy(500,450,true,2,360,450));
        enemyList.add(new Enemy(580,520,true,1));
        enemyList.add(new Enemy(580,300,true,3));
//        enemyList.add(new Enemy(670,520,true,1));

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

        if (is_jump) {
            pos.setLocation(pos.getX(), pos.getY() - upV);
            if (upTime == 16) {
                is_jump = false;
                upTime = 0;
                is_Flying = true;
            } else {
                upTime += 1;
            }
        }

        if (on_obstacle) {
            is_Flying = false;
        }

        if (is_Flying && !is_jump) {
            upTime = 0;

            if (groundPosition % pos.getY() < 5) {
                is_Flying = false;
            } else {
                pos.setLocation(pos.getX(), pos.getY() + upV);
            }

        }

            for (Enemy enemy : enemyList) {
                int currentX = enemy.getX();
                if (currentX < 650) {
                    enemy.setX(650);
                } else if (currentX > 1000) {
                    enemy.setX(1000);
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
        clearBackground(500, 500);



        if (is_left)
        {
            if (!is_center)
            {
                drawImage(background, 0, 0, 10500, 645);
                designObstacle();
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
//                System.out.println(-pos.getX()+250);
                drawImage(background, 0, 0, 10500, 645);
                designObstacle();
                    paintenemy();
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

//                System.out.println(pos.getX());
            }
        }
        else
        {
            if (!is_center) {
                drawImage(background, 0, 0, 10500, 645);
                drawImage(block,obstaclePos.getX(),obstaclePos.getY());
                designObstacle();
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
                drawImage(block,obstaclePos.getX(),obstaclePos.getY());
                drawImage(blockQuestion[getFrame(1,3)],200,50,32,32);
                designObstacle();
                    paintenemy();
                restoreLastTransform();
                if(is_Flying || is_jump){
                    drawImage(jumpMario,250, pos.getY(), 20 * 2, 20 * 2);
                }else {
                    drawImage(frames[currentFrame], 250, pos.getY(), 20 * 2, 20 * 2);
                }


            }
        }
    }

    Point2D pos = new Point2D.Double();
    Point2D obstaclePos= new Point2D.Double();
    boolean is_moving = false;
    boolean is_left = false;
    boolean is_right = false;
    boolean is_center = false;
    boolean is_jump = false;
    boolean is_Flying = false;
    boolean on_obstacle = false;
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
                is_Flying = true;
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
