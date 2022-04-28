package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;

public final class DozeSuspendScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public final void setDozeScreenState(int i) {
        if (i == 4) {
            i = 3;
        }
        super.setDozeScreenState(i);
    }

    public DozeSuspendScreenStatePreventingAdapter(DozeMachine.Service service) {
        super(service);
    }
}
