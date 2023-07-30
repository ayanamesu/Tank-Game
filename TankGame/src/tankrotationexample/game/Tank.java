package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Tank{

    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;


    private float R = 4f;
    private float ROTATIONSPEED = 3.0f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private boolean ShootPressed;

    private Bullet b;

    ArrayList<Bullet> ammo = new ArrayList<>();


    float fireDelay = 120f;
    float coolDown = 0f;
    float rateOfFire = 1f;


    private int health = 100;
    private int lives = 5;

    private boolean isDead;
    private float speed = 4f;

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
    }
    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && this.coolDown >= this.fireDelay) {
            this.coolDown = 0;
            b = new Bullet(x, y, angle, ResourceManager.getSprite("bullet"));
            this.ammo.add(b);
        }

//        if (this.getHitBox().intersects(other.getHitBox())) {
//            handleCollision(other);
//        }

        this.coolDown += this.rateOfFire;

//        this.ammo.forEach(b -> b.update());
//
//        this.updateHitBox((int) x, (int) y);
//
//        this.shootOther(other);


    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    public void addLife() {this.lives += 1;}
    public void addSpeed() {this.speed += 2f;}

    public void resetHealth() { this.health = 100; }




    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 88) {
            x = GameConstants.GAME_WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        if (b!= null) {
            this.b.drawImage(g2d);
        }
        this.ammo.forEach(b -> b.drawImage(g2d));
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

    }


    public void toggleShootPressed() {
        this.ShootPressed = true;
    }

    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }
}
