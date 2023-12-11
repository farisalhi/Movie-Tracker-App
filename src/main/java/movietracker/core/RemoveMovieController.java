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

/*
 * Movie Deletion class
 * The class containing all FXML elements and functions relating to movie deletion in the fxml popup
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 10, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Controller class to launch popup for movie deletion
 */
public class RemoveMovieController {
    // Get updated data from menu
    Data data = Menu.getData();

    // FXML elements
    @FXML
    private ChoiceBox<String> movieName;
    private Label status;
    private TextArea viewData;
    private PauseTransition pause;

    /**
     * Function to remove a movie
     * @param event Remove movie. Button click
     */
    @FXML
    void removeMovie(ActionEvent event) {
        try {
            String name = movieName.getValue(); // Get the string name of the movie to be removed
            boolean success = data.removeMovie(name);
            if (success) { // if remove is successful, close the window and display confirmation
                ((Stage) movieName.getScene().getWindow()).close();
                status.setTextFill(Color.GREEN);
                status.setText("Deleted movie.");
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter an integer for the rating.");
            alert.showAndWait();
        }
    }

     /**
     * Function to initialize all the choice boxes
     */
    protected void initializeChoices() {
        ArrayList<Movie> movies = data.getMovies(); // get the list of movies
        movieName.getItems().clear(); // clear the list in case of previous data
        if (!movies.isEmpty()) { // check if it's empty
            for (Movie movie : movies) {
                // loop through the list and add the names to the choice box items
                String listName = movie.getName();
                movieName.getItems().add(listName);
                movieName.setValue(movies.get(0).getName());
            }
        }
    }

    /**
     *
     * @param status Label for status updates
     * @param viewData TextArea for viewing data
     * @param pause PauseTransition for label timeout
     */
    public void setup(Label status, TextArea viewData, PauseTransition pause) {
        this.status = status;
        this.viewData = viewData;
        this.pause = pause;
    }
}
