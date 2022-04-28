package com.android.systemui.media;

import android.app.smartspace.SmartspaceAction;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartspaceMediaData.kt */
public final class SmartspaceMediaData {
    public final int backgroundColor;
    public final SmartspaceAction cardAction;
    public final Intent dismissIntent;
    public final long headphoneConnectionTimeMillis;
    public final boolean isActive;
    public final boolean isValid;
    public final String packageName;
    public final List<SmartspaceAction> recommendations;
    public final String targetId;

    public static SmartspaceMediaData copy$default(SmartspaceMediaData smartspaceMediaData, String str, boolean z, boolean z2, Intent intent, int i, long j, int i2) {
        String str2;
        boolean z3;
        boolean z4;
        String str3;
        SmartspaceAction smartspaceAction;
        List<SmartspaceAction> list;
        Intent intent2;
        int i3;
        long j2;
        SmartspaceMediaData smartspaceMediaData2 = smartspaceMediaData;
        int i4 = i2;
        if ((i4 & 1) != 0) {
            str2 = smartspaceMediaData2.targetId;
        } else {
            str2 = str;
        }
        if ((i4 & 2) != 0) {
            z3 = smartspaceMediaData2.isActive;
        } else {
            z3 = z;
        }
        if ((i4 & 4) != 0) {
            z4 = smartspaceMediaData2.isValid;
        } else {
            z4 = z2;
        }
        if ((i4 & 8) != 0) {
            str3 = smartspaceMediaData2.packageName;
        } else {
            str3 = null;
        }
        if ((i4 & 16) != 0) {
            smartspaceAction = smartspaceMediaData2.cardAction;
        } else {
            smartspaceAction = null;
        }
        if ((i4 & 32) != 0) {
            list = smartspaceMediaData2.recommendations;
        } else {
            list = null;
        }
        if ((i4 & 64) != 0) {
            intent2 = smartspaceMediaData2.dismissIntent;
        } else {
            intent2 = intent;
        }
        if ((i4 & 128) != 0) {
            i3 = smartspaceMediaData2.backgroundColor;
        } else {
            i3 = i;
        }
        if ((i4 & 256) != 0) {
            j2 = smartspaceMediaData2.headphoneConnectionTimeMillis;
        } else {
            j2 = j;
        }
        Objects.requireNonNull(smartspaceMediaData);
        return new SmartspaceMediaData(str2, z3, z4, str3, smartspaceAction, list, intent2, i3, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SmartspaceMediaData)) {
            return false;
        }
        SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) obj;
        return Intrinsics.areEqual(this.targetId, smartspaceMediaData.targetId) && this.isActive == smartspaceMediaData.isActive && this.isValid == smartspaceMediaData.isValid && Intrinsics.areEqual(this.packageName, smartspaceMediaData.packageName) && Intrinsics.areEqual(this.cardAction, smartspaceMediaData.cardAction) && Intrinsics.areEqual(this.recommendations, smartspaceMediaData.recommendations) && Intrinsics.areEqual(this.dismissIntent, smartspaceMediaData.dismissIntent) && this.backgroundColor == smartspaceMediaData.backgroundColor && this.headphoneConnectionTimeMillis == smartspaceMediaData.headphoneConnectionTimeMillis;
    }

    public final int hashCode() {
        int i;
        int hashCode = this.targetId.hashCode() * 31;
        boolean z = this.isActive;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i2 = (hashCode + (z ? 1 : 0)) * 31;
        boolean z3 = this.isValid;
        if (!z3) {
            z2 = z3;
        }
        int hashCode2 = (this.packageName.hashCode() + ((i2 + (z2 ? 1 : 0)) * 31)) * 31;
        SmartspaceAction smartspaceAction = this.cardAction;
        int i3 = 0;
        if (smartspaceAction == null) {
            i = 0;
        } else {
            i = smartspaceAction.hashCode();
        }
        int hashCode3 = (this.recommendations.hashCode() + ((hashCode2 + i) * 31)) * 31;
        Intent intent = this.dismissIntent;
        if (intent != null) {
            i3 = intent.hashCode();
        }
        return Long.hashCode(this.headphoneConnectionTimeMillis) + FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.backgroundColor, (hashCode3 + i3) * 31, 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SmartspaceMediaData(targetId=");
        m.append(this.targetId);
        m.append(", isActive=");
        m.append(this.isActive);
        m.append(", isValid=");
        m.append(this.isValid);
        m.append(", packageName=");
        m.append(this.packageName);
        m.append(", cardAction=");
        m.append(this.cardAction);
        m.append(", recommendations=");
        m.append(this.recommendations);
        m.append(", dismissIntent=");
        m.append(this.dismissIntent);
        m.append(", backgroundColor=");
        m.append(this.backgroundColor);
        m.append(", headphoneConnectionTimeMillis=");
        m.append(this.headphoneConnectionTimeMillis);
        m.append(')');
        return m.toString();
    }

    public SmartspaceMediaData(String str, boolean z, boolean z2, String str2, SmartspaceAction smartspaceAction, List<SmartspaceAction> list, Intent intent, int i, long j) {
        this.targetId = str;
        this.isActive = z;
        this.isValid = z2;
        this.packageName = str2;
        this.cardAction = smartspaceAction;
        this.recommendations = list;
        this.dismissIntent = intent;
        this.backgroundColor = i;
        this.headphoneConnectionTimeMillis = j;
    }
}
