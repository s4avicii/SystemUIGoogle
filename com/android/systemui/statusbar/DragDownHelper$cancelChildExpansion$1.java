package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.systemui.ExpandHelper;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.Objects;

/* compiled from: LockscreenShadeTransitionController.kt */
public final class DragDownHelper$cancelChildExpansion$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ ExpandableView $child;
    public final /* synthetic */ DragDownHelper this$0;

    public DragDownHelper$cancelChildExpansion$1(DragDownHelper dragDownHelper, ExpandableView expandableView) {
        this.this$0 = dragDownHelper;
        this.$child = expandableView;
    }

    public final void onAnimationEnd(Animator animator) {
        DragDownHelper dragDownHelper = this.this$0;
        Objects.requireNonNull(dragDownHelper);
        ExpandHelper.Callback callback = dragDownHelper.expandCallback;
        if (callback == null) {
            callback = null;
        }
        ((NotificationStackScrollLayout.C135711) callback).setUserLockedChild(this.$child, false);
    }
}
