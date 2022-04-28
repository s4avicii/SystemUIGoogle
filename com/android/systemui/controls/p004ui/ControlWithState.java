package com.android.systemui.controls.p004ui;

import android.content.ComponentName;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.service.controls.Control;
import com.android.systemui.controls.controller.ControlInfo;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.ControlWithState */
/* compiled from: ControlWithState.kt */
public final class ControlWithState {

    /* renamed from: ci */
    public final ControlInfo f48ci;
    public final ComponentName componentName;
    public final Control control;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlWithState)) {
            return false;
        }
        ControlWithState controlWithState = (ControlWithState) obj;
        return Intrinsics.areEqual(this.componentName, controlWithState.componentName) && Intrinsics.areEqual(this.f48ci, controlWithState.f48ci) && Intrinsics.areEqual(this.control, controlWithState.control);
    }

    public final int hashCode() {
        int hashCode = (this.f48ci.hashCode() + (this.componentName.hashCode() * 31)) * 31;
        Control control2 = this.control;
        return hashCode + (control2 == null ? 0 : control2.hashCode());
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ControlWithState(componentName=");
        m.append(this.componentName);
        m.append(", ci=");
        m.append(this.f48ci);
        m.append(", control=");
        m.append(this.control);
        m.append(')');
        return m.toString();
    }

    public ControlWithState(ComponentName componentName2, ControlInfo controlInfo, Control control2) {
        this.componentName = componentName2;
        this.f48ci = controlInfo;
        this.control = control2;
    }
}
