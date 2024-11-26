import java.io.IOException;
import java.util.List;
import java.util.Scanner;




public class MainApp {
    public static void main(String[] args) {
        String userFilePath = "users.json";
        String articlesFilePath = "articles.csv";
        String stopwordsFilePath = "stopwords.txt";

        UserManager userManager = new UserManager(userFilePath);
        FileHandler fileHandler = new FileHandler();
        RecommendationEngine recommendationEngine = new RecommendationEngine();

        try {
            List<Article> articles = fileHandler.readArticles(articlesFilePath);
            NLPProcessor nlpProcessor = new NLPProcessor(stopwordsFilePath);
            nlpProcessor.processArticles(articles);

            Scanner scanner = new Scanner(System.in);
            User user = null;
            boolean running = true;

            while (running) {
                System.out.println("\nWelcome! Please select an option:");
                System.out.println("1. Sign Up");
                System.out.println("2. Log In");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
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

                    case 2:
                        System.out.println("Enter username:");
                        String loginUsername = scanner.nextLine();
                        System.out.println("Enter password:");
                        String loginPassword = scanner.nextLine();
                        user = userManager.login(loginUsername, loginPassword);

                        if (user != null) {
                            System.out.println("Login successful!");

                            boolean loggedIn = true;
                            while (loggedIn) {
                                System.out.println("\nWelcome, " + user.getUsername() + "!");
                                System.out.println("1. Enter Your Preferences");
                                System.out.println("2. Get Recommendations");
                                System.out.println("3. Log Out");

                                int userChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                switch (userChoice) {
                                    case 1:
                                        System.out.println("Enter your preferences (comma-separated topics):");
                                        String preferencesInput = scanner.nextLine();
                                        String[] preferences = preferencesInput.split(",");
                                        for (String preference : preferences) {
                                            userManager.addUserPreference(user, preference.trim().toLowerCase());
                                        }
                                        System.out.println("Preferences updated successfully!");
                                        break;

                                    case 2:
                                        List<Article> recommendedArticles = recommendationEngine.recommendArticles(user, articles);
                                        if (recommendedArticles.isEmpty()) {
                                            System.out.println("No articles match your preferences.");
                                        } else {
                                            System.out.println("Recommended articles for you:");
                                            for (Article article : recommendedArticles) {
                                                System.out.println(article);
                                            }
                                        }
                                        break;

                                    case 3:
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
                        System.out.println("Exiting the system. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

