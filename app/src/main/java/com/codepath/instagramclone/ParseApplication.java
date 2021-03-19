package com.codepath.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application  {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("mo32t29ybI57dZvwGjZDEw2beItBYe9iZn2mOQ5m")
                .clientKey("IJD3RnSXQUB7Ok5uZNId2qxTgQU9LDkJ3SC7Nbs1")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
