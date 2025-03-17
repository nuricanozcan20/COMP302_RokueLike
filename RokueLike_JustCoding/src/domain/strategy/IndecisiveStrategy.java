package domain.strategy;

import domain.monsters.WizardMonster;
import ui.GameMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IndecisiveStrategy implements WizardStrategy{
    private Timer timer;
    @Override
    public void execute(GameMode gameMode, WizardMonster wizard) {
        timer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode.removeMonster(wizard);
                WizardMonster.wizardCount--;
            }
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
    }

    @Override

    public int getWizardType(){
        return 3;
    }

    @Override
    public void stopTimer() {
        timer.stop();
    }

    @Override
    public void resumeTimer() {
        timer.start();
    }
}
