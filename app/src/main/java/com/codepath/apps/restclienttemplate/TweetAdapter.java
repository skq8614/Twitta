package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import static com.codepath.apps.restclienttemplate.models.GlideOptions.bitmapTransform;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    List<Tweet> mTweets;
    Context context;
    //pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet>tweets){
        this.mTweets = tweets;
    }

    //for each row, inflate the layout and cache references into ViewHOlder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //bind the values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //get the data according to position
        final Tweet tweet = mTweets.get(position);

        //populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTime.setText(tweet.time);
        holder.tvUser.setText(tweet.user.screenName);

        //Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);

        Glide.with(context).load(tweet.user.profileImageUrl)
                .apply(bitmapTransform(new CircleCrop()))
                .into(holder.ivProfileImage);

        final int REQUEST_CODE = 20;
        ImageButton btnReply = holder.btnReply;
        final String username = tweet.user.screenName;
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ReplyActivity.class);
                //i.putExtra("tweet", Parcels.wrap(tweet));
                i.putExtra("username", Parcels.wrap(username));
                ((Activity)context).startActivityForResult(i, REQUEST_CODE);
            }
        });
    }


    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create the ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTime;
        public EditText tvText;
        public TextView tvUser;
        public ImageButton btnReply;

        public ViewHolder(View itemView){
            super(itemView);
            //perform findViewById lookups
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvText = itemView.findViewById(R.id.tvText);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvUser = itemView.findViewById(R.id.tvUser);
            btnReply = itemView.findViewById(R.id.btnReply);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION){
                Tweet tweet = mTweets.get(position);
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("Tweet", Parcels.wrap(tweet));
                context.startActivity(i);
            }

        }
    }
}
