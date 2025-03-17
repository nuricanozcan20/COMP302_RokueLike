package ui;

import domain.halls.Hall;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class BuildModeFeatures {

    public static void addFeaturePanel(JFrame frame) {
        // Add the build mode features
        FeaturePanel featurePanel = new FeaturePanel();
        featurePanel.setOpaque(false); // Make the panel transparent
        featurePanel.setBounds(0, 0, 1080, 720);
        featurePanel.setLayout(null); // Set layout to null
        frame.getContentPane().add(featurePanel);
        frame.getContentPane().setComponentZOrder(featurePanel, 0); // Ensure featurePanel is on top

        addHallLabels(featurePanel);
        addInventoryImage(featurePanel);
    }
    public static JLabel addRemainingObjectLimit(FeaturePanel panel, Hall hall) {
        int remainingObjects = hall.getRequiredItemCount()-hall.getItemCount();
        JLabel objectLimitLabel = new JLabel(remainingObjects+" Object(s) Required", SwingConstants.CENTER);
        try {
            InputStream fontStream = ResourceLoader.loadResourceAsStream("assets/fonts/font2.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(9f);
            objectLimitLabel.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        objectLimitLabel.setBounds(785, 40, 192, 50);
        objectLimitLabel.setOpaque(true); // Make the label opaque
        objectLimitLabel.setBackground(new Color(105, 111, 153, 255)); // Set background color to black
        objectLimitLabel.setForeground(Color.WHITE);
        Color borderColor = new Color(165, 120, 85, 255);
        objectLimitLabel.setBorder(new LineBorder(borderColor, 1)); // Set red line border with thickness 2
        panel.add(objectLimitLabel);
        return objectLimitLabel;
    }

    public static void addHallLabels(FeaturePanel panel) {
        JLabel earthLabel = new JLabel("Hall of Earth",SwingConstants.CENTER);
        JLabel waterLabel = new JLabel("Hall of Water",SwingConstants.CENTER);
        JLabel airLabel = new JLabel("Hall of Air",SwingConstants.CENTER);
        JLabel fireLabel = new JLabel("Hall of Fire",SwingConstants.CENTER);

        try {
            InputStream fontStream = ResourceLoader.loadResourceAsStream("assets/fonts/font2.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(12f);
            earthLabel.setFont(customFont);
            waterLabel.setFont(customFont);
            airLabel.setFont(customFont);
            fireLabel.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        Color textColor = new Color(162, 156, 151);
        Color earth = new Color(68, 118, 50);
        Color water = new Color(51, 119, 153);
        Color air = new Color(203, 170, 67);
        Color fire = new Color(122, 51, 51);
        earthLabel.setForeground(textColor); // Set text color to white
        earthLabel.setOpaque(true); // Make the label opaque
        earthLabel.setBackground(earth); // Set background color to black
        waterLabel.setForeground(textColor);
        waterLabel.setOpaque(true); // Make the label opaque
        waterLabel.setBackground(water); // Set background color to black
        airLabel.setForeground(textColor);
        airLabel.setOpaque(true); // Make the label opaque
        airLabel.setBackground(air); // Set background color to black
        fireLabel.setForeground(textColor);
        fireLabel.setOpaque(true); // Make the label opaque
        fireLabel.setBackground(fire); // Set background color to black

        earthLabel.setBounds(460, 500, 200, 50);
        waterLabel.setBounds(110, 500, 200, 50);
        airLabel.setBounds(110, 150, 200, 50);
        fireLabel.setBounds(460, 150, 200, 50);
        panel.add(earthLabel);
        panel.add(waterLabel);
        panel.add(airLabel);
        panel.add(fireLabel);
    }
    public static void addInventoryImage(FeaturePanel panel) {
        BufferedImage chestBottom = scaleImage(ResourceLoader.loadImage("assets/features/Buildmodechestbottom.png"), 2);
        BufferedImage chestMid = scaleImage(ResourceLoader.loadImage("assets/features/Buildmodechestmid.png"), 2);
        BufferedImage chestTop = scaleImage(ResourceLoader.loadImage("assets/features/Buildmodechesttop.png"), 2);

        int width = Math.max(chestBottom.getWidth(), Math.max(chestMid.getWidth(), chestTop.getWidth()));
        int midCount = 5; // Number of times to repeat the mid image
        int height = chestBottom.getHeight() + chestTop.getHeight() + (chestMid.getHeight() * midCount);

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combinedImage.getGraphics();
        g.drawImage(chestTop, 0, 0, null);
        for (int i = 0; i < midCount; i++) {
            g.drawImage(chestMid, 0, chestTop.getHeight() + (i * chestMid.getHeight()), null);
        }
        g.drawImage(chestBottom, 0, chestTop.getHeight() + (midCount * chestMid.getHeight()), null);
        g.dispose();

        ImageIcon combinedIcon = new ImageIcon(combinedImage);
        JLabel combinedLabel = new JLabel(combinedIcon);
        combinedLabel.setBounds(800, 150, combinedIcon.getIconWidth(), combinedIcon.getIconHeight());
        panel.add(combinedLabel);

        addColumnImage(panel);
        addBoxImage(panel);
        addStackedBoxImage(panel);
        addChestImage(panel);
        addLadderImage(panel);
        addSkullImage(panel);
        addBlockImage(panel);

    }

    private static void addColumnImage(FeaturePanel panel) {
        BufferedImage column = scaleImage(ResourceLoader.loadImage("assets/build_mode_assets/column.png"), 1);
        ImageIcon columnIcon = new ImageIcon(column);
        JLabel columnLabel = new JLabel(columnIcon);
        columnLabel.setBounds(870, 250, columnIcon.getIconWidth(), columnIcon.getIconHeight());
        panel.add(columnLabel, 0); // Add columnLabel on top
        enableDragAndDrop(columnLabel); // Enable drag-and-drop for the box image
    }

    // Add this method to enable drag-and-drop for the box image
    private static void enableDragAndDrop(JLabel label) {
        label.setTransferHandler(new TransferHandler("icon") {
            @Override
            public int getSourceActions(JComponent c) {
                return COPY;
            }

            @Override
            protected Transferable createTransferable(JComponent c) {
                JLabel sourceLabel = (JLabel) c;
                return new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[]{DataFlavor.imageFlavor};
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return DataFlavor.imageFlavor.equals(flavor);
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) {
                        return ((ImageIcon) sourceLabel.getIcon()).getImage();
                    }
                };
            }
        });

        // Add mouse listener to trigger drag
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JComponent comp = (JComponent) e.getSource();
                TransferHandler handler = comp.getTransferHandler();
                handler.exportAsDrag(comp, e, TransferHandler.COPY);
            }
        });
    }


    private static void addBoxImage(FeaturePanel panel) {
        BufferedImage box = scaleImage(ResourceLoader.loadImage("assets/build_mode_assets/box.png"), 1);
        ImageIcon boxIcon = new ImageIcon(box);
        JLabel boxLabel = new JLabel(boxIcon);
        boxLabel.setBounds(870, 300, boxIcon.getIconWidth(), boxIcon.getIconHeight());
        panel.add(boxLabel, 0); // Add boxLabel on top
        enableDragAndDrop(boxLabel); // Enable drag-and-drop for the box image
    }

    private static void addStackedBoxImage(FeaturePanel panel) {
        BufferedImage stackedBox = scaleImage(ResourceLoader.loadImage("assets/build_mode_assets/boxes_stacked.png"), 1);
        ImageIcon stackedBoxIcon = new ImageIcon(stackedBox);
        JLabel stackedBoxLabel = new JLabel(stackedBoxIcon);
        stackedBoxLabel.setBounds(870, 350, stackedBoxIcon.getIconWidth(), stackedBoxIcon.getIconHeight());
        panel.add(stackedBoxLabel, 0); // Add columnLabel on top
        enableDragAndDrop(stackedBoxLabel); // Enable drag-and-drop for the box image
    }

    private static void addChestImage(FeaturePanel panel) {
        BufferedImage chest = scaleImage(ResourceLoader.loadImage("assets/build_mode_assets/chest_closed.png"), 1);
        ImageIcon chestIcon = new ImageIcon(chest);
        JLabel chestLabel = new JLabel(chestIcon);
        chestLabel.setBounds(870, 400, chestIcon.getIconWidth(), chestIcon.getIconHeight());
        panel.add(chestLabel, 0); // Add columnLabel on top
        enableDragAndDrop(chestLabel); // Enable drag-and-drop for the box image
    }

    private static void addLadderImage(FeaturePanel panel) {
        BufferedImage ladder = scaleImage(ResourceLoader.loadImage("assets/build_mode_assets/ladder.png"), 1);
        ImageIcon ladderIcon = new ImageIcon(ladder);
        JLabel ladderLabel = new JLabel(ladderIcon);
        ladderLabel.setBounds(870, 450, ladderIcon.getIconWidth(), ladderIcon.getIconHeight());
        panel.add(ladderLabel, 0); // Add columnLabel on top
        enableDragAndDrop(ladderLabel);
    }

    private static void addSkullImage(FeaturePanel panel) {
        BufferedImage skull = scaleImage(ResourceLoader.loadImage("assets/build_mode_assets/skull.png"), 1);
        ImageIcon skullIcon = new ImageIcon(skull);
        JLabel skullLabel = new JLabel(skullIcon);
        skullLabel.setBounds(870, 500, skullIcon.getIconWidth(), skullIcon.getIconHeight());
        panel.add(skullLabel, 0); // Add columnLabel on top
        enableDragAndDrop(skullLabel);
    }

    private static void addBlockImage(FeaturePanel panel) {
        BufferedImage block = scaleImage(ResourceLoader.loadImage("assets/build_mode_assets/mini_block.png"), 1);
        ImageIcon blockImage = new ImageIcon(block);
        JLabel blockLabel = new JLabel(blockImage);
        blockLabel.setBounds(870, 530, blockImage.getIconWidth(), blockImage.getIconHeight());
        panel.add(blockLabel, 0); // Add columnLabel on top
        enableDragAndDrop(blockLabel); // Enable drag-and-drop for the box image
    }


    private static BufferedImage scaleImage(BufferedImage originalImage, int scaleFactor) {
        int newWidth = originalImage.getWidth() * scaleFactor;
        int newHeight = originalImage.getHeight() * scaleFactor;
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedScaledImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return bufferedScaledImage;
    }
}