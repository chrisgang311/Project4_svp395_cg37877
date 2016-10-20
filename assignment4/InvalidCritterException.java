/* CRITTERS <InvalidCritterException.java>
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

public class InvalidCritterException extends Exception {
	String offending_class;
	
	public InvalidCritterException(String critter_class_name) {
		offending_class = critter_class_name;
	}
	
	public String toString() {
		return "Invalid Critter Class: " + offending_class;
	}

}
