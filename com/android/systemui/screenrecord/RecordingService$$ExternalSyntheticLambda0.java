package com.android.systemui.screenrecord;

import android.content.Intent;
import android.os.UserHandle;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RecordingService$$ExternalSyntheticLambda0 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ RecordingService f$0;
    public final /* synthetic */ Intent f$1;
    public final /* synthetic */ UserHandle f$2;

    public /* synthetic */ RecordingService$$ExternalSyntheticLambda0(RecordingService recordingService, Intent intent, UserHandle userHandle) {
        this.f$0 = recordingService;
        this.f$1 = intent;
        this.f$2 = userHandle;
    }

    public final boolean onDismiss() {
        RecordingService recordingService = this.f$0;
        Intent intent = this.f$1;
        UserHandle userHandle = this.f$2;
        int i = RecordingService.$r8$clinit;
        Objects.requireNonNull(recordingService);
        recordingService.startActivity(Intent.createChooser(intent, recordingService.getResources().getString(C1777R.string.screenrecord_share_label)).setFlags(268435456));
        recordingService.mNotificationManager.cancelAsUser((String) null, 4273, userHandle);
        return false;
    }
}
