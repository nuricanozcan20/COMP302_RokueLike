package ui;

import domain.enchantments.CloakOfProtection;
import domain.enchantments.ExtraLife;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;


public class GameModeImageFactory {
    private static final Random random = new Random();
    private ImageIcon[] rightImages;
    private ImageIcon[] leftImages;

    public GameModeImageFactory(){
        loadPlayerImages();
    }

    private void loadPlayerImages() {
        int x = 11*50/20;
        int y= 11*76/20;
        rightImages = new ImageIcon[] {
                new ImageIcon(ResourceLoader.loadImage("assets/characters/player_right1.png").getScaledInstance(x, y, Image.SCALE_SMOOTH)),
                new ImageIcon(ResourceLoader.loadImage("assets/characters/player_right2.png").getScaledInstance(x, y, Image.SCALE_SMOOTH))
        };
        leftImages = new ImageIcon[] {
                new ImageIcon(ResourceLoader.loadImage("assets/characters/player_left1.png").getScaledInstance(x, y, Image.SCALE_SMOOTH)),
                new ImageIcon(ResourceLoader.loadImage("assets/characters/player_left2.png").getScaledInstance(x, y, Image.SCALE_SMOOTH))
        };
    }


    public JLabel addPlayerImage(GameMode game) {
        int x = 11*50/20;
        int y= 11*76/20;
        JFrame frame = game.getFrame();
        BufferedImage playerImage = ResourceLoader.loadImage("assets/characters/player_right1.png");
        ImageIcon playerIcon = new ImageIcon(playerImage.getScaledInstance(x, y, Image.SCALE_SMOOTH));
        JLabel playerLabel = new JLabel(playerIcon);

        // Calculate the center position
        int centerX = (frame.getWidth() - playerIcon.getIconWidth()) / 2;
        int centerY = (frame.getHeight() - playerIcon.getIconHeight()) / 2;

        playerLabel.setBounds(centerX, centerY, playerIcon.getIconWidth(), playerIcon.getIconHeight());
        frame.getContentPane().add(playerLabel);
        frame.getContentPane().setComponentZOrder(playerLabel, 1);
        frame.revalidate();
        frame.repaint();
        return playerLabel;
    }
    public JLabel addExtraLifeImage(GameMode game) {
        JFrame frame = game.getFrame();
        BufferedImage extraLifeImage = ResourceLoader.loadImage("assets/features/extralife.png");
        ImageIcon extraLifeIcon = new ImageIcon(extraLifeImage);
        JLabel extraLifeLabel = new JLabel(extraLifeIcon);

        int extraLifeX = random.nextInt(700 - 26 - extraLifeLabel.getWidth()); // Random X position
        int extraLifeY = random.nextInt(640 - 46 - extraLifeLabel.getHeight()); // Random Y position

        extraLifeLabel.setBounds(extraLifeX, extraLifeY, extraLifeIcon.getIconWidth(), extraLifeIcon.getIconHeight());
        frame.getContentPane().add(extraLifeLabel);
        frame.getContentPane().setComponentZOrder(extraLifeLabel, 1);
        frame.revalidate();
        frame.repaint();
        return extraLifeLabel;
    }

    public JLabel addCloakImage(GameMode game) {
        JFrame frame = game.getFrame();
        BufferedImage cloakImage = ResourceLoader.loadImage("assets/features/cloak.png");
        ImageIcon cloakIcon = new ImageIcon(cloakImage);
        JLabel cloakLabel = new JLabel(cloakIcon);

        int cloakX = random.nextInt(700 - 26 - cloakLabel.getWidth()); // Random X position
        int cloakY = random.nextInt(640 - 46 - cloakLabel.getHeight()); // Random Y position

        cloakLabel.setBounds(cloakX, cloakY, cloakIcon.getIconWidth(), cloakIcon.getIconHeight());
        frame.getContentPane().add(cloakLabel);
        frame.getContentPane().setComponentZOrder(cloakLabel, 1);
        frame.revalidate();
        frame.repaint();
        return cloakLabel;
    }

    public JLabel addRevealImage(GameMode game) {
        JFrame frame = game.getFrame();
        BufferedImage revealImage = ResourceLoader.loadImage("assets/features/reveal.png");
        ImageIcon revealIcon = new ImageIcon(revealImage);
        JLabel revealLabel = new JLabel(revealIcon);

        // Set the initial position of the reveal
        int revealX = random.nextInt(700 - 26 - revealLabel.getWidth()); // Random X position
        int revealY = random.nextInt(640 - 46 - revealLabel.getHeight()); // Random Y position

        revealLabel.setBounds(revealX, revealY, revealIcon.getIconWidth(), revealIcon.getIconHeight());
        frame.getContentPane().add(revealLabel);
        frame.getContentPane().setComponentZOrder(revealLabel, 1);
        frame.revalidate();
        frame.repaint();
        return revealLabel;
    }

    public JLabel addRuneImage(GameMode game) {
        JFrame frame = game.getFrame();
        BufferedImage runeImage = ResourceLoader.loadImage("assets/features/rune.png");
        ImageIcon runeIcon = new ImageIcon(runeImage);
        JLabel runeLabel = new JLabel(runeIcon);

        int runeX = 300;
        int runeY = 400;

        runeLabel.setBounds(runeX, runeY, runeIcon.getIconWidth(), runeIcon.getIconHeight());
        frame.getContentPane().add(runeLabel);
        frame.getContentPane().setComponentZOrder(runeLabel, 1);
        frame.revalidate();
        frame.repaint();
        runeLabel.setVisible(false);
        return runeLabel;
    }

    public JLabel addLuringGemImage(GameMode game) {
        JFrame frame = game.getFrame();
        BufferedImage luringGemImage = ResourceLoader.loadImage("assets/features/luringgem.png");
        ImageIcon luringGemIcon = new ImageIcon(luringGemImage);
        JLabel luringGemLabel = new JLabel(luringGemIcon);

        int luringGemX = random.nextInt(700 - 26 - luringGemLabel.getWidth()); // Random X position
        int luringGemY = random.nextInt(640 - 46 - luringGemLabel.getHeight()); // Random Y position

        luringGemLabel.setBounds(luringGemX, luringGemY, luringGemIcon.getIconWidth(), luringGemIcon.getIconHeight());
        frame.getContentPane().add(luringGemLabel);
        frame.getContentPane().setComponentZOrder(luringGemLabel, 1);
        frame.revalidate();
        frame.repaint();
        return luringGemLabel;
    }

    public JLabel addExtraTimeImage(GameMode game){
        JFrame frame = game.getFrame();
        Image extraTimeImage = ResourceLoader.loadImage("assets/features/extratime.png").getScaledInstance(17, 25, Image.SCALE_SMOOTH);
        ImageIcon extraTimeIcon = new ImageIcon(extraTimeImage);
        JLabel extraTimeLabel = new JLabel(extraTimeIcon);

        int extraTimeX = random.nextInt(700 - 26 - extraTimeLabel.getWidth()); // Random X position
        int extraTimeY = random.nextInt(640 - 46 - extraTimeLabel.getHeight()); // Random Y position

        extraTimeLabel.setBounds(extraTimeX, extraTimeY, extraTimeIcon.getIconWidth(), extraTimeIcon.getIconHeight());
        frame.getContentPane().add(extraTimeLabel);
        frame.getContentPane().setComponentZOrder(extraTimeLabel, 1);
        frame.revalidate();
        frame.repaint();
        return extraTimeLabel;
    }

    public ImageIcon[] getRightImages() {
        return rightImages;
    }
    public ImageIcon[] getLeftImages() {
        return leftImages;
    }

}
