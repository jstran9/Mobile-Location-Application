package restaurantfinder.example.tran.yelpfindrestaurants.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * helper class to test if there is internet connectivity
 */
public class TestForNetworkConnection {
    /**
     * The calling activity's context.
     */
    private Context mContext;
    /**
     * @param context The context of the calling activity
     */
    public TestForNetworkConnection(Context context) {mContext = context;}

    /**
     * helper method to check if there is internet connectivity.
     * this method is useful because if there is no internet then making the Yelp search call isn't possible <br/>
     * to look at the database to see if there are any stored businesses.
     */
    public boolean checkForInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
