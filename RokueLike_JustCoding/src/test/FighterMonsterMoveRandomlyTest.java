package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import domain.Player;
import domain.monsters.FighterMonster;
import domain.monsters.Monster;
import ui.GameMode;

public class FighterMonsterMoveRandomlyTest {

    private FighterMonster fighterMonster;
    private Player player;
    private JLabel monsterLabel;
    private List<JLabel> savedLabels;
    private List<Monster> monsters;
    private GameMode gameMode;

    @BeforeEach
    public void setUp() {
        player = new Player();
        gameMode = new GameMode(player,new HallOfEarth(player),new HallOfAir(player),new HallOfWater(player),new HallOfFire(player));
        monsterLabel = new JLabel();
        fighterMonster = new FighterMonster(100, 100, monsterLabel, player);
        savedLabels = new ArrayList<>();
        monsters = new ArrayList<>();
    }
    @Test //Test to check if the monster moves if there is no collision
    public void testMoveRandomly() {
        int initialX = fighterMonster.getX();
        int initialY = fighterMonster.getY();
        fighterMonster.moveRandomly(new Rectangle(0,0,1,1), savedLabels, monsters);
        assertTrue(initialX!=fighterMonster.getX() || initialY != fighterMonster.getY()); //The monster should have moved since there is no collision
    }

    @Test void testCollisionWithPlayer(){ //Test to check if the monster does not collide with the player
        for(int i = 0; i < 100; i++){
            player.setX(i);
            player.setY(i);
            fighterMonster.setX(i+2);
            fighterMonster.setY(i+2);
            fighterMonster.moveRandomly(new Rectangle(i,i,1,1), savedLabels, monsters);
            assertFalse(fighterMonster.isCollision(gameMode.getPlayerLabel().getBounds()));
        }
    }

    @Test void testCollisionWithMonster(){ //Test to check if the monster does not collide with another monster
        for (int i = 0; i < 100; i++){
            Monster monster = new FighterMonster(i,i,new JLabel(),player);
            monsters.add(monster);
            fighterMonster.setX(i+2);
            fighterMonster.setY(i+2);
            fighterMonster.moveRandomly(new Rectangle(i,i,1,1), savedLabels, monsters);
            assertFalse(fighterMonster.isCollision(monster.getMonsterLabel().getBounds()));
        }
    }

    @Test
    void testGoingOutOfBounds(){ //Test to check if the monster does not move if it goes out of bounds
        fighterMonster.setX(700);
        fighterMonster.setY(640);
        fighterMonster.moveRandomly(new Rectangle(0,0,1,1), savedLabels, monsters);
        assertTrue(fighterMonster.getX() <= 700);
        assertTrue(fighterMonster.getY() <= 640);
    }

    @Test
    void testGoingOutOfBounds2(){
        fighterMonster.setX(50);
        fighterMonster.setY(20);
        fighterMonster.moveRandomly(new Rectangle(0,0,1,1), savedLabels, monsters);
        assertTrue(fighterMonster.getX() >= 50);
        assertTrue(fighterMonster.getY() >= 20);}

    @Test
    void testCollisionWithSavedLabels(){ //Test to check if the monster does not collide with saved labels
        for (int i = 0; i < 100; i++){
            JLabel label = new JLabel();
            label.setBounds(i,i,1,1);
            savedLabels.add(label);
            fighterMonster.setX(i+2);
            fighterMonster.setY(i+2);
            fighterMonster.moveRandomly(new Rectangle(i,i,1,1), savedLabels, monsters);
            assertFalse(fighterMonster.isCollision(label.getBounds()));
        }
    }
}
