package model.utils;

import model.exceptionhandler.LoadSaveException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String BOMB_ATLAS = "bomb.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String CRABBY_SPRITE = "crabby_sprite.png";
    public static final String STATUS_BAR = "health_power_bar.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";

    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String SMALL_CHAIN = "small_chains.png";
    public static final String LARGE_CHAIN = "large_chains.png";
    public static final String CANDLE = "candle.png";
    public static final String CANDLE_LIGHT = "candle_light.png";
    public static final String CONTAINER_ATLAS = "objects_sprites.png";
    public static final String TRAP_ATLAS = "trap_atlas.png";

    public static final String RUNNING_PARTICLE = "run_particle.png";
    public static final String FALLING_PARTICLE = "falling_particle.png";
    public static final String JUMPING_PARTICLE = "jumping_particle.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        try (InputStream is = LoadSave.class.getResourceAsStream("/" + fileName)) {
            if (is == null) {
                throw new LoadSaveException("Resource not found: " + fileName);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new LoadSaveException("Error loading image: " + fileName, e);
        }
    }

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        if (url == null) {
            throw new LoadSaveException("Levels directory not found");
        }

        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new LoadSaveException("Invalid URI syntax for levels directory", e);
        }

        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            throw new LoadSaveException("No level files found");
        }

        File[] filesSorted = new File[files.length];
        for (int i = 0; i < filesSorted.length; i++) {
            for (File f : files) {
                if (f.getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = f;
                    break;
                }
            }
            if (filesSorted[i] == null) {
                throw new LoadSaveException("Missing level file: " + (i + 1) + ".png");
            }
        }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];
        for (int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new LoadSaveException("Error reading level file: " + filesSorted[i].getName(), e);
            }
        }
        return imgs;
    }
}
