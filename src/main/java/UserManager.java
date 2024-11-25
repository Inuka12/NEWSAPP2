import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class UserManager {
    private Map<String, User> users;
    private String filePath;
    private ObjectMapper objectMapper;

    public UserManager(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.users = loadUsers();
    }

    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
            return users;
        }

        try {
            users = objectMapper.readValue(file, objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, User.class));
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
        return users;
    }

    public void saveUsers() {
        try {
            objectMapper.writeValue(new File(filePath), users);
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }

    public boolean signUp(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return false;
        }
        users.put(username, new User(username, password));
        saveUsers();
        return true;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    public void addUserPreference(User user, String topic) {
        user.addPreference(topic);
        saveUsers();
    }

    public void removeUserPreference(User user, String topic) {
        user.removePreference(topic);
        saveUsers();
    }

    public Map<String, User> getAllUsers() {
        return users;
    }
}


