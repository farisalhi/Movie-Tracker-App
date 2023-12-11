package movietracker.core.part2;

import java.io.File;

/*
 * Movie Tracker main to run non-GUI version of the app
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Main class to set up prgram args and launch the menu
 */
public class Main {
    /**
     * Main function that checks for program args and launches app menu
     * @param args String array containing program args (1 argument containing path to load file)
     */
    public static void main(String[] args) {
        if (args.length == 1) { // loading from command-line
            File loadFile = new File(args[0]); // first argument is the file we want to load from
            // load the file and run the program
            Menu.loadFile(loadFile);
            Menu.menuLoop();
        } else {
            // not loading from command-line. Run program normally.
            Menu.menuLoop();
        }
    }
}
