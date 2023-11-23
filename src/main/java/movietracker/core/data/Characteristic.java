package movietracker.core.data;

public abstract class Characteristic {

    protected String movie;

    public Characteristic(String movie) {
        this.movie = movie;
    }
    public String getMovie() {
        return movie;
    }

}
