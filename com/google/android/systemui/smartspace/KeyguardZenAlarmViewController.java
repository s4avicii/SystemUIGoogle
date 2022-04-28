package com.google.android.systemui.smartspace;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Handler;
import android.text.format.DateFormat;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.KFunction;

/* compiled from: KeyguardZenAlarmViewController.kt */
public final class KeyguardZenAlarmViewController {
    public final Drawable alarmImage;
    public final AlarmManager alarmManager;
    public final Context context;
    public final Drawable dndImage;
    public final Handler handler;
    public final KeyguardZenAlarmViewController$nextAlarmCallback$1 nextAlarmCallback = new KeyguardZenAlarmViewController$nextAlarmCallback$1(this);
    public final NextAlarmController nextAlarmController;
    public final BcSmartspaceDataPlugin plugin;
    public final KFunction<Unit> showNextAlarm = new KeyguardZenAlarmViewController$showNextAlarm$1(this);
    public LinkedHashSet smartspaceViews = new LinkedHashSet();
    public final KeyguardZenAlarmViewController$zenModeCallback$1 zenModeCallback = new KeyguardZenAlarmViewController$zenModeCallback$1(this);
    public final ZenModeController zenModeController;

    @VisibleForTesting
    public static /* synthetic */ void getSmartspaceViews$annotations() {
    }

    @VisibleForTesting
    public final void showAlarm() {
        boolean z;
        String str;
        long nextAlarm = this.zenModeController.getNextAlarm();
        if (nextAlarm > 0) {
            if (nextAlarm <= TimeUnit.HOURS.toMillis(12) + System.currentTimeMillis()) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                if (DateFormat.is24HourFormat(this.context, ActivityManager.getCurrentUser())) {
                    str = "HH:mm";
                } else {
                    str = "h:mm";
                }
                String obj = DateFormat.format(str, nextAlarm).toString();
                for (BcSmartspaceDataPlugin.SmartspaceView nextAlarm2 : this.smartspaceViews) {
                    nextAlarm2.setNextAlarm(this.alarmImage, obj);
                }
                return;
            }
        }
        for (BcSmartspaceDataPlugin.SmartspaceView nextAlarm3 : this.smartspaceViews) {
            nextAlarm3.setNextAlarm((Drawable) null, (String) null);
        }
    }

    @VisibleForTesting
    public final void updateDnd() {
        boolean z;
        if (this.zenModeController.getZen() != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            String string = this.context.getResources().getString(C1777R.string.accessibility_quick_settings_dnd);
            for (BcSmartspaceDataPlugin.SmartspaceView dnd : this.smartspaceViews) {
                dnd.setDnd(this.dndImage, string);
            }
            return;
        }
        for (BcSmartspaceDataPlugin.SmartspaceView dnd2 : this.smartspaceViews) {
            dnd2.setDnd((Drawable) null, (String) null);
        }
    }

    public final void updateNextAlarm() {
        this.alarmManager.cancel(new C2313x126337b1((Function0) this.showNextAlarm));
        long nextAlarm = this.zenModeController.getNextAlarm();
        if (nextAlarm > 0) {
            long millis = nextAlarm - TimeUnit.HOURS.toMillis(12);
            if (millis > 0) {
                this.alarmManager.setExact(1, millis, "lock_screen_next_alarm", new C2313x126337b1((Function0) this.showNextAlarm), this.handler);
            }
        }
        showAlarm();
    }

    public KeyguardZenAlarmViewController(Context context2, BcSmartspaceDataPlugin bcSmartspaceDataPlugin, ZenModeController zenModeController2, AlarmManager alarmManager2, NextAlarmController nextAlarmController2, Handler handler2) {
        this.context = context2;
        this.plugin = bcSmartspaceDataPlugin;
        this.zenModeController = zenModeController2;
        this.alarmManager = alarmManager2;
        this.nextAlarmController = nextAlarmController2;
        this.handler = handler2;
        Drawable drawable = context2.getResources().getDrawable(C1777R.C1778drawable.stat_sys_dnd, (Resources.Theme) null);
        Objects.requireNonNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.InsetDrawable");
        this.dndImage = ((InsetDrawable) drawable).getDrawable();
        this.alarmImage = context2.getResources().getDrawable(C1777R.C1778drawable.ic_access_alarms_big, (Resources.Theme) null);
    }
}
