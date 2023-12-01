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
import movietracker.core.data.List;
import movietracker.core.data.Movie;
import movietracker.core.part2.Menu;
import movietracker.core.util.FileLoader;
import movietracker.core.util.FileSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MovieController {

    //private static Data data;
    Data data = Menu.getData();

    private final PauseTransition pause = new PauseTransition(Duration.seconds(2));

    @FXML
    private Label status;

    @FXML
    private TextArea viewData;

    public void initialize() {
        data = new Data();
    }

    /*public void setData(Data data) {
        this.data = data;
    }*/

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
    void addGenre(ActionEvent event) {
        //TODO
    }

    @FXML
    void createList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("createlist.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 150);
            stage.setResizable(false);

            ListController listController = loader.getController();
            //listController.setData(data);
            listController.initializeChoices();
            stage.setTitle("Create a list");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

//            List list = data.getList(ListController.listNumber);
//            status.setText(String.format("List '%s' created!", list.getName()));

            status.setText("Created list.");
            status.setTextFill(Color.GREEN);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteList(ActionEvent event) {
        //TODO
    }

    @FXML
    void addMovie(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("addmovie.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 200);
            stage.setResizable(false);

            AddMovieController addMovieController = loader.getController();
            //addMovieController.setData(data);

            stage.setTitle("Add a movie");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            //Movie movie = ((AddMovieController)fxmlLoader.getController()).getMovie();
            //status.setText(String.format("Movie '%s' added to '%s'!", movie.getName(), movie.getList()));
            status.setText("Added movie.");
            status.setTextFill(Color.GREEN);
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

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
        //TODO
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
        //TODO
    }

    @FXML
    void viewTop5(ActionEvent event) {
        //TODO
    }
}