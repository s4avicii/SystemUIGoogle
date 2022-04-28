package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.LinkedHashSet;

/* compiled from: Gate.kt */
public abstract class Gate {
    public boolean active;
    public final Context context;
    public boolean isBlocked;
    public final LinkedHashSet listeners;
    public final Handler notifyHandler;

    /* compiled from: Gate.kt */
    public interface Listener {
        void onGateChanged(Gate gate);
    }

    public Gate(Context context2, Handler handler) {
        this.context = context2;
        this.notifyHandler = handler;
        this.listeners = new LinkedHashSet();
    }

    public abstract void onActivate();

    public abstract void onDeactivate();

    public final boolean isBlocking() {
        if (!this.active || !this.isBlocked) {
            return false;
        }
        return true;
    }

    public final void registerListener(Listener listener) {
        this.listeners.add(listener);
        if (!this.active && (!this.listeners.isEmpty())) {
            this.active = true;
            onActivate();
        }
    }

    public final void setBlocking(boolean z) {
        if (this.isBlocked != z) {
            this.isBlocked = z;
            if (this.active) {
                for (Listener gate$notifyListeners$1$1 : this.listeners) {
                    this.notifyHandler.post(new Gate$notifyListeners$1$1(gate$notifyListeners$1$1, this));
                }
            }
        }
    }

    public final void unregisterListener(Listener listener) {
        this.listeners.remove(listener);
        if (this.active && this.listeners.isEmpty()) {
            this.active = false;
            onDeactivate();
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public /* synthetic */ Gate(Context context2) {
        this(context2, new Handler(Looper.getMainLooper()));
    }
}
