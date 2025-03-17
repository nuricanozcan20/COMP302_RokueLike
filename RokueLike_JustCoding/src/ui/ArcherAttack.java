package ui;

import domain.Player;
import domain.monsters.ArcherMonster;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ArcherAttack {
    private List<Timer> arrowTimers = new ArrayList<>();
    private final Timer attackTimer;
    private final ArcherMonster archerMonster;
    private final Player player;
    private final JFrame frame;
    private JLabel playerLabel;
    private GameMode game;

    public ArcherAttack(ArcherMonster archerMonster, Player player,JFrame frame,JLabel playerLabel,GameMode game) {
        this.archerMonster = archerMonster;
        this.player = player;
        this.frame = frame;
        this.playerLabel = playerLabel;
        this.game = game;
        // Create a Swing Timer for shooting arrows
        attackTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (archerMonster.isActive() && archerMonster.isPlayerWithinRange(player)) {
                    shootArrow(archerMonster.getMonsterLabel(),playerLabel, frame, player,game);
                }
            }
        });
        attackTimer.start();

    }

    public Timer shootArrow(JLabel archerLabel, JLabel playerLabel, JFrame frame, Player player, GameMode game) {
        /**
         * Requires:
         * - Non-null JLabel objects for both archerLabel and playerLabel.
         * - A non-null JFrame object (frame) to display the arrow movement.
         * - A non-null Player object (player) with valid x and y coordinates.
         * - A non-null GameMode object (game) to check for game state (e.g., pause).
         *
         * Modifies:
         * - The JFrame instance by adding a JLabel for the arrow.
         * - Arrow movement is updated periodically based on player's position.
         * - Player's health is decreased if the arrow intersects with the player.
         *
         * Effects:
         * - Creates a Timer to handle arrow movement.
         * - Moves the arrow towards the player's position step by step.
         * - Stops the Timer and removes the arrow if it hits the player.
         * - Updates the player's health and triggers game feature updates if the player is hit.
         */

        int startX = archerLabel.getX();
        int startY = archerLabel.getY();

        // Create a new arrow label for each shot
        BufferedImage arrowImage = ResourceLoader.loadImage("assets/monster_assets/arrow.png");
        ImageIcon arrowIcon = new ImageIcon(arrowImage);
        JLabel arrowLabel = new JLabel(arrowIcon);

        arrowLabel.setBounds(startX, startY, arrowLabel.getIcon().getIconWidth(), arrowLabel.getIcon().getIconHeight());
        frame.getContentPane().add(arrowLabel);
        frame.getContentPane().setComponentZOrder(arrowLabel, 0);
        arrowLabel.setVisible(true);

        int targetX = player.getX();
        int targetY = player.getY();
        final int stepSize = 3; // Adjust step size for smoother movement

        // Calculate the direction vector
        final double directionX = targetX - startX;
        final double directionY = targetY - startY;
        final double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);

        // Normalize direction and scale with step size
        final double stepX = (directionX / magnitude) * stepSize;
        final double stepY = (directionY / magnitude) * stepSize;

        Timer arrowTimer = new Timer(16, null);
        arrowTimer.addActionListener(new ActionListener() {
            double currentX = startX;
            double currentY = startY;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getIsPaused()) {
                    arrowTimer.stop();
                    return;
                }
                currentX += stepX;
                currentY += stepY;

                // Convert to integers to prevent "shaking"
                arrowLabel.setLocation((int) Math.round(currentX), (int) Math.round(currentY));
                frame.revalidate();
                frame.repaint();

                if (arrowLabel.getBounds().intersects(playerLabel.getBounds())) {
                    arrowTimer.stop();
                    arrowLabel.setVisible(false);
                    frame.getContentPane().remove(arrowLabel);
                    if (player.getHealth() != 0) {
                        player.decreaseHealth();
                        game.updateFeatures();
                        ResourceLoader.playSound("assets/sounds/arrow_sound.wav");
                        ResourceLoader.playSound("assets/sounds/player_hit.wav");
                        GameModeOverlays.showDamageOverlay(game);
                    }
                }
                /*
                // Stop if the arrow is close enough to the target
                if (Math.abs(currentX - targetX) < stepSize && Math.abs(currentY - targetY) < stepSize) {
                    arrowTimer.stop();
                    arrowLabel.setVisible(false);
                }
                */
            }
        });

        arrowTimer.start();
        arrowTimers.add(arrowTimer);
        return arrowTimer;
    }

    public void highlightArcher(JLabel archerLabel, boolean highlight) {
        if (highlight) {
            archerLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3)); // Bold gray border
        } else {
            archerLabel.setBorder(null); // Remove the border
        }
        archerLabel.getParent().revalidate();
        archerLabel.getParent().repaint();
    }
    public void stopShooting(){
        attackTimer.stop();
    }
    public Timer getAttackTimer(){
        return attackTimer;
    }

    public void stopTimers(){
        for (Timer timer : arrowTimers) {
            timer.stop();
        }
    }
    public void continueTimers(){
        for (Timer timer : arrowTimers) {
            timer.start();
        }
    }

}