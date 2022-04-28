package com.android.systemui.p006qs;

import android.view.View;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.Unit;

/* renamed from: com.android.systemui.qs.FgsManagerController$showDialog$1$2 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$showDialog$1$2 implements Runnable {
    public final /* synthetic */ SystemUIDialog $dialog;
    public final /* synthetic */ View $viewLaunchedFrom;
    public final /* synthetic */ FgsManagerController this$0;

    public FgsManagerController$showDialog$1$2(View view, SystemUIDialog systemUIDialog, FgsManagerController fgsManagerController) {
        this.$viewLaunchedFrom = view;
        this.$dialog = systemUIDialog;
        this.this$0 = fgsManagerController;
    }

    public final void run() {
        Unit unit;
        View view = this.$viewLaunchedFrom;
        if (view == null) {
            unit = null;
        } else {
            FgsManagerController fgsManagerController = this.this$0;
            SystemUIDialog systemUIDialog = this.$dialog;
            DialogLaunchAnimator dialogLaunchAnimator = fgsManagerController.dialogLaunchAnimator;
            LaunchAnimator.Timings timings = DialogLaunchAnimator.TIMINGS;
            dialogLaunchAnimator.showFromView(systemUIDialog, view, false);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            this.$dialog.show();
        }
    }
}
