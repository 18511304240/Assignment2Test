import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Enemy implements Runnable {
    //    食人花敌人
    public static List<Image> flower = new ArrayList<>();
    //    蘑菇敌人
    public static List<Image> mogu = new ArrayList<>();
    //鸟敌人
    public static List<Image> bird = new ArrayList<>();
    //    存储当前坐标
    private int x, y;
    //    存储敌人类型
    private int type;
    //    判断敌人运动方向
    public static boolean face_to = true;
    //    用于显示敌人的当前图像
    private BufferedImage show;
    //    食人花运动的极限范围
    private int max_up = 0;
    private int max_down = 0;
    //    定义线程对象
    private Thread thread = new Thread(this);
    //    定义当前的图片的状态
    private int image_type = 0;
    //判断光束出现的时间
    private int time_to = 0;
    //判断光束是否消失
    private boolean vanish = false;

    //    蘑菇敌人的构造函数
    public Enemy(int x, int y, boolean face_to, int type) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        show = (BufferedImage) mogu.get(0);
        thread.start();
    }

    //    食人花敌人的构造函数
    public Enemy(int x, int y, boolean face_to, int type, int max_up, int max_down) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        this.max_down = max_down;
        this.max_up = max_up;
        show = (BufferedImage) flower.get(0);
        thread.start();
    }
//    //光束的构造函数
//    public Enemy(int x, int y, boolean face_to, int type, BackGround bg,int time_to,boolean vanish) {
//        this.x = x;
//        this.y = y;
//        this.face_to = face_to;
//        this.type = type;
//        this.bg = bg;
//        this.time_to = time_to;
//        this.vanish = vanish;
//        show = StaticValue.light.get(0);
//        thread.start();
//}

    //     死亡方法
    public void death() {
        show = (BufferedImage) mogu.get(2);
//        this.bg.getEnemyList().remove(this);
    }


    @Override
    public void run() {
        while (true) {
//            判断是否为蘑菇敌人
            if (type == 1) {
                if (face_to) {
                    this.x -= 1;
                } else {
                    this.x += 1;
                }
                image_type = image_type == 1 ? 0 : 1;
                show = (BufferedImage) mogu.get(image_type);
            }
            //            判断是否为鸟敌人
            if (type == 3) {
                if (face_to) {
                    this.x -= 1;
                } else {
                    this.x += 1;
                }
                image_type = image_type == 1 ? 0 : 1;
                show = (BufferedImage) bird.get(image_type);
            }
//            if (type == 4) {
//                if (face_to) {
//                    this.x -= 4;
//                } else {
//                    this.x += 4;
//                }
//                image_type = image_type == 1 ? 0 : 1;
//                show = StaticValue.fire.get(image_type);
//            }
//            定义两个布尔变量
            boolean canLeft = true;
            boolean canRight = true;

            if (this.x == 650){
                canLeft = false;
            }

            if (this.y == 1000){
                canRight = false;
            }
            if (face_to && !canLeft || this.x == 0) {
                face_to = false;
            } else if ((!face_to) && (!canRight) || this.x == 1000) {
                face_to = true;
            }
//            判断是否为食人花敌人
            if (type == 2) {
                if (face_to) {
                    this.y -= 1;
                } else {
                    this.y += 1;
                }
                image_type = image_type == 1 ? 0 : 1;

//                判断食人花是否到达极限位置
                if (face_to && (this.y == max_up)) {
                    face_to = false;
                }
                if ((!face_to) && (this.y == max_down)) {
                    face_to = true;
                }
                show = (BufferedImage) flower.get(image_type);
            }

//            if(type == 5){
//                if(vanish){
//                    this.time_to -= 20;
//                }else{
//                    this.time_to += 20;
//                }
//
//                if(vanish && (this.time_to == 0)){
//                    vanish = false;
//                    image_type = 0;
//                }
//                if(!vanish && (this.time_to == 800)){
//                    vanish = true;
//                    image_type = 1;
//                }
//                show = StaticValue.light.get(image_type);
//            }
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }
    }

    public BufferedImage getShow() {
        return show;
    }

    public  int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public  void setX(int i){
         this.x = i;
    }
    public void setY(int i){
        this.y = i;
    }

    public int getType() {
        return type;
    }

    public boolean isVanish() {

        return vanish;
    }


}
