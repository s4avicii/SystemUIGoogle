package com.android.systemui.wallet.p011ui;

import android.view.View;
import java.util.Objects;

/* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WalletCardCarousel$$ExternalSyntheticLambda0 implements View.OnLayoutChangeListener {
    public final /* synthetic */ WalletCardCarousel f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ WalletCardCarousel$$ExternalSyntheticLambda0(WalletCardCarousel walletCardCarousel, View view) {
        this.f$0 = walletCardCarousel;
        this.f$1 = view;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        WalletCardCarousel walletCardCarousel = this.f$0;
        View view2 = this.f$1;
        int i9 = WalletCardCarousel.$r8$clinit;
        Objects.requireNonNull(walletCardCarousel);
        walletCardCarousel.updateCardView(view2);
    }
}
