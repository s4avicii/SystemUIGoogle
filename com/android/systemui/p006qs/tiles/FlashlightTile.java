package com.android.systemui.p006qs.tiles;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.Lifecycle;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.FlashlightController;

/* renamed from: com.android.systemui.qs.tiles.FlashlightTile */
public final class FlashlightTile extends QSTileImpl<QSTile.BooleanState> implements FlashlightController.FlashlightListener {
    public final FlashlightController mFlashlightController;
    public final QSTile.Icon mIcon = QSTileImpl.ResourceIcon.get(17302825);

    public final int getMetricsCategory() {
        return 119;
    }

    public final void handleUserSwitch(int i) {
    }

    public final void onFlashlightAvailabilityChanged(boolean z) {
        refreshState((Object) null);
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.media.action.STILL_IMAGE_CAMERA");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_flashlight_label);
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        booleanState.label = this.mHost.getContext().getString(C1777R.string.quick_settings_flashlight_label);
        booleanState.secondaryLabel = "";
        booleanState.stateDescription = "";
        int i = 1;
        if (!this.mFlashlightController.isAvailable()) {
            booleanState.icon = this.mIcon;
            booleanState.slash.isSlashed = true;
            String string = this.mContext.getString(C1777R.string.quick_settings_flashlight_camera_in_use);
            booleanState.secondaryLabel = string;
            booleanState.stateDescription = string;
            booleanState.state = 0;
            return;
        }
        if (obj instanceof Boolean) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            if (booleanValue != booleanState.value) {
                booleanState.value = booleanValue;
            } else {
                return;
            }
        } else {
            booleanState.value = this.mFlashlightController.isEnabled();
        }
        booleanState.icon = this.mIcon;
        booleanState.slash.isSlashed = !booleanState.value;
        booleanState.contentDescription = this.mContext.getString(C1777R.string.quick_settings_flashlight_label);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        if (booleanState.value) {
            i = 2;
        }
        booleanState.state = i;
    }

    public final boolean isAvailable() {
        return this.mFlashlightController.hasFlashlight();
    }

    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    public final void onFlashlightError() {
        refreshState(Boolean.FALSE);
    }

    public FlashlightTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, FlashlightController flashlightController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mFlashlightController = flashlightController;
        flashlightController.observe((Lifecycle) this.mLifecycle, this);
    }

    public final void handleClick(View view) {
        if (!ActivityManager.isUserAMonkey()) {
            boolean z = !((QSTile.BooleanState) this.mState).value;
            refreshState(Boolean.valueOf(z));
            this.mFlashlightController.setFlashlight(z);
        }
    }

    public final void onFlashlightChanged(boolean z) {
        refreshState(Boolean.valueOf(z));
    }

    public final void handleLongClick(View view) {
        handleClick(view);
    }

    public final void handleDestroy() {
        super.handleDestroy();
    }
}
