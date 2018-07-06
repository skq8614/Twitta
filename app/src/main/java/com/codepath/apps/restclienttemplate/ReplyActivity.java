package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ReplyActivity extends AppCompatActivity {

    TwitterClient client;
    View view;
    EditText tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        client = TwitterApp.getRestClient(this);
        tvText = findViewById(R.id.tvText);
        setTitle("Twitta - Reply to this tweet");
        setText();
    }

    public void setText(){
        String username = Parcels.unwrap(getIntent().getParcelableExtra("username"));
        String intro = "@" + username;
        tvText.setText(intro);
        tvText.setSelection(intro.length());
    }

    public void onTweet(View view){
        EditText simpleEditText = (EditText) findViewById(R.id.tvText);
        String strValue = simpleEditText.getText().toString();
        client.sendTweet(strValue, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent i = new Intent();
                    i.putExtra("tweet", Parcels.wrap(tweet));
                    setResult(RESULT_OK, i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }
        });

    }
}
