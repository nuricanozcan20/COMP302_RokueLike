package ui;

import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class GameModeOverlays implements Serializable {
    private static JPanel damageOverlay;

    public static void showDamageOverlay(GameMode gameMode) {
        JFrame frame = gameMode.getFrame();
        if (damageOverlay == null) {
            damageOverlay = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Image damageImage = ResourceLoader.loadImage("assets/damage_screen.png");
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Set transparency
                    g2d.drawImage(damageImage, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            };
            damageOverlay.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            damageOverlay.setOpaque(false);
            frame.getContentPane().add(damageOverlay);
            frame.getContentPane().setComponentZOrder(damageOverlay, 0);
        }
        damageOverlay.setVisible(true);
        frame.revalidate();
        frame.repaint();

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                damageOverlay.setVisible(false);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static JPanel showPauseOverlay(GameMode game) {
        JFrame frame = game.getFrame();
        JPanel pauseOverlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent gray
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                Font customFont = null;
                try (InputStream fontStream = ResourceLoader.loadResourceAsStream("assets/fonts/font2.ttf")) {
                    customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.BOLD, 50f);
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(customFont);
                } catch (FontFormatException | IOException e) {
                    e.printStackTrace();
                }
                g.setFont(customFont);
                FontMetrics fm = g.getFontMetrics();
                String text = "PAUSED";
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g.drawString(text, x, y);
            }
        };
        pauseOverlay.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        pauseOverlay.setOpaque(false);
        pauseOverlay.setVisible(false);
        frame.getContentPane().add(pauseOverlay);
        frame.getContentPane().setComponentZOrder(pauseOverlay, 0);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        return pauseOverlay;
    }
}