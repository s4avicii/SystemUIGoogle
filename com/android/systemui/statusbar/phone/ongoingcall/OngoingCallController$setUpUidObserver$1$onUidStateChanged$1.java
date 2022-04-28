package com.android.systemui.statusbar.phone.ongoingcall;

import java.util.Iterator;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController$setUpUidObserver$1$onUidStateChanged$1 implements Runnable {
    public final /* synthetic */ OngoingCallController this$0;

    public OngoingCallController$setUpUidObserver$1$onUidStateChanged$1(OngoingCallController ongoingCallController) {
        this.this$0 = ongoingCallController;
    }

    public final void run() {
        Iterator it = this.this$0.mListeners.iterator();
        while (it.hasNext()) {
            ((OngoingCallListener) it.next()).onOngoingCallStateChanged();
        }
    }
}
