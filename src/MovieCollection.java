import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName) {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void menu() {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q")) {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option) {
        if (option.equals("t")) {
            searchTitles();
        } else if (option.equals("c")) {
            searchCast();
        } else if (option.equals("k")) {
            searchKeywords();
        } else if (option.equals("g")) {
            listGenres();
        } else if (option.equals("r")) {
            listHighestRated();
        } else if (option.equals("h")) {
            listHighestRevenue();
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles() {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1) {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie) {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        System.out.println("Enter a cast search term: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> cast = new ArrayList<String>();
        ArrayList<String> searchCast = new ArrayList<String>();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) {
            String movieCast = movies.get(i).getCast();
            movieCast = movieCast.toLowerCase();
            for (int j = 0; j < movieCast.length(); j++) {
                int delimiter = movieCast.indexOf("|");
                if (delimiter == -1) {
                    delimiter = movieCast.length();
                }
                String actor = movieCast.substring(0, delimiter);
                movieCast = movieCast.substring(delimiter);
                if (!(cast.contains(actor))) {
                    cast.add(actor);
                }
            }
        }
        for (int i = 0; i < cast.size(); i++) {
            if (cast.get(i).contains(searchTerm)) {
                searchCast.add(cast.get(i));
            }
        }
        searchCast.sort(String::compareTo);
        for (int i = 0; i < searchCast.size(); i++) {
            System.out.print(i + 1 + ". ");
            System.out.println(searchCast.get(i));
        }
        System.out.println("Enter the number of the actor you want to know more about: ");
        String actorNum = scanner.nextLine();
        while (Integer.parseInt(actorNum) > searchCast.size() || Integer.parseInt(actorNum) <= 0) {
            System.out.println("Enter a number: ");
            actorNum = scanner.nextLine();
        }
        int actorNumAsInt = Integer.parseInt(actorNum);
        System.out.println((searchCast.get(actorNumAsInt - 1)) + "'s movies: ");
        for (Movie movie : movies) {
            String[] movieCast = movie.getCast().split("\\|");
            for (String name : movieCast) {
                if (name.equalsIgnoreCase(searchCast.get(actorNumAsInt - 1))) {
                    results.add(movie);
                }
            }
        }
        sortResults(results);
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + title);
        }
        System.out.println("Choose a movie to learn more about: ");
        int movieChoice = scanner.nextInt();
        scanner.nextLine();
        Movie selected = results.get(movieChoice - 1);
        displayMovieInfo(selected);
        System.out.println("\nPress enter to return to menu.");
        scanner.nextLine();
    }

    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String movieKeyword = movies.get(i).getKeywords();
            movieKeyword = movieKeyword.toLowerCase();

            if (movieKeyword.indexOf(searchTerm) != -1) {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String keyword = results.get(i).getKeywords();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + keyword);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        ArrayList<String> genre = new ArrayList<String>();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) {
            String movieGenres = movies.get(i).getGenres();
            movieGenres = movieGenres.toLowerCase();
            String[] genreList = movieGenres.split("\\|");
            for (String genre2 : genreList) {
                if (genre.indexOf(genre2) == -1) {
                    genre.add(genre2);
                }
            }
        }
        genre.sort(String::compareTo);
        for (int i = 0; i < genre.size(); i++) {
            System.out.print(i + 1 + ". ");
            System.out.println(genre.get(i));
        }
        System.out.println("Enter the number of the genre you want to know more about: ");
        String genreNum = scanner.nextLine();
        while (Integer.parseInt(genreNum) > genre.size() || Integer.parseInt(genreNum) <= 0) {
            System.out.println("Enter a number: ");
            genreNum = scanner.nextLine();
        }
        int genreNumAsInt = Integer.parseInt(genreNum);
        System.out.println((genre.get(genreNumAsInt - 1)) + " movies: ");
        for (Movie movie : movies) {
            String[] movieCast = movie.getGenres().split("\\|");
            for (String name : movieCast) {
                if (name.equalsIgnoreCase(genre.get(genreNumAsInt - 1))) {
                    results.add(movie);
                }
            }
        }
        sortResults(results);
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + title);
        }
        sortResults(results);
        System.out.println("Choose a movie to learn more about: ");
        int movieChoice = scanner.nextInt();
        scanner.nextLine();
        Movie selected = results.get(movieChoice - 1);
        displayMovieInfo(selected);
        System.out.println("\nPress enter to return to menu.");
        scanner.nextLine();
    }

    private void listHighestRated() {
        ArrayList<Double> orderedRatings = new ArrayList<Double>();
        for(int i=0;i<movies.size();i++){
            orderedRatings.add(movies.get(i).getUserRating());
        }
        orderedRatings.sort(Double::compareTo);
        Collections.sort(orderedRatings, Collections.reverseOrder());
        ArrayList<Movie> top50Ratings = new ArrayList<Movie>();
        for(int i=0;i<50;i++){
            for(Movie movie : movies){
                if(movie.getUserRating() == orderedRatings.get(i)){
                    if(!top50Ratings.contains(movie)){
                        top50Ratings.add(movie);
                        break;
                    }
                }
            }
        }
        int i = 0;
        for (Movie movie: top50Ratings)
        {
            i++;
            System.out.println(i + ". " + movie.getTitle());
        }
        System.out.println("Pick a movie to learn more about: ");
        int choice = scanner.nextInt();
        while (choice>top50Ratings.size() || choice<1){
            System.out.println("Enter number: ");
            choice = scanner.nextInt();
        }
        Movie selected = top50Ratings.get(choice-1);
        displayMovieInfo(selected);
        System.out.println("\n ** Press Enter to return to the main menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Integer> revenue = new ArrayList<Integer>();
        for (Movie movie : movies) {
            revenue.add(movie.getRevenue());
        }
        revenue.sort(Integer::compareTo);
        revenue.sort(Collections.reverseOrder());
        ArrayList<Movie> top50revenue = new ArrayList<Movie>();
        for (int i = 0; i < 50; i++){
            for (Movie movie : movies){
                if (movie.getRevenue() == revenue.get(i)){
                    if (!top50revenue.contains(movie)){
                        top50revenue.add(movie);
                        break;
                    }
                }
            }
        }
        int i = 0;
        for (Movie movie: top50revenue){
            i++;
            System.out.println(i + ". " + movie.getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice > top50revenue.size() || choice < 1){
            System.out.print("Enter number: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        Movie selectedMovie = top50revenue.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}