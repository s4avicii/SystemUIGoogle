package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Icon;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.ControlStatus;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsModel.kt */
public final class ControlStatusWrapper extends ElementWrapper implements ControlInterface {
    public final ControlStatus controlStatus;

    public ControlStatusWrapper(ControlStatus controlStatus2) {
        super(0);
        this.controlStatus = controlStatus2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ControlStatusWrapper) && Intrinsics.areEqual(this.controlStatus, ((ControlStatusWrapper) obj).controlStatus);
    }

    public final String getControlId() {
        return this.controlStatus.getControlId();
    }

    public final Icon getCustomIcon() {
        return this.controlStatus.getCustomIcon();
    }

    public final int getDeviceType() {
        return this.controlStatus.getDeviceType();
    }

    public final CharSequence getSubtitle() {
        return this.controlStatus.getSubtitle();
    }

    public final CharSequence getTitle() {
        return this.controlStatus.getTitle();
    }

    public final int hashCode() {
        return this.controlStatus.hashCode();
    }

    public final ComponentName getComponent() {
        ControlStatus controlStatus2 = this.controlStatus;
        Objects.requireNonNull(controlStatus2);
        return controlStatus2.component;
    }

    public final boolean getFavorite() {
        ControlStatus controlStatus2 = this.controlStatus;
        Objects.requireNonNull(controlStatus2);
        return controlStatus2.favorite;
    }

    public final boolean getRemoved() {
        ControlStatus controlStatus2 = this.controlStatus;
        Objects.requireNonNull(controlStatus2);
        return controlStatus2.removed;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ControlStatusWrapper(controlStatus=");
        m.append(this.controlStatus);
        m.append(')');
        return m.toString();
    }
}
