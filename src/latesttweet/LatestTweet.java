package latesttweet;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.*;
import twitter4j.conf.*;

import java.util.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


public class LatestTweet {
	public static PrintStream consolePrint;
	
	public static void main(String[] args) throws TwitterException, IOException {
		TweetBot twitter = new TweetBot(consolePrint);
		//consolePrint = System.out;
		
		//String message = "Testing from Twitter Bot!";
		//twitter.tweetOut(message);
		//@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a keyword ");
		String keyword = scan.next();
//		while(!"done".equals(twitterHandle)){
//			twitter.queryHandle(twitterHandle);
//			System.out.print("Enter a Twitter handle: ");
//			twitterHandle = scan.next();
//		}
		
		twitter.saQuery(keyword);
		 
	}
}

	class TweetBot
	{
		private Twitter twitter;
		private PrintStream consolePrint;
		private List<Status> statuses;
		
		public TweetBot(PrintStream console){
			twitter = TwitterFactory.getSingleton();
			consolePrint = console;
			statuses = new ArrayList<Status>();
		}
	
		public void tweetOut(String message) throws TwitterException {
			Status status = twitter.updateStatus(message);
			System.out.println("Successfully updated the status to[" + status.getText() + "].");
		}
		
		public void queryHandle(String handle) throws TwitterException, IOException
		{
			statuses.clear();
			fetchTweets(handle);
			int counter = statuses.size();
			while(counter > 0){
				counter--;
				System.out.println("Tweet #" + counter + " : " + statuses.get(counter).getText());
			}
		}
		
		private void fetchTweets(String handle) throws TwitterException, IOException {
			Paging page = new Paging(1, 100);
			int p = 1;
			while(p<=10){
				page.setPage(p);
				statuses.addAll(twitter.getUserTimeline(handle, page));
				p++;
			}
		}
		
		public void saQuery (String searchTerm){
			Query query = new Query(searchTerm);
			query.setCount(100);
			query.setGeoCode(new GeoLocation(29.7633, -95.3633), 20, Query.MILES);
			//query.setSince("2018-04-30");
			
			try
			{
				QueryResult result = twitter.search(query);
				int counter = 0;
				System.out.println("count: " + result.getTweets().size());
				for(Status tweet: result.getTweets())
				{
					counter++;
					System.out.println("Tweet #: " + counter + ": @" + tweet.getUser().getName() +
							" tweeted \"" + tweet.getText());
				} 
			}
			catch (TwitterException e)
			{
				e.printStackTrace();
			}
			
			System.out.println();
		}
	
	
}
