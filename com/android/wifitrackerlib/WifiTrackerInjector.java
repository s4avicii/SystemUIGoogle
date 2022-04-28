package com.android.wifitrackerlib;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserManager;
import android.util.ArraySet;
import com.android.p012wm.shell.C1777R;

public final class WifiTrackerInjector {
    public final DevicePolicyManager mDevicePolicyManager;
    public final ArraySet mNoAttributionAnnotationPackages = new ArraySet();
    public final UserManager mUserManager;

    public WifiTrackerInjector(Context context) {
        UserManager.isDeviceInDemoMode(context);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        String[] split = context.getString(C1777R.string.wifitrackerlib_no_attribution_annotation_packages).split(",");
        for (String add : split) {
            this.mNoAttributionAnnotationPackages.add(add);
        }
    }
}
