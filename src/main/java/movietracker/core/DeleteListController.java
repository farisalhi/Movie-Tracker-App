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
        try {
            String name = listName.getValue();
            ArrayList<List> lists = data.getLists();
            for(int i = 0;i<lists.size(); i++){
                List list = lists.get(i);
                if (Objects.equals(list.getName(), name)) {
                    boolean success = data.deleteList(name);
                    if (success) {
                        ((Stage) listName.getScene().getWindow()).close();
                        status.setText("Deleted List.");
                        status.setTextFill(Color.GREEN);
                        pause.setOnFinished(event1 -> status.setText(null));
                        pause.play();
                        viewData.setText("");
                        i--;
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

    /**
     * Function to initialize all the choice boxes
     */
    protected void initializeChoices() {
        // List choice box
        ArrayList<List> lists = data.getLists(); // get the list of lists
        listName.getItems().clear(); // clear the list in case of previous data
        if (!lists.isEmpty()) {
            for (List list : lists) {
                // loop through the list and add the names to the choice box items
                String list_name = list.getName();
                listName.getItems().add(list_name);
            }
        }

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