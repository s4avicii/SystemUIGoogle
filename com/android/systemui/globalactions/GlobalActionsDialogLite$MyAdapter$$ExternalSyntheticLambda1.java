package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1 implements View.OnLongClickListener {
    public final /* synthetic */ GlobalActionsDialogLite.MyAdapter f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(GlobalActionsDialogLite.MyAdapter myAdapter, int i) {
        this.f$0 = myAdapter;
        this.f$1 = i;
    }

    public final boolean onLongClick(View view) {
        GlobalActionsDialogLite.MyAdapter myAdapter = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(myAdapter);
        GlobalActionsDialogLite.Action item = GlobalActionsDialogLite.this.mAdapter.getItem(i);
        if (!(item instanceof GlobalActionsDialogLite.LongPressAction)) {
            return false;
        }
        GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
        if (globalActionsDialogLite.mDialog != null) {
            globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
            GlobalActionsDialogLite.this.mDialog.dismiss();
        } else {
            Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
        }
        return ((GlobalActionsDialogLite.LongPressAction) item).onLongPress();
    }
}
