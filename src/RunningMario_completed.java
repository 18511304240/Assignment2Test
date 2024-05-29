import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;


public class RunningMario_completed extends GameEngine{
    // Main Function
    public static void main(String args[]) {
        // Warning - don't edit this function.

        // Create a new game.
        createGame(new RunningMario_completed(),60);
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
        public int setType(int i){
            type = i;
            return type;
        }
        public int setY(int i){
            this.y = i;
            return y;
        }

        public Obstacle(int x, int y, int type){
            this.x = x;
            this.y = y;

            this.type = type;

        }

    }

    public class EnemyTest{
        private boolean isAnimating = false;
        int x;
        int y;
        int type;
        int v;
        int newX;
        boolean isRight;
        boolean isLive = true;
        boolean Turtleisdie = false;
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
                if (type==3){
                    if (n==0){
                    if (newX - x >= 16){
                        animatemushroom();
                        n++;
                    }
                }}
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


        public void setLocation(int x,int y){
            this.x = x;
            this.y = y;
        }
        public int getY() {
            return y;
        }
        public int getType() {
            return type;
        }

        public boolean Turtleisdie() {  return Turtleisdie;}
        public int getX() {
            return x;
        }

        public int getNewX() {
            return newX;
        }

        public void setLive(boolean live) {
            isLive = live;
        }

        public boolean isAnimating() {
            return isAnimating;
        }

        public void setAnimating(boolean isAnimating) {
            this.isAnimating = isAnimating;
        }
        public boolean isLive() {
            return isLive;
        }
        public int setY(int y) {
            this.y = y;
            return y;
        }
    }

    public void designEnemy(){
        enemyTests.add(new EnemyTest(200,groundPosition,1,2,true));
        enemyTests.add(new EnemyTest(700,groundPosition,1,2,false));
        enemyTests.add(new EnemyTest(1300,groundPosition,2,2,false));
        enemyTests.add(new EnemyTest(1500,groundPosition,2,2,true));
    }

    int dieTime = 40;
    int dieTime2 = 40;
    public void drawEnemy(){
        for(int i = 0,len = enemyTests.size();i < len;i++){
            if (enemyTests.get(i).isLive) {
                enemyTests.get(i).run();
                if (enemyTests.get(i).getType()==1){
                    drawImage(Chestnut[getFrame(0.3,2)],enemyTests.get(i).getNewX(),enemyTests.get(i).getY(),40,40);
                }else if (enemyTests.get(i).getType()==2){
                    if (!enemyTests.get(i).Turtleisdie){
                        drawImage(Turtle[getFrame(0.3,2)],enemyTests.get(i).getNewX(),enemyTests.get(i).getY(),40,40);
                    }else{
                        drawImage(TurtleDie,enemyTests.get(i).getNewX(),enemyTests.get(i).getY(),40,40);
                    }
                }else if (enemyTests.get(i).getType()==3){
                    drawImage(mushroom,enemyTests.get(i).getNewX(),enemyTests.get(i).getY(),40,45);
                }


            }
            else {

                if(dieTime == 0 || dieTime2 == 0){
                    enemyTests.remove(enemyTests.get(i));
                    len--;
                    i--;
                    if (dieTime==0){
                        dieTime = 40;
                    }else if (dieTime2==0){
//                        animateTurtle(i);
                        dieTime2 = 40;
                    }

                }else {
                    if (enemyTests.get(i).getType()==1){
                        drawImage(ChestnutDie,enemyTests.get(i).getNewX(),enemyTests.get(i).getY() + 25,40,15);
                        dieTime--;
                    }
                    if (enemyTests.get(i).getType()==2){
//                        animateTurtle(i);
                        dieTime2--;
                        //乌龟退场
                    }
                }


            }
        }

    }
    //-------------------------------------------------------
    // Your Program
    //-------------------------------------------------------
    Image sheet,sheet2,sheet3_Enemy,zhuan1,M1,G1,mushroom;
    Image background;
    Image menubackground1;
    Image[] frames,blockQuestion,coinsAppear,Chestnut,Turtle;
    Image jumpMario,deadMario,jumpMario1,deadMario1;
    Image ChestnutDie,TurtleDie;
    Image block,Tube,block2,zhuan2;
    int groundPosition;
    int currentFrame;
    int upTime;
    double animTime;
    double timeElapsed;
    boolean gameover = false;
    int score = 0;
    List<Obstacle> blockList = new ArrayList<>();
    List<EnemyTest> enemyTests = new ArrayList<>();
    AudioClip Title = loadAudio("bgm.wav");
    int n =0;


