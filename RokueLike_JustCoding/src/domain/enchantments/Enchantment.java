package domain.enchantments;

import java.io.Serializable;

public abstract class Enchantment implements Serializable {
    private static final long serialVersionUID = 1L;
    protected boolean isAvailable;

    // Constructor
    public Enchantment() {
        this.isAvailable = false;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
