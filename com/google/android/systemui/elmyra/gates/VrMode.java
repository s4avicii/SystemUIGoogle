package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.Log;

public final class VrMode extends Gate {
    public boolean mInVrMode;
    public final IVrManager mVrManager = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
    public final C22641 mVrStateCallbacks = new IVrStateCallbacks.Stub() {
        public final void onVrStateChanged(boolean z) {
            VrMode vrMode = VrMode.this;
            if (z != vrMode.mInVrMode) {
                vrMode.mInVrMode = z;
                vrMode.notifyListener();
            }
        }
    };

    public final void onActivate() {
        IVrManager iVrManager = this.mVrManager;
        if (iVrManager != null) {
            try {
                this.mInVrMode = iVrManager.getVrModeState();
                this.mVrManager.registerListener(this.mVrStateCallbacks);
            } catch (RemoteException e) {
                Log.e("Elmyra/VrMode", "Could not register IVrManager listener", e);
                this.mInVrMode = false;
            }
        }
    }

    public final void onDeactivate() {
        IVrManager iVrManager = this.mVrManager;
        if (iVrManager != null) {
            try {
                iVrManager.unregisterListener(this.mVrStateCallbacks);
            } catch (RemoteException e) {
                Log.e("Elmyra/VrMode", "Could not unregister IVrManager listener", e);
                this.mInVrMode = false;
            }
        }
    }

    public VrMode(Context context) {
        super(context);
    }

    public final boolean isBlocked() {
        return this.mInVrMode;
    }
}