    Obstacle tempObstacle;
    //-------------------------------------------------------
    // Game
    //-------------------------------------------------------

    //draw and design the level
    public void designObstacle(){
        blockList.add(new Obstacle(100,50,1));
        blockList.add(new Obstacle(150,400,5));
        blockList.add(new Obstacle(350,390,1));
        blockList.add(new Obstacle(390,390,1));
        blockList.add(new Obstacle(430,230,2));
        blockList.add(new Obstacle(470,230,1));
        blockList.add(new Obstacle(510,230,2));
        blockList.add(new Obstacle(550,475,3));
        blockList.add(new Obstacle(650,230,1));

//        blockList.add(new Obstacle(690,350,6));

        blockList.add(new Obstacle(690,390,2));
        blockList.add(new Obstacle(730,390,1));
        blockList.add(new Obstacle(770,390,2));
        blockList.add(new Obstacle(810,390,1));

        blockList.add(new Obstacle(890,230,2));
        blockList.add(new Obstacle(970,230,2));
        blockList.add(new Obstacle(1050,230,2));
        blockList.add(new Obstacle(850,390,1));
        blockList.add(new Obstacle(1500,475,3));
//        blockList.add(new Obstacle(1150,500,1));
        blockList.add(new Obstacle(1050,390,1));

        blockList.add(new Obstacle(1140,390,1));
        blockList.add(new Obstacle(1220,390,2));


        blockList.add(new Obstacle(1300,390,7));


//三管道第一关
        blockList.add(new Obstacle(1500,475,3));
        blockList.add(new Obstacle(1800,475,3));
        blockList.add(new Obstacle(2100,475,3));

        blockList.add(new Obstacle(2200,350,1));
        blockList.add(new Obstacle(2400,450,1));
        blockList.add(new Obstacle(2500,350,1));
        blockList.add(new Obstacle(2540,350,1));
        blockList.add(new Obstacle(2580,350,2));
//        blockList.add(new Obstacle(2700,230,1));
        for (int i=0;i<5;i++){
            blockList.add(new Obstacle(2700+i*40,230,1));
        }
        blockList.add(new Obstacle(2900,230,2));
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(3000+i*40,390,1));
        }
        blockList.add(new Obstacle(3300,390,2));
        blockList.add(new Obstacle(3400,5155,3));
        //平行四边形
        for (int i=0;i<5;i++){
            int yy=515-i*40;
            int xx=3800+i*40;
            for (int j=5;j>=0;j--){
                blockList.add(new Obstacle(xx+j*40,yy,4));

            }

        }
        blockList.add(new Obstacle(4200,355,2));

        //三角形
        for (int i=0;i<5;i++){
            int yy=355+i*40;
            int xx=4500-i*40;
            for (int j=0;j<=i;j++){
                blockList.add(new Obstacle(xx+j*40,yy,4));

            }
        }
        blockList.add(new Obstacle(4620,355,4));
        blockList.add(new Obstacle(4620,395,4));
        blockList.add(new Obstacle(4620,435,4));
        blockList.add(new Obstacle(4620,475,4));

        //T1
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(4700+i*40,355,4));
        }
        for (int i=0;i<4;i++){
            blockList.add(new Obstacle(4740,395+i*40,4));
        }

        blockList.add(new Obstacle(4900,160,2));

        //T2
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(5000+i*40,355,4));
        }
        for (int i=0;i<4;i++){
            blockList.add(new Obstacle(5040,395+i*40,4));
        }

        blockList.add(new Obstacle(5220,160,2));

        //T3
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(5340+i*40,355,4));
        }
        for (int i=0;i<4;i++){
            blockList.add(new Obstacle(5380,395+i*40,4));
        }
