package com.android.p012wm.shell.startingsurface;

import android.os.IInterface;
import android.os.RemoteException;

/* renamed from: com.android.wm.shell.startingsurface.IStartingWindowListener */
public interface IStartingWindowListener extends IInterface {
    void onTaskLaunching(int i, int i2, int i3) throws RemoteException;
}
