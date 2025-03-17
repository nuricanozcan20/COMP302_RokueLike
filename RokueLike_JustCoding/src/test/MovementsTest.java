package test;

import domain.Player;
import domain.halls.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physics.Movements;
import ui.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementsTest {
    private GameMode gameMode;
    private Player player;
    private JLabel playerLabel;
    private Set<Integer> pressedKeys;
    private List<Rectangle> objectBounds;

    @BeforeEach
    public void setUp() {
        player = new Player();
        playerLabel = new JLabel();
        playerLabel.setSize(50, 50);
        gameMode = new GameMode(player,new HallOfEarth(player),new HallOfAir(player),new HallOfWater(player),new HallOfFire(player));
        gameMode.setPlayer(player);
        gameMode.setPlayerLabel(playerLabel);
        pressedKeys = new HashSet<>();
        objectBounds = List.of(new Rectangle(100, 100, 50, 50)); // Example object bounds
    }

    @Test
    public void testPlayerMoveUp() {
        pressedKeys.add(KeyEvent.VK_UP);
        Movements.playerMovement(pressedKeys, gameMode, objectBounds);
        assertEquals(player.getY(), player.getInitialY()-2);
    }

    @Test
    public void testPlayerMoveDown() {
        pressedKeys.add(KeyEvent.VK_DOWN);
        Movements.playerMovement(pressedKeys, gameMode, objectBounds);
        assertEquals(player.getY(), player.getInitialY()+2);
    }

    @Test
    public void testPlayerMoveLeft() {
        pressedKeys.add(KeyEvent.VK_LEFT);
        Movements.playerMovement(pressedKeys, gameMode, objectBounds);
        assertEquals(player.getX(), player.getInitialX()-2);
    }

    @Test
    public void testPlayerMoveRight() {
        pressedKeys.add(KeyEvent.VK_RIGHT);
        Movements.playerMovement(pressedKeys, gameMode, objectBounds);
        assertEquals(player.getX(), player.getInitialX()+2);
    }

    @Test
    public void testPlayerCollision() {
        player.setX(100);
        player.setY(100);
        pressedKeys.add(KeyEvent.VK_RIGHT);
        Movements.playerMovement(pressedKeys, gameMode, objectBounds);
        assertEquals(player.getX(), 100); // Player should not move due to collision
    }
}