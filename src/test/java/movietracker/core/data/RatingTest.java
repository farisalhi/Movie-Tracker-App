package movietracker.core.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Movie Tracker Application rating Test class
 * The test class containing tests for all non-I/O and non-GUI functions in the Rating class
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 10, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * RatingTest class containing all junit tests for Rating class
 */
class RatingTest {

    @Test
    void getRating() {
        //create a new movie and get the rating
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        int actualRating = movie.getRating();
        int expectedRating = 5;
        assertEquals(expectedRating, actualRating);
    }

    @Test
    void getMovie() {
        //create a new movie and use it to create a new rating
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        Rating rating = new Rating(movie, movie.getRating());
        Movie actualMovie = rating.getMovie();
        assertEquals(movie, actualMovie);
    }
}