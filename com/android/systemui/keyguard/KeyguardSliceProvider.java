package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceProvider;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import androidx.slice.builders.impl.TemplateBuilderImpl;
import androidx.slice.core.SliceActionImpl;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.SystemUIAppComponentFactory;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class KeyguardSliceProvider extends SliceProvider implements NextAlarmController.NextAlarmChangeCallback, ZenModeController.Callback, NotificationMediaManager.MediaListener, StatusBarStateController.StateListener, SystemUIAppComponentFactory.ContextInitializer {
    @VisibleForTesting
    public static final int ALARM_VISIBILITY_HOURS = 12;
    public static KeyguardSliceProvider sInstance;
    public static final Object sInstanceLock = new Object();
    public AlarmManager mAlarmManager;
    public final Uri mAlarmUri = Uri.parse("content://com.android.systemui.keyguard/alarm");
    public ContentResolver mContentResolver;
    public SystemUIAppComponentFactory.ContextAvailableCallback mContextAvailableCallback;
    public final Date mCurrentTime = new Date();
    public DateFormat mDateFormat;
    public String mDatePattern;
    public final Uri mDateUri = Uri.parse("content://com.android.systemui.keyguard/date");
    public final Uri mDndUri = Uri.parse("content://com.android.systemui.keyguard/dnd");
    public DozeParameters mDozeParameters;
    public boolean mDozing;
    public final Handler mHandler = new Handler();
    public final Uri mHeaderUri = Uri.parse("content://com.android.systemui.keyguard/header");
    @VisibleForTesting
    public final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.DATE_CHANGED".equals(action)) {
                synchronized (this) {
                    KeyguardSliceProvider.this.updateClockLocked();
                }
            } else if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                synchronized (this) {
                    KeyguardSliceProvider.this.cleanDateFormatLocked();
                }
            }
        }
    };
    public KeyguardBypassController mKeyguardBypassController;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    @VisibleForTesting
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
        public final void onTimeChanged() {
            synchronized (this) {
                KeyguardSliceProvider.this.updateClockLocked();
            }
        }

        public final void onTimeZoneChanged(TimeZone timeZone) {
            synchronized (this) {
                KeyguardSliceProvider.this.cleanDateFormatLocked();
            }
        }
    };
    public String mLastText;
    public CharSequence mMediaArtist;
    public final Handler mMediaHandler = new Handler();
    public boolean mMediaIsVisible;
    public NotificationMediaManager mMediaManager;
    public CharSequence mMediaTitle;
    public final Uri mMediaUri = Uri.parse("content://com.android.systemui.keyguard/media");
    @VisibleForTesting
    public SettableWakeLock mMediaWakeLock;
    public String mNextAlarm;
    public NextAlarmController mNextAlarmController;
    public AlarmManager.AlarmClockInfo mNextAlarmInfo;
    public PendingIntent mPendingIntent;
    public boolean mRegistered;
    public final Uri mSliceUri = Uri.parse("content://com.android.systemui.keyguard/main");
    public int mStatusBarState;
    public StatusBarStateController mStatusBarStateController;
    public final KeyguardSliceProvider$$ExternalSyntheticLambda0 mUpdateNextAlarm = new KeyguardSliceProvider$$ExternalSyntheticLambda0(this);
    public ZenModeController mZenModeController;

    @VisibleForTesting
    public void cleanDateFormatLocked() {
        this.mDateFormat = null;
    }

    @VisibleForTesting
    public boolean isRegistered() {
        boolean z;
        synchronized (this) {
            z = this.mRegistered;
        }
        return z;
    }

    public final void onDozingChanged(boolean z) {
        boolean z2;
        synchronized (this) {
            boolean needsMediaLocked = needsMediaLocked();
            this.mDozing = z;
            if (needsMediaLocked != needsMediaLocked()) {
                z2 = true;
            } else {
                z2 = false;
            }
        }
        if (z2) {
            notifyChange();
        }
    }

    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        long triggerTime;
        synchronized (this) {
            this.mNextAlarmInfo = alarmClockInfo;
            this.mAlarmManager.cancel(this.mUpdateNextAlarm);
            AlarmManager.AlarmClockInfo alarmClockInfo2 = this.mNextAlarmInfo;
            if (alarmClockInfo2 == null) {
                triggerTime = -1;
            } else {
                triggerTime = alarmClockInfo2.getTriggerTime() - TimeUnit.HOURS.toMillis(12);
            }
            long j = triggerTime;
            if (j > 0) {
                this.mAlarmManager.setExact(1, j, "lock_screen_next_alarm", this.mUpdateNextAlarm, this.mHandler);
            }
        }
        updateNextAlarm();
    }

    public final void onPrimaryMetadataOrStateChanged(MediaMetadata mediaMetadata, int i) {
        synchronized (this) {
            boolean isPlayingState = NotificationMediaManager.isPlayingState(i);
            this.mMediaHandler.removeCallbacksAndMessages((Object) null);
            if (!this.mMediaIsVisible || isPlayingState || this.mStatusBarState == 0) {
                this.mMediaWakeLock.setAcquired(false);
                updateMediaStateLocked(mediaMetadata, i);
            } else {
                this.mMediaWakeLock.setAcquired(true);
                this.mMediaHandler.postDelayed(new KeyguardSliceProvider$$ExternalSyntheticLambda1(this, mediaMetadata, i), 2000);
            }
        }
    }

    public final void onStateChanged(int i) {
        boolean z;
        synchronized (this) {
            boolean needsMediaLocked = needsMediaLocked();
            this.mStatusBarState = i;
            if (needsMediaLocked != needsMediaLocked()) {
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            notifyChange();
        }
    }

    @VisibleForTesting
    public void registerClockUpdate() {
        synchronized (this) {
            if (!this.mRegistered) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.DATE_CHANGED");
                intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
                getContext().registerReceiver(this.mIntentReceiver, intentFilter, (String) null, (Handler) null);
                this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
                this.mRegistered = true;
            }
        }
    }

    public final void updateNextAlarm() {
        String str;
        synchronized (this) {
            boolean z = false;
            if (this.mNextAlarmInfo != null) {
                if (this.mNextAlarmInfo.getTriggerTime() <= TimeUnit.HOURS.toMillis((long) 12) + System.currentTimeMillis()) {
                    z = true;
                }
            }
            if (z) {
                if (android.text.format.DateFormat.is24HourFormat(getContext(), ActivityManager.getCurrentUser())) {
                    str = "HH:mm";
                } else {
                    str = "h:mm";
                }
                this.mNextAlarm = android.text.format.DateFormat.format(str, this.mNextAlarmInfo.getTriggerTime()).toString();
            } else {
                this.mNextAlarm = "";
            }
        }
        notifyChange();
    }

    static {
        new StyleSpan(1);
    }

    public final void addMediaLocked(ListBuilder listBuilder) {
        Icon icon;
        if (!TextUtils.isEmpty(this.mMediaTitle)) {
            ListBuilder.HeaderBuilder headerBuilder = new ListBuilder.HeaderBuilder(this.mHeaderUri);
            headerBuilder.mTitle = this.mMediaTitle;
            headerBuilder.mTitleLoading = false;
            listBuilder.mImpl.setHeader(headerBuilder);
            if (!TextUtils.isEmpty(this.mMediaArtist)) {
                ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mMediaUri);
                rowBuilder.mTitle = this.mMediaArtist;
                rowBuilder.mTitleLoading = false;
                NotificationMediaManager notificationMediaManager = this.mMediaManager;
                IconCompat iconCompat = null;
                if (notificationMediaManager == null) {
                    icon = null;
                } else {
                    icon = notificationMediaManager.getMediaIcon();
                }
                if (icon != null) {
                    iconCompat = IconCompat.createFromIcon(getContext(), icon);
                }
                if (iconCompat != null) {
                    rowBuilder.addEndItem(iconCompat, 0);
                }
                listBuilder.addRow(rowBuilder);
            }
        }
    }

    public final void addNextAlarmLocked(ListBuilder listBuilder) {
        if (!TextUtils.isEmpty(this.mNextAlarm)) {
            IconCompat createWithResource = IconCompat.createWithResource(getContext(), C1777R.C1778drawable.ic_access_alarms_big);
            ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mAlarmUri);
            rowBuilder.mTitle = this.mNextAlarm;
            rowBuilder.mTitleLoading = false;
            rowBuilder.addEndItem(createWithResource, 0);
            listBuilder.addRow(rowBuilder);
        }
    }

    public final void addZenModeLocked(ListBuilder listBuilder) {
        boolean z;
        if (this.mZenModeController.getZen() != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mDndUri);
            rowBuilder.mContentDescription = getContext().getResources().getString(C1777R.string.accessibility_quick_settings_dnd);
            rowBuilder.addEndItem(IconCompat.createWithResource(getContext(), C1777R.C1778drawable.stat_sys_dnd), 0);
            listBuilder.addRow(rowBuilder);
        }
    }

    public final String getFormattedDateLocked() {
        if (this.mDateFormat == null) {
            DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(this.mDatePattern, Locale.getDefault());
            instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
            this.mDateFormat = instanceForSkeleton;
        }
        this.mCurrentTime.setTime(System.currentTimeMillis());
        return this.mDateFormat.format(this.mCurrentTime);
    }

    public final boolean needsMediaLocked() {
        boolean z;
        boolean z2;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        if (keyguardBypassController == null || !keyguardBypassController.getBypassEnabled() || !this.mDozeParameters.getAlwaysOn()) {
            z = false;
        } else {
            z = true;
        }
        if (this.mStatusBarState != 0 || !this.mMediaIsVisible) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (TextUtils.isEmpty(this.mMediaTitle) || !this.mMediaIsVisible || (!this.mDozing && !z && !z2)) {
            return false;
        }
        return true;
    }

    public final void notifyChange() {
        this.mContentResolver.notifyChange(this.mSliceUri, (ContentObserver) null);
    }

    public Slice onBindSlice() {
        Slice build;
        Trace.beginSection("KeyguardSliceProvider#onBindSlice");
        synchronized (this) {
            ListBuilder listBuilder = new ListBuilder(getContext(), this.mSliceUri);
            if (needsMediaLocked()) {
                addMediaLocked(listBuilder);
            } else {
                ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mDateUri);
                rowBuilder.mTitle = this.mLastText;
                rowBuilder.mTitleLoading = false;
                listBuilder.addRow(rowBuilder);
            }
            addNextAlarmLocked(listBuilder);
            addZenModeLocked(listBuilder);
            addPrimaryActionLocked(listBuilder);
            build = ((TemplateBuilderImpl) listBuilder.mImpl).build();
        }
        Trace.endSection();
        return build;
    }

    public void onCreateSliceProvider() {
        this.mContextAvailableCallback.onContextAvailable(getContext());
        this.mMediaWakeLock = new SettableWakeLock(WakeLock.createPartial(getContext(), "media"), "media");
        synchronized (sInstanceLock) {
            KeyguardSliceProvider keyguardSliceProvider = sInstance;
            if (keyguardSliceProvider != null) {
                keyguardSliceProvider.onDestroy();
            }
            this.mDatePattern = getContext().getString(C1777R.string.system_ui_aod_date_pattern);
            this.mPendingIntent = PendingIntent.getActivity(getContext(), 0, new Intent(getContext(), KeyguardSliceProvider.class), 67108864);
            this.mMediaManager.addCallback(this);
            this.mStatusBarStateController.addCallback(this);
            this.mNextAlarmController.addCallback(this);
            this.mZenModeController.addCallback(this);
            sInstance = this;
            registerClockUpdate();
            updateClockLocked();
        }
    }

    @VisibleForTesting
    public void onDestroy() {
        synchronized (sInstanceLock) {
            this.mNextAlarmController.removeCallback(this);
            this.mZenModeController.removeCallback(this);
            this.mMediaWakeLock.setAcquired(false);
            this.mAlarmManager.cancel(this.mUpdateNextAlarm);
            if (this.mRegistered) {
                this.mRegistered = false;
                this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
                getContext().unregisterReceiver(this.mIntentReceiver);
            }
            sInstance = null;
        }
    }

    public final void addPrimaryActionLocked(ListBuilder listBuilder) {
        SliceAction sliceAction = new SliceAction(this.mPendingIntent, IconCompat.createWithResource(getContext(), C1777R.C1778drawable.ic_access_alarms_big), 0, this.mLastText);
        SliceActionImpl sliceActionImpl = sliceAction.mSliceAction;
        Objects.requireNonNull(sliceActionImpl);
        sliceActionImpl.mIsActivity = true;
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(Uri.parse("content://com.android.systemui.keyguard/action"));
        rowBuilder.mPrimaryAction = sliceAction;
        listBuilder.addRow(rowBuilder);
    }

    public void updateClockLocked() {
        String formattedDateLocked = getFormattedDateLocked();
        if (!formattedDateLocked.equals(this.mLastText)) {
            this.mLastText = formattedDateLocked;
            notifyChange();
        }
    }

    public final void updateMediaStateLocked(MediaMetadata mediaMetadata, int i) {
        CharSequence charSequence;
        boolean isPlayingState = NotificationMediaManager.isPlayingState(i);
        CharSequence charSequence2 = null;
        if (mediaMetadata != null) {
            charSequence = mediaMetadata.getText("android.media.metadata.TITLE");
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = getContext().getResources().getString(C1777R.string.music_controls_no_title);
            }
        } else {
            charSequence = null;
        }
        if (mediaMetadata != null) {
            charSequence2 = mediaMetadata.getText("android.media.metadata.ARTIST");
        }
        if (isPlayingState != this.mMediaIsVisible || !TextUtils.equals(charSequence, this.mMediaTitle) || !TextUtils.equals(charSequence2, this.mMediaArtist)) {
            this.mMediaTitle = charSequence;
            this.mMediaArtist = charSequence2;
            this.mMediaIsVisible = isPlayingState;
            notifyChange();
        }
    }

    public final void onZenChanged(int i) {
        notifyChange();
    }

    public final void setContextAvailableCallback(SystemUIAppComponentFactory.ContextAvailableCallback contextAvailableCallback) {
        this.mContextAvailableCallback = contextAvailableCallback;
    }

    public final void onConfigChanged() {
        notifyChange();
    }
}
