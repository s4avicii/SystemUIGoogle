package com.android.systemui.navigationbar;

import android.content.Intent;
import android.os.UserHandle;
import android.view.View;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda6 implements View.OnLongClickListener {
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda6(NavigationBar navigationBar) {
        this.f$0 = navigationBar;
    }

    public final boolean onLongClick(View view) {
        NavigationBar navigationBar = this.f$0;
        Objects.requireNonNull(navigationBar);
        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
        intent.addFlags(268468224);
        intent.setClassName(ThemeOverlayApplier.ANDROID_PACKAGE, AccessibilityButtonChooserActivity.class.getName());
        navigationBar.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
        return true;
    }
}
