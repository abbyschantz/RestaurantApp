package aschantz.restaurantapp.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aschantz on 12/2/16.
 */
public class Post {
    private String uid;
    private String author;
    private String place;
    private String title;
    private String body;
    private String userRating;
    private String googleRating;

    public Post() {
    }

    public Post(String uid, String author, String place, String title, String body, String userRating, String googleRating) {
        this.uid = uid;
        this.author = author;
        this.place = place;
        this.title = title;
        this.body = body;
        this.userRating = userRating;
        this.googleRating = googleRating;

    }

    //used to upload post object to the firebase, so we don't type columns one by one
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("place", place);
        result.put("title", title);
        result.put("body", body);
        result.put("userRating", userRating);
        result.put("googleRating",googleRating);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getGoogleRating() {
        return googleRating;
    }

    public void setGoogleRating(String googleRating) {
        this.googleRating = googleRating;
    }
}

