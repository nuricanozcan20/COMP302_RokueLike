/**
THE OVERVIEW OF THE PLAYER CLASS
The Player class represents the player in roKUlike game.
It contains the player's position, health, level, inventory, and remaining time.
It also has the initial position to put player on the grid.
The player can move up, down, left, and right. The position of the player is updated accordingly.
The player can also add and remove enchantments from the inventory.
The player's health is decreased when the player is hit by an enemy.
The player's level is increased when the player finds the rune and passes to other hall.
This instance is created only once throughout the running, and it is an example of Singleton pattern.
*/

/**
 * Abstract Function:
 * AF(c) = A player with:
 *   - Position: (c.x, c.y)
 *   - Health: c.health
 *   - Level: c.level
 *   - Inventory: c.inventory (array of Enchantment)
 *   - Remaining Time: c.remainingTime
 *   - Initial Time: c.initialTime
 *   - Initial Position: (c.initialX, c.initialY)
 *   - Movement boundaries: (c.minX, c.minY, c.maxX, c.maxY)
 */

package domain;
import domain.enchantments.*;

import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int health;
    private int level;
    private Enchantment[] inventory;
    public int initialTime;
    public int remainingTime;
    private final int initialX = 640;
    private final int initialY = 360;

    public Player() {
        x = initialX;
        y = initialY;
        minX = 10;
        minY = 20;
        maxX = 700 - 26;
        maxY = 640 - 46;
        health = 3;
        level = 1;
        inventory = new Enchantment[6];
    }

    public int getInitialTime() {
        return initialTime;
    }

    public int getremainingTime() {
        return remainingTime;
    }

    public boolean addEnchantment(Enchantment enchantment) {
        /**
         * Adds an enchantment to the player's inventory.
         *
         * Requires: `enchantment` is not null.
         * Modifies: `inventory` array if there's an empty slot.
         * Effects: If there's space in the inventory, adds the enchantment
         *          and returns true. Otherwise, prints "Inventory is full!"
         *          and returns false.
         */
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = enchantment;
                System.out.println("Enchantment added to inventory successfully!");
                return true;
            }
        }
        System.out.println("Inventory is full!");
        return false;
    }

    public boolean removeEnchantment(Enchantment enchantment) {
        if (enchantment instanceof Reveal) {
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] instanceof Reveal) {
                    inventory[i] = null;
                    System.out.println("Enchantment removed from inventory succesfully!");
                    return true;
                }
            }
        }
        else if (enchantment instanceof LuringGem) {
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] instanceof LuringGem) {
                    inventory[i] = null;
                    System.out.println("Enchantment removed from inventory succesfully!");
                    return true;
                }
            }
        }
        else if (enchantment instanceof CloakOfProtection) {
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] instanceof CloakOfProtection) {
                    inventory[i] = null;
                    System.out.println("Enchantment removed from inventory succesfully!");
                    return true;
                }
            }
        }
        System.out.println("Enchantment not found in inventory!");
        return false;
    }

    public Enchantment[] getInventory() {
        return inventory;
    }

    public void setInventory(Enchantment[] inventory) {
        this.inventory = inventory;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveUp() {
        if (y - 2 >= minY) {
            y -= 2; // Adjust the value as needed
        }
    }

    public void moveDown() {
        if (y + 2 <= maxY) {
            y += 2;
        }
    }

    public void moveLeft() {
        if (x - 2 >= minX) {
            x -= 2;
        }
    }

    public void moveRight() {
        if (x + 2 <= maxX) {
            x += 2;
        }
    }

    public boolean isInventoryFull() {
        int counter = 0;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                counter++;
            }
        }

        return counter == 6;
    }

    public void decreaseHealth() {
        if (health > 0) {
            health--;
        }
    }

    public void increaseHealth() {
        health++;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public void increaseLevel() {
        level++;
    }

    public int getRemainingTime() {
        return remainingTime;
    }
    public void decreaseRemainingTime() {
        remainingTime--;
    }
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }


    public int getInitialX() {
        return initialX;
    }
    public int getInitialY() {
        return initialY;
    }

    public boolean repOk() {
        if (x < minX || x > maxX || y < minY || y > maxY) {
            return false;
        }
        if (health < 0) {
            return false;
        }
        if (inventory.length > 6) {
            return false;
        }
        if (remainingTime < 0) {
            return false;
        }
        for (Enchantment e : inventory) {
            if (e != null && !(e instanceof Enchantment)) return false; // Invalid inventory item
        }
        return true;
    }
    public int getMinX() {
        return minX;
    }
    public int getMinY() {
        return minY;
    }
    public int getMaxX() {
        return maxX;
    }
    public int getMaxY() {
        return maxY;
    }

    public void resetPlayer() {
        x = initialX;
        y = initialY;
        health = 3;
        level = 1;
        inventory = new Enchantment[6];
        this.initialTime = 100;
        this.remainingTime = 100;
    }

    public void setInitialTime(int i) {
        this.initialTime = i;
    }
}