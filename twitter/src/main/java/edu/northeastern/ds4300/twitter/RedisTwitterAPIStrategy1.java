package edu.northeastern.ds4300.twitter;

import java.util.*;
import redis.clients.jedis.*;

public class RedisTwitterAPI implements TwitterAPI {


    private Jedis jedis = new Jedis("localhost");


    public void reset() {
        jedis.flushAll();
    }


    private long getNextID()
    {
        return jedis.incr("nextTweetID");
    }


    public void postTweet(Tweet t, boolean broadcast)
    {
        String key = "tweets:"+t.getUserID()+":"+getNextID();
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
        List<Tweet> toReturn = new ArrayList<>();
        ArrayList<String> recentTweetIDs = new ArrayList<>();

        Set<String> follows = jedis.smembers("follows:" + userID);

        for (String id : follows) {

            List<String> tweetKeys = new ArrayList<>(jedis.keys("tweets:" + id + ":*"));

            for (String tweetKey : tweetKeys) {
                int lastColon = tweetKey.lastIndexOf(':');
                String tweetID = tweetKey.substring(lastColon+1);
                // Compare tweet ID against elements of the sorted array list
                recentTweetIDs.add(tweetID);
                Collections.sort(recentTweetIDs, Collections.reverseOrder());
                if (recentTweetIDs.size() > 20) {
                    recentTweetIDs.subList(20, recentTweetIDs.size()).clear();
                }
            }
        }

        try {
            for (String tweetID : recentTweetIDs) {
                if(tweetID.equals("-1")) {
                    continue;
                }
                List<String> tweetKeyArray = new ArrayList<>(jedis.keys("tweets:*:" + tweetID));
                String tweetKey = tweetKeyArray.get(0);
                String tweetUserID = tweetKey.substring(tweetKey.indexOf(':')+1, tweetKey.lastIndexOf(':'));
                String jedisContent = jedis.get(tweetKey);
                String dateString = jedisContent.substring(0, jedisContent.indexOf(':'));
                Date tweetDate = new Date(Long.parseLong(dateString));
                String tweetContent = jedisContent.substring(jedisContent.indexOf(':')+1);
                Tweet toAdd = new Tweet(tweetUserID, tweetDate, tweetContent);
                toReturn.add(toAdd);
            }
        } catch (redis.clients.jedis.exceptions.JedisConnectionException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public List<String> getFollowers(String userID) {  return null;  }

}
