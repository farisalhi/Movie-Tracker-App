package movietracker.core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movietracker.core.data.Data;

import java.net.URL;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    private Data data;
    public static int listNumber = 0;
    final String FAVOURITE = "Favourites";
    final String WATCHED = "Watched";
    final String WANT_TO_WATCH = "Want-to-watch";

    @FXML
    private TextField listName;

    @FXML
    private ChoiceBox<String> listType;

    public void setData(Data data) {
        this.data = data;
    }

    public String getListName() {
        return listName.getText();
    }

    @FXML
    public void createList(ActionEvent event) {
        String type = listType.getValue();
        String name = listName.getText();
        if (!name.isEmpty()) {
            boolean success = data.storeNewList(listNumber, type, name);
            if (success) {
                ((Stage) listName.getScene().getWindow()).close();
                listNumber++;
            } else {
                System.out.println("This list already exists!");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please name your list.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] types = {FAVOURITE, WATCHED, WANT_TO_WATCH};
        listType.getItems().addAll(types);
        listType.setValue(types[0]);
    }
}
