package aschantz.restaurantapp;

import android.content.Context;
import android.media.Rating;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import aschantz.restaurantapp.model.Post;

/**
 * Created by aschantz on 12/2/16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvPlace;
        public TextView tvTitle;
        public TextView tvBody;
        public Button btnDelete;
        public RatingBar userRatingBar;
        public RatingBar googleRatingBar;
        public TextView priceRange;
        public ImageView resAccepted;
        public ImageView veg;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvPlace = (TextView) itemView.findViewById(R.id.tvPlace);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            userRatingBar = (RatingBar) itemView.findViewById(R.id.userRatingBar);
            googleRatingBar = (RatingBar) itemView.findViewById(R.id.googleRatingBar);
            priceRange = (TextView) itemView.findViewById(R.id.priceRange);
            resAccepted = (ImageView) itemView.findViewById(R.id.resAccepted);
            veg = (ImageView) itemView.findViewById(R.id.veg);
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
        //postList.add(new Post("1", "Austor", "DemoTitle", "Body"));

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
//        try {
//            Log.d("onBind print ", "testing" + tmpPost.getGoogleRating());
//            Log.d("googelRating alone", tmpPost.getGoogleRating());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        viewHolder.tvAuthor.setText(tmpPost.getAuthor());
        viewHolder.tvPlace.setText(tmpPost.getPlace());
        viewHolder.tvTitle.setText(tmpPost.getTitle());
        viewHolder.tvBody.setText(tmpPost.getBody());
        viewHolder.priceRange.setText(tmpPost.getPriceRange());
        viewHolder.btnDelete.setVisibility(View.GONE);
        try {
            if (tmpPost.getResAccepted()) {
                viewHolder.resAccepted.setImageResource(R.drawable.vegtemp);
            }
        } catch (Exception e) {

        }

        //viewHolder.userRatingBar.setRating(Float.valueOf("0.0"));
//        try {
//            viewHolder.userRatingBar.setRating(Float.valueOf(tmpPost.getUserRating()));
//        }catch (Exception e) {
//            viewHolder.userRatingBar.setRating(0f); // 0f means 0 but a float value, it is faster than parsing a string „0.0” to float.
//        }
        //viewHolder.googleRatingBar.setRating(Float.valueOf(Float.valueOf("4.7")));
        try {
            viewHolder.userRatingBar.setRating(Float.valueOf(tmpPost.getUserRating()));
            viewHolder.googleRatingBar.setRating(Float.valueOf(tmpPost.getGoogleRating()));
        }catch (Exception e) {
            viewHolder.googleRatingBar.setRating(0f); // 0f means 0 but a float value, it is faster than parsing a string „0.0” to float.
        }


        //to make sure you only can delete your own posts
        //if (uId.equals(tmpPost.getUid())) {
        //    then display delete button and set onclick listener for it
        // make sure you also remove from the adapter but do it in PostsActivity
        //and use onChildRemoved and remove from adapter HERE
        if(uId.equals(tmpPost.getUid())) {
            viewHolder.btnDelete.setVisibility(View.VISIBLE);
            //display delete button and set onclick listener for it
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removePost(position);
                }
            });

        }

        setAnimation(viewHolder.itemView, position);
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

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
