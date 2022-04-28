package com.android.systemui;

import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import com.android.p012wm.shell.pip.PipMediaController$$ExternalSyntheticLambda1;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.Lazy;
import java.util.Optional;

public final class ActivityStarterDelegate implements ActivityStarter {
    public Lazy<Optional<StatusBar>> mActualStarterOptionalLazy;

    public final void postStartActivityDismissingKeyguard(Intent intent, int i) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda5(intent, i));
    }

    public final void startActivity(Intent intent, boolean z, boolean z2, int i) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda11(intent, z, z2, i));
    }

    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        this.mActualStarterOptionalLazy.get().ifPresent(new PipMediaController$$ExternalSyntheticLambda1(pendingIntent, 1));
    }

    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda12(onDismissAction, runnable, z));
    }

    public final void postQSRunnableDismissingKeyguard(Runnable runnable) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda0(runnable, 0));
    }

    public final void postStartActivityDismissingKeyguard(Intent intent, int i, ActivityLaunchAnimator.Controller controller) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda6(intent, i, controller));
    }

    public final void startActivity(Intent intent, boolean z) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda7(intent, z));
    }

    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda2(pendingIntent, runnable));
    }

    public ActivityStarterDelegate(Lazy<Optional<StatusBar>> lazy) {
        this.mActualStarterOptionalLazy = lazy;
    }

    public final void postStartActivityDismissingKeyguard(PendingIntent pendingIntent) {
        this.mActualStarterOptionalLazy.get().ifPresent(new QSTileHost$$ExternalSyntheticLambda1(pendingIntent, 1));
    }

    public final void startActivity(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, boolean z2) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda8(intent, z, controller, z2));
    }

    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda3(pendingIntent, runnable, view));
    }

    public final void postStartActivityDismissingKeyguard(PendingIntent pendingIntent, ActivityLaunchAnimator.Controller controller) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda1(pendingIntent, controller));
    }

    public final void startActivity(Intent intent, boolean z, boolean z2) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda10(intent, z, z2));
    }

    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityLaunchAnimator.Controller controller) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda4(pendingIntent, runnable, controller));
    }

    public final void startActivity(Intent intent, boolean z, ActivityStarter.Callback callback) {
        this.mActualStarterOptionalLazy.get().ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda9(intent, z, callback));
    }
}
