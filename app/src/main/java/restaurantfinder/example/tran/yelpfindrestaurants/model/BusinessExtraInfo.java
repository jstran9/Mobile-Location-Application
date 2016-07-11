package restaurantfinder.example.tran.yelpfindrestaurants.model;

/**
 * This class will hold extra business information such as the category and if the business is currently open or closed.
 */
public class BusinessExtraInfo {

    /**
     * What type of business it is: Electronics Store, Italian Food, American Food, etc.
     */
    private String mBusinessCategory;

    /**
     * If the business is open or closed.
     */
    private String mOpenStatus;

    /**
     * allow instantiation of this class and sets default values.
     */
    public BusinessExtraInfo() {
        mBusinessCategory = "unable to retrieve the category";
        mOpenStatus = "unable to determine if open";
    }

    /**
     * @return The category of the business.
     */
    public String getBusinessCategory() {
        return mBusinessCategory;
    }

    /**
     * Sets the business's category type
     * @param businessCategory The category
     */
    public void setBusinessCategory(String businessCategory) {
        mBusinessCategory = businessCategory;
    }

    /**
     * @return If the business is open or closed
     */
    public String getOpenStatus() {
        return mOpenStatus;
    }

    /**
     * Sets if the business is closed or open.
     * @param openStatus The status of the business.
     */
    public void setOpenStatus(String openStatus) {
        mOpenStatus = openStatus;
    }
}
