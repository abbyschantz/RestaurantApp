package aschantz.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aschantz.restaurantapp.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aschantz on 12/2/16.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btnRegister)
    void registerClick() {
        if(!isFormValid()) {
            return;
        }

        showProgressDialog();
        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            FirebaseUser fbUser = task.getResult().getUser();
                            //update the user profile to set name of user
                            //extracts display name from the email (takes until @)
                            fbUser.updateProfile(new UserProfileChangeRequest.Builder().
                                    setDisplayName(usernameFromEmail(fbUser.getEmail())).build());


                            User user = new User(usernameFromEmail(fbUser.getEmail()), fbUser.getEmail());
                            databaseReference.child("users").child(fbUser.getUid()).setValue(user);

                            Toast.makeText(LoginActivity.this, "User created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    //login
    @OnClick(R.id.btnLogin)
    void loginChick() {
        //below (int) is to make app crash for crash report testing
        //int num = 6/0;
        if(!isFormValid()) {
            return;
        }

        showProgressDialog();

        //sign in process then give onClickListener to request
        //gives response from backend to see if authentication was successful
        //if it was, then the method is called
        firebaseAuth.signInWithEmailAndPassword(
                etEmail.getText().toString(),
                etPassword.getText().toString()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if(task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, PostsActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Required");
            return false;
        }
        return true;
    }

}

