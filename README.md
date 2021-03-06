# DS4300 Assignment 2
## Contributions: 
Griffin Milas: Stategy 1

Dan Krasnonosenkikh: Strategy 1

Matthew Wojtowicz: Strategy 2

## Adustments: 
We changed our follows/follower generator to create about 5 followers per user as compared to ours for assignment 1 which only had about 1 follower per user. This allows any given timeline to contain multiple user's tweets which helps to better reflect the actual usage and performance of getTimeline.

## Methods
We implemented two different strategies. For the first strategy, whenever a tweet is posted it is simply added to the key-value table for the user's tweets. Whenever a timeline is requested, that user's timeline is generated in real time by accessing their follower's tweets and returning the 20 most recent ones. For the second strategy, whenever a tweet is posted it is added to the key-value pair for the user's tweet as well as added to the timeline of anyone who follows that user. Whenever a timeline is requested, this allows for the 20 most recent tweets in a user's timeline to be quickly fetched. 

## Results

### Strategy 1

#### Tweet Posting
Rate: 10,595 Tweets/Second

### Timeline Fetching
Rate: 0.18 Timelines/Second

### Strategy 2

#### Tweet Posting
Rate: 847 Tweets/Second

### Timeline Fetching
Rate: 255 Timelines/Second


## Analysis
### Computer Specs
CPU: i7 7700HQ. 4 cores at 2.80GHz
RAM: 16gb 
Storage: 512gb SSD. Sequential read up to 3000 MB/s and sequential write up to 1150 MB/s

### How do the two strategies compare? How does Redis compare with MySQL?
Strategy one was over 10 times faster than strategy 2 at posting tweets, but it was 1,000 times slower than strategy 2 at fetching timelines. Since Strategy 2 builds/updates a user's timeline as soon as a tweet is posted, the timeline fetches are understandably much faster than strategy 1 which has to build a user's timeline from scratch each time it is requested. This makes Strategy 2 much better performance wise as the number of tweets and user's grows. Compared to our MySQL implmentation which posted 241 Tweets/Second and fetched tweets at 4.78 Timelines/Second, Strategy 2 of Redis was signficantly faster in both metrics and is clearly better suited for this application than MySQL. 
