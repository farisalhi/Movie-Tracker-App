package movietracker.core.util;

import movietracker.core.data.*;
import java.io.*;
import java.util.ArrayList;

/*
 * FileSaver class to assist in saving data to a comma-separated file
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * FileSaver class to loop through data structures and save data to comma-separated file
 */
public class FileSaver {
    /**
     * This function gets the objects stored in data and writes them to a save file.
     * @param saveFile The file we are writing to.
     * @param data The data we want to save.
     */
    public void saveFile (File saveFile, Data data) {
        // try-catch block to catch any i/o errors in the file
        try {
            FileWriter fileWriter = new FileWriter(saveFile); // create a new file writer
            PrintWriter printWriter = new PrintWriter(fileWriter); // create a new print writer
            // saving lists
            ArrayList<List> lists = data.getLists(); // get the list of lists
            for (List list : lists) { // loop through the list and get all the info from the list object
                char listType = ' ';
                switch (list.getType()) { // switch to get the character representation of the list type
                    case "Favourites" -> listType = 'F';
                    case "Watched" -> listType = 'W';
                    case "Want-to-watch" -> listType = 'A';
                }
                // write the comma-separated data to the save file
                printWriter.printf("list,%s,%s\n", listType, list.getName());
            }
            // saving movies
            ArrayList<Movie> movies = data.getMovies(); // get the movies list
            for (Movie movie : movies) { // loop through the list and get all the info from the movie object
                // write the comma-separated data to the save file
                printWriter.printf("movie,%s,%s,%d,%s\n", movie.getList(), movie.getName(), movie.getRating(), movie.getGenre());
            }
            printWriter.flush(); // flush the print writer
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
