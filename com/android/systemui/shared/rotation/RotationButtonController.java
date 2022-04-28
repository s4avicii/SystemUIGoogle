package com.android.systemui.shared.rotation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.IRotationWatcher;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.LinearInterpolator;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.view.RotationPolicy;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda4;
import com.android.p012wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda1;
import com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda3;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda4;
import com.android.systemui.shared.recents.utilities.ViewRippler;
import com.android.systemui.shared.rotation.RotationButton;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda11;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestController$$ExternalSyntheticLambda1;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class RotationButtonController {
    public static final LinearInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public final AccessibilityManager mAccessibilityManager;
    @SuppressLint({"InlinedApi"})
    public int mBehavior = 1;
    public final VolumeDialogImpl$$ExternalSyntheticLambda11 mCancelPendingRotationProposal = new VolumeDialogImpl$$ExternalSyntheticLambda11(this, 2);
    public final Context mContext;
    public final int mDarkIconColor;
    public boolean mHomeRotationEnabled;
    public boolean mHoveringRotationSuggestion;
    public final int mIconCcwStart0ResId;
    public final int mIconCcwStart90ResId;
    public final int mIconCwStart0ResId;
    public final int mIconCwStart90ResId;
    public int mIconResId;
    public boolean mIsNavigationBarShowing;
    public boolean mIsRecentsAnimationRunning;
    public int mLastRotationSuggestion;
    public final int mLightIconColor;
    public boolean mListenersRegistered = false;
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public boolean mPendingRotationSuggestion;
    public final SuggestController$$ExternalSyntheticLambda1 mRemoveRotationProposal = new SuggestController$$ExternalSyntheticLambda1(this, 4);
    public Consumer<Integer> mRotWatcherListener;
    public ObjectAnimator mRotateHideAnimator;
    public RotationButton mRotationButton;
    public final C11201 mRotationWatcher = new IRotationWatcher.Stub() {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final void onRotationChanged(int i) {
            RotationButtonController.this.mMainThreadHandler.postAtFrontOfQueue(new StartingWindowController$$ExternalSyntheticLambda1(this, i, 1));
        }
    };
    public boolean mSkipOverrideUserLockPrefsOnce;
    public final TaskStackListenerImpl mTaskStackListener;
    public final UiEventLoggerImpl mUiEventLogger = new UiEventLoggerImpl();
    public final ViewRippler mViewRippler = new ViewRippler();
    public final Supplier<Integer> mWindowRotationProvider;

    public class TaskStackListenerImpl extends TaskStackChangeListener {
        public TaskStackListenerImpl() {
        }

        public final void onActivityRequestedOrientationChanged(int i) {
            Optional.ofNullable(ActivityManagerWrapper.sInstance).map(BubbleData$$ExternalSyntheticLambda4.INSTANCE$3).ifPresent(new CompatUIController$$ExternalSyntheticLambda0(this, i, 1));
        }

        public final void onTaskMovedToFront() {
            RotationButtonController rotationButtonController = RotationButtonController.this;
            Objects.requireNonNull(rotationButtonController);
            rotationButtonController.setRotateSuggestionButtonState(false, false);
        }

        public final void onTaskRemoved() {
            RotationButtonController rotationButtonController = RotationButtonController.this;
            Objects.requireNonNull(rotationButtonController);
            rotationButtonController.setRotateSuggestionButtonState(false, false);
        }

        public final void onTaskStackChanged() {
            RotationButtonController rotationButtonController = RotationButtonController.this;
            Objects.requireNonNull(rotationButtonController);
            rotationButtonController.setRotateSuggestionButtonState(false, false);
        }
    }

    public final void showAndLogRotationSuggestion() {
        setRotateSuggestionButtonState(true, false);
        rescheduleRotationTimeout(false);
        this.mUiEventLogger.log(RotationButtonEvent.ROTATION_SUGGESTION_SHOWN);
    }

    public enum RotationButtonEvent implements UiEventLogger.UiEventEnum {
        ROTATION_SUGGESTION_SHOWN(206),
        ROTATION_SUGGESTION_ACCEPTED(207);
        
        private final int mId;

        /* access modifiers changed from: public */
        RotationButtonEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final void rescheduleRotationTimeout(boolean z) {
        int i;
        ObjectAnimator objectAnimator;
        if (!z || (((objectAnimator = this.mRotateHideAnimator) == null || !objectAnimator.isRunning()) && this.mRotationButton.isVisible())) {
            this.mMainThreadHandler.removeCallbacks(this.mRemoveRotationProposal);
            Handler handler = this.mMainThreadHandler;
            SuggestController$$ExternalSyntheticLambda1 suggestController$$ExternalSyntheticLambda1 = this.mRemoveRotationProposal;
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            if (this.mHoveringRotationSuggestion) {
                i = 16000;
            } else {
                i = 5000;
            }
            handler.postDelayed(suggestController$$ExternalSyntheticLambda1, (long) accessibilityManager.getRecommendedTimeoutMillis(i, 4));
        }
    }

    public final void setRotateSuggestionButtonState(boolean z, boolean z2) {
        View currentView;
        Drawable imageDrawable;
        if ((z || this.mRotationButton.isVisible()) && (currentView = this.mRotationButton.getCurrentView()) != null && (imageDrawable = this.mRotationButton.getImageDrawable()) != null) {
            boolean z3 = false;
            this.mPendingRotationSuggestion = false;
            this.mMainThreadHandler.removeCallbacks(this.mCancelPendingRotationProposal);
            if (z) {
                ObjectAnimator objectAnimator = this.mRotateHideAnimator;
                if (objectAnimator != null && objectAnimator.isRunning()) {
                    this.mRotateHideAnimator.cancel();
                }
                this.mRotateHideAnimator = null;
                currentView.setAlpha(1.0f);
                if (imageDrawable instanceof AnimatedVectorDrawable) {
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageDrawable;
                    animatedVectorDrawable.reset();
                    animatedVectorDrawable.start();
                }
                if (Settings.Secure.getInt(this.mContext.getContentResolver(), "num_rotation_suggestions_accepted", 0) >= 3) {
                    z3 = true;
                }
                if (!z3) {
                    ViewRippler viewRippler = this.mViewRippler;
                    Objects.requireNonNull(viewRippler);
                    View view = viewRippler.mRoot;
                    if (view != null) {
                        view.removeCallbacks(viewRippler.mRipple);
                    }
                    viewRippler.mRoot = currentView;
                    currentView.postOnAnimationDelayed(viewRippler.mRipple, 50);
                    viewRippler.mRoot.postOnAnimationDelayed(viewRippler.mRipple, 2000);
                    viewRippler.mRoot.postOnAnimationDelayed(viewRippler.mRipple, 4000);
                    viewRippler.mRoot.postOnAnimationDelayed(viewRippler.mRipple, 6000);
                    viewRippler.mRoot.postOnAnimationDelayed(viewRippler.mRipple, 8000);
                }
                this.mRotationButton.show();
                return;
            }
            ViewRippler viewRippler2 = this.mViewRippler;
            Objects.requireNonNull(viewRippler2);
            View view2 = viewRippler2.mRoot;
            if (view2 != null) {
                view2.removeCallbacks(viewRippler2.mRipple);
            }
            if (z2) {
                ObjectAnimator objectAnimator2 = this.mRotateHideAnimator;
                if (objectAnimator2 != null && objectAnimator2.isRunning()) {
                    this.mRotateHideAnimator.pause();
                }
                this.mRotationButton.hide();
                return;
            }
            ObjectAnimator objectAnimator3 = this.mRotateHideAnimator;
            if (objectAnimator3 == null || !objectAnimator3.isRunning()) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(currentView, "alpha", new float[]{0.0f});
                ofFloat.setDuration(100);
                ofFloat.setInterpolator(LINEAR_INTERPOLATOR);
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        RotationButtonController.this.mRotationButton.hide();
                    }
                });
                this.mRotateHideAnimator = ofFloat;
                ofFloat.start();
            }
        }
    }

    public final void setRotationButton(RotationButton rotationButton, RotationButton.RotationButtonUpdatesCallback rotationButtonUpdatesCallback) {
        this.mRotationButton = rotationButton;
        rotationButton.setRotationButtonController(this);
        this.mRotationButton.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda4(this, 2));
        this.mRotationButton.setOnHoverListener(new RotationButtonController$$ExternalSyntheticLambda0(this));
        this.mRotationButton.setUpdatesCallback(rotationButtonUpdatesCallback);
    }

    public static void $r8$lambda$zgkIWtDwDdf8jAM6lj_qLTw8at8(RotationButtonController rotationButtonController, View view) {
        Objects.requireNonNull(rotationButtonController);
        rotationButtonController.mUiEventLogger.log(RotationButtonEvent.ROTATION_SUGGESTION_ACCEPTED);
        ContentResolver contentResolver = rotationButtonController.mContext.getContentResolver();
        int i = Settings.Secure.getInt(contentResolver, "num_rotation_suggestions_accepted", 0);
        if (i < 3) {
            Settings.Secure.putInt(contentResolver, "num_rotation_suggestions_accepted", i + 1);
        }
        RotationPolicy.setRotationLockAtAngle(rotationButtonController.mContext, true, rotationButtonController.mLastRotationSuggestion);
        view.performHapticFeedback(1);
    }

    public RotationButtonController(Context context, int i, int i2, NavigationBarView$$ExternalSyntheticLambda3 navigationBarView$$ExternalSyntheticLambda3) {
        this.mContext = context;
        this.mLightIconColor = i;
        this.mDarkIconColor = i2;
        this.mIconCcwStart0ResId = C1777R.C1778drawable.ic_sysbar_rotate_button_ccw_start_0;
        this.mIconCcwStart90ResId = C1777R.C1778drawable.ic_sysbar_rotate_button_ccw_start_90;
        this.mIconCwStart0ResId = C1777R.C1778drawable.ic_sysbar_rotate_button_cw_start_0;
        this.mIconCwStart90ResId = C1777R.C1778drawable.ic_sysbar_rotate_button_cw_start_90;
        this.mIconResId = C1777R.C1778drawable.ic_sysbar_rotate_button_ccw_start_90;
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mTaskStackListener = new TaskStackListenerImpl();
        this.mWindowRotationProvider = navigationBarView$$ExternalSyntheticLambda3;
    }
}
