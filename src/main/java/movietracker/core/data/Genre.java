package movietracker.core.data;

public class Genre extends Characteristic {

    public enum movieGenre {
        Action, Horror, Adventure, Fantasy, Comedy, Science_Fiction, Drama, Romance, None;
    }
    private final movieGenre genre;

    public Genre(String movie, movieGenre genre) {
        super(movie);
        this.genre = genre;
    }

    public Genre.movieGenre getGenre() {
        return genre;
    }

    public String getMovie() {
        return movie;
    }
}
