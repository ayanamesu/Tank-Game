package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet {
    private int x;
    private int y;
    private int vx;
    private int vy;

    private boolean active;

    public Bullet(int x, int y, int angle, BufferedImage bulletImage) {
        this.x = x;
        this.y = y;
        this.vx = (int) Math.round(3 * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(3 * Math.sin(Math.toRadians(angle)));

    }

    public void update() {

    }

    public void drawImage(Graphics2D g) {
        if (active) {

        }
    }

    public boolean isActive() {
        return active;
    }
}