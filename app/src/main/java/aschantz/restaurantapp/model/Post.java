package aschantz.restaurantapp.model;

import android.graphics.drawable.Drawable;

import com.google.firebase.auth.api.model.StringList;
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
    private String priceRange;
    private String dishes;
    private String res;
    private String veg;
    private String gluten;
    private String wait;
    private String alc;

    public Post() {
    }

    public Post(String uid, String author, String place, String title, String body,
                String userRating, String googleRating, String priceRange, String dishes,
                String res, String veg, String gluten, String wait, String alc) {
        this.uid = uid;
        this.author = author;
        this.place = place;
        this.title = title;
        this.body = body;
        this.userRating = userRating;
        this.googleRating = googleRating;
        this.priceRange = priceRange;
        this.dishes = dishes;
        this.res = res;
        this.veg = veg;
        this.gluten = gluten;
        this.wait = wait;
        this.alc = alc;

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
        result.put("priceRange", priceRange);
        result.put("dishes", dishes);
        result.put("res", res);
        result.put("veg", res);
        result.put("gluten", gluten);
        result.put("wait", wait);
        result.put("alc", alc);
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

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getVeg() {
        return veg;
    }

    public void setVeg(String veg) {
        this.veg = veg;
    }

    public String getGluten() {
        return gluten;
    }

    public void setGluten(String gluten) {
        this.gluten = gluten;
    }

    public String getWait() {
        return wait;
    }

    public void setWait(String wait) {
        this.wait = wait;
    }

    public String getAlc() {
        return alc;
    }

    public void setAlc(String alc) {
        this.alc = alc;
    }
}

