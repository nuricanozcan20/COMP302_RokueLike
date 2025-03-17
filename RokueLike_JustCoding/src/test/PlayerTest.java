package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.Player;
import domain.enchantments.Enchantment;
import domain.enchantments.Reveal;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Movements;
import ui.EnchantmentFeatures;
import ui.GameMode;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class PlayerTest {

    private Player player;
    private GameMode gameMode;

    @BeforeEach
    public void setUp() {
        player = new Player();
        gameMode = new GameMode(player, new HallOfEarth(player),new HallOfAir(player),new HallOfWater(player),new HallOfFire(player));
    }

    @Test
    public void testInitialPosition() {
        assertTrue(player.repOk(), "Initial position should be within bounds.");
        gameMode.switchToNextHall();
        assertTrue(player.repOk(), "Initial position should be within bounds.");
    }

    @Test
    public void testMoveUp() {
        player.setY(player.getMinY()); // Ensure the player can move up
        player.moveUp();
        assertTrue(player.repOk(), "Player's position should be within bounds after moving up.");
    }

    @Test
    public void testMoveRight() {
        player.setX(player.getMaxX()); // Ensure the player can move up
        player.moveRight();
        assertTrue(player.repOk(), "Player's position should be within bounds after moving right.");
    }

    @Test
    public void testAddEnchantment() {
        for(int i = 0; i < 6; i++) {
            Enchantment enchantment = new Reveal();
            assertTrue(player.addEnchantment(enchantment), "Enchantment should be added successfully.");
        }
        EnchantmentFeatures.collectLuringGem(gameMode);
        assertTrue(player.repOk(), "Inventory should be valid after adding an enchantment.");
    }

    @Test
    public void testDecreaseHealth() {
        player.setHealth(1);
        player.decreaseHealth();
        assertTrue(player.repOk(), "Health should be non-negative after decreasing.");
    }
    @Test
    public void testPlayerMovementInGame(){
        Set<Integer> pressedKeys = new HashSet<>();
        List<Rectangle> objectBounds = List.of(new Rectangle(100, 100, 50, 50)); // Example object bounds
        pressedKeys.add(KeyEvent.VK_UP);
        player.setY(player.getMinY());
        player.setX(player.getMinX());
        Movements.playerMovement(pressedKeys,gameMode,objectBounds);
        assertTrue(player.repOk(),"Player's position should be within bounds after moving up.");
        pressedKeys.add(KeyEvent.VK_LEFT);
        Movements.playerMovement(pressedKeys,gameMode,objectBounds);
        assertTrue(player.repOk(),"Player's position should be within bounds after moving up.");

    }
    @Test
    public void testPlayerLevelIncrease(){
        player.setLevel(4);
        gameMode.switchToNextHall();
        assertTrue(player.repOk(),"Player's level should be increased by 1 but it must not be greater than 4.");
    }

    @Test
    public void testPlayerInventory(){
        for(int i = 0; i < 6; i++) {
            Enchantment enchantment = new Reveal();
            assertTrue(player.addEnchantment(enchantment), "Enchantment should be added successfully.");
        }
        gameMode.switchToNextHall();
        EnchantmentFeatures.collectLuringGem(gameMode);
        assertTrue(player.repOk(),"Player's inventory must be recovered when player passes to next hall.");
    }
}