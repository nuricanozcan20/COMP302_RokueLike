package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.monsters.ArcherMonster;
import domain.monsters.FighterMonster;
import domain.monsters.WizardMonster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import domain.Player;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import domain.monsters.Monster;
import ui.GameMode;
import ui.HallUI;
import ui.MonsterCreator;


public class PlaceMonsterTest {

    private JFrame frame;
    private Player player;
    private GameMode gameMode;

    @BeforeEach
    public void setUp() {
        frame = new JFrame();
        player = new Player();
        ArcherMonster.archerCount=0;
        WizardMonster.wizardCount=0;
        FighterMonster.fighterCount=0;
        HallOfEarth hallOfEarth = new HallOfEarth(player);
        HallOfAir hallOfAir = new HallOfAir(player);
        HallOfWater hallOfWater = new HallOfWater(player);
        HallOfFire hallOfFire = new HallOfFire(player);
        gameMode = new GameMode(player, hallOfEarth, hallOfAir, hallOfWater, hallOfFire);
    }

    @Test
    public void testPlaceRandomMonster() {
        int initialMonsterCount = gameMode.getMonsters().size();
        Monster monster = MonsterCreator.randomMonsterGenerate(frame, player);
        MonsterCreator.placeRandomMonster(gameMode,monster);
        int newMonsterCount = gameMode.getMonsters().size();
        assertEquals(initialMonsterCount + 1, newMonsterCount, "A new monster should be added.");
    }

    @Test
    public void testMonsterCreationLimit() {
        // Simulate the condition where no more monsters can be generated
        ArcherMonster.archerCount=4;
        WizardMonster.wizardCount=1;
        FighterMonster.fighterCount=4;
        int initialMonsterCount = gameMode.getMonsters().size();
        Monster monster = MonsterCreator.randomMonsterGenerate(frame, player);
        MonsterCreator.placeRandomMonster(gameMode,monster);
        int newMonsterCount = gameMode.getMonsters().size();
        assertEquals(initialMonsterCount, newMonsterCount, "No more monsters should be added.");
    }

    @Test
    public void testPlayerCollision(){
        player.setX(50);
        player.setY(50);
        Monster monster = MonsterCreator.randomMonsterGenerate(frame, player);
        monster.setX(50);
        monster.setY(50);
        MonsterCreator.placeRandomMonster(gameMode,monster);

        assertNotEquals(player.getX(), monster.getX(), "Player and monster should not have the same x-coordinate.");
        assertNotEquals(player.getY(), monster.getY(), "Player and monster should not have the same y-coordinate.");

    }

    @Test
    public void testMonsterCollision(){
        Monster monster1 = MonsterCreator.randomMonsterGenerate(frame, player);
        Monster monster2 = MonsterCreator.randomMonsterGenerate(frame, player);
        monster1.setX(50);
        monster1.setY(50);
        monster2.setX(50);
        monster2.setY(50);
        MonsterCreator.placeRandomMonster(gameMode,monster2);

        assertNotEquals(monster1.getX(), monster2.getX(), "Monsters should not have the same x-coordinate.");
        assertNotEquals(monster1.getY(), monster2.getY(), "Monsters should not have the same y-coordinate.");
    }

    @Test
    public void testMonsterObjectCollision(){
        JLabel object = new JLabel();
        object.setBounds(50, 50, 50, 50);
        HallUI hallUI = new HallUI(gameMode.getHallOfEarth());
        hallUI.getSavedLabels().add(object);
        Monster monster = MonsterCreator.randomMonsterGenerate(frame, player);
        monster.setX(50);
        monster.setY(50);
        MonsterCreator.placeRandomMonster(gameMode,monster);

        assertNotEquals(monster.getX(), object.getX(), "Monster and object should not have the same x-coordinate.");
        assertNotEquals(monster.getY(), object.getY(), "Monster and object should not have the same y-coordinate.");
    }
}