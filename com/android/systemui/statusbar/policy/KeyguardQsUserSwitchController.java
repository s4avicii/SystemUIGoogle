package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardConstants;
import com.android.keyguard.KeyguardVisibilityHelper;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0;
import com.android.systemui.communal.CommunalStateController;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.UserAvatarView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.ViewController;

public final class KeyguardQsUserSwitchController extends ViewController<FrameLayout> {
    public static final AnimationProperties ANIMATION_PROPERTIES;
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public C16233 mAdapter;
    public int mBarState;
    public final ConfigurationController mConfigurationController;
    public C16222 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onUiModeChanged() {
            KeyguardQsUserSwitchController.this.updateView(true);
        }
    };
    public final Context mContext;
    public UserSwitcherController.UserRecord mCurrentUser;
    public final C16255 mDataSetObserver = new DataSetObserver() {
        public final void onChanged() {
            KeyguardQsUserSwitchController.this.updateView(false);
        }
    };
    public final FalsingManager mFalsingManager;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardVisibilityHelper mKeyguardVisibilityHelper;
    public Resources mResources;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final C16211 mStatusBarStateListener = new StatusBarStateController.StateListener() {
        public final void onStateChanged(int i) {
            if (KeyguardQsUserSwitchController.DEBUG) {
                Log.d("KeyguardQsUserSwitchController", String.format("onStateChanged: newState=%d", new Object[]{Integer.valueOf(i)}));
            }
            boolean goingToFullShade = KeyguardQsUserSwitchController.this.mStatusBarStateController.goingToFullShade();
            boolean isKeyguardFadingAway = KeyguardQsUserSwitchController.this.mKeyguardStateController.isKeyguardFadingAway();
            KeyguardQsUserSwitchController keyguardQsUserSwitchController = KeyguardQsUserSwitchController.this;
            int i2 = keyguardQsUserSwitchController.mBarState;
            keyguardQsUserSwitchController.mBarState = i;
            keyguardQsUserSwitchController.mKeyguardVisibilityHelper.setViewVisibility(i, isKeyguardFadingAway, goingToFullShade, i2);
        }
    };
    public final UiEventLogger mUiEventLogger;
    public UserAvatarView mUserAvatarView;
    public final UserSwitchDialogController mUserSwitchDialogController;
    public final UserSwitcherController mUserSwitcherController;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardQsUserSwitchController(FrameLayout frameLayout, Context context, Resources resources, UserSwitcherController userSwitcherController, CommunalStateController communalStateController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, ConfigurationController configurationController, SysuiStatusBarStateController sysuiStatusBarStateController, ScreenOffAnimationController screenOffAnimationController, UserSwitchDialogController userSwitchDialogController, UiEventLogger uiEventLogger) {
        super(frameLayout);
        if (DEBUG) {
            Log.d("KeyguardQsUserSwitchController", "New KeyguardQsUserSwitchController");
        }
        this.mContext = context;
        this.mResources = resources;
        this.mUserSwitcherController = userSwitcherController;
        KeyguardStateController keyguardStateController2 = keyguardStateController;
        this.mKeyguardStateController = keyguardStateController2;
        this.mFalsingManager = falsingManager;
        this.mConfigurationController = configurationController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mKeyguardVisibilityHelper = new KeyguardVisibilityHelper(frameLayout, communalStateController, keyguardStateController2, screenOffAnimationController, false, false);
        this.mUserSwitchDialogController = userSwitchDialogController;
        this.mUiEventLogger = uiEventLogger;
    }

    static {
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 360;
        ANIMATION_PROPERTIES = animationProperties;
    }

    public final void onInit() {
        if (DEBUG) {
            Log.d("KeyguardQsUserSwitchController", "onInit");
        }
        this.mUserAvatarView = (UserAvatarView) ((FrameLayout) this.mView).findViewById(C1777R.C1779id.kg_multi_user_avatar);
        this.mAdapter = new UserSwitcherController.BaseUserAdapter(this.mUserSwitcherController) {
            public final View getView(int i, View view, ViewGroup viewGroup) {
                return null;
            }
        };
        this.mUserAvatarView.setOnClickListener(new LayoutPreference$$ExternalSyntheticLambda0(this, 5));
        this.mUserAvatarView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, KeyguardQsUserSwitchController.this.mContext.getString(C1777R.string.accessibility_quick_settings_choose_user_action)));
            }
        });
    }

    public final void onViewAttached() {
        if (DEBUG) {
            Log.d("KeyguardQsUserSwitchController", "onViewAttached");
        }
        this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
        this.mDataSetObserver.onChanged();
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        updateView(true);
    }

    public final void onViewDetached() {
        if (DEBUG) {
            Log.d("KeyguardQsUserSwitchController", "onViewDetached");
        }
        this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }

    public final void updateView(boolean z) {
        boolean z2;
        String str;
        int i;
        Drawable drawable;
        Drawable drawable2;
        UserInfo userInfo;
        UserSwitcherController.UserRecord userRecord = this.mCurrentUser;
        this.mCurrentUser = null;
        int i2 = 0;
        while (true) {
            if (i2 < this.mAdapter.getCount()) {
                UserSwitcherController.UserRecord item = this.mAdapter.getItem(i2);
                if (item.isCurrent) {
                    this.mCurrentUser = item;
                    z2 = !item.equals(userRecord);
                    break;
                }
                i2++;
            } else if (this.mCurrentUser != null || userRecord == null) {
                z2 = false;
            } else {
                z2 = true;
            }
        }
        if (z2 || z) {
            UserSwitcherController.UserRecord userRecord2 = this.mCurrentUser;
            if (userRecord2 == null || (userInfo = userRecord2.info) == null || TextUtils.isEmpty(userInfo.name)) {
                str = this.mContext.getString(C1777R.string.accessibility_multi_user_switch_switcher);
            } else {
                str = this.mContext.getString(C1777R.string.accessibility_quick_settings_user, new Object[]{this.mCurrentUser.info.name});
            }
            if (!TextUtils.equals(this.mUserAvatarView.getContentDescription(), str)) {
                this.mUserAvatarView.setContentDescription(str);
            }
            UserSwitcherController.UserRecord userRecord3 = this.mCurrentUser;
            if (userRecord3 != null) {
                i = userRecord3.resolveId();
            } else {
                i = -10000;
            }
            UserAvatarView userAvatarView = this.mUserAvatarView;
            UserSwitcherController.UserRecord userRecord4 = this.mCurrentUser;
            if (userRecord4 == null || userRecord4.picture == null) {
                if (userRecord4 == null || !userRecord4.isGuest) {
                    drawable2 = this.mContext.getDrawable(C1777R.C1778drawable.ic_avatar_user);
                } else {
                    drawable2 = this.mContext.getDrawable(C1777R.C1778drawable.ic_avatar_guest_user);
                }
                drawable = drawable2;
                drawable.setTint(this.mResources.getColor(C1777R.color.kg_user_switcher_avatar_icon_color, this.mContext.getTheme()));
            } else {
                drawable = new CircleFramedDrawable(this.mCurrentUser.picture, (int) this.mResources.getDimension(C1777R.dimen.kg_framed_avatar_size));
            }
            userAvatarView.setDrawableWithBadge(new LayerDrawable(new Drawable[]{this.mContext.getDrawable(C1777R.C1778drawable.kg_bg_avatar), drawable}).mutate(), i);
        }
    }
}
