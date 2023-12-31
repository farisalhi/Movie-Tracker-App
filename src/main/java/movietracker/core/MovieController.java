package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import movietracker.core.data.Data;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import movietracker.core.data.Genre;
import movietracker.core.data.List;
import movietracker.core.data.Movie;
import movietracker.core.part2.Menu;
import movietracker.core.util.FileLoader;
import movietracker.core.util.FileSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/*
 * Movie Tracker Application main controller class
 * The main controller class containing all FXML elements and functions to handle functions for movie storage
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Class to handle main application controller functions
 */
public class MovieController {

    //private static Data data;
    private static Data data = Menu.getData();

    // A pause transition to set a time limit on status messages.
    protected PauseTransition pause = new PauseTransition(Duration.seconds(5));

    // Choice box elements
    @FXML
    private ChoiceBox<String> movieInfo;

    @FXML
    private ChoiceBox<String> viewByList;

    @FXML
    private ChoiceBox<Genre.movieGenre> topByGenre;

    @FXML
    private ChoiceBox<String> topByList;

    @FXML
    private ChoiceBox<String> topByListType;

    // elements for viewing data
    @FXML
    protected Label status;

    @FXML
    private TextArea viewData;

    /**
     * Function to populate the choice boxes with updated data.
     */
    public void initialize() {
        initializeChoices();
    }

    /**
     * setUp function to initialize status label and its pause transition
     *
     * @param status Label for status updates
     * @param pause  PauseTransition for label timeout
     */
    public void setup(Label status, PauseTransition pause) {
        this.status = status;
        this.pause = pause;
    }

