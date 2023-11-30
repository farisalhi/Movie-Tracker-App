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
        String name = movieName.getText();
        int rating = 0;
        if (!name.isEmpty()) {
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
            alert.setContentText("Please type the name of the movie you'd like to add.");
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
