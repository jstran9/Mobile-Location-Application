package restaurantfinder.example.tran.yelpfindrestaurants.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.ListView;
import android.widget.Toast;

import restaurantfinder.example.tran.yelpfindrestaurants.R;

public class MainActivity extends AppCompatActivity {
    /**
     * arbitrary value to allow for ACCESS_COARSE_LOCATION
     */
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1024;
    /**
     * arbitrary value to allow for ACCESS_FINE_LOCATION
     */
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 2048;
    /**
     * a message to inform the user about the necessity of permissions.
     */
    private static final String LOCATION_NOTIFICATION_MESSAGE = "Please allow location permission(s) the next time you try this action";
    /**
     *
     */
    private static final String LOCATION_NOTIFICATION = "You must have location services provided to use the search functionality";
    /**
     * The view to hold a suggestion of search terms, to be implemented after the yelp search v3 implementation.
     */
    private ListView mSuggestionsList;
    /**
     * The field where the user enters search terms to find a restaurant
     */
    private SearchView mSearchTerms;
    /**
     * a flag to test if the location permission is on (true) or off (false).
     */
    private boolean locationPermissionsGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSuggestionsList = (ListView) findViewById(R.id.listOfSuggestions);

        mSearchTerms = (SearchView) findViewById(R.id.userSearchTerms);

        locationPermissionsGranted = false;

        mSearchTerms.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                checkLocationPermissions(); // ensure location services are on so the next activity can utilize the user location.
                if(locationPermissionsGranted) {
                    mSearchTerms.clearFocus();
                    mSearchTerms.setQuery("", false);

                    Intent startResultsActivity = new Intent(MainActivity.this, SearchResults.class);
                    startResultsActivity.putExtra("userSearchTerm", query);
                    startActivity(startResultsActivity);
                }
                else {
                    Toast.makeText(getBaseContext(), LOCATION_NOTIFICATION, Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    /**
     * helper method to ensure location services can be used
     */
    private void checkLocationPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        }
        else {
            locationPermissionsGranted = true;
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        else {
            locationPermissionsGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, LOCATION_NOTIFICATION_MESSAGE, Toast.LENGTH_LONG).show();
                }
                else {
                    locationPermissionsGranted = true;
                }
                break;
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, LOCATION_NOTIFICATION_MESSAGE, Toast.LENGTH_LONG).show();
                }
                else {
                    locationPermissionsGranted = true;
                }
                break;
            default:
                break;
        }
    }

}
