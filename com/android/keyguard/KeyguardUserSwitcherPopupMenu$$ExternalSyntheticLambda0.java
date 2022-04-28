package com.android.keyguard;

import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardUserSwitcherPopupMenu$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    public final /* synthetic */ KeyguardUserSwitcherPopupMenu f$0;

    public /* synthetic */ KeyguardUserSwitcherPopupMenu$$ExternalSyntheticLambda0(KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu) {
        this.f$0 = keyguardUserSwitcherPopupMenu;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu = this.f$0;
        Objects.requireNonNull(keyguardUserSwitcherPopupMenu);
        if (motionEvent.getActionMasked() == 0) {
            return keyguardUserSwitcherPopupMenu.mFalsingManager.isFalseTap(1);
        }
        return false;
    }
}
