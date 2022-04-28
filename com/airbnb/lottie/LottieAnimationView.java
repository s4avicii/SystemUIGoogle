package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PathMeasure;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AppCompatImageView;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.utils.LogcatLogger;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.lang.ref.WeakReference;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.zip.ZipInputStream;
import javax.net.ssl.SSLException;

public class LottieAnimationView extends AppCompatImageView {
    public static final C04391 DEFAULT_FAILURE_LISTENER = new LottieListener<Throwable>() {
        public final void onResult(Object obj) {
            boolean z;
            Throwable th = (Throwable) obj;
            PathMeasure pathMeasure = Utils.pathMeasure;
            if ((th instanceof SocketException) || (th instanceof ClosedChannelException) || (th instanceof InterruptedIOException) || (th instanceof ProtocolException) || (th instanceof SSLException) || (th instanceof UnknownHostException) || (th instanceof UnknownServiceException)) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                Objects.requireNonNull(Logger.INSTANCE);
                HashSet hashSet = LogcatLogger.loggedMessages;
                if (!hashSet.contains("Unable to load composition.")) {
                    Log.w("LOTTIE", "Unable to load composition.", th);
                    hashSet.add("Unable to load composition.");
                    return;
                }
                return;
            }
            throw new IllegalStateException("Unable to parse composition", th);
        }
    };
    public String animationName;
    public int animationResId;
    public boolean autoPlay = false;
    public int buildDrawingCacheDepth = 0;
    public boolean cacheComposition = true;
    public LottieComposition composition;
    public LottieTask<LottieComposition> compositionTask;
    public LottieListener<Throwable> failureListener;
    public int fallbackResource = 0;
    public boolean isInitialized;
    public final C04402 loadedListener = new LottieListener<LottieComposition>() {
        public final void onResult(Object obj) {
            LottieAnimationView.this.setComposition((LottieComposition) obj);
        }
    };
    public final LottieDrawable lottieDrawable = new LottieDrawable();
    public HashSet lottieOnCompositionLoadedListeners = new HashSet();
    public RenderMode renderMode = RenderMode.AUTOMATIC;
    public boolean wasAnimatingWhenDetached = false;
    public boolean wasAnimatingWhenNotShown = false;
    public final C04413 wrappedFailureListener = new LottieListener<Throwable>() {
        public final void onResult(Object obj) {
            Throwable th = (Throwable) obj;
            LottieAnimationView lottieAnimationView = LottieAnimationView.this;
            int i = lottieAnimationView.fallbackResource;
            if (i != 0) {
                lottieAnimationView.setImageResource(i);
            }
            LottieListener lottieListener = LottieAnimationView.this.failureListener;
            if (lottieListener == null) {
                lottieListener = LottieAnimationView.DEFAULT_FAILURE_LISTENER;
            }
            lottieListener.onResult(th);
        }
    };

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public String animationName;
        public int animationResId;
        public String imageAssetsFolder;
        public boolean isAnimating;
        public float progress;
        public int repeatCount;
        public int repeatMode;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.animationName = parcel.readString();
            this.progress = parcel.readFloat();
            this.isAnimating = parcel.readInt() != 1 ? false : true;
            this.imageAssetsFolder = parcel.readString();
            this.repeatMode = parcel.readInt();
            this.repeatCount = parcel.readInt();
        }

        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.animationName);
            parcel.writeFloat(this.progress);
            parcel.writeInt(this.isAnimating ? 1 : 0);
            parcel.writeString(this.imageAssetsFolder);
            parcel.writeInt(this.repeatMode);
            parcel.writeInt(this.repeatCount);
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, LottieValueCallback<T> lottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, t, lottieValueCallback);
    }

    public void cancelAnimation() {
        this.wasAnimatingWhenNotShown = false;
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.lazyCompositionTasks.clear();
        lottieDrawable2.animator.cancel();
        enableOrDisableHardwareLayer();
    }

    public void pauseAnimation() {
        this.autoPlay = false;
        this.wasAnimatingWhenDetached = false;
        this.wasAnimatingWhenNotShown = false;
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.lazyCompositionTasks.clear();
        LottieValueAnimator lottieValueAnimator = lottieDrawable2.animator;
        Objects.requireNonNull(lottieValueAnimator);
        lottieValueAnimator.removeFrameCallback(true);
        enableOrDisableHardwareLayer();
    }

    public void setAnimation(InputStream inputStream, String str) {
        setCompositionTask(LottieCompositionFactory.cache(str, new Callable<LottieResult<LottieComposition>>(inputStream, str) {
            public final /* synthetic */ String val$cacheKey;
            public final /* synthetic */ InputStream val$stream;

            {
                this.val$stream = r1;
                this.val$cacheKey = r2;
            }

            public final Object call() throws Exception {
                return LottieCompositionFactory.fromJsonInputStreamSync(this.val$stream, this.val$cacheKey);
            }
        }));
    }

    @Deprecated
    public void setAnimationFromJson(String str) {
        setAnimationFromJson(str, (String) null);
    }

    public final void setCompositionTask(LottieTask<LottieComposition> lottieTask) {
        this.composition = null;
        this.lottieDrawable.clearComposition();
        cancelLoaderTask();
        lottieTask.addListener(this.loadedListener);
        lottieTask.addFailureListener(this.wrappedFailureListener);
        this.compositionTask = lottieTask;
    }

    public void setMaxFrame(int i) {
        this.lottieDrawable.setMaxFrame(i);
    }

    public void setMinAndMaxFrame(String str) {
        this.lottieDrawable.setMinAndMaxFrame(str);
    }

    public void setMinFrame(int i) {
        this.lottieDrawable.setMinFrame(i);
    }

    static {
        Class<LottieAnimationView> cls = LottieAnimationView.class;
    }

    public void addAnimatorListener(Animator.AnimatorListener animatorListener) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.addListener(animatorListener);
    }

    public void addAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.addUpdateListener(animatorUpdateListener);
    }

    public boolean addLottieOnCompositionLoadedListener(LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            lottieOnCompositionLoadedListener.onCompositionLoaded(lottieComposition);
        }
        return this.lottieOnCompositionLoadedListeners.add(lottieOnCompositionLoadedListener);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, final SimpleLottieValueCallback<T> simpleLottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, t, new LottieValueCallback<T>() {
            public final T getValue(LottieFrameInfo<T> lottieFrameInfo) {
                return SimpleLottieValueCallback.this.getValue();
            }
        });
    }

    public void buildDrawingCache(boolean z) {
        this.buildDrawingCacheDepth++;
        super.buildDrawingCache(z);
        if (this.buildDrawingCacheDepth == 1 && getWidth() > 0 && getHeight() > 0 && getLayerType() == 1 && getDrawingCache(z) == null) {
            setRenderMode(RenderMode.HARDWARE);
        }
        this.buildDrawingCacheDepth--;
        C0438L.endSection();
    }

    public final void cancelLoaderTask() {
        LottieTask<LottieComposition> lottieTask = this.compositionTask;
        if (lottieTask != null) {
            C04402 r1 = this.loadedListener;
            synchronized (lottieTask) {
                lottieTask.successListeners.remove(r1);
            }
            LottieTask<LottieComposition> lottieTask2 = this.compositionTask;
            C04413 r3 = this.wrappedFailureListener;
            Objects.requireNonNull(lottieTask2);
            synchronized (lottieTask2) {
                lottieTask2.failureListeners.remove(r3);
            }
        }
    }

    public void disableExtraScaleModeInFitXY() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.isExtraScaleEnabled = false;
    }

    public void enableMergePathsForKitKatAndAbove(boolean z) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        if (lottieDrawable2.enableMergePaths != z) {
            lottieDrawable2.enableMergePaths = z;
            if (lottieDrawable2.composition != null) {
                lottieDrawable2.buildCompositionLayer();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        if (r0 == false) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        if (r0 != 1) goto L_0x000c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void enableOrDisableHardwareLayer() {
        /*
            r4 = this;
            com.airbnb.lottie.RenderMode r0 = r4.renderMode
            int r0 = r0.ordinal()
            r1 = 2
            r2 = 1
            if (r0 == 0) goto L_0x000e
            if (r0 == r2) goto L_0x0028
        L_0x000c:
            r1 = r2
            goto L_0x0028
        L_0x000e:
            com.airbnb.lottie.LottieComposition r0 = r4.composition
            if (r0 == 0) goto L_0x0017
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.hasDashPattern
        L_0x0017:
            com.airbnb.lottie.LottieComposition r0 = r4.composition
            if (r0 == 0) goto L_0x0025
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.maskAndMatteCount
            r3 = 4
            if (r0 <= r3) goto L_0x0025
            r0 = 0
            goto L_0x0026
        L_0x0025:
            r0 = r2
        L_0x0026:
            if (r0 == 0) goto L_0x000c
        L_0x0028:
            int r0 = r4.getLayerType()
            if (r1 == r0) goto L_0x0032
            r0 = 0
            r4.setLayerType(r1, r0)
        L_0x0032:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieAnimationView.enableOrDisableHardwareLayer():void");
    }

    public long getDuration() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            return (long) lottieComposition.getDuration();
        }
        return 0;
    }

    public int getFrame() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        LottieValueAnimator lottieValueAnimator = lottieDrawable2.animator;
        Objects.requireNonNull(lottieValueAnimator);
        return (int) lottieValueAnimator.frame;
    }

    public String getImageAssetsFolder() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        return lottieDrawable2.imageAssetsFolder;
    }

    public float getMaxFrame() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        return lottieDrawable2.animator.getMaxFrame();
    }

    public float getMinFrame() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        return lottieDrawable2.animator.getMinFrame();
    }

    public PerformanceTracker getPerformanceTracker() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        LottieComposition lottieComposition = lottieDrawable2.composition;
        if (lottieComposition != null) {
            return lottieComposition.performanceTracker;
        }
        return null;
    }

    public float getProgress() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        return lottieDrawable2.animator.getAnimatedValueAbsolute();
    }

    public int getRepeatCount() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        return lottieDrawable2.animator.getRepeatCount();
    }

    public int getRepeatMode() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        return lottieDrawable2.animator.getRepeatMode();
    }

    public float getScale() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        return lottieDrawable2.scale;
    }

    public float getSpeed() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        LottieValueAnimator lottieValueAnimator = lottieDrawable2.animator;
        Objects.requireNonNull(lottieValueAnimator);
        return lottieValueAnimator.speed;
    }

    public boolean hasMasks() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        CompositionLayer compositionLayer = lottieDrawable2.compositionLayer;
        if (compositionLayer == null || !compositionLayer.hasMasks()) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasMatte() {
        /*
            r4 = this;
            com.airbnb.lottie.LottieDrawable r4 = r4.lottieDrawable
            java.util.Objects.requireNonNull(r4)
            com.airbnb.lottie.model.layer.CompositionLayer r4 = r4.compositionLayer
            r0 = 0
            r1 = 1
            if (r4 == 0) goto L_0x0050
            java.lang.Boolean r2 = r4.hasMatte
            if (r2 != 0) goto L_0x0047
            com.airbnb.lottie.model.layer.BaseLayer r2 = r4.matteLayer
            if (r2 == 0) goto L_0x0015
            r2 = r1
            goto L_0x0016
        L_0x0015:
            r2 = r0
        L_0x0016:
            if (r2 == 0) goto L_0x001d
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r4.hasMatte = r2
            goto L_0x003e
        L_0x001d:
            java.util.ArrayList r2 = r4.layers
            int r2 = r2.size()
            int r2 = r2 - r1
        L_0x0024:
            if (r2 < 0) goto L_0x0043
            java.util.ArrayList r3 = r4.layers
            java.lang.Object r3 = r3.get(r2)
            com.airbnb.lottie.model.layer.BaseLayer r3 = (com.airbnb.lottie.model.layer.BaseLayer) r3
            java.util.Objects.requireNonNull(r3)
            com.airbnb.lottie.model.layer.BaseLayer r3 = r3.matteLayer
            if (r3 == 0) goto L_0x0037
            r3 = r1
            goto L_0x0038
        L_0x0037:
            r3 = r0
        L_0x0038:
            if (r3 == 0) goto L_0x0040
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r4.hasMatte = r2
        L_0x003e:
            r4 = r1
            goto L_0x004d
        L_0x0040:
            int r2 = r2 + -1
            goto L_0x0024
        L_0x0043:
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            r4.hasMatte = r2
        L_0x0047:
            java.lang.Boolean r4 = r4.hasMatte
            boolean r4 = r4.booleanValue()
        L_0x004d:
            if (r4 == 0) goto L_0x0050
            r0 = r1
        L_0x0050:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieAnimationView.hasMatte():boolean");
    }

    public boolean isAnimating() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        LottieValueAnimator lottieValueAnimator = lottieDrawable2.animator;
        if (lottieValueAnimator == null) {
            return false;
        }
        return lottieValueAnimator.running;
    }

    public boolean isMergePathsEnabledForKitKatAndAbove() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        return lottieDrawable2.enableMergePaths;
    }

    @Deprecated
    public void loop(boolean z) {
        int i;
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        if (z) {
            i = -1;
        } else {
            i = 0;
        }
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.setRepeatCount(i);
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        String str = savedState.animationName;
        this.animationName = str;
        if (!TextUtils.isEmpty(str)) {
            setAnimation(this.animationName);
        }
        int i = savedState.animationResId;
        this.animationResId = i;
        if (i != 0) {
            setAnimation(i);
        }
        setProgress(savedState.progress);
        if (savedState.isAnimating) {
            playAnimation();
        }
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        String str2 = savedState.imageAssetsFolder;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.imageAssetsFolder = str2;
        setRepeatMode(savedState.repeatMode);
        setRepeatCount(savedState.repeatCount);
    }

    public final void onVisibilityChanged(View view, int i) {
        if (this.isInitialized) {
            if (isShown()) {
                if (this.wasAnimatingWhenNotShown) {
                    resumeAnimation();
                    this.wasAnimatingWhenNotShown = false;
                }
            } else if (isAnimating()) {
                pauseAnimation();
                this.wasAnimatingWhenNotShown = true;
            }
        }
    }

    public void removeAllAnimatorListeners() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.removeAllListeners();
    }

    public void removeAllLottieOnCompositionLoadedListener() {
        this.lottieOnCompositionLoadedListeners.clear();
    }

    public void removeAllUpdateListeners() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.removeAllUpdateListeners();
        lottieDrawable2.animator.addUpdateListener(lottieDrawable2.progressUpdateListener);
    }

    public void removeAnimatorListener(Animator.AnimatorListener animatorListener) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.removeListener(animatorListener);
    }

    public boolean removeLottieOnCompositionLoadedListener(LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener) {
        return this.lottieOnCompositionLoadedListeners.remove(lottieOnCompositionLoadedListener);
    }

    public void removeUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.removeUpdateListener(animatorUpdateListener);
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        return this.lottieDrawable.resolveKeyPath(keyPath);
    }

    public void reverseAnimationSpeed() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        LottieValueAnimator lottieValueAnimator = lottieDrawable2.animator;
        Objects.requireNonNull(lottieValueAnimator);
        lottieValueAnimator.speed = -lottieValueAnimator.speed;
    }

    public void setAnimationFromJson(String str, String str2) {
        setAnimation(new ByteArrayInputStream(str.getBytes()), str2);
    }

    public void setAnimationFromUrl(String str) {
        LottieTask<LottieComposition> lottieTask;
        if (this.cacheComposition) {
            Context context = getContext();
            HashMap hashMap = LottieCompositionFactory.taskCache;
            lottieTask = LottieCompositionFactory.cache(SupportMenuInflater$$ExternalSyntheticOutline0.m4m("url_", str), new Callable<LottieResult<LottieComposition>>(context, str) {
                public final /* synthetic */ Context val$context;
                public final /* synthetic */ String val$url;

                {
                    this.val$context = r1;
                    this.val$url = r2;
                }

                /* JADX WARNING: Removed duplicated region for block: B:19:0x0068  */
                /* JADX WARNING: Removed duplicated region for block: B:33:0x0095  */
                /* JADX WARNING: Removed duplicated region for block: B:34:0x009b  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final java.lang.Object call() throws java.lang.Exception {
                    /*
                        r9 = this;
                        android.content.Context r0 = r9.val$context
                        java.lang.String r9 = r9.val$url
                        com.airbnb.lottie.network.NetworkFetcher r1 = new com.airbnb.lottie.network.NetworkFetcher
                        r1.<init>(r0, r9)
                        com.airbnb.lottie.network.FileExtension r9 = com.airbnb.lottie.network.FileExtension.ZIP
                        com.airbnb.lottie.network.NetworkCache r0 = r1.networkCache
                        java.util.Objects.requireNonNull(r0)
                        r2 = 0
                        java.lang.String r3 = r0.url     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.io.File r4 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0064 }
                        android.content.Context r5 = r0.appContext     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.io.File r5 = r5.getCacheDir()     // Catch:{ FileNotFoundException -> 0x0064 }
                        com.airbnb.lottie.network.FileExtension r6 = com.airbnb.lottie.network.FileExtension.JSON     // Catch:{ FileNotFoundException -> 0x0064 }
                        r7 = 0
                        java.lang.String r8 = com.airbnb.lottie.network.NetworkCache.filenameForUrl(r3, r6, r7)     // Catch:{ FileNotFoundException -> 0x0064 }
                        r4.<init>(r5, r8)     // Catch:{ FileNotFoundException -> 0x0064 }
                        boolean r5 = r4.exists()     // Catch:{ FileNotFoundException -> 0x0064 }
                        if (r5 == 0) goto L_0x002c
                        goto L_0x0043
                    L_0x002c:
                        java.io.File r4 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0064 }
                        android.content.Context r0 = r0.appContext     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.io.File r0 = r0.getCacheDir()     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.lang.String r3 = com.airbnb.lottie.network.NetworkCache.filenameForUrl(r3, r9, r7)     // Catch:{ FileNotFoundException -> 0x0064 }
                        r4.<init>(r0, r3)     // Catch:{ FileNotFoundException -> 0x0064 }
                        boolean r0 = r4.exists()     // Catch:{ FileNotFoundException -> 0x0064 }
                        if (r0 == 0) goto L_0x0042
                        goto L_0x0043
                    L_0x0042:
                        r4 = r2
                    L_0x0043:
                        if (r4 != 0) goto L_0x0046
                        goto L_0x0064
                    L_0x0046:
                        java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0064 }
                        r0.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.lang.String r3 = r4.getAbsolutePath()
                        java.lang.String r5 = ".zip"
                        boolean r3 = r3.endsWith(r5)
                        if (r3 == 0) goto L_0x0058
                        r6 = r9
                    L_0x0058:
                        r4.getAbsolutePath()
                        com.airbnb.lottie.utils.Logger.debug()
                        androidx.core.util.Pair r3 = new androidx.core.util.Pair
                        r3.<init>(r6, r0)
                        goto L_0x0065
                    L_0x0064:
                        r3 = r2
                    L_0x0065:
                        if (r3 != 0) goto L_0x0068
                        goto L_0x0093
                    L_0x0068:
                        F r0 = r3.first
                        com.airbnb.lottie.network.FileExtension r0 = (com.airbnb.lottie.network.FileExtension) r0
                        S r3 = r3.second
                        java.io.InputStream r3 = (java.io.InputStream) r3
                        if (r0 != r9) goto L_0x0086
                        java.util.zip.ZipInputStream r9 = new java.util.zip.ZipInputStream
                        r9.<init>(r3)
                        java.lang.String r0 = r1.url
                        com.airbnb.lottie.LottieResult r0 = com.airbnb.lottie.LottieCompositionFactory.fromZipStreamSyncInternal(r9, r0)     // Catch:{ all -> 0x0081 }
                        com.airbnb.lottie.utils.Utils.closeQuietly(r9)
                        goto L_0x008c
                    L_0x0081:
                        r0 = move-exception
                        com.airbnb.lottie.utils.Utils.closeQuietly(r9)
                        throw r0
                    L_0x0086:
                        java.lang.String r9 = r1.url
                        com.airbnb.lottie.LottieResult r0 = com.airbnb.lottie.LottieCompositionFactory.fromJsonInputStreamSync(r3, r9)
                    L_0x008c:
                        V r9 = r0.value
                        if (r9 == 0) goto L_0x0093
                        r2 = r9
                        com.airbnb.lottie.LottieComposition r2 = (com.airbnb.lottie.LottieComposition) r2
                    L_0x0093:
                        if (r2 == 0) goto L_0x009b
                        com.airbnb.lottie.LottieResult r9 = new com.airbnb.lottie.LottieResult
                        r9.<init>((com.airbnb.lottie.LottieComposition) r2)
                        goto L_0x00aa
                    L_0x009b:
                        com.airbnb.lottie.utils.Logger.debug()
                        com.airbnb.lottie.LottieResult r9 = r1.fetchFromNetworkInternal()     // Catch:{ IOException -> 0x00a3 }
                        goto L_0x00aa
                    L_0x00a3:
                        r9 = move-exception
                        com.airbnb.lottie.LottieResult r0 = new com.airbnb.lottie.LottieResult
                        r0.<init>((java.lang.Throwable) r9)
                        r9 = r0
                    L_0x00aa:
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieCompositionFactory.C04441.call():java.lang.Object");
                }
            });
        } else {
            lottieTask = LottieCompositionFactory.cache((String) null, new Callable<LottieResult<LottieComposition>>(getContext(), str) {
                public final /* synthetic */ Context val$context;
                public final /* synthetic */ String val$url;

                {
                    this.val$context = r1;
                    this.val$url = r2;
                }

                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final java.lang.Object call() {
                    /*
                        r9 = this;
                        android.content.Context r0 = r9.val$context
                        java.lang.String r9 = r9.val$url
                        com.airbnb.lottie.network.NetworkFetcher r1 = new com.airbnb.lottie.network.NetworkFetcher
                        r1.<init>(r0, r9)
                        com.airbnb.lottie.network.FileExtension r9 = com.airbnb.lottie.network.FileExtension.ZIP
                        com.airbnb.lottie.network.NetworkCache r0 = r1.networkCache
                        java.util.Objects.requireNonNull(r0)
                        r2 = 0
                        java.lang.String r3 = r0.url     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.io.File r4 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0064 }
                        android.content.Context r5 = r0.appContext     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.io.File r5 = r5.getCacheDir()     // Catch:{ FileNotFoundException -> 0x0064 }
                        com.airbnb.lottie.network.FileExtension r6 = com.airbnb.lottie.network.FileExtension.JSON     // Catch:{ FileNotFoundException -> 0x0064 }
                        r7 = 0
                        java.lang.String r8 = com.airbnb.lottie.network.NetworkCache.filenameForUrl(r3, r6, r7)     // Catch:{ FileNotFoundException -> 0x0064 }
                        r4.<init>(r5, r8)     // Catch:{ FileNotFoundException -> 0x0064 }
                        boolean r5 = r4.exists()     // Catch:{ FileNotFoundException -> 0x0064 }
                        if (r5 == 0) goto L_0x002c
                        goto L_0x0043
                    L_0x002c:
                        java.io.File r4 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0064 }
                        android.content.Context r0 = r0.appContext     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.io.File r0 = r0.getCacheDir()     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.lang.String r3 = com.airbnb.lottie.network.NetworkCache.filenameForUrl(r3, r9, r7)     // Catch:{ FileNotFoundException -> 0x0064 }
                        r4.<init>(r0, r3)     // Catch:{ FileNotFoundException -> 0x0064 }
                        boolean r0 = r4.exists()     // Catch:{ FileNotFoundException -> 0x0064 }
                        if (r0 == 0) goto L_0x0042
                        goto L_0x0043
                    L_0x0042:
                        r4 = r2
                    L_0x0043:
                        if (r4 != 0) goto L_0x0046
                        goto L_0x0064
                    L_0x0046:
                        java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0064 }
                        r0.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0064 }
                        java.lang.String r3 = r4.getAbsolutePath()
                        java.lang.String r5 = ".zip"
                        boolean r3 = r3.endsWith(r5)
                        if (r3 == 0) goto L_0x0058
                        r6 = r9
                    L_0x0058:
                        r4.getAbsolutePath()
                        com.airbnb.lottie.utils.Logger.debug()
                        androidx.core.util.Pair r3 = new androidx.core.util.Pair
                        r3.<init>(r6, r0)
                        goto L_0x0065
                    L_0x0064:
                        r3 = r2
                    L_0x0065:
                        if (r3 != 0) goto L_0x0068
                        goto L_0x0093
                    L_0x0068:
                        F r0 = r3.first
                        com.airbnb.lottie.network.FileExtension r0 = (com.airbnb.lottie.network.FileExtension) r0
                        S r3 = r3.second
                        java.io.InputStream r3 = (java.io.InputStream) r3
                        if (r0 != r9) goto L_0x0086
                        java.util.zip.ZipInputStream r9 = new java.util.zip.ZipInputStream
                        r9.<init>(r3)
                        java.lang.String r0 = r1.url
                        com.airbnb.lottie.LottieResult r0 = com.airbnb.lottie.LottieCompositionFactory.fromZipStreamSyncInternal(r9, r0)     // Catch:{ all -> 0x0081 }
                        com.airbnb.lottie.utils.Utils.closeQuietly(r9)
                        goto L_0x008c
                    L_0x0081:
                        r0 = move-exception
                        com.airbnb.lottie.utils.Utils.closeQuietly(r9)
                        throw r0
                    L_0x0086:
                        java.lang.String r9 = r1.url
                        com.airbnb.lottie.LottieResult r0 = com.airbnb.lottie.LottieCompositionFactory.fromJsonInputStreamSync(r3, r9)
                    L_0x008c:
                        V r9 = r0.value
                        if (r9 == 0) goto L_0x0093
                        r2 = r9
                        com.airbnb.lottie.LottieComposition r2 = (com.airbnb.lottie.LottieComposition) r2
                    L_0x0093:
                        if (r2 == 0) goto L_0x009b
                        com.airbnb.lottie.LottieResult r9 = new com.airbnb.lottie.LottieResult
                        r9.<init>((com.airbnb.lottie.LottieComposition) r2)
                        goto L_0x00aa
                    L_0x009b:
                        com.airbnb.lottie.utils.Logger.debug()
                        com.airbnb.lottie.LottieResult r9 = r1.fetchFromNetworkInternal()     // Catch:{ IOException -> 0x00a3 }
                        goto L_0x00aa
                    L_0x00a3:
                        r9 = move-exception
                        com.airbnb.lottie.LottieResult r0 = new com.airbnb.lottie.LottieResult
                        r0.<init>((java.lang.Throwable) r9)
                        r9 = r0
                    L_0x00aa:
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieCompositionFactory.C04441.call():java.lang.Object");
                }
            });
        }
        setCompositionTask(lottieTask);
    }

    public void setApplyingOpacityToLayersEnabled(boolean z) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.isApplyingOpacityToLayersEnabled = z;
    }

    public void setComposition(LottieComposition lottieComposition) {
        this.lottieDrawable.setCallback(this);
        this.composition = lottieComposition;
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        boolean z = false;
        if (lottieDrawable2.composition != lottieComposition) {
            lottieDrawable2.isDirty = false;
            lottieDrawable2.clearComposition();
            lottieDrawable2.composition = lottieComposition;
            lottieDrawable2.buildCompositionLayer();
            LottieValueAnimator lottieValueAnimator = lottieDrawable2.animator;
            Objects.requireNonNull(lottieValueAnimator);
            if (lottieValueAnimator.composition == null) {
                z = true;
            }
            lottieValueAnimator.composition = lottieComposition;
            if (z) {
                float f = lottieValueAnimator.minFrame;
                Objects.requireNonNull(lottieComposition);
                lottieValueAnimator.setMinAndMaxFrames((float) ((int) Math.max(f, lottieComposition.startFrame)), (float) ((int) Math.min(lottieValueAnimator.maxFrame, lottieComposition.endFrame)));
            } else {
                Objects.requireNonNull(lottieComposition);
                lottieValueAnimator.setMinAndMaxFrames((float) ((int) lottieComposition.startFrame), (float) ((int) lottieComposition.endFrame));
            }
            float f2 = lottieValueAnimator.frame;
            lottieValueAnimator.frame = 0.0f;
            lottieValueAnimator.setFrame((float) ((int) f2));
            lottieDrawable2.setProgress(lottieDrawable2.animator.getAnimatedFraction());
            lottieDrawable2.scale = lottieDrawable2.scale;
            lottieDrawable2.updateBounds();
            lottieDrawable2.updateBounds();
            Iterator it = new ArrayList(lottieDrawable2.lazyCompositionTasks).iterator();
            while (it.hasNext()) {
                ((LottieDrawable.LazyCompositionTask) it.next()).run();
                it.remove();
            }
            lottieDrawable2.lazyCompositionTasks.clear();
            boolean z2 = lottieDrawable2.performanceTrackingEnabled;
            Objects.requireNonNull(lottieComposition);
            PerformanceTracker performanceTracker = lottieComposition.performanceTracker;
            Objects.requireNonNull(performanceTracker);
            performanceTracker.enabled = z2;
            z = true;
        }
        enableOrDisableHardwareLayer();
        if (getDrawable() != this.lottieDrawable || z) {
            setImageDrawable((Drawable) null);
            setImageDrawable(this.lottieDrawable);
            onVisibilityChanged(this, getVisibility());
            requestLayout();
            Iterator it2 = this.lottieOnCompositionLoadedListeners.iterator();
            while (it2.hasNext()) {
                ((LottieOnCompositionLoadedListener) it2.next()).onCompositionLoaded(lottieComposition);
            }
        }
    }

    public void setFontAssetDelegate(FontAssetDelegate fontAssetDelegate) {
        Objects.requireNonNull(this.lottieDrawable);
    }

    public void setFrame(int i) {
        this.lottieDrawable.setFrame(i);
    }

    public void setImageAssetDelegate(ImageAssetDelegate imageAssetDelegate) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.imageAssetDelegate = imageAssetDelegate;
        ImageAssetManager imageAssetManager = lottieDrawable2.imageAssetManager;
        if (imageAssetManager != null) {
            imageAssetManager.delegate = imageAssetDelegate;
        }
    }

    public void setImageAssetsFolder(String str) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.imageAssetsFolder = str;
    }

    public void setMaxFrame(String str) {
        this.lottieDrawable.setMaxFrame(str);
    }

    public void setMaxProgress(float f) {
        this.lottieDrawable.setMaxProgress(f);
    }

    public void setMinAndMaxFrame(String str, String str2, boolean z) {
        this.lottieDrawable.setMinAndMaxFrame(str, str2, z);
    }

    public void setMinAndMaxProgress(float f, float f2) {
        this.lottieDrawable.setMinAndMaxProgress(f, f2);
    }

    public void setMinFrame(String str) {
        this.lottieDrawable.setMinFrame(str);
    }

    public void setMinProgress(float f) {
        this.lottieDrawable.setMinProgress(f);
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.performanceTrackingEnabled = z;
        LottieComposition lottieComposition = lottieDrawable2.composition;
        if (lottieComposition != null) {
            PerformanceTracker performanceTracker = lottieComposition.performanceTracker;
            Objects.requireNonNull(performanceTracker);
            performanceTracker.enabled = z;
        }
    }

    public void setProgress(float f) {
        this.lottieDrawable.setProgress(f);
    }

    public void setRenderMode(RenderMode renderMode2) {
        this.renderMode = renderMode2;
        enableOrDisableHardwareLayer();
    }

    public void setRepeatCount(int i) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.setRepeatCount(i);
    }

    public void setRepeatMode(int i) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.animator.setRepeatMode(i);
    }

    public void setSafeMode(boolean z) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.safeMode = z;
    }

    public void setScale(float f) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.scale = f;
        lottieDrawable2.updateBounds();
        if (getDrawable() == this.lottieDrawable) {
            setImageDrawable((Drawable) null);
            setImageDrawable(this.lottieDrawable);
        }
    }

    public void setSpeed(float f) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        LottieValueAnimator lottieValueAnimator = lottieDrawable2.animator;
        Objects.requireNonNull(lottieValueAnimator);
        lottieValueAnimator.speed = f;
    }

    public void setTextDelegate(TextDelegate textDelegate) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        lottieDrawable2.textDelegate = textDelegate;
    }

    public Bitmap updateBitmap(String str, Bitmap bitmap) {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        ImageAssetManager imageAssetManager = lottieDrawable2.getImageAssetManager();
        Bitmap bitmap2 = null;
        if (imageAssetManager == null) {
            Logger.warning("Cannot update bitmap. Most likely the drawable is not added to a View which prevents Lottie from getting a Context.");
        } else {
            if (bitmap == null) {
                LottieImageAsset lottieImageAsset = imageAssetManager.imageAssets.get(str);
                Objects.requireNonNull(lottieImageAsset);
                Bitmap bitmap3 = lottieImageAsset.bitmap;
                lottieImageAsset.bitmap = null;
                bitmap2 = bitmap3;
            } else {
                LottieImageAsset lottieImageAsset2 = imageAssetManager.imageAssets.get(str);
                Objects.requireNonNull(lottieImageAsset2);
                bitmap2 = lottieImageAsset2.bitmap;
                imageAssetManager.putBitmap(str, bitmap);
            }
            lottieDrawable2.invalidateSelf();
        }
        return bitmap2;
    }

    public final void init(AttributeSet attributeSet) {
        String string;
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView);
        boolean z = false;
        if (!isInEditMode()) {
            this.cacheComposition = obtainStyledAttributes.getBoolean(1, true);
            boolean hasValue = obtainStyledAttributes.hasValue(9);
            boolean hasValue2 = obtainStyledAttributes.hasValue(5);
            boolean hasValue3 = obtainStyledAttributes.hasValue(15);
            if (!hasValue || !hasValue2) {
                if (hasValue) {
                    int resourceId = obtainStyledAttributes.getResourceId(9, 0);
                    if (resourceId != 0) {
                        setAnimation(resourceId);
                    }
                } else if (hasValue2) {
                    String string2 = obtainStyledAttributes.getString(5);
                    if (string2 != null) {
                        setAnimation(string2);
                    }
                } else if (hasValue3 && (string = obtainStyledAttributes.getString(15)) != null) {
                    setAnimationFromUrl(string);
                }
                setFallbackResource(obtainStyledAttributes.getResourceId(4, 0));
            } else {
                throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use only one at once.");
            }
        }
        if (obtainStyledAttributes.getBoolean(0, false)) {
            this.wasAnimatingWhenDetached = true;
            this.autoPlay = true;
        }
        if (obtainStyledAttributes.getBoolean(7, false)) {
            LottieDrawable lottieDrawable2 = this.lottieDrawable;
            Objects.requireNonNull(lottieDrawable2);
            lottieDrawable2.animator.setRepeatCount(-1);
        }
        if (obtainStyledAttributes.hasValue(12)) {
            setRepeatMode(obtainStyledAttributes.getInt(12, 1));
        }
        if (obtainStyledAttributes.hasValue(11)) {
            setRepeatCount(obtainStyledAttributes.getInt(11, -1));
        }
        if (obtainStyledAttributes.hasValue(14)) {
            setSpeed(obtainStyledAttributes.getFloat(14, 1.0f));
        }
        setImageAssetsFolder(obtainStyledAttributes.getString(6));
        setProgress(obtainStyledAttributes.getFloat(8, 0.0f));
        enableMergePathsForKitKatAndAbove(obtainStyledAttributes.getBoolean(3, false));
        if (obtainStyledAttributes.hasValue(2)) {
            addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(obtainStyledAttributes.getColor(2, 0))));
        }
        if (obtainStyledAttributes.hasValue(13)) {
            LottieDrawable lottieDrawable3 = this.lottieDrawable;
            float f = obtainStyledAttributes.getFloat(13, 1.0f);
            Objects.requireNonNull(lottieDrawable3);
            lottieDrawable3.scale = f;
            lottieDrawable3.updateBounds();
        }
        if (obtainStyledAttributes.hasValue(10)) {
            int i = obtainStyledAttributes.getInt(10, 0);
            if (i >= RenderMode.values().length) {
                i = 0;
            }
            setRenderMode(RenderMode.values()[i]);
        }
        if (getScaleType() != null) {
            LottieDrawable lottieDrawable4 = this.lottieDrawable;
            ImageView.ScaleType scaleType = getScaleType();
            Objects.requireNonNull(lottieDrawable4);
            lottieDrawable4.scaleType = scaleType;
        }
        obtainStyledAttributes.recycle();
        LottieDrawable lottieDrawable5 = this.lottieDrawable;
        Context context = getContext();
        PathMeasure pathMeasure = Utils.pathMeasure;
        if (Settings.Global.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f) != 0.0f) {
            z = true;
        }
        Boolean valueOf = Boolean.valueOf(z);
        Objects.requireNonNull(lottieDrawable5);
        lottieDrawable5.systemAnimationsEnabled = valueOf.booleanValue();
        enableOrDisableHardwareLayer();
        this.isInitialized = true;
    }

    public void invalidateDrawable(Drawable drawable) {
        Drawable drawable2 = getDrawable();
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        if (drawable2 == lottieDrawable2) {
            super.invalidateDrawable(lottieDrawable2);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.autoPlay || this.wasAnimatingWhenDetached) {
            playAnimation();
            this.autoPlay = false;
            this.wasAnimatingWhenDetached = false;
        }
    }

    public final void onDetachedFromWindow() {
        if (isAnimating()) {
            cancelAnimation();
            this.wasAnimatingWhenDetached = true;
        }
        super.onDetachedFromWindow();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0038, code lost:
        if (r3.wasAnimatingWhenDetached != false) goto L_0x003a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.os.Parcelable onSaveInstanceState() {
        /*
            r3 = this;
            android.os.Parcelable r0 = super.onSaveInstanceState()
            com.airbnb.lottie.LottieAnimationView$SavedState r1 = new com.airbnb.lottie.LottieAnimationView$SavedState
            r1.<init>((android.os.Parcelable) r0)
            java.lang.String r0 = r3.animationName
            r1.animationName = r0
            int r0 = r3.animationResId
            r1.animationResId = r0
            com.airbnb.lottie.LottieDrawable r0 = r3.lottieDrawable
            java.util.Objects.requireNonNull(r0)
            com.airbnb.lottie.utils.LottieValueAnimator r0 = r0.animator
            float r0 = r0.getAnimatedValueAbsolute()
            r1.progress = r0
            com.airbnb.lottie.LottieDrawable r0 = r3.lottieDrawable
            java.util.Objects.requireNonNull(r0)
            com.airbnb.lottie.utils.LottieValueAnimator r0 = r0.animator
            r2 = 0
            if (r0 != 0) goto L_0x002a
            r0 = r2
            goto L_0x002c
        L_0x002a:
            boolean r0 = r0.running
        L_0x002c:
            if (r0 != 0) goto L_0x003a
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r0 = androidx.core.view.ViewCompat.Api19Impl.isAttachedToWindow(r3)
            if (r0 != 0) goto L_0x003b
            boolean r0 = r3.wasAnimatingWhenDetached
            if (r0 == 0) goto L_0x003b
        L_0x003a:
            r2 = 1
        L_0x003b:
            r1.isAnimating = r2
            com.airbnb.lottie.LottieDrawable r0 = r3.lottieDrawable
            java.util.Objects.requireNonNull(r0)
            java.lang.String r0 = r0.imageAssetsFolder
            r1.imageAssetsFolder = r0
            com.airbnb.lottie.LottieDrawable r0 = r3.lottieDrawable
            java.util.Objects.requireNonNull(r0)
            com.airbnb.lottie.utils.LottieValueAnimator r0 = r0.animator
            int r0 = r0.getRepeatMode()
            r1.repeatMode = r0
            com.airbnb.lottie.LottieDrawable r3 = r3.lottieDrawable
            java.util.Objects.requireNonNull(r3)
            com.airbnb.lottie.utils.LottieValueAnimator r3 = r3.animator
            int r3 = r3.getRepeatCount()
            r1.repeatCount = r3
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieAnimationView.onSaveInstanceState():android.os.Parcelable");
    }

    public void playAnimation() {
        if (isShown()) {
            this.lottieDrawable.playAnimation();
            enableOrDisableHardwareLayer();
            return;
        }
        this.wasAnimatingWhenNotShown = true;
    }

    public void resumeAnimation() {
        if (isShown()) {
            this.lottieDrawable.resumeAnimation();
            enableOrDisableHardwareLayer();
            return;
        }
        this.wasAnimatingWhenNotShown = true;
    }

    public void setAnimation(int i) {
        LottieTask<LottieComposition> lottieTask;
        this.animationResId = i;
        this.animationName = null;
        if (this.cacheComposition) {
            Context context = getContext();
            lottieTask = LottieCompositionFactory.cache(LottieCompositionFactory.rawResCacheKey(context, i), new Callable<LottieResult<LottieComposition>>(new WeakReference(context), context.getApplicationContext(), i) {
                public final /* synthetic */ Context val$appContext;
                public final /* synthetic */ WeakReference val$contextRef;
                public final /* synthetic */ int val$rawRes;

                {
                    this.val$contextRef = r1;
                    this.val$appContext = r2;
                    this.val$rawRes = r3;
                }

                public final Object call() throws Exception {
                    Context context = (Context) this.val$contextRef.get();
                    if (context == null) {
                        context = this.val$appContext;
                    }
                    int i = this.val$rawRes;
                    try {
                        return LottieCompositionFactory.fromJsonInputStreamSync(context.getResources().openRawResource(i), LottieCompositionFactory.rawResCacheKey(context, i));
                    } catch (Resources.NotFoundException e) {
                        return new LottieResult((Throwable) e);
                    }
                }
            });
        } else {
            Context context2 = getContext();
            HashMap hashMap = LottieCompositionFactory.taskCache;
            lottieTask = LottieCompositionFactory.cache((String) null, new Callable<LottieResult<LottieComposition>>(new WeakReference(context2), context2.getApplicationContext(), i) {
                public final /* synthetic */ Context val$appContext;
                public final /* synthetic */ WeakReference val$contextRef;
                public final /* synthetic */ int val$rawRes;

                {
                    this.val$contextRef = r1;
                    this.val$appContext = r2;
                    this.val$rawRes = r3;
                }

                public final Object call() throws Exception {
                    Context context = (Context) this.val$contextRef.get();
                    if (context == null) {
                        context = this.val$appContext;
                    }
                    int i = this.val$rawRes;
                    try {
                        return LottieCompositionFactory.fromJsonInputStreamSync(context.getResources().openRawResource(i), LottieCompositionFactory.rawResCacheKey(context, i));
                    } catch (Resources.NotFoundException e) {
                        return new LottieResult((Throwable) e);
                    }
                }
            });
        }
        setCompositionTask(lottieTask);
    }

    public void setImageBitmap(Bitmap bitmap) {
        cancelLoaderTask();
        super.setImageBitmap(bitmap);
    }

    public void setImageDrawable(Drawable drawable) {
        cancelLoaderTask();
        super.setImageDrawable(drawable);
    }

    public void setImageResource(int i) {
        cancelLoaderTask();
        super.setImageResource(i);
    }

    public void setMinAndMaxFrame(int i, int i2) {
        this.lottieDrawable.setMinAndMaxFrame(i, i2);
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(scaleType);
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        if (lottieDrawable2 != null) {
            Objects.requireNonNull(lottieDrawable2);
            lottieDrawable2.scaleType = scaleType;
        }
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public void setAnimation(String str) {
        LottieTask<LottieComposition> lottieTask;
        this.animationName = str;
        this.animationResId = 0;
        if (this.cacheComposition) {
            Context context = getContext();
            HashMap hashMap = LottieCompositionFactory.taskCache;
            String m = SupportMenuInflater$$ExternalSyntheticOutline0.m4m("asset_", str);
            lottieTask = LottieCompositionFactory.cache(m, new Callable<LottieResult<LottieComposition>>(context.getApplicationContext(), str, m) {
                public final /* synthetic */ Context val$appContext;
                public final /* synthetic */ String val$cacheKey;
                public final /* synthetic */ String val$fileName;

                {
                    this.val$appContext = r1;
                    this.val$fileName = r2;
                    this.val$cacheKey = r3;
                }

                public final Object call() throws Exception {
                    ZipInputStream zipInputStream;
                    Context context = this.val$appContext;
                    String str = this.val$fileName;
                    String str2 = this.val$cacheKey;
                    try {
                        if (!str.endsWith(".zip")) {
                            return LottieCompositionFactory.fromJsonInputStreamSync(context.getAssets().open(str), str2);
                        }
                        zipInputStream = new ZipInputStream(context.getAssets().open(str));
                        LottieResult<LottieComposition> fromZipStreamSyncInternal = LottieCompositionFactory.fromZipStreamSyncInternal(zipInputStream, str2);
                        Utils.closeQuietly(zipInputStream);
                        return fromZipStreamSyncInternal;
                    } catch (IOException e) {
                        return new LottieResult((Throwable) e);
                    } catch (Throwable th) {
                        Utils.closeQuietly(zipInputStream);
                        throw th;
                    }
                }
            });
        } else {
            Context context2 = getContext();
            HashMap hashMap2 = LottieCompositionFactory.taskCache;
            lottieTask = LottieCompositionFactory.cache((String) null, new Callable<LottieResult<LottieComposition>>(context2.getApplicationContext(), str, (String) null) {
                public final /* synthetic */ Context val$appContext;
                public final /* synthetic */ String val$cacheKey;
                public final /* synthetic */ String val$fileName;

                {
                    this.val$appContext = r1;
                    this.val$fileName = r2;
                    this.val$cacheKey = r3;
                }

                public final Object call() throws Exception {
                    ZipInputStream zipInputStream;
                    Context context = this.val$appContext;
                    String str = this.val$fileName;
                    String str2 = this.val$cacheKey;
                    try {
                        if (!str.endsWith(".zip")) {
                            return LottieCompositionFactory.fromJsonInputStreamSync(context.getAssets().open(str), str2);
                        }
                        zipInputStream = new ZipInputStream(context.getAssets().open(str));
                        LottieResult<LottieComposition> fromZipStreamSyncInternal = LottieCompositionFactory.fromZipStreamSyncInternal(zipInputStream, str2);
                        Utils.closeQuietly(zipInputStream);
                        return fromZipStreamSyncInternal;
                    } catch (IOException e) {
                        return new LottieResult((Throwable) e);
                    } catch (Throwable th) {
                        Utils.closeQuietly(zipInputStream);
                        throw th;
                    }
                }
            });
        }
        setCompositionTask(lottieTask);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public void setCacheComposition(boolean z) {
        this.cacheComposition = z;
    }

    public void setFailureListener(LottieListener<Throwable> lottieListener) {
        this.failureListener = lottieListener;
    }

    public void setFallbackResource(int i) {
        this.fallbackResource = i;
    }

    public LottieComposition getComposition() {
        return this.composition;
    }
}
