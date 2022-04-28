package com.google.android.systemui.assist.uihints;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.text.SpannableStringBuilder;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.TextView;
import androidx.leanback.R$color;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.assist.DeviceConfigHelper;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda34;
import com.google.android.systemui.assist.uihints.TranscriptionController;
import com.google.common.util.concurrent.ImmediateFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.ArrayList;
import java.util.Objects;
import kotlinx.atomicfu.AtomicFU;

public class TranscriptionView extends TextView implements TranscriptionController.TranscriptionSpaceView {
    public static final PathInterpolator INTERPOLATOR_SCROLL = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);
    public final float BUMPER_DISTANCE_END_PX;
    public final float BUMPER_DISTANCE_START_PX;
    public final float FADE_DISTANCE_END_PX;
    public final float FADE_DISTANCE_START_PX;
    public final int TEXT_COLOR_DARK;
    public final int TEXT_COLOR_LIGHT;
    public boolean mCardVisible;
    public DeviceConfigHelper mDeviceConfigHelper;
    public int mDisplayWidthPx;
    public boolean mHasDarkBackground;
    public SettableFuture<Void> mHideFuture;
    public Matrix mMatrix;
    public int mRequestedTextColor;
    public float[] mStops;
    public ValueAnimator mTranscriptionAnimation;
    public TranscriptionAnimator mTranscriptionAnimator;
    public SpannableStringBuilder mTranscriptionBuilder;
    public AnimatorSet mVisibilityAnimators;

    public class TranscriptionAnimator implements ValueAnimator.AnimatorUpdateListener {
        public float mDistance;
        public ArrayList mSpans = new ArrayList();
        public float mStartX;

        public TranscriptionAnimator() {
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            if (Math.abs(floatValue - this.mStartX) < Math.abs(this.mDistance)) {
                TranscriptionView.this.setX(floatValue);
                TranscriptionView transcriptionView = TranscriptionView.this;
                PathInterpolator pathInterpolator = TranscriptionView.INTERPOLATOR_SCROLL;
                transcriptionView.updateColor();
            }
            this.mSpans.forEach(new StatusBar$$ExternalSyntheticLambda34(valueAnimator, 4));
            TranscriptionView.this.invalidate();
        }
    }

    public class TranscriptionSpan extends ReplacementSpan {
        public float mCurrentFraction = 0.0f;
        public float mStartFraction = 0.0f;

        public TranscriptionSpan() {
        }

        public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            float f2 = this.mStartFraction;
            float f3 = 1.0f;
            if (f2 != 1.0f) {
                f3 = AtomicFU.clamp((((1.0f - f2) / 1.0f) * this.mCurrentFraction) + f2, 0.0f, 1.0f);
            }
            Paint paint2 = paint;
            paint2.setAlpha((int) Math.ceil((double) (f3 * 255.0f)));
            canvas.drawText(charSequence, i, i2, f, (float) i4, paint2);
        }

        public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            return (int) Math.ceil((double) TranscriptionView.this.getPaint().measureText(charSequence, 0, charSequence.length()));
        }

        public TranscriptionSpan(TranscriptionSpan transcriptionSpan) {
            Objects.requireNonNull(transcriptionSpan);
            this.mStartFraction = AtomicFU.clamp(transcriptionSpan.mCurrentFraction, 0.0f, 1.0f);
        }
    }

    public TranscriptionView(Context context) {
        this(context, (AttributeSet) null);
    }

    @VisibleForTesting
    public static float interpolate(long j, long j2, float f) {
        return (((float) (j2 - j)) * f) + ((float) j);
    }

    public TranscriptionView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @VisibleForTesting
    public long getAdaptiveDuration(float f, float f2) {
        Objects.requireNonNull(this.mDeviceConfigHelper);
        long j = DeviceConfigHelper.getLong("assist_transcription_duration_per_px_regular", 4);
        Objects.requireNonNull(this.mDeviceConfigHelper);
        float interpolate = interpolate(j, DeviceConfigHelper.getLong("assist_transcription_duration_per_px_fast", 3), f / f2);
        Objects.requireNonNull(this.mDeviceConfigHelper);
        long j2 = DeviceConfigHelper.getLong("assist_transcription_max_duration", 400);
        Objects.requireNonNull(this.mDeviceConfigHelper);
        return Math.min(j2, Math.max(DeviceConfigHelper.getLong("assist_transcription_min_duration", 20), (long) (f * interpolate)));
    }

    public final float getFullyVisibleDistance(float f) {
        int i = this.mDisplayWidthPx;
        float f2 = this.BUMPER_DISTANCE_END_PX;
        float f3 = this.FADE_DISTANCE_END_PX;
        if (f < ((float) i) - (((this.BUMPER_DISTANCE_START_PX + f2) + f3) + this.FADE_DISTANCE_START_PX)) {
            return (((float) i) - f) / 2.0f;
        }
        return ((((float) i) - f) - f3) - f2;
    }

    public final ListenableFuture<Void> hide(boolean z) {
        SettableFuture<Void> settableFuture = this.mHideFuture;
        if (settableFuture != null && !settableFuture.isDone()) {
            return this.mHideFuture;
        }
        this.mHideFuture = new SettableFuture<>();
        final QSTileImpl$$ExternalSyntheticLambda1 qSTileImpl$$ExternalSyntheticLambda1 = new QSTileImpl$$ExternalSyntheticLambda1(this, 6);
        if (!z) {
            if (this.mVisibilityAnimators.isRunning()) {
                this.mVisibilityAnimators.end();
            } else {
                qSTileImpl$$ExternalSyntheticLambda1.run();
            }
            return ImmediateFuture.NULL;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        this.mVisibilityAnimators = animatorSet;
        animatorSet.play(ObjectAnimator.ofFloat(this, View.ALPHA, new float[]{getAlpha(), 0.0f}).setDuration(400));
        if (!this.mCardVisible) {
            this.mVisibilityAnimators.play(ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, new float[]{getTranslationY(), (float) (getHeight() * -1)}).setDuration(700));
        }
        this.mVisibilityAnimators.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                qSTileImpl$$ExternalSyntheticLambda1.run();
            }
        });
        this.mVisibilityAnimators.start();
        return this.mHideFuture;
    }

    public final void onFontSizeChanged() {
        setTextSize(0, this.mContext.getResources().getDimension(C1777R.dimen.transcription_text_size));
    }

    public final void setHasDarkBackground(boolean z) {
        if (z != this.mHasDarkBackground) {
            this.mHasDarkBackground = z;
            updateColor();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: com.google.android.systemui.assist.uihints.TranscriptionView$TranscriptionSpan} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x01d6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setTranscription(java.lang.String r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r7 = 0
            r0.setVisibility(r7)
            r17.updateDisplayWidth()
            android.animation.ValueAnimator r2 = r0.mTranscriptionAnimation
            r8 = 1
            if (r2 == 0) goto L_0x0018
            boolean r2 = r2.isRunning()
            if (r2 == 0) goto L_0x0018
            r9 = r8
            goto L_0x0019
        L_0x0018:
            r9 = r7
        L_0x0019:
            if (r9 == 0) goto L_0x0020
            android.animation.ValueAnimator r2 = r0.mTranscriptionAnimation
            r2.cancel()
        L_0x0020:
            android.text.SpannableStringBuilder r2 = r0.mTranscriptionBuilder
            java.lang.String r2 = r2.toString()
            boolean r10 = r2.isEmpty()
            android.text.SpannableStringBuilder r2 = r0.mTranscriptionBuilder
            java.lang.String r2 = r2.toString()
            if (r2 == 0) goto L_0x003b
            boolean r3 = r2.isEmpty()
            if (r3 == 0) goto L_0x0039
            goto L_0x003b
        L_0x0039:
            r3 = r7
            goto L_0x003c
        L_0x003b:
            r3 = r8
        L_0x003c:
            r11 = 2
            if (r3 != 0) goto L_0x00bc
            if (r1 == 0) goto L_0x004a
            boolean r3 = r18.isEmpty()
            if (r3 == 0) goto L_0x0048
            goto L_0x004a
        L_0x0048:
            r3 = r7
            goto L_0x004b
        L_0x004a:
            r3 = r8
        L_0x004b:
            if (r3 == 0) goto L_0x004f
            goto L_0x00bc
        L_0x004f:
            java.lang.String r3 = r2.toLowerCase()
            java.lang.String r4 = r18.toLowerCase()
            int r5 = r3.length()
            int r6 = r4.length()
            int[] r12 = new int[r11]
            r12[r8] = r6
            r12[r7] = r5
            java.lang.Class<int> r5 = int.class
            java.lang.Object r5 = java.lang.reflect.Array.newInstance(r5, r12)
            r6 = r5
            int[][] r6 = (int[][]) r6
            r5 = r7
        L_0x006f:
            int r12 = r3.length()
            if (r5 >= r12) goto L_0x00a7
            char r12 = r3.charAt(r5)
            r13 = r7
        L_0x007a:
            int r14 = r4.length()
            if (r13 >= r14) goto L_0x00a4
            char r14 = r4.charAt(r13)
            if (r12 != r14) goto L_0x00a1
            r14 = 32
            if (r12 != r14) goto L_0x008c
            r14 = r7
            goto L_0x008d
        L_0x008c:
            r14 = r8
        L_0x008d:
            if (r5 == 0) goto L_0x009b
            if (r13 != 0) goto L_0x0092
            goto L_0x009b
        L_0x0092:
            int r15 = r5 + -1
            r15 = r6[r15]
            int r16 = r13 + -1
            r15 = r15[r16]
            goto L_0x009c
        L_0x009b:
            r15 = r7
        L_0x009c:
            r16 = r6[r5]
            int r15 = r15 + r14
            r16[r13] = r15
        L_0x00a1:
            int r13 = r13 + 1
            goto L_0x007a
        L_0x00a4:
            int r5 = r5 + 1
            goto L_0x006f
        L_0x00a7:
            r3 = 0
            int r4 = r2.length()
            r5 = 0
            int r12 = r18.length()
            r1 = r18
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r12
            com.google.android.systemui.assist.uihints.StringUtils$StringStabilityInfo r1 = com.google.android.systemui.assist.uihints.StringUtils.getRightMostStabilityInfoLeaf(r1, r2, r3, r4, r5, r6)
            goto L_0x00c2
        L_0x00bc:
            com.google.android.systemui.assist.uihints.StringUtils$StringStabilityInfo r2 = new com.google.android.systemui.assist.uihints.StringUtils$StringStabilityInfo
            r2.<init>(r1)
            r1 = r2
        L_0x00c2:
            android.text.SpannableStringBuilder r2 = r0.mTranscriptionBuilder
            r2.clear()
            android.text.SpannableStringBuilder r2 = r0.mTranscriptionBuilder
            java.lang.String r3 = r1.stable
            r2.append(r3)
            android.text.SpannableStringBuilder r2 = r0.mTranscriptionBuilder
            java.lang.String r3 = r1.unstable
            r2.append(r3)
            android.text.TextPaint r2 = r17.getPaint()
            android.text.SpannableStringBuilder r3 = r0.mTranscriptionBuilder
            java.lang.String r3 = r3.toString()
            float r2 = r2.measureText(r3)
            double r2 = (double) r2
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            android.view.ViewGroup$LayoutParams r3 = r17.getLayoutParams()
            if (r3 == 0) goto L_0x00f4
            r3.width = r2
            r0.setLayoutParams(r3)
        L_0x00f4:
            r17.updateColor()
            r3 = 0
            if (r10 != 0) goto L_0x01d6
            java.lang.String r4 = r1.stable
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L_0x0104
            goto L_0x01d6
        L_0x0104:
            java.lang.String r2 = r1.stable
            int r2 = r2.length()
            if (r9 == 0) goto L_0x014b
            java.lang.String r4 = r1.stable
            java.lang.String r5 = " "
            boolean r4 = r4.endsWith(r5)
            if (r4 != 0) goto L_0x014b
            java.lang.String r4 = r1.unstable
            boolean r4 = r4.startsWith(r5)
            if (r4 != 0) goto L_0x014b
            java.lang.String r1 = r1.stable
            java.lang.String r4 = "\\s+"
            java.lang.String[] r1 = r1.split(r4)
            int r4 = r1.length
            if (r4 <= 0) goto L_0x0132
            int r4 = r1.length
            int r4 = r4 - r8
            r1 = r1[r4]
            int r1 = r1.length()
            int r2 = r2 - r1
        L_0x0132:
            com.google.android.systemui.assist.uihints.TranscriptionView$TranscriptionAnimator r1 = r0.mTranscriptionAnimator
            java.util.Objects.requireNonNull(r1)
            java.util.ArrayList r1 = r1.mSpans
            boolean r4 = r1.isEmpty()
            if (r4 != 0) goto L_0x014b
            int r3 = r1.size()
            int r3 = r3 - r8
            java.lang.Object r1 = r1.get(r3)
            r3 = r1
            com.google.android.systemui.assist.uihints.TranscriptionView$TranscriptionSpan r3 = (com.google.android.systemui.assist.uihints.TranscriptionView.TranscriptionSpan) r3
        L_0x014b:
            r0.setUpSpans(r2, r3)
            com.google.android.systemui.assist.uihints.TranscriptionView$TranscriptionAnimator r1 = r0.mTranscriptionAnimator
            java.util.Objects.requireNonNull(r1)
            com.google.android.systemui.assist.uihints.TranscriptionView r2 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            android.text.TextPaint r2 = r2.getPaint()
            com.google.android.systemui.assist.uihints.TranscriptionView r3 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            android.text.SpannableStringBuilder r3 = r3.mTranscriptionBuilder
            java.lang.String r3 = r3.toString()
            float r2 = r2.measureText(r3)
            com.google.android.systemui.assist.uihints.TranscriptionView r3 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            float r3 = r3.getX()
            r1.mStartX = r3
            com.google.android.systemui.assist.uihints.TranscriptionView r3 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            float r3 = r3.getFullyVisibleDistance(r2)
            float r4 = r1.mStartX
            float r3 = r3 - r4
            r1.mDistance = r3
            com.google.android.systemui.assist.uihints.TranscriptionView r3 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            r3.updateColor()
            com.google.android.systemui.assist.uihints.TranscriptionView r3 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            float r4 = r1.mDistance
            float r4 = java.lang.Math.abs(r4)
            com.google.android.systemui.assist.uihints.TranscriptionView r5 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            int r5 = r5.mDisplayWidthPx
            float r5 = (float) r5
            long r3 = r3.getAdaptiveDuration(r4, r5)
            com.google.android.systemui.assist.uihints.TranscriptionView r5 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            int r6 = r5.mDisplayWidthPx
            float r6 = (float) r6
            float r5 = r5.getX()
            float r6 = r6 - r5
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x01b0
            com.google.android.systemui.assist.uihints.TranscriptionView r2 = com.google.android.systemui.assist.uihints.TranscriptionView.this
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.assist.DeviceConfigHelper r2 = r2.mDeviceConfigHelper
            r5 = 50
            java.util.Objects.requireNonNull(r2)
            java.lang.String r2 = "assist_transcription_fade_in_duration"
            long r5 = com.android.systemui.assist.DeviceConfigHelper.getLong(r2, r5)
            long r5 = r5 + r3
            goto L_0x01b1
        L_0x01b0:
            r5 = r3
        L_0x01b1:
            float r2 = r1.mDistance
            float r9 = (float) r5
            float r3 = (float) r3
            float r9 = r9 / r3
            float r9 = r9 * r2
            float[] r2 = new float[r11]
            float r3 = r1.mStartX
            r2[r7] = r3
            float r3 = r3 + r9
            r2[r8] = r3
            android.animation.ValueAnimator r2 = android.animation.ValueAnimator.ofFloat(r2)
            android.animation.ValueAnimator r2 = r2.setDuration(r5)
            android.view.animation.PathInterpolator r3 = INTERPOLATOR_SCROLL
            r2.setInterpolator(r3)
            r2.addUpdateListener(r1)
            r0.mTranscriptionAnimation = r2
            r2.start()
            goto L_0x01f1
        L_0x01d6:
            java.lang.String r4 = r1.stable
            int r4 = r4.length()
            java.lang.String r1 = r1.unstable
            int r1 = r1.length()
            int r1 = r1 + r4
            r0.setUpSpans(r1, r3)
            float r1 = (float) r2
            float r1 = r0.getFullyVisibleDistance(r1)
            r0.setX(r1)
            r17.updateColor()
        L_0x01f1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.TranscriptionView.setTranscription(java.lang.String):void");
    }

    public final void setUpSpans(int i, TranscriptionSpan transcriptionSpan) {
        TranscriptionSpan transcriptionSpan2;
        TranscriptionAnimator transcriptionAnimator = this.mTranscriptionAnimator;
        Objects.requireNonNull(transcriptionAnimator);
        transcriptionAnimator.mSpans.clear();
        String spannableStringBuilder = this.mTranscriptionBuilder.toString();
        String substring = spannableStringBuilder.substring(i);
        if (substring.length() > 0) {
            int indexOf = spannableStringBuilder.indexOf(substring, i);
            int length = substring.length() + indexOf;
            if (transcriptionSpan == null) {
                transcriptionSpan2 = new TranscriptionSpan();
            } else {
                transcriptionSpan2 = new TranscriptionSpan(transcriptionSpan);
            }
            this.mTranscriptionBuilder.setSpan(transcriptionSpan2, indexOf, length, 33);
            TranscriptionAnimator transcriptionAnimator2 = this.mTranscriptionAnimator;
            Objects.requireNonNull(transcriptionAnimator2);
            transcriptionAnimator2.mSpans.add(transcriptionSpan2);
        }
        setText(this.mTranscriptionBuilder, TextView.BufferType.SPANNABLE);
        updateColor();
    }

    public final void updateColor() {
        int i = this.mRequestedTextColor;
        if (i == 0) {
            if (this.mHasDarkBackground) {
                i = this.TEXT_COLOR_DARK;
            } else {
                i = this.TEXT_COLOR_LIGHT;
            }
        }
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, (float) this.mDisplayWidthPx, 0.0f, new int[]{0, i, i, 0}, this.mStops, Shader.TileMode.CLAMP);
        this.mMatrix.setTranslate(-getTranslationX(), 0.0f);
        linearGradient.setLocalMatrix(this.mMatrix);
        getPaint().setShader(linearGradient);
        invalidate();
    }

    public final void updateDisplayWidth() {
        Display defaultDisplay = R$color.getDefaultDisplay(this.mContext);
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int i = point.x;
        this.mDisplayWidthPx = i;
        float f = this.BUMPER_DISTANCE_START_PX;
        float f2 = (float) i;
        this.mStops = new float[]{f / f2, (f + this.FADE_DISTANCE_START_PX) / f2, ((f2 - this.FADE_DISTANCE_END_PX) - this.BUMPER_DISTANCE_END_PX) / f2, 1.0f};
        updateColor();
    }

    public TranscriptionView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String spannableStringBuilder = this.mTranscriptionBuilder.toString();
        setTranscription("");
        this.mTranscriptionAnimator = new TranscriptionAnimator();
        setTranscription(spannableStringBuilder);
    }

    public TranscriptionView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTranscriptionBuilder = new SpannableStringBuilder();
        this.mVisibilityAnimators = new AnimatorSet();
        this.mHideFuture = null;
        this.mHasDarkBackground = false;
        this.mCardVisible = false;
        this.mRequestedTextColor = 0;
        this.mMatrix = new Matrix();
        this.mDisplayWidthPx = 0;
        this.mTranscriptionAnimator = new TranscriptionAnimator();
        initializeDeviceConfigHelper(new DeviceConfigHelper());
        this.BUMPER_DISTANCE_START_PX = context.getResources().getDimension(C1777R.dimen.zerostate_icon_tap_padding) + context.getResources().getDimension(C1777R.dimen.zerostate_icon_left_margin);
        this.BUMPER_DISTANCE_END_PX = context.getResources().getDimension(C1777R.dimen.keyboard_icon_tap_padding) + context.getResources().getDimension(C1777R.dimen.keyboard_icon_right_margin);
        this.FADE_DISTANCE_START_PX = context.getResources().getDimension(C1777R.dimen.zerostate_icon_size);
        this.FADE_DISTANCE_END_PX = context.getResources().getDimension(C1777R.dimen.keyboard_icon_size) / 2.0f;
        this.TEXT_COLOR_DARK = context.getResources().getColor(C1777R.color.transcription_text_dark);
        this.TEXT_COLOR_LIGHT = context.getResources().getColor(C1777R.color.transcription_text_light);
        updateDisplayWidth();
        setHasDarkBackground(!this.mHasDarkBackground);
    }

    @VisibleForTesting
    public void initializeDeviceConfigHelper(DeviceConfigHelper deviceConfigHelper) {
        this.mDeviceConfigHelper = deviceConfigHelper;
    }

    public final void setCardVisible(boolean z) {
        this.mCardVisible = z;
    }
}
