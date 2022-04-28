package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Icon;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.controller.ControlInfo;
import java.util.Objects;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsModel.kt */
public final class ControlInfoWrapper extends ElementWrapper implements ControlInterface {
    public final ComponentName component;
    public final ControlInfo controlInfo;
    public Function2<? super ComponentName, ? super String, Icon> customIconGetter;
    public boolean favorite = true;

    public ControlInfoWrapper(ComponentName componentName, ControlInfo controlInfo2, Function2 function2) {
        super(0);
        this.component = componentName;
        this.controlInfo = controlInfo2;
        int i = ControlInfoWrapper$customIconGetter$1.$r8$clinit;
        this.customIconGetter = function2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlInfoWrapper)) {
            return false;
        }
        ControlInfoWrapper controlInfoWrapper = (ControlInfoWrapper) obj;
        ComponentName componentName = this.component;
        Objects.requireNonNull(controlInfoWrapper);
        return Intrinsics.areEqual(componentName, controlInfoWrapper.component) && Intrinsics.areEqual(this.controlInfo, controlInfoWrapper.controlInfo) && this.favorite == controlInfoWrapper.favorite;
    }

    public final boolean getRemoved() {
        return false;
    }

    public final String getControlId() {
        ControlInfo controlInfo2 = this.controlInfo;
        Objects.requireNonNull(controlInfo2);
        return controlInfo2.controlId;
    }

    public final Icon getCustomIcon() {
        return this.customIconGetter.invoke(this.component, getControlId());
    }

    public final int getDeviceType() {
        ControlInfo controlInfo2 = this.controlInfo;
        Objects.requireNonNull(controlInfo2);
        return controlInfo2.deviceType;
    }

    public final CharSequence getSubtitle() {
        ControlInfo controlInfo2 = this.controlInfo;
        Objects.requireNonNull(controlInfo2);
        return controlInfo2.controlSubtitle;
    }

    public final CharSequence getTitle() {
        ControlInfo controlInfo2 = this.controlInfo;
        Objects.requireNonNull(controlInfo2);
        return controlInfo2.controlTitle;
    }

    public final int hashCode() {
        int hashCode = (this.controlInfo.hashCode() + (this.component.hashCode() * 31)) * 31;
        boolean z = this.favorite;
        if (z) {
            z = true;
        }
        return hashCode + (z ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ControlInfoWrapper(component=");
        m.append(this.component);
        m.append(", controlInfo=");
        m.append(this.controlInfo);
        m.append(", favorite=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.favorite, ')');
    }

    public final ComponentName getComponent() {
        return this.component;
    }

    public final boolean getFavorite() {
        return this.favorite;
    }
}
