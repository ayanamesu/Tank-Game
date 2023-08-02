package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tank extends GameObject {

    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;



    private float R = 3f;
    private float ROTATIONSPEED = 3.0f;

    List<Bullet> ammo = new ArrayList<>();
    long timeSinceLastShot = 0L;
    long cooldown = 2000;
    Bullet currentChargeBullet = null;



    private int health = 100;
    private int lives = 3;

    private boolean isDead;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private boolean ShootPressed;

    private Rectangle hitbox;

    private boolean isReverse;
    private float initialX;
    private float initialY;
    private boolean hasReceivedPowerUp = false;

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.isReverse = false;
        this.isDead = false;
        this.initialX = x;
        this.initialY = y;




        this.hitbox= new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
    }
    public Rectangle getHitbox() {
        return this.hitbox.getBounds();
    }
    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    private float safeX() {
        // Calculate the safe x-coordinate for bullets to start from
        float tankCenterX = x + this.img.getWidth() / 5f;
        float bulletXOffset = (float) (R * Math.cos(Math.toRadians(angle)));
        return tankCenterX + bulletXOffset;
    }

    private float safeY() {
        // Calculate the safe y-coordinate for bullets to start from
        float tankCenterY = y + this.img.getHeight() / 2f;
        float bulletYOffset = (float) (R * Math.sin(Math.toRadians(angle)));
        return tankCenterY + bulletYOffset;
    }


    public void resetHealth() {
        this.health = 100;
    }

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

    void update(GameWorld gw) {
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

        if (this.ShootPressed && ((this.timeSinceLastShot + this.cooldown) < System.currentTimeMillis())) {
            this.timeSinceLastShot = System.currentTimeMillis();
//            this.ammo.add(new Bullet(x, y, angle, ResourceManager.getSprite("bullet")));
            var b = new Bullet(safeX(),y,angle,ResourceManager.getSprite("bullet"));
            this.ammo.add(b);
            gw.addGameObject(b);
            gw.anims.add(new Animation(safeX(),y,ResourceManager.getAnimation("bulletshoot")));
            ResourceManager.getSound("shotfire").playSound();


        }

        this.ammo.forEach(bullet -> bullet.update());
        this.hitbox.setLocation((int)x,(int)y);

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
        this.hitbox.setLocation((int)this.x, (int)this.y);


    }
    public void getShot() {
        this.health -= 15;

        if (this.health <= 0) {
            this.resetHealth();
            this.lives -= 1;
        }

        if(this.lives <= 0) {
            this.isDead = true;
        }

    }

    public boolean getIsDead() {
        return this.isDead;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        if(this.currentChargeBullet != null) {
            this.currentChargeBullet.drawImage(g2d);
        }
        this.ammo.forEach(b ->b.drawImage(g2d));
        g2d.setColor(Color.CYAN);
        g2d.drawRect((int)x-25,(int)y-20, 100, 10);
        long currentWidth = 100 - ((this.timeSinceLastShot + this.cooldown) - System.currentTimeMillis())/20;
        if(currentWidth >100) {
            currentWidth = 100;
        }
        g2d.fillRect((int)x-25,(int)y-20,(int)currentWidth , 10);
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
        if(this.health >= 70) {
            g2d.setColor(Color.GREEN);
        } else if (this.health >= 35) {
            g2d.setColor(Color.ORANGE);
        } else {
            g2d.setColor(Color.RED);
        }
        g2d.drawRect((int)x-25,(int)y-30, 100, 12 );
        g2d.fillRect((int)x-25,(int)y-30, this.health, 12 );

        for (int i=0; i < this.lives; i++) {
            g2d.drawOval((int)(x-10) + (i*20), (int)y + 55, 15, 15);
            g2d.fillOval((int)(x-10) + (i*20), (int)y + 55, 15, 15);
        }

    }

    public void collides(GameObject with) {
        if (with instanceof Bullet) {
            if (!isDead) {
                health -= 15;
                if (health <= 0) {
                    isDead = true;
                    if (lives > 1) {
                        lives--;
                        respawn();
                    } else {
                        // display game over message
                        System.out.println("Game Over");
                    }
                }

            }
        } else if (with instanceof Wall || with instanceof BreakableWall || with instanceof Tank) {
            if (isReverse) {
                this.moveForwards();
            } else {
                this.moveBackwards();
            }
            checkBorder();
        } else if (with instanceof PowerUps) {
            ((PowerUps) with).applyPowerUp(this);


        }
    }

    private void respawn() {
        this.x = initialX;
        this.y = initialY;
        this.health = 100;
        this.isDead = false;
        hasReceivedPowerUp = false;
    }





    public void toggleShootPressed() {
        this.ShootPressed = true;
    }

    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }


    public void addHealth() {
        if (!hasReceivedPowerUp) {
            this.health += 25;
            hasReceivedPowerUp = true;
            System.out.println("Hp increase +25");

        }
        if(this.health >100) {
            this.health = 100;
        }
    }
    public void addSpeed() {
        if (!hasReceivedPowerUp) {
            this.R *= 1.05f;
            hasReceivedPowerUp = true;
            System.out.println("speed");
        }
    }

    //figure this out later or scrap it
    public void addDamageIncrease() {
        for (Bullet bullet : ammo) {
            bullet.setImage(ResourceManager.getSprite("star2"));
            bullet.setDamage(bullet.getDamage() + 10);
        }
    }

}
