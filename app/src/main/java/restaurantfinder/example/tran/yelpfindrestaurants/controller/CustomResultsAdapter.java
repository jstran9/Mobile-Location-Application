package restaurantfinder.example.tran.yelpfindrestaurants.controller;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import restaurantfinder.example.tran.yelpfindrestaurants.R;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjects;

public class CustomResultsAdapter extends BaseAdapter {

    /**
     * A helper string to notify the total distance to the restaurant.
     */
    private static final String DISTANCE_STRING = "miles away";

    /**
     * A list of restaurant objects.
     */
    private List<BusinessObjects> mBusinessObjectsList;

    /**
     * The current context of the calling activity
     */
    private Context mContext;

    /**
     * @param context The current context
     * @param businessObjectsList Restaurant objects list.
     */
    public CustomResultsAdapter(Context context, List<BusinessObjects> businessObjectsList) {
        mContext = context;
        mBusinessObjectsList = businessObjectsList;
    }

    /**
     * @return The number of restaurant objects.
     */
    @Override
    public int getCount() {
        return mBusinessObjectsList.size();
    }

    /**
     * @param position The position of the current restaurant object item
     * @return Gets a restaurant object given a position
     */
    @Override
    public Object getItem(int position) {
        return mBusinessObjectsList.get(position);
    }

    /**
     * not implemented
     * @param position The current position
     * @return Gets an item id
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * @param position The position of the current restaurant object item.
     * @param convertView The old view.
     * @param parent The parent of the view.
     * @return Returns a view with the properly initialized views.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.result_item, null);
            holder = new ViewHolder();
            holder.businessImage = (ImageView) convertView.findViewById(R.id.businessImage);
            holder.businessName = (TextView) convertView.findViewById(R.id.businessName);
            holder.businessDistance = (TextView) convertView.findViewById(R.id.businessDistance);
            holder.categoryImage = (ImageView) convertView.findViewById(R.id.categoryImage);
            holder.businessCategory = (TextView) convertView.findViewById(R.id.businessCategory);
            holder.businessStatus = (TextView) convertView.findViewById(R.id.businessStatus);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        BusinessObjects resultObject = mBusinessObjectsList.get(position);

        holder.businessImage.setImageBitmap(resultObject.getBusinessImage());
        holder.businessName.setText(resultObject.getBusinessName());
        holder.businessDistance.setText(String.format("%s %s", resultObject.getDistance(), DISTANCE_STRING));
        holder.categoryImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_type_image));
        holder.businessCategory.setText(String.format("%s", resultObject.getBusinessCategory()));
        holder.businessStatus.setText(String.format("%s", resultObject.getBusinessStatus()));

        return convertView;
    }

    private static class ViewHolder {
        /**
         * View holding the image to represent the restaurant
         */
        ImageView businessImage;
        /**
         * View holding the restaurant name
         */
        TextView businessName;
        /**
         * View holding the average rating text.
         */
        TextView businessDistance;
        /**
         * View holding the category image (a default image).
         */
        ImageView categoryImage;
        /**
         * View holding the estimated distance to the restaurant.
         */
        TextView businessCategory;
        /**
         * View holding the restaurant's address.
         */
        TextView businessStatus;
    }
}
