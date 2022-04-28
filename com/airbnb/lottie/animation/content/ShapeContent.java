package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import androidx.appcompat.app.LayoutIncludeDetector;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ShapeKeyframeAnimation;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.model.content.ShapePath;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ShapeContent implements PathContent, BaseKeyframeAnimation.AnimationListener {
    public final boolean hidden;
    public boolean isPathValid;
    public final LottieDrawable lottieDrawable;
    public final Path path = new Path();
    public final ShapeKeyframeAnimation shapeAnimation;
    public LayoutIncludeDetector trimPaths = new LayoutIncludeDetector();

    public final void onValueChanged() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    public final void setContents(List<Content> list, List<Content> list2) {
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i < arrayList.size()) {
                Content content = (Content) arrayList.get(i);
                if (content instanceof TrimPathContent) {
                    TrimPathContent trimPathContent = (TrimPathContent) content;
                    Objects.requireNonNull(trimPathContent);
                    if (trimPathContent.type == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                        LayoutIncludeDetector layoutIncludeDetector = this.trimPaths;
                        Objects.requireNonNull(layoutIncludeDetector);
                        ((List) layoutIncludeDetector.mXmlParserStack).add(trimPathContent);
                        trimPathContent.addListener(this);
                    }
                }
                i++;
            } else {
                return;
            }
        }
    }

    public final Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        this.path.set((Path) this.shapeAnimation.getValue());
        this.path.setFillType(Path.FillType.EVEN_ODD);
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    public ShapeContent(LottieDrawable lottieDrawable2, BaseLayer baseLayer, ShapePath shapePath) {
        Objects.requireNonNull(shapePath);
        this.hidden = shapePath.hidden;
        this.lottieDrawable = lottieDrawable2;
        BaseKeyframeAnimation<ShapeData, Path> createAnimation = shapePath.shapePath.createAnimation();
        this.shapeAnimation = (ShapeKeyframeAnimation) createAnimation;
        baseLayer.addAnimation(createAnimation);
        createAnimation.addUpdateListener(this);
    }
}
