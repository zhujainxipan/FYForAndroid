package com.ht.fyforandroid.net.threecache;

import android.graphics.*;

/**
 * Created by Android Studio
 * Project:WantToGo
 * Author:gudao[FR]
 * Email:lizhenmeng90@163.com
 * Date:15-6-24
 */
public class ImageUtil {

    public static Bitmap getRoundBitmap(Bitmap bitmap){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);

        final Rect rect = new Rect(0, 0,
                bitmap.getWidth(),
                bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
