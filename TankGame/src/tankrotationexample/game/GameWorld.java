package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private final Launcher lf;
    private long tick = 0;

    List<GameObject> gobjs = new ArrayList<>(1000);
    List<Animation> anims = new ArrayList<>();
    private List<Bullet> bulletsCollided = new ArrayList<>();


    /**
     *
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }



    @Override
    public void run() {
        try {
            while (true) {
                this.tick++;
                this.t1.update(this); // update tank
                this.t2.update(this);
                this.anims.forEach(animation -> animation.update());
                this.checkCollision();

                this.repaint();   // redraw game


                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                 */

                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    private void checkCollision() {
        for(int i = 0; i < this.gobjs.size(); i++) {
            GameObject obj1 = this.gobjs.get(i);
            if (obj1 instanceof  BreakableWall || obj1 instanceof Wall || obj1 instanceof  Health || obj1 instanceof  speed || obj1 instanceof  powerup) {
                continue;
            }
            for (int j = 0; j < this.gobjs.size(); j++) {
                if (i == j) continue;
                GameObject obj2 = this.gobjs.get(j);
                if (obj2 instanceof  Tank) continue;
                if(obj1.getHitbox().intersects(obj2.getHitbox())) {
                    obj1.collides(obj2);
                    System.out.println(obj1 + " " + "Has hit" + " " + obj2);

                }

            }
        }
    }
//        for (int i = 0; i < this.gobjs.size(); i++) {
//            GameObject obj1 = this.gobjs.get(i);
//            if (obj1 instanceof BreakableWall || obj1 instanceof Wall || obj1 instanceof Health || obj1 instanceof speed || obj1 instanceof powerup) {
//                continue;
//            }
//            for (int j = 0; j < this.gobjs.size(); j++) {
//                if (i == j) continue;
//                GameObject obj2 = this.gobjs.get(j);
//                if (obj2 instanceof Tank) continue;
//
//
//                if (obj1 instanceof Bullet && bulletsCollided.contains(obj1)) {
//                    continue;
//                }
//
//                if (obj1.getHitbox().intersects(obj2.getHitbox())) {
//                    obj1.collides(obj2);
//                    System.out.println(obj1 + " Has hit " + obj2);
//
//
//                    if (obj1 instanceof Bullet) {
//                        bulletsCollided.add((Bullet) obj1);
//                    }
//                }
//            }
//        }
//    }



    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        //Sound
//        Clip musicTheme = ResourceManager.getSound("Music.mp3");


        /**
         * 0 ---> nothing
         * 9 ---> unbreakables But non-collidable
         * 3 ---> unbreakables
         * 2 ---> breakables
         * 4 ---> health
         * 5 ---> speed
         * 6 ---> powerup
         */

        InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResourceAsStream("maps/map1.csv")));
//        this.anims.add(new Animation(300,300,ResourceManager.getAnimation("bullethit")));
//        this.anims.add(new Animation(350,300,ResourceManager.getAnimation("bulletshoot")));
//        this.anims.add(new Animation(400,300,ResourceManager.getAnimation("powerpick")));
//        this.anims.add(new Animation(450,300,ResourceManager.getAnimation("puffsmoke")));
//        this.anims.add(new Animation(500,300,ResourceManager.getAnimation("rocketflame")));
//        this.anims.add(new Animation(550,300,ResourceManager.getAnimation("rockethit")));
        try (BufferedReader mapReader = new BufferedReader(isr)) {
            int row = 0;
            String[] gameItems;
            while (mapReader.ready()) {
                gameItems = mapReader.readLine().strip().split(",");
                for (int col = 0; col < gameItems.length; col++) {
                    String gameObject = gameItems[col];
                    if ("0".equals(gameObject)) continue;
                    this.gobjs.add(GameObject.newInstance(gameObject, col * 30, row * 30));
                }
                row++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        t1 = new Tank(300, 600, 0, 0, (short) 0, ResourceManager.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank(1800, 600, 0, 0, (short) 180, ResourceManager.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_C);
        this.lf.getJf().addKeyListener(tc2);

        this.gobjs.add(t1);this.gobjs.add(t2);
    }



    private void drawFloor(Graphics2D buffer) {
        BufferedImage floor = ResourceManager.getSprite("bg");
        for (int i = 0; i < GameConstants.GAME_WORLD_WIDTH; i += 320) {
            for (int j = 0; j < GameConstants.GAME_WORLD_HEIGHT; j += 240) {
                buffer.drawImage(floor, i, j, null);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        this.drawFloor(buffer);
        this.gobjs.forEach(gameObject -> gameObject.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        this.anims.forEach(animation -> animation.drawImage(buffer));
        g2.drawImage(world, 0, 0, null);
        // IF I TRY TO USE THIS BUFFERIMAGE for mm I GET A GIANT ERROR loop
//        BufferedImage mm = world.getSubimage(0,0,GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);
        this.drawSplitScreen(world, g2);
        this.drawMiniMap(world, g2);


    }



    private void drawSplitScreen(BufferedImage world, Graphics2D g2) {
        int leftTankX = (int) t1.getX() - GameConstants.GAME_SCREEN_WIDTH / 4;
        int leftTankY = (int) t1.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2;
        int rightTankX = (int) t2.getX() - GameConstants.GAME_SCREEN_WIDTH / 4;
        int rightTankY = (int) t2.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2;

        leftTankX = Math.max(0, Math.min(leftTankX, world.getWidth() - GameConstants.GAME_SCREEN_WIDTH / 2));
        leftTankY = Math.max(0, Math.min(leftTankY, world.getHeight() - GameConstants.GAME_SCREEN_HEIGHT));
        rightTankX = Math.max(0, Math.min(rightTankX, world.getWidth() - GameConstants.GAME_SCREEN_WIDTH / 2));
        rightTankY = Math.max(0, Math.min(rightTankY, world.getHeight() - GameConstants.GAME_SCREEN_HEIGHT));

        BufferedImage lh = world.getSubimage(leftTankX, leftTankY, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rh = world.getSubimage(rightTankX, rightTankY, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);

        g2.drawImage(lh, 0, 0, null);
        g2.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH / 2, 0, null);

        g2.setColor(Color.white);
        g2.drawRect(GameConstants.GAME_SCREEN_WIDTH / 2 - 2, 0, 4, GameConstants.GAME_SCREEN_HEIGHT);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH / 2 - 2, 0, 4, GameConstants.GAME_SCREEN_HEIGHT);
    }


    private void drawMiniMap(BufferedImage world, Graphics2D g2) {
        BufferedImage minimap = new BufferedImage(GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D minimapGraphics = minimap.createGraphics();
        minimapGraphics.drawImage(world, 0, 0, null);
        g2.scale(0.2, 0.2);
        g2.drawImage(minimap,
                (GameConstants.GAME_SCREEN_WIDTH * 5)/2- (GameConstants.GAME_WORLD_WIDTH )/2,
                (GameConstants.GAME_SCREEN_HEIGHT * 5) - (GameConstants.GAME_WORLD_HEIGHT)-190, null);
    }


    public void addGameObject(GameObject obj) {
        this.gobjs.add(obj);
    }
}
