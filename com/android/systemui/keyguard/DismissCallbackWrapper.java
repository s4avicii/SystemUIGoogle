package com.android.systemui.keyguard;

import com.android.internal.policy.IKeyguardDismissCallback;

public final class DismissCallbackWrapper {
    public IKeyguardDismissCallback mCallback;

    public DismissCallbackWrapper(IKeyguardDismissCallback iKeyguardDismissCallback) {
        this.mCallback = iKeyguardDismissCallback;
    }
}
