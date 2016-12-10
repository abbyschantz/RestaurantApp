package aschantz.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aschantz.restaurantapp.model.Friend;
import aschantz.restaurantapp.model.Post;
import aschantz.restaurantapp.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aschantz on 12/9/16.
 */
public class AddFriendActivity extends BaseActivity {


    //Map<String, User> users = new HashMap<String, User>();
    Map<String, Friend> friends = new HashMap<>();

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    public List friendList;
    public Object friendObject;

    private String userId;



    @BindView(R.id.etEmail)
    EditText etEmail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        ButterKnife.bind(this);

        //etEmail = (EditText) findViewById(R.id.etEmail);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        userId = getUid();




        //printMap(users);


    }

    @OnClick(R.id.btnAddFriend)
    void registerClick() {
        if(!isFormValid()) {
            return;
        }


        //get reference to the firebase database, then say we want to work with the post child
        //want to create new method under the posts
        String key = FirebaseDatabase.getInstance().getReference().child("friends").push().getKey();
        Friend newFriend = new Friend(getUid(),etEmail.getText().toString());

        //makes branch in database for post, then the key, then the post data
        FirebaseDatabase.getInstance().getReference().child("friends").child(key).setValue(newFriend);

        Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show();


        finish();

        //get reference to the firebase database, then say we want to work with the post child
        //want to create new method under the posts
        //String key = FirebaseDatabase.getInstance().getReference().child("users").push().getKey();


//        User addFriendUser = new User(etEmail.getText().toString());
//        String emailString = etEmail.getText().toString();
//        addFriend(emailString);

////////////////////////
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReferenceFromUrl("https://restaurantsapp-9a203.firebaseio.com/");
//
//
//        DatabaseReference usersRef = ref.child("users").child("friends");
//
//        Map<String, User> users = new HashMap<String, User>();
//        users.put("sabby", new User("abby.s"));
//        users.put("ggenna", new User("genna.g"));
//
//        usersRef.push().setValue(users);


        //////////////////// \\ ////////////////////////////////////////////////////////////////////////////////


        //makes branch in database for post, then the key, then the post data
        //FirebaseDatabase.getInstance().getReference().child("users").child(key).setValue(addFriend);
        //FirebaseDatabase.getInstance().getReference().child("users").child(getUid()).child("friends").push(addFriend);


//        Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show();
//
//        finish();


    }

    //username from email
    //extracts the username from the email address (string before the @ symbol)
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    //make sure the email and password fields are not empty
    private boolean isFormValid() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Required");
            return false;
        }

        return true;
    }

    private void addFriend (String email) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://restaurantsapp-9a203.firebaseio.com/");


        DatabaseReference friendsRef = ref.child("friends");

        friends.put(email, new Friend(email));
//        friends.put("ggenna", new User("genna.g"));

        friendsRef.push().setValue(friends);

    }


//
//    // Get a reference to our posts
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReferenceFromUrl("https://restaurantsapp-9a203.firebaseio.com/");
//
//    //DatabaseReference usersRef = ref.child("users").child("friends");
//
//    ValueEventListener usersEventListener = ref.child("friends").addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot snapshot) {
//            friendObject = snapshot.getValue();
//
//
//
//
//            for (int i = 0; i < friendObject.toString().length(); i++ ){
//                //System.out.println(friendObject.toString().charAt(i));
//
//            }
//            System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
//            System.out.println(userId);
//            if (snapshot.getValue().toString().contains("{email=Jeff, uid="+userId)) {
//                System.out.println("HAPPYHAPPPYJOBJON");
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    });

}
