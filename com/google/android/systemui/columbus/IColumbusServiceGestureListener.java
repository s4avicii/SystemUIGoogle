package com.google.android.systemui.columbus;

import android.os.IInterface;
import android.os.RemoteException;

public interface IColumbusServiceGestureListener extends IInterface {
    void onTrigger() throws RemoteException;
}
