package com.android.systemui.flags;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: Flag.kt */
public final class ResourceBooleanFlag {

    /* renamed from: id */
    public final int f56id;
    public final int resourceId;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResourceBooleanFlag)) {
            return false;
        }
        ResourceBooleanFlag resourceBooleanFlag = (ResourceBooleanFlag) obj;
        int i = this.f56id;
        Objects.requireNonNull(resourceBooleanFlag);
        return i == resourceBooleanFlag.f56id && this.resourceId == resourceBooleanFlag.resourceId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.resourceId) + (Integer.hashCode(this.f56id) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ResourceBooleanFlag(id=");
        m.append(this.f56id);
        m.append(", resourceId=");
        return Insets$$ExternalSyntheticOutline0.m11m(m, this.resourceId, ')');
    }

    public ResourceBooleanFlag(int i, int i2) {
        this.f56id = i;
        this.resourceId = i2;
    }
}
