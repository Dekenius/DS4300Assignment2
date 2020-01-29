package edu.northeastern.ds4300.twitter;

import java.util.List;

public class RedisTwitterAPI implements TwitterAPI {




    public void reset() { }

    public void postTweet(Tweet t, boolean broadcast) { }

    public void addFollower(String userID, String followerID) { }

    public List<Tweet> getTimeline(String userID) {
        return null;
    }

    public List<String> getFollowers(String userID) {  return null;  }

    public void addToTimeline(Tweet t, String userID) { }
}
