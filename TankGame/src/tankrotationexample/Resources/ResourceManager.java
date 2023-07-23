package tankrotationexample.Resources;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap();
    private final static Map<String, List<BufferedImage>> animation = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<>();

    private static void initSprites() {
        ResourceManager.class.getClassLoader().getResource("tank/tank1.png");

    }
    public static void loadResources() {
        ResourceManager.initSprites();
    }
    public static void main(String[] args) {
        ResourceManager.initSprites();
    }
}
