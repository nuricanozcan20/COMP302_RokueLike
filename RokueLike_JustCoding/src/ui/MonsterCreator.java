package ui;

import domain.Player;
import domain.monsters.ArcherMonster;
import domain.monsters.FighterMonster;
import domain.monsters.Monster;
import domain.monsters.WizardMonster;
import utils.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MonsterCreator {


    protected static Monster addWizardImage(JFrame frame, Player player) {
        if (WizardMonster.wizardCount < 1) {
            BufferedImage wizardImage = ResourceLoader.loadImage("assets/characters/wizard2x.png");
            ImageIcon wizardIcon = new ImageIcon(wizardImage);
            JLabel wizardLabel = new JLabel(wizardIcon);

            int wizardX = 50;
            int wizardY = 500;
            WizardMonster wizardMonster = new WizardMonster(wizardLabel, player);

            wizardLabel.setBounds(wizardX, wizardY, wizardIcon.getIconWidth(), wizardIcon.getIconHeight());
            frame.getContentPane().add(wizardLabel);
            frame.getContentPane().setComponentZOrder(wizardLabel, 0);
            frame.revalidate();
            frame.repaint();
            System.out.println("Wizard Created");
            return wizardMonster;
        } else {
            return null;
        }
    }

    protected static Monster addArcherImage(JFrame frame, Player player) {
        if (ArcherMonster.archerCount < 4) {
            BufferedImage archerImage = ResourceLoader.loadImage("assets/characters/archer2x.png");
            ImageIcon archerIcon = new ImageIcon(archerImage);
            JLabel archerLabel = new JLabel(archerIcon);

            // Set the initial position of the archer
            int archerX = 500;
            int archerY = 300;
            ArcherMonster archerMonster = new ArcherMonster(archerX, archerY, archerLabel, player);

            archerLabel.setBounds(archerX, archerY, archerIcon.getIconWidth(), archerIcon.getIconHeight());
            frame.getContentPane().add(archerLabel);
            frame.getContentPane().setComponentZOrder(archerLabel, 0);
            frame.revalidate();
            frame.repaint();

            ArcherMonster.archerCount++;
            System.out.println("Archer Created");
            return archerMonster;
        } else {
            return null;
        }
    }

    protected static Monster addFighterImage(JFrame frame, Player player) {
        if (FighterMonster.fighterCount < 4) {
            BufferedImage fighterImage = ResourceLoader.loadImage("assets/characters/fighter2x.png");
            ImageIcon fighterIcon = new ImageIcon(fighterImage);
            JLabel fighterLabel = new JLabel(fighterIcon);


            // Set the initial position of the fighter
            int fighterX = 600;
            int fighterY = 400;
            FighterMonster fighterMonster = new FighterMonster(fighterX, fighterY, fighterLabel, player);


            fighterLabel.setBounds(fighterX, fighterY, fighterIcon.getIconWidth(), fighterIcon.getIconHeight());
            frame.getContentPane().add(fighterLabel);
            frame.getContentPane().setComponentZOrder(fighterLabel, 0);
            frame.revalidate();
            frame.repaint();

            FighterMonster.fighterCount++;
            System.out.println("Fighter Created");
            return fighterMonster;
        } else {
            return null;
        }
    }

    private static final Random random = new Random();

    public static Monster randomMonsterGenerate(JFrame frame, Player player) {
        /**
         * Requires:
         * - A non-null JFrame object for the game window.
         * - A non-null Player object to associate with the created monsters.
         * - At least one type of monster (WizardMonster, ArcherMonster, or FighterMonster) must not have reached its limit.
         *
         * Modifies:
         * - The JFrame instance (adds a JLabel representing the monster to the content pane if created).
         * - The respective monster count (wizardCount, archerCount, or fighterCount) for the generated monster.
         *
         * Effects:
         * - Randomly generates one of three types of monsters (WizardMonster, ArcherMonster, or FighterMonster),
         *   based on availability and count limits.
         * - If all monster types have reached their respective count limits, returns null.
         * - Adds the generated monster's visual representation to the game window.
         */

        if (player == null) {
            throw new NullPointerException("Player cannot be null");
        }

        int monsterType = random.nextInt(3);
        if(WizardMonster.wizardCount>=1 && ArcherMonster.archerCount>=4 && FighterMonster.fighterCount>=4){
            return null;
        }
        switch (monsterType) {
            case 0:
                if(WizardMonster.wizardCount < 1) {
                    return addWizardImage(frame, player);
                }
                else {
                    return randomMonsterGenerate(frame, player);
                }
            case 1:
                if(ArcherMonster.archerCount < 4) {
                    return addArcherImage(frame, player);
                }
                else {
                    return randomMonsterGenerate(frame, player);
                }
            case 2:
                if (FighterMonster.fighterCount < 4) {
                    return addFighterImage(frame, player);
                }
                else {
                    return randomMonsterGenerate(frame, player);
                }
            default:
                throw new IllegalStateException("Unexpected value: " + monsterType);
        }
    }

    public static void placeRandomMonster(GameMode game,Monster randomMonster){
        // Requires: A Monster object "randomMonster" which is not null.
        // Modifies: This method modifies the GUI components of the frame, the position of the "randomMonster",
        //           and the "monsters" list. It also potentially updates the static "wizardCount" field if a WizardMonster is placed.
        // Effects: Places the given "randomMonster" in a random location on the GUI, ensuring it does not collide
        //          with existing labels, other monsters, or appear too close to the player's hero. Specific mechanisms
        //          are triggered depending on the type of the monster.
        Player player = game.getPlayer(); // Retrieve the player object from the game.
        if(randomMonster==null){
            return;  // If the input monster is null, exit the function.
        }
        if (randomMonster != null) {
            JLabel monsterLabel = randomMonster.getMonsterLabel(); // Retrieve the JLabel associated with the monster.
            boolean placed = false; // A flag to indicate whether the monster has been successfully placed.

            while (!placed) { // Loop until a valid position is found for the monster.
                Random random = new Random();
                // Generate random x and y coordinates within the bounds of the frame minus label dimensions.
                int x = random.nextInt(700-26 - monsterLabel.getWidth());
                int y = random.nextInt(640-46 - monsterLabel.getHeight());
                int heroX = player.getX(); // Get the current x-coordinate of the player's hero.
                int heroY = player.getY(); // Get the current y-coordinate of the player's hero.

                // Calculate the distance between the generated position and the hero.
                int distanceToHero = (int) Math.sqrt((x - heroX) * (x - heroX) + (y - heroY) * (y - heroY));

                // Ensure the monster is placed at least 100 units away from the hero.
                while (distanceToHero < 100) {
                    x = random.nextInt(700-26 - monsterLabel.getWidth());
                    y = random.nextInt(640-46 - monsterLabel.getHeight());
                    distanceToHero = (int) Math.sqrt((x - heroX) * (x - heroX) + (y - heroY) * (y - heroY));
                }

                monsterLabel.setBounds(x, y, monsterLabel.getWidth(), monsterLabel.getHeight());
                randomMonster.setX(x); // Update the x-coordinate of the monster.
                randomMonster.setY(y); // Update the y-coordinate of the monster.

                boolean collision = false; // Flag to check for collisions with other elements.

                // Check for collisions with existing labels in the hall.
                for (JLabel label : game.getHall().getUIController().getSavedLabels()) {
                    if (monsterLabel.getBounds().intersects(label.getBounds())) {
                        collision = true;
                        break;
                    }
                }

                // Check for collisions with other monsters already placed.
                for(Monster monster : game.getMonsters()){
                    if(monster.getMonsterLabel().getBounds().intersects(monsterLabel.getBounds())){
                        collision = true;
                        break;
                    }
                }

                // If no collisions, add the monster to the frame and finalize placement.
                if (!collision) {
                    game.getFrame().getContentPane().add(monsterLabel);
                    game.getFrame().getContentPane().setComponentZOrder(monsterLabel, 1);
                    placed = true;
                }
            }

            game.getMonsters().add(randomMonster); // Add the monster to the list of monsters.

            // Trigger specific mechanisms based on the type of monster.
            if (randomMonster instanceof ArcherMonster) {
                game.archerMechanism((ArcherMonster) randomMonster);
            } else if (randomMonster instanceof FighterMonster) {
                game.fighterMechanism((FighterMonster) randomMonster);
            } else if (randomMonster instanceof WizardMonster) {
                game.wizardMechanism((WizardMonster) randomMonster);
                game.setWizardMonster((WizardMonster) randomMonster); // Assign the current wizard monster.
                WizardMonster.wizardCount++; // Increment the static wizard count.
            }

        }
    }


}
