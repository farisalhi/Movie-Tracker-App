package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.data.Genre;
import movietracker.core.data.List;
import movietracker.core.part2.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Add Movie Controller class
 * The class containing all FXML elements and functions relating to movie adding in the fxml popup
 *
 * @author Faris Salhi (30117469), Ariel Motsi ()
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Controller class to launch popup for movie adding
 */
public class AddMovieController {

    // Get updated data from menu
    Data data = Menu.getData();

    // FXML elements
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

    /**
     * Function to add a new movie
     * @param event Add movie. Button click.
     */
    @FXML
    void addMovie(ActionEvent event) {
        String listName = listChoice.getValue(); // get name of movie from choice box
        Genre.movieGenre genre = genreChoice.getValue(); // get genre from choice box
        try { // try to get an integer value for rating
            String name = movieName.getText(); // get name of movie from text field
            String ratingText = movieRating.getText(); // get rating from text field
            // check if all input boxes are filled
                if (!Objects.equals(ratingText, null) && !Objects.equals(listName, null) && !Objects.equals(name, null)) {
                    int rating = Integer.parseInt(ratingText); // parse rating into integer
                    if (rating >= 0 && rating <= 5) { // check if it's within bounds
                        // attempt to store the movie
                        boolean success = data.storeNewMovie(Menu.movieNumber, listName, name, rating, genre);
                        if (success) {
                            // if successful, increase movieNumber by one, close the stage, and display confirmation message
                            Menu.movieNumber++;
                            ((Stage) movieName.getScene().getWindow()).close();
                            status.setText("Movie Added.");
                            status.setTextFill(Color.GREEN);
                            pause.setOnFinished(event1 -> status.setText(null));
                            pause.play();
                        } else { // HashCode finds duplicate movie based on name
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("You've already added this movie. Please choose a different movie.");
                            alert.showAndWait();
                        }
                    } else { // Rating wasn't within bounds
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Rating should be on a scale of 0-5.");
                        alert.showAndWait();
                    }
                } else { // Not all fields were filled
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter a name, list, genre and rating.");
                    alert.showAndWait();
                }
        } catch (IllegalArgumentException e) { // Rating wasn't an integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter an integer for the rating.");
            alert.showAndWait();
        }
    }

    /**
     * Function to launch popup for list creation
     * @param event New list. Button click.
     */
    @FXML
    void createNewList(ActionEvent event) {
        try {
            // create new fxml loader and get fxml file
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("movietracker.fxml"));
            Scene scene = new Scene(loader.load(), 200, 150); // create new scene
            // create controller instance of movieController
            MovieController movieController = loader.getController();
            movieController.createList(event);
            ((Stage) listChoice.getScene().getWindow()).close(); // close window upon button click
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to initialize all the choice boxes
     */
    protected void initializeChoices() {
        // List choice box
        ArrayList<List> lists = data.getLists(); // get the list of lists
        listChoice.getItems().clear(); // clear the list in case of previous data
        if (!lists.isEmpty()) { // Check if the list is empty
            listChoice.setValue(lists.get(0).getName()); // set the initial value to the first item
            for (List list:lists){
                // loop through the list and add the names to the choice box items
                String listName = list.getName();
                listChoice.getItems().add(listName);
            }
        }

        // Genre choice box
        Genre.movieGenre[] genres = Genre.movieGenre.values(); // get the list of genres
        genreChoice.getItems().clear(); // clear the list in case of previous data
        genreChoice.getItems().addAll(genres); // add all the genres to the choice box items
        genreChoice.setValue(genres[0]); // set the initial value to the first item
    }

    /**
     * Setup function to initialize status label, view data, and pause transition
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
