package ui;

import domain.Player;
import domain.monsters.FighterMonster;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FighterAttack {
    private FighterMonster fighter;
    private Timer attackTimer;
    private GameMode gameMode;
    private boolean canAttack;

    public FighterAttack(GameMode gameMode, FighterMonster fighter) {
        this.fighter = fighter;
        this.gameMode = gameMode;
        this.canAttack = true;
        startAttackTimer();
    }

    private void startAttackTimer() {
        attackTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlayerCloseEnough() && canAttack) {
                    gameMode.getPlayer().decreaseHealth(); // Decrease player's health
                    ResourceLoader.playSound("assets/sounds/fighter_attack.wav");
                    ResourceLoader.playSound("assets/sounds/player_hit.wav");
                    gameMode.updateFeatures(); // Update the game features
                    GameModeOverlays.showDamageOverlay(gameMode); // Show the damage overlay
                    canAttack = false; // Disable further attacks
                    resetAttackFlag(); // Reset the attack flag after 3 seconds
                }
            }
        });
        attackTimer.start();
    }

    private boolean isPlayerCloseEnough() {
        int distance = 50;
        int fighterX = fighter.getX();
        int fighterY = fighter.getY();
        int playerX = gameMode.getPlayer().getX();
        int playerY = gameMode.getPlayer().getY();

        return Math.abs(fighterX - playerX) <= distance && Math.abs(fighterY - playerY) <= distance;
    }

    private void resetAttackFlag() {
        Timer resetTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canAttack = true; // Re-enable attacks
            }
        });
        resetTimer.setRepeats(false); // Only execute once
        resetTimer.start();
    }

    public Timer getAttackTimer() {
        return attackTimer;
    }
}