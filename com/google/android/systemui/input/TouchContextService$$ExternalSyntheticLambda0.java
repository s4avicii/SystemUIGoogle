package com.google.android.systemui.input;

import android.os.IBinder;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TouchContextService$$ExternalSyntheticLambda0 implements IBinder.DeathRecipient {
    public final /* synthetic */ TouchContextService f$0;
    public final /* synthetic */ IBinder f$1;

    public /* synthetic */ TouchContextService$$ExternalSyntheticLambda0(TouchContextService touchContextService, IBinder iBinder) {
        this.f$0 = touchContextService;
        this.f$1 = iBinder;
    }

    public final void binderDied() {
        TouchContextService touchContextService = this.f$0;
        IBinder iBinder = this.f$1;
        Objects.requireNonNull(touchContextService);
        synchronized (touchContextService.mLock) {
            if (touchContextService.mService.asBinder() == iBinder) {
                touchContextService.mService = null;
            }
        }
    }
}
