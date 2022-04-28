package com.android.systemui.p006qs;

import android.graphics.drawable.Drawable;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.statusbar.policy.UserInfoController;

/* renamed from: com.android.systemui.qs.FooterActionsController$onUserInfoChangedListener$1 */
/* compiled from: FooterActionsController.kt */
public final class FooterActionsController$onUserInfoChangedListener$1 implements UserInfoController.OnUserInfoChangedListener {
    public final /* synthetic */ FooterActionsController this$0;

    public FooterActionsController$onUserInfoChangedListener$1(FooterActionsController footerActionsController) {
        this.this$0 = footerActionsController;
    }

    public final void onUserInfoChanged(String str, Drawable drawable) {
        ((FooterActionsView) this.this$0.mView).onUserInfoChanged(drawable, this.this$0.userManager.isGuestUser(KeyguardUpdateMonitor.getCurrentUser()));
    }
}
