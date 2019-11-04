package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public String name;
    public String screenName;
    public String profileImageURL;

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

        return user;
    }
}
