package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private float x;
    private float y;
    private float vx;
    private float vy;

    private float charge = 1f;

    private float angle;

    private float R = 6;

    private BufferedImage img;

    private Rectangle hitbox;

    private boolean visible;

    public Bullet(float x, float y, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.vx =0;
        this.vy = 0;
        this.angle = angle;
        this.hitbox= new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
    }
    public Rectangle getHitbox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void collides(GameObject obj2) {

    }

    public void increaseCharge() {
        this.charge = this.charge + 0.05f;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    void update() {

        this.moveForwards();
        this.hitbox.setLocation((int)x,(int)y);

    }

    private void moveForwards() {
        x += Math.round(R * Math.cos(Math.toRadians(angle)));
        y += Math.round(R * Math.sin(Math.toRadians(angle)));
        checkBorder();
    }

    private void checkBorder() {
        if ((x < 30) || (x >= GameConstants.GAME_WORLD_WIDTH - 88)) {
            this.visible = false;
        }
        if ((y < 30) || (y >= GameConstants.GAME_WORLD_HEIGHT - 88)) {
            this.visible = false;
        }

//        this.updateHitBox((int) x, (int) y);
    }

//    private void updateHitBox(int x, int y) {
//        this.hitbox.x = x;
//        this.hitbox.y = y;
//    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        rotation.scale(this.charge,this.charge);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

    }

    public void setHeading(float x, float y, float angle) {
        this.x =x;
        this.y =y;
        this.angle = angle;

    }
}
