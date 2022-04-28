package com.android.systemui.statusbar.notification.row;

import android.view.View;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationConversationInfo$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ View f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ View f$2;

    public /* synthetic */ NotificationConversationInfo$$ExternalSyntheticLambda4(View view, View view2, View view3) {
        this.f$0 = view;
        this.f$1 = view2;
        this.f$2 = view3;
    }

    public final void run() {
        View view = this.f$0;
        View view2 = this.f$1;
        View view3 = this.f$2;
        int i = NotificationConversationInfo.$r8$clinit;
        view.setSelected(false);
        view2.setSelected(true);
        view3.setSelected(false);
    }
}
