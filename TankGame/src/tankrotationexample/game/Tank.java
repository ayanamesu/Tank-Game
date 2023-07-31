package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Tank extends GameObject implements PowerUps {

    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;

    private float R = 3f;
    private float ROTATIONSPEED = 3.0f;

    private Bullet b;

    Bullet currentChargeBullet = null;
    private ArrayList<Wall> walls;
    ArrayList<Bullet> ammo = new ArrayList<>();

    float fireDelay = 50f;
    float coolDown = 0f;
    float rateOfFire = 1f;
    private float speed;

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

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.isReverse = false;
        this.isDead = false;

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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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
            //instead of chargedBullet make it so that if u hold spacebar it shoot another type of bullet "star2"?
//            if (this.currentChargeBullet == null) {
//                this.currentChargeBullet = new Bullet(x, y, angle, ResourceManager.getSprite("star2"));
//            } else {
//                this.currentChargeBullet.increaseCharge();
//                this.currentChargeBullet.setHeading(x, y, angle);
//            }
//        } else {
//                if(this.currentChargeBullet != null) {
//                    this.ammo.add(currentChargeBullet);
//                    this.currentChargeBullet = null;
//                    this.coolDown = 0;
//                }
//            }
            this.coolDown = 0;
            b = new Bullet(x, y, angle, ResourceManager.getSprite("bullet"));
            this.ammo.add(b);


        }
        this.coolDown += this.rateOfFire;

        this.ammo.forEach(b -> b.update());
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
        if (b!= null) {
            this.b.drawImage(g2d);
        }
        this.ammo.forEach(b -> b.drawImage(g2d));
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
        g2d.drawRect((int)x-25,(int)y-30, 100, 10 );
        g2d.fillRect((int)x-25,(int)y-30, this.health, 10 );

        for (int i=0; i < this.lives; i++) {
            g2d.drawOval((int)(x-10) + (i*20), (int)y + 55, 15, 15);
            g2d.fillOval((int)(x-10) + (i*20), (int)y + 55, 15, 15);
        }

    }

    public void collides(GameObject with) {
        if (with instanceof Bullet) {
            if (!isDead) {
                health -= 25;
                if (health <= 0) {
                    isDead = true;
                    lives--;
                    if (lives > 0) {
                        respawn();
                    } else {
                        System.out.println("Game Over");
                    }
                }
            }
        } else if (with instanceof  Wall) {
            //idk if this should be it, skips through walls
            if(isReverse) {
                this.moveForwards();
            } else {
                this.moveBackwards();
            } checkBorder();

        } else if (with instanceof  BreakableWall) {
            if(isReverse) {
                this.moveForwards();
            } else {
                this.moveBackwards();
            } checkBorder();

        } else if (with instanceof  Tank) {
            if(isReverse) {
                this.moveForwards();
            } else {
                this.moveBackwards();
            }

        }  else if (with instanceof Health) {
            if (this.health < 100) {
                this.health += 50;
                if (this.health > 100) {
                    this.health = 100;
                }
                ((Health) with).onCollected();
            }
        } else if (with instanceof  PowerUps) {
            ((speed)with).applyPowerUp(this);
        }
    }

    private void respawn() {
        health = 100;
        x = initialX;
        y = initialY;
    }


    public void toggleShootPressed() {
        this.ShootPressed = true;
    }

    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    @Override
    public void applyPowerUp(GameObject tank) {
        if (tank instanceof Tank) {
            Tank t = (Tank) tank;
            // Apply power-up effect to the tank
            // For example, increase the tank's speed and health
            t.setSpeed(t.getSpeed() * 1.5f); // Increase speed by 50%

        }
    }
}
