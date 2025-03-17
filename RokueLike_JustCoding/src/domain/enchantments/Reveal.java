package domain.enchantments;

public class Reveal extends Enchantment {
    private boolean isAvailable; // If the enchantment is collected and available
    private boolean isActive; // If the enchantment is currently being used
    private long activationTime; // Time when the enchantment was activated
    private static final int DURATION = 10000; // Duration in milliseconds (10 seconds)

    public Reveal() {
        this.isAvailable = true;
        this.isActive = true;
        this.activationTime = 0;
    }

    public void collect() {
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        if (isAvailable && !isActive) {
            isActive = true;
            activationTime = System.currentTimeMillis();
        }
    }

    public void deactivate() {
        this.isActive = false;
        this.isAvailable = false;
    }

    public boolean shouldDeactivate() {
        return isActive && (System.currentTimeMillis() - activationTime >= DURATION);
    }
}