import org.apache.commons.csv.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileHandler {
    public List<Article> readArticles(String filePath) throws IOException {
        List<Article> articles = new ArrayList<>();
        try (CSVParser parser = new CSVParser(
                new FileReader(filePath),
                CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
        )) {
            for (CSVRecord record : parser) {
                String topic = record.get("topic");
                String link = record.get("link");
                String domain = record.get("domain");
                String publishedDate = record.get("published_date");
                String title = record.get("title");
                String language = record.get("lang");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime date = LocalDateTime.parse(publishedDate, formatter);

                articles.add(new Article(topic, link, domain, date, title, language));
            }
        }
        return articles;
    }
}

