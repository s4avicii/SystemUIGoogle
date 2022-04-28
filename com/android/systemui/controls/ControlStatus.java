package com.android.systemui.controls;

import android.content.ComponentName;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Icon;
import android.service.controls.Control;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlStatus.kt */
public final class ControlStatus implements ControlInterface {
    public final ComponentName component;
    public final Control control;
    public boolean favorite;
    public final boolean removed;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlStatus)) {
            return false;
        }
        ControlStatus controlStatus = (ControlStatus) obj;
        return Intrinsics.areEqual(this.control, controlStatus.control) && Intrinsics.areEqual(this.component, controlStatus.component) && this.favorite == controlStatus.favorite && this.removed == controlStatus.removed;
    }

    public final String getControlId() {
        return this.control.getControlId();
    }

    public final Icon getCustomIcon() {
        return this.control.getCustomIcon();
    }

    public final int getDeviceType() {
        return this.control.getDeviceType();
    }

    public final CharSequence getSubtitle() {
        return this.control.getSubtitle();
    }

    public final CharSequence getTitle() {
        return this.control.getTitle();
    }

    public final int hashCode() {
        int hashCode = (this.component.hashCode() + (this.control.hashCode() * 31)) * 31;
        boolean z = this.favorite;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i = (hashCode + (z ? 1 : 0)) * 31;
        boolean z3 = this.removed;
        if (!z3) {
            z2 = z3;
        }
        return i + (z2 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ControlStatus(control=");
        m.append(this.control);
        m.append(", component=");
        m.append(this.component);
        m.append(", favorite=");
        m.append(this.favorite);
        m.append(", removed=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.removed, ')');
    }

    public ControlStatus(Control control2, ComponentName componentName, boolean z, boolean z2) {
        this.control = control2;
        this.component = componentName;
        this.favorite = z;
        this.removed = z2;
    }

    public final ComponentName getComponent() {
        return this.component;
    }

    public final boolean getFavorite() {
        return this.favorite;
    }

    public final boolean getRemoved() {
        return this.removed;
    }
}
