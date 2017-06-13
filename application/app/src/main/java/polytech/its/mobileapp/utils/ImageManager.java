package polytech.its.mobileapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * @author: Abdelkarim Andolerzak
 */

public class ImageManager {

    public Bitmap   imageBuilder(String content) {
        byte[] blob = decompress(content);
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        return bmp;
    }

    public byte[] decompress(String decompressed) {
        return Base64.decode(decompressed, Base64.DEFAULT);
    }
}
