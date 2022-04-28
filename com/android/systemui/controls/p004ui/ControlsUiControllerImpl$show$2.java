package com.android.systemui.controls.p004ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.controls.management.ControlsProviderSelectorActivity;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$show$2 */
/* compiled from: ControlsUiControllerImpl.kt */
public /* synthetic */ class ControlsUiControllerImpl$show$2 extends FunctionReferenceImpl implements Function1<List<? extends SelectionItem>, Unit> {
    public ControlsUiControllerImpl$show$2(ControlsUiController controlsUiController) {
        super(1, controlsUiController, ControlsUiControllerImpl.class, "showInitialSetupView", "showInitialSetupView(Ljava/util/List;)V", 0);
    }

    public final Object invoke(Object obj) {
        List list = (List) obj;
        ControlsUiControllerImpl controlsUiControllerImpl = (ControlsUiControllerImpl) this.receiver;
        ComponentName componentName = ControlsUiControllerImpl.EMPTY_COMPONENT;
        Objects.requireNonNull(controlsUiControllerImpl);
        Context context = controlsUiControllerImpl.activityContext;
        Runnable runnable = null;
        if (context == null) {
            context = null;
        }
        Intent intent = new Intent(context, ControlsProviderSelectorActivity.class);
        intent.putExtra("back_should_exit", true);
        controlsUiControllerImpl.startActivity(intent);
        Runnable runnable2 = controlsUiControllerImpl.onDismiss;
        if (runnable2 != null) {
            runnable = runnable2;
        }
        runnable.run();
        return Unit.INSTANCE;
    }
}
