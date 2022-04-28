package com.android.p012wm.shell.splitscreen;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreen */
public interface SplitScreen {

    /* renamed from: com.android.wm.shell.splitscreen.SplitScreen$SplitScreenListener */
    public interface SplitScreenListener {
        void onSplitVisibilityChanged() {
        }

        void onStagePositionChanged(int i, int i2) {
        }

        void onTaskStageChanged(int i, int i2, boolean z) {
        }
    }

    ISplitScreen createExternalInterface() {
        return null;
    }

    void onFinishedWakingUp();

    void onKeyguardVisibilityChanged(boolean z);
}
