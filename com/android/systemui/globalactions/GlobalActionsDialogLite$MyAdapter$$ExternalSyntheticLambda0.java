package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ GlobalActionsDialogLite.MyAdapter f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0(GlobalActionsDialogLite.MyAdapter myAdapter, int i) {
        this.f$0 = myAdapter;
        this.f$1 = i;
    }

    public final void onClick(View view) {
        GlobalActionsDialogLite.MyAdapter myAdapter = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(myAdapter);
        GlobalActionsDialogLite.Action item = GlobalActionsDialogLite.this.mAdapter.getItem(i);
        if (!(item instanceof GlobalActionsDialogLite.SilentModeTriStateAction)) {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mDialog == null) {
                Log.w("GlobalActionsDialogLite", "Action clicked while mDialog is null.");
            } else if (!(item instanceof GlobalActionsDialogLite.PowerOptionsAction)) {
                globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                GlobalActionsDialogLite.this.mDialog.dismiss();
            }
            item.onPress();
        }
    }
}
