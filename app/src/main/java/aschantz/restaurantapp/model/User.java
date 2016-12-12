package aschantz.restaurantapp.model;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aschantz on 12/2/16.
 */
public class User {
    private String email;
    private String userName;
    private List friendList;

    public User() {
    }

    public User(String email, String userName, List friendList) {
        this.email = email;
        this.userName = userName;
        this.friendList = friendList;
    }

    public User(String email) {
        this.email = email;
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

    public List getFriendList() {
        return friendList;
    }

    public void setFriendList(List friendList) {
        this.friendList = friendList;
    }

}

