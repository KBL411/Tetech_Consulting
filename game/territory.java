package game;

import java.util.ArrayList;

public class territory {
	private int Id;
	private int Id_Player;
	private String Name;
	private int Nb_Dice;
	private ArrayList<Integer> Neighboring_Territories;
	
	public territory(int id, int id_Player, String name, int nb_Dice, int[] neighboring_Territories) {
		super();
		Id = id;
		Id_Player = id_Player;
		Name = name;
		Nb_Dice = nb_Dice;
		Neighboring_Territories = new ArrayList<Integer>();
	}
	
	public territory(int id) {
		super();
		Id = id;
	}
	

	public int getId() {
		return Id;
	}

	
	public void setId(int id) {
		Id = id;
	}

	
	public int getId_Player() {
		return Id_Player;
	}

	
	public void setId_Player(int id_Player) {
		Id_Player = id_Player;
	}

	
	public String getName() {
		return Name;
	}

	
	public void setName(String name) {
		Name = name;
	}

	
	public int getNb_Dice() {
		return Nb_Dice;
	}

	
	public void setNb_Dice(int nb_Dice) {
		Nb_Dice = nb_Dice;
	}

	public ArrayList<Integer> getNeighboring_Territories() {
		return Neighboring_Territories;
	}

	public void setNeighboring_Territories(ArrayList<Integer> neighboring_Territories) {
		Neighboring_Territories = neighboring_Territories;
	}

	@Override
	public String toString() {
		return "Territory [Id=" + Id + ", Id_Player=" + Id_Player + ", Name=" + Name + ", Nb_Dice=" + Nb_Dice
				+ ", Neighboring_Territories=" + Neighboring_Territories + "]\n";
	}

	
	
	
	
}

