package ui;

import domain.Player;
import utils.ResourceLoader;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class HelpScreen {
    private JFrame frame;
    private Player player;

    public HelpScreen(Player player) {
        initialize();
        this.player = player;
    }

    private void initialize() {
        frame = new JFrame("Help");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(100,100,1080, 720);
        frame.setLayout(null);

        JButton exitButton = new JButton();
        Image exitButtonImage = ResourceLoader.loadImage("assets/main_menu_assets/main_menu_exit.PNG").getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon exitButtonIcon = new ImageIcon(exitButtonImage.getScaledInstance(90, 60, Image.SCALE_SMOOTH));
        exitButton.setIcon(exitButtonIcon);
        exitButton.setBounds(10, 10, 90, 60);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> {
            frame.dispose();
            MainMenu mainMenu = new MainMenu(player);
        });
        frame.getContentPane().add(exitButton);


        BufferedImage player = ResourceLoader.loadImage("assets/help_screen/player_right1.png");
        ImageIcon playerIcon = new ImageIcon(player);
        JLabel playerLabel = new JLabel(playerIcon);
        playerLabel.setBounds(50, 100, playerIcon.getIconWidth(), playerIcon.getIconHeight());
        frame.getContentPane().add(playerLabel);
        addPlayerExpressionLabels(playerIcon);


        Image rune = ResourceLoader.loadImage("assets/help_screen/rune.png").getScaledInstance(30, 31, Image.SCALE_SMOOTH);
        ImageIcon runeIcon = new ImageIcon(rune);
        JLabel runeLabel = new JLabel(runeIcon);
        runeLabel.setBounds(50, 280, runeIcon.getIconWidth(), runeIcon.getIconHeight());
        frame.getContentPane().add(runeLabel);
        addRuneExpression(runeIcon);

        Image fighter = ResourceLoader.loadImage("assets/help_screen/fighter4x.png")
                .getScaledInstance(45, 60, Image.SCALE_SMOOTH);
        ImageIcon fighterIcon = new ImageIcon(fighter);
        JLabel fighterLabel = new JLabel(fighterIcon);
        fighterLabel.setBounds(425, 100, fighterIcon.getIconWidth(), fighterIcon.getIconHeight());
        frame.getContentPane().add(fighterLabel);
        addFighterExpression(fighterIcon);

        Image archer = ResourceLoader.loadImage("assets/help_screen/archer4x.png")
                .getScaledInstance(48, 45, Image.SCALE_SMOOTH);
        ImageIcon archerIcon = new ImageIcon(archer);
        JLabel archerLabel = new JLabel(archerIcon);
        archerLabel.setBounds(425, 250, archerIcon.getIconWidth(), archerIcon.getIconHeight());
        frame.getContentPane().add(archerLabel);
        addArcherExpression(archerIcon);

        Image wizard = ResourceLoader.loadImage("assets/help_screen/wizard4x.png")
                .getScaledInstance(48, 45, Image.SCALE_SMOOTH);
        ImageIcon wizardIcon = new ImageIcon(wizard);
        JLabel wizardLabel = new JLabel(wizardIcon);
        wizardLabel.setBounds(750, 100, wizardIcon.getIconWidth(), wizardIcon.getIconHeight());
        frame.getContentPane().add(wizardLabel);
        addWizardExpression(wizardIcon);

        Image cloak = ResourceLoader.loadImage("assets/help_screen/cloak.png")
                .getScaledInstance(41, 48, Image.SCALE_SMOOTH);
        ImageIcon cloakIcon = new ImageIcon(cloak);
        JLabel cloakLabel = new JLabel(cloakIcon);
        cloakLabel.setBounds(50, 450, cloakIcon.getIconWidth(), cloakIcon.getIconHeight());
        frame.getContentPane().add(cloakLabel);
        addCloakExpression(cloakIcon);

        Image extraLife = ResourceLoader.loadImage("assets/help_screen/extra_life.png")
                .getScaledInstance(48, 52, Image.SCALE_SMOOTH);
        ImageIcon extraLifeIcon = new ImageIcon(extraLife);
        JLabel extraLifeLabel = new JLabel(extraLifeIcon);
        extraLifeLabel.setBounds(50, 575, extraLifeIcon.getIconWidth(), extraLifeIcon.getIconHeight());
        frame.getContentPane().add(extraLifeLabel);
        addLifeExpression(extraLifeIcon);

        Image gem = ResourceLoader.loadImage("assets/help_screen/gem.png")
                .getScaledInstance(39, 54, Image.SCALE_SMOOTH);
        ImageIcon gemIcon = new ImageIcon(gem);
        JLabel gemLabel = new JLabel(gemIcon);
        gemLabel.setBounds(425, 450, gemIcon.getIconWidth(), gemIcon.getIconHeight());
        frame.getContentPane().add(gemLabel);
        addGemExpression(gemIcon);

        Image reveal = ResourceLoader.loadImage("assets/help_screen/reveal.png")
                .getScaledInstance(48, 52, Image.SCALE_SMOOTH);
        ImageIcon revealIcon = new ImageIcon(reveal);
        JLabel revealLabel = new JLabel(revealIcon);
        revealLabel.setBounds(425, 575, revealIcon.getIconWidth(), revealIcon.getIconHeight());
        frame.getContentPane().add(revealLabel);
        addRevealExpression(revealIcon);

        Image time = ResourceLoader.loadImage("assets/help_screen/time.png")
                .getScaledInstance(34, 50, Image.SCALE_SMOOTH);
        ImageIcon timeIcon = new ImageIcon(time);
        JLabel timeLabel = new JLabel(timeIcon);
        timeLabel.setBounds(750, 450, timeIcon.getIconWidth(), timeIcon.getIconHeight());
        frame.getContentPane().add(timeLabel);
        addTimeExpression(timeIcon);


        try (InputStream fontStream = ResourceLoader.loadResourceAsStream("assets/fonts/font2.ttf")) {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(7f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            for (Component comp : frame.getContentPane().getComponents()) {
                if (comp instanceof JLabel && comp != playerLabel) {
                    comp.setFont(customFont);
                }
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Set the background using BackgroundCreator
        BufferedImage background = BackgroundCreator.createBackground();
        ImageIcon backgroundIcon = new ImageIcon(background);
        JLabel back = new JLabel(backgroundIcon);
        back.setBounds(0, 0, 1080, 720);
        frame.getContentPane().add(back);

        frame.setVisible(true);
    }

    private void addPlayerExpressionLabels(ImageIcon playerIcon) {
        String[] lines = {
                "This is you!",
                "You can move around", "using the arrow keys.",
                "You need to find runes" ,"in each hall to" ,"pass to the next hall.",
                "Be careful for monsters!"
        };

        int yOffset = 80;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(100 + playerIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addRuneExpression(ImageIcon runeIcon){
        String[] lines = {
                "This is the rune!", "You must check the objects", "by clicking them with","LEFT MOUSE BUTTON.",
                "If you find the rune", "door will open."
        };
        int yOffset = 250;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(120 + runeIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addFighterExpression(ImageIcon fighterIcon){
        String[] lines = {
                "This is fighter monster", "Do not get too close!"


        };

        int yOffset = 110;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(450 + fighterIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addArcherExpression(ImageIcon archerIcon) {
        String[] lines = {
                "This is archer monster", "If it sees you!","It will shoot you!","Be careful!"
        };

        int yOffset = 250;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(450 + archerIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addWizardExpression(ImageIcon wizardIcon) {
        String[] lines = {
                "This is wizard monster", "Who knows what it will do!","Be open for surprises!"
        };

        int yOffset = 100;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(775 + wizardIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addTimeExpression(ImageIcon timeIcon) {
        String[] lines = {
                "Extra time","You will gain 10 seconds","when you collect it."
        };

        int yOffset = 450;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(775 + timeIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addGemExpression(ImageIcon gemIcon) {
        String[] lines = {
                "Luring Gem", "Distracts fighters","Click B and then","W,A,S,D to direct"
        };

        int yOffset = 450;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(450 + gemIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addRevealExpression(ImageIcon revealIcon) {
        String[] lines = {
                "Reveal", "It give hints about the","rune's position","Click R to use it."
        };

        int yOffset = 575;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(440 + revealIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addCloakExpression(ImageIcon cloakIcon){
        String[] lines = {
                "Cloak of Protection", "It protects you from","archer monsters' arrows","Click P to use it."

        };
        int yOffset = 450;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(110 + cloakIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

    private void addLifeExpression(ImageIcon lifeIcon){
        String[] lines = {
                "Extra Life", "It gives you an extra life","when you collect it."

        };
        int yOffset = 575;
        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setBackground(Color.WHITE);
            lineLabel.setOpaque(true);
            lineLabel.setBounds(105 + lifeIcon.getIconWidth() + 10, yOffset, 200, 30);
            frame.getContentPane().add(lineLabel);
            yOffset += 20; // Adjust the offset for the next label
        }
    }

}