package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationInfo;
import java.util.Objects;
import java.util.Set;

public class PartialConversationInfo extends LinearLayout implements NotificationGuts.GutsContent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String mAppName;
    public int mAppUid;
    public ChannelEditorDialogController mChannelEditorDialogController;
    public String mDelegatePkg;
    public NotificationGuts mGutsContainer;
    public boolean mIsDeviceProvisioned;
    public boolean mIsNonBlockable;
    public NotificationChannel mNotificationChannel;
    public KeyguardPatternViewController$$ExternalSyntheticLambda0 mOnDone = new KeyguardPatternViewController$$ExternalSyntheticLambda0(this, 1);
    public NotificationInfo.OnSettingsClickListener mOnSettingsClickListener;
    public String mPackageName;
    public Drawable mPkgIcon;
    public PackageManager mPm;
    public boolean mPresentingChannelEditorDialog = false;
    public boolean mPressedApply;
    public StatusBarNotification mSbn;
    @VisibleForTesting
    public boolean mSkipPost = false;
    public Set<NotificationChannel> mUniqueChannelsInRow;

    public final View getContentView() {
        return this;
    }

    public final boolean handleCloseControls(boolean z, boolean z2) {
        return false;
    }

    @VisibleForTesting
    public boolean isAnimating() {
        return false;
    }

    public final boolean needsFalsingProtection() {
        return true;
    }

    public final void onFinishedClosing() {
    }

    public final boolean willBeRemoved() {
        return false;
    }

    public final boolean post(Runnable runnable) {
        if (!this.mSkipPost) {
            return super.post(runnable);
        }
        runnable.run();
        return true;
    }

    public PartialConversationInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final int getActualHeight() {
        return getHeight();
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.mGutsContainer != null && accessibilityEvent.getEventType() == 32) {
            NotificationGuts notificationGuts = this.mGutsContainer;
            Objects.requireNonNull(notificationGuts);
            if (notificationGuts.mExposed) {
                accessibilityEvent.getText().add(this.mContext.getString(C1777R.string.notification_channel_controls_opened_accessibility, new Object[]{this.mAppName}));
                return;
            }
            accessibilityEvent.getText().add(this.mContext.getString(C1777R.string.notification_channel_controls_closed_accessibility, new Object[]{this.mAppName}));
        }
    }

    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
    }

    public final boolean shouldBeSaved() {
        return this.mPressedApply;
    }
}
