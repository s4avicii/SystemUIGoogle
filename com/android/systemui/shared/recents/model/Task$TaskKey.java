package com.android.systemui.shared.recents.model;

import android.content.ComponentName;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewDebug;
import java.util.Objects;

public class Task$TaskKey implements Parcelable {
    public static final Parcelable.Creator<Task$TaskKey> CREATOR = new Parcelable.Creator<Task$TaskKey>() {
        public final Object createFromParcel(Parcel parcel) {
            return new Task$TaskKey(parcel.readInt(), parcel.readInt(), (Intent) parcel.readTypedObject(Intent.CREATOR), (ComponentName) parcel.readTypedObject(ComponentName.CREATOR), parcel.readInt(), parcel.readLong(), parcel.readInt());
        }

        public final Object[] newArray(int i) {
            return new Task$TaskKey[i];
        }
    };
    @ViewDebug.ExportedProperty(category = "recents")
    public final Intent baseIntent;
    @ViewDebug.ExportedProperty(category = "recents")
    public final int displayId;
    @ViewDebug.ExportedProperty(category = "recents")

    /* renamed from: id */
    public final int f74id;
    @ViewDebug.ExportedProperty(category = "recents")
    public long lastActiveTime;
    public int mHashCode;
    public final ComponentName sourceComponent;
    @ViewDebug.ExportedProperty(category = "recents")
    public final int userId;
    @ViewDebug.ExportedProperty(category = "recents")
    public int windowingMode;

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Task$TaskKey)) {
            return false;
        }
        Task$TaskKey task$TaskKey = (Task$TaskKey) obj;
        if (this.f74id == task$TaskKey.f74id && this.windowingMode == task$TaskKey.windowingMode && this.userId == task$TaskKey.userId) {
            return true;
        }
        return false;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("id=");
        m.append(this.f74id);
        m.append(" windowingMode=");
        m.append(this.windowingMode);
        m.append(" user=");
        m.append(this.userId);
        m.append(" lastActiveTime=");
        m.append(this.lastActiveTime);
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f74id);
        parcel.writeInt(this.windowingMode);
        parcel.writeTypedObject(this.baseIntent, i);
        parcel.writeInt(this.userId);
        parcel.writeLong(this.lastActiveTime);
        parcel.writeInt(this.displayId);
        parcel.writeTypedObject(this.sourceComponent, i);
    }

    public Task$TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4) {
        this.f74id = i;
        this.windowingMode = i2;
        this.baseIntent = intent;
        this.sourceComponent = componentName;
        this.userId = i3;
        this.lastActiveTime = j;
        this.displayId = i4;
        this.mHashCode = Objects.hash(new Object[]{Integer.valueOf(i), Integer.valueOf(this.windowingMode), Integer.valueOf(i3)});
    }

    public final int hashCode() {
        return this.mHashCode;
    }
}
