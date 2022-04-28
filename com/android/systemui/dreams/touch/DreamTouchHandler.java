package com.android.systemui.dreams.touch;

import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;

public interface DreamTouchHandler {

    public interface TouchSession {

        public interface Callback {
            void onRemoved();
        }
    }

    void onSessionStart(DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl);
}
