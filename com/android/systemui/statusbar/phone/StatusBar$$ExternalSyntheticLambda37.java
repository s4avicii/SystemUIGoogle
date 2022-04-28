package com.android.systemui.statusbar.phone;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.RemoteAnimationAdapter;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda37 implements Function1 {
    public final /* synthetic */ StatusBar f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Intent f$2;
    public final /* synthetic */ int[] f$3;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda37(StatusBar statusBar, boolean z, Intent intent, int[] iArr) {
        this.f$0 = statusBar;
        this.f$1 = z;
        this.f$2 = intent;
        this.f$3 = iArr;
    }

    public final Object invoke(Object obj) {
        boolean z;
        int[] iArr;
        String action;
        StatusBar statusBar = this.f$0;
        boolean z2 = this.f$1;
        Intent intent = this.f$2;
        int[] iArr2 = this.f$3;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        ActivityOptions activityOptions = new ActivityOptions(StatusBar.getActivityOptions(statusBar.mDisplayId, (RemoteAnimationAdapter) obj));
        activityOptions.setDisallowEnterPictureInPictureWhileLaunching(z2);
        if (intent == null || (action = intent.getAction()) == null) {
            z = false;
        } else {
            z = action.equals("android.media.action.STILL_IMAGE_CAMERA");
        }
        if (z) {
            activityOptions.setRotationAnimationHint(3);
        }
        if ("android.settings.panel.action.VOLUME".equals(intent.getAction())) {
            activityOptions.setDisallowEnterPictureInPictureWhileLaunching(true);
        }
        try {
            iArr = iArr2;
            try {
                iArr[0] = ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, statusBar.mContext.getBasePackageName(), statusBar.mContext.getAttributionTag(), intent, intent.resolveTypeIfNeeded(statusBar.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, activityOptions.toBundle(), UserHandle.CURRENT.getIdentifier());
            } catch (RemoteException e) {
                e = e;
            }
        } catch (RemoteException e2) {
            e = e2;
            iArr = iArr2;
            Log.w("StatusBar", "Unable to start activity", e);
            return Integer.valueOf(iArr[0]);
        }
        return Integer.valueOf(iArr[0]);
    }
}
