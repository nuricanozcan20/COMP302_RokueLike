package utils;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {

    public static BufferedImage loadImage(String path) {
        try (InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new RuntimeException("Resource not found: " + path);
            }
            return ImageIO.read(stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

    public static InputStream loadResourceAsStream(String path) {
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            throw new RuntimeException("Resource not found: " + path);
        }
        return stream;
    }

    public static void playSound(String soundFile) {
        try (InputStream audioSrc = ResourceLoader.class.getResourceAsStream("/" + soundFile);
             InputStream bufferedIn = new BufferedInputStream(audioSrc)) {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