    /**
     * About function to display information pertaining to application and authors
     *
     * @param event About > About Movie Tracker. Button click
     */
    @FXML
    void about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // create a new information alert
        alert.setTitle("About Movie Tracker");
        // Content to display about authors and app
        alert.setContentText("""
                Author: Faris Salhi, Ariel Motsi
                Email: faris.salhi@ucalgary.ca, ariel.motsi@ucalgary.ca
                Version: 1.0
                This is an application to add movies, lists, genres and ratings to organize your movies.
                """);
        alert.setHeaderText("About");
        alert.showAndWait();
    }

    /**
     * Opens new stage for user to create a new list.
     *
     * @param event List > Create list. Button click
     */
    @FXML
    void createList(ActionEvent event) {
        try { // try to launch a new stage from a separate fxml file
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("createlist.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 130); // set stage dimensions
            stage.setResizable(false); // cannot be resized by user

            stage.setTitle("Create a list");
            ListController listController = loader.getController(); // get the controller from the fxml loader
            listController.initializeChoices(); // update all choice boxes to reflect current data
            listController.setup(status, viewData, pause); // set up labels to reflect current data in main app window
            // set modality to disallow user from going back to main app window without finishing with the popup.
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            initialize(); // Update current data to reflect changes
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens new stage for user to delete an existing list.
     *
     * @param event List > Delete List. Button click
     */
    @FXML
    void deleteList(ActionEvent event) {
        ArrayList<List> lists = data.getLists(); // get the list of lists
        if (!lists.isEmpty()) { // check if the user has created a list yet
            try { // try to launch a new stage from a separate fxml file
                FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("deleteList.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(loader.load(), 200, 100); // set stage dimensions
                stage.setResizable(false); // cannot be resized by user

                stage.setTitle("Delete a List");
                DeleteListController deleteListController = loader.getController(); // get the controller from the fxml loader
                deleteListController.initializeChoices(); // update all choice boxes to reflect current data
                deleteListController.setup(status, viewData, pause); // set up labels to reflect current data in main app window
                // set modality to disallow user from going back to main app window without finishing with the popup.
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                initialize(); // Update current data to reflect changes
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else { // inform the user that there's no existing list to delete
            status.setText("You haven't created any lists yet!");
            status.setTextFill(Color.BLACK);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Opens a new stage for user to add a new movie.
     *
     * @param event Movie > Add Movie. Button click
     * @throws IOException Runtime exception in case of fxml loader fail
     */
    @FXML
    void addMovie(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("addmovie.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 200); // set stage dimensions
            stage.setResizable(false); // cannot be resized by user

            stage.setTitle("Add a Movie");
            AddMovieController addMovieController = loader.getController(); // get the controller from the fxml loader
            addMovieController.initializeChoices(); // update all choice boxes to reflect current data
            addMovieController.setup(status, viewData, pause); // set up labels to reflect current data in main app window
            // set modality to disallow user from going back to main app window without finishing with the popup.
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            initialize(); // Update current data to reflect changes
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to remove a previously added movie from a list.
     *
     * @param event Movie > Remove Movie
    */
    @FXML
    void removeMovie(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies(); // get the list of movies
        if (!movies.isEmpty()) { // check if the user has added a movie yet
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("removeMovie.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 200, 100); // set stage dimensions
                stage.setResizable(false);

                stage.setTitle("Delete a Movie"); // cannot be resized by user
                RemoveMovieController removeMovieController = fxmlLoader.getController(); // get the controller from the fxml loader
                removeMovieController.initializeChoices(); // update all choice boxes to reflect current data
                removeMovieController.setup(status, viewData, pause); // set up labels to reflect current data in main app window
                // set modality to disallow user from going back to main app window without finishing with the popup.
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                initialize(); // Update current data to reflect changes
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else { // inform the user that there doesn't exist a movie to remove
            status.setText("You haven't added any movies yet!");
            status.setTextFill(Color.BLACK);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * For file loading using command-line arguments only.
     * Needs work
     */
    protected void loadFile(File loadFile) {
        if (loadFile.exists() && loadFile.isFile() && loadFile.canRead()) { // checks file properties.
            FileLoader fl = new FileLoader(); // create a FileLoader object
            data = fl.loadFile(loadFile); // assign the data from the loadFile function as the current data
            initialize();
            status.setText(String.format("Data loaded from %s", loadFile.getName()));
            status.setTextFill(Color.GREEN);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        } else {
            System.err.printf("The load file %s does not exist!%n", loadFile.getName());
        }
    }

    /**
     * Function to load data from user-selected .txt file
     *
     * @param event File > load. Button click
     */
    @FXML
    void load(ActionEvent event) {
        // Create a new FileChooser and set the title of the file search window
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a text file to load");
        // Automatically search within the current folder
        fc.setInitialDirectory(new File("."));
        // Adding extension filter to only accept .txt and .csv files.
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Comma-separated files  (*.txt, *.csv)", "*.txt", "*.csv");
        fc.getExtensionFilters().add(extension);
        // Set a File variable to the file selected in the file search window
        File loadFile = fc.showOpenDialog(new Stage());
        if (loadFile != null) { // Check if the user cancels the operation (nothing gets selected)
            // Check if the file exists and if it's a file and if its information is readable
            if (loadFile.exists() && loadFile.isFile() && loadFile.canRead()) {
                try { //Try to load the file
                    FileLoader fl = new FileLoader();
                    data = fl.loadFile(loadFile);
                    initialize(); // update choice boxes
                    //update status message
                    status.setText(String.format("Data loaded from %s", loadFile.getName()));
                    pause.setOnFinished(event1 -> status.setText(null));
                    pause.play();

                } catch (Exception e) { //Exception in case of corrupt file/data
                    status.setText("File contains no data or unreadable data.");
                    status.setTextFill(Color.RED);
                    pause.setOnFinished(event1 -> status.setText(null));
                    pause.play();
                }
            } else { // In case of unreadable/non-existent file
                status.setText("File doesn't exist!");
                status.setTextFill(Color.RED);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            }
        } else { // In case user cancels load
            status.setText("Canceled load.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Simple function to quit application
     *
     * @param event File > Quit. Button click.
     */
    @FXML
    void quit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Function to save current data to a save.txt file located in current directory.
     *
     * @param event File > Save. Button click.
     */
    @FXML
    void save(ActionEvent event) {
        // Get the save.txt file from the directory
        File saveFile = new File("save.txt");
        FileSaver fs = new FileSaver();
        // Check if the file wasn't deleted, and if a file and can be written to.
        if (saveFile.exists() && saveFile.isFile() && saveFile.canWrite()) {
            try { // Try-catch block to attempt saving current data to the file.
                fs.saveFile(saveFile, data); // invoke saveFile function in FileSaver.java
                status.setText(String.format("Saved data to %s", saveFile.getName())); // print success
                status.setTextFill(Color.GREEN);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            } catch (Exception e) { // Exception in case of was no data added to save.
                status.setText("No data to save.");
                status.setTextFill(Color.RED);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            }
        } else { // In case of unreadable/non-existent file
            status.setText("save.txt file doesn't exist.");
            status.setTextFill(Color.RED);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Function to save current data to a user-created file.
     *
     * @param event File > Save As. Button click.
     */
    @FXML
    void saveAs(ActionEvent event) {
        // Create a new fileChooser
        FileChooser fc = new FileChooser();
        fc.setTitle("Create a text file to save to.");
        // Set initial directory to current directory
        fc.setInitialDirectory(new File("."));
        // Adding extension filter to only accept .txt or .csv files.
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Comma-separated files  (*.txt, *.csv)", "*.txt", "*.csv");
        fc.getExtensionFilters().add(extension);

        // Create a new FileSaver
        FileSaver fs = new FileSaver();
        // Create a new save file named what the user inputs
        File saveFile = fc.showSaveDialog(new Stage());
        if (saveFile != null) { // Check if the user cancels the operation (no file is created)
            try { // try-catch block to attempt saving data to new file
                fs.saveFile(saveFile, data); // invoke saveFile function in FileSaver.java
                //update status message for success
                status.setText(String.format("Saved data to %s", saveFile.getName()));
                status.setTextFill(Color.GREEN);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            } catch (Exception e) { // Exception: no data existed to be saved
                status.setText("No data to save.");
                status.setTextFill(Color.RED);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            }
        } else { // User canceled save
            status.setText("Canceled save.");
            status.setTextFill(Color.RED);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Basic info message to inform user about how to use the application.
     *
     * @param event Help > tutorial. Button click.
     */
    @FXML
    void tutorial(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Message");
        alert.setTitle("Help");
        alert.setContentText("""
                * This is a Movie Tracking/Sorting application.
                * You can create and delete lists of different types to sort your movies by (under List menu option) and add and remove movies (under Movie menu option).
                * To add a movie, you need to add a rating, genre and the list it will be added to.
                * You can also load and save a text file with movies and lists that can be stored (under the File menu option).
                * The buttons trigger different functions as per labels to display different information under the PRINT window in the middle.
                * The success/failure status will be displayed at the bottom as the application runs.
                """);
        alert.showAndWait();
    }

    /**
     * Views all the movies and their assigned genres
     *
     * @param event View genres. Button click
     */
    @FXML
    void viewGenres(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies(); // get arraylist of movies
        if (!movies.isEmpty()) { // check if it's empty
            printGenres(movies); // invoke helper function to output data
        } else { // no movies added
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewGenres. Creates a string with the formatted info.
     *
     * @param movies Movie Arraylist containing all the current movies.
     */
    private void printGenres(ArrayList<Movie> movies) {
// format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "Genre");
        textData += "-----------------------------\n";

// Loop through every movie and append the related info to the string.
        for (Movie movie : movies) {
            textData += String.format("%-15s %-15s\n", movie.getName(), movie.getGenre());
        }
// output to text area in application
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views all lists.
     *
     * @param event View all lists. Button click.
     */
    @FXML
    void viewLists(ActionEvent event) {
        ArrayList<List> lists = data.getLists(); // get arraylist of lists
        if (!lists.isEmpty()) { // check if it's empty
            printLists(lists); // invoke helper function to output data
        } else { // No lists created
            status.setText("You haven't added any lists.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewLists. Creates a string with the formatted info.
     *
     * @param lists List ArrayList containing all the created lists.
     */
    public void printLists(ArrayList<List> lists) {
// format text for header info
        String textData = String.format("%-15s %-15s\n", "List", "Name");
        textData += "-----------------------------\n";
// Loop through every list and append  the related info to the string.
        for (List list : lists) {
            textData += String.format("%-15s %-15s\n", list.getType(), list.getName());
        }
// output to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views all movies and the lists they are in.
     *
     * @param event View movies and lists. Button click.
     */
    @FXML
    void viewMovieLists(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies(); // Get the arraylist of movies
        if (!movies.isEmpty()) { // check if it's empty
            printMoviesLists(movies); // invoke helper function to output data
        } else { // no movies added
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewMovieLists. Creates a string with the formatted info
     *
     * @param movies Movie ArrayList containing all the movies
     */
    public void printMoviesLists(ArrayList<Movie> movies) {
// format text for header info
        String textData = String.format("%-15s %-15s\n", "Name", "List");
        textData += "-----------------------------\n";
// Loop through every movie and append the related info to the string.
        for (Movie movie : movies) {
            textData += String.format("%-15s %-15s\n", movie.getName(), movie.getList());
        }
// output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views all info relating a movie.
     *
     * @param event View movie info. Button click
     */
    @FXML
    void viewMovieInfo(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies(); // get the arraylist of movies
        String movieName = movieInfo.getValue(); // get the name of the movie from the choice box
        if (!movies.isEmpty()) { // check if there are movies
            // Loop through every movie and check if the name is equal to the name in the choice box.
            for (Movie movie : movies) {
                if (movie.getName().equals(movieName)) {
                    printMovieInfo(movie); // invoke helper function to output data
                }
            }
        } else {
            status.setText("You haven't added any movies.");
            status.setTextFill(Color.BLACK);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewMovieInfo. Creates a string with the formatted info
     *
     * @param movie Movie selected for info viewing.
     */
    public void printMovieInfo(Movie movie) {
        String textData = (""); // initialize String with blank space.
        textData += movie.toString(); // format the movie info into toString
        // output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views the top 5 movies by list.
     *
     * @param event Top movies by list. Button click
     */
    @FXML
    void viewTopByList(ActionEvent event) {
        HashMap<Movie, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings
        ArrayList<Movie> top5List = data.getTop5List(); // get arraylist of top movies by list
        if (!ratings.isEmpty()) { //check if there are any rating added
            String list = topByList.getValue(); // get the user's selection of list from choice box
            data.storeTop5List(ratings, list); // invoke storeTop5List to sort data
            printTop5List(top5List, ratings); // invoke helper function to output data
        } else { // no ratings added means no movies added
            status.setText("You haven't added any movies.");
            status.setTextFill(Color.BLACK);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Views all the movies in a given list.
     *
     * @param event View
     */
    @FXML
    void viewByList(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies(); // get list of movies
        ArrayList<List> lists = data.getLists(); // get list of lists
        if (!movies.isEmpty()) { // check if it's empty
            String list = viewByList.getValue();
            for (List listChoice : lists) {
                if (listChoice.getName().equals(list));
                printMoviesByList(list);
            }
        } else {
            status.setText("You haven't added any movies.");
            status.setTextFill(Color.BLACK);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewByList. Creates a string with the formatted info
     *
     * @param list Name of selected list
     */
    private void printMoviesByList(String list) {
        ArrayList<Movie> movies = data.getMovies();
        // format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "List");
        textData += "-----------------------------\n";
        // Loop through the movies name in the movie list and append the related info to the string
        for (Movie movie : movies) {
            if (movie.getList().equals(list)) {
                textData += String.format("%-15s %-15s\n", movie.getName(), movie.getList());
            }
        }
        // output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Helper function for viewTop5ByList. Creates a String with the formatted info
     *
     * @param top5List String ArrayList containing the names of the top 5 movies in the list.
     * @param ratings  Hashmap containing the ratings for the movies
     */
    private void printTop5List(ArrayList<Movie> top5List, HashMap<Movie, Integer> ratings) {
        // format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";
        // Loop through the movie name in the top 5 list and append the related info to the string
        for (Movie movie : top5List) {
            textData += String.format("%-15s %-15d\n", movie.getName(), ratings.get(movie));
        }
        // output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views the top 5 movies by the list type.
     *
     * @param event Top movies by list type. Button click.
     */
    @FXML
    void viewTopByListType(ActionEvent event) {
        HashMap<Movie, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings
// Get all String ArrayLists containing the top 5 movies in the three list types
        ArrayList<Movie> top5Fav = data.getTop5Fav();
        ArrayList<Movie> top5Watched = data.getTop5Watched();
        ArrayList<Movie> top5WTW = data.getTop5WTW();

        if (!ratings.isEmpty()) { // Check if the ratings hashmap is empty
            String type = topByListType.getValue(); // Get the user's selection for list type
            switch (type) { // switch to deal with type cases.
                // Invoke the appropriate store and helper function for each type.
                case "Favourites" -> {
                    data.storeTop5Fav(ratings);
                    printTopFav(top5Fav, ratings);
                }
                case "Watched" -> {
                    data.storeTop5Watched(ratings);
                    printTopWatched(top5Watched, ratings);
                }
                case "Want-to-watch" -> {
                    data.storeTop5WTW(ratings);
                    printTopWTW(top5WTW, ratings);
                }
            }
        } else { // no ratings means no movies have been added
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for the top 5 movies in Favourites type list
     *
     * @param top5Fav String ArrayList containing the top 5 movies in Favourite lists.
     * @param ratings Hashmap containing the integer ratings for the movies
     */
    private void printTopFav(ArrayList<Movie> top5Fav, HashMap<Movie, Integer> ratings) {
// format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";
// Loop through the movie name in the top 5 list and append the related info to the string
        for (Movie movie : top5Fav) {
            textData += String.format("%-15s %-15d\n", movie.getName(), ratings.get(movie));
        }
// output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Helper function for the top 5 movies in Watched type list
     *
     * @param top5Watched String ArrayList containing the top 5 movies in Watched lists.
     * @param ratings     Hashmap containing the integer ratings for the movies
     */
    private void printTopWatched(ArrayList<Movie> top5Watched, HashMap<Movie, Integer> ratings) {
// format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";
// Loop through the movie name in the top 5 list and append the related info to the string
        for (Movie movie : top5Watched) {
            textData += String.format("%-15s %-15d\n", movie.getName(), ratings.get(movie));
        }
// output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Helper function for the top 5 movies in Want-to-watch type list
     *
     * @param top5WTW String ArrayList containing the top 5 movies in Want-to-watch lists.
     * @param ratings Hashmap containing the integer ratings for the movies
     */
    private void printTopWTW(ArrayList<Movie> top5WTW, HashMap<Movie, Integer> ratings) {
        // format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";
// Loop through the movie name in the top 5 list and append the related info to the string
        for (Movie movie : top5WTW) {
            textData += String.format("%-15s %-15d\n", movie.getName(), ratings.get(movie));
        }
// output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views all the movies.
     *
     * @param event View movies. Button click
     */
    @FXML
    void viewMovies(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies(); //get the arraylist of movies
        if (!movies.isEmpty()) { // check if it's empty
            printMovies(movies); //invoke helper function to output data
        } else { // no movies were added
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewMovies. Creates a string Creates a String with the formatted info
     *
     * @param movies Movie ArrayList containing all the movies
     */
    public void printMovies(ArrayList<Movie> movies) {
// format text for header info
        String textData = String.format("%-15s\n", "Name");
        textData += "-----------------------------\n";
// Loop through the movie name in the top 5 list and append the related info to the string
        for (Movie movie : movies) {
            textData += String.format("%-15s\n", movie.getName());
        }
// output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views all the movies and their assigned ratings.
     *
     * @param event View ratings. Button click
     */
    @FXML
    void viewRatings(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies(); //get the arraylist of movies
        if (!movies.isEmpty()) { // check if it's empty
            printRatings(movies); //invoke helper function to output data
        } else { // no movies were added
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewRatings. Creates a string Creates a String with the formatted info
     *
     * @param movies Movie ArrayList containing all the movies
     */
    private void printRatings(ArrayList<Movie> movies) {
// format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "Ratings");
        textData += "-----------------------------\n";
// Loop through the movie name in the top 5 list and append the related info to the string
        for (Movie movie : movies) {
            textData += String.format("%-15s %-15d\n", movie.getName(), movie.getRating());
        }
// output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views the top 5 movies in their genres
     *
     * @param event Top movies by genre. Button click.
     */
    @FXML
    void viewTopByGenre(ActionEvent event) {
        HashMap<Movie, Genre.movieGenre> genreLookup = data.getGenreLookup(); // get the hashmap of movie genres
        HashMap<Movie, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings
        ArrayList<Movie> top5Genre = data.getTop5Genre(); // get the list of top 5 movies by genre
        if (!genreLookup.isEmpty()) { // check if the genre lookup hashmap is empty
            Genre.movieGenre genre = topByGenre.getValue(); // get the genre for viewing selected by the user
            data.storeTop5Genre(ratings, genre); // invoke the function to sort the data
            printTopByGenre(top5Genre, ratings); // invoke the helper function for data output
        } else { //if there are no genres assigned, then there are no movies
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewTopByGenre
     *
     * @param top5Genre String ArrayList containing the top 5 movies in a genre
     * @param ratings   Hashmap containing the integer ratings for the movies
     */
    private void printTopByGenre(ArrayList<Movie> top5Genre, HashMap<Movie, Integer> ratings) {
        // format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";
        // Loop through the movie name in the top 5 list and append the related info to the string
        for (Movie movie : top5Genre) {
            textData += String.format("%-15s %-15s\n", movie.getName(), ratings.get(movie));
        }
        // output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views the top genre with the most movies
     *
     * @param event Most popular genres. Button click
     */
    @FXML
    void viewTopGenres(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) { // check if there are any movies added
            data.storeTopGenres(movies); // invoke the function to find the genre with the most movies
            printTopGenres(); // invoke helper function to output data to text area
        } else { //if the user hasn't added any movies
            status.setText("You haven't added any movies.");
            status.setTextFill(Color.BLACK);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();

        }
    }

    /**
     * Helper function to display the genre with the most movies and the number of movies in the genre
     */
    private void printTopGenres() {
        HashMap<Genre.movieGenre, Integer> topGenre = data.getTopGenre(); // get the hashmap of top genre
        // format text for header info
        String textData = String.format("%-15s %-15s\n", "Genre", "Number of movies");
        textData += "-----------------------------\n";
        // Loop through the genre in the top genre hashmap and append the related info to the string
        for (Genre.movieGenre genre : topGenre.keySet()) {
            textData += String.format("%-15s %-15s\n", genre, topGenre.get(genre));
        }
        // output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Views the top 5 movies.
     *
     * @param event View top 5 movies. Button click
     */
    @FXML
    void viewTop5(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies(); // get the arraylist of movies
        HashMap<Movie, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings
        ArrayList<Movie> top5 = data.getTop5(); // get the arraylist of top 5 movies
        if (!movies.isEmpty()) { // check if the movie list is empty
            data.storeTop5(ratings); // invoke the function for sorting the data
            printTop5(top5, ratings); // invoke helper function for data output
        } else { // if the list is empty, there are no movies
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    /**
     * Helper function for viewTop5.Creates a String with the formatted info
     *
     * @param top5    String ArrayList containing the top 5 movies.
     * @param ratings Hashmap containing the integer ratings for the movies
     */
    private void printTop5(ArrayList<Movie> top5, HashMap<Movie, Integer> ratings) {
        // format text for header info
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";
        // Loop through the movie name in the top 5 list and append the related info to the string
        for (Movie movie : top5) {
            textData += String.format("%-15s %-15s\n", movie.getName(), ratings.get(movie));
        }
        // output data to text area
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    /**
     * Function to initialize all the choice boxes
     */
    protected void initializeChoices() {
        // List choice box
        ArrayList<List> lists = data.getLists(); // get the list of lists
        topByList.getItems().clear(); // clear the list in case of previous data
        if (!lists.isEmpty()) { // check if it isn't empty
            topByList.setValue(lists.get(0).getName()); // set the initial value to the first item
            for (List list : lists) { // loop through each list
                // get the string name and add it to the choice box items
                String listName = list.getName();
                topByList.getItems().add(listName);
            }
        }

        // view by list choice box
        ArrayList<List> lists2 = data.getLists(); // get the list of lists
        viewByList.getItems().clear(); // clear the list in case of previous data
        if (!lists2.isEmpty()) { // check if it isn't empty
            viewByList.setValue(lists.get(0).getName()); // set the initial value to the first item
            for (List list : lists2) { // loop through each list
                // get the string name and add it to the choice box items
                String listName = list.getName();
                viewByList.getItems().add(listName);
            }
        }

        // Movie choice box
        ArrayList<Movie> movies = data.getMovies(); // get the list of movies
        movieInfo.getItems().clear(); // clear the list in case of previous data
        if (!movies.isEmpty()) { // check if it isn't empty
            movieInfo.setValue(movies.get(0).getName()); // set the initial value to the first item
            for (Movie movie : movies) { // loop through each movie
                // get the string name and add it to the choice box items
                String movieName = movie.getName();
                movieInfo.getItems().add(movieName);
            }
        }

        // List type choice box
        String[] types = {"Favourites", "Watched", "Want-to-watch"}; // declare all the list types
        topByListType.getItems().clear(); // clear the list in case of previous data
        topByListType.getItems().addAll(types); // add all the list types to the choice box items
        topByListType.setValue(types[0]); // set the initial value to the first item

// Genre choice box
        Genre.movieGenre[] genres = Genre.movieGenre.values(); // get the list of genres
        topByGenre.getItems().clear(); // clear the list in case of previous data
        topByGenre.getItems().addAll(genres); // add all the genres to the choice box items
        topByGenre.setValue(genres[0]); // set the initial value to the first item
    }
}