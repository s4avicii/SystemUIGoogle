package com.android.systemui.globalactions;

import android.app.Dialog;
import android.content.DialogInterface;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.scrim.ScrimDrawable;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GlobalActionsImpl$$ExternalSyntheticLambda0 implements DialogInterface.OnShowListener {
    public final /* synthetic */ GlobalActionsImpl f$0;
    public final /* synthetic */ ScrimDrawable f$1;
    public final /* synthetic */ Dialog f$2;

    public /* synthetic */ GlobalActionsImpl$$ExternalSyntheticLambda0(GlobalActionsImpl globalActionsImpl, ScrimDrawable scrimDrawable, Dialog dialog) {
        this.f$0 = globalActionsImpl;
        this.f$1 = scrimDrawable;
        this.f$2 = dialog;
    }

    public final void onShow(DialogInterface dialogInterface) {
        GlobalActionsImpl globalActionsImpl = this.f$0;
        ScrimDrawable scrimDrawable = this.f$1;
        Dialog dialog = this.f$2;
        Objects.requireNonNull(globalActionsImpl);
        if (globalActionsImpl.mBlurUtils.supportsBlursOnWindows()) {
            scrimDrawable.setAlpha(255);
            globalActionsImpl.mBlurUtils.applyBlur(dialog.getWindow().getDecorView().getViewRootImpl(), (int) globalActionsImpl.mBlurUtils.blurRadiusOfRatio(1.0f), true);
            return;
        }
        scrimDrawable.setAlpha((int) (globalActionsImpl.mContext.getResources().getFloat(C1777R.dimen.shutdown_scrim_behind_alpha) * 255.0f));
    }
}
