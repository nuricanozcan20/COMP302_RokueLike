package domain.monsters;

import domain.Player;
import domain.strategy.IndecisiveStrategy;
import domain.strategy.TeleportPlayerStrategy;
import domain.strategy.TeleportRuneStrategy;
import domain.strategy.WizardStrategy;

import javax.swing.*;
import java.awt.*;

public class WizardMonster extends Monster {
    private boolean isActive; // Wizard state
    private int width = 50; // Width of the wizard's sprite
    private int height = 50; // Height of the wizard's sprite
    public static int wizardCount = 0; // Count of wizard monsters
    private WizardStrategy strategy;

    // Constructor
    public WizardMonster(JLabel monsterLabel, Player player) {
        super(monsterLabel,player,monsterLabel.getX(),monsterLabel.getY());
        this.isActive = false;
        determineWizardStrategy();
    }

    // Check collision with another rectangle (e.g., player's position)
    public boolean collidesWith(Rectangle otherBounds) {
        Rectangle wizardBounds = new Rectangle(getX(), getY(), width, height);
        return wizardBounds.intersects(otherBounds);
    }

    public WizardStrategy getStrategy() {
        return strategy;
    }
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getMonsterLabel().getWidth(), getMonsterLabel().getHeight());
    }
    public void setActive(boolean active){
        this.isActive = active;
    }


    //This method is used to determine the strategy of the wizard monster.
    //It checks the remaining time of the player and sets the strategy accordingly.
    //If the remaining time is less than 30% of the total time, it sets the TeleportPlayerStrategy.
    //If the remaining time is more than 70% of the total time, it sets the TeleportRuneStrategy.
    //Otherwise, it sets the IndecisiveStrategy.
    //It does not change the strategy if the current strategy is already active.
    //This method is called in the constructor of the WizardMonster class to determine the strategy when the monster is created.
    //This method also called in the GameMode with a timer to update the strategy of the wizard monster.
    //It is dynamically changed according to the remaining time of the player.
    public void determineWizardStrategy(){
        int remainingTime = getPlayer().getRemainingTime();
        int totalTime = getPlayer().getInitialTime();

        if (remainingTime < (totalTime * 0.3)) {
            if(!(strategy instanceof TeleportPlayerStrategy)){
                if(strategy!=null){
                    strategy.stopTimer();
                }
                strategy = new TeleportPlayerStrategy();
                setActive(false);
            }
        }
        else if (remainingTime > (totalTime * 0.7)) {
            if(!(strategy instanceof TeleportRuneStrategy)){
                if(strategy!=null){
                    strategy.stopTimer();
                }
                strategy = new TeleportRuneStrategy();
                setActive(false);
            }
        }
        else {
            if(!(strategy instanceof IndecisiveStrategy)){
                if(strategy!=null){
                    strategy.stopTimer();
                }
                strategy = new IndecisiveStrategy();
                setActive(false);
            }
        }
    }
}