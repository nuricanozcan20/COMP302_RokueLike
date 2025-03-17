package domain.halls;

import domain.Player;
import ui.HallUI;

public class HallOfWater extends Hall {
    public HallOfWater(Player p) {
        super(p);
        setRequiredItemCount(13);
    }

    public boolean isBuildModeSatisfied() {
        return getItemCount() >= getRequiredItemCount();
    }


}