package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class map {

	private ArrayList<territory> territory_list = new ArrayList<territory>(); // in order to store the map of the object
	// territory

	// private ArrayList<player> players = new ArrayList<player>(); // in order to
	// store the player

	private int[][] matrix;// in order to store the shape of the map

	private int[][] matrix_player;
	
	private int nb_nomansland;
	
	private ArrayList<Integer> renfor = new ArrayList<Integer>();
	private ArrayList<territory> already = new ArrayList<territory>();

	// private int Nb_lac; // in order to store the number of supressed territory

	public map(int nb_player) {
		if (nb_player == 2) {
			this.matrix = new int[5][5];
			this.matrix_player = new int[5][5];
			nb_nomansland = 5;
		}
		if (nb_player == 3) {
			this.matrix = new int[6][6];
			this.matrix_player = new int[6][6];
			nb_nomansland = 6;
		}
		if (nb_player == 4) {
			this.matrix = new int[7][7];
			this.matrix_player = new int[7][7];
			nb_nomansland = 9;
		}
		if (nb_player == 5) {
			this.matrix = new int[8][8];
			this.matrix_player = new int[8][8];
			nb_nomansland = 14;
		}
		if (nb_player == 6) {
			this.matrix = new int[8][8];
			this.matrix_player = new int[8][8];
			nb_nomansland = 4;
		}
		if (nb_player == 7) {
			this.matrix = new int[9][9];
			this.matrix_player = new int[9][9];
			nb_nomansland = 11;
		}
		if (nb_player == 8) {
			this.matrix = new int[9][9];
			this.matrix_player = new int[9][9];
			nb_nomansland = 1;

		}
		int cpt = 0;
		for (int i = 0; i <= matrix.length - 1; i++) {
			for (int j = 0; j <= matrix.length - 1; j++) {
				cpt = cpt + 1;
				matrix[i][j] = cpt;
			}
		}

		while (nb_nomansland > 0) {
			int x = new Random().nextInt(matrix.length - 1);
			int y = new Random().nextInt(matrix.length - 1);
			if (this.matrix[x][y] != -1) {
				this.matrix[x][y] = -1;
				nb_nomansland -= 1;
			}
		}

		ArrayList<Integer> list_of_id_teritory = new ArrayList<Integer>();
		HashMap<Integer, ArrayList<Integer>> boundaries = new HashMap<Integer, ArrayList<Integer>>();

		for (int i = 0; i <= matrix.length - 1; i++) {
			for (int j = 0; j <= matrix.length - 1; j++) {

				if (matrix[i][j] != -1) {
					list_of_id_teritory.add(matrix[i][j]);

					if (j == 0 && i == 0) { // if we are at the top left corner we only check on the right side the down
											// side and on the Right down diagonal 0# like this
						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[0][1]);
						tab1.add(matrix[1][0]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (j == 0 && i == matrix.length - 1) { // if we are at the top right corner we only check like this
															// #0

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[matrix.length - 2][0]);
						tab1.add(matrix[matrix.length - 1][1]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (i == 0 && j == matrix.length - 1) { // if we are at the down left corner we on ly check like
															// this ##

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[0][matrix.length - 2]);
						tab1.add(matrix[1][matrix.length - 1]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (i == matrix.length - 1 && j == matrix.length - 1) { // if we are at the down right corner we on
																			// ly check like this ##

						ArrayList<Integer> tab1 = new ArrayList<Integer>();

						tab1.add(matrix[matrix.length - 1][matrix.length - 2]);
						tab1.add(matrix[matrix.length - 2][matrix.length - 1]);
						boundaries.put(matrix[i][j], tab1);

					}
//                                                                                                                                                     ___
					if (j == 0 && i > 0 && i < matrix.length - 1) { // if we are on edge we have four check for the four
																	// edge of the matrix #0#, ###, |##, ##| zone where
																	// we check: #, the case of the proper teritory: 0,
																	// border of the matrix |or¯or_
						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i - 1][j]);
						tab1.add(matrix[i + 1][j]);
						tab1.add(matrix[i][j + 1]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (i == 0 && j > 0 && j < matrix.length - 1) {

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i][j + 1]);
						tab1.add(matrix[i + 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (j == matrix.length - 1 && i > 0 && i < matrix.length - 1) {

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i - 1][j]);
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i + 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (i == matrix.length - 1 && j > 0 && j < matrix.length - 1) {
						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i][j + 1]);
						tab1.add(matrix[i - 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}
//																																											###
					if (i > 0 && j > 0 && i < matrix.length - 1 && j < matrix.length - 1) { // if we are not on the edge
																							// of the matrix we can
																							// check all over the box
																							// like this #0#
						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i - 1][j]);
						tab1.add(matrix[i + 1][j]);
						tab1.add(matrix[i][j + 1]);
						tab1.add(matrix[i][j - 1]);
						boundaries.put(matrix[i][j], tab1);

					}

				}
			}
		}

		for (int i : list_of_id_teritory) {
			System.out.println(boundaries.get(i));
			while (boundaries.get(i).contains(-1)) {
				boundaries.get(i).remove(boundaries.get(i).indexOf(-1));
			}
			System.out.println(boundaries.get(i));
			this.territory_list.add(new territory(i, boundaries.get(i)));
		}
	}

	public void add_player_to_territory(ArrayList<player> players) {
		ArrayList<Integer> memory = new ArrayList<Integer>();

		for (player p : players) {
			int mem = 10;
			while (mem > 0) {
				int x = new Random().nextInt(players.size() * 10);
				if (!memory.contains(x)) {
					memory.add(x);
					this.territory_list.get(x).setId_Player(p.getID());
					ArrayList<Integer> change = p.getTerritories();
					change.add(this.territory_list.get(x).getId());
					p.setTerritories(change);
					mem = mem - 1;
				}
			}
		}

	}

	public void add_dice_to_territory(ArrayList<player> players) {
		
		for (territory t : this.territory_list) {
			if(t.getNb_Dice()==0) {
			t.setNb_Dice(t.getNb_Dice()+1);}
		}
		

		for (player p : players) {
			int cpt = p.getNb_R_dice();
			while (cpt > 0) {
				int x = new Random().nextInt(p.getTerritories().size() - 1);
				int y = p.getTerritories().get(x);
				for (territory t : this.territory_list) {
					if (t.getId() == y && t.getNb_Dice() < 8) {
						t.setNb_Dice(t.getNb_Dice() + 1);
						cpt = cpt - 1;
					}
				}

			}
			p.setNb_dice(p.getNb_dice()+p.getNb_R_dice());
			p.setNb_R_dice(0);
		}

	}


	public void reinforcement_dice(int id_player,ArrayList<player> players) {
		ArrayList<territory> ter = new ArrayList<territory>();
		//ArrayList<territory> already = new ArrayList<territory>();
		System.out.println("for the player: "+ id_player);
		for( territory t : this.territory_list) {
			if( t.getId_Player()==id_player) {
				ter.add(t);
			}
		}
		System.out.println(ter);
		int renfor=0;
		for(territory t : ter) {
			renfor=1;
			if(!this.already.contains(t)) {
			this.neighbor(t,id_player, ter,renfor);}
		}
		
		System.out.println("les dés de renfore sont au nombre de: "+Collections.max(this.renfor));
		players.get(id_player-1).setNb_R_dice(Collections.max(this.renfor));
		this.renfor.clear();
		this.already.clear();
		
	}
	
	public void neighbor(territory t, int id_player,ArrayList<territory> ter, int renfor) {
		/*if(this.already.size()==ter.size()) {
			return renfor;
		}*/
		this.already.add(t);
		System.out.println("from teritory: "+t.getId()+"\n already: "+this.already.size()+"\n ter: "+ter.size()+"\n renfor: "+ renfor);
	
		//ArrayList<territory> test = new ArrayList<territory>();
		
		for( territory to : ter) {
			if(t.getNeighboring_Territories().contains(to.getId()) && !this.already.contains(to)) {
				renfor = renfor+1;
				
				this.renfor.add(renfor);
				this.neighbor(to, id_player,ter,renfor);
				
			}
			
		}
		
		
	}
	
	

	public void display_map() {
		String matrix = "";
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix.length; j++) {

				if (j != this.matrix.length - 1) {

					if (this.matrix[i][j] < 10 && this.matrix[i][j] != -1) {
						matrix = matrix + " " + this.matrix[i][j] + " || ";

					} else {
						matrix = matrix + this.matrix[i][j] + " || ";
					}

				}

				else {

					matrix = matrix + this.matrix[i][j];
				}

			}
			matrix = matrix + "\n------------------------------\n";
		}

		System.out.println(matrix);
	}
	
	public void display_map_player(ArrayList<player> players) {
		String matrix = "";
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix.length; j++) {
					for(player p : players)
						if(p.getTerritories().contains(this.matrix[i][j])) {
							matrix = matrix + " " + p.getID() + " || ";
							this.matrix_player[i][j]=p.getID();
						}
						
					if(this.matrix[i][j]==-1) {
							matrix = matrix + " Ø || ";
							this.matrix_player[i][j]=-1;
						}

			}
			matrix = matrix + "\n------------------------------\n";
		}

		System.out.println(matrix);
	}

	public ArrayList<territory> getterritory_list() {
		return territory_list;
	}

	public void setterritory_list(ArrayList<territory> territory_list) {
		this.territory_list = territory_list;
	}

	/*
	 * public ArrayList<player> getPlayers() { return players; }
	 * 
	 * public void setPlayers(ArrayList<player> players) { this.players = players; }
	 */

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
}
