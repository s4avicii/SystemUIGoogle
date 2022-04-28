package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.ContentInfo;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.clipboardoverlay.ClipboardOverlayController;
import com.google.android.systemui.gamedashboard.GameMenuActivity;
import com.google.android.systemui.gamedashboard.GameModeDndController;
import com.google.android.systemui.gamedashboard.ShortcutBarController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RemoteInputView$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RemoteInputView$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                RemoteInputView remoteInputView = (RemoteInputView) this.f$0;
                Object obj = RemoteInputView.VIEW_TAG;
                Objects.requireNonNull(remoteInputView);
                remoteInputView.setAttachment((ContentInfo) null);
                return;
            case 1:
                ClipboardOverlayController clipboardOverlayController = (ClipboardOverlayController) this.f$0;
                Objects.requireNonNull(clipboardOverlayController);
                Context context = clipboardOverlayController.mContext;
                Intent intent = new Intent("android.intent.action.REMOTE_COPY");
                intent.addFlags(268468224);
                context.startActivity(intent);
                clipboardOverlayController.animateOut();
                return;
            default:
                GameMenuActivity gameMenuActivity = (GameMenuActivity) this.f$0;
                IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
                Objects.requireNonNull(gameMenuActivity);
                boolean z = !gameMenuActivity.mDndController.isGameModeDndOn();
                ShortcutBarController shortcutBarController = gameMenuActivity.mShortcutBarController;
                if (z) {
                    shortcutBarController.mToast.showShortcutText(C1777R.string.game_dashboard_dnd_on);
                } else {
                    Objects.requireNonNull(shortcutBarController);
                }
                GameModeDndController gameModeDndController = gameMenuActivity.mDndController;
                Objects.requireNonNull(gameModeDndController);
                gameModeDndController.mFilterActive = z;
                gameModeDndController.mGameActive = z;
                gameModeDndController.updateRule();
                return;
        }
    }
}
