package restaurantfinder.example.tran.yelpfindrestaurants.utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import restaurantfinder.example.tran.yelpfindrestaurants.R;

/**
 * A helper class helping to build a detailed information view when a user clicks on an icon on the map view.
 */
public class BuildDetailedInfoWindowAdapter {

    /**
     * The context of the calling activity.
     */
    private Context mContext;

    public BuildDetailedInfoWindowAdapter(Context context) { mContext = context;}

    /**
     * @param marker The object holding the information of the business.
     * @return a detailed information view with the contents of the business.
     */
    public LinearLayout createInfoWindowAdapter(Marker marker) {
        String[] snippetContents = marker.getSnippet().split("-");
        if(snippetContents.length == 2) {
            return createViewForBusinesses(marker.getTitle(), snippetContents[0], snippetContents[1]);
        }
        else {
            return createViewForCurrentLocation(marker.getTitle(), marker.getSnippet());
        }
    }

    /**
     * @param title The name of the business.
     * @param category The type of business.
     * @param distance The distance tot he business.
     * @return A layout representing the information shown for businesses.
     */
    public LinearLayout createViewForBusinesses(String title, String category, String distance) {
        LinearLayout detailedInformation = new LinearLayout(mContext);
        detailedInformation.setOrientation(LinearLayout.VERTICAL);
        TextView businessName = new TextView(mContext);
        businessName.setTextColor(Color.BLACK);
        businessName.setGravity(Gravity.CENTER);
        businessName.setTypeface(null, Typeface.BOLD);
        businessName.setText(title);

        LinearLayout horizontalDetailedLayout = new LinearLayout(mContext);
        horizontalDetailedLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView categoryImage = new ImageView(mContext);
        categoryImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_type_image));

        TextView businessCategory = new TextView(mContext);
        businessCategory.setTextColor(Color.GRAY);
        businessCategory.setText(category);

        TextView businessDistance = new TextView(mContext);
        businessDistance.setTextColor(Color.GRAY);
        businessDistance.setText(distance);

        detailedInformation.addView(businessName);
        horizontalDetailedLayout.addView(categoryImage);
        horizontalDetailedLayout.addView(businessCategory);
        detailedInformation.addView(horizontalDetailedLayout);
        detailedInformation.addView(businessDistance);
        return detailedInformation;
    }

    /**
     * @param currentLocation The current location.
     * @param currentLocationSnippet The description of the current location.
     * @return A layout representing the information shown for the current location.
     */
    public LinearLayout createViewForCurrentLocation(String currentLocation, String currentLocationSnippet) {
        LinearLayout detailedInformation = new LinearLayout(mContext);
        detailedInformation.setOrientation(LinearLayout.VERTICAL);

        TextView currentLocationName = new TextView(mContext);
        currentLocationName.setTextColor(Color.GRAY);
        currentLocationName.setText(currentLocation);

        TextView currentLocationDescription = new TextView(mContext);
        currentLocationDescription.setTextColor(Color.GRAY);
        currentLocationDescription.setText(currentLocationSnippet);

        detailedInformation.addView(currentLocationName);
        detailedInformation.addView(currentLocationDescription);

        return detailedInformation;
    }

}
