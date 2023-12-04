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

public class DeleteListController {
    Data data = Menu.getData();
    @FXML
    private ChoiceBox<String> listName;
    private Label status;
    private TextArea viewData;
    private PauseTransition pause;

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

    public void setup(Label status, TextArea viewData, PauseTransition pause) {
        this.status = status;
        this.viewData = viewData;
        this.pause = pause;
    }
}