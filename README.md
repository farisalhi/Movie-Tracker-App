# Computer Science 219 Project

## Movie Tracker
This program is a project where we used Java to create an app where a user can store and keep track of Movies they have watched, want-to-watch and their favourites. The app also allows the user to view different types of data about the movies they have stored by using the GUI interface's buttons and options. Running the program allows the user to:

### How to run
**Without .jar file** <br>
You can run the application using the IDE or with this command in the target/classes folder: <br>
java --module-path "path to javafx library" --add-modules javafx.controls,javafx.fxml movietracker.core.MainGUI

**With .jar file** <br>
Run it through the jar file in the out/artifacts/MovieTracker folder: <br>
java --module-path "path to javafx library" --add-modules javafx.controls,javafx.fxml -jar MovieTracker.jar movietracker.core.MainGUI



### Menu Bar Functions
* Create and delete different lists that movies will be stored in
* Add movies by adding the name of the movie, a rating, a genre and the list it will be added to.
* Added movies can be deleted by selecting the movie name in the Delete option of the Movie menu option.
* Movies and lists can be loaded from a text file of the user's choice
* The user can also save the movies and lists they have created in a text file with a name of their choice (Save-As option) or a default name (save.txt).
* A brief guide on what the app does and where to find certain features is in the Help menu option.
* Access the About in the menu to find details about the authors and program version.

### Sort Functions (left side) 
* The user can view a specific movie's info (movie name, rating, genre, movie number, list).
* Movies can be sorted to get a top 5 rated by list name, list type (Favourites, Watched, Want-to-watch), genre and the overall top 5 rated movies among all the stored movies.
* The user can also get the most popular genre which will be the genre with the most movies along with the number of movies in the genre.

### View Functions (right side)
* The user can view all the movies that have been added or all the lists that have been created.
* They can also see the movies and the lists they were added to, the movies and their respective ratings or the movies and the genres they have been put in.

### Print (middle area)
* This is the area where the different functions' display will show.

### Status (bottom of window)
* The status will display the success or failure messages as the program runs and different functions are called.

### How to set up this Movie Tracker
1. Clone/Pull the project to have a copy you can run on your device
2. Look for the MainGUI.java class (src/main/java/movietracker.core/MainGUI) and run it
3. A window should come up with the GUI that you can interact with
