package com.google.android.systemui.gamedashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.animation.PhysicsAnimatorKt;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.google.android.systemui.gamedashboard.RevealButton;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

public class ShortcutBarView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener, ConfigurationController.ConfigurationListener, RecordingController.RecordingStateChangeCallback {
    public static final int SHORTCUT_BAR_BACKGROUND_COLOR = Color.parseColor("#99000000");
    public ShortcutBarContainer mBar;
    public final int mBarMarginEnd = getResources().getDimensionPixelSize(C1777R.dimen.game_dashboard_shortcut_bar_margin_end);
    public ConfigurationController mConfigurationController;
    public ShortcutBarButton mEntryPointButton;
    public Consumer<Rect> mExcludeBackRegionCallback;
    public TextView mFpsView;
    public Insets mInsets;
    public boolean mIsAttached;
    public boolean mIsDockingAnimationRunning = false;
    public boolean mIsDragging = false;
    public boolean mIsEntryPointVisible;
    public boolean mIsFpsVisible;
    public boolean mIsRecordVisible;
    public boolean mIsScreenshotVisible;
    public final C22893 mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            float f;
            float f2;
            int i9;
            int i10;
            float f3;
            ShortcutBarView shortcutBarView = ShortcutBarView.this;
            int i11 = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
            Objects.requireNonNull(shortcutBarView);
            if (shortcutBarView.mRevealButton.getVisibility() == 0) {
                f = shortcutBarView.mRevealButton.getTranslationX();
            } else {
                f = shortcutBarView.mBar.getTranslationX();
            }
            ShortcutBarView shortcutBarView2 = ShortcutBarView.this;
            Objects.requireNonNull(shortcutBarView2);
            if (shortcutBarView2.mRevealButton.getVisibility() == 0) {
                f2 = shortcutBarView2.mRevealButton.getTranslationY();
            } else {
                f2 = shortcutBarView2.mBar.getTranslationY();
            }
            float f4 = (float) (i3 - i);
            float f5 = (float) (i7 - i5);
            float f6 = (float) (i4 - i2);
            float f7 = (float) (i8 - i6);
            ShortcutBarView shortcutBarView3 = ShortcutBarView.this;
            Objects.requireNonNull(shortcutBarView3);
            if (shortcutBarView3.mRevealButton.getVisibility() == 0) {
                i9 = shortcutBarView3.mRevealButton.getWidth();
            } else {
                i9 = shortcutBarView3.mBar.getWidth();
            }
            ShortcutBarView shortcutBarView4 = ShortcutBarView.this;
            Objects.requireNonNull(shortcutBarView4);
            if (shortcutBarView4.mRevealButton.getVisibility() == 0) {
                i10 = shortcutBarView4.mRevealButton.getHeight();
            } else {
                i10 = shortcutBarView4.mBar.getHeight();
            }
            if (ShortcutBarView.this.mRevealButton.getVisibility() == 0) {
                RevealButton revealButton = ShortcutBarView.this.mRevealButton;
                Objects.requireNonNull(revealButton);
                if (revealButton.mRightSide) {
                    f3 = f4 - ((float) ShortcutBarView.this.mRevealButtonIconWidth);
                } else {
                    f3 = 0.0f;
                }
            } else {
                float f8 = (float) i9;
                f3 = ((f4 - f8) * f) / (f5 - f8);
            }
            float f9 = (float) i10;
            float f10 = ((f6 - f9) * f2) / (f7 - f9);
            ShortcutBarView shortcutBarView5 = ShortcutBarView.this;
            Objects.requireNonNull(shortcutBarView5);
            if (shortcutBarView5.mRevealButton.getVisibility() == 0) {
                shortcutBarView5.mRevealButton.setTranslationX(f3);
                shortcutBarView5.mRevealButton.setTranslationY(f10);
            } else {
                shortcutBarView5.mBar.setTranslationX(f3);
                shortcutBarView5.mBar.setTranslationY(f10);
            }
            ShortcutBarView.this.snapBarBackIfNecessary();
            ShortcutBarView shortcutBarView6 = ShortcutBarView.this;
            shortcutBarView6.removeOnLayoutChangeListener(shortcutBarView6.mOnLayoutChangeListener);
        }
    };
    public final C22861 mOnTouchListener = new ShortcutBarTouchController() {
        public final PhysicsAnimator.SpringConfig mSpringConfig = new PhysicsAnimator.SpringConfig(700.0f, 1.0f);

        public final View getView() {
            return ShortcutBarView.this.mBar;
        }

        public final void onMove(float f, float f2) {
            ShortcutBarView.this.mBar.setTranslationX(f);
            ShortcutBarView.this.mBar.setTranslationY(f2);
            if (ShortcutBarView.this.mBar.getX() < 0.0f) {
                ShortcutBarView shortcutBarView = ShortcutBarView.this;
                if (shortcutBarView.mInsets.left == 0) {
                    ShortcutBarView.m309$$Nest$mdock(shortcutBarView, false);
                    return;
                }
            }
            if (ShortcutBarView.this.mBar.getX() + ((float) ShortcutBarView.this.mBar.getWidth()) > ((float) ShortcutBarView.this.getWidth())) {
                ShortcutBarView shortcutBarView2 = ShortcutBarView.this;
                if (shortcutBarView2.mInsets.right == 0) {
                    ShortcutBarView.m309$$Nest$mdock(shortcutBarView2, true);
                }
            }
        }

        public final void onUp(float f, float f2) {
            ShortcutBarView shortcutBarView = ShortcutBarView.this;
            if (!shortcutBarView.mIsDockingAnimationRunning) {
                ShortcutBarContainer shortcutBarContainer = shortcutBarView.mBar;
                Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
                PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(shortcutBarContainer);
                DynamicAnimation.C01371 r3 = DynamicAnimation.TRANSLATION_X;
                ShortcutBarView shortcutBarView2 = ShortcutBarView.this;
                int i = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
                Objects.requireNonNull(shortcutBarView2);
                float f3 = (float) (shortcutBarView2.mInsets.left + shortcutBarView2.mBarMarginEnd);
                ShortcutBarView shortcutBarView3 = ShortcutBarView.this;
                Objects.requireNonNull(shortcutBarView3);
                instance.flingThenSpring(r3, f, new PhysicsAnimator.FlingConfig(1.9f, f3, (float) (((shortcutBarView3.getWidth() - shortcutBarView3.mBarMarginEnd) - shortcutBarView3.mInsets.right) - shortcutBarView3.mBar.getWidth()), 0.0f), this.mSpringConfig, true);
                DynamicAnimation.C01422 r10 = DynamicAnimation.TRANSLATION_Y;
                ShortcutBarView shortcutBarView4 = ShortcutBarView.this;
                Objects.requireNonNull(shortcutBarView4);
                ShortcutBarView shortcutBarView5 = ShortcutBarView.this;
                Objects.requireNonNull(shortcutBarView5);
                float height = (float) ((shortcutBarView5.getHeight() - shortcutBarView5.mInsets.bottom) - shortcutBarView5.mBar.getHeight());
                WeakHashMap<Object, PhysicsAnimator<?>> weakHashMap = PhysicsAnimatorKt.animators;
                instance.flingConfigs.put(r10, new PhysicsAnimator.FlingConfig(1.9f, (float) shortcutBarView4.mInsets.top, height, f2));
                instance.endListeners.add(new PhysicsAnimator.EndListener<View>() {
                    public final void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z, boolean z2, float f, float f2) {
                        View view = (View) obj;
                        if (!z2 && floatPropertyCompat == DynamicAnimation.TRANSLATION_Y) {
                            ShortcutBarView shortcutBarView = ShortcutBarView.this;
                            int i = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
                            shortcutBarView.snapBarBackVertically().start();
                        }
                    }
                });
                instance.start();
            }
        }

        public final boolean onTouch(View view, MotionEvent motionEvent) {
            super.onTouch(view, motionEvent);
            int action = motionEvent.getAction();
            if (action == 0) {
                ShortcutBarView.this.mIsDragging = true;
            } else if (action == 1 || action == 3) {
                ShortcutBarView.this.mIsDragging = false;
            }
            return true;
        }
    };
    public int mOrientation = -1;
    public ShortcutBarButton mRecordButton;
    public RevealButton mRevealButton;
    public final int mRevealButtonIconHeight = getResources().getDimensionPixelSize(C1777R.dimen.game_dashboard_shortcut_bar_reveal_button_height);
    public final int mRevealButtonIconWidth = (getResources().getDimensionPixelSize(C1777R.dimen.game_dashboard_shortcut_bar_reveal_button_width) / 2);
    public final C22882 mRevealButtonOnTouchListener = new ShortcutBarTouchController() {
        public final View getView() {
            return ShortcutBarView.this.mRevealButton;
        }

        public final void onMove(float f, float f2) {
            ShortcutBarView.this.mRevealButton.setTranslationY(f2);
        }

        public final void onUp(float f, float f2) {
            ShortcutBarView shortcutBarView = ShortcutBarView.this;
            if (!shortcutBarView.mIsDockingAnimationRunning) {
                RevealButton revealButton = shortcutBarView.mRevealButton;
                Objects.requireNonNull(revealButton);
                if (!revealButton.mRightSide || f >= -500.0f) {
                    RevealButton revealButton2 = ShortcutBarView.this.mRevealButton;
                    Objects.requireNonNull(revealButton2);
                    if (revealButton2.mRightSide || f <= 500.0f) {
                        RevealButton revealButton3 = ShortcutBarView.this.mRevealButton;
                        Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
                        PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(revealButton3);
                        DynamicAnimation.C01422 r0 = DynamicAnimation.TRANSLATION_Y;
                        ShortcutBarView shortcutBarView2 = ShortcutBarView.this;
                        int i = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
                        Objects.requireNonNull(shortcutBarView2);
                        ShortcutBarView shortcutBarView3 = ShortcutBarView.this;
                        Objects.requireNonNull(shortcutBarView3);
                        WeakHashMap<Object, PhysicsAnimator<?>> weakHashMap = PhysicsAnimatorKt.animators;
                        instance.flingConfigs.put(r0, new PhysicsAnimator.FlingConfig(1.9f, (float) shortcutBarView2.mInsets.top, (float) ((shortcutBarView3.getHeight() - shortcutBarView3.mRevealButtonIconHeight) - shortcutBarView3.mInsets.bottom), f2));
                        instance.start();
                        return;
                    }
                }
                ShortcutBarView shortcutBarView4 = ShortcutBarView.this;
                Objects.requireNonNull(shortcutBarView4);
                shortcutBarView4.autoUndock(0.0f);
            }
        }
    };
    public ScreenRecordController mScreenRecordController;
    public ShortcutBarButton mScreenshotButton;
    public int mShiftForTransientBar;
    public final ShortcutBarView$$ExternalSyntheticLambda3 mSystemGestureExcludeUpdater = new ShortcutBarView$$ExternalSyntheticLambda3(this);
    public final int[] mTempInts = new int[2];
    public final Rect mTmpRect = new Rect();
    public final int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    public GameDashboardUiEventLogger mUiEventLogger;

    public abstract class ShortcutBarTouchController implements View.OnTouchListener {
        public boolean mCancelled = false;
        public boolean mHasMoved = false;
        public final PointF mTouchDown = new PointF();
        public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
        public final PointF mViewPositionOnTouchDown = new PointF();

        public abstract View getView();

        public abstract void onMove(float f, float f2);

        public abstract void onUp(float f, float f2);

        public ShortcutBarTouchController() {
        }

        public final void cancelAnimation() {
            View view = getView();
            Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
            PhysicsAnimator.Companion.getInstance(view).cancel();
            this.mCancelled = true;
            this.mHasMoved = false;
            this.mVelocityTracker.clear();
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            float rawX = motionEvent.getRawX() - motionEvent.getX();
            float rawY = motionEvent.getRawY() - motionEvent.getY();
            motionEvent.offsetLocation(rawX, rawY);
            this.mVelocityTracker.addMovement(motionEvent);
            motionEvent.offsetLocation(-rawX, -rawY);
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mTouchDown.set(motionEvent.getRawX(), motionEvent.getRawY());
                this.mViewPositionOnTouchDown.set(getView().getTranslationX(), getView().getTranslationY());
                this.mHasMoved = false;
                this.mCancelled = false;
            } else if (action != 1) {
                if (action != 2) {
                    if (action != 3 || this.mCancelled) {
                        return true;
                    }
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    onUp(this.mVelocityTracker.getXVelocity(), this.mVelocityTracker.getYVelocity());
                    this.mHasMoved = false;
                    this.mVelocityTracker.clear();
                } else if (this.mCancelled) {
                    return true;
                } else {
                    onMove((motionEvent.getRawX() - this.mTouchDown.x) + this.mViewPositionOnTouchDown.x, (motionEvent.getRawY() - this.mTouchDown.y) + this.mViewPositionOnTouchDown.y);
                    if (Math.hypot((double) (motionEvent.getRawX() - this.mTouchDown.x), (double) (motionEvent.getRawY() - this.mTouchDown.y)) > ((double) ShortcutBarView.this.mTouchSlop)) {
                        this.mHasMoved = true;
                    }
                }
            } else if (this.mCancelled) {
                return true;
            } else {
                if (!this.mHasMoved) {
                    view.performClick();
                } else {
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    onUp(this.mVelocityTracker.getXVelocity(), this.mVelocityTracker.getYVelocity());
                }
                this.mHasMoved = false;
                this.mVelocityTracker.clear();
            }
            return true;
        }
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mShiftForTransientBar = 0;
        if (this.mOrientation == 1) {
            this.mInsets = windowInsets.getStableInsets();
        } else {
            Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars());
            if (insetsIgnoringVisibility.right > 0) {
                this.mInsets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars() | WindowInsets.Type.displayCutout());
                this.mShiftForTransientBar = insetsIgnoringVisibility.right;
            } else {
                this.mInsets = windowInsets.getStableInsets();
            }
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    public final void autoUndock(float f) {
        boolean z;
        final float f2;
        if (this.mRevealButton.getVisibility() == 0) {
            if (this.mRevealButton.getTranslationX() > 0.0f) {
                z = true;
            } else {
                z = false;
            }
            if (!this.mIsDockingAnimationRunning) {
                this.mOnTouchListener.cancelAnimation();
                this.mRevealButtonOnTouchListener.cancelAnimation();
                this.mBar.setTranslationX(this.mRevealButton.getTranslationX());
                float translationY = this.mRevealButton.getTranslationY() + ((float) (this.mRevealButton.getHeight() / 2));
                ShortcutBarContainer shortcutBarContainer = this.mBar;
                shortcutBarContainer.setTranslationY(translationY - ((float) (shortcutBarContainer.getHeight() / 2)));
                if (z) {
                    f2 = ((float) ((getWidth() - this.mBar.getWidth()) - this.mBarMarginEnd)) - f;
                } else {
                    f2 = ((float) this.mBarMarginEnd) + f;
                }
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mRevealButton, FrameLayout.TRANSLATION_X, new float[]{f2});
                ObjectAnimator ofInt = ObjectAnimator.ofInt(this.mRevealButton, RevealButton.BACKGROUND_WIDTH, new int[]{this.mBar.getWidth()});
                ObjectAnimator ofInt2 = ObjectAnimator.ofInt(this.mRevealButton, RevealButton.BACKGROUND_HEIGHT, new int[]{this.mBar.getHeight()});
                ObjectAnimator ofInt3 = ObjectAnimator.ofInt(this.mRevealButton, RevealButton.ICON_ALPHA, new int[]{255, 0});
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mBar, FrameLayout.TRANSLATION_X, new float[]{f2});
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mBar, FrameLayout.ALPHA, new float[]{0.0f, 1.0f});
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(new Animator[]{ofFloat, ofInt, ofInt2, ofInt3, ofFloat2, ofFloat3});
                animatorSet.setInterpolator(new FastOutSlowInInterpolator());
                animatorSet.setDuration(200);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        ShortcutBarView.this.mRevealButton.setVisibility(4);
                        RevealButton.C22821 r3 = RevealButton.BACKGROUND_WIDTH;
                        ShortcutBarView shortcutBarView = ShortcutBarView.this;
                        r3.set(shortcutBarView.mRevealButton, Integer.valueOf(shortcutBarView.mRevealButtonIconWidth * 2));
                        RevealButton.C22832 r32 = RevealButton.BACKGROUND_HEIGHT;
                        ShortcutBarView shortcutBarView2 = ShortcutBarView.this;
                        r32.set(shortcutBarView2.mRevealButton, Integer.valueOf(shortcutBarView2.mRevealButtonIconHeight));
                        RevealButton.ICON_ALPHA.set(ShortcutBarView.this.mRevealButton, 255);
                        ShortcutBarContainer shortcutBarContainer = ShortcutBarView.this.mBar;
                        Objects.requireNonNull(shortcutBarContainer);
                        shortcutBarContainer.mUseClearBackground = false;
                        shortcutBarContainer.invalidate();
                        ShortcutBarView.this.mBar.setTranslationX(f2);
                        ShortcutBarView.this.snapBarBackVertically().start();
                        ShortcutBarView.this.mIsDockingAnimationRunning = false;
                    }

                    public final void onAnimationStart(Animator animator) {
                        ShortcutBarView shortcutBarView = ShortcutBarView.this;
                        shortcutBarView.mIsDockingAnimationRunning = true;
                        shortcutBarView.mBar.setVisibility(0);
                        ShortcutBarContainer shortcutBarContainer = ShortcutBarView.this.mBar;
                        Objects.requireNonNull(shortcutBarContainer);
                        shortcutBarContainer.mUseClearBackground = true;
                        shortcutBarContainer.invalidate();
                    }
                });
                animatorSet.start();
            }
        }
    }

    public final void getTouchableRegion() {
        int i;
        int i2;
        if (this.mRevealButton.getVisibility() == 0) {
            this.mRevealButton.getLocationInWindow(this.mTempInts);
        } else {
            this.mBar.getLocationInWindow(this.mTempInts);
        }
        Rect rect = this.mTmpRect;
        int[] iArr = this.mTempInts;
        int i3 = iArr[0];
        int i4 = iArr[1];
        int i5 = iArr[0];
        if (this.mRevealButton.getVisibility() == 0) {
            i = this.mRevealButton.getWidth();
        } else {
            i = this.mBar.getWidth();
        }
        int i6 = i + i5;
        int i7 = this.mTempInts[1];
        if (this.mRevealButton.getVisibility() == 0) {
            i2 = this.mRevealButton.getHeight();
        } else {
            i2 = this.mBar.getHeight();
        }
        rect.set(i3, i4, i6, i2 + i7);
    }

    public final void onConfigChanged(Configuration configuration) {
        int i = configuration.orientation;
        if (i != this.mOrientation) {
            addOnLayoutChangeListener(this.mOnLayoutChangeListener);
            this.mOrientation = i;
        }
    }

    public final void onCountdown(long j) {
        int i;
        int floorDiv = (int) Math.floorDiv(j + 500, 1000);
        if (floorDiv == 1) {
            i = C1777R.C1778drawable.game_dashboard_screen_record_countdown_1;
        } else if (floorDiv == 2) {
            i = C1777R.C1778drawable.game_dashboard_screen_record_countdown_2;
        } else if (floorDiv != 3) {
            i = C1777R.C1778drawable.ic_screen_record;
        } else {
            i = C1777R.C1778drawable.game_dashboard_screen_record_countdown_3;
        }
        setScreenRecordButtonBackground(true);
        this.mRecordButton.setImageResource(i);
        this.mRecordButton.setContentDescription(this.mContext.getString(C1777R.string.accessibility_game_dashboard_shortcut_bar_stop_recording));
    }

    public final void onRecordingEnd() {
        this.mScreenshotButton.announceForAccessibility(this.mContext.getString(C1777R.string.accessibility_game_dashboard_screen_record_stopped));
        onScreenRecordStop();
    }

    public final void onScreenRecordStop() {
        ScreenRecordController screenRecordController = this.mScreenRecordController;
        Objects.requireNonNull(screenRecordController);
        RecordingController recordingController = screenRecordController.mController;
        Objects.requireNonNull(recordingController);
        recordingController.mListeners.remove(this);
        setScreenRecordButtonBackground(false);
        setScreenRecordButtonDrawable();
        RevealButton revealButton = this.mRevealButton;
        Objects.requireNonNull(revealButton);
        revealButton.mIsRecording = false;
        revealButton.invalidate();
        this.mRecordButton.setContentDescription(this.mContext.getString(C1777R.string.accessibility_game_dashboard_shortcut_bar_start_recording));
    }

    public final void setScreenRecordButtonBackground(boolean z) {
        int i;
        Drawable background = this.mRecordButton.getBackground();
        if (z) {
            i = this.mContext.getColor(C1777R.color.game_dashboard_screen_record_countdown);
        } else {
            i = SHORTCUT_BAR_BACKGROUND_COLOR;
        }
        background.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC));
        this.mRecordButton.setBackground(background);
    }

    public final void setScreenRecordButtonDrawable() {
        Drawable mutate = this.mContext.getDrawable(C1777R.C1778drawable.ic_screen_record).mutate();
        mutate.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
        this.mRecordButton.setImageDrawable(mutate);
    }

    public final boolean shouldBeVisible() {
        if (this.mIsScreenshotVisible || this.mIsRecordVisible || this.mIsFpsVisible || this.mIsEntryPointVisible) {
            return true;
        }
        return false;
    }

    public final void slideIn() {
        float f;
        RevealButton revealButton = this.mRevealButton;
        Objects.requireNonNull(revealButton);
        int width = ((View) revealButton.getParent()).getWidth();
        if (!revealButton.mRightSide) {
            width = -revealButton.getWidth();
        }
        float width2 = (float) (revealButton.getWidth() / 2);
        Property property = View.TRANSLATION_X;
        float[] fArr = new float[2];
        float f2 = (float) width;
        fArr[0] = f2;
        if (revealButton.mRightSide) {
            f = f2 - width2;
        } else {
            f = f2 + width2;
        }
        fArr[1] = f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(revealButton, property, fArr);
        ofFloat.setDuration(200);
        ofFloat.start();
    }

    public final void snapBarBackIfNecessary() {
        boolean z;
        int i;
        if (this.mRevealButton.getVisibility() != 0) {
            if ((((float) this.mBar.getWidth()) / 2.0f) + this.mBar.getTranslationX() > ((float) getWidth()) / 2.0f) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                i = ((getWidth() - this.mBarMarginEnd) - this.mInsets.right) - this.mBar.getWidth();
            } else {
                i = this.mInsets.left + this.mBarMarginEnd;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mBar, FrameLayout.TRANSLATION_X, new float[]{(float) i});
            ObjectAnimator snapBarBackVertically = snapBarBackVertically();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ofFloat, snapBarBackVertically});
            animatorSet.setInterpolator(new FastOutSlowInInterpolator());
            animatorSet.setDuration(100);
            animatorSet.start();
        }
    }

    public final ObjectAnimator snapBarBackVertically() {
        float min = Math.min((float) ((getHeight() - this.mInsets.bottom) - this.mBar.getHeight()), Math.max((float) this.mInsets.top, this.mBar.getTranslationY()));
        return ObjectAnimator.ofFloat(this.mBar, FrameLayout.TRANSLATION_Y, new float[]{min});
    }

    /* renamed from: -$$Nest$mdock  reason: not valid java name */
    public static void m309$$Nest$mdock(ShortcutBarView shortcutBarView, final boolean z) {
        int i;
        Objects.requireNonNull(shortcutBarView);
        if (!shortcutBarView.mIsDockingAnimationRunning) {
            shortcutBarView.mOnTouchListener.cancelAnimation();
            shortcutBarView.mRevealButtonOnTouchListener.cancelAnimation();
            shortcutBarView.mRevealButton.setTranslationX(shortcutBarView.mBar.getTranslationX());
            float translationY = shortcutBarView.mBar.getTranslationY() + ((float) (shortcutBarView.mBar.getHeight() / 2));
            RevealButton revealButton = shortcutBarView.mRevealButton;
            revealButton.setTranslationY(translationY - ((float) (revealButton.getHeight() / 2)));
            RevealButton.C22821 r0 = RevealButton.BACKGROUND_WIDTH;
            r0.set(shortcutBarView.mRevealButton, Integer.valueOf(shortcutBarView.mBar.getWidth()));
            RevealButton.C22832 r1 = RevealButton.BACKGROUND_HEIGHT;
            r1.set(shortcutBarView.mRevealButton, Integer.valueOf(shortcutBarView.mBar.getHeight()));
            if (z) {
                i = shortcutBarView.getWidth() - shortcutBarView.mRevealButtonIconWidth;
            } else {
                i = -shortcutBarView.mRevealButtonIconWidth;
            }
            final float f = (float) i;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(shortcutBarView.mBar, FrameLayout.TRANSLATION_X, new float[]{f});
            ObjectAnimator ofInt = ObjectAnimator.ofInt(shortcutBarView.mRevealButton, r0, new int[]{shortcutBarView.mRevealButtonIconWidth * 2});
            ObjectAnimator ofInt2 = ObjectAnimator.ofInt(shortcutBarView.mRevealButton, r1, new int[]{shortcutBarView.mRevealButtonIconHeight});
            ObjectAnimator ofInt3 = ObjectAnimator.ofInt(shortcutBarView.mRevealButton, RevealButton.ICON_ALPHA, new int[]{0, 255});
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(shortcutBarView.mRevealButton, FrameLayout.TRANSLATION_X, new float[]{f});
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(shortcutBarView.mBar, FrameLayout.ALPHA, new float[]{1.0f, 0.0f});
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ofFloat, ofInt, ofInt2, ofInt3, ofFloat2, ofFloat3});
            animatorSet.setInterpolator(new FastOutSlowInInterpolator());
            animatorSet.setDuration(200);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    RevealButton revealButton = ShortcutBarView.this.mRevealButton;
                    boolean z = z;
                    Objects.requireNonNull(revealButton);
                    revealButton.mRightSide = z;
                    revealButton.invalidate();
                    ShortcutBarView.this.mRevealButton.setTranslationX(f);
                    RevealButton.ICON_ALPHA.set(ShortcutBarView.this.mRevealButton, 255);
                    ShortcutBarView.this.mBar.setVisibility(4);
                    ShortcutBarContainer shortcutBarContainer = ShortcutBarView.this.mBar;
                    Objects.requireNonNull(shortcutBarContainer);
                    shortcutBarContainer.mUseClearBackground = false;
                    shortcutBarContainer.invalidate();
                    ShortcutBarView.this.mIsDockingAnimationRunning = false;
                }

                public final void onAnimationStart(Animator animator) {
                    ShortcutBarView shortcutBarView = ShortcutBarView.this;
                    shortcutBarView.mIsDockingAnimationRunning = true;
                    shortcutBarView.mRevealButton.setVisibility(0);
                    ShortcutBarContainer shortcutBarContainer = ShortcutBarView.this.mBar;
                    Objects.requireNonNull(shortcutBarContainer);
                    shortcutBarContainer.mUseClearBackground = true;
                    shortcutBarContainer.invalidate();
                }
            });
            animatorSet.start();
        }
    }

    public ShortcutBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsAttached = true;
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
        getViewTreeObserver().addOnDrawListener(this.mSystemGestureExcludeUpdater);
        this.mConfigurationController.addCallback(this);
        int i = this.mContext.getResources().getConfiguration().orientation;
        int i2 = this.mOrientation;
        if (i2 == -1) {
            this.mOrientation = i;
            return;
        }
        if (i2 != i) {
            addOnLayoutChangeListener(this.mOnLayoutChangeListener);
            this.mOrientation = i;
        }
        if (this.mIsRecordVisible) {
            ScreenRecordController screenRecordController = this.mScreenRecordController;
            Objects.requireNonNull(screenRecordController);
            RecordingController recordingController = screenRecordController.mController;
            Objects.requireNonNull(recordingController);
            recordingController.mListeners.add(this);
            setScreenRecordButtonDrawable();
            ScreenRecordController screenRecordController2 = this.mScreenRecordController;
            Objects.requireNonNull(screenRecordController2);
            boolean isRecording = screenRecordController2.mController.isRecording();
            setScreenRecordButtonBackground(isRecording);
            RevealButton revealButton = this.mRevealButton;
            Objects.requireNonNull(revealButton);
            revealButton.mIsRecording = isRecording;
            revealButton.invalidate();
        }
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        getTouchableRegion();
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(this.mTmpRect);
    }

    public final void onCountdownEnd() {
        setScreenRecordButtonDrawable();
        ScreenRecordController screenRecordController = this.mScreenRecordController;
        Objects.requireNonNull(screenRecordController);
        if (!screenRecordController.mController.isRecording()) {
            ScreenRecordController screenRecordController2 = this.mScreenRecordController;
            Objects.requireNonNull(screenRecordController2);
            RecordingController recordingController = screenRecordController2.mController;
            Objects.requireNonNull(recordingController);
            if (!recordingController.mIsStarting) {
                this.mScreenshotButton.announceForAccessibility(this.mContext.getString(C1777R.string.accessibility_game_dashboard_screen_record_cancelled));
                onScreenRecordStop();
            }
        }
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsAttached = false;
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        getViewTreeObserver().removeOnDrawListener(this.mSystemGestureExcludeUpdater);
        this.mConfigurationController.removeCallback(this);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBar = (ShortcutBarContainer) findViewById(C1777R.C1779id.expanded_bar);
        this.mRevealButton = (RevealButton) findViewById(C1777R.C1779id.reveal);
        this.mScreenshotButton = (ShortcutBarButton) this.mBar.findViewById(C1777R.C1779id.screenshot);
        this.mRecordButton = (ShortcutBarButton) this.mBar.findViewById(C1777R.C1779id.record);
        this.mFpsView = (TextView) this.mBar.findViewById(C1777R.C1779id.fps);
        this.mEntryPointButton = (ShortcutBarButton) this.mBar.findViewById(C1777R.C1779id.entry_point);
    }
}
