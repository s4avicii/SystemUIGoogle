package com.android.systemui.statusbar.notification.collection.render;

import java.util.ArrayList;

/* compiled from: NodeController.kt */
public interface NodeSpec {
    ArrayList getChildren();

    NodeController getController();

    NodeSpec getParent();
}
