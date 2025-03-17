package test;

import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssetLoadingTest {

    @Test
    public void testLoadAsset() throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/assets/floors/floor_mud_ne.png"));
        assertNotNull(image, "The image should be loaded successfully");
    }
}