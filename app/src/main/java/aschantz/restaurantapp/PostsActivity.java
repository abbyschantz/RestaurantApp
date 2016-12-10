package aschantz.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import aschantz.restaurantapp.model.Friend;
import aschantz.restaurantapp.model.Post;

/**
 * Created by aschantz on 12/2/16.
 */
public class PostsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private PostsAdapter postsAdapter;
    String friendsSnapshotValue;
    //private FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostsActivity.this, CreatePostActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        postsAdapter = new PostsAdapter(getApplicationContext(), getUid());
        RecyclerView recyclerViewPlaces = (RecyclerView) findViewById(
                R.id.recyclerViewPosts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerViewPlaces.setLayoutManager(layoutManager);
        recyclerViewPlaces.setAdapter(postsAdapter);

        initPostListener();

//        friendsAdapter = new FriendsAdapter(getApplicationContext(), getUid());
//        Friend youFriend = new Friend(getUid(), getUserName());
//        friendsAdapter.addFriend(youFriend, getUid());

        //checkFriends();
    }

    private void initPostListener() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot dataSnapshotWithin = dataSnapshot;
                final Post newPost = dataSnapshot.getValue(Post.class);
                final String postUid = newPost.getUid();
                final String postAuthor = newPost.getAuthor();
                System.out.println("friends snapshot value"+friendsSnapshotValue);
                if (getUid().equals(postUid)) {
                    postsAdapter.addPost(newPost, dataSnapshot.getKey());
                    System.out.println(dataSnapshot.getValue().toString());

                } else {
                    checkFriends(postAuthor, dataSnapshotWithin, newPost);
                    //if(friendsSnapshotValue.contains("{email=" + postAuthor + ", uid=" + getUid())) {
                        //System.out.println("FRIENDSSNAPSHOTVALUEIN");


                }

//
                //postsAdapter.addPost(newPost, dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //remove post from adapter

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            //logout user
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (id == R.id.add_friend) {
            startActivity(new Intent(this, AddFriendActivity.class));
        }
        if (id == R.id.view_friends) {
            startActivity(new Intent(this, FriendsActivity.class));
        }
        if (id == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkFriends(final String postAuthor, DataSnapshot dataSnapshot, Post newPost) {
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReferenceFromUrl("https://restaurantsapp-9a203.firebaseio.com/");
            final DataSnapshot dataSnapshotIN = dataSnapshot;
            final Post newPostIN = newPost;

            //DatabaseReference usersRef = ref.child("users").child("friends");

            ValueEventListener usersEventListener = ref.child("friends").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    friendsSnapshotValue = snapshot.getValue().toString();

                    try {
                        if (snapshot.getValue().toString().contains("{email=" + postAuthor + ", uid=" + getUid())) {
                            //postsAdapter.addPost(newPost, dataSnapshot.getKey());
                            postsAdapter.addPost(newPostIN, dataSnapshotIN.getKey());
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }

    }


}
