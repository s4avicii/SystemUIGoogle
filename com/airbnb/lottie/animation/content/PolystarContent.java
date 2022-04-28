package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.appcompat.app.LayoutIncludeDetector;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class PolystarContent implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    public final boolean hidden;
    public final FloatKeyframeAnimation innerRadiusAnimation;
    public final FloatKeyframeAnimation innerRoundednessAnimation;
    public boolean isPathValid;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final FloatKeyframeAnimation outerRadiusAnimation;
    public final FloatKeyframeAnimation outerRoundednessAnimation;
    public final Path path = new Path();
    public final FloatKeyframeAnimation pointsAnimation;
    public final BaseKeyframeAnimation<?, PointF> positionAnimation;
    public final FloatKeyframeAnimation rotationAnimation;
    public LayoutIncludeDetector trimPaths = new LayoutIncludeDetector();
    public final PolystarShape.Type type;

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

    public final <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        FloatKeyframeAnimation floatKeyframeAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation2;
        if (t == LottieProperty.POLYSTAR_POINTS) {
            this.pointsAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_ROTATION) {
            this.rotationAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_INNER_RADIUS && (floatKeyframeAnimation2 = this.innerRadiusAnimation) != null) {
            floatKeyframeAnimation2.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_OUTER_RADIUS) {
            this.outerRadiusAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS && (floatKeyframeAnimation = this.innerRoundednessAnimation) != null) {
            floatKeyframeAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
            this.outerRoundednessAnimation.setValueCallback(lottieValueCallback);
        }
    }

    public final Path getPath() {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        double d;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        double d2;
        float f13;
        float f14;
        float f15;
        float f16;
        float f17;
        float f18;
        double d3;
        double d4;
        double d5;
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        int ordinal = this.type.ordinal();
        double d6 = 0.0d;
        if (ordinal == 0) {
            float floatValue = ((Float) this.pointsAnimation.getValue()).floatValue();
            FloatKeyframeAnimation floatKeyframeAnimation = this.rotationAnimation;
            if (floatKeyframeAnimation != null) {
                d6 = (double) ((Float) floatKeyframeAnimation.getValue()).floatValue();
            }
            double radians = Math.toRadians(d6 - 90.0d);
            double d7 = (double) floatValue;
            float f19 = (float) (6.283185307179586d / d7);
            float f20 = f19 / 2.0f;
            float f21 = floatValue - ((float) ((int) floatValue));
            int i = (f21 > 0.0f ? 1 : (f21 == 0.0f ? 0 : -1));
            if (i != 0) {
                radians += (double) ((1.0f - f21) * f20);
            }
            float floatValue2 = ((Float) this.outerRadiusAnimation.getValue()).floatValue();
            float floatValue3 = ((Float) this.innerRadiusAnimation.getValue()).floatValue();
            FloatKeyframeAnimation floatKeyframeAnimation2 = this.innerRoundednessAnimation;
            if (floatKeyframeAnimation2 != null) {
                f = ((Float) floatKeyframeAnimation2.getValue()).floatValue() / 100.0f;
            } else {
                f = 0.0f;
            }
            FloatKeyframeAnimation floatKeyframeAnimation3 = this.outerRoundednessAnimation;
            if (floatKeyframeAnimation3 != null) {
                f2 = ((Float) floatKeyframeAnimation3.getValue()).floatValue() / 100.0f;
            } else {
                f2 = 0.0f;
            }
            if (i != 0) {
                f7 = MotionController$$ExternalSyntheticOutline0.m7m(floatValue2, floatValue3, f21, floatValue3);
                double d8 = (double) f7;
                f4 = floatValue3;
                f3 = f;
                f6 = (float) (Math.cos(radians) * d8);
                f5 = (float) (d8 * Math.sin(radians));
                this.path.moveTo(f6, f5);
                d = radians + ((double) ((f19 * f21) / 2.0f));
            } else {
                f4 = floatValue3;
                f3 = f;
                double d9 = (double) floatValue2;
                float cos = (float) (Math.cos(radians) * d9);
                f5 = (float) (Math.sin(radians) * d9);
                this.path.moveTo(cos, f5);
                d = radians + ((double) f20);
                f6 = cos;
                f7 = 0.0f;
            }
            double ceil = Math.ceil(d7) * 2.0d;
            float f22 = f20;
            int i2 = i;
            int i3 = 0;
            boolean z = false;
            while (true) {
                double d10 = (double) i3;
                if (d10 >= ceil) {
                    break;
                }
                if (z) {
                    f8 = floatValue2;
                } else {
                    f8 = f4;
                }
                int i4 = (f7 > 0.0f ? 1 : (f7 == 0.0f ? 0 : -1));
                if (i4 == 0 || d10 != ceil - 2.0d) {
                    f9 = f19;
                    f10 = f22;
                } else {
                    f9 = f19;
                    f10 = (f19 * f21) / 2.0f;
                }
                if (i4 == 0 || d10 != ceil - 1.0d) {
                    f11 = f7;
                    f7 = f8;
                    f12 = f10;
                } else {
                    f12 = f10;
                    f11 = f7;
                }
                double d11 = (double) f7;
                double d12 = d10;
                float cos2 = (float) (Math.cos(d) * d11);
                float sin = (float) (d11 * Math.sin(d));
                if (f3 == 0.0f && f2 == 0.0f) {
                    this.path.lineTo(cos2, sin);
                    f13 = sin;
                    d2 = d;
                    f14 = f2;
                } else {
                    d2 = d;
                    float f23 = f5;
                    float f24 = f2;
                    double atan2 = (double) ((float) (Math.atan2((double) f5, (double) f6) - 1.5707963267948966d));
                    float cos3 = (float) Math.cos(atan2);
                    float sin2 = (float) Math.sin(atan2);
                    float f25 = f23;
                    f13 = sin;
                    f14 = f24;
                    double atan22 = (double) ((float) (Math.atan2((double) sin, (double) cos2) - 1.5707963267948966d));
                    float cos4 = (float) Math.cos(atan22);
                    float sin3 = (float) Math.sin(atan22);
                    if (z) {
                        f15 = f3;
                    } else {
                        f15 = f14;
                    }
                    if (z) {
                        f16 = f14;
                    } else {
                        f16 = f3;
                    }
                    if (z) {
                        f17 = f4;
                    } else {
                        f17 = floatValue2;
                    }
                    if (z) {
                        f18 = floatValue2;
                    } else {
                        f18 = f4;
                    }
                    float f26 = f17 * f15 * 0.47829f;
                    float f27 = cos3 * f26;
                    float f28 = f26 * sin2;
                    float f29 = f18 * f16 * 0.47829f;
                    float f30 = cos4 * f29;
                    float f31 = f29 * sin3;
                    if (i2 != 0) {
                        if (i3 == 0) {
                            f27 *= f21;
                            f28 *= f21;
                        } else if (d12 == ceil - 1.0d) {
                            f30 *= f21;
                            f31 *= f21;
                        }
                    }
                    this.path.cubicTo(f6 - f27, f25 - f28, cos2 + f30, f13 + f31, cos2, f13);
                }
                d = d2 + ((double) f12);
                z = !z;
                i3++;
                f6 = cos2;
                f7 = f11;
                f19 = f9;
                f5 = f13;
                f2 = f14;
            }
            PointF value = this.positionAnimation.getValue();
            this.path.offset(value.x, value.y);
            this.path.close();
        } else if (ordinal == 1) {
            int floor = (int) Math.floor((double) ((Float) this.pointsAnimation.getValue()).floatValue());
            FloatKeyframeAnimation floatKeyframeAnimation4 = this.rotationAnimation;
            if (floatKeyframeAnimation4 != null) {
                d6 = (double) ((Float) floatKeyframeAnimation4.getValue()).floatValue();
            }
            double radians2 = Math.toRadians(d6 - 90.0d);
            double d13 = (double) floor;
            float floatValue4 = ((Float) this.outerRoundednessAnimation.getValue()).floatValue() / 100.0f;
            float floatValue5 = ((Float) this.outerRadiusAnimation.getValue()).floatValue();
            double d14 = (double) floatValue5;
            float cos5 = (float) (Math.cos(radians2) * d14);
            float sin4 = (float) (Math.sin(radians2) * d14);
            this.path.moveTo(cos5, sin4);
            double d15 = (double) ((float) (6.283185307179586d / d13));
            double d16 = radians2 + d15;
            double ceil2 = Math.ceil(d13);
            int i5 = 0;
            while (((double) i5) < ceil2) {
                float cos6 = (float) (Math.cos(d16) * d14);
                double d17 = ceil2;
                float sin5 = (float) (Math.sin(d16) * d14);
                if (floatValue4 != 0.0f) {
                    d5 = d14;
                    d4 = d16;
                    double atan23 = (double) ((float) (Math.atan2((double) sin4, (double) cos5) - 1.5707963267948966d));
                    float cos7 = (float) Math.cos(atan23);
                    d3 = d15;
                    double atan24 = (double) ((float) (Math.atan2((double) sin5, (double) cos6) - 1.5707963267948966d));
                    float f32 = floatValue5 * floatValue4 * 0.25f;
                    this.path.cubicTo(cos5 - (cos7 * f32), sin4 - (((float) Math.sin(atan23)) * f32), cos6 + (((float) Math.cos(atan24)) * f32), sin5 + (f32 * ((float) Math.sin(atan24))), cos6, sin5);
                } else {
                    d4 = d16;
                    d5 = d14;
                    d3 = d15;
                    this.path.lineTo(cos6, sin5);
                }
                d16 = d4 + d3;
                i5++;
                sin4 = sin5;
                cos5 = cos6;
                ceil2 = d17;
                d14 = d5;
                d15 = d3;
            }
            PointF value2 = this.positionAnimation.getValue();
            this.path.offset(value2.x, value2.y);
            this.path.close();
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    public PolystarContent(LottieDrawable lottieDrawable2, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable2;
        Objects.requireNonNull(polystarShape);
        this.name = polystarShape.name;
        PolystarShape.Type type2 = polystarShape.type;
        this.type = type2;
        this.hidden = polystarShape.hidden;
        BaseKeyframeAnimation<Float, Float> createAnimation = polystarShape.points.createAnimation();
        this.pointsAnimation = (FloatKeyframeAnimation) createAnimation;
        BaseKeyframeAnimation<PointF, PointF> createAnimation2 = polystarShape.position.createAnimation();
        this.positionAnimation = createAnimation2;
        BaseKeyframeAnimation<Float, Float> createAnimation3 = polystarShape.rotation.createAnimation();
        this.rotationAnimation = (FloatKeyframeAnimation) createAnimation3;
        BaseKeyframeAnimation<Float, Float> createAnimation4 = polystarShape.outerRadius.createAnimation();
        this.outerRadiusAnimation = (FloatKeyframeAnimation) createAnimation4;
        BaseKeyframeAnimation<Float, Float> createAnimation5 = polystarShape.outerRoundedness.createAnimation();
        this.outerRoundednessAnimation = (FloatKeyframeAnimation) createAnimation5;
        PolystarShape.Type type3 = PolystarShape.Type.STAR;
        if (type2 == type3) {
            this.innerRadiusAnimation = (FloatKeyframeAnimation) polystarShape.innerRadius.createAnimation();
            this.innerRoundednessAnimation = (FloatKeyframeAnimation) polystarShape.innerRoundedness.createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(createAnimation);
        baseLayer.addAnimation(createAnimation2);
        baseLayer.addAnimation(createAnimation3);
        baseLayer.addAnimation(createAnimation4);
        baseLayer.addAnimation(createAnimation5);
        if (type2 == type3) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        createAnimation.addUpdateListener(this);
        createAnimation2.addUpdateListener(this);
        createAnimation3.addUpdateListener(this);
        createAnimation4.addUpdateListener(this);
        createAnimation5.addUpdateListener(this);
        if (type2 == type3) {
            this.innerRadiusAnimation.addUpdateListener(this);
            this.innerRoundednessAnimation.addUpdateListener(this);
        }
    }

    public final void resolveKeyPath(KeyPath keyPath, int i, ArrayList arrayList, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, arrayList, keyPath2, this);
    }

    public final String getName() {
        return this.name;
    }
}
