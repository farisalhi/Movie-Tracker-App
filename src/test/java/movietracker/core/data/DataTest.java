package movietracker.core.data;

import movietracker.core.part2.Menu;
import org.junit.jupiter.api.Test;

import java.lang.management.MonitorInfo;
import java.util.ArrayList;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;

/*
 * Movie Tracker Application Data Test class
 * The test class containing tests for all non-I/O and non-GUI data functions in the Data class
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * DataTest class containing all junit tests for Data class
 */
class DataTest {

    @Test
    void storeNewMovie() {
        Data data = new Data();
        //Test Data
        int movieNum = 1;
        String movieList = "Favourites";
        String expectedName = "Blade Runner";
        Genre.movieGenre movieGenre = Genre.movieGenre.Science_Fiction;
        int rating = 5;
        //Function stores Test Data
        data.storeNewMovie(movieNum, movieList, expectedName, rating, movieGenre);
        Movie movie = data.getMovie(movieNum);
        movie.setRating(rating);
        movie.setGenre(movieGenre);
        // Retrieve the result
        movie = data.getMovies().get(0);
        int size = data.getMovies().size();
        // Compare results to expected values
        assertEquals(1, size, "Size of list is not 1");
        assertEquals(movie.getNum(), movieNum, "The movie numbers do not match");
        assertEquals(movie.getList(), movieList, "The list that the movies are in do not match");
        assertEquals(movie.getName(), expectedName, "The movie names do not match");
        assertEquals(movie.getGenre(), movieGenre, "The genres do not match");
        assertEquals(movie.getRating(), rating);

        // Retrieve the result
        boolean exists = data.getMovieLookup().containsKey(movieNum);
        int actualSize = data.getMovieLookup().size();
        String actualName = data.getMovie(movieNum).getName();

        assertEquals(1, actualSize, "Size of Hashmap is not 1");
        assertTrue(exists, "The movie number has not been stored");

        assertEquals(expectedName, actualName, "The names of the movies do not match");
    }

    @Test
    void storeNewList() {
        Data data = new Data();
        //Test Data
        int listNum = 1;
        String listType = "Favourites";
        String expectedName = "Favs";
        //Function stores Test Data
        data.storeNewList(listNum, listType, expectedName);
        // Retrieve the result
        List list = data.getLists().get(0);
        int size = data.getLists().size();

        // Compare actual results to expected results
        assertEquals(1, size, "Size of list is not 1");
        assertEquals(list.getNum(), listNum, "The list numbers do not match");
        assertEquals(list.getType(), listType, "The list types do not match");
        assertEquals(list.getName(), expectedName, "The list names do not match");

        // Retrieve the result
        boolean exists = data.getListLookup().containsKey(listNum);
        int actualSize = data.getListLookup().size();
        String actualName = data.getListLookup().get(listNum).getName();

        //Compare results to expected results
        assertEquals(1, actualSize, "Size of Hashmap is not 1");
        assertTrue(exists, "The list number has not been stored");
        assertEquals(expectedName, actualName, "The names of the lists do not match");
    }

    @Test
    void getListsTest() {
        Data data = new Data();

        //Test Data
        int listNum = 1;
        String listType = "Favourites";
        String listName = "Favs";
        // Store data using function
        data.storeNewList(listNum, listType, listName);
        //Retrieve the results
        List list = data.getLists().get(0);
        int size = data.getLists().size();
        //Compare the actual results to the expected results
        assertEquals(1, size, "Size of list is not 1");
        assertEquals(list.getNum(), listNum, "The list numbers do not match");
        assertEquals(list.getType(), listType, "The list types do not match");
        assertEquals(list.getName(), listName, "The list names do not match");
    }

