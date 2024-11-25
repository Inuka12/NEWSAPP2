import opennlp.tools.tokenize.SimpleTokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class NLPProcessor {
    private final Set<String> stopwords;

    public NLPProcessor(String stopwordsFilePath) throws IOException {
        this.stopwords = loadStopwords(stopwordsFilePath);
    }

    private Set<String> loadStopwords(String filePath) throws IOException {
        Set<String> stopwords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopwords.add(line.trim().toLowerCase());
            }
        }
        return stopwords;
    }

    public List<String> processText(String text) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(text.toLowerCase());
        List<String> keywords = new ArrayList<>();
        for (String token : tokens) {
            if (!stopwords.contains(token) && token.length() > 2) {
                keywords.add(token);
            }
        }
        return keywords;
    }

    public void processArticles(List<Article> articles) {
        for (Article article : articles) {
            String content = article.getTitle() + " " + article.getTopic();
            List<String> keywords = processText(content);
            article.setKeywords(keywords);
        }
    }
}
