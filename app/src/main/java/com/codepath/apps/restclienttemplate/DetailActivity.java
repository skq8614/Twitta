package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import static com.codepath.apps.restclienttemplate.models.GlideOptions.bitmapTransform;

public class DetailActivity extends AppCompatActivity {

    public ImageView ivProfileImage;
    public TextView tvUsername;
    public TextView tvBody;
    public TextView tvTime;
    public TextView tvUser;
    public ImageButton btnReply;
    ImageView itemView;

    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Twitta");

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvUsername = findViewById(R.id.tvUserName);
        tvBody = findViewById(R.id.tvBody);
        tvTime = findViewById(R.id.tvTime);
        tvUser = findViewById(R.id.tvUser);
        btnReply = findViewById(R.id.btnReply);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));
        tvUsername.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvTime.setText(tweet.createdAt);
        tvUser.setText("@" + tweet.user.screenName);

        Glide.with(this).load(tweet.user.profileImageUrl)
                .apply(bitmapTransform(new CircleCrop()))
                .into(ivProfileImage);

    }
}
