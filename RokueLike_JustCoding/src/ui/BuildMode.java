// BuildMode.java
package ui;

import javax.swing.*;

import domain.Player;
import domain.halls.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BuildMode {
    JFrame frame;
    private Player player;
    private HallOfAir hallOfAir;
    private HallOfFire hallOfFire;
    private HallOfWater hallOfWater;
    private HallOfEarth hallOfEarth;

    public BuildMode(Player player, Hall hall, List<Hall> otherHalls) {
        this.player = player;
        initialize(hall, otherHalls);
    }

    public BuildMode(Player player) {
        this.player = player;
        initialize();
    }

    private void initialize() {
        frame = MainMenu.createFrame();
        frame.setResizable(false);
        BufferedImage background = BackgroundCreator.createBackground();
        ImageIcon backgroundIcon = new ImageIcon(background);
        JLabel back = new JLabel(backgroundIcon);
        back.setBounds(0, 0, 1080, 720);

        frame.getContentPane().add(back);

        // Add the build mode features
        FeaturePanel featurePanel = new FeaturePanel();
        featurePanel.setOpaque(false); // Make the panel transparent
        featurePanel.setBounds(0, 0, 1080, 720);
        featurePanel.setLayout(null); // Set layout to null
        frame.getContentPane().add(featurePanel);
        frame.getContentPane().setComponentZOrder(featurePanel, 1); // Ensure featurePanel is on top
        JButton exitButton =featurePanel.addExitButton(featurePanel);
        JButton continueButton = featurePanel.addContinueButton(featurePanel);


        exitButton.addActionListener(e -> {
            frame.dispose();
            new MainMenu(player);
        });

        continueButton.addActionListener(e -> {
            if (allRequirementsMet()) {
                frame.dispose();
                new GameMode(new Player(), hallOfEarth, hallOfAir, hallOfWater, hallOfFire); // Open game mode with HallOfEarth first
            } else {
                JOptionPane.showMessageDialog(frame, "Each hall must have at required objects placed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        BuildModeFeatures.addInventoryImage(featurePanel);
        BuildModeFeatures.addHallLabels(featurePanel);

        // Calculate dimensions to use 3/4 of the screen
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int hallSize = Math.min(frameWidth, frameHeight) * 5 / 6 / 2;

        // Create and add HallOfAir
        hallOfAir = new HallOfAir(player);
        hallOfAir.getUIController().getHallPanel().setBounds(50, 20, hallSize, hallSize);
        addHallPanelClickListener(hallOfAir.getUIController().getHallPanel(), hallOfAir);

        // Create and add HallOfFire
        hallOfFire = new HallOfFire(player);
        hallOfFire.getUIController().getHallPanel().setBounds(50 + hallSize + 50, 20, hallSize, hallSize);
        addHallPanelClickListener(hallOfFire.getUIController().getHallPanel(), hallOfFire);

        // Create and add HallOfWater
        hallOfWater = new HallOfWater(player);
        hallOfWater.getUIController().getHallPanel().setBounds(50, 20 + hallSize + 50, hallSize, hallSize);
        addHallPanelClickListener(hallOfWater.getUIController().getHallPanel(), hallOfWater);

        // Create and add HallOfEarth
        hallOfEarth = new HallOfEarth(player);
        hallOfEarth.getUIController().getHallPanel().setBounds(50 + hallSize + 50, 20 + hallSize + 50, hallSize, hallSize);
        addHallPanelClickListener(hallOfEarth.getUIController().getHallPanel(), hallOfEarth);

        // Add hall panels to the frame
        frame.getContentPane().add(hallOfAir.getUIController().getHallPanel());
        frame.getContentPane().add(hallOfFire.getUIController().getHallPanel());
        frame.getContentPane().add(hallOfWater.getUIController().getHallPanel());
        frame.getContentPane().add(hallOfEarth.getUIController().getHallPanel());

        // Ensure the background is added last so it doesn't cover other components
        frame.getContentPane().setComponentZOrder(back, frame.getContentPane().getComponentCount() - 1);

        frame.setVisible(true);
    }

    private void initialize(Hall hall, List<Hall> otherHalls) {
        frame = MainMenu.createFrame();
        BufferedImage background = BackgroundCreator.createBackground();
        ImageIcon backgroundIcon = new ImageIcon(background);
        JLabel back = new JLabel(backgroundIcon);
        back.setBounds(0, 0, 1080, 720);

        frame.getContentPane().add(back);

        // Add the build mode features
        FeaturePanel featurePanel = new FeaturePanel();
        featurePanel.setOpaque(false); // Make the panel transparent
        featurePanel.setBounds(0, 0, 1080, 720);
        featurePanel.setLayout(null); // Set layout to null
        frame.getContentPane().add(featurePanel);
        frame.getContentPane().setComponentZOrder(featurePanel, 0); // Ensure featurePanel is on top
        JButton exitButton = featurePanel.addExitButton(featurePanel);
        JButton continueButton = featurePanel.addContinueButton(featurePanel);
        JButton saveButton = featurePanel.addSaveButton(featurePanel);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MainMenu(player);
        });

        continueButton.addActionListener(e -> {
            if (allRequirementsMet()) {
                frame.dispose();
                new GameMode(new Player(), hallOfEarth, hallOfAir, hallOfWater, hallOfFire); // Open game mode with HallOfEarth first
            } else {
                JOptionPane.showMessageDialog(frame, "Each hall must meet requirements.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        BuildModeFeatures.addInventoryImage(featurePanel);
        BuildModeFeatures.addHallLabels(featurePanel);

        // Calculate dimensions to use 3/4 of the screen
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int hallSize = Math.min(frameWidth, frameHeight) * 5 / 6 / 2;
        if (hall instanceof HallOfAir) {
            this.hallOfAir = (HallOfAir) hall;
        } else if (hall instanceof HallOfFire) {
            this.hallOfFire = (HallOfFire) hall;
        } else if (hall instanceof HallOfWater) {
            this.hallOfWater = (HallOfWater) hall;
        } else if (hall instanceof HallOfEarth) {
            this.hallOfEarth = (HallOfEarth) hall;
        }
        for (Hall h : otherHalls) {
            if (h instanceof HallOfAir) {
                this.hallOfAir = (HallOfAir) h;
            } else if (h instanceof HallOfFire) {
                this.hallOfFire = (HallOfFire) h;
            } else if (h instanceof HallOfWater) {
                this.hallOfWater = (HallOfWater) h;
            } else if (h instanceof HallOfEarth) {
                this.hallOfEarth = (HallOfEarth) h;
            }
        }
        hallOfAir.getUIController().getHallPanel().setBounds(50, 20, hallSize, hallSize);
        addHallPanelClickListener(hallOfAir.getUIController().getHallPanel(), hallOfAir);

        hallOfFire.getUIController().getHallPanel().setBounds(50 + hallSize + 50, 20, hallSize, hallSize);
        addHallPanelClickListener(hallOfFire.getUIController().getHallPanel(), hallOfFire);

        hallOfWater.getUIController().getHallPanel().setBounds(50, 20 + hallSize + 50, hallSize, hallSize);
        addHallPanelClickListener(hallOfWater.getUIController().getHallPanel(), hallOfWater);

        hallOfEarth.getUIController().getHallPanel().setBounds(50 + hallSize + 50, 20 + hallSize + 50, hallSize, hallSize);
        addHallPanelClickListener(hallOfEarth.getUIController().getHallPanel(), hallOfEarth);

        // Add hall panels to the frame
        frame.getContentPane().add(hallOfAir.getUIController().getHallPanel());
        frame.getContentPane().add(hallOfFire.getUIController().getHallPanel());
        frame.getContentPane().add(hallOfWater.getUIController().getHallPanel());
        frame.getContentPane().add(hallOfEarth.getUIController().getHallPanel());

        // Ensure the background is added last so it doesn't cover other components
        frame.getContentPane().setComponentZOrder(back, frame.getContentPane().getComponentCount() - 1);

        frame.setVisible(true);
    }

    private void addHallPanelClickListener(JPanel hallPanel, Hall hall) {
        for (MouseListener listener : hallPanel.getMouseListeners()) {
            hallPanel.removeMouseListener(listener);
        }
        hallPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                frame.dispose();
                List<Hall> otherHalls = new ArrayList<>();
                if (hall instanceof HallOfAir) {
                    otherHalls.add(hallOfFire);
                    otherHalls.add(hallOfWater);
                    otherHalls.add(hallOfEarth);
                } else if (hall instanceof HallOfFire) {
                    otherHalls.add(hallOfAir);
                    otherHalls.add(hallOfWater);
                    otherHalls.add(hallOfEarth);
                } else if (hall instanceof HallOfWater) {
                    otherHalls.add(hallOfAir);
                    otherHalls.add(hallOfFire);
                    otherHalls.add(hallOfEarth);
                } else if (hall instanceof HallOfEarth) {
                    otherHalls.add(hallOfAir);
                    otherHalls.add(hallOfFire);
                    otherHalls.add(hallOfWater);
                }
                System.out.println("The hall is created");
                new HallEditor(player, hall, otherHalls);
            }
        });
    }

    private boolean allRequirementsMet() {
        return true; //to make changes easier
        //return hallOfAir.isBuildModeSatisfied() && hallOfEarth.isBuildModeSatisfied() && hallOfFire.isBuildModeSatisfied() && hallOfWater.isBuildModeSatisfied();

    }
}