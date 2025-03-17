package ui;

import domain.Player;
import domain.halls.HallOfEarth;
import domain.halls.HallOfFire;
import domain.halls.HallOfWater;
import domain.halls.HallOfAir;

public class Main {

    public static void main(String[] args) {

        Player player = new Player();
        HallOfEarth hall1 = new HallOfEarth(player);
        HallOfAir hall2 = new HallOfAir(player);
        HallOfWater hall3 = new HallOfWater(player);
        HallOfFire hall4 = new HallOfFire(player);

        MainMenu mainMenu = new MainMenu(player);
        }
    }
