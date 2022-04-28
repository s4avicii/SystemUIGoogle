package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.DeviceControlsTile$handleClick$1 */
/* compiled from: DeviceControlsTile.kt */
public final class DeviceControlsTile$handleClick$1 implements Runnable {
    public final /* synthetic */ ActivityLaunchAnimator.Controller $animationController;
    public final /* synthetic */ Intent $intent;
    public final /* synthetic */ DeviceControlsTile this$0;

    public DeviceControlsTile$handleClick$1(DeviceControlsTile deviceControlsTile, Intent intent, GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController) {
        this.this$0 = deviceControlsTile;
        this.$intent = intent;
        this.$animationController = ghostedViewLaunchAnimatorController;
    }

    public final void run() {
        boolean z;
        DeviceControlsTile deviceControlsTile = this.this$0;
        Objects.requireNonNull(deviceControlsTile);
        if (deviceControlsTile.mState.state == 2) {
            z = true;
        } else {
            z = false;
        }
        this.this$0.mActivityStarter.startActivity(this.$intent, true, this.$animationController, z);
    }
}
