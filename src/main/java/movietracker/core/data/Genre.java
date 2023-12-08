package movietracker.core.data;

/**
 * Genre Object class extending Characteristic class
 * The class for Genre enum declaration
 *
 * @author Faris Salhi (30117469), Ariel Motsi ()
 * Dec. 8, 2023
 * Tutorial T06
 * @version 1.0
 */
public class Genre extends Characteristic {

    /**
     * movieGenre enum containing a few genres to choose from
     */
    public enum movieGenre {
        Action, Horror, Adventure, Fantasy, Comedy, Science_Fiction, Drama, Romance, None;
    }
    private final movieGenre genre;

    /**
     * Constructor for Genre object
     * @param movie String name of movie inherited from Characteristic class
     * @param genre movie genre from enum
     */
    public Genre(Movie movie, movieGenre genre) {
        super(movie);
        this.genre = genre;
    }

    /**
     * Getter for returning genre
     * @return Genre.movieGenre
     */
    public Genre.movieGenre getGenre() {
        return genre;
    }
}
