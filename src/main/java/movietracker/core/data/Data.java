package movietracker.core.data;

import java.util.*;

/**
 * Movie Tracker Application Data class
 * The class containing functions to handle data storage and movie/list sorting
 *
 * @author Faris Salhi (30117469), Ariel Motsi ()
 * Dec. 5, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * Class for handling data storage and sorting
 */
public class Data {

    // Private static constant ArrayLists and HashMaps and HashLists for data storage.
    private final ArrayList<List> list;
    private final HashMap<Integer, List> listLookup;
    private final HashSet<List> listDuplicateLookup;

    private final ArrayList<Movie> movieList;
    private final HashMap<Integer, Movie> movieLookup;
    private final HashSet<Movie> movieDuplicateLookup;

    private final ArrayList<Rating> ratingList;
    private final HashMap<String, Integer> ratingLookup;

    private final HashMap<Movie, Genre.movieGenre> genreLookup;


    // HashSet
    private final ArrayList<String> top5;
    private final ArrayList<String> top5List;
    private final ArrayList<String> top5Fav;
    private final ArrayList<String> top5WTW;
    private final ArrayList<String> top5Watched;
    private final ArrayList<String> top5Genre;
    private final ArrayList<String> topGenres;

    public Data() {
        this.list = new ArrayList<>();
        this.listLookup = new HashMap<>();
        this.listDuplicateLookup = new HashSet<>();

        this.movieList = new ArrayList<>();
        this.movieLookup = new HashMap<>();
        this.movieDuplicateLookup = new HashSet<>();

        this.ratingList = new ArrayList<>();
        this.ratingLookup = new HashMap<>();

        this.genreLookup = new HashMap<>();

        this.top5 = new ArrayList<>();
        this.top5List = new ArrayList<>();
        this.top5Fav = new ArrayList<>();
        this.top5WTW = new ArrayList<>();
        this.top5Watched = new ArrayList<>();
        this.top5Genre = new ArrayList<>();
        this.topGenres = new ArrayList<>();
    }

