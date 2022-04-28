package com.android.p012wm.shell.bubbles;

import android.graphics.Bitmap;
import android.graphics.Path;

/* renamed from: com.android.wm.shell.bubbles.BubbleViewProvider */
public interface BubbleViewProvider {
    Bitmap getAppBadge();

    Bitmap getBubbleIcon();

    int getDotColor();

    Path getDotPath();

    BubbleExpandedView getExpandedView();

    BadgedImageView getIconView$1();

    String getKey();

    int getTaskId();

    void setTaskViewVisibility();

    boolean showDot();
}
