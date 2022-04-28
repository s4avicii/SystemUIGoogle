package com.android.systemui.statusbar.notification.collection.render;

import android.view.LayoutInflater;
import android.view.View;
import com.android.systemui.statusbar.notification.stack.MediaContainerView;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaContainerController.kt */
public final class MediaContainerController implements NodeController {
    public final LayoutInflater layoutInflater;
    public MediaContainerView mediaContainerView;

    public final int getChildCount() {
        return 0;
    }

    public final String getNodeLabel() {
        return "MediaContainer";
    }

    public final void onViewAdded() {
    }

    public final void onViewMoved() {
    }

    public final void onViewRemoved() {
    }

    public final void addChildAt(NodeController nodeController, int i) {
        throw new RuntimeException("Not supported");
    }

    public final View getChildAt(int i) {
        throw new RuntimeException("Not supported");
    }

    public final View getView() {
        MediaContainerView mediaContainerView2 = this.mediaContainerView;
        Intrinsics.checkNotNull(mediaContainerView2);
        return mediaContainerView2;
    }

    public final void moveChildTo(NodeController nodeController, int i) {
        throw new RuntimeException("Not supported");
    }

    public final void removeChild(NodeController nodeController, boolean z) {
        throw new RuntimeException("Not supported");
    }

    public MediaContainerController(LayoutInflater layoutInflater2) {
        this.layoutInflater = layoutInflater2;
    }
}
