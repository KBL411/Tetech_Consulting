package game;

import java.util.ArrayList;

public class player {
	private int ID;
	private String Name;
	private ArrayList<Integer> Territories;
	private int Nb_dice;
	
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
	
	public void attackTerritory() {}
	
	public int rolldice(int Nb_dice) {
		return 0;
	}
	
	public int endTurn(int turn) {
		return 0;
	}
	

	
}