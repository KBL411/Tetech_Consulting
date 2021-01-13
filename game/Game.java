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

        boolean Winner = false;
        int turns = 0;
        int action ;

        while (!Winner){

            for (game.player player : joueurs){

                //reset the action variable
                action = 0;
                System.out.println(" ");System.out.println("Hello " + player.getName());

                //We continue to play until the player decide to end his turn
                while (action != 2){

                    System.out.println(" ");
                    System.out.println("1. Attack");
                    System.out.println("2. END TURN");
                    System.out.print("What you want to do : ");
                    action = sc.nextInt();

                    if(action == 1 ){

                        player.attackTerritory();

                    }
                    else if(action == 2 ){

                        player.endTurn(turns);

                    }
                    else{

                        System.out.println(" ");System.out.println("Invalid selection");
                        System.out.println("1.Attack");
                        System.out.println("2. END TURN");
                        System.out.print("Please enter a valid choice : ");
                        action = sc.nextInt();

                    }

                }

            }
            turns++;

        }
        //  Display the map (manquante)


    }

}
