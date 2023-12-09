package movietracker.core.part2;

import java.io.File;

/**
 * Main to run non-GUI version of application through terminal with or without program args
 * @author faris.salhi Faris Salhi
 * @author ariel.motsi Ariel Motsi
 * date: Dec. 8, 2023
 * tutorial: T06
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
