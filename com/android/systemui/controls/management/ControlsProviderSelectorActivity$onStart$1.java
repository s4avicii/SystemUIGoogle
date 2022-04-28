package com.android.systemui.controls.management;

import android.content.ComponentName;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: ControlsProviderSelectorActivity.kt */
public /* synthetic */ class ControlsProviderSelectorActivity$onStart$1 extends FunctionReferenceImpl implements Function1<ComponentName, Unit> {
    public ControlsProviderSelectorActivity$onStart$1(Object obj) {
        super(1, obj, ControlsProviderSelectorActivity.class, "launchFavoritingActivity", "launchFavoritingActivity(Landroid/content/ComponentName;)V", 0);
    }

    public final Object invoke(Object obj) {
        ControlsProviderSelectorActivity controlsProviderSelectorActivity = (ControlsProviderSelectorActivity) this.receiver;
        Objects.requireNonNull(controlsProviderSelectorActivity);
        controlsProviderSelectorActivity.executor.execute(new ControlsProviderSelectorActivity$launchFavoritingActivity$1((ComponentName) obj, controlsProviderSelectorActivity));
        return Unit.INSTANCE;
    }
}
