package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Movie Tracker Application main
 * The main class to set up files and launch the gui scene
 *
 * @author Faris Salhi (30117469), Ariel Motsi ()
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Application class to set up files launch gui scene.
 */
public class MainGUI extends Application {
    /**
     * Function to initialize fxml stage and receive command-line arguments
     * @param stage The gui stage where the main application is run
     * @throws IOException If an I/O exception occurs during loading of FXML or scene creation.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Retrieve argument parameters from command-line
        Parameters parameters = getParameters();
        List<String> args = parameters.getRaw();

        // Create a new fxmlLoader from the correct controller fxml path
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("movietracker.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600); // initialize a new scene
        // get the controller instance from the fxmlLoader
        MovieController movieController = fxmlLoader.getController();
        stage.setResizable(false); // make sure user can't resize window
        stage.setTitle("Movie Tracker!"); // set application title
        // initialize stage and present
        stage.setScene(scene);
        stage.show();

        // Check for command-line arguments
        if (args.size() == 1) { // loading from command-line
            File loadFile = new File(args.get(0)); // first argument is the file we want to load from
            // get the status and pause variables from the moviecontroller instance
            Label status = movieController.status;
            PauseTransition pause = movieController.pause;
            // set up the variables and load the file
            movieController.setup(status, pause);
            movieController.loadFile(loadFile);

        } else if (args.size() > 1) { // invalid argument length
            System.out.println("Should be one argument: file.txt");
        }
    }

    /**
     * Main to launch gui scene with program args
     * @param args String array containing program arguments.
     */
    public static void main(String[] args) {
        launch(args); // launch application.
    }
}