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
    }

    public void addFollower(String userID, String followerID) {
        String key = "follows:"+followerID;
        jedis.sadd(key, userID);
    }

    public void addFollows(String userID, String followsID)
    {
        String key = "follows:"+userID;
        jedis.sadd(key, followsID);
    }

    public List<Tweet> getTimeline(String userID) {
        // Create tweet ID holder
        List<Tweet> toReturn = new ArrayList<Tweet>();
        ArrayList<String> recentTweetIDs = new ArrayList<String>(
                Arrays.asList("-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1",
                        "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1")
        );

        List<String> follows = jedis.lrange("follows:" + userID, 0, -1);
        for (String id : follows) {
           List<String> tweetKeys = new ArrayList<String>(jedis.keys("tweets:"+id+":*"));
           for (String tweetKey : tweetKeys) {
               int lastColon = tweetKey.lastIndexOf(':');
               String tweetID = tweetKey.substring(lastColon);
               // Compare tweet ID against elements of the sorted array list
               recentTweetIDs.add(tweetID);
               Collections.sort(recentTweetIDs);
               recentTweetIDs.remove(20);
           }
        }
        for (String tweetID : recentTweetIDs) {
            List<String> tweetKeyArray = new ArrayList<String>(jedis.keys("tweets:*:"+tweetID));
            String tweetKey = tweetKeyArray.get(0);
            String tweetUserID = tweetKey.substring(tweetKey.indexOf(':'), tweetKey.lastIndexOf(':'));
            String jedisContent = jedis.get(tweetKey);
            String dateString = jedisContent.substring(0, jedisContent.indexOf(':'));
            Date tweetDate = new Date(Long.parseLong(dateString));
            String tweetContent = jedisContent.substring(jedisContent.indexOf(':'));
            Tweet toAdd = new Tweet(tweetUserID, tweetDate, tweetContent);
            toReturn.add(toAdd);
        }
        return toReturn;
    }

    public List<String> getFollowers(String userID) {  return null;  }


}
