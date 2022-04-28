package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;

public final class DozeScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public final void setDozeScreenState(int i) {
        if (i == 3) {
            i = 2;
        } else if (i == 4) {
            i = 6;
        }
        super.setDozeScreenState(i);
    }

    public DozeScreenStatePreventingAdapter(DozeMachine.Service service) {
        super(service);
    }
}
