package movietracker.core.data;

public class Rating extends Characteristic {
    private final int rating;
    public Rating(String movie, int rating) {
        super(movie);
        this.rating = rating;
    }
    public int getRating() {
        return this.rating;
    }
}