    @Test
    void getMovieListTest() {
        Data data = new Data();

        //Test Data 1
        int movieNum1 = 1;
        String listName1 = "Favourites";
        String movieName1 = "Blade Runner";
        Genre.movieGenre movieGenre = null;
        int rating1 = 5;
        //Storing test data using function
        data.storeNewMovie(movieNum1, listName1, movieName1, rating1, movieGenre);
        //Retrieve the result
        ArrayList<Movie> movieList = data.getMovies();
        Movie movie1 = movieList.get(0);
        int size1 = movieList.size();
        //Compare results to expected values
        assertEquals(1, size1, "Size of list is not 1");
        assertEquals(movie1.getNum(), movieNum1, "The movie numbers do not match");
        assertEquals(movie1.getList(), listName1, "The list names do not match");
        assertEquals(movie1.getName(), movieName1, "The movie names do not match");

        //Test Data 2
        int movieNum2 = 2;
        String listName2 = "Watched";
        String movieName2 = "The Dark Knight";
        int rating2 = 5;
        //Storing test data using function
        data.storeNewMovie(movieNum2, listName2, movieName2, rating2, movieGenre);
        //Retrieve the result
        Movie movie2 = movieList.get(1);
        int size2 = movieList.size();
        //Compare results to the expected values
        assertEquals(2, size2, "Size of list is not 2");
        assertEquals(movie2.getNum(), movieNum2, "The movie numbers do not match");
        assertEquals(movie2.getList(), listName2, "The list names do not match");
        assertEquals(movie2.getName(), movieName2, "The movie names do not match");
    }

    @Test
    void getListLookupTest() {
        Data data = new Data();
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        // initialize list vars
        int listNum = 1;
        String listType = "Favourites";
        String listName = "Favs";
        // Store info using function
        data.storeNewList(listNum, listType, listName);
        //Retrieve the results
        HashMap<Integer, List> listLookup = data.getListLookup();
        String actualListName = listLookup.get(listNum).getName();
        int size = listLookup.size();
        String actual = movie.toString();
        String expected = """
                Name:     Heat
                Number:   1
                List:     Favs
                Rating:   5 stars
                Genre:    Action
                """.trim();
        //Compare the actual results to the expected results
        assertEquals(1, size, "Size of HashMap is not 1");
        assertEquals(listName, actualListName, "The list names do not match");
        assertEquals(expected, actual, "The HashMap lists do not match");
    }

    @Test
    void getMovieLookup() {
        Data data = new Data();
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        // initialize movie vars
        int movieNum = 1;
        String listName = "Favourites";
        String movieName = "Blade Runner";
        Genre.movieGenre movieGenre = null;
        int rating = 5;
        // Store info using function
        data.storeNewMovie(movieNum, listName, movieName, rating, movieGenre);
        //Retrieve the results
        HashMap<Integer, Movie> movieLookup = data.getMovieLookup();
        String actualMovieName = movieLookup.get(movieNum).getName();
        int size = movieLookup.size();
        String actual = movie.toString();
        String expected = """
                Name:     Heat
                Number:   1
                List:     Favs
                Rating:   5 stars
                Genre:    Action
                """.trim();
        //Compare the actual results to the expected results
        assertEquals(1, size, "Size of HashMap is not 1");
        assertEquals(movieName, actualMovieName, "The movie names do not match");
        assertEquals(expected, actual, "The HashMap movie entries do not match");
    }

    @Test
    void storeRatingTest() {
        Data data = new Data();

        //Test Data
        int expectedRating = 4;
        String movieName = "Blade Runner";
        data.storeNewMovie(1, "Favs", movieName, expectedRating, Genre.movieGenre.Science_Fiction);
        Movie expectedMovie = data.getMovie(1);
        //Function stores Test Data
        data.storeRating(expectedMovie, expectedRating);
        // Retrieve the result
        int actualRating = data.getRating().get(0).getRating();
        Movie actualMovie = data.getRating().get(0).getMovie();
        int size = data.getRating().size();
        // Compare results to expected values
        assertEquals(1, size, "Size of list is not 1");
        assertEquals(expectedRating, actualRating, "The movie ratings do not match");
        assertEquals(movieName, actualMovie.getName(), "The movie names do not match");
        // Retrieve the result and Compare Lookup values to expected results
        boolean exists = data.getRatingLookup().containsKey(expectedMovie);
        int sizeL = data.getRatingLookup().size();
        int movieRatingL = data.getRatingLookup().get(expectedMovie);
        assertEquals(1, sizeL, "Size of Hashmap is not 1");
        assertTrue(exists, "The movie name has not been stored");
        assertEquals(expectedRating, movieRatingL, "The ratings of the movies do not match");
    }

