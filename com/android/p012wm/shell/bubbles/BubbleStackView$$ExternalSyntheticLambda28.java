package com.android.p012wm.shell.bubbles;

import android.view.WindowInsets;
import com.android.p012wm.shell.pip.phone.PipController;
import com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda28 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda28 implements Consumer {
    public static final /* synthetic */ BubbleStackView$$ExternalSyntheticLambda28 INSTANCE = new BubbleStackView$$ExternalSyntheticLambda28(0);
    public static final /* synthetic */ BubbleStackView$$ExternalSyntheticLambda28 INSTANCE$1 = new BubbleStackView$$ExternalSyntheticLambda28(1);
    public static final /* synthetic */ BubbleStackView$$ExternalSyntheticLambda28 INSTANCE$2 = new BubbleStackView$$ExternalSyntheticLambda28(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda28(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Bubble bubble = (Bubble) obj;
                int i = BubbleStackView.FLYOUT_HIDE_AFTER;
                Objects.requireNonNull(bubble);
                BubbleExpandedView bubbleExpandedView = bubble.mExpandedView;
                if (bubbleExpandedView != null) {
                    bubbleExpandedView.applyThemeAttrs();
                    return;
                }
                return;
            case 1:
                WindowInsets windowInsets = (WindowInsets) obj;
                int i2 = NotificationsQuickSettingsContainer.$r8$clinit;
                return;
            default:
                PipController pipController = (PipController) obj;
                int i3 = PipController.IPipImpl.$r8$clinit;
                Objects.requireNonNull(pipController);
                pipController.mPinnedStackAnimationRecentsCallback = null;
                pipController.onPipCornerRadiusChanged();
                return;
        }
    }
}
