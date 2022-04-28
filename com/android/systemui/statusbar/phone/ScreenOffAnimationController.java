package com.android.systemui.statusbar.phone;

import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: ScreenOffAnimationController.kt */
public final class ScreenOffAnimationController implements WakefulnessLifecycle.Observer {
    public final ArrayList animations;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    public final boolean isKeyguardShowDelayed() {
        ArrayList arrayList = this.animations;
        if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((ScreenOffAnimation) it.next()).isKeyguardShowDelayed()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x0006 A[LOOP:0: B:1:0x0006->B:4:0x0016, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onStartedGoingToSleep() {
        /*
            r1 = this;
            java.util.ArrayList r1 = r1.animations
            java.util.Iterator r1 = r1.iterator()
        L_0x0006:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x0018
            java.lang.Object r0 = r1.next()
            com.android.systemui.statusbar.phone.ScreenOffAnimation r0 = (com.android.systemui.statusbar.phone.ScreenOffAnimation) r0
            boolean r0 = r0.startAnimation()
            if (r0 == 0) goto L_0x0006
        L_0x0018:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ScreenOffAnimationController.onStartedGoingToSleep():void");
    }

    public final boolean overrideNotificationsFullyDozingOnKeyguard() {
        ArrayList arrayList = this.animations;
        if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((ScreenOffAnimation) it.next()).overrideNotificationsDozeAmount()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean shouldExpandNotifications() {
        ArrayList arrayList = this.animations;
        if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((ScreenOffAnimation) it.next()).isAnimationPlaying()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean shouldIgnoreKeyguardTouches() {
        ArrayList arrayList = this.animations;
        if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((ScreenOffAnimation) it.next()).isAnimationPlaying()) {
                    return true;
                }
            }
        }
        return false;
    }

    public ScreenOffAnimationController(Optional<SysUIUnfoldComponent> optional, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, WakefulnessLifecycle wakefulnessLifecycle2) {
        this.wakefulnessLifecycle = wakefulnessLifecycle2;
        FoldAodAnimationController foldAodAnimationController = null;
        SysUIUnfoldComponent orElse = optional.orElse((Object) null);
        this.animations = ArraysKt___ArraysKt.filterNotNull(new ScreenOffAnimation[]{orElse != null ? orElse.getFoldAodAnimationController() : foldAodAnimationController, unlockedScreenOffAnimationController});
    }
}
