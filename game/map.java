package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class map {

	private ArrayList<territory> territory_list = new ArrayList<territory>(); // in order to store the map of the object
	// territory

	private ArrayList<player> players = new ArrayList<player>(); // in order to store the player

	private int[][] matrix;// in order to store the shape of the map

	private int nb_nomansland;

	// private int Nb_lac; // in order to store the number of supressed territory

	public map(int nb_player) {
		if (nb_player == 2) {
			this.matrix = new int[5][5];
			nb_nomansland = 5;
		}
		if (nb_player == 3) {
			this.matrix = new int[6][6];
			nb_nomansland = 6;
		}
		if (nb_player == 4) {
			this.matrix = new int[7][7];
			nb_nomansland = 9;
		}
		if (nb_player == 5) {
			this.matrix = new int[8][8];
			nb_nomansland = 14;
		}
		if (nb_player == 6) {
			this.matrix = new int[8][8];
			nb_nomansland = 4;
		}
		if (nb_player == 7) {
			this.matrix = new int[9][9];
			nb_nomansland = 11;
		}
		if (nb_player == 8) {
			this.matrix = new int[9][9];
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
		

		for(int i : list_of_id_teritory) {
			this.territory_list.add(new territory(i,boundaries.get(i)));
		}
	}

	public void add_player_to_territory(int Nb_player) {
	}

	public void add_dice_to_territory() {
	}

	public void reinforcement_dice(int id_player) {
	}

	public void display_map() {
		String matrix = "";
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix.length; j++) {

				if (j != this.matrix.length - 1) {

					if (this.matrix[i][j] == (this.matrix[i][j + 1])) {
						if (this.matrix[i][j] < 10) {
							matrix = matrix + this.matrix[i][j] + "###";

						} else {
							matrix = matrix + this.matrix[i][j] + "##";
						}
					} else {
						if (this.matrix[i][j] < 10) {
							matrix = matrix + this.matrix[i][j] + "   ";

						} else {
							matrix = matrix + this.matrix[i][j] + "  ";
						}

					}

				} else {

					matrix = matrix + this.matrix[i][j];
				}

			}
			matrix = matrix + "\n                                \n";
		}

		System.out.println(matrix);
	}

	public ArrayList<territory> getterritory_list() {
		return territory_list;
	}

	public void setterritory_list(ArrayList<territory> territory_list) {
		this.territory_list = territory_list;
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
