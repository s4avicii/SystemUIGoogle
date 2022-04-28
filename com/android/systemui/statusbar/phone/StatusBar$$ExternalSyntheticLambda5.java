package com.android.systemui.statusbar.phone;

import android.app.Fragment;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.p006qs.QSFragment;
import com.android.systemui.p006qs.QSPanelController;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.settings.brightness.BrightnessMirrorHandler;
import com.android.systemui.settings.brightness.BrightnessMirrorHandler$brightnessMirrorListener$1;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda5 implements FragmentHostManager.FragmentListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda5(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void onFragmentViewCreated(Fragment fragment) {
        StatusBar statusBar = this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        C0961QS qs = (C0961QS) fragment;
        if (qs instanceof QSFragment) {
            QSFragment qSFragment = (QSFragment) qs;
            Objects.requireNonNull(qSFragment);
            QSPanelController qSPanelController = qSFragment.mQSPanelController;
            statusBar.mQSPanelController = qSPanelController;
            BrightnessMirrorController brightnessMirrorController = statusBar.mBrightnessMirrorController;
            Objects.requireNonNull(qSPanelController);
            BrightnessMirrorHandler brightnessMirrorHandler = qSPanelController.mBrightnessMirrorHandler;
            Objects.requireNonNull(brightnessMirrorHandler);
            BrightnessMirrorController brightnessMirrorController2 = brightnessMirrorHandler.mirrorController;
            if (brightnessMirrorController2 != null) {
                brightnessMirrorController2.mBrightnessMirrorListeners.remove(brightnessMirrorHandler.brightnessMirrorListener);
            }
            brightnessMirrorHandler.mirrorController = brightnessMirrorController;
            BrightnessMirrorHandler$brightnessMirrorListener$1 brightnessMirrorHandler$brightnessMirrorListener$1 = brightnessMirrorHandler.brightnessMirrorListener;
            Objects.requireNonNull(brightnessMirrorController);
            Objects.requireNonNull(brightnessMirrorHandler$brightnessMirrorListener$1);
            brightnessMirrorController.mBrightnessMirrorListeners.add(brightnessMirrorHandler$brightnessMirrorListener$1);
            brightnessMirrorHandler.updateBrightnessMirror();
        }
    }
}
