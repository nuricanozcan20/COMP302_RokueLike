package domain.monsters;

import domain.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ArcherMonster extends Monster {
    private final int range = 4; // Shooting range (4 squares)
    private boolean isActive; // Monster state
    private boolean canSeePlayer = true; // Default to true

    public static int archerCount = 0; // Count of archer monsters

    // Constructor
    public ArcherMonster(int x, int y, JLabel monsterLabel, Player player) {
        super(monsterLabel,player,monsterLabel.getX(),monsterLabel.getY());
        this.isActive = true;
    }

    // Check if the player is within the shooting range
    public boolean isPlayerWithinRange(Player player) {
        int archerX = this.getX();
        int archerY = this.getY();
        int playerX = player.getX();
        int playerY = player.getY();

        int distanceX = Math.abs(archerX - playerX);
        int distanceY = Math.abs(archerY - playerY);

        return distanceX <= 200 && distanceY <= 200; // each square is 32 pixels
    }

    // Stop the monster activity (e.g., when the hero leaves the hall)
    public void deactivate() {
        this.isActive = false;
        //attackTimer.cancel();
        System.out.println("Archer Monster deactivated.");
    }

    public boolean canSeePlayer() {
        return canSeePlayer;
    }

    public void setCanSeePlayer(boolean canSeePlayer) {
        this.canSeePlayer = canSeePlayer;
    }

    // Method to stop shooting arrows and clear existing arrows
    public void stopShooting() {
        this.isActive = false;
    }


    // Method to resume shooting arrows
    public void resumeShooting() {
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }


}