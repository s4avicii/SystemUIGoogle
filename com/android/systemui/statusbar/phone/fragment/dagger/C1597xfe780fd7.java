package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController_Factory_Factory;
import com.android.systemui.statusbar.phone.StatusBarMoveFromCenterAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvidePhoneStatusBarViewControllerFactory */
public final class C1597xfe780fd7 implements Factory<PhoneStatusBarViewController> {
    public final Provider<NotificationPanelViewController> notificationPanelViewControllerProvider;
    public final Provider<PhoneStatusBarViewController.Factory> phoneStatusBarViewControllerFactoryProvider;
    public final Provider<PhoneStatusBarView> phoneStatusBarViewProvider;

    public final Object get() {
        StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController;
        PhoneStatusBarViewController.Factory factory = this.phoneStatusBarViewControllerFactoryProvider.get();
        PhoneStatusBarView phoneStatusBarView = this.phoneStatusBarViewProvider.get();
        NotificationPanelViewController notificationPanelViewController = this.notificationPanelViewControllerProvider.get();
        Objects.requireNonNull(notificationPanelViewController);
        NotificationPanelViewController.C148318 r7 = notificationPanelViewController.mStatusBarViewTouchEventHandler;
        Objects.requireNonNull(factory);
        ScopedUnfoldTransitionProgressProvider orElse = factory.progressProvider.orElse((Object) null);
        SysUIUnfoldComponent orElse2 = factory.unfoldComponent.orElse((Object) null);
        if (orElse2 == null) {
            statusBarMoveFromCenterAnimationController = null;
        } else {
            statusBarMoveFromCenterAnimationController = orElse2.getStatusBarMoveFromCenterAnimationController();
        }
        return new PhoneStatusBarViewController(phoneStatusBarView, orElse, statusBarMoveFromCenterAnimationController, factory.userSwitcherController, r7, factory.configurationController);
    }

    public C1597xfe780fd7(PhoneStatusBarViewController_Factory_Factory phoneStatusBarViewController_Factory_Factory, Provider provider, Provider provider2) {
        this.phoneStatusBarViewControllerFactoryProvider = phoneStatusBarViewController_Factory_Factory;
        this.phoneStatusBarViewProvider = provider;
        this.notificationPanelViewControllerProvider = provider2;
    }
}
