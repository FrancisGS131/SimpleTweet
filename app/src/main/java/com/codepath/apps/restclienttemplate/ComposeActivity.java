package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    //Convention to have tag as the name of the class!
    public static final String TAG = "ComposeActivity";
    public static final int MAX_TWEET_LENGTH = 140;

    EditText etCompose;
    Button btnTweet;

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        //Initializing elements
        client = TwitterApp.getRestClient(this);
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);


        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Fires right before text is changing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Fires right after the text has changed
                String tweetContent = editable.toString();
                if(tweetContent.length() > MAX_TWEET_LENGTH) {
                    btnTweet.setClickable(false);
                }
                else if(tweetContent.length() == 0){
                    Toast.makeText(ComposeActivity.this,"Sorry, your tweet cannot be empty",Toast.LENGTH_SHORT).show();
                    btnTweet.setClickable(false);
                }
                else
                    btnTweet.setClickable(true);
            }
        });

        // Adding click listener to button. When button is tapped, want to publish text to Twitter. See steps below
        // Set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Before publishing, need to filter out message for several requirements
                    - Is the tweet too long? If it is, tell the user to shorten their tweet!
                    - Is the tweet empty?

                    Conditions:
                    if(tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this,"Sorry, your tweet cannot be empty",Toast.LENGTH_LONG).show();
                    return; }

                    if(tweetContent.length() > MAX_TWEET_LENGTH){
                        Toast.makeText(ComposeActivity.this,"Sorry, your tweet is too long!",Toast.LENGTH_LONG).show();
                        return; }

                    > Now handled in a addTextListener that disables the button accordingly
                 */

                String tweetContent = etCompose.getText().toString();
                //For debugging purposes
                Toast.makeText(ComposeActivity.this,tweetContent,Toast.LENGTH_LONG).show();

                //Make an API call to Twitter to publish the tweet
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"onSuccess to publish tweet");

                        /*
                            What we expect to get back is the JsonObject, and the jsonObject is a tweet model
                            > Thus, we can use the same parsing for a JsonObject to a Tweet.
                              As a reminder, fromJson unpacks the jsonObject into a Tweet package that contains all the minute
                              details about the tweet (body, creation date, id, user details)
                         */

                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG,"Published tweet says: "+tweet.body);

                            // Prepare data intent
                            Intent intent = new Intent();
                            // Pass relevant data back as a result
                            intent.putExtra("tweet", Parcels.wrap(tweet));

                            // Activity finished ok, return the data
                            setResult(RESULT_OK,intent); // set result code and bundle data for response
                            // closes the activity, pass data to parent
                            finish();

                        } catch (JSONException e) {
                            Log.e(TAG,"Failed to publish tweet!",e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"onFailure to publish tweet",throwable);
                    }
                });
            }
        });

    }
}
