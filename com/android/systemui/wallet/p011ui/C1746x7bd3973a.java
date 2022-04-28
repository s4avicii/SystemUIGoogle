package com.android.systemui.wallet.p011ui;

import android.service.quickaccesswallet.WalletCard;
import android.view.View;
import com.android.systemui.wallet.p011ui.WalletCardCarousel;
import com.android.systemui.wallet.p011ui.WalletScreenController;
import java.util.Objects;

/* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel$WalletCardCarouselAdapter$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1746x7bd3973a implements View.OnClickListener {
    public final /* synthetic */ WalletCardCarousel.WalletCardCarouselAdapter f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ WalletCardViewInfo f$2;

    public /* synthetic */ C1746x7bd3973a(WalletCardCarousel.WalletCardCarouselAdapter walletCardCarouselAdapter, int i, WalletCardViewInfo walletCardViewInfo) {
        this.f$0 = walletCardCarouselAdapter;
        this.f$1 = i;
        this.f$2 = walletCardViewInfo;
    }

    public final void onClick(View view) {
        WalletCard walletCard;
        WalletCardCarousel.WalletCardCarouselAdapter walletCardCarouselAdapter = this.f$0;
        int i = this.f$1;
        WalletCardViewInfo walletCardViewInfo = this.f$2;
        Objects.requireNonNull(walletCardCarouselAdapter);
        WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
        if (i != walletCardCarousel.mCenteredAdapterPosition) {
            walletCardCarousel.smoothScrollToPosition(i);
            return;
        }
        WalletScreenController walletScreenController = (WalletScreenController) walletCardCarousel.mSelectionListener;
        Objects.requireNonNull(walletScreenController);
        if ((walletScreenController.mKeyguardStateController.isUnlocked() || !walletScreenController.mFalsingManager.isFalseTap(1)) && (walletCardViewInfo instanceof WalletScreenController.QAWalletCardViewInfo) && (walletCard = ((WalletScreenController.QAWalletCardViewInfo) walletCardViewInfo).mWalletCard) != null && walletCard.getPendingIntent() != null) {
            if (!walletScreenController.mKeyguardStateController.isUnlocked()) {
                walletScreenController.mUiEventLogger.log(WalletUiEvent.QAW_UNLOCK_FROM_CARD_CLICK);
            }
            walletScreenController.mUiEventLogger.log(WalletUiEvent.QAW_CLICK_CARD);
            walletScreenController.mActivityStarter.startPendingIntentDismissingKeyguard(walletCardViewInfo.getPendingIntent());
        }
    }
}
