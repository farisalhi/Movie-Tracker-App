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
                            Menu.movieNumber++;
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

    /**
     * Function to launch popup for list creation
     * @param event New list. Button click.
     */
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

    /**
     * Function to initialize all the choice boxes
     */
    protected void initializeChoices() {
        // List choice box
        ArrayList<List> lists = data.getLists(); // get the list of lists
        listChoice.getItems().clear(); // clear the list in case of previous data
        if (!lists.isEmpty()) { // Check if the list is empty
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
     *
     * @param status
     * @param viewData
     * @param pause
     */
    public void setup(Label status, TextArea viewData, PauseTransition pause) {
        this.status = status;
        this.viewData = viewData;
        this.pause = pause;
    }
}
