package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

    public String body;
    public String createdAt;
    public long id;
    public User user;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        return tweet;
    }
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();

        /*
            1. Unpacks the json object into a "Tweet"
            2. Stores body information, creation date, and user credentials
            3. User is its own subcategory that stores user's name, screen name, and profile image
            4. Everything above is stored into Tweet
            5. This Tweet is returned and added/stored into a list of tweets.
            6. The array list of tweets is populated and is then returned.
             > What is returned is a list of tweets containing relevant info about the tweet
         */
        for (int i=0;i<jsonArray.length();i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweets;
    }
}
