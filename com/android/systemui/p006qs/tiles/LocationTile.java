package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.LifecycleOwner;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.LocationTile */
public final class LocationTile extends QSTileImpl<QSTile.BooleanState> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LocationController mController;
    public final QSTile.Icon mIcon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_location);
    public final KeyguardStateController mKeyguard;

    /* renamed from: com.android.systemui.qs.tiles.LocationTile$Callback */
    public final class Callback implements LocationController.LocationChangeCallback, KeyguardStateController.Callback {
        public Callback() {
        }

        public final void onKeyguardShowingChanged() {
            LocationTile locationTile = LocationTile.this;
            Objects.requireNonNull(locationTile);
            locationTile.refreshState((Object) null);
        }

        public final void onLocationSettingsChanged() {
            LocationTile locationTile = LocationTile.this;
            Objects.requireNonNull(locationTile);
            locationTile.refreshState((Object) null);
        }
    }

    public final int getMetricsCategory() {
        return 122;
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_location_label);
    }

    public final void handleClick(View view) {
        if (!this.mKeyguard.isMethodSecure() || !this.mKeyguard.isShowing()) {
            this.mController.setLocationEnabled(!((QSTile.BooleanState) this.mState).value);
            return;
        }
        this.mActivityStarter.postQSRunnableDismissingKeyguard(new WMShell$7$$ExternalSyntheticLambda2(this, 2));
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        booleanState.value = this.mController.isLocationEnabled();
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_share_location");
        if (!booleanState.disabledByPolicy) {
            checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_location");
        }
        booleanState.icon = this.mIcon;
        int i = 1;
        booleanState.slash.isSlashed = !booleanState.value;
        String string = this.mContext.getString(C1777R.string.quick_settings_location_label);
        booleanState.label = string;
        booleanState.contentDescription = string;
        if (booleanState.value) {
            i = 2;
        }
        booleanState.state = i;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public LocationTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, LocationController locationController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback();
        this.mController = locationController;
        this.mKeyguard = keyguardStateController;
        locationController.observe((LifecycleOwner) this, callback);
        keyguardStateController.observe((LifecycleOwner) this, callback);
    }
}