    /**
     * Creates a new object array containing all three characteristics of each new list. Adds each object array to another object array called 'list.'
     *
     * @param listNum  Integer value for list number.
     * @param listType String value for list type (Favourite, watched, want-to-watch).
     * @param listName String value for list name.
     */
    public boolean storeNewList(int listNum, String listType, String listName) {
        List newList = new List(listNum, listType, listName);
        for (List list : listDuplicateLookup) {
            if (Objects.equals(list.getName(), listName)) {
                return false;
            }
        }
        if (!listDuplicateLookup.contains(newList)) {
            // Add the new list to the 'list' object array
            list.add(newList);
            // Add the list number and name as the key-value pairs for the listLookup HashMap.
            listLookup.put(listNum, newList);
            listDuplicateLookup.add(newList);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Getter for storeNewList ArrayList
     *
     * @return list ArrayList storing the list number, list type and list name as an object
     */
    public ArrayList<List> getLists() {
        return list;
    }

    /**
     * Getter for the listLookup HashMap from storeNewList
     *
     * @return listLookup HashMap that stores the list number as the keys and the list name as the values
     */
    public HashMap<Integer, List> getListLookup() {
        return listLookup;
    }

    /**
     * Getter for a List using its list number
     *
     * @param listNum the number assigned to the list to differentiate between lists
     * @return the list (value) for the corresponding listNum key in listLookup
     */
    public List getList(int listNum) {
        return listLookup.get(listNum);
    }

    /**
     * storeNewMovie function that adds new movie information to an ArrayList and HashMap
     *
     * @param movieNum  the number assigned to the movie
     * @param listName  the name of the list the movie is added to
     * @param movieName the name of the movie
     */
    public boolean storeNewMovie(int movieNum, String listName, String movieName, int movieRating, Genre.movieGenre movieGenre) {
        Movie newMovie = new Movie(movieNum, listName, movieName, movieRating, movieGenre);
        // Add the new list to the 'movieList' object array
        if (!movieDuplicateLookup.contains(newMovie)) {
            movieList.add(newMovie);
            // Add the movie number and name as the key-value pairs for the movieLookup HashMap.
            movieLookup.put(movieNum, newMovie);
            movieDuplicateLookup.add(newMovie);
            ratingLookup.put(movieName, movieRating);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Function to check if a list can be removed.
     * @param name String name of the list to be removed.
     * @return Boolean if the remove was successful.
     */
    public boolean deleteList(String name) {
       for (int i = 0; i < movieList.size(); i++) { // loop through size of movie list
           Movie movie = movieList.get(i); // get the movie at each index
           if (Objects.equals(movie.getList(), name)) { // check if the movie name parameter is a real movie
               // remove it from the movie list and hashmaps for lookup, duplicate lookup, rating, and genre
               movieList.remove(movie);
               movieLookup.remove(movie.getNum(), movie);
               movieDuplicateLookup.remove(movie);
               ratingLookup.remove(movie.getName(), movie.getRating());
               genreLookup.remove(movie, movie.getGenre());
               i--;
           }
       }

        for (int i = 0; i < list.size(); i++) { // loop through the size of the list of lists
            List list_single = list.get(i); // get the list at each index
            if (Objects.equals(list_single.getName(), name)) {
                list.remove(list_single);
                listLookup.remove(list_single.getNum(), list_single);
                listDuplicateLookup.remove(list_single);
                return true;
            }
        }
        return false;
    }


    /**
     * Function to check if a movie is in the movie list to remove.
     * @param name String name of the movie to be removed
     * @return Boolean if the remove was successful.
     */
    public boolean removeMovie(String name){
        for(Movie movie : movieList){ // Loop through every movie in the movie list
            if (Objects.equals(movie.getName(), name)) { // Check if the movie is in the list
                // remove the movie from all the lists and hashmaps it's in
                movieList.remove(movie);
                movieLookup.remove(movie.getNum(), movie);
                movieDuplicateLookup.remove(movie);
                ratingLookup.remove(name, movie.getRating());
                genreLookup.remove(movie, movie.getGenre());
                return true;
            }
        }
        return false;
    }


    /**
     * Getter for storeNewMovie function
     * @return ArrayList that stores the Movies
     */
    public ArrayList<Movie> getMovies() {
        return movieList;
    }

    /**
     * Getter for the movieLookup HashMap from storeNewMovie function
     * @return movieLookup HashMap with movie number as the keys and movie name as the values
     */
    public HashMap<Integer, Movie> getMovieLookup() {
        return movieLookup;
    }

    /**
     * Getter for a Movie using its movie number
     * @param movieNum the number assigned to the movie to differentiate between movies
     * @return the movie (value) for the corresponding movieNum key in movieLookup
     */
    public Movie getMovie(int movieNum) {
        return movieLookup.get(movieNum);
    }

    /**
     * storeRating function that stores the rating along with the movie names as an ArrayList and HashMap
     * @param movie the name of the movie
     * @param rating the rating of the movie
     */
    public void storeRating(String movie, int rating) {
        Rating newRating = new Rating(movie, rating);
        // Add the new list to the 'ratingList' object array
        ratingList.add(newRating);
        // Add the name and rating as the key-value pairs for the ratingLookup HashMap.
        ratingLookup.put(movie, rating);
    }

    /**
     * Getter for the storeRating ArrayList.
     * @return ratingList ArrayList that stores the movie name and rating as objects
     */
    public ArrayList<Rating> getRating() {
        return ratingList;
    }

    /**
     * Getter for the ratings HashMap storing movie names and ratings from storeRating function
     * @return ratingLookup HashMap that stores the movie name as the keys and the rating as a value
     */
    public HashMap<String, Integer> getRatingLookup() {
        return ratingLookup;
    }

    /**
     * Storing a movie and its genre in a genreLookup Hashmap
     * @param movie the Movie we want to add to a genre
     * @param genre the Genre we add the movie to
     */
    public void addGenre(Movie movie, Genre.movieGenre genre) {
        genreLookup.put(movie, genre);
    }

    /**
     * Getter for genreLookup
     * @return HashMap storing Movies as keys and genres as values
     */
    public HashMap<Movie, Genre.movieGenre> getGenreLookup() {
        return genreLookup;
    }

    /**
     * Extracting the top 5 rated movies in the requested genre
     * @param movieRatings the HashMap of movies and ratings
     * @param genre the genre we get the top 5 for
     */
    public void storeTop5Genre(HashMap<String, Integer> movieRatings, Genre.movieGenre genre) {
        ArrayList<Integer> top5 = new ArrayList<>();
        for (int rating : movieRatings.values()) {
            top5.add(rating);
        }
        top5.sort(Comparator.reverseOrder());
        int count = 0;
        top5Genre.clear();
        for (int rating : top5) {
            for (String key : movieRatings.keySet()) {
                if (count < 5 && rating >= 0) {
                    for (Movie movie : genreLookup.keySet()) {
                        if (movie.getName().equals(key) && genre.equals(movie.getGenre()) && rating == movieRatings.get(key) && !top5Genre.contains(key) ){
                            top5Genre.add(key);
                            count++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Getter for ArrayList with the top 5 movies in the genre
     * @return ArrayList of the top 5 movies
     */
    public ArrayList<String> getTop5Genre() {
        return top5Genre;
    }

    /**
     * storeTop5 function that pulls out the top 5 rated movies across all lists
     * @param movieRatings HashMap storing the movie names as the keys and the ratings as the values
     */
    public void storeTop5(HashMap<String, Integer> movieRatings) {
        // Create a new ArrayList to store the top 5 ratings
        ArrayList<Integer> top5ratings = new ArrayList<>();
        // For each rating in the movieRatings values, add the rating to the top 5 ratings list.
        for (int rating : movieRatings.values()) {
            top5ratings.add(rating);
        }
        // Sort the ratings by reverse order
        top5ratings.sort(Comparator.reverseOrder());
        // Set a counter to 0 to keep track of the movies
        int count = 0;
        // Clear the top 5 list in case there are already movies in it.
        top5.clear();
        // For each rating in the top 5 ratings list, if there are not yet 5 movies in the list, add the name of the movie at the key (rating) to the top 5 list.
        for (int rating : top5ratings) {
            for (String key : movieRatings.keySet()) {
                if (count < 5 && rating >= 0) {
                    if (rating == movieRatings.get(key) && !top5.contains(key)) {
                        top5.add(key);
                        count++;
                    }
                }
            }
        }
    }



    /**
     * storeTop5Fav function that pulls out the top 5 rated movies in the Favourites lists
     * @param movieRatings HashMap storing the movie names as the keys and the ratings as the values
     */
    public void storeTop5Fav(HashMap<String, Integer> movieRatings) {
        // Create a new ArrayList to store the top 5 ratings
        ArrayList<Integer> top5FavRatings = new ArrayList<>();
        // For each rating in the movieRatings values, add the rating to the top 5 ratings list.
        for (int rating : movieRatings.values()) {
            top5FavRatings.add(rating);
        }
        // Sort the ratings by reverse order
        top5FavRatings.sort(Comparator.reverseOrder());
        // Set a counter to 0 to keep track of the movies
        int count = 0;
        // Clear the top 5 list in case there are already movies in it.
        top5Fav.clear();
        // For each rating in the top 5 ratings list, if there are not yet 5 movies in the list, add the name of the movie at the key (rating) to the top 5 list.
        for (int rating : top5FavRatings) {
            for (String key : movieRatings.keySet()) {
                if (count < 5 && rating >= 0) {
                    // Loop through the list and movieList to find the matching entries that meet all the requirements
                    for(List list : list){
                        for(Movie movie : movieList){
                            if (movie.getName().equals(key) && list.getType().equals("Favourites") && movie.getList().equals(list.getName()) && rating == movieRatings.get(key) && !top5Fav.contains(key)){
                                    top5Fav.add(key);
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }

    /**
     * storeTop5 function that pulls out the top 5 rated movies from the Want-To-Watch lists
     * @param movieRatings HashMap storing the movie names as the keys and the ratings as the values
     */
    public void storeTop5WTW(HashMap<String, Integer> movieRatings) {
        // Create a new ArrayList to store the top 5 ratings
        ArrayList<Integer> top5WTWRatings = new ArrayList<>();
        // For each rating in the movieRatings values, add the rating to the top 5 ratings list.
        for (int rating : movieRatings.values()) {
            top5WTWRatings.add(rating);
        }
        // Sort the ratings by reverse order
        top5WTWRatings.sort(Comparator.reverseOrder());
        // Set a counter to 0 to keep track of the movies
        int count = 0;
        // Clear the top 5 list in case there are already movies in it.
        top5WTW.clear();
        // For each rating in the top 5 ratings list, if there are not yet 5 movies in the list, add the name of the movie at the key (rating) to the top 5 list.
        for (int rating : top5WTWRatings) {
            for (String key : movieRatings.keySet()) {
                if (count < 5 && rating >= 0) {
                    // Loop through the list and movieList to find the matching entries that meet all the requirements
                    for(List list : list){
                        for(Movie movie : movieList){
                            if (movie.getName().equals(key) && list.getType().equals("Want-to-watch") && movie.getList().equals(list.getName()) && rating == movieRatings.get(key) && !top5WTW.contains(key)){
                                top5WTW.add(key);
                                count++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * storeTop5Watched function that stores the top 5 rated movies in the Watched list
     * @param movieRatings HashMap storing the movie names as the keys and the ratings as the values
     */
    public void storeTop5Watched(HashMap<String, Integer> movieRatings) {
        // Create a new ArrayList to store the top 5 ratings
        ArrayList<Integer> top5WatchedRatings = new ArrayList<>();
        // For each rating in the movieRatings values, add the rating to the top 5 ratings list.
        for (int rating : movieRatings.values()) {
            top5WatchedRatings.add(rating);
        }
        // Sort the ratings by reverse order
        top5WatchedRatings.sort(Comparator.reverseOrder());
        // Set a counter to 0 to keep track of the movies
        int count = 0;
        // Clear the top 5 list in case there are already movies in it.
        top5Watched.clear();
        // For each rating in the top 5 ratings list, if there are not yet 5 movies in the list, add the name of the movie at the key (rating) to the top 5 list.
        for (int rating : top5WatchedRatings) {
            for (String key : movieRatings.keySet()) {
                if (count < 5 && rating >= 0) {
                    // Loop through the list and movieList to find the matching entries that meet all the requirements
                    for(List list : list){
                        for(Movie movie : movieList){
                            if (movie.getName().equals(key) && list.getType().equals("Watched") && movie.getList().equals(list.getName()) && rating == movieRatings.get(key) && !top5Watched.contains(key)){
                                top5Watched.add(key);
                                count++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Function that stores the top 5 rated movies in a specified list
     * @param movieRatings HashMap storing the movie names as the keys and the ratings as the values
     * @param list String name of the list to search in.
     */
    public void storeTop5List(HashMap<String, Integer> movieRatings, String list) {
        // Create a new ArrayList to store the top 5 ratings
        ArrayList<Integer> top5ListRatings = new ArrayList<>();
        // For each rating in the movieRatings values, add the rating to the top 5 ratings list.
        for (int rating : movieRatings.values()) {
            top5ListRatings.add(rating);
        }
        // Sort the ratings by reverse order
        top5ListRatings.sort(Comparator.reverseOrder());
        // Set a counter to 0 to keep track of the movies
        int count = 0;
        // Clear the top 5 list in case there are already movies in it.
        top5List.clear();
        // For each rating in the top 5 ratings list, if there are not yet 5 movies in the list, add the name of the movie at the key (rating) to the top 5 list.
        for (int rating : top5ListRatings) {
            for (String key : movieRatings.keySet()) {
                if (count < 5 && rating >= 0) {
                    // Loop through the list and movieList to find the matching entries that meet all the requirements
                    for (Movie movie : movieList) {
                        if (movie.getName().equals(key) && movie.getList().equals(list) && rating == movieRatings.get(key) && !top5List.contains(key)) {
                            top5List.add(key);
                            count++;
                        }
                    }
                }
            }
        }
    }

    public void storeTopGenres(ArrayList<Movie> movies) {
        //TODO
    }

    /**
     * Getter for the top 5 movies in a given list from the storeTop5List function
     * @return String Arraylist of top 5 movies in a given list.
     */
    public ArrayList<String> getTop5List() {
        return top5List;
    }

    /**
     * Getter for the top 5 movies in the Want-To-Watch from the storeTop5WTW function
     * @return HashSet of 5 movies
     */
    public ArrayList<String> getTop5WTW() {
        return top5WTW;
    }

    /**
     * Getter for the top 5 movies in the Watched lists from storeTop5Watched function
     * @return HashSet of 5 movies.
     */
    public ArrayList<String> getTop5Watched() {
        return top5Watched;
    }
    /**
     * Getter for the Top 5 movies in all lists from storeTop5 function.
     * @return HashSet of the top 5 movies in all lists
     */
    public ArrayList<String> getTop5() {
        return top5;
    }

    /**
     * Getter for the top 5 movies in Favourites lists from storeTop5Favourites function
     * @return HashSet of top 5 movies in Favourites
     */
    public ArrayList<String> getTop5Fav() {
        return top5Fav;
    }
}
