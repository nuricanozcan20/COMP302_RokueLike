package ui;

import domain.Player;
import domain.enchantments.*;
import domain.monsters.ArcherMonster;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class EnchantmentFeatures {

    public static void collectExtraLife(GameMode gameMode) {
        /**
         * Collects the extra life enchantment if the player collides with the extra life label.
         *
         * REQUIRES:
         * - `extraLifeLabel` must be visible and initialized on the game screen.
         * - The `player` object must be properly initialized.
         *
         * MODIFIES:
         * - Hides the `extraLifeLabel` from the game screen.
         * - Increases the player's health or life.
         * - Updates the feature panel in the game UI.
         *
         * EFFECTS:
         * - Adds an extra life to the player.
         * - Plays a sound effect to indicate the collection of the extra life.
         * - Updates the game UI to reflect the player's new health.
         */
        if (gameMode.getPlayer().getHealth() >= 5) {
            return;
        }
        JLabel extraLifeLabel = gameMode.getExtraLifeLabel();
        ExtraLife extraLife = new ExtraLife();
        if (extraLifeLabel.isVisible()) {

            extraLifeLabel.setVisible(false); // Hide the extra life image after collection
            extraLife.apply(gameMode.getPlayer());

            gameMode.updateFeatures();

            // Play the sound for collecting extra life
            ResourceLoader.playSound("assets/sounds/getlife.wav");

            System.out.println("Extra Life collected! Player health: " + gameMode.getPlayer().getHealth());
        }
    }

    public static void collectExtraTime(GameMode gameMode) {
        JLabel extraTimeLabel = gameMode.getExtraTimeLabel();
        Player player = gameMode.getPlayer();
        ExtraTime extraTime = new ExtraTime();
        if (extraTimeLabel.isVisible()) {
            extraTimeLabel.setVisible(false); // Hide the extra life image after collection
            extraTime.apply(player);

            gameMode.updateFeatures();
            // Play the sound for collecting extra life
            ResourceLoader.playSound("assets/sounds/ticking.wav");
        }
    }


    public static void collectLuringGem(GameMode gameMode) {
        JLabel luringGemLabel = gameMode.getLuringGemLabel();
        if (luringGemLabel.isVisible()) {
            if (gameMode.getPlayer().isInventoryFull()) {
                System.out.println("Player's inventory is full!");
            }
            else {
                luringGemLabel.setVisible(false); // Hide the luring gem from the game screen
                System.out.println("Luring Gem collected!");
                LuringGem luringGem1 = new LuringGem();
                luringGem1.setCollectable(false);
                if (gameMode.getPlayer().addEnchantment(luringGem1)) {
                    gameMode.updateFeatures();
                }
            }
        }
    }

    public static void collectCloak(GameMode gameMode) {
        JLabel cloakLabel = gameMode.getCloakLabel();
        if (cloakLabel.isVisible()) {
            if (gameMode.getPlayer().isInventoryFull()) {
                System.out.println("Player's inventory is full!");
            }
            else {
                //cloakOfProtection.activate();
                cloakLabel.setVisible(false); // Hide the cloak from the game screen
                System.out.println("Cloak of Protection collected!");
                if (gameMode.getPlayer().addEnchantment(new CloakOfProtection())) {
                    gameMode.updateFeatures();
                }
            }
        }
    }

    public static void collectRevealEnchantment(GameMode gameMode, Reveal reveal) {
        JLabel revealLabel = gameMode.getRevealLabel();
        if (revealLabel.isVisible()) {
            if (gameMode.getPlayer().isInventoryFull()) {
                System.out.println("Player's inventory is full!");
            }
            else {
                reveal.collect();
                gameMode.getFrame().getContentPane().remove(revealLabel);
                gameMode.getFrame().revalidate();
                gameMode.getFrame().repaint();
                revealLabel.setVisible(false); // Hide the reveal from the game screen
                System.out.println("Reveal enchantment collected!");
                if (gameMode.getPlayer().addEnchantment(reveal)) {
                    gameMode.updateFeatures();
                }
            }
        }
    }

    public static void useLuringGem(String direction,GameMode gameMode) {
        /**
         * Uses the Luring Gem enchantment from the player's inventory and moves it in the specified direction.
         *
         * REQUIRES:
         * - The `player` object and its inventory must be properly initialized.
         * - The `frame` (game GUI) must be initialized and capable of displaying the Luring Gem label.
         * - The `direction` parameter must be one of the following: "W" (up), "A" (left), "S" (down), or "D" (right).
         *
         * MODIFIES:
         * - The player's inventory (removes the used Luring Gem).
         * - The game frame (`frame`):
         *   - Adds a new JLabel representing the Luring Gem and moves it in the specified direction.
         * - The `featurePanel` (updates it after using the Luring Gem).
         *
         * EFFECTS:
         * - If a Luring Gem exists in the player's inventory:
         *   - Activates the Luring Gem and moves it visually in the specified direction on the game frame.
         *   - Removes the Luring Gem from the player's inventory.
         *   - Plays a sound to indicate the activation of the Luring Gem.
         *   - Updates the feature panel to reflect the change in the player's inventory.
         * - If no Luring Gem exists in the player's inventory, no action is taken.
         * - Ensures the Luring Gem does not move outside the bounds of the game frame.
         */
        Player player = gameMode.getPlayer();
        JFrame frame = gameMode.getFrame();
        for (int i = 0; i < player.getInventory().length; i++) {
            if (player.getInventory()[i] instanceof LuringGem) {
                LuringGem luringGem = (LuringGem) player.getInventory()[i];

                luringGem.use(player.getPosition(), direction); // Pass player's position
                BufferedImage luringGemImage = ResourceLoader.loadImage("assets/features/luringgem.png");
                ImageIcon luringGemIcon = new ImageIcon(luringGemImage);
                JLabel usingluringGemLabel = new JLabel(luringGemIcon);

                usingluringGemLabel.setBounds(player.getX(), player.getY(), luringGemIcon.getIconWidth(), luringGemIcon.getIconHeight());
                frame.getContentPane().add(usingluringGemLabel);
                frame.getContentPane().setComponentZOrder(usingluringGemLabel, 1);
                frame.revalidate();
                frame.repaint();
                gameMode.moveLuringGem(direction, usingluringGemLabel); // Move the luring gem in the specified direction
                if (player.removeEnchantment(luringGem)) {
                    gameMode.updateFeatures();
                }
                ResourceLoader.playSound("assets/sounds/use_luring_gem.wav"); // Play the luring gem activation sound
                luringGem.setAvailable(false);
                break;
            }
        }
    }

    public static void activateCloak(GameMode gameMode,CloakOfProtection cloakOfProtection,Map<ArcherMonster,ArcherAttack> archerAttacks) {
        /**
         * Activates the Cloak of Protection enchantment if available in the player's inventory.
         *
         * REQUIRES:
         * - The player must have a valid `CloakOfProtection` instance in their inventory.
         * - The player's inventory and archer-related objects (`archerAttacks`) must be properly initialized.
         *
         * MODIFIES:
         * - The player's inventory (removes the `CloakOfProtection` if used).
         * - The `canSeePlayer` and shooting status of all archer monsters.
         * - The feature panel in the game UI.
         *
         * EFFECTS:
         * - Activates the Cloak of Protection, hiding the player from all archer monsters for 20 seconds.
         * - Plays a sound to indicate activation.
         * - Temporarily disables all archer monsters' ability to see and shoot at the player.
         * - Highlights all archer monsters during the cloak's active duration.
         * - After 20 seconds, restores archers' ability to see and attack the player, and removes highlights.
         */
        Player player = gameMode.getPlayer();
        for (int i = 0; i < player.getInventory().length; i++) {
            if (player.getInventory()[i] instanceof CloakOfProtection) {
                // isCloakActive = true;
                System.out.println("Cloak of Protection activated! Player is hidden from the archers.");

                if (player.removeEnchantment(cloakOfProtection)) {
                    gameMode.updateFeatures();
                }

                ResourceLoader.playSound("assets/sounds/use_cloak.wav"); // Play the cloak activation sound

                // Stop all archers from shooting and highlight them
                for (Map.Entry<ArcherMonster, ArcherAttack> entry : archerAttacks.entrySet()) {
                    ArcherMonster archer = entry.getKey();
                    ArcherAttack archerAttack = entry.getValue();
                    archer.setCanSeePlayer(false); // Disable archer's ability to see the player
                    archer.stopShooting(); // Stop the archer from shooting arrows
                    archerAttack.highlightArcher(archer.getMonsterLabel(), true); // Highlight the archer
                }

                // Schedule deactivation after 20 seconds
                Timer cloakTimer = new Timer(20000, e -> {
                    // isCloakActive = false;
                    for (Map.Entry<ArcherMonster, ArcherAttack> entry : archerAttacks.entrySet()) {
                        ArcherMonster archer = entry.getKey();
                        ArcherAttack archerAttack = entry.getValue();
                        archer.setCanSeePlayer(true); // Re-enable archer's ability to see the player
                        archer.resumeShooting(); // Resume the archer's shooting
                        archerAttack.highlightArcher(archer.getMonsterLabel(), false); // Remove the highlight
                    }
                    System.out.println("Cloak of Protection has expired.");
                });
                cloakOfProtection.use();
                cloakTimer.setRepeats(false);
                cloakTimer.start();
                gameMode.getTimers().add(cloakTimer);
                break;
            }

        }
    }

    public static void revealRuneHint(GameMode gameMode,Reveal reveal,JLabel runeLabel,Rectangle hintRectangle,JPanel drawingPanel) {
        Player player = gameMode.getPlayer();
        for (int i = 0; i < player.getInventory().length; i++) {
            if (player.getInventory()[i] instanceof Reveal) {
                if (reveal.isAvailable()) {
                    ResourceLoader.playSound("assets/sounds/reveal_activation.wav"); // Play sound when reveal is activated

                    if (player.removeEnchantment(reveal)) {
                        gameMode.updateFeatures();
                    }

                    // Calculate the hint rectangle (4x4 squares)
                    int hintX = Math.max(0, runeLabel.getX() - 100);
                    int hintY = Math.max(0, runeLabel.getY() - 100);
                    int hintWidth = 200;
                    int hintHeight = 200;

                    gameMode.setHintRectangle(new Rectangle(hintX, hintY, hintWidth, hintHeight));

                    // Schedule deactivation after 10 seconds
                    Timer hintTimer = new Timer(10000, e -> {
                        reveal.deactivate();
                        System.out.println(reveal.isAvailable());
                        gameMode.setHintRectangle(null); // Clear the hint rectangle
                        drawingPanel.repaint(); // Repaint to remove the rectangle
                    });
                    hintTimer.setRepeats(false);
                    hintTimer.start();
                    gameMode.getTimers().add(hintTimer);

                    drawingPanel.repaint(); // Trigger a repaint to show the rectangle
                }
                break;
            }
        }
    }
}
