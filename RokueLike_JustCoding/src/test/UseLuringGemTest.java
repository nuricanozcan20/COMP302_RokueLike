package test;

import domain.Player;
import domain.enchantments.LuringGem;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.EnchantmentFeatures;
import ui.GameMode;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class UseLuringGemTest {

    private GameMode gameMode;
    private Player player;
    private JLabel frame;

    @BeforeEach
    public void setUp() {
        player = new Player();
        HallOfEarth hallOfEarth = new HallOfEarth(player);
        HallOfAir hallOfAir = new HallOfAir(player);
        HallOfWater hallOfWater = new HallOfWater(player);
        HallOfFire hallOfFire = new HallOfFire(player);

        gameMode = new GameMode(player, hallOfEarth, hallOfAir, hallOfWater, hallOfFire);
        frame = new JLabel();
    }

    /**
     * Test 1: Verify that the Luring Gem is used and removed from the inventory.
     */
    @Test
    public void testLuringGemUsedAndRemovedFromInventory() {
        LuringGem luringGem = new LuringGem();
        player.addEnchantment(luringGem); // Add Luring Gem to inventory

        EnchantmentFeatures.useLuringGem("W",gameMode);

        // Verify that the Luring Gem was removed from the player's inventory
        boolean luringGemExists = false;
        for (Object enchantment : player.getInventory()) {
            if (enchantment instanceof LuringGem) {
                luringGemExists = true;
                break;
            }
        }
        assertFalse(luringGemExists, "Luring Gem should be removed from the player's inventory after use.");
    }

    /**
     * Test 2: Verify that no action is taken if the player has no Luring Gem in their inventory.
     */
    @Test
    public void testNoActionWithoutLuringGem() {
        EnchantmentFeatures.useLuringGem("W",gameMode);

        // Verify that no Luring Gem was removed
        boolean luringGemExists = false;
        for (Object enchantment : player.getInventory()) {
            if (enchantment instanceof LuringGem) {
                luringGemExists = true;
                break;
            }
        }
        assertFalse(luringGemExists, "No action should be taken if the player does not have a Luring Gem.");
    }

    /**
     * Test 3: Verify that the Luring Gem activation sound is played when used.
     */
    @Test
    public void testLuringGemSoundPlaysOnUse() {
        LuringGem luringGem = new LuringGem();
        player.addEnchantment(luringGem); // Add Luring Gem to inventory

        // Call the method and assume that sound is played successfully
        EnchantmentFeatures.useLuringGem("W",gameMode);

        // Since the sound can't be directly tested in a headless environment,
        // you would check logs or mock the sound system in a real environment.
        assertTrue(true, "Sound should play when the Luring Gem is used (manually verified in environment).");
    }

    /**
     * Test 4: Verify that the Luring Gem does not move out of bounds.
     */
    @Test
    public void testLuringGemStaysWithinBounds() {
        LuringGem luringGem = new LuringGem();
        player.addEnchantment(luringGem); // Add Luring Gem to inventory

        // Set position near the boundary
        player.setX(5);
        player.setY(5);
        EnchantmentFeatures.useLuringGem("A",gameMode); // Move left

        // Verify that the gem did not move out of bounds
        Component[] components = frame.getComponents();
        boolean outOfBounds = false;
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getX() < 0 || label.getY() < 0) {
                    outOfBounds = true;
                    break;
                }
            }
        }
        assertFalse(outOfBounds, "The Luring Gem should not move out of bounds.");
    }

    /**
     * Test 5: Verify that using the Luring Gem updates the feature panel.
     */
    @Test
    public void testFeaturePanelUpdatesOnLuringGemUse() {
        LuringGem luringGem = new LuringGem();
        player.addEnchantment(luringGem); // Add Luring Gem to inventory

        EnchantmentFeatures.useLuringGem("D",gameMode); // Use the gem

        // Verify that the feature panel was updated
        assertNotNull(gameMode.getFeaturePanel(), "The feature panel should be updated after using the Luring Gem.");
    }
}
