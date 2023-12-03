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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DeleteListController {
    Data data = Menu.getData();
    @FXML
    private ChoiceBox<String> listName;

    @FXML
    void deleteList(ActionEvent event) {
        try {
            String name = listName.getValue();
            ArrayList<List> lists = data.getLists();
            for (List list : lists) {
                if (!name.isEmpty() && Objects.equals(list.getName(), name)) {
                    boolean success = data.deleteList(name);
                    if (success) {
                        ((Stage) listName.getScene().getWindow()).close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Enter a valid list name!.");
                        alert.showAndWait();
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter an integer for the rating.");
            alert.showAndWait();
        }
    }

    protected void initializeChoices() {
        ArrayList<List> lists = data.getLists();
        listName.getItems().clear();
        if (!lists.isEmpty()) {
            for (List list : lists) {
                String list_name = list.getName();
                listName.getItems().add(list_name);
            }
        }
    }
}