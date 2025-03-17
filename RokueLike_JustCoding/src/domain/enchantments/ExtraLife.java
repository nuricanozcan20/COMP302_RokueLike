package domain.enchantments;

import domain.Player;

public class ExtraLife extends Enchantment {

    public ExtraLife() {
        this.isAvailable = true;
    }

    public void apply(Player player) {
        player.increaseHealth();
    }

}