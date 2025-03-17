package ui;

import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class Intro {
    private static final int CHARACTER_SIZE = 100;
    private static final int TIMER_DELAY = 16; // milliseconds (approx. 60 FPS)
    private static final String[] CHARACTER_PATHS = {
            "assets/intro_assets/char1.png",
            "assets/intro_assets/char2.png",
            "assets/intro_assets/char3.png",
            "assets/intro_assets/char4.png"
    };
    private JLabel[] characterLabels = new JLabel[CHARACTER_PATHS.length];
    private Random random = new Random();

    public Intro() {
        initialize();
    }

    private void initialize() {
        JFrame frame = new JFrame("Intro");
        frame.setBounds(100, 100, 1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome the game");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        int continueLabelWidth = 200;
        int continueLabelHeight = 30;
        int continueLabelX = (frame.getWidth() - continueLabelWidth) / 2;
        int continueLabelY = 370; // Position it just above the button
        welcomeLabel.setBounds(continueLabelX, continueLabelY, continueLabelWidth, continueLabelHeight);
        frame.getContentPane().add(welcomeLabel);

        // Load and scale the continue button image
        String continueButtonPath = "assets/intro_assets/continue_btn.png";
        ImageIcon continueIcon = null;
        BufferedImage continueImage = ResourceLoader.loadImage(continueButtonPath);
        Image scaledContinueImage = continueImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        continueIcon = new ImageIcon(scaledContinueImage);

        JButton continueButton = new JButton(continueIcon);
        int buttonWidth = 200;
        int buttonHeight = 200;
        int x = (frame.getWidth() - buttonWidth) / 2;
        continueButton.setBounds(x, 400, buttonWidth, buttonHeight);
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                //new MainMenu();
            }
        });
        frame.getContentPane().add(continueButton);

        // Create and add character labels
        for (int i = 0; i < CHARACTER_PATHS.length; i++) {
            characterLabels[i] = createCharacterLabel(CHARACTER_PATHS[i], i);
            if (characterLabels[i] != null) {
                frame.getContentPane().add(characterLabels[i]);
            }
        }

        JLabel back = createBackgroundLabel();
        frame.getContentPane().add(back);
        frame.getContentPane().setComponentZOrder(back, frame.getContentPane().getComponentCount() - 1);


        Timer timer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveCharactersSmoothly(frame);
            }
        });
        timer.start();

        // Play background music
        playBackgroundMusic("assets.sounds/dungeon_sound.wav");
    }

    private JLabel createBackgroundLabel() {
        BufferedImage background = BackgroundCreator.createBackground();
        ImageIcon backgroundIcon = new ImageIcon(background);
        JLabel back = new JLabel(backgroundIcon);
        back.setBounds(0, 0, 1080, 720);
        return back;
    }

    private JLabel createCharacterLabel(String path, int cornerIndex) {
        BufferedImage characterImage = ResourceLoader.loadImage(path);
        ImageIcon characterIcon = new ImageIcon(characterImage.getScaledInstance(CHARACTER_SIZE, CHARACTER_SIZE, Image.SCALE_SMOOTH));
        JLabel characterLabel = new JLabel(characterIcon);

        // Initialize character positions
        int x = 250, y = 200;
        switch (cornerIndex) {
            case 0:
                x = 250;
                y = 200;
                break;
            case 1:
                x = 1080 - x;
                y = 200;
                break;
            case 2:
                x = 250;
                y = 720 - y;
                break;
            case 3:
                x = 1080 - x;
                y = 720 - y;
                break;
        }

        characterLabel.setBounds(x, y, CHARACTER_SIZE, CHARACTER_SIZE);
        return characterLabel;
    }

    private void moveCharactersSmoothly(JFrame frame) {
        for (JLabel characterLabel : characterLabels) {
            int targetX = characterLabel.getX() + random.nextInt(21) - 10; // Move by -10 to +10 pixels
            int targetY = characterLabel.getY() + random.nextInt(21) - 10; // Move by -10 to +10 pixels
            targetX = Math.max(0, Math.min(targetX, frame.getWidth() - CHARACTER_SIZE));
            targetY = Math.max(0, Math.min(targetY, frame.getHeight() - CHARACTER_SIZE));

            // Check for collision with buttons
            boolean collision = false;
            for (Component component : frame.getContentPane().getComponents()) {
                if (component instanceof JButton) {
                    Rectangle buttonBounds = component.getBounds();
                    Rectangle characterBounds = new Rectangle(targetX, targetY, CHARACTER_SIZE, CHARACTER_SIZE);
                    if (characterBounds.intersects(buttonBounds)) {
                        collision = true;
                        break;
                    }
                }
            }

            if (!collision) {
                // Smoothly interpolate to the target position
                int currentX = characterLabel.getX();
                int currentY = characterLabel.getY();
                int deltaX = (targetX - currentX) / 5;
                int deltaY = (targetY - currentY) / 5;

                characterLabel.setLocation(currentX + deltaX, currentY + deltaY);
            }
        }
        frame.repaint();
    }

    private void playBackgroundMusic(String filePath) {
        try (InputStream audioSrc = ResourceLoader.loadResourceAsStream(filePath);
             InputStream bufferedIn = new BufferedInputStream(audioSrc)) {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}