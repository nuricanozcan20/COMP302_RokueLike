package domain.strategy;

import domain.monsters.WizardMonster;
import ui.GameMode;

public interface WizardStrategy {
    //This the main interface to implement different strategies for the wizard monster
    void execute(GameMode gameMode, WizardMonster wizard);
    int getWizardType();
    void stopTimer();
    void resumeTimer();
}
