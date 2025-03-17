package physics;

import domain.Player;
import domain.halls.Hall;
import ui.GameModeImageFactory;
import ui.HallUI;
import ui.GameMode;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

public class Movements {
    //This method is used to move the player in the game.
    //It is a static method that takes the pressedKeys, gameMode, and objectBounds as parameters.
    //It takes the pressed keys as a set of integers to have multiple keys pressed at the same time.
    //It also has objectBounds as a list of rectangles to check if the player will collide with any object.
    //To change the speed of the player, you can change the value of the increment in the futureX and futureY variables.
    //After calculating the future position of the player, it updates the player's position by calling the updateplayerPosition method.
    private static Timer imageSwitchTimer;
    private static ImageIcon[] rightImages;
    private static ImageIcon[] leftImages;
    private static int currentImageIndex = 0;
    private static boolean movingRight = true;
    private static boolean isMoving = false;

    public static void initializeImages(GameModeImageFactory imageFactory) {
        rightImages = imageFactory.getRightImages();
        leftImages = imageFactory.getLeftImages();
    }

    public static void playerMovement(Set<Integer> pressedKeys, GameMode gameMode, List<Rectangle> objectBounds) {
        Player player = gameMode.getPlayer();
        int futureX = player.getX();
        int futureY = player.getY();
        Rectangle playerBounds = new Rectangle(futureX, futureY, gameMode.getPlayerLabel().getWidth(), gameMode.getPlayerLabel().getHeight());
        isMoving = false;
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            futureY -= 2;
            playerBounds.setLocation(futureX, futureY);
            if (!willCollide(playerBounds, objectBounds)) {
                player.moveUp();
                isMoving = true;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            futureY += 2;
            playerBounds.setLocation(futureX, futureY);
            if (!willCollide(playerBounds, objectBounds)) {
                player.moveDown();
                isMoving = true;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            futureX -= 2;
            playerBounds.setLocation(futureX, futureY);
            if (!willCollide(playerBounds, objectBounds)) {
                player.moveLeft();
                movingRight = false;
                isMoving = true;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            futureX += 2;
            playerBounds.setLocation(futureX, futureY);
            if (!willCollide(playerBounds, objectBounds)) {
                player.moveRight();
                movingRight = true;
                isMoving = true;
            }
        }
        updatePlayerPosition(gameMode);
    }

    public static boolean willCollide(Rectangle playerBounds, List<Rectangle> objectBounds) {
        for (Rectangle objectBound : objectBounds) {
            if (playerBounds.intersects(objectBound)) {
                return true;
            }
        }
        return false;
    }
    public static void updatePlayerPosition(GameMode game) {
        game.getPlayerLabel().setLocation(game.getPlayer().getX(), game.getPlayer().getY());
        game.getPlayerLabel().setIcon(getCurrentImage());
        game.getFrame().revalidate();
        game.getFrame().repaint();
    }

    public static ImageIcon getCurrentImage() {
        return movingRight ? rightImages[currentImageIndex] : leftImages[currentImageIndex];
    }

    public static Timer startImageSwitchTimer() {
        imageSwitchTimer = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isMoving) {
                    currentImageIndex = (currentImageIndex + 1) % 2;
                }
                isMoving = false;
            }
        });
        imageSwitchTimer.start();
        return imageSwitchTimer;
    }

}