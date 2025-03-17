package ui;

import domain.Player;
import domain.halls.HallOfAir;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu {
    private Player player;



    public MainMenu(Player player) {
        this.player = player;
        initialize();
    }

    private void initialize() {
        JFrame frame = createFrame();
        frame.setResizable(false);
        JLabel back = createBackgroundLabel();

        frame.getContentPane().add(back);

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int buttonWidth = 240;
        int buttonHeight = 160;
        int x = (frameWidth - buttonWidth + 300) / 2;

        JButton startButton = createButton("Start a new game", "RokueLike_JustCoding/src/assets/main_menu_assets/main_menu_play.PNG", x, 200, buttonWidth, buttonHeight);
        startButton.addActionListener(e -> {
            frame.dispose();
            BuildMode buildMode = new BuildMode(player);
        });
        frame.getContentPane().add(startButton);

        JButton loadButton = createButton("", "RokueLike_JustCoding/src/assets/main_menu_assets/main_menu_load.PNG", x, 320, buttonWidth, buttonHeight);
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("RokueLike_JustCoding/src/savedGames"));
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                GameMode gameMode = new GameMode(player, new HallOfEarth(player), new HallOfAir(player), new HallOfWater(player), new HallOfFire(player));
                gameMode.loadGame(selectedFile.getAbsolutePath());
                frame.dispose();
            }
        });
        frame.getContentPane().add(loadButton);

        JButton helpButton = createButton("Help", "RokueLike_JustCoding/src/assets/main_menu_assets/main_menu_help.PNG", x, 440, buttonWidth, buttonHeight);
        helpButton.addActionListener(e ->{
            frame.dispose();
            HelpScreen helpScreen = new HelpScreen(player);
                });
        frame.getContentPane().add(helpButton);

        JButton exitButton = createButton("Exit", "RokueLike_JustCoding/src/assets/main_menu_assets/main_menu_exit.PNG", 20, 20, 120, 80);
        exitButton.addActionListener(e -> System.exit(0));
        frame.getContentPane().add(exitButton);

        JButton creditsButton = createButton("Credits", "RokueLike_JustCoding/src/assets/main_menu_assets/credits.PNG", x, 560, buttonWidth, buttonHeight);
        creditsButton.addActionListener(e -> {
            frame.dispose();
            CreditsScreen creditsScreen = new CreditsScreen(player);
        });
        frame.getContentPane().add(creditsButton);

        frame.getContentPane().setComponentZOrder(back, frame.getContentPane().getComponentCount() - 1);
        frame.setVisible(true);

    }

    private JButton createButton(String text, String imagePath, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        try {
            BufferedImage loadImage = ImageIO.read(new File(imagePath));
            Image scaledImage = loadImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBounds(x - 10, y - 10, width + 20, height + 20);
                button.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(width + 20, height + 20, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBounds(x, y, width, height);
                button.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
            }
        });

        return button;
    }

    public static JFrame createFrame() {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        return frame;
    }

    public static JLabel createBackgroundLabel() {
        BufferedImage background = BackgroundCreator.createFixedMainMenuBackground();
        ImageIcon backgroundIcon = new ImageIcon(background);
        JLabel back = new JLabel(backgroundIcon);
        back.setBounds(0, 0, 1080, 720);
        return back;
    }
}