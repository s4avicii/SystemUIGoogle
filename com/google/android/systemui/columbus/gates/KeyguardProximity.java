package com.google.android.systemui.columbus.gates;

import android.content.Context;
import java.util.Objects;

/* compiled from: KeyguardProximity.kt */
public final class KeyguardProximity extends Gate {
    public boolean isListening;
    public final KeyguardVisibility keyguardGate;
    public final KeyguardProximity$keyguardListener$1 keyguardListener = new KeyguardProximity$keyguardListener$1(this);
    public final Proximity proximity;
    public final KeyguardProximity$proximityListener$1 proximityListener = new KeyguardProximity$proximityListener$1(this);

    public final void onActivate() {
        this.keyguardGate.registerListener(this.keyguardListener);
        updateProximityListener();
    }

    public final void onDeactivate() {
        this.keyguardGate.unregisterListener(this.keyguardListener);
        updateProximityListener();
    }

    public final String toString() {
        return super.toString() + " [isListening -> " + this.isListening + "; proximityBlocked -> " + this.proximity.isBlocking() + ']';
    }

    public final void updateProximityListener() {
        boolean z = true;
        if (this.active) {
            KeyguardVisibility keyguardVisibility = this.keyguardGate;
            Objects.requireNonNull(keyguardVisibility);
            if (keyguardVisibility.keyguardStateController.get().isShowing()) {
                KeyguardVisibility keyguardVisibility2 = this.keyguardGate;
                Objects.requireNonNull(keyguardVisibility2);
                if (!keyguardVisibility2.keyguardStateController.get().isOccluded()) {
                    if (!this.isListening) {
                        this.proximity.registerListener(this.proximityListener);
                        this.isListening = true;
                    }
                    if (!this.isListening || !this.proximity.isBlocking()) {
                        z = false;
                    }
                    setBlocking(z);
                }
            }
        }
        if (this.isListening) {
            this.proximity.unregisterListener(this.proximityListener);
            this.isListening = false;
        }
        z = false;
        setBlocking(z);
    }

    public KeyguardProximity(Context context, KeyguardVisibility keyguardVisibility, Proximity proximity2) {
        super(context);
        this.keyguardGate = keyguardVisibility;
        this.proximity = proximity2;
        updateProximityListener();
    }
}
