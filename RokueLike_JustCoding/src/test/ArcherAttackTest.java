package test;

import domain.Player;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import domain.monsters.ArcherMonster;
import org.junit.jupiter.api.Test;
import ui.ArcherAttack;
import ui.GameMode;

import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArcherAttackTest {

    @Test
    public void testShootArrow_NormalMovement() {
        // Arrange
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        JLabel archerLabel = new JLabel();
        JLabel playerLabel = new JLabel();
        playerLabel.setBounds(200, 200, 50, 50); // Set player's position
        frame.add(playerLabel);

        Player player = new Player();

        HallOfEarth hallOfEarth = new HallOfEarth(player);
        HallOfAir hallOfAir = new HallOfAir(player);
        HallOfWater hallOfWater = new HallOfWater(player);
        HallOfFire hallOfFire = new HallOfFire(player);

        player.setX(200);
        player.setY(200);
        GameMode game = new GameMode(player, hallOfEarth, hallOfAir, hallOfWater, hallOfFire);
        ArcherMonster archerMonster = new ArcherMonster(100, 100, archerLabel, player);

        ArcherAttack archerAttack = new ArcherAttack(archerMonster, player, frame, playerLabel, game);

        // Act
        Timer arrowTimer = archerAttack.shootArrow(archerLabel, playerLabel, frame, player, game);

        // Assert
        assertNotNull(arrowTimer, "Timer should be created for arrow movement.");
        assertTrue(arrowTimer.isRunning(), "Arrow Timer should be running.");
    }

    @Test
    public void testShootArrow_InvalidInputs() {
        // Arrange
        JFrame frame = null;
        JLabel archerLabel = new JLabel();
        JLabel playerLabel = new JLabel();
        Player player = new Player();

        HallOfEarth hallOfEarth = new HallOfEarth(player);
        HallOfAir hallOfAir = new HallOfAir(player);
        HallOfWater hallOfWater = new HallOfWater(player);
        HallOfFire hallOfFire = new HallOfFire(player);

        GameMode game = new GameMode(player, hallOfEarth, hallOfAir, hallOfWater, hallOfFire);

        ArcherMonster archerMonster = new ArcherMonster(100, 100, archerLabel, player);
        ArcherAttack archerAttack = new ArcherAttack(archerMonster, player, frame, playerLabel, game);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            archerAttack.shootArrow(archerLabel, playerLabel, frame, player, game);
        }, "Null frame should throw a NullPointerException.");
    }
}
