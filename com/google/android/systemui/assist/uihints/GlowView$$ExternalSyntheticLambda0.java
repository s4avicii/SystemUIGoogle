package com.google.android.systemui.assist.uihints;

import android.widget.ImageView;
import com.google.android.systemui.assist.uihints.BlurProvider;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GlowView$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ GlowView f$0;
    public final /* synthetic */ BlurProvider.BlurResult f$1;

    public /* synthetic */ GlowView$$ExternalSyntheticLambda0(GlowView glowView, BlurProvider.BlurResult blurResult) {
        this.f$0 = glowView;
        this.f$1 = blurResult;
    }

    public final void accept(Object obj) {
        GlowView glowView = this.f$0;
        BlurProvider.BlurResult blurResult = this.f$1;
        ImageView imageView = (ImageView) obj;
        int i = GlowView.$r8$clinit;
        Objects.requireNonNull(glowView);
        imageView.setImageDrawable(blurResult.drawable.getConstantState().newDrawable().mutate());
        imageView.setImageMatrix(glowView.mGlowImageMatrix);
    }
}
