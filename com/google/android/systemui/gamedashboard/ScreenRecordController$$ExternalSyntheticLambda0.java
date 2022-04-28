package com.google.android.systemui.gamedashboard;

import android.content.Context;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.ScreenRecordDialog;
import com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenRecordController$$ExternalSyntheticLambda0 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ ScreenRecordController f$0;

    public /* synthetic */ ScreenRecordController$$ExternalSyntheticLambda0(ScreenRecordController screenRecordController) {
        this.f$0 = screenRecordController;
    }

    public final boolean onDismiss() {
        ScreenRecordController screenRecordController = this.f$0;
        Objects.requireNonNull(screenRecordController);
        RecordingController recordingController = screenRecordController.mController;
        Context context = screenRecordController.mContext;
        Objects.requireNonNull(recordingController);
        new ScreenRecordDialog(context, recordingController, recordingController.mUserContextProvider, (StandardWifiEntry$$ExternalSyntheticLambda0) null).show();
        return false;
    }
}
