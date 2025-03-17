package domain.enchantments;

public class CloakOfProtection extends Enchantment {

    // Constructor
    public CloakOfProtection() {
        this.isAvailable = false; // Cloak is not available initially
    }

    // Method to activate the cloak (marks it as collected or ready to use)
    public void activate() {
        if (!isAvailable) {
            this.isAvailable = true; // Only activate if not already available
            System.out.println("Cloak of Protection activated!");
        }else {
            System.out.println("Cloak of Protection is already active.");
        }
    }

    // Method to use the cloak (marks it as unavailable after usage)
    public void use() {
        this.isAvailable = false; // Mark cloak as used
        System.out.println("Cloak of Protection has been used!");
        /*
        if (isAvailable) {
            this.isAvailable = false; // Mark cloak as used
            System.out.println("Cloak of Protection has been used!");
        } else {
            System.out.println("Cloak of Protection is not available.");
        }
        */
    }
}