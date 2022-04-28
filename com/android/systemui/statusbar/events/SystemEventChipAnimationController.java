package com.android.systemui.statusbar.events;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher;
import com.android.systemui.statusbar.window.StatusBarWindowController;

/* compiled from: SystemEventChipAnimationController.kt */
public final class SystemEventChipAnimationController {
    public View animationDotView;
    public FrameLayout animationWindowView;
    public final Context context;
    public View currentAnimatedView;
    public boolean initialized;
    public final StatusBarLocationPublisher locationPublisher;
    public final StatusBarWindowController statusBarWindowController;

    public SystemEventChipAnimationController(Context context2, StatusBarWindowController statusBarWindowController2, StatusBarLocationPublisher statusBarLocationPublisher) {
        this.context = context2;
        this.statusBarWindowController = statusBarWindowController2;
        this.locationPublisher = statusBarLocationPublisher;
    }
}
