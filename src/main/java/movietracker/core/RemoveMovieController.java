package movietracker.core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.part2.Menu;

import java.util.HashMap;

public class RemoveMovieController {
    Data data = Menu.getData();
    @FXML
    private TextField movieName;

    @FXML
    void removeMovie(ActionEvent event) {
        try {
            String name = movieName.getText().trim();
            HashMap<String, Integer> ratingLookup = data.getRatingLookup();
            if (!name.isEmpty() && ratingLookup.containsKey(name)) {
                boolean success = data.removeMovie(name);
                if (success) {
                    ((Stage) movieName.getScene().getWindow()).close();
                }
            }else {
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
}