//金字塔1
        for (int i=0;i<9;i++){
            blockList.add(new Obstacle(5800+i*40,515,4));
        }
        for (int i=0;i<7;i++){
            blockList.add(new Obstacle(5840+i*40,475,4));
        }
        for (int i=0;i<5;i++){
            blockList.add(new Obstacle(5880+i*40,435,4));
        }
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(5920+i*40,395,4));
        }
        blockList.add(new Obstacle(5960,355,4));

        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(6280+i*40,315,2));
        }

//金字塔2
        for (int i=0;i<9;i++){
            blockList.add(new Obstacle(6500+i*40,515,4));
        }
        for (int i=0;i<7;i++){
            blockList.add(new Obstacle(6540+i*40,475,4));
        }
        for (int i=0;i<5;i++){
            blockList.add(new Obstacle(6580+i*40,435,4));
        }
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(6620+i*40,395,4));
        }
        blockList.add(new Obstacle(6660,355,4));

        //天梯
        blockList.add(new Obstacle(6960,515,1));
        for (int i=0;i<2;i++){
            blockList.add(new Obstacle(7000,515-i*40,1));
        }
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(7120,515-i*40,1));
        }
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(7160,515-i*40,1));
        }
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(7200,515-i*40,1));
        }
        for (int i=0;i<4;i++){
            blockList.add(new Obstacle(7240,515-i*40,1));
        }
        for (int i=0;i<5;i++){
            blockList.add(new Obstacle(7360,515-i*40,1));
        }
        for (int i=0;i<5;i++){
            blockList.add(new Obstacle(7400,515-i*40,1));
        }
        for (int i=0;i<5;i++){
            blockList.add(new Obstacle(7440,515-i*40,1));
        }
        for (int i=0;i<6;i++){
            blockList.add(new Obstacle(7480,515-i*40,1));
        }
        for (int i=0;i<7;i++){
            blockList.add(new Obstacle(7600,515-i*40,1));
        }
        for (int i=0;i<7;i++){
            blockList.add(new Obstacle(7640,515-i*40,1));
        }
        for (int i=0;i<7;i++){
            blockList.add(new Obstacle(7680,515-i*40,1));
        }
        for (int i=0;i<8;i++){
            blockList.add(new Obstacle(7720,515-i*40,1));
        }
        for (int i=0;i<8;i++){
            blockList.add(new Obstacle(7760,515-i*40,1));
        }




        //天空之城
        for (int i=0;i<20;i++){
            for (int j=0;j<=1;j++){
                blockList.add(new Obstacle(8000+i*40,355-j*40,1));
            }
        }
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(8000,275-i*40,1));
        }

        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(8760,275-i*40,1));
        }
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(8120+i*40,195,2));
        }
        blockList.add(new Obstacle(8380,115,2));
        for (int i=0;i<3;i++){
            blockList.add(new Obstacle(8560+i*40,195,2));
        }
