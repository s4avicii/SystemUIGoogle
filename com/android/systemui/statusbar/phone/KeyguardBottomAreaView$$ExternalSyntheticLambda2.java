package com.android.systemui.statusbar.phone;

import android.content.Intent;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardBottomAreaView$$ExternalSyntheticLambda2 implements Supplier {
    public final /* synthetic */ KeyguardBottomAreaView f$0;

    public /* synthetic */ KeyguardBottomAreaView$$ExternalSyntheticLambda2(KeyguardBottomAreaView keyguardBottomAreaView) {
        this.f$0 = keyguardBottomAreaView;
    }

    public final Object get() {
        KeyguardBottomAreaView keyguardBottomAreaView = this.f$0;
        Intent intent = KeyguardBottomAreaView.PHONE_INTENT;
        Objects.requireNonNull(keyguardBottomAreaView);
        return new KeyguardBottomAreaView.DefaultRightButton();
    }
}
