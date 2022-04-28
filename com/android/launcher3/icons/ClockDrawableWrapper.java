package com.android.launcher3.icons;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;

@TargetApi(26)
public final class ClockDrawableWrapper extends AdaptiveIconDrawable implements BitmapInfo.Extender {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long TICK_MS = TimeUnit.MINUTES.toMillis(1);
    public final AnimationInfo mAnimationInfo = new AnimationInfo(0);
    public AnimationInfo mThemeInfo = null;

    public static class AnimationInfo {
        public Drawable.ConstantState baseDrawableState;
        public int defaultHour;
        public int defaultMinute;
        public int defaultSecond;
        public int hourLayerIndex;
        public int minuteLayerIndex;
        public int secondLayerIndex;

        public AnimationInfo() {
        }

        public AnimationInfo(int i) {
        }

        public final AnimationInfo copyForIcon(Drawable drawable) {
            AnimationInfo animationInfo = new AnimationInfo();
            animationInfo.baseDrawableState = drawable.getConstantState();
            animationInfo.defaultHour = this.defaultHour;
            animationInfo.defaultMinute = this.defaultMinute;
            animationInfo.defaultSecond = this.defaultSecond;
            animationInfo.hourLayerIndex = this.hourLayerIndex;
            animationInfo.minuteLayerIndex = this.minuteLayerIndex;
            animationInfo.secondLayerIndex = this.secondLayerIndex;
            return animationInfo;
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x006d  */
        /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean applyTime(java.util.Calendar r9, android.graphics.drawable.LayerDrawable r10) {
            /*
                r8 = this;
                long r0 = java.lang.System.currentTimeMillis()
                r9.setTimeInMillis(r0)
                r0 = 10
                int r1 = r9.get(r0)
                int r2 = r8.defaultHour
                r3 = 12
                int r2 = 12 - r2
                int r2 = r2 + r1
                int r2 = r2 % r3
                int r1 = r9.get(r3)
                int r4 = r8.defaultMinute
                int r4 = 60 - r4
                int r4 = r4 + r1
                int r4 = r4 % 60
                r1 = 13
                int r1 = r9.get(r1)
                int r5 = r8.defaultSecond
                int r5 = 60 - r5
                int r5 = r5 + r1
                int r5 = r5 % 60
                int r1 = r8.hourLayerIndex
                r6 = 1
                r7 = -1
                if (r1 == r7) goto L_0x0046
                android.graphics.drawable.Drawable r1 = r10.getDrawable(r1)
                int r2 = r2 * 60
                int r3 = r9.get(r3)
                int r3 = r3 + r2
                boolean r1 = r1.setLevel(r3)
                if (r1 == 0) goto L_0x0046
                r1 = r6
                goto L_0x0047
            L_0x0046:
                r1 = 0
            L_0x0047:
                int r2 = r8.minuteLayerIndex
                if (r2 == r7) goto L_0x005d
                android.graphics.drawable.Drawable r2 = r10.getDrawable(r2)
                int r9 = r9.get(r0)
                int r9 = r9 * 60
                int r9 = r9 + r4
                boolean r9 = r2.setLevel(r9)
                if (r9 == 0) goto L_0x005d
                r1 = r6
            L_0x005d:
                int r8 = r8.secondLayerIndex
                if (r8 == r7) goto L_0x006d
                android.graphics.drawable.Drawable r8 = r10.getDrawable(r8)
                int r5 = r5 * r0
                boolean r8 = r8.setLevel(r5)
                if (r8 == 0) goto L_0x006d
                goto L_0x006e
            L_0x006d:
                r6 = r1
            L_0x006e:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.launcher3.icons.ClockDrawableWrapper.AnimationInfo.applyTime(java.util.Calendar, android.graphics.drawable.LayerDrawable):boolean");
        }
    }

    public static class ClockBitmapInfo extends BitmapInfo {
        public final AnimationInfo animInfo;
        public final float boundsOffset;
        public final Bitmap mFlattenedBackground;
        public final Bitmap themeBackground;
        public final AnimationInfo themeData;

        public final BitmapInfo clone() {
            ClockBitmapInfo clockBitmapInfo = new ClockBitmapInfo(this.icon, this.color, 1.0f - (this.boundsOffset * 2.0f), this.animInfo, this.mFlattenedBackground, this.themeData, this.themeBackground);
            clockBitmapInfo.mMono = this.mMono;
            clockBitmapInfo.mWhiteShadowLayer = this.mWhiteShadowLayer;
            clockBitmapInfo.flags = this.flags;
            clockBitmapInfo.badgeInfo = this.badgeInfo;
            return clockBitmapInfo;
        }

        @TargetApi(10000)
        public final FastBitmapDrawable newIcon(Context context) {
            AnimationInfo animationInfo = this.animInfo;
            Bitmap bitmap = this.mFlattenedBackground;
            if (animationInfo == null) {
                return super.newIcon(context);
            }
            FastBitmapDrawable newDrawable = new ClockIconDrawable.ClockConstantState(this.icon, this.color, this.boundsOffset, animationInfo, bitmap, (ColorFilter) null).newDrawable();
            applyFlags(context, newDrawable);
            return newDrawable;
        }

        public ClockBitmapInfo(Bitmap bitmap, int i, float f, AnimationInfo animationInfo, Bitmap bitmap2, AnimationInfo animationInfo2, Bitmap bitmap3) {
            super(bitmap, i);
            this.boundsOffset = Math.max(0.035f, (1.0f - f) / 2.0f);
            this.animInfo = animationInfo;
            this.mFlattenedBackground = bitmap2;
            this.themeData = animationInfo2;
            this.themeBackground = bitmap3;
        }
    }

    public static class ClockIconDrawable extends FastBitmapDrawable implements Runnable {
        public final AnimationInfo mAnimInfo;
        public final Bitmap mBG;
        public final Paint mBgPaint;
        public final float mBoundsOffset;
        public final float mCanvasScale;
        public final LayerDrawable mFG;
        public final AdaptiveIconDrawable mFullDrawable;
        public final Calendar mTime = Calendar.getInstance();

        public static class ClockConstantState extends FastBitmapDrawable.FastBitmapConstantState {
            public final AnimationInfo mAnimInfo;
            public final Bitmap mBG;
            public final ColorFilter mBgFilter;
            public final float mBoundsOffset;

            public final FastBitmapDrawable createDrawable() {
                return new ClockIconDrawable(this);
            }

            public ClockConstantState(Bitmap bitmap, int i, float f, AnimationInfo animationInfo, Bitmap bitmap2, ColorFilter colorFilter) {
                super(bitmap, i);
                this.mBoundsOffset = f;
                this.mAnimInfo = animationInfo;
                this.mBG = bitmap2;
                this.mBgFilter = colorFilter;
            }
        }

        public ClockIconDrawable(ClockConstantState clockConstantState) {
            super(clockConstantState.mBitmap, clockConstantState.mIconColor);
            Paint paint = new Paint(3);
            this.mBgPaint = paint;
            float f = clockConstantState.mBoundsOffset;
            this.mBoundsOffset = f;
            AnimationInfo animationInfo = clockConstantState.mAnimInfo;
            this.mAnimInfo = animationInfo;
            this.mBG = clockConstantState.mBG;
            paint.setColorFilter(clockConstantState.mBgFilter);
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) animationInfo.baseDrawableState.newDrawable();
            this.mFullDrawable = adaptiveIconDrawable;
            this.mFG = (LayerDrawable) adaptiveIconDrawable.getForeground();
            this.mCanvasScale = 1.0f - (f * 2.0f);
        }

        public final void drawInternal(Canvas canvas, Rect rect) {
            if (this.mAnimInfo == null) {
                canvas.drawBitmap(this.mBitmap, (Rect) null, rect, this.mPaint);
                return;
            }
            canvas.drawBitmap(this.mBG, (Rect) null, rect, this.mBgPaint);
            this.mAnimInfo.applyTime(this.mTime, this.mFG);
            int save = canvas.save();
            canvas.translate((float) rect.left, (float) rect.top);
            float f = this.mCanvasScale;
            canvas.scale(f, f, (float) (rect.width() / 2), (float) (rect.height() / 2));
            canvas.clipPath(this.mFullDrawable.getIconMask());
            this.mFG.draw(canvas);
            canvas.restoreToCount(save);
            reschedule();
        }

        public final FastBitmapDrawable.FastBitmapConstantState newConstantState() {
            return new ClockConstantState(this.mBitmap, this.mIconColor, this.mBoundsOffset, this.mAnimInfo, this.mBG, this.mBgPaint.getColorFilter());
        }

        public final void run() {
            if (this.mAnimInfo.applyTime(this.mTime, this.mFG)) {
                invalidateSelf();
            } else {
                reschedule();
            }
        }

        public final void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
            this.mFullDrawable.setBounds(0, 0, rect.width(), rect.height());
        }

        public final void reschedule() {
            if (isVisible()) {
                unscheduleSelf(this);
                long uptimeMillis = SystemClock.uptimeMillis();
                long j = ClockDrawableWrapper.TICK_MS;
                scheduleSelf(this, (uptimeMillis - (uptimeMillis % j)) + j);
            }
        }

        public final boolean setVisible(boolean z, boolean z2) {
            boolean visible = super.setVisible(z, z2);
            if (z) {
                reschedule();
            } else {
                unscheduleSelf(this);
            }
            return visible;
        }

        public final void updateFilter() {
            super.updateFilter();
            this.mFullDrawable.setColorFilter(this.mPaint.getColorFilter());
        }
    }

    @TargetApi(10000)
    public static ClockDrawableWrapper forExtras(Bundle bundle, IntFunction<Drawable> intFunction) {
        int i;
        if (bundle == null || (i = bundle.getInt("com.android.launcher3.LEVEL_PER_TICK_ICON_ROUND", 0)) == 0) {
            return null;
        }
        Drawable mutate = intFunction.apply(i).mutate();
        if (!(mutate instanceof AdaptiveIconDrawable)) {
            return null;
        }
        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) mutate;
        ClockDrawableWrapper clockDrawableWrapper = new ClockDrawableWrapper(adaptiveIconDrawable);
        AnimationInfo animationInfo = clockDrawableWrapper.mAnimationInfo;
        animationInfo.baseDrawableState = mutate.getConstantState();
        animationInfo.hourLayerIndex = bundle.getInt("com.android.launcher3.HOUR_LAYER_INDEX", -1);
        animationInfo.minuteLayerIndex = bundle.getInt("com.android.launcher3.MINUTE_LAYER_INDEX", -1);
        animationInfo.secondLayerIndex = bundle.getInt("com.android.launcher3.SECOND_LAYER_INDEX", -1);
        animationInfo.defaultHour = bundle.getInt("com.android.launcher3.DEFAULT_HOUR", 0);
        animationInfo.defaultMinute = bundle.getInt("com.android.launcher3.DEFAULT_MINUTE", 0);
        animationInfo.defaultSecond = bundle.getInt("com.android.launcher3.DEFAULT_SECOND", 0);
        LayerDrawable layerDrawable = (LayerDrawable) clockDrawableWrapper.getForeground();
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        int i2 = animationInfo.hourLayerIndex;
        if (i2 < 0 || i2 >= numberOfLayers) {
            animationInfo.hourLayerIndex = -1;
        }
        int i3 = animationInfo.minuteLayerIndex;
        if (i3 < 0 || i3 >= numberOfLayers) {
            animationInfo.minuteLayerIndex = -1;
        }
        int i4 = animationInfo.secondLayerIndex;
        if (i4 < 0 || i4 >= numberOfLayers) {
            animationInfo.secondLayerIndex = -1;
        } else {
            layerDrawable.setDrawable(i4, (Drawable) null);
            animationInfo.secondLayerIndex = -1;
        }
        if (IconProvider.ATLEAST_T && (adaptiveIconDrawable.getMonochrome() instanceof LayerDrawable)) {
            clockDrawableWrapper.mThemeInfo = animationInfo.copyForIcon(new AdaptiveIconDrawable(new ColorDrawable(-1), adaptiveIconDrawable.getMonochrome().mutate()));
        }
        animationInfo.applyTime(Calendar.getInstance(), layerDrawable);
        return clockDrawableWrapper;
    }

    public final ClockBitmapInfo getExtendedInfo(Bitmap bitmap, int i, BaseIconFactory baseIconFactory, float f) {
        return new ClockBitmapInfo(bitmap, i, f, this.mAnimationInfo, baseIconFactory.createScaledBitmapWithShadow(new AdaptiveIconDrawable(getBackground().getConstantState().newDrawable(), (Drawable) null)), (AnimationInfo) null, (Bitmap) null);
    }

    public final Drawable getMonochrome() {
        AnimationInfo animationInfo = this.mThemeInfo;
        if (animationInfo == null) {
            return null;
        }
        Drawable mutate = animationInfo.baseDrawableState.newDrawable().mutate();
        if (!(mutate instanceof AdaptiveIconDrawable)) {
            return null;
        }
        Drawable foreground = ((AdaptiveIconDrawable) mutate).getForeground();
        this.mThemeInfo.applyTime(Calendar.getInstance(), (LayerDrawable) foreground);
        return foreground;
    }

    public ClockDrawableWrapper(AdaptiveIconDrawable adaptiveIconDrawable) {
        super(adaptiveIconDrawable.getBackground(), adaptiveIconDrawable.getForeground());
    }

    public final void drawForPersistence(Canvas canvas) {
        LayerDrawable layerDrawable = (LayerDrawable) getForeground();
        int i = this.mAnimationInfo.hourLayerIndex;
        if (i != -1) {
            layerDrawable.getDrawable(i).setLevel(0);
        }
        int i2 = this.mAnimationInfo.minuteLayerIndex;
        if (i2 != -1) {
            layerDrawable.getDrawable(i2).setLevel(0);
        }
        int i3 = this.mAnimationInfo.secondLayerIndex;
        if (i3 != -1) {
            layerDrawable.getDrawable(i3).setLevel(0);
        }
        draw(canvas);
        this.mAnimationInfo.applyTime(Calendar.getInstance(), (LayerDrawable) getForeground());
    }
}
