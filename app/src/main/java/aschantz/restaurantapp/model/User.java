package aschantz.restaurantapp.model;

/**
 * Created by aschantz on 12/2/16.
 */
public class User {
    private String email;
    private String userName;

    public User() {}

    public User(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

