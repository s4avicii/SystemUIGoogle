package com.android.systemui.accessibility;

import android.os.Handler;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import android.view.accessibility.IWindowMagnificationConnection;
import android.view.accessibility.IWindowMagnificationConnectionCallback;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda2;

public final class WindowMagnificationConnectionImpl extends IWindowMagnificationConnection.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public IWindowMagnificationConnectionCallback mConnectionCallback;
    public final Handler mHandler;
    public final ModeSwitchesController mModeSwitchesController;
    public final WindowMagnification mWindowMagnification;

    public final void enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new WindowMagnificationConnectionImpl$$ExternalSyntheticLambda2(this, i, f, f2, f3, f4, f5, iRemoteMagnificationAnimationCallback));
    }

    public final void disableWindowMagnification(int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new WindowMagnificationConnectionImpl$$ExternalSyntheticLambda4(this, i, iRemoteMagnificationAnimationCallback));
    }

    public final void moveWindowMagnifier(int i, float f, float f2) {
        this.mHandler.post(new WindowMagnificationConnectionImpl$$ExternalSyntheticLambda1(this, i, f, f2));
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [android.os.Binder, com.android.systemui.accessibility.WindowMagnificationConnectionImpl] */
    public final void removeMagnificationButton(int i) {
        this.mHandler.post(new OverviewProxyService$1$$ExternalSyntheticLambda2(this, i, 1));
    }

    public final void setScale(int i, float f) {
        this.mHandler.post(new WindowMagnificationConnectionImpl$$ExternalSyntheticLambda0(this, i, f));
    }

    public final void showMagnificationButton(int i, int i2) {
        this.mHandler.post(new WindowMagnificationConnectionImpl$$ExternalSyntheticLambda3(this, i, i2));
    }

    public WindowMagnificationConnectionImpl(WindowMagnification windowMagnification, Handler handler, ModeSwitchesController modeSwitchesController) {
        this.mWindowMagnification = windowMagnification;
        this.mHandler = handler;
        this.mModeSwitchesController = modeSwitchesController;
    }

    public final void setConnectionCallback(IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback) {
        this.mConnectionCallback = iWindowMagnificationConnectionCallback;
    }
}
