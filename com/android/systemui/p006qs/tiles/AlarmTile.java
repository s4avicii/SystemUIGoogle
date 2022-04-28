package com.android.systemui.p006qs.tiles;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
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
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.NextAlarmController;
import java.util.Locale;
import kotlin.Unit;

/* renamed from: com.android.systemui.qs.tiles.AlarmTile */
/* compiled from: AlarmTile.kt */
public final class AlarmTile extends QSTileImpl<QSTile.State> {
    public final AlarmTile$callback$1 callback;
    public final Intent defaultIntent = new Intent("android.intent.action.SHOW_ALARMS");
    public final QSTile.Icon icon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_alarm);
    public AlarmManager.AlarmClockInfo lastAlarmInfo;
    public final UserTracker userTracker;

    /* renamed from: getDefaultIntent$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m65x39713f4d() {
    }

    public final Intent getLongClickIntent() {
        return null;
    }

    public final int getMetricsCategory() {
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleClick(android.view.View r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 != 0) goto L_0x0004
            goto L_0x0032
        L_0x0004:
            r1 = 32
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            android.view.ViewParent r2 = r5.getParent()
            boolean r2 = r2 instanceof android.view.ViewGroup
            if (r2 != 0) goto L_0x0034
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Skipping animation as view "
            r1.append(r2)
            r1.append(r5)
            java.lang.String r5 = " is not attached to a ViewGroup"
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            java.lang.Exception r1 = new java.lang.Exception
            r1.<init>()
            java.lang.String r2 = "ActivityLaunchAnimator"
            android.util.Log.wtf(r2, r5, r1)
        L_0x0032:
            r2 = r0
            goto L_0x003a
        L_0x0034:
            com.android.systemui.animation.GhostedViewLaunchAnimatorController r2 = new com.android.systemui.animation.GhostedViewLaunchAnimatorController
            r3 = 4
            r2.<init>((android.view.View) r5, (java.lang.Integer) r1, (int) r3)
        L_0x003a:
            android.app.AlarmManager$AlarmClockInfo r5 = r4.lastAlarmInfo
            if (r5 != 0) goto L_0x003f
            goto L_0x0043
        L_0x003f:
            android.app.PendingIntent r0 = r5.getShowIntent()
        L_0x0043:
            if (r0 == 0) goto L_0x004b
            com.android.systemui.plugins.ActivityStarter r4 = r4.mActivityStarter
            r4.postStartActivityDismissingKeyguard((android.app.PendingIntent) r0, (com.android.systemui.animation.ActivityLaunchAnimator.Controller) r2)
            goto L_0x0053
        L_0x004b:
            com.android.systemui.plugins.ActivityStarter r5 = r4.mActivityStarter
            android.content.Intent r4 = r4.defaultIntent
            r0 = 0
            r5.postStartActivityDismissingKeyguard(r4, r0, r2)
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tiles.AlarmTile.handleClick(android.view.View):void");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.status_bar_alarm);
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        Unit unit;
        String str;
        state.icon = this.icon;
        state.label = getTileLabel();
        AlarmManager.AlarmClockInfo alarmClockInfo = this.lastAlarmInfo;
        if (alarmClockInfo == null) {
            unit = null;
        } else {
            if (DateFormat.is24HourFormat(this.mContext, this.userTracker.getUserId())) {
                str = "EHm";
            } else {
                str = "Ehma";
            }
            state.secondaryLabel = DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), str), alarmClockInfo.getTriggerTime()).toString();
            state.state = 2;
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            state.secondaryLabel = this.mContext.getString(C1777R.string.qs_alarm_tile_no_alarm);
            state.state = 1;
        }
        state.contentDescription = TextUtils.concat(new CharSequence[]{state.label, ", ", state.secondaryLabel});
    }

    public final QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.handlesLongClick = false;
        return state;
    }

    public AlarmTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, UserTracker userTracker2, NextAlarmController nextAlarmController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.userTracker = userTracker2;
        AlarmTile$callback$1 alarmTile$callback$1 = new AlarmTile$callback$1(this);
        this.callback = alarmTile$callback$1;
        nextAlarmController.observe((LifecycleOwner) this, alarmTile$callback$1);
    }
}
