import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
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

        public Obstacle(int x,int y,int type){
            this.x = x;
            this.y = y;

            this.type = type;

            if(type == 1) {
                drawImage(block,x,y,32,32);
            }else if(type == 2){
                drawImage(blockQuestion[currentFrame],x,y,32,32);
            }
        }


    }

    //-------------------------------------------------------
    // Your Program
    //-------------------------------------------------------
    Image sheet,sheet2;
    Image background;
    Image[] frames,blockQuestion;
    Image jumpMario;
    Image block;
    int groundPosition;
    int currentFrame;
    int upTime;
    double animTime;
    List<Obstacle> blockList = new ArrayList<>();
    //-------------------------------------------------------
    // Game
    //-------------------------------------------------------

    //draw and design the level
    public void designObstacle(){
        blockList.add(new Obstacle(100,50,1));
    }
//    public void drawMario(){
//        if(is_Flying || is_jump){
//            drawImage(jumpMario,pos.getX());
//        }
//    }

    // Initialise the Game
    public void init() {
        downV = 5;
        frames = new Image[4];
        blockQuestion = new Image[3];
        sheet = loadImage("miniMario.png");
        sheet2 = loadImage("Obstacle.png");
        background = loadImage("MarioBackground.png");
        for (int i = 0; i < 4; i++) {
            frames[i] = subImage(sheet,16*i,0,16,16);
        }
        block = subImage(sheet2,34,0,17,17);
        for(int i = 0; i < 3; i++){
            blockQuestion[i] = subImage(sheet2,432 + 18 * i,0,17,17);
        }

        jumpMario = subImage(sheet,80,0,16,16);
        groundPosition = 551 - 32;
        pos.setLocation(100,groundPosition);

        obstaclePos.setLocation(100,100);

    }


    // Updates the display
    @Override
    public void update(double dt) {
        // Update Function
        animTime += dt;


        //currentFrame = (currentFrame + 1) % 3;
        if(is_moving) {
            if(is_left)
            {
                pos.setLocation(pos.getX()-10,pos.getY());
            }
            else
            {
                pos.setLocation(pos.getX()+10,pos.getY());
            }



            if(pos.getX()<0)
            {
                pos.setLocation(0,pos.getY());
            }

            currentFrame = getFrame(0.3, 4);
            System.out.println(getFrame(0.3,4));
        }
        else
        {
            currentFrame = 0;
        }

        if(is_jump) {
            if(upTime == 10){
                is_jump = false;
                upTime = 0;
                is_Flying = true;
            }else {
                pos.setLocation(pos.getX(),pos.getY()-upV);
                upTime+=1;
            }
        }

        if(is_Flying){


            pos.setLocation(pos.getX(),pos.getY()+upV);

            if (pos.getY() == groundPosition){
                is_Flying = false;
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
                    drawImage(jumpMario,pos.getX() + 16 * 2 , pos.getY(), -16 * 2, 16 * 2);
                }else {
                    drawImage(frames[currentFrame], pos.getX() + 16 * 2 , pos.getY(), -16 * 2, 16 * 2);
                }



            }
            else
            {
                saveCurrentTransform();
                translate(-pos.getX()+250,0);
//                System.out.println(-pos.getX()+250);
                drawImage(background, 0, 0, 10500, 645);
                designObstacle();

                restoreLastTransform();
                if(is_Flying || is_jump){
                    drawImage(jumpMario, 250 + 16*2, pos.getY(), -16 * 2, 16 * 2);
                }else {
                    drawImage(frames[currentFrame], 250 + 16*2, pos.getY(), -16 * 2, 16 * 2);
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
                    drawImage(jumpMario, pos.getX(), pos.getY(), 16 * 2, 16 * 2);
                }else {
                    drawImage(frames[currentFrame], pos.getX(), pos.getY(), 16 * 2, 16 * 2);
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
                restoreLastTransform();
                if(is_Flying || is_jump){
                    drawImage(jumpMario,250, pos.getY(), 16 * 2, 16 * 2);
                }else {
                    drawImage(frames[currentFrame], 250, pos.getY(), 16 * 2, 16 * 2);
                }


            }
        }
    }

    Point2D pos = new Point2D.Double();
    Point2D obstaclePos= new Point2D.Double();
    boolean is_moving = false;
    boolean is_left = false;
    boolean is_center = false;
    boolean is_jump = false;
    boolean is_Flying = false;
    int upV,downV;

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            is_moving = true;
            is_left = false;

        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            is_moving = true;
            is_left = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP){
            is_jump = true;
            upV = 8;
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

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
