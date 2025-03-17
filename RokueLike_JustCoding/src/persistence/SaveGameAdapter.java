package persistence;

import domain.Player;
import domain.halls.Hall;
import domain.monsters.Monster;
import domain.strategy.WizardStrategy;
import ui.HallUI;


import java.util.List;

public interface SaveGameAdapter {
    void saveGameState(String fileName, Player player, List<Hall> halls, List<Monster> monsters);
    Object[] loadGameState(String fileName);
}
