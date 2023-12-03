package movietracker.core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movietracker.core.data.Data;
import movietracker.core.data.List;
import movietracker.core.part2.Menu;

public class ListController {
    //private Data data;
    Data data = Menu.getData();

    //public static int listNumber = 0;

    @FXML
    private TextField listName;

    @FXML
    private ChoiceBox<String> listType;

    /*public void setData(Data data) {
        this.data = data;
        initializeChoices();
    }*/

   /* public List getList() {
        return data.getList(listNumber);
    }*/

    @FXML
    public void createList(ActionEvent event) {
        String type = listType.getValue();
        String name = listName.getText();
        if (!name.isEmpty()) {
            boolean success = data.storeNewList(Menu.listNumber++, type, name);
            if (success) {
                ((Stage) listName.getScene().getWindow()).close();
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
        listType.getItems().addAll(types);
        listType.setValue(types[0]);
    }
}
