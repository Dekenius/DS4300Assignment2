package edu.northeastern.ds4300.twitter;

import java.util.Date;

public class TwitterTester {

    private static TwitterAPI api = new RedisTwitterAPI();

    public static void main(String[] args)
    {
        Tweet t = new Tweet("5", new Date(), "NoSQL is fun");
        api.postTweet(t, true);
    }

}
