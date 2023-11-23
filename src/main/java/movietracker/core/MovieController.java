package movietracker.core;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class MovieController {
    @FXML
    private Label status;

    @FXML
    private Label viewData;

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
    void addList(ActionEvent event) {

    }

    @FXML
    void addMovie(ActionEvent event) {

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

}