/* CRITTERS <Critter2.java>
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

// Critter 1 can run in 5 possible directions; East, South East, South, and South 
// West, and West. It has a 50 percent chance of engaging in a fight. Critter 1 requires
// little energy to reproduce (> 25), so it should reproduce frequently. It is represented 
// by the string “1”.

public class Critter2 extends Critter {
	@Override
	public String toString() { return "2"; }
	
	private int dir;
	
	public Critter2() {
		dir = getDir();
	}
	
	public boolean fight(String not_used) { return Critter.getRandomInt(9) > 2; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 200) {
			Critter2 kid = new Critter2();
			reproduce(kid, getDir());
		}
		
		dir = getDir();
	}
	
	private static int getDir(){
		int direction = Critter.getRandomInt(8);
		if (direction > 4){
			direction -= 4;
		}
		return direction;
	}
}
