package restaurantfinder.example.tran.yelpfindrestaurants.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import restaurantfinder.example.tran.yelpfindrestaurants.R;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjects;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjectsDBHelper;
import restaurantfinder.example.tran.yelpfindrestaurants.utility.GetGPSCoordinates;
import restaurantfinder.example.tran.yelpfindrestaurants.utility.TestForNetworkConnection;

public class SearchResults extends AppCompatActivity {
    /**
     * a view to display the list of businesses.
     */
    private ListView mListOfBusinesses;

    /**
     * the user entered search terms from the main activity
     */
    private String mSearchTerms;

    /**
     * a list of restaurant objects.
     */
    private List<BusinessObjects> mBusinessObjectsList;

    /**
     * user's current latitude
     */
    private double mLatitude;

    /**
     * current longitude
     */
    private double mLongitude;

    /**
     * The adapter holding and sorting the restaurant objects
     */
    private CustomResultsAdapter resultListAdapter;

    /**
     * Button to allow user to view the businesses displayed on a map view.
     */
    private Button mStartMapActivity;

    /**
     * gets previous results
     */
    private Button mPrevious;

    /**
     * get next results
     */
    private Button mNext;

    /**
     * number of businesses from the Yelp Search query.
     */
    private int mNumBusinesses;

    /**
     * Helps control which results are being returned from the user search.
     */
    private int mOffsetValue;

    /**
     * the number of results returned from the yelp search.
     */
    private final int NUM_RETURNED_RESULTS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        mBusinessObjectsList = new ArrayList<>();
        mListOfBusinesses = (ListView) findViewById(R.id.resultListView);
        mStartMapActivity = (Button) findViewById(R.id.goToMapsView);
        mPrevious = (Button) findViewById(R.id.previousResults);
        mNext = (Button) findViewById(R.id.nextResults);

        mOffsetValue = 0;
        mNumBusinesses = 0;

        mPrevious.setClickable(false);
        mPrevious.setVisibility(View.INVISIBLE);
        mNext.setClickable(false);
        mNext.setVisibility(View.INVISIBLE);

        getUserSearchTerm();

        ActionBar bar = getSupportActionBar();

        if(bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setTitle(R.string.resultsActivityDisplayName);
            bar.setIcon(R.drawable.ic_snorlax);
        }

        GetGPSCoordinates getLatAndLong = new GetGPSCoordinates(this);
        getLatAndLong.getLocation();

        mLatitude = getLatAndLong.getLatitude();
        mLongitude = getLatAndLong.getLongitude();

        setOnClickListeners();

