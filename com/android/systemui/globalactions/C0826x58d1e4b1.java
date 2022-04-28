package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0826x58d1e4b1 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ C0826x58d1e4b1(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite) {
        this.f$0 = actionsDialogLite;
    }

    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
        int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
        Objects.requireNonNull(actionsDialogLite);
        GlobalActionsDialogLite.MyOverflowAdapter myOverflowAdapter = actionsDialogLite.mOverflowAdapter;
        Objects.requireNonNull(myOverflowAdapter);
        GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i);
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
