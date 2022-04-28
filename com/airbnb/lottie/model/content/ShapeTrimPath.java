package com.airbnb.lottie.model.content;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.TrimPathContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.layer.BaseLayer;

public final class ShapeTrimPath implements ContentModel {
    public final AnimatableFloatValue end;
    public final boolean hidden;
    public final String name;
    public final AnimatableFloatValue offset;
    public final AnimatableFloatValue start;
    public final Type type;

    public enum Type {
        SIMULTANEOUSLY,
        INDIVIDUALLY
    }

    public final Content toContent(LottieDrawable lottieDrawable, BaseLayer baseLayer) {
        return new TrimPathContent(baseLayer, this);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Trim Path: {start: ");
        m.append(this.start);
        m.append(", end: ");
        m.append(this.end);
        m.append(", offset: ");
        m.append(this.offset);
        m.append("}");
        return m.toString();
    }

    public ShapeTrimPath(String str, Type type2, AnimatableFloatValue animatableFloatValue, AnimatableFloatValue animatableFloatValue2, AnimatableFloatValue animatableFloatValue3, boolean z) {
        this.name = str;
        this.type = type2;
        this.start = animatableFloatValue;
        this.end = animatableFloatValue2;
        this.offset = animatableFloatValue3;
        this.hidden = z;
    }
}
