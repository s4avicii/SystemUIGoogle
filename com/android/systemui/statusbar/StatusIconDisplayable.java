package com.android.systemui.statusbar;

import com.android.systemui.plugins.DarkIconDispatcher;

public interface StatusIconDisplayable extends DarkIconDispatcher.DarkReceiver {
    String getSlot();

    int getVisibleState();

    boolean isIconBlocked() {
        return false;
    }

    boolean isIconVisible();

    void setDecorColor(int i);

    void setStaticDrawableColor(int i);

    void setVisibleState(int i) {
        setVisibleState(2, false);
    }

    void setVisibleState(int i, boolean z);
}
