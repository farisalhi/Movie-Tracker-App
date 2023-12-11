package movietracker.core.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Movie Tracker Application Genre Test class
 * The test class containing tests for all non-I/O and non-GUI functions in the Genre class
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 10, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * GenreTest class containing all junit tests for Genre class
 */
class GenreTest {

    @Test
    void getGenre() {
        // create a new movie
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        // create a new genre from an enum genre
        Genre genre = new Genre(movie, Genre.movieGenre.Adventure);
        Genre.movieGenre actualGenre = genre.getGenre();
        Genre.movieGenre expectedGenre = Genre.movieGenre.Adventure;
        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    void getMovie() {
        //create a new movie and use it to create a new genre
        Movie movie = new Movie(1, "Favs", "Heat", 5, Genre.movieGenre.Action);
        Genre genre = new Genre(movie, Genre.movieGenre.Adventure);
        Movie actualMovie = genre.getMovie();
        assertEquals(movie, actualMovie);
    }
}