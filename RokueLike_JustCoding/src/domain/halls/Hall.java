package domain.halls;

import domain.Player;
import ui.HallUI;

import java.io.Serializable;

public abstract class Hall implements Serializable {
    private static final long serialVersionUID = 1L;
    private Player player;
    private int itemCount;
    private boolean isDoorOpen;
    private int requiredItemCount;

    public HallUI getUIController() {
        return uiController;
    }

    public void setUIController(HallUI uiController) {
        this.uiController = uiController;
    }

    private HallUI uiController;
    public Hall(Player p) {
        itemCount = 0;
        player = p;
        isDoorOpen = false;
        uiController = new HallUI(this);
    }



    public void increaseItemCount() {
        itemCount++;
    }

    public int getItemCount() {
        return itemCount;
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public void openDoor() {
        isDoorOpen = true;
    }

    public void setRequiredItemCount(int requiredItemCount) {
        this.requiredItemCount = requiredItemCount;
    }
    public int getRequiredItemCount() {
        return requiredItemCount;
    }

}
