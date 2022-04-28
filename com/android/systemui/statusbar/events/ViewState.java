package com.android.systemui.statusbar.events;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.view.View;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyDotViewController.kt */
public final class ViewState {
    public final String contentDescription;
    public final int cornerIndex;
    public final View designatedCorner;
    public final Rect landscapeRect;
    public final boolean layoutRtl;
    public final int paddingTop;
    public final Rect portraitRect;
    public final boolean qsExpanded;
    public final int rotation;
    public final Rect seascapeRect;
    public final boolean shadeExpanded;
    public final boolean systemPrivacyEventIsActive;
    public final Rect upsideDownRect;
    public final boolean viewInitialized;

    public ViewState() {
        this(0);
    }

    public /* synthetic */ ViewState(int i) {
        this(false, false, false, false, (Rect) null, (Rect) null, (Rect) null, (Rect) null, false, 0, 0, -1, (View) null, (String) null);
    }

    public static ViewState copy$default(ViewState viewState, boolean z, boolean z2, boolean z3, boolean z4, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z5, int i, int i2, int i3, View view, String str, int i4) {
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        Rect rect5;
        Rect rect6;
        Rect rect7;
        Rect rect8;
        boolean z10;
        int i5;
        int i6;
        int i7;
        View view2;
        String str2;
        ViewState viewState2 = viewState;
        int i8 = i4;
        if ((i8 & 1) != 0) {
            z6 = viewState2.viewInitialized;
        } else {
            z6 = z;
        }
        if ((i8 & 2) != 0) {
            z7 = viewState2.systemPrivacyEventIsActive;
        } else {
            z7 = z2;
        }
        if ((i8 & 4) != 0) {
            z8 = viewState2.shadeExpanded;
        } else {
            z8 = z3;
        }
        if ((i8 & 8) != 0) {
            z9 = viewState2.qsExpanded;
        } else {
            z9 = z4;
        }
        if ((i8 & 16) != 0) {
            rect5 = viewState2.portraitRect;
        } else {
            rect5 = rect;
        }
        if ((i8 & 32) != 0) {
            rect6 = viewState2.landscapeRect;
        } else {
            rect6 = rect2;
        }
        if ((i8 & 64) != 0) {
            rect7 = viewState2.upsideDownRect;
        } else {
            rect7 = rect3;
        }
        if ((i8 & 128) != 0) {
            rect8 = viewState2.seascapeRect;
        } else {
            rect8 = rect4;
        }
        if ((i8 & 256) != 0) {
            z10 = viewState2.layoutRtl;
        } else {
            z10 = z5;
        }
        if ((i8 & 512) != 0) {
            i5 = viewState2.rotation;
        } else {
            i5 = i;
        }
        if ((i8 & 1024) != 0) {
            i6 = viewState2.paddingTop;
        } else {
            i6 = i2;
        }
        if ((i8 & 2048) != 0) {
            i7 = viewState2.cornerIndex;
        } else {
            i7 = i3;
        }
        if ((i8 & 4096) != 0) {
            view2 = viewState2.designatedCorner;
        } else {
            view2 = view;
        }
        if ((i8 & 8192) != 0) {
            str2 = viewState2.contentDescription;
        } else {
            str2 = str;
        }
        Objects.requireNonNull(viewState);
        return new ViewState(z6, z7, z8, z9, rect5, rect6, rect7, rect8, z10, i5, i6, i7, view2, str2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ViewState)) {
            return false;
        }
        ViewState viewState = (ViewState) obj;
        return this.viewInitialized == viewState.viewInitialized && this.systemPrivacyEventIsActive == viewState.systemPrivacyEventIsActive && this.shadeExpanded == viewState.shadeExpanded && this.qsExpanded == viewState.qsExpanded && Intrinsics.areEqual(this.portraitRect, viewState.portraitRect) && Intrinsics.areEqual(this.landscapeRect, viewState.landscapeRect) && Intrinsics.areEqual(this.upsideDownRect, viewState.upsideDownRect) && Intrinsics.areEqual(this.seascapeRect, viewState.seascapeRect) && this.layoutRtl == viewState.layoutRtl && this.rotation == viewState.rotation && this.paddingTop == viewState.paddingTop && this.cornerIndex == viewState.cornerIndex && Intrinsics.areEqual(this.designatedCorner, viewState.designatedCorner) && Intrinsics.areEqual(this.contentDescription, viewState.contentDescription);
    }

    public ViewState(boolean z, boolean z2, boolean z3, boolean z4, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z5, int i, int i2, int i3, View view, String str) {
        this.viewInitialized = z;
        this.systemPrivacyEventIsActive = z2;
        this.shadeExpanded = z3;
        this.qsExpanded = z4;
        this.portraitRect = rect;
        this.landscapeRect = rect2;
        this.upsideDownRect = rect3;
        this.seascapeRect = rect4;
        this.layoutRtl = z5;
        this.rotation = i;
        this.paddingTop = i2;
        this.cornerIndex = i3;
        this.designatedCorner = view;
        this.contentDescription = str;
    }

    public final Rect contentRectForRotation(int i) {
        if (i == 0) {
            Rect rect = this.portraitRect;
            Intrinsics.checkNotNull(rect);
            return rect;
        } else if (i == 1) {
            Rect rect2 = this.landscapeRect;
            Intrinsics.checkNotNull(rect2);
            return rect2;
        } else if (i == 2) {
            Rect rect3 = this.upsideDownRect;
            Intrinsics.checkNotNull(rect3);
            return rect3;
        } else if (i == 3) {
            Rect rect4 = this.seascapeRect;
            Intrinsics.checkNotNull(rect4);
            return rect4;
        } else {
            throw new IllegalArgumentException("not a rotation (" + i + ')');
        }
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z = this.viewInitialized;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i6 = (z ? 1 : 0) * true;
        boolean z3 = this.systemPrivacyEventIsActive;
        if (z3) {
            z3 = true;
        }
        int i7 = (i6 + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.shadeExpanded;
        if (z4) {
            z4 = true;
        }
        int i8 = (i7 + (z4 ? 1 : 0)) * 31;
        boolean z5 = this.qsExpanded;
        if (z5) {
            z5 = true;
        }
        int i9 = (i8 + (z5 ? 1 : 0)) * 31;
        Rect rect = this.portraitRect;
        int i10 = 0;
        if (rect == null) {
            i = 0;
        } else {
            i = rect.hashCode();
        }
        int i11 = (i9 + i) * 31;
        Rect rect2 = this.landscapeRect;
        if (rect2 == null) {
            i2 = 0;
        } else {
            i2 = rect2.hashCode();
        }
        int i12 = (i11 + i2) * 31;
        Rect rect3 = this.upsideDownRect;
        if (rect3 == null) {
            i3 = 0;
        } else {
            i3 = rect3.hashCode();
        }
        int i13 = (i12 + i3) * 31;
        Rect rect4 = this.seascapeRect;
        if (rect4 == null) {
            i4 = 0;
        } else {
            i4 = rect4.hashCode();
        }
        int i14 = (i13 + i4) * 31;
        boolean z6 = this.layoutRtl;
        if (!z6) {
            z2 = z6;
        }
        int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.cornerIndex, FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.paddingTop, FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.rotation, (i14 + (z2 ? 1 : 0)) * 31, 31), 31), 31);
        View view = this.designatedCorner;
        if (view == null) {
            i5 = 0;
        } else {
            i5 = view.hashCode();
        }
        int i15 = (m + i5) * 31;
        String str = this.contentDescription;
        if (str != null) {
            i10 = str.hashCode();
        }
        return i15 + i10;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ViewState(viewInitialized=");
        m.append(this.viewInitialized);
        m.append(", systemPrivacyEventIsActive=");
        m.append(this.systemPrivacyEventIsActive);
        m.append(", shadeExpanded=");
        m.append(this.shadeExpanded);
        m.append(", qsExpanded=");
        m.append(this.qsExpanded);
        m.append(", portraitRect=");
        m.append(this.portraitRect);
        m.append(", landscapeRect=");
        m.append(this.landscapeRect);
        m.append(", upsideDownRect=");
        m.append(this.upsideDownRect);
        m.append(", seascapeRect=");
        m.append(this.seascapeRect);
        m.append(", layoutRtl=");
        m.append(this.layoutRtl);
        m.append(", rotation=");
        m.append(this.rotation);
        m.append(", paddingTop=");
        m.append(this.paddingTop);
        m.append(", cornerIndex=");
        m.append(this.cornerIndex);
        m.append(", designatedCorner=");
        m.append(this.designatedCorner);
        m.append(", contentDescription=");
        m.append(this.contentDescription);
        m.append(')');
        return m.toString();
    }
}
