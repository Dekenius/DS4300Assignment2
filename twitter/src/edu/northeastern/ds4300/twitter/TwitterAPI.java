package edu.northeastern.ds4300.twitter;

import java.util.List;

public interface TwitterAPI {
    public void postTweet(Tweet t, boolean broadcast);
    void addFollower(String userID, String followerID);
    List<Tweet> getTimeline(String userID);
    public List<String> getFollowers(String userID);
    public void reset();
}
