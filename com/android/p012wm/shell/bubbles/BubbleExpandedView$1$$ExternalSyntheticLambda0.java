package com.android.p012wm.shell.bubbles;

import android.graphics.Insets;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.Toast;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleExpandedView;
import com.android.settingslib.wifi.WifiTracker;
import com.android.systemui.accessibility.WindowMagnificationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.statusbar.phone.NotificationTapHelper;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleExpandedView$1$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleExpandedView$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleExpandedView$1$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        int i;
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                BubbleExpandedView.C17971 r4 = (BubbleExpandedView.C17971) this.f$0;
                Objects.requireNonNull(r4);
                BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
                BubbleController bubbleController = bubbleExpandedView.mController;
                Bubble bubble = bubbleExpandedView.mBubble;
                Objects.requireNonNull(bubble);
                bubbleController.removeBubble(bubble.mKey, 3);
                return;
            case 1:
                ((WifiTracker.WifiListener) this.f$0).onAccessPointsChanged();
                return;
            case 2:
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) this.f$0;
                boolean z2 = WindowMagnificationController.DEBUG;
                Objects.requireNonNull(windowMagnificationController);
                WindowMetrics currentWindowMetrics = windowMagnificationController.mWm.getCurrentWindowMetrics();
                Insets insets = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
                if (insets.bottom != 0) {
                    i = currentWindowMetrics.getBounds().bottom - insets.bottom;
                } else {
                    i = -1;
                }
                if (i != windowMagnificationController.mSystemGestureTop) {
                    windowMagnificationController.mSystemGestureTop = i;
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    windowMagnificationController.updateSysUIState(false);
                    return;
                }
                return;
            case 3:
                KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.f$0;
                boolean z3 = KeyguardViewMediator.DEBUG;
                Objects.requireNonNull(keyguardViewMediator);
                keyguardViewMediator.mTrustManager.reportKeyguardShowingChanged();
                return;
            case 4:
                ScreenshotController screenshotController = (ScreenshotController) this.f$0;
                ScreenshotController.C10731 r0 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
                Objects.requireNonNull(screenshotController);
                Toast.makeText(screenshotController.mContext, C1777R.string.screenshot_saved_title, 0).show();
                return;
            case 5:
                ScrollCaptureController scrollCaptureController = (ScrollCaptureController) this.f$0;
                Objects.requireNonNull(scrollCaptureController);
                scrollCaptureController.mCaptureCompleter.set(new ScrollCaptureController.LongScreenshot(scrollCaptureController.mSession, scrollCaptureController.mImageTileSet));
                return;
            default:
                ((NotificationTapHelper) this.f$0).makeInactive();
                return;
        }
    }
}
