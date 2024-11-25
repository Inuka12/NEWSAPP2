import java.util.*;
import java.util.stream.Collectors;

public class RecommendationEngine {
    public List<Article> recommendArticles(User user, List<Article> articles) {
        Set<String> userPreferences = user.getPreferences();
        if (userPreferences == null || userPreferences.isEmpty()) {
            return Collections.emptyList();
        }
        Set<String> normalizedUserPreferences = userPreferences.stream()
                .map(String::toLowerCase).collect(Collectors.toSet());

        return articles.stream()
                .filter(article -> {
                    Set<String> keywords = new HashSet<>(article.getKeywords() != null ? article.getKeywords() : Collections.emptyList());
                    return keywords.stream().anyMatch(normalizedUserPreferences::contains);
                })
                .sorted(Comparator.comparingInt(
                        article -> -countMatchingKeywords(normalizedUserPreferences, new HashSet<>(article.getKeywords() != null ? article.getKeywords() : Collections.emptyList()))))
                .collect(Collectors.toList());
    }

    private int countMatchingKeywords(Set<String> preferences, Set<String> keywords) {
        return (int) keywords.stream().filter(preferences::contains).count();
    }
}
