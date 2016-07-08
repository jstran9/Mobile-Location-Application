package restaurantfinder.example.tran.yelpfindrestaurants;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import restaurantfinder.example.tran.yelpfindrestaurants.utility.TestForNetworkConnection;


/**
 * helper class to test key methods of the TestForNetworkConnection class.
 */
@RunWith(AndroidJUnit4.class)
public class TestForNetworkConnectionUnitTest extends AndroidJUnitRunner {
    /**
     * tests to see if there is an available internet connection on the device.
     */
    @Test
    public void testCheckForInternetConnection() {
        TestForNetworkConnection networkTester = new TestForNetworkConnection(InstrumentationRegistry.getTargetContext());
        Assert.assertEquals(true, networkTester.checkForInternetConnection());
    }

}
