package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.statusbar.IStatusBarService;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda2;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.google.android.systemui.gamedashboard.ShortcutBarView$$ExternalSyntheticLambda0;
import java.util.Objects;

public class FeedbackInfo extends LinearLayout implements NotificationGuts.GutsContent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String mAppName;
    public NotificationEntry mEntry;
    public ExpandableNotificationRow mExpandableNotificationRow;
    public AssistantFeedbackController mFeedbackController;
    public NotificationGuts mGutsContainer;
    public NotificationMenuRowPlugin mMenuRowPlugin;
    public NotificationGutsManager mNotificationGutsManager;
    public String mPkg;
    public PackageManager mPm;
    public NotificationListenerService.Ranking mRanking;
    public IStatusBarService mStatusBarService;

    public final View getContentView() {
        return this;
    }

    public final boolean handleCloseControls(boolean z, boolean z2) {
        return false;
    }

    public final boolean needsFalsingProtection() {
        return false;
    }

    public final boolean shouldBeSaved() {
        return false;
    }

    public final boolean willBeRemoved() {
        return false;
    }

    public final void handleFeedback(boolean z) {
        int i;
        Bundle bundle = new Bundle();
        if (z) {
            i = 1;
        } else {
            i = -1;
        }
        bundle.putInt("feedback.rating", i);
        AssistantFeedbackController assistantFeedbackController = this.mFeedbackController;
        Objects.requireNonNull(assistantFeedbackController);
        if (assistantFeedbackController.mFeedbackEnabled) {
            try {
                this.mStatusBarService.onNotificationFeedbackReceived(this.mRanking.getKey(), bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    public static void $r8$lambda$v7f9kE2ar9FL2Q9Wqe6UnRy2T6A(FeedbackInfo feedbackInfo, View view) {
        Objects.requireNonNull(feedbackInfo);
        ExpandableNotificationRow expandableNotificationRow = feedbackInfo.mExpandableNotificationRow;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
        feedbackInfo.mMenuRowPlugin = notificationMenuRowPlugin;
        NotificationMenuRowPlugin.MenuItem menuItem = null;
        if (notificationMenuRowPlugin != null) {
            menuItem = notificationMenuRowPlugin.getLongpressMenuItem(feedbackInfo.mContext);
        }
        feedbackInfo.mGutsContainer.closeControls(view, false);
        feedbackInfo.mNotificationGutsManager.openGuts(feedbackInfo.mExpandableNotificationRow, 0, 0, menuItem);
        feedbackInfo.handleFeedback(false);
    }

    public final void bindGuts(PackageManager packageManager, StatusBarNotification statusBarNotification, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, AssistantFeedbackController assistantFeedbackController) {
        Drawable drawable;
        this.mPkg = statusBarNotification.getPackageName();
        this.mPm = packageManager;
        this.mEntry = notificationEntry;
        this.mExpandableNotificationRow = expandableNotificationRow;
        Objects.requireNonNull(notificationEntry);
        this.mRanking = notificationEntry.mRanking;
        this.mFeedbackController = assistantFeedbackController;
        this.mAppName = this.mPkg;
        this.mStatusBarService = (IStatusBarService) Dependency.get(IStatusBarService.class);
        this.mNotificationGutsManager = (NotificationGutsManager) Dependency.get(NotificationGutsManager.class);
        try {
            ApplicationInfo applicationInfo = this.mPm.getApplicationInfo(this.mPkg, 795136);
            if (applicationInfo != null) {
                this.mAppName = String.valueOf(this.mPm.getApplicationLabel(applicationInfo));
                drawable = this.mPm.getApplicationIcon(applicationInfo);
            } else {
                drawable = null;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            drawable = this.mPm.getDefaultActivityIcon();
        }
        ((ImageView) findViewById(C1777R.C1779id.pkg_icon)).setImageDrawable(drawable);
        ((TextView) findViewById(C1777R.C1779id.pkg_name)).setText(this.mAppName);
        TextView textView = (TextView) findViewById(C1777R.C1779id.prompt);
        TextView textView2 = (TextView) findViewById(C1777R.C1779id.yes);
        TextView textView3 = (TextView) findViewById(C1777R.C1779id.f91no);
        textView2.setVisibility(0);
        textView3.setVisibility(0);
        textView2.setOnClickListener(new NavigationBar$$ExternalSyntheticLambda2(this, 1));
        textView3.setOnClickListener(new ShortcutBarView$$ExternalSyntheticLambda0(this, 1));
        StringBuilder sb = new StringBuilder();
        int feedbackStatus = this.mFeedbackController.getFeedbackStatus(this.mEntry);
        if (feedbackStatus == 1) {
            sb.append(this.mContext.getText(C1777R.string.feedback_alerted));
        } else if (feedbackStatus == 2) {
            sb.append(this.mContext.getText(C1777R.string.feedback_silenced));
        } else if (feedbackStatus == 3) {
            sb.append(this.mContext.getText(C1777R.string.feedback_promoted));
        } else if (feedbackStatus == 4) {
            sb.append(this.mContext.getText(C1777R.string.feedback_demoted));
        }
        sb.append(" ");
        sb.append(this.mContext.getText(C1777R.string.feedback_prompt));
        textView.setText(Html.fromHtml(sb.toString()));
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

    public FeedbackInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
