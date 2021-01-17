package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class map {

	private ArrayList<territory> territory_list = new ArrayList<territory>(); // in order to store the map of the object
	// territory

	// private ArrayList<player> players = new ArrayList<player>(); // in order to
	// store the player

	private int[][] personal_map;
	private int personal_L;
	private int personal_l;

	private int[][] matrix;// in order to store the shape of the map

	private int[][] matrix_player;// in order to store the shape of the map with player

	private int nb_nomansland; // the number of non usable territory

	private ArrayList<Integer> renfor = new ArrayList<Integer>();
	private ArrayList<territory> already = new ArrayList<territory>();

	public void creat_map_csv(String filepath, int nb_player) throws IOException {

		try {
			File csvFile = new File(filepath); // gets the file at the path

			if (csvFile.isFile()) {
				BufferedReader csvReader = new BufferedReader(new FileReader(filepath));
				String row;
				int y = 0;
				
				while ((row = csvReader.readLine()) != null) {
					String[] data = row.split(";");
					if (y >= 2) {
						for (int x = 0; x <= this.personal_l-1; x++) {
							//System.out.println("hauteur"+ this.personal_map.length+" largeur "+this.personal_map[0].length+ " y "+y);
							
							int z= Integer.parseInt(data[x]);
							
							//System.out.println(this.personal_map[y-2][x]);
							if(z<=nb_player) {
								//System.out.println(" y "+(y-3)+"x "+x+" z "+z);
							this.personal_map[y-3][x] = z;}
						}
						y++;
					}
					if(y==0) {
						this.personal_L=Integer.parseInt(data[0]);
						//System.out.println("y ="+y+" "+this.personal_L);
						y++;
					}
					if(y==1) {
						this.personal_l=Integer.parseInt(data[0]);
						//System.out.println("y ="+y+" "+this.personal_l);
						this.personal_map = new int[this.personal_L][this.personal_l];
						y++;
					}
				}
				csvReader.close();
			} else {
				throw new WrongFilepathException("Invalid Filepath");
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public class WrongFilepathException extends RuntimeException {
		public WrongFilepathException(String errorMessage) {
			super(errorMessage);
		}
	}

	public map(int nb_player, boolean bool, String filepath) throws IOException {
		this.creat_map_csv(filepath, nb_player);
		this.matrix = new int[this.personal_map.length][this.personal_map[0].length];
		this.matrix_player = new int[this.personal_map.length][this.personal_map[0].length];

		int cpt = 0;
		for (int i = 0; i <= matrix.length - 1; i++) {
			for (int j = 0; j <= matrix[0].length - 1; j++) {
				if (this.personal_map[i][j] == -1) {
					matrix[i][j] = -1;
					cpt = cpt + 1;
				} else {
					cpt = cpt + 1; // we had a différent numbre for each teritory
					matrix[i][j] = cpt;
				}
			}
		}

		// ici faire que les -1 dans le fichier csv soit inscrit

		ArrayList<Integer> list_of_id_teritory = new ArrayList<Integer>();
		HashMap<Integer, ArrayList<Integer>> boundaries = new HashMap<Integer, ArrayList<Integer>>();

		for (int i = 0; i <= matrix.length - 1; i++) {
			for (int j = 0; j <= matrix.length - 1; j++) {

				if (matrix[i][j] != -1) {
					list_of_id_teritory.add(matrix[i][j]);

					if (j == 0 && i == 0) { // if we are at the top left corner we only check on the right side the
											// downside

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[0][1]);
						tab1.add(matrix[1][0]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (j == 0 && i == matrix.length - 1) { // if we are at the top right corner we only check the left
															// side and the down side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[matrix.length - 2][0]);
						tab1.add(matrix[matrix.length - 1][1]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (i == 0 && j == matrix.length - 1) { // if we are at the down left corner we on ly check the up
															// side and right side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[0][matrix.length - 2]);
						tab1.add(matrix[1][matrix.length - 1]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (i == matrix.length - 1 && j == matrix.length - 1) { // if we are at the down right corner we
																			// only check up side and left side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();

						tab1.add(matrix[matrix.length - 1][matrix.length - 2]);
						tab1.add(matrix[matrix.length - 2][matrix.length - 1]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (j == 0 && i > 0 && i < matrix.length - 1) { // if we are on the up edge we check right side ,
																	// left side and down side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i - 1][j]);
						tab1.add(matrix[i + 1][j]);
						tab1.add(matrix[i][j + 1]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (i == 0 && j > 0 && j < matrix.length - 1) { // if we are on the left edge we check right side ,
																	// up side and down side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i][j + 1]);
						tab1.add(matrix[i + 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (j == matrix.length - 1 && i > 0 && i < matrix.length - 1) { // if we are on the right edge we
																					// check up side , left side and
																					// down side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i - 1][j]);
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i + 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (i == matrix.length - 1 && j > 0 && j < matrix.length - 1) { // if we are on the udown edge we
																					// check right side , left side and
																					// up side
						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i][j + 1]);
						tab1.add(matrix[i - 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (i > 0 && j > 0 && i < matrix.length - 1 && j < matrix.length - 1) { // else we check all the
																							// side

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
			// System.out.println(boundaries.get(i));
			while (boundaries.get(i).contains(-1)) {// we clean all the boundaries for no possibility to go in -1
													// territory
				boundaries.get(i).remove(boundaries.get(i).indexOf(-1));
			}
			// System.out.println(boundaries.get(i));
			this.territory_list.add(new territory(i, boundaries.get(i)));
		}
	}

	public map(int nb_player) { // we creat a matrix with at least 10 territory for eache player then we will
								// kill the other territorys
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
				cpt = cpt + 1; // wa hade a différent numbre for each teritory
				matrix[i][j] = cpt;
			}
		}

		while (nb_nomansland > 0) {
			int x = new Random().nextInt(matrix.length - 1); // we creat some random nomansland
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

					if (j == 0 && i == 0) { // if we are at the top left corner we only check on the right side the
											// downside

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[0][1]);
						tab1.add(matrix[1][0]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (j == 0 && i == matrix.length - 1) { // if we are at the top right corner we only check the left
															// side and the down side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[matrix.length - 2][0]);
						tab1.add(matrix[matrix.length - 1][1]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (i == 0 && j == matrix.length - 1) { // if we are at the down left corner we on ly check the up
															// side and right side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[0][matrix.length - 2]);
						tab1.add(matrix[1][matrix.length - 1]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (i == matrix.length - 1 && j == matrix.length - 1) { // if we are at the down right corner we
																			// only check up side and left side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();

						tab1.add(matrix[matrix.length - 1][matrix.length - 2]);
						tab1.add(matrix[matrix.length - 2][matrix.length - 1]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (j == 0 && i > 0 && i < matrix.length - 1) { // if we are on the up edge we check right side ,
																	// left side and down side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i - 1][j]);
						tab1.add(matrix[i + 1][j]);
						tab1.add(matrix[i][j + 1]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (i == 0 && j > 0 && j < matrix.length - 1) { // if we are on the left edge we check right side ,
																	// up side and down side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i][j + 1]);
						tab1.add(matrix[i + 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (j == matrix.length - 1 && i > 0 && i < matrix.length - 1) { // if we are on the right edge we
																					// check up side , left side and
																					// down side

						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i - 1][j]);
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i + 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}
					if (i == matrix.length - 1 && j > 0 && j < matrix.length - 1) { // if we are on the udown edge we
																					// check right side , left side and
																					// up side
						ArrayList<Integer> tab1 = new ArrayList<Integer>();
						tab1.add(matrix[i][j - 1]);
						tab1.add(matrix[i][j + 1]);
						tab1.add(matrix[i - 1][j]);
						boundaries.put(matrix[i][j], tab1);

					}

					if (i > 0 && j > 0 && i < matrix.length - 1 && j < matrix.length - 1) { // else we check all the
																							// side

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
			// System.out.println(boundaries.get(i));
			while (boundaries.get(i).contains(-1)) {// we clean all the boundaries for no possibility to go in -1
													// territory
				boundaries.get(i).remove(boundaries.get(i).indexOf(-1));
			}
			// System.out.println(boundaries.get(i));
			this.territory_list.add(new territory(i, boundaries.get(i)));
		}
	}

	public void add_player_to_territory(ArrayList<player> players) { // in order to dispatch player on the map
		ArrayList<Integer> memory = new ArrayList<Integer>();

		for (player p : players) {
			int mem = 10; // each player has 10 territory
			while (mem > 0) {
				int x = new Random().nextInt(players.size() * 10); // we provide terriotry randomly
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
		System.out.println("\njoueur placer sur la map\n");
	}

	public void add_player_to_territory(ArrayList<player> players, boolean bool) {

		// faire un parcour de la matrice et du fichier csv associer le territoire a son
		// propriétaire

		for (player p : players) {
			int cpt = 0;
			for (int i = 0; i <= matrix.length - 1; i++) {
				for (int j = 0; j <= matrix[0].length - 1; j++) {
					// System.out.println("id player "+p.getID()+" this personal map
					// "+this.personal_map[i][j]+" matrix "+this.matrix[i][j]);
					if (p.getID() == this.personal_map[i][j]) {

						this.territory_list.get(this.matrix[i][j] - 1 - cpt).setId_Player(p.getID());
						ArrayList<Integer> change = p.getTerritories();
						change.add(this.territory_list.get(this.matrix[i][j] - 1 - cpt).getId());
						p.setTerritories(change);

					} else if (this.personal_map[i][j] == -1) {
						cpt = cpt + 1;
					}

				}

			}

		}
		System.out.println("\njoueur placé sur la map\n");

	}

	public void add_dice_to_territory(ArrayList<player> players) { // in order to add dice on the territory of the map

		for (territory t : this.territory_list) {// if a territory has no dice he recieve on
			if (t.getNb_Dice() == 0) {
				t.setNb_Dice(t.getNb_Dice() + 1);
			}
		}

		for (player p : players) { // for each player we check their terriotrys and add dice randomly one by one
									// until we reach the limite of dice
			int cpt = p.getNb_R_dice();
			while (cpt > 0) {
				int x = new Random().nextInt(p.getTerritories().size() - 1);
				int y = p.getTerritories().get(x);
				for (territory t : this.territory_list) {
					if (t.getId() == y && t.getNb_Dice() < 8) {
						t.setNb_Dice(t.getNb_Dice() + 1);
						System.out.println("One dice added to the territory " + t.getId() + " now it have "
								+ t.getNb_Dice() + " dices");
						cpt = cpt - 1;
					}
				}

			}
			p.setNb_dice(p.getNb_dice() + p.getNb_R_dice());
			p.setNb_R_dice(0);
		}

	}

	public int reinforcement_dice_calc(ArrayList<Integer> x, territory T) {
		x.removeIf(n -> (n == T.getId()));
		int ret = 1;
		for (int j = 0; j < x.size(); j++) {
			for (Integer neighbor : T.getNeighboring_Territories()) {
				if (x.isEmpty() || x.size() >= j) {
					break;
				} else if (x.get(j) == neighbor) {
					for (int i = 0; i < this.territory_list.size(); i++) {
						if (this.territory_list.get(i).getId() == x.get(j)) {
							ret += reinforcement_dice_calc(x, this.territory_list.get(i));
							break;
						}
					}
				}
			}
		}
		return ret;
	}

	public void reinforcement_dice2(int id_player, ArrayList<player> p) {
		ArrayList<Integer> x = (ArrayList<Integer>) p.get(id_player - 1).getTerritories().clone();
		int Nb_RDice = 0;
		while (!x.isEmpty()) {
			territory T = null;
			for (int i = 0; i < this.territory_list.size(); i++) {
				if (this.territory_list.get(i).getId() == x.get(0)) {
					T = this.territory_list.get(i);
					break;
				}
			}

			if (T != null) {
				int NbTer = reinforcement_dice_calc(x, T);
				if (NbTer > Nb_RDice)
					Nb_RDice = NbTer;
			}
		}
		p.get(id_player - 1).setNb_R_dice(Nb_RDice);
		System.out.println("les dés de renforcement sont au nombre de " + Nb_RDice);
	}

	public void reinforcement_dice(int id_player, ArrayList<player> players) {
		ArrayList<territory> ter = new ArrayList<territory>();
		// ArrayList<territory> already = new ArrayList<territory>();
		System.out.println("for the player: " + id_player);
		for (territory t : this.territory_list) {
			if (t.getId_Player() == id_player) {
				ter.add(t); // we add to ter all the territory of the current player
			}
		}
		System.out.println(ter);
		int renfor = 0;
		for (territory t : ter) { // we iterate all the teritory of the current player
			renfor = 1;
			if (!this.already.contains(t)) { // we check if the teritory as been already check
				this.neighbor(t, id_player, ter, renfor);
			} // if not we call neighbor methode
		}

		System.out.println("les dés de renfore sont au nombre de: " + Collections.max(this.renfor)); // we take the
																										// maximum of
																										// renfor and
																										// this is the
																										// greatest
																										// number of
																										// contigous
																										// territory
		players.get(id_player - 1).setNb_R_dice(Collections.max(this.renfor)); // we set to the player the numbre of his
																				// bonus dice
		this.renfor.clear(); // we clear for the next time
		this.already.clear();

	}

	public void neighbor(territory t, int id_player, ArrayList<territory> ter, int renfor) {
		/*
		 * if(this.already.size()==ter.size()) { return renfor; }
		 */
		this.already.add(t); // we add the territory to the list already to keep it in memory
		System.out.println("from teritory: " + t.getId() + "\n already: " + this.already.size() + "\n ter: "
				+ ter.size() + "\n renfor: " + renfor);

		// ArrayList<territory> test = new ArrayList<territory>();

		for (territory to : ter) { // we iterate the territory of the player
			if (t.getNeighboring_Territories().contains(to.getId()) && !this.already.contains(to)) { // we check if the
																										// territories
																										// touche
																										// another
																										// territories
																										// and if is not
																										// counted
																										// before
				renfor = renfor + 1;

				this.renfor.add(renfor); // we add the number of reiforcemnet to the list renfor
				this.neighbor(to, id_player, ter, renfor); // we call neighbor ofr the contigous teriroiry

			}

		}

	}

	public void display_map() {// in order to display the map
		String matrix = "	matrice des territoires	  \n";
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

	public void display_personal_map() {// in order to display the map
		String matrix = "	matrice perso	  \n";
		for (int i = 0; i < this.personal_map.length; i++) {
			for (int j = 0; j < this.personal_map[0].length; j++) {

				if (j != this.personal_map.length - 1) {

					if (this.personal_map[i][j] < 10 && this.personal_map[i][j] != -1) {
						matrix = matrix + " " + this.personal_map[i][j] + " || ";

					} else {
						matrix = matrix + this.personal_map[i][j] + " || ";
					}

				}

				else {

					matrix = matrix + this.personal_map[i][j];
				}

			}
			matrix = matrix + "\n------------------------------\n";
		}

		System.out.println(matrix);
	}

	public void display_map_player(ArrayList<player> players) { // we display the matrix but we replace the id of the
																// territory by the id of the owner
		String matrix = "	teritoires des joueurs   \n";
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix.length; j++) {
				for (player p : players)
					if (p.getTerritories().contains(this.matrix[i][j])) {
						matrix = matrix + " " + p.getID() + " || ";
						this.matrix_player[i][j] = p.getID();
					}

				if (this.matrix[i][j] == -1) {
					matrix = matrix + " Ø || ";
					this.matrix_player[i][j] = -1;
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
