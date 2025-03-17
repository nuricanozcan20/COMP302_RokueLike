package test;

import domain.Player;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.EnchantmentCreator;
import ui.GameMode;
import ui.GameModeImageFactory;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateRandomEnchantmentTest {

    private GameMode gameMode;
    private JLabel extraLifeLabel;
    private JLabel cloakLabel;
    private JLabel revealLabel;
    private JLabel luringGemLabel;

    @BeforeEach
    public void setUp() {
        Player player = new Player();
        HallOfEarth hallOfEarth = new HallOfEarth(player);
        HallOfAir hallOfAir = new HallOfAir(player);
        HallOfWater hallOfWater = new HallOfWater(player);
        HallOfFire hallOfFire = new HallOfFire(player);
        gameMode = new GameMode(player, hallOfEarth, hallOfAir, hallOfWater, hallOfFire);

        // Initialize enchantment labels for verification
        extraLifeLabel = new JLabel();
        cloakLabel = new JLabel();
        revealLabel = new JLabel();
        luringGemLabel = new JLabel();

        gameMode.setExtraLifeLabel(extraLifeLabel);
        gameMode.setExtraLifeLabel(cloakLabel);
        gameMode.setExtraLifeLabel(revealLabel);
        gameMode.setExtraLifeLabel(luringGemLabel);
    }

    /**
     * Test 1: Verify if an enchantment is generated and visible after the timer executes once.
     */
    @Test
    public void testGenerateRandomEnchantmentOnce() throws InterruptedException {
        EnchantmentCreator.generateRandomEnchantment(gameMode,new GameModeImageFactory());

        // Allow some time for the timer to trigger the enchantment generation
        Thread.sleep(2500);

        // Check if at least one of the enchantments is visible
        boolean isEnchantmentVisible =
                (extraLifeLabel.isVisible() || cloakLabel.isVisible() || revealLabel.isVisible() || luringGemLabel.isVisible());

        assertTrue(isEnchantmentVisible, "At least one enchantment should be visible after the timer executes.");
    }

    /**
     * Test 2: Verify if multiple enchantments are generated after a longer duration.
     */
    @Test
    public void testGenerateMultipleEnchantments() throws InterruptedException {
        EnchantmentCreator.generateRandomEnchantment(gameMode,new GameModeImageFactory());

        // Allow enough time for the timer to execute multiple times
        Thread.sleep(7000);

        // Check if more than one enchantment is visible
        int visibleEnchantments = 0;
        if (extraLifeLabel.isVisible()) visibleEnchantments++;
        if (cloakLabel.isVisible()) visibleEnchantments++;
        if (revealLabel.isVisible()) visibleEnchantments++;
        if (luringGemLabel.isVisible()) visibleEnchantments++;

        assertTrue(visibleEnchantments > 1, "More than one enchantment should be visible after the timer executes multiple times.");
    }

    /**
     * Test 3: Verify that each type of enchantment is generated at least once.
     */
    @Test
    public void testAllEnchantmentsGenerated() throws InterruptedException {
        EnchantmentCreator.generateRandomEnchantment(gameMode,new GameModeImageFactory());

        boolean extraLifeGenerated = false;
        boolean cloakGenerated = false;
        boolean revealGenerated = false;
        boolean luringGemGenerated = false;

        // Wait and check over multiple intervals to ensure all enchantments are generated
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2500); // Wait for the timer to trigger enchantment generation

            if (extraLifeLabel.isVisible()) extraLifeGenerated = true;
            if (cloakLabel.isVisible()) cloakGenerated = true;
            if (revealLabel.isVisible()) revealGenerated = true;
            if (luringGemLabel.isVisible()) luringGemGenerated = true;

            // Break early if all enchantments have been generated
            if (extraLifeGenerated && cloakGenerated && revealGenerated && luringGemGenerated) {
                break;
            }
        }

        assertTrue(extraLifeGenerated, "ExtraLife enchantment should be generated at least once.");
        assertTrue(cloakGenerated, "Cloak enchantment should be generated at least once.");
        assertTrue(revealGenerated, "Reveal enchantment should be generated at least once.");
        assertTrue(luringGemGenerated, "LuringGem enchantment should be generated at least once.");
    }

}
