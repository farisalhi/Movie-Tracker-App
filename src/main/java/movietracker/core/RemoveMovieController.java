package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.data.List;
import movietracker.core.data.Movie;
import movietracker.core.part2.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class RemoveMovieController {
    Data data = Menu.getData();
    @FXML
    private ChoiceBox<String> movieName;
    private Label status;
    private TextArea viewData;
    private PauseTransition pause;

    @FXML
    void removeMovie(ActionEvent event) {
        try {
            String name = movieName.getValue().trim();
            HashMap<String, Integer> ratingLookup = data.getRatingLookup();
            if (!name.isEmpty() && ratingLookup.containsKey(name)) {
                boolean success = data.removeMovie(name);
                if (success) {
                    ((Stage) movieName.getScene().getWindow()).close();
                    status.setTextFill(Color.GREEN);
                    status.setText("Deleted movie.");
                    pause.setOnFinished(event1 -> status.setText(null));
                    pause.play();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Enter a valid movie name!.");
                alert.showAndWait();
            }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter an integer for the rating.");
            alert.showAndWait();
        }
    }

    protected void initializeChoices() {
        ArrayList<Movie> movies = data.getMovies();
        movieName.getItems().clear();
        if (!movies.isEmpty()) {
            for (Movie movie : movies) {
                String listName = movie.getName();
                movieName.getItems().add(listName);
            }
        }
    }
    public void setup(Label status, TextArea viewData, PauseTransition pause) {
        this.status = status;
        this.viewData = viewData;
        this.pause = pause;
    }
}
