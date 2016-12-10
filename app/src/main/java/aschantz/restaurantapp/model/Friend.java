package aschantz.restaurantapp.model;

/**
 * Created by aschantz on 12/10/16.
 */
public class Friend {

    private String uid;
    private String email;

    public Friend() {
    }

    public Friend(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public Friend(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
