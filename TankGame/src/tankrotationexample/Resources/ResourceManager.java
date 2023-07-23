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
        return ImageIO.read(ResourceManager.class.getClassLoader().getResource(path));
    }

    private static void initSprites() {
        try {
            ResourceManager.sprites.put("tank1",loadSprites("tank/tank1.png"));
            ResourceManager.sprites.put("tank2",loadSprites("tank/tank2.png"));
            ResourceManager.sprites.put("bullet",loadSprites("bullets/bullet.png"));
            ResourceManager.sprites.put("star1",loadSprites("bullets/star1.png"));
            ResourceManager.sprites.put("star2",loadSprites("bullets/star2.png"));
            ResourceManager.sprites.put("bwall",loadSprites("walls/breakableWall.png"));
            ResourceManager.sprites.put("bwall1",loadSprites("walls/breakableWall1.png"));
            ResourceManager.sprites.put("bwall2",loadSprites("walls/breakableWall2.png"));
            ResourceManager.sprites.put("ubwall",loadSprites("walls/unbreakableWall.png"));
            ResourceManager.sprites.put("menu",loadSprites("menu/title.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void loadResources() {
        ResourceManager.initSprites();
    }


    public static BufferedImage getSprite(String type) {
        if(!ResourceManager.sprites.containsKey(type)){
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }
}
