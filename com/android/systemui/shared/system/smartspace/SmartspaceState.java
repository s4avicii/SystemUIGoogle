package com.android.systemui.shared.system.smartspace;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: SmartspaceState.kt */
public final class SmartspaceState implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR();
    public Rect boundsOnScreen = new Rect();
    public int selectedPage;
    public boolean visibleOnScreen;

    /* compiled from: SmartspaceState.kt */
    public static final class CREATOR implements Parcelable.Creator<SmartspaceState> {
        public final Object createFromParcel(Parcel parcel) {
            SmartspaceState smartspaceState = new SmartspaceState();
            smartspaceState.boundsOnScreen = (Rect) parcel.readParcelable(C11241.INSTANCE.getClass().getClassLoader());
            smartspaceState.selectedPage = parcel.readInt();
            smartspaceState.visibleOnScreen = parcel.readBoolean();
            return smartspaceState;
        }

        public final Object[] newArray(int i) {
            return new SmartspaceState[i];
        }
    }

    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("boundsOnScreen: ");
        m.append(this.boundsOnScreen);
        m.append(", selectedPage: ");
        m.append(this.selectedPage);
        m.append(", visibleOnScreen: ");
        m.append(this.visibleOnScreen);
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeParcelable(this.boundsOnScreen, 0);
        }
        if (parcel != null) {
            parcel.writeInt(this.selectedPage);
        }
        if (parcel != null) {
            parcel.writeBoolean(this.visibleOnScreen);
        }
    }
}
