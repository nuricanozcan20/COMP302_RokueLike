package test;

import domain.Player;
import domain.halls.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameMode;
import ui.HallUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeleportRuneTest {

    private GameMode gameMode;
    private JLabel runeLabel;
    private List<JLabel> savedLabels;
    private Hall hall;

    @BeforeEach
    void setUp() {
        // Create a real Player instance
        Player player = new Player();
        player.setLevel(1); // Set initial level to 1

        // Create and configure the rune label
        runeLabel = new JLabel();
        runeLabel.setBounds(300, 400, 700, 640); // Set dummy position and size

        // Create and configure saved labels
        savedLabels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel();
            label.setBounds(100 + i * 50, 200 + i * 50, 20, 20); // Set unique positions for labels
            savedLabels.add(label);
        }

        // Mock a Hall and UIController to provide saved labels
        hall = new HallOfEarth(player);
        HallUI uiController = new HallUI(hall) {
            @Override
            public List<JLabel> getSavedLabels() {
                return savedLabels; // Return mocked saved labels
            }
        };
        hall.setUIController(uiController);

        // Initialize the GameMode instance
        gameMode = new GameMode(player, new HallOfEarth(player), new HallOfAir(player), new HallOfWater(player), new HallOfFire(player));
        gameMode.getFrame().add(runeLabel); // Add rune label to the frame
        gameMode.getFrame().revalidate();
        gameMode.getFrame().repaint();
    }

    @Test
    void testTeleportRune_NormalCase() {
        // Debugging: Check savedLabels before test
        System.out.println("Saved Labels:");
        for (JLabel label : savedLabels) {
            System.out.println("Label at: (" + label.getX() + ", " + label.getY() + ")");
        }

        // Act: Call the teleportRune method
        gameMode.teleportRune();

        // Debugging: Check runeLabel position after teleportation
        System.out.println("Rune Label Position: (" + runeLabel.getX() + ", " + runeLabel.getY() + ")");

        // Assert: Verify the rune's new position matches one of the saved label positions
        boolean isPositionValid = savedLabels.stream()
                .anyMatch(label -> runeLabel.getX() == label.getX() && runeLabel.getY() == label.getY());

        assertTrue(isPositionValid, "Rune should be teleported to one of the saved label positions.");
    }


    @Test
    void testTeleportRune_NoSavedLabels() {
        // **Test Purpose:** Verify that the method behaves correctly when there are no saved labels.
        // Arrange: Clear the saved labels list
        savedLabels.clear();

        // Act: Call the teleportRune method
        gameMode.teleportRune();

        // Assert: Verify that the rune's position does not change
        assertEquals(300, runeLabel.getX(), "Rune's X position should remain unchanged.");
        assertEquals(400, runeLabel.getY(), "Rune's Y position should remain unchanged.");
    }

    @Test
    void testTeleportRune_NullHallUIController() {
        // **Test Purpose:** Verify that the method handles a null UIController gracefully.
        // Arrange: Set the Hall's UIController to null
        hall.setUIController(null);

        // Act & Assert: Call the method and expect no exceptions
        assertDoesNotThrow(gameMode::teleportRune, "Method should handle a null UIController without throwing exceptions.");
    }
}
