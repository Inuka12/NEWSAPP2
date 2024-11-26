import java.time.LocalDateTime;
import java.util.List;

public class Article {
    private String topic; // The topic  of the article
    private String link;
    private String domain;
    private LocalDateTime publishedDate;
    private String title;
    private String language;
    private List<String> keywords;  // List of keywords associated with the article for easier filter recommendation

    public Article(String topic, String link, String domain, LocalDateTime publishedDate, String title, String language)

    {
        this.topic = topic;  // Constructor to initialize key attributes of an article
        this.link = link;
        this.domain = domain;
        this.publishedDate = publishedDate;
        this.title = title;
        this.language = language;
    }

    //getters and setters
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override    // Overridden toString method for a readable representation of an article object
    public String toString() {
        return "Article{" +
                "topic='" + topic + '\'' +
                ", link='" + link + '\'' +
                ", domain='" + domain + '\'' +
                ", publishedDate=" + publishedDate +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
