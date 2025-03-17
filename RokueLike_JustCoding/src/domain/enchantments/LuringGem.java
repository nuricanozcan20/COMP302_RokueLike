package domain.enchantments;

import java.awt.Point;

public class LuringGem extends Enchantment {

    private boolean isAvailable; // Indicates if the luring gem is available for use
    private boolean isCollectable;
    private boolean isActive;

    // Constructor
    public LuringGem() {
        this.isAvailable = true; // Luring gem is not available initially
        this.isCollectable = true;
        this.isActive = false;
    }

    // Method to activate the luring gem (marks it as collected or ready to use)
    public void activate() {
        if (!isAvailable) {
            this.isAvailable = true; // Only activate if not already available
            System.out.println("LuringGem activated!");
        } else {
            System.out.println("LuringGem is already active.");
        }
    }

    // Method to check if the luring gem is available
    public boolean isAvailable() {
        return isAvailable;
    }

    // Method to use the luring gem
    public void use(Point playerPosition, String direction) {
        if (isAvailable) {
            this.isCollectable = false;
            this.isAvailable = false; // Mark the luring gem as used
            throwLure(playerPosition, direction); // Throw the lure from the player's position in the specified direction
            System.out.println("Luring Gem used successfully in direction: " + direction);
        } else {
            System.out.println("Luring Gem is not available.");
        }
    }

    // Method to simulate throwing the lure from the player's position in the specified direction
    private void throwLure(Point playerPosition, String direction) {
        System.out.println("Luring Gem thrown from position: " + playerPosition);
        switch (direction.toUpperCase()) {
            case "A": // Left
                System.out.println("Luring Gem thrown to the left!");
                break;
            case "D": // Right
                System.out.println("Luring Gem thrown to the right!");
                break;
            case "W": // Up
                System.out.println("Luring Gem thrown upwards!");
                break;
            case "S": // Down
                System.out.println("Luring Gem thrown downwards!");
                break;
            default:
                System.out.println("Invalid direction for Luring Gem!");
                break;
        }
    }

    public boolean isCollectable() {
        return isCollectable;
    }

    public void setCollectable(boolean collectable) {
        this.isCollectable = collectable;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
        {
        }
    }
    public boolean getIsActive() {
        return isActive;
    }
}