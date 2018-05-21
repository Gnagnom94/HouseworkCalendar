package namenotfoundunica.houseworkcalendar;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

public class CustomDrawable {
    public static Drawable getTintedDrawable(Context context, int res, int color)
    {
        Drawable drawable = ContextCompat.getDrawable(context, res);

        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        return drawable;
    }
}
