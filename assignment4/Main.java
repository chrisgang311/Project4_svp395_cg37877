/* CRITTERS <Main.java>
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

package assignment4; // cannot be in default package
import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        boolean quit = false;
        String userInput;
    	String delim = "[ ]+";
        
        while (!quit) {
        	userInput = kb.nextLine();
        	try {
        		String[] tokens = userInput.split(delim);
        		if (tokens.length > 3) {
        			throw new ArrayIndexOutOfBoundsException("error processing: " + userInput);
        		} else if (userInput.equals("quit")) {
	        		quit = true;
	        	} else if (userInput.equals("show")) {
	        		Critter.displayWorld();
	        	} else if (tokens[0].equals("step")) {
	        		if (tokens.length == 2) {
	        			for (int i = 0; i < Integer.parseInt(tokens[1]); i++) {
	        				Critter.worldTimeStep();
	        			}
	        		} else if (tokens.length == 1) {
	        			Critter.worldTimeStep();
	        		}
	        	} else if (tokens[0].equals("seed")) { 
	        		Critter.setSeed(Integer.parseInt(tokens[1]));
	        	} else if (tokens[0].equals("make")) {
	        		if (tokens.length == 3) {
	        			for (int i = 0; i < Integer.parseInt(tokens[2]); i++) {
		        			Critter.makeCritter("assignment4." + tokens[1]);
		        		}
	        		} else if (tokens.length == 2) {
	        			Critter.makeCritter("assignment4." + tokens[1]);
	        		}
	        	} else if (tokens[0].equals("stats")) {
	        		Class critterSub = Class.forName("assignment4." + tokens[1]);
	        		Critter critter = (Critter) critterSub.newInstance();
	        		List<Critter> instances =  critter.getInstances("assignment4." + tokens[1]);
	        		Class<?>[] types = {List.class};
	    			Method sub = critterSub.getMethod("runStats", types);
	    			sub.invoke(null, instances);
	    			
	        	} else {
	        		System.out.println("invalid command: " + userInput);
	        	}
        	} catch (NumberFormatException e) {
        		System.out.println("error processing: " + userInput);
        	} catch (ArrayIndexOutOfBoundsException e) {
        		System.out.println("error processing: " + userInput);
        	} catch (InvalidCritterException e) {
        		System.out.println("error processing: " + userInput);
        	} catch (ClassNotFoundException e) {
        		System.out.println("error processing: " + userInput);
        	} catch (IllegalAccessException e) {
        		System.out.println("error processing: " + userInput);
    		} catch (InstantiationException e) {
    			System.out.println("error processing: " + userInput);
    		} catch (NoSuchMethodException e) {
    			System.out.println("error processing: " + userInput);
			} catch (SecurityException e) {
				System.out.println("error processing: " + userInput);
			} catch (IllegalArgumentException e) {
				System.out.println("error processing: " + userInput);
			} catch (InvocationTargetException e) {
				System.out.println("error processing: " + userInput);
			}
        	
        }
        
        
        
        /* Write your code above */
        System.out.flush();

    }
}
