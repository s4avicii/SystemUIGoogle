package com.android.p012wm.shell.pip.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.keyguard.KeyguardStatusView$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.pip.PipUtils;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/* renamed from: com.android.wm.shell.pip.phone.PipMenuView */
public final class PipMenuView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilityManager mAccessibilityManager;
    public final ArrayList mActions = new ArrayList();
    public LinearLayout mActionsGroup;
    public boolean mAllowMenuTimeout = true;
    public boolean mAllowTouches = true;
    public Drawable mBackgroundDrawable;
    public int mBetweenActionPaddingLand;
    public final PhonePipMenuController mController;
    public boolean mDidLastShowMenuResize;
    public View mDismissButton;
    public int mDismissFadeOutDurationMs;
    public View mEnterSplitButton;
    public boolean mFocusedTaskAllowSplitScreen;
    public final KeyguardStatusView$$ExternalSyntheticLambda0 mHideMenuRunnable = new KeyguardStatusView$$ExternalSyntheticLambda0(this, 9);
    public ShellExecutor mMainExecutor;
    public Handler mMainHandler;
    public C19061 mMenuBgUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            PipMenuView.this.mBackgroundDrawable.setAlpha((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 0.3f * 255.0f));
        }
    };
    public View mMenuContainer;
    public AnimatorSet mMenuContainerAnimator;
    public int mMenuState;
    public PipMenuIconsAlgorithm mPipMenuIconsAlgorithm;
    public final PipUiEventLogger mPipUiEventLogger;
    public View mSettingsButton;
    public final Optional<SplitScreenController> mSplitScreenControllerOptional;
    public View mTopEndContainer;
    public View mViewRoot;

    public PipMenuView(Context context, PhonePipMenuController phonePipMenuController, ShellExecutor shellExecutor, Handler handler, Optional<SplitScreenController> optional, PipUiEventLogger pipUiEventLogger) {
        super(context, (AttributeSet) null, 0);
        this.mContext = context;
        this.mController = phonePipMenuController;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mSplitScreenControllerOptional = optional;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        View.inflate(context, C1777R.layout.pip_menu, this);
        Drawable drawable = this.mContext.getDrawable(C1777R.C1778drawable.pip_menu_background);
        this.mBackgroundDrawable = drawable;
        drawable.setAlpha(0);
        View findViewById = findViewById(C1777R.C1779id.background);
        this.mViewRoot = findViewById;
        findViewById.setBackground(this.mBackgroundDrawable);
        View findViewById2 = findViewById(C1777R.C1779id.menu_container);
        this.mMenuContainer = findViewById2;
        findViewById2.setAlpha(0.0f);
        this.mTopEndContainer = findViewById(C1777R.C1779id.top_end_container);
        View findViewById3 = findViewById(C1777R.C1779id.settings);
        this.mSettingsButton = findViewById3;
        findViewById3.setAlpha(0.0f);
        this.mSettingsButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda2(this, 0));
        View findViewById4 = findViewById(C1777R.C1779id.dismiss);
        this.mDismissButton = findViewById4;
        findViewById4.setAlpha(0.0f);
        this.mDismissButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda3(this, 0));
        findViewById(C1777R.C1779id.expand_button).setOnClickListener(new PipMenuView$$ExternalSyntheticLambda4(this, 0));
        View findViewById5 = findViewById(C1777R.C1779id.enter_split);
        this.mEnterSplitButton = findViewById5;
        findViewById5.setAlpha(0.0f);
        this.mEnterSplitButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda5(this, 0));
        findViewById(C1777R.C1779id.resize_handle).setAlpha(0.0f);
        this.mActionsGroup = (LinearLayout) findViewById(C1777R.C1779id.actions_group);
        this.mBetweenActionPaddingLand = getResources().getDimensionPixelSize(C1777R.dimen.pip_between_action_padding_land);
        PipMenuIconsAlgorithm pipMenuIconsAlgorithm = new PipMenuIconsAlgorithm();
        this.mPipMenuIconsAlgorithm = pipMenuIconsAlgorithm;
        ViewGroup viewGroup = (ViewGroup) this.mViewRoot;
        ViewGroup viewGroup2 = (ViewGroup) this.mTopEndContainer;
        View findViewById6 = findViewById(C1777R.C1779id.resize_handle);
        View view = this.mEnterSplitButton;
        View view2 = this.mSettingsButton;
        View view3 = this.mDismissButton;
        pipMenuIconsAlgorithm.mDragHandle = findViewById6;
        pipMenuIconsAlgorithm.mEnterSplitButton = view;
        pipMenuIconsAlgorithm.mSettingsButton = view2;
        pipMenuIconsAlgorithm.mDismissButton = view3;
        this.mDismissFadeOutDurationMs = context.getResources().getInteger(C1777R.integer.config_pipExitAnimationDuration);
        setAccessibilityDelegate(new View.AccessibilityDelegate() {
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i == 16) {
                    PipMenuView pipMenuView = PipMenuView.this;
                    if (pipMenuView.mMenuState != 1) {
                        PhonePipMenuController phonePipMenuController = pipMenuView.mController;
                        Objects.requireNonNull(phonePipMenuController);
                        phonePipMenuController.mListeners.forEach(QSTileHost$$ExternalSyntheticLambda4.INSTANCE$3);
                    }
                }
                return super.performAccessibilityAction(view, i, bundle);
            }

            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, PipMenuView.this.getResources().getString(C1777R.string.pip_menu_title)));
            }
        });
    }

    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mAllowMenuTimeout) {
            repostDelayedHide(2000);
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.mAllowTouches) {
            return false;
        }
        if (this.mAllowMenuTimeout) {
            repostDelayedHide(2000);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void hideMenu(final Runnable runnable, final boolean z, boolean z2, int i) {
        long j;
        if (this.mMenuState != 0) {
            this.mMainExecutor.removeCallbacks(this.mHideMenuRunnable);
            if (z) {
                notifyMenuStateChangeStart(0, z2, (PipTaskOrganizer$$ExternalSyntheticLambda3) null);
            }
            this.mMenuContainerAnimator = new AnimatorSet();
            View view = this.mMenuContainer;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, new float[]{view.getAlpha(), 0.0f});
            ofFloat.addUpdateListener(this.mMenuBgUpdateListener);
            View view2 = this.mSettingsButton;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view2, View.ALPHA, new float[]{view2.getAlpha(), 0.0f});
            View view3 = this.mDismissButton;
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view3, View.ALPHA, new float[]{view3.getAlpha(), 0.0f});
            View view4 = this.mEnterSplitButton;
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view4, View.ALPHA, new float[]{view4.getAlpha(), 0.0f});
            this.mMenuContainerAnimator.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3, ofFloat4});
            this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_OUT);
            AnimatorSet animatorSet = this.mMenuContainerAnimator;
            if (i == 0) {
                j = 0;
            } else if (i == 1) {
                j = 125;
            } else if (i == 2) {
                j = (long) this.mDismissFadeOutDurationMs;
            } else {
                throw new IllegalStateException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Invalid animation type ", i));
            }
            animatorSet.setDuration(j);
            this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    PipMenuView.this.setVisibility(8);
                    if (z) {
                        PipMenuView.m295$$Nest$mnotifyMenuStateChangeFinish(PipMenuView.this, 0);
                    }
                    Runnable runnable = runnable;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
            this.mMenuContainerAnimator.start();
        }
    }

    public final void notifyMenuStateChangeStart(int i, boolean z, PipTaskOrganizer$$ExternalSyntheticLambda3 pipTaskOrganizer$$ExternalSyntheticLambda3) {
        PhonePipMenuController phonePipMenuController = this.mController;
        Objects.requireNonNull(phonePipMenuController);
        if (i != phonePipMenuController.mMenuState) {
            phonePipMenuController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda0(i, z, pipTaskOrganizer$$ExternalSyntheticLambda3));
            boolean z2 = true;
            if (i == 1) {
                PipMediaController pipMediaController = phonePipMenuController.mMediaController;
                PhonePipMenuController.C18931 r0 = phonePipMenuController.mMediaActionListener;
                Objects.requireNonNull(pipMediaController);
                if (!pipMediaController.mActionListeners.contains(r0)) {
                    pipMediaController.mActionListeners.add(r0);
                    r0.onMediaActionsChanged(pipMediaController.getMediaActions());
                }
            } else {
                PipMediaController pipMediaController2 = phonePipMenuController.mMediaController;
                PhonePipMenuController.C18931 r02 = phonePipMenuController.mMediaActionListener;
                Objects.requireNonNull(pipMediaController2);
                r02.onMediaActionsChanged(Collections.emptyList());
                pipMediaController2.mActionListeners.remove(r02);
            }
            try {
                IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
                IBinder focusGrantToken = phonePipMenuController.mSystemWindows.getFocusGrantToken(phonePipMenuController.mPipMenuView);
                if (i == 0) {
                    z2 = false;
                }
                windowSession.grantEmbeddedWindowFocus((IWindow) null, focusGrantToken, z2);
            } catch (RemoteException e) {
                Log.e("PhonePipMenuController", "Unable to update focus as menu appears/disappears", e);
            }
        }
    }

    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 111) {
            return super.onKeyUp(i, keyEvent);
        }
        hideMenu((Runnable) null, true, this.mDidLastShowMenuResize, 1);
        return true;
    }

    public final void repostDelayedHide(int i) {
        int recommendedTimeoutMillis = this.mAccessibilityManager.getRecommendedTimeoutMillis(i, 5);
        this.mMainExecutor.removeCallbacks(this.mHideMenuRunnable);
        this.mMainExecutor.executeDelayed(this.mHideMenuRunnable, (long) recommendedTimeoutMillis);
    }

    public final void showMenu(int i, Rect rect, boolean z, boolean z2, boolean z3) {
        boolean z4;
        final int i2 = i;
        final boolean z5 = z;
        boolean z6 = z2;
        this.mAllowMenuTimeout = z5;
        this.mDidLastShowMenuResize = z6;
        boolean z7 = this.mContext.getResources().getBoolean(C1777R.bool.config_pipEnableEnterSplitButton);
        int i3 = this.mMenuState;
        if (i3 != i2) {
            if (!z6 || !(i3 == 1 || i2 == 1)) {
                z4 = false;
            } else {
                z4 = true;
            }
            this.mAllowTouches = !z4;
            this.mMainExecutor.removeCallbacks(this.mHideMenuRunnable);
            AnimatorSet animatorSet = this.mMenuContainerAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            this.mMenuContainerAnimator = new AnimatorSet();
            View view = this.mMenuContainer;
            float f = 1.0f;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, new float[]{view.getAlpha(), 1.0f});
            ofFloat.addUpdateListener(this.mMenuBgUpdateListener);
            View view2 = this.mSettingsButton;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view2, View.ALPHA, new float[]{view2.getAlpha(), 1.0f});
            View view3 = this.mDismissButton;
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view3, View.ALPHA, new float[]{view3.getAlpha(), 1.0f});
            View view4 = this.mEnterSplitButton;
            Property property = View.ALPHA;
            float[] fArr = new float[2];
            fArr[0] = view4.getAlpha();
            if (!z7 || !this.mFocusedTaskAllowSplitScreen) {
                f = 0.0f;
            }
            fArr[1] = f;
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view4, property, fArr);
            if (i2 == 1) {
                this.mMenuContainerAnimator.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3, ofFloat4});
            } else {
                this.mMenuContainerAnimator.playTogether(new Animator[]{ofFloat4});
            }
            this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_IN);
            this.mMenuContainerAnimator.setDuration(125);
            this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationCancel(Animator animator) {
                    PipMenuView.this.mAllowTouches = true;
                }

                public final void onAnimationEnd(Animator animator) {
                    PipMenuView pipMenuView = PipMenuView.this;
                    pipMenuView.mAllowTouches = true;
                    PipMenuView.m295$$Nest$mnotifyMenuStateChangeFinish(pipMenuView, i2);
                    if (z5) {
                        PipMenuView.this.repostDelayedHide(3500);
                    }
                }
            });
            if (z3) {
                notifyMenuStateChangeStart(i2, z6, new PipTaskOrganizer$$ExternalSyntheticLambda3(this, 5));
            } else {
                notifyMenuStateChangeStart(i2, z6, (PipTaskOrganizer$$ExternalSyntheticLambda3) null);
                setVisibility(0);
                this.mMenuContainerAnimator.start();
            }
            updateActionViews(i, rect);
        } else if (z5) {
            repostDelayedHide(2000);
        }
    }

    public static void $r8$lambda$ZIdnozLD4vi0K38Zv_I0w2iOV1U(PipMenuView pipMenuView, View view) {
        Objects.requireNonNull(pipMenuView);
        if (view.getAlpha() != 0.0f) {
            Pair<ComponentName, Integer> topPipActivity = PipUtils.getTopPipActivity(pipMenuView.mContext);
            if (topPipActivity.first != null) {
                Intent intent = new Intent("android.settings.PICTURE_IN_PICTURE_SETTINGS", Uri.fromParts("package", ((ComponentName) topPipActivity.first).getPackageName(), (String) null));
                intent.setFlags(268468224);
                pipMenuView.mContext.startActivityAsUser(intent, UserHandle.of(((Integer) topPipActivity.second).intValue()));
                pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_SHOW_SETTINGS);
            }
        }
    }

    /* renamed from: -$$Nest$mnotifyMenuStateChangeFinish  reason: not valid java name */
    public static void m295$$Nest$mnotifyMenuStateChangeFinish(PipMenuView pipMenuView, int i) {
        Objects.requireNonNull(pipMenuView);
        pipMenuView.mMenuState = i;
        PhonePipMenuController phonePipMenuController = pipMenuView.mController;
        Objects.requireNonNull(phonePipMenuController);
        if (i != phonePipMenuController.mMenuState) {
            phonePipMenuController.mListeners.forEach(new ShellCommandHandlerImpl$$ExternalSyntheticLambda0(i, 1));
        }
        phonePipMenuController.mMenuState = i;
        if (i != 0) {
            SystemWindows systemWindows = phonePipMenuController.mSystemWindows;
            PipMenuView pipMenuView2 = phonePipMenuController.mPipMenuView;
            Objects.requireNonNull(systemWindows);
            SystemWindows.PerDisplay perDisplay = systemWindows.mPerDisplay.get(0);
            if (perDisplay != null) {
                perDisplay.setShellRootAccessibilityWindow(1, pipMenuView2);
                return;
            }
            return;
        }
        SystemWindows systemWindows2 = phonePipMenuController.mSystemWindows;
        Objects.requireNonNull(systemWindows2);
        SystemWindows.PerDisplay perDisplay2 = systemWindows2.mPerDisplay.get(0);
        if (perDisplay2 != null) {
            perDisplay2.setShellRootAccessibilityWindow(1, (FrameLayout) null);
        }
    }

    public final void updateActionViews(int i, Rect rect) {
        int i2;
        float f;
        int i3;
        int i4;
        ViewGroup viewGroup = (ViewGroup) findViewById(C1777R.C1779id.expand_container);
        ViewGroup viewGroup2 = (ViewGroup) findViewById(C1777R.C1779id.actions_container);
        viewGroup2.setOnTouchListener(PipMenuView$$ExternalSyntheticLambda6.INSTANCE);
        boolean z = true;
        if (i == 1) {
            i2 = 0;
        } else {
            i2 = 4;
        }
        viewGroup.setVisibility(i2);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        if (this.mActions.isEmpty() || i == 0) {
            viewGroup2.setVisibility(4);
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
        } else {
            viewGroup2.setVisibility(0);
            if (this.mActionsGroup != null) {
                LayoutInflater from = LayoutInflater.from(this.mContext);
                while (this.mActionsGroup.getChildCount() < this.mActions.size()) {
                    this.mActionsGroup.addView((PipMenuActionView) from.inflate(C1777R.layout.pip_menu_action, this.mActionsGroup, false));
                }
                for (int i5 = 0; i5 < this.mActionsGroup.getChildCount(); i5++) {
                    View childAt = this.mActionsGroup.getChildAt(i5);
                    if (i5 < this.mActions.size()) {
                        i4 = 0;
                    } else {
                        i4 = 8;
                    }
                    childAt.setVisibility(i4);
                }
                if (rect.width() <= rect.height()) {
                    z = false;
                }
                for (int i6 = 0; i6 < this.mActions.size(); i6++) {
                    RemoteAction remoteAction = (RemoteAction) this.mActions.get(i6);
                    PipMenuActionView pipMenuActionView = (PipMenuActionView) this.mActionsGroup.getChildAt(i6);
                    remoteAction.getIcon().loadDrawableAsync(this.mContext, new PipMenuView$$ExternalSyntheticLambda0(pipMenuActionView), this.mMainHandler);
                    pipMenuActionView.setContentDescription(remoteAction.getContentDescription());
                    if (remoteAction.isEnabled()) {
                        pipMenuActionView.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda1(remoteAction, 0));
                    }
                    pipMenuActionView.setEnabled(remoteAction.isEnabled());
                    if (remoteAction.isEnabled()) {
                        f = 1.0f;
                    } else {
                        f = 0.54f;
                    }
                    pipMenuActionView.setAlpha(f);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) pipMenuActionView.getLayoutParams();
                    if (!z || i6 <= 0) {
                        i3 = 0;
                    } else {
                        i3 = this.mBetweenActionPaddingLand;
                    }
                    layoutParams2.leftMargin = i3;
                }
            }
            layoutParams.topMargin = getResources().getDimensionPixelSize(C1777R.dimen.pip_action_padding);
            layoutParams.bottomMargin = getResources().getDimensionPixelSize(C1777R.dimen.pip_expand_container_edge_margin);
        }
        viewGroup.requestLayout();
    }
}
