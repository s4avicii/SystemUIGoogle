package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.net.NetworkPolicyManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.android.systemui.statusbar.policy.DataSaverController;
import java.util.ArrayList;
import java.util.Objects;

public final class DataSaverControllerImpl implements DataSaverController {
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final ArrayList<DataSaverController.Listener> mListeners = new ArrayList<>();
    public final C16081 mPolicyListener = new NetworkPolicyManager.Listener() {
        public final void onRestrictBackgroundChanged(final boolean z) {
            DataSaverControllerImpl.this.mHandler.post(new Runnable() {
                public final void run() {
                    DataSaverControllerImpl dataSaverControllerImpl = DataSaverControllerImpl.this;
                    boolean z = z;
                    Objects.requireNonNull(dataSaverControllerImpl);
                    synchronized (dataSaverControllerImpl.mListeners) {
                        for (int i = 0; i < dataSaverControllerImpl.mListeners.size(); i++) {
                            dataSaverControllerImpl.mListeners.get(i).onDataSaverChanged(z);
                        }
                    }
                }
            });
        }
    };
    public final NetworkPolicyManager mPolicyManager;

    public final void addCallback(Object obj) {
        DataSaverController.Listener listener = (DataSaverController.Listener) obj;
        synchronized (this.mListeners) {
            this.mListeners.add(listener);
            if (this.mListeners.size() == 1) {
                this.mPolicyManager.registerListener(this.mPolicyListener);
            }
        }
        listener.onDataSaverChanged(isDataSaverEnabled());
    }

    public final boolean isDataSaverEnabled() {
        return this.mPolicyManager.getRestrictBackground();
    }

    public final void removeCallback(Object obj) {
        DataSaverController.Listener listener = (DataSaverController.Listener) obj;
        synchronized (this.mListeners) {
            this.mListeners.remove(listener);
            if (this.mListeners.size() == 0) {
                this.mPolicyManager.unregisterListener(this.mPolicyListener);
            }
        }
    }

    public final void setDataSaverEnabled(boolean z) {
        this.mPolicyManager.setRestrictBackground(z);
        try {
            this.mPolicyListener.onRestrictBackgroundChanged(z);
        } catch (RemoteException unused) {
        }
    }

    public DataSaverControllerImpl(Context context) {
        this.mPolicyManager = NetworkPolicyManager.from(context);
    }
}
