package restaurantfinder.example.tran.yelpfindrestaurants;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import restaurantfinder.example.tran.yelpfindrestaurants.controller.ListOfBOs;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessExtraInfo;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjects;

/**
 * helper class to test key methods of the ListOfBOs class.
 */
@RunWith(AndroidJUnit4.class)
public class ListOfBOsUnitTest extends AndroidJUnitRunner {
    /**
     * sample URL for testing.
     */
    private static final String THREE_GUYS_PIZZA_URL = "http://www.yelp.com/biz/three-guys-pies-fremont-2?utm_campaign=yelp_api\u0026utm_medium=api_v2_search\u0026utm_source=HpCd7gjQrgmnIZkkY7Ry4Q";
    /**
     * the message to represent that the JSoup parser could not get the category.
     */
    private static final String BUSINESS_CATEGORY = "unable to retrieve the category";
    /**
     * the message to represent that the JSoup parser could not determine if the business is open.
     */
    private static final String OPEN_STATUS = "unable to determine if open";

    /**
     * tests to see if it can be determined if the business is open or closed and what type of business it is.
     */
    @Test
    public void testGetCurrentStatus() {
        ListOfBOs businessObjectTester = new ListOfBOs(InstrumentationRegistry.getTargetContext());
        BusinessExtraInfo extraInfo = businessObjectTester.getCurrentStatus(THREE_GUYS_PIZZA_URL);
        Assert.assertNotSame(BUSINESS_CATEGORY, extraInfo.getBusinessCategory());
        Assert.assertNotSame(OPEN_STATUS, extraInfo.getOpenStatus());
    }

    /**
     * tests to see if businesses
     */
    @Test
    public void testFindRestaurants() {
        ListOfBOs businessObjectTester = new ListOfBOs(InstrumentationRegistry.getTargetContext());
        double currentLatitude = 37.735969;
        double currentLongitude = -122.310791;
        String userSearchTerm = "pizza";
        Integer numResults = 4;
        businessObjectTester.setUserSearchTerm(userSearchTerm);
        businessObjectTester.setNumResults(numResults);
        businessObjectTester.findBusinesses(currentLatitude, currentLongitude);
        List<BusinessObjects> listOfBusinesses = businessObjectTester.getBusinessObjects();
        int numberOfBusinesses = 4;
        Assert.assertEquals(listOfBusinesses.size(), numberOfBusinesses);
    }
}
