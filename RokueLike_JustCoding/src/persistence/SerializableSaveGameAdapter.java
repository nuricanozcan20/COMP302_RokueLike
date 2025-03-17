package persistence;

import domain.Player;
import domain.halls.Hall;
import domain.monsters.Monster;
import domain.strategy.WizardStrategy;

import java.io.*;
import java.util.List;

public class SerializableSaveGameAdapter implements SaveGameAdapter {


    @Override
    public void saveGameState(String fileName, Player player, List<Hall> halls, List<Monster> monsters) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(player);
            oos.writeObject(halls);
            oos.writeObject(monsters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object[] loadGameState(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Player player = (Player) ois.readObject();
            List<Hall> halls = (List<Hall>) ois.readObject();
            List<Monster> monsters = (List<Monster>) ois.readObject();
            return new Object[]{player, halls,monsters};
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}


