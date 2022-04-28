package com.android.systemui.shared.system;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.AnnotationValidations;

public class RemoteTransitionCompat implements Parcelable {
    public static final Parcelable.Creator<RemoteTransitionCompat> CREATOR = new Parcelable.Creator<RemoteTransitionCompat>() {
        public final Object createFromParcel(Parcel parcel) {
            return new RemoteTransitionCompat(parcel);
        }

        public final Object[] newArray(int i) {
            return new RemoteTransitionCompat[i];
        }
    };
    public TransitionFilter mFilter = null;
    public final RemoteTransition mTransition;

    @VisibleForTesting
    public static class RecentsControllerWrap extends RecentsAnimationControllerCompat {
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        byte b;
        if (this.mFilter != null) {
            b = (byte) 2;
        } else {
            b = 0;
        }
        parcel.writeByte(b);
        parcel.writeTypedObject(this.mTransition, i);
        TransitionFilter transitionFilter = this.mFilter;
        if (transitionFilter != null) {
            parcel.writeTypedObject(transitionFilter, i);
        }
    }

    public RemoteTransitionCompat(Parcel parcel) {
        TransitionFilter transitionFilter;
        byte readByte = parcel.readByte();
        RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
        if ((readByte & 2) == 0) {
            transitionFilter = null;
        } else {
            transitionFilter = (TransitionFilter) parcel.readTypedObject(TransitionFilter.CREATOR);
        }
        this.mTransition = remoteTransition;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, remoteTransition);
        this.mFilter = transitionFilter;
    }
}
