package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.Lifecycle;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.SettingObserver;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.p006qs.tiles.dialog.QSZenModeDialogMetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.DndTile */
public final class DndTile extends QSTileImpl<QSTile.BooleanState> {
    public static final Intent ZEN_SETTINGS = new Intent("android.settings.ZEN_MODE_SETTINGS");
    public final ZenModeController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public boolean mListening;
    public final C10452 mPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
            if ("DndTileCombinedIcon".equals(str) || "DndTileVisible".equals(str)) {
                DndTile dndTile = DndTile.this;
                Objects.requireNonNull(dndTile);
                dndTile.refreshState((Object) null);
            }
        }
    };
    public final QSZenModeDialogMetricsLogger mQSZenDialogMetricsLogger;
    public final C10441 mSettingZenDuration;
    public final SharedPreferences mSharedPreferences;
    public final C10463 mZenCallback;

    public final int getMetricsCategory() {
        return 118;
    }

    public final void handleUserSwitch(int i) {
        handleRefreshState((Object) null);
        this.mSettingZenDuration.setUserId(i);
    }

    static {
        new Intent("android.settings.ZEN_MODE_PRIORITY_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_dnd_label);
    }

    public final void handleClick(View view) {
        boolean z;
        if (((QSTile.BooleanState) this.mState).value) {
            this.mController.setZen(0, (Uri) null, this.TAG);
            return;
        }
        int value = this.mSettingZenDuration.getValue();
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 0) == 0 || Settings.Secure.getInt(this.mContext.getContentResolver(), "zen_settings_updated", 0) == 1) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 0);
            this.mController.setZen(1, (Uri) null, this.TAG);
            Intent intent = new Intent("android.settings.ZEN_MODE_ONBOARDING");
            intent.addFlags(268468224);
            this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        } else if (value == -1) {
            this.mUiHandler.post(new DndTile$$ExternalSyntheticLambda0(this, view, 0));
        } else if (value != 0) {
            this.mController.setZen(1, ZenModeConfig.toTimeCondition(this.mContext, value, this.mHost.getUserId(), true).id, this.TAG);
        } else {
            this.mController.setZen(1, (Uri) null, this.TAG);
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        int i;
        boolean z;
        int i2;
        boolean z2;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        ZenModeController zenModeController = this.mController;
        if (zenModeController != null) {
            if (obj instanceof Integer) {
                i = ((Integer) obj).intValue();
            } else {
                i = zenModeController.getZen();
            }
            boolean z3 = false;
            if (i != 0) {
                z = true;
            } else {
                z = false;
            }
            boolean z4 = booleanState.value;
            if (booleanState.slash == null) {
                booleanState.slash = new QSTile.SlashState();
            }
            booleanState.dualTarget = true;
            booleanState.value = z;
            if (z) {
                i2 = 2;
            } else {
                i2 = 1;
            }
            booleanState.state = i2;
            booleanState.slash.isSlashed = !z;
            booleanState.label = getTileLabel();
            Context context = this.mContext;
            if (i != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            booleanState.secondaryLabel = TextUtils.emptyIfNull(ZenModeConfig.getDescription(context, z2, this.mController.getConfig(), false));
            booleanState.icon = QSTileImpl.ResourceIcon.get(17302824);
            checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_adjust_volume");
            if (i == 1) {
                booleanState.contentDescription = this.mContext.getString(C1777R.string.accessibility_quick_settings_dnd) + ", " + booleanState.secondaryLabel;
            } else if (i == 2) {
                booleanState.contentDescription = this.mContext.getString(C1777R.string.accessibility_quick_settings_dnd) + ", " + this.mContext.getString(C1777R.string.accessibility_quick_settings_dnd_none_on) + ", " + booleanState.secondaryLabel;
            } else if (i != 3) {
                booleanState.contentDescription = this.mContext.getString(C1777R.string.accessibility_quick_settings_dnd);
            } else {
                booleanState.contentDescription = this.mContext.getString(C1777R.string.accessibility_quick_settings_dnd) + ", " + this.mContext.getString(C1777R.string.accessibility_quick_settings_dnd_alarms_on) + ", " + booleanState.secondaryLabel;
            }
            booleanState.dualLabelContentDescription = this.mContext.getResources().getString(C1777R.string.accessibility_quick_settings_open_settings, new Object[]{getTileLabel()});
            booleanState.expandedAccessibilityClassName = Switch.class.getName();
            if (this.mSettingZenDuration.getValue() == -1) {
                z3 = true;
            }
            booleanState.forceExpandIcon = z3;
        }
    }

    public final boolean isAvailable() {
        return this.mSharedPreferences.getBoolean("DndTileVisible", false);
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public DndTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ZenModeController zenModeController, SharedPreferences sharedPreferences, SecureSettings secureSettings, DialogLaunchAnimator dialogLaunchAnimator) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        C10463 r1 = new ZenModeController.Callback() {
            public final void onZenChanged(int i) {
                DndTile.this.refreshState(Integer.valueOf(i));
            }
        };
        this.mZenCallback = r1;
        this.mController = zenModeController;
        this.mSharedPreferences = sharedPreferences;
        zenModeController.observe((Lifecycle) this.mLifecycle, r1);
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mSettingZenDuration = new SettingObserver(secureSettings, this.mUiHandler, this.mHost.getUserId()) {
            public final void handleValueChanged(int i, boolean z) {
                DndTile dndTile = DndTile.this;
                Objects.requireNonNull(dndTile);
                dndTile.refreshState((Object) null);
            }
        };
        this.mQSZenDialogMetricsLogger = new QSZenModeDialogMetricsLogger(this.mContext);
    }

    public final void handleDestroy() {
        super.handleDestroy();
        this.mSettingZenDuration.setListening(false);
    }

    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening != z) {
            this.mListening = z;
            if (z) {
                Context context = this.mContext;
                context.getSharedPreferences(context.getPackageName(), 0).registerOnSharedPreferenceChangeListener(this.mPrefListener);
            } else {
                Context context2 = this.mContext;
                context2.getSharedPreferences(context2.getPackageName(), 0).unregisterOnSharedPreferenceChangeListener(this.mPrefListener);
            }
            this.mSettingZenDuration.setListening(z);
        }
    }

    public final void handleSecondaryClick(View view) {
        handleLongClick(view);
    }

    public final Intent getLongClickIntent() {
        return ZEN_SETTINGS;
    }
}
