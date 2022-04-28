package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.actions.Action;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: SetupWizard.kt */
public final class SetupWizard$actionListener$1 implements Action.Listener {
    public final /* synthetic */ SetupWizard this$0;

    public SetupWizard$actionListener$1(SetupWizard setupWizard) {
        this.this$0 = setupWizard;
    }

    public final void onActionAvailabilityChanged(Action action) {
        T t;
        boolean z;
        SetupWizard setupWizard = this.this$0;
        Iterator<T> it = setupWizard.exceptions.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            Action action2 = (Action) t;
            Objects.requireNonNull(action2);
            if (action2.isAvailable) {
                break;
            }
        }
        if (t != null) {
            z = true;
        } else {
            z = false;
        }
        setupWizard.exceptionActive = z;
        this.this$0.updateBlocking();
    }
}
