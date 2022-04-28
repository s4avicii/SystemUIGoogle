package com.android.settingslib.notification;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSDndEvent;
import com.android.systemui.p006qs.tiles.dialog.QSZenModeDialogMetricsLogger;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public final class EnableZenModeDialog {
    @VisibleForTesting
    public static final int COUNTDOWN_ALARM_CONDITION_INDEX = 2;
    @VisibleForTesting
    public static final int COUNTDOWN_CONDITION_INDEX = 1;
    public static final boolean DEBUG = Log.isLoggable("EnableZenModeDialog", 3);
    public static final int DEFAULT_BUCKET_INDEX;
    @VisibleForTesting
    public static final int FOREVER_CONDITION_INDEX = 0;
    public static final int MAX_BUCKET_MINUTES;
    public static final int[] MINUTE_BUCKETS;
    public static final int MIN_BUCKET_MINUTES;
    public AlarmManager mAlarmManager;
    public int mBucketIndex = -1;
    @VisibleForTesting
    public Context mContext;
    @VisibleForTesting
    public Uri mForeverId;
    @VisibleForTesting
    public LayoutInflater mLayoutInflater;
    public final ZenModeDialogMetricsLogger mMetricsLogger;
    @VisibleForTesting
    public NotificationManager mNotificationManager;
    public int mUserId;
    @VisibleForTesting
    public TextView mZenAlarmWarning;
    public RadioGroup mZenRadioGroup;
    @VisibleForTesting
    public LinearLayout mZenRadioGroupContent;

    @VisibleForTesting
    public static class ConditionTag {
        public Condition condition;
        public TextView line1;
        public TextView line2;
        public View lines;

        /* renamed from: rb */
        public RadioButton f36rb;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0060, code lost:
        r0.mBucketIndex = r6;
        r5 = android.service.notification.ZenModeConfig.toTimeCondition(r0.mContext, r15, r17, android.app.ActivityManager.getCurrentUser(), false);
     */
    /* renamed from: -$$Nest$monClickTimeButton  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m161$$Nest$monClickTimeButton(com.android.settingslib.notification.EnableZenModeDialog r20, android.view.View r21, com.android.settingslib.notification.EnableZenModeDialog.ConditionTag r22, boolean r23, int r24) {
        /*
            r0 = r20
            r1 = r22
            r2 = r23
            java.util.Objects.requireNonNull(r20)
            com.android.settingslib.notification.ZenModeDialogMetricsLogger r3 = r0.mMetricsLogger
            com.android.systemui.qs.tiles.dialog.QSZenModeDialogMetricsLogger r3 = (com.android.systemui.p006qs.tiles.dialog.QSZenModeDialogMetricsLogger) r3
            java.util.Objects.requireNonNull(r3)
            android.content.Context r4 = r3.mContext
            r5 = 163(0xa3, float:2.28E-43)
            com.android.internal.logging.MetricsLogger.action(r4, r5, r2)
            com.android.internal.logging.UiEventLoggerImpl r3 = r3.mUiEventLogger
            if (r2 == 0) goto L_0x001e
            com.android.systemui.qs.QSDndEvent r4 = com.android.systemui.p006qs.QSDndEvent.QS_DND_TIME_UP
            goto L_0x0020
        L_0x001e:
            com.android.systemui.qs.QSDndEvent r4 = com.android.systemui.p006qs.QSDndEvent.QS_DND_TIME_DOWN
        L_0x0020:
            r3.log(r4)
            int[] r3 = MINUTE_BUCKETS
            int r4 = r3.length
            int r5 = r0.mBucketIndex
            r6 = -1
            r7 = 1
            r8 = 0
            if (r5 != r6) goto L_0x0087
            android.service.notification.Condition r3 = r1.condition
            r5 = 0
            if (r3 == 0) goto L_0x0035
            android.net.Uri r3 = r3.id
            goto L_0x0036
        L_0x0035:
            r3 = r5
        L_0x0036:
            long r9 = android.service.notification.ZenModeConfig.tryParseCountdownConditionId(r3)
            long r11 = java.lang.System.currentTimeMillis()
            r3 = r8
        L_0x003f:
            if (r3 >= r4) goto L_0x0072
            if (r2 == 0) goto L_0x0045
            r6 = r3
            goto L_0x0048
        L_0x0045:
            int r6 = r4 + -1
            int r6 = r6 - r3
        L_0x0048:
            int[] r13 = MINUTE_BUCKETS
            r17 = r13[r6]
            r13 = 60000(0xea60, float:8.4078E-41)
            int r13 = r13 * r17
            long r13 = (long) r13
            long r15 = r11 + r13
            if (r2 == 0) goto L_0x005a
            int r13 = (r15 > r9 ? 1 : (r15 == r9 ? 0 : -1))
            if (r13 > 0) goto L_0x0060
        L_0x005a:
            if (r2 != 0) goto L_0x006f
            int r13 = (r15 > r9 ? 1 : (r15 == r9 ? 0 : -1))
            if (r13 >= 0) goto L_0x006f
        L_0x0060:
            r0.mBucketIndex = r6
            android.content.Context r14 = r0.mContext
            int r18 = android.app.ActivityManager.getCurrentUser()
            r19 = 0
            android.service.notification.Condition r5 = android.service.notification.ZenModeConfig.toTimeCondition(r14, r15, r17, r18, r19)
            goto L_0x0072
        L_0x006f:
            int r3 = r3 + 1
            goto L_0x003f
        L_0x0072:
            if (r5 != 0) goto L_0x00a2
            int r2 = DEFAULT_BUCKET_INDEX
            r0.mBucketIndex = r2
            android.content.Context r3 = r0.mContext
            int[] r4 = MINUTE_BUCKETS
            r2 = r4[r2]
            int r4 = android.app.ActivityManager.getCurrentUser()
            android.service.notification.Condition r5 = android.service.notification.ZenModeConfig.toTimeCondition(r3, r2, r4)
            goto L_0x00a2
        L_0x0087:
            int r4 = r4 - r7
            if (r2 == 0) goto L_0x008b
            r6 = r7
        L_0x008b:
            int r5 = r5 + r6
            int r2 = java.lang.Math.min(r4, r5)
            int r2 = java.lang.Math.max(r8, r2)
            r0.mBucketIndex = r2
            android.content.Context r4 = r0.mContext
            r2 = r3[r2]
            int r3 = android.app.ActivityManager.getCurrentUser()
            android.service.notification.Condition r5 = android.service.notification.ZenModeConfig.toTimeCondition(r4, r2, r3)
        L_0x00a2:
            r2 = r21
            r3 = r24
            r0.bind(r5, r2, r3)
            android.service.notification.Condition r2 = r1.condition
            java.lang.String r2 = r0.computeAlarmWarningText(r2)
            android.widget.TextView r3 = r0.mZenAlarmWarning
            r3.setText(r2)
            android.widget.TextView r0 = r0.mZenAlarmWarning
            if (r2 != 0) goto L_0x00ba
            r8 = 8
        L_0x00ba:
            r0.setVisibility(r8)
            android.widget.RadioButton r0 = r1.f36rb
            r0.setChecked(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.notification.EnableZenModeDialog.m161$$Nest$monClickTimeButton(com.android.settingslib.notification.EnableZenModeDialog, android.view.View, com.android.settingslib.notification.EnableZenModeDialog$ConditionTag, boolean, int):void");
    }

    static {
        int[] iArr = ZenModeConfig.MINUTE_BUCKETS;
        MINUTE_BUCKETS = iArr;
        MIN_BUCKET_MINUTES = iArr[0];
        MAX_BUCKET_MINUTES = iArr[iArr.length - 1];
        DEFAULT_BUCKET_INDEX = Arrays.binarySearch(iArr, 60);
    }

    @VisibleForTesting
    public void bind(Condition condition, final View view, final int i) {
        boolean z;
        final ConditionTag conditionTag;
        boolean z2;
        String str;
        float f;
        float f2;
        boolean z3;
        boolean z4;
        if (condition != null) {
            boolean z5 = true;
            if (condition.state == 1) {
                z = true;
            } else {
                z = false;
            }
            if (view.getTag() != null) {
                conditionTag = (ConditionTag) view.getTag();
            } else {
                conditionTag = new ConditionTag();
            }
            view.setTag(conditionTag);
            RadioButton radioButton = conditionTag.f36rb;
            if (radioButton == null) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (radioButton == null) {
                conditionTag.f36rb = (RadioButton) this.mZenRadioGroup.getChildAt(i);
            }
            conditionTag.condition = condition;
            final Uri uri = condition.id;
            if (DEBUG) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("bind i=");
                m.append(this.mZenRadioGroupContent.indexOfChild(view));
                m.append(" first=");
                m.append(z2);
                m.append(" condition=");
                m.append(uri);
                Log.d("EnableZenModeDialog", m.toString());
            }
            conditionTag.f36rb.setEnabled(z);
            conditionTag.f36rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    int i;
                    if (z) {
                        conditionTag.f36rb.setChecked(true);
                        if (EnableZenModeDialog.DEBUG) {
                            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onCheckedChanged ");
                            m.append(uri);
                            Log.d("EnableZenModeDialog", m.toString());
                        }
                        QSZenModeDialogMetricsLogger qSZenModeDialogMetricsLogger = (QSZenModeDialogMetricsLogger) EnableZenModeDialog.this.mMetricsLogger;
                        Objects.requireNonNull(qSZenModeDialogMetricsLogger);
                        MetricsLogger.action(qSZenModeDialogMetricsLogger.mContext, 164);
                        qSZenModeDialogMetricsLogger.mUiEventLogger.log(QSDndEvent.QS_DND_CONDITION_SELECT);
                        EnableZenModeDialog enableZenModeDialog = EnableZenModeDialog.this;
                        Condition condition = conditionTag.condition;
                        Objects.requireNonNull(enableZenModeDialog);
                        String computeAlarmWarningText = enableZenModeDialog.computeAlarmWarningText(condition);
                        enableZenModeDialog.mZenAlarmWarning.setText(computeAlarmWarningText);
                        TextView textView = enableZenModeDialog.mZenAlarmWarning;
                        if (computeAlarmWarningText == null) {
                            i = 8;
                        } else {
                            i = 0;
                        }
                        textView.setVisibility(i);
                    }
                }
            });
            if (conditionTag.lines == null) {
                conditionTag.lines = view.findViewById(16908290);
            }
            if (conditionTag.line1 == null) {
                conditionTag.line1 = (TextView) view.findViewById(16908308);
            }
            if (conditionTag.line2 == null) {
                conditionTag.line2 = (TextView) view.findViewById(16908309);
            }
            if (!TextUtils.isEmpty(condition.line1)) {
                str = condition.line1;
            } else {
                str = condition.summary;
            }
            String str2 = condition.line2;
            conditionTag.line1.setText(str);
            if (TextUtils.isEmpty(str2)) {
                conditionTag.line2.setVisibility(8);
            } else {
                conditionTag.line2.setVisibility(0);
                conditionTag.line2.setText(str2);
            }
            conditionTag.lines.setEnabled(z);
            View view2 = conditionTag.lines;
            float f3 = 1.0f;
            if (z) {
                f = 1.0f;
            } else {
                f = 0.4f;
            }
            view2.setAlpha(f);
            conditionTag.lines.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    ConditionTag.this.f36rb.setChecked(true);
                }
            });
            long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(uri);
            ImageView imageView = (ImageView) view.findViewById(16908313);
            ImageView imageView2 = (ImageView) view.findViewById(16908314);
            if (i != 1 || tryParseCountdownConditionId <= 0) {
                if (imageView != null) {
                    ((ViewGroup) view).removeView(imageView);
                }
                if (imageView2 != null) {
                    ((ViewGroup) view).removeView(imageView2);
                }
            } else {
                imageView.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        EnableZenModeDialog.m161$$Nest$monClickTimeButton(EnableZenModeDialog.this, view, conditionTag, false, i);
                        conditionTag.lines.setAccessibilityLiveRegion(1);
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        EnableZenModeDialog.m161$$Nest$monClickTimeButton(EnableZenModeDialog.this, view, conditionTag, true, i);
                        conditionTag.lines.setAccessibilityLiveRegion(1);
                    }
                });
                int i2 = this.mBucketIndex;
                if (i2 > -1) {
                    if (i2 > 0) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    imageView.setEnabled(z4);
                    if (this.mBucketIndex >= MINUTE_BUCKETS.length - 1) {
                        z5 = false;
                    }
                    imageView2.setEnabled(z5);
                } else {
                    if (tryParseCountdownConditionId - System.currentTimeMillis() > ((long) (MIN_BUCKET_MINUTES * 60000))) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    imageView.setEnabled(z3);
                    imageView2.setEnabled(!Objects.equals(condition.summary, ZenModeConfig.toTimeCondition(this.mContext, MAX_BUCKET_MINUTES, ActivityManager.getCurrentUser()).summary));
                }
                if (imageView.isEnabled()) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.5f;
                }
                imageView.setAlpha(f2);
                if (!imageView2.isEnabled()) {
                    f3 = 0.5f;
                }
                imageView2.setAlpha(f3);
            }
            view.setVisibility(0);
            return;
        }
        throw new IllegalArgumentException("condition must not be null");
    }

    @VisibleForTesting
    public void bindGenericCountdown() {
        int i = DEFAULT_BUCKET_INDEX;
        this.mBucketIndex = i;
        bind(ZenModeConfig.toTimeCondition(this.mContext, MINUTE_BUCKETS[i], ActivityManager.getCurrentUser()), this.mZenRadioGroupContent.getChildAt(1), 1);
    }

    @VisibleForTesting
    public void bindNextAlarm(Condition condition) {
        boolean z;
        int i;
        View childAt = this.mZenRadioGroupContent.getChildAt(2);
        ConditionTag conditionTag = (ConditionTag) childAt.getTag();
        if (condition != null) {
            bind(condition, childAt, 2);
        }
        ConditionTag conditionTag2 = (ConditionTag) childAt.getTag();
        int i2 = 0;
        if (conditionTag2 == null || conditionTag2.condition == null) {
            z = false;
        } else {
            z = true;
        }
        View childAt2 = this.mZenRadioGroup.getChildAt(2);
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        childAt2.setVisibility(i);
        if (!z) {
            i2 = 8;
        }
        childAt.setVisibility(i2);
    }

    @VisibleForTesting
    public String computeAlarmWarningText(Condition condition) {
        boolean z;
        long j;
        int i;
        if ((this.mNotificationManager.getNotificationPolicy().priorityCategories & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(this.mUserId);
        if (nextAlarmClock != null) {
            j = nextAlarmClock.getTriggerTime();
        } else {
            j = 0;
        }
        if (j < currentTimeMillis) {
            return null;
        }
        if (condition == null || isForever(condition)) {
            i = C1777R.string.zen_alarm_warning_indef;
        } else {
            long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(condition.id);
            if (tryParseCountdownConditionId <= currentTimeMillis || j >= tryParseCountdownConditionId) {
                i = 0;
            } else {
                i = C1777R.string.zen_alarm_warning;
            }
        }
        if (i == 0) {
            return null;
        }
        return this.mContext.getResources().getString(i, new Object[]{getTime(j, currentTimeMillis)});
    }

    public final Condition forever() {
        return new Condition(Condition.newId(this.mContext).appendPath("forever").build(), this.mContext.getString(17041779), "", "", 0, 1, 0);
    }

    @VisibleForTesting
    public ConditionTag getConditionTagAt(int i) {
        return (ConditionTag) this.mZenRadioGroupContent.getChildAt(i).getTag();
    }

    @VisibleForTesting
    public String getTime(long j, long j2) {
        boolean z;
        String str;
        int i;
        if (j - j2 < 86400000) {
            z = true;
        } else {
            z = false;
        }
        boolean is24HourFormat = DateFormat.is24HourFormat(this.mContext, ActivityManager.getCurrentUser());
        if (z) {
            if (is24HourFormat) {
                str = "Hm";
            } else {
                str = "hma";
            }
        } else if (is24HourFormat) {
            str = "EEEHm";
        } else {
            str = "EEEhma";
        }
        CharSequence format = DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), str), j);
        if (z) {
            i = C1777R.string.alarm_template;
        } else {
            i = C1777R.string.alarm_template_far;
        }
        return this.mContext.getResources().getString(i, new Object[]{format});
    }

    @VisibleForTesting
    public Condition getTimeUntilNextAlarmCondition() {
        long j;
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(11, 0);
        gregorianCalendar.set(12, 0);
        gregorianCalendar.set(13, 0);
        gregorianCalendar.set(14, 0);
        gregorianCalendar.add(5, 6);
        AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(this.mUserId);
        if (nextAlarmClock != null) {
            j = nextAlarmClock.getTriggerTime();
        } else {
            j = 0;
        }
        if (j <= 0) {
            return null;
        }
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.setTimeInMillis(j);
        gregorianCalendar2.set(11, 0);
        gregorianCalendar2.set(12, 0);
        gregorianCalendar2.set(13, 0);
        gregorianCalendar2.set(14, 0);
        if (gregorianCalendar.compareTo(gregorianCalendar2) >= 0) {
            return ZenModeConfig.toNextAlarmCondition(this.mContext, j, ActivityManager.getCurrentUser());
        }
        return null;
    }

    @VisibleForTesting
    public boolean isAlarm(Condition condition) {
        if (condition == null || !ZenModeConfig.isValidCountdownToAlarmConditionId(condition.id)) {
            return false;
        }
        return true;
    }

    @VisibleForTesting
    public boolean isCountdown(Condition condition) {
        if (condition == null || !ZenModeConfig.isValidCountdownConditionId(condition.id)) {
            return false;
        }
        return true;
    }

    public final boolean isForever(Condition condition) {
        if (condition == null || !this.mForeverId.equals(condition.id)) {
            return false;
        }
        return true;
    }

    public EnableZenModeDialog(Context context, QSZenModeDialogMetricsLogger qSZenModeDialogMetricsLogger) {
        this.mContext = context;
        this.mMetricsLogger = qSZenModeDialogMetricsLogger;
    }

    @VisibleForTesting
    public void bindConditions(Condition condition) {
        bind(forever(), this.mZenRadioGroupContent.getChildAt(0), 0);
        if (condition == null) {
            bindGenericCountdown();
            bindNextAlarm(getTimeUntilNextAlarmCondition());
        } else if (isForever(condition)) {
            getConditionTagAt(0).f36rb.setChecked(true);
            bindGenericCountdown();
            bindNextAlarm(getTimeUntilNextAlarmCondition());
        } else if (isAlarm(condition)) {
            bindGenericCountdown();
            bindNextAlarm(condition);
            getConditionTagAt(2).f36rb.setChecked(true);
        } else if (isCountdown(condition)) {
            bindNextAlarm(getTimeUntilNextAlarmCondition());
            bind(condition, this.mZenRadioGroupContent.getChildAt(1), 1);
            getConditionTagAt(1).f36rb.setChecked(true);
        } else {
            Slog.d("EnableZenModeDialog", "Invalid manual condition: " + condition);
        }
    }
}
