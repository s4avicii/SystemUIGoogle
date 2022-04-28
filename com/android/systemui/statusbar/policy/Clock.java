package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.icu.text.DateTimePatternGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.widget.TextView;
import androidx.leanback.R$raw;
import com.android.keyguard.KeyguardStatusView$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.R$styleable;
import com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda1;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeCommandReceiver;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Clock extends TextView implements DemoModeCommandReceiver, TunerService.Tunable, CommandQueue.Callbacks, DarkIconDispatcher.DarkReceiver, ConfigurationController.ConfigurationListener {
    public final int mAmPmStyle;
    public boolean mAttached;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public Calendar mCalendar;
    public SimpleDateFormat mClockFormat;
    public String mClockFormatString;
    public boolean mClockVisibleByPolicy;
    public boolean mClockVisibleByUser;
    public final CommandQueue mCommandQueue;
    public SimpleDateFormat mContentDescriptionFormat;
    public int mCurrentUserId;
    public final C16041 mCurrentUserTracker;
    public boolean mDemoMode;
    public final C16052 mIntentReceiver;
    public Locale mLocale;
    public final C16063 mScreenReceiver;
    public boolean mScreenReceiverRegistered;
    public final C16074 mSecondTick;
    public Handler mSecondsHandler;
    public boolean mShowSeconds;

    public Clock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void onDemoModeFinished() {
        this.mDemoMode = false;
        updateClock();
    }

    public final void onDemoModeStarted() {
        this.mDemoMode = true;
    }

    /* JADX INFO: finally extract failed */
    public Clock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mClockVisibleByPolicy = true;
        this.mClockVisibleByUser = true;
        this.mIntentReceiver = new BroadcastReceiver() {
            public static final /* synthetic */ int $r8$clinit = 0;

            public final void onReceive(Context context, Intent intent) {
                Handler handler = Clock.this.getHandler();
                if (handler != null) {
                    String action = intent.getAction();
                    if (action.equals("android.intent.action.TIMEZONE_CHANGED")) {
                        handler.post(new SystemUIApplication$$ExternalSyntheticLambda1(this, intent.getStringExtra("time-zone"), 3));
                    } else if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        handler.post(new Clock$2$$ExternalSyntheticLambda0(this, Clock.this.getResources().getConfiguration().locale));
                    }
                    handler.post(new KeyguardStatusView$$ExternalSyntheticLambda0(this, 6));
                }
            }
        };
        this.mScreenReceiver = new BroadcastReceiver() {
            /* JADX WARNING: Code restructure failed: missing block: B:7:0x0020, code lost:
                r4 = r4.this$0;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void onReceive(android.content.Context r5, android.content.Intent r6) {
                /*
                    r4 = this;
                    java.lang.String r5 = r6.getAction()
                    java.lang.String r6 = "android.intent.action.SCREEN_OFF"
                    boolean r6 = r6.equals(r5)
                    if (r6 == 0) goto L_0x0018
                    com.android.systemui.statusbar.policy.Clock r4 = com.android.systemui.statusbar.policy.Clock.this
                    android.os.Handler r5 = r4.mSecondsHandler
                    if (r5 == 0) goto L_0x0034
                    com.android.systemui.statusbar.policy.Clock$4 r4 = r4.mSecondTick
                    r5.removeCallbacks(r4)
                    goto L_0x0034
                L_0x0018:
                    java.lang.String r6 = "android.intent.action.SCREEN_ON"
                    boolean r5 = r6.equals(r5)
                    if (r5 == 0) goto L_0x0034
                    com.android.systemui.statusbar.policy.Clock r4 = com.android.systemui.statusbar.policy.Clock.this
                    android.os.Handler r5 = r4.mSecondsHandler
                    if (r5 == 0) goto L_0x0034
                    com.android.systemui.statusbar.policy.Clock$4 r4 = r4.mSecondTick
                    long r0 = android.os.SystemClock.uptimeMillis()
                    r2 = 1000(0x3e8, double:4.94E-321)
                    long r0 = r0 / r2
                    long r0 = r0 * r2
                    long r0 = r0 + r2
                    r5.postAtTime(r4, r0)
                L_0x0034:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.Clock.C16063.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        this.mSecondTick = new Runnable() {
            public final void run() {
                Clock clock = Clock.this;
                if (clock.mCalendar != null) {
                    clock.updateClock();
                }
                Clock.this.mSecondsHandler.postAtTime(this, ((SystemClock.uptimeMillis() / 1000) * 1000) + 1000);
            }
        };
        this.mCommandQueue = (CommandQueue) Dependency.get(CommandQueue.class);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.Clock, 0, 0);
        try {
            this.mAmPmStyle = obtainStyledAttributes.getInt(0, 2);
            getCurrentTextColor();
            obtainStyledAttributes.recycle();
            BroadcastDispatcher broadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
            this.mBroadcastDispatcher = broadcastDispatcher;
            this.mCurrentUserTracker = new CurrentUserTracker(broadcastDispatcher) {
                public final void onUserSwitched(int i) {
                    Clock.this.mCurrentUserId = i;
                }
            };
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final void dispatchDemoCommand(String str, Bundle bundle) {
        String string = bundle.getString("millis");
        String string2 = bundle.getString("hhmm");
        if (string != null) {
            this.mCalendar.setTimeInMillis(Long.parseLong(string));
        } else if (string2 != null && string2.length() == 4) {
            int parseInt = Integer.parseInt(string2.substring(0, 2));
            int parseInt2 = Integer.parseInt(string2.substring(2));
            if (DateFormat.is24HourFormat(getContext(), this.mCurrentUserId)) {
                this.mCalendar.set(11, parseInt);
            } else {
                this.mCalendar.set(10, parseInt);
            }
            this.mCalendar.set(12, parseInt2);
        }
        setText(getSmallTime());
        setContentDescription(this.mContentDescriptionFormat.format(this.mCalendar.getTime()));
    }

    public final void onColorsChanged(boolean z) {
        int i;
        Context context = this.mContext;
        if (z) {
            i = 2132018190;
        } else {
            i = 2132018181;
        }
        setTextColor(Utils.getColorAttrDefaultColor(new ContextThemeWrapper(context, i), C1777R.attr.wallpaperTextColor));
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("clock_super_parcelable"));
        if (bundle.containsKey("current_user_id")) {
            this.mCurrentUserId = bundle.getInt("current_user_id");
        }
        this.mClockVisibleByPolicy = bundle.getBoolean("visible_by_policy", true);
        this.mClockVisibleByUser = bundle.getBoolean("visible_by_user", true);
        this.mShowSeconds = bundle.getBoolean("show_seconds", false);
        if (bundle.containsKey("visibility")) {
            super.setVisibility(bundle.getInt("visibility"));
        }
    }

    public final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clock_super_parcelable", super.onSaveInstanceState());
        bundle.putInt("current_user_id", this.mCurrentUserId);
        bundle.putBoolean("visible_by_policy", this.mClockVisibleByPolicy);
        bundle.putBoolean("visible_by_user", this.mClockVisibleByUser);
        bundle.putBoolean("show_seconds", this.mShowSeconds);
        bundle.putInt("visibility", getVisibility());
        return bundle;
    }

    public final void onTuningChanged(String str, String str2) {
        if ("clock_seconds".equals(str)) {
            this.mShowSeconds = TunerService.parseIntegerSwitch(str2, false);
            updateShowSeconds();
        } else if ("icon_blacklist".equals(str)) {
            this.mClockVisibleByUser = !StatusBarIconController.getIconHideList(getContext(), str2).contains("clock");
            updateClockVisibility();
            updateClockVisibility();
        }
    }

    public final void setVisibility(int i) {
        boolean z;
        if (i == 0) {
            if (!this.mClockVisibleByPolicy || !this.mClockVisibleByUser) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                return;
            }
        }
        super.setVisibility(i);
    }

    public final void updateClock() {
        if (!this.mDemoMode) {
            this.mCalendar.setTimeInMillis(System.currentTimeMillis());
            setText(getSmallTime());
            setContentDescription(this.mContentDescriptionFormat.format(this.mCalendar.getTime()));
        }
    }

    public final void updateClockVisibility() {
        boolean z;
        int i = 0;
        if (!this.mClockVisibleByPolicy || !this.mClockVisibleByUser) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            i = 8;
        }
        super.setVisibility(i);
    }

    public final void updateShowSeconds() {
        if (this.mShowSeconds) {
            if (this.mSecondsHandler == null && getDisplay() != null) {
                this.mSecondsHandler = new Handler();
                if (getDisplay().getState() == 2) {
                    this.mSecondsHandler.postAtTime(this.mSecondTick, ((SystemClock.uptimeMillis() / 1000) * 1000) + 1000);
                }
                this.mScreenReceiverRegistered = true;
                IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                this.mBroadcastDispatcher.registerReceiver(this.mScreenReceiver, intentFilter);
            }
        } else if (this.mSecondsHandler != null) {
            this.mScreenReceiverRegistered = false;
            this.mBroadcastDispatcher.unregisterReceiver(this.mScreenReceiver);
            this.mSecondsHandler.removeCallbacks(this.mSecondTick);
            this.mSecondsHandler = null;
            updateClock();
        }
    }

    public final void disable(int i, int i2, int i3, boolean z) {
        boolean z2;
        if (i == getDisplay().getDisplayId()) {
            if ((8388608 & i2) == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 != this.mClockVisibleByPolicy) {
                this.mClockVisibleByPolicy = z2;
                updateClockVisibility();
            }
        }
    }

    public final CharSequence getSmallTime() {
        String str;
        SimpleDateFormat simpleDateFormat;
        String str2;
        String str3;
        Context context = getContext();
        boolean is24HourFormat = DateFormat.is24HourFormat(context, this.mCurrentUserId);
        DateTimePatternGenerator instance = DateTimePatternGenerator.getInstance(context.getResources().getConfiguration().locale);
        if (this.mShowSeconds) {
            if (is24HourFormat) {
                str3 = "Hms";
            } else {
                str3 = "hms";
            }
            str = instance.getBestPattern(str3);
        } else {
            if (is24HourFormat) {
                str2 = "Hm";
            } else {
                str2 = "hm";
            }
            str = instance.getBestPattern(str2);
        }
        if (!str.equals(this.mClockFormatString)) {
            this.mContentDescriptionFormat = new SimpleDateFormat(str);
            if (this.mAmPmStyle != 0) {
                int i = 0;
                boolean z = false;
                while (true) {
                    if (i >= str.length()) {
                        i = -1;
                        break;
                    }
                    char charAt = str.charAt(i);
                    if (charAt == '\'') {
                        z = !z;
                    }
                    if (!z && charAt == 'a') {
                        break;
                    }
                    i++;
                }
                if (i >= 0) {
                    int i2 = i;
                    while (i2 > 0 && Character.isWhitespace(str.charAt(i2 - 1))) {
                        i2--;
                    }
                    str = str.substring(0, i2) + 61184 + str.substring(i2, i) + "a" + 61185 + str.substring(i + 1);
                }
            }
            simpleDateFormat = new SimpleDateFormat(str);
            this.mClockFormat = simpleDateFormat;
            this.mClockFormatString = str;
        } else {
            simpleDateFormat = this.mClockFormat;
        }
        String format = simpleDateFormat.format(this.mCalendar.getTime());
        if (this.mAmPmStyle != 0) {
            int indexOf = format.indexOf(61184);
            int indexOf2 = format.indexOf(61185);
            if (indexOf >= 0 && indexOf2 > indexOf) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(format);
                int i3 = this.mAmPmStyle;
                if (i3 == 2) {
                    spannableStringBuilder.delete(indexOf, indexOf2 + 1);
                } else {
                    if (i3 == 1) {
                        spannableStringBuilder.setSpan(new RelativeSizeSpan(0.7f), indexOf, indexOf2, 34);
                    }
                    spannableStringBuilder.delete(indexOf2, indexOf2 + 1);
                    spannableStringBuilder.delete(indexOf, indexOf + 1);
                }
                return spannableStringBuilder;
            }
        }
        return format;
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mAttached) {
            this.mAttached = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_TICK");
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            this.mBroadcastDispatcher.registerReceiverWithHandler(this.mIntentReceiver, intentFilter, (Handler) Dependency.get(Dependency.TIME_TICK_HANDLER), UserHandle.ALL);
            ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "clock_seconds", "icon_blacklist");
            this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
            this.mCurrentUserTracker.startTracking();
            this.mCurrentUserId = this.mCurrentUserTracker.getCurrentUserId();
        }
        this.mCalendar = Calendar.getInstance(TimeZone.getDefault());
        this.mClockFormatString = "";
        updateClock();
        updateClockVisibility();
        updateShowSeconds();
    }

    public final void onDarkChanged(ArrayList<Rect> arrayList, float f, int i) {
        setTextColor(DarkIconDispatcher.getTint(arrayList, this, i));
    }

    public final void onDensityOrFontScaleChanged() {
        R$raw.updateFontSize(this, C1777R.dimen.status_bar_clock_size);
        setPaddingRelative(this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.status_bar_clock_starting_padding), 0, this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.status_bar_clock_end_padding), 0);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mScreenReceiverRegistered) {
            this.mScreenReceiverRegistered = false;
            this.mBroadcastDispatcher.unregisterReceiver(this.mScreenReceiver);
            Handler handler = this.mSecondsHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mSecondTick);
                this.mSecondsHandler = null;
            }
        }
        if (this.mAttached) {
            this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
            this.mAttached = false;
            ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
            this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
            this.mCurrentUserTracker.stopTracking();
        }
    }
}
