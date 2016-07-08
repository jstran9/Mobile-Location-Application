package restaurantfinder.example.tran.yelpfindrestaurants.utility;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * A class with helper methods to modify business image(s).
 */
public class RefactorBusinessImages {

    public RefactorBusinessImages() {}

    /**
     * @param businessImage The image to be resized.
     * @param newWidth The new width of the resized image.
     * @param newHeight The new height of the resized image.
     * @return A resized image.
     */
    public Bitmap getResizedBitmap(Bitmap businessImage, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createBitmap(businessImage, 0, 0, newHeight, newWidth);
        businessImage.recycle();
        return resizedBitmap;
    }

    /**
     * @param businessObjectImage The image to be converted.
     * @return The image of the business as a byte array.
     */
    public byte[] getBusinessObjectImage(Bitmap businessObjectImage) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int compressQuality = 100; // value doesn't matter but 100 represents max quality.
        businessObjectImage.compress(Bitmap.CompressFormat.PNG, compressQuality, stream);
        return stream.toByteArray();
    }

}
