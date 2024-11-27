import opennlp.tools.tokenize.SimpleTokenizer;  // For tokenizing text into words

import java.io.BufferedReader;         // For reading text files line by line
import java.io.FileReader;             // For reading files
import java.io.IOException;             // For handling input/output exceptions
import java.util.*;



public class NLPProcessor
{
    private final Set<String> stopwords;  // A set of stopwords to filter out irrelevant words

    public NLPProcessor(String stopwordsFilePath) throws IOException     //Constructor to initialize the NLPProcessor with a stopwords file
    {
        this.stopwords = loadStopwords(stopwordsFilePath);
    }

    private Set<String> loadStopwords(String filePath) throws IOException   //Loads stopwords from a file into a set for quick lookup
    {
        Set<String> stopwords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = reader.readLine()) != null)    // Trim and convert stopwords to lowercase for uniformity
            {
                stopwords.add(line.trim().toLowerCase());
            }
        }
        return stopwords;
    }

    public List<String> processText(String text)                //Processes a given text to extract keywords.Removes stopwords and filters out short words
    {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;    // OpenNLP tokenizer instance
        String[] tokens = tokenizer.tokenize(text.toLowerCase());  // Tokenize the text
        List<String> keywords = new ArrayList<>();
        for (String token : tokens)
        {
            if (!stopwords.contains(token) && token.length() > 2)   // Include tokens that are not stopwords and have more than 2 characters
            {
                keywords.add(token);
            }
        }
        return keywords;
    }

    public void processArticles(List<Article> articles)  //Processes a list of articles to extract keywords and associate them with each article
    {
        for (Article article : articles)   // Combine title and topic for keyword extraction
        {
            String content = article.getTitle() + " " + article.getTopic();
            List<String> keywords = processText(content);  // Extract keywords from combined content
            article.setKeywords(keywords);              // Assign extracted keywords to the article
        }
    }
}
