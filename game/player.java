package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class player {
	private int ID;
	private String Name;
	private ArrayList<Integer> Territories;
	private int Nb_dice=10;
	private int Nb_R_dice=10;
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public ArrayList<Integer> getTerritories() {
		return Territories;
	}
	
	public void setTerritories(ArrayList<Integer> territories) {
		Territories = territories;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getNb_dice() {
		return Nb_dice;
	}

	public void setNb_dice(int nb_dice) {
		Nb_dice = nb_dice;
	}
	
	public int getNb_R_dice() {
		return Nb_R_dice;
	}

	public void setNb_R_dice(int nb_R_dice) {
		Nb_R_dice = nb_R_dice;
	}

	@Override
	public String toString() {
		return "Player [ID=" + ID + ", Name=" + Name + ", Territories=" + Territories + "]";
	}

	public player(int id,String name, ArrayList<Integer> territories) {
		super();
		ID =id;
		Name = name;
		Territories = territories;
	}
	public player(int id,String name) {
		super();
		ID =id;
		Name = name;
		Territories = new ArrayList<Integer>();
	}
	
	public void attackTerritory(player p, map m) {

		System.out.println("You Attack");

		// 1. liste de tous les territoire du joueur
		System.out.println(" ");
		System.out.println("Below your territories");
		System.out.println(p.getTerritories());
		System.out.println(" ");

		// 2. choix du territoire attaquant
		Scanner choice = new Scanner(System.in);
		int temp = 0;
		territory terr_att = null;

		do{

			System.out.print("With wich territory do you want to make an attack :");
			temp = choice.nextInt();

		}while(!p.getTerritories().contains(temp));// tant que le territoire choisi appartient bien a la liste


		for(int i = 0 ; i < m.getterritory_list().size(); i++){
			//On retrouve le territoire depuis son ID en parcourant la liste de tt les territoires
			territory temp_terr = m.getterritory_list().get(i);

			if( temp_terr.getId() == temp) {

				terr_att = temp_terr;

			}
		}

		//If the attack territory just have one dice the attack connot be reached
		if(terr_att.getNb_Dice() == 1){

			System.out.println(" ");
			System.out.print("You cannot attack from here, you just have one dice");
			System.out.println(" ");

		}else {

			// 3. liste territoire adjacent au territoire attaquant selectionné
			System.out.println(" ");
			System.out.println("Below the territories that you can attack from your chosen territory");
			ArrayList<Integer> att_neighb = terr_att.getNeighboring_Territories(); // Neighbors - Our territories
			att_neighb.removeAll(p.getTerritories()); //On enleve tout nos territoires pour eviter de pouvoir les attaquer
			System.out.println(att_neighb);


			// 4. Choix du territoire attaqué
			temp = 0;
			territory terr_def = null;
			int attack, defense;

			if(att_neighb.isEmpty()){ // Si il n'y a pas d'attaque possible on ne propose meme pas la selection

				System.out.println(" ");
				System.out.print("No territories can be attack from here");
				System.out.println(" ");

			}else {
				do{

					System.out.print("With which territory do you want to attack :");
					temp = choice.nextInt();

				}while(!att_neighb.contains(temp)); // tant que le territoire choisi appartient bien a la liste


				for(int i = 0 ; i < m.getterritory_list().size(); i++){
					//On retrouve le territoire depuis son ID en parcourant la liste de tt les territoires.
					territory temp_terr = m.getterritory_list().get(i);

					if( temp_terr.getId() == temp) {

						terr_def = temp_terr;

					}
				}

				// 5. On récupere les nombres de dés des territoires et on fait un rolldice pour chaque
				attack = rolldice(terr_att.getNb_Dice());
				defense = rolldice(terr_def.getNb_Dice());

				// 6. on effectue les actions
				if(attack > defense){
					//attacker moves his dice to the conquered territory, except one that
					//remains on the starting territory and the defeated dice of the opponent disappears

					terr_def.setId_Player(terr_att.getId_Player()); //changer l'appartenance du territoire
					terr_def.setNb_Dice(terr_att.getNb_Dice()-1);
					terr_att.setNb_Dice(1);

					System.out.println("Attack succeed,  Score : " + attack + " / " + defense);
					System.out.println(" ");

				}else if (attack < defense){
					// (attacker) only keeps one die in his territory and the attacked territory remains unchanged
					terr_att.setNb_Dice(1);
					System.out.println("Attack failed,  Score : " + attack + " / " + defense);
					System.out.println(" ");

				}else{
					terr_att.setNb_Dice(1);
					terr_def.setNb_Dice(1);
					System.out.println("It's a draw, Score : " + attack + " / " + defense);
					System.out.println(" ");

				}

				// 7. Display the new map (in the Game/Main file)
			}
		}
	}
	
	public int rolldice(int Nb_dice) {
		Random rand = new Random();
		int tot = 0;
		int temp;

		for (int i = 0 ; i<Nb_dice ; i++){
			temp = rand.nextInt(6) + 1;
			tot += temp;
		}

		return tot;
	}
	
	public int endTurn(int turn) {
		System.out.println("Your turns n" + turn + " ends here");
		//  Display the map (missing)
		return 0;
	}
	

}