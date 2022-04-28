package com.google.android.systemui;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import androidx.coordinatorlayout.R$styleable;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.dagger.GlobalRootComponent;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import com.google.android.systemui.dagger.DaggerSysUIGoogleGlobalRootComponent;
import com.google.android.systemui.dagger.SysUIGoogleSysUIComponent;
import com.google.android.systemui.gesture.BackGestureTfClassifierProviderGoogle;
import com.google.android.systemui.screenshot.ScreenshotNotificationSmartActionsProviderGoogle;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class SystemUIGoogleFactory extends SystemUIFactory {
    public final R$styleable createBackGestureTfClassifierProvider(AssetManager assetManager, String str) {
        return new BackGestureTfClassifierProviderGoogle(assetManager, str);
    }

    public final ScreenshotNotificationSmartActionsProvider createScreenshotNotificationSmartActionsProvider(Context context, Executor executor, Handler handler) {
        return new ScreenshotNotificationSmartActionsProviderGoogle(context, executor, handler);
    }

    public GlobalRootComponent buildGlobalRootComponent(Context context) {
        DaggerSysUIGoogleGlobalRootComponent.Builder builder = (DaggerSysUIGoogleGlobalRootComponent.Builder) DaggerSysUIGoogleGlobalRootComponent.builder();
        Objects.requireNonNull(builder);
        Objects.requireNonNull(context);
        builder.context = context;
        return builder.build();
    }

    public final void init(Context context, boolean z) throws ExecutionException, InterruptedException {
        super.init(context, z);
        if (this.mInitializeComponents) {
            ((SysUIGoogleSysUIComponent) this.mSysUIComponent).createKeyguardSmartspaceController();
        }
    }
}
