package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.image.BufferedImage;

public class GameObject {

    public static GameObject newInstance(String type, float x, float y) {
        return switch (type) {
            case "9", "3" -> new Wall(x, y, ResourceManager.getSprite("ubwall"));
            case "2" -> new BreakableWall(x, y, ResourceManager.getSprite("bwall"));
            case "4" -> new Health(x, y, ResourceManager.getSprite("potion"));
            case "5" -> new speed(x, y, ResourceManager.getSprite("speed"));
            case "6" -> new powerup(x, y, ResourceManager.getSprite("powerup"));
            default -> throw new UnsupportedOperationException();
        };
    }
}
