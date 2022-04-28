package com.android.systemui.statusbar.phone;

import android.database.ContentObserver;
import android.os.Handler;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1 */
/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class C1594x7f4c478e extends ContentObserver {
    public final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1594x7f4c478e(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        super((Handler) null);
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public final void onChange(boolean z) {
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
        Objects.requireNonNull(unlockedScreenOffAnimationController);
        unlockedScreenOffAnimationController.animatorDurationScale = unlockedScreenOffAnimationController.globalSettings.getFloat();
    }
}
