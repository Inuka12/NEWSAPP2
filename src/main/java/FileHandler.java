import org.apache.commons.csv.*;  //parsing CSV files efficiently

import java.io.*;           //handling file input/output
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  //formatting/parsing date and time strings
import java.util.*
        ;



public class FileHandler {
    public List<Article> readArticles(String filePath) throws IOException {  // IOException if an error occurs during file reading.
        List<Article> articles = new ArrayList<>();   /// Initialize list to store articles
        try (CSVParser parser = new CSVParser(     //// Try-with-resources to ensure the parser is closed after use
                new FileReader(filePath),         //// Read the file at the specified path

                CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()  // Use default CSV format
                                                                                                                 // Specify ';' as the delimiter
        ))                                                                                                       // Treat the first row as the header
                                                                                                                 // Ignore case when matching headers
                                                                                                                 // Trim whitespace from header and field values
        {
            for (CSVRecord record : parser)     // Loop through each record in the CSV file
            {
                String topic = record.get("topic");      // Extract data from each column based on header names
                String link = record.get("link");
                String domain = record.get("domain");
                String publishedDate = record.get("published_date");
                String title = record.get("title");
                String language = record.get("lang");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   // Parse the published date into a LocalDateTime object
                LocalDateTime date = LocalDateTime.parse(publishedDate, formatter);

                articles.add(new Article(topic, link, domain, date, title, language));  // Create an Article object and add it to the list
            }
        }
        return articles; // Return the list of parsed articles
    }
}

