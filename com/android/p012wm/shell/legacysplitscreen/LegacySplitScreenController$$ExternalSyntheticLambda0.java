package com.android.p012wm.shell.legacysplitscreen;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.common.DisplayChangeController;
import com.android.p012wm.shell.common.DisplayLayout;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LegacySplitScreenController$$ExternalSyntheticLambda0 implements DisplayChangeController.OnDisplayChangingListener {
    public final /* synthetic */ LegacySplitScreenController f$0;

    public /* synthetic */ LegacySplitScreenController$$ExternalSyntheticLambda0(LegacySplitScreenController legacySplitScreenController) {
        this.f$0 = legacySplitScreenController;
    }

    public final void onRotateDisplay(int i, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
        int i4;
        int i5;
        LegacySplitScreenController legacySplitScreenController = this.f$0;
        Objects.requireNonNull(legacySplitScreenController);
        LegacySplitScreenTaskListener legacySplitScreenTaskListener = legacySplitScreenController.mSplits;
        Objects.requireNonNull(legacySplitScreenTaskListener);
        if (legacySplitScreenTaskListener.mSplitScreenSupported && legacySplitScreenController.mWindowManagerProxy != null) {
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            LegacySplitDisplayLayout legacySplitDisplayLayout = new LegacySplitDisplayLayout(legacySplitScreenController.mContext, new DisplayLayout(legacySplitScreenController.mDisplayController.getDisplayLayout(i)), legacySplitScreenController.mSplits);
            legacySplitDisplayLayout.mDisplayLayout.rotateTo(legacySplitDisplayLayout.mContext.getResources(), i3);
            Configuration configuration = new Configuration();
            configuration.unset();
            DisplayLayout displayLayout = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout);
            if (displayLayout.mWidth > displayLayout.mHeight) {
                i4 = 2;
            } else {
                i4 = 1;
            }
            configuration.orientation = i4;
            DisplayLayout displayLayout2 = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout2);
            int i6 = displayLayout2.mWidth;
            DisplayLayout displayLayout3 = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout3);
            Rect rect = new Rect(0, 0, i6, displayLayout3.mHeight);
            DisplayLayout displayLayout4 = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout4);
            rect.inset(displayLayout4.mNonDecorInsets);
            configuration.windowConfiguration.setAppBounds(rect);
            DisplayLayout displayLayout5 = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout5);
            int i7 = displayLayout5.mWidth;
            DisplayLayout displayLayout6 = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout6);
            rect.set(0, 0, i7, displayLayout6.mHeight);
            DisplayLayout displayLayout7 = legacySplitDisplayLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout7);
            rect.inset(displayLayout7.mStableInsets);
            configuration.screenWidthDp = (int) (((float) rect.width()) / legacySplitDisplayLayout.mDisplayLayout.density());
            configuration.screenHeightDp = (int) (((float) rect.height()) / legacySplitDisplayLayout.mDisplayLayout.density());
            legacySplitDisplayLayout.mContext = legacySplitDisplayLayout.mContext.createConfigurationContext(configuration);
            legacySplitDisplayLayout.mSnapAlgorithm = null;
            legacySplitDisplayLayout.mMinimizedSnapAlgorithm = null;
            legacySplitDisplayLayout.mResourcesValid = false;
            legacySplitScreenController.mRotateSplitLayout = legacySplitDisplayLayout;
            if (legacySplitScreenController.mMinimized) {
                i5 = legacySplitScreenController.mView.mSnapTargetBeforeMinimized.position;
            } else {
                i5 = legacySplitDisplayLayout.getSnapAlgorithm().getMiddleTarget().position;
            }
            legacySplitDisplayLayout.resizeSplits(legacySplitDisplayLayout.getSnapAlgorithm().calculateNonDismissingSnapTarget(i5).position, windowContainerTransaction2);
            if (legacySplitScreenController.isSplitActive() && legacySplitScreenController.mHomeStackResizable) {
                legacySplitScreenController.mWindowManagerProxy.applyHomeTasksMinimized(legacySplitDisplayLayout, legacySplitScreenController.mSplits.mSecondary.token, windowContainerTransaction2);
            }
            if (legacySplitScreenController.mWindowManagerProxy.queueSyncTransactionIfWaiting(windowContainerTransaction2)) {
                Slog.w("SplitScreenCtrl", "Screen rotated while other operations were pending, this may result in some graphical artifacts.");
            } else {
                windowContainerTransaction.merge(windowContainerTransaction2, true);
            }
        }
    }
}
