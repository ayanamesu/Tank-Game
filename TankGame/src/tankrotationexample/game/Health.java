package tankrotationexample.game;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends GameObject implements PowerUps {
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
        this.hasCollided = true;

    }
    public void drawImage(Graphics buffer) {
        if(!hasCollided) {
            buffer.drawImage(this.img, (int) x, (int) y, null);
        }
    }
    @Override
    public void applyPowerUp(Tank tank) {
        tank.addHealth();
        collides(tank);
    }
}
