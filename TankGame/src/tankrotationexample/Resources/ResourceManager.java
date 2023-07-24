package tankrotationexample.Resources;
import javax.sound.sampled.*;
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

    //Maybe Wrong
//    private static Clip loadSound(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(ResourceManager.class.getClassLoader().getResource(path));
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioInputStream);
//        return clip;
//    }

    private static void initSprites() {
        try {
            ResourceManager.sprites.put("tank1", loadSprites("tank/tank1.png"));
            ResourceManager.sprites.put("tank2", loadSprites("tank/tank2.png"));
            ResourceManager.sprites.put("bullet", loadSprites("bullets/bullet.png"));
            ResourceManager.sprites.put("star1", loadSprites("bullets/star1.png"));
            ResourceManager.sprites.put("star2", loadSprites("bullets/star2.png"));
            ResourceManager.sprites.put("bwall", loadSprites("walls/breakableWall.png"));
            ResourceManager.sprites.put("bwall1", loadSprites("walls/breakableWall1.png"));
            ResourceManager.sprites.put("bwall2", loadSprites("walls/breakableWall2.png"));
            ResourceManager.sprites.put("ubwall", loadSprites("walls/unbreakableWall.png"));
            ResourceManager.sprites.put("powerup", loadSprites("powerUp/powerup.png"));
            ResourceManager.sprites.put("potion", loadSprites("powerUp/potion.png"));
            ResourceManager.sprites.put("speed", loadSprites("powerUp/speedrum.png"));
            ResourceManager.sprites.put("bg", loadSprites("floor/bg.bmp"));
            ResourceManager.sprites.put("menu", loadSprites("menu/title.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //tried with sounds
//    private static void initSounds() {
//        try {
//            ResourceManager.sounds.put("bulletsound", loadSound("sounds/bullet.wav"));
//            ResourceManager.sounds.put("musicTheme", loadSound("sounds/Music.mp3"));
//            ResourceManager.sounds.put("music", loadSound("sounds/music.wav"));
//            ResourceManager.sounds.put("pickup", loadSound("sounds/pickup.wav"));
//            ResourceManager.sounds.put("shotexplosion", loadSound("sounds/shotexplosion.wav"));
//            ResourceManager.sounds.put("shotfiring", loadSound("sounds/shotfiring.wav"));
//        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public static void loadResources() {
        ResourceManager.initSprites();
//        ResourceManager.initSounds();
    }


    public static BufferedImage getSprite(String type) {
        if (!ResourceManager.sprites.containsKey(type)) {
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }

    //Maybe Wrong
//    public static Clip getSound(String type) {
//        if (!ResourceManager.sounds.containsKey(type)) {
//            throw new RuntimeException("%s is missing from sound resources".formatted(type));
//        }
//        return ResourceManager.sounds.get(type);
//    }
}