    @Test
    void getRatingTest() {
        Data data = new Data();

        //Test Data
        int expectedRating = 4;
        String movieName = "Blade Runner";

        data.storeNewMovie(expectedRating, "Favs", movieName, 5, Genre.movieGenre.Science_Fiction);
        Movie expectedMovie = data.getMovie(expectedRating);
        // Store data using function
        data.storeRating(expectedMovie, expectedRating);
        //Retrieve the results
        ArrayList<Rating> ratingList = data.getRating();
        Rating rating = ratingList.get(0);
        int size = ratingList.size();
        //Compare the actual results to the expected results
        assertEquals(1, size, "Size of list is not 1");
        assertEquals(rating.getRating(), expectedRating, "The movie ratings do not match");
        assertEquals(rating.getMovie(), expectedMovie, "The movie names do not match");
    }

    @Test
    void getRatingLookup() {
        Data data = new Data();

        //Test Data 1
        int rating = 1;
        data.storeNewMovie(rating, "Favs", "Dune", 5, Genre.movieGenre.Science_Fiction);
        Movie movie = data.getMovie(rating);

        HashMap<Movie, Integer> expectedRatingLookup = new HashMap<>();
        expectedRatingLookup.put(movie, rating);
        // Store data using function
        data.storeRating(movie, rating);
        //Retrieve the results
        HashMap<Movie, Integer> ratingLookup = data.getRatingLookup();
        int actualRating = ratingLookup.get(movie);
        int size = ratingLookup.size();
        //Compare the actual results to the expected results
        assertEquals(1, size, "Size of HashMap is not 1");
        assertEquals(rating, actualRating, "The movie ratings do not match");
        assertEquals(expectedRatingLookup, ratingLookup, "The HashMap entries do not match");
    }

    @Test
        //Test for 6 movies
    void storeTop5Test1() {
        Data data = new Data();
        HashMap<Movie, Integer> ratings = new HashMap<>();
        // Create 6 movies
        data.storeNewMovie(1,"Fav", "Movie 1", 0, Genre.movieGenre.None);
        data.storeNewMovie(2,"Fav", "Movie 2", 0,Genre.movieGenre.None);
        data.storeNewMovie(3,"Fav", "Movie 3", 0,Genre.movieGenre.None);
        data.storeNewMovie(4,"Fav", "Movie 4", 0, Genre.movieGenre.None);
        data.storeNewMovie(5,"Fav", "Movie 5", 0,Genre.movieGenre.None);
        data.storeNewMovie(6,"Fav", "Movie 6", 0,Genre.movieGenre.None);
        // add them to the hashmap
        ratings.put(data.getMovie(1), 5);
        ratings.put(data.getMovie(2), 3);
        ratings.put(data.getMovie(3), 4);
        ratings.put(data.getMovie(4), 4);
        ratings.put(data.getMovie(5), 2);
        ratings.put(data.getMovie(6), 1);
        // sort them based on ratings
        data.storeTop5(ratings);

        ArrayList<Movie> actual = data.getTop5();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(1));
        expected.add(data.getMovie(4));
        expected.add(data.getMovie(3));
        expected.add(data.getMovie(2));
        expected.add(data.getMovie(5));

