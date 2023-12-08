package movietracker.core.data;

/**
 * Characteristic Object abstract class
 * The abstract class for movie characteristics
 *
 * @author Faris Salhi (30117469), Ariel Motsi ()
 * Dec. 8, 2023
 * Tutorial T06
 * @version 1.0
 */

public abstract class Characteristic {

    // every characteristic must have a movie associated with it
    protected String movie;

    /**
     * Constructor for String movie name
     * @param movie String movie name
     */
    public Characteristic(String movie) {
        this.movie = movie;
    }
    public String getMovie() {
        return movie;
    }

}
