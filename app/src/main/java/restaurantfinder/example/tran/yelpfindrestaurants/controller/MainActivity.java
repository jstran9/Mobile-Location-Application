package restaurantfinder.example.tran.yelpfindrestaurants.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.ListView;

import restaurantfinder.example.tran.yelpfindrestaurants.R;

public class MainActivity extends AppCompatActivity {

    /**
     * The view to hold a suggestion of search terms.
     */
    private ListView mSuggestionsList;

    /**
     * The field where the user enters search terms to find a restaurant
     */
    private SearchView mSearchTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSuggestionsList = (ListView) findViewById(R.id.listOfSuggestions);

        mSearchTerms = (SearchView) findViewById(R.id.userSearchTerms);

        mSearchTerms.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchTerms.clearFocus();
                mSearchTerms.setQuery("", false);

                Intent startResultsActivity = new Intent(MainActivity.this, SearchResults.class);
                startResultsActivity.putExtra("userSearchTerm", query);
                startActivity(startResultsActivity);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
