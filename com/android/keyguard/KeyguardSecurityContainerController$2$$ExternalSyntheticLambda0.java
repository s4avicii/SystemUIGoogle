package com.android.keyguard;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0 implements Runnable {
    public static final /* synthetic */ KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0 INSTANCE = new KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0();

    public final void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException unused) {
        }
        System.gc();
        System.runFinalization();
        System.gc();
    }
}
