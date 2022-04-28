package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletCard;
import android.util.Log;
import android.view.View;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.QuickAccessWalletTile */
public final class QuickAccessWalletTile extends QSTileImpl<QSTile.State> {
    public final WalletCardRetriever mCardRetriever = new WalletCardRetriever();
    @VisibleForTesting
    public Drawable mCardViewDrawable;
    public final QuickAccessWalletController mController;
    public boolean mIsWalletUpdating = true;
    public final KeyguardStateController mKeyguardStateController;
    public final String mLabel = this.mContext.getString(C1777R.string.wallet_title);
    public final PackageManager mPackageManager;
    public final SecureSettings mSecureSettings;
    public WalletCard mSelectedCard;

    /* renamed from: com.android.systemui.qs.tiles.QuickAccessWalletTile$WalletCardRetriever */
    public class WalletCardRetriever implements QuickAccessWalletClient.OnWalletCardsRetrievedCallback {
        public WalletCardRetriever() {
        }

        public final void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
            QuickAccessWalletTile quickAccessWalletTile = QuickAccessWalletTile.this;
            quickAccessWalletTile.mIsWalletUpdating = false;
            quickAccessWalletTile.mCardViewDrawable = null;
            quickAccessWalletTile.mSelectedCard = null;
            Objects.requireNonNull(quickAccessWalletTile);
            quickAccessWalletTile.refreshState((Object) null);
        }

