package movietracker.core;

import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import movietracker.core.data.Data;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import movietracker.core.data.List;
import movietracker.core.data.Movie;

import java.io.IOException;
import java.util.ArrayList;

public class MovieController {

    private Data data;

    private final PauseTransition pause = new PauseTransition(Duration.seconds(2));

    @FXML
    private Label status;

    @FXML
    private TextArea viewData;

    public void initialize() {
        data = new Data();
    }

    public void setData(Data data) {
        this.data = data;
    }

    @FXML
    void about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Movie Tracker");
        alert.setContentText("""
                Author: Faris Salhi, Ariel Motsi
                Email: faris.salhi@ucalgary.ca, ariel.motsi@ucalgary.ca
                Version: 1.0
                This is an application to add movies, lists, genres and ratings to organize your movies.
                """);
        alert.setHeaderText("About");
        alert.showAndWait();
    }

    @FXML
    void addGenre(ActionEvent event) {
        //TODO
    }

    @FXML
    void createList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("createlist.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 150);
            stage.setResizable(false);

            ListController listController = loader.getController();
            listController.setData(data);

            stage.setTitle("Create a list");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

//            List list = data.getList(ListController.listNumber);
//            status.setText(String.format("List '%s' created!", list.getName()));

            status.setText("Created list.");
            status.setTextFill(Color.GREEN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteList(ActionEvent event) {
        //TODO
    }

    @FXML
    void addMovie(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("addmovie.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 200, 150);
            stage.setResizable(false);

            AddMovieController addMovieController = loader.getController();
            addMovieController.setData(data);

            stage.setTitle("Add a movie");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            //Movie movie = ((AddMovieController)fxmlLoader.getController()).getMovie();
            //status.setText(String.format("Movie '%s' added to '%s'!", movie.getName(), movie.getList()));
            status.setText("Added movie.");
            status.setTextFill(Color.GREEN);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addRating(ActionEvent event) {
        //TODO
    }

    @FXML
    void load(ActionEvent event) {
        //TODO
    }

    @FXML
    void quit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void save(ActionEvent event) {
        //TODO
    }

    @FXML
    void saveAs(ActionEvent event) {
        //TODO
    }

    @FXML
    void tutorial(ActionEvent event) {
        //TODO
    }

    @FXML
    void viewGenres(ActionEvent event) {
        //TODO
    }

    @FXML
    void viewLists(ActionEvent event) {
        ArrayList<List> lists = data.getLists();
        if (!lists.isEmpty()) {
            printLists(lists);
        } else {
            status.setText("You haven't added any lists.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    public void printLists(ArrayList<List> lists) {
        String textData = String.format("%-15s %-15s\n", "List", "Name");
        textData += "-----------------------------\n";

        for (List list : lists) {
            textData += String.format("%-15s %-15s\n", list.getType(), list.getName());
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewMovieLists(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) {
            printMoviesLists(movies);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    public void printMoviesLists(ArrayList<Movie> movies) {
        String textData = String.format("%-15s %-15s\n", "Name", "List");
        textData += "-----------------------------\n";

        for (Movie movie : movies) {
            textData += String.format("%-15s %-15s\n", movie.getName(), movie.getList());
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewMovies(ActionEvent event) {
        ArrayList<Movie> movies = data.getMovies();
        if (!movies.isEmpty()) {
            printMovies(movies);
        } else {
            status.setText("You haven't added any movies.");
            pause.setOnFinished(event1 -> status.setText(null));
            pause.play();
        }
    }

    public void printMovies(ArrayList<Movie> movies) {
        String textData = String.format("%-15s\n", "Name");
        textData += "-----------------------------\n";

        for (Movie movie : movies) {
            textData += String.format("%-15s\n", movie.getName());
        }
        viewData.setFont(Font.font("PT Mono"));
        viewData.setText(textData);
    }

    @FXML
    void viewRatings(ActionEvent event) {
        //TODO
    }

    @FXML
    void viewTop5(ActionEvent event) {
        //TODO
    }
}