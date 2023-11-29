package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import movietracker.core.data.Data;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import movietracker.core.data.List;
import movietracker.core.data.Movie;

import java.io.IOException;
import java.util.ArrayList;

public class MovieController {

    private static Data data = new Data();

    private final PauseTransition pause = new PauseTransition(Duration.seconds(2));

    @FXML
    private Label status;

    @FXML
    private TextArea viewData;

    public void initialize() {
        data = new Data();

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
    void addGenre(ActionEvent event) {

    }


    @FXML
    void createList(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("createlist.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 200, 150);
            stage.setResizable(false);
            stage.setTitle("Create a list");
            stage.setScene(scene);
            ((ListController)fxmlLoader.getController()).setData(data);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            String listName = ((ListController)fxmlLoader.getController()).getListName();
            status.setText(String.format("List '%s' created!", listName));
            status.setTextFill(Color.GREEN);
        } catch (Exception e) {
            status.setText("Can't launch window for create list!");
            status.setTextFill(Color.RED);
        }
    }

    @FXML
    void deleteList(ActionEvent event) {

    }

    @FXML
    void addMovie(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("addmovie.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 200, 150);
            stage.setResizable(false);
            stage.setTitle("Add movie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            status.setText("Can't launch window for add movie!");
            status.setTextFill(Color.RED);
        }
    }

    @FXML
    void addRating(ActionEvent event) {

    }

    @FXML
    void load(ActionEvent event) {

    }

    @FXML
    void quit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void saveAs(ActionEvent event) {

    }

    @FXML
    void tutorial(ActionEvent event) {

    }

    @FXML
    void viewGenres(ActionEvent event) {

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
        String textData = "";
        textData += String.format("%-15s\t'%s'\n", "List", "Name");
        textData += ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        for (List list : lists) {
            textData += String.format("%-15s\t'%s'\n", list.getType(), list.getName());
        }
        viewData.setText(textData);
    }

    @FXML
    void viewMovieLists(ActionEvent event) {

    }

    @FXML
    void viewMovies(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) {
            for (Movie movie : movies) {
                viewData.setText(movie.getName());
            }
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    @FXML
    void viewRatings(ActionEvent event) {

    }

    @FXML
    void viewTop5(ActionEvent event) {

    }

}