package movietracker.core.part2;

import java.io.File;

/**
 * Explanation of class
 * @author faris.salhi Faris Salhi
 * @author ariel.motsi Ariel Motsi
 * date: Nov. 7, 2023
 * tutorial: T06
 */
public class Main {
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
