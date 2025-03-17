package test;

import domain.Player;
import domain.monsters.ArcherMonster;
import domain.monsters.FighterMonster;
import domain.monsters.Monster;
import domain.monsters.WizardMonster;
import org.junit.jupiter.api.Test;
import ui.MonsterCreator;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterCreatorTest {

    @Test
    public void testRandomMonsterGenerate_NormalExecution() {
        // Arrange
        JFrame frame = new JFrame();
        Player player = new Player();

        // Reset monster counts for testing
        WizardMonster.wizardCount = 0;
        ArcherMonster.archerCount = 0;
        FighterMonster.fighterCount = 0;

        // Act
        Monster monster = MonsterCreator.randomMonsterGenerate(frame, player);

        // Assert
        assertNotNull(monster, "A monster should be created when at least one type is available.");
        assertTrue(
                monster instanceof WizardMonster || monster instanceof ArcherMonster || monster instanceof FighterMonster,
                "The created monster should be one of the valid types."
        );
    }

    @Test
    public void testRandomMonsterGenerate_AllLimitsReached() {
        // Arrange
        JFrame frame = new JFrame();
        Player player = new Player();

        // Set monster counts to their respective limits
        WizardMonster.wizardCount = 1;
        ArcherMonster.archerCount = 4;
        FighterMonster.fighterCount = 4;

        // Act
        Monster monster = MonsterCreator.randomMonsterGenerate(frame, player);

        // Assert
        assertNull(monster, "No monster should be created when all monster limits are reached.");
    }

    @Test
    public void testRandomMonsterGenerate_NullFrame() {
        // Arrange
        JFrame frame = null;
        Player player = new Player();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            MonsterCreator.randomMonsterGenerate(frame, player);
        }, "A null frame should throw a NullPointerException.");
    }

    @Test
    public void testRandomMonsterGenerate_NullPlayer() {
        // Arrange
        JFrame frame = new JFrame();
        Player player = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            MonsterCreator.randomMonsterGenerate(frame, player);
        }, "A null player should throw a NullPointerException.");
    }

    @Test
    public void testRandomMonsterGenerate_SpecificMonsterLimits() {
        // Arrange
        JFrame frame = new JFrame();
        Player player = new Player();

        // Set specific monster limits
        WizardMonster.wizardCount = 1; // Limit reached
        ArcherMonster.archerCount = 0; // Available
        FighterMonster.fighterCount = 4; // Limit reached

        // Act
        Monster monster = MonsterCreator.randomMonsterGenerate(frame, player);

        // Assert
        assertNotNull(monster, "A monster should be created when at least one type is available.");
        assertTrue(
                monster instanceof ArcherMonster,
                "The created monster should be an ArcherMonster as it's the only available type."
        );
    }
}
