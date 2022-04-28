package com.android.systemui.globalactions;

import androidx.lifecycle.Observer;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GlobalActionsDialogLite$$ExternalSyntheticLambda0 implements Observer {
    public final /* synthetic */ GlobalActionsDialogLite f$0;

    public /* synthetic */ GlobalActionsDialogLite$$ExternalSyntheticLambda0(GlobalActionsDialogLite globalActionsDialogLite) {
        this.f$0 = globalActionsDialogLite;
    }

    public final void onChanged(Object obj) {
        GlobalActionsDialogLite globalActionsDialogLite = this.f$0;
        Integer num = (Integer) obj;
        Objects.requireNonNull(globalActionsDialogLite);
        globalActionsDialogLite.mHandler.sendEmptyMessage(1);
    }
}
