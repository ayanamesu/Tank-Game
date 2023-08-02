package tankrotationexample.Resources;
import tankrotationexample.game.Sound;

import javax.sound.sampled.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap();
    private final static Map<String, Sound> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animations = new HashMap<>();
    private final static Map<String, Integer> animationInfo = new HashMap<>() {{
        put("bullethit", 24);
        put("bulletshoot", 24);
        put("powerpick", 32);
        put("puffsmoke", 32);
        put("rocketflame", 16);
        put("rockethit", 32);

    }};


    private static BufferedImage loadSprites(String path) throws IOException {
        return ImageIO.read(ResourceManager.class.getClassLoader().getResource(path));
    }

    //Maybe Wrong
    private static Sound loadSound(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResource(path)));
        Clip c = AudioSystem.getClip();
        c.open(ais);
        Sound s = new Sound(c);
        s.setVolume(.1f);
        return s;
    }

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



    public static void loadResources() {
        ResourceManager.initSprites();
        ResourceManager.initAnimations();
        ResourceManager.initSounds();
    }


    public static BufferedImage getSprite(String type) {
        if (!ResourceManager.sprites.containsKey(type)) {
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }


    public static List<BufferedImage> getAnimation(String type) {
        if(!ResourceManager.animations.containsKey(type)) {
            throw new RuntimeException("%s resource is missing.".formatted(type));
        }
        return ResourceManager.animations.get(type);
    }

    public static Sound getSound(String type) {
        if(!ResourceManager.sounds.containsKey(type)) {
            throw new RuntimeException("%s resource is missing.".formatted(type));
        }
        return ResourceManager.sounds.get(type);
    }

    private static void initAnimations() {
        String baseName = "animations/%s/%s_%04d.png";
        animationInfo.forEach((animationName, frameCount) -> {
            List<BufferedImage> frames = new ArrayList<>();
            try {
                for (int i = 0; i < frameCount; i++) {
                    String spritePath = baseName.formatted(animationName, animationName, i);
                    frames.add(loadSprites(spritePath));
                }
                ResourceManager.animations.put(animationName, frames);
            } catch (IOException e) {
                System.out.println(e);
                    throw new RuntimeException(e);
            }
            });

        }

    private static void initSounds() {
        try {
            ResourceManager.sounds.put("bullet_shoot", loadSound("sounds/bullet_shoot.wav"));
            ResourceManager.sounds.put("explosion", loadSound("sounds/explosion.wav"));
            ResourceManager.sounds.put("bgs", loadSound("sounds/Music.mid"));
            ResourceManager.sounds.put("pickup", loadSound("sounds/pickup.wav"));
            ResourceManager.sounds.put("shotfire", loadSound("sounds/shotfiring.wav"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ResourceManager.loadResources();
        Sound bg = ResourceManager.getSound("bgs");
        bg.setLooping();

        while (true) {
            System.out.println();

        }
    }


}
