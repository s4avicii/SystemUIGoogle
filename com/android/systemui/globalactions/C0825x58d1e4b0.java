package com.android.systemui.globalactions;

import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0825x58d1e4b0 implements View.OnTouchListener {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ C0825x58d1e4b0(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite) {
        this.f$0 = actionsDialogLite;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
        int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
        Objects.requireNonNull(actionsDialogLite);
        actionsDialogLite.mGestureDetector.onTouchEvent(motionEvent);
        return view.onTouchEvent(motionEvent);
    }
}
