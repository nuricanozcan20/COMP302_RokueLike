package ui;

import javax.swing.*;
import java.util.Random;
import java.util.List;

public class EnchantmentCreator {

    private static final Random random = new Random();

    public static Timer generateRandomEnchantment(GameMode gameMode, GameModeImageFactory imageFactory) {
        /**
         * Generates random enchantments at regular intervals (every 2000 milliseconds).
         *
         * REQUIRES:
         * - The `extraLifeLabel`, `cloakLabel`, `revealLabel`, and `luringGemLabel` must be initialized or null.
         * - The `timers` list must be initialized to track active timers.
         * - A valid GUI environment (e.g., `frame`) must be set up for adding enchantment labels.
         *
         * MODIFIES:
         * - The visibility of the enchantment labels (`extraLifeLabel`, `cloakLabel`, `revealLabel`, `luringGemLabel`).
         * - Adds new enchantment labels to the frame if they are not yet initialized or visible.
         * - Updates the `timers` list by adding a new timer for enchantment generation.
         *
         * EFFECTS:
         * - Randomly selects one of the four enchantments (`ExtraLife`, `Cloak`, `Reveal`, or `LuringGem`) to generate.
         * - Adds the corresponding label to the GUI and makes it visible if it was not already visible.
         * - Repeats this process every 2000 milliseconds.
         */
        Timer enchantmentGenerator = new Timer(2000, e -> {
                int randomEnchantment = random.nextInt(5);

                switch (randomEnchantment) {
                    case 0:
                        if (gameMode.getExtraLifeLabel() == null || !gameMode.getExtraLifeLabel().isVisible()) {
                            gameMode.setExtraLifeLabel(imageFactory.addExtraLifeImage(gameMode));
                        }
                        break;
                    case 1:
                        if (gameMode.getCloakLabel() == null || !gameMode.getCloakLabel().isVisible()) {
                            gameMode.setCloakLabel(imageFactory.addCloakImage(gameMode));
                        }
                        break;
                    case 2:
                        if (gameMode.getRevealLabel() == null || !gameMode.getRevealLabel().isVisible()) {
                            gameMode.setRevealLabel(imageFactory.addRevealImage(gameMode));
                        }
                        break;
                    case 3:
                        if (gameMode.getLuringGemLabel() == null || !gameMode.getLuringGemLabel().isVisible()) {
                            gameMode.setLuringGemLabel(imageFactory.addLuringGemImage(gameMode));
                        }
                        break;
                    case 4:
                        if (gameMode.getExtraTimeLabel() == null || !gameMode.getExtraTimeLabel().isVisible()) {
                            gameMode.setExtraTimeLabel(imageFactory.addExtraTimeImage(gameMode));
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + randomEnchantment);
                }
            });
            enchantmentGenerator.start();
            return enchantmentGenerator;
    }
}
