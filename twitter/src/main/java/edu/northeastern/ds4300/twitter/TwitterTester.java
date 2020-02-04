package edu.northeastern.ds4300.twitter;

import java.util.Date;

public class TwitterTester {

    private static TwitterAPI api = new RedisTwitterAPI();

    public static void main(String[] args) {
        api.reset();

        BufferedReader csvReader = new BufferedReader(new FileReader("new.csv"));

        while((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            api.addFollower(data[0], data[1]);
        }

        csvReader.close();

        tweetTest();
        timelineTest();
    }

    private static void tweetTest() throws IOException, SQLException {
        BufferedReader csvReader2 = new BufferedReader(new FileReader("new2.csv"));

        System.out.println("Starting tweet test.");
        long start = System.currentTimeMillis();

        while((row = csvReader2.readLine()) != null) {
            String[] data = row.split(",");
            Tweet t = new Tweet(Integer.parseInt(data[1]), data[2], data[3]);
            api.postTweet(t, true);
        }


        long end = System.currentTimeMillis();
        System.out.println("Seconds taken to post: "+(end-start)/1000.0);

        csvReader2.close();
    }

    private static void timelineTest() {

        // Start a timer
        System.out.println("Starting timeline test.");
        Instant start = Instant.now();

        // Pick random user's timeline
        for (int i = 0; i < 1000; ++i) {
            api.getTimeline((int)(Math.random() * 1000));
        }

        // Print a "done" message / close timer
        Instant end = Instant.now();
        System.out.println("Finished timeline test. Duration: " + Duration.between(start, end));
    }

}
