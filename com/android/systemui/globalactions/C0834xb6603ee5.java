package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$MyPowerOptionsAdapter$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0834xb6603ee5 implements View.OnClickListener {
    public final /* synthetic */ GlobalActionsDialogLite.MyPowerOptionsAdapter f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ C0834xb6603ee5(GlobalActionsDialogLite.MyPowerOptionsAdapter myPowerOptionsAdapter, int i) {
        this.f$0 = myPowerOptionsAdapter;
        this.f$1 = i;
    }

    public final void onClick(View view) {
        GlobalActionsDialogLite.MyPowerOptionsAdapter myPowerOptionsAdapter = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(myPowerOptionsAdapter);
        GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mPowerItems.get(i);
        if (!(action instanceof GlobalActionsDialogLite.SilentModeTriStateAction)) {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mDialog != null) {
                globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                GlobalActionsDialogLite.this.mDialog.dismiss();
            } else {
                Log.w("GlobalActionsDialogLite", "Action clicked while mDialog is null.");
            }
            action.onPress();
        }
    }
}
