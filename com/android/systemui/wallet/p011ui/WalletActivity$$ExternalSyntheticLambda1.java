package com.android.systemui.wallet.p011ui;

import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* renamed from: com.android.systemui.wallet.ui.WalletActivity$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WalletActivity$$ExternalSyntheticLambda1 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ WalletActivity f$0;

    public /* synthetic */ WalletActivity$$ExternalSyntheticLambda1(WalletActivity walletActivity) {
        this.f$0 = walletActivity;
    }

    public final boolean onDismiss() {
        WalletActivity walletActivity = this.f$0;
        int i = WalletActivity.$r8$clinit;
        Objects.requireNonNull(walletActivity);
        walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_SHOW_ALL);
        walletActivity.mActivityStarter.startActivity(walletActivity.mWalletClient.createWalletIntent(), true);
        walletActivity.finish();
        return false;
    }
}
