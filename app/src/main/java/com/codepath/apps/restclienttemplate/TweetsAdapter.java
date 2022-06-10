package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;


    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }
    //pass in Context and list of tweets

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        ImageView twPic;
        TextView twTime;
        TextView twLikes;
        ImageView twPicture;
        TextView twReply;
        TextView twRetweet;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfile);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            twPic = itemView.findViewById(R.id.twPicture);
            twTime = itemView.findViewById(R.id.twTime);
            twLikes = itemView.findViewById(R.id.twLike);
            twPicture = itemView.findViewById(R.id.twLikePicture);
            twReply = itemView.findViewById(R.id.twComment);
            twRetweet  = itemView.findViewById(R.id.tvRetweet);
           // twReply  = itemView.findViewById(R.id.twReteet);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            Glide.with(context).load(tweet.user.publicImageURL).into(ivProfileImage);
            twReply.setText("25");
            twTime.setText(tweet.time);
            twLikes.setText(Integer.toString(tweet.likedAmounts));
            twRetweet.setText(Integer.toString(tweet.retweetCount));

            if(tweet.hasPicture)
            {
                twPic.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.imageURL).into(twPic);
            }
            else {
                twPic.setVisibility(View.GONE);
            }
        }


    }
}
