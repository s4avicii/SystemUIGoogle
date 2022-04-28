package com.android.systemui.shared.system.smartspace;

import android.os.IInterface;
import android.os.RemoteException;

public interface ILauncherUnlockAnimationController extends IInterface {
    void playUnlockAnimation() throws RemoteException;

    void prepareForUnlock(boolean z, int i) throws RemoteException;

    void setSmartspaceVisibility(int i) throws RemoteException;

    void setUnlockAmount(float f) throws RemoteException;
}
