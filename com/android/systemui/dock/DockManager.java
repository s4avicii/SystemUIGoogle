package com.android.systemui.dock;

import com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda0;

public interface DockManager {

    public interface AlignmentStateListener {
        void onAlignmentStateChanged(int i);
    }

    public interface DockEventListener {
        void onEvent(int i);
    }

    void addAlignmentStateListener(KeyguardIndicationController$$ExternalSyntheticLambda0 keyguardIndicationController$$ExternalSyntheticLambda0);

    void addListener(DockEventListener dockEventListener);

    boolean isDocked();

    boolean isHidden();

    void removeListener(DockEventListener dockEventListener);
}
