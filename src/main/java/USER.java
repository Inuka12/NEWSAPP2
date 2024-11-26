import com.fasterxml.jackson.annotation.JsonProperty; //JSON property mapping
import java.util.HashSet; //for working with sets
import java.util.Set;

public class User {
    @JsonProperty("username") // Username of the user, mapped to "username" in JSON
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("preferences")
    private Set<String> preferences = new HashSet<>();

    public User() {
        this.preferences = new HashSet<>(); // Default constructor initializing an empty preferences set
    }

    public User(String username, String password) {  // Constructor to initialize username,password with empty preferences
        this.username = username;
        this.password = password;
        this.preferences = new HashSet<>();
    }

    public String getUsername() {  //getters and setters
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

    public void setPreferences(Set<String> preferences) {  // Setter for user preferences, ensuring a non-null set
        this.preferences = preferences != null ? preferences : new HashSet<>();
    }

    public void addPreference(String topic) {  // Method to add a topic to the user's preferences
        preferences.add(topic);
    }

    public void removePreference(String topic) { //not implemented (out scope)
        preferences.remove(topic);
    }

    @Override
    public String toString() {  // Overridden toString method for a readable representation of a user object
        return "User{" +
                "username='" + username + '\'' +
                ", preferences=" + preferences +
                '}';
    }
}
