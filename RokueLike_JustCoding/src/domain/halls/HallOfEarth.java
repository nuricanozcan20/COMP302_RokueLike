package domain.halls;

import domain.Player;
import ui.HallUI;

public class HallOfEarth extends Hall {
    public HallOfEarth(Player p) {
        super(p); // Earth color
        setRequiredItemCount(6);
    }

    public boolean isBuildModeSatisfied() {
        return getItemCount() >= getRequiredItemCount();
    }

}