package ui;

import domain.Player;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CreditsScreen {
    private JFrame frame;
    private Player player;

    public CreditsScreen(Player player) {
        initialize();
        this.player = player;
    }

    private void initialize() {
        frame = new JFrame("Credits");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(100,100,1080, 720);
        frame.setResizable(false);
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

        String[] credits = {
                "JUST CODING PRESENTS",
                "RokueLike",
                "Game Design & Development: Just Coding",
                "",
                "Graphical Assets:",
                "Author: 0x72","Url: https://0x72.itch.io/16x16-dungeon-tileset",
                "",
                "Author:RandallCurtis","Url: https://randallcurtis.itch.io/16-bit-rpg-icons",
                "",
                "Author:Sr.Toasty","Url: https://srtoasty.itch.io/ui-inventory-assets-mini-pack",
                "",
                "Sound Effects are adapted from:",
                "https://freesound.org/",
                "Special Thanks: TAMTA KAPANADZE"
        };

        Font customFont = null;
        Font customFontLarge = null;
        try (InputStream fontStream = ResourceLoader.loadResourceAsStream("assets/fonts/font2.ttf")) {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(10f);
            customFontLarge = customFont.deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        int yOffset = 50;
        for (int i = 0; i < credits.length; i++) {
            JLabel creditLabel = new JLabel(credits[i]);
            creditLabel.setVerticalAlignment(SwingConstants.CENTER);
            creditLabel.setHorizontalAlignment(SwingConstants.CENTER);
            creditLabel.setBounds(0, yOffset, 1100, 30);
            creditLabel.setForeground(Color.WHITE); // Set text color to white
            if (customFont != null) {
                if (i == 0 || i == 1 || i == 2 || i == 16) {
                    creditLabel.setFont(customFontLarge);
                    yOffset += 40;
                }else if (i==15){
                    creditLabel.setFont(customFont);
                    yOffset += 50;
                }
                else {
                    creditLabel.setFont(customFont);
                    yOffset += 30;
                }
            }
            frame.getContentPane().add(creditLabel);
        }

        // Set the background using BackgroundCreator
        BufferedImage background = BackgroundCreator.createBackground();
        ImageIcon backgroundIcon = new ImageIcon(background);
        JLabel back = new JLabel(backgroundIcon);
        back.setBounds(0, 0, 1080, 720);
        frame.getContentPane().add(back);

        frame.setVisible(true);
    }
}