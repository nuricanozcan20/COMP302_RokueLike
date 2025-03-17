package domain.monsters;

import domain.Player;

import javax.swing.*;
import java.io.Serializable;

public class Monster implements Serializable {
    private static int monsterCount = 0;
    private int monsterID;
    private int x;
    private int y;
    private static final long serialVersionUID = 1L;
    private JLabel monsterLabel;
    private Player player;

    public Monster(JLabel monsterLabel, Player player, int x, int y) {
        this.monsterLabel = monsterLabel;
        this.player = player;
        this.x = x;
        this.y = y;
        monsterID = monsterCount++;
    }

    public JLabel getMonsterLabel() {
        return monsterLabel;
    }

    public Player getPlayer() {
        return player;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getMonsterID() {
        return monsterID;
    }
}
