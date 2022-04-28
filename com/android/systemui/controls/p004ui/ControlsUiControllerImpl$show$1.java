package com.android.systemui.controls.p004ui;

import android.content.ComponentName;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$show$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public /* synthetic */ class ControlsUiControllerImpl$show$1 extends FunctionReferenceImpl implements Function1<List<? extends SelectionItem>, Unit> {
    public ControlsUiControllerImpl$show$1(ControlsUiController controlsUiController) {
        super(1, controlsUiController, ControlsUiControllerImpl.class, "showSeedingView", "showSeedingView(Ljava/util/List;)V", 0);
    }

    public final Object invoke(Object obj) {
        List list = (List) obj;
        ControlsUiControllerImpl controlsUiControllerImpl = (ControlsUiControllerImpl) this.receiver;
        ComponentName componentName = ControlsUiControllerImpl.EMPTY_COMPONENT;
        Objects.requireNonNull(controlsUiControllerImpl);
        LayoutInflater from = LayoutInflater.from(controlsUiControllerImpl.context);
        ViewGroup viewGroup = controlsUiControllerImpl.parent;
        ViewGroup viewGroup2 = null;
        if (viewGroup == null) {
            viewGroup = null;
        }
        from.inflate(C1777R.layout.controls_no_favorites, viewGroup, true);
        ViewGroup viewGroup3 = controlsUiControllerImpl.parent;
        if (viewGroup3 != null) {
            viewGroup2 = viewGroup3;
        }
        ((TextView) viewGroup2.requireViewById(C1777R.C1779id.controls_subtitle)).setText(controlsUiControllerImpl.context.getResources().getString(C1777R.string.controls_seeding_in_progress));
        return Unit.INSTANCE;
    }
}
