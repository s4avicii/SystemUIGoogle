package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.UserManager;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.exifinterface.media.C0155xe8491b12;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.UserIcons;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KeyguardSecurityContainer extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActivePointerId;
    public AlertDialog mAlertDialog;
    public int mCurrentMode;
    public boolean mDisappearAnimRunning;
    public FalsingManager mFalsingManager;
    public GlobalSettings mGlobalSettings;
    public boolean mIsDragging;
    public float mLastTouchY;
    public final ArrayList mMotionEventListeners;
    public KeyguardSecurityViewFlipper mSecurityViewFlipper;
    public final SpringAnimation mSpringAnimation;
    public float mStartTouchY;
    public SwipeListener mSwipeListener;
    public boolean mSwipeUpToRetry;
    public UserSwitcherController mUserSwitcherController;
    public final VelocityTracker mVelocityTracker;
    public final ViewConfiguration mViewConfiguration;
    public ViewMode mViewMode;
    public final C05141 mWindowInsetsAnimationCallback;

    public static class DefaultViewMode implements ViewMode {
        public KeyguardSecurityViewFlipper mViewFlipper;

        public final void init(ViewGroup viewGroup, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController) {
            this.mViewFlipper = keyguardSecurityViewFlipper;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) keyguardSecurityViewFlipper.getLayoutParams();
            layoutParams.gravity = 1;
            this.mViewFlipper.setLayoutParams(layoutParams);
            this.mViewFlipper.setTranslationX(0.0f);
        }
    }

    public static class OneHandedViewMode implements ViewMode {
        public GlobalSettings mGlobalSettings;
        public ValueAnimator mRunningOneHandedAnimator;
        public ViewGroup mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        public final void updateSecurityViewLocation() {
            updateSecurityViewLocation(isLeftAligned(), false);
        }

        public final void init(ViewGroup viewGroup, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController) {
            this.mView = viewGroup;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            this.mGlobalSettings = globalSettings;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) keyguardSecurityViewFlipper.getLayoutParams();
            layoutParams.gravity = 83;
            this.mViewFlipper.setLayoutParams(layoutParams);
            updateSecurityViewLocation(isLeftAligned(), false);
        }

        public final boolean isLeftAligned() {
            if (this.mGlobalSettings.getInt("one_handed_keyguard_side", 0) == 0) {
                return true;
            }
            return false;
        }

        public final void updatePositionByTouchX(float f) {
            boolean z;
            if (f <= ((float) this.mView.getWidth()) / 2.0f) {
                z = true;
            } else {
                z = false;
            }
            updateSecurityViewLocation(z, false);
        }

        public final void updateSecurityViewLocation(boolean z, boolean z2) {
            int i;
            ValueAnimator valueAnimator = this.mRunningOneHandedAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.mRunningOneHandedAnimator = null;
            }
            boolean z3 = false;
            if (z) {
                i = 0;
            } else {
                i = this.mView.getMeasuredWidth() - this.mViewFlipper.getWidth();
            }
            if (z2) {
                Interpolator loadInterpolator = AnimationUtils.loadInterpolator(this.mView.getContext(), 17563674);
                PathInterpolator pathInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
                PathInterpolator pathInterpolator2 = Interpolators.LINEAR_OUT_SLOW_IN;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                this.mRunningOneHandedAnimator = ofFloat;
                ofFloat.setDuration(500);
                this.mRunningOneHandedAnimator.setInterpolator(Interpolators.LINEAR);
                int translationX = (int) this.mViewFlipper.getTranslationX();
                int dimension = (int) this.mView.getResources().getDimension(C1777R.dimen.one_handed_bouncer_move_animation_translation);
                if (this.mViewFlipper.hasOverlappingRendering() && this.mViewFlipper.getLayerType() != 2) {
                    z3 = true;
                }
                boolean z4 = z3;
                if (z4) {
                    this.mViewFlipper.setLayerType(2, (Paint) null);
                }
                float alpha = this.mViewFlipper.getAlpha();
                this.mRunningOneHandedAnimator.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        OneHandedViewMode.this.mRunningOneHandedAnimator = null;
                    }
                });
                this.mRunningOneHandedAnimator.addUpdateListener(new C0518xc47775e2(this, loadInterpolator, dimension, z, pathInterpolator, alpha, translationX, pathInterpolator2, i, z4));
                this.mRunningOneHandedAnimator.start();
                return;
            }
            this.mViewFlipper.setTranslationX((float) i);
        }

        public final int getChildWidthMeasureSpec(int i) {
            return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i) / 2, 1073741824);
        }

        public final void handleTap(MotionEvent motionEvent) {
            int i;
            float x = motionEvent.getX();
            boolean isLeftAligned = isLeftAligned();
            if ((isLeftAligned && x > ((float) this.mView.getWidth()) / 2.0f) || (!isLeftAligned && x < ((float) this.mView.getWidth()) / 2.0f)) {
                boolean z = !isLeftAligned;
                this.mGlobalSettings.putInt("one_handed_keyguard_side", z ^ true ? 1 : 0);
                if (z) {
                    i = 5;
                } else {
                    i = 6;
                }
                SysUiStatsLog.write(63, i);
                updateSecurityViewLocation(z, true);
            }
        }
    }

    public interface SecurityCallback {
    }

    public interface SwipeListener {
    }

    public static class UserSwitcherViewMode implements ViewMode {
        public FalsingManager mFalsingManager;
        public KeyguardUserSwitcherPopupMenu mPopup;
        public Resources mResources;
        public TextView mUserSwitcher;
        public UserSwitcherController mUserSwitcherController;
        public ViewGroup mUserSwitcherViewGroup;
        public ViewGroup mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        public final void init(ViewGroup viewGroup, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController) {
            Drawable drawable;
            final UserSwitcherController.UserRecord userRecord;
            this.mView = viewGroup;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            this.mFalsingManager = falsingManager;
            this.mUserSwitcherController = userSwitcherController;
            this.mResources = viewGroup.getContext().getResources();
            if (this.mUserSwitcherViewGroup == null) {
                LayoutInflater.from(viewGroup.getContext()).inflate(C1777R.layout.keyguard_bouncer_user_switcher, this.mView, true);
                this.mUserSwitcherViewGroup = (ViewGroup) this.mView.findViewById(C1777R.C1779id.keyguard_bouncer_user_switcher);
            }
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            Bitmap userIcon = UserManager.get(this.mView.getContext()).getUserIcon(currentUser);
            if (userIcon != null) {
                drawable = new BitmapDrawable(userIcon);
            } else {
                drawable = UserIcons.getDefaultUserIcon(this.mResources, currentUser, false);
            }
            ((ImageView) this.mView.findViewById(C1777R.C1779id.user_icon)).setImageDrawable(drawable);
            updateSecurityViewLocation();
            this.mUserSwitcher = (TextView) this.mView.findViewById(C1777R.C1779id.user_switcher_header);
            UserSwitcherController userSwitcherController2 = this.mUserSwitcherController;
            Objects.requireNonNull(userSwitcherController2);
            int i = 0;
            while (true) {
                if (i >= userSwitcherController2.mUsers.size()) {
                    userRecord = null;
                    break;
                }
                userRecord = userSwitcherController2.mUsers.get(i);
                if (userRecord.isCurrent) {
                    break;
                }
                i++;
            }
            this.mUserSwitcher.setText(this.mUserSwitcherController.getCurrentUserName());
            ViewGroup viewGroup2 = (ViewGroup) this.mView.findViewById(C1777R.C1779id.user_switcher_anchor);
            C05161 r3 = new UserSwitcherController.BaseUserAdapter(this.mUserSwitcherController) {
                public final View getView(int i, View view, ViewGroup viewGroup) {
                    Drawable drawable;
                    float f;
                    Drawable drawable2;
                    int i2;
                    UserSwitcherController.UserRecord item = getItem(i);
                    FrameLayout frameLayout = (FrameLayout) view;
                    if (frameLayout == null) {
                        frameLayout = (FrameLayout) LayoutInflater.from(viewGroup.getContext()).inflate(C1777R.layout.keyguard_bouncer_user_switcher_item, viewGroup, false);
                    }
                    TextView textView = (TextView) frameLayout.getChildAt(0);
                    textView.setText(getName(viewGroup.getContext(), item));
                    if (item.picture != null) {
                        drawable = new BitmapDrawable(item.picture);
                    } else {
                        Context context = frameLayout.getContext();
                        if (!item.isCurrent || !item.isGuest) {
                            drawable2 = UserSwitcherController.BaseUserAdapter.getIconDrawable(context, item);
                        } else {
                            drawable2 = context.getDrawable(C1777R.C1778drawable.ic_avatar_guest_user);
                        }
                        if (item.isSwitchToEnabled) {
                            i2 = Utils.getColorAttrDefaultColor(context, 17956901);
                        } else {
                            i2 = context.getResources().getColor(C1777R.color.kg_user_switcher_restricted_avatar_icon_color, context.getTheme());
                        }
                        drawable2.setTint(i2);
                        Drawable drawable3 = context.getDrawable(C1777R.C1778drawable.kg_bg_avatar);
                        drawable3.setTintBlendMode(BlendMode.DST);
                        drawable3.setTint(Utils.getColorAttrDefaultColor(context, 17956912));
                        drawable = new LayerDrawable(new Drawable[]{drawable3, drawable2});
                    }
                    int dimensionPixelSize = frameLayout.getResources().getDimensionPixelSize(C1777R.dimen.bouncer_user_switcher_item_icon_size);
                    int dimensionPixelSize2 = frameLayout.getResources().getDimensionPixelSize(C1777R.dimen.bouncer_user_switcher_item_icon_padding);
                    drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                    textView.setCompoundDrawablePadding(dimensionPixelSize2);
                    textView.setCompoundDrawablesRelative(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
                    if (item == userRecord) {
                        textView.setBackground(frameLayout.getContext().getDrawable(C1777R.C1778drawable.bouncer_user_switcher_item_selected_bg));
                    } else {
                        textView.setBackground((Drawable) null);
                    }
                    frameLayout.setEnabled(item.isSwitchToEnabled);
                    if (frameLayout.isEnabled()) {
                        f = 1.0f;
                    } else {
                        f = 0.38f;
                    }
                    frameLayout.setAlpha(f);
                    return frameLayout;
                }
            };
            if (r3.getCount() < 2) {
                ((LayerDrawable) this.mUserSwitcher.getBackground()).getDrawable(1).setAlpha(0);
                viewGroup2.setClickable(false);
                return;
            }
            ((LayerDrawable) this.mUserSwitcher.getBackground()).getDrawable(1).setAlpha(255);
            viewGroup2.setOnClickListener(new C0519xd955338c(this, viewGroup2, r3));
        }

        public final void reset() {
            KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu = this.mPopup;
            if (keyguardUserSwitcherPopupMenu != null) {
                keyguardUserSwitcherPopupMenu.dismiss();
                this.mPopup = null;
            }
        }

        public final void startAppearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
            if (securityMode != KeyguardSecurityModel.SecurityMode.Password) {
                this.mUserSwitcherViewGroup.setAlpha(0.0f);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mUserSwitcherViewGroup, View.ALPHA, new float[]{1.0f});
                ofFloat.setInterpolator(Interpolators.ALPHA_IN);
                ofFloat.setDuration(500);
                ofFloat.start();
            }
        }

        public final void startDisappearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
            if (securityMode != KeyguardSecurityModel.SecurityMode.Password) {
                int dimensionPixelSize = this.mResources.getDimensionPixelSize(C1777R.dimen.disappear_y_translation);
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Y, new float[]{(float) dimensionPixelSize});
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mView, View.ALPHA, new float[]{0.0f});
                animatorSet.setInterpolator(Interpolators.STANDARD_ACCELERATE);
                animatorSet.playTogether(new Animator[]{ofFloat2, ofFloat});
                animatorSet.start();
            }
        }

        public final void updateSecurityViewLocation() {
            if (this.mResources.getConfiguration().orientation == 1) {
                KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = this.mViewFlipper;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) keyguardSecurityViewFlipper.getLayoutParams();
                layoutParams.gravity = 1;
                keyguardSecurityViewFlipper.setLayoutParams(layoutParams);
                ViewGroup viewGroup = this.mUserSwitcherViewGroup;
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
                layoutParams2.gravity = 1;
                viewGroup.setLayoutParams(layoutParams2);
                this.mUserSwitcherViewGroup.setTranslationY(0.0f);
                return;
            }
            KeyguardSecurityViewFlipper keyguardSecurityViewFlipper2 = this.mViewFlipper;
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) keyguardSecurityViewFlipper2.getLayoutParams();
            layoutParams3.gravity = 85;
            keyguardSecurityViewFlipper2.setLayoutParams(layoutParams3);
            ViewGroup viewGroup2 = this.mUserSwitcherViewGroup;
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) viewGroup2.getLayoutParams();
            layoutParams4.gravity = 19;
            viewGroup2.setLayoutParams(layoutParams4);
            this.mUserSwitcherViewGroup.setTranslationY((float) (-this.mResources.getDimensionPixelSize(C1777R.dimen.status_bar_height)));
        }

        public final int getChildWidthMeasureSpec(int i) {
            return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i) / 2, 1073741824);
        }
    }

    public interface ViewMode {
        int getChildWidthMeasureSpec(int i) {
            return i;
        }

        void handleTap(MotionEvent motionEvent) {
        }

        void init(ViewGroup viewGroup, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController) {
        }

        void reset() {
        }

        void startAppearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        }

        void startDisappearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        }

        void updatePositionByTouchX(float f) {
        }

        void updateSecurityViewLocation() {
        }
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void onMeasure(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < getChildCount(); i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                int childWidthMeasureSpec = this.mViewMode.getChildWidthMeasureSpec(i);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                layoutParams.width = View.MeasureSpec.getSize(childWidthMeasureSpec);
                measureChildWithMargins(childAt, childWidthMeasureSpec, 0, i2, 0);
                i3 = Math.max(i3, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                i4 = Math.max(i4, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                i5 = View.combineMeasuredStates(i5, childAt.getMeasuredState());
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + i3, getSuggestedMinimumWidth()), i, i5), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + i4, getSuggestedMinimumHeight()), i2, i5 << 16));
    }

    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void showAlmostAtWipeDialog(int i, int i2, int i3) {
        String str;
        if (i3 == 1) {
            str = this.mContext.getString(C1777R.string.kg_failed_attempts_almost_at_wipe, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        } else if (i3 == 2) {
            str = this.mContext.getString(C1777R.string.kg_failed_attempts_almost_at_erase_profile, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        } else if (i3 != 3) {
            str = null;
        } else {
            str = this.mContext.getString(C1777R.string.kg_failed_attempts_almost_at_erase_user, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        }
        showDialog(str);
    }

    public final void showWipeDialog(int i, int i2) {
        String str;
        if (i2 == 1) {
            str = this.mContext.getString(C1777R.string.kg_failed_attempts_now_wiping, new Object[]{Integer.valueOf(i)});
        } else if (i2 == 2) {
            str = this.mContext.getString(C1777R.string.kg_failed_attempts_now_erasing_profile, new Object[]{Integer.valueOf(i)});
        } else if (i2 != 3) {
            str = null;
        } else {
            str = this.mContext.getString(C1777R.string.kg_failed_attempts_now_erasing_user, new Object[]{Integer.valueOf(i)});
        }
        showDialog(str);
    }

    public enum BouncerUiEvent implements UiEventLogger.UiEventEnum {
        UNKNOWN(0),
        BOUNCER_DISMISS_EXTENDED_ACCESS(413),
        BOUNCER_DISMISS_BIOMETRIC(414),
        BOUNCER_DISMISS_NONE_SECURITY(415),
        BOUNCER_DISMISS_PASSWORD(416),
        BOUNCER_DISMISS_SIM(417),
        BOUNCER_PASSWORD_SUCCESS(418),
        BOUNCER_PASSWORD_FAILURE(419);
        
        private final int mId;

        /* access modifiers changed from: public */
        BouncerUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public KeyguardSecurityContainer(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public static String modeToString(int i) {
        if (i == 0) {
            return "Default";
        }
        if (i == 1) {
            return "OneHanded";
        }
        if (i == 2) {
            return "UserSwitcher";
        }
        throw new IllegalArgumentException(C0155xe8491b12.m16m("mode: ", i, " not supported"));
    }

    public final boolean isOneHandedModeLeftAligned() {
        if (this.mCurrentMode != 1 || !((OneHandedViewMode) this.mViewMode).isLeftAligned()) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        if (r3 != 3) goto L_0x007c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            java.util.ArrayList r0 = r5.mMotionEventListeners
            java.util.stream.Stream r0 = r0.stream()
            com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda0 r1 = new com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda0
            r1.<init>(r6)
            boolean r0 = r0.anyMatch(r1)
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001c
            boolean r0 = super.onInterceptTouchEvent(r6)
            if (r0 == 0) goto L_0x001a
            goto L_0x001c
        L_0x001a:
            r0 = r1
            goto L_0x001d
        L_0x001c:
            r0 = r2
        L_0x001d:
            int r3 = r6.getActionMasked()
            if (r3 == 0) goto L_0x0067
            if (r3 == r2) goto L_0x0064
            r4 = 2
            if (r3 == r4) goto L_0x002c
            r6 = 3
            if (r3 == r6) goto L_0x0064
            goto L_0x007c
        L_0x002c:
            boolean r3 = r5.mIsDragging
            if (r3 == 0) goto L_0x0031
            return r2
        L_0x0031:
            boolean r3 = r5.mSwipeUpToRetry
            if (r3 != 0) goto L_0x0036
            return r1
        L_0x0036:
            com.android.keyguard.KeyguardSecurityViewFlipper r3 = r5.mSecurityViewFlipper
            com.android.keyguard.KeyguardInputView r3 = r3.getSecurityView()
            boolean r3 = r3.disallowInterceptTouch(r6)
            if (r3 == 0) goto L_0x0043
            return r1
        L_0x0043:
            int r1 = r5.mActivePointerId
            int r1 = r6.findPointerIndex(r1)
            android.view.ViewConfiguration r3 = r5.mViewConfiguration
            int r3 = r3.getScaledTouchSlop()
            float r3 = (float) r3
            r4 = 1082130432(0x40800000, float:4.0)
            float r3 = r3 * r4
            r4 = -1
            if (r1 == r4) goto L_0x007c
            float r4 = r5.mStartTouchY
            float r6 = r6.getY(r1)
            float r4 = r4 - r6
            int r6 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r6 <= 0) goto L_0x007c
            r5.mIsDragging = r2
            return r2
        L_0x0064:
            r5.mIsDragging = r1
            goto L_0x007c
        L_0x0067:
            int r1 = r6.getActionIndex()
            float r2 = r6.getY(r1)
            r5.mStartTouchY = r2
            int r6 = r6.getPointerId(r1)
            r5.mActivePointerId = r6
            android.view.VelocityTracker r5 = r5.mVelocityTracker
            r5.clear()
        L_0x007c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityContainer.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void showDialog(String str) {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        AlertDialog create = new AlertDialog.Builder(this.mContext).setTitle((CharSequence) null).setMessage(str).setCancelable(false).setNeutralButton(C1777R.string.f97ok, (DialogInterface.OnClickListener) null).create();
        this.mAlertDialog = create;
        if (!(this.mContext instanceof Activity)) {
            create.getWindow().setType(2009);
        }
        this.mAlertDialog.show();
    }

    public final void showTimeoutDialog(int i, int i2, LockPatternUtils lockPatternUtils, KeyguardSecurityModel.SecurityMode securityMode) {
        int i3;
        int i4 = i2 / 1000;
        int ordinal = securityMode.ordinal();
        if (ordinal == 2) {
            i3 = C1777R.string.kg_too_many_failed_pattern_attempts_dialog_message;
        } else if (ordinal == 3) {
            i3 = C1777R.string.kg_too_many_failed_password_attempts_dialog_message;
        } else if (ordinal != 4) {
            i3 = 0;
        } else {
            i3 = C1777R.string.kg_too_many_failed_pin_attempts_dialog_message;
        }
        if (i3 != 0) {
            showDialog(this.mContext.getString(i3, new Object[]{Integer.valueOf(lockPatternUtils.getCurrentFailedPasswordAttempts(i)), Integer.valueOf(i4)}));
        }
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mMotionEventListeners = new ArrayList();
        this.mLastTouchY = -1.0f;
        this.mActivePointerId = -1;
        this.mStartTouchY = -1.0f;
        this.mViewMode = new DefaultViewMode();
        this.mCurrentMode = 0;
        this.mWindowInsetsAnimationCallback = new WindowInsetsAnimation.Callback() {
            public final Rect mFinalBounds = new Rect();
            public final Rect mInitialBounds = new Rect();

            public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                if (!KeyguardSecurityContainer.this.mDisappearAnimRunning) {
                    InteractionJankMonitor.getInstance().end(17);
                    for (int i = 0; i < KeyguardSecurityContainer.this.getChildCount(); i++) {
                        View childAt = KeyguardSecurityContainer.this.getChildAt(i);
                        childAt.setTranslationY((float) 0);
                        childAt.setAlpha(1.0f);
                    }
                    return;
                }
                InteractionJankMonitor.getInstance().end(20);
            }

            public final void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                KeyguardSecurityContainer.this.mSecurityViewFlipper.getBoundsOnScreen(this.mInitialBounds);
            }

            public final WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                int i;
                float f;
                float f2;
                boolean z = KeyguardSecurityContainer.this.mDisappearAnimRunning;
                if (z) {
                    i = -(this.mFinalBounds.bottom - this.mInitialBounds.bottom);
                } else {
                    i = this.mInitialBounds.bottom - this.mFinalBounds.bottom;
                }
                float f3 = (float) i;
                if (z) {
                    f = -(((float) (this.mFinalBounds.bottom - this.mInitialBounds.bottom)) * 0.75f);
                } else {
                    f = 0.0f;
                }
                float f4 = 1.0f;
                int i2 = 0;
                for (WindowInsetsAnimation next : list) {
                    if ((next.getTypeMask() & WindowInsets.Type.ime()) != 0) {
                        f4 = next.getInterpolatedFraction();
                        i2 += (int) MathUtils.lerp(f3, f, f4);
                    }
                }
                KeyguardSecurityContainer keyguardSecurityContainer = KeyguardSecurityContainer.this;
                if (keyguardSecurityContainer.mDisappearAnimRunning) {
                    f2 = 1.0f - f4;
                } else {
                    f2 = Math.max(f4, keyguardSecurityContainer.getAlpha());
                }
                for (int i3 = 0; i3 < KeyguardSecurityContainer.this.getChildCount(); i3++) {
                    View childAt = KeyguardSecurityContainer.this.getChildAt(i3);
                    childAt.setTranslationY((float) i2);
                    childAt.setAlpha(f2);
                }
                return windowInsets;
            }

            public final WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                KeyguardSecurityContainer keyguardSecurityContainer = KeyguardSecurityContainer.this;
                if (!keyguardSecurityContainer.mDisappearAnimRunning) {
                    KeyguardInputView securityView = keyguardSecurityContainer.mSecurityViewFlipper.getSecurityView();
                    if (securityView != null) {
                        InteractionJankMonitor.getInstance().begin(securityView, 17);
                    }
                } else {
                    KeyguardInputView securityView2 = keyguardSecurityContainer.mSecurityViewFlipper.getSecurityView();
                    if (securityView2 != null) {
                        InteractionJankMonitor.getInstance().begin(securityView2, 20);
                    }
                }
                KeyguardSecurityContainer.this.mSecurityViewFlipper.getBoundsOnScreen(this.mFinalBounds);
                return bounds;
            }
        };
        this.mSpringAnimation = new SpringAnimation(this, DynamicAnimation.f22Y);
        this.mViewConfiguration = ViewConfiguration.get(context);
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int max = Integer.max(windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom, windowInsets.getInsets(WindowInsets.Type.ime()).bottom);
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), max);
        return windowInsets.inset(0, 0, 0, max);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mViewMode.updateSecurityViewLocation();
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSecurityViewFlipper = (KeyguardSecurityViewFlipper) findViewById(C1777R.C1779id.view_flipper);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mViewMode.updateSecurityViewLocation();
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0085  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            int r0 = r8.getActionMasked()
            java.util.ArrayList r1 = r7.mMotionEventListeners
            java.util.stream.Stream r1 = r1.stream()
            com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda1 r2 = new com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda1
            r2.<init>(r8)
            boolean r1 = r1.anyMatch(r2)
            if (r1 != 0) goto L_0x0018
            super.onTouchEvent(r8)
        L_0x0018:
            r1 = -1082130432(0xffffffffbf800000, float:-1.0)
            r2 = 1
            r3 = 0
            if (r0 == r2) goto L_0x006b
            r4 = 2
            if (r0 == r4) goto L_0x0046
            r4 = 3
            if (r0 == r4) goto L_0x006b
            r1 = 6
            if (r0 == r1) goto L_0x0028
            goto L_0x0083
        L_0x0028:
            int r1 = r8.getActionIndex()
            int r4 = r8.getPointerId(r1)
            int r5 = r7.mActivePointerId
            if (r4 != r5) goto L_0x0083
            if (r1 != 0) goto L_0x0038
            r1 = r2
            goto L_0x0039
        L_0x0038:
            r1 = r3
        L_0x0039:
            float r4 = r8.getY(r1)
            r7.mLastTouchY = r4
            int r1 = r8.getPointerId(r1)
            r7.mActivePointerId = r1
            goto L_0x0083
        L_0x0046:
            android.view.VelocityTracker r4 = r7.mVelocityTracker
            r4.addMovement(r8)
            int r4 = r7.mActivePointerId
            int r4 = r8.findPointerIndex(r4)
            float r4 = r8.getY(r4)
            float r5 = r7.mLastTouchY
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0068
            float r1 = r4 - r5
            float r5 = r7.getTranslationY()
            r6 = 1048576000(0x3e800000, float:0.25)
            float r1 = r1 * r6
            float r1 = r1 + r5
            r7.setTranslationY(r1)
        L_0x0068:
            r7.mLastTouchY = r4
            goto L_0x0083
        L_0x006b:
            r4 = -1
            r7.mActivePointerId = r4
            r7.mLastTouchY = r1
            r7.mIsDragging = r3
            android.view.VelocityTracker r1 = r7.mVelocityTracker
            float r1 = r1.getYVelocity()
            androidx.dynamicanimation.animation.SpringAnimation r4 = r7.mSpringAnimation
            java.util.Objects.requireNonNull(r4)
            r4.mVelocity = r1
            r1 = 0
            r4.animateToFinalPosition(r1)
        L_0x0083:
            if (r0 != r2) goto L_0x00db
            float r0 = r7.getTranslationY()
            float r0 = -r0
            r1 = 1092616192(0x41200000, float:10.0)
            android.content.res.Resources r4 = r7.getResources()
            android.util.DisplayMetrics r4 = r4.getDisplayMetrics()
            float r1 = android.util.TypedValue.applyDimension(r2, r1, r4)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x00d2
            com.android.keyguard.KeyguardSecurityContainer$SwipeListener r7 = r7.mSwipeListener
            if (r7 == 0) goto L_0x00db
            com.android.keyguard.KeyguardSecurityContainerController$3 r7 = (com.android.keyguard.KeyguardSecurityContainerController.C05223) r7
            com.android.keyguard.KeyguardSecurityContainerController r8 = com.android.keyguard.KeyguardSecurityContainerController.this
            com.android.keyguard.KeyguardUpdateMonitor r8 = r8.mUpdateMonitor
            java.util.Objects.requireNonNull(r8)
            int r8 = r8.mFaceRunningState
            if (r8 != r2) goto L_0x00ae
            r3 = r2
        L_0x00ae:
            if (r3 != 0) goto L_0x00db
            com.android.keyguard.KeyguardSecurityContainerController r8 = com.android.keyguard.KeyguardSecurityContainerController.this
            com.android.keyguard.KeyguardUpdateMonitor r8 = r8.mUpdateMonitor
            r8.requestFaceAuth(r2)
            com.android.keyguard.KeyguardSecurityContainerController r8 = com.android.keyguard.KeyguardSecurityContainerController.this
            com.android.keyguard.KeyguardSecurityContainerController$2 r8 = r8.mKeyguardSecurityCallback
            r8.userActivity()
            com.android.keyguard.KeyguardSecurityContainerController r7 = com.android.keyguard.KeyguardSecurityContainerController.this
            r8 = 0
            java.util.Objects.requireNonNull(r7)
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r0 = r7.mCurrentSecurityMode
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.None
            if (r0 == r1) goto L_0x00db
            com.android.keyguard.KeyguardInputViewController r7 = r7.getCurrentSecurityController()
            r7.showMessage(r8, r8)
            goto L_0x00db
        L_0x00d2:
            boolean r0 = r7.mIsDragging
            if (r0 != 0) goto L_0x00db
            com.android.keyguard.KeyguardSecurityContainer$ViewMode r7 = r7.mViewMode
            r7.handleTap(r8)
        L_0x00db:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityContainer.onTouchEvent(android.view.MotionEvent):boolean");
    }
}
