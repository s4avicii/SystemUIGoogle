package com.android.systemui.p006qs.tiles;

import android.view.View;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.screenrecord.ScreenRecordDialog;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenRecordTile$$ExternalSyntheticLambda0 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ ScreenRecordTile f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ ScreenRecordDialog f$2;
    public final /* synthetic */ View f$3;

    public /* synthetic */ ScreenRecordTile$$ExternalSyntheticLambda0(ScreenRecordTile screenRecordTile, boolean z, ScreenRecordDialog screenRecordDialog, View view) {
        this.f$0 = screenRecordTile;
        this.f$1 = z;
        this.f$2 = screenRecordDialog;
        this.f$3 = view;
    }

    public final boolean onDismiss() {
        ScreenRecordTile screenRecordTile = this.f$0;
        boolean z = this.f$1;
        ScreenRecordDialog screenRecordDialog = this.f$2;
        View view = this.f$3;
        if (z) {
            DialogLaunchAnimator dialogLaunchAnimator = screenRecordTile.mDialogLaunchAnimator;
            Objects.requireNonNull(dialogLaunchAnimator);
            dialogLaunchAnimator.showFromView(screenRecordDialog, view, false);
        } else {
            Objects.requireNonNull(screenRecordTile);
            screenRecordDialog.show();
        }
        return false;
    }
}