        getSearchResults(1);
    }

    /**
     * helper method to determine if the previous or next button should be disabled from being clicked.
     */
    private void checkUserButtons() {
        if(mOffsetValue == 0) {
            mPrevious.setClickable(false);
            mPrevious.setVisibility(View.INVISIBLE);
        }
        else {
            mPrevious.setClickable(true);
            mPrevious.setVisibility(View.VISIBLE);
        }

        int numResults = NUM_RETURNED_RESULTS;
        if(mOffsetValue * numResults + numResults > mNumBusinesses) {
            mNext.setClickable(false);
            mNext.setVisibility(View.INVISIBLE);
        }
        else {
            mNext.setClickable(true);
            mNext.setVisibility(View.VISIBLE);
        }
    }

    /**
     * helper method to set the listeners of the buttons on this screen.
     */
    private void setOnClickListeners() {
        mStartMapActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchMapActivity = new Intent(SearchResults.this, BusinessMapView.class);
                launchMapActivity.putExtra("currentLat", mLatitude);
                launchMapActivity.putExtra("currentLng", mLongitude);
                startActivity(launchMapActivity);
            }
        });

        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOffsetValue > 0) {
                    mOffsetValue--;
                    getSearchResults(2);
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numResults = NUM_RETURNED_RESULTS;
                if(mOffsetValue * numResults + numResults < mNumBusinesses) {
                    mOffsetValue++;
                    getSearchResults(3);
                }
            }
        });
    }

    /**
     * helper method that runs a yelp search or queries the database if internet is not currently available.
     */
    private void getSearchResults(int dialogMessageType) {
        TestForNetworkConnection testForNetworkConnection = new TestForNetworkConnection(this);

        if(testForNetworkConnection.checkForInternetConnection()) {
            // there is internet connectivity, so try to use the Yelp Search.
            GetRestaurantListings getROObjects = new GetRestaurantListings(this);
            getROObjects.setDialogMessage(dialogMessageType);
            getROObjects.execute();
        }
        else {
            // no internet so see if there are any results in the database.
            queryDatabaseAndUpdateList();
        }
    }

    /**
     * helper method to display last search results if there is no internet connectivity (caching mechanism).
     */
    private void queryDatabaseAndUpdateList() {
        BusinessObjectsDBHelper dbObject = new BusinessObjectsDBHelper(this);
        mBusinessObjectsList = dbObject.getBusinessObjectsList();

        resultListAdapter = new CustomResultsAdapter(this, mBusinessObjectsList);
        mListOfBusinesses.setAdapter(resultListAdapter);
    }

    /**
     * helper method to retrieve what the user entered in the previous activity.
     */
    private void getUserSearchTerm() {
        Intent callingIntent = getIntent();
        Bundle bundle;
        if(callingIntent != null) bundle = callingIntent.getExtras();
        else return;

        if(bundle != null) mSearchTerms = bundle.getString("userSearchTerm");
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

    private class GetRestaurantListings extends AsyncTask<Void, Void, Void> {
        /**
         * The message to inform the user that the query is being performed.
         */
        private String mDialogMessage;

        /**
         * current activity context
         */
        private final Context mContext;

        /**
         * an object to invoke methods to get a list of restaurants.
         */
        private ListOfBOs mListOfRestaurantObjects;

        /**
         * a dialog to display to the user for when the yelp search is querying.
         */
        private ProgressDialog mDialog;

        /**
         * @param context The context of the calling activity.
         */
        public GetRestaurantListings(Context context) { mContext = context; }

        /**
         * Sets a dialog message for the user.
         * @param dialogMessageType The type of message to display to the user.
         */
        public void setDialogMessage(int dialogMessageType) {
            if(dialogMessageType == 1) {
                mDialogMessage = mContext.getResources().getString(R.string.defaultDialogMessage);
            }
            else if(dialogMessageType == 2) {
                mDialogMessage = mContext.getResources().getString(R.string.previousDialogMessage);
            }
            else if(dialogMessageType == 3) {
                mDialogMessage = mContext.getResources().getString(R.string.nextDialogMessage);
            }
        }

        /**
         * Creates the dialog for the user while querying for a list of restaurants.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage(mDialogMessage);
            mDialog.show();
        }

        /**
         * Finds restaurants given the user's current location and the entered search term(s).
         * @param params void parameter
         * @return A null object
         */
        @Override
        protected Void doInBackground(Void... params) {

            mListOfRestaurantObjects = new ListOfBOs(mContext);

            mListOfRestaurantObjects.setNumResults(NUM_RETURNED_RESULTS);
            mListOfRestaurantObjects.setUserSearchTerm(mSearchTerms);

            /**
             * multiplication by num_returned_results as this will help offset the businesses returned.
             */
            mListOfRestaurantObjects.findBusinesses(mLatitude, mLongitude, mOffsetValue * NUM_RETURNED_RESULTS);
            return null;
        }

        /**
         * Creates the view with a suggestion of restaurants for the user.
         * @param aVoid void parameter
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            mBusinessObjectsList = mListOfRestaurantObjects.getBusinessObjects();
            mNumBusinesses = mListOfRestaurantObjects.getTotalBusinesses();

            checkUserButtons();

            if(mBusinessObjectsList.size() > 0) {
                resultListAdapter = new CustomResultsAdapter(mContext, mBusinessObjectsList);
                mListOfBusinesses.setAdapter(resultListAdapter);
            }
            else {
                /**
                 * though the Yelp search call was made, it's possible it couldn't find any businesses, so try checking the db.
                 * it's not always guaranteed that the database will have businesses stored.
                 */
                queryDatabaseAndUpdateList();
            }
            super.onPostExecute(aVoid);
            mDialog.dismiss();
        }
    }

}
