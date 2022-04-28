package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import androidx.constraintlayout.motion.utils.ArcCurveFit;
import androidx.constraintlayout.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.MotionController;
import androidx.constraintlayout.motion.widget.MotionInterpolator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionPaths;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;
import java.util.Objects;

public class MotionTelltales extends MockView {
    public Matrix mInvertMatrix = new Matrix();
    public MotionLayout mMotionLayout;
    public Paint mPaintTelltales = new Paint();
    public int mTailColor;
    public float mTailScale;
    public int mVelocityMode;
    public float[] velocity = new float[2];

    public final void onDraw(Canvas canvas) {
        float f;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        float[] fArr;
        float[] fArr2;
        float f2;
        float f3;
        SplineSet splineSet;
        SplineSet splineSet2;
        SplineSet splineSet3;
        SplineSet splineSet4;
        int i6;
        SplineSet splineSet5;
        KeyCycleOscillator keyCycleOscillator;
        KeyCycleOscillator keyCycleOscillator2;
        KeyCycleOscillator keyCycleOscillator3;
        KeyCycleOscillator keyCycleOscillator4;
        float f4;
        float f5;
        float f6;
        double[] dArr;
        int i7;
        float f7;
        float[] fArr3;
        MotionTelltales motionTelltales = this;
        super.onDraw(canvas);
        getMatrix().invert(motionTelltales.mInvertMatrix);
        if (motionTelltales.mMotionLayout == null) {
            ViewParent parent = getParent();
            if (parent instanceof MotionLayout) {
                motionTelltales.mMotionLayout = (MotionLayout) parent;
                return;
            }
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int i8 = 5;
        float[] fArr4 = {0.1f, 0.25f, 0.5f, 0.75f, 0.9f};
        MotionTelltales motionTelltales2 = motionTelltales;
        int i9 = 0;
        while (i9 < i8) {
            float f8 = fArr4[i9];
            int i10 = 0;
            while (i10 < i8) {
                float f9 = fArr4[i10];
                MotionLayout motionLayout = motionTelltales2.mMotionLayout;
                float[] fArr5 = motionTelltales2.velocity;
                int i11 = motionTelltales2.mVelocityMode;
                Objects.requireNonNull(motionLayout);
                float f10 = motionLayout.mLastVelocity;
                float f11 = motionLayout.mTransitionLastPosition;
                if (motionLayout.mInterpolator != null) {
                    float signum = Math.signum(motionLayout.mTransitionGoalPosition - f11);
                    float interpolation = motionLayout.mInterpolator.getInterpolation(motionLayout.mTransitionLastPosition + 1.0E-5f);
                    f11 = motionLayout.mInterpolator.getInterpolation(motionLayout.mTransitionLastPosition);
                    f10 = (((interpolation - f11) / 1.0E-5f) * signum) / motionLayout.mTransitionDuration;
                }
                Interpolator interpolator = motionLayout.mInterpolator;
                if (interpolator instanceof MotionInterpolator) {
                    f10 = ((MotionInterpolator) interpolator).getVelocity$1();
                }
                float f12 = f10;
                MotionController motionController = motionLayout.mFrameArrayList.get(motionTelltales2);
                if ((i11 & 1) == 0) {
                    int width2 = getWidth();
                    int height2 = getHeight();
                    Objects.requireNonNull(motionController);
                    float adjustedPosition = motionController.getAdjustedPosition(f11, motionController.mVelocity);
                    HashMap<String, SplineSet> hashMap = motionController.mAttributesMap;
                    KeyCycleOscillator keyCycleOscillator5 = null;
                    if (hashMap == null) {
                        fArr = fArr4;
                        splineSet = null;
                    } else {
                        splineSet = hashMap.get("translationX");
                        fArr = fArr4;
                    }
                    HashMap<String, SplineSet> hashMap2 = motionController.mAttributesMap;
                    i5 = i11;
                    if (hashMap2 == null) {
                        i4 = i9;
                        splineSet2 = null;
                    } else {
                        splineSet2 = hashMap2.get("translationY");
                        i4 = i9;
                    }
                    HashMap<String, SplineSet> hashMap3 = motionController.mAttributesMap;
                    i3 = i10;
                    if (hashMap3 == null) {
                        i2 = height;
                        splineSet3 = null;
                    } else {
                        splineSet3 = hashMap3.get("rotation");
                        i2 = height;
                    }
                    HashMap<String, SplineSet> hashMap4 = motionController.mAttributesMap;
                    i = width;
                    if (hashMap4 == null) {
                        splineSet4 = null;
                    } else {
                        splineSet4 = hashMap4.get("scaleX");
                    }
                    HashMap<String, SplineSet> hashMap5 = motionController.mAttributesMap;
                    f = f12;
                    if (hashMap5 == null) {
                        i6 = width2;
                        splineSet5 = null;
                    } else {
                        splineSet5 = hashMap5.get("scaleY");
                        i6 = width2;
                    }
                    HashMap<String, KeyCycleOscillator> hashMap6 = motionController.mCycleMap;
                    if (hashMap6 == null) {
                        keyCycleOscillator = null;
                    } else {
                        keyCycleOscillator = hashMap6.get("translationX");
                    }
                    HashMap<String, KeyCycleOscillator> hashMap7 = motionController.mCycleMap;
                    if (hashMap7 == null) {
                        keyCycleOscillator2 = null;
                    } else {
                        keyCycleOscillator2 = hashMap7.get("translationY");
                    }
                    HashMap<String, KeyCycleOscillator> hashMap8 = motionController.mCycleMap;
                    if (hashMap8 == null) {
                        keyCycleOscillator3 = null;
                    } else {
                        keyCycleOscillator3 = hashMap8.get("rotation");
                    }
                    HashMap<String, KeyCycleOscillator> hashMap9 = motionController.mCycleMap;
                    if (hashMap9 == null) {
                        keyCycleOscillator4 = null;
                    } else {
                        keyCycleOscillator4 = hashMap9.get("scaleX");
                    }
                    HashMap<String, KeyCycleOscillator> hashMap10 = motionController.mCycleMap;
                    if (hashMap10 != null) {
                        keyCycleOscillator5 = hashMap10.get("scaleY");
                    }
                    KeyCycleOscillator keyCycleOscillator6 = keyCycleOscillator5;
                    VelocityMatrix velocityMatrix = new VelocityMatrix();
                    float[] fArr6 = fArr5;
                    velocityMatrix.mDRotate = 0.0f;
                    velocityMatrix.mDTranslateY = 0.0f;
                    velocityMatrix.mDTranslateX = 0.0f;
                    velocityMatrix.mDScaleY = 0.0f;
                    velocityMatrix.mDScaleX = 0.0f;
                    if (splineSet3 != null) {
                        f4 = f9;
                        f5 = f8;
                        velocityMatrix.mDRotate = (float) splineSet3.mCurveFit.getSlope((double) adjustedPosition);
                        velocityMatrix.mRotate = splineSet3.get(adjustedPosition);
                    } else {
                        f4 = f9;
                        f5 = f8;
                    }
                    if (splineSet != null) {
                        velocityMatrix.mDTranslateX = (float) splineSet.mCurveFit.getSlope((double) adjustedPosition);
                    }
                    if (splineSet2 != null) {
                        velocityMatrix.mDTranslateY = (float) splineSet2.mCurveFit.getSlope((double) adjustedPosition);
                    }
                    if (splineSet4 != null) {
                        velocityMatrix.mDScaleX = (float) splineSet4.mCurveFit.getSlope((double) adjustedPosition);
                    }
                    if (splineSet5 != null) {
                        velocityMatrix.mDScaleY = (float) splineSet5.mCurveFit.getSlope((double) adjustedPosition);
                    }
                    if (keyCycleOscillator3 != null) {
                        velocityMatrix.mDRotate = keyCycleOscillator3.getSlope(adjustedPosition);
                    }
                    if (keyCycleOscillator != null) {
                        velocityMatrix.mDTranslateX = keyCycleOscillator.getSlope(adjustedPosition);
                    }
                    if (keyCycleOscillator2 != null) {
                        velocityMatrix.mDTranslateY = keyCycleOscillator2.getSlope(adjustedPosition);
                    }
                    if (!(keyCycleOscillator4 == null && keyCycleOscillator6 == null)) {
                        if (keyCycleOscillator4 == null) {
                            velocityMatrix.mDScaleX = keyCycleOscillator4.getSlope(adjustedPosition);
                        }
                        if (keyCycleOscillator6 == null) {
                            velocityMatrix.mDScaleY = keyCycleOscillator6.getSlope(adjustedPosition);
                        }
                    }
                    ArcCurveFit arcCurveFit = motionController.mArcSpline;
                    if (arcCurveFit != null) {
                        double[] dArr2 = motionController.mInterpolateData;
                        if (dArr2.length > 0) {
                            double d = (double) adjustedPosition;
                            arcCurveFit.getPos(d, dArr2);
                            motionController.mArcSpline.getSlope(d, motionController.mInterpolateVelocity);
                            MotionPaths motionPaths = motionController.mStartMotionPath;
                            int[] iArr = motionController.mInterpolateVariables;
                            double[] dArr3 = motionController.mInterpolateVelocity;
                            double[] dArr4 = motionController.mInterpolateData;
                            Objects.requireNonNull(motionPaths);
                            i7 = i5;
                            fArr3 = fArr6;
                            f7 = f4;
                            MotionPaths.setDpDt(f4, f5, fArr6, iArr, dArr3, dArr4);
                        } else {
                            fArr3 = fArr6;
                            i7 = i5;
                            f7 = f4;
                        }
                        velocityMatrix.applyTransform(f7, f5, i6, height2, fArr3);
                        i5 = i7;
                        fArr2 = fArr3;
                        f3 = f7;
                    } else {
                        float[] fArr7 = fArr6;
                        float f13 = f4;
                        if (motionController.mSpline != null) {
                            double adjustedPosition2 = (double) motionController.getAdjustedPosition(adjustedPosition, motionController.mVelocity);
                            motionController.mSpline[0].getSlope(adjustedPosition2, motionController.mInterpolateVelocity);
                            motionController.mSpline[0].getPos(adjustedPosition2, motionController.mInterpolateData);
                            float f14 = motionController.mVelocity[0];
                            int i12 = 0;
                            while (true) {
                                dArr = motionController.mInterpolateVelocity;
                                if (i12 >= dArr.length) {
                                    break;
                                }
                                dArr[i12] = dArr[i12] * ((double) f14);
                                i12++;
                            }
                            MotionPaths motionPaths2 = motionController.mStartMotionPath;
                            int[] iArr2 = motionController.mInterpolateVariables;
                            double[] dArr5 = motionController.mInterpolateData;
                            Objects.requireNonNull(motionPaths2);
                            fArr2 = fArr7;
                            f3 = f13;
                            MotionPaths.setDpDt(f13, f5, fArr7, iArr2, dArr, dArr5);
                            velocityMatrix.applyTransform(f3, f5, i6, height2, fArr2);
                        } else {
                            fArr2 = fArr7;
                            MotionPaths motionPaths3 = motionController.mEndMotionPath;
                            KeyCycleOscillator keyCycleOscillator7 = keyCycleOscillator6;
                            float f15 = motionPaths3.f12x;
                            MotionPaths motionPaths4 = motionController.mStartMotionPath;
                            KeyCycleOscillator keyCycleOscillator8 = keyCycleOscillator4;
                            float f16 = f15 - motionPaths4.f12x;
                            KeyCycleOscillator keyCycleOscillator9 = keyCycleOscillator2;
                            float f17 = motionPaths3.f13y - motionPaths4.f13y;
                            KeyCycleOscillator keyCycleOscillator10 = keyCycleOscillator;
                            fArr2[0] = (((motionPaths3.width - motionPaths4.width) + f16) * f13) + ((1.0f - f13) * f16);
                            fArr2[1] = (((motionPaths3.height - motionPaths4.height) + f17) * f5) + ((1.0f - f5) * f17);
                            velocityMatrix.mDRotate = 0.0f;
                            velocityMatrix.mDTranslateY = 0.0f;
                            velocityMatrix.mDTranslateX = 0.0f;
                            velocityMatrix.mDScaleY = 0.0f;
                            velocityMatrix.mDScaleX = 0.0f;
                            if (splineSet3 != null) {
                                f6 = f13;
                                velocityMatrix.mDRotate = (float) splineSet3.mCurveFit.getSlope((double) adjustedPosition);
                                velocityMatrix.mRotate = splineSet3.get(adjustedPosition);
                            } else {
                                f6 = f13;
                            }
                            if (splineSet != null) {
                                velocityMatrix.mDTranslateX = (float) splineSet.mCurveFit.getSlope((double) adjustedPosition);
                            }
                            if (splineSet2 != null) {
                                velocityMatrix.mDTranslateY = (float) splineSet2.mCurveFit.getSlope((double) adjustedPosition);
                            }
                            if (splineSet4 != null) {
                                velocityMatrix.mDScaleX = (float) splineSet4.mCurveFit.getSlope((double) adjustedPosition);
                            }
                            if (splineSet5 != null) {
                                velocityMatrix.mDScaleY = (float) splineSet5.mCurveFit.getSlope((double) adjustedPosition);
                            }
                            if (keyCycleOscillator3 != null) {
                                velocityMatrix.mDRotate = keyCycleOscillator3.getSlope(adjustedPosition);
                            }
                            if (keyCycleOscillator10 != null) {
                                velocityMatrix.mDTranslateX = keyCycleOscillator10.getSlope(adjustedPosition);
                            }
                            if (keyCycleOscillator9 != null) {
                                velocityMatrix.mDTranslateY = keyCycleOscillator9.getSlope(adjustedPosition);
                            }
                            if (!(keyCycleOscillator8 == null && keyCycleOscillator7 == null)) {
                                if (keyCycleOscillator8 == null) {
                                    velocityMatrix.mDScaleX = keyCycleOscillator8.getSlope(adjustedPosition);
                                }
                                if (keyCycleOscillator7 == null) {
                                    velocityMatrix.mDScaleY = keyCycleOscillator7.getSlope(adjustedPosition);
                                }
                            }
                            f3 = f6;
                            velocityMatrix.applyTransform(f6, f5, i6, height2, fArr2);
                        }
                    }
                    f2 = f5;
                } else {
                    i = width;
                    i2 = height;
                    f = f12;
                    fArr = fArr4;
                    i4 = i9;
                    i5 = i11;
                    f3 = f9;
                    f2 = f8;
                    i3 = i10;
                    fArr2 = fArr5;
                    motionController.getDpDt(f11, f3, f2, fArr2);
                }
                if (i5 < 2) {
                    fArr2[0] = fArr2[0] * f;
                    fArr2[1] = fArr2[1] * f;
                }
                this.mInvertMatrix.mapVectors(this.velocity);
                int i13 = i;
                float f18 = ((float) i13) * f3;
                int i14 = i2;
                float f19 = ((float) i14) * f2;
                float[] fArr8 = this.velocity;
                float f20 = fArr8[0];
                float f21 = this.mTailScale;
                float f22 = f19 - (fArr8[1] * f21);
                this.mInvertMatrix.mapVectors(fArr8);
                canvas.drawLine(f18, f19, f18 - (f20 * f21), f22, this.mPaintTelltales);
                i10 = i3 + 1;
                f8 = f2;
                motionTelltales2 = this;
                width = i13;
                fArr4 = fArr;
                i9 = i4;
                i8 = 5;
                height = i14;
                motionTelltales = motionTelltales2;
            }
            int i15 = width;
            float[] fArr9 = fArr4;
            int i16 = height;
            MotionTelltales motionTelltales3 = motionTelltales;
            int i17 = i16;
            i9++;
            i8 = 5;
            MotionTelltales motionTelltales4 = motionTelltales3;
            height = i17;
            motionTelltales = motionTelltales4;
        }
    }

    public MotionTelltales(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mVelocityMode = 0;
        this.mTailColor = -65281;
        this.mTailScale = 0.25f;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MotionTelltales);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 0) {
                    this.mTailColor = obtainStyledAttributes.getColor(index, this.mTailColor);
                } else if (index == 2) {
                    this.mVelocityMode = obtainStyledAttributes.getInt(index, this.mVelocityMode);
                } else if (index == 1) {
                    this.mTailScale = obtainStyledAttributes.getFloat(index, this.mTailScale);
                }
            }
        }
        this.mPaintTelltales.setColor(this.mTailColor);
        this.mPaintTelltales.setStrokeWidth(5.0f);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        postInvalidate();
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
