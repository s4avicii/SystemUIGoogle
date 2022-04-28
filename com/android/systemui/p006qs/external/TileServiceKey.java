package com.android.systemui.p006qs.external;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.qs.external.TileServiceKey */
/* compiled from: CustomTileStatePersister.kt */
public final class TileServiceKey {
    public final ComponentName componentName;
    public final String string;
    public final int user;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TileServiceKey)) {
            return false;
        }
        TileServiceKey tileServiceKey = (TileServiceKey) obj;
        return Intrinsics.areEqual(this.componentName, tileServiceKey.componentName) && this.user == tileServiceKey.user;
    }

    public final int hashCode() {
        return Integer.hashCode(this.user) + (this.componentName.hashCode() * 31);
    }

    public TileServiceKey(ComponentName componentName2, int i) {
        this.componentName = componentName2;
        this.user = i;
        this.string = componentName2.flattenToString() + ':' + i;
    }

    public final String toString() {
        return this.string;
    }
}
