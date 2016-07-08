package restaurantfinder.example.tran.yelpfindrestaurants.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds.Builder;

import java.util.List;

import restaurantfinder.example.tran.yelpfindrestaurants.R;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjects;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjectsDBHelper;
import restaurantfinder.example.tran.yelpfindrestaurants.utility.BuildDetailedInfoWindowAdapter;
import restaurantfinder.example.tran.yelpfindrestaurants.utility.RefactorBusinessImages;


public class BusinessMapView extends AppCompatActivity implements OnMapReadyCallback {
    /**
     * The title of the marker for the current location.
     */
    private static final String CURRENT_LOCATION_TITLE = "Your Current Location";
    /**
     * The description for the current location marker.
     */
    private static final String CURRENT_LOCATION_SNIPPET = "Your current location after entering a business search.";
    /**
     *
     */
    private static final String DISTANCE_STRING = "miles away.";
    /**
     * The list that holds the business objects from the prior activity.
     */
    List<BusinessObjects> mBusinessObjectsList;
    /**
     * The current latitude (coordinate) of the user
     */
    private double mCurrentLatitude;
    /**
     * The current longitude (coordinate) of the user
     */
    private double mCurrentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_map_view);

        getStoredData();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.title_activity_business_map_view);
            actionBar.setIcon(R.drawable.ic_snorlax);
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * helper method to retrieve the bundle of data from the prior activity
     */
    private void getStoredData() {
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            BusinessObjectsDBHelper dbObject = new BusinessObjectsDBHelper(this);
            mBusinessObjectsList = dbObject.getBusinessObjectsList();
            mCurrentLatitude = bundle.getDouble("currentLat");
            mCurrentLongitude = bundle.getDouble("currentLng");
        }
    }

    /**
     * Create menu options
     * @param menu The item to inflate action bar.
     * @return A modified action bar with item(s).
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_item_choice, menu);
        return true;
    }

    /**
     * Creates a(n) event handler(s) for menu items.
     * @param item The menu item
     * @return true or false depending if an item has been pressed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.back_to_previous) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng currentLocation = new LatLng(mCurrentLatitude, mCurrentLongitude);
        BusinessObjects businessObjects;
        LatLng businessMarkers;
        Builder markersBuilder = new Builder();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        RefactorBusinessImages modifyImageObject = new RefactorBusinessImages();
        markersBuilder.include(currentLocation); // add to the builder to compute a maximum bound.
        Drawable currentLocationImage = ContextCompat.getDrawable(this, R.drawable.ic_snorlax);
        Bitmap locationImage = ((BitmapDrawable)currentLocationImage).getBitmap();
        for(int i = 0; i < mBusinessObjectsList.size(); i++) {
            businessObjects = mBusinessObjectsList.get(i);
            businessMarkers = new LatLng(Double.parseDouble(businessObjects.getBusinessLat()), Double.parseDouble(businessObjects.getBusinessLng()));
            markersBuilder.include(businessMarkers);

            googleMap.addMarker(new MarkerOptions().position(businessMarkers).title(businessObjects.getBusinessName()
                    + " (" + businessObjects.getBusinessStatus() + ")")
                    .icon(BitmapDescriptorFactory.fromBitmap(modifyImageObject.getResizedBitmap(businessObjects.getBusinessImage(), locationImage.getWidth(), locationImage.getHeight())))
                    .snippet(businessObjects.getBusinessCategory() + "-" + businessObjects.getDistance() + " " + DISTANCE_STRING));
        }
        LatLngBounds mapBounds = markersBuilder.build();
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title(CURRENT_LOCATION_TITLE).icon(BitmapDescriptorFactory.fromBitmap(locationImage)).snippet(CURRENT_LOCATION_SNIPPET));

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // get contents from each marker and add on to an infoWindow view accordingly.
                Context activityContext = getApplicationContext();
                BuildDetailedInfoWindowAdapter createInfoAdapterView = new BuildDetailedInfoWindowAdapter(activityContext);

                return createInfoAdapterView.createInfoWindowAdapter(marker);
            }
        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBounds, displayMetrics.widthPixels, displayMetrics.heightPixels, 0));
    }
}
