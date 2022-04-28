package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.vr.IVrManager;
import android.util.Log;

/* compiled from: VrMode.kt */
public final class VrMode extends Gate {
    public boolean inVrMode;
    public final IVrManager vrManager = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
    public final VrMode$vrStateCallbacks$1 vrStateCallbacks = new VrMode$vrStateCallbacks$1(this);

    public final void onActivate() {
        IVrManager iVrManager = this.vrManager;
        if (iVrManager != null) {
            try {
                boolean z = true;
                if (!iVrManager.getVrModeState()) {
                    z = false;
                }
                this.inVrMode = z;
                iVrManager.registerListener(this.vrStateCallbacks);
            } catch (RemoteException e) {
                Log.e("Columbus/VrMode", "Could not register IVrManager listener", e);
                this.inVrMode = false;
            }
        }
        setBlocking(this.inVrMode);
    }

    public final void onDeactivate() {
        try {
            IVrManager iVrManager = this.vrManager;
            if (iVrManager != null) {
                iVrManager.unregisterListener(this.vrStateCallbacks);
            }
        } catch (RemoteException e) {
            Log.e("Columbus/VrMode", "Could not unregister IVrManager listener", e);
        }
        this.inVrMode = false;
    }

    public VrMode(Context context) {
        super(context);
    }
}
