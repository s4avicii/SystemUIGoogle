package com.android.p012wm.shell.pip;

/* renamed from: com.android.wm.shell.pip.PipTransitionState */
public final class PipTransitionState {
    public boolean mInSwipePipToHomeTransition;
    public int mState = 0;

    public final boolean isInPip() {
        int i = this.mState;
        if (i < 1 || i == 5) {
            return false;
        }
        return true;
    }
}
