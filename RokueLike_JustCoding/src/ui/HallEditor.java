package ui;

import domain.Player;
import domain.halls.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public class HallEditor implements Serializable {
    private JFrame frame;
    private Hall hall;
    private List<Hall> otherHalls;
    private Player player;
    private Timer timer;
    private JLabel remainingLabel;

    public HallEditor(Player player, Hall hall, List<Hall> otherHalls) {
        this.hall = hall;
        this.player = player;
        this.otherHalls = otherHalls;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1080, 720); // Set the same size as the build mode
        frame.setLayout(null);
        frame.setBounds(100, 100, 1080, 720);
        BufferedImage background = BackgroundCreator.createBackground();
        ImageIcon backgroundIcon = new ImageIcon(background);
        JLabel back = new JLabel(backgroundIcon);
        back.setBounds(0, 0, 1080, 720);
        frame.getContentPane().add(back);

        // Add the build mode features
        FeaturePanel featurePanel = new FeaturePanel();
        featurePanel.setOpaque(false); // Make the panel transparent
        featurePanel.setBounds(0, 0, 1080, 720);
        featurePanel.setLayout(null); // Set layout to null
        frame.getContentPane().add(featurePanel);
        frame.getContentPane().setComponentZOrder(featurePanel, 0); // Ensure featurePanel is on top
        JButton exitButton = featurePanel.addExitButton(featurePanel);
        exitButton.addActionListener(e -> {
            if (hall instanceof HallOfEarth && hall.getItemCount() < 6) {
                JOptionPane.showMessageDialog(frame, "You must add at least " + (6 - hall.getItemCount()) + " objects more to the Hall of Earth before exiting.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (hall instanceof HallOfAir && hall.getItemCount() < 9) {
                JOptionPane.showMessageDialog(frame, "You must add at least" + (9 - hall.getItemCount()) + " objects more to the Hall of Air before exiting.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (hall instanceof HallOfWater && hall.getItemCount() < 13) {
                JOptionPane.showMessageDialog(frame, "You must add at least " + (13 - hall.getItemCount()) + " objects more to the Hall of Water before exiting.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (hall instanceof HallOfFire && hall.getItemCount() < 17) {
                JOptionPane.showMessageDialog(frame, "You must add at least " + (17 - hall.getItemCount()) + " objects more to the Hall of Fire before exiting.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                hall.getUIController().saveState(); // Save the current state of the hall
                frame.dispose();
                timer.stop();
                new BuildMode(player, hall, otherHalls); // Return to BuildMode with the updated hall
            }
        });
        featurePanel.add(exitButton);

        BuildModeFeatures.addInventoryImage(featurePanel);

        // Add the hall panel to the frame
        JPanel hallPanel = hall.getUIController().getHallPanel();
        hallPanel.setBounds(0, 0, 700, 640);
        hallPanel.setLayout(null); // Set layout to null
        removeMouseListeners(hallPanel); // Remove existing MouseListeners
        frame.getContentPane().add(hallPanel);
        frame.getContentPane().setComponentZOrder(hallPanel, 0); // Ensure hallPanel is on top

        hallPanel.setTransferHandler(new TransferHandler("icon") {
            @Override
            public boolean canImport(TransferSupport support) {
                // Accept only image flavor
                return support.isDataFlavorSupported(DataFlavor.imageFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }

                try {
                    // Retrieve the image being transferred
                    Transferable transferable = support.getTransferable();
                    Image image = (Image) transferable.getTransferData(DataFlavor.imageFlavor);

                    // Create a new JLabel with the dropped image
                    ImageIcon droppedIcon = new ImageIcon(image);
                    JLabel label = new JLabel(droppedIcon);

                    // Set the drop location
                    Point dropPoint = support.getDropLocation().getDropPoint();
                    label.setBounds(dropPoint.x, dropPoint.y, droppedIcon.getIconWidth(), droppedIcon.getIconHeight());

                    // Add the JLabel to the hall panel
                    hallPanel.add(label);
                    hallPanel.revalidate();
                    hallPanel.repaint();

                    hall.increaseItemCount(); // Increment the item count

                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
        remainingLabel = BuildModeFeatures.addRemainingObjectLimit(featurePanel, hall);
        startTimer();
        // Ensure the hall panel is repainted
        hallPanel.repaint();

        frame.setVisible(true);
    }

    private void removeMouseListeners(JPanel panel) {
        for (MouseListener listener : panel.getMouseListeners()) {
            panel.removeMouseListener(listener);
        }
    }
    private void startTimer() {
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRemainingLabel();
            }
        });
        timer.start();
    }
    private void updateRemainingLabel() {
        int remainingObjects = Math.max(hall.getRequiredItemCount() - hall.getItemCount(),0);
        remainingLabel.setText(remainingObjects + " Object(s) Required");
    }

}