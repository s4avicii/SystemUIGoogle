package com.android.systemui.globalactions;

import android.view.ContextThemeWrapper;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0824x58d1e4af implements View.OnClickListener {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ C0824x58d1e4af(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite) {
        this.f$0 = actionsDialogLite;
    }

    public final void onClick(View view) {
        GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
        int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
        Objects.requireNonNull(actionsDialogLite);
        GlobalActionsPopupMenu globalActionsPopupMenu = new GlobalActionsPopupMenu(new ContextThemeWrapper(actionsDialogLite.mContext, 2132017471), false);
        globalActionsPopupMenu.setOnItemClickListener(new C0826x58d1e4b1(actionsDialogLite));
        globalActionsPopupMenu.mOnItemLongClickListener = new C0827x58d1e4b2(actionsDialogLite);
        globalActionsPopupMenu.setAnchorView(actionsDialogLite.findViewById(C1777R.C1779id.global_actions_overflow_button));
        globalActionsPopupMenu.setAdapter(actionsDialogLite.mOverflowAdapter);
        actionsDialogLite.mOverflowPopup = globalActionsPopupMenu;
        globalActionsPopupMenu.show();
    }
}
