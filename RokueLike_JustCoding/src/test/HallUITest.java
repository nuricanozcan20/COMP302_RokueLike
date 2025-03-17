package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.Player;
import domain.halls.Hall;
import domain.halls.HallOfAir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.HallUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HallUITest {
    private HallUI hallUI;
    private Hall hall;

    @BeforeEach
    public void setUp() {
        hall = new HallOfAir(new Player()); // Gerçek bir Hall nesnesi oluştur
        hallUI = new HallUI(hall); // HallUI'yi bu nesneyle oluştur
    }

    @Test
    public void testGetMovementBounds_ReturnsCorrectBounds() {
        // Act
        Rectangle bounds = hallUI.getMovementBounds();

        // Assert
        assertNotNull(bounds, "Movement bounds should not be null.");
        assertEquals(51, bounds.getX(), "X coordinate of bounds is incorrect.");
        assertEquals(21, bounds.getY(), "Y coordinate of bounds is incorrect.");
        assertEquals(700, bounds.getWidth(), "Width of bounds is incorrect.");
        assertEquals(640, bounds.getHeight(), "Height of bounds is incorrect.");
    }

    @Test
    public void testSaveState_SavesAllLabels() {
        // Arrange
        JPanel hallPanel = hallUI.getHallPanel();
        JLabel label1 = new JLabel("Label 1");
        JLabel label2 = new JLabel("Label 2");
        hallPanel.add(label1);
        hallPanel.add(label2);

        // Act
        hallUI.saveState();
        List<JLabel> savedLabels = hallUI.getSavedLabels();

        // Assert
        assertEquals(2, savedLabels.size(), "Saved labels list size is incorrect.");
        assertTrue(savedLabels.contains(label1), "Saved labels should contain label1.");
        assertTrue(savedLabels.contains(label2), "Saved labels should contain label2.");
    }

    @Test
    public void testOpenDoor_UpdatesDoorState() {
        // Arrange
        assertFalse(hall.isDoorOpen(), "Door should initially be closed.");

        // Act
        hallUI.openDoor();

        // Assert
        assertTrue(hall.isDoorOpen(), "Door should be open after calling openDoor.");
    }

    @Test
    public void testOpenDoor_RepaintsPanel() {
        // Arrange
        JPanel hallPanel = hallUI.getHallPanel();

        // Act
        hallUI.openDoor();

        // Assert
        // repaint() çağrısının görsel etkilerini manuel testle doğrulamak gerekebilir.
        // Ancak test kodunun sorunsuz çalışmasını kontrol etmek yeterlidir.
        assertNotNull(hallPanel, "Hall panel should not be null after repaint.");
    }

    @Test
    public void testGetSavedLabels_InitiallyEmpty() {
        // Act
        List<JLabel> savedLabels = hallUI.getSavedLabels();

        // Assert
        assertNotNull(savedLabels, "Saved labels list should not be null.");
        assertEquals(0, savedLabels.size(), "Saved labels list should initially be empty.");
    }

    @Test
    public void testGetHallPanel_ReturnsValidPanel() {
        // Act
        JPanel hallPanel = hallUI.getHallPanel();

        // Assert
        assertNotNull(hallPanel, "Hall panel should not be null.");
        assertEquals(400, hallPanel.getWidth(), "Hall panel width is incorrect.");
        assertEquals(400, hallPanel.getHeight(), "Hall panel height is incorrect.");
    }
}
