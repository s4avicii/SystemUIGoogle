package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import android.os.Binder;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.elmyra.sensors.GestureSensor;
import java.util.ArrayList;
import java.util.Arrays;

public final class CameraAction extends ServiceAction {
    public final String mCameraPackageName;
    public final StatusBar mStatusBar;

    public static class Builder {
        public final Context mContext;
        public ArrayList mFeedbackEffects = new ArrayList();
        public final StatusBar mStatusBar;

        public Builder(Context context, StatusBar statusBar) {
            this.mContext = context;
            this.mStatusBar = statusBar;
        }
    }

    public final boolean checkSupportedCaller() {
        String str = this.mCameraPackageName;
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

    public CameraAction(Context context, StatusBar statusBar, ArrayList arrayList) {
        super(context, arrayList);
        this.mCameraPackageName = context.getResources().getString(C1777R.string.google_camera_app_package_name);
        this.mStatusBar = statusBar;
    }
}
