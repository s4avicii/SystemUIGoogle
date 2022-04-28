package com.android.systemui.statusbar.policy;

import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.util.Log;

/* compiled from: WalletControllerImpl.kt */
public final class WalletControllerImpl implements WalletController {
    public final QuickAccessWalletClient quickAccessWalletClient;

    public final Integer getWalletPosition() {
        if (this.quickAccessWalletClient.isWalletServiceAvailable()) {
            Log.i("WalletControllerImpl", "Setting WalletTile position: 3");
            return 3;
        }
        Log.i("WalletControllerImpl", "Setting WalletTile position: null");
        return null;
    }

    public WalletControllerImpl(QuickAccessWalletClient quickAccessWalletClient2) {
        this.quickAccessWalletClient = quickAccessWalletClient2;
    }
}
