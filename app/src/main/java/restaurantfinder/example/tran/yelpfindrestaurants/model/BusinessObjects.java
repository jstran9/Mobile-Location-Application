package restaurantfinder.example.tran.yelpfindrestaurants.model;

import android.graphics.Bitmap;

public class BusinessObjects {
    /**
     * the name of the business
     */
    private String mBusinessName;

    /**
     * the distance
     */
    private double mDistance;

    /**
     * A string representation if the restaurant is currently open or closed.
     */
    private String mBusinessStatus;
    /**
     * A picture representing an image of the business/location.
     */
    private Bitmap mBusinessImage;

    /**
     * The category of the business.
     */
    private String mBusinessCategory;

    /**
     * The latitude (coordinate) of the business.
     */
    private String mBusinessLat;

    /**
     * The longitude (coordinate) of the business.
     */
    private String mBusinessLng;

    /**
     * Default constructor
     */
    public BusinessObjects() {}

    /**
     * @return The distance to the restaurant.
     */
    public double getDistance() {
        return mDistance;
    }

    /**
     * Sets the distance to the restaurant.
     * @param distance The distance to the restaurant
     */
    public void setDistance(double distance) {
        mDistance = distance;
    }

    /**
     * @return The name of restaurant
     */
    public String getBusinessName() {
        return mBusinessName;
    }

    /**
     * Sets the name of the restaurant
     * @param restaurantName The name of the restaurant.
     */
    public void setBusinessName(String restaurantName) {
        mBusinessName = restaurantName;
    }

    /**
     * @return The current status of the restaurant.
     */
    public String getBusinessStatus() {
        return mBusinessStatus;
    }

    /**
     * sets the status of the restaurant.
     * @param openStatus The status of the restaurant
     */
    public void setBusinessStatus(String openStatus) {
        mBusinessStatus = openStatus;
    }

    /**
     * @return The type of business.
     */
    public String getBusinessCategory() { return mBusinessCategory; }

    /**
     * Sets the business's category type.
     * @param businessCategory The category of the business.
     */
    public void setBusinessCategory(String businessCategory) { mBusinessCategory = businessCategory; }

    /**
     * @return The image representing the business/location.
     */
    public Bitmap getBusinessImage() {
        return mBusinessImage;
    }

    /**
     * sets the image to represent the business/location.
     * @param businessImage The image representing the business/location.
     */
    public void setBusinessImage(Bitmap businessImage) {
        mBusinessImage = businessImage;
    }

    /**
     * @return The latitude (coordinate) of the business.
     */
    public String getBusinessLat() {
        return mBusinessLat;
    }

    /**
     * sets the latitude (coordinate) of the business
     * @param businessLat The latitude (coordinate) value.
     */
    public void setBusinessLat(String businessLat) {
        mBusinessLat = businessLat;
    }

    /**
     * @return The longitude (coordinate) of the business.
     */
    public String getBusinessLng() {
        return mBusinessLng;
    }

    /**
     * sets the longitude (coordinate) of the business.
     * @param businessLng The longitude (coordinate) value.
     */
    public void setBusinessLng(String businessLng) {
        mBusinessLng = businessLng;
    }
}
