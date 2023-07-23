package tankrotationexample.Resources;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap();
    private final static Map<String, List<BufferedImage>> animation = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<>();

    private static BufferedImage loadSprites(String path) throws IOException {
        return ImageIO.read(ResourceManager.class.getClassLoader().getResource("tank/tank1.png"));
    }

    private static void initSprites() {
        try {

            ResourceManager.sprites.put("tank1",loadSprites("tank/tank1.png"));

            ResourceManager.sprites.put("tank2",loadSprites("tank/tank2.png"));

            ResourceManager.sprites.put("bullet",loadSprites("bullets/bullet.png"));

            ResourceManager.sprites.put("star1",t);

            ResourceManager.sprites.put("star2",t);

            ResourceManager.sprites.put("bullet",t);

            ResourceManager.sprites.put("menu",loadSprites("menu/title.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void loadResources() {
        ResourceManager.initSprites();
    }
    public static void main(String[] args) {
        ResourceManager.initSprites();
        System.out.println();
    }
}
