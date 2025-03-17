package test;
import static org.junit.jupiter.api.Assertions.*;

import domain.Player;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import domain.enchantments.Reveal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.EnchantmentFeatures;
import ui.FeaturePanel;
import ui.GameMode;

import javax.swing.*;
import java.util.ArrayList;


public class RevealRuneTest {


    private GameMode gameMode;
    private Player player;
    private Reveal reveal;

    @BeforeEach
    public void setUp() {
        player = new Player();
        reveal = new Reveal();
        HallOfAir hallOfAir = new HallOfAir(player);
        HallOfEarth hallOfEarth = new HallOfEarth(player);
        HallOfWater hallOfWater = new HallOfWater(player);
        HallOfFire hallOfFire = new HallOfFire(player);

        player.addEnchantment(reveal);
        gameMode = new GameMode(player, hallOfEarth, hallOfAir, hallOfWater, hallOfFire);
        gameMode.drawingPanel = new JPanel();
        gameMode.setFeaturePanel(new FeaturePanel());
        gameMode.timers = new ArrayList<>();


    }

    /**
     * Test: Checks if the hint rectangle is correctly created and the enchantment is removed
     * when the reveal enchantment is available and active.
     */
    @Test
    public void testRevealRuneHintWithAvailableEnchantment() throws InterruptedException {
        // Preconditions
        assertTrue(reveal.isAvailable(), "Reveal enchantment should be available.");
        assertNull(gameMode.hintRectangle, "Hint rectangle should initially be null.");

        EnchantmentFeatures.revealRuneHint(gameMode,reveal,gameMode.getRuneLabel(),gameMode.getHintRectangle(),gameMode.getDrawingPanel());
        // Postconditions
        assertNotNull(gameMode.hintRectangle, "Hint rectangle should be created.");
        Thread.sleep(11000);
        assertFalse(reveal.isAvailable(), "Reveal enchantment should be deactivated.");
    }

    /**
     * Test: Ensures the method does nothing if the reveal enchantment is unavailable.
     */
    @Test
    public void testRevealRuneHintWithoutAvailableEnchantment() {
        // Make reveal unavailable
        reveal.deactivate();

        // Preconditions
        assertFalse(reveal.isAvailable(), "Reveal enchantment should not be available.");
        assertNull(gameMode.hintRectangle, "Hint rectangle should initially be null.");

        EnchantmentFeatures.revealRuneHint(gameMode,reveal,gameMode.getRuneLabel(),gameMode.getHintRectangle(),gameMode.getDrawingPanel());

        // Postconditions
        assertNull(gameMode.hintRectangle, "Hint rectangle should not be created.");
        assertTrue(gameMode.timers.isEmpty(), "No timer should be added.");
    }

    /**
     * Test: Ensures the method does nothing if the player's inventory doesn't contain the reveal enchantment.
     */
    @Test
    public void testRevealRuneHintWithoutEnchantmentInInventory() {
        // Remove reveal from inventory
        player.removeEnchantment(reveal);

        // Preconditions
        assertNull(gameMode.hintRectangle, "Hint rectangle should initially be null.");
        assertTrue(gameMode.timers.isEmpty(), "No timer should be added.");

        EnchantmentFeatures.revealRuneHint(gameMode,reveal,gameMode.getRuneLabel(),gameMode.getHintRectangle(),gameMode.getDrawingPanel());

        // Postconditions
        assertNull(gameMode.hintRectangle, "Hint rectangle should not be created.");
        assertTrue(gameMode.timers.isEmpty(), "No timer should be added.");
    }
}