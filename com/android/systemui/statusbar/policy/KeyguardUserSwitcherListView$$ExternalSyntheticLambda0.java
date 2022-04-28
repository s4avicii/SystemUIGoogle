package com.android.systemui.statusbar.policy;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardUserSwitcherListView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ KeyguardUserSwitcherListView f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ KeyguardUserDetailItemView[] f$2;

    public /* synthetic */ KeyguardUserSwitcherListView$$ExternalSyntheticLambda0(KeyguardUserSwitcherListView keyguardUserSwitcherListView, boolean z, KeyguardUserDetailItemView[] keyguardUserDetailItemViewArr) {
        this.f$0 = keyguardUserSwitcherListView;
        this.f$1 = z;
        this.f$2 = keyguardUserDetailItemViewArr;
    }

    public final void run() {
        KeyguardUserSwitcherListView keyguardUserSwitcherListView = this.f$0;
        boolean z = this.f$1;
        KeyguardUserDetailItemView[] keyguardUserDetailItemViewArr = this.f$2;
        boolean z2 = KeyguardUserSwitcherListView.DEBUG;
        Objects.requireNonNull(keyguardUserSwitcherListView);
        keyguardUserSwitcherListView.setClipChildren(true);
        keyguardUserSwitcherListView.setClipToPadding(true);
        keyguardUserSwitcherListView.mAnimating = false;
        if (!z) {
            for (int i = 1; i < keyguardUserDetailItemViewArr.length; i++) {
                keyguardUserDetailItemViewArr[i].updateVisibilities(false, true, false);
            }
        }
    }
}
