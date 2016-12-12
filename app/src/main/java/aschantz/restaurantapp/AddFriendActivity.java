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


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        userId = getUid();

    }

    @OnClick(R.id.btnAddFriend)
    void registerClick() {
        if (!isFormValid()) {
            return;
        }


        //get reference to the firebase database, then say we want to work with the post child
        //want to create new method under the posts
        String key = FirebaseDatabase.getInstance().getReference().child("friends").push().getKey();
        Friend newFriend = new Friend(getUid(), etEmail.getText().toString());

        //makes branch in database for post, then the key, then the post data
        FirebaseDatabase.getInstance().getReference().child("friends").child(key).setValue(newFriend);

        Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show();


        finish();


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

    private void addFriend(String email) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://restaurantsapp-9a203.firebaseio.com/");


        DatabaseReference friendsRef = ref.child("friends");

        friends.put(email, new Friend(email));

        friendsRef.push().setValue(friends);

    }


}
