package restaurantfinder.example.tran.yelpfindrestaurants.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

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
     * An object to receive notifications of location changes.
     */
    private LocationListener mLocationListener;

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
     * Sets the longitude for the user.
     * @param longitude
     */
    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    /**
     * @return The latitude of the user.
     */
    public double getLatitude() {
        return mLatitude;
    }

    /**
     * Sets the latitude for the user.
     * @param latitude The user's current latitude.
     */
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    /**
     * http://stackoverflow.com/questions/20438627/getlastknownlocation-returns-null
     * helper method to get the latitude and longitude of the user.
     */
    public Location getLocation() {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (mLocationManager != null) {
                try {
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
                catch(SecurityException e) {
                    e.printStackTrace();
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
        if(mLocationManager != null && mLocationListener != null) {
            try {
                mLocationManager.removeUpdates(mLocationListener);
                mLocationManager = null;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
