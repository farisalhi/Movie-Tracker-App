package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.data.Genre;
import movietracker.core.data.List;
import movietracker.core.data.Movie;
import movietracker.core.part2.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AddMovieController {

    //private Data data;
    Data data = Menu.getData();

    // Static constant for movie counting.
   // public static int movieNumber = 0;

    @FXML
    private TextField movieName;

    @FXML
    private TextField movieRating;

    @FXML
    private ChoiceBox<String> listChoice;

    @FXML
    private ChoiceBox<Genre.movieGenre> genreChoice;
    private PauseTransition pause;
    private Label status;
    private TextArea viewData;



    @FXML
    void addMovie(ActionEvent event) {
        Menu.movieNumber++;
        String listName = listChoice.getValue();
        Genre.movieGenre genre = genreChoice.getValue();
        try {
            String name = movieName.getText();
            String ratingText = movieRating.getText();
                if (!Objects.equals(ratingText, null) && !Objects.equals(listName, null) && !Objects.equals(name, null)) {
                    int rating = Integer.parseInt(ratingText);
                    if (rating >= 0 && rating <= 5) {
                        boolean success = data.storeNewMovie(Menu.movieNumber, listName, name, rating, genre);
                        if (success) {
                            ((Stage) movieName.getScene().getWindow()).close();
                            status.setText("Movie Added.");
                            status.setTextFill(Color.GREEN);
                            pause.setOnFinished(event1 -> status.setText(null));
                            pause.play();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("You've already added this movie. Please choose a different movie.");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Rating should be on a scale of 0-5.");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter a name, list, genre and rating.");
                    alert.showAndWait();
                }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter an integer for the rating.");
            alert.showAndWait();
        }
    }

    @FXML
    void createNewList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("movietracker.fxml"));
            Scene scene = new Scene(loader.load(), 200, 150);
            MovieController movieController = loader.getController();
            movieController.createList(event);
            ((Stage) listChoice.getScene().getWindow()).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void initializeChoices() {
        ArrayList<List> lists = data.getLists();
        listChoice.getItems().clear();
        if (!lists.isEmpty()) {
            for (List list:lists){
                String listName = list.getName();
                listChoice.getItems().add(listName);
            }
        }

        Genre.movieGenre[] genres = Genre.movieGenre.values();
        genreChoice.getItems().addAll(genres);
        genreChoice.setValue(genres[0]);
    }
    public void setup(Label status, TextArea viewData, PauseTransition pause) {
        this.status = status;
        this.viewData = viewData;
        this.pause = pause;
    }
}
