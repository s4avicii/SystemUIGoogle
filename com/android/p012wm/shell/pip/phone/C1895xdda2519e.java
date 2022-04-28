package com.android.p012wm.shell.pip.phone;

import android.graphics.Region;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.p012wm.shell.pip.phone.PipAccessibilityInteractionConnection;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1895xdda2519e implements Runnable {
    public final /* synthetic */ PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ IAccessibilityInteractionConnectionCallback f$4;

    public /* synthetic */ C1895xdda2519e(PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl, long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, Bundle bundle) {
        this.f$0 = pipAccessibilityInteractionConnectionImpl;
        this.f$1 = j;
        this.f$3 = i;
        this.f$4 = iAccessibilityInteractionConnectionCallback;
    }

    public final void run() {
        List<AccessibilityNodeInfo> list;
        PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl = this.f$0;
        long j = this.f$1;
        int i = this.f$3;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = this.f$4;
        Objects.requireNonNull(pipAccessibilityInteractionConnectionImpl);
        PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection = PipAccessibilityInteractionConnection.this;
        Objects.requireNonNull(pipAccessibilityInteractionConnection);
        try {
            if (j == AccessibilityNodeInfo.ROOT_NODE_ID) {
                list = pipAccessibilityInteractionConnection.getNodeList();
            } else {
                list = null;
            }
            iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(list, i);
        } catch (RemoteException unused) {
        }
    }
}
