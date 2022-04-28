package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.Choreographer;
import android.view.View;
import android.widget.ImageView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.LayerParser;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class LottieDrawable extends Drawable implements Drawable.Callback, Animatable {
    public int alpha;
    public final LottieValueAnimator animator;
    public LottieComposition composition;
    public CompositionLayer compositionLayer;
    public boolean enableMergePaths;
    public FontAssetManager fontAssetManager;
    public ImageAssetDelegate imageAssetDelegate;
    public ImageAssetManager imageAssetManager;
    public String imageAssetsFolder;
    public boolean isApplyingOpacityToLayersEnabled;
    public boolean isDirty;
    public boolean isExtraScaleEnabled;
    public final ArrayList<LazyCompositionTask> lazyCompositionTasks;
    public final Matrix matrix = new Matrix();
    public boolean performanceTrackingEnabled;
    public final C04511 progressUpdateListener;
    public boolean safeMode;
    public float scale;
    public ImageView.ScaleType scaleType;
    public boolean systemAnimationsEnabled;
    public TextDelegate textDelegate;

    public interface LazyCompositionTask {
        void run();
    }

    public final void draw(Canvas canvas) {
        this.isDirty = false;
        if (this.safeMode) {
            try {
                drawInternal(canvas);
            } catch (Throwable unused) {
                Objects.requireNonNull(Logger.INSTANCE);
            }
        } else {
            drawInternal(canvas);
        }
        C0438L.endSection();
    }

    public final int getOpacity() {
        return -3;
    }

    public final void setMaxFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMaxFrame(i);
                }
            });
            return;
        }
        LottieValueAnimator lottieValueAnimator = this.animator;
        Objects.requireNonNull(lottieValueAnimator);
        lottieValueAnimator.setMinAndMaxFrames(lottieValueAnimator.minFrame, ((float) i) + 0.99f);
    }

    public final void setMinAndMaxFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMinAndMaxFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            int i = (int) marker.startFrame;
            setMinAndMaxFrame(i, ((int) marker.durationFrames) + i);
            return;
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Cannot find marker with name ", str, "."));
    }

    public final void setMinFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMinFrame(i);
                }
            });
            return;
        }
        LottieValueAnimator lottieValueAnimator = this.animator;
        Objects.requireNonNull(lottieValueAnimator);
        lottieValueAnimator.setMinAndMaxFrames((float) i, (float) ((int) lottieValueAnimator.maxFrame));
    }

    public final <T> void addValueCallback(final KeyPath keyPath, final T t, final LottieValueCallback<T> lottieValueCallback) {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.addValueCallback(keyPath, t, lottieValueCallback);
                }
            });
            return;
        }
        Objects.requireNonNull(keyPath);
        KeyPathElement keyPathElement = keyPath.resolvedElement;
        boolean z = true;
        if (keyPathElement != null) {
            keyPathElement.addValueCallback(t, lottieValueCallback);
        } else {
            List<KeyPath> resolveKeyPath = resolveKeyPath(keyPath);
            for (int i = 0; i < resolveKeyPath.size(); i++) {
                KeyPath keyPath2 = resolveKeyPath.get(i);
                Objects.requireNonNull(keyPath2);
                keyPath2.resolvedElement.addValueCallback(t, lottieValueCallback);
            }
            z = true ^ resolveKeyPath.isEmpty();
        }
        if (z) {
            invalidateSelf();
            if (t == LottieProperty.TIME_REMAP) {
                setProgress(this.animator.getAnimatedValueAbsolute());
            }
        }
    }

    public final void buildCompositionLayer() {
        LottieComposition lottieComposition = this.composition;
        JsonReader.Options options = LayerParser.NAMES;
        Objects.requireNonNull(lottieComposition);
        Rect rect = lottieComposition.bounds;
        List emptyList = Collections.emptyList();
        Layer.LayerType layerType = Layer.LayerType.PRE_COMP;
        List emptyList2 = Collections.emptyList();
        AnimatableTransform animatableTransform = r14;
        AnimatableTransform animatableTransform2 = new AnimatableTransform();
        Layer layer = r2;
        Layer layer2 = new Layer(emptyList, lottieComposition, "__container", -1, layerType, -1, (String) null, emptyList2, animatableTransform, 0, 0, 0, 0.0f, 0.0f, rect.width(), rect.height(), (AnimatableTextFrame) null, (AnimatableTextProperties) null, Collections.emptyList(), Layer.MatteType.NONE, (AnimatableFloatValue) null, false);
        LottieComposition lottieComposition2 = this.composition;
        Objects.requireNonNull(lottieComposition2);
        this.compositionLayer = new CompositionLayer(this, layer, lottieComposition2.layers, this.composition);
    }

    public final void clearComposition() {
        LottieValueAnimator lottieValueAnimator = this.animator;
        Objects.requireNonNull(lottieValueAnimator);
        if (lottieValueAnimator.running) {
            this.animator.cancel();
        }
        this.composition = null;
        this.compositionLayer = null;
        this.imageAssetManager = null;
        LottieValueAnimator lottieValueAnimator2 = this.animator;
        Objects.requireNonNull(lottieValueAnimator2);
        lottieValueAnimator2.composition = null;
        lottieValueAnimator2.minFrame = -2.14748365E9f;
        lottieValueAnimator2.maxFrame = 2.14748365E9f;
        invalidateSelf();
    }

    public final void drawInternal(Canvas canvas) {
        float f;
        float f2;
        int i = -1;
        if (ImageView.ScaleType.FIT_XY == this.scaleType) {
            if (this.compositionLayer != null) {
                Rect bounds = getBounds();
                LottieComposition lottieComposition = this.composition;
                Objects.requireNonNull(lottieComposition);
                float width = ((float) bounds.width()) / ((float) lottieComposition.bounds.width());
                LottieComposition lottieComposition2 = this.composition;
                Objects.requireNonNull(lottieComposition2);
                float height = ((float) bounds.height()) / ((float) lottieComposition2.bounds.height());
                if (this.isExtraScaleEnabled) {
                    float min = Math.min(width, height);
                    if (min < 1.0f) {
                        f2 = 1.0f / min;
                        width /= f2;
                        height /= f2;
                    } else {
                        f2 = 1.0f;
                    }
                    if (f2 > 1.0f) {
                        i = canvas.save();
                        float width2 = ((float) bounds.width()) / 2.0f;
                        float height2 = ((float) bounds.height()) / 2.0f;
                        float f3 = width2 * min;
                        float f4 = min * height2;
                        canvas.translate(width2 - f3, height2 - f4);
                        canvas.scale(f2, f2, f3, f4);
                    }
                }
                this.matrix.reset();
                this.matrix.preScale(width, height);
                this.compositionLayer.draw(canvas, this.matrix, this.alpha);
                if (i > 0) {
                    canvas.restoreToCount(i);
                }
            }
        } else if (this.compositionLayer != null) {
            float f5 = this.scale;
            LottieComposition lottieComposition3 = this.composition;
            Objects.requireNonNull(lottieComposition3);
            LottieComposition lottieComposition4 = this.composition;
            Objects.requireNonNull(lottieComposition4);
            float min2 = Math.min(((float) canvas.getWidth()) / ((float) lottieComposition3.bounds.width()), ((float) canvas.getHeight()) / ((float) lottieComposition4.bounds.height()));
            if (f5 > min2) {
                f = this.scale / min2;
            } else {
                min2 = f5;
                f = 1.0f;
            }
            if (f > 1.0f) {
                i = canvas.save();
                LottieComposition lottieComposition5 = this.composition;
                Objects.requireNonNull(lottieComposition5);
                float width3 = ((float) lottieComposition5.bounds.width()) / 2.0f;
                LottieComposition lottieComposition6 = this.composition;
                Objects.requireNonNull(lottieComposition6);
                float height3 = ((float) lottieComposition6.bounds.height()) / 2.0f;
                float f6 = width3 * min2;
                float f7 = height3 * min2;
                float f8 = this.scale;
                canvas.translate((width3 * f8) - f6, (f8 * height3) - f7);
                canvas.scale(f, f, f6, f7);
            }
            this.matrix.reset();
            this.matrix.preScale(min2, min2);
            this.compositionLayer.draw(canvas, this.matrix, this.alpha);
            if (i > 0) {
                canvas.restoreToCount(i);
            }
        }
    }

    public final int getIntrinsicHeight() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return -1;
        }
        Objects.requireNonNull(lottieComposition);
        return (int) (((float) lottieComposition.bounds.height()) * this.scale);
    }

    public final int getIntrinsicWidth() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return -1;
        }
        Objects.requireNonNull(lottieComposition);
        return (int) (((float) lottieComposition.bounds.width()) * this.scale);
    }

    public final void invalidateSelf() {
        if (!this.isDirty) {
            this.isDirty = true;
            Drawable.Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public final boolean isRunning() {
        LottieValueAnimator lottieValueAnimator = this.animator;
        if (lottieValueAnimator == null) {
            return false;
        }
        return lottieValueAnimator.running;
    }

    public final void playAnimation() {
        float f;
        float f2;
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.playAnimation();
                }
            });
            return;
        }
        if (this.systemAnimationsEnabled || this.animator.getRepeatCount() == 0) {
            LottieValueAnimator lottieValueAnimator = this.animator;
            Objects.requireNonNull(lottieValueAnimator);
            lottieValueAnimator.running = true;
            boolean isReversed = lottieValueAnimator.isReversed();
            Iterator it = lottieValueAnimator.listeners.iterator();
            while (it.hasNext()) {
                ((Animator.AnimatorListener) it.next()).onAnimationStart(lottieValueAnimator, isReversed);
            }
            if (lottieValueAnimator.isReversed()) {
                f2 = lottieValueAnimator.getMaxFrame();
            } else {
                f2 = lottieValueAnimator.getMinFrame();
            }
            lottieValueAnimator.setFrame((float) ((int) f2));
            lottieValueAnimator.lastFrameTimeNs = 0;
            lottieValueAnimator.repeatCount = 0;
            if (lottieValueAnimator.running) {
                lottieValueAnimator.removeFrameCallback(false);
                Choreographer.getInstance().postFrameCallback(lottieValueAnimator);
            }
        }
        if (!this.systemAnimationsEnabled) {
            LottieValueAnimator lottieValueAnimator2 = this.animator;
            Objects.requireNonNull(lottieValueAnimator2);
            if (lottieValueAnimator2.speed < 0.0f) {
                f = this.animator.getMinFrame();
            } else {
                f = this.animator.getMaxFrame();
            }
            setFrame((int) f);
            LottieValueAnimator lottieValueAnimator3 = this.animator;
            Objects.requireNonNull(lottieValueAnimator3);
            lottieValueAnimator3.removeFrameCallback(true);
            boolean isReversed2 = lottieValueAnimator3.isReversed();
            Iterator it2 = lottieValueAnimator3.listeners.iterator();
            while (it2.hasNext()) {
                ((Animator.AnimatorListener) it2.next()).onAnimationEnd(lottieValueAnimator3, isReversed2);
            }
        }
    }

    public final List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        if (this.compositionLayer == null) {
            Logger.warning("Cannot resolve KeyPath. Composition is not set yet.");
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        this.compositionLayer.resolveKeyPath(keyPath, 0, arrayList, new KeyPath(new String[0]));
        return arrayList;
    }

    public final void resumeAnimation() {
        float f;
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.resumeAnimation();
                }
            });
            return;
        }
        if (this.systemAnimationsEnabled || this.animator.getRepeatCount() == 0) {
            LottieValueAnimator lottieValueAnimator = this.animator;
            Objects.requireNonNull(lottieValueAnimator);
            lottieValueAnimator.running = true;
            lottieValueAnimator.removeFrameCallback(false);
            Choreographer.getInstance().postFrameCallback(lottieValueAnimator);
            lottieValueAnimator.lastFrameTimeNs = 0;
            if (lottieValueAnimator.isReversed() && lottieValueAnimator.frame == lottieValueAnimator.getMinFrame()) {
                lottieValueAnimator.frame = lottieValueAnimator.getMaxFrame();
            } else if (!lottieValueAnimator.isReversed() && lottieValueAnimator.frame == lottieValueAnimator.getMaxFrame()) {
                lottieValueAnimator.frame = lottieValueAnimator.getMinFrame();
            }
        }
        if (!this.systemAnimationsEnabled) {
            LottieValueAnimator lottieValueAnimator2 = this.animator;
            Objects.requireNonNull(lottieValueAnimator2);
            if (lottieValueAnimator2.speed < 0.0f) {
                f = this.animator.getMinFrame();
            } else {
                f = this.animator.getMaxFrame();
            }
            setFrame((int) f);
            LottieValueAnimator lottieValueAnimator3 = this.animator;
            Objects.requireNonNull(lottieValueAnimator3);
            lottieValueAnimator3.removeFrameCallback(true);
            boolean isReversed = lottieValueAnimator3.isReversed();
            Iterator it = lottieValueAnimator3.listeners.iterator();
            while (it.hasNext()) {
                ((Animator.AnimatorListener) it.next()).onAnimationEnd(lottieValueAnimator3, isReversed);
            }
        }
    }

    public final void setAlpha(int i) {
        this.alpha = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        Logger.warning("Use addColorFilter instead.");
    }

    public final void setFrame(final int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setFrame(i);
                }
            });
        } else {
            this.animator.setFrame((float) i);
        }
    }

    public final void setMaxProgress(final float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMaxProgress(f);
                }
            });
            return;
        }
        float f2 = lottieComposition.startFrame;
        Objects.requireNonNull(lottieComposition);
        float f3 = lottieComposition.endFrame;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        setMaxFrame((int) MotionController$$ExternalSyntheticOutline0.m7m(f3, f2, f, f2));
    }

    public final void setMinAndMaxProgress(final float f, final float f2) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMinAndMaxProgress(f, f2);
                }
            });
            return;
        }
        float f3 = lottieComposition.startFrame;
        Objects.requireNonNull(lottieComposition);
        float f4 = lottieComposition.endFrame;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        LottieComposition lottieComposition2 = this.composition;
        Objects.requireNonNull(lottieComposition2);
        float f5 = lottieComposition2.startFrame;
        LottieComposition lottieComposition3 = this.composition;
        Objects.requireNonNull(lottieComposition3);
        setMinAndMaxFrame((int) MotionController$$ExternalSyntheticOutline0.m7m(f4, f3, f, f3), (int) MotionController$$ExternalSyntheticOutline0.m7m(lottieComposition3.endFrame, f5, f2, f5));
    }

    public final void setMinProgress(final float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMinProgress(f);
                }
            });
            return;
        }
        float f2 = lottieComposition.startFrame;
        Objects.requireNonNull(lottieComposition);
        float f3 = lottieComposition.endFrame;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        setMinFrame((int) MotionController$$ExternalSyntheticOutline0.m7m(f3, f2, f, f2));
    }

    public final void setProgress(final float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setProgress(f);
                }
            });
            return;
        }
        LottieValueAnimator lottieValueAnimator = this.animator;
        float f2 = lottieComposition.startFrame;
        Objects.requireNonNull(lottieComposition);
        float f3 = lottieComposition.endFrame;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        lottieValueAnimator.setFrame(((f3 - f2) * f) + f2);
        C0438L.endSection();
    }

    public final void stop() {
        this.lazyCompositionTasks.clear();
        LottieValueAnimator lottieValueAnimator = this.animator;
        Objects.requireNonNull(lottieValueAnimator);
        lottieValueAnimator.removeFrameCallback(true);
        boolean isReversed = lottieValueAnimator.isReversed();
        Iterator it = lottieValueAnimator.listeners.iterator();
        while (it.hasNext()) {
            ((Animator.AnimatorListener) it.next()).onAnimationEnd(lottieValueAnimator, isReversed);
        }
    }

    public final void updateBounds() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            float f = this.scale;
            Objects.requireNonNull(lottieComposition);
            LottieComposition lottieComposition2 = this.composition;
            Objects.requireNonNull(lottieComposition2);
            setBounds(0, 0, (int) (((float) lottieComposition.bounds.width()) * f), (int) (((float) lottieComposition2.bounds.height()) * f));
        }
    }

    public LottieDrawable() {
        LottieValueAnimator lottieValueAnimator = new LottieValueAnimator();
        this.animator = lottieValueAnimator;
        this.scale = 1.0f;
        this.systemAnimationsEnabled = true;
        this.safeMode = false;
        new HashSet();
        this.lazyCompositionTasks = new ArrayList<>();
        C04511 r3 = new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LottieDrawable lottieDrawable = LottieDrawable.this;
                CompositionLayer compositionLayer = lottieDrawable.compositionLayer;
                if (compositionLayer != null) {
                    compositionLayer.setProgress(lottieDrawable.animator.getAnimatedValueAbsolute());
                }
            }
        };
        this.progressUpdateListener = r3;
        this.alpha = 255;
        this.isExtraScaleEnabled = true;
        this.isDirty = false;
        lottieValueAnimator.addUpdateListener(r3);
    }

    public final ImageAssetManager getImageAssetManager() {
        Context context;
        boolean z;
        if (getCallback() == null) {
            return null;
        }
        ImageAssetManager imageAssetManager2 = this.imageAssetManager;
        if (imageAssetManager2 != null) {
            Drawable.Callback callback = getCallback();
            if (callback != null && (callback instanceof View)) {
                context = ((View) callback).getContext();
            } else {
                context = null;
            }
            Objects.requireNonNull(imageAssetManager2);
            if (!(context == null && imageAssetManager2.context == null) && !imageAssetManager2.context.equals(context)) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                this.imageAssetManager = null;
            }
        }
        if (this.imageAssetManager == null) {
            Drawable.Callback callback2 = getCallback();
            String str = this.imageAssetsFolder;
            ImageAssetDelegate imageAssetDelegate2 = this.imageAssetDelegate;
            LottieComposition lottieComposition = this.composition;
            Objects.requireNonNull(lottieComposition);
            this.imageAssetManager = new ImageAssetManager(callback2, str, imageAssetDelegate2, lottieComposition.images);
        }
        return this.imageAssetManager;
    }

    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final void setMaxFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMaxFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            setMaxFrame((int) (marker.startFrame + marker.durationFrames));
            return;
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Cannot find marker with name ", str, "."));
    }

    public final void setMinFrame(final String str) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMinFrame(str);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            setMinFrame((int) marker.startFrame);
            return;
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Cannot find marker with name ", str, "."));
    }

    public final void setMinAndMaxFrame(final String str, final String str2, final boolean z) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMinAndMaxFrame(str, str2, z);
                }
            });
            return;
        }
        Marker marker = lottieComposition.getMarker(str);
        if (marker != null) {
            int i = (int) marker.startFrame;
            Marker marker2 = this.composition.getMarker(str2);
            if (str2 != null) {
                setMinAndMaxFrame(i, (int) (marker2.startFrame + (z ? 1.0f : 0.0f)));
                return;
            }
            throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Cannot find marker with name ", str2, "."));
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Cannot find marker with name ", str, "."));
    }

    public final void setMinAndMaxFrame(final int i, final int i2) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run() {
                    LottieDrawable.this.setMinAndMaxFrame(i, i2);
                }
            });
        } else {
            this.animator.setMinAndMaxFrames((float) i, ((float) i2) + 0.99f);
        }
    }

    static {
        Class<LottieDrawable> cls = LottieDrawable.class;
    }

    public final int getAlpha() {
        return this.alpha;
    }

    public final void start() {
        playAnimation();
    }
}
