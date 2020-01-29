package edu.northeastern.ds4300.twitter;

import java.util.Date;
import redis.clients.jedis.*;

public class TwitterTester {

    private static TwitterAPI api = new RedisTwitterAPI();

    public static void main(String[] args)
    {
        api.reset();
        api.addFollower("5", "6");
        api.addFollower("5", "7");
        api.addFollower("5", "8");
        api.addFollower("5", "9");
        api.addFollower("5", "10");
        api.addFollower("5", "11");
        api.addFollower("5", "12");
        api.addFollower("5", "13");
        api.addFollower("5", "14");
        api.addFollower("5", "15");


        long start = System.currentTimeMillis();
        for (int i=0; i<10; i++) {
            if (i % 10000 == 0) System.out.println(i);
            Tweet t = new Tweet("5", new Date(), "NoSQL is fun");
            api.postTweet(t, true);
        }
        long end = System.currentTimeMillis();
        System.out.println("Seconds: "+(end-start)/1000.0);

    }

}
