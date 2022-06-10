package com.codepath.apps.restclienttemplate.models;

import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.os.Build;
import android.text.format.DateUtils;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public String imageURL;
    public  boolean hasPicture;
    public String time;
    public int likedAmounts;
    public int retweetCount;
    public int replyCount;

    public Tweet(){}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.retweetCount = jsonObject.getInt("retweet_count");

        // I put the value of 25, as I do not have a Premium Twitter API account to actually access this
        tweet.replyCount = 25;
        tweet.likedAmounts = (int) jsonObject.getInt("favorite_count");
        tweet.time = tweet.getRelativeTimeAgo(tweet.createdAt);

        JSONObject entities = jsonObject.getJSONObject("entities");
        if(entities.has("media")) {
            tweet.hasPicture = true;
            JSONArray jsonArray = entities.getJSONArray("media");
            JSONObject picture = jsonArray.getJSONObject(0);
            tweet.imageURL = picture.getString("media_url_https");
        }
        else {
            tweet.hasPicture = false;
            tweet.imageURL="";
        }

        return tweet;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();

        for(int i = 0;i< jsonArray.length();i++)
        {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweets;
    }


}
