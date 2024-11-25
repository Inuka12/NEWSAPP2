import java.time.LocalDateTime;
import java.util.List;

public class Article {
    private String topic;
    private String link;
    private String domain;
    private LocalDateTime publishedDate;
    private String title;
    private String language;
    private List<String> keywords;

    public Article(String topic, String link, String domain, LocalDateTime publishedDate, String title, String language) {
        this.topic = topic;
        this.link = link;
        this.domain = domain;
        this.publishedDate = publishedDate;
        this.title = title;
        this.language = language;
    }

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

    @Override
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
