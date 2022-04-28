package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$MyPowerOptionsAdapter$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0835xb6603ee6 implements View.OnLongClickListener {
    public final /* synthetic */ GlobalActionsDialogLite.MyPowerOptionsAdapter f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ C0835xb6603ee6(GlobalActionsDialogLite.MyPowerOptionsAdapter myPowerOptionsAdapter, int i) {
        this.f$0 = myPowerOptionsAdapter;
        this.f$1 = i;
    }

    public final boolean onLongClick(View view) {
        GlobalActionsDialogLite.MyPowerOptionsAdapter myPowerOptionsAdapter = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(myPowerOptionsAdapter);
        GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mPowerItems.get(i);
        if (!(action instanceof GlobalActionsDialogLite.LongPressAction)) {
            return false;
        }
        GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
        if (globalActionsDialogLite.mDialog != null) {
            globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
            GlobalActionsDialogLite.this.mDialog.dismiss();
        } else {
            Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
        }
        return ((GlobalActionsDialogLite.LongPressAction) action).onLongPress();
    }
}
