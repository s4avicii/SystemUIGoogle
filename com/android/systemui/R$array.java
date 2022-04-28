package com.android.systemui;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.widget.ImageView;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.internal.util.ContrastColorUtil;
import com.android.p012wm.shell.C1777R;
import java.util.concurrent.CancellationException;
import kotlinx.coroutines.channels.ReceiveChannel;

public final class R$array {
    public static final int[] sLocationBase = new int[2];
    public static final int[] sLocationOffset = new int[2];

    public static final void cancelConsumed(ReceiveChannel receiveChannel, Throwable th) {
        CancellationException cancellationException = null;
        if (th != null) {
            if (th instanceof CancellationException) {
                cancellationException = (CancellationException) th;
            }
            if (cancellationException == null) {
                cancellationException = new CancellationException("Channel was consumed, consumer had failed");
                cancellationException.initCause(th);
            }
        }
        receiveChannel.cancel(cancellationException);
    }

    public static float interpolate(float f, float f2, float f3) {
        return (f2 * f3) + ((1.0f - f3) * f);
    }

    public static void checkArgument(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(str);
        }
    }

    public static String logKey(String str) {
        if (str == null) {
            return "null";
        }
        return str.replace("\n", "");
    }

    public static void ensureOnMainThread(String str) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, " must be called from the UI thread."));
        }
    }

    public static int getFontScaledHeight(Context context, int i) {
        return (int) (((float) context.getResources().getDimensionPixelSize(i)) * Math.max(1.0f, context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density));
    }

    public static int interpolateColors(int i, int i2, float f) {
        return Color.argb((int) interpolate((float) Color.alpha(i), (float) Color.alpha(i2), f), (int) interpolate((float) Color.red(i), (float) Color.red(i2), f), (int) interpolate((float) Color.green(i), (float) Color.green(i2), f), (int) interpolate((float) Color.blue(i), (float) Color.blue(i2), f));
    }

    public static boolean isGrayscale(ImageView imageView, ContrastColorUtil contrastColorUtil) {
        Object tag = imageView.getTag(C1777R.C1779id.icon_is_grayscale);
        if (tag != null) {
            return Boolean.TRUE.equals(tag);
        }
        boolean isGrayscaleIcon = contrastColorUtil.isGrayscaleIcon(imageView.getDrawable());
        imageView.setTag(C1777R.C1779id.icon_is_grayscale, Boolean.valueOf(isGrayscaleIcon));
        return isGrayscaleIcon;
    }
}
