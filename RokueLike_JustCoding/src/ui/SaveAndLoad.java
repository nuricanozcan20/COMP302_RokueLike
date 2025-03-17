package ui;

import domain.Player;
import domain.monsters.ArcherMonster;
import domain.monsters.FighterMonster;
import domain.monsters.Monster;
import domain.halls.Hall;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import domain.monsters.WizardMonster;
import persistence.SaveGameAdapter;

import javax.swing.*;
import java.util.List;

public class SaveAndLoad {

    public static void saveGame(SaveGameAdapter saveGameAdapter,String fileName, Player player, List<Hall> halls, List<Monster> monsters) {
        String filePath = "RokueLike_JustCoding/src/savedGames/" + fileName;
        saveGameAdapter.saveGameState(filePath, player, halls, monsters);
    }

    public static Object[] loadGame(SaveGameAdapter saveGameAdapter,String fileName, JFrame frame) {
        Object[] gameState = saveGameAdapter.loadGameState(fileName);
        if (gameState != null) {
            Player player = (Player) gameState[0];
            List<Hall> loadedHalls = (List<Hall>) gameState[1];
            List<Monster> monsters = (List<Monster>) gameState[2];

            // Clear existing monsters from the frame
            for (Monster monster : monsters) {
                frame.getContentPane().remove(monster.getMonsterLabel());
            }

            // Re-add monsters to the frame and set their positions
            for (Monster monster : monsters) {
                JLabel monsterLabel = monster.getMonsterLabel();
                frame.getContentPane().add(monsterLabel);
                frame.getContentPane().setComponentZOrder(monsterLabel, 1);
                monsterLabel.setBounds(monsterLabel.getX(), monsterLabel.getY(), monsterLabel.getWidth(), monsterLabel.getHeight());
            }

            // Refresh the frame
            frame.revalidate();
            frame.repaint();

            return new Object[]{player, loadedHalls, monsters};
        } else {
            System.out.println("Failed to load game.");
            return null;
        }
    }

    public static void loadMonsters(List<Monster> monsters, GameMode gameMode, JFrame frame) {
        // Clear existing monsters from the frame
        for (Monster monster : monsters) {
            frame.getContentPane().remove(monster.getMonsterLabel());
        }

        // Re-add monsters to the frame and set their positions
        for (Monster monster : monsters) {
            JLabel monsterLabel = monster.getMonsterLabel();
            frame.getContentPane().add(monsterLabel);
            frame.getContentPane().setComponentZOrder(monsterLabel, 1);
            monsterLabel.setBounds(monsterLabel.getX(), monsterLabel.getY(), monsterLabel.getWidth(), monsterLabel.getHeight());

            // Start timers for specific monster types
            if (monster instanceof ArcherMonster) {
                gameMode.archerMechanism((ArcherMonster) monster);
                ArcherMonster.archerCount++;
            } else if (monster instanceof WizardMonster) {
                gameMode.wizardMechanism((WizardMonster) monster);
            }else{
                FighterMonster.fighterCount++;
            }

        }

        // Refresh the frame
        frame.revalidate();
        frame.repaint();
    }

    public static void refreshFeaturePanel(GameMode gameMode){
        FeaturePanel featurePanel = GameModeFeatures.refreshFeaturePanel(gameMode.getFeaturePanel(), gameMode.getPlayer(), gameMode);
    }


}