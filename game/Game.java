package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static void main(String [] args) {

        // Input number of player
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number of Player for your DiceWars game : ");
        int NB_players = sc.nextInt();

        // Checking
        while(NB_players > 8 || NB_players == 0){

            System.out.println(" ");System.out.println("Invalid number of players");
            System.out.print("Please enter a valid number of players : ");
            NB_players = sc.nextInt();

        }

        //  Initialization of the map (manquante)


        //list of players
        ArrayList<player> joueurs = new ArrayList<player>();

        //  Initialization of Players
        for (int i = 1; i <= NB_players; i++) {

            System.out.print("Please enter a name for the player n°" + i + " : ");
            String temp = sc.next();
            joueurs.add(new player(i, temp));

        }

        //To display the list
        //for (game.player player : joueurs) System.out.println(player);

        //  Display the map
        System.out.print("Bravo Chacal tu as un jeu avec " + NB_players + " joueurs.");
    }

}
