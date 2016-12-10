package aschantz.restaurantapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import aschantz.restaurantapp.model.Friend;


/**
 * Created by aschantz on 12/10/16.
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserId;
        public TextView tvEmail;
        public Button btnDelete;
        public String author;


        public ViewHolder(View itemView) {
            super(itemView);
            tvUserId = (TextView) itemView.findViewById(R.id.tvUserId);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

        }


    }

    private Context context;
    private List<Friend> friendList;
    private List<String> friendKeys;
    private String uId;
    private int lastPosition = -1;
    private DatabaseReference friendRef;

    public FriendsAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;
        this.friendList = new ArrayList<Friend>();
        this.friendKeys = new ArrayList<String>();

        //dummy item
        friendList.add(new Friend("1", "default"));

        friendRef = FirebaseDatabase.getInstance().getReference("friends");



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_friend, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Friend tmpFriend = friendList.get(position);

        //viewHolder.tvUserId.setText(tmpFriend.getUserId());
        try {
            viewHolder.tvEmail.setText(tmpFriend.getEmail());
        }catch (Exception e) {

        }


        if (uId.equals(tmpFriend.getUid())) {
            viewHolder.btnDelete.setVisibility(View.VISIBLE);
            //display delete button and set onclick listener for it
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFriend(position);
                }
            });

        }

        setAnimation(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void addFriend(Friend place, String key) {
        friendList.add(place);
        friendKeys.add(key);
        notifyDataSetChanged();
    }

    public void removeFriend(int index) {
        friendRef.child(friendKeys.get(index)).removeValue();
        friendList.remove(index);
        friendKeys.remove(index);
        notifyItemRemoved(index);
    }



    public void removeFriendByKey(String key) {
        int index = friendKeys.indexOf(key);
        if (index != -1) {
            friendList.remove(index);
            friendKeys.remove(index);
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
