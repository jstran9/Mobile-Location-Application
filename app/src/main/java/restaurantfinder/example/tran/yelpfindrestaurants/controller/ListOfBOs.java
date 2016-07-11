package restaurantfinder.example.tran.yelpfindrestaurants.controller;

import static javax.measure.unit.NonSI.MILE;
import static javax.measure.unit.SI.METER;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.measure.Measure;
import javax.measure.converter.UnitConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import restaurantfinder.example.tran.yelpfindrestaurants.R;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessExtraInfo;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjects;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjectsDBHelper;
import restaurantfinder.example.tran.yelpfindrestaurants.model.Yelp;

/**
 * @author Todd
 * This class holds restaurant objects
 */
public class ListOfBOs {

	/**
	 * the sort mode for the search call.
	 */
	private String mSortValue = "0";

	/**
	 * list of restaurant objects
	 */
	private List<BusinessObjects> mBusinessObjectsList;

	/**
	 * the number of results to be given back to the user
	 */
	private Integer mNumResults;
	
	/**
	 * the key words, i.e. pizza if the user is looking for places that serve pizza.
	 */
	private String mUserSearchTerm;

	/**
	 * object to make the yelp method calls
	 */
	private Yelp mYelp;

	/**
	 * A database object to help add to and read from the database.
	 */
	private BusinessObjectsDBHelper dbObject;
	
	/**
	 * initialize an empty list of restaurant objects
	 */
	public ListOfBOs(Context context)
	{
		this.mBusinessObjectsList = new ArrayList<>();
		Resources accessResources = context.getResources();
		mYelp = new Yelp(accessResources.getString(R.string.YelpConsumerKey), accessResources.getString(R.string.YelpConsumerSecret),
				accessResources.getString(R.string.YelpToken), accessResources.getString(R.string.YelpTokenSecret));
		dbObject = new BusinessObjectsDBHelper(context);
		mSortValue = "0"; // by default sort mode 0.
	}
	
	/**
	 * initializes a list of restaurant objects.
	 * @param businessImage The image of the business.
	 * @param businessName The business's name.
	 * @param distance The distance from the business.
	 * @param businessType The number of reviews.
	 * @param businessStatus The status of the restaurant.
	 * @param businessLat The latitude (coordinate) of the business.
	 * @param businessLng The longitude (coordinate) of the business.
	 */
	public void addToListAndDB(Bitmap businessImage, String businessName, double distance, String businessType, String businessStatus, String businessLat, String businessLng)
	{
		BusinessObjects r1 = new BusinessObjects();
		r1.setBusinessImage(businessImage);
		r1.setBusinessName(businessName);
		r1.setDistance(distance);
		r1.setBusinessCategory(businessType);
		r1.setBusinessStatus(businessStatus);
		r1.setBusinessLat(businessLat);
		r1.setBusinessLng(businessLng);
		dbObject.addBusinessObject(r1);
		this.mBusinessObjectsList.add(r1);
	}

	/**
	 * sets the numResults field
	 * @param numResults the number of results
	 */
	public void setNumResults(Integer numResults)
	{
		this.mNumResults = numResults;
	}
	
	/**
	 * sets the search term field
	 * @param userSearchTerm the terms the user enters
	 */
	public void setUserSearchTerm(String userSearchTerm)
	{
		this.mUserSearchTerm = userSearchTerm;
	}

	/**
	 * sets the sort value field
	 * @param sortValue The sort value.
     */
	public void setSortValue(String sortValue) { mSortValue = sortValue; }


	/**
	 * this method uses the latitude and longitude from the calling Async task to update the contents of the lists of yelp search results
	 * one list will be sorted and the other will be sorted by highest review and the most reviews.
	 */
	public void findBusinesses(double latitude, double longitude)
	{
		// make the search.
		String response = mYelp.search(mUserSearchTerm, latitude, longitude, mNumResults, mSortValue); // based off the current address
		JSONObject json, business, location, businessLatLng;
		JSONArray businesses;
		String detailedRestaurantPage, businessLng, businessLat;
		double metersToMiles;
		DecimalFormat decimalConversion;
		Bitmap businessImage;
		BusinessExtraInfo extraBusinessInformation;
		try {
			json = new JSONObject(response);
		    UnitConverter toMiles1 = METER.getConverterTo(MILE);
		    businesses = json.getJSONArray("businesses");
			// once a list of businesses can possibly be parsed, empty the database to prepare for new businesses to be inserted.
			if(businesses.length() > 0) dbObject.deleteBusinessObjects();
		    for (int i = 0; i < businesses.length(); i++) {
		        business = businesses.getJSONObject(i);
		        location = business.getJSONObject("location"); // get the location
				metersToMiles = business.getDouble("distance"); // the distance not converted
		        metersToMiles = toMiles1.convert(Measure.valueOf(metersToMiles, MILE).doubleValue(MILE));
		        decimalConversion = new DecimalFormat("##.##"); // converted distance (a String)
				detailedRestaurantPage = business.getString("url"); // the website's url

				businessLatLng = location.getJSONObject("coordinate");
				businessLng = String.valueOf(businessLatLng.getDouble("longitude"));
				businessLat = String.valueOf(businessLatLng.getDouble("latitude"));
				businessImage = getBusinessImage(business.getString("image_url")); // get an image of the business.
				extraBusinessInformation = getCurrentStatus(detailedRestaurantPage);

		        addToListAndDB(businessImage, business.getString("name"), Double.parseDouble(decimalConversion.format(metersToMiles)), extraBusinessInformation.getBusinessCategory(), extraBusinessInformation.getOpenStatus(), businessLat, businessLng);
		    }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param restaurantPageURL The URL of the restaurant.
	 * @return A string to represent if the restaurant is currently open, closed, or soon to close.
	 */
	public BusinessExtraInfo getCurrentStatus(String restaurantPageURL) {
		Document doc;
		BusinessExtraInfo extraInformation = new BusinessExtraInfo();
		try {
			doc = Jsoup.connect(restaurantPageURL).get();
			Elements tagContents = doc.select("span.category-str-list, span.status.open, span.status.closed");
			if(tagContents.size() == 2) {
				String categoryContents = tagContents.get(0).text();
				String statusContents = tagContents.get(1).text();
				// by default the data members have default values, they will only be overridden if values can be extracted from the restaurant's detailed page.
				if(categoryContents != null && categoryContents.length() > 0) extraInformation.setBusinessCategory(categoryContents);
				if(statusContents != null && statusContents.length() > 0) extraInformation.setOpenStatus(statusContents);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(restaurantPageURL + " cannot be parsed properly.");
		}
		return extraInformation;
	}

	/**
	 * A helper method to get an image (average rating or picture representing the restaurant).
	 * @param restaurantRatingURL The URL to access the image of interest.
	 * @return An image object containing the relevant information.
     */
	private Bitmap getBusinessImage(String restaurantRatingURL) {
		URL url;
		HttpURLConnection connection = null;
		InputStream input = null;
		Bitmap businessImage = null;
		try {
			url = new URL(restaurantRatingURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			input = connection.getInputStream();
			businessImage = BitmapFactory.decodeStream(input);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				connection.disconnect();
			}
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return businessImage;
	}

	/**
	 * returns the sorted List
	 */
	public List<BusinessObjects> getBusinessObjects()
	{
		Collections.sort(mBusinessObjectsList, new RestaurantObjectDistanceComparator()); // sort by descending distance.
		return mBusinessObjectsList;
	}
}
