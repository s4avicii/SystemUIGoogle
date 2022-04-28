package com.android.keyguard;

import android.content.res.Configuration;
import android.view.ViewGroup;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import java.util.Objects;

public final class KeyguardMessageAreaController extends ViewController<KeyguardMessageArea> {
    public final ConfigurationController mConfigurationController;
    public C05042 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onConfigChanged(Configuration configuration) {
            int statusBarHeight;
            KeyguardMessageArea keyguardMessageArea = (KeyguardMessageArea) KeyguardMessageAreaController.this.mView;
            Objects.requireNonNull(keyguardMessageArea);
            if (keyguardMessageArea.mContainer != null && keyguardMessageArea.mTopMargin != (statusBarHeight = SystemBarUtils.getStatusBarHeight(keyguardMessageArea.getContext()))) {
                keyguardMessageArea.mTopMargin = statusBarHeight;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardMessageArea.mContainer.getLayoutParams();
                marginLayoutParams.topMargin = keyguardMessageArea.mTopMargin;
                keyguardMessageArea.mContainer.setLayoutParams(marginLayoutParams);
            }
        }

        public final void onDensityOrFontScaleChanged() {
            ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).onDensityOrFontScaleChanged();
        }

        public final void onThemeChanged() {
            ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).onThemeChanged();
        }
    };
    public C05031 mInfoCallback = new KeyguardUpdateMonitorCallback() {
        public final void onFinishedGoingToSleep(int i) {
            ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).setSelected(false);
        }

        public final void onKeyguardBouncerChanged(boolean z) {
            KeyguardMessageArea keyguardMessageArea = (KeyguardMessageArea) KeyguardMessageAreaController.this.mView;
            Objects.requireNonNull(keyguardMessageArea);
            keyguardMessageArea.mBouncerVisible = z;
            ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).update();
        }

        public final void onStartedWakingUp() {
            ((KeyguardMessageArea) KeyguardMessageAreaController.this.mView).setSelected(true);
        }
    };
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    public final void setMessage(CharSequence charSequence) {
        ((KeyguardMessageArea) this.mView).setMessage(charSequence);
    }

    public static class Factory {
        public final ConfigurationController mConfigurationController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

        public Factory(KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mConfigurationController = configurationController;
        }
    }

    public final void onViewAttached() {
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor);
        ((KeyguardMessageArea) this.mView).setSelected(keyguardUpdateMonitor.mDeviceInteractive);
        ((KeyguardMessageArea) this.mView).onThemeChanged();
    }

    public final void onViewDetached() {
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
    }

    public final void setMessage(int i) {
        KeyguardMessageArea keyguardMessageArea = (KeyguardMessageArea) this.mView;
        Objects.requireNonNull(keyguardMessageArea);
        keyguardMessageArea.setMessage(i != 0 ? keyguardMessageArea.getContext().getResources().getText(i) : null);
    }

    public KeyguardMessageAreaController(KeyguardMessageArea keyguardMessageArea, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
        super(keyguardMessageArea);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
    }
}
