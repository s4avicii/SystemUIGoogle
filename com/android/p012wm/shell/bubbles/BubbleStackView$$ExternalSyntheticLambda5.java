package com.android.p012wm.shell.bubbles;

import android.content.IntentFilter;
import android.view.View;
import com.android.keyguard.KeyguardPasswordView;
import com.android.keyguard.KeyguardPasswordViewController;
import com.google.android.systemui.gamedashboard.GameMenuActivity;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda5 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                Objects.requireNonNull(bubbleStackView);
                bubbleStackView.showManageMenu(false);
                Consumer<String> consumer = bubbleStackView.mUnbubbleConversationCallback;
                BubbleData bubbleData = bubbleStackView.mBubbleData;
                Objects.requireNonNull(bubbleData);
                consumer.accept(bubbleData.mSelectedBubble.getKey());
                return;
            case 1:
                KeyguardPasswordViewController keyguardPasswordViewController = (KeyguardPasswordViewController) this.f$0;
                Objects.requireNonNull(keyguardPasswordViewController);
                keyguardPasswordViewController.mKeyguardSecurityCallback.userActivity();
                keyguardPasswordViewController.mInputMethodManager.showInputMethodPickerFromSystem(false, ((KeyguardPasswordView) keyguardPasswordViewController.mView).getContext().getDisplayId());
                return;
            default:
                GameMenuActivity gameMenuActivity = (GameMenuActivity) this.f$0;
                IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
                Objects.requireNonNull(gameMenuActivity);
                gameMenuActivity.navigateToGameModeView();
                return;
        }
    }
}
