package com.google.android.systemui.smartspace;

import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.Objects;

/* compiled from: KeyguardZenAlarmViewController.kt */
public final class KeyguardZenAlarmViewController$init$1 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ KeyguardZenAlarmViewController this$0;

    public KeyguardZenAlarmViewController$init$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController) {
        this.this$0 = keyguardZenAlarmViewController;
    }

    public final void onViewAttachedToWindow(View view) {
        KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
        Objects.requireNonNull(keyguardZenAlarmViewController);
        keyguardZenAlarmViewController.smartspaceViews.add((BcSmartspaceDataPlugin.SmartspaceView) view);
        KeyguardZenAlarmViewController keyguardZenAlarmViewController2 = this.this$0;
        Objects.requireNonNull(keyguardZenAlarmViewController2);
        if (keyguardZenAlarmViewController2.smartspaceViews.size() == 1) {
            KeyguardZenAlarmViewController keyguardZenAlarmViewController3 = this.this$0;
            keyguardZenAlarmViewController3.zenModeController.addCallback(keyguardZenAlarmViewController3.zenModeCallback);
            KeyguardZenAlarmViewController keyguardZenAlarmViewController4 = this.this$0;
            keyguardZenAlarmViewController4.nextAlarmController.addCallback(keyguardZenAlarmViewController4.nextAlarmCallback);
        }
        KeyguardZenAlarmViewController keyguardZenAlarmViewController5 = this.this$0;
        Objects.requireNonNull(keyguardZenAlarmViewController5);
        keyguardZenAlarmViewController5.updateDnd();
        keyguardZenAlarmViewController5.updateNextAlarm();
    }

    public final void onViewDetachedFromWindow(View view) {
        KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
        Objects.requireNonNull(keyguardZenAlarmViewController);
        keyguardZenAlarmViewController.smartspaceViews.remove((BcSmartspaceDataPlugin.SmartspaceView) view);
        KeyguardZenAlarmViewController keyguardZenAlarmViewController2 = this.this$0;
        Objects.requireNonNull(keyguardZenAlarmViewController2);
        if (keyguardZenAlarmViewController2.smartspaceViews.isEmpty()) {
            KeyguardZenAlarmViewController keyguardZenAlarmViewController3 = this.this$0;
            keyguardZenAlarmViewController3.zenModeController.removeCallback(keyguardZenAlarmViewController3.zenModeCallback);
            KeyguardZenAlarmViewController keyguardZenAlarmViewController4 = this.this$0;
            keyguardZenAlarmViewController4.nextAlarmController.removeCallback(keyguardZenAlarmViewController4.nextAlarmCallback);
        }
    }
}
