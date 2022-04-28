package com.android.systemui.statusbar.phone;

import android.app.AlertDialog;
import android.content.Context;
import com.android.systemui.statusbar.KeyboardShortcuts;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.concurrency.MessageRouter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda15 implements MessageRouter.DataMessageListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda15(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void onMessage(Object obj) {
        boolean z;
        AlertDialog alertDialog;
        StatusBar statusBar = this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        int i = ((StatusBar.KeyboardShortcutsMessage) obj).mDeviceId;
        Context context = statusBar.mContext;
        synchronized (KeyboardShortcuts.sLock) {
            KeyboardShortcuts keyboardShortcuts = KeyboardShortcuts.sInstance;
            if (keyboardShortcuts == null || (alertDialog = keyboardShortcuts.mKeyboardShortcutsDialog) == null || !alertDialog.isShowing()) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                KeyboardShortcuts.dismiss();
            } else {
                KeyboardShortcuts.show(context, i);
            }
        }
    }
}
