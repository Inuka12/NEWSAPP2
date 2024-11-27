import java.util.*;
import java.util.stream.Collectors;  //stream operations on collections

public class RecommendationEngine {
    public List<Article> recommendArticles(User user, List<Article> articles)  // Get the user's preferences
    {
        Set<String> userPreferences = user.getPreferences();
        if (userPreferences == null || userPreferences.isEmpty())  // If no preferences are set, return an empty list
        {
            return Collections.emptyList();
        }
        Set<String> normalizedUserPreferences = userPreferences.stream() //for case-insensitive matching
                .map(String::toLowerCase).collect(Collectors.toSet());  // Convert each preference to lowercase

        return articles.stream()  // Filter and sort articles based on matching keywords
                .filter
                        (article ->
                {                                                                            // Get the keywords of the article, ensuring null safety
                    Set<String> keywords = new HashSet<>
                            (article.getKeywords() != null ? article.getKeywords() : Collections.emptyList());
                    return keywords.stream().anyMatch(normalizedUserPreferences::contains);  // Check if any keyword matches the user preferences
                })
                .sorted
                        (Comparator.comparingInt     // Sort articles by the number of matching keywords
                                (

                        article -> -countMatchingKeywords
                                (normalizedUserPreferences, new HashSet<>
                                                (article.getKeywords() != null ? article.getKeywords() : Collections.emptyList())
                        ))
                )
                .collect(Collectors.toList());  // Collect the filtered and sorted articles into a list
    }

    private int countMatchingKeywords(Set<String> preferences, Set<String> keywords)  // Count how many keywords are present in the user's preferences
    {
        return (int) keywords.stream().filter(preferences::contains).count();  // Filter keywords that match preferences
    }
}
