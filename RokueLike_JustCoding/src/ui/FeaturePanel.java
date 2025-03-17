package ui;

import utils.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FeaturePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(128, 128, 128, 128));
        int rectWidth = 200;
        int rectHeight = 640;
        int x = getWidth() - rectWidth - 100;
        int y = (getHeight() - rectHeight) / 2 - 10;
        int arcWidth = 50; // Width of the arc at the corners
        int arcHeight = 50; // Height of the arc at the corners
        g2d.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1080, 720); // Set the preferred size of the panel
    }

    public JButton addExitButton(FeaturePanel panel) {
        ImageIcon exitIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/exit_btn.png"));
        JButton exitButton = new JButton(exitIcon);
        exitButton.setContentAreaFilled(false); // Make the button transparent
        exitButton.setBorderPainted(false); // Remove the border
        exitButton.setFocusable(false); // Make the button non-focusable
        exitButton.setBounds(790, 100, exitIcon.getIconWidth(), exitIcon.getIconHeight());
        panel.add(exitButton);
        return exitButton;
    }

    public JButton addPauseButton(FeaturePanel panel) {
        ImageIcon pauseIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/pause_btn.png"));
        JButton pauseButton = new JButton(pauseIcon);
        pauseButton.setContentAreaFilled(false); // Make the button transparent
        pauseButton.setBorderPainted(false); // Remove the border
        pauseButton.setFocusable(false); // Make the button non-focusable
        pauseButton.setBounds(825, 100, pauseIcon.getIconWidth(), pauseIcon.getIconHeight());
        panel.add(pauseButton);
        return pauseButton;
    }

    public JButton addContinueButton(FeaturePanel panel) {
        ImageIcon continueIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/continue_btn.png"));
        JButton continueButton = new JButton(continueIcon);
        continueButton.setContentAreaFilled(false); // Make the button transparent
        continueButton.setBorderPainted(false); // Remove the border
        continueButton.setFocusable(false); // Make the button non-focusable
        continueButton.setBounds(865, 100, continueIcon.getIconWidth(), continueIcon.getIconHeight());
        panel.add(continueButton);
        return continueButton;
    }

    public JButton addSaveButton(FeaturePanel panel) {
        ImageIcon saveIcon = new ImageIcon(ResourceLoader.loadImage("assets/features/save.png"));
        JButton saveButton = new JButton(saveIcon);
        saveButton.setContentAreaFilled(false); // Make the button transparent
        saveButton.setBorderPainted(false); // Remove the border
        saveButton.setFocusable(false); // Make the button non-focusable
        saveButton.setBounds(910, 102, saveIcon.getIconWidth(), saveIcon.getIconHeight());
        panel.add(saveButton);
        return saveButton;
    }
}