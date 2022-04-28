package com.android.systemui.statusbar.notification.collection.listbuilder;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotifSection.kt */
public final class NotifSection {
    public final int bucket;
    public final NotifComparator comparator;
    public final NodeController headerController;
    public final int index;
    public final NotifSectioner sectioner;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotifSection)) {
            return false;
        }
        NotifSection notifSection = (NotifSection) obj;
        return Intrinsics.areEqual(this.sectioner, notifSection.sectioner) && this.index == notifSection.index;
    }

    public final int hashCode() {
        return Integer.hashCode(this.index) + (this.sectioner.hashCode() * 31);
    }

    public final String getLabel() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Section(");
        m.append(this.index);
        m.append(", ");
        m.append(this.bucket);
        m.append(", \"");
        NotifSectioner notifSectioner = this.sectioner;
        Objects.requireNonNull(notifSectioner);
        m.append(notifSectioner.mName);
        m.append("\")");
        return m.toString();
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("NotifSection(sectioner=");
        m.append(this.sectioner);
        m.append(", index=");
        return Insets$$ExternalSyntheticOutline0.m11m(m, this.index, ')');
    }

    public NotifSection(NotifSectioner notifSectioner, int i) {
        this.sectioner = notifSectioner;
        this.index = i;
        this.headerController = notifSectioner.getHeaderNodeController();
        this.comparator = notifSectioner.getComparator();
        Objects.requireNonNull(notifSectioner);
        this.bucket = notifSectioner.mBucket;
    }
}
