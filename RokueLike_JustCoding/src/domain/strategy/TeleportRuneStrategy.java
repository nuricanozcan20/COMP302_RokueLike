package domain.strategy;

import domain.monsters.WizardMonster;
import ui.GameMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class TeleportRuneStrategy implements WizardStrategy, Serializable {
    private static final long serialVersionUID = 1L;
    //This is the part where the remaining time is bigger than the %70 percent of the initial time
    private Timer teleportTimer;
    @Override
    public void execute(GameMode gameMode, WizardMonster wizard) {
        // Teleport the rune
        teleportTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode.teleportRune();
            }
        });
        teleportTimer.start();

    }
    public void stopTimer(){
        if(teleportTimer != null){
            teleportTimer.stop();
        }
    }
    public int getWizardType(){
        return 2;
    }
    public void resumeTimer(){
        if(teleportTimer != null){
            teleportTimer.start();
        }
    }

}
