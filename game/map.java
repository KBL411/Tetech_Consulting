package game;

import java.util.ArrayList;

public class map {

	private ArrayList<territory> carte = new ArrayList<territory>(); // in order to store the map of the object territory
	
	private ArrayList<player> players = new ArrayList<player>();  // in order to store the player
	
	private int matrix[][] = new int[10][10]; // in order to store the shape of the map
	
	//private int Nb_lac; // in order to store the number of supressed territory
	
	
	public map() {}
	
	public void add_player_to_territory(int Nb_player) {}
	
	public void add_dice_to_territory() {}
	
	public void reinforcement_dice(int id_player) {	
	}

	public void display_map() {}
	
	public ArrayList<territory> getCarte() {
		return carte;
	}

	public void setCarte(ArrayList<territory> carte) {
		this.carte = carte;
	}

	public ArrayList<player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<player> players) {
		this.players = players;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
}
