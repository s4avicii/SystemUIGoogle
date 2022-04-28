package com.android.p012wm.shell.util;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* renamed from: com.android.wm.shell.util.StagedSplitBounds */
public class StagedSplitBounds implements Parcelable {
    public static final Parcelable.Creator<StagedSplitBounds> CREATOR = new Parcelable.Creator<StagedSplitBounds>() {
        public final Object createFromParcel(Parcel parcel) {
            return new StagedSplitBounds(parcel);
        }

        public final Object[] newArray(int i) {
            return new StagedSplitBounds[i];
        }
    };
    public final boolean appsStackedVertically;
    public final float leftTaskPercent;
    public final Rect leftTopBounds;
    public final int leftTopTaskId;
    public final Rect rightBottomBounds;
    public final int rightBottomTaskId;
    public final float topTaskPercent;
    public final Rect visualDividerBounds;

    public StagedSplitBounds(Rect rect, Rect rect2, int i, int i2) {
        this.leftTopBounds = rect;
        this.rightBottomBounds = rect2;
        this.leftTopTaskId = i;
        this.rightBottomTaskId = i2;
        if (rect2.top > rect.top) {
            this.visualDividerBounds = new Rect(rect.left, rect.bottom, rect.right, rect2.top);
            this.appsStackedVertically = true;
        } else {
            this.visualDividerBounds = new Rect(rect.right, rect.top, rect2.left, rect.bottom);
            this.appsStackedVertically = false;
        }
        this.leftTaskPercent = ((float) rect.width()) / ((float) rect2.right);
        this.topTaskPercent = ((float) rect.height()) / ((float) rect2.bottom);
    }

    public final int describeContents() {
        return 0;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{this.leftTopBounds, this.rightBottomBounds, Integer.valueOf(this.leftTopTaskId), Integer.valueOf(this.rightBottomTaskId)});
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof StagedSplitBounds)) {
            return false;
        }
        StagedSplitBounds stagedSplitBounds = (StagedSplitBounds) obj;
        if (!Objects.equals(this.leftTopBounds, stagedSplitBounds.leftTopBounds) || !Objects.equals(this.rightBottomBounds, stagedSplitBounds.rightBottomBounds) || this.leftTopTaskId != stagedSplitBounds.leftTopTaskId || this.rightBottomTaskId != stagedSplitBounds.rightBottomTaskId) {
            return false;
        }
        return true;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("LeftTop: ");
        m.append(this.leftTopBounds);
        m.append(", taskId: ");
        m.append(this.leftTopTaskId);
        m.append("\nRightBottom: ");
        m.append(this.rightBottomBounds);
        m.append(", taskId: ");
        m.append(this.rightBottomTaskId);
        m.append("\nDivider: ");
        m.append(this.visualDividerBounds);
        m.append("\nAppsVertical? ");
        m.append(this.appsStackedVertically);
        return m.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.leftTopBounds, i);
        parcel.writeTypedObject(this.rightBottomBounds, i);
        parcel.writeTypedObject(this.visualDividerBounds, i);
        parcel.writeFloat(this.topTaskPercent);
        parcel.writeFloat(this.leftTaskPercent);
        parcel.writeBoolean(this.appsStackedVertically);
        parcel.writeInt(this.leftTopTaskId);
        parcel.writeInt(this.rightBottomTaskId);
    }

    public StagedSplitBounds(Parcel parcel) {
        this.leftTopBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.rightBottomBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.visualDividerBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.topTaskPercent = parcel.readFloat();
        this.leftTaskPercent = parcel.readFloat();
        this.appsStackedVertically = parcel.readBoolean();
        this.leftTopTaskId = parcel.readInt();
        this.rightBottomTaskId = parcel.readInt();
    }
}
