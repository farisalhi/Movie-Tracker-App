package movietracker.core.data;

/*
 * Characteristic Object abstract class
 * The abstract class for movie characteristics
 *
 * @author Faris Salhi (30117469), Ariel Motsi ()
 * Dec. 8, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Abstract class for defining movie characteristics
 */
public abstract class Characteristic {

    // every characteristic should have a movie associated with it
    protected Movie movie;

    /**
     * Constructor for String movie name
     * @param movie String movie name
     */
    public Characteristic(Movie movie) {
        this.movie = movie;
    }

    /**
     * Getter for Movie. All characteristics should be able to get the movie they're associated with
     * @return Movie. The movie associated with the characteristic.
     */
    public Movie getMovie() {
        return movie;
    }
}
