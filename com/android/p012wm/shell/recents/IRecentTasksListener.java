package com.android.p012wm.shell.recents;

import android.os.IInterface;
import android.os.RemoteException;

/* renamed from: com.android.wm.shell.recents.IRecentTasksListener */
public interface IRecentTasksListener extends IInterface {
    void onRecentTasksChanged() throws RemoteException;
}
