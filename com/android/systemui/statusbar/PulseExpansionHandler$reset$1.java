package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.Objects;

/* compiled from: PulseExpansionHandler.kt */
public final class PulseExpansionHandler$reset$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ ExpandableView $child;
    public final /* synthetic */ PulseExpansionHandler this$0;

    public PulseExpansionHandler$reset$1(PulseExpansionHandler pulseExpansionHandler, ExpandableView expandableView) {
        this.this$0 = pulseExpansionHandler;
        this.$child = expandableView;
    }

    public final void onAnimationEnd(Animator animator) {
        PulseExpansionHandler pulseExpansionHandler = this.this$0;
        ExpandableView expandableView = this.$child;
        Objects.requireNonNull(pulseExpansionHandler);
        if (expandableView instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) expandableView).setUserLocked(false);
        }
    }
}
