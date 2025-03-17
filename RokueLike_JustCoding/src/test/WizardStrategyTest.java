package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.halls.*;
import domain.strategy.IndecisiveStrategy;
import domain.strategy.TeleportPlayerStrategy;
import domain.strategy.TeleportRuneStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.Player;
import domain.monsters.WizardMonster;
import domain.strategy.WizardStrategy;
import ui.GameMode;

import javax.swing.*;

public class WizardStrategyTest {

    private GameMode gameMode;
    private Player player;
    private WizardMonster wizardMonster;

    @BeforeEach
    public void setUp() {
        player = new Player();
        gameMode = new GameMode(player, new HallOfEarth(player), new HallOfAir(player), new HallOfWater(player), new HallOfFire(player));
        wizardMonster = new WizardMonster(new JLabel(), player);
    }

    @Test
    public void testTeleportRuneStrategy() {
        player.setRemainingTime(80); // Set the remaining time to 80 to trigger the TeleportRuneStrategy
        wizardMonster.determineWizardStrategy();
        assertTrue(wizardMonster.getStrategy() instanceof TeleportRuneStrategy);
    }
    @Test
    public void testTeleportPlayerStrategy() {
        player.setRemainingTime(20); // Set the remaining time to 20 to trigger the TeleportPlayerStrategy
        wizardMonster.determineWizardStrategy();
        assertTrue(wizardMonster.getStrategy() instanceof TeleportPlayerStrategy);
    }

    @Test
    public void testIndecisiveStrategy() {
        player.setRemainingTime(50); // Set the remaining time to 50 to trigger the IndecisiveStrategy
        wizardMonster.determineWizardStrategy();
        assertTrue(wizardMonster.getStrategy() instanceof IndecisiveStrategy);
    }

    @Test
    public void testDynamicStrategyChange(){
        player.setRemainingTime(80); // Set the remaining time to 80 to trigger the TeleportRuneStrategy
        wizardMonster.determineWizardStrategy();
        assertTrue(wizardMonster.getStrategy() instanceof TeleportRuneStrategy);

        player.setRemainingTime(20); // Set the remaining time to 20 to trigger the TeleportPlayerStrategy
        wizardMonster.determineWizardStrategy();
        assertTrue(wizardMonster.getStrategy() instanceof TeleportPlayerStrategy);

        player.setRemainingTime(50); // Set the remaining time to 50 to trigger the IndecisiveStrategy
        wizardMonster.determineWizardStrategy();
        assertTrue(wizardMonster.getStrategy() instanceof IndecisiveStrategy);
    }
}