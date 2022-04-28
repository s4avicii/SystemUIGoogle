package com.android.systemui.wallet.p011ui;

import android.app.Activity;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import com.google.android.systemui.gamedashboard.GameMenuActivity;
import java.util.Objects;

/* renamed from: com.android.systemui.wallet.ui.WalletActivity$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WalletActivity$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Activity f$0;

    public /* synthetic */ WalletActivity$$ExternalSyntheticLambda0(Activity activity, int i) {
        this.$r8$classId = i;
        this.f$0 = activity;
    }

    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                WalletActivity walletActivity = (WalletActivity) this.f$0;
                int i = WalletActivity.$r8$clinit;
                Objects.requireNonNull(walletActivity);
                if (walletActivity.mWalletClient.createWalletIntent() == null) {
                    Log.w("WalletActivity", "Unable to create wallet app intent.");
                    return;
                } else if (!walletActivity.mKeyguardStateController.isUnlocked() && walletActivity.mFalsingManager.isFalseTap(1)) {
                    return;
                } else {
                    if (walletActivity.mKeyguardStateController.isUnlocked()) {
                        walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_SHOW_ALL);
                        walletActivity.mActivityStarter.startActivity(walletActivity.mWalletClient.createWalletIntent(), true);
                        walletActivity.finish();
                        return;
                    }
                    walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_UNLOCK_FROM_SHOW_ALL_BUTTON);
                    walletActivity.mKeyguardDismissUtil.executeWhenUnlocked(new WalletActivity$$ExternalSyntheticLambda1(walletActivity), false, true);
                    return;
                }
            default:
                GameMenuActivity gameMenuActivity = (GameMenuActivity) this.f$0;
                IntentFilter intentFilter = GameMenuActivity.DND_FILTER;
                Objects.requireNonNull(gameMenuActivity);
                gameMenuActivity.navigateToGameModeView();
                return;
        }
    }
}
