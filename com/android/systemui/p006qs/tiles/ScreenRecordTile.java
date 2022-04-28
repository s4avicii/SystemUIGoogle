package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.Lifecycle;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.ScreenRecordTile */
public final class ScreenRecordTile extends QSTileImpl<QSTile.BooleanState> implements RecordingController.RecordingStateChangeCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final RecordingController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardStateController mKeyguardStateController;
    public long mMillisUntilFinished = 0;

    /* renamed from: com.android.systemui.qs.tiles.ScreenRecordTile$Callback */
    public final class Callback implements RecordingController.RecordingStateChangeCallback {
        public Callback() {
        }

        public final void onCountdown(long j) {
            ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
            screenRecordTile.mMillisUntilFinished = j;
            screenRecordTile.refreshState((Object) null);
        }

        public final void onCountdownEnd() {
            ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
            Objects.requireNonNull(screenRecordTile);
            screenRecordTile.refreshState((Object) null);
        }

        public final void onRecordingEnd() {
            ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
            Objects.requireNonNull(screenRecordTile);
            screenRecordTile.refreshState((Object) null);
        }

        public final void onRecordingStart() {
            ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
            Objects.requireNonNull(screenRecordTile);
            screenRecordTile.refreshState((Object) null);
        }
    }

    public final Intent getLongClickIntent() {
        return null;
    }

    public final int getMetricsCategory() {
        return 0;
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_screen_record_label);
    }

    public final void handleClick(View view) {
        RecordingController recordingController = this.mController;
        Objects.requireNonNull(recordingController);
        if (recordingController.mIsStarting) {
            Log.d("ScreenRecordTile", "Cancelling countdown");
            this.mController.cancelCountdown();
        } else if (this.mController.isRecording()) {
            this.mController.stopRecording();
        } else {
            this.mUiHandler.post(new ScreenRecordTile$$ExternalSyntheticLambda1(this, view, 0));
        }
        refreshState((Object) null);
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        int i;
        boolean z2;
        CharSequence charSequence;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        RecordingController recordingController = this.mController;
        Objects.requireNonNull(recordingController);
        boolean z3 = recordingController.mIsStarting;
        boolean isRecording = this.mController.isRecording();
        if (isRecording || z3) {
            z = true;
        } else {
            z = false;
        }
        booleanState.value = z;
        if (isRecording || z3) {
            i = 2;
        } else {
            i = 1;
        }
        booleanState.state = i;
        booleanState.label = this.mContext.getString(C1777R.string.quick_settings_screen_record_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_screenrecord);
        if (booleanState.state == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        booleanState.forceExpandIcon = z2;
        if (isRecording) {
            booleanState.secondaryLabel = this.mContext.getString(C1777R.string.quick_settings_screen_record_stop);
        } else if (z3) {
            booleanState.secondaryLabel = String.format("%d...", new Object[]{Integer.valueOf((int) Math.floorDiv(this.mMillisUntilFinished + 500, 1000))});
        } else {
            booleanState.secondaryLabel = this.mContext.getString(C1777R.string.quick_settings_screen_record_start);
        }
        if (TextUtils.isEmpty(booleanState.secondaryLabel)) {
            charSequence = booleanState.label;
        } else {
            charSequence = TextUtils.concat(new CharSequence[]{booleanState.label, ", ", booleanState.secondaryLabel});
        }
        booleanState.contentDescription = charSequence;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = this.mContext.getString(C1777R.string.quick_settings_screen_record_label);
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    public ScreenRecordTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, RecordingController recordingController, KeyguardDismissUtil keyguardDismissUtil, KeyguardStateController keyguardStateController, DialogLaunchAnimator dialogLaunchAnimator) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback();
        this.mController = recordingController;
        Objects.requireNonNull(recordingController);
        recordingController.observe((Lifecycle) this.mLifecycle, callback);
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mKeyguardStateController = keyguardStateController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
    }
}
