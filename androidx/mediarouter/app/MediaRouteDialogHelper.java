package androidx.mediarouter.app;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.android.p012wm.shell.C1777R;

public final class MediaRouteDialogHelper {
    public static int getDialogWidth(Context context) {
        boolean z;
        int i;
        float fraction;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics.widthPixels < displayMetrics.heightPixels) {
            z = true;
        } else {
            z = false;
        }
        TypedValue typedValue = new TypedValue();
        Resources resources = context.getResources();
        if (z) {
            i = C1777R.dimen.mr_dialog_fixed_width_minor;
        } else {
            i = C1777R.dimen.mr_dialog_fixed_width_major;
        }
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.type;
        if (i2 == 5) {
            fraction = typedValue.getDimension(displayMetrics);
        } else if (i2 != 6) {
            return -2;
        } else {
            int i3 = displayMetrics.widthPixels;
            fraction = typedValue.getFraction((float) i3, (float) i3);
        }
        return (int) fraction;
    }
}
