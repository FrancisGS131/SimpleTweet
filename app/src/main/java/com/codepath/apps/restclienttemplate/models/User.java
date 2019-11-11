package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {
    //By doing @ColumnInfo, you're storing the variables into different columns at a data table
    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String screenName;

    @ColumnInfo
    public String profileImageURL;

    // Empty constructor needed for the Parceler library
    public User() {}

    public static User fromJson(JSONObject jsonObject) throws JSONException {

        /*
            1. Json is unpacked into a subcategory called User
            2. In the Json file, User is a list of strings. User itself is inside a list of info in the Json
            3. Unpack the user's name, screen name, and profile image into the User subcategory
            4. Return User and go back to Tweet class
         */

        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageURL = jsonObject.getString("profile_image_url_https");
        user.id = jsonObject.getLong("id");

        return user;
    }

    public static List<User> fromJsonTweetArray(List<Tweet> tweetsFromNetwork) {
        List<User> users = new ArrayList<>();
        for(int i=0;i<tweetsFromNetwork.size();i++){
            users.add(tweetsFromNetwork.get(i).user);
        }

        return users;
    }
}
