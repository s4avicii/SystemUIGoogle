package kotlinx.coroutines;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;

/* compiled from: JobSupport.kt */
public final class Empty implements Incomplete {
    public final boolean isActive;

    public final NodeList getList() {
        return null;
    }

    public final String toString() {
        String str;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Empty{");
        if (this.isActive) {
            str = "Active";
        } else {
            str = "New";
        }
        m.append(str);
        m.append('}');
        return m.toString();
    }

    public Empty(boolean z) {
        this.isActive = z;
    }

    public final boolean isActive() {
        return this.isActive;
    }
}
