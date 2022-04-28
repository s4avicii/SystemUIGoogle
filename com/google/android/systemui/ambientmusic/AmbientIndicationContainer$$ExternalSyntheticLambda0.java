package com.google.android.systemui.ambientmusic;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AmbientIndicationContainer$$ExternalSyntheticLambda0 implements View.OnLayoutChangeListener {
    public final /* synthetic */ AmbientIndicationContainer f$0;

    public /* synthetic */ AmbientIndicationContainer$$ExternalSyntheticLambda0(AmbientIndicationContainer ambientIndicationContainer) {
        this.f$0 = ambientIndicationContainer;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        AmbientIndicationContainer ambientIndicationContainer = this.f$0;
        int i9 = AmbientIndicationContainer.$r8$clinit;
        Objects.requireNonNull(ambientIndicationContainer);
        ambientIndicationContainer.updateBottomSpacing();
    }
}
