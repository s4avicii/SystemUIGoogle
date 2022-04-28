package com.google.android.systemui.elmyra;

import android.os.IInterface;
import android.os.RemoteException;

public interface IElmyraServiceGestureListener extends IInterface {
    void onGestureDetected() throws RemoteException;

    void onGestureProgress(float f, int i) throws RemoteException;
}
