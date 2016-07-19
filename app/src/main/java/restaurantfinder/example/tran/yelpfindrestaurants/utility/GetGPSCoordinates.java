package restaurantfinder.example.tran.yelpfindrestaurants.utility;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

import java.util.List;

/**
 * helper class to get the latitude and longitude of the user.
 * @author Todd
 */
public class GetGPSCoordinates {

    /**
     * The latitude of the user's location.
     */
    private double mLatitude;
    /**
     * The longitude of the user's location.
     */
    private double mLongitude;

    /**
     * An object to help use the location services of the system.
     */
    private LocationManager mLocationManager;

    /**
     * The context of the calling activity
     */
    private Context mContext;

    /**
     * @param context The current context of the calling activity
     */
    public GetGPSCoordinates(Context context) {
        mContext = context;
    }

    /**
     * @return The user's current latitude
     */
    public double getLongitude() {
        return mLongitude;
    }

    /**
     * @return The latitude of the user.
     */
    public double getLatitude() {
        return mLatitude;
    }

    /**
     * http://stackoverflow.com/questions/20438627/getlastknownlocation-returns-null
     * helper method to get the latitude and longitude of the user.
     */
    public Location getLocation() {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getAllProviders();
        Location bestLocation = null;
        for (String provider : providers) {
            if (mLocationManager != null) {
                    if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Location location = mLocationManager.getLastKnownLocation(provider);
                        if (location == null) {
                            continue;
                        }
                        if (bestLocation == null || location.getAccuracy() > bestLocation.getAccuracy()) {
                            mLatitude = location.getLatitude();
                            mLongitude = location.getLongitude();
                            bestLocation = location;
                        }
                    }
            }
        }
        stopGPSTracking();
        return bestLocation;
    }

    /**
     * helper method to close the location manager and location listener objects after the user's latitude and longitude has been found.
     */
    public void stopGPSTracking() {
        if(mLocationManager != null) {
            try {
                mLocationManager = null;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