        assertEquals(expected, actual);
    }

    @Test
        // Test for 3 movies
    void storeTop5Test2() {
        Data data = new Data();
        HashMap<Movie, Integer> ratings = new HashMap<>();
        data.storeNewMovie(1,"Fav", "Movie 1", 0, Genre.movieGenre.None);
        data.storeNewMovie(2,"Fav", "Movie 2", 0,Genre.movieGenre.None);
        data.storeNewMovie(3,"Fav", "Movie 3", 0,Genre.movieGenre.None);

        ratings.put(data.getMovie(1), 4);
        ratings.put(data.getMovie(2), 1);
        ratings.put(data.getMovie(3), 3);
        data.storeTop5(ratings);

        ArrayList<Movie> actual = data.getTop5();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(1));
        expected.add(data.getMovie(3));
        expected.add(data.getMovie(2));

        assertEquals(expected, actual);
    }

    @Test
        // Test for 6 movies
    void storeTop5FavTest() {
        Data data = new Data();
        HashMap<Movie, Integer> ratings = new HashMap<>();
        data.storeNewList(1,"Favourites", "Fav");
        data.storeNewMovie(1,"Fav", "Movie 1", 0, Genre.movieGenre.None);
        data.storeNewMovie(2,"Fav", "Movie 2", 0, Genre.movieGenre.None);
        data.storeNewMovie(3,"Fav", "Movie 3", 0, Genre.movieGenre.None);
        data.storeNewMovie(4,"Fav", "Movie 4", 0, Genre.movieGenre.None);
        data.storeNewMovie(5,"Fav", "Movie 5", 0, Genre.movieGenre.None);
        data.storeNewMovie(6,"Fav", "Movie 6", 0, Genre.movieGenre.None);

        ratings.put(data.getMovie(1), 5);
        ratings.put(data.getMovie(2), 3);
        ratings.put(data.getMovie(3), 4);
        ratings.put(data.getMovie(4), 4);
        ratings.put(data.getMovie(5), 2);
        ratings.put(data.getMovie(6), 1);

        data.storeTop5Fav(ratings);

        ArrayList<Movie> actual = data.getTop5Fav();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(1));
        expected.add(data.getMovie(4));
        expected.add(data.getMovie(3));
        expected.add(data.getMovie(2));
        expected.add(data.getMovie(5));

        assertEquals(expected, actual);
    }

    @Test
        // Test for 6 movies with 4 in Want-to-watch list
    void storeTop5WTWTest() {
        Data data = new Data();

        HashMap<Movie, Integer> ratings = new HashMap<>();
        data.storeNewMovie(1,"WTW", "Movie 1", 0, Genre.movieGenre.None);
        data.storeNewMovie(2,"WTW", "Movie 2", 0, Genre.movieGenre.None);
        data.storeNewMovie(3,"WTW", "Movie 3", 0, Genre.movieGenre.None);
        data.storeNewMovie(4,"WTW", "Movie 4", 0, Genre.movieGenre.None);
        data.storeNewMovie(5,"Fav", "Movie 5", 0,Genre.movieGenre.None);
        data.storeNewMovie(6,"Fav", "Movie 6", 0,Genre.movieGenre.None);
        ratings.put(data.getMovie(1), 5);
        ratings.put(data.getMovie(2), 3);
        ratings.put(data.getMovie(3), 4);
        ratings.put(data.getMovie(4) ,4);
        ratings.put(data.getMovie(5) ,2);
        ratings.put(data.getMovie(6) ,1);
        data.storeNewList(1,"Want-to-watch", "WTW");
        data.storeTop5WTW(ratings);

        ArrayList<Movie> actual = data.getTop5WTW();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(1));
        expected.add(data.getMovie(4));
        expected.add(data.getMovie(3));
        expected.add(data.getMovie(2));

        assertEquals(expected, actual);
    }

    @Test
        // Test for 4 movies, all 4 being in required list
    void storeTop5WatchedTest() {
        Data data = new Data();

        HashMap<Movie, Integer> ratings = new HashMap<>();
        data.storeNewMovie(1,"asd", "Movie 1", 0,Genre.movieGenre.None);
        data.storeNewMovie(2,"asd", "Movie 2", 0,Genre.movieGenre.None);
        data.storeNewMovie(3,"asd", "Movie 3", 0,Genre.movieGenre.None);
        data.storeNewMovie(4,"asd", "Movie 4", 0,Genre.movieGenre.None);
        ratings.put(data.getMovie(1),5);
        ratings.put(data.getMovie(2),3);
        ratings.put(data.getMovie(3),4);
        ratings.put(data.getMovie(4),4);
        data.storeNewList(1,"Watched", "asd");
        data.storeTop5Watched(ratings);

        ArrayList<Movie> actual = data.getTop5Watched();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(1));
        expected.add(data.getMovie(4));
        expected.add(data.getMovie(3));
        expected.add(data.getMovie(2));


        assertEquals(expected, actual);
    }

    @Test
        // Test for 6 movies with all being in Want-to-watch list
    void getTop5WTWTest() {
        Data data = new Data();

        HashMap<Movie, Integer> ratings = new HashMap<>();
        data.storeNewMovie(1,"WTW", "Movie 1", 0,Genre.movieGenre.None);
        data.storeNewMovie(2,"WTW", "Movie 2", 0,Genre.movieGenre.None);
        data.storeNewMovie(3,"WTW", "Movie 3", 0,Genre.movieGenre.None);
        data.storeNewMovie(4,"WTW", "Movie 4", 0,Genre.movieGenre.None);
        data.storeNewMovie(5,"WTW", "Movie 5", 0,Genre.movieGenre.None);
        data.storeNewMovie(6,"WTW", "Movie 6", 0,Genre.movieGenre.None);
        ratings.put(data.getMovie(1), 5);
        ratings.put(data.getMovie(2), 3);
        ratings.put(data.getMovie(3), 4);
        ratings.put(data.getMovie(4), 4);
        ratings.put(data.getMovie(5), 2);
        ratings.put(data.getMovie(6), 1);
        data.storeNewList(1,"Want-to-watch", "WTW");
        data.storeTop5WTW(ratings);

        ArrayList<Movie> actual = data.getTop5WTW();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(1));
        expected.add(data.getMovie(4));
        expected.add(data.getMovie(3));
        expected.add(data.getMovie(2));
        expected.add(data.getMovie(5));

        assertEquals(expected, actual);
    }

    @Test
        // Test for 4 movies, all 2 being in required list
    void getTop5WatchedTest() {
        Data data = new Data();

        HashMap<Movie, Integer> ratings = new HashMap<>();
        data.storeNewMovie(1,"asd", "Movie 1", 0,Genre.movieGenre.None);
        data.storeNewMovie(2,"asd", "Movie 2", 0,Genre.movieGenre.None);
        data.storeNewMovie(3,"Fav", "Movie 3", 0,Genre.movieGenre.None);
        data.storeNewMovie(4,"Fav", "Movie 4", 0,Genre.movieGenre.None);

        ratings.put(data.getMovie(1),5);
        ratings.put(data.getMovie(2),3);
        ratings.put(data.getMovie(3),4);
        ratings.put(data.getMovie(4),4);

        data.storeNewList(1,"Watched", "asd");
        data.storeTop5Watched(ratings);

        ArrayList<Movie> actual = data.getTop5Watched();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(1));
        expected.add(data.getMovie(2));

        assertEquals(expected, actual);
    }

    @Test
        // Test for 6 movies with 1 in Favourites list
    void getTop5FavTest() {
        Data data = new Data();

        HashMap<Movie, Integer> ratings = new HashMap<>();
        data.storeNewMovie(1,"Fav", "Movie 1", 0,Genre.movieGenre.None);
        data.storeNewMovie(2,"asd", "Movie 2", 0,Genre.movieGenre.None);
        data.storeNewMovie(3,"asd", "Movie 3", 0,Genre.movieGenre.None);
        data.storeNewMovie(4,"asd", "Movie 4", 0,Genre.movieGenre.None);
        data.storeNewMovie(5,"asd", "Movie 5", 0,Genre.movieGenre.None);
        data.storeNewMovie(6,"asd", "Movie 6", 0,Genre.movieGenre.None);
        ratings.put(data.getMovie(1),5);
        ratings.put(data.getMovie(2),3);
        ratings.put(data.getMovie(3),4);
        ratings.put(data.getMovie(4),4);
        ratings.put(data.getMovie(5),5);
        ratings.put(data.getMovie(6),3);

        data.storeNewList(1,"Favourites", "Fav");
        data.storeTop5Fav(ratings);

        ArrayList<Movie> actual = data.getTop5Fav();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(1));

        assertEquals(expected, actual);
    }

    @Test
        //Test for 0 rated movies
    void getTop5Test1() {
        Data data = new Data();

        HashMap<Movie, Integer> ratings = new HashMap<>();

        data.storeTop5(ratings);
        ArrayList<Movie> actual = data.getTop5();
        ArrayList<Movie> expected = new ArrayList<>();
        ;
        assertEquals(expected, actual);
    }
    @Test
    void getGenreLookupTest() {
        HashMap<Movie, Genre.movieGenre> expected = new HashMap<>();
        Data data = new Data();
        Movie movie = new Movie(1,"Favs", "Qwerty", 5, Genre.movieGenre.None);
        data.addGenre(movie, Genre.movieGenre.Drama);
        movie.setGenre(Genre.movieGenre.Drama);
        HashMap<Movie, Genre.movieGenre> actual = data.getGenreLookup();
        expected.put(movie, Genre.movieGenre.Drama);
        assertEquals(expected, actual);
    }
    @Test
        // Test for 2 movies
    void getTop5Test2() {
        Data data = new Data();

        HashMap<Movie, Integer> ratings = new HashMap<>();
        data.storeNewMovie(1,"Fav", "Movie 1", 0,Genre.movieGenre.None);
        data.storeNewMovie(2,"asd", "Movie 2", 0,Genre.movieGenre.None);
        ratings.put(data.getMovie(1),1);
        ratings.put(data.getMovie(2),4);
        data.storeTop5(ratings);

        ArrayList<Movie> actual = data.getTop5();
        ArrayList<Movie> expected = new ArrayList<>();

        expected.add(data.getMovie(2));
        expected.add(data.getMovie(1));

        assertEquals(expected, actual);
    }
    @Test
    void getMovieTest() {
        Data data = new Data();
        data.storeNewMovie(1,"Favs", "Rush Hour", 5, Genre.movieGenre.Action);
        data.storeNewMovie(2,"Favs", "Up", 3, Genre.movieGenre.Comedy);
        Movie actual = data.getMovie(2);
        String expected = """
                Name:     Up
                Number:   2
                List:     Favs
                Rating:   3 stars
                Genre:    Comedy
                """.trim();
        assertEquals(expected, actual.toString());
    }
    @Test
    void addGenreTest() {
        HashMap<Movie, Genre.movieGenre> expected = new HashMap<>();
        Data data = new Data();
        Movie movie = new Movie(1,"Favs", "Qwerty", 5, Genre.movieGenre.None);
        data.addGenre(movie, Genre.movieGenre.Comedy);
        movie.setGenre(Genre.movieGenre.Comedy);
        HashMap<Movie, Genre.movieGenre> actual = data.getGenreLookup();
        expected.put(movie, Genre.movieGenre.Comedy);
        assertEquals(expected, actual);
    }

    @Test
    void storeTop5GenreTest() {
        Data data = new Data();
        ArrayList<Movie> expected = new ArrayList<>();
        HashMap<Movie, Integer> ratings = new HashMap<>();
        // Storing data to test functions
        data.storeNewMovie(1,"Favs", "Qwerty", 5, Genre.movieGenre.Comedy);
        data.storeNewMovie(2,"Favs", "Qwert", 4, Genre.movieGenre.Comedy);
        data.storeNewMovie(3,"Favs", "Qwer", 3, Genre.movieGenre.Comedy);
        data.storeNewMovie(4,"WTW", "Qwe", 2, Genre.movieGenre.Comedy);
        data.storeNewMovie(5,"WTW", "Qw", 4, Genre.movieGenre.Comedy);
        data.storeNewMovie(6,"Favs", "Q", 5, Genre.movieGenre.Comedy);

        // storing ratings in HashMap
        ratings.put(data.getMovie(1), 5);
        ratings.put(data.getMovie(2), 4);
        ratings.put(data.getMovie(3), 3);
        ratings.put(data.getMovie(4), 2);
        ratings.put(data.getMovie(5), 4);
        ratings.put(data.getMovie(6), 5);

        Movie movie1 = data.getMovie(1);
        Movie movie2 = data.getMovie(2);
        Movie movie3 = data.getMovie(3);
        Movie movie4 = data.getMovie(4);
        Movie movie5 = data.getMovie(5);
        Movie movie6 = data.getMovie(6);
        // adding genres to movies
        data.addGenre(movie1, Genre.movieGenre.Comedy);
        data.addGenre(movie2, Genre.movieGenre.Comedy);
        data.addGenre(movie3, Genre.movieGenre.Comedy);
        data.addGenre(movie4, Genre.movieGenre.Comedy);
        data.addGenre(movie5, Genre.movieGenre.Comedy);
        data.addGenre(movie6, Genre.movieGenre.Comedy);
        //adding to expected ArrayList
        expected.add(data.getMovie(6));
        expected.add(data.getMovie(1));
        expected.add(data.getMovie(5));
        expected.add(data.getMovie(2));
        expected.add(data.getMovie(3));
        // calling function for testing
        data.storeTop5Genre(ratings, Genre.movieGenre.Comedy);
        ArrayList<Movie> actual = data.getTop5Genre();
        // comparing results to expected outcome
        assertEquals(expected, actual);
    }
    @Test
    void getTop5GenreTest() {
        Data data = new Data();
        ArrayList<Movie> expected = new ArrayList<>();
        HashMap<Movie, Integer> ratings = new HashMap<>();
        // Storing data to test functions
        data.storeNewMovie(1,"Favs", "Qwerty", 5, Genre.movieGenre.Action);
        data.storeNewMovie(2,"Favs", "Qwert", 4, Genre.movieGenre.Comedy);
        data.storeNewMovie(3,"Favs", "Qwer", 3, Genre.movieGenre.Action);
        data.storeNewMovie(4,"WTW", "Qwe", 2, Genre.movieGenre.Comedy);
        data.storeNewMovie(5,"WTW", "Qw", 4, Genre.movieGenre.Comedy);
        data.storeNewMovie(6,"Favs", "Q", 5, Genre.movieGenre.Comedy);

        // storing ratings in HashMap
        ratings.put(data.getMovie(1), 5);
        ratings.put(data.getMovie(2), 4);
        ratings.put(data.getMovie(3), 3);
        ratings.put(data.getMovie(4), 2);
        ratings.put(data.getMovie(5), 4);
        ratings.put(data.getMovie(6), 5);

        Movie movie1 = data.getMovie(1);
        Movie movie2 = data.getMovie(2);
        Movie movie3 = data.getMovie(3);
        Movie movie4 = data.getMovie(4);
        Movie movie5 = data.getMovie(5);
        Movie movie6 = data.getMovie(6);

        // adding genres to movies
        data.addGenre(movie1, Genre.movieGenre.Action);
        data.addGenre(movie2, Genre.movieGenre.Comedy);
        data.addGenre(movie3, Genre.movieGenre.Action);
        data.addGenre(movie4, Genre.movieGenre.Comedy);
        data.addGenre(movie5, Genre.movieGenre.Comedy);
        data.addGenre(movie6, Genre.movieGenre.Comedy);

        //adding to expected ArrayList
        expected.add(data.getMovie(6));
        expected.add(data.getMovie(5));
        expected.add(data.getMovie(2));
        expected.add(data.getMovie(4));
        // calling function for testing
        data.storeTop5Genre(ratings, Genre.movieGenre.Comedy);
        ArrayList<Movie> actual = data.getTop5Genre();
        // comparing results to expected outcome
        assertEquals(expected, actual);
    }

    @Test
    void storeTop5ListTest() {
        // Test for 3 movies in the same list
        Data data = new Data();
        ArrayList<Movie> expected = new ArrayList<>();
        HashMap<Movie, Integer> ratings = new HashMap<>();
        String list = "Favs";
        // Storing data to test functions
        data.storeNewMovie(1, list, "Movie 1", 5, Genre.movieGenre.None);
        data.storeNewMovie(2,list, "Movie 2", 4, Genre.movieGenre.None);
        data.storeNewMovie(3,list, "Movie 3", 3, Genre.movieGenre.None);
        data.storeNewMovie(4,"WTW", "Movie 4", 2, Genre.movieGenre.None);
        data.storeNewMovie(5,"WTW", "Movie 5", 4, Genre.movieGenre.None);

        // storing ratings in HashMap
        ratings.put(data.getMovie(1), 3);
        ratings.put(data.getMovie(2), 5);
        ratings.put(data.getMovie(3), 1);
        ratings.put(data.getMovie(4), 2);
        ratings.put(data.getMovie(5), 4);

        //adding to expected ArrayList
        expected.add(data.getMovie(2));
        expected.add(data.getMovie(1));
        expected.add(data.getMovie(3));

        // calling function for testing
        data.storeTop5List(ratings, list);
        ArrayList<Movie> actual = data.getTop5List();
        // comparing results to expected outcome
        assertEquals(expected, actual);
    }

    @Test
    void storeTopGenreTest() {
        // Test for a genre with 2 movies in it
        Data data = new Data();
        ArrayList<Movie> movies = new ArrayList<>();
        HashMap<Genre.movieGenre, Integer> expected = new HashMap<>();
        String list = "Favs";
        // Storing data to test functions
        data.storeNewMovie(1, list, "Movie 1", 5, Genre.movieGenre.Drama);
        data.storeNewMovie(2,list, "Movie 2", 4, Genre.movieGenre.Drama);
        data.storeNewMovie(3,list, "Movie 3", 3, Genre.movieGenre.Science_Fiction);
        data.storeNewMovie(4,"WTW", "Movie 4", 2, Genre.movieGenre.Fantasy);
        data.storeNewMovie(5,"WTW", "Movie 5", 4, Genre.movieGenre.Comedy);
        // Add all the movies to the movies ArrayList
        movies.add(data.getMovie(1));
        movies.add(data.getMovie(2));
        movies.add(data.getMovie(3));
        movies.add(data.getMovie(4));
        movies.add(data.getMovie(5));
        //adding to expected ArrayList
        expected.put(Genre.movieGenre.Drama, 2);

        // calling function for testing
        data.storeTopGenres(movies);
        HashMap<Genre.movieGenre, Integer> actual = data.getTopGenre();
        // comparing results to expected outcome
        assertEquals(expected, actual);
    }

    @Test
    void deleteListTest() {
        // Test for list with three movies in it
        Data data = new Data();
        // store a new list
        data.storeNewList(1, "Favourites", "Favs");
        List list = data.getList(1);
        // create a new movie arraylist and store three movies in it
        ArrayList<Movie> movies = new ArrayList<>();
        data.storeNewMovie(1, list.getName(), "Movie 1", 5, Genre.movieGenre.Drama);
        data.storeNewMovie(2, list.getName(), "Movie 2", 4, Genre.movieGenre.Drama);
        data.storeNewMovie(3, list.getName(), "Movie 3", 3, Genre.movieGenre.Science_Fiction);

        movies.add(data.getMovie(1));
        movies.add(data.getMovie(2));
        movies.add(data.getMovie(3));

        data.deleteList(list.getName());

        int expectedListSize = data.getLists().size();
        int actualListSize = 0;
        assertEquals(expectedListSize, actualListSize); // check if list is removed from the list of lists

        int expectedListLookupSize = data.getListLookup().size();
        int actualListLookupSize = 0;
        assertEquals(expectedListLookupSize, actualListLookupSize); // check if the list is removed from the listLookup

        int expectedListDupSize = data.getListDuplicate().size();
        int actualListDupSize = 0;
        assertEquals(expectedListDupSize, actualListDupSize); // check if the list is removed from the duplicate list Lookup

        int expectedMovieSize = data.getMovies().size();
        int actualMovieSize = 0;
        assertEquals(expectedMovieSize, actualMovieSize); // check if the movies were deleted from the movie list

        int expectedMovieLookupSize = data.getMovieLookup().size();
        int actualMovieLookupSize = 0;
        assertEquals(expectedMovieLookupSize, actualMovieLookupSize); // check if the list is removed from the listLookup

        int expectedMovieDupSize = data.getMovieDuplicate().size();
        int actualMovieDupSize = 0;
        assertEquals(expectedMovieDupSize, actualMovieDupSize); // check if the list is removed from the duplicate movie Lookup

        int expectedRatingSize = data.getRatingLookup().size();
        int actualRatingSize = 0;
        assertEquals(expectedRatingSize, actualRatingSize); // check if the rating is removed from the rating Lookup

        int expectedGenreSize = data.getGenreLookup().size();
        int actualGenreSize = 0;
        assertEquals(expectedGenreSize, actualGenreSize); // check if the genre is removed from the genre Lookup
    }

    @Test
    void removeMovieTest() {
        // Test for list with three movies in it
        Data data = new Data();
        // store a new list
        data.storeNewList(1, "Favourites", "Favs");
        List list = data.getList(1);
        // create a new movie arraylist and store three movies in it
        ArrayList<Movie> movies = new ArrayList<>();
        data.storeNewMovie(1, list.getName(), "Movie 1", 5, Genre.movieGenre.Drama);
        data.storeNewMovie(2, list.getName(), "Movie 2", 4, Genre.movieGenre.Drama);
        data.storeNewMovie(3, list.getName(), "Movie 3", 3, Genre.movieGenre.Science_Fiction);

        movies.add(data.getMovie(1));
        movies.add(data.getMovie(2));
        movies.add(data.getMovie(3));

        Movie movie1 = data.getMovie(1);
        data.removeMovie(movie1.getName());

        assertFalse(data.getMovies().contains(movie1)); // check if the movie was deleted from the movie list

        assertFalse(data.getMovieLookup().containsValue(movie1)); // check if the Movie was removed from the movie lookup

        assertFalse(data.getMovieDuplicate().contains(movie1)); // check if the movie was removed from the duplicate movie Lookup

        int expectedRatingSize = data.getRatingLookup().size();
        int actualRatingSize = 2;
        assertEquals(expectedRatingSize, actualRatingSize); // check if the rating is removed from the rating Lookup

        int expectedGenreSize = data.getGenreLookup().size();
        int actualGenreSize = 2;
        assertEquals(expectedGenreSize, actualGenreSize); // check if the genre is removed from the genre Lookup
    }
}