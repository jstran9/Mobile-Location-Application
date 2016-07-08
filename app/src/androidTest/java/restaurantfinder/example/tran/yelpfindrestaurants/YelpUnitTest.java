package restaurantfinder.example.tran.yelpfindrestaurants;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import restaurantfinder.example.tran.yelpfindrestaurants.controller.SearchResults;
import restaurantfinder.example.tran.yelpfindrestaurants.model.Yelp;

/**
 * helper class to test key methods of the Yelp class.
 */
@RunWith(AndroidJUnit4.class)
public class YelpUnitTest extends AndroidJUnitRunner {
    /**
     * tests to see if the Yelp API search call can return a result.
     */
    @Test
    public void testSearch() {
        Resources accessResources = InstrumentationRegistry.getTargetContext().getResources();
        Yelp yelpAPIObject = new Yelp(accessResources.getString(R.string.YelpConsumerKey), accessResources.getString(R.string.YelpConsumerSecret),
                accessResources.getString(R.string.YelpToken), accessResources.getString(R.string.YelpTokenSecret));
        String exampleSearch = "pizza";
        double currentLatitude = 37.4674561;
        double currentLongitude = -121.9060851;
        Integer numResults = 20;
        String sortType = "0"; // default sort.
        Assert.assertNotNull(yelpAPIObject.search(exampleSearch, currentLatitude, currentLongitude, numResults, sortType));
    }
}
