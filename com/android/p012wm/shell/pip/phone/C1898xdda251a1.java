package com.android.p012wm.shell.pip.phone;

import android.graphics.Region;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.p012wm.shell.pip.phone.PipAccessibilityInteractionConnection;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1898xdda251a1 implements Runnable {
    public final /* synthetic */ PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl f$0;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ IAccessibilityInteractionConnectionCallback f$5;

    public /* synthetic */ C1898xdda251a1(PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl, long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec) {
        this.f$0 = pipAccessibilityInteractionConnectionImpl;
        this.f$4 = i;
        this.f$5 = iAccessibilityInteractionConnectionCallback;
    }

    public final void run() {
        PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl = this.f$0;
        int i = this.f$4;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = this.f$5;
        Objects.requireNonNull(pipAccessibilityInteractionConnectionImpl);
        Objects.requireNonNull(PipAccessibilityInteractionConnection.this);
        try {
            iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo) null, i);
        } catch (RemoteException unused) {
        }
    }
}
