package movietracker.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("movietracker.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setResizable(false);
        stage.setTitle("Movie Tracker!");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        if (args.length == 1) { // loading from command-line
//            File loadFile = new File(args[0]); // first argument is the file we want to load from
//            // load the file and run the program
//            MovieController.loadFile(loadFile);
//        }
        launch(args);
    }
}