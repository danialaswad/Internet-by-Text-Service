package polytech.its.mobileapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author: Abdelkarim Andolerzak
 */

public class ImageManager {

    public Bitmap imageBuilder(String content) {
        byte[] blob = content.getBytes();
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        return bmp;
    }
}
