package com.google.android.systemui.assist.uihints;

import android.app.PendingIntent;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;

public final class TouchOutsideHandler implements NgaMessageHandler.ConfigInfoListener {
    public PendingIntent mTouchOutside;

    public final void onConfigInfo(NgaMessageHandler.ConfigInfo configInfo) {
        this.mTouchOutside = configInfo.onTouchOutside;
    }
}
