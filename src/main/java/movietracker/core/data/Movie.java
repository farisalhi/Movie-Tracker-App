package movietracker.core.data;

import java.util.Objects;

public class Movie {
    protected final int movieNum;
    private final String listName;
    private final String movieName;
    private int movieRating;
    private Genre.movieGenre movieGenre;

    public Movie(int movieNum, String listName, String movieName, int movieRating, Genre.movieGenre movieGenre) {
        this.movieNum = movieNum;
        this.listName = listName;
        this.movieName = movieName;
        this.movieGenre = movieGenre;
        this.movieRating = movieRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(movieName, movie.movieName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieName);
    }

    public String getName() {
        return this.movieName;
    }
    public String getList() {
        return this.listName;
    }
    public int getNum() {
        return this.movieNum;
    }

    public int getRating() {
        return this.movieRating;
    }

    public void setRating(int rating) {
        this.movieRating = rating;
    }
    public Genre.movieGenre getGenre() {
        return this.movieGenre;
    }
    public void setGenre(Genre.movieGenre genre) {
        this.movieGenre = genre;
    }

    @Override
    public String toString(){
        return String.format("Name:     %s\nNumber:   %d\nList:     %s\nRating:   %d stars\nGenre:    %s",
                movieName, movieNum, listName, movieRating, movieGenre);
    }
}
