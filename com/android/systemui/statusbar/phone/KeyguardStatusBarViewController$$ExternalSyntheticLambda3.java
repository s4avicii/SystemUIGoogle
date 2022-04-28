package com.android.systemui.statusbar.phone;

import android.graphics.drawable.Drawable;
import com.android.systemui.statusbar.policy.UserInfoController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda3 implements UserInfoController.OnUserInfoChangedListener {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda3(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        this.f$0 = keyguardStatusBarViewController;
    }

    public final void onUserInfoChanged(String str, Drawable drawable) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        Objects.requireNonNull(keyguardStatusBarViewController);
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
        Objects.requireNonNull(keyguardStatusBarView);
        keyguardStatusBarView.mMultiUserAvatar.setImageDrawable(drawable);
    }
}
