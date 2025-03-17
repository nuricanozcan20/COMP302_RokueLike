package domain.strategy;

import domain.monsters.Monster;
import domain.monsters.WizardMonster;
import ui.GameMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;

public class TeleportPlayerStrategy implements WizardStrategy, Serializable {
    private static final long serialVersionUID = 1L;
    //This is the strategy to teleport the player when
    //the remaning time is less than %30 of the initial time
    private Timer teleportTimer;
    private Timer disappearTimer;
    @Override
    public void execute(GameMode gameMode, WizardMonster wizard) {
        //Teleport to player
        teleportTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teleportPlayer(gameMode, wizard);
            }
        });
        teleportTimer.setRepeats(false); // Ensure the timer only runs once
        teleportTimer.start();

        disappearTimer = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode.removeMonster(wizard);
            }
        });
        disappearTimer.setRepeats(false); // Ensure the timer only runs once
        disappearTimer.start();
    }

    public void teleportPlayer(GameMode gameMode, WizardMonster wizard) {
        int minX = 10;
        int minY = 20;
        int maxX = 700 - 26;
        int maxY = 640 - 46;
        Random random = new Random();
        int randomX = random.nextInt(maxX - minX + 1) + minX;
        int randomY = random.nextInt(maxY - minY + 1) + minY;

        boolean collision = true;
        while (collision) {
            randomX = random.nextInt(maxX - minX + 1) + minX;
            randomY = random.nextInt(maxY - minY + 1) + minY;

            int counter = 0;
            for (JLabel label : gameMode.getHall().getUIController().getSavedLabels()) {
                if (gameMode.getPlayerLabel().getBounds().intersects(label.getBounds())) {
                    collision = true;
                    counter++;
                    break;
                }
            }
            for (Monster monster : gameMode.getMonsters()) {
                if (monster.getMonsterLabel().getBounds().intersects(gameMode.getPlayerLabel().getBounds())) {
                    collision = true;
                    counter++;
                    break;
                }
            }
            if (counter == 0) {
                collision = false;
            }
        }

        gameMode.getPlayer().setX(randomX);
        gameMode.getPlayer().setY(randomY);
    }

    @Override
    public int getWizardType(){
        return 1;
    }

    public void stopTimer(){
        if(teleportTimer != null){
            teleportTimer.stop();
        }
        if(disappearTimer != null){
            disappearTimer.stop();
        }
    }
    public void resumeTimer(){
        if(teleportTimer != null){
            teleportTimer.start();
        }
        if(disappearTimer != null){
            disappearTimer.start();
        }
    }
}
