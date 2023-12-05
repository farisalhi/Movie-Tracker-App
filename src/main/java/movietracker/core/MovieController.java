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
import java.util.HashMap;

public class MovieController {

    //private static Data data;
    static Data data = Menu.getData();

    private static final PauseTransition pause = new PauseTransition(Duration.seconds(2));

    @FXML
    private ChoiceBox<String> movieInfo;

    @FXML
    private ChoiceBox<Genre.movieGenre> topByGenre;

    @FXML
    private ChoiceBox<String> topByList;

    @FXML
    private ChoiceBox<String> topByListType;


    @FXML
    private Label status;

    @FXML
    private TextArea viewData;

    public void initialize() {
        initializeChoices();
    }

    public void setData(Data data) {
        this.data = data;
    }

    @FXML
    void about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Movie Tracker");
        alert.setContentText("""
                Author: Faris Salhi, Ariel Motsi
                Email: faris.salhi@ucalgary.ca, ariel.motsi@ucalgary.ca
                Version: 1.0
                This is an application to add movies, lists, genres and ratings to organize your movies.
                """);
        alert.setHeaderText("About");
        alert.showAndWait();
    }

    @FXML
    void createList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("createlist.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 130);
            stage.setResizable(false);

            ListController listController = loader.getController();
            listController.initializeChoices();
            listController.setup(status,viewData, pause);
            stage.setTitle("Create a list");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("deleteList.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 100);
            stage.setResizable(false);

            DeleteListController deleteListController = loader.getController();
            deleteListController.initializeChoices();
            deleteListController.setup(status, viewData, pause);
            stage.setTitle("Delete a List");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addMovie(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("addmovie.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 200);
            stage.setResizable(false);

            AddMovieController addMovieController = loader.getController();
            addMovieController.initializeChoices();
            addMovieController.setup(status, viewData, pause);
            stage.setTitle("Added Movie");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            initialize();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void removeMovie(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("removeMovie.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 200, 100);
            Stage stage = new Stage();
            stage.setTitle("Delete a Movie");
            RemoveMovieController removeMovieController = fxmlLoader.getController();
            removeMovieController.initializeChoices();
            removeMovieController.setup(status, viewData, pause);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /*
     * For file loading using command-line arguments only.
     *
    protected static void loadFile(File loadFile) {
        if (loadFile.exists() && loadFile.isFile() && loadFile.canRead()) { // checks file properties.
            FileLoader fl = new FileLoader(); // create a FileLoader object
            data = fl.loadFile(loadFile); // assign the data from the loadFile function as the current data
            status.setText(String.format("Data loaded from %s", loadFile.getName()));
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        } else {
            System.err.printf("The load file %s does not exist!%n", loadFile.getName());
        }
    }*/

    @FXML
    void load(ActionEvent event) {
        // Create a new FileChooser and set the title of the file search window
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a text file to load");
        // Automatically search within the current folder
        fc.setInitialDirectory(new File("."));
        // Adding extension filter to only accept .txt files.
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Text files  (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extension);
        // Set a File variable to the file selected in the file search window
        File loadFile = fc.showOpenDialog(new Stage());
        if (loadFile != null) { // Check if the user cancels the operation (nothing gets selected)
            // Check if the file exists and if it's a file and if its information is readable
            if (loadFile.exists() && loadFile.isFile() && loadFile.canRead()) {
                try {
                    FileLoader fl = new FileLoader();
                    data = fl.loadFile(loadFile);
                    initialize();
                    status.setText(String.format("Data loaded from %s", loadFile.getName()));
                    pause.setOnFinished(event1 -> status.setText(null));
                    pause.play();

                } catch (Exception e) {
                    status.setText("File contains no data or unreadable data.");
                    status.setTextFill(Color.RED);
                    pause.setOnFinished(event1 -> status.setText(null));
                    pause.play();
                }
            } else {
                status.setText("File doesn't exist!");
                status.setTextFill(Color.RED);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            }
        } else {
            status.setText("Canceled load.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    @FXML
    void quit(ActionEvent event) {
        Platform.exit();
    }

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
            } catch (Exception e) { // Exception: There was no data to save.
                status.setText("No data to save.");
                status.setTextFill(Color.RED);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            }
        } else { // File not found
            status.setText("save.txt file doesn't exist.");
            status.setTextFill(Color.RED);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    @FXML
    void saveAs(ActionEvent event) {
        // Create a new fileChooser
        FileChooser fc = new FileChooser();
        fc.setTitle("Create a text file to save to.");
        // Set initial directory to current directory
        fc.setInitialDirectory(new File("."));
        // Adding extension filter to only accept .txt files.
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Text files  (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extension);

        FileSaver fs = new FileSaver();

        // Create a new save file named what the user inputs
        File saveFile = fc.showSaveDialog(new Stage());
        if (saveFile != null) { // Check if the user cancels the operation (no file is created)
            try { // try-catch block to attempt saving data to new file
                fs.saveFile(saveFile, data); // invoke saveFile function in FileSaver.java
                status.setText(String.format("Saved data to %s", saveFile.getName())); // print success
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

    @FXML
    void tutorial(ActionEvent event) {
        //TODO
    }

    @FXML
    void viewGenres(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) {
            printGenres(movies);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    private void printGenres(ArrayList<Movie> movies) {
        String textData = String.format("%-15s %-15s\n", "Movie", "Genre");
        textData += "-----------------------------\n";

        for (Movie movie : movies) {
            textData += String.format("%-15s %-15s\n", movie.getName(), movie.getGenre());
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewLists(ActionEvent event) {
        ArrayList<List> lists = data.getLists();
        if (!lists.isEmpty()) {
            printLists(lists);
        } else {
            status.setText("You haven't added any lists.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    public void printLists(ArrayList<List> lists) {
        String textData = String.format("%-15s %-15s\n", "List", "Name");
        textData += "-----------------------------\n";

        for (List list : lists) {
            textData += String.format("%-15s %-15s\n", list.getType(), list.getName());
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewMovieLists(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) {
            printMoviesLists(movies);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    public void printMoviesLists(ArrayList<Movie> movies) {
        String textData = String.format("%-15s %-15s\n", "Name", "List");
        textData += "-----------------------------\n";

        for (Movie movie : movies) {
            textData += String.format("%-15s %-15s\n", movie.getName(), movie.getList());
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewMovieInfo(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        String movieName = movieInfo.getValue();
        for (Movie movie:movies){
            if(movie.getName().equals(movieName)){
                printMovieInfo(movie);
            }
        }

    }
    public void printMovieInfo(Movie movie) {
        String textData =("");
        textData += movie.toString();
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }
    @FXML
    void viewTopByList(ActionEvent event) {
        HashMap<String, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings
        ArrayList<String> top5List = data.getTop5List();
        if (!ratings.isEmpty()) {
            String list = topByList.getValue();
            data.storeTop5List(ratings, list);
            printTop5List(top5List, ratings);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    private void printTop5List(ArrayList<String> top5List, HashMap<String, Integer> ratings) {
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";

        for (String movie : top5List) {
            textData += String.format("%-15s %-15d\n", movie, ratings.get(movie));
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewTopByListType(ActionEvent event) {
        HashMap<String, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings

        ArrayList<String> top5Fav = data.getTop5Fav();
        ArrayList<String> top5Watched = data.getTop5Watched();
        ArrayList<String> top5WTW = data.getTop5WTW();

        if (!ratings.isEmpty()) {
            String type = topByListType.getValue();
            switch (type) {
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
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    private void printTopFav(ArrayList<String> top5Fav, HashMap<String, Integer> ratings) {
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";

        for (String movie : top5Fav) {
            textData += String.format("%-15s %-15d\n", movie, ratings.get(movie));
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }
    private void printTopWatched(ArrayList<String> top5Watched, HashMap<String, Integer> ratings) {
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";

        for (String movie : top5Watched) {
            textData += String.format("%-15s %-15d\n", movie, ratings.get(movie));
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }
    private void printTopWTW(ArrayList<String> top5WTW, HashMap<String, Integer> ratings) {
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";

        for (String movie : top5WTW) {
            textData += String.format("%-15s %-15d\n", movie, ratings.get(movie));
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewMovies(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) {
            printMovies(movies);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    public void printMovies(ArrayList<Movie> movies) {
        String textData = String.format("%-15s\n", "Name");
        textData += "-----------------------------\n";

        for (Movie movie : movies) {
            textData += String.format("%-15s\n", movie.getName());
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewRatings(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) {
            printRatings(movies);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    private void printRatings(ArrayList<Movie> movies) {
        String textData = String.format("%-15s %-15s\n", "Movie", "Ratins");
        textData += "-----------------------------\n";

        for (Movie movie : movies) {
            textData += String.format("%-15s %-15d\n", movie.getName(), movie.getRating());
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewTopByGenre(ActionEvent event) {
        HashMap<Movie, Genre.movieGenre> genreLookup = data.getGenreLookup(); // get the hashmap of movie genres
        HashMap<String, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings
        ArrayList<String> top5Genre = data.getTop5Genre();
        if (!genreLookup.isEmpty()) {
            Genre.movieGenre genre = topByGenre.getValue();
            data.storeTop5Genre(ratings, genre);
            printTopByGenre(top5Genre, ratings);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    private void printTopByGenre(ArrayList<String> top5Genre, HashMap<String, Integer> ratings) {
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";

        for (String movie : top5Genre) {
            textData += String.format("%-15s %-15s\n", movie, ratings.get(movie));
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewTopGenres() {
        //TODO
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) {
            data.storeTopGenres(movies);
            printTopGenres(movies);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    private void printTopGenres(ArrayList<Movie> movies) {
        //TODO
    }

    @FXML
    void viewTop5(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        HashMap<String, Integer> ratings = data.getRatingLookup(); // get the hashmap of ratings
        ArrayList<String> top5 = data.getTop5();
        if (!movies.isEmpty()) {
            data.storeTop5(ratings);
            printTop5(top5, ratings);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    private void printTop5(ArrayList<String> top5, HashMap<String, Integer> ratings) {
        String textData = String.format("%-15s %-15s\n", "Movie", "Rating");
        textData += "-----------------------------\n";

        for (String movie : top5) {
            textData += String.format("%-15s %-15s\n", movie, ratings.get(movie));
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    protected void initializeChoices() {
        ArrayList<List> lists = data.getLists();
        topByList.getItems().clear();
        if (!lists.isEmpty()) {
            for (List list : lists){
                String listName = list.getName();
                topByList.getItems().add(listName);
                topByList.setValue(lists.get(0).getName());
            }
        }

        ArrayList<Movie> movies = data.getMovies();
        movieInfo.getItems().clear();
        if (!movies.isEmpty()) {
            for (Movie movie : movies){
                String movieName = movie.getName();
                movieInfo.getItems().add(movieName);
                movieInfo.setValue(movies.get(0).getName());
            }
        }

        String[] types = {"Favourites", "Watched", "Want-to-watch"};
        topByListType.getItems().clear();
        topByListType.getItems().addAll(types);
        topByListType.setValue(types[0]);

        Genre.movieGenre[] genres = Genre.movieGenre.values();
        topByGenre.getItems().clear();
        topByGenre.getItems().addAll(genres);
        topByGenre.setValue(genres[0]);
    }
}