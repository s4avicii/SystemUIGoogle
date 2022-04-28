package com.android.systemui.statusbar.notification.collection.render;

import android.view.View;

/* compiled from: NodeController.kt */
public interface NodeController {
    void addChildAt(NodeController nodeController, int i);

    View getChildAt(int i);

    int getChildCount();

    String getNodeLabel();

    View getView();

    void moveChildTo(NodeController nodeController, int i);

    void onViewAdded();

    void onViewMoved();

    void onViewRemoved();

    void removeChild(NodeController nodeController, boolean z);
}
