package domain.enchantments;

import domain.Player;

public class ExtraTime extends Enchantment{
    public void apply(Player player) {
        player.setRemainingTime(player.getRemainingTime() + 10);
    }
}
