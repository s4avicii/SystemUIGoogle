package com.android.systemui.dagger;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dagger.DependencyProvider_ProvideAccessibilityFloatingMenuControllerFactory */
public final class C0774xb22f7179 implements Factory<AccessibilityFloatingMenuController> {
    public final Provider<AccessibilityButtonModeObserver> accessibilityButtonModeObserverProvider;
    public final Provider<AccessibilityButtonTargetsObserver> accessibilityButtonTargetsObserverProvider;
    public final Provider<Context> contextProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final DependencyProvider module;

    public final Object get() {
        Objects.requireNonNull(this.module);
        return new AccessibilityFloatingMenuController(this.contextProvider.get(), this.accessibilityButtonTargetsObserverProvider.get(), this.accessibilityButtonModeObserverProvider.get(), this.keyguardUpdateMonitorProvider.get());
    }

    public C0774xb22f7179(DependencyProvider dependencyProvider, Provider<Context> provider, Provider<AccessibilityButtonTargetsObserver> provider2, Provider<AccessibilityButtonModeObserver> provider3, Provider<KeyguardUpdateMonitor> provider4) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
        this.accessibilityButtonTargetsObserverProvider = provider2;
        this.accessibilityButtonModeObserverProvider = provider3;
        this.keyguardUpdateMonitorProvider = provider4;
    }
}
