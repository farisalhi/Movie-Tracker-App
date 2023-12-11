package movietracker.core.data;
/*
 * Rating class extends Characteristic class
 * The class for Rating object declaration
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 8, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Rating class for Rating object creation
 */
public class Rating extends Characteristic {
    private final int rating;

    /**
     * Constructor for Rating object
     * @param movie String name of movie inherited from Characteristic class
     * @param rating integer rating for movie
     */
    public Rating(Movie movie, int rating) {
        super(movie);
        this.rating = rating;
    }

    /**
     * Getter for integer rating
     * @return integer rating
     */
    public int getRating() {
        return this.rating;
    }
}
