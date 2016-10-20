/* CRITTERS <Critter3.java>
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

// Critter 3 can run either East or West. It has a 10% chance of engaging in a 
// fight. Critter 3 requires (> 69) energy to reproduce. It is represented by 
// the string “3”.

public class Critter3 extends Critter {
	@Override
	public String toString() { return "3"; }
	
	private int dir;
	
	public Critter3() {
		dir = getDir();
	}
	
	public boolean fight(String not_used) { return Critter.getRandomInt(10) < 1; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		
		if (getEnergy() > 69) {
			Critter3 kid = new Critter3();
			reproduce(kid, getDir());
		}
		
		dir = getDir();
	}
	
	private static int getDir(){
		int fifty = Critter.getRandomInt(2);
		if (fifty != 0) return 4;
		return 0;
	}
}