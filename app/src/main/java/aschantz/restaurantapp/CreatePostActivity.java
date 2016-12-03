package aschantz.restaurantapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import aschantz.restaurantapp.model.Post;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aschantz on 12/2/16.
 */
public class CreatePostActivity extends BaseActivity{
    EditText etTitle = (EditText) findViewById(R.id.etTitle);
    EditText etBody = (EditText) findViewById(R.id.etBody);

//    @BindView(R.id.etTitle)
//    EditText etTitle;
//    @BindView(R.id.etBody)
//    EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        //ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSend)
    void sendClick() {
        if(!isFormValid()) {
            return;
        }

        //get reference to the firebase database, then say we want to work with the post child
        //want to create new method under the posts
        String key = FirebaseDatabase.getInstance().getReference().child("posts").push().getKey();
        Post newPost = new Post(getUid(), getUserName(), etTitle.getText().toString(), etBody.getText().toString());

        //makes branch in database for post, then the key, then the post data
        FirebaseDatabase.getInstance().getReference().child("posts").child(key).setValue(newPost);

        Toast.makeText(this, "Post created", Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean isFormValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etTitle.getText().toString())) {
            etTitle.setError("Required");
            result = false;
        } else {
            etTitle.setError(null);
        }

        if (TextUtils.isEmpty(etBody.getText().toString())) {
            etBody.setError("Required");
            result = false;
        } else {
            etBody.setError(null);
        }

        return result;
    }
}
