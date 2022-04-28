package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;

public final class StatusBarIconHolder {
    public StatusBarIcon mIcon;
    public StatusBarSignalPolicy.MobileIconState mMobileState;
    public int mTag = 0;
    public int mType = 0;
    public StatusBarSignalPolicy.WifiIconState mWifiState;

    public static StatusBarIconHolder fromCallIndicatorState(Context context, StatusBarSignalPolicy.CallIndicatorIconState callIndicatorIconState) {
        int i;
        String str;
        StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder();
        boolean z = callIndicatorIconState.isNoCalling;
        if (z) {
            i = callIndicatorIconState.noCallingResId;
        } else {
            i = callIndicatorIconState.callStrengthResId;
        }
        if (z) {
            str = callIndicatorIconState.noCallingDescription;
        } else {
            str = callIndicatorIconState.callStrengthDescription;
        }
        statusBarIconHolder.mIcon = new StatusBarIcon(UserHandle.SYSTEM, context.getPackageName(), Icon.createWithResource(context, i), 0, 0, str);
        statusBarIconHolder.mTag = callIndicatorIconState.subId;
        return statusBarIconHolder;
    }
}
