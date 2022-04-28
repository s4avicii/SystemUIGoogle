package com.android.systemui.statusbar.phone;

import android.content.Intent;
import android.os.UserManager;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.DejankUtils;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.UserSwitcherActivity;
import com.android.systemui.util.ViewController;

public final class MultiUserSwitchController extends ViewController<MultiUserSwitch> {
    public final ActivityStarter mActivityStarter;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final C14621 mOnClickListener = new View.OnClickListener() {
        public final void onClick(View view) {
            if (!MultiUserSwitchController.this.mFalsingManager.isFalseTap(1)) {
                if (MultiUserSwitchController.this.mFeatureFlags.isEnabled(Flags.FULL_SCREEN_USER_SWITCHER)) {
                    Intent intent = new Intent(view.getContext(), UserSwitcherActivity.class);
                    intent.addFlags(335544320);
                    MultiUserSwitchController.this.mActivityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) ActivityLaunchAnimator.Controller.fromView(view, (Integer) null), true);
                    return;
                }
                MultiUserSwitchController.this.mUserSwitchDialogController.showDialog(view);
            }
        }
    };
    public C14632 mUserListener;
    public final UserManager mUserManager;
    public final UserSwitchDialogController mUserSwitchDialogController;
    public final UserSwitcherController mUserSwitcherController;

    public static class Factory {
        public final ActivityStarter mActivityStarter;
        public final FalsingManager mFalsingManager;
        public final FeatureFlags mFeatureFlags;
        public final UserManager mUserManager;
        public final UserSwitchDialogController mUserSwitchDialogController;
        public final UserSwitcherController mUserSwitcherController;

        public Factory(UserManager userManager, UserSwitcherController userSwitcherController, FalsingManager falsingManager, UserSwitchDialogController userSwitchDialogController, FeatureFlags featureFlags, ActivityStarter activityStarter) {
            this.mUserManager = userManager;
            this.mUserSwitcherController = userSwitcherController;
            this.mFalsingManager = falsingManager;
            this.mUserSwitchDialogController = userSwitchDialogController;
            this.mActivityStarter = activityStarter;
            this.mFeatureFlags = featureFlags;
        }
    }

    public final String getCurrentUser() {
        if (((Boolean) DejankUtils.whitelistIpcs(new MultiUserSwitchController$$ExternalSyntheticLambda0(this))).booleanValue()) {
            return this.mUserSwitcherController.getCurrentUserName();
        }
        return null;
    }

    public final void onInit() {
        UserSwitcherController userSwitcherController;
        if (this.mUserManager.isUserSwitcherEnabled() && this.mUserListener == null && (userSwitcherController = this.mUserSwitcherController) != null) {
            this.mUserListener = new UserSwitcherController.BaseUserAdapter(userSwitcherController) {
                public final View getView(int i, View view, ViewGroup viewGroup) {
                    return null;
                }

                public final void notifyDataSetChanged() {
                    MultiUserSwitchController multiUserSwitchController = MultiUserSwitchController.this;
                    ((MultiUserSwitch) multiUserSwitchController.mView).refreshContentDescription(multiUserSwitchController.getCurrentUser());
                }
            };
            ((MultiUserSwitch) this.mView).refreshContentDescription(getCurrentUser());
        }
        ((MultiUserSwitch) this.mView).refreshContentDescription(getCurrentUser());
    }

    public final void onViewAttached() {
        ((MultiUserSwitch) this.mView).setOnClickListener(this.mOnClickListener);
    }

    public final void onViewDetached() {
        ((MultiUserSwitch) this.mView).setOnClickListener((View.OnClickListener) null);
    }

    public MultiUserSwitchController(MultiUserSwitch multiUserSwitch, UserManager userManager, UserSwitcherController userSwitcherController, FalsingManager falsingManager, UserSwitchDialogController userSwitchDialogController, FeatureFlags featureFlags, ActivityStarter activityStarter) {
        super(multiUserSwitch);
        this.mUserManager = userManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mFalsingManager = falsingManager;
        this.mUserSwitchDialogController = userSwitchDialogController;
        this.mFeatureFlags = featureFlags;
        this.mActivityStarter = activityStarter;
    }
}
