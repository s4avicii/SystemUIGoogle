package com.android.systemui.statusbar.policy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.graphics.ColorUtils;
import com.android.keyguard.KeyguardConstants;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardVisibilityHelper;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.animation.AppearAnimationUtils;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.communal.CommunalStateController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Objects;

public final class KeyguardUserSwitcherController extends ViewController<KeyguardUserSwitcherView> {
    public static final AnimationProperties ANIMATION_PROPERTIES;
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final KeyguardUserAdapter mAdapter;
    public final KeyguardUserSwitcherScrim mBackground;
    public int mBarState;
    public ObjectAnimator mBgAnimator;
    public float mDarkAmount;
    public final C16294 mDataSetObserver = new DataSetObserver() {
        public final void onChanged() {
            KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
            Objects.requireNonNull(keyguardUserSwitcherController);
            int childCount = keyguardUserSwitcherController.mListView.getChildCount();
            int count = keyguardUserSwitcherController.mAdapter.getCount();
            int max = Math.max(childCount, count);
            if (KeyguardUserSwitcherController.DEBUG) {
                Log.d("KeyguardUserSwitcherController", String.format("refreshUserList childCount=%d adapterCount=%d", new Object[]{Integer.valueOf(childCount), Integer.valueOf(count)}));
            }
            boolean z = false;
            for (int i = 0; i < max; i++) {
                if (i < count) {
                    View view = null;
                    if (i < childCount) {
                        view = keyguardUserSwitcherController.mListView.getChildAt(i);
                    }
                    KeyguardUserDetailItemView keyguardUserDetailItemView = (KeyguardUserDetailItemView) keyguardUserSwitcherController.mAdapter.getView(i, view, keyguardUserSwitcherController.mListView);
                    UserSwitcherController.UserRecord userRecord = (UserSwitcherController.UserRecord) keyguardUserDetailItemView.getTag();
                    if (userRecord.isCurrent) {
                        if (i != 0) {
                            Log.w("KeyguardUserSwitcherController", "Current user is not the first view in the list");
                        }
                        int i2 = userRecord.info.id;
                        keyguardUserDetailItemView.updateVisibilities(true, keyguardUserSwitcherController.mUserSwitcherOpen, false);
                        z = true;
                    } else {
                        keyguardUserDetailItemView.updateVisibilities(keyguardUserSwitcherController.mUserSwitcherOpen, true, false);
                    }
                    float f = keyguardUserSwitcherController.mDarkAmount;
                    if (keyguardUserDetailItemView.mDarkAmount != f) {
                        keyguardUserDetailItemView.mDarkAmount = f;
                        keyguardUserDetailItemView.mName.setTextColor(ColorUtils.blendARGB(keyguardUserDetailItemView.mTextColor, -1, f));
                    }
                    if (view == null) {
                        keyguardUserSwitcherController.mListView.addView(keyguardUserDetailItemView);
                    } else if (view != keyguardUserDetailItemView) {
                        KeyguardUserSwitcherListView keyguardUserSwitcherListView = keyguardUserSwitcherController.mListView;
                        Objects.requireNonNull(keyguardUserSwitcherListView);
                        keyguardUserSwitcherListView.removeViewAt(i);
                        keyguardUserSwitcherListView.addView(keyguardUserDetailItemView, i);
                    }
                } else {
                    KeyguardUserSwitcherListView keyguardUserSwitcherListView2 = keyguardUserSwitcherController.mListView;
                    Objects.requireNonNull(keyguardUserSwitcherListView2);
                    keyguardUserSwitcherListView2.removeViewAt(keyguardUserSwitcherListView2.getChildCount() - 1);
                }
            }
            if (!z) {
                Log.w("KeyguardUserSwitcherController", "Current user is not listed");
            }
        }
    };
    public final C16261 mInfoCallback = new KeyguardUpdateMonitorCallback() {
        public final void onKeyguardVisibilityChanged(boolean z) {
            if (KeyguardUserSwitcherController.DEBUG) {
                Log.d("KeyguardUserSwitcherController", String.format("onKeyguardVisibilityChanged %b", new Object[]{Boolean.valueOf(z)}));
            }
            if (!z) {
                KeyguardUserSwitcherController.this.closeSwitcherIfOpenAndNotSimple(false);
            }
        }

        public final void onUserSwitching(int i) {
            KeyguardUserSwitcherController.this.closeSwitcherIfOpenAndNotSimple(false);
        }
    };
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardVisibilityHelper mKeyguardVisibilityHelper;
    public KeyguardUserSwitcherListView mListView;
    public final ScreenLifecycle mScreenLifecycle;
    public final C16272 mScreenObserver = new ScreenLifecycle.Observer() {
        public final void onScreenTurnedOff() {
            if (KeyguardUserSwitcherController.DEBUG) {
                Log.d("KeyguardUserSwitcherController", "onScreenTurnedOff");
            }
            KeyguardUserSwitcherController.this.closeSwitcherIfOpenAndNotSimple(false);
        }
    };
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final C16283 mStatusBarStateListener = new StatusBarStateController.StateListener() {
        public final void onDozeAmountChanged(float f, float f2) {
            boolean z = true;
            if (KeyguardUserSwitcherController.DEBUG) {
                Log.d("KeyguardUserSwitcherController", String.format("onDozeAmountChanged: linearAmount=%f amount=%f", new Object[]{Float.valueOf(f), Float.valueOf(f2)}));
            }
            KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
            Objects.requireNonNull(keyguardUserSwitcherController);
            if (f2 != 1.0f) {
                z = false;
            }
            if (f2 != keyguardUserSwitcherController.mDarkAmount) {
                keyguardUserSwitcherController.mDarkAmount = f2;
                KeyguardUserSwitcherListView keyguardUserSwitcherListView = keyguardUserSwitcherController.mListView;
                Objects.requireNonNull(keyguardUserSwitcherListView);
                int childCount = keyguardUserSwitcherListView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = keyguardUserSwitcherListView.getChildAt(i);
                    if (childAt instanceof KeyguardUserDetailItemView) {
                        KeyguardUserDetailItemView keyguardUserDetailItemView = (KeyguardUserDetailItemView) childAt;
                        Objects.requireNonNull(keyguardUserDetailItemView);
                        if (keyguardUserDetailItemView.mDarkAmount != f2) {
                            keyguardUserDetailItemView.mDarkAmount = f2;
                            keyguardUserDetailItemView.mName.setTextColor(ColorUtils.blendARGB(keyguardUserDetailItemView.mTextColor, -1, f2));
                        }
                    }
                }
                if (z) {
                    keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(false);
                }
            }
        }