//        blockList.add(new Obstacle(8280,235,3));
//        blockList.add(new Obstacle(8440,235,3));
        blockList.add(new Obstacle(8320,235,4));
        blockList.add(new Obstacle(8320,275,4));

        blockList.add(new Obstacle(8440,235,4));
        blockList.add(new Obstacle(8440,275,4));


        for (int i=0;i<6;i++){
            blockList.add(new Obstacle(7920+i*200,475,3));
        }
        blockList.add(new Obstacle(9030,275,2));

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
            }else if (o.getType() == 5){
                drawImage(zhuan2,o.getX(),o.getY(),40,40);
            } else if (o.getType() ==6) {
                drawImage(coinsAppear[getFrame(1,3)],o.getX(),o.getY(),40,40);

            }else if (o.getType() ==7) {
                drawImage(blockQuestion[getFrame(1,3)],o.getX(),o.getY(),40,40);

            }
        }
    }

    //collision Check
    public void checkCollision() {
        Timer coinTimer = new Timer();

        if (!is_dead){
        for (int i = 0; i < blockList.size(); i++) {
                Obstacle s = blockList.get(i);
                if (s.getType() == 3) {
                    if (pos.getX() + 40 == s.getX() && s.getY() < pos.getY() + 40) {
                        is_right = false;
                        break;
                    } else if (pos.getX() + 40 > s.getX() && pos.getX() < s.getX() + 80 && s.getY() % (pos.getY() + 40) < 10) {
                        on_obstacle = true;
                        tempObstacle = s;
                        break;
                    } else if (pos.getX() == s.getX() + 80 && s.getY() < pos.getY() + 40) {
                        is_left = false;
                        break;
                    }
                }

                if (s.getType() == 1 || s.getType() == 2 || s.getType() == 4 || s.getType() == 5 || s.getType() == 7) {
                    if (pos.getY() < s.getY() + 40 && pos.getY() > s.getY() - 40 && pos.getX() + 40 == s.getX()) {
                        is_right = false;
                    } else if (pos.getX() + 40 > s.getX() && pos.getX() < s.getX() + 40 && (pos.getY() % (s.getY() + 40) < 5)) {
                        if (s.getType() == 1) {
                            is_jump = false;
                            is_Flying = true;
                            animateBox(s);
                        } else if (s.getType() == 2) {
                            score += 100;
                            is_jump = false;
                            is_Flying = true;
                            animateBox(s);
                            blockList.add(new Obstacle(s.getX(),s.getY()-50,6));
                            displayCoins();
                        }else if (s.getType() == 7) {
                            is_jump = false;
                            is_Flying = true;
                            animateBox(s);
                            enemyTests.add(new EnemyTest(s.getX(),s.getY()-45,3,2,true));
                        } else if (s.getType() == 5) {
                            is_jump = false;
                            is_Flying = true;
                            blockList.remove(i);
                        }
                    } else if (pos.getY() < s.getY() + 40 && pos.getY() > s.getY() - 40 && pos.getX() == s.getX() + 40) {
                        is_left = false;
                    } else if (pos.getX() + 40 > s.getX() && pos.getX() < s.getX() + 40 && s.getY() % (pos.getY() + 40) < 10) {
                        on_obstacle = true;
                        tempObstacle = s;
                        System.out.println(s.getY() - (pos.getY() + 40));


                    }
                }
            }

            if (on_obstacle) {
                if (tempObstacle.getType() == 3) {
                    if ((pos.getX() > tempObstacle.getX() + 80)) {
                        on_obstacle = false;
                        is_Flying = true;
                    } else if ((pos.getX() + 40) < tempObstacle.getX()) {
                        System.out.println(pos.getX());
                        System.out.println(tempObstacle.getX());
                        on_obstacle = false;
                        is_Flying = true;
                    } else if (is_jump) {
                        on_obstacle = false;
                    }
                } else if (tempObstacle.getType() == 1 || tempObstacle.getType() == 2 || tempObstacle.getType() == 4 || tempObstacle.getType() == 5) {
                    if ((pos.getX() > tempObstacle.getX() + 40)) {
                        on_obstacle = false;
                        is_Flying = true;
                    } else if ((pos.getX() + 40) < tempObstacle.getX()) {
                        System.out.println(pos.getX());
                        System.out.println(tempObstacle.getX());
                        on_obstacle = false;
                        is_Flying = true;
                    } else if (is_jump) {
                        on_obstacle = false;
                    }
                }

            }

            for (int i = 0, len = enemyTests.size(); i < len; i++) {
                EnemyTest k = enemyTests.get(i);
                if (is_Flying && enemyTests.get(i).getY() % (pos.getY() + 40) < 15 && pos.getX() + 40 > enemyTests.get(i).getNewX() && pos.getX() < enemyTests.get(i).getNewX() + 40) {
                    if (enemyTests.get(i).isLive) {
                        if (enemyTests.get(i).getType() == 2) {
                            if (!enemyTests.get(i).Turtleisdie) {
                                rebound = true;
                                enemyTests.get(i).Turtleisdie = true;
                                if (pos.getX() + 20 < enemyTests.get(i).getNewX()) {
                                    enemyTests.get(i).isRight = true;
                                    enemyTests.get(i).v = Math.abs(enemyTests.get(i).v);
                                } else {
                                    enemyTests.get(i).isRight = false;
                                    enemyTests.get(i).v = -Math.abs(enemyTests.get(i).v);
                                }
                            } else if (!enemyTests.get(i).isAnimating()) {
                                animateTurtle(k);

                                rebound = true;
                            }
                        } else {
                            rebound = true;
                            enemyTests.get(i).setLive(false);
                        }
                    } else if (enemyTests.get(i).getType() == 2 && !enemyTests.get(i).isAnimating()) {
                        animateTurtle(k);
                    }

                }
                if (!is_Flying && pos.getY() == enemyTests.get(i).getY() && pos.getX() + 40 > enemyTests.get(i).getNewX() && pos.getX() < enemyTests.get(i).getNewX() + 40) {
                    if (enemyTests.get(i).isLive) {
                        if (enemyTests.get(i).getType()==3){
                            Tobebig = true;
                        }else {
                            is_dead = true;
                        }

                    }
                }

            }


        }
    }



    private void animateBox(Obstacle i) {
        final int moveDistance = 10;
        final int returnSpeed = 2;
        int initialY = i.getY();

        Thread animationThread = new Thread(() -> {
            int currentY = i.getY();

            int targetY = currentY - moveDistance;

            while (i.getY() > targetY) {

               i.setY(i.getY() - 1);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(i.getType() == 2){
                i.setType(1);
            }
            while (i.getY() < initialY) {
                i.setY(i.getY() + returnSpeed);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i.setY(initialY);
        });
        animationThread.start();
    }
    private void animatemushroom() {
        for (int i = 0, len = enemyTests.size(); i < len; i++) {
            if (enemyTests.get(i).getType() == 3) {
        final int moveSpeed = 5;
        final int returnSpeed = 2;
        int initialY = enemyTests.get(i).getY();
                EnemyTest k = enemyTests.get(i);

        Thread animationThread = new Thread(() -> {
            int currentY = k.getY();
            int targetY = groundPosition;

            // Drop the enemy
            while (k.getY() < targetY) {
                k.setY(k.getY() + moveSpeed);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            k.setY(targetY);

        });

        animationThread.start();
    }}}

    public void displayCoins() {
        Timer coinTimer = new Timer();

        for (int i = 0; i < blockList.size(); i++) {
            Obstacle c = blockList.get(i);
            if (c.getType() == 6) {
                animateCoin(c);
                coinTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        blockList.remove(c);
                    }
                }, 1200);
            }
        }
    }

    private void animateCoin(Obstacle coin) {
        final int moveDistance = 30;
        final int speed = 2;
        final int delay = 30;

        Thread animationThread = new Thread(() -> {
            int initialY = coin.getY();
            int targetY = initialY - moveDistance;


            while (coin.getY() > targetY) {
                coin.setY(coin.getY() - speed);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            while (coin.getY() < initialY) {
                coin.setY(coin.getY() + speed);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            coin.setY(initialY);
        });
        animationThread.start();
    }
    public void timereset(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 在此处执行超过5秒后的操作
                gameover = true;
            }
        };

        // 设置定时任务，在5秒后执行
        timer.schedule(task, 3000);
    }




    private void animatemario() {
        final int ascendDistance = 5; // 上升的距离
        final int ascendSpeed = 5;
        final int descendSpeed = 3;



        Thread hitAnimationThread = new Thread(() -> {
            // 马里奥上升
            int distanceAscended = 0; // 已上升的距离
            while (distanceAscended < ascendDistance) {
                pos.setLocation(pos.getX(), pos.getY() - ascendSpeed);
                distanceAscended += ascendSpeed; // 更新已上升的距离

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 等待一段时间
            try {
                Thread.sleep(500); // 停顿一下
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 马里奥下降
            while (pos.getY() < 700) { // 这里的条件可以根据你的实际需求进行调整
                pos.setLocation(pos.getX(), pos.getY() + descendSpeed);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        hitAnimationThread.start();

    }

    private void animateTurtle(EnemyTest turtle) {
        final int riseDistance = 20; // 上升的距离
        final int fallSpeed = 10; // 下落的速度
        final int fallDistance = 600; // 下落的总距离，假设屏幕高度为600
        int initialY = turtle.getY();

        turtle.setAnimating(true);

        Thread deathAnimationThread = new Thread(() -> {
            int currentY = turtle.getY();
            int targetRiseY = currentY - riseDistance;

            // 上升
            while (turtle.getY() > targetRiseY) {
                turtle.setY(turtle.getY() - 1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 下落到屏幕外
            while (turtle.getY() < initialY + fallDistance) {
                turtle.setY(turtle.getY() + fallSpeed);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 动画完成
            turtle.setAnimating(false);
            turtle.setLive(false);
        });


        deathAnimationThread.start();
    }

    enum GameState {Menu, Codex, Play,Help};
    GameState state = GameState.Menu;
    int menuOption = 0;


    public void resetgame(){

        score = 0;
        init();
//        state = GameState.Menu;
//        groundPosition = 550 - 38;
//        pos.setLocation(100,groundPosition);
//        designObstacle();
//        designEnemy();

    }

    // Initialise the Game
    public void init() {


        setWindowSize(800,645);
//        if (state == GameState.Play) {
            startAudioLoop(Title, 10);

//        }
        gameover = false;
        is_dead = false;



        upV = 10;
        frames = new Image[4];
        blockQuestion = new Image[3];
        coinsAppear = new Image[3];
        Chestnut = new Image[2];
        Turtle = new Image[2];
        sheet = loadImage("miniMario.png");
        sheet2 = loadImage("Obstacle.png");
        zhuan1 = loadImage("zhuan1.png");
        sheet3_Enemy = loadImage("AllCharacter.png");
        background = loadImage("MarioBackground.png");
        menubackground1 = loadImage("menubackground1.png");
        Tube = subImage(sheet2,0,145,35,35);
        for (int i = 0; i < 4; i++) {
            frames[i] = subImage(sheet,16*i,0,16,16);
        }
        block = subImage(sheet2,34,0,17,17);
        block2 = subImage(sheet2,0,18,17,17);
        zhuan2 = subImage(zhuan1,0,0,30,30);
        for(int i = 0; i < 3; i++){
            blockQuestion[i] = subImage(sheet2,432 + 18 * i,0,17,17);
        }
        for(int i = 0; i < 3; i++){
            coinsAppear[i] = subImage(sheet2,432 + 18 * i,18,17,17);
        }
        deadMario = subImage(sheet,96,0,16,16);
        jumpMario = subImage(sheet,80,0,16,16);
        jumpMario1 = subImage(sheet,80,0,16,16);
        for(int i = 0; i < 2;i++){
            Chestnut[i] = subImage(sheet3_Enemy,227 + i * 16,11,16,17);
        }
        for(int i = 0; i < 2;i++){
            Turtle[i] = subImage(sheet3_Enemy,338 + i * 16,29,16,35);
        }
        ChestnutDie = subImage(sheet3_Enemy,259,19,15,8);
        TurtleDie = subImage(sheet3_Enemy,402,19,16,35);

            mushroom = subImage(sheet3_Enemy,434 ,29,35,35);



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

        if (is_dead){
            is_moving = false;
            is_left = false;
            is_right = false;
            is_Flying = false;
            animatemario();
        }

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
//            System.out.println(pos.getX());
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
            System.out.println(pos.getY());
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
                pos.setLocation(pos.getX(), groundPosition);
            } else {
                pos.setLocation(pos.getX(), Math.min(pos.getY() + upV, groundPosition));
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
//        changeBackgroundColor(Color.white);
        clearBackground(width(),height());
        drawImage(menubackground1,0,0,800,645);

        if(state == GameState.Menu) {
            // Show Main Menu
            drawMenu();
        } else if(state == GameState.Play) {
            // Show Game
            drawGame();
        } else if (state == GameState.Help) {
            drawHelp();
        }
        else if(state == GameState.Codex) {
            // Show Options Menu
            drawCodex();
        }


    }

    public void drawGame(){
        if (!gameover){
             if (is_left)
            {
                if (!is_center)
                {
                    drawImage(background, 0, 0, 10500, 645);
                    drawObstacle();
                    drawEnemy();
                    restoreLastTransform();
                    if(is_dead){
                        drawImage(deadMario, pos.getX() + 20 * 2, pos.getY(), 20 * 2, 20 * 2);
                        timereset();
                    }else
                    if(is_Flying || is_jump){
                        if (!Tobebig) {
                            drawImage(jumpMario, pos.getX() + 20 * 2, pos.getY(), -20 * 2, 20 * 2);
                        }else {
                            drawImage(jumpMario1, pos.getX() + 20 * 2, pos.getY(), -20 * 4, 20 * 4);
                        }
                    }else {
                        if(is_dead){
                            drawImage(deadMario, pos.getX(), pos.getY(), 20 * 2, 20 * 2);
                            timereset();
                        }else {
                            drawImage(frames[currentFrame], pos.getX() + 20 * 2 , pos.getY(), -20 * 2, 20 * 2);
                        }
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
                    if(is_dead){
                        drawImage(deadMario, 250 + 20*2, pos.getY(), 20 * 2, 20 * 2);
                        timereset();
                    }else
                    if(is_Flying || is_jump){
                        if (!Tobebig) {
                            drawImage(jumpMario, 250 + 20 * 2, pos.getY(), -20 * 2, 20 * 2);
                        }else {
                            drawImage(jumpMario1, pos.getX() + 20 * 2, pos.getY(), -20 * 4, 20 * 4);
                        }
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
                    restoreLastTransform();
                    if(is_dead){
                        drawImage(deadMario, pos.getX(), pos.getY(), 20 * 2, 20 * 2);
                        timereset();
                    }else
                    if(is_Flying || is_jump){
                        if (!Tobebig) {
                            drawImage(jumpMario, pos.getX(), pos.getY(), 20 * 2, 20 * 2);
                        }else {
                            drawImage(jumpMario1, pos.getX() + 20 * 2, pos.getY(), -20 * 4, 20 * 4);
                        }

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
                    if(is_dead){
                            drawImage(deadMario, 250, pos.getY(), 20 * 2, 20 * 2);
                        timereset();
                    }else
                    if(is_Flying || is_jump){
                        if (!Tobebig) {
                            drawImage(jumpMario, 250, pos.getY(), 20 * 2, 20 * 2);
                        }else {
                            drawImage(jumpMario1, pos.getX() + 20 * 2, pos.getY(), -20 * 4, 20 * 4);
                        }

                    }else {

                            drawImage(frames[currentFrame], 250, pos.getY(), 20 * 2, 20 * 2);


                    }

                }
            }
            changeColor(green);
            drawBoldText(50,50,"score:"+score,"Arial", 30);
        }else{
            changeColor(white);
            drawBoldText(80, 300, "GAME OVER!", "Arial", 100);
            drawText(60, 500, "Press Space to quit the game!", "Arial", 50);
        }



        V -= 5;
    }
    public void drawMenu(){
        if(menuOption == 0) {
            changeColor(white);
//            drawText(200, 350, "Play");
            drawBoldText(200,200,"Play","Arial",80);
            M1 = subImage(sheet,16,0,16,16);
            drawImage(M1, 100, 120, 100, 100);
        } else {
            changeColor(150, 150, 150);
            drawBoldText(200,200,"Play","Arial",50);
        }

        // Options
        if(menuOption == 1) {
            changeColor( white);
            drawBoldText(200, 300, "Codex","Arial",80);
            M1 = subImage(sheet,16,0,16,16);
            drawImage(M1, 100, 220, 100, 100);
        } else {
            changeColor(150, 150, 150);
            drawBoldText(200, 300, "Codex","Arial",50);
        }

        // Exit
        if(menuOption == 2) {
            changeColor( white);
            drawBoldText(200, 420, "Help","Arial",80);
            M1 = subImage(sheet,16,0,16,16);
            drawImage(M1, 100, 350, 100, 100);
        } else {
            changeColor(150, 150, 150);
            drawBoldText(200, 400, "Exit","Arial",50);
        }
    }

    public void drawCodex(){
        changeColor(green);
//            drawText(200, 350, "Play");
        drawBoldText(50,100,"Mario","Arial",50);
        drawBoldText(200,100,"Goomba ","Arial",50);
        M1 = subImage(sheet,16,0,16,16);
        drawImage(M1, 50, 120, 100, 100);
        G1 = subImage(sheet3_Enemy,227,11,16,17);
        drawImage(G1,250,120,100,100);

    }

    public void drawHelp(){
        changeColor(green);
        drawBoldText(100,100,"Help Text","Arial",20);
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
    boolean is_dead = false;
    boolean Tobebig = false;
    int upV;

    @Override
    public void keyPressed(KeyEvent e) {
        if(state == GameState.Menu) {
            // Call keyPressed for Main Menu
            keyPressedMenu(e);
        }  else if(state == GameState.Play) {
            // Call keyPressed for Game
            keyPressedGame(e);
        } else if (state == GameState.Help) {
            keyPressedHelp(e);
        } else if (state == GameState.Codex) {
            keyPressedCodex(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(state == GameState.Play) {
            // Call handler for Game Play
            keyReleasedGame(e);
        }

    }
    public void keyPressedGame(KeyEvent e){

        if (is_dead){
            is_moving = false;
            is_left = false;
            is_right = false;
            is_Flying = false;
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
//              resetgame();
              stopAudioLoop(Title);
              state = GameState.Menu;
                System.exit(0);

            }
//            if (e.getKeyCode() == KeyEvent.VK_ENTER){
////                GameState state = GameState.Menu;
//                resetgame();
//            }
        }else{
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
    }
    public void keyPressedMenu(KeyEvent e){
        // Move up in the menu
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            // Add code to move up the menu
            if(menuOption<=3){
                menuOption--;
            }else {
                menuOption = 3;
            }
        }
        // Move down in the menu
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Add code to move down the menu
            if (menuOption<=3) {

                menuOption++;
            }else {
                menuOption = 0;
            }
        }
        // Select an item
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Add code to either play the game,
            // move to the options menu or
            // exit the game
            if (menuOption == 0){
//                resetgame();
                state = GameState.Play;
            } else if (menuOption == 1) {
                state = GameState.Codex;

            } else if (menuOption ==2) {
                state = GameState.Help;
            }

        }
    }

    public void keyPressedCodex(KeyEvent e) {
        // Move up in the menu
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            // Add code to move up the menu
            if(menuOption<=3){
                menuOption--;
            }else {
                menuOption = 3;
            }
        }
        // Move down in the menu
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Add code to move down the menu
            if(menuOption<=3){
                menuOption++;
            }else {
                menuOption = 3;
            }
        }
        // Select an item
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Add code to change difficulty or
            // return to the main menu

                state = GameState.Menu;


        }
    }
    public void keyPressedHelp(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            state = GameState.Menu;
        }
    }

    public void keyReleasedGame(KeyEvent e){
        if (is_dead){
            is_moving = false;
            is_left = false;
            is_right = false;
            is_Flying = false;
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
//                System.exit(0);
                resetgame();
                state = GameState.Menu;
            }
//            if (e.getKeyCode() == KeyEvent.VK_ENTER){
////                GameState state = GameState.Menu;
//                resetgame();
//            }
        }else {
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
