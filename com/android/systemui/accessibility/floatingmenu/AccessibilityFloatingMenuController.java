package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import java.util.Objects;
import java.util.Optional;

public final class AccessibilityFloatingMenuController implements AccessibilityButtonModeObserver.ModeChangedListener, AccessibilityButtonTargetsObserver.TargetsChangedListener {
    public final AccessibilityButtonModeObserver mAccessibilityButtonModeObserver;
    public final AccessibilityButtonTargetsObserver mAccessibilityButtonTargetsObserver;
    public int mBtnMode;
    public String mBtnTargets;
    public Context mContext;
    @VisibleForTesting
    public IAccessibilityFloatingMenu mFloatingMenu;
    public boolean mIsAccessibilityManagerServiceReady;
    public boolean mIsKeyguardVisible;
    @VisibleForTesting
    public final KeyguardUpdateMonitorCallback mKeyguardCallback = new KeyguardUpdateMonitorCallback() {
        public final void onKeyguardVisibilityChanged(boolean z) {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController.mIsKeyguardVisible = z;
            if (accessibilityFloatingMenuController.mIsAccessibilityManagerServiceReady) {
                accessibilityFloatingMenuController.handleFloatingMenuVisibility(z, accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets);
            }
        }

        public final void onUserSwitchComplete(int i) {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController.mContext = accessibilityFloatingMenuController.mContext.createContextAsUser(UserHandle.of(i), 0);
            AccessibilityFloatingMenuController accessibilityFloatingMenuController2 = AccessibilityFloatingMenuController.this;
            AccessibilityButtonModeObserver accessibilityButtonModeObserver = accessibilityFloatingMenuController2.mAccessibilityButtonModeObserver;
            Objects.requireNonNull(accessibilityButtonModeObserver);
            accessibilityFloatingMenuController2.mBtnMode = AccessibilityButtonModeObserver.parseAccessibilityButtonMode(Settings.Secure.getStringForUser(accessibilityButtonModeObserver.mContentResolver, accessibilityButtonModeObserver.mKey, -2));
            AccessibilityFloatingMenuController accessibilityFloatingMenuController3 = AccessibilityFloatingMenuController.this;
            AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver = accessibilityFloatingMenuController3.mAccessibilityButtonTargetsObserver;
            Objects.requireNonNull(accessibilityButtonTargetsObserver);
            accessibilityFloatingMenuController3.mBtnTargets = Settings.Secure.getStringForUser(accessibilityButtonTargetsObserver.mContentResolver, accessibilityButtonTargetsObserver.mKey, -2);
            AccessibilityFloatingMenuController accessibilityFloatingMenuController4 = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController4.handleFloatingMenuVisibility(accessibilityFloatingMenuController4.mIsKeyguardVisible, accessibilityFloatingMenuController4.mBtnMode, accessibilityFloatingMenuController4.mBtnTargets);
        }

        public final void onUserSwitching(int i) {
            AccessibilityFloatingMenuController.this.destroyFloatingMenu();
        }

        public final void onUserUnlocked() {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController.mIsAccessibilityManagerServiceReady = true;
            accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mIsKeyguardVisible, accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets);
        }
    };
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    public final void destroyFloatingMenu() {
        IAccessibilityFloatingMenu iAccessibilityFloatingMenu = this.mFloatingMenu;
        if (iAccessibilityFloatingMenu != null) {
            AccessibilityFloatingMenu accessibilityFloatingMenu = (AccessibilityFloatingMenu) iAccessibilityFloatingMenu;
            AccessibilityFloatingMenuView accessibilityFloatingMenuView = accessibilityFloatingMenu.mMenuView;
            Objects.requireNonNull(accessibilityFloatingMenuView);
            if (accessibilityFloatingMenuView.mIsShowing) {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = accessibilityFloatingMenu.mMenuView;
                Objects.requireNonNull(accessibilityFloatingMenuView2);
                if (accessibilityFloatingMenuView2.mIsShowing) {
                    accessibilityFloatingMenuView2.mIsShowing = false;
                    accessibilityFloatingMenuView2.mWindowManager.removeView(accessibilityFloatingMenuView2);
                    accessibilityFloatingMenuView2.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) null);
                    accessibilityFloatingMenuView2.setSystemGestureExclusion();
                }
                AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = accessibilityFloatingMenu.mMenuView;
                Objects.requireNonNull(accessibilityFloatingMenuView3);
                accessibilityFloatingMenuView3.mOnDragEndListener = Optional.ofNullable((Object) null);
                accessibilityFloatingMenu.mMigrationTooltipView.hide();
                accessibilityFloatingMenu.mDockTooltipView.hide();
                accessibilityFloatingMenu.mContext.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mContentObserver);
                accessibilityFloatingMenu.mContext.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mSizeContentObserver);
                accessibilityFloatingMenu.mContext.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mFadeOutContentObserver);
                accessibilityFloatingMenu.mContext.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mEnabledA11yServicesContentObserver);
            }
            this.mFloatingMenu = null;
        }
    }

    public final void handleFloatingMenuVisibility(boolean z, int i, String str) {
        boolean z2;
        boolean z3;
        boolean z4;
        if (z) {
            destroyFloatingMenu();
            return;
        }
        if (i != 1 || TextUtils.isEmpty(str)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            if (this.mFloatingMenu == null) {
                this.mFloatingMenu = new AccessibilityFloatingMenu(this.mContext);
            }
            AccessibilityFloatingMenu accessibilityFloatingMenu = (AccessibilityFloatingMenu) this.mFloatingMenu;
            Objects.requireNonNull(accessibilityFloatingMenu);
            AccessibilityFloatingMenuView accessibilityFloatingMenuView = accessibilityFloatingMenu.mMenuView;
            Objects.requireNonNull(accessibilityFloatingMenuView);
            if (!accessibilityFloatingMenuView.mIsShowing) {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = accessibilityFloatingMenu.mMenuView;
                Objects.requireNonNull(accessibilityFloatingMenuView2);
                if (!accessibilityFloatingMenuView2.mIsShowing) {
                    accessibilityFloatingMenuView2.mIsShowing = true;
                    accessibilityFloatingMenuView2.mWindowManager.addView(accessibilityFloatingMenuView2, accessibilityFloatingMenuView2.mCurrentLayoutParams);
                    accessibilityFloatingMenuView2.setOnApplyWindowInsetsListener(new AccessibilityFloatingMenuView$$ExternalSyntheticLambda2(accessibilityFloatingMenuView2));
                    accessibilityFloatingMenuView2.setSystemGestureExclusion();
                }
                accessibilityFloatingMenu.mMenuView.onTargetsChanged(AccessibilityTargetHelper.getTargets(accessibilityFloatingMenu.mContext, 0));
                AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = accessibilityFloatingMenu.mMenuView;
                if (Settings.Secure.getInt(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_fade_enabled", 1) == 1) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                accessibilityFloatingMenuView3.updateOpacityWith(z3, Settings.Secure.getFloat(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_opacity", 0.55f));
                accessibilityFloatingMenu.mMenuView.setSizeType(Settings.Secure.getInt(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_size", 0));
                accessibilityFloatingMenu.mMenuView.setShapeType(Settings.Secure.getInt(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_icon_type", 0));
                AccessibilityFloatingMenuView accessibilityFloatingMenuView4 = accessibilityFloatingMenu.mMenuView;
                AccessibilityFloatingMenu$$ExternalSyntheticLambda0 accessibilityFloatingMenu$$ExternalSyntheticLambda0 = new AccessibilityFloatingMenu$$ExternalSyntheticLambda0(accessibilityFloatingMenu);
                Objects.requireNonNull(accessibilityFloatingMenuView4);
                accessibilityFloatingMenuView4.mOnDragEndListener = Optional.ofNullable(accessibilityFloatingMenu$$ExternalSyntheticLambda0);
                if (Settings.Secure.getInt(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_migration_tooltip_prompt", 0) == 1) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (z4) {
                    MigrationTooltipView migrationTooltipView = accessibilityFloatingMenu.mMigrationTooltipView;
                    Objects.requireNonNull(migrationTooltipView);
                    if (!migrationTooltipView.mIsShowing) {
                        migrationTooltipView.mIsShowing = true;
                        migrationTooltipView.updateTooltipView();
                        migrationTooltipView.mWindowManager.addView(migrationTooltipView, migrationTooltipView.mCurrentLayoutParams);
                    }
                    Settings.Secure.putInt(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_migration_tooltip_prompt", 0);
                }
                accessibilityFloatingMenu.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_button_targets"), false, accessibilityFloatingMenu.mContentObserver, -2);
                accessibilityFloatingMenu.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_floating_menu_size"), false, accessibilityFloatingMenu.mSizeContentObserver, -2);
                accessibilityFloatingMenu.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_floating_menu_fade_enabled"), false, accessibilityFloatingMenu.mFadeOutContentObserver, -2);
                accessibilityFloatingMenu.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_floating_menu_opacity"), false, accessibilityFloatingMenu.mFadeOutContentObserver, -2);
                accessibilityFloatingMenu.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("enabled_accessibility_services"), false, accessibilityFloatingMenu.mEnabledA11yServicesContentObserver, -2);
                return;
            }
            return;
        }
        destroyFloatingMenu();
    }

    public final void onAccessibilityButtonModeChanged(int i) {
        this.mBtnMode = i;
        handleFloatingMenuVisibility(this.mIsKeyguardVisible, i, this.mBtnTargets);
    }

    public final void onAccessibilityButtonTargetsChanged(String str) {
        this.mBtnTargets = str;
        handleFloatingMenuVisibility(this.mIsKeyguardVisible, this.mBtnMode, str);
    }

    public AccessibilityFloatingMenuController(Context context, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, AccessibilityButtonModeObserver accessibilityButtonModeObserver, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mAccessibilityButtonTargetsObserver = accessibilityButtonTargetsObserver;
        this.mAccessibilityButtonModeObserver = accessibilityButtonModeObserver;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mIsKeyguardVisible = false;
        this.mIsAccessibilityManagerServiceReady = false;
    }
}
