package com.android.systemui.dreams.touch.dagger;

import android.animation.ValueAnimator;
import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;
import dagger.internal.SetFactory;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Provider;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BouncerSwipeModule$$ExternalSyntheticLambda0 implements BouncerSwipeTouchHandler.ValueAnimatorCreator {
    public static final /* synthetic */ BouncerSwipeModule$$ExternalSyntheticLambda0 INSTANCE = new BouncerSwipeModule$$ExternalSyntheticLambda0();

    public ValueAnimator create(float f, float f2) {
        return ValueAnimator.ofFloat(new float[]{f, f2});
    }

    /* renamed from: m */
    public static SetFactory m56m(ArrayList arrayList, Provider provider, ArrayList arrayList2, List list) {
        arrayList.add(provider);
        return new SetFactory(arrayList2, list);
    }
}
