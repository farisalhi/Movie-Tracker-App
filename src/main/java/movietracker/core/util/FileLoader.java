package movietracker.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import movietracker.core.data.*;
import movietracker.core.part2.*;

public class FileLoader {
    /**
     * This function gets the load file, reads it, and then stores data.
     * @param loadFile The load file we are reading from
     * @return The data to be loaded to the main program.
     */
    public Data loadFile(File loadFile) {
        // get the current available data so that any previously inputted data isn't overwritten.
        Data data = Menu.getData();
        // try-catch block to catch any i/o errors in the file
        try (Scanner scanner = new Scanner(loadFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(","); // split data by commas
                // load the list objects
                if (elements[0].equals("list")) { // first element is the object type (list)
                    Menu.listNumber++;
                    System.out.println(Menu.listNumber+ " in fileloader");
                    char type = elements[1].charAt(0); // second element is the list type
                    String listName = elements[2]; // third element is the list name
                    String listType = null;
                    switch (type) { // switch to get the character representation of the list type
                        case 'F' -> listType = "Favourites";
                        case 'W' -> listType = "Watched";
                        case 'A' -> listType = "Want-to_watch";
                    }
                    // store the data
                    data.storeNewList(Menu.listNumber, listType, listName);
                }
                // load the movie objects
                if (elements[0].equals("movie")) { // first element is the object type (movie)
                    Menu.movieNumber++;
                    String listName = elements[1]; // second element is the list name
                    String movieName = elements[2]; // third element is the movie name
                    int movieRating = Integer.parseInt(elements[3]); // fourth element is the rating
                    Genre.movieGenre movieGenre;
                    if (!elements[4].equals("null")) { // check if there is a genre assigned
                        movieGenre = Genre.movieGenre.valueOf(elements[4]); // fifth element is the genre
                    } else {
                        movieGenre = Genre.movieGenre.None; // if no genre is assigned, assign it as none
                    }
                    // store the data.
                    data.storeNewMovie(Menu.movieNumber, listName, movieName, movieRating, movieGenre);
                    Movie movie = data.getMovie(Menu.movieNumber);
                    data.storeRating(movieName, movieRating);
                    data.addGenre(movie ,movieGenre);
                }
            }
        } catch (FileNotFoundException e) { // catch file exception
            System.err.println("File not found");
            System.exit(1);
        }
        return data;
    }
}
