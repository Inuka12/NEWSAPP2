import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;

public class User {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("preferences")
    private Set<String> preferences = new HashSet<>();

    public User() {
        this.preferences = new HashSet<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.preferences = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<String> preferences) {
        this.preferences = preferences != null ? preferences : new HashSet<>();
    }

    public void addPreference(String topic) {
        preferences.add(topic);
    }

    public void removePreference(String topic) {
        preferences.remove(topic);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", preferences=" + preferences +
                '}';
    }
}
