package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0827x58d1e4b2 implements AdapterView.OnItemLongClickListener {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ C0827x58d1e4b2(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite) {
        this.f$0 = actionsDialogLite;
    }

    public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
        GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
        int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
        Objects.requireNonNull(actionsDialogLite);
        GlobalActionsDialogLite.MyOverflowAdapter myOverflowAdapter = actionsDialogLite.mOverflowAdapter;
        Objects.requireNonNull(myOverflowAdapter);
        GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i);
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
