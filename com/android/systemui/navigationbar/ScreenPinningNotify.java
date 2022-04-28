package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.SystemClock;
import android.util.Slog;
import android.widget.Toast;
import com.android.p012wm.shell.C1777R;

public final class ScreenPinningNotify {
    public final Context mContext;
    public long mLastShowToastTime;
    public Toast mLastToast;

    public ScreenPinningNotify(Context context) {
        this.mContext = context;
    }

    public final void showEscapeToast(boolean z, boolean z2) {
        int i;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - this.mLastShowToastTime < 1000) {
            Slog.i("ScreenPinningNotify", "Ignore toast since it is requested in very short interval.");
            return;
        }
        Toast toast = this.mLastToast;
        if (toast != null) {
            toast.cancel();
        }
        if (z) {
            i = C1777R.string.screen_pinning_toast_gesture_nav;
        } else if (z2) {
            i = C1777R.string.screen_pinning_toast;
        } else {
            i = C1777R.string.screen_pinning_toast_recents_invisible;
        }
        Context context = this.mContext;
        Toast makeText = Toast.makeText(context, context.getString(i), 1);
        makeText.show();
        this.mLastToast = makeText;
        this.mLastShowToastTime = elapsedRealtime;
    }
}
