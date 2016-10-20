/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	private void move(int stepSize, int direction) {
		boolean xEdge = false;
		boolean yEdge = false;
		
		// Find exceptions for critters at edges of map
		// x_coord is at left edge
		if (x_coord - stepSize < 0 && direction >= 3 && direction <= 5) {
			x_coord = Params.world_width + x_coord - stepSize;
			xEdge = true;
		}
		// y_coord is at top edge
		if (y_coord - stepSize < 0 && direction >= 1 && direction <= 3) {
			y_coord = Params.world_height + y_coord - stepSize;
			yEdge = true;
		}
		// x_coord is at right edge
		if (x_coord + stepSize > Params.world_width - 1 && (direction == 1 || direction == 0 || direction == 7)) {
			x_coord = (x_coord + stepSize) % Params.world_width;
			xEdge = true;
		}
		// y_coord is at bottom edge
		if (y_coord + stepSize > Params.world_height - 1 && direction >= 5 && direction <= 7) {
			y_coord = (y_coord + stepSize) % Params.world_height;

			yEdge = true;
		}
		
		// Changes the x and y coordinates of the critter
		switch (direction) {
		case 0: if (!xEdge)	x_coord += stepSize;
				break;
		case 1: if (!xEdge)	x_coord += stepSize;
				if (!yEdge) y_coord -= stepSize;
				break;
		case 2: if (!yEdge) y_coord -= stepSize;
				break;
		case 3: if (!xEdge) x_coord -= stepSize;
				if (!yEdge) y_coord -= stepSize;
				break;
		case 4: if (!xEdge) x_coord -= stepSize;
				break;
		case 5: if (!xEdge) x_coord -= stepSize;
				if (!yEdge) y_coord += stepSize;
				break;
		case 6: if (!yEdge) y_coord += stepSize;
				break;
		case 7: if (!xEdge)	x_coord += stepSize;
				if (!yEdge) y_coord += stepSize;
				break;
		default: break;
		}
		
		
		// Deduct energy cost for movement
		if (stepSize == 1) {
			energy -= Params.walk_energy_cost;
		} else if (stepSize == 2) { 
			energy -= Params.run_energy_cost;
		}
	}
	
	protected final void walk(int direction) {
		move(1, direction);
	}
	
	protected final void run(int direction) {
		move(2, direction);
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy) {
			return;
		}
		offspring.energy = (int) Math.floor((double) this.energy / 2);
		this.energy = (int) Math.ceil((double) this.energy / 2);
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.move(1, direction);
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class critterSub = Class.forName(critter_class_name);
			Critter critter = (Critter) critterSub.newInstance();
			critter.x_coord = Critter.getRandomInt(Params.world_width);
			critter.y_coord = Critter.getRandomInt(Params.world_height);
			critter.energy = Params.start_energy;
			population.add(critter);
		}
		catch (ClassNotFoundException e) {
			throw new InvalidCritterException("error processing: " + critter_class_name);
		}
		catch (IllegalAccessException e) {
			throw new InvalidCritterException("error processing: " + critter_class_name);
		}
		catch (InstantiationException e) {
			throw new InvalidCritterException("error processing: " + critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.removeAll(population);
		babies.removeAll(babies);
	}
	
	public static void worldTimeStep() {
		// Incrementing 2d int map where a critter exists
		int[][] mapCount = new int[Params.world_width][Params.world_height];
		for (Critter c : population) {
			mapCount[c.x_coord][c.y_coord]++;
		}
		
		// If  a place in the map is greater than 1 then 2 or more critter occupy 1 place
		ArrayList<Critter> moved = new ArrayList<Critter>();
		for (int i = 0; i < Params.world_height * Params.world_width; i++) {
			if (mapCount[i % Params.world_width][i / Params.world_width] > 1) {
				ArrayList<Critter> fightList = new ArrayList<Critter>();
				for (Critter c : population) {
					if (c.x_coord == i % Params.world_width && c.y_coord == i / Params.world_width) {
						fightList.add(c);
					}
				}
				moved.addAll(fightClub(fightList));
			}
		}
		
		ArrayList<Critter> removePop = new ArrayList<Critter>();
		for (Critter c : population) {
			if (!moved.contains(c)) c.doTimeStep();
			c.energy -= Params.rest_energy_cost;
			if (c.energy <= 0) {
				removePop.add(c);
			}
		}
		
		// Culling the dead critters from the population
		population.removeAll(removePop);
		
		// Adding all offspring to population
		population.addAll(babies);
		
		// Clearing offspring array list
		babies.clear();
		
		// Creating algae
		try {
			for (int i = 0; i < Params.refresh_algae_count; i++) {
				Critter.makeCritter("assignment4.Algae");
			}
		} catch (InvalidCritterException e) {
			System.out.println("error processing: " + "assignment4.Algae");
		}
	}
	
	public static ArrayList<Critter> fightClub(ArrayList<Critter> fightList) {
		ArrayList<Critter> moved = new ArrayList<Critter>();
		int oldXCoord = 0;
		int oldYCoord = 0;
		boolean hasMoved = true;
		while (fightList.size() > 1) {
			boolean aFight = fightList.get(0).fight(fightList.get(1).toString());
			boolean bFight = fightList.get(1).fight(fightList.get(0).toString());
			if (!aFight) {
				oldXCoord = fightList.get(0).x_coord;
				oldYCoord = fightList.get(0).y_coord;
				fightList.get(0).doTimeStep();
				for (Critter c : population) {
					if (fightList.get(0).x_coord == c.x_coord && fightList.get(0).y_coord == c.y_coord) {
						fightList.get(0).x_coord = oldXCoord;
						fightList.get(0).y_coord = oldYCoord;
						hasMoved = false;
					}
				}
				if (hasMoved) 
					moved.add(fightList.get(0));
			}
			if (!bFight) {
				oldXCoord = fightList.get(1).x_coord;
				oldYCoord = fightList.get(1).y_coord;
				fightList.get(1).doTimeStep();
				for (Critter c : population) {
					if (fightList.get(1).x_coord == c.x_coord && fightList.get(1).y_coord == c.y_coord) {
						fightList.get(1).x_coord = oldXCoord;
						fightList.get(1).y_coord = oldYCoord;
						hasMoved = false;
					}
				}
				if (hasMoved) 
					moved.add(fightList.get(1));
			}
			if (fightList.get(0).x_coord == fightList.get(1).x_coord &&
					fightList.get(0).y_coord == fightList.get(1).y_coord &&
					fightList.get(0).energy > 0 && fightList.get(1).energy > 0) {
				int aPower, bPower;
				if (aFight) {
					aPower = Critter.getRandomInt(fightList.get(0).energy + 1);
				} else {
					aPower = 0;
				}
				if (bFight) {
					bPower = Critter.getRandomInt(fightList.get(1).energy + 1);
				} else {
					bPower = 0;
				}
				if (aPower >= bPower) {
					fightList.get(0).energy += fightList.get(1).energy / 2;
					fightList.get(1).energy = 0;
					fightList.remove(1);
				} else if (aPower < bPower) {
					fightList.get(1).energy += fightList.get(0).energy / 2;
					fightList.get(0).energy = 0;
					fightList.remove(0);
				}
			}
		}
		return moved;
	}	
	
	public static void displayWorld() {
		// Creates 2D array of critters for positions on map
		Critter[][] critterMap = new Critter[Params.world_width][Params.world_height];
		for (Critter c : population) {
			critterMap[c.x_coord][c.y_coord] = c;
		}
		
		
		// Prints top border
		System.out.print("+");
		for (int i = 0; i < Params.world_width; i++) {
			System.out.print("-");
		}
		System.out.println("+" + " Number of critters: " + population.size());
		
		// Prints map
		for (int i = 0; i < Params.world_height; i++) {
			System.out.print("|");
			for (int j = 0; j < Params.world_width; j++) {
				if (critterMap[j][i] != null) {
					System.out.print(critterMap[j][i].toString());
				} else {
					System.out.print(" ");
				}
			}
			System.out.println("|");
		}
		
		// Prints bottom border
		System.out.print("+");
		for (int i = 0; i < Params.world_width; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}
}
