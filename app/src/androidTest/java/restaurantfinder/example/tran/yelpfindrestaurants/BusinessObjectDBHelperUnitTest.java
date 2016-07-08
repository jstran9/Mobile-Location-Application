package restaurantfinder.example.tran.yelpfindrestaurants;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjectsDBHelper;

@RunWith(AndroidJUnit4.class)
public class BusinessObjectDBHelperUnitTest extends AndroidJUnitRunner {
    /**
     * the number of expected businesses to be stored.
     */
    private static final int NUM_RESULTS = 4;

    @Test
    public void testGetBusinessObjectsList() {
        BusinessObjectsDBHelper dbObject = new BusinessObjectsDBHelper(InstrumentationRegistry.getTargetContext());
        Assert.assertEquals(NUM_RESULTS, dbObject.getBusinessObjectsList().size());
    }
}
