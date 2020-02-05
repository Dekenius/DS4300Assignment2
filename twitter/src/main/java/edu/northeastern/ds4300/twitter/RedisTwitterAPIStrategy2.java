package edu.northeastern.ds4300.twitter;

import java.util.*;
import redis.clients.jedis.*;

public class RedisTwitterAPI implements TwitterAPI {


    Jedis jedis = new Jedis("localhost");


    public void reset() {
        jedis.flushAll();
    }


    private long getNextID()
    {
      return jedis.incr("nextTweetID");
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
               addToTimeline(key, f);

        }
    }

    //receives tweet_key and the followers user_id
    private void addToTimeline(String tweet_key, String userID)
    {
        String timeline_key = "timeline:"+userID;
        jedis.lpush(timeline_key, tweet_key);
    }



    public void addFollower(String userID, String followerID)
    {
        String key = "followers:"+userID;
        jedis.sadd(key, followerID);
    }

    public List<Tweet> getTimeline(String userID) {
        ArrayList<String> tweet_list = new ArrayList<>();

        for (String tweet_key : jedis.lrange("timeline:"+userID, 0, 19))
            String tweet_value = jedis.get(tweet_key);

            //some redex to extract the user_id from the tweet key
            Pattern p = Pattern.compile(" (?<=:)(?<x>.*?)(?=:) ");
            Matcher m = p.matcher(tweet_value);

            MatchResult result = m.toMatchResult();

            String creator_userID = result.toString();
            System.out.println("1 " + creator_userID);

            int i = tweet_value.indexOf(':');
            Date tweet_date = new Date(Long.parseLong(tweet_value.substring(0, i)) * 1000);
            System.out.println("2 " + tweet_date);
            System.out.println("3 " + tweet_value.substring(i));

            Tweet timeline_tweet = new Tweet(creator_userID, tweet_date, tweet_value.substring(i));
            tweet_list.add(timeline_tweet);
        }


        return tweet_list;
    }

    public List<String> getFollowers(String userID) {

        return jedis.smembers("followers:"+userID);
    }
}
