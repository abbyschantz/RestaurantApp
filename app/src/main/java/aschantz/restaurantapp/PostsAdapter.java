package aschantz.restaurantapp;

import android.content.Context;
import android.media.Rating;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import aschantz.restaurantapp.model.Friend;
import aschantz.restaurantapp.model.Post;

/**
 * Created by aschantz on 12/2/16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView card_view;
        public TextView tvAuthor;
        public TextView tvPlace;
        public TextView tvTitle;
        public TextView tvBody;
        public TextView dishes;
        public Button btnDelete;
        public RatingBar userRatingBar;
        public RatingBar googleRatingBar;
        public TextView priceRange;
        public ImageView res;
        public ImageView veg;
        //        public TextView veg;
        public ImageView gluten;
        public ImageView wait;
        public ImageView alc;

        public ViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvPlace = (TextView) itemView.findViewById(R.id.tvPlace);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            userRatingBar = (RatingBar) itemView.findViewById(R.id.userRatingBar);
            googleRatingBar = (RatingBar) itemView.findViewById(R.id.googleRatingBar);
            priceRange = (TextView) itemView.findViewById(R.id.priceRange);
            dishes = (TextView) itemView.findViewById(R.id.dishes);
            res = (ImageView) itemView.findViewById(R.id.res);
            veg = (ImageView) itemView.findViewById(R.id.veg);
//            veg = (TextView) itemView.findViewById(R.id.veg);
            gluten = (ImageView) itemView.findViewById(R.id.gluten);
            wait = (ImageView) itemView.findViewById(R.id.wait);
            alc = (ImageView) itemView.findViewById(R.id.alc);
        }
    }

    private Context context;
    private List<Post> postList;
    private List<String> postKeys;
    private String uId;
    private int lastPosition = -1;
    private DatabaseReference postsRef;

    public PostsAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;
        this.postList = new ArrayList<Post>();
        this.postKeys = new ArrayList<String>();

        //dummy item
        postList.add(new Post("1", "defaultAuthor", "defaultPlace",
                "defaultTitle", "defaultBody", "defaultRating", "defaultGrating", "defaultPrice", "defaultDishes",
                "defaultRes", "defaultVeg", "defaultGluten", "defaultWait",  "defaultAlc"));

        postsRef = FirebaseDatabase.getInstance().getReference("posts");


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_post, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Post tmpPost = postList.get(position);

        viewHolder.tvAuthor.setText(tmpPost.getAuthor());
        viewHolder.tvPlace.setText(tmpPost.getPlace());
        viewHolder.tvTitle.setText(tmpPost.getTitle());
        viewHolder.tvBody.setText(tmpPost.getBody());
        viewHolder.priceRange.setText(tmpPost.getPriceRange());
        //viewHolder.res.setText(tmpPost.getRes());
        //viewHolder.gluten.setText(tmpPost.getGluten());
        //viewHolder.wait.setText(tmpPost.getWait());
        //viewHolder.alc.setText(tmpPost.getAlc());
//        viewHolder.veg.setText(tmpPost.getVeg());


        try {
            if (tmpPost.getVeg().equals("None")) {
                //viewHolder.veg.setImageResource(R.drawable.vegtemp);
                viewHolder.veg.setImageResource(R.mipmap.veg_red);
            } else if (tmpPost.getVeg().equals("A Few")) {
                viewHolder.veg.setImageResource(R.mipmap.veg_orange);
            } else if (tmpPost.getVeg().equals("Lots")) {
                viewHolder.veg.setImageResource(R.mipmap.veg_yellow);
            } else if (tmpPost.getVeg().equals("Only")) {
                viewHolder.veg.setImageResource(R.mipmap.veg_green);
            } else {
                viewHolder.veg.setImageResource(R.mipmap.veg_unknown);
            }

        } catch (Exception e) {

        }

        try {
            if (tmpPost.getGluten().equals("None")) {
                //viewHolder.veg.setImageResource(R.drawable.vegtemp);
                viewHolder.gluten.setImageResource(R.mipmap.gf_red);
            } else if (tmpPost.getGluten().equals("A Few")) {
                viewHolder.gluten.setImageResource(R.mipmap.gf_orange);
            } else if (tmpPost.getGluten().equals("Lots")) {
                viewHolder.gluten.setImageResource(R.mipmap.gf_yellow);
            } else if (tmpPost.getGluten().equals("Only")) {
                viewHolder.gluten.setImageResource(R.mipmap.gf_green);
            } else {
                viewHolder.gluten.setImageResource(R.mipmap.gf_unknown);
            }

        } catch (Exception e) {

        }

        try {
            if (tmpPost.getAlc().equals("no_alcohol")) {
                //viewHolder.veg.setImageResource(R.drawable.vegtemp);
                viewHolder.alc.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getAlc().equals("soft_alcohol")) {
                viewHolder.alc.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getAlc().equals("hard_alcohol")) {
                viewHolder.alc.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getAlc().equals("all_alcohol")) {
                viewHolder.alc.setImageResource(R.mipmap.ic_launcher);
            } else {
                viewHolder.alc.setImageResource(R.mipmap.ic_launcher);
            }

        } catch (Exception e) {

        }

        try {
            if (tmpPost.getWait().equals("0-10")) {
                //viewHolder.veg.setImageResource(R.drawable.vegtemp);
                viewHolder.wait.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getWait().equals("10-30")) {
                viewHolder.wait.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getWait().equals("30-60")) {
                viewHolder.wait.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getWait().equals("60+")) {
                viewHolder.wait.setImageResource(R.mipmap.ic_launcher);
            } else {
                viewHolder.wait.setImageResource(R.mipmap.ic_launcher);
            }

        } catch (Exception e) {

        }

        try {
            if (tmpPost.getRes().equals("no")) {
                //viewHolder.veg.setImageResource(R.drawable.vegtemp);
                viewHolder.res.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getRes().equals("yes")) {
                viewHolder.res.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getRes().equals("I would")) {
                viewHolder.res.setImageResource(R.mipmap.ic_launcher);
            } else if (tmpPost.getRes().equals("Do, but hard")) {
                viewHolder.res.setImageResource(R.mipmap.ic_launcher);
            } else {
                viewHolder.res.setImageResource(R.mipmap.ic_launcher);
            }

        } catch (Exception e) {

        }


        viewHolder.btnDelete.setVisibility(View.GONE);
        try {
            viewHolder.userRatingBar.setRating(Float.valueOf(tmpPost.getUserRating()));
            viewHolder.googleRatingBar.setRating(Float.valueOf(tmpPost.getGoogleRating()));
        } catch (Exception e) {
            viewHolder.googleRatingBar.setRating(0f); // 0f means 0 but a float value, it is faster than parsing a string „0.0” to float.
        }
        try {
            viewHolder.dishes.setText(tmpPost.getDishes());
        } catch (Exception e) {
            viewHolder.dishes.setText("N/A");
        }


        //to make sure you only can delete your own posts
        //if (uId.equals(tmpPost.getUid())) {
        //    then display delete button and set onclick listener for it
        // make sure you also remove from the adapter but do it in PostsActivity
        //and use onChildRemoved and remove from adapter HERE
        if (uId.equals(tmpPost.getUid())) {
            viewHolder.btnDelete.setVisibility(View.VISIBLE);
            //display delete button and set onclick listener for it
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removePost(position);
                }
            });

        }
//        if (uId.equals(tmpPost.getUid())) {
//            postList.remove(position);
//            postKeys.remove(position);
//            notifyItemRemoved(position);
//        }

        setAnimation(viewHolder.itemView, position);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReferenceFromUrl("https://restaurantsapp-9a203.firebaseio.com/");


            //DatabaseReference usersRef = ref.child("users").child("friends");

            ValueEventListener usersEventListener = ref.child("friends").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //friendObject = snapshot.getValue();


                    //for (int i = 0; i < friendObject.toString().length(); i++ ){
                    //System.out.println(friendObject.toString().charAt(i));

                    //}
                    String author = viewHolder.tvAuthor.getText().toString();
                    System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                    System.out.println(uId);
                    try {
                        if (!snapshot.getValue().toString().contains("{email=" + author + ", uid=" + uId)) {
                            System.out.println("HAPPYHAPPYJOYJOY");
                            viewHolder.card_view.setVisibility(View.GONE);
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

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addPost(Post place, String key) {
        postList.add(place);
        postKeys.add(key);
        notifyDataSetChanged();
    }

    public void removePost(int index) {
        postsRef.child(postKeys.get(index)).removeValue();
        postList.remove(index);
        postKeys.remove(index);
        notifyItemRemoved(index);
    }



    public void removePostByKey(String key) {
        int index = postKeys.indexOf(key);
        if (index != -1) {
            postList.remove(index);
            postKeys.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void hidePost(int index) {
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


}