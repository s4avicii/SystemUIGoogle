package com.android.systemui.classifier;

import com.android.systemui.plugins.FalsingManager;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BrightLineFalsingManager$$ExternalSyntheticLambda0 implements Consumer {
    public static final /* synthetic */ BrightLineFalsingManager$$ExternalSyntheticLambda0 INSTANCE = new BrightLineFalsingManager$$ExternalSyntheticLambda0();

    public final void accept(Object obj) {
        ((FalsingManager.FalsingTapListener) obj).onDoubleTapRequired();
    }
}
