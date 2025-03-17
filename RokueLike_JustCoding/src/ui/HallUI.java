// Hall.java
package ui;

import domain.halls.*;
import utils.ResourceLoader;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HallUI implements Serializable {
    protected JPanel hallPanel;
    private List<JLabel> savedLabels = new ArrayList<>();
    private BufferedImage wallLayout;
    private int doorPosition;
    private Hall hall;

    public HallUI(Hall hall) {
        this.hall = hall;
        initialize();
    }

    private void initialize() {
        hallPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWalls(g);
            }
        };
        hallPanel.setOpaque(false); // Make the background transparent
        hallPanel.setBounds(50, 50, 400, 400); // Default square dimensions
    }

    public JPanel getHallPanel() {
        return hallPanel;
    }

    public void saveState() {
        savedLabels.clear();
        for (Component component : hallPanel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                savedLabels.add(label);
            }
        }
    }

    public int getHeight() {
        return hallPanel.getHeight();
    }

    public int getWidth() {
        return hallPanel.getWidth();
    }

    public Rectangle getMovementBounds() {
        // Return the bounds within which the hero can move
        return new Rectangle(51, 21, 700, 640);
    }

    protected void drawWalls(Graphics g) {

        BufferedImage wallOuterE = ResourceLoader.loadImage("assets/walls/wall_outer_e.png");
        BufferedImage wallOuterN = ResourceLoader.loadImage("assets/walls/wall_outer_n.png");
        BufferedImage wallOuterW = ResourceLoader.loadImage("assets/walls/wall_outer_w.png");
        BufferedImage wallInnerS = ResourceLoader.loadImage("assets/walls/wall_inner_s.png");
        BufferedImage wallInnerSE = ResourceLoader.loadImage("assets/walls/wall_inner_se.png");
        BufferedImage wallInnerSW = ResourceLoader.loadImage("assets/walls/wall_inner_sw.png");
        BufferedImage wallOuterNW = ResourceLoader.loadImage("assets/walls/wall_outer_nw.png");
        BufferedImage wallOuterNE = ResourceLoader.loadImage("assets/walls/wall_outer_ne.png");
        BufferedImage doorClosed = ResourceLoader.loadImage("assets/walls/door_closed.png");
        BufferedImage doorOpen = ResourceLoader.loadImage("assets/walls/door_open.png");
        BufferedImage columnWall = ResourceLoader.loadImage("assets/walls/column_wall.png");

        BufferedImage[] bricks = {
                ResourceLoader.loadImage("assets/walls/wall_front_left.png"),
        ResourceLoader.loadImage("assets/walls/wall_front_right.png"),
        ResourceLoader.loadImage("assets/walls/wall_front.png"),
        ResourceLoader.loadImage("assets/walls/wall_left.png"),
        ResourceLoader.loadImage("assets/walls/wall_missing_brick_1.png"),
        ResourceLoader.loadImage("assets/walls/wall_missing_brick_2.png"),
        ResourceLoader.loadImage("assets/walls/wall_right.png")
        };

        // Load additional images for HallOfAir
        BufferedImage wallFlag = null;
        BufferedImage wallGargoyle = null;
        if (hall instanceof HallOfWater) {
            wallFlag = ResourceLoader.loadImage("assets/walls/wall_flag_blue.png");
            wallGargoyle = ResourceLoader.loadImage("assets/walls/wall_gargoyle_blue_2.png");
            doorPosition = hallPanel.getWidth() / 2 + 12;
        }

        if (hall instanceof HallOfEarth) {
            wallFlag = ResourceLoader.loadImage("assets/walls/wall_flag_green.png");
            wallGargoyle = ResourceLoader.loadImage("assets/walls/wall_gargoyle_green_2.png");
            doorPosition = hallPanel.getWidth() / 2 - 36;
        }

        if (hall instanceof HallOfFire) {
            wallFlag = ResourceLoader.loadImage("assets/walls/wall_flag_red.png");
            wallGargoyle = ResourceLoader.loadImage("assets/walls/wall_gargoyle_red_2.png");
            doorPosition = hallPanel.getWidth() / 2 - 24;
        }

        if (hall instanceof HallOfAir) {
            wallFlag = ResourceLoader.loadImage("assets/walls/wall_flag_yellow.png");
            doorPosition = hallPanel.getWidth() / 2 - 36;
        }

        int tileSize = 16; // Updated tile size to 16x16 pixels
        int brickIndex = 0;

        // Debug: Check if images are loaded
        if (wallOuterE == null || wallOuterN == null || wallOuterW == null || wallInnerS == null ||
                wallInnerSE == null || wallInnerSW == null || wallOuterNW == null || wallOuterNE == null || doorClosed == null || columnWall == null) {
            return;
        }

        // Draw bricks under the top row
        for (int x = 16; x < hallPanel.getWidth(); x += tileSize) {
            g.drawImage(bricks[brickIndex], x, tileSize, null);
            brickIndex = (brickIndex + 1) % bricks.length;
        }

        //Draw bricks under the bottom row

        for (int x = 16; x < hallPanel.getWidth(); x += tileSize) {
            if (x < doorPosition + 24 && x > doorPosition - 6) {
                continue;
            }
            g.drawImage(bricks[brickIndex], x, hallPanel.getHeight() - tileSize, null);
            brickIndex = (brickIndex + 1) % bricks.length;
        }


        // Draw the left and right columns
        for (int y = tileSize; y < hallPanel.getHeight() - tileSize - 16; y += tileSize) {
            g.drawImage(wallOuterW, 0, y, null);
        }
        for (int y = tileSize; y < hallPanel.getHeight() - tileSize - 16; y += tileSize) {
            g.drawImage(wallOuterE, hallPanel.getWidth() - 4, y, null);
        }
        for (int y = hallPanel.getHeight() - tileSize - 16; y < hallPanel.getHeight() - 20; y += 20) {
            g.drawImage(wallOuterE, hallPanel.getWidth() - 4, y, null);
        }

        // Draw the top row
        for (int x = 0; x < hallPanel.getWidth(); x += tileSize) {
            if (x == 0) {
                g.drawImage(wallOuterNW, x, 0, null);
            } else if (x == hallPanel.getWidth() - tileSize) {
                g.drawImage(wallOuterNE, x, 0, null);
            } else {
                g.drawImage(wallOuterN, x, 0, null);
            }
        }

        //Draw the bottom row
        for (int x = 11; x < hallPanel.getWidth(); x += tileSize) {
            if (x < doorPosition + 20 && x > doorPosition - 6) {
                continue;
            }
            if (x == 11) {
                g.drawImage(wallInnerSW, x, hallPanel.getHeight() - tileSize - 16, null);
            } else if (x == hallPanel.getWidth() - tileSize) {
                g.drawImage(wallInnerSE, x, hallPanel.getHeight() - tileSize - 16, null);
            } else {
                g.drawImage(wallInnerS, x, hallPanel.getHeight() - tileSize - 16, null);
            }
        }


        // Draw additional images for HallOfAir at the bottom

        if (hall instanceof HallOfWater) {
            g.drawImage(wallFlag, hallPanel.getWidth() / 2 + 48, hallPanel.getHeight() - tileSize, null);
            g.drawImage(wallGargoyle, hallPanel.getWidth() / 2 - 8, hallPanel.getHeight() - tileSize, null);
            g.drawImage(columnWall, hallPanel.getWidth() / 2 - 16, 0, null);
        }

        // Draw additional images for HallOfAir at the bottom
        if (hall instanceof HallOfEarth) {
            g.drawImage(wallFlag, hallPanel.getWidth() / 2 + 48, hallPanel.getHeight() - tileSize, null);
            g.drawImage(wallGargoyle, hallPanel.getWidth() / 2 - 8, hallPanel.getHeight() - tileSize, null);
            g.drawImage(columnWall, hallPanel.getWidth() / 2 + 16, 0, null);
        }
        // Draw additional images for HallOfAir at the bottom
        if (hall instanceof HallOfFire) {
            g.drawImage(wallFlag, hallPanel.getWidth() / 2 + 48, hallPanel.getHeight() - tileSize, null);
            g.drawImage(wallGargoyle, hallPanel.getWidth() / 2 - 8, hallPanel.getHeight() - tileSize, null);
            g.drawImage(columnWall, hallPanel.getWidth() / 2 - 8, 0, null);
        }
        // Draw additional images for HallOfAir at the bottom
        if (hall instanceof HallOfAir) {
            g.drawImage(wallFlag, hallPanel.getWidth() / 2 + 48, hallPanel.getHeight() - tileSize, null);
            g.drawImage(wallGargoyle, hallPanel.getWidth() / 2 - 8, hallPanel.getHeight() - tileSize, null);
            g.drawImage(columnWall, hallPanel.getWidth() / 2 + 32, 0, null);
        }

        BufferedImage doorImage = hall.isDoorOpen() ? doorOpen : doorClosed;
        g.drawImage(doorImage, doorPosition, hallPanel.getHeight() - 32, null);


    }

    public int getDoorPosition() {
        return doorPosition;
    }

    public List<JLabel> getSavedLabels() {
        return savedLabels;
    }

    public void openDoor() {
        hall.openDoor();
        ResourceLoader.playSound("assets/sounds/door_open.wav"); // Play the door opening sound
        hallPanel.repaint(); // Trigger a repaint to update the door image
    }




}