        public final void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
            Log.i("QuickAccessWalletTile", "Successfully retrieved wallet cards.");
            QuickAccessWalletTile.this.mIsWalletUpdating = false;
            List walletCards = getWalletCardsResponse.getWalletCards();
            if (walletCards.isEmpty()) {
                Log.d("QuickAccessWalletTile", "No wallet cards exist.");
                QuickAccessWalletTile quickAccessWalletTile = QuickAccessWalletTile.this;
                quickAccessWalletTile.mCardViewDrawable = null;
                quickAccessWalletTile.mSelectedCard = null;
                Objects.requireNonNull(quickAccessWalletTile);
                quickAccessWalletTile.refreshState((Object) null);
                return;
            }
            int selectedIndex = getWalletCardsResponse.getSelectedIndex();
            if (selectedIndex >= walletCards.size()) {
                Log.w("QuickAccessWalletTile", "Error retrieving cards: Invalid selected card index.");
                QuickAccessWalletTile quickAccessWalletTile2 = QuickAccessWalletTile.this;
                quickAccessWalletTile2.mSelectedCard = null;
                quickAccessWalletTile2.mCardViewDrawable = null;
                return;
            }
            QuickAccessWalletTile.this.mSelectedCard = (WalletCard) walletCards.get(selectedIndex);
            QuickAccessWalletTile quickAccessWalletTile3 = QuickAccessWalletTile.this;
            quickAccessWalletTile3.mCardViewDrawable = quickAccessWalletTile3.mSelectedCard.getCardImage().loadDrawable(QuickAccessWalletTile.this.mContext);
            QuickAccessWalletTile quickAccessWalletTile4 = QuickAccessWalletTile.this;
            Objects.requireNonNull(quickAccessWalletTile4);
            quickAccessWalletTile4.refreshState((Object) null);
        }
    }

    public final Intent getLongClickIntent() {
        return null;
    }

    public final int getMetricsCategory() {
        return 0;
    }

    public final CharSequence getTileLabel() {
        QuickAccessWalletController quickAccessWalletController = this.mController;
        Objects.requireNonNull(quickAccessWalletController);
        CharSequence serviceLabel = quickAccessWalletController.mQuickAccessWalletClient.getServiceLabel();
        if (serviceLabel == null) {
            return this.mLabel;
        }
        return serviceLabel;
    }

    public final void handleClick(View view) {
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController;
        if (view == null) {
            ghostedViewLaunchAnimatorController = null;
        } else {
            ghostedViewLaunchAnimatorController = ActivityLaunchAnimator.Controller.fromView(view, 32);
        }
        this.mUiHandler.post(new QuickAccessWalletTile$$ExternalSyntheticLambda0(this, ghostedViewLaunchAnimatorController, 0));
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.Icon icon;
        int i;
        QuickAccessWalletController quickAccessWalletController = this.mController;
        Objects.requireNonNull(quickAccessWalletController);
        CharSequence serviceLabel = quickAccessWalletController.mQuickAccessWalletClient.getServiceLabel();
        if (serviceLabel == null) {
            serviceLabel = this.mLabel;
        }
        state.label = serviceLabel;
        state.contentDescription = serviceLabel;
        QuickAccessWalletController quickAccessWalletController2 = this.mController;
        Objects.requireNonNull(quickAccessWalletController2);
        Drawable tileIcon = quickAccessWalletController2.mQuickAccessWalletClient.getTileIcon();
        if (tileIcon == null) {
            icon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_wallet_lockscreen);
        } else {
            icon = new QSTileImpl.DrawableIcon(tileIcon);
        }
        state.icon = icon;
        boolean z = !this.mKeyguardStateController.isUnlocked();
        QuickAccessWalletController quickAccessWalletController3 = this.mController;
        Objects.requireNonNull(quickAccessWalletController3);
        if (quickAccessWalletController3.mQuickAccessWalletClient.isWalletServiceAvailable()) {
            QuickAccessWalletController quickAccessWalletController4 = this.mController;
            Objects.requireNonNull(quickAccessWalletController4);
            if (quickAccessWalletController4.mQuickAccessWalletClient.isWalletFeatureAvailable()) {
                WalletCard walletCard = this.mSelectedCard;
                if (walletCard == null) {
                    state.state = 1;
                    Context context = this.mContext;
                    if (this.mIsWalletUpdating) {
                        i = C1777R.string.wallet_secondary_label_updating;
                    } else {
                        i = C1777R.string.wallet_secondary_label_no_card;
                    }
                    state.secondaryLabel = context.getString(i);
                    state.sideViewCustomDrawable = null;
                } else if (z) {
                    state.state = 1;
                    state.secondaryLabel = this.mContext.getString(C1777R.string.wallet_secondary_label_device_locked);
                    state.sideViewCustomDrawable = null;
                } else {
                    state.state = 2;
                    state.secondaryLabel = walletCard.getContentDescription();
                    state.sideViewCustomDrawable = this.mCardViewDrawable;
                }
                state.stateDescription = state.secondaryLabel;
                return;
            }
        }
        state.state = 0;
        state.secondaryLabel = null;
        state.sideViewCustomDrawable = null;
    }

    public final boolean isAvailable() {
        if (!this.mPackageManager.hasSystemFeature("android.hardware.nfc.hce") || this.mPackageManager.hasSystemFeature("org.chromium.arc") || this.mSecureSettings.getString("nfc_payment_default_component") == null) {
            return false;
        }
        return true;
    }

    public final QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.handlesLongClick = false;
        return state;
    }

    public QuickAccessWalletTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardStateController keyguardStateController, PackageManager packageManager, SecureSettings secureSettings, QuickAccessWalletController quickAccessWalletController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mController = quickAccessWalletController;
        this.mKeyguardStateController = keyguardStateController;
        this.mPackageManager = packageManager;
        this.mSecureSettings = secureSettings;
    }

    public final void handleDestroy() {
        super.handleDestroy();
        this.mController.unregisterWalletChangeObservers(QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x002c, code lost:
        if (r5.mQuickAccessWalletClient.isWalletFeatureAvailable() == false) goto L_0x002e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleSetListening(boolean r5) {
        /*
            r4 = this;
            super.handleSetListening(r5)
            if (r5 == 0) goto L_0x0041
            com.android.systemui.wallet.controller.QuickAccessWalletController r5 = r4.mController
            com.android.systemui.qs.tiles.QuickAccessWalletTile$WalletCardRetriever r0 = r4.mCardRetriever
            r1 = 1
            com.android.systemui.wallet.controller.QuickAccessWalletController$WalletChangeEvent[] r1 = new com.android.systemui.wallet.controller.QuickAccessWalletController.WalletChangeEvent[r1]
            r2 = 0
            com.android.systemui.wallet.controller.QuickAccessWalletController$WalletChangeEvent r3 = com.android.systemui.wallet.controller.QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE
            r1[r2] = r3
            r5.setupWalletChangeObservers(r0, r1)
            com.android.systemui.wallet.controller.QuickAccessWalletController r5 = r4.mController
            java.util.Objects.requireNonNull(r5)
            android.service.quickaccesswallet.QuickAccessWalletClient r5 = r5.mQuickAccessWalletClient
            boolean r5 = r5.isWalletServiceAvailable()
            if (r5 == 0) goto L_0x002e
            com.android.systemui.wallet.controller.QuickAccessWalletController r5 = r4.mController
            java.util.Objects.requireNonNull(r5)
            android.service.quickaccesswallet.QuickAccessWalletClient r5 = r5.mQuickAccessWalletClient
            boolean r5 = r5.isWalletFeatureAvailable()
            if (r5 != 0) goto L_0x003a
        L_0x002e:
            java.lang.String r5 = "QuickAccessWalletTile"
            java.lang.String r0 = "QAW service is unavailable, recreating the wallet client."
            android.util.Log.i(r5, r0)
            com.android.systemui.wallet.controller.QuickAccessWalletController r5 = r4.mController
            r5.reCreateWalletClient()
        L_0x003a:
            com.android.systemui.wallet.controller.QuickAccessWalletController r5 = r4.mController
            com.android.systemui.qs.tiles.QuickAccessWalletTile$WalletCardRetriever r4 = r4.mCardRetriever
            r5.queryWalletCards(r4)
        L_0x0041:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tiles.QuickAccessWalletTile.handleSetListening(boolean):void");
    }
}
