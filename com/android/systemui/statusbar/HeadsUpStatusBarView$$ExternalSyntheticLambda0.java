package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HeadsUpStatusBarView$$ExternalSyntheticLambda0 implements NotificationEntry.OnSensitivityChangedListener {
    public final /* synthetic */ HeadsUpStatusBarView f$0;

    public /* synthetic */ HeadsUpStatusBarView$$ExternalSyntheticLambda0(HeadsUpStatusBarView headsUpStatusBarView) {
        this.f$0 = headsUpStatusBarView;
    }

    public final void onSensitivityChanged(NotificationEntry notificationEntry) {
        HeadsUpStatusBarView headsUpStatusBarView = this.f$0;
        int i = HeadsUpStatusBarView.$r8$clinit;
        Objects.requireNonNull(headsUpStatusBarView);
        if (notificationEntry == headsUpStatusBarView.mShowingEntry) {
            headsUpStatusBarView.setEntry(notificationEntry);
            return;
        }
        throw new IllegalStateException("Got a sensitivity change for " + notificationEntry + " but mShowingEntry is " + headsUpStatusBarView.mShowingEntry);
    }
}
