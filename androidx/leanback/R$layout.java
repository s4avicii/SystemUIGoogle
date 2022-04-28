package androidx.leanback;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.common.base.Strings;
import java.util.Objects;

public final class R$layout {
    public static final int[] ETHERNET_CONNECTION_VALUES = {C1777R.string.accessibility_ethernet_disconnected, C1777R.string.accessibility_ethernet_connected};
    public static final int[] PHONE_SIGNAL_STRENGTH = {C1777R.string.accessibility_no_phone, C1777R.string.accessibility_phone_one_bar, C1777R.string.accessibility_phone_two_bars, C1777R.string.accessibility_phone_three_bars, C1777R.string.accessibility_phone_signal_full};
    public static final int[] WIFI_CONNECTION_STRENGTH = {C1777R.string.accessibility_no_wifi, C1777R.string.accessibility_wifi_one_bar, C1777R.string.accessibility_wifi_two_bars, C1777R.string.accessibility_wifi_three_bars, C1777R.string.accessibility_wifi_signal_full};
    public static Boolean sIsEnabled;

    public static int saturatedCast(long j) {
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        if (j < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) j;
    }

    public static final String toString(byte b) {
        if (b == 0) {
            return "BUILT_IN";
        }
        if (b == 1) {
            return "FIXED";
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("0x");
        m.append(Integer.toHexString(Byte.toUnsignedInt(b)));
        return m.toString();
    }

    public /* synthetic */ R$layout(SwipeDismissBehavior swipeDismissBehavior) {
        Objects.requireNonNull(swipeDismissBehavior);
        swipeDismissBehavior.alphaStartSwipeDistance = Math.min(Math.max(0.0f, 0.1f), 1.0f);
        swipeDismissBehavior.alphaEndSwipeDistance = Math.min(Math.max(0.0f, 0.6f), 1.0f);
        swipeDismissBehavior.swipeDirection = 0;
    }

    public static int constrainToRange(int i, int i2) {
        boolean z;
        if (i2 <= 1073741823) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return Math.min(Math.max(i, i2), 1073741823);
        }
        throw new IllegalArgumentException(Strings.lenientFormat("min (%s) must be less than or equal to max (%s)", Integer.valueOf(i2), 1073741823));
    }
}
