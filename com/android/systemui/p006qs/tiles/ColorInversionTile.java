package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.SettingObserver;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.ColorInversionTile */
public final class ColorInversionTile extends QSTileImpl<QSTile.BooleanState> {
    public final QSTile.Icon mIcon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_invert_colors);
    public final C10421 mSetting;

    public final int getMetricsCategory() {
        return 116;
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.settings.COLOR_INVERSION_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_inversion_label);
    }

    public final void handleClick(View view) {
        C10421 r3 = this.mSetting;
        Objects.requireNonNull(r3);
        r3.mSettingsProxy.putIntForUser(r3.mSettingName, ((QSTile.BooleanState) this.mState).value ^ true ? 1 : 0, r3.mUserId);
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        int i;
        boolean z;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (obj instanceof Integer) {
            i = ((Integer) obj).intValue();
        } else {
            i = this.mSetting.getValue();
        }
        int i2 = 1;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        booleanState.value = z;
        if (z) {
            i2 = 2;
        }
        booleanState.state = i2;
        booleanState.label = this.mContext.getString(C1777R.string.quick_settings_inversion_label);
        booleanState.icon = this.mIcon;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = booleanState.label;
    }

    public final void handleUserSwitch(int i) {
        this.mSetting.setUserId(i);
        handleRefreshState(Integer.valueOf(this.mSetting.getValue()));
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public ColorInversionTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, UserTracker userTracker, SecureSettings secureSettings) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mSetting = new SettingObserver(secureSettings, this.mHandler, userTracker.getUserId()) {
            public final void handleValueChanged(int i, boolean z) {
                ColorInversionTile.this.handleRefreshState(Integer.valueOf(i));
            }
        };
    }

    public final void handleDestroy() {
        super.handleDestroy();
        this.mSetting.setListening(false);
    }

    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        this.mSetting.setListening(z);
    }
}
