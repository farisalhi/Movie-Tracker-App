package movietracker.core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.data.List;
import movietracker.core.part2.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DeleteListController {
    Data data = Menu.getData();
    @FXML
    private TextField listName;

    @FXML
    private ChoiceBox<String> listType;

    @FXML
    void deleteList(ActionEvent event) {
        try {
            String name = listName.getText().trim();
            String type = listType.getValue();
            ArrayList<List> list = data.getLists();
            for (List list_single : list) {
                if (!name.isEmpty() && Objects.equals(list_single.getName(), name) &&
                        Objects.equals(list_single.getType(), type)) {
                    boolean success = data.deleteList(name,type);
                    if (success) {
                        ((Stage) listName.getScene().getWindow()).close();
                        ((Stage) listType.getScene().getWindow()).close();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Enter a valid movie name!.");
                    alert.showAndWait();
                }
            }
        }catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter an integer for the rating.");
            alert.showAndWait();
        }
    }
    protected void initializeChoices() {
        String[] types = {"Favourites", "Watched", "Want-to-watch"};
        listType.getItems().addAll(types);
        listType.setValue(types[0]);
    }
}