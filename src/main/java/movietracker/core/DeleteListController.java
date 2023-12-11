package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.data.List;
import movietracker.core.part2.Menu;
import java.util.ArrayList;
import java.util.Objects;

/*
 * Delete List class
 * The class containing all FXML elements and functions relating to list deletion in the fxml popup
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Controller class to launch popup for list deletion
 */
public class DeleteListController {
    // Get updated data from menu
    Data data = Menu.getData();

    // FXML elements
    @FXML
    private ChoiceBox<String> listName;
    private Label status;
    private TextArea viewData;
    private PauseTransition pause;

    /**
     * Function to delete a list
     * @param event Delete list. Button click.
     */
    @FXML
    void deleteList(ActionEvent event) {
        String name = listName.getValue(); // get list name from choice box
        ArrayList<List> lists = data.getLists(); // get list of all lists
        for (int i = 0; i < lists.size(); i++) { // loop each list
            List list = lists.get(i);
            if (Objects.equals(list.getName(), name)) { // if the name of the selected list is a real list
                boolean success = data.deleteList(name); // check if the delete list function worked
                if (success) {
                    // if successful, close the stage and display confirmation message
                    ((Stage) listName.getScene().getWindow()).close();
                    status.setText("Deleted List.");
                    status.setTextFill(Color.GREEN);
                    pause.setOnFinished(event1 -> status.setText(null));
                    pause.play();
                    viewData.setText("");
                    i--;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("List doesn't exist!");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Function to initialize all the choice boxes
     */
    protected void initializeChoices() {
        // List choice box
        ArrayList<List> lists = data.getLists(); // get the list of lists
        listName.getItems().clear(); // clear the list in case of previous data
        if (!lists.isEmpty()) {
            listName.setValue(lists.get(0).getName()); // set the initial value to the first item
            for (List list : lists) {
                // loop through the list and add the names to the choice box items
                String list_name = list.getName();
                listName.getItems().add(list_name);
            }
        }

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