package com.android.p012wm.shell.compatui.letterboxedu;

import android.view.View;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.letterboxedu.LetterboxEduAnimationController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LetterboxEduAnimationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ LetterboxEduAnimationController f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ LetterboxEduAnimationController$$ExternalSyntheticLambda0(LetterboxEduAnimationController letterboxEduAnimationController, View view, KeyguardUpdateMonitor$$ExternalSyntheticLambda8 keyguardUpdateMonitor$$ExternalSyntheticLambda8) {
        this.f$0 = letterboxEduAnimationController;
        this.f$1 = view;
        this.f$2 = keyguardUpdateMonitor$$ExternalSyntheticLambda8;
    }

    public final void run() {
        LetterboxEduAnimationController letterboxEduAnimationController = this.f$0;
        View view = this.f$1;
        Runnable runnable = this.f$2;
        Objects.requireNonNull(letterboxEduAnimationController);
        view.setAlpha(0.0f);
        letterboxEduAnimationController.mDialogAnimation = null;
        runnable.run();
    }
}
