package movietracker.core.part2;

import java.io.File;
import java.util.*;
import movietracker.core.data.*;
import movietracker.core.data.List;
import movietracker.core.util.*;

/* This purpose of this program is to allow a user to store and rate movies in custom lists.
 * @author faris salhi (30117469)
 * @author ariel motsi (30147625)
 * date: Nov. 7, 2023
 * tutorial: T06
 */

/**
 * Prints the menu options to be presented to the user option program start.
 */
public class Menu {
    private static Data data = new Data(); // Create a new object data.

    /**
     * Getter for the current data
     * @return The current loaded data available to the user.
     */
    public static Data getData() {
        return data;
    }

    // initialize program static vars and constants
    private static final Scanner scanner = new Scanner(System.in);

    // Static constants for list and movie counting.
    public static int listNumber = 0;
    public static int movieNumber = 0;
    // The menu containing all the options
    private static final String optMessage = """
            Hello! Please select an option to begin tracking your movies:
            Menu Options
            \t[1] Create a new list.
            \t[2] Add a movie.
            \t[3] Rate a movie.
            \t[4] Add a genre.
            \t[5] Print all movies.
            \t[6] Print all lists.
            \t[7] Print movies and their lists.
            \t[8] Print movies and their ratings.
            \t[9] Print movies and their genres.
            \t[10] View a movie.
            \t[11] Print the top 5 movies.
            \t[12] Print top 5 by genre.
            \t[13] Print the top 5 favourites movies.
            \t[14] Print the top 5 watched movies.
            \t[15] Print the top 5 want-to-watch movies.
            \t[16] Load a file.
            \t[17] Save.
            \t[18] Exit.
            """;

    /**
     * Non-menu function that checks the load file and sends it to the movietracker.core.util.FileLoader.
     * @param loadFile The file to be loaded.
     */
    protected static void loadFile(File loadFile) {
        if (loadFile.exists() && loadFile.isFile() && loadFile.canRead()) { // checks file properties.
            FileLoader fl = new FileLoader(); // create a movietracker.core.util.FileLoader object
            data = fl.loadFile(loadFile); // assign the data from the loadFile function as the current data
            System.out.printf("Loaded data from save %s\n", loadFile);
        } else {
            System.err.printf("The save file %s does not exist!%n", loadFile.getAbsoluteFile());
        }
    }

    /**
     * Non-menu function that checks the save file and sends it to the movietracker.core.util.FileSaver
     * @param saveFile The file we are saving the data to.
     * @param data The data being saved.
     */
    protected static void saveFile(File saveFile, Data data) {
        if (saveFile.exists() && saveFile.isFile() && saveFile.canWrite()) { // check file properties.
            FileSaver fs = new FileSaver(); // Create a FileSaver object
            fs.saveFile(saveFile, data); // send the file and data to the saveFile function in the FileSaver class
            System.out.printf("Saved data to %s\n", saveFile);
        } else {
            System.err.printf("The save file %s does not exist!%n", saveFile.getAbsoluteFile());
        }
    }

