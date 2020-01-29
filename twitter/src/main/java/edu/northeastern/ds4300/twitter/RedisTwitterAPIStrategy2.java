package edu.northeastern.ds4300.twitter;

import java.util.*;
import redis.clients.jedis.*;

public class RedisTwitterAPI implements TwitterAPI {


    Jedis jedis = new Jedis("localhost");


    public void reset() {
        jedis.flushAll();
    }


    public long getNextID()
    {
        long next = jedis.incr("nextTweetID");
        return next;
    }


    public void postTweet(Tweet t, boolean broadcast)
    {
        String key = "tweet:"+t.getUserID()+":"+getNextID();
        String value = t.toString();
        jedis.set(key,value);

        if (broadcast)
        {
            Set<String> followers = jedis.smembers("followers:"+t.getUserID());
            for (String f : followers)
               addToTimeline(t, f);

        }
    }

    public void addToTimeline(Tweet t, String userID)
    {
        String key = "timeline:"+userID;
        String value = t.toString();
        jedis.lpush(key, value);
    }



    public void addFollower(String userID, String followerID)
    {
        String key = "followers:"+userID;
        jedis.sadd(key, followerID);
    }

    public List<Tweet> getTimeline(String userID) {
        return null;
    }

    public List<String> getFollowers(String userID) {  return null;  }


}
