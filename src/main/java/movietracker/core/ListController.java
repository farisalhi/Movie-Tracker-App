package movietracker.core;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.data.List;
import movietracker.core.part2.Menu;

public class ListController {
    Data data = Menu.getData();


    @FXML
    private TextField listName;

    @FXML
    private ChoiceBox<String> listType;
    private Label status;
    private TextArea viewData;
    private PauseTransition pause;

    @FXML
    public void createList(ActionEvent event) {
        String type = listType.getValue();
        String name = listName.getText();
        if (!name.isEmpty()) {
            boolean success = data.storeNewList(Menu.listNumber++, type, name);
            if (success) {
                ((Stage) listName.getScene().getWindow()).close();
                status.setText("Created list.");
                status.setTextFill(Color.GREEN);
                pause.setOnFinished(event1 -> status.setText(null));
                pause.play();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This list name is already in use. Please choose a different name.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please name your list.");
            alert.showAndWait();
        }
    }

    protected void initializeChoices() {
        String[] types = {"Favourites", "Watched", "Want-to-watch"};
        listType.getItems().clear();
        listType.getItems().addAll(types);
        listType.setValue(types[0]);
    }
    public void setup(Label status, TextArea viewData, PauseTransition pause) {
        this.status = status;
        this.viewData = viewData;
        this.pause = pause;
    }
}
