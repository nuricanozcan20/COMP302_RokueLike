package ui;

//

import domain.Player;
import domain.enchantments.CloakOfProtection;
import domain.enchantments.Enchantment;
import domain.enchantments.LuringGem;
import domain.enchantments.Reveal;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameModeFeatures {
    private static JLabel remainingTimeLabel;




    public static FeaturePanel addFeaturePanel(JFrame frame, Player player, GameMode game) {
        FeaturePanel featurePanel = new FeaturePanel();
        featurePanel.setOpaque(false); // Make the panel transparent
        featurePanel.setBounds(0, 0, 1080, 720);
        featurePanel.setLayout(null); // Set layout to null
        frame.getContentPane().add(featurePanel);
        frame.getContentPane().setComponentZOrder(featurePanel, 1); // Ensure featurePanel is on top

        JButton exitButton = featurePanel.addExitButton(featurePanel);
        JButton pauseButton = featurePanel.addPauseButton(featurePanel);
        JButton continueButton = featurePanel.addContinueButton(featurePanel);
        JButton saveButton = featurePanel.addSaveButton(featurePanel);
        addInventoryImage(featurePanel, player);
        addHeartImage(featurePanel, player);
        addTimerImage(featurePanel);
        addTimerText(featurePanel, game, player);

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pauseGame();
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resumeGame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.terminateTheGame();
                game.switchToBuildMode();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pauseGame();
                String fileName = JOptionPane.showInputDialog(frame, "Enter save file name:");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    game.saveGame(fileName);
                    JOptionPane.showMessageDialog(frame, "Game saved successfully.");
                }

            }
        });


        // Request focus for the frame to ensure key events are captured
        frame.requestFocusInWindow();
        return featurePanel;
    }

    public static FeaturePanel refreshFeaturePanel(FeaturePanel panel, Player player, GameMode game) {
        panel.removeAll();
        panel.setOpaque(false); // Make the panel transparent
        panel.setBounds(0, 0, 1080, 720);
        panel.setLayout(null); // Set layout to null

        JButton exitButton = panel.addExitButton(panel);
        JButton pauseButton = panel.addPauseButton(panel);
        JButton continueButton = panel.addContinueButton(panel);
        JButton saveButton = panel.addSaveButton(panel);
        addInventoryImage(panel, player);
        addHeartImage(panel, player);
        addTimerImage(panel);
        addTimerText(panel, game, player);

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pauseGame();
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resumeGame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.switchToBuildMode();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pauseGame();
                String fileName = JOptionPane.showInputDialog(game.getFrame(), "Enter save file name:");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    game.saveGame(fileName);
                    JOptionPane.showMessageDialog(game.getFrame(), "Game saved successfully.");
                }

            }
        });

        panel.revalidate();
        panel.repaint();

        return panel;
    }

    private static void addInventoryImage(FeaturePanel panel, Player player) {
        ImageIcon inventoryIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/inventory.png"));
        Image inventoryImage = inventoryIcon.getImage();
        int newWidth = 160;
        int newHeight = 117 * 2; // According to the original image size
        Image scaledInventoryImage = inventoryImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledInventoryIcon = new ImageIcon(scaledInventoryImage);
        JLabel inventoryLabel = new JLabel(scaledInventoryIcon);
        inventoryLabel.setBounds(800, 300, newWidth, newHeight);
        panel.add(inventoryLabel);

        for (int i = 0; i < player.getInventory().length; i++) {
            if (player.getInventory()[i] != null) {
                Enchantment enchantment = player.getInventory()[i];
                if (enchantment instanceof CloakOfProtection) {
                    addCloakImage(panel, inventoryLabel, i);
                } else if (enchantment instanceof LuringGem) {
                    addGemImage(panel, inventoryLabel, i);
                } else if (enchantment instanceof Reveal) {
                    addRevealImage(panel, inventoryLabel, i);
                }
            }

        }
    }

    private static void addHeartImage(FeaturePanel panel, Player player) {
        ImageIcon heartIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/heart.png"));
        for (int i = 0; i < player.getHealth(); i++) {
            JLabel heartLabel = new JLabel(heartIcon);
            heartLabel.setBounds(780 + i * 40, 250, heartIcon.getIconWidth(), heartIcon.getIconHeight());
            panel.add(heartLabel);
        }
    }

    private static void addTimerImage(FeaturePanel panel) {
        ImageIcon timerIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/timer.png"));
        Image timerImage = timerIcon.getImage();
        int newWidth = 50; // Set the desired width
        int newHeight = 50; // Set the desired height
        Image scaledTimerImage = timerImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledTimerIcon = new ImageIcon(scaledTimerImage);
        JLabel timerLabel = new JLabel(scaledTimerIcon);
        timerLabel.setBounds(800, 150, newWidth, newHeight);
        panel.add(timerLabel);
    }

    private static void addTimerText(FeaturePanel panel, GameMode game, Player player) {
        try {
            // Load the custom font using ResourceLoader
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.loadResourceAsStream("assets/fonts/font2.ttf")).deriveFont(Font.BOLD, 16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            JLabel timeTextLabel = new JLabel("Time:");
            timeTextLabel.setBounds(860, 165, 150, 30); // Adjust the position and size as needed
            timeTextLabel.setFont(customFont); // Set the custom font
            timeTextLabel.setForeground(new Color(250, 234, 220)); // Set the text color to the custom RGB color
            panel.add(timeTextLabel);

            remainingTimeLabel = new JLabel(String.valueOf(player.getremainingTime() + " seconds")); // Placeholder for remaining time
            remainingTimeLabel.setBounds(790, 210, 200, 30); // Position below the "Time:" text
            remainingTimeLabel.setFont(customFont); // Set the custom font
            remainingTimeLabel.setForeground(new Color(250, 234, 220)); // Set the text color to the custom RGB color
            panel.add(remainingTimeLabel);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private static void addCloakImage(FeaturePanel panel, JLabel inventoryLabel, int index) {
        int vertical = 0;
        int horizontal = 0;
        if (0 <= index && index <= 2) {
            vertical = 90;
            horizontal = (index == 0) ? 35 : horizontal;
            horizontal = (index == 1) ? 65 : horizontal;
            horizontal = (index == 2) ? 95 : horizontal;
        } else if (3 <= index && index <= 5) {
            vertical = 120;
            horizontal = (index == 3) ? 35 : horizontal;
            horizontal = (index == 4) ? 65 : horizontal;
            horizontal = (index == 5) ? 95 : horizontal;
        }
        ImageIcon cloakIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/cloak.png"));
        JLabel cloakLabel = new JLabel(cloakIcon);
        cloakLabel.setBounds(inventoryLabel.getX() + horizontal, inventoryLabel.getY() + vertical, cloakIcon.getIconWidth(), cloakIcon.getIconHeight());
        panel.add(cloakLabel);
        panel.setComponentZOrder(cloakLabel, 1); // Ensure cloakLabel is on top
    }

    private static void addRevealImage(FeaturePanel panel, JLabel inventoryLabel, int index) {
        int vertical = 0;
        int horizontal = 0;
        if (0 <= index && index <= 2) {
            vertical = 90;
            horizontal = (index == 0) ? 35 : horizontal;
            horizontal = (index == 1) ? 65 : horizontal;
            horizontal = (index == 2) ? 95 : horizontal;
        } else if (3 <= index && index <= 5) {
            vertical = 120;
            horizontal = (index == 3) ? 35 : horizontal;
            horizontal = (index == 4) ? 65 : horizontal;
            horizontal = (index == 5) ? 95 : horizontal;
        }
        ImageIcon revealIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/reveal.png"));
        JLabel revealLabel = new JLabel(revealIcon);
        revealLabel.setBounds(inventoryLabel.getX() + horizontal, inventoryLabel.getY() + vertical, revealIcon.getIconWidth(), revealIcon.getIconHeight());
        panel.add(revealLabel);
        panel.setComponentZOrder(revealLabel, 1); // Ensure revealLabel is on top
    }

    private static void addGemImage(FeaturePanel panel, JLabel inventoryLabel, int index) {
        int vertical = 0;
        int horizontal = 0;
        if (0 <= index && index <= 2) {
            vertical = 90;
            horizontal = (index == 0) ? 35 : horizontal;
            horizontal = (index == 1) ? 65 : horizontal;
            horizontal = (index == 2) ? 95 : horizontal;
        } else if (3 <= index && index <= 5) {
            vertical = 120;
            horizontal = (index == 3) ? 35 : horizontal;
            horizontal = (index == 4) ? 65 : horizontal;
            horizontal = (index == 5) ? 95 : horizontal;
        }
        ImageIcon gemIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/gem.png"));
        JLabel gemLabel = new JLabel(gemIcon);
        gemLabel.setBounds(inventoryLabel.getX() + horizontal, inventoryLabel.getY() + vertical, gemIcon.getIconWidth(), gemIcon.getIconHeight());
        panel.add(gemLabel);
        panel.setComponentZOrder(gemLabel, 1); // Ensure gemLabel is on top
    }

    public static JLabel getRemainingTimeLabel() {
        return remainingTimeLabel;
    }

}