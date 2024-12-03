
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;   // For managing a thread pool
import java.util.concurrent.Executors;        // For creating a thread pool

public class MainApp {
    public static void main(String[] args)

    {
        String userFilePath = "users.json";          // Path to store user data
        String articlesFilePath = "articles.csv";   // Path to the article dataset
        String stopwordsFilePath = "stopwords.txt";  // Path to the stopwords file

        UserManager userManager = new UserManager(userFilePath);   // Manages user-related operations
        FileHandler fileHandler = new FileHandler();           // Handles file operations for articles
        RecommendationEngine recommendationEngine = new RecommendationEngine();   // Provides article recommendations

        ExecutorService executorService = Executors.newFixedThreadPool(4); // Thread pool for concurrency 4
        try
        {
            List<Article> articles = fileHandler.readArticles(articlesFilePath);   // Read articles from file
            NLPProcessor nlpProcessor = new NLPProcessor(stopwordsFilePath);       // Initialize NLP for keyword extraction


            // Process articles in a separate thread
            executorService.submit(() ->
            {
                try {
                    nlpProcessor.processArticles(articles);   // Extract keywords for all articles
                    System.out.println("Article processing completed.");
                } catch (Exception e) {
                    System.err.println("Error during article processing: " + e.getMessage());
                }
            });

            Scanner scanner = new Scanner(System.in);
            boolean running = true;  // Main loop

            while (running)
            {
                System.out.println("\nWelcome! Please select an option:");
                System.out.println("1. Sign Up");
                System.out.println("2. Log In");
                System.out.println("3. Exit");

                int choice = getValidNumericInput(scanner);


                switch (choice) {                                       // Handle user sign-up
                    case 1:
                        System.out.println("Enter username:");
                        String signupUsername = scanner.nextLine();
                        System.out.println("Enter password:");
                        String signupPassword = scanner.nextLine();

                        if (userManager.signUp(signupUsername, signupPassword)) {
                            System.out.println("Sign-up successful!");
                        } else {
                            System.out.println("Sign-up failed. Username already exists.");
                        }
                        break;

                    case 2:                                                // Handle user login
                        System.out.println("Enter username:");
                        String loginUsername = scanner.nextLine();
                        System.out.println("Enter password:");
                        String loginPassword = scanner.nextLine();

                        User user = userManager.login(loginUsername, loginPassword);

                        if (user != null)
                        {
                            System.out.println("Login successful!");
                            boolean loggedIn = true;

                            while (loggedIn) {                                  // User actions after login
                                System.out.println("\nWelcome," + user.getUsername() + "!" + " Please select an option:");
                                System.out.println("1. Enter Your Preferences");
                                System.out.println("2. Get Recommendations");
                                System.out.println("3. Log Out");

                                int userChoice = getValidNumericInput(scanner);


                                switch (userChoice) {                // Handle preferences
                                    case 1:
                                        System.out.println("Enter your preferences (comma-separated ):");
                                        String preferencesInput = scanner.nextLine();


                                        String[] preferences = preferencesInput.split(",");  // add preferences synchronously
                                        for (String preference : preferences) {
                                            userManager.addUserPreference(user, preference.trim().toLowerCase());
                                        }
                                        System.out.println("Preferences added successfully!");
                                        break;

                                    case 2:

                                        List<Article> recommendedArticles = recommendationEngine.recommendArticles(user, articles); // Fetch recommendations synchronously
                                        if (recommendedArticles.isEmpty()) {
                                            System.out.println("No articles match your preferences.");
                                        } else {
                                            System.out.println("Recommended articles for you:");
                                            for (Article article : recommendedArticles) {
                                                System.out.println(article);
                                            }
                                        }
                                        break;

                                    case 3:                        // Logout user
                                        loggedIn = false;
                                        System.out.println("Logged out successfully.");
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                            }
                        }
                        break;

                    case 3:
                        running = false;
                        executorService.shutdown(); // Shutdown thread pool
                        System.out.println("Exiting the system. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (IOException e) {  // Handle exceptions related to file operations
            System.err.println("Error: " + e.getMessage());
        } finally {
            executorService.shutdown();  // Ensure thread pool is closed
        }
    }


    private static int getValidNumericInput(Scanner scanner) {  // Helper method to get a valid numeric input
        while (true) {                                          // Keep prompting the user until a valid input is provided
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();                  // Read the user's input as a string
            try {
                return Integer.parseInt(input); // Convert input to an integer
            } catch (NumberFormatException e) {  // Handle cases where the input is not a valid integer
                System.out.println("Invalid input. Please enter a valid option.");
            }
        }
    }

}
