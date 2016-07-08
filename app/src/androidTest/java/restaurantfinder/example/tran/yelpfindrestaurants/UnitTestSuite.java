package restaurantfinder.example.tran.yelpfindrestaurants;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * a helper class to act as a test suite to run the unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BusinessObjectDBHelperUnitTest.class, ListOfBOsUnitTest.class, TestForNetworkConnectionUnitTest.class, YelpUnitTest.class})
public class UnitTestSuite {}
