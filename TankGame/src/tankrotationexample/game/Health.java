package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    public Health(float x, float y, BufferedImage img) {
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

    public void onCollected() {
        // You can add any specific effects here if needed.
        // For now, we can simply remove the health potion from the game.
        // You can modify this part based on what you want to do when the health potion is collected.
        this.x = -100; // Move the health potion off-screen to "delete" it from the game
        this.y = -100;
    }


    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);
    }
}
