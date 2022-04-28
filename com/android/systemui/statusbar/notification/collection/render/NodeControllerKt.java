package com.android.systemui.statusbar.notification.collection.render;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: NodeController.kt */
public final class NodeControllerKt {
    public static final void treeSpecToStrHelper(NodeSpec nodeSpec, StringBuilder sb, String str) {
        sb.append(str + '{' + nodeSpec.getController().getNodeLabel() + "}\n");
        if (!nodeSpec.getChildren().isEmpty()) {
            String stringPlus = Intrinsics.stringPlus(str, "  ");
            for (NodeSpec treeSpecToStrHelper : nodeSpec.getChildren()) {
                treeSpecToStrHelper(treeSpecToStrHelper, sb, stringPlus);
            }
        }
    }
}
