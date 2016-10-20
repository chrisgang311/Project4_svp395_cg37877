/* CRITTERS <Critter1.java>
 * EE422C Project 4 submission by
 * <Samuel Patterson>
 * <svp395>
 * <16445>
 * <Christopher Gang>
 * <cg37877>
 * <16445>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

// Critter 1 can run in 5 possible directions; East, South East, South, and South West, 
// and West. It has a 50 percent chance of engaging in a fight. Critter 1 requires little
// energy to reproduce (> 25), so it should reproduce frequently. It is represented by the string “1”.

public class Critter1 extends Critter {
	@Override
	public String toString() { return "1"; }
	
	private int dir;
	
	public Critter1() {
		dir = getDir();
	}
	
	public boolean fight(String not_used) { return Critter.getRandomInt(10) > 4; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		
		if (getEnergy() > 25) {
			Critter1 kid = new Critter1();
			reproduce(kid, getDir());
		}
		
		dir = getDir();
	}
	
	private static int getDir(){
		int direction = Critter.getRandomInt(8);
		if (direction > 0 && direction < 4){
			direction += 4;
		}
		return direction;
	}
}
