package edu.northeastern.ds4300.twitter;

import java.util.Date;

public class Tweet {

    private String userID;
    private Date creationDate;
    private String tweet;

    public Tweet(String userID, Date creationDate, String tweet) {
        this.userID = userID;
        this.creationDate = creationDate;
        this.tweet = tweet;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String toString() { return creationDate.getTime()+":"+tweet; }
}