    /**
     * Prints the option menu and asks for input to select a choice.
     * Each choice runs the related function.
     */
    protected static void menuLoop() {
        // Print option message and ask for input selection
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print(optMessage);
        String option = scanner.nextLine();
        // switch options to execute selected function
        while (!option.equals("18")) {
            switch (option) {
                case "1" -> menuCreateList();
                case "2" -> menuAddMovie();
                case "3" -> menuAddRating();
                case "4" -> menuAddGenre();
                case "5" -> menuPrintAllMovies();
                case "6" -> menuPrintAllLists();
                case "7" -> menuPrintMovieLists();
                case "8" -> menuPrintRating();
                case "9" -> menuPrintGenre();
                case "10" -> menuViewMovie();
                case "11" -> menuTop5();
                case "12" -> menuTop5Genre();
                case "13" -> menuTop5Favourites();
                case "14" -> menuTop5Watched();
                case "15" -> menuTop5WantToWatch();
                case "16" -> menuLoadFile();
                case "17" -> menuSaveFile();
                default -> System.out.printf("Option %s not recognized.\n", option);
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print(optMessage);
            option = scanner.nextLine();
        }
        System.out.println("Thanks for using this program. \nGoodbye :)");
    }

    /**
     * movietracker.core.part2.Menu function to save data to a save file.
     */
    private static void menuSaveFile() {
        System.out.println("Enter the name of the file to save to: "); // ask user for name of file to save to
        String saveFile = scanner.nextLine();
        saveFile(new File(saveFile), data); // create a new File object from that name and sent it to the saveFile function.
    }

    /**
     * movietracker.core.part2.Menu function to load data from a save file.
     */
    private static void menuLoadFile() {
        System.out.println("Enter the name of the file to load: "); // ask user for name of file to load from
        String loadFile = scanner.nextLine();
        loadFile(new File(loadFile)); // create a new File object from that name and send it to the loadFile function.
    }

    /**
     * Sorts the top 5 movies from the selected genre
     */
    private static void menuTop5Genre() {
        HashMap<Movie, Genre.movieGenre> genreLookup = data.getGenreLookup(); // get the hashmap of movie genres
        HashMap<String, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings
        if (!genreLookup.isEmpty()) { // check if there are movies with assigned genres
            System.out.println("Which genre would you like to sort by?");
            Genre.movieGenre[] genres = Genre.movieGenre.values(); // create a list of genres from the genre enum values
            for (int i = 0; i < genres.length - 1; i++) { // loop through the list to display the genres selections
                System.out.printf("[%d] %s\n", i + 1, genres[i]);
            }
            String choice = scanner.nextLine();
            int option = Integer.parseInt(choice);
            if (option >= 1 && option <= genres.length) { // if the user selection is valid...
                Genre.movieGenre genre = genres[option - 1]; // assign the genre to the option they selected
                data.storeTop5Genre(ratings, genre); // store it in data.
                if (!data.getTop5Genre().isEmpty()) {
                    System.out.printf("Top 5 movies in %s:\n", genre);
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    for (int i = 0; i < data.getTop5Genre().size(); i++) {
                        String movie = data.getTop5Genre().get(i);
                            System.out.printf("%d. %s\n", i + 1, movie);
                    }
                } else {
                    System.out.printf("You haven't added any %s movies.\n", genre);
                }
            }
            returnToMenu();
        } else {
            addGenre();
            returnToMenu();
        }
    }

    /**
     * Gets the top 5 movies from the storeTop5 function in Data.java and prints them out in descending order of rating.
     */
    private static void menuTop5() {
        boolean noRatings = true;
        // Retrieve movieRatings map from Data.java
        HashMap<String, Integer> movieRatings = data.getRatingLookup();
        // Check if map is empty. If not, call the storeTop5 function and print the header.
        for (int rating : movieRatings.values()) {
            if (rating > -1) {
                noRatings = false;
                break;
            }
        }
        if (!noRatings) {
            data.storeTop5(movieRatings);
            System.out.println("Top 5 Movies:");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            // Iterate through the getTop5 HashSet and print the name of the movie and its rating.
            for (int i = 0; i < data.getTop5().size(); i++) {
                String movie = data.getTop5().get(i);
                System.out.printf("%d. %s\n", i + 1, movie);
            }
            returnToMenu();
        }
        // If map is empty, display a message and ask user for input to continue the program.
        else {
            rateMovie();
            returnToMenu();
        }
    }

    /**
     * Gives user option to rate a movie when they attempt to use certain features that require rated movies.
     * Extracted to avoid code-reuse
     */
    private static void rateMovie() {
        System.out.println("You haven't rated any movies. Would you like to choose a movie to rate? ('y' for Yes)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            menuAddRating();
        }
    }

    /**
     * Gets the top 5 movies from the storeTop5Fav function in Data.java and prints them out
     */
    private static void menuTop5Favourites() {
        HashMap<String, Integer> movieRatings = data.getRatingLookup(); // get the hashmap of movie ratings
        ArrayList<List> lists = data.getLists(); // get the arraylist of lists
        boolean listEmpty = true;
        boolean noRatings = true;
        // Check if map is empty. If not, call the storeTop5Watched function and print the header.
        for (List list : lists) {
            if (list.getType().equals("Favourites")) {
                listEmpty = false;
                break;
            }
        }
        for (int rating : movieRatings.values()) {
            if (rating > -1) {
                noRatings = false;
                break;
            }
        }
        if (!movieRatings.isEmpty() && !listEmpty && !noRatings) {
            data.storeTop5Fav(movieRatings);
            System.out.println("Top 5 Movies in Favourites:");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            // Iterate through the getTop5 HashSet and print the name of the movie and its rating.
            for (int i = 0; i < data.getTop5Fav().size(); i++) {
                String movie = data.getTop5Fav().get(i);
                System.out.printf("%d. %s\n", i + 1, movie);
            }
            returnToMenu();
            // If map is empty, display a message and ask user for input to continue the program.
        } else {
            System.out.println("There are no rated movies in 'Favourites'.");
            returnToMenu();
        }
    }

    /**
     * Gets the top 5 movies from the storeTop5WTW function in Data.java and prints them out
     */
    private static void menuTop5WantToWatch() {
        HashMap<String, Integer> movieRatings = data.getRatingLookup(); // get the hashmap of movie ratings
        ArrayList<List> lists = data.getLists(); // get the arraylist of lists
        boolean listEmpty = true;
        boolean noRatings = true;
        // Check if map is empty. If not, call the storeTop5Watched function and print the header.
        for (List list : lists) {
            if (list.getType().equals("Want-to-watch")) {
                listEmpty = false;
                break;
            }
        }
        for (int rating : movieRatings.values()) {
            if (rating > -1) {
                noRatings = false;
                break;
            }
        }
        if (!movieRatings.isEmpty() && !listEmpty && !noRatings) {
            data.storeTop5WTW(movieRatings);
            System.out.println("Top 5 Movies in Want-to-watch:");
            // Iterate through the getTop5WTW HashSet and print the name of the movie and its rating.
            for (int i = 0; i < data.getTop5WTW().size(); i++) {
                String movie = data.getTop5WTW().get(i);
                System.out.printf("%d. %s\n", i + 1, movie);
            }
            returnToMenu();
            // If map is empty, display a message and ask user for input to continue the program.
        } else {
            System.out.println("There are no rated movies in 'Want-to-watch'.");
            returnToMenu();
        }
    }


    /**
     * Gets the top 5 movies from the storeTop5Watched function in Data.java and prints them out
     */
    private static void menuTop5Watched() {
        HashMap<String, Integer> movieRatings = data.getRatingLookup(); // get hashmap of movie ratings
        ArrayList<List> lists = data.getLists(); // get arraylist of lists
        boolean listEmpty = true;
        boolean noRatings = true;
        // Check if map is empty. If not, call the storeTop5Watched function and print the header.
        for (List list : lists) {
            if (list.getType().equals("Watched")) {
                listEmpty = false;
                break;
            }
        }
        for (int rating : movieRatings.values()) {
            if (rating > 0) {
                noRatings = false;
                break;
            }
        }
        if (!movieRatings.isEmpty() && !listEmpty && !noRatings) {
            data.storeTop5Watched(movieRatings);
            System.out.println("Top 5 Movies in Watched:");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            // Iterate through the getTop5Watched HashSet and print the name of the movie and its rating.
            for (int i = 0; i < data.getTop5Watched().size(); i++) {
                String movie = data.getTop5Watched().get(i);
                System.out.printf("%d. %s\n", i + 1, movie);
            }
            returnToMenu();
            // If map is empty, display a message and ask user for input to continue the program.
        } else {
            System.out.println("There are no rated movies in 'Watched'.");
            returnToMenu();

        }
    }

    /**
     * Prints movies and their genres
     */
    private static void menuPrintGenre() {
        ArrayList<Movie> movies = data.getMovies(); // get arraylist of movies
        if (!movies.isEmpty()) { // check if it isn't empty
            System.out.println("Movie        \tGenre");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (Movie movie : movies) { // loop through it and get info
                System.out.printf("%-13s\t%s\n", movie.getName(), movie.getGenre());
            }
            returnToMenu();
        } else { // the user hasn't assigned any genres. Ask them if they want to.
            addGenre();
            returnToMenu();
        }
    }

    /**
     * Function to ask user if they want to assign a new genre. Extracted to avoid code-reuse.
     */
    private static void addGenre() {
        System.out.println("You haven't assigned any genres. Would you like to assign one? ('y' for Yes)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            menuAddGenre();
            menuPrintGenre();
        }
    }

    /**
     * Prints the movies and their ratings side-by-side.
     */
    private static void menuPrintRating() {
        // Get the map of ratings from Data.java
        HashMap<String, Integer> ratings = data.getRatingLookup();
        boolean noRatings = true;
        // Check if the map is not empty
        for (String key : ratings.keySet()) {
            if (ratings.get(key) != 0) {
                noRatings = false;
            }
        }
        if (!noRatings) {
            System.out.println("Movie        \tRating");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            // Iterate through the map keys (ratings) and print the values at those keys (movies)
            for (String key : ratings.keySet()) {
                if (ratings.get(key) != 0) {
                    System.out.printf("%-13s\t%s stars\n", key, ratings.get(key));
                }
            }
            returnToMenu();
        } else {
            // If map is empty, display a message and ask user for input to continue the program.
            System.out.println("You haven't rated any movies. Would you like to choose a movie to rate? ('y' for Yes)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                menuAddRating();
                menuPrintRating();
                returnToMenu();
            }
        }
    }

    /**
     * Prints all the movies and the lists they've been added to.
     */
    public static void menuPrintMovieLists() {
        // Get the arraylist of movies from Data.java
        ArrayList<Movie> movies = data.getMovies();
        // Check if the list is not empty.
        if (!movies.isEmpty()) {
            System.out.println("List         \t Movie");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            // For each object entry in the list, print the name of the list, and the movie associated with it.
            for (Movie movie : movies) {
                System.out.printf("%-13s\t'%s'\n", movie.getList(), movie.getName());
            }
            returnToMenu();
            // If the list is empty, display a message and ask for input to continue the program.
        } else {
            createNewList();
            returnToMenu();
        }
    }

    /**
     * Prints all the created lists and their names.
     */
    private static void menuPrintAllLists() {
        // Get the arraylist of lists from Data.java
        ArrayList<List> lists = data.getLists();
        // Check if the list is not empty
        if (!lists.isEmpty()) {
            // For each object entry in the list, print the list type and the name of the list.
            System.out.println("List         \t Name");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (List list : lists) {
                System.out.printf("%-13s\t'%s'\n", list.getType(), list.getName());
            }
            returnToMenu();
            // If the list is empty, display a message and ask for input to continue the program.
        } else {
            createNewList();
            returnToMenu();
        }
    }

    /**
     * Gives user option to create a new list when they haven't created one. Extracted to avoid code-reuse
     */
    private static void createNewList() {
        System.out.println("You haven't created any lists. Would you like to create one? ('y' for Yes)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            menuCreateList();
        }
    }

    /**
     * Print all the movies that have been added.
     */
    private static void menuPrintAllMovies() {
        // Get the arraylist of movies
        ArrayList<Movie> movies = data.getMovies();
        // Check if the list is not empty
        if (!movies.isEmpty()) {
            System.out.println("Movies:");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            // Iterate through the items of the list until the size of the list, print the name of the movie.
            // Using an iterative for loop to avoid ConcurrentModificationException.
            for (Movie movie : movies) {
                // Assign an object entry to the movie name index of the list.
                System.out.printf("\t%s \n", movie.getName());
            }
            returnToMenu();
            // If the list is empty, display a message and ask for input to continue the program.
        } else {
            createNewMovie();
            returnToMenu();
        }
    }

    /**
     * Gives user option to create a new movie when they haven't created one. Extracted to avoid code-reuse
     */
    private static void createNewMovie() {
        System.out.println("You haven't added any movies. Would you like to add one? ('y' for Yes)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            menuAddMovie();
        }
    }

    /**
     * Add a rating between 1-5 stars for each movie.
     */
    private static void menuAddRating() {
        ArrayList<Movie> movies = data.getMovies(); // Get movie arraylist
        HashMap<Integer, Movie> movieLookup = data.getMovieLookup(); // Get movie hashmap lookup
        int rating; // initialize rating vars.
        if (!movies.isEmpty()) { // Check if the movie list is not empty
            // Ask the user to select the movie they wish to rate
            System.out.println("Which movie would you like to rate?");
            System.out.println("Movies:");
            // Iterate through the movie list and print the movie name and number so the user can select a choice.
            for (Movie movie : movies) {
                System.out.printf("\t[%s] '%s'\n", movie.getNum(), movie.getName());
            }
            String choice = scanner.nextLine(); // input to select a movie
            int option = Integer.parseInt(choice);
            while (option <= 0 || option > movies.size()) {
                System.out.printf("Invalid option, Select between %d - %d.", 1, movies.size());
                for (Movie movie : movies) {
                    System.out.printf("\t[%s] '%s'\n", movie.getNum(), movie.getName());
                }
                choice = scanner.nextLine(); // input to select a movie
                option = Integer.parseInt(choice);
            }
            // Iterate through the map of movies, searching for the movie number which the user asked for.
            if (movieLookup.containsKey(option)) {
                // If the number is found, assign the value at that key to a variable which is the name of the movie.
                Movie movie = movieLookup.get(option);
                // Ask user to rate the movie by an int value.
                System.out.printf("What do you rate '%s' on a scale of 1-5?\n", movie.getName());
                rating = scanner.nextInt();
                scanner.nextLine(); // account for unwanted input character
                while (rating <= 0 || rating > 5) {
                    System.out.printf("Invalid rating. What do you rate '%s' on a scale of 1-5?\n", movie.getName());
                    rating = scanner.nextInt();
                    scanner.nextLine(); // account for unwanted input character
                }
                data.storeRating(movie.getName(), rating);
                movie.setRating(rating);
                System.out.printf("'%s' rated %d stars.\n", movie.getName(), rating);
                returnToMenu();
            } else {
                System.out.println("Invalid selection.");
            }
        } else { // Otherwise the user hasn't added any movies. Ask them to add one.
            createNewMovie();
            menuAddRating();
            returnToMenu();
        }
    }

    /**
     * Intermediary function to allow user to view their data without the program switching too quickly.
     */
    private static void returnToMenu() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); // line separator for clarity
        System.out.println("Press enter to continue.");
        String input = scanner.nextLine();
        if (input.equals(" ")) {
            menuLoop();
        }
    }

    /**
     * Adds a user's inputted movie to a created list.
     */
    public static void menuAddMovie() {
        // Get the ObjectArray of lists from Data.java
        ArrayList<List> lists = data.getLists();
        // Get the HashMap of lists from Data.java
        HashMap<Integer, List> listLookup = data.getListLookup();
        // Initialize vars
        Genre.movieGenre genre = Genre.movieGenre.None;
        int rating = 0;
        // Prompt user to enter the name of the movie they want to add.
        System.out.println("Enter a movie to add:");
        String movie = scanner.nextLine();
        // Check if at least one list already exists
        if (!lists.isEmpty()) {
            // Prompt the user to select the list they want to add their movie to.
            // Print the list of available lists.
            System.out.println("Select a list to add it to:");
            for (List list : lists) {
                System.out.printf("[%s] %-13s\t%s\n", list.getNum(), list.getType(), list.getName());
            }
            System.out.printf("[%s] Create a new list\n", listNumber + 1);
            String choice = scanner.nextLine();
            int option = Integer.parseInt(choice);
            while (option <= 0 || option > lists.size() + 1) {
                System.out.println("Invalid option.\nSelect a list to add it to: ");
                for (List list : lists) {
                    System.out.printf("[%s] %-13s\t%s\n", list.getNum(), list.getType(), list.getName());
                }
                System.out.printf("[%s] Create a new list\n", listNumber + 1);
                choice = scanner.nextLine();
                option = Integer.parseInt(choice);
            }
            List list = listLookup.get(option);
            // For each list number in the listLookup HashMap, check if the user's selection matches it.{
            if (listLookup.containsKey(option)) {
                // Increase the static movie counting variable by one
                movieNumber++;
                // Execute the storeNewMovie function to add the movie to the list.
                boolean success = data.storeNewMovie(movieNumber, list.getName(), movie, rating, genre);
                if (success) {
                    System.out.printf("'%s' added to '%s.'\n", movie, list.getName());
                    returnToMenu();
                } else {
                    System.out.printf("'%s' has already been added.\n", movie);
                }
            } else if (option == listNumber + 1) {
                menuCreateList();
                List listL = listLookup.get(listNumber);
                movieNumber++;
                data.storeNewMovie(movieNumber, listL.getName(), movie, rating, genre);
                System.out.printf("'%s' added to '%s.'\n", movie, listL.getName());
                returnToMenu();
            }
            // If the user hasn't created any previous lists, ask them if they'd like to and continue the program.
        } else {
            createNewList();
            for (List list : lists) {
                listLookup.get(0);
                movieNumber++;
                data.storeNewMovie(movieNumber, list.getName(), movie, rating, genre);
                System.out.printf("'%s' added to '%s.'\n", movie, list.getName());
                returnToMenu();
            }
        }
    }

    /**
     * Gives user option to add a genre to a movie from an enum selection of genres.
     */
    public static void menuAddGenre() {
        ArrayList<Movie> movies = data.getMovies(); // Get movie arraylist
        HashMap<Integer, Movie> movieLookup = data.getMovieLookup(); // Get movie hashmap lookup
        if (!movies.isEmpty()) { // Check if the movie list is not empty
            System.out.println("Which movie would you like to add a genre to?");
            System.out.println("Movies:");
            for (Movie movie : movies) { // loop through the list and get the movie info for selection
                System.out.printf("\t[%s] '%s'\n", movie.getNum(), movie.getName());
            }
            String choice = scanner.nextLine(); // input to select a movie
            int option = Integer.parseInt(choice);
            while (option <= 0 || option > movies.size()) { // catch invalid selection
                System.out.println("Invalid option. Which movie would you like to add a genre to?");
                System.out.println("Movies:");
                for (Movie movie : movies) {
                    System.out.printf("\t[%s] '%s'\n", movie.getNum(), movie.getName());
                }
                choice = scanner.nextLine(); // input to select a movie
                option = Integer.parseInt(choice);
            }
            // Iterate through the map of movies, searching for the movie number which the user asked for.
            if (movieLookup.containsKey(option)) {
                // If the number is found, assign the value at that key to a variable which is the name of the movie.
                Movie movie = movieLookup.get(option);
                System.out.printf("What genre is '%s'?\n", movie.getName());
                Genre.movieGenre[] genres = Genre.movieGenre.values(); // create an arraylist of genres from the genre enum values
                for (int i = 0; i < genres.length - 1; i++) { // loop through the genres to display the selection
                    System.out.printf("[%d] %s\n", i + 1, genres[i]);
                }
                String genreChoice = scanner.nextLine();
                int genreOption = Integer.parseInt(genreChoice);
                if (genreOption >= 1 && genreOption <= genres.length) { // if the input is valid, get the selected genre
                    Genre.movieGenre genreL = genres[genreOption - 1];
                    data.addGenre(movie, genreL); // store it in data
                    movie.setGenre(genreL); // set it in the movie class
                    System.out.printf("Labeled '%s' as %s\n", movie.getName(), genreL);
                    returnToMenu();
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        } else {
            createNewMovie();
            menuAddGenre();
        }
    }

    /**
     * Gives 3 options for list types and asks user to choose and name one.
     */
    private static void menuCreateList() {
        // Initialize local constants for list types
        final String FAVOURITE = "Favourites";
        final String WATCHED = "Watched";
        final String WANT_TO_WATCH = "Want-to-watch";
        // Initialize type and name variables
        String type;
        String name;
        // Print the list of options for the user to choose from
        System.out.print("""
                What kind of list would you like to create?
                \t[1] Favourites
                \t[2] Watched
                \t[3] Want-to-watch
                \t[4] Back
                """);
        // Using a switch, evaluate the user's choice
        String choice = scanner.nextLine();
        while (!choice.equals("4")) {
            switch (choice) {
                case "1" -> {
                    // Set the type to favourite and increase the static listNumber counting variable by 1.
                    type = FAVOURITE;
                    listNumber++;
                    // Ask the user to name the list.
                    System.out.println("What would you like to call your favourites list?");
                    name = scanner.nextLine().trim();
                    boolean success = data.storeNewList(listNumber, type, name);
                    if (success) {
                        System.out.printf("Favourites list '%s' created.\n", name);
                        returnToMenu();
                    } else {
                        System.out.println("This list already exists!");
                    }
                }
                case "2" -> {
                    // Set the type to watched and increase the static listNumber counting variable by 1.
                    type = WATCHED;
                    listNumber++;
                    // Ask the user to name the list.
                    System.out.println("What would you like to call your watched list?");
                    name = scanner.nextLine().trim();
                    boolean success = data.storeNewList(listNumber, type, name);
                    if (success) {
                        System.out.printf("Watched list '%s' created.\n", name);
                        returnToMenu();
                    } else {
                        System.out.println("This list already exists!");
                    }
                }
                case "3" -> {
                    // Set the type to want-to-watch and increase the static listNumber counting variable by 1.
                    type = WANT_TO_WATCH;
                    listNumber++;
                    // Ask the user to name the list.
                    System.out.println("What would you like to call your want-to-watch list?");
                    name = scanner.nextLine().trim();
                    boolean success = data.storeNewList(listNumber, type, name);
                    if (success) {
                        System.out.printf("Want-to-watch list '%s' created.\n", name);
                        returnToMenu();
                    } else {
                        System.out.println("This list already exists!");
                    }
                }
                default -> {
                    System.out.printf("Option %s not recognized.\n", choice);
                    returnToMenu();
                    menuCreateList();
                }
            }
            break;
        }
    }

    private static void menuViewMovie() {
        ArrayList<Movie> moviesList = data.getMovies();
        if(!moviesList.isEmpty()) {
            System.out.println("Which movie would you like to view?");
            for (Movie movieEntry : moviesList) {
                System.out.printf("\t[%s] %s\n", movieEntry.getNum(), movieEntry.getName());
            }
            String choice = scanner.nextLine().trim();
            int movieNum = Integer.parseInt(choice);
            Movie movie = data.getMovie(movieNum);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(movie);
            returnToMenu();
        } else {
            createNewMovie();
        }
    }
}