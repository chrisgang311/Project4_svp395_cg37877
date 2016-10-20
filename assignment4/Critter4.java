/* CRITTERS <Critter4.java>
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

// Critter 4 can walk either North or South. It will always elect to
// fight. Critter 4 requires (> 100) energy to reproduce. It is represented
// by the string “4”.

public class Critter4 extends Critter {
	@Override
	public String toString() { return "4"; }
	
	private int dir;
	
	public Critter4() {
		dir = getDir();
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 100) {
			Critter4 kid = new Critter4();
			reproduce(kid, getDir());
		}
		
		dir = getDir();
	}
	
	private static int getDir(){
		int fifty = Critter.getRandomInt(2);
		if (fifty != 0) return 2;
		return 6;
	}
}