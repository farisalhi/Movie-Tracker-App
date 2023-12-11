package movietracker.core.data;

import movietracker.core.data.Genre;
import movietracker.core.data.Movie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Movie Tracker Application Movie Test class
 * The test class containing tests for all non-I/O and non-GUI functions in the Movie class
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * MovieTest class containing all junit tests for Movie class
 */

class MovieTest {

    @Test
    void getName() {
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        String name = movie.getName();
        String expected = "Heat";
        assertEquals(expected, name);
    }

    @Test
    void getList() {
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        String list = movie.getList();
        String expected = "Favs";
        assertEquals(expected, list);
    }

    @Test
    void getNum() {
        Movie movie = new Movie(1, "Favs", "Heat", 5,  Genre.movieGenre.Action);
        int movieNum = movie.getNum();
        int expected = 1;
        assertEquals(expected, movieNum);
    }

    @Test
    void getGenre() {
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        Genre.movieGenre genre = movie.getGenre();
        Genre.movieGenre expected = Genre.movieGenre.Action;
        assertEquals(expected, genre);
    }

    @Test
    void setGenre() {
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.None);
        movie.setGenre(Genre.movieGenre.Science_Fiction);
        Genre.movieGenre genre1 = movie.getGenre();
        Genre.movieGenre expected1 = Genre.movieGenre.Science_Fiction;
        assertEquals(expected1, genre1);

        movie.setGenre(Genre.movieGenre.Action);
        Genre.movieGenre genre2 = movie.getGenre();
        Genre.movieGenre expected2 = Genre.movieGenre.Action;
        assertEquals(expected2, genre2);
    }

    @Test
    void setRating() {
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.None);
        movie.setRating(3);
        int rating = movie.getRating();
        int expected = 3;
        assertEquals(expected, rating);
    }

    @Test
    void getRating() {
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.None);
        movie.setRating(3);
        int rating1 = movie.getRating();
        int expected1 = 3;
        assertEquals(expected1, rating1);

        movie.setRating(4);
        int rating2 = movie.getRating();
        int expected2 = 4;
        assertEquals(expected2, rating2);
    }

    @Test
    void testToString() {
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        String actual = movie.toString();
        String expected = """
                Name:     Heat
                Number:   1
                List:     Favs
                Rating:   5 stars
                Genre:    Action
                """.trim();
        assertEquals(expected, actual);
    }
}