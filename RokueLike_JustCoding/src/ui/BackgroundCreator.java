package ui;

import domain.halls.Hall;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfWater;
import utils.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BackgroundCreator {

    private static final int TILE_SIZE = 16;
    private static final int WIDTH = 1080;
    private static final int HEIGHT = 720;
    private static final int LIMITED_WIDTH = 664;
    private static final int LIMITED_HEIGHT = 574;
    private static final String[] FLOOR_IMAGE_PATHS = {
            "assets/floors/floor_mud_ne.png",
            "assets/floors/floor_mud_nw.png",
            "assets/floors/floor_mud_s_1.png",
            "assets/floors/floor_mud_w.png",
            "assets/floors/floor_plain.png",
            "assets/floors/floor_stain_1.png",
            "assets/floors/floor_stain_2.png"
    };

    // Cache floor images in memory
    private static BufferedImage[] cachedFloorImages;

    /**
     * Preloads all floor images into memory for efficient reuse.
     */
    private static void preloadFloorImages() {
        if (cachedFloorImages == null) {
            cachedFloorImages = new BufferedImage[FLOOR_IMAGE_PATHS.length];
            for (int i = 0; i < FLOOR_IMAGE_PATHS.length; i++) {
                cachedFloorImages[i] = ResourceLoader.loadImage(FLOOR_IMAGE_PATHS[i]);
            }
        }
    }
    public static BufferedImage createBackground() {
        preloadFloorImages(); // Ensure images are preloaded

        // Create a new blank background
        BufferedImage background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = background.getGraphics();
        Random random = new Random();

        // Use cached images to draw the background
        for (int y = 0; y < HEIGHT; y += TILE_SIZE) {
            for (int x = 0; x < WIDTH; x += TILE_SIZE) {
                int randomIndex = random.nextInt(cachedFloorImages.length);
                g.drawImage(cachedFloorImages[randomIndex], x, y, TILE_SIZE, TILE_SIZE, null);
            }
        }

        g.dispose();
        return background;
    }

    public static BufferedImage createFixedBackground() {
        return ResourceLoader.loadImage("assets/fixed_background.JPG");
    }

    public static BufferedImage createFixedMainMenuBackground() {
        return ResourceLoader.loadImage("assets/main_menu_assets/main_menu.JPG");
    }

    public static BufferedImage createHallBackground(Hall hall) {
        if(hall instanceof HallOfEarth){
            return ResourceLoader.loadImage("assets/halls/hall_of_earth.JPG");
        } else if (hall instanceof HallOfAir) {
            return ResourceLoader.loadImage("assets/halls/hall_of_air.JPG");
        }
        else if (hall instanceof HallOfWater) {
            return ResourceLoader.loadImage("assets/halls/hall_of_water.JPG");
        }
        else {
            return ResourceLoader.loadImage("assets/halls/hall_of_fire.JPG");
        }
    }

    public static BufferedImage createLimitedBackground() {
        preloadFloorImages();
        BufferedImage background = new BufferedImage(LIMITED_WIDTH, LIMITED_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = background.getGraphics();
        Random random = new Random();

        // Use cached images to draw the background
        for (int y = 0; y < LIMITED_HEIGHT; y += TILE_SIZE) {
            for (int x = 0; x < LIMITED_WIDTH; x += TILE_SIZE) {
                int randomIndex = random.nextInt(cachedFloorImages.length);
                g.drawImage(cachedFloorImages[randomIndex], x, y, TILE_SIZE, TILE_SIZE, null);
            }
        }
        g.dispose();
        return background;
    }


}
