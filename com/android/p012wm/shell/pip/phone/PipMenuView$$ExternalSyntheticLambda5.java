package com.android.p012wm.shell.pip.phone;

import android.view.View;
import com.android.p012wm.shell.bubbles.BubbleData;
import com.android.p012wm.shell.bubbles.BubbleStackView;
import com.android.systemui.screenshot.ScreenshotEvent;
import com.android.systemui.screenshot.ScreenshotView;
import com.android.systemui.screenshot.SwipeDismissHandler;
import com.android.systemui.wmshell.WMShell$8$$ExternalSyntheticLambda0;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipMenuView$$ExternalSyntheticLambda5 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipMenuView$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                PipMenuView pipMenuView = (PipMenuView) this.f$0;
                Objects.requireNonNull(pipMenuView);
                if (pipMenuView.mEnterSplitButton.getAlpha() != 0.0f) {
                    PhonePipMenuController phonePipMenuController = pipMenuView.mController;
                    Objects.requireNonNull(phonePipMenuController);
                    pipMenuView.hideMenu(new WMShell$8$$ExternalSyntheticLambda0(phonePipMenuController, 8), false, true, 1);
                    return;
                }
                return;
            case 1:
                ScreenshotView.C10865 r4 = (ScreenshotView.C10865) this.f$0;
                int i = ScreenshotView.C10865.$r8$clinit;
                Objects.requireNonNull(r4);
                ScreenshotView screenshotView = ScreenshotView.this;
                screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_EXPLICIT_DISMISSAL, 0, screenshotView.mPackageName);
                ScreenshotView screenshotView2 = ScreenshotView.this;
                Objects.requireNonNull(screenshotView2);
                SwipeDismissHandler swipeDismissHandler = screenshotView2.mSwipeDismissHandler;
                Objects.requireNonNull(swipeDismissHandler);
                swipeDismissHandler.dismiss(1.0f);
                return;
            default:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                int i2 = BubbleStackView.FLYOUT_HIDE_AFTER;
                Objects.requireNonNull(bubbleStackView);
                BubbleData bubbleData = bubbleStackView.mBubbleData;
                Objects.requireNonNull(bubbleData);
                bubbleData.mShowingOverflow = true;
                bubbleStackView.mBubbleData.setSelectedBubble(bubbleStackView.mBubbleOverflow);
                bubbleStackView.mBubbleData.setExpanded(true);
                return;
        }
    }
}
