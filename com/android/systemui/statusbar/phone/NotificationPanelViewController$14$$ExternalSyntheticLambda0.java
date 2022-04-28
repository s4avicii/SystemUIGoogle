package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationPanelViewController$14$$ExternalSyntheticLambda0 implements View.OnLayoutChangeListener {
    public final /* synthetic */ NotificationPanelViewController.C147914 f$0;

    public /* synthetic */ NotificationPanelViewController$14$$ExternalSyntheticLambda0(NotificationPanelViewController.C147914 r1) {
        this.f$0 = r1;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        NotificationPanelViewController.C147914 r0 = this.f$0;
        if (i4 - i2 != i8 - i6) {
            NotificationPanelViewController.this.mHeightListener.onQsHeightChanged();
        } else {
            Objects.requireNonNull(r0);
        }
    }
}
