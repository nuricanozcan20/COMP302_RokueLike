package domain.halls;

import domain.Player;
import ui.HallUI;

public class HallOfFire extends Hall {
    private int requiredItemCount = 17;
    public HallOfFire(Player p) {
        super(p);
        setRequiredItemCount(17);
    }

    public boolean isBuildModeSatisfied() {
        return getItemCount() >= getRequiredItemCount();
    }

}