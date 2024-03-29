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
	
	public void attackTerritory(player p, map m,ArrayList<player> ps) {

		System.out.println("You Attack");

		// 1. liste de tous les territoire du joueur
		String power ="";
		
		for(territory t : m.getterritory_list()) {
			if(p.getTerritories().contains(t.getId())) {
				power =power+"territoire n� "+t.getId()+"|| NB of dice "+t.getNb_Dice()+"\n";
			}
		}
		System.out.println(power);

		// 2. choix du territoire attaquant
		Scanner choice = new Scanner(System.in);
		int temp = 0;
		territory terr_att = null;

		do{

			System.out.print("With wich territory do you want to make an attack :");

			// Checking the input type
			while (choice.hasNext()) {

				if (choice.hasNextInt()) {

					temp = choice.nextInt();
					break;

				}

				choice.next();
				System.out.println(" "); System.out.println("Invalid input type");
				System.out.print("Please, enter a INTEGER for your choice : ");
			}

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

			// 3. liste territoire adjacent au territoire attaquant selectionne
			System.out.println(" ");
			System.out.println("Below the territories that you can attack from your chosen territory");
			ArrayList<Integer> att_neighb = terr_att.getNeighboring_Territories(); // Neighbors - Our territories
			att_neighb.removeAll(p.getTerritories()); //On enleve tout nos territoires pour eviter de pouvoir les attaquer
			System.out.println(att_neighb);


			// 4. Choix du territoire attaque
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
					// Checking the input type
					while (choice.hasNext()) {

						if (choice.hasNextInt()) {

							temp = choice.nextInt();
							break;

						}

						choice.next();
						System.out.println(" "); System.out.println("Invalid input type");
						System.out.print("Please, enter a INTEGER for your choice : ");
					}

				}while(!att_neighb.contains(temp)); // tant que le territoire choisi appartient bien a la liste


				for(int i = 0 ; i < m.getterritory_list().size(); i++){
					//On retrouve le territoire depuis son ID en parcourant la liste de tt les territoires.
					territory temp_terr = m.getterritory_list().get(i);

					if( temp_terr.getId() == temp) {

						terr_def = temp_terr;

					}
				}
				
				player player_def = null;
				for (player pl : ps) {
					if(pl.getID()== terr_def.getId_Player()) {
						player_def=pl;
					}
				}

				// 5. On récupere les nombres de dés des territoires et on fait un rolldice pour chaque
				attack = rolldice(terr_att.getNb_Dice());
				defense = rolldice(terr_def.getNb_Dice());

				// 6. on effectue les actions
				if(attack > defense){
					//attacker moves his dice to the conquered territory, except one that
					//remains on the starting territory and the defeated dice of the opponent disappears
					System.out.println(m.getterritory_list().get(m.getterritory_list().indexOf(terr_def)));
					m.getterritory_list().get(m.getterritory_list().indexOf(terr_def)).setId_Player(terr_att.getId_Player());//changer l'appartenance du territoire
					System.out.println(m.getterritory_list().get(m.getterritory_list().indexOf(terr_def)));
					System.out.println(ps.get(ps.indexOf(p)).getTerritories());
					p.getTerritories().add(terr_def.getId());
					System.out.println(ps.get(ps.indexOf(p)).getTerritories());
					
					System.out.println(ps.get(ps.indexOf(player_def)).getTerritories());
					player_def.getTerritories().remove(player_def.getTerritories().indexOf(terr_def.getId()));
					System.out.println(ps.get(ps.indexOf(player_def)).getTerritories());
					
					m.getterritory_list().get(m.getterritory_list().indexOf(terr_def)).setNb_Dice(terr_att.getNb_Dice()-1);
					
					m.getterritory_list().get(m.getterritory_list().indexOf(terr_att)).setNb_Dice(1);

					System.out.println("Attack succeed,  Score : " + attack + " / " + defense);
					System.out.println(" ");

				}else if (attack < defense){
					// (attacker) only keeps one die in his territory and the attacked territory remains unchanged
					m.getterritory_list().get(m.getterritory_list().indexOf(terr_att)).setNb_Dice(1);
					System.out.println("Attack failed,  Score : " + attack + " / " + defense);
					System.out.println(" ");

				}else{
					m.getterritory_list().get(m.getterritory_list().indexOf(terr_att)).setNb_Dice(1);
					m.getterritory_list().get(m.getterritory_list().indexOf(terr_def)).setNb_Dice(1);
					System.out.println("It's a draw, Score : " + attack + " / " + defense);
					System.out.println(" ");

				}
				// 7. Display the new map (in the Game/Main file)
			}
		}
		//choice.close();
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