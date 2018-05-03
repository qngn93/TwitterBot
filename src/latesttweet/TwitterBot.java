package latesttweet;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.*;

import java.util.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * @author Quoc Nguyen
 * This Twitter Bot imports twitter4j library to communicate with Twitter's API
 * Enables user to search for trends in a specific area
 */
public class TwitterBot 
{
	//constant for number of tweets displayed
	public static final int TOTAL_NUM_TWEET = 10;
	
	//gets only tweets since  date range
	public static final String DATE_RANGE = "2018-05-02";
	
	//location set Houston, TX with 20 mile radius
	public static final double LONGITUDE = 29.7633;
	public static final double LATITUDE = -95.3633;
	public static final double RADIUS = 20;
	
	/**
	 * Prompts user for keyword or phrase
	 * Sends keyword/phrase to be filtered & displayed
	 */
	public static void main(String [] args) throws TwitterException, IOException 
	{

		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a keyword or phrase: ");
		String keyword = scan.nextLine();

		Query queryResult = filterQuery(keyword);
		displayQuery(queryResult);
	}

	/**
	 * This method narrows down queries to specific location, date and number of tweets
	 * More can be added for deeper specification
	 * @return	query back to main to be displayed
	 */
	private static Query filterQuery(String searchTerm) 
	{
		Query query = new Query(searchTerm);
		GeoLocation location = new GeoLocation(LONGITUDE, LATITUDE);
		
		query.setCount(TOTAL_NUM_TWEET);
		query.setSince(DATE_RANGE);
		query.setGeoCode(location, RADIUS, Query.MILES);
		
		return query;
	}
	
	private static void displayQuery (Query query) 
	{
		//access Twitter API using twitter4j.properties file
		Twitter twitter = TwitterFactory.getSingleton();

		try 
		{
			QueryResult result = twitter.search(query);
			int tweetCount = 0;
			System.out.println("count: " + result.getTweets().size());
			
			for(Status tweet: result.getTweets()) 
			{
				tweetCount++;
				System.out.println("Tweet #: " + tweetCount + ": @" 
						+ tweet.getUser().getScreenName() 
						+ " tweeted \"" + tweet.getText());
			} 
		}
		catch (TwitterException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println();
	}
}
