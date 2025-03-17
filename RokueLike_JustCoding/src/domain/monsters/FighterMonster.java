package domain.monsters;

import domain.Player;
import ui.GameMode;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.List;

public class FighterMonster extends Monster {
    private Random random;
    private int directionX;
    private int directionY;
    private int steps;
    private static final int MAX_STEPS = 50; // Number of steps in one direction
    private static final int MIN_X = 50;
    private static final int MIN_Y = 20;
    private static final int MAX_X = 700;
    private static final int MAX_Y = 640;
    public static int fighterCount = 0; // Count of fighter monsters

    public FighterMonster(int startX, int startY, JLabel monsterLabel, Player player) {
        super(monsterLabel, player, startX, startY);
        this.random = new Random();
        this.steps = 0;
        setRandomDirection();
    }

    private void setRandomDirection() {
        this.directionX = random.nextInt(5) - 2; // Random value between -2 and 2
        this.directionY = random.nextInt(5) - 2; // Random value between -2 and 2
    }

    //This method is used to move the FighterMonster randomly in the hall.
    //It has several parameters to prevent the monster from colliding with the player or other monsters or hall objects.
    //It also has a random movement pattern to make the monster move in a random direction.
    //It determines a random step count and a random direction.
    //It moves the monster in the random direction until the step count is reached.
    //If the monster collides with the player or other monsters or hall objects, it changes the direction.
    //It also checks if the monster is going out of bounds and prevents it from moving out of bounds.
    public void moveRandomly(Rectangle playerBounds, List<JLabel> savedLabels, List<Monster> monsters) {
        if (steps >= MAX_STEPS) {
            setRandomDirection();
            steps = 0;
        }

        int futureX = getX() + directionX;
        int futureY = getY() + directionY;
        Rectangle futureBounds = new Rectangle(futureX, futureY, getMonsterLabel().getWidth(), getMonsterLabel().getHeight());

        boolean collision = false;
        for (JLabel label : savedLabels) {
            if (futureBounds.intersects(label.getBounds())) {
                collision = true;
                break;
            }
        }
        for (Monster monster : monsters) {
            if (monster.getMonsterID() == this.getMonsterID()) {
                continue;
            }
            if (futureBounds.intersects(monster.getMonsterLabel().getBounds())) {
                collision = true;
                break;
            }
        }

        if (!collision && !futureBounds.intersects(playerBounds)) {
            setX(Math.max(MIN_X, Math.min(futureX, MAX_X - getMonsterLabel().getWidth())));
            setY(Math.max(MIN_Y, Math.min(futureY, MAX_Y - getMonsterLabel().getHeight())));
            steps++;
        } else {
            setRandomDirection();
            steps = 0;
        }

        getMonsterLabel().setLocation(getX(), getY());
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getMonsterLabel().getWidth(), getMonsterLabel().getHeight());
    }

    public boolean isCollision(Rectangle rectangle) {
        return getBounds().intersects(rectangle);
    }

    public void moveTowardsLuringGem(GameMode gameMode) {
        JLabel luringGemLabel = gameMode.getMovingLuringGemLabel();
        int monsterX = this.getX();
        int monsterY = this.getY();
        int gemX = luringGemLabel.getX();
        int gemY = luringGemLabel.getY();

        int stepSize = 2; // Adjust step size as needed
        int deltaX = gemX - monsterX;
        int deltaY = gemY - monsterY;

        if (Math.abs(deltaX) > stepSize) {
            monsterX += (deltaX > 0) ? stepSize : -stepSize;
        }
        if (Math.abs(deltaY) > stepSize) {
            monsterY += (deltaY > 0) ? stepSize : -stepSize;
        }

        Rectangle futureBounds = new Rectangle(monsterX, monsterY, getMonsterLabel().getWidth(), getMonsterLabel().getHeight());

        boolean collision = false;
        for (JLabel label : gameMode.getSavedLabels()) {
            if (futureBounds.intersects(label.getBounds())) {
                collision = true;
                break;
            }
        }
        for (Monster monster : gameMode.getMonsters()) {
            if (monster.getMonsterID() == this.getMonsterID()) {
                continue;
            }
            if (futureBounds.intersects(monster.getMonsterLabel().getBounds())) {
                collision = true;
                break;
            }
        }

        if (!collision) {
            this.setX(monsterX);
            this.setY(monsterY);
            getMonsterLabel().setLocation(monsterX, monsterY);
        }
    }
}

