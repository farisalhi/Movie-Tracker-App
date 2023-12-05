package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.part2.Menu;

/**
 * List Controller class
 * The class containing all FXML elements and functions relating to list creation in the fxml popup
 *
 * @author Faris Salhi (30117469), Ariel Motsi ()
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Controller class to launch popup for list creation
 */
public class ListController {

    // Get updated data from menu
    Data data = Menu.getData();

    // FXML elements
    @FXML
    private TextField listName;
    @FXML
    private ChoiceBox<String> listType;
    private Label status;
    private TextArea viewData;
    private PauseTransition pause;

    /**
     * Function to create a new list
     * @param event Create list. Button click
     */
    @FXML
    public void createList(ActionEvent event) {
        String type = listType.getValue(); // get the list type from choice box
        String name = listName.getText(); // get the list name from text field
        if (!name.isEmpty()) { // check if the user hasn't left the text field blank
            // Store a new list and update list number
            boolean success = data.storeNewList(Menu.listNumber, type, name);
            if (success) { // if data storage was successful, close the stage and display success message.
                Menu.listNumber++; // update list number by 1
                ((Stage) listName.getScene().getWindow()).close(); // close popup
                // display status confirmation message
                status.setText("Created list.");
                status.setTextFill(Color.GREEN);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            } else { // hashcode warning about duplicate list based on name
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This list name is already in use. Please choose a different name.");
                alert.showAndWait();
            }
        } else { // Alert user to enter a name
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please name your list.");
            alert.showAndWait();
        }
    }

    /**
     * Function to initialize all the choice boxes
     */
    protected void initializeChoices() {
        // List type choice box
        String[] types = {"Favourites", "Watched", "Want-to-watch"};
        listType.getItems().clear(); // clear the list in case of previous data
        listType.getItems().addAll(types); // add all the list types to the choice box items
        listType.setValue(types[0]); // set the initial value to the first item
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
