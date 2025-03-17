package test;

import domain.Player;
import domain.enchantments.Enchantment;
import domain.enchantments.ExtraLife;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ui.EnchantmentFeatures;
import ui.GameMode;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectExtraLifeTest {

    private GameMode gameMode;
    private Player player;
    private JLabel extraLifeLabel;

    @BeforeEach
    void setUp() {
        // Create a real Player instance
        player = new Player();
        player.setHealth(3); // Set initial health to 3
        player.setInventory(new Enchantment[5]); // Initialize an empty inventory

        // Create and configure the extra life label
        extraLifeLabel = new JLabel();
        extraLifeLabel.setBounds(100, 100, 50, 50); // Set dummy position and size
        extraLifeLabel.setVisible(true); // Ensure the label is initially visible

        // Create mock halls for GameMode initialization
        HallOfEarth hallOfEarth = new HallOfEarth(player);
        HallOfAir hallOfAir = new HallOfAir(player);
        HallOfWater hallOfWater = new HallOfWater(player);
        HallOfFire hallOfFire = new HallOfFire(player);

        // Initialize the GameMode instance with real objects
        gameMode = new GameMode(player, hallOfEarth, hallOfAir, hallOfWater, hallOfFire);
        gameMode.setPlayer(player);

        // Set the extra life label in the GameMode instance
        gameMode.setExtraLifeLabel(extraLifeLabel);

        // Add the extra life label to the game frame
        gameMode.getFrame().add(extraLifeLabel);
        gameMode.getFrame().revalidate(); // Refresh the frame to apply changes
        gameMode.getFrame().repaint();
    }

    @Test
    void testCollectExtraLife_NormalCase() {
        // **Test Purpose:** Verify the behavior when the player collects an extra life successfully.

        // Act: Call the method to collect the extra life
        EnchantmentFeatures.collectExtraLife(gameMode);

        // Assert: Verify the expected outcomes
        // 1. The extra life label should no longer be visible
        assertFalse(extraLifeLabel.isVisible(), "Extra life label should be invisible after collection.");
        // 2. The player's health should increase
        assertEquals(4, player.getHealth(), "Player's health should increase by 1.");
    }

    @Test
    void testCollectExtraLife_LabelNotVisible() {
        // **Test Purpose:** Ensure that no changes occur if the extra life label is already invisible.
        // Arrange: Make the extra life label invisible
        extraLifeLabel.setVisible(false);

        // Act: Attempt to collect the extra life
        EnchantmentFeatures.collectExtraLife(gameMode);

        // Assert: Verify the outcomes
        // 1. The label remains invisible
        assertFalse(extraLifeLabel.isVisible(), "Extra life label should remain invisible.");
        // 2. The player's health should not change
        assertEquals(3, player.getHealth(), "Player's health should remain unchanged.");
    }

    @Test
    void testCollectExtraLife_PlayerNotInitialized() {
        // **Test Purpose:** Verify that the method handles a null player object gracefully.
        // Arrange: Set the player instance to null
        gameMode.setPlayer(null);

        // Act & Assert: Call the method and expect a NullPointerException
        Executable executable = () -> EnchantmentFeatures.collectExtraLife(gameMode);
        assertThrows(NullPointerException.class, executable, "Should throw NullPointerException if player is null.");    }
}