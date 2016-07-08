package restaurantfinder.example.tran.yelpfindrestaurants.model;

import android.provider.BaseColumns;

public class BusinessObjectsContract {

    public BusinessObjectsContract() {}

    public static final class BusinessObjectEntry implements BaseColumns {
        /**
         * helper string to delimit the text type.
         */
        public static final String TEXT_TYPE = " TEXT, ";
        /**
         * helper string to delimit the blob type.
         */
        public static final String BLOB_TYPE = " BLOB, ";
        /**
         * helper string to delimit the real type.
         */
        public static final String REAL_TYPE = " REAL, ";
        /**
         * the name of the table to hold the contents of the results.
         */
        public static final String BUSINESS_OBJECT_TABLE = "business_object_table";
        /**
         * an auto incrementing key so that each business is unique, there may be multiple locations of a business so using the business's name isn't sufficient.
         */
        public static final String AUTO_INCREMENT_KEY = "id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        /**
         * the column to store the name of the business.
         */
        public static final String BUSINESS_NAME = "business_name";
        /**
         * the column to store the distance of the business from the user.
         */
        public static final String BUSINESS_DISTANCE = "business_distance";
        /**
         * the column to store if the business is currently open or closed.
         */
        public static final String BUSINESS_STATUS = "business_status";
        /**
         * the column to store an image of the business.
         */
        public static final String BUSINESS_IMAGE = "business_image";
        /**
         * the column to store the type of business (pizza place, electronics store, etc).
         */
        public static final String BUSINESS_CATEGORY = "business_category";
        /**
         * the column to store the latitude (coordinate) of the business.
         */
        public static final String BUSINESS_LATITUDE = "business_latitude";
        /**
         * the column to store the longitude (coordinate) of the business.
         * doesn't use a helper string as it is the last column.
         */
        public static final String BUSINESS_LONGITUDE = "business_longitude";
    }
}
