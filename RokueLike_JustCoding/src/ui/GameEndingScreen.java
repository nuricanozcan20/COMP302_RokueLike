package ui;

import domain.Player;
import utils.ResourceLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class GameEndingScreen extends JPanel {

    private Font customFont;
    private String message;
    private boolean isWin;
    private Player player;
    private Image loseImage;

    public GameEndingScreen(Player player, boolean isWin) {
        this.isWin = isWin;
        this.player = player;
        if (isWin) {
            message = "YOU WIN";
            loadLoseImage();
        } else {
            message = "GAME OVER";
            loadLoseImage();
        }
        loadCustomFont();
        setOpaque(true); // Make the panel opaque
        setBackground(Color.BLACK); // Set the background color to black
        setLayout(null); // Use null layout
    }

    private void loadCustomFont() {
        try (InputStream fontStream = ResourceLoader.loadResourceAsStream("assets/fonts/font2.ttf")) {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.BOLD, 50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            customFont = new Font("Arial", Font.BOLD, 50); // Fallback font
        }
    }

    private void loadLoseImage() {
        try {
            loseImage = new ImageIcon(ResourceLoader.loadImage("assets/main_menu_assets/lose_screen.jpg")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK); // Opaque black background
        g.fillRect(0, 0, getWidth(), getHeight());
        if (isWin) {
            g.setColor(Color.GREEN);
            g.setFont(customFont);
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(message)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(message, x, y);
        } else {
            g.drawImage(loseImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.RED);
            g.setFont(customFont);
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(message)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(message, x, y);
        }
    }

    private void addButtons(Player player, JFrame frame) {
        Color color = isWin ? Color.GREEN : Color.RED;
        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.setFocusable(false); // Remove focus border
        returnButton.setBackground(Color.BLACK); // Set background color to black
        returnButton.setForeground(color);
        returnButton.setBorder(new LineBorder(color, 2)); // Set red outline
        returnButton.setFont(customFont.deriveFont(Font.BOLD, 15f)); // Use custom font
        returnButton.setBounds((getWidth() - 375) / 2, frame.getHeight() - 250, 400, 50);

        // Add mouse listener to change font size on hover
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                returnButton.setFont(customFont.deriveFont(Font.BOLD, 20f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                returnButton.setFont(customFont.deriveFont(Font.BOLD, 15f));
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(GameEndingScreen.this);
                topFrame.dispose();
                new MainMenu(player); // Assuming MainMenu is the class for the main menu
            }
        });
        add(returnButton);

        // Add Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFocusable(false); // Remove focus border
        exitButton.setBackground(Color.BLACK); // Set background color to black
        exitButton.setForeground(color);
        exitButton.setBorder(new LineBorder(color, 2)); // Set red outline
        exitButton.setFont(customFont.deriveFont(Font.BOLD, 15f));
        exitButton.setBounds((getWidth() - 375) / 2, frame.getHeight() - 180, 400, 50);

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setFont(customFont.deriveFont(Font.BOLD, 20f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setFont(customFont.deriveFont(Font.BOLD, 15f));
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        add(exitButton);
    }

    public void showGameOverScreen(JFrame frame) {
        frame.getContentPane().removeAll();
        this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.getContentPane().add(this);
        addButtons(player, frame);
        this.setVisible(true);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    public void showGameWinScreen(JFrame frame) {
        frame.getContentPane().removeAll();
        this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.getContentPane().add(this);
        addButtons(player, frame);
        this.setVisible(true);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}