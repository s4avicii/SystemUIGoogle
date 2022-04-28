package com.android.p012wm.shell.splitscreen;

import android.os.IInterface;
import android.os.RemoteException;

/* renamed from: com.android.wm.shell.splitscreen.ISplitScreenListener */
public interface ISplitScreenListener extends IInterface {
    void onStagePositionChanged(int i, int i2) throws RemoteException;

    void onTaskStageChanged(int i, int i2, boolean z) throws RemoteException;
}
