package domain.halls;

import domain.Player;
import ui.HallUI;

public class HallOfAir extends Hall {
    public HallOfAir(Player p) {
        super(p); // Air color
        setRequiredItemCount(9);
    }

    public boolean isBuildModeSatisfied() {
        return getItemCount() >= getRequiredItemCount();
    }



}