package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import android.os.Binder;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.elmyra.sensors.GestureSensor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class SettingsAction extends ServiceAction {
    public final LaunchOpa mLaunchOpa;
    public final String mSettingsPackageName;
    public final StatusBar mStatusBar;

    public SettingsAction(Context context, StatusBar statusBar, LaunchOpa launchOpa) {
        super(context, (ArrayList) null);
        this.mSettingsPackageName = context.getResources().getString(C1777R.string.settings_app_package_name);
        this.mStatusBar = statusBar;
        this.mLaunchOpa = launchOpa;
    }

    public static class Builder {
        public final Context mContext;
        public final StatusBar mStatusBar;

        public Builder(Context context, StatusBar statusBar) {
            this.mContext = context;
            this.mStatusBar = statusBar;
        }
    }

    public final boolean checkSupportedCaller() {
        String str = this.mSettingsPackageName;
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if (packagesForUid == null) {
            return false;
        }
        return Arrays.asList(packagesForUid).contains(str);
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.mStatusBar.collapseShade();
        super.onTrigger(detectionProperties);
    }

    public final void triggerAction() {
        if (this.mLaunchOpa.isAvailable()) {
            LaunchOpa launchOpa = this.mLaunchOpa;
            Objects.requireNonNull(launchOpa);
            launchOpa.launchOpa(0);
        }
    }
}
