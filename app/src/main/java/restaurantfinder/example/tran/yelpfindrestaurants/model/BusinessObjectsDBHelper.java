package restaurantfinder.example.tran.yelpfindrestaurants.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;
import java.util.List;
import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjectsContract.BusinessObjectEntry;
import restaurantfinder.example.tran.yelpfindrestaurants.utility.RefactorBusinessImages;

/**
 * A class to implement a local SQLite database.
 */
public class BusinessObjectsDBHelper extends SQLiteOpenHelper {
    /**
     * the database version
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * the name of the database
     */
    private static final String DATABASE_NAME = "businessObjects.db";
    /**
     * helper string to get all the rows from the business objects table.
     */
    private static final String BUSINESS_OBJECTS_SELECT = "SELECT * FROM " + BusinessObjectEntry.BUSINESS_OBJECT_TABLE + " ORDER BY " + BusinessObjectEntry.BUSINESS_DISTANCE + " DESC";
    /**
     * helper string to delete the contents of the business object table.
     */
    public static final String BUSINESS_DELETE_CONTENT = "DELETE FROM " + BusinessObjectEntry.BUSINESS_OBJECT_TABLE;
    /**
     * helper string to drop the business object table.
     */
    public static final String BUSINESS_OBJECTS_DROP_TABLE = "DROP TABLE IF EXISTS " + BusinessObjectEntry.BUSINESS_OBJECT_TABLE;
    /**
     * helper string to create the business object table.
     */
    public static final String CREATE_BUSINESS_OBJECTS_TABLE = "CREATE TABLE " + BusinessObjectEntry.BUSINESS_OBJECT_TABLE + "(" + BusinessObjectEntry.AUTO_INCREMENT_KEY
            + BusinessObjectEntry.BUSINESS_NAME + BusinessObjectEntry.TEXT_TYPE + BusinessObjectEntry.BUSINESS_DISTANCE + BusinessObjectEntry.REAL_TYPE +
            BusinessObjectEntry.BUSINESS_STATUS + BusinessObjectEntry.TEXT_TYPE + BusinessObjectEntry.BUSINESS_IMAGE + BusinessObjectEntry.BLOB_TYPE +
            BusinessObjectEntry.BUSINESS_CATEGORY + BusinessObjectEntry.TEXT_TYPE + BusinessObjectEntry.BUSINESS_LATITUDE + BusinessObjectEntry.TEXT_TYPE
            + BusinessObjectEntry.BUSINESS_LONGITUDE + " TEXT)";

    public BusinessObjectsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUSINESS_OBJECTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BUSINESS_OBJECTS_DROP_TABLE);
        onCreate(db);
    }

    /**
     * helper method to add a business object to the database.
     * @param businessInformation The business object to be added
     */
    public void addBusinessObject(BusinessObjects businessInformation) {
        SQLiteDatabase db = this.getWritableDatabase();
        RefactorBusinessImages changeImageType = new RefactorBusinessImages();

        ContentValues businessObjectValues = new ContentValues();
        businessObjectValues.put(BusinessObjectEntry.BUSINESS_NAME, businessInformation.getBusinessName());
        businessObjectValues.put(BusinessObjectEntry.BUSINESS_DISTANCE, businessInformation.getDistance());
        businessObjectValues.put(BusinessObjectEntry.BUSINESS_STATUS, businessInformation.getBusinessStatus());
        businessObjectValues.put(BusinessObjectEntry.BUSINESS_IMAGE, changeImageType.getBusinessObjectImage(businessInformation.getBusinessImage()));
        businessObjectValues.put(BusinessObjectEntry.BUSINESS_CATEGORY, businessInformation.getBusinessCategory());
        businessObjectValues.put(BusinessObjectEntry.BUSINESS_LATITUDE, businessInformation.getBusinessLat());
        businessObjectValues.put(BusinessObjectEntry.BUSINESS_LONGITUDE, businessInformation.getBusinessLng());

        db.insert(BusinessObjectEntry.BUSINESS_OBJECT_TABLE, null, businessObjectValues);
        db.close();
    }

    /**
     * helper method to delete the contents of the business object table.
     */
    public void deleteBusinessObjects() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(BUSINESS_DELETE_CONTENT);
        db.close();
    }

    /**
     * @return Returns all the business objects currently stored.
     */
    public List<BusinessObjects> getBusinessObjectsList() {
        List<BusinessObjects> businessObjectsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(BUSINESS_OBJECTS_SELECT, null);
        if(cursor.moveToFirst()) {
            do {
                BusinessObjects businessObject = new BusinessObjects();
                businessObject.setBusinessName(cursor.getString(cursor.getColumnIndex(BusinessObjectEntry.BUSINESS_NAME)));
                businessObject.setDistance(cursor.getDouble(cursor.getColumnIndex(BusinessObjectEntry.BUSINESS_DISTANCE)));
                businessObject.setBusinessStatus(cursor.getString(cursor.getColumnIndex(BusinessObjectEntry.BUSINESS_STATUS)));
                businessObject.setBusinessImage(getBusinessObjectImage(cursor));
                businessObject.setBusinessCategory(cursor.getString(cursor.getColumnIndex(BusinessObjectEntry.BUSINESS_CATEGORY)));
                businessObject.setBusinessLat(cursor.getString(cursor.getColumnIndex(BusinessObjectEntry.BUSINESS_LATITUDE)));
                businessObject.setBusinessLng(cursor.getString(cursor.getColumnIndex(BusinessObjectEntry.BUSINESS_LONGITUDE)));
                businessObjectsList.add(businessObject);
            } while(cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return businessObjectsList;
    }

    /**
     * @param cursor A pointer to the current row in the database table.
     * @return A bitmap representation the business's image.
     */
    public Bitmap getBusinessObjectImage(Cursor cursor) {
        byte[] businessObjectImage = cursor.getBlob(cursor.getColumnIndex(BusinessObjectEntry.BUSINESS_IMAGE));
        return BitmapFactory.decodeByteArray(businessObjectImage, 0, businessObjectImage.length);
    }
}
