package com.android.systemui.accessibility;

import android.graphics.Rect;
import android.graphics.Region;
import android.os.RemoteException;
import android.view.IWindow;
import android.view.View;
import android.view.WindowManagerGlobal;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WindowMagnificationController$$ExternalSyntheticLambda1 implements View.OnLayoutChangeListener {
    public final /* synthetic */ WindowMagnificationController f$0;

    public /* synthetic */ WindowMagnificationController$$ExternalSyntheticLambda1(WindowMagnificationController windowMagnificationController) {
        this.f$0 = windowMagnificationController;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        WindowMagnificationController windowMagnificationController = this.f$0;
        Objects.requireNonNull(windowMagnificationController);
        int i9 = windowMagnificationController.mBorderDragSize;
        Region region = new Region(i9, i9, windowMagnificationController.mMirrorView.getWidth() - windowMagnificationController.mBorderDragSize, windowMagnificationController.mMirrorView.getHeight() - windowMagnificationController.mBorderDragSize);
        region.op(new Rect((windowMagnificationController.mMirrorView.getWidth() - windowMagnificationController.mDragViewSize) - windowMagnificationController.mBorderDragSize, (windowMagnificationController.mMirrorView.getHeight() - windowMagnificationController.mDragViewSize) - windowMagnificationController.mBorderDragSize, windowMagnificationController.mMirrorView.getWidth(), windowMagnificationController.mMirrorView.getHeight()), Region.Op.DIFFERENCE);
        try {
            WindowManagerGlobal.getWindowSession().updateTapExcludeRegion(IWindow.Stub.asInterface(windowMagnificationController.mMirrorView.getWindowToken()), region);
        } catch (RemoteException unused) {
        }
    }
}
