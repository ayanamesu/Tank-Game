package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {
    float x,y;
    BufferedImage img;

    private Rectangle hitbox;
    public Wall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox= new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());

    }
    public Rectangle getHitbox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void collides(GameObject obj2) {

    }
    @Override
    public String toString() {
        return "Wall-> x: " + x + " y: " + y;
    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);
        //test
//        buffer.setColor(Color.black);
//        buffer.drawRect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height);
    }


}
