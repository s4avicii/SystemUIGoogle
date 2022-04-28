package com.android.systemui.privacy;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyItem.kt */
public final class PrivacyItem {
    public final PrivacyApplication application;
    public final String log;
    public final boolean paused;
    public final PrivacyType privacyType;
    public final long timeStampElapsed;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PrivacyItem)) {
            return false;
        }
        PrivacyItem privacyItem = (PrivacyItem) obj;
        return this.privacyType == privacyItem.privacyType && Intrinsics.areEqual(this.application, privacyItem.application) && this.timeStampElapsed == privacyItem.timeStampElapsed && this.paused == privacyItem.paused;
    }

    public final int hashCode() {
        int hashCode = this.application.hashCode();
        int hashCode2 = (Long.hashCode(this.timeStampElapsed) + ((hashCode + (this.privacyType.hashCode() * 31)) * 31)) * 31;
        boolean z = this.paused;
        if (z) {
            z = true;
        }
        return hashCode2 + (z ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("PrivacyItem(privacyType=");
        m.append(this.privacyType);
        m.append(", application=");
        m.append(this.application);
        m.append(", timeStampElapsed=");
        m.append(this.timeStampElapsed);
        m.append(", paused=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.paused, ')');
    }

    public PrivacyItem(PrivacyType privacyType2, PrivacyApplication privacyApplication, long j, boolean z) {
        this.privacyType = privacyType2;
        this.application = privacyApplication;
        this.timeStampElapsed = j;
        this.paused = z;
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('(');
        m.append(privacyType2.getLogName());
        m.append(", ");
        m.append(privacyApplication.packageName);
        m.append('(');
        m.append(privacyApplication.uid);
        m.append("), ");
        m.append(j);
        m.append(", paused=");
        m.append(z);
        m.append(')');
        this.log = m.toString();
    }
}
