package com.android.systemui.keyguard;

import android.os.UserHandle;
import android.os.UserManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ KeyguardViewMediator f$0;
    public final /* synthetic */ UserManager f$1;
    public final /* synthetic */ UserHandle f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda4(KeyguardViewMediator keyguardViewMediator, UserManager userManager, UserHandle userHandle, int i) {
        this.f$0 = keyguardViewMediator;
        this.f$1 = userManager;
        this.f$2 = userHandle;
        this.f$3 = i;
    }

    public final void run() {
        KeyguardViewMediator keyguardViewMediator = this.f$0;
        UserManager userManager = this.f$1;
        UserHandle userHandle = this.f$2;
        int i = this.f$3;
        boolean z = KeyguardViewMediator.DEBUG;
        Objects.requireNonNull(keyguardViewMediator);
        for (int of : userManager.getProfileIdsWithDisabled(userHandle.getIdentifier())) {
            keyguardViewMediator.mContext.sendBroadcastAsUser(KeyguardViewMediator.USER_PRESENT_INTENT, UserHandle.of(of));
        }
        keyguardViewMediator.mLockPatternUtils.userPresent(i);
    }
}
