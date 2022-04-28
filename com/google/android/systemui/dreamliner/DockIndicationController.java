package com.google.android.systemui.dreamliner;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class DockIndicationController implements StatusBarStateController.StateListener, View.OnClickListener, View.OnAttachStateChangeListener, ConfigurationController.ConfigurationListener {
    @VisibleForTesting
    public static final String ACTION_ASSISTANT_POODLE = "com.google.android.systemui.dreamliner.ASSISTANT_POODLE";
    public static final long KEYGUARD_INDICATION_TIMEOUT_MILLIS;
    public static final long PROMO_SHOWING_TIME_MILLIS;
    public final AccessibilityManager mAccessibilityManager;
    public final Context mContext;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda6 mDisableLiveRegionRunnable = new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, 11);
    @VisibleForTesting
    public FrameLayout mDockPromo;
    @VisibleForTesting
    public ImageView mDockedTopIcon;
    public boolean mDocking;
    public boolean mDozing;
    public final Animation mHidePromoAnimation;
    public final ScrimView$$ExternalSyntheticLambda0 mHidePromoRunnable = new ScrimView$$ExternalSyntheticLambda0(this, 9);
    @VisibleForTesting
    public boolean mIconViewsValidated;
    public final KeyguardIndicationController mKeyguardIndicationController;
    public TextView mPromoText;
    public boolean mShowPromo;
    public final Animation mShowPromoAnimation;
    public int mShowPromoTimes;
    public final StatusBar mStatusBar;
    public int mStatusBarState;
    public boolean mTopIconShowing;
    public KeyguardIndicationTextView mTopIndicationView;

    public static class PhotoAnimationListener implements Animation.AnimationListener {
        public final void onAnimationRepeat(Animation animation) {
        }

        public final void onAnimationStart(Animation animation) {
        }
    }

    public final void onViewAttachedToWindow(View view) {
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        PROMO_SHOWING_TIME_MILLIS = timeUnit.toMillis(2);
        KEYGUARD_INDICATION_TIMEOUT_MILLIS = timeUnit.toMillis(15);
    }

    @VisibleForTesting
    public void initializeIconViews() {
        StatusBar statusBar = this.mStatusBar;
        Objects.requireNonNull(statusBar);
        NotificationShadeWindowView notificationShadeWindowView = statusBar.mNotificationShadeWindowView;
        ImageView imageView = (ImageView) notificationShadeWindowView.findViewById(C1777R.C1779id.docked_top_icon);
        this.mDockedTopIcon = imageView;
        imageView.setImageResource(C1777R.C1778drawable.ic_assistant_logo);
        this.mDockedTopIcon.setContentDescription(this.mContext.getString(C1777R.string.accessibility_assistant_poodle));
        this.mDockedTopIcon.setTooltipText(this.mContext.getString(C1777R.string.accessibility_assistant_poodle));
        this.mDockedTopIcon.setOnClickListener(this);
        this.mDockPromo = (FrameLayout) notificationShadeWindowView.findViewById(C1777R.C1779id.dock_promo);
        TextView textView = (TextView) notificationShadeWindowView.findViewById(C1777R.C1779id.photo_promo_text);
        this.mPromoText = textView;
        textView.setAutoSizeTextTypeUniformWithConfiguration(10, 16, 1, 2);
        notificationShadeWindowView.findViewById(C1777R.C1779id.ambient_indication).addOnAttachStateChangeListener(this);
        this.mTopIndicationView = (KeyguardIndicationTextView) notificationShadeWindowView.findViewById(C1777R.C1779id.keyguard_indication_text);
        this.mIconViewsValidated = true;
    }

    public final void onDozingChanged(boolean z) {
        this.mDozing = z;
        updateVisibility();
        updateLiveRegionIfNeeded();
        if (!this.mDozing) {
            this.mShowPromo = false;
        } else {
            showPromoInner();
        }
    }

    public final void onLocaleListChanged() {
        if (!this.mIconViewsValidated) {
            initializeIconViews();
        }
        this.mPromoText.setText(this.mContext.getResources().getString(C1777R.string.dock_promo_text));
    }

    public final void onStateChanged(int i) {
        this.mStatusBarState = i;
        updateVisibility();
    }

    public final void showPromoInner() {
        if (this.mDozing && this.mDocking && this.mShowPromo) {
            this.mKeyguardIndicationController.setVisible(false);
            this.mDockPromo.setVisibility(0);
            this.mDockPromo.startAnimation(this.mShowPromoAnimation);
            this.mShowPromoTimes++;
        }
    }

    public final void updateLiveRegionIfNeeded() {
        int accessibilityLiveRegion = this.mTopIndicationView.getAccessibilityLiveRegion();
        if (this.mDozing && this.mDocking) {
            this.mTopIndicationView.removeCallbacks(this.mDisableLiveRegionRunnable);
            KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
            KeyguardUpdateMonitor$$ExternalSyntheticLambda6 keyguardUpdateMonitor$$ExternalSyntheticLambda6 = this.mDisableLiveRegionRunnable;
            long j = KEYGUARD_INDICATION_TIMEOUT_MILLIS;
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            if (accessibilityManager != null) {
                j = (long) accessibilityManager.getRecommendedTimeoutMillis(Math.toIntExact(j), 2);
            }
            keyguardIndicationTextView.postDelayed(keyguardUpdateMonitor$$ExternalSyntheticLambda6, j);
        } else if (accessibilityLiveRegion != 1) {
            this.mTopIndicationView.setAccessibilityLiveRegion(1);
        }
    }

    public final void updateVisibility() {
        if (!this.mIconViewsValidated) {
            initializeIconViews();
        }
        boolean z = false;
        if (!this.mDozing || !this.mDocking) {
            this.mDockPromo.setVisibility(8);
            this.mDockedTopIcon.setVisibility(8);
            int i = this.mStatusBarState;
            if (i == 1 || i == 2) {
                z = true;
            }
            this.mKeyguardIndicationController.setVisible(z);
        } else if (!this.mTopIconShowing) {
            this.mDockedTopIcon.setVisibility(8);
        } else {
            this.mDockedTopIcon.setVisibility(0);
        }
    }

    public DockIndicationController(Context context, KeyguardIndicationControllerGoogle keyguardIndicationControllerGoogle, SysuiStatusBarStateController sysuiStatusBarStateController, StatusBar statusBar) {
        this.mContext = context;
        this.mStatusBar = statusBar;
        this.mKeyguardIndicationController = keyguardIndicationControllerGoogle;
        sysuiStatusBarStateController.addCallback(this);
        Animation loadAnimation = AnimationUtils.loadAnimation(context, C1777R.anim.dock_promo_animation);
        this.mShowPromoAnimation = loadAnimation;
        loadAnimation.setAnimationListener(new PhotoAnimationListener() {
            public final void onAnimationEnd(Animation animation) {
                DockIndicationController dockIndicationController = DockIndicationController.this;
                FrameLayout frameLayout = dockIndicationController.mDockPromo;
                ScrimView$$ExternalSyntheticLambda0 scrimView$$ExternalSyntheticLambda0 = dockIndicationController.mHidePromoRunnable;
                long j = DockIndicationController.PROMO_SHOWING_TIME_MILLIS;
                AccessibilityManager accessibilityManager = dockIndicationController.mAccessibilityManager;
                if (accessibilityManager != null) {
                    j = (long) accessibilityManager.getRecommendedTimeoutMillis(Math.toIntExact(j), 2);
                }
                frameLayout.postDelayed(scrimView$$ExternalSyntheticLambda0, j);
            }
        });
        Animation loadAnimation2 = AnimationUtils.loadAnimation(context, C1777R.anim.dock_promo_fade_out);
        this.mHidePromoAnimation = loadAnimation2;
        loadAnimation2.setAnimationListener(new PhotoAnimationListener() {
            public final void onAnimationEnd(Animation animation) {
                DockIndicationController dockIndicationController = DockIndicationController.this;
                if (dockIndicationController.mShowPromoTimes < 3) {
                    dockIndicationController.showPromoInner();
                    return;
                }
                dockIndicationController.mKeyguardIndicationController.setVisible(true);
                DockIndicationController.this.mDockPromo.setVisibility(8);
            }
        });
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
    }

    public final void onClick(View view) {
        if (view.getId() == C1777R.C1779id.docked_top_icon) {
            Intent intent = new Intent(ACTION_ASSISTANT_POODLE);
            intent.addFlags(1073741824);
            try {
                this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
            } catch (SecurityException e) {
                Log.w("DLIndicator", "Cannot send event for intent= " + intent, e);
            }
        }
    }

    public final void onViewDetachedFromWindow(View view) {
        view.removeOnAttachStateChangeListener(this);
        this.mIconViewsValidated = false;
        this.mDockedTopIcon = null;
    }
}
