import com.fasterxml.jackson.databind.ObjectMapper; // JSON serialization and deserialization
import java.io.*;                                  //utility classes for file handling and collections
import java.util.*;

public class UserManager {
    private Map<String, User> users;   // Map to store users with their username as the key

    private String filePath;  // Path to the file where user data is stored
    private ObjectMapper objectMapper; // ObjectMapper for handling JSON operations

    public UserManager(String filePath) {     // Constructor to initialize UserManager with a file path and load existing users
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.users = loadUsers();
    }

    public Map<String, User> loadUsers() {  // Method to load users from the file into the map
        Map<String, User> users = new HashMap<>();
        File file = new File(filePath);

        if (!file.exists()) {   // Create the file if it does not exist
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
            return users;
        }

        try {   // Read users from the file using ObjectMapper
            users = objectMapper.readValue(file, objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, User.class));
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
        return users;
    }

    public void saveUsers() {  // Method to save the current users map to the file
        try {
            objectMapper.writeValue(new File(filePath), users);
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }


  public synchronized boolean signUp(String username, String password) // Synchronized method to handle user sign-up, ensuring thread safety
  {                                                                   //only one thread can execute these methods at a time
      if (users.containsKey(username)) {                 // Check if the username already exists in the user map
          System.out.println("Username already exists.");
          return false; // Notify if the username is taken,returm false.
      }
      users.put(username, new User(username, password));  // Add the new user to the user map
      saveUsers();  // Save the updated user data to the file
      return true;
  }

    public synchronized User login(String username, String password)
    { // Synchronized method to handle user login, ensuring thread safety
        User user = users.get(username); // Retrieve the user object
        if (user != null && user.getPassword().equals(password)) {  // Validate the password

            return user;
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    public synchronized void addUserPreference(User user, String topic) {
        user.addPreference(topic);  // Add user's preferences

        saveUsers();
    }

    public synchronized void removeUserPreference(User user, String topic) { //not implemented
        user.removePreference(topic);
        saveUsers();
    }


    public Map<String, User> getAllUsers() // Method to retrieve all users
    {
        return users;
    }
}


