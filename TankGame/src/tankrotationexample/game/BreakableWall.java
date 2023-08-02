package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    private int numHits;
    public BreakableWall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox= new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
        this.numHits = 0;
    }
    public Rectangle getHitbox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void collides(GameObject obj2) {

    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);
    }
}
