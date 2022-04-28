package com.android.systemui.demomode;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

/* compiled from: DemoModeAvailabilityTracker.kt */
public abstract class DemoModeAvailabilityTracker {
    public final DemoModeAvailabilityTracker$allowedObserver$1 allowedObserver;
    public final Context context;
    public boolean isDemoModeAvailable;
    public boolean isInDemoMode;
    public final DemoModeAvailabilityTracker$onObserver$1 onObserver;

    public abstract void onDemoModeAvailabilityChanged();

    public abstract void onDemoModeFinished();

    public abstract void onDemoModeStarted();

    public final void startTracking() {
        ContentResolver contentResolver = this.context.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("sysui_demo_allowed"), false, this.allowedObserver);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("sysui_tuner_demo_on"), false, this.onObserver);
    }

    public DemoModeAvailabilityTracker(Context context2) {
        boolean z;
        this.context = context2;
        boolean z2 = false;
        if (Settings.Global.getInt(context2.getContentResolver(), "sysui_tuner_demo_on", 0) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.isInDemoMode = z;
        this.isDemoModeAvailable = Settings.Global.getInt(context2.getContentResolver(), "sysui_demo_allowed", 0) != 0 ? true : z2;
        this.allowedObserver = new DemoModeAvailabilityTracker$allowedObserver$1(this, new Handler(Looper.getMainLooper()));
        this.onObserver = new DemoModeAvailabilityTracker$onObserver$1(this, new Handler(Looper.getMainLooper()));
    }
}
