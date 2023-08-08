package tankrotationexample.game;
import tankrotationexample.Resources.ResourceManager;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    private int health = 50;


    public BreakableWall(float x, float y, BufferedImage img) {
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
        if (obj2 instanceof Bullet) {
            ResourceManager.getSound("explosion").playSound();
            health -= 25;
            if(this.health < 50) {
                this.img = ResourceManager.getSprite("bwall2");
            }
            if (this.health == 0) {
                hasCollided = true;
            }
        }
    }

    public void drawImage(Graphics buffer) {
        if(!hasCollided) {
            buffer.drawImage(this.img, (int) x, (int) y, null);
        }
    }
}
