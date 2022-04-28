package com.android.p012wm.shell.pip;

import android.os.IInterface;
import android.os.RemoteException;

/* renamed from: com.android.wm.shell.pip.IPipAnimationListener */
public interface IPipAnimationListener extends IInterface {
    void onPipAnimationStarted() throws RemoteException;

    void onPipCornerRadiusChanged(int i) throws RemoteException;
}
