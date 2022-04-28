package com.airbnb.lottie.model.layer;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class Layer {
    public final LottieComposition composition;
    public final boolean hidden;
    public final List<Keyframe<Float>> inOutKeyframes;
    public final long layerId;
    public final String layerName;
    public final LayerType layerType;
    public final List<Mask> masks;
    public final MatteType matteType;
    public final long parentId;
    public final int preCompHeight;
    public final int preCompWidth;
    public final String refId;
    public final List<ContentModel> shapes;
    public final int solidColor;
    public final int solidHeight;
    public final int solidWidth;
    public final float startFrame;
    public final AnimatableTextFrame text;
    public final AnimatableTextProperties textProperties;
    public final AnimatableFloatValue timeRemapping;
    public final float timeStretch;
    public final AnimatableTransform transform;

    public enum LayerType {
        PRE_COMP,
        IMAGE,
        UNKNOWN
    }

    public enum MatteType {
        NONE,
        INVERT
    }

    public Layer(List<ContentModel> list, LottieComposition lottieComposition, String str, long j, LayerType layerType2, long j2, String str2, List<Mask> list2, AnimatableTransform animatableTransform, int i, int i2, int i3, float f, float f2, int i4, int i5, AnimatableTextFrame animatableTextFrame, AnimatableTextProperties animatableTextProperties, List<Keyframe<Float>> list3, MatteType matteType2, AnimatableFloatValue animatableFloatValue, boolean z) {
        this.shapes = list;
        this.composition = lottieComposition;
        this.layerName = str;
        this.layerId = j;
        this.layerType = layerType2;
        this.parentId = j2;
        this.refId = str2;
        this.masks = list2;
        this.transform = animatableTransform;
        this.solidWidth = i;
        this.solidHeight = i2;
        this.solidColor = i3;
        this.timeStretch = f;
        this.startFrame = f2;
        this.preCompWidth = i4;
        this.preCompHeight = i5;
        this.text = animatableTextFrame;
        this.textProperties = animatableTextProperties;
        this.inOutKeyframes = list3;
        this.matteType = matteType2;
        this.timeRemapping = animatableFloatValue;
        this.hidden = z;
    }

    public final String toString(String str) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(str);
        m.append(this.layerName);
        m.append("\n");
        LottieComposition lottieComposition = this.composition;
        long j = this.parentId;
        Objects.requireNonNull(lottieComposition);
        LongSparseArray<Layer> longSparseArray = lottieComposition.layerMap;
        Objects.requireNonNull(longSparseArray);
        Layer layer = (Layer) longSparseArray.get(j, (Long) null);
        if (layer != null) {
            m.append("\t\tParents: ");
            m.append(layer.layerName);
            LottieComposition lottieComposition2 = this.composition;
            long j2 = layer.parentId;
            Objects.requireNonNull(lottieComposition2);
            LongSparseArray<Layer> longSparseArray2 = lottieComposition2.layerMap;
            Objects.requireNonNull(longSparseArray2);
            Layer layer2 = (Layer) longSparseArray2.get(j2, (Long) null);
            while (layer2 != null) {
                m.append("->");
                m.append(layer2.layerName);
                LottieComposition lottieComposition3 = this.composition;
                long j3 = layer2.parentId;
                Objects.requireNonNull(lottieComposition3);
                LongSparseArray<Layer> longSparseArray3 = lottieComposition3.layerMap;
                Objects.requireNonNull(longSparseArray3);
                layer2 = (Layer) longSparseArray3.get(j3, (Long) null);
            }
            m.append(str);
            m.append("\n");
        }
        if (!this.masks.isEmpty()) {
            m.append(str);
            m.append("\tMasks: ");
            m.append(this.masks.size());
            m.append("\n");
        }
        if (!(this.solidWidth == 0 || this.solidHeight == 0)) {
            m.append(str);
            m.append("\tBackground: ");
            m.append(String.format(Locale.US, "%dx%d %X\n", new Object[]{Integer.valueOf(this.solidWidth), Integer.valueOf(this.solidHeight), Integer.valueOf(this.solidColor)}));
        }
        if (!this.shapes.isEmpty()) {
            m.append(str);
            m.append("\tShapes:\n");
            for (ContentModel next : this.shapes) {
                m.append(str);
                m.append("\t\t");
                m.append(next);
                m.append("\n");
            }
        }
        return m.toString();
    }

    public final String toString() {
        return toString("");
    }
}
