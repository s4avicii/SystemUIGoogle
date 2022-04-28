package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextClock;
import com.android.p012wm.shell.C1777R;

public class SplitClockView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextClock mAmPmView;
    public C16461 mIntentReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.TIME_SET".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action) || "android.intent.action.LOCALE_CHANGED".equals(action) || "android.intent.action.CONFIGURATION_CHANGED".equals(action) || "android.intent.action.USER_SWITCHED".equals(action)) {
                SplitClockView splitClockView = SplitClockView.this;
                int i = SplitClockView.$r8$clinit;
                splitClockView.updatePatterns();
            }
        }
    };
    public TextClock mTimeView;

    public SplitClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        getContext().registerReceiverAsUser(this.mIntentReceiver, UserHandle.ALL, intentFilter, (String) null, (Handler) null);
        updatePatterns();
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(this.mIntentReceiver);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeView = (TextClock) findViewById(C1777R.C1779id.time_view);
        this.mAmPmView = (TextClock) findViewById(C1777R.C1779id.am_pm_view);
        this.mTimeView.setShowCurrentUserTime(true);
        this.mAmPmView.setShowCurrentUserTime(true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updatePatterns() {
        /*
            r9 = this;
            android.content.Context r0 = r9.getContext()
            int r1 = android.app.ActivityManager.getCurrentUser()
            java.lang.String r0 = android.text.format.DateFormat.getTimeFormatString(r0, r1)
            int r1 = r0.length()
            r2 = 1
            int r1 = r1 - r2
            r3 = 0
            r4 = r1
            r5 = r3
        L_0x0015:
            r6 = -1
            if (r4 < 0) goto L_0x003b
            char r7 = r0.charAt(r4)
            r8 = 97
            if (r7 != r8) goto L_0x0022
            r8 = r2
            goto L_0x0023
        L_0x0022:
            r8 = r3
        L_0x0023:
            boolean r7 = java.lang.Character.isWhitespace(r7)
            if (r8 == 0) goto L_0x002a
            r5 = r2
        L_0x002a:
            if (r8 != 0) goto L_0x0038
            if (r7 == 0) goto L_0x002f
            goto L_0x0038
        L_0x002f:
            if (r4 != r1) goto L_0x0032
            goto L_0x0036
        L_0x0032:
            if (r5 == 0) goto L_0x0036
            int r4 = r4 + r2
            goto L_0x003e
        L_0x0036:
            r4 = r6
            goto L_0x003e
        L_0x0038:
            int r4 = r4 + -1
            goto L_0x0015
        L_0x003b:
            if (r5 == 0) goto L_0x0036
            r4 = r3
        L_0x003e:
            if (r4 != r6) goto L_0x0045
            java.lang.String r1 = ""
            r2 = r1
            r1 = r0
            goto L_0x004d
        L_0x0045:
            java.lang.String r1 = r0.substring(r3, r4)
            java.lang.String r2 = r0.substring(r4)
        L_0x004d:
            android.widget.TextClock r3 = r9.mTimeView
            r3.setFormat12Hour(r1)
            android.widget.TextClock r3 = r9.mTimeView
            r3.setFormat24Hour(r1)
            android.widget.TextClock r1 = r9.mTimeView
            r1.setContentDescriptionFormat12Hour(r0)
            android.widget.TextClock r1 = r9.mTimeView
            r1.setContentDescriptionFormat24Hour(r0)
            android.widget.TextClock r0 = r9.mAmPmView
            r0.setFormat12Hour(r2)
            android.widget.TextClock r9 = r9.mAmPmView
            r9.setFormat24Hour(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SplitClockView.updatePatterns():void");
    }
}
