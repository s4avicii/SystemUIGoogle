package com.android.systemui.doze;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.ScreenDecorations$2$$ExternalSyntheticLambda0;
import com.android.systemui.doze.DozeMachine;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeTriggers$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ DozeTriggers f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ float f$4;
    public final /* synthetic */ float f$5;
    public final /* synthetic */ boolean f$6;
    public final /* synthetic */ boolean f$7;
    public final /* synthetic */ float[] f$8;

    public /* synthetic */ DozeTriggers$$ExternalSyntheticLambda5(DozeTriggers dozeTriggers, int i, boolean z, boolean z2, float f, float f2, boolean z3, boolean z4, float[] fArr) {
        this.f$0 = dozeTriggers;
        this.f$1 = i;
        this.f$2 = z;
        this.f$3 = z2;
        this.f$4 = f;
        this.f$5 = f2;
        this.f$6 = z3;
        this.f$7 = z4;
        this.f$8 = fArr;
    }

    public final void accept(Object obj) {
        DozeTriggers dozeTriggers = this.f$0;
        int i = this.f$1;
        boolean z = this.f$2;
        boolean z2 = this.f$3;
        float f = this.f$4;
        float f2 = this.f$5;
        boolean z3 = this.f$6;
        boolean z4 = this.f$7;
        float[] fArr = this.f$8;
        Boolean bool = (Boolean) obj;
        if (bool != null) {
            Objects.requireNonNull(dozeTriggers);
            if (bool.booleanValue()) {
                dozeTriggers.mDozeLog.traceSensorEventDropped(i, "prox reporting near");
                return;
            }
        }
        if (z || z2) {
            if (!(f == -1.0f || f2 == -1.0f)) {
                dozeTriggers.mDozeHost.onSlpiTap(f, f2);
            }
            dozeTriggers.gentleWakeUp(i);
            return;
        }
        boolean z5 = true;
        if (z3) {
            Objects.requireNonNull(dozeTriggers);
            if (!dozeTriggers.mKeyguardStateController.isOccluded() && !dozeTriggers.mBatteryController.isPluggedInWireless()) {
                z5 = false;
            }
            if (z5) {
                DozeLog dozeLog = dozeTriggers.mDozeLog;
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("keyguardOccluded=");
                m.append(dozeTriggers.mKeyguardStateController.isOccluded());
                m.append(" pluggedInWireless=");
                m.append(dozeTriggers.mBatteryController.isPluggedInWireless());
                dozeLog.traceSensorEventDropped(i, m.toString());
                return;
            }
            dozeTriggers.gentleWakeUp(i);
        } else if (z4) {
            DozeMachine.State state = dozeTriggers.mMachine.getState();
            if (state == DozeMachine.State.DOZE_AOD || state == DozeMachine.State.DOZE) {
                dozeTriggers.mAodInterruptRunnable = new DozeTriggers$$ExternalSyntheticLambda1(dozeTriggers, f, f2, fArr);
            }
            dozeTriggers.requestPulse(10, true, (ScreenDecorations$2$$ExternalSyntheticLambda0) null);
        } else {
            dozeTriggers.mDozeHost.extendPulse(i);
        }
    }
}
