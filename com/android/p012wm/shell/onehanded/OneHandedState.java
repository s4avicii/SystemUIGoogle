package com.android.p012wm.shell.onehanded;

import java.util.ArrayList;

/* renamed from: com.android.wm.shell.onehanded.OneHandedState */
public final class OneHandedState {
    public static int sCurrentState;
    public ArrayList mStateChangeListeners = new ArrayList();

    /* renamed from: com.android.wm.shell.onehanded.OneHandedState$OnStateChangedListener */
    public interface OnStateChangedListener {
        void onStateChanged(int i) {
        }
    }

    public final void setState(int i) {
        sCurrentState = i;
        if (!this.mStateChangeListeners.isEmpty()) {
            this.mStateChangeListeners.forEach(new OneHandedState$$ExternalSyntheticLambda0(i));
        }
    }

    public OneHandedState() {
        sCurrentState = 0;
    }
}
