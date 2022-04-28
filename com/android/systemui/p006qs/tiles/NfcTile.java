package com.android.systemui.p006qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.NfcTile */
public final class NfcTile extends QSTileImpl<QSTile.BooleanState> {
    public NfcAdapter mAdapter;
    public BroadcastDispatcher mBroadcastDispatcher;
    public final QSTile.Icon mIcon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_qs_nfc);
    public C10471 mNfcReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            NfcTile nfcTile = NfcTile.this;
            Objects.requireNonNull(nfcTile);
            nfcTile.refreshState((Object) null);
        }
    };

    public final int getMetricsCategory() {
        return 800;
    }

    public final void handleUserSwitch(int i) {
    }

    public final NfcAdapter getAdapter() {
        if (this.mAdapter == null) {
            try {
                this.mAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
            } catch (UnsupportedOperationException unused) {
                this.mAdapter = null;
            }
        }
        return this.mAdapter;
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.settings.NFC_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_nfc_label);
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        int i = 1;
        if (getAdapter() == null || !getAdapter().isEnabled()) {
            z = false;
        } else {
            z = true;
        }
        booleanState.value = z;
        if (getAdapter() == null) {
            i = 0;
        } else if (booleanState.value) {
            i = 2;
        }
        booleanState.state = i;
        booleanState.icon = this.mIcon;
        booleanState.label = this.mContext.getString(C1777R.string.quick_settings_nfc_label);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = booleanState.label;
    }

    public final boolean isAvailable() {
        if (this.mContext.getString(C1777R.string.quick_settings_tiles_stock).contains("nfc")) {
            return this.mContext.getPackageManager().hasSystemFeature("android.hardware.nfc");
        }
        return false;
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public NfcTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    public final void handleClick(View view) {
        if (getAdapter() != null) {
            if (!getAdapter().isEnabled()) {
                getAdapter().enable();
            } else {
                getAdapter().disable();
            }
        }
    }

    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (z) {
            this.mBroadcastDispatcher.registerReceiver(this.mNfcReceiver, new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED"));
        } else {
            this.mBroadcastDispatcher.unregisterReceiver(this.mNfcReceiver);
        }
    }
}
