package com.android.systemui.statusbar.notification.row;

import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda3;
import com.android.settingslib.Utils;
import com.android.settingslib.notification.ConversationIconFactory;
import com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda5;
import com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.wmshell.BubblesManager;
import java.util.Objects;
import java.util.Optional;

public class NotificationConversationInfo extends LinearLayout implements NotificationGuts.GutsContent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActualHeight;
    public int mAppBubble;
    public String mAppName;
    public int mAppUid;
    public Handler mBgHandler;
    public Notification.BubbleMetadata mBubbleMetadata;
    public Optional<BubblesManager> mBubblesManagerOptional;
    public TextView mDefaultDescriptionView;
    public String mDelegatePkg;
    public NotificationEntry mEntry;
    public NotificationGuts mGutsContainer;
    public INotificationManager mINotificationManager;
    public ConversationIconFactory mIconFactory;
    public boolean mIsDeviceProvisioned;
    public Handler mMainHandler;
    public NotificationChannel mNotificationChannel;
    public LayoutPreference$$ExternalSyntheticLambda0 mOnDefaultClick = new LayoutPreference$$ExternalSyntheticLambda0(this, 4);
    public NotificationConversationInfo$$ExternalSyntheticLambda0 mOnDone = new NotificationConversationInfo$$ExternalSyntheticLambda0(this);
    public AuthBiometricView$$ExternalSyntheticLambda5 mOnFavoriteClick = new AuthBiometricView$$ExternalSyntheticLambda5(this, 4);
    public LongScreenshotActivity$$ExternalSyntheticLambda0 mOnMuteClick = new LongScreenshotActivity$$ExternalSyntheticLambda0(this, 1);
    public OnSettingsClickListener mOnSettingsClickListener;
    public OnUserInteractionCallback mOnUserInteractionCallback;
    public String mPackageName;
    public PeopleSpaceWidgetManager mPeopleSpaceWidgetManager;
    public PackageManager mPm;
    public boolean mPressedApply;
    public TextView mPriorityDescriptionView;
    public StatusBarNotification mSbn;
    public int mSelectedAction = -1;
    public ShadeController mShadeController;
    public ShortcutInfo mShortcutInfo;
    public TextView mSilentDescriptionView;
    @VisibleForTesting
    public boolean mSkipPost = false;

    public interface OnSettingsClickListener {
    }

    public class UpdateChannelRunnable implements Runnable {
        public final int mAction;
        public final String mAppPkg;
        public final int mAppUid;
        public NotificationChannel mChannelToUpdate;
        public final INotificationManager mINotificationManager;

        public UpdateChannelRunnable(INotificationManager iNotificationManager, String str, int i, int i2, NotificationChannel notificationChannel) {
            this.mINotificationManager = iNotificationManager;
            this.mAppPkg = str;
            this.mAppUid = i;
            this.mChannelToUpdate = notificationChannel;
            this.mAction = i2;
        }

        public final void run() {
            try {
                int i = this.mAction;
                if (i == 0) {
                    NotificationChannel notificationChannel = this.mChannelToUpdate;
                    notificationChannel.setImportance(Math.max(notificationChannel.getOriginalImportance(), 3));
                    if (this.mChannelToUpdate.isImportantConversation()) {
                        this.mChannelToUpdate.setImportantConversation(false);
                        this.mChannelToUpdate.setAllowBubbles(false);
                    }
                } else if (i == 2) {
                    this.mChannelToUpdate.setImportantConversation(true);
                    if (this.mChannelToUpdate.isImportantConversation()) {
                        this.mChannelToUpdate.setAllowBubbles(true);
                        if (NotificationConversationInfo.this.mAppBubble == 0) {
                            this.mINotificationManager.setBubblesAllowed(this.mAppPkg, this.mAppUid, 2);
                        }
                        if (NotificationConversationInfo.this.mBubblesManagerOptional.isPresent()) {
                            NotificationConversationInfo.this.post(new DozeUi$$ExternalSyntheticLambda1(this, 7));
                        }
                    }
                    NotificationChannel notificationChannel2 = this.mChannelToUpdate;
                    notificationChannel2.setImportance(Math.max(notificationChannel2.getOriginalImportance(), 3));
                } else if (i == 4) {
                    if (this.mChannelToUpdate.getImportance() == -1000 || this.mChannelToUpdate.getImportance() >= 3) {
                        this.mChannelToUpdate.setImportance(2);
                    }
                    if (this.mChannelToUpdate.isImportantConversation()) {
                        this.mChannelToUpdate.setImportantConversation(false);
                        this.mChannelToUpdate.setAllowBubbles(false);
                    }
                }
                this.mINotificationManager.updateNotificationChannelForPackage(this.mAppPkg, this.mAppUid, this.mChannelToUpdate);
            } catch (RemoteException e) {
                Log.e("ConversationGuts", "Unable to update notification channel", e);
            }
        }
    }

    public final View getContentView() {
        return this;
    }

    @VisibleForTesting
    public boolean isAnimating() {
        return false;
    }

    public final boolean needsFalsingProtection() {
        return true;
    }

    public final void onFinishedClosing() {
        this.mSelectedAction = -1;
    }

    public final void updateToggleActions(int i, boolean z) {
        boolean z2;
        int i2;
        boolean z3 = true;
        if (z) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.setOrdering(0);
            TransitionSet addTransition = transitionSet.addTransition(new Fade(2)).addTransition(new ChangeBounds());
            Transition duration = new Fade(1).setStartDelay(150).setDuration(200);
            PathInterpolator pathInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            addTransition.addTransition(duration.setInterpolator(pathInterpolator));
            transitionSet.setDuration(350);
            transitionSet.setInterpolator(pathInterpolator);
            TransitionManager.beginDelayedTransition(this, transitionSet);
        }
        View findViewById = findViewById(C1777R.C1779id.priority);
        View findViewById2 = findViewById(C1777R.C1779id.default_behavior);
        View findViewById3 = findViewById(C1777R.C1779id.silence);
        if (i == 0) {
            this.mDefaultDescriptionView.setVisibility(0);
            this.mSilentDescriptionView.setVisibility(8);
            this.mPriorityDescriptionView.setVisibility(8);
            post(new NotificationConversationInfo$$ExternalSyntheticLambda4(findViewById, findViewById2, findViewById3));
        } else if (i == 2) {
            this.mPriorityDescriptionView.setVisibility(0);
            this.mDefaultDescriptionView.setVisibility(8);
            this.mSilentDescriptionView.setVisibility(8);
            post(new NotificationConversationInfo$$ExternalSyntheticLambda2(findViewById, findViewById2, findViewById3));
        } else if (i == 4) {
            this.mSilentDescriptionView.setVisibility(0);
            this.mDefaultDescriptionView.setVisibility(8);
            this.mPriorityDescriptionView.setVisibility(8);
            post(new NotificationConversationInfo$$ExternalSyntheticLambda3(findViewById, findViewById2, findViewById3));
        } else {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unrecognized behavior: ");
            m.append(this.mSelectedAction);
            throw new IllegalArgumentException(m.toString());
        }
        if (getPriority() != i) {
            z2 = true;
        } else {
            z2 = false;
        }
        TextView textView = (TextView) findViewById(C1777R.C1779id.done);
        if (z2) {
            i2 = C1777R.string.inline_ok_button;
        } else {
            i2 = C1777R.string.inline_done_button;
        }
        textView.setText(i2);
        if (i != 2) {
            z3 = false;
        }
        bindIcon(z3);
    }

    public final boolean willBeRemoved() {
        return false;
    }

    public final void bindIcon(boolean z) {
        Drawable drawable;
        ConversationIconFactory conversationIconFactory = this.mIconFactory;
        ShortcutInfo shortcutInfo = this.mShortcutInfo;
        Objects.requireNonNull(conversationIconFactory);
        Drawable shortcutIconDrawable = conversationIconFactory.mLauncherApps.getShortcutIconDrawable(shortcutInfo, conversationIconFactory.mFillResIconDpi);
        int i = 0;
        if (shortcutIconDrawable == null) {
            shortcutIconDrawable = this.mContext.getDrawable(C1777R.C1778drawable.ic_person).mutate();
            TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16843829});
            int color = obtainStyledAttributes.getColor(0, 0);
            obtainStyledAttributes.recycle();
            shortcutIconDrawable.setTint(color);
        }
        ((ImageView) findViewById(C1777R.C1779id.conversation_icon)).setImageDrawable(shortcutIconDrawable);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.conversation_icon_badge_icon);
        ConversationIconFactory conversationIconFactory2 = this.mIconFactory;
        String str = this.mPackageName;
        int userId = UserHandle.getUserId(this.mSbn.getUid());
        Objects.requireNonNull(conversationIconFactory2);
        try {
            drawable = Utils.getBadgedIcon(conversationIconFactory2.mContext, conversationIconFactory2.mPackageManager.getApplicationInfoAsUser(str, 128, userId));
        } catch (PackageManager.NameNotFoundException unused) {
            drawable = conversationIconFactory2.mPackageManager.getDefaultActivityIcon();
        }
        imageView.setImageDrawable(drawable);
        View findViewById = findViewById(C1777R.C1779id.conversation_icon_badge_ring);
        if (!z) {
            i = 8;
        }
        findViewById.setVisibility(i);
    }

    public final int getPriority() {
        if (this.mNotificationChannel.getImportance() <= 2 && this.mNotificationChannel.getImportance() > -1000) {
            return 4;
        }
        if (this.mNotificationChannel.isImportantConversation()) {
            return 2;
        }
        return 0;
    }

    public final boolean handleCloseControls(boolean z, boolean z2) {
        int i;
        if (!z || (i = this.mSelectedAction) <= -1) {
            return false;
        }
        this.mBgHandler.post(new UpdateChannelRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, i, this.mNotificationChannel));
        NotificationEntry notificationEntry = this.mEntry;
        Objects.requireNonNull(notificationEntry);
        notificationEntry.mIsMarkedForUserTriggeredMovement = true;
        this.mMainHandler.postDelayed(new TaskView$$ExternalSyntheticLambda3(this, 4), 360);
        return false;
    }

    public final boolean post(Runnable runnable) {
        if (!this.mSkipPost) {
            return super.post(runnable);
        }
        runnable.run();
        return true;
    }

    @VisibleForTesting
    public void setSelectedAction(int i) {
        if (this.mSelectedAction != i) {
            this.mSelectedAction = i;
        }
    }

    public final boolean willShowAsBubble() {
        if (this.mBubbleMetadata == null || !BubblesManager.areBubblesEnabled(this.mContext, this.mSbn.getUser())) {
            return false;
        }
        return true;
    }

    public NotificationConversationInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDefaultDescriptionView = (TextView) findViewById(C1777R.C1779id.default_summary);
        this.mSilentDescriptionView = (TextView) findViewById(C1777R.C1779id.silence_summary);
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

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mActualHeight = getHeight();
    }

    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    public final int getActualHeight() {
        return this.mActualHeight;
    }

    public final boolean shouldBeSaved() {
        return this.mPressedApply;
    }
}