        public final void onStateChanged(int i) {
            if (KeyguardUserSwitcherController.DEBUG) {
                Log.d("KeyguardUserSwitcherController", String.format("onStateChanged: newState=%d", new Object[]{Integer.valueOf(i)}));
            }
            boolean goingToFullShade = KeyguardUserSwitcherController.this.mStatusBarStateController.goingToFullShade();
            boolean isKeyguardFadingAway = KeyguardUserSwitcherController.this.mKeyguardStateController.isKeyguardFadingAway();
            KeyguardUserSwitcherController keyguardUserSwitcherController = KeyguardUserSwitcherController.this;
            int i2 = keyguardUserSwitcherController.mBarState;
            keyguardUserSwitcherController.mBarState = i;
            if (keyguardUserSwitcherController.mStatusBarStateController.goingToFullShade() || KeyguardUserSwitcherController.this.mKeyguardStateController.isKeyguardFadingAway()) {
                KeyguardUserSwitcherController.this.closeSwitcherIfOpenAndNotSimple(true);
            }
            KeyguardUserSwitcherController keyguardUserSwitcherController2 = KeyguardUserSwitcherController.this;
            Objects.requireNonNull(keyguardUserSwitcherController2);
            keyguardUserSwitcherController2.mKeyguardVisibilityHelper.setViewVisibility(i, isKeyguardFadingAway, goingToFullShade, i2);
        }
    };
    public final UserSwitcherController mUserSwitcherController;
    public boolean mUserSwitcherOpen;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardUserSwitcherController(KeyguardUserSwitcherView keyguardUserSwitcherView, Context context, Resources resources, LayoutInflater layoutInflater, ScreenLifecycle screenLifecycle, UserSwitcherController userSwitcherController, CommunalStateController communalStateController, KeyguardStateController keyguardStateController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ScreenOffAnimationController screenOffAnimationController) {
        super(keyguardUserSwitcherView);
        if (DEBUG) {
            Log.d("KeyguardUserSwitcherController", "New KeyguardUserSwitcherController");
        }
        this.mScreenLifecycle = screenLifecycle;
        UserSwitcherController userSwitcherController2 = userSwitcherController;
        this.mUserSwitcherController = userSwitcherController2;
        KeyguardStateController keyguardStateController2 = keyguardStateController;
        this.mKeyguardStateController = keyguardStateController2;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        Context context2 = context;
        this.mAdapter = new KeyguardUserAdapter(context2, resources, layoutInflater, userSwitcherController2, this);
        this.mKeyguardVisibilityHelper = new KeyguardVisibilityHelper(keyguardUserSwitcherView, communalStateController, keyguardStateController2, screenOffAnimationController, false, false);
        this.mBackground = new KeyguardUserSwitcherScrim(context2);
    }

    public static class KeyguardUserAdapter extends UserSwitcherController.BaseUserAdapter implements View.OnClickListener {
        public final Context mContext;
        public KeyguardUserSwitcherController mKeyguardUserSwitcherController;
        public final LayoutInflater mLayoutInflater;
        public final Resources mResources;
        public ArrayList<UserSwitcherController.UserRecord> mUsersOrdered = new ArrayList<>();

        public KeyguardUserAdapter(Context context, Resources resources, LayoutInflater layoutInflater, UserSwitcherController userSwitcherController, KeyguardUserSwitcherController keyguardUserSwitcherController) {
            super(userSwitcherController);
            this.mContext = context;
            this.mResources = resources;
            this.mLayoutInflater = layoutInflater;
            this.mKeyguardUserSwitcherController = keyguardUserSwitcherController;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            float f;
            ColorMatrixColorFilter colorMatrixColorFilter;
            Drawable drawable;
            int i2;
            UserSwitcherController.UserRecord item = getItem(i);
            int i3 = 0;
            if (!(view instanceof KeyguardUserDetailItemView) || !(view.getTag() instanceof UserSwitcherController.UserRecord)) {
                view = this.mLayoutInflater.inflate(C1777R.layout.keyguard_user_switcher_item, viewGroup, false);
            }
            KeyguardUserDetailItemView keyguardUserDetailItemView = (KeyguardUserDetailItemView) view;
            keyguardUserDetailItemView.setOnClickListener(this);
            String name = getName(this.mContext, item);
            if (item.picture == null) {
                if (!item.isCurrent || !item.isGuest) {
                    drawable = UserSwitcherController.BaseUserAdapter.getIconDrawable(this.mContext, item);
                } else {
                    drawable = this.mContext.getDrawable(C1777R.C1778drawable.ic_avatar_guest_user);
                }
                if (item.isSwitchToEnabled) {
                    i2 = C1777R.color.kg_user_switcher_avatar_icon_color;
                } else {
                    i2 = C1777R.color.kg_user_switcher_restricted_avatar_icon_color;
                }
                drawable.setTint(this.mResources.getColor(i2, this.mContext.getTheme()));
                Drawable mutate = new LayerDrawable(new Drawable[]{this.mContext.getDrawable(C1777R.C1778drawable.kg_bg_avatar), drawable}).mutate();
                int resolveId = item.resolveId();
                keyguardUserDetailItemView.mName.setText(name);
                keyguardUserDetailItemView.mAvatar.setDrawableWithBadge(mutate, resolveId);
            } else {
                CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(item.picture, (int) this.mResources.getDimension(C1777R.dimen.kg_framed_avatar_size));
                if (item.isSwitchToEnabled) {
                    colorMatrixColorFilter = null;
                } else {
                    ColorMatrix colorMatrix = new ColorMatrix();
                    colorMatrix.setSaturation(0.0f);
                    colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
                }
                circleFramedDrawable.setColorFilter(colorMatrixColorFilter);
                int i4 = item.info.id;
                keyguardUserDetailItemView.mName.setText(name);
                keyguardUserDetailItemView.mAvatar.setDrawableWithBadge(circleFramedDrawable, i4);
            }
            keyguardUserDetailItemView.setActivated(item.isCurrent);
            boolean z = item.isDisabledByAdmin;
            View view2 = keyguardUserDetailItemView.mRestrictedPadlock;
            if (view2 != null) {
                if (!z) {
                    i3 = 8;
                }
                view2.setVisibility(i3);
            }
            keyguardUserDetailItemView.setEnabled(!z);
            keyguardUserDetailItemView.setEnabled(item.isSwitchToEnabled);
            if (keyguardUserDetailItemView.isEnabled()) {
                f = 1.0f;
            } else {
                f = 0.38f;
            }
            keyguardUserDetailItemView.setAlpha(f);
            keyguardUserDetailItemView.setTag(item);
            return keyguardUserDetailItemView;
        }

        public final void notifyDataSetChanged() {
            ArrayList<UserSwitcherController.UserRecord> users = super.getUsers();
            this.mUsersOrdered = new ArrayList<>(users.size());
            for (int i = 0; i < users.size(); i++) {
                UserSwitcherController.UserRecord userRecord = users.get(i);
                if (userRecord.isCurrent) {
                    this.mUsersOrdered.add(0, userRecord);
                } else {
                    this.mUsersOrdered.add(userRecord);
                }
            }
            super.notifyDataSetChanged();
        }

        /* JADX WARNING: Removed duplicated region for block: B:7:0x0024 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onClick(android.view.View r4) {
            /*
                r3 = this;
                java.lang.Object r4 = r4.getTag()
                com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord r4 = (com.android.systemui.statusbar.policy.UserSwitcherController.UserRecord) r4
                com.android.systemui.statusbar.policy.KeyguardUserSwitcherController r0 = r3.mKeyguardUserSwitcherController
                java.util.Objects.requireNonNull(r0)
                com.android.keyguard.KeyguardVisibilityHelper r1 = r0.mKeyguardVisibilityHelper
                java.util.Objects.requireNonNull(r1)
                boolean r1 = r1.mKeyguardViewVisibilityAnimating
                r2 = 1
                if (r1 != 0) goto L_0x0021
                com.android.systemui.statusbar.policy.KeyguardUserSwitcherListView r0 = r0.mListView
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mAnimating
                if (r0 == 0) goto L_0x001f
                goto L_0x0021
            L_0x001f:
                r0 = 0
                goto L_0x0022
            L_0x0021:
                r0 = r2
            L_0x0022:
                if (r0 == 0) goto L_0x0025
                return
            L_0x0025:
                com.android.systemui.statusbar.policy.KeyguardUserSwitcherController r0 = r3.mKeyguardUserSwitcherController
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mUserSwitcherOpen
                if (r0 == 0) goto L_0x0042
                boolean r0 = r4.isCurrent
                if (r0 == 0) goto L_0x003d
                boolean r0 = r4.isGuest
                if (r0 == 0) goto L_0x0037
                goto L_0x003d
            L_0x0037:
                com.android.systemui.statusbar.policy.KeyguardUserSwitcherController r3 = r3.mKeyguardUserSwitcherController
                r3.closeSwitcherIfOpenAndNotSimple(r2)
                goto L_0x0047
            L_0x003d:
                r0 = 0
                r3.onUserListItemClicked(r4, r0)
                goto L_0x0047
            L_0x0042:
                com.android.systemui.statusbar.policy.KeyguardUserSwitcherController r3 = r3.mKeyguardUserSwitcherController
                r3.setUserSwitcherOpened(r2, r2)
            L_0x0047:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.KeyguardUserSwitcherController.KeyguardUserAdapter.onClick(android.view.View):void");
        }

        public final ArrayList<UserSwitcherController.UserRecord> getUsers() {
            return this.mUsersOrdered;
        }
    }

    static {
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 360;
        ANIMATION_PROPERTIES = animationProperties;
    }

    public final boolean closeSwitcherIfOpenAndNotSimple(boolean z) {
        if (this.mUserSwitcherOpen) {
            UserSwitcherController userSwitcherController = this.mUserSwitcherController;
            Objects.requireNonNull(userSwitcherController);
            if (!userSwitcherController.mSimpleUserSwitcher) {
                setUserSwitcherOpened(false, z);
                return true;
            }
        }
        return false;
    }

    public final void onInit() {
        if (DEBUG) {
            Log.d("KeyguardUserSwitcherController", "onInit");
        }
        this.mListView = (KeyguardUserSwitcherListView) ((KeyguardUserSwitcherView) this.mView).findViewById(C1777R.C1779id.keyguard_user_switcher_list);
        ((KeyguardUserSwitcherView) this.mView).setOnTouchListener(new KeyguardUserSwitcherController$$ExternalSyntheticLambda0(this));
    }

    public final void onViewAttached() {
        if (DEBUG) {
            Log.d("KeyguardUserSwitcherController", "onViewAttached");
        }
        this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
        this.mAdapter.notifyDataSetChanged();
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        this.mScreenLifecycle.addObserver(this.mScreenObserver);
        UserSwitcherController userSwitcherController = this.mUserSwitcherController;
        Objects.requireNonNull(userSwitcherController);
        if (userSwitcherController.mSimpleUserSwitcher) {
            setUserSwitcherOpened(true, true);
            return;
        }
        ((KeyguardUserSwitcherView) this.mView).addOnLayoutChangeListener(this.mBackground);
        ((KeyguardUserSwitcherView) this.mView).setBackground(this.mBackground);
        this.mBackground.setAlpha(0);
    }

    public final void onViewDetached() {
        if (DEBUG) {
            Log.d("KeyguardUserSwitcherController", "onViewDetached");
        }
        closeSwitcherIfOpenAndNotSimple(false);
        this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        ScreenLifecycle screenLifecycle = this.mScreenLifecycle;
        C16272 r2 = this.mScreenObserver;
        Objects.requireNonNull(screenLifecycle);
        screenLifecycle.mObservers.remove(r2);
        ((KeyguardUserSwitcherView) this.mView).removeOnLayoutChangeListener(this.mBackground);
        ((KeyguardUserSwitcherView) this.mView).setBackground((Drawable) null);
        this.mBackground.setAlpha(0);
    }

    public final void setUserSwitcherOpened(boolean z, boolean z2) {
        AppearAnimationUtils appearAnimationUtils;
        KeyguardUserSwitcherListView$$ExternalSyntheticLambda0 keyguardUserSwitcherListView$$ExternalSyntheticLambda0;
        float f;
        boolean z3 = z2;
        boolean z4 = DEBUG;
        if (z4) {
            Log.d("KeyguardUserSwitcherController", String.format("setUserSwitcherOpened: %b -> %b (animate=%b)", new Object[]{Boolean.valueOf(this.mUserSwitcherOpen), Boolean.valueOf(z), Boolean.valueOf(z2)}));
        }
        this.mUserSwitcherOpen = z;
        if (z4) {
            Log.d("KeyguardUserSwitcherController", String.format("updateVisibilities: animate=%b", new Object[]{Boolean.valueOf(z2)}));
        }
        ObjectAnimator objectAnimator = this.mBgAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (this.mUserSwitcherOpen) {
            ObjectAnimator ofInt = ObjectAnimator.ofInt(this.mBackground, "alpha", new int[]{0, 255});
            this.mBgAnimator = ofInt;
            ofInt.setDuration(400);
            this.mBgAnimator.setInterpolator(Interpolators.ALPHA_IN);
            this.mBgAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    KeyguardUserSwitcherController.this.mBgAnimator = null;
                }
            });
            this.mBgAnimator.start();
        } else {
            ObjectAnimator ofInt2 = ObjectAnimator.ofInt(this.mBackground, "alpha", new int[]{255, 0});
            this.mBgAnimator = ofInt2;
            ofInt2.setDuration(400);
            this.mBgAnimator.setInterpolator(Interpolators.ALPHA_OUT);
            this.mBgAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    KeyguardUserSwitcherController.this.mBgAnimator = null;
                }
            });
            this.mBgAnimator.start();
        }
        KeyguardUserSwitcherListView keyguardUserSwitcherListView = this.mListView;
        boolean z5 = this.mUserSwitcherOpen;
        if (KeyguardUserSwitcherListView.DEBUG) {
            Objects.requireNonNull(keyguardUserSwitcherListView);
            Log.d("KeyguardUserSwitcherListView", String.format("updateVisibilities: open=%b animate=%b childCount=%d", new Object[]{Boolean.valueOf(z5), Boolean.valueOf(z2), Integer.valueOf(keyguardUserSwitcherListView.getChildCount())}));
        }
        keyguardUserSwitcherListView.mAnimating = false;
        int childCount = keyguardUserSwitcherListView.getChildCount();
        KeyguardUserDetailItemView[] keyguardUserDetailItemViewArr = new KeyguardUserDetailItemView[childCount];
        for (int i = 0; i < childCount; i++) {
            keyguardUserDetailItemViewArr[i] = (KeyguardUserDetailItemView) keyguardUserSwitcherListView.getChildAt(i);
            keyguardUserDetailItemViewArr[i].clearAnimation();
            if (i == 0) {
                keyguardUserDetailItemViewArr[i].updateVisibilities(true, z5, z3);
                keyguardUserDetailItemViewArr[i].setClickable(true);
            } else {
                keyguardUserDetailItemViewArr[i].setClickable(z5);
                if (z5) {
                    keyguardUserDetailItemViewArr[i].updateVisibilities(true, true, false);
                }
            }
        }
        if (z3 && childCount > 1) {
            keyguardUserDetailItemViewArr[0] = null;
            keyguardUserSwitcherListView.setClipChildren(false);
            keyguardUserSwitcherListView.setClipToPadding(false);
            keyguardUserSwitcherListView.mAnimating = true;
            if (z5) {
                appearAnimationUtils = keyguardUserSwitcherListView.mAppearAnimationUtils;
            } else {
                appearAnimationUtils = keyguardUserSwitcherListView.mDisappearAnimationUtils;
            }
            KeyguardUserSwitcherListView$$ExternalSyntheticLambda0 keyguardUserSwitcherListView$$ExternalSyntheticLambda02 = new KeyguardUserSwitcherListView$$ExternalSyntheticLambda0(keyguardUserSwitcherListView, z5, keyguardUserDetailItemViewArr);
            Objects.requireNonNull(appearAnimationUtils);
            AppearAnimationUtils.AppearAnimationProperties appearAnimationProperties = appearAnimationUtils.mProperties;
            appearAnimationProperties.maxDelayColIndex = -1;
            appearAnimationProperties.maxDelayRowIndex = -1;
            appearAnimationProperties.delays = new long[childCount][];
            long j = -1;
            for (int i2 = 0; i2 < childCount; i2++) {
                appearAnimationUtils.mProperties.delays[i2] = new long[1];
                long calculateDelay = appearAnimationUtils.calculateDelay(i2, 0);
                AppearAnimationUtils.AppearAnimationProperties appearAnimationProperties2 = appearAnimationUtils.mProperties;
                appearAnimationProperties2.delays[i2][0] = calculateDelay;
                if (keyguardUserDetailItemViewArr[i2] != null && calculateDelay > j) {
                    appearAnimationProperties2.maxDelayColIndex = 0;
                    appearAnimationProperties2.maxDelayRowIndex = i2;
                    j = calculateDelay;
                }
            }
            AppearAnimationUtils.AppearAnimationProperties appearAnimationProperties3 = appearAnimationUtils.mProperties;
            if (appearAnimationProperties3.maxDelayRowIndex == -1 || appearAnimationProperties3.maxDelayColIndex == -1) {
                keyguardUserSwitcherListView$$ExternalSyntheticLambda02.run();
                return;
            }
            int i3 = 0;
            while (true) {
                long[][] jArr = appearAnimationProperties3.delays;
                if (i3 < jArr.length) {
                    long j2 = jArr[i3][0];
                    if (appearAnimationProperties3.maxDelayRowIndex == i3 && appearAnimationProperties3.maxDelayColIndex == 0) {
                        keyguardUserSwitcherListView$$ExternalSyntheticLambda0 = keyguardUserSwitcherListView$$ExternalSyntheticLambda02;
                    } else {
                        keyguardUserSwitcherListView$$ExternalSyntheticLambda0 = null;
                    }
                    if (appearAnimationUtils.mRowTranslationScaler != null) {
                        int length = jArr.length;
                        f = (float) (Math.pow((double) (length - i3), 2.0d) / ((double) length));
                    } else {
                        f = 1.0f;
                    }
                    float f2 = f * appearAnimationUtils.mStartTranslation;
                    KeyguardUserDetailItemView keyguardUserDetailItemView = keyguardUserDetailItemViewArr[i3];
                    long j3 = appearAnimationUtils.mDuration;
                    boolean z6 = appearAnimationUtils.mAppearing;
                    if (!z6) {
                        f2 = -f2;
                    }
                    appearAnimationUtils.createAnimation((Object) keyguardUserDetailItemView, j2, j3, f2, z6, appearAnimationUtils.mInterpolator, (Runnable) keyguardUserSwitcherListView$$ExternalSyntheticLambda0);
                    i3++;
                    keyguardUserSwitcherListView$$ExternalSyntheticLambda02 = keyguardUserSwitcherListView$$ExternalSyntheticLambda02;
                } else {
                    return;
                }
            }
        }
    }
}
