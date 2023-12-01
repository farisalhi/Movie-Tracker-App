package movietracker.core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.data.Genre;
import movietracker.core.data.List;
import movietracker.core.data.Movie;

import java.util.ArrayList;

public class AddMovieController {

    private Data data;

    // Static constant for movie counting.
    public static int movieNumber = 0;

    @FXML
    private TextField movieName;

    @FXML
    private TextField movieRating;

    @FXML
    private ChoiceBox<List> listChoice;

    @FXML
    private ChoiceBox<Genre.movieGenre> genreChoice;

    public void setData(Data data) {
        this.data = data;
        initializeChoices();
    }

    public Movie getMovie() {
        return data.getMovie(movieNumber);
    }

    @FXML
    void addMovie(ActionEvent event) {
        List list = listChoice.getValue();
        Genre.movieGenre genre = genreChoice.getValue();
        try {
            String name = movieName.getText();
            String ratingText = movieRating.getText();
                if (!ratingText.isEmpty()) {
                    int rating = Integer.parseInt(ratingText);
                    if (rating >= 0 && rating <= 5) {
                        boolean success = data.storeNewMovie(movieNumber, list.getName(), name, rating, genre);
                        if (success) {
                            ((Stage) movieName.getScene().getWindow()).close();
                            movieNumber++;
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
                    alert.setContentText("Please enter a rating.");
                    alert.showAndWait();
                }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter the name of the movie.");
            alert.showAndWait();
        }
    }

    private void initializeChoices() {
        ArrayList<List> lists = data.getLists();
        if (!lists.isEmpty()){
            listChoice.getItems().addAll(lists);
            listChoice.setValue(lists.get(0));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Create a list first!");
            alert.showAndWait();
        }
        Genre.movieGenre[] genres = Genre.movieGenre.values();
        genreChoice.getItems().addAll(genres);
        genreChoice.setValue(genres[0]);
    }
}
