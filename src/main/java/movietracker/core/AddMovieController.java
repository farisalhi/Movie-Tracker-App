package movietracker.core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import movietracker.core.data.Data;
import movietracker.core.data.Genre;
import movietracker.core.data.List;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMovieController implements Initializable {

    private static final Data data = new Data();

    // Static constant for movie counting.
    public static int movieNumber = 0;

    @FXML
    private TextField movieName;

    @FXML
    private ChoiceBox<List> listChoice;

    @FXML
    private ChoiceBox<Genre.movieGenre> genreChoice;

    @FXML
    void addMovie(ActionEvent event) {
        List list = listChoice.getValue();

        Genre.movieGenre[] genres = Genre.movieGenre.values();
        genreChoice.getItems().addAll(genres);
        genreChoice.setValue(genres[0]);
        Genre.movieGenre genre = genreChoice.getValue();

        String name = movieName.getText();
        int rating = 0;

        movieNumber++;
        // Execute the storeNewMovie function to add the movie to the list.
        data.storeNewMovie(movieNumber, list.getName(), name, rating, genre);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<List> lists = data.getLists();
        if (!lists.isEmpty()) {
            listChoice.getItems().addAll(lists);
            listChoice.setValue(lists.get(0));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Create a list first!");
            alert.showAndWait();
        }
    }
}
