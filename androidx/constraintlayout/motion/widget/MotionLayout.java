package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Flow;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import androidx.constraintlayout.solver.widgets.WidgetContainer;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.StateSet;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.widget.NestedScrollView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class MotionLayout extends ConstraintLayout implements NestedScrollingParent3 {
    public static boolean IS_IN_EDIT_MODE;
    public long mAnimationStartTime = 0;
    public int mBeginState = -1;
    public RectF mBoundsCheck = new RectF();
    public int mCurrentState = -1;
    public int mDebugPath = 0;
    public DecelerateInterpolator mDecelerateLogic = new DecelerateInterpolator();
    public DevModeDraw mDevModeDraw;
    public int mEndState = -1;
    public int mEndWrapHeight;
    public int mEndWrapWidth;
    public HashMap<View, MotionController> mFrameArrayList = new HashMap<>();
    public int mFrames = 0;
    public int mHeightMeasureMode;
    public boolean mInTransition = false;
    public boolean mInteractionEnabled = true;
    public Interpolator mInterpolator;
    public boolean mIsAnimating = false;
    public boolean mKeepAnimating = false;
    public KeyCache mKeyCache = new KeyCache();
    public long mLastDrawTime = -1;
    public float mLastFps = 0.0f;
    public int mLastHeightMeasureSpec = 0;
    public int mLastLayoutHeight;
    public int mLastLayoutWidth;
    public float mLastVelocity = 0.0f;
    public int mLastWidthMeasureSpec = 0;
    public float mListenerPosition = 0.0f;
    public int mListenerState = 0;
    public boolean mMeasureDuringTransition = false;
    public Model mModel = new Model();
    public boolean mNeedsFireTransitionCompleted = false;
    public ArrayList<MotionHelper> mOnHideHelpers = null;
    public ArrayList<MotionHelper> mOnShowHelpers = null;
    public float mPostInterpolationPosition;
    public View mRegionView = null;
    public MotionScene mScene;
    public View mScrollTarget;
    public float mScrollTargetDT;
    public float mScrollTargetDX;
    public float mScrollTargetDY;
    public long mScrollTargetTime;
    public int mStartWrapHeight;
    public int mStartWrapWidth;
    public StopLogic mStopLogic = new StopLogic();
    public boolean mTemporalInterpolator = false;
    public ArrayList<Integer> mTransitionCompleted = new ArrayList<>();
    public float mTransitionDuration = 1.0f;
    public float mTransitionGoalPosition = 0.0f;
    public boolean mTransitionInstantly;
    public float mTransitionLastPosition = 0.0f;
    public long mTransitionLastTime;
    public ArrayList<TransitionListener> mTransitionListeners = null;
    public float mTransitionPosition = 0.0f;
    public boolean mUndergoingMotion = false;
    public int mWidthMeasureMode;

    public class DecelerateInterpolator extends MotionInterpolator {
        public float currentP = 0.0f;
        public float initalV = 0.0f;
        public float maxA;

        public DecelerateInterpolator() {
        }

        public final float getInterpolation(float f) {
            float f2 = this.initalV;
            if (f2 > 0.0f) {
                float f3 = this.maxA;
                if (f2 / f3 < f) {
                    f = f2 / f3;
                }
                MotionLayout.this.mLastVelocity = f2 - (f3 * f);
                return ((f2 * f) - (((f3 * f) * f) / 2.0f)) + this.currentP;
            }
            float f4 = this.maxA;
            if ((-f2) / f4 < f) {
                f = (-f2) / f4;
            }
            MotionLayout.this.mLastVelocity = (f4 * f) + f2;
            return (((f4 * f) * f) / 2.0f) + (f2 * f) + this.currentP;
        }

        public final float getVelocity$1() {
            return MotionLayout.this.mLastVelocity;
        }
    }

    public class DevModeDraw {
        public Rect mBounds = new Rect();
        public Paint mFillPaint;
        public int mKeyFrameCount;
        public float[] mKeyFramePoints;
        public Paint mPaint;
        public Paint mPaintGraph;
        public Paint mPaintKeyframes;
        public Path mPath;
        public int[] mPathMode;
        public float[] mPoints;
        public float[] mRectangle;
        public int mShadowTranslate = 1;
        public Paint mTextPaint;

        public final void drawPathRelativeTicks(Canvas canvas, float f, float f2) {
            float f3 = f;
            float f4 = f2;
            float[] fArr = this.mPoints;
            float f5 = fArr[0];
            float f6 = fArr[1];
            float f7 = fArr[fArr.length - 2];
            float f8 = fArr[fArr.length - 1];
            float hypot = (float) Math.hypot((double) (f5 - f7), (double) (f6 - f8));
            float f9 = f7 - f5;
            float f10 = f8 - f6;
            float f11 = (((f4 - f6) * f10) + ((f3 - f5) * f9)) / (hypot * hypot);
            float f12 = f5 + (f9 * f11);
            float f13 = f6 + (f11 * f10);
            Path path = new Path();
            path.moveTo(f, f4);
            path.lineTo(f12, f13);
            float hypot2 = (float) Math.hypot((double) (f12 - f3), (double) (f13 - f4));
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("");
            m.append(((float) ((int) ((hypot2 * 100.0f) / hypot))) / 100.0f);
            String sb = m.toString();
            getTextBounds(sb, this.mTextPaint);
            canvas.drawTextOnPath(sb, path, (hypot2 / 2.0f) - ((float) (this.mBounds.width() / 2)), -20.0f, this.mTextPaint);
            canvas.drawLine(f3, f4, f12, f13, this.mPaintGraph);
        }

        public final void drawPathScreenTicks(Canvas canvas, float f, float f2, int i, int i2) {
            Canvas canvas2 = canvas;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("");
            m.append(((float) ((int) (((double) (((f - ((float) (i / 2))) * 100.0f) / ((float) (MotionLayout.this.getWidth() - i)))) + 0.5d))) / 100.0f);
            String sb = m.toString();
            getTextBounds(sb, this.mTextPaint);
            canvas2.drawText(sb, ((f / 2.0f) - ((float) (this.mBounds.width() / 2))) + 0.0f, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(0.0f, 1.0f), f2, this.mPaintGraph);
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("");
            m2.append(((float) ((int) (((double) (((f2 - ((float) (i2 / 2))) * 100.0f) / ((float) (MotionLayout.this.getHeight() - i2)))) + 0.5d))) / 100.0f);
            String sb2 = m2.toString();
            getTextBounds(sb2, this.mTextPaint);
            canvas2.drawText(sb2, f + 5.0f, 0.0f - ((f2 / 2.0f) - ((float) (this.mBounds.height() / 2))), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(0.0f, 1.0f), this.mPaintGraph);
        }

        public DevModeDraw() {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mPaint.setColor(-21965);
            this.mPaint.setStrokeWidth(2.0f);
            this.mPaint.setStyle(Paint.Style.STROKE);
            Paint paint2 = new Paint();
            this.mPaintKeyframes = paint2;
            paint2.setAntiAlias(true);
            this.mPaintKeyframes.setColor(-2067046);
            this.mPaintKeyframes.setStrokeWidth(2.0f);
            this.mPaintKeyframes.setStyle(Paint.Style.STROKE);
            Paint paint3 = new Paint();
            this.mPaintGraph = paint3;
            paint3.setAntiAlias(true);
            this.mPaintGraph.setColor(-13391360);
            this.mPaintGraph.setStrokeWidth(2.0f);
            this.mPaintGraph.setStyle(Paint.Style.STROKE);
            Paint paint4 = new Paint();
            this.mTextPaint = paint4;
            paint4.setAntiAlias(true);
            this.mTextPaint.setColor(-13391360);
            this.mTextPaint.setTextSize(MotionLayout.this.getContext().getResources().getDisplayMetrics().density * 12.0f);
            this.mRectangle = new float[8];
            Paint paint5 = new Paint();
            this.mFillPaint = paint5;
            paint5.setAntiAlias(true);
            this.mPaintGraph.setPathEffect(new DashPathEffect(new float[]{4.0f, 8.0f}, 0.0f));
            this.mKeyFramePoints = new float[100];
            this.mPathMode = new int[50];
        }

        public final void drawAll(Canvas canvas, int i, int i2, MotionController motionController) {
            int i3;
            int i4;
            int i5;
            float f;
            float f2;
            Canvas canvas2 = canvas;
            int i6 = i;
            MotionController motionController2 = motionController;
            if (i6 == 4) {
                boolean z = false;
                boolean z2 = false;
                for (int i7 = 0; i7 < this.mKeyFrameCount; i7++) {
                    int[] iArr = this.mPathMode;
                    if (iArr[i7] == 1) {
                        z = true;
                    }
                    if (iArr[i7] == 2) {
                        z2 = true;
                    }
                }
                if (z) {
                    float[] fArr = this.mPoints;
                    canvas.drawLine(fArr[0], fArr[1], fArr[fArr.length - 2], fArr[fArr.length - 1], this.mPaintGraph);
                }
                if (z2) {
                    drawPathCartesian(canvas);
                }
            }
            if (i6 == 2) {
                float[] fArr2 = this.mPoints;
                canvas.drawLine(fArr2[0], fArr2[1], fArr2[fArr2.length - 2], fArr2[fArr2.length - 1], this.mPaintGraph);
            }
            if (i6 == 3) {
                drawPathCartesian(canvas);
            }
            canvas2.drawLines(this.mPoints, this.mPaint);
            View view = motionController2.mView;
            if (view != null) {
                i4 = view.getWidth();
                i3 = motionController2.mView.getHeight();
            } else {
                i4 = 0;
                i3 = 0;
            }
            int i8 = 1;
            while (i8 < i2 - 1) {
                if (i6 == 4 && this.mPathMode[i8 - 1] == 0) {
                    i5 = i8;
                } else {
                    float[] fArr3 = this.mKeyFramePoints;
                    int i9 = i8 * 2;
                    float f3 = fArr3[i9];
                    float f4 = fArr3[i9 + 1];
                    this.mPath.reset();
                    this.mPath.moveTo(f3, f4 + 10.0f);
                    this.mPath.lineTo(f3 + 10.0f, f4);
                    this.mPath.lineTo(f3, f4 - 10.0f);
                    this.mPath.lineTo(f3 - 10.0f, f4);
                    this.mPath.close();
                    int i10 = i8 - 1;
                    MotionPaths motionPaths = motionController2.mMotionPaths.get(i10);
                    if (i6 == 4) {
                        int[] iArr2 = this.mPathMode;
                        if (iArr2[i10] == 1) {
                            drawPathRelativeTicks(canvas2, f3 - 0.0f, f4 - 0.0f);
                        } else if (iArr2[i10] == 2) {
                            drawPathCartesianTicks(canvas2, f3 - 0.0f, f4 - 0.0f);
                        } else if (iArr2[i10] == 3) {
                            f = f4;
                            f2 = f3;
                            i5 = i8;
                            drawPathScreenTicks(canvas, f3 - 0.0f, f4 - 0.0f, i4, i3);
                            canvas2.drawPath(this.mPath, this.mFillPaint);
                        }
                        f = f4;
                        f2 = f3;
                        i5 = i8;
                        canvas2.drawPath(this.mPath, this.mFillPaint);
                    } else {
                        f = f4;
                        f2 = f3;
                        i5 = i8;
                    }
                    if (i6 == 2) {
                        drawPathRelativeTicks(canvas2, f2 - 0.0f, f - 0.0f);
                    }
                    if (i6 == 3) {
                        drawPathCartesianTicks(canvas2, f2 - 0.0f, f - 0.0f);
                    }
                    if (i6 == 6) {
                        drawPathScreenTicks(canvas, f2 - 0.0f, f - 0.0f, i4, i3);
                    }
                    canvas2.drawPath(this.mPath, this.mFillPaint);
                }
                i8 = i5 + 1;
            }
            float[] fArr4 = this.mPoints;
            if (fArr4.length > 1) {
                canvas2.drawCircle(fArr4[0], fArr4[1], 8.0f, this.mPaintKeyframes);
                float[] fArr5 = this.mPoints;
                canvas2.drawCircle(fArr5[fArr5.length - 2], fArr5[fArr5.length - 1], 8.0f, this.mPaintKeyframes);
            }
        }

        public final void drawPathCartesian(Canvas canvas) {
            float[] fArr = this.mPoints;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            canvas.drawLine(Math.min(f, f3), Math.max(f2, f4), Math.max(f, f3), Math.max(f2, f4), this.mPaintGraph);
            canvas.drawLine(Math.min(f, f3), Math.min(f2, f4), Math.min(f, f3), Math.max(f2, f4), this.mPaintGraph);
        }

        public final void drawPathCartesianTicks(Canvas canvas, float f, float f2) {
            Canvas canvas2 = canvas;
            float[] fArr = this.mPoints;
            float f3 = fArr[0];
            float f4 = fArr[1];
            float f5 = fArr[fArr.length - 2];
            float f6 = fArr[fArr.length - 1];
            float min = Math.min(f3, f5);
            float max = Math.max(f4, f6);
            float min2 = f - Math.min(f3, f5);
            float max2 = Math.max(f4, f6) - f2;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("");
            m.append(((float) ((int) (((double) ((min2 * 100.0f) / Math.abs(f5 - f3))) + 0.5d))) / 100.0f);
            String sb = m.toString();
            getTextBounds(sb, this.mTextPaint);
            canvas2.drawText(sb, ((min2 / 2.0f) - ((float) (this.mBounds.width() / 2))) + min, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(f3, f5), f2, this.mPaintGraph);
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("");
            m2.append(((float) ((int) (((double) ((max2 * 100.0f) / Math.abs(f6 - f4))) + 0.5d))) / 100.0f);
            String sb2 = m2.toString();
            getTextBounds(sb2, this.mTextPaint);
            canvas2.drawText(sb2, f + 5.0f, max - ((max2 / 2.0f) - ((float) (this.mBounds.height() / 2))), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(f4, f6), this.mPaintGraph);
        }

        public final void getTextBounds(String str, Paint paint) {
            paint.getTextBounds(str, 0, str.length(), this.mBounds);
        }
    }

    public class Model {
        public ConstraintSet mEnd = null;
        public int mEndId;
        public ConstraintWidgetContainer mLayoutEnd = new ConstraintWidgetContainer();
        public ConstraintWidgetContainer mLayoutStart = new ConstraintWidgetContainer();
        public ConstraintSet mStart = null;
        public int mStartId;

        public Model() {
        }

        public final void build() {
            int childCount = MotionLayout.this.getChildCount();
            MotionLayout.this.mFrameArrayList.clear();
            for (int i = 0; i < childCount; i++) {
                View childAt = MotionLayout.this.getChildAt(i);
                MotionLayout.this.mFrameArrayList.put(childAt, new MotionController(childAt));
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt2 = MotionLayout.this.getChildAt(i2);
                MotionController motionController = MotionLayout.this.mFrameArrayList.get(childAt2);
                if (motionController != null) {
                    if (this.mStart != null) {
                        ConstraintWidget widget = getWidget(this.mLayoutStart, childAt2);
                        if (widget != null) {
                            ConstraintSet constraintSet = this.mStart;
                            MotionPaths motionPaths = motionController.mStartMotionPath;
                            motionPaths.time = 0.0f;
                            motionPaths.position = 0.0f;
                            motionController.readView(motionPaths);
                            motionController.mStartMotionPath.setBounds((float) widget.getX(), (float) widget.getY(), (float) widget.getWidth(), (float) widget.getHeight());
                            int i3 = motionController.mId;
                            Objects.requireNonNull(constraintSet);
                            ConstraintSet.Constraint constraint = constraintSet.get(i3);
                            motionController.mStartMotionPath.applyParameters(constraint);
                            motionController.mMotionStagger = constraint.motion.mMotionStagger;
                            motionController.mStartPoint.setState(widget, constraintSet, motionController.mId);
                        } else {
                            Log.e("MotionLayout", Debug.getLocation() + "no widget for  " + Debug.getName(childAt2) + " (" + childAt2.getClass().getName() + ")");
                        }
                    }
                    if (this.mEnd != null) {
                        ConstraintWidget widget2 = getWidget(this.mLayoutEnd, childAt2);
                        if (widget2 != null) {
                            ConstraintSet constraintSet2 = this.mEnd;
                            MotionPaths motionPaths2 = motionController.mEndMotionPath;
                            motionPaths2.time = 1.0f;
                            motionPaths2.position = 1.0f;
                            motionController.readView(motionPaths2);
                            motionController.mEndMotionPath.setBounds((float) widget2.getX(), (float) widget2.getY(), (float) widget2.getWidth(), (float) widget2.getHeight());
                            MotionPaths motionPaths3 = motionController.mEndMotionPath;
                            int i4 = motionController.mId;
                            Objects.requireNonNull(constraintSet2);
                            motionPaths3.applyParameters(constraintSet2.get(i4));
                            motionController.mEndPoint.setState(widget2, constraintSet2, motionController.mId);
                        } else {
                            Log.e("MotionLayout", Debug.getLocation() + "no widget for  " + Debug.getName(childAt2) + " (" + childAt2.getClass().getName() + ")");
                        }
                    }
                }
            }
        }

        public final void initFrom(ConstraintSet constraintSet, ConstraintSet constraintSet2) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            this.mStart = constraintSet;
            this.mEnd = constraintSet2;
            ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutStart;
            MotionLayout motionLayout = MotionLayout.this;
            boolean z = MotionLayout.IS_IN_EDIT_MODE;
            ConstraintWidgetContainer constraintWidgetContainer2 = motionLayout.mLayoutWidget;
            Objects.requireNonNull(constraintWidgetContainer2);
            BasicMeasure.Measurer measurer = constraintWidgetContainer2.mMeasurer;
            Objects.requireNonNull(constraintWidgetContainer);
            constraintWidgetContainer.mMeasurer = measurer;
            DependencyGraph dependencyGraph = constraintWidgetContainer.mDependencyGraph;
            Objects.requireNonNull(dependencyGraph);
            dependencyGraph.mMeasurer = measurer;
            ConstraintWidgetContainer constraintWidgetContainer3 = this.mLayoutEnd;
            ConstraintWidgetContainer constraintWidgetContainer4 = MotionLayout.this.mLayoutWidget;
            Objects.requireNonNull(constraintWidgetContainer4);
            BasicMeasure.Measurer measurer2 = constraintWidgetContainer4.mMeasurer;
            Objects.requireNonNull(constraintWidgetContainer3);
            constraintWidgetContainer3.mMeasurer = measurer2;
            DependencyGraph dependencyGraph2 = constraintWidgetContainer3.mDependencyGraph;
            Objects.requireNonNull(dependencyGraph2);
            dependencyGraph2.mMeasurer = measurer2;
            ConstraintWidgetContainer constraintWidgetContainer5 = this.mLayoutStart;
            Objects.requireNonNull(constraintWidgetContainer5);
            constraintWidgetContainer5.mChildren.clear();
            ConstraintWidgetContainer constraintWidgetContainer6 = this.mLayoutEnd;
            Objects.requireNonNull(constraintWidgetContainer6);
            constraintWidgetContainer6.mChildren.clear();
            copy(MotionLayout.this.mLayoutWidget, this.mLayoutStart);
            copy(MotionLayout.this.mLayoutWidget, this.mLayoutEnd);
            if (constraintSet != null) {
                setupConstraintWidget(this.mLayoutStart, constraintSet);
            }
            setupConstraintWidget(this.mLayoutEnd, constraintSet2);
            ConstraintWidgetContainer constraintWidgetContainer7 = this.mLayoutStart;
            boolean isRtl = MotionLayout.this.isRtl();
            Objects.requireNonNull(constraintWidgetContainer7);
            constraintWidgetContainer7.mIsRtl = isRtl;
            this.mLayoutStart.updateHierarchy();
            ConstraintWidgetContainer constraintWidgetContainer8 = this.mLayoutEnd;
            boolean isRtl2 = MotionLayout.this.isRtl();
            Objects.requireNonNull(constraintWidgetContainer8);
            constraintWidgetContainer8.mIsRtl = isRtl2;
            this.mLayoutEnd.updateHierarchy();
            ViewGroup.LayoutParams layoutParams = MotionLayout.this.getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams.width == -2) {
                    this.mLayoutStart.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    this.mLayoutEnd.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (layoutParams.height == -2) {
                    this.mLayoutStart.setVerticalDimensionBehaviour(dimensionBehaviour);
                    this.mLayoutEnd.setVerticalDimensionBehaviour(dimensionBehaviour);
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:112:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x00c1  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00f5  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x00f8  */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x00fb  */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x0114  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x013b  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x013e  */
        /* JADX WARNING: Removed duplicated region for block: B:56:0x0143  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void reEvaluateState() {
            /*
                r12 = this;
                androidx.constraintlayout.motion.widget.MotionLayout r0 = androidx.constraintlayout.motion.widget.MotionLayout.this
                int r2 = r0.mLastWidthMeasureSpec
                int r3 = r0.mLastHeightMeasureSpec
                int r0 = android.view.View.MeasureSpec.getMode(r2)
                int r1 = android.view.View.MeasureSpec.getMode(r3)
                androidx.constraintlayout.motion.widget.MotionLayout r4 = androidx.constraintlayout.motion.widget.MotionLayout.this
                r4.mWidthMeasureMode = r0
                r4.mHeightMeasureMode = r1
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r4.mLayoutWidget
                java.util.Objects.requireNonNull(r0)
                int r0 = r0.mOptimizationLevel
                androidx.constraintlayout.motion.widget.MotionLayout r1 = androidx.constraintlayout.motion.widget.MotionLayout.this
                int r4 = r1.mCurrentState
                int r5 = r1.mBeginState
                if (r4 != r5) goto L_0x0034
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r4 = r12.mLayoutEnd
                r1.resolveSystem(r4, r0, r2, r3)
                androidx.constraintlayout.widget.ConstraintSet r1 = r12.mStart
                if (r1 == 0) goto L_0x0044
                androidx.constraintlayout.motion.widget.MotionLayout r1 = androidx.constraintlayout.motion.widget.MotionLayout.this
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r4 = r12.mLayoutStart
                r1.resolveSystem(r4, r0, r2, r3)
                goto L_0x0044
            L_0x0034:
                androidx.constraintlayout.widget.ConstraintSet r4 = r12.mStart
                if (r4 == 0) goto L_0x003d
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r4 = r12.mLayoutStart
                r1.resolveSystem(r4, r0, r2, r3)
            L_0x003d:
                androidx.constraintlayout.motion.widget.MotionLayout r1 = androidx.constraintlayout.motion.widget.MotionLayout.this
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r4 = r12.mLayoutEnd
                r1.resolveSystem(r4, r0, r2, r3)
            L_0x0044:
                androidx.constraintlayout.motion.widget.MotionLayout r0 = androidx.constraintlayout.motion.widget.MotionLayout.this
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r1 = r12.mLayoutStart
                int r1 = r1.getWidth()
                r0.mStartWrapWidth = r1
                androidx.constraintlayout.motion.widget.MotionLayout r0 = androidx.constraintlayout.motion.widget.MotionLayout.this
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r1 = r12.mLayoutStart
                int r1 = r1.getHeight()
                r0.mStartWrapHeight = r1
                androidx.constraintlayout.motion.widget.MotionLayout r0 = androidx.constraintlayout.motion.widget.MotionLayout.this
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r1 = r12.mLayoutEnd
                int r1 = r1.getWidth()
                r0.mEndWrapWidth = r1
                androidx.constraintlayout.motion.widget.MotionLayout r0 = androidx.constraintlayout.motion.widget.MotionLayout.this
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r1 = r12.mLayoutEnd
                int r1 = r1.getHeight()
                r0.mEndWrapHeight = r1
                androidx.constraintlayout.motion.widget.MotionLayout r0 = androidx.constraintlayout.motion.widget.MotionLayout.this
                int r1 = r0.mStartWrapWidth
                int r4 = r0.mEndWrapWidth
                r8 = 0
                r9 = 1
                if (r1 != r4) goto L_0x007f
                int r5 = r0.mStartWrapHeight
                int r6 = r0.mEndWrapHeight
                if (r5 == r6) goto L_0x007d
                goto L_0x007f
            L_0x007d:
                r5 = r8
                goto L_0x0080
            L_0x007f:
                r5 = r9
            L_0x0080:
                r0.mMeasureDuringTransition = r5
                int r5 = r0.mStartWrapHeight
                int r6 = r0.mWidthMeasureMode
                r7 = -2147483648(0xffffffff80000000, float:-0.0)
                if (r6 != r7) goto L_0x0092
                float r6 = (float) r1
                float r10 = r0.mPostInterpolationPosition
                int r4 = r4 - r1
                float r1 = (float) r4
                float r10 = r10 * r1
                float r10 = r10 + r6
                int r1 = (int) r10
            L_0x0092:
                r4 = r1
                int r1 = r0.mHeightMeasureMode
                if (r1 != r7) goto L_0x00a2
                float r1 = (float) r5
                float r6 = r0.mPostInterpolationPosition
                int r0 = r0.mEndWrapHeight
                int r0 = r0 - r5
                float r0 = (float) r0
                float r6 = r6 * r0
                float r6 = r6 + r1
                int r0 = (int) r6
                r5 = r0
            L_0x00a2:
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r12.mLayoutStart
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mWidthMeasuredTooSmall
                if (r0 != 0) goto L_0x00b7
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r12.mLayoutEnd
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mWidthMeasuredTooSmall
                if (r0 == 0) goto L_0x00b5
                goto L_0x00b7
            L_0x00b5:
                r6 = r8
                goto L_0x00b8
            L_0x00b7:
                r6 = r9
            L_0x00b8:
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r12.mLayoutStart
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mHeightMeasuredTooSmall
                if (r0 != 0) goto L_0x00cd
                androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r12.mLayoutEnd
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mHeightMeasuredTooSmall
                if (r0 == 0) goto L_0x00cb
                goto L_0x00cd
            L_0x00cb:
                r7 = r8
                goto L_0x00ce
            L_0x00cd:
                r7 = r9
            L_0x00ce:
                androidx.constraintlayout.motion.widget.MotionLayout r1 = androidx.constraintlayout.motion.widget.MotionLayout.this
                r1.resolveMeasuredDimension(r2, r3, r4, r5, r6, r7)
                androidx.constraintlayout.motion.widget.MotionLayout r12 = androidx.constraintlayout.motion.widget.MotionLayout.this
                java.util.Objects.requireNonNull(r12)
                int r0 = r12.getChildCount()
                androidx.constraintlayout.motion.widget.MotionLayout$Model r1 = r12.mModel
                r1.build()
                r12.mInTransition = r9
                int r1 = r12.getWidth()
                int r2 = r12.getHeight()
                androidx.constraintlayout.motion.widget.MotionScene r3 = r12.mScene
                java.util.Objects.requireNonNull(r3)
                androidx.constraintlayout.motion.widget.MotionScene$Transition r3 = r3.mCurrentTransition
                r4 = -1
                if (r3 == 0) goto L_0x00f8
                int r3 = r3.mPathMotionArc
                goto L_0x00f9
            L_0x00f8:
                r3 = r4
            L_0x00f9:
                if (r3 == r4) goto L_0x0111
                r4 = r8
            L_0x00fc:
                if (r4 >= r0) goto L_0x0111
                java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r5 = r12.mFrameArrayList
                android.view.View r6 = r12.getChildAt(r4)
                java.lang.Object r5 = r5.get(r6)
                androidx.constraintlayout.motion.widget.MotionController r5 = (androidx.constraintlayout.motion.widget.MotionController) r5
                if (r5 == 0) goto L_0x010e
                r5.mPathMotionArc = r3
            L_0x010e:
                int r4 = r4 + 1
                goto L_0x00fc
            L_0x0111:
                r3 = r8
            L_0x0112:
                if (r3 >= r0) goto L_0x0131
                java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r4 = r12.mFrameArrayList
                android.view.View r5 = r12.getChildAt(r3)
                java.lang.Object r4 = r4.get(r5)
                androidx.constraintlayout.motion.widget.MotionController r4 = (androidx.constraintlayout.motion.widget.MotionController) r4
                if (r4 == 0) goto L_0x012e
                androidx.constraintlayout.motion.widget.MotionScene r5 = r12.mScene
                r5.getKeyFrames(r4)
                long r5 = java.lang.System.nanoTime()
                r4.setup(r1, r2, r5)
            L_0x012e:
                int r3 = r3 + 1
                goto L_0x0112
            L_0x0131:
                androidx.constraintlayout.motion.widget.MotionScene r1 = r12.mScene
                java.util.Objects.requireNonNull(r1)
                androidx.constraintlayout.motion.widget.MotionScene$Transition r1 = r1.mCurrentTransition
                r2 = 0
                if (r1 == 0) goto L_0x013e
                float r1 = r1.mStagger
                goto L_0x013f
            L_0x013e:
                r1 = r2
            L_0x013f:
                int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
                if (r2 == 0) goto L_0x0219
                double r2 = (double) r1
                r4 = 0
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 >= 0) goto L_0x014c
                r2 = r9
                goto L_0x014d
            L_0x014c:
                r2 = r8
            L_0x014d:
                float r1 = java.lang.Math.abs(r1)
                r3 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
                r4 = 2139095039(0x7f7fffff, float:3.4028235E38)
                r7 = r3
                r6 = r4
                r5 = r8
            L_0x015a:
                if (r5 >= r0) goto L_0x0187
                java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r10 = r12.mFrameArrayList
                android.view.View r11 = r12.getChildAt(r5)
                java.lang.Object r10 = r10.get(r11)
                androidx.constraintlayout.motion.widget.MotionController r10 = (androidx.constraintlayout.motion.widget.MotionController) r10
                float r11 = r10.mMotionStagger
                boolean r11 = java.lang.Float.isNaN(r11)
                if (r11 != 0) goto L_0x0171
                goto L_0x0188
            L_0x0171:
                androidx.constraintlayout.motion.widget.MotionPaths r10 = r10.mEndMotionPath
                float r11 = r10.f12x
                float r10 = r10.f13y
                if (r2 == 0) goto L_0x017b
                float r10 = r10 - r11
                goto L_0x017c
            L_0x017b:
                float r10 = r10 + r11
            L_0x017c:
                float r6 = java.lang.Math.min(r6, r10)
                float r7 = java.lang.Math.max(r7, r10)
                int r5 = r5 + 1
                goto L_0x015a
            L_0x0187:
                r9 = r8
            L_0x0188:
                r5 = 1065353216(0x3f800000, float:1.0)
                if (r9 == 0) goto L_0x01eb
                r6 = r8
            L_0x018d:
                if (r6 >= r0) goto L_0x01b2
                java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r7 = r12.mFrameArrayList
                android.view.View r9 = r12.getChildAt(r6)
                java.lang.Object r7 = r7.get(r9)
                androidx.constraintlayout.motion.widget.MotionController r7 = (androidx.constraintlayout.motion.widget.MotionController) r7
                float r9 = r7.mMotionStagger
                boolean r9 = java.lang.Float.isNaN(r9)
                if (r9 != 0) goto L_0x01af
                float r9 = r7.mMotionStagger
                float r4 = java.lang.Math.min(r4, r9)
                float r7 = r7.mMotionStagger
                float r3 = java.lang.Math.max(r3, r7)
            L_0x01af:
                int r6 = r6 + 1
                goto L_0x018d
            L_0x01b2:
                if (r8 >= r0) goto L_0x0219
                java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r6 = r12.mFrameArrayList
                android.view.View r7 = r12.getChildAt(r8)
                java.lang.Object r6 = r6.get(r7)
                androidx.constraintlayout.motion.widget.MotionController r6 = (androidx.constraintlayout.motion.widget.MotionController) r6
                float r7 = r6.mMotionStagger
                boolean r7 = java.lang.Float.isNaN(r7)
                if (r7 != 0) goto L_0x01e8
                float r7 = r5 - r1
                float r7 = r5 / r7
                r6.mStaggerScale = r7
                if (r2 == 0) goto L_0x01dd
                float r7 = r6.mMotionStagger
                float r7 = r3 - r7
                float r9 = r3 - r4
                float r7 = r7 / r9
                float r7 = r7 * r1
                float r7 = r1 - r7
                r6.mStaggerOffset = r7
                goto L_0x01e8
            L_0x01dd:
                float r7 = r6.mMotionStagger
                float r7 = r7 - r4
                float r7 = r7 * r1
                float r9 = r3 - r4
                float r7 = r7 / r9
                float r7 = r1 - r7
                r6.mStaggerOffset = r7
            L_0x01e8:
                int r8 = r8 + 1
                goto L_0x01b2
            L_0x01eb:
                if (r8 >= r0) goto L_0x0219
                java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r3 = r12.mFrameArrayList
                android.view.View r4 = r12.getChildAt(r8)
                java.lang.Object r3 = r3.get(r4)
                androidx.constraintlayout.motion.widget.MotionController r3 = (androidx.constraintlayout.motion.widget.MotionController) r3
                java.util.Objects.requireNonNull(r3)
                androidx.constraintlayout.motion.widget.MotionPaths r4 = r3.mEndMotionPath
                float r9 = r4.f12x
                float r4 = r4.f13y
                if (r2 == 0) goto L_0x0206
                float r4 = r4 - r9
                goto L_0x0207
            L_0x0206:
                float r4 = r4 + r9
            L_0x0207:
                float r9 = r5 - r1
                float r9 = r5 / r9
                r3.mStaggerScale = r9
                float r4 = r4 - r6
                float r4 = r4 * r1
                float r9 = r7 - r6
                float r4 = r4 / r9
                float r4 = r1 - r4
                r3.mStaggerOffset = r4
                int r8 = r8 + 1
                goto L_0x01eb
            L_0x0219:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.Model.reEvaluateState():void");
        }

        public final void setupConstraintWidget(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet) {
            SparseArray sparseArray = new SparseArray();
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
            sparseArray.clear();
            sparseArray.put(0, constraintWidgetContainer);
            sparseArray.put(MotionLayout.this.getId(), constraintWidgetContainer);
            Objects.requireNonNull(constraintWidgetContainer);
            Iterator<ConstraintWidget> it = constraintWidgetContainer.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                Objects.requireNonNull(next);
                sparseArray.put(((View) next.mCompanionWidget).getId(), next);
            }
            Iterator<ConstraintWidget> it2 = constraintWidgetContainer.mChildren.iterator();
            while (it2.hasNext()) {
                ConstraintWidget next2 = it2.next();
                Objects.requireNonNull(next2);
                View view = (View) next2.mCompanionWidget;
                int id = view.getId();
                Objects.requireNonNull(constraintSet);
                if (constraintSet.mConstraints.containsKey(Integer.valueOf(id))) {
                    constraintSet.mConstraints.get(Integer.valueOf(id)).applyTo(layoutParams);
                }
                next2.setWidth(constraintSet.get(view.getId()).layout.mWidth);
                next2.setHeight(constraintSet.get(view.getId()).layout.mHeight);
                if (view instanceof ConstraintHelper) {
                    ConstraintHelper constraintHelper = (ConstraintHelper) view;
                    int id2 = constraintHelper.getId();
                    if (constraintSet.mConstraints.containsKey(Integer.valueOf(id2))) {
                        ConstraintSet.Constraint constraint = constraintSet.mConstraints.get(Integer.valueOf(id2));
                        if (next2 instanceof HelperWidget) {
                            constraintHelper.loadParameters(constraint, (HelperWidget) next2, layoutParams, sparseArray);
                        }
                    }
                    if (view instanceof Barrier) {
                        ((Barrier) view).validateParams();
                    }
                }
                layoutParams.resolveLayoutDirection(MotionLayout.this.getLayoutDirection());
                MotionLayout motionLayout = MotionLayout.this;
                boolean z = MotionLayout.IS_IN_EDIT_MODE;
                motionLayout.applyConstraintsFromLayoutParams(false, view, next2, layoutParams, sparseArray);
                if (constraintSet.get(view.getId()).propertySet.mVisibilityMode == 1) {
                    next2.mVisibility = view.getVisibility();
                } else {
                    next2.mVisibility = constraintSet.get(view.getId()).propertySet.visibility;
                }
            }
            Iterator<ConstraintWidget> it3 = constraintWidgetContainer.mChildren.iterator();
            while (it3.hasNext()) {
                ConstraintWidget next3 = it3.next();
                if (next3 instanceof Helper) {
                    Helper helper = (Helper) next3;
                    helper.removeAllIds();
                    Objects.requireNonNull(next3);
                    ConstraintHelper constraintHelper2 = (ConstraintHelper) next3.mCompanionWidget;
                    Objects.requireNonNull(constraintHelper2);
                    helper.removeAllIds();
                    for (int i = 0; i < constraintHelper2.mCount; i++) {
                        helper.add((ConstraintWidget) sparseArray.get(constraintHelper2.mIds[i]));
                    }
                    if (helper instanceof VirtualLayout) {
                        VirtualLayout virtualLayout = (VirtualLayout) helper;
                        for (int i2 = 0; i2 < virtualLayout.mWidgetsCount; i2++) {
                            ConstraintWidget constraintWidget = virtualLayout.mWidgets[i2];
                        }
                    }
                }
            }
        }

        public static void copy(ConstraintWidgetContainer constraintWidgetContainer, ConstraintWidgetContainer constraintWidgetContainer2) {
            ConstraintWidget constraintWidget;
            Objects.requireNonNull(constraintWidgetContainer);
            ArrayList<ConstraintWidget> arrayList = constraintWidgetContainer.mChildren;
            HashMap hashMap = new HashMap();
            hashMap.put(constraintWidgetContainer, constraintWidgetContainer2);
            Objects.requireNonNull(constraintWidgetContainer2);
            constraintWidgetContainer2.mChildren.clear();
            constraintWidgetContainer2.copy(constraintWidgetContainer, hashMap);
            Iterator<ConstraintWidget> it = arrayList.iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                if (next instanceof androidx.constraintlayout.solver.widgets.Barrier) {
                    constraintWidget = new androidx.constraintlayout.solver.widgets.Barrier();
                } else if (next instanceof Guideline) {
                    constraintWidget = new Guideline();
                } else if (next instanceof Flow) {
                    constraintWidget = new Flow();
                } else if (next instanceof Helper) {
                    constraintWidget = new HelperWidget();
                } else {
                    constraintWidget = new ConstraintWidget();
                }
                constraintWidgetContainer2.mChildren.add(constraintWidget);
                ConstraintWidget constraintWidget2 = constraintWidget.mParent;
                if (constraintWidget2 != null) {
                    ((WidgetContainer) constraintWidget2).mChildren.remove(constraintWidget);
                    constraintWidget.mParent = null;
                }
                constraintWidget.mParent = constraintWidgetContainer2;
                hashMap.put(next, constraintWidget);
            }
            Iterator<ConstraintWidget> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ConstraintWidget next2 = it2.next();
                ((ConstraintWidget) hashMap.get(next2)).copy(next2, hashMap);
            }
        }

        public static ConstraintWidget getWidget(ConstraintWidgetContainer constraintWidgetContainer, View view) {
            Objects.requireNonNull(constraintWidgetContainer);
            if (constraintWidgetContainer.mCompanionWidget == view) {
                return constraintWidgetContainer;
            }
            ArrayList<ConstraintWidget> arrayList = constraintWidgetContainer.mChildren;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ConstraintWidget constraintWidget = arrayList.get(i);
                Objects.requireNonNull(constraintWidget);
                if (constraintWidget.mCompanionWidget == view) {
                    return constraintWidget;
                }
            }
            return null;
        }
    }

    public static class MyTracker {

        /* renamed from: me */
        public static MyTracker f11me = new MyTracker();
        public VelocityTracker tracker;
    }

    public interface TransitionListener {
        void onTransitionChange();

        void onTransitionCompleted();

        void onTransitionStarted();

        void onTransitionTrigger();
    }

    public MotionLayout(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        return false;
    }

    public final boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
    }

    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
    }

    public final void onStopNestedScroll(View view, int i) {
        TouchResponse touchResponse;
        float f;
        boolean z;
        this.mScrollTarget = null;
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            float f2 = this.mScrollTargetDX;
            float f3 = this.mScrollTargetDT;
            float f4 = f2 / f3;
            float f5 = this.mScrollTargetDY / f3;
            Objects.requireNonNull(motionScene);
            MotionScene.Transition transition = motionScene.mCurrentTransition;
            if (transition != null && (touchResponse = transition.mTouchResponse) != null) {
                boolean z2 = false;
                touchResponse.mDragStarted = false;
                MotionLayout motionLayout = touchResponse.mMotionLayout;
                Objects.requireNonNull(motionLayout);
                float f6 = motionLayout.mTransitionLastPosition;
                touchResponse.mMotionLayout.getAnchorDpDt(touchResponse.mTouchAnchorId, f6, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                float f7 = touchResponse.mTouchDirectionX;
                float[] fArr = touchResponse.mAnchorDpDt;
                float f8 = fArr[0];
                float f9 = touchResponse.mTouchDirectionY;
                float f10 = fArr[1];
                float f11 = 0.0f;
                if (f7 != 0.0f) {
                    f = (f4 * f7) / fArr[0];
                } else {
                    f = (f5 * f9) / fArr[1];
                }
                if (!Float.isNaN(f)) {
                    f6 += f / 3.0f;
                }
                if (f6 != 0.0f) {
                    if (f6 != 1.0f) {
                        z = true;
                    } else {
                        z = false;
                    }
                    int i2 = touchResponse.mOnTouchUp;
                    if (i2 != 3) {
                        z2 = true;
                    }
                    if (z2 && z) {
                        MotionLayout motionLayout2 = touchResponse.mMotionLayout;
                        if (((double) f6) >= 0.5d) {
                            f11 = 1.0f;
                        }
                        motionLayout2.touchAnimateTo(i2, f11, f);
                    }
                }
            }
        }
    }

    public final void parseLayoutDescription(int i) {
    }

    public final void setProgress(float f) {
        if (f <= 0.0f) {
            this.mCurrentState = this.mBeginState;
        } else if (f >= 1.0f) {
            this.mCurrentState = this.mEndState;
        } else {
            this.mCurrentState = -1;
        }
        if (this.mScene != null) {
            this.mTransitionInstantly = true;
            this.mTransitionGoalPosition = f;
            this.mTransitionPosition = f;
            this.mTransitionLastTime = -1;
            this.mAnimationStartTime = -1;
            this.mInterpolator = null;
            this.mInTransition = true;
            invalidate();
        }
    }

    public final void setTransition(int i) {
        MotionScene.Transition transition;
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            Iterator<MotionScene.Transition> it = motionScene.mTransitionList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    transition = null;
                    break;
                }
                transition = it.next();
                if (transition.mId == i) {
                    break;
                }
            }
            Objects.requireNonNull(transition);
            int i2 = transition.mConstraintSetStart;
            this.mBeginState = i2;
            int i3 = transition.mConstraintSetEnd;
            this.mEndState = i3;
            float f = Float.NaN;
            int i4 = this.mCurrentState;
            if (i4 == i2) {
                f = 0.0f;
            } else if (i4 == i3) {
                f = 1.0f;
            }
            MotionScene motionScene2 = this.mScene;
            Objects.requireNonNull(motionScene2);
            motionScene2.mCurrentTransition = transition;
            TouchResponse touchResponse = transition.mTouchResponse;
            if (touchResponse != null) {
                touchResponse.setRTL(motionScene2.mRtl);
            }
            this.mModel.initFrom(this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
            rebuildScene();
            this.mTransitionLastPosition = Float.isNaN(f) ? 0.0f : f;
            if (Float.isNaN(f)) {
                Log.v("MotionLayout", Debug.getLocation() + " transitionToStart ");
                animateTo(0.0f);
                return;
            }
            setProgress(f);
        }
    }

    public final void animateTo(float f) {
        int i;
        Interpolator interpolator;
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            float f2 = this.mTransitionLastPosition;
            float f3 = this.mTransitionPosition;
            if (f2 != f3 && this.mTransitionInstantly) {
                this.mTransitionLastPosition = f3;
            }
            float f4 = this.mTransitionLastPosition;
            if (f4 != f) {
                this.mTemporalInterpolator = false;
                this.mTransitionGoalPosition = f;
                MotionScene.Transition transition = motionScene.mCurrentTransition;
                if (transition != null) {
                    i = transition.mDuration;
                } else {
                    i = motionScene.mDefaultDuration;
                }
                this.mTransitionDuration = ((float) i) / 1000.0f;
                setProgress(f);
                MotionScene motionScene2 = this.mScene;
                Objects.requireNonNull(motionScene2);
                MotionScene.Transition transition2 = motionScene2.mCurrentTransition;
                int i2 = transition2.mDefaultInterpolator;
                if (i2 == -2) {
                    interpolator = AnimationUtils.loadInterpolator(motionScene2.mMotionLayout.getContext(), motionScene2.mCurrentTransition.mDefaultInterpolatorID);
                } else if (i2 == -1) {
                    interpolator = new Interpolator() {
                        public final float getInterpolation(
/*
Method generation error in method: androidx.constraintlayout.motion.widget.MotionScene.1.getInterpolation(float):float, dex: classes.dex
                        jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: androidx.constraintlayout.motion.widget.MotionScene.1.getInterpolation(float):float, class status: UNLOADED
                        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:175)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:152)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                        
*/
                    };
                } else if (i2 == 0) {
                    interpolator = new AccelerateDecelerateInterpolator();
                } else if (i2 == 1) {
                    interpolator = new AccelerateInterpolator();
                } else if (i2 == 2) {
                    interpolator = new android.view.animation.DecelerateInterpolator();
                } else if (i2 == 4) {
                    interpolator = new AnticipateInterpolator();
                } else if (i2 != 5) {
                    interpolator = null;
                } else {
                    interpolator = new BounceInterpolator();
                }
                this.mInterpolator = interpolator;
                this.mTransitionInstantly = false;
                this.mAnimationStartTime = System.nanoTime();
                this.mInTransition = true;
                this.mTransitionPosition = f4;
                this.mTransitionLastPosition = f4;
                invalidate();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x02b7  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x02e3  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x02fe  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x030e  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0319  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0328  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0332  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x033f  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x034b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dispatchDraw(android.graphics.Canvas r29) {
        /*
            r28 = this;
            r0 = r28
            r1 = r29
            r2 = 0
            r0.evaluate(r2)
            super.dispatchDraw(r29)
            androidx.constraintlayout.motion.widget.MotionScene r3 = r0.mScene
            if (r3 != 0) goto L_0x0010
            return
        L_0x0010:
            int r3 = r0.mDebugPath
            r4 = 1
            r3 = r3 & r4
            r5 = 1093664768(0x41300000, float:11.0)
            r6 = 1092616192(0x41200000, float:10.0)
            if (r3 != r4) goto L_0x00d2
            boolean r3 = r28.isInEditMode()
            if (r3 != 0) goto L_0x00d2
            int r3 = r0.mFrames
            int r3 = r3 + r4
            r0.mFrames = r3
            long r7 = java.lang.System.nanoTime()
            long r9 = r0.mLastDrawTime
            r11 = -1
            int r3 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r3 == 0) goto L_0x0050
            long r9 = r7 - r9
            r11 = 200000000(0xbebc200, double:9.8813129E-316)
            int r3 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r3 <= 0) goto L_0x0052
            int r3 = r0.mFrames
            float r3 = (float) r3
            float r9 = (float) r9
            r10 = 814313567(0x3089705f, float:1.0E-9)
            float r9 = r9 * r10
            float r3 = r3 / r9
            r9 = 1120403456(0x42c80000, float:100.0)
            float r3 = r3 * r9
            int r3 = (int) r3
            float r3 = (float) r3
            float r3 = r3 / r9
            r0.mLastFps = r3
            r0.mFrames = r2
            r0.mLastDrawTime = r7
            goto L_0x0052
        L_0x0050:
            r0.mLastDrawTime = r7
        L_0x0052:
            android.graphics.Paint r3 = new android.graphics.Paint
            r3.<init>()
            r7 = 1109917696(0x42280000, float:42.0)
            r3.setTextSize(r7)
            float r7 = r0.mTransitionLastPosition
            r8 = 1148846080(0x447a0000, float:1000.0)
            float r7 = r7 * r8
            int r7 = (int) r7
            float r7 = (float) r7
            float r7 = r7 / r6
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            float r9 = r0.mLastFps
            r8.append(r9)
            java.lang.String r9 = " fps "
            r8.append(r9)
            int r9 = r0.mBeginState
            java.lang.String r9 = androidx.constraintlayout.motion.widget.Debug.getState(r0, r9)
            r8.append(r9)
            java.lang.String r9 = " -> "
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            java.lang.StringBuilder r8 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r8)
            int r9 = r0.mEndState
            java.lang.String r9 = androidx.constraintlayout.motion.widget.Debug.getState(r0, r9)
            r8.append(r9)
            java.lang.String r9 = " (progress: "
            r8.append(r9)
            r8.append(r7)
            java.lang.String r7 = " ) state="
            r8.append(r7)
            int r7 = r0.mCurrentState
            r9 = -1
            if (r7 != r9) goto L_0x00a8
            java.lang.String r7 = "undefined"
            goto L_0x00ac
        L_0x00a8:
            java.lang.String r7 = androidx.constraintlayout.motion.widget.Debug.getState(r0, r7)
        L_0x00ac:
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            r8 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r3.setColor(r8)
            int r8 = r28.getHeight()
            int r8 = r8 + -29
            float r8 = (float) r8
            r1.drawText(r7, r5, r8, r3)
            r8 = -7864184(0xffffffffff880088, float:NaN)
            r3.setColor(r8)
            int r8 = r28.getHeight()
            int r8 = r8 + -30
            float r8 = (float) r8
            r1.drawText(r7, r6, r8, r3)
        L_0x00d2:
            int r3 = r0.mDebugPath
            if (r3 <= r4) goto L_0x0490
            androidx.constraintlayout.motion.widget.MotionLayout$DevModeDraw r3 = r0.mDevModeDraw
            if (r3 != 0) goto L_0x00e1
            androidx.constraintlayout.motion.widget.MotionLayout$DevModeDraw r3 = new androidx.constraintlayout.motion.widget.MotionLayout$DevModeDraw
            r3.<init>()
            r0.mDevModeDraw = r3
        L_0x00e1:
            androidx.constraintlayout.motion.widget.MotionLayout$DevModeDraw r3 = r0.mDevModeDraw
            java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r7 = r0.mFrameArrayList
            androidx.constraintlayout.motion.widget.MotionScene r8 = r0.mScene
            java.util.Objects.requireNonNull(r8)
            androidx.constraintlayout.motion.widget.MotionScene$Transition r9 = r8.mCurrentTransition
            if (r9 == 0) goto L_0x00f1
            int r8 = r9.mDuration
            goto L_0x00f3
        L_0x00f1:
            int r8 = r8.mDefaultDuration
        L_0x00f3:
            int r0 = r0.mDebugPath
            java.util.Objects.requireNonNull(r3)
            if (r7 == 0) goto L_0x0490
            int r9 = r7.size()
            if (r9 != 0) goto L_0x0102
            goto L_0x0490
        L_0x0102:
            r29.save()
            androidx.constraintlayout.motion.widget.MotionLayout r9 = androidx.constraintlayout.motion.widget.MotionLayout.this
            boolean r9 = r9.isInEditMode()
            r10 = 2
            if (r9 != 0) goto L_0x015b
            r9 = r0 & 1
            if (r9 != r10) goto L_0x015b
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            androidx.constraintlayout.motion.widget.MotionLayout r10 = androidx.constraintlayout.motion.widget.MotionLayout.this
            android.content.Context r10 = r10.getContext()
            android.content.res.Resources r10 = r10.getResources()
            androidx.constraintlayout.motion.widget.MotionLayout r11 = androidx.constraintlayout.motion.widget.MotionLayout.this
            int r11 = r11.mEndState
            java.lang.String r10 = r10.getResourceName(r11)
            r9.append(r10)
            java.lang.String r10 = ":"
            r9.append(r10)
            androidx.constraintlayout.motion.widget.MotionLayout r10 = androidx.constraintlayout.motion.widget.MotionLayout.this
            java.util.Objects.requireNonNull(r10)
            float r10 = r10.mTransitionLastPosition
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            androidx.constraintlayout.motion.widget.MotionLayout r10 = androidx.constraintlayout.motion.widget.MotionLayout.this
            int r10 = r10.getHeight()
            int r10 = r10 + -30
            float r10 = (float) r10
            android.graphics.Paint r11 = r3.mTextPaint
            r1.drawText(r9, r6, r10, r11)
            androidx.constraintlayout.motion.widget.MotionLayout r6 = androidx.constraintlayout.motion.widget.MotionLayout.this
            int r6 = r6.getHeight()
            int r6 = r6 + -29
            float r6 = (float) r6
            android.graphics.Paint r10 = r3.mPaint
            r1.drawText(r9, r5, r6, r10)
        L_0x015b:
            java.util.Collection r5 = r7.values()
            java.util.Iterator r5 = r5.iterator()
            r6 = r1
            r7 = r6
        L_0x0165:
            boolean r9 = r5.hasNext()
            if (r9 == 0) goto L_0x048d
            java.lang.Object r9 = r5.next()
            androidx.constraintlayout.motion.widget.MotionController r9 = (androidx.constraintlayout.motion.widget.MotionController) r9
            java.util.Objects.requireNonNull(r9)
            androidx.constraintlayout.motion.widget.MotionPaths r10 = r9.mStartMotionPath
            int r10 = r10.mDrawPath
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r11 = r9.mMotionPaths
            java.util.Iterator r11 = r11.iterator()
        L_0x017e:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0191
            java.lang.Object r12 = r11.next()
            androidx.constraintlayout.motion.widget.MotionPaths r12 = (androidx.constraintlayout.motion.widget.MotionPaths) r12
            int r12 = r12.mDrawPath
            int r10 = java.lang.Math.max(r10, r12)
            goto L_0x017e
        L_0x0191:
            androidx.constraintlayout.motion.widget.MotionPaths r11 = r9.mEndMotionPath
            int r11 = r11.mDrawPath
            int r10 = java.lang.Math.max(r10, r11)
            if (r0 <= 0) goto L_0x019e
            if (r10 != 0) goto L_0x019e
            r10 = r4
        L_0x019e:
            if (r10 != 0) goto L_0x01a1
            goto L_0x0165
        L_0x01a1:
            float[] r4 = r3.mKeyFramePoints
            int[] r11 = r3.mPathMode
            if (r4 == 0) goto L_0x01f5
            androidx.constraintlayout.motion.utils.CurveFit[] r12 = r9.mSpline
            r12 = r12[r2]
            double[] r12 = r12.getTimePoints()
            if (r11 == 0) goto L_0x01cd
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r13 = r9.mMotionPaths
            java.util.Iterator r13 = r13.iterator()
            r14 = r2
        L_0x01b8:
            boolean r15 = r13.hasNext()
            if (r15 == 0) goto L_0x01cd
            java.lang.Object r15 = r13.next()
            androidx.constraintlayout.motion.widget.MotionPaths r15 = (androidx.constraintlayout.motion.widget.MotionPaths) r15
            int r16 = r14 + 1
            int r15 = r15.mMode
            r11[r14] = r15
            r14 = r16
            goto L_0x01b8
        L_0x01cd:
            r11 = r2
            r13 = r11
        L_0x01cf:
            int r14 = r12.length
            if (r2 >= r14) goto L_0x01f0
            androidx.constraintlayout.motion.utils.CurveFit[] r14 = r9.mSpline
            r13 = r14[r13]
            r14 = r12[r2]
            r16 = r0
            double[] r0 = r9.mInterpolateData
            r13.getPos((double) r14, (double[]) r0)
            androidx.constraintlayout.motion.widget.MotionPaths r0 = r9.mStartMotionPath
            int[] r13 = r9.mInterpolateVariables
            double[] r14 = r9.mInterpolateData
            r0.getCenter(r13, r14, r4, r11)
            int r11 = r11 + 2
            int r2 = r2 + 1
            r13 = 0
            r0 = r16
            goto L_0x01cf
        L_0x01f0:
            r16 = r0
            int r11 = r11 / 2
            goto L_0x01f8
        L_0x01f5:
            r16 = r0
            r11 = 0
        L_0x01f8:
            r3.mKeyFrameCount = r11
            r0 = 1
            if (r10 < r0) goto L_0x047f
            int r0 = r8 / 16
            float[] r2 = r3.mPoints
            if (r2 == 0) goto L_0x0208
            int r2 = r2.length
            int r4 = r0 * 2
            if (r2 == r4) goto L_0x0215
        L_0x0208:
            int r2 = r0 * 2
            float[] r2 = new float[r2]
            r3.mPoints = r2
            android.graphics.Path r2 = new android.graphics.Path
            r2.<init>()
            r3.mPath = r2
        L_0x0215:
            int r2 = r3.mShadowTranslate
            float r2 = (float) r2
            r6.translate(r2, r2)
            android.graphics.Paint r2 = r3.mPaint
            r4 = 1996488704(0x77000000, float:2.5961484E33)
            r2.setColor(r4)
            android.graphics.Paint r2 = r3.mFillPaint
            r2.setColor(r4)
            android.graphics.Paint r2 = r3.mPaintKeyframes
            r2.setColor(r4)
            android.graphics.Paint r2 = r3.mPaintGraph
            r2.setColor(r4)
            float[] r2 = r3.mPoints
            int r4 = r0 + -1
            float r4 = (float) r4
            r6 = 1065353216(0x3f800000, float:1.0)
            float r6 = r6 / r4
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r4 = r9.mAttributesMap
            java.lang.String r11 = "translationX"
            if (r4 != 0) goto L_0x0242
            r4 = 0
            goto L_0x0248
        L_0x0242:
            java.lang.Object r4 = r4.get(r11)
            androidx.constraintlayout.motion.widget.SplineSet r4 = (androidx.constraintlayout.motion.widget.SplineSet) r4
        L_0x0248:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r12 = r9.mAttributesMap
            java.lang.String r13 = "translationY"
            if (r12 != 0) goto L_0x0251
            r12 = 0
            goto L_0x0257
        L_0x0251:
            java.lang.Object r12 = r12.get(r13)
            androidx.constraintlayout.motion.widget.SplineSet r12 = (androidx.constraintlayout.motion.widget.SplineSet) r12
        L_0x0257:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.KeyCycleOscillator> r14 = r9.mCycleMap
            if (r14 != 0) goto L_0x025d
            r11 = 0
            goto L_0x0263
        L_0x025d:
            java.lang.Object r11 = r14.get(r11)
            androidx.constraintlayout.motion.widget.KeyCycleOscillator r11 = (androidx.constraintlayout.motion.widget.KeyCycleOscillator) r11
        L_0x0263:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.KeyCycleOscillator> r14 = r9.mCycleMap
            if (r14 != 0) goto L_0x0269
            r13 = 0
            goto L_0x026f
        L_0x0269:
            java.lang.Object r13 = r14.get(r13)
            androidx.constraintlayout.motion.widget.KeyCycleOscillator r13 = (androidx.constraintlayout.motion.widget.KeyCycleOscillator) r13
        L_0x026f:
            r14 = 0
        L_0x0270:
            r17 = 0
            if (r14 >= r0) goto L_0x0366
            float r15 = (float) r14
            float r15 = r15 * r6
            r18 = r0
            float r0 = r9.mStaggerScale
            r19 = 1065353216(0x3f800000, float:1.0)
            int r20 = (r0 > r19 ? 1 : (r0 == r19 ? 0 : -1))
            if (r20 == 0) goto L_0x029c
            r20 = r5
            float r5 = r9.mStaggerOffset
            int r21 = (r15 > r5 ? 1 : (r15 == r5 ? 0 : -1))
            if (r21 >= 0) goto L_0x028a
            r15 = r17
        L_0x028a:
            int r21 = (r15 > r5 ? 1 : (r15 == r5 ? 0 : -1))
            r22 = r6
            if (r21 <= 0) goto L_0x02a0
            r21 = r7
            double r6 = (double) r15
            r23 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r6 = (r6 > r23 ? 1 : (r6 == r23 ? 0 : -1))
            if (r6 >= 0) goto L_0x02a2
            float r15 = r15 - r5
            float r15 = r15 * r0
            goto L_0x02a2
        L_0x029c:
            r20 = r5
            r22 = r6
        L_0x02a0:
            r21 = r7
        L_0x02a2:
            double r5 = (double) r15
            androidx.constraintlayout.motion.widget.MotionPaths r0 = r9.mStartMotionPath
            androidx.constraintlayout.motion.utils.Easing r0 = r0.mKeyFrameEasing
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r7 = r9.mMotionPaths
            java.util.Iterator r7 = r7.iterator()
            r23 = r17
            r17 = 2143289344(0x7fc00000, float:NaN)
        L_0x02b1:
            boolean r24 = r7.hasNext()
            if (r24 == 0) goto L_0x02df
            java.lang.Object r24 = r7.next()
            r25 = r5
            r5 = r24
            androidx.constraintlayout.motion.widget.MotionPaths r5 = (androidx.constraintlayout.motion.widget.MotionPaths) r5
            androidx.constraintlayout.motion.utils.Easing r6 = r5.mKeyFrameEasing
            if (r6 == 0) goto L_0x02dc
            r24 = r6
            float r6 = r5.time
            int r27 = (r6 > r15 ? 1 : (r6 == r15 ? 0 : -1))
            if (r27 >= 0) goto L_0x02d2
            r23 = r6
            r0 = r24
            goto L_0x02dc
        L_0x02d2:
            boolean r6 = java.lang.Float.isNaN(r17)
            if (r6 == 0) goto L_0x02dc
            float r5 = r5.time
            r17 = r5
        L_0x02dc:
            r5 = r25
            goto L_0x02b1
        L_0x02df:
            r25 = r5
            if (r0 == 0) goto L_0x02fe
            boolean r5 = java.lang.Float.isNaN(r17)
            if (r5 == 0) goto L_0x02ea
            goto L_0x02ec
        L_0x02ea:
            r19 = r17
        L_0x02ec:
            float r5 = r15 - r23
            float r19 = r19 - r23
            float r5 = r5 / r19
            double r5 = (double) r5
            double r5 = r0.get(r5)
            float r0 = (float) r5
            float r0 = r0 * r19
            float r0 = r0 + r23
            double r5 = (double) r0
            goto L_0x0300
        L_0x02fe:
            r5 = r25
        L_0x0300:
            androidx.constraintlayout.motion.utils.CurveFit[] r0 = r9.mSpline
            r7 = 0
            r0 = r0[r7]
            double[] r7 = r9.mInterpolateData
            r0.getPos((double) r5, (double[]) r7)
            androidx.constraintlayout.motion.utils.ArcCurveFit r0 = r9.mArcSpline
            if (r0 == 0) goto L_0x0319
            double[] r7 = r9.mInterpolateData
            r19 = r8
            int r8 = r7.length
            if (r8 <= 0) goto L_0x031b
            r0.getPos((double) r5, (double[]) r7)
            goto L_0x031b
        L_0x0319:
            r19 = r8
        L_0x031b:
            androidx.constraintlayout.motion.widget.MotionPaths r0 = r9.mStartMotionPath
            int[] r5 = r9.mInterpolateVariables
            double[] r6 = r9.mInterpolateData
            int r7 = r14 * 2
            r0.getCenter(r5, r6, r2, r7)
            if (r11 == 0) goto L_0x0332
            r0 = r2[r7]
            float r5 = r11.get(r15)
            float r5 = r5 + r0
            r2[r7] = r5
            goto L_0x033d
        L_0x0332:
            if (r4 == 0) goto L_0x033d
            r0 = r2[r7]
            float r5 = r4.get(r15)
            float r5 = r5 + r0
            r2[r7] = r5
        L_0x033d:
            if (r13 == 0) goto L_0x034b
            int r7 = r7 + 1
            r0 = r2[r7]
            float r5 = r13.get(r15)
            float r5 = r5 + r0
            r2[r7] = r5
            goto L_0x0358
        L_0x034b:
            if (r12 == 0) goto L_0x0358
            int r7 = r7 + 1
            r0 = r2[r7]
            float r5 = r12.get(r15)
            float r5 = r5 + r0
            r2[r7] = r5
        L_0x0358:
            int r14 = r14 + 1
            r0 = r18
            r8 = r19
            r5 = r20
            r7 = r21
            r6 = r22
            goto L_0x0270
        L_0x0366:
            r20 = r5
            r21 = r7
            r19 = r8
            int r0 = r3.mKeyFrameCount
            r3.drawAll(r7, r10, r0, r9)
            android.graphics.Paint r0 = r3.mPaint
            r2 = -21965(0xffffffffffffaa33, float:NaN)
            r0.setColor(r2)
            android.graphics.Paint r0 = r3.mPaintKeyframes
            r2 = -2067046(0xffffffffffe0759a, float:NaN)
            r0.setColor(r2)
            android.graphics.Paint r0 = r3.mFillPaint
            r0.setColor(r2)
            android.graphics.Paint r0 = r3.mPaintGraph
            r2 = -13391360(0xffffffffff33aa00, float:-2.388145E38)
            r0.setColor(r2)
            int r0 = r3.mShadowTranslate
            int r0 = -r0
            float r0 = (float) r0
            r7.translate(r0, r0)
            int r0 = r3.mKeyFrameCount
            r3.drawAll(r7, r10, r0, r9)
            r0 = 5
            if (r10 != r0) goto L_0x0479
            android.graphics.Path r0 = r3.mPath
            r0.reset()
            r0 = 0
        L_0x03a2:
            r2 = 50
            if (r0 > r2) goto L_0x044f
            float r4 = (float) r0
            float r2 = (float) r2
            float r4 = r4 / r2
            float[] r2 = r3.mRectangle
            r5 = 0
            float r4 = r9.getAdjustedPosition(r4, r5)
            androidx.constraintlayout.motion.utils.CurveFit[] r5 = r9.mSpline
            r6 = 0
            r5 = r5[r6]
            double r6 = (double) r4
            double[] r4 = r9.mInterpolateData
            r5.getPos((double) r6, (double[]) r4)
            androidx.constraintlayout.motion.widget.MotionPaths r4 = r9.mStartMotionPath
            int[] r5 = r9.mInterpolateVariables
            double[] r6 = r9.mInterpolateData
            java.util.Objects.requireNonNull(r4)
            float r7 = r4.f12x
            float r8 = r4.f13y
            float r10 = r4.width
            float r4 = r4.height
            r11 = 0
        L_0x03cd:
            int r12 = r5.length
            r13 = 3
            if (r11 >= r12) goto L_0x03ec
            r14 = r6[r11]
            float r14 = (float) r14
            r15 = r5[r11]
            r12 = 1
            if (r15 == r12) goto L_0x03e8
            r12 = 2
            if (r15 == r12) goto L_0x03e6
            if (r15 == r13) goto L_0x03e4
            r12 = 4
            if (r15 == r12) goto L_0x03e2
            goto L_0x03e9
        L_0x03e2:
            r4 = r14
            goto L_0x03e9
        L_0x03e4:
            r10 = r14
            goto L_0x03e9
        L_0x03e6:
            r8 = r14
            goto L_0x03e9
        L_0x03e8:
            r7 = r14
        L_0x03e9:
            int r11 = r11 + 1
            goto L_0x03cd
        L_0x03ec:
            float r10 = r10 + r7
            float r4 = r4 + r8
            r5 = 2143289344(0x7fc00000, float:NaN)
            java.lang.Float.isNaN(r5)
            java.lang.Float.isNaN(r5)
            float r7 = r7 + r17
            float r8 = r8 + r17
            float r10 = r10 + r17
            float r4 = r4 + r17
            r6 = 0
            r2[r6] = r7
            r6 = 1
            r2[r6] = r8
            r6 = 2
            r2[r6] = r10
            r2[r13] = r8
            r6 = 4
            r2[r6] = r10
            r6 = 5
            r2[r6] = r4
            r6 = 6
            r2[r6] = r7
            r7 = 7
            r2[r7] = r4
            android.graphics.Path r2 = r3.mPath
            float[] r4 = r3.mRectangle
            r8 = 0
            r8 = r4[r8]
            r10 = 1
            r4 = r4[r10]
            r2.moveTo(r8, r4)
            android.graphics.Path r2 = r3.mPath
            float[] r4 = r3.mRectangle
            r8 = 2
            r8 = r4[r8]
            r4 = r4[r13]
            r2.lineTo(r8, r4)
            android.graphics.Path r2 = r3.mPath
            float[] r4 = r3.mRectangle
            r8 = 4
            r8 = r4[r8]
            r10 = 5
            r4 = r4[r10]
            r2.lineTo(r8, r4)
            android.graphics.Path r2 = r3.mPath
            float[] r4 = r3.mRectangle
            r6 = r4[r6]
            r4 = r4[r7]
            r2.lineTo(r6, r4)
            android.graphics.Path r2 = r3.mPath
            r2.close()
            int r0 = r0 + 1
            goto L_0x03a2
        L_0x044f:
            r0 = 0
            r2 = 1
            android.graphics.Paint r4 = r3.mPaint
            r5 = 1140850688(0x44000000, float:512.0)
            r4.setColor(r5)
            r4 = 1073741824(0x40000000, float:2.0)
            r1.translate(r4, r4)
            android.graphics.Path r4 = r3.mPath
            android.graphics.Paint r5 = r3.mPaint
            r1.drawPath(r4, r5)
            r4 = -1073741824(0xffffffffc0000000, float:-2.0)
            r1.translate(r4, r4)
            android.graphics.Paint r4 = r3.mPaint
            r5 = -65536(0xffffffffffff0000, float:NaN)
            r4.setColor(r5)
            android.graphics.Path r4 = r3.mPath
            android.graphics.Paint r5 = r3.mPaint
            r1.drawPath(r4, r5)
            r7 = r1
            goto L_0x047b
        L_0x0479:
            r0 = 0
            r2 = 1
        L_0x047b:
            r4 = r2
            r6 = r7
            r2 = r0
            goto L_0x0485
        L_0x047f:
            r20 = r5
            r19 = r8
            r2 = 0
            r4 = r0
        L_0x0485:
            r0 = r16
            r8 = r19
            r5 = r20
            goto L_0x0165
        L_0x048d:
            r29.restore()
        L_0x0490:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.dispatchDraw(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:127:0x01b6, code lost:
        if (r0.mListenerState == r0.mEndState) goto L_0x01ba;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void evaluate(boolean r21) {
        /*
            r20 = this;
            r0 = r20
            long r1 = r0.mTransitionLastTime
            r3 = -1
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 != 0) goto L_0x0010
            long r1 = java.lang.System.nanoTime()
            r0.mTransitionLastTime = r1
        L_0x0010:
            float r1 = r0.mTransitionLastPosition
            r2 = 0
            int r3 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            r4 = -1
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r3 <= 0) goto L_0x0020
            int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r3 >= 0) goto L_0x0020
            r0.mCurrentState = r4
        L_0x0020:
            boolean r3 = r0.mKeepAnimating
            r6 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r8 = 0
            r9 = 1
            if (r3 != 0) goto L_0x0034
            boolean r3 = r0.mInTransition
            if (r3 == 0) goto L_0x017f
            if (r21 != 0) goto L_0x0034
            float r3 = r0.mTransitionGoalPosition
            int r3 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r3 == 0) goto L_0x017f
        L_0x0034:
            float r3 = r0.mTransitionGoalPosition
            float r3 = r3 - r1
            float r1 = java.lang.Math.signum(r3)
            long r10 = java.lang.System.nanoTime()
            android.view.animation.Interpolator r3 = r0.mInterpolator
            boolean r3 = r3 instanceof androidx.constraintlayout.motion.widget.MotionInterpolator
            r12 = 814313567(0x3089705f, float:1.0E-9)
            if (r3 != 0) goto L_0x0053
            long r13 = r0.mTransitionLastTime
            long r13 = r10 - r13
            float r3 = (float) r13
            float r3 = r3 * r1
            float r3 = r3 * r12
            float r13 = r0.mTransitionDuration
            float r3 = r3 / r13
            goto L_0x0054
        L_0x0053:
            r3 = r2
        L_0x0054:
            float r13 = r0.mTransitionLastPosition
            float r13 = r13 + r3
            boolean r3 = r0.mTransitionInstantly
            if (r3 == 0) goto L_0x005d
            float r13 = r0.mTransitionGoalPosition
        L_0x005d:
            int r3 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x0067
            float r14 = r0.mTransitionGoalPosition
            int r14 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r14 >= 0) goto L_0x0071
        L_0x0067:
            int r14 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r14 > 0) goto L_0x0077
            float r14 = r0.mTransitionGoalPosition
            int r14 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r14 > 0) goto L_0x0077
        L_0x0071:
            float r13 = r0.mTransitionGoalPosition
            r0.mInTransition = r8
            r14 = r9
            goto L_0x0078
        L_0x0077:
            r14 = r8
        L_0x0078:
            r0.mTransitionLastPosition = r13
            r0.mTransitionPosition = r13
            r0.mTransitionLastTime = r10
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionLayout$TransitionListener> r15 = r0.mTransitionListeners
            if (r15 == 0) goto L_0x0092
            boolean r15 = r15.isEmpty()
            if (r15 != 0) goto L_0x0092
            r20.fireTransitionChange()
            r0.mIsAnimating = r9
            if (r14 == 0) goto L_0x0092
            r20.fireTransitionCompleted()
        L_0x0092:
            android.view.animation.Interpolator r15 = r0.mInterpolator
            if (r15 == 0) goto L_0x00e4
            if (r14 != 0) goto L_0x00e4
            boolean r14 = r0.mTemporalInterpolator
            if (r14 == 0) goto L_0x00e0
            long r13 = r0.mAnimationStartTime
            long r13 = r10 - r13
            float r13 = (float) r13
            float r13 = r13 * r12
            float r12 = r15.getInterpolation(r13)
            r0.mTransitionLastPosition = r12
            r0.mTransitionLastTime = r10
            android.view.animation.Interpolator r10 = r0.mInterpolator
            boolean r11 = r10 instanceof androidx.constraintlayout.motion.widget.MotionInterpolator
            if (r11 == 0) goto L_0x00de
            androidx.constraintlayout.motion.widget.MotionInterpolator r10 = (androidx.constraintlayout.motion.widget.MotionInterpolator) r10
            float r10 = r10.getVelocity$1()
            float r11 = java.lang.Math.abs(r10)
            r13 = 953267991(0x38d1b717, float:1.0E-4)
            int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r11 > 0) goto L_0x00c3
            r0.mInTransition = r8
        L_0x00c3:
            int r11 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r11 <= 0) goto L_0x00d0
            int r11 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r11 < 0) goto L_0x00d0
            r0.mTransitionLastPosition = r5
            r0.mInTransition = r8
            r12 = r5
        L_0x00d0:
            int r10 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r10 >= 0) goto L_0x00de
            int r10 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r10 > 0) goto L_0x00de
            r0.mTransitionLastPosition = r2
            r0.mInTransition = r8
            r13 = r2
            goto L_0x00e4
        L_0x00de:
            r13 = r12
            goto L_0x00e4
        L_0x00e0:
            float r13 = r15.getInterpolation(r13)
        L_0x00e4:
            if (r3 <= 0) goto L_0x00ec
            float r3 = r0.mTransitionGoalPosition
            int r3 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x00f6
        L_0x00ec:
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L_0x00fa
            float r1 = r0.mTransitionGoalPosition
            int r1 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r1 > 0) goto L_0x00fa
        L_0x00f6:
            float r13 = r0.mTransitionGoalPosition
            r0.mInTransition = r8
        L_0x00fa:
            int r1 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x0102
            int r1 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r1 > 0) goto L_0x0104
        L_0x0102:
            r0.mInTransition = r8
        L_0x0104:
            int r1 = r20.getChildCount()
            r0.mKeepAnimating = r8
            long r10 = java.lang.System.nanoTime()
            r0.mPostInterpolationPosition = r13
            r3 = r8
        L_0x0111:
            if (r3 >= r1) goto L_0x0137
            android.view.View r15 = r0.getChildAt(r3)
            java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r12 = r0.mFrameArrayList
            java.lang.Object r12 = r12.get(r15)
            r14 = r12
            androidx.constraintlayout.motion.widget.MotionController r14 = (androidx.constraintlayout.motion.widget.MotionController) r14
            if (r14 == 0) goto L_0x0133
            boolean r12 = r0.mKeepAnimating
            androidx.constraintlayout.motion.widget.KeyCache r8 = r0.mKeyCache
            r16 = r13
            r17 = r10
            r19 = r8
            boolean r8 = r14.interpolate(r15, r16, r17, r19)
            r8 = r8 | r12
            r0.mKeepAnimating = r8
        L_0x0133:
            int r3 = r3 + 1
            r8 = 0
            goto L_0x0111
        L_0x0137:
            boolean r1 = r0.mMeasureDuringTransition
            if (r1 == 0) goto L_0x013e
            r20.requestLayout()
        L_0x013e:
            boolean r1 = r0.mKeepAnimating
            if (r1 == 0) goto L_0x0145
            r20.invalidate()
        L_0x0145:
            boolean r1 = r0.mInTransition
            if (r1 == 0) goto L_0x014c
            r20.invalidate()
        L_0x014c:
            int r1 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r1 > 0) goto L_0x0167
            int r1 = r0.mBeginState
            if (r1 == r4) goto L_0x0167
            int r3 = r0.mCurrentState
            if (r3 == r1) goto L_0x015a
            r8 = r9
            goto L_0x015b
        L_0x015a:
            r8 = 0
        L_0x015b:
            r0.mCurrentState = r1
            androidx.constraintlayout.motion.widget.MotionScene r3 = r0.mScene
            androidx.constraintlayout.widget.ConstraintSet r1 = r3.getConstraintSet(r1)
            r1.applyCustomAttributes(r0)
            goto L_0x0168
        L_0x0167:
            r8 = 0
        L_0x0168:
            double r3 = (double) r13
            int r1 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r1 < 0) goto L_0x017f
            int r1 = r0.mCurrentState
            int r3 = r0.mEndState
            if (r1 == r3) goto L_0x0174
            r8 = r9
        L_0x0174:
            r0.mCurrentState = r3
            androidx.constraintlayout.motion.widget.MotionScene r1 = r0.mScene
            androidx.constraintlayout.widget.ConstraintSet r1 = r1.getConstraintSet(r3)
            r1.applyCustomAttributes(r0)
        L_0x017f:
            float r1 = r0.mTransitionLastPosition
            int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r3 < 0) goto L_0x018f
            int r2 = r0.mCurrentState
            int r3 = r0.mEndState
            if (r2 == r3) goto L_0x018c
            r8 = r9
        L_0x018c:
            r0.mCurrentState = r3
            goto L_0x019c
        L_0x018f:
            int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r2 > 0) goto L_0x019c
            int r2 = r0.mCurrentState
            int r3 = r0.mBeginState
            if (r2 == r3) goto L_0x019a
            r8 = r9
        L_0x019a:
            r0.mCurrentState = r3
        L_0x019c:
            boolean r2 = r0.mIsAnimating
            if (r2 == 0) goto L_0x01b9
            double r1 = (double) r1
            r3 = 0
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 > 0) goto L_0x01ae
            int r3 = r0.mListenerState
            int r4 = r0.mBeginState
            if (r3 != r4) goto L_0x01ae
            r8 = r9
        L_0x01ae:
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 < 0) goto L_0x01b9
            int r1 = r0.mListenerState
            int r2 = r0.mEndState
            if (r1 != r2) goto L_0x01b9
            goto L_0x01ba
        L_0x01b9:
            r9 = r8
        L_0x01ba:
            boolean r1 = r0.mNeedsFireTransitionCompleted
            r1 = r1 | r9
            r0.mNeedsFireTransitionCompleted = r1
            if (r9 == 0) goto L_0x01ca
            boolean r1 = r20.isInLayout()
            if (r1 != 0) goto L_0x01ca
            r20.requestLayout()
        L_0x01ca:
            float r1 = r0.mTransitionLastPosition
            r0.mTransitionPosition = r1
            if (r9 != 0) goto L_0x01d4
            boolean r1 = r0.mIsAnimating
            if (r1 == 0) goto L_0x01dc
        L_0x01d4:
            r20.fireTransitionChange()
            if (r9 == 0) goto L_0x01dc
            r20.fireTransitionCompleted()
        L_0x01dc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.evaluate(boolean):void");
    }

    public final void fireTransitionChange() {
        ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
        if (arrayList != null && !arrayList.isEmpty() && this.mListenerPosition != this.mTransitionPosition) {
            if (this.mListenerState != -1) {
                ArrayList<TransitionListener> arrayList2 = this.mTransitionListeners;
                if (arrayList2 != null) {
                    Iterator<TransitionListener> it = arrayList2.iterator();
                    while (it.hasNext()) {
                        it.next().onTransitionStarted();
                    }
                }
                this.mIsAnimating = true;
            }
            this.mListenerState = -1;
            this.mListenerPosition = this.mTransitionPosition;
            ArrayList<TransitionListener> arrayList3 = this.mTransitionListeners;
            if (arrayList3 != null) {
                Iterator<TransitionListener> it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    it2.next().onTransitionChange();
                }
            }
            this.mIsAnimating = true;
        }
    }

    public final void fireTransitionCompleted() {
        int i;
        ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
        if (arrayList != null && !arrayList.isEmpty() && this.mListenerState == -1) {
            this.mListenerState = this.mCurrentState;
            if (!this.mTransitionCompleted.isEmpty()) {
                ArrayList<Integer> arrayList2 = this.mTransitionCompleted;
                i = arrayList2.get(arrayList2.size() - 1).intValue();
            } else {
                i = -1;
            }
            int i2 = this.mCurrentState;
            if (i != i2 && i2 != -1) {
                this.mTransitionCompleted.add(Integer.valueOf(i2));
            }
        }
    }

    public final void getAnchorDpDt(int i, float f, float f2, float f3, float[] fArr) {
        String str;
        HashMap<View, MotionController> hashMap = this.mFrameArrayList;
        View viewById = getViewById(i);
        MotionController motionController = hashMap.get(viewById);
        if (motionController != null) {
            motionController.getDpDt(f, f2, f3, fArr);
            viewById.getY();
            return;
        }
        if (viewById == null) {
            str = VendorAtomValue$$ExternalSyntheticOutline0.m0m("", i);
        } else {
            str = viewById.getContext().getResources().getResourceName(i);
        }
        MotionLayout$$ExternalSyntheticOutline0.m9m("WARNING could not find view id ", str, "MotionLayout");
    }

    public final boolean handlesTouchEvent(float f, float f2, View view, MotionEvent motionEvent) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (handlesTouchEvent(((float) view.getLeft()) + f, ((float) view.getTop()) + f2, viewGroup.getChildAt(i), motionEvent)) {
                    return true;
                }
            }
        }
        this.mBoundsCheck.set(((float) view.getLeft()) + f, ((float) view.getTop()) + f2, f + ((float) view.getRight()), f2 + ((float) view.getBottom()));
        if (motionEvent.getAction() == 0) {
            if (!this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY()) || !view.onTouchEvent(motionEvent)) {
                return false;
            }
            return true;
        } else if (view.onTouchEvent(motionEvent)) {
            return true;
        }
        return false;
    }

    public final void onAttachedToWindow() {
        int i;
        super.onAttachedToWindow();
        MotionScene motionScene = this.mScene;
        if (!(motionScene == null || (i = this.mCurrentState) == -1)) {
            ConstraintSet constraintSet = motionScene.getConstraintSet(i);
            MotionScene motionScene2 = this.mScene;
            Objects.requireNonNull(motionScene2);
            int i2 = 0;
            while (true) {
                boolean z = true;
                if (i2 < motionScene2.mConstraintSetMap.size()) {
                    int keyAt = motionScene2.mConstraintSetMap.keyAt(i2);
                    int i3 = motionScene2.mDeriveMap.get(keyAt);
                    int size = motionScene2.mDeriveMap.size();
                    while (true) {
                        if (i3 <= 0) {
                            z = false;
                            break;
                        } else if (i3 == keyAt) {
                            break;
                        } else {
                            int i4 = size - 1;
                            if (size < 0) {
                                break;
                            }
                            i3 = motionScene2.mDeriveMap.get(i3);
                            size = i4;
                        }
                    }
                    if (z) {
                        Log.e("MotionScene", "Cannot be derived from yourself");
                        break;
                    } else {
                        motionScene2.readConstraintChain(keyAt);
                        i2++;
                    }
                } else {
                    for (int i5 = 0; i5 < motionScene2.mConstraintSetMap.size(); i5++) {
                        ConstraintSet valueAt = motionScene2.mConstraintSetMap.valueAt(i5);
                        Objects.requireNonNull(valueAt);
                        int childCount = getChildCount();
                        int i6 = 0;
                        while (i6 < childCount) {
                            View childAt = getChildAt(i6);
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
                            int id = childAt.getId();
                            if (!valueAt.mForceId || id != -1) {
                                if (!valueAt.mConstraints.containsKey(Integer.valueOf(id))) {
                                    valueAt.mConstraints.put(Integer.valueOf(id), new ConstraintSet.Constraint());
                                }
                                ConstraintSet.Constraint constraint = valueAt.mConstraints.get(Integer.valueOf(id));
                                if (!constraint.layout.mApply) {
                                    constraint.fillFrom(id, layoutParams);
                                    if (childAt instanceof ConstraintHelper) {
                                        ConstraintHelper constraintHelper = (ConstraintHelper) childAt;
                                        constraint.layout.mReferenceIds = Arrays.copyOf(constraintHelper.mIds, constraintHelper.mCount);
                                        if (childAt instanceof Barrier) {
                                            Barrier barrier = (Barrier) childAt;
                                            ConstraintSet.Layout layout = constraint.layout;
                                            androidx.constraintlayout.solver.widgets.Barrier barrier2 = barrier.mBarrier;
                                            Objects.requireNonNull(barrier2);
                                            layout.mBarrierAllowsGoneWidgets = barrier2.mAllowsGoneWidget;
                                            ConstraintSet.Layout layout2 = constraint.layout;
                                            layout2.mBarrierDirection = barrier.mIndicatedType;
                                            androidx.constraintlayout.solver.widgets.Barrier barrier3 = barrier.mBarrier;
                                            Objects.requireNonNull(barrier3);
                                            layout2.mBarrierMargin = barrier3.mMargin;
                                        }
                                    }
                                    constraint.layout.mApply = true;
                                }
                                ConstraintSet.PropertySet propertySet = constraint.propertySet;
                                if (!propertySet.mApply) {
                                    propertySet.visibility = childAt.getVisibility();
                                    constraint.propertySet.alpha = childAt.getAlpha();
                                    constraint.propertySet.mApply = true;
                                }
                                ConstraintSet.Transform transform = constraint.transform;
                                if (!transform.mApply) {
                                    transform.mApply = true;
                                    transform.rotation = childAt.getRotation();
                                    constraint.transform.rotationX = childAt.getRotationX();
                                    constraint.transform.rotationY = childAt.getRotationY();
                                    constraint.transform.scaleX = childAt.getScaleX();
                                    constraint.transform.scaleY = childAt.getScaleY();
                                    float pivotX = childAt.getPivotX();
                                    float pivotY = childAt.getPivotY();
                                    if (!(((double) pivotX) == 0.0d && ((double) pivotY) == 0.0d)) {
                                        ConstraintSet.Transform transform2 = constraint.transform;
                                        transform2.transformPivotX = pivotX;
                                        transform2.transformPivotY = pivotY;
                                    }
                                    constraint.transform.translationX = childAt.getTranslationX();
                                    constraint.transform.translationY = childAt.getTranslationY();
                                    constraint.transform.translationZ = childAt.getTranslationZ();
                                    ConstraintSet.Transform transform3 = constraint.transform;
                                    if (transform3.applyElevation) {
                                        transform3.elevation = childAt.getElevation();
                                    }
                                }
                                i6++;
                            } else {
                                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                            }
                        }
                    }
                }
            }
            if (constraintSet != null) {
                constraintSet.applyTo(this);
            }
            this.mBeginState = this.mCurrentState;
        }
        onNewStateAttachHandlers();
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionScene.Transition transition;
        TouchResponse touchResponse;
        int i;
        RectF touchRegion;
        MotionScene motionScene = this.mScene;
        if (motionScene != null && this.mInteractionEnabled && (transition = motionScene.mCurrentTransition) != null && (!transition.mDisable) && (touchResponse = transition.mTouchResponse) != null && ((motionEvent.getAction() != 0 || (touchRegion = touchResponse.getTouchRegion(this, new RectF())) == null || touchRegion.contains(motionEvent.getX(), motionEvent.getY())) && (i = touchResponse.mTouchRegionId) != -1)) {
            View view = this.mRegionView;
            if (view == null || view.getId() != i) {
                this.mRegionView = findViewById(i);
            }
            View view2 = this.mRegionView;
            if (view2 != null) {
                this.mBoundsCheck.set((float) view2.getLeft(), (float) this.mRegionView.getTop(), (float) this.mRegionView.getRight(), (float) this.mRegionView.getBottom());
                if (this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY()) && !handlesTouchEvent(0.0f, 0.0f, this.mRegionView, motionEvent)) {
                    return onTouchEvent(motionEvent);
                }
            }
        }
        return false;
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mScene == null) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int i5 = i3 - i;
        int i6 = i4 - i2;
        if (!(this.mLastLayoutWidth == i5 && this.mLastLayoutHeight == i6)) {
            rebuildScene();
            evaluate(true);
        }
        this.mLastLayoutWidth = i5;
        this.mLastLayoutHeight = i6;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0099, code lost:
        if (r3 != false) goto L_0x009b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:102:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0137  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0159  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0163  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0187  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01a6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r18, int r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            androidx.constraintlayout.motion.widget.MotionScene r3 = r0.mScene
            if (r3 != 0) goto L_0x000e
            super.onMeasure(r18, r19)
            return
        L_0x000e:
            int r3 = r0.mLastWidthMeasureSpec
            r4 = 0
            r5 = 1
            if (r3 != r1) goto L_0x001b
            int r3 = r0.mLastHeightMeasureSpec
            if (r3 == r2) goto L_0x0019
            goto L_0x001b
        L_0x0019:
            r3 = r4
            goto L_0x001c
        L_0x001b:
            r3 = r5
        L_0x001c:
            boolean r6 = r0.mNeedsFireTransitionCompleted
            if (r6 == 0) goto L_0x0069
            r0.mNeedsFireTransitionCompleted = r4
            r17.onNewStateAttachHandlers()
            boolean r3 = r0.mIsAnimating
            if (r3 == 0) goto L_0x0068
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionLayout$TransitionListener> r3 = r0.mTransitionListeners
            if (r3 == 0) goto L_0x0068
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x0034
            goto L_0x0068
        L_0x0034:
            r0.mIsAnimating = r4
            java.util.ArrayList<java.lang.Integer> r3 = r0.mTransitionCompleted
            java.util.Iterator r3 = r3.iterator()
        L_0x003c:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x0063
            java.lang.Object r6 = r3.next()
            java.lang.Integer r6 = (java.lang.Integer) r6
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionLayout$TransitionListener> r7 = r0.mTransitionListeners
            if (r7 == 0) goto L_0x003c
            java.util.Iterator r7 = r7.iterator()
        L_0x0050:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x003c
            java.lang.Object r8 = r7.next()
            androidx.constraintlayout.motion.widget.MotionLayout$TransitionListener r8 = (androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener) r8
            r6.intValue()
            r8.onTransitionCompleted()
            goto L_0x0050
        L_0x0063:
            java.util.ArrayList<java.lang.Integer> r3 = r0.mTransitionCompleted
            r3.clear()
        L_0x0068:
            r3 = r5
        L_0x0069:
            boolean r6 = r0.mDirtyHierarchy
            if (r6 == 0) goto L_0x006e
            r3 = r5
        L_0x006e:
            r0.mLastWidthMeasureSpec = r1
            r0.mLastHeightMeasureSpec = r2
            androidx.constraintlayout.motion.widget.MotionScene r6 = r0.mScene
            int r6 = r6.getStartId()
            androidx.constraintlayout.motion.widget.MotionScene r7 = r0.mScene
            java.util.Objects.requireNonNull(r7)
            androidx.constraintlayout.motion.widget.MotionScene$Transition r7 = r7.mCurrentTransition
            r8 = -1
            if (r7 != 0) goto L_0x0084
            r7 = r8
            goto L_0x0086
        L_0x0084:
            int r7 = r7.mConstraintSetEnd
        L_0x0086:
            if (r3 != 0) goto L_0x009b
            androidx.constraintlayout.motion.widget.MotionLayout$Model r3 = r0.mModel
            java.util.Objects.requireNonNull(r3)
            int r9 = r3.mStartId
            if (r6 != r9) goto L_0x0098
            int r3 = r3.mEndId
            if (r7 == r3) goto L_0x0096
            goto L_0x0098
        L_0x0096:
            r3 = r4
            goto L_0x0099
        L_0x0098:
            r3 = r5
        L_0x0099:
            if (r3 == 0) goto L_0x00c2
        L_0x009b:
            int r3 = r0.mBeginState
            if (r3 == r8) goto L_0x00c2
            super.onMeasure(r18, r19)
            androidx.constraintlayout.motion.widget.MotionLayout$Model r1 = r0.mModel
            androidx.constraintlayout.motion.widget.MotionScene r2 = r0.mScene
            androidx.constraintlayout.widget.ConstraintSet r2 = r2.getConstraintSet(r6)
            androidx.constraintlayout.motion.widget.MotionScene r3 = r0.mScene
            androidx.constraintlayout.widget.ConstraintSet r3 = r3.getConstraintSet(r7)
            r1.initFrom(r2, r3)
            androidx.constraintlayout.motion.widget.MotionLayout$Model r1 = r0.mModel
            r1.reEvaluateState()
            androidx.constraintlayout.motion.widget.MotionLayout$Model r1 = r0.mModel
            java.util.Objects.requireNonNull(r1)
            r1.mStartId = r6
            r1.mEndId = r7
            goto L_0x010d
        L_0x00c2:
            int r1 = r17.getPaddingTop()
            int r2 = r17.getPaddingBottom()
            int r2 = r2 + r1
            int r1 = r17.getPaddingLeft()
            int r3 = r17.getPaddingRight()
            int r3 = r3 + r1
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r1 = r0.mLayoutWidget
            int r1 = r1.getWidth()
            int r1 = r1 + r3
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            int r3 = r3.getHeight()
            int r3 = r3 + r2
            int r2 = r0.mWidthMeasureMode
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r2 != r6) goto L_0x00f7
            int r1 = r0.mStartWrapWidth
            float r2 = (float) r1
            float r7 = r0.mPostInterpolationPosition
            int r8 = r0.mEndWrapWidth
            int r8 = r8 - r1
            float r1 = (float) r8
            float r7 = r7 * r1
            float r7 = r7 + r2
            int r1 = (int) r7
            r17.requestLayout()
        L_0x00f7:
            int r2 = r0.mHeightMeasureMode
            if (r2 != r6) goto L_0x010a
            int r2 = r0.mStartWrapHeight
            float r3 = (float) r2
            float r6 = r0.mPostInterpolationPosition
            int r7 = r0.mEndWrapHeight
            int r7 = r7 - r2
            float r2 = (float) r7
            float r6 = r6 * r2
            float r6 = r6 + r3
            int r3 = (int) r6
            r17.requestLayout()
        L_0x010a:
            r0.setMeasuredDimension(r1, r3)
        L_0x010d:
            float r1 = r0.mTransitionGoalPosition
            float r2 = r0.mTransitionLastPosition
            float r1 = r1 - r2
            float r1 = java.lang.Math.signum(r1)
            long r2 = java.lang.System.nanoTime()
            android.view.animation.Interpolator r6 = r0.mInterpolator
            boolean r7 = r6 instanceof androidx.constraintlayout.motion.utils.StopLogic
            r8 = 814313567(0x3089705f, float:1.0E-9)
            r9 = 0
            if (r7 != 0) goto L_0x012f
            long r10 = r0.mTransitionLastTime
            long r10 = r2 - r10
            float r7 = (float) r10
            float r7 = r7 * r1
            float r7 = r7 * r8
            float r10 = r0.mTransitionDuration
            float r7 = r7 / r10
            goto L_0x0130
        L_0x012f:
            r7 = r9
        L_0x0130:
            float r10 = r0.mTransitionLastPosition
            float r10 = r10 + r7
            boolean r7 = r0.mTransitionInstantly
            if (r7 == 0) goto L_0x0139
            float r10 = r0.mTransitionGoalPosition
        L_0x0139:
            int r7 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r7 <= 0) goto L_0x0143
            float r11 = r0.mTransitionGoalPosition
            int r11 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r11 >= 0) goto L_0x014d
        L_0x0143:
            int r11 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r11 > 0) goto L_0x0150
            float r11 = r0.mTransitionGoalPosition
            int r11 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r11 > 0) goto L_0x0150
        L_0x014d:
            float r10 = r0.mTransitionGoalPosition
            goto L_0x0151
        L_0x0150:
            r5 = r4
        L_0x0151:
            if (r6 == 0) goto L_0x0167
            if (r5 != 0) goto L_0x0167
            boolean r5 = r0.mTemporalInterpolator
            if (r5 == 0) goto L_0x0163
            long r10 = r0.mAnimationStartTime
            long r2 = r2 - r10
            float r2 = (float) r2
            float r2 = r2 * r8
            float r10 = r6.getInterpolation(r2)
            goto L_0x0167
        L_0x0163:
            float r10 = r6.getInterpolation(r10)
        L_0x0167:
            if (r7 <= 0) goto L_0x016f
            float r2 = r0.mTransitionGoalPosition
            int r2 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0179
        L_0x016f:
            int r1 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r1 > 0) goto L_0x017b
            float r1 = r0.mTransitionGoalPosition
            int r1 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r1 > 0) goto L_0x017b
        L_0x0179:
            float r10 = r0.mTransitionGoalPosition
        L_0x017b:
            r0.mPostInterpolationPosition = r10
            int r1 = r17.getChildCount()
            long r2 = java.lang.System.nanoTime()
        L_0x0185:
            if (r4 >= r1) goto L_0x01a2
            android.view.View r12 = r0.getChildAt(r4)
            java.util.HashMap<android.view.View, androidx.constraintlayout.motion.widget.MotionController> r5 = r0.mFrameArrayList
            java.lang.Object r5 = r5.get(r12)
            r11 = r5
            androidx.constraintlayout.motion.widget.MotionController r11 = (androidx.constraintlayout.motion.widget.MotionController) r11
            if (r11 == 0) goto L_0x019f
            androidx.constraintlayout.motion.widget.KeyCache r5 = r0.mKeyCache
            r13 = r10
            r14 = r2
            r16 = r5
            r11.interpolate(r12, r13, r14, r16)
        L_0x019f:
            int r4 = r4 + 1
            goto L_0x0185
        L_0x01a2:
            boolean r1 = r0.mMeasureDuringTransition
            if (r1 == 0) goto L_0x01a9
            r17.requestLayout()
        L_0x01a9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.onMeasure(int, int):void");
    }

    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        MotionScene.Transition transition;
        TouchResponse touchResponse;
        float f;
        float f2;
        TouchResponse touchResponse2;
        boolean z;
        TouchResponse touchResponse3;
        TouchResponse touchResponse4;
        int i4;
        int i5 = i;
        int i6 = i2;
        MotionScene motionScene = this.mScene;
        if (motionScene != null && (transition = motionScene.mCurrentTransition) != null) {
            boolean z2 = transition.mDisable;
            if (!z2) {
                if (transition == null || !(!z2) || (touchResponse4 = transition.mTouchResponse) == null || (i4 = touchResponse4.mTouchRegionId) == -1 || this.mScrollTarget.getId() == i4) {
                    MotionScene motionScene2 = this.mScene;
                    if (motionScene2 != null) {
                        MotionScene.Transition transition2 = motionScene2.mCurrentTransition;
                        if (transition2 == null || (touchResponse3 = transition2.mTouchResponse) == null) {
                            z = false;
                        } else {
                            z = touchResponse3.mMoveWhenScrollAtTop;
                        }
                        if (z) {
                            float f3 = this.mTransitionPosition;
                            if ((f3 == 1.0f || f3 == 0.0f) && view.canScrollVertically(-1)) {
                                return;
                            }
                        }
                    }
                    Objects.requireNonNull(transition);
                    if (transition.mTouchResponse != null) {
                        MotionScene.Transition transition3 = this.mScene.mCurrentTransition;
                        Objects.requireNonNull(transition3);
                        TouchResponse touchResponse5 = transition3.mTouchResponse;
                        Objects.requireNonNull(touchResponse5);
                        if ((touchResponse5.mFlags & 1) != 0) {
                            MotionScene motionScene3 = this.mScene;
                            float f4 = (float) i5;
                            float f5 = (float) i6;
                            Objects.requireNonNull(motionScene3);
                            MotionScene.Transition transition4 = motionScene3.mCurrentTransition;
                            if (transition4 == null || (touchResponse2 = transition4.mTouchResponse) == null) {
                                f2 = 0.0f;
                            } else {
                                MotionLayout motionLayout = touchResponse2.mMotionLayout;
                                Objects.requireNonNull(motionLayout);
                                touchResponse2.mMotionLayout.getAnchorDpDt(touchResponse2.mTouchAnchorId, motionLayout.mTransitionLastPosition, touchResponse2.mTouchAnchorX, touchResponse2.mTouchAnchorY, touchResponse2.mAnchorDpDt);
                                float f6 = touchResponse2.mTouchDirectionX;
                                if (f6 != 0.0f) {
                                    float[] fArr = touchResponse2.mAnchorDpDt;
                                    if (fArr[0] == 0.0f) {
                                        fArr[0] = 1.0E-7f;
                                    }
                                    f2 = (f4 * f6) / fArr[0];
                                } else {
                                    float[] fArr2 = touchResponse2.mAnchorDpDt;
                                    if (fArr2[1] == 0.0f) {
                                        fArr2[1] = 1.0E-7f;
                                    }
                                    f2 = (f5 * touchResponse2.mTouchDirectionY) / fArr2[1];
                                }
                            }
                            float f7 = this.mTransitionLastPosition;
                            if ((f7 <= 0.0f && f2 < 0.0f) || (f7 >= 1.0f && f2 > 0.0f)) {
                                this.mScrollTarget.setNestedScrollingEnabled(false);
                                this.mScrollTarget.post(new Runnable() {
                                    public final void run() {
                                        MotionLayout.this.mScrollTarget.setNestedScrollingEnabled(true);
                                    }
                                });
                                return;
                            }
                        }
                    }
                    float f8 = this.mTransitionPosition;
                    long nanoTime = System.nanoTime();
                    float f9 = (float) i5;
                    this.mScrollTargetDX = f9;
                    float f10 = (float) i6;
                    this.mScrollTargetDY = f10;
                    this.mScrollTargetDT = (float) (((double) (nanoTime - this.mScrollTargetTime)) * 1.0E-9d);
                    this.mScrollTargetTime = nanoTime;
                    MotionScene motionScene4 = this.mScene;
                    Objects.requireNonNull(motionScene4);
                    MotionScene.Transition transition5 = motionScene4.mCurrentTransition;
                    if (!(transition5 == null || (touchResponse = transition5.mTouchResponse) == null)) {
                        MotionLayout motionLayout2 = touchResponse.mMotionLayout;
                        Objects.requireNonNull(motionLayout2);
                        float f11 = motionLayout2.mTransitionLastPosition;
                        if (!touchResponse.mDragStarted) {
                            touchResponse.mDragStarted = true;
                            touchResponse.mMotionLayout.setProgress(f11);
                        }
                        touchResponse.mMotionLayout.getAnchorDpDt(touchResponse.mTouchAnchorId, f11, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                        float f12 = touchResponse.mTouchDirectionX;
                        float[] fArr3 = touchResponse.mAnchorDpDt;
                        if (((double) Math.abs((touchResponse.mTouchDirectionY * fArr3[1]) + (f12 * fArr3[0]))) < 0.01d) {
                            float[] fArr4 = touchResponse.mAnchorDpDt;
                            fArr4[0] = 0.01f;
                            fArr4[1] = 0.01f;
                        }
                        float f13 = touchResponse.mTouchDirectionX;
                        if (f13 != 0.0f) {
                            f = (f9 * f13) / touchResponse.mAnchorDpDt[0];
                        } else {
                            f = (f10 * touchResponse.mTouchDirectionY) / touchResponse.mAnchorDpDt[1];
                        }
                        float max = Math.max(Math.min(f11 + f, 1.0f), 0.0f);
                        MotionLayout motionLayout3 = touchResponse.mMotionLayout;
                        Objects.requireNonNull(motionLayout3);
                        if (max != motionLayout3.mTransitionLastPosition) {
                            touchResponse.mMotionLayout.setProgress(max);
                        }
                    }
                    if (f8 != this.mTransitionPosition) {
                        iArr[0] = i5;
                        iArr[1] = i6;
                    }
                    evaluate(false);
                    if (iArr[0] != 0 || iArr[1] != 0) {
                        this.mUndergoingMotion = true;
                    }
                }
            }
        }
    }

    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (!(!this.mUndergoingMotion && i == 0 && i2 == 0)) {
            iArr[0] = iArr[0] + i3;
            iArr[1] = iArr[1] + i4;
        }
        this.mUndergoingMotion = false;
    }

    public final void onNewStateAttachHandlers() {
        TouchResponse touchResponse;
        MotionScene motionScene = this.mScene;
        if (motionScene != null && !motionScene.autoTransition(this, this.mCurrentState)) {
            int i = this.mCurrentState;
            if (i != -1) {
                MotionScene motionScene2 = this.mScene;
                Objects.requireNonNull(motionScene2);
                Iterator<MotionScene.Transition> it = motionScene2.mTransitionList.iterator();
                while (it.hasNext()) {
                    MotionScene.Transition next = it.next();
                    if (next.mOnClicks.size() > 0) {
                        Iterator<MotionScene.Transition.TransitionOnClick> it2 = next.mOnClicks.iterator();
                        while (it2.hasNext()) {
                            it2.next().removeOnClickListeners(this);
                        }
                    }
                }
                Iterator<MotionScene.Transition> it3 = motionScene2.mAbstractTransitionList.iterator();
                while (it3.hasNext()) {
                    MotionScene.Transition next2 = it3.next();
                    if (next2.mOnClicks.size() > 0) {
                        Iterator<MotionScene.Transition.TransitionOnClick> it4 = next2.mOnClicks.iterator();
                        while (it4.hasNext()) {
                            it4.next().removeOnClickListeners(this);
                        }
                    }
                }
                Iterator<MotionScene.Transition> it5 = motionScene2.mTransitionList.iterator();
                while (it5.hasNext()) {
                    MotionScene.Transition next3 = it5.next();
                    if (next3.mOnClicks.size() > 0) {
                        Iterator<MotionScene.Transition.TransitionOnClick> it6 = next3.mOnClicks.iterator();
                        while (it6.hasNext()) {
                            it6.next().addOnClickListeners(this, i, next3);
                        }
                    }
                }
                Iterator<MotionScene.Transition> it7 = motionScene2.mAbstractTransitionList.iterator();
                while (it7.hasNext()) {
                    MotionScene.Transition next4 = it7.next();
                    if (next4.mOnClicks.size() > 0) {
                        Iterator<MotionScene.Transition.TransitionOnClick> it8 = next4.mOnClicks.iterator();
                        while (it8.hasNext()) {
                            it8.next().addOnClickListeners(this, i, next4);
                        }
                    }
                }
            }
            if (this.mScene.supportTouch()) {
                MotionScene motionScene3 = this.mScene;
                Objects.requireNonNull(motionScene3);
                MotionScene.Transition transition = motionScene3.mCurrentTransition;
                if (transition != null && (touchResponse = transition.mTouchResponse) != null) {
                    View findViewById = touchResponse.mMotionLayout.findViewById(touchResponse.mTouchAnchorId);
                    if (findViewById == null) {
                        Log.w("TouchResponse", " cannot find view to handle touch");
                    }
                    if (findViewById instanceof NestedScrollView) {
                        NestedScrollView nestedScrollView = (NestedScrollView) findViewById;
                        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
                            public final boolean onTouch(
/*
Method generation error in method: androidx.constraintlayout.motion.widget.TouchResponse.1.onTouch(android.view.View, android.view.MotionEvent):boolean, dex: classes.dex
                            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: androidx.constraintlayout.motion.widget.TouchResponse.1.onTouch(android.view.View, android.view.MotionEvent):boolean, class status: UNLOADED
                            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                            
*/
                        });
                        nestedScrollView.mOnScrollChangeListener = new NestedScrollView.OnScrollChangeListener() {
                        };
                    }
                }
            }
        }
    }

    public final void onRtlPropertiesChanged(int i) {
        TouchResponse touchResponse;
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            boolean isRtl = isRtl();
            Objects.requireNonNull(motionScene);
            motionScene.mRtl = isRtl;
            MotionScene.Transition transition = motionScene.mCurrentTransition;
            if (transition != null && (touchResponse = transition.mTouchResponse) != null) {
                touchResponse.setRTL(isRtl);
            }
        }
    }

    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        MotionScene.Transition transition;
        this.mScrollTarget = view2;
        MotionScene motionScene = this.mScene;
        if (motionScene == null || (transition = motionScene.mCurrentTransition) == null) {
            return false;
        }
        Objects.requireNonNull(transition);
        if (transition.mTouchResponse == null) {
            return false;
        }
        MotionScene.Transition transition2 = this.mScene.mCurrentTransition;
        Objects.requireNonNull(transition2);
        TouchResponse touchResponse = transition2.mTouchResponse;
        Objects.requireNonNull(touchResponse);
        if ((touchResponse.mFlags & 2) != 0) {
            return false;
        }
        return true;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        TouchResponse touchResponse;
        char c;
        char c2;
        float f;
        int i;
        float f2;
        char c3;
        char c4;
        char c5;
        char c6;
        float f3;
        float f4;
        RectF rectF;
        View findViewById;
        MotionEvent motionEvent2;
        MotionScene.Transition transition;
        boolean z;
        int i2;
        TouchResponse touchResponse2;
        RectF touchRegion;
        float f5;
        MotionEvent motionEvent3 = motionEvent;
        MotionScene motionScene = this.mScene;
        if (motionScene == null || !this.mInteractionEnabled || !motionScene.supportTouch()) {
            return super.onTouchEvent(motionEvent);
        }
        MotionScene motionScene2 = this.mScene;
        MotionScene.Transition transition2 = motionScene2.mCurrentTransition;
        if (transition2 != null && !(!transition2.mDisable)) {
            return super.onTouchEvent(motionEvent);
        }
        int i3 = this.mCurrentState;
        RectF rectF2 = new RectF();
        if (motionScene2.mVelocityTracker == null) {
            Objects.requireNonNull(motionScene2.mMotionLayout);
            MyTracker myTracker = MyTracker.f11me;
            myTracker.tracker = VelocityTracker.obtain();
            motionScene2.mVelocityTracker = myTracker;
        }
        MyTracker myTracker2 = motionScene2.mVelocityTracker;
        Objects.requireNonNull(myTracker2);
        VelocityTracker velocityTracker = myTracker2.tracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent3);
        }
        if (i3 != -1) {
            int action = motionEvent.getAction();
            if (action == 0) {
                motionScene2.mLastTouchX = motionEvent.getRawX();
                motionScene2.mLastTouchY = motionEvent.getRawY();
                motionScene2.mLastTouchDown = motionEvent3;
                TouchResponse touchResponse3 = motionScene2.mCurrentTransition.mTouchResponse;
                if (touchResponse3 != null) {
                    MotionLayout motionLayout = motionScene2.mMotionLayout;
                    int i4 = touchResponse3.mLimitBoundsTo;
                    if (i4 == -1 || (findViewById = motionLayout.findViewById(i4)) == null) {
                        rectF = null;
                    } else {
                        rectF2.set((float) findViewById.getLeft(), (float) findViewById.getTop(), (float) findViewById.getRight(), (float) findViewById.getBottom());
                        rectF = rectF2;
                    }
                    if (rectF == null || rectF.contains(motionScene2.mLastTouchDown.getX(), motionScene2.mLastTouchDown.getY())) {
                        RectF touchRegion2 = motionScene2.mCurrentTransition.mTouchResponse.getTouchRegion(motionScene2.mMotionLayout, rectF2);
                        if (touchRegion2 == null || touchRegion2.contains(motionScene2.mLastTouchDown.getX(), motionScene2.mLastTouchDown.getY())) {
                            motionScene2.mMotionOutsideRegion = false;
                        } else {
                            motionScene2.mMotionOutsideRegion = true;
                        }
                        TouchResponse touchResponse4 = motionScene2.mCurrentTransition.mTouchResponse;
                        float f6 = motionScene2.mLastTouchX;
                        float f7 = motionScene2.mLastTouchY;
                        Objects.requireNonNull(touchResponse4);
                        touchResponse4.mLastTouchX = f6;
                        touchResponse4.mLastTouchY = f7;
                    } else {
                        motionScene2.mLastTouchDown = null;
                    }
                }
                return true;
            } else if (action == 2) {
                float rawY = motionEvent.getRawY() - motionScene2.mLastTouchY;
                float rawX = motionEvent.getRawX() - motionScene2.mLastTouchX;
                if ((((double) rawX) == 0.0d && ((double) rawY) == 0.0d) || (motionEvent2 = motionScene2.mLastTouchDown) == null) {
                    return true;
                }
                if (i3 != -1) {
                    StateSet stateSet = motionScene2.mStateSet;
                    if (stateSet == null || (i2 = stateSet.stateGetConstraintID(i3)) == -1) {
                        i2 = i3;
                    }
                    ArrayList arrayList = new ArrayList();
                    Iterator<MotionScene.Transition> it = motionScene2.mTransitionList.iterator();
                    while (it.hasNext()) {
                        MotionScene.Transition next = it.next();
                        if (next.mConstraintSetStart == i2 || next.mConstraintSetEnd == i2) {
                            arrayList.add(next);
                        }
                    }
                    RectF rectF3 = new RectF();
                    Iterator it2 = arrayList.iterator();
                    float f8 = 0.0f;
                    transition = null;
                    while (it2.hasNext()) {
                        MotionScene.Transition transition3 = (MotionScene.Transition) it2.next();
                        if (!transition3.mDisable && (touchResponse2 = transition3.mTouchResponse) != null) {
                            touchResponse2.setRTL(motionScene2.mRtl);
                            RectF touchRegion3 = transition3.mTouchResponse.getTouchRegion(motionScene2.mMotionLayout, rectF3);
                            if ((touchRegion3 == null || touchRegion3.contains(motionEvent2.getX(), motionEvent2.getY())) && ((touchRegion = transition3.mTouchResponse.getTouchRegion(motionScene2.mMotionLayout, rectF3)) == null || touchRegion.contains(motionEvent2.getX(), motionEvent2.getY()))) {
                                TouchResponse touchResponse5 = transition3.mTouchResponse;
                                Objects.requireNonNull(touchResponse5);
                                float f9 = (touchResponse5.mTouchDirectionY * rawY) + (touchResponse5.mTouchDirectionX * rawX);
                                if (transition3.mConstraintSetEnd == i3) {
                                    f5 = -1.0f;
                                } else {
                                    f5 = 1.1f;
                                }
                                float f10 = f9 * f5;
                                if (f10 > f8) {
                                    f8 = f10;
                                    transition = transition3;
                                }
                            }
                        }
                    }
                } else {
                    transition = motionScene2.mCurrentTransition;
                }
                if (transition != null) {
                    setTransition(transition);
                    RectF touchRegion4 = motionScene2.mCurrentTransition.mTouchResponse.getTouchRegion(motionScene2.mMotionLayout, rectF2);
                    if (touchRegion4 == null || touchRegion4.contains(motionScene2.mLastTouchDown.getX(), motionScene2.mLastTouchDown.getY())) {
                        z = false;
                    } else {
                        z = true;
                    }
                    motionScene2.mMotionOutsideRegion = z;
                    TouchResponse touchResponse6 = motionScene2.mCurrentTransition.mTouchResponse;
                    float f11 = motionScene2.mLastTouchX;
                    float f12 = motionScene2.mLastTouchY;
                    Objects.requireNonNull(touchResponse6);
                    touchResponse6.mLastTouchX = f11;
                    touchResponse6.mLastTouchY = f12;
                    touchResponse6.mDragStarted = false;
                }
            }
        }
        MotionScene.Transition transition4 = motionScene2.mCurrentTransition;
        if (!(transition4 == null || (touchResponse = transition4.mTouchResponse) == null || motionScene2.mMotionOutsideRegion)) {
            MyTracker myTracker3 = motionScene2.mVelocityTracker;
            Objects.requireNonNull(myTracker3);
            VelocityTracker velocityTracker2 = myTracker3.tracker;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent3);
            }
            int action2 = motionEvent.getAction();
            if (action2 == 0) {
                touchResponse.mLastTouchX = motionEvent.getRawX();
                touchResponse.mLastTouchY = motionEvent.getRawY();
                touchResponse.mDragStarted = false;
            } else if (action2 == 1) {
                touchResponse.mDragStarted = false;
                myTracker3.tracker.computeCurrentVelocity(1000);
                float xVelocity = myTracker3.tracker.getXVelocity();
                float yVelocity = myTracker3.tracker.getYVelocity();
                MotionLayout motionLayout2 = touchResponse.mMotionLayout;
                Objects.requireNonNull(motionLayout2);
                float f13 = motionLayout2.mTransitionLastPosition;
                int i5 = touchResponse.mTouchAnchorId;
                if (i5 != -1) {
                    touchResponse.mMotionLayout.getAnchorDpDt(i5, f13, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                    c2 = 0;
                    c = 1;
                } else {
                    float min = (float) Math.min(touchResponse.mMotionLayout.getWidth(), touchResponse.mMotionLayout.getHeight());
                    float[] fArr = touchResponse.mAnchorDpDt;
                    c = 1;
                    fArr[1] = touchResponse.mTouchDirectionY * min;
                    c2 = 0;
                    fArr[0] = min * touchResponse.mTouchDirectionX;
                }
                float f14 = touchResponse.mTouchDirectionX;
                float[] fArr2 = touchResponse.mAnchorDpDt;
                float f15 = fArr2[c2];
                float f16 = fArr2[c];
                if (f14 != 0.0f) {
                    f = xVelocity / fArr2[c2];
                } else {
                    f = yVelocity / fArr2[c];
                }
                if (!Float.isNaN(f)) {
                    f13 += f / 3.0f;
                }
                if (!(f13 == 0.0f || f13 == 1.0f || (i = touchResponse.mOnTouchUp) == 3)) {
                    MotionLayout motionLayout3 = touchResponse.mMotionLayout;
                    if (((double) f13) < 0.5d) {
                        f2 = 0.0f;
                    } else {
                        f2 = 1.0f;
                    }
                    motionLayout3.touchAnimateTo(i, f2, f);
                }
            } else if (action2 == 2) {
                float rawY2 = motionEvent.getRawY() - touchResponse.mLastTouchY;
                float rawX2 = motionEvent.getRawX() - touchResponse.mLastTouchX;
                if (Math.abs((touchResponse.mTouchDirectionY * rawY2) + (touchResponse.mTouchDirectionX * rawX2)) > 10.0f || touchResponse.mDragStarted) {
                    MotionLayout motionLayout4 = touchResponse.mMotionLayout;
                    Objects.requireNonNull(motionLayout4);
                    float f17 = motionLayout4.mTransitionLastPosition;
                    if (!touchResponse.mDragStarted) {
                        touchResponse.mDragStarted = true;
                        touchResponse.mMotionLayout.setProgress(f17);
                    }
                    int i6 = touchResponse.mTouchAnchorId;
                    if (i6 != -1) {
                        touchResponse.mMotionLayout.getAnchorDpDt(i6, f17, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                        c4 = 0;
                        c3 = 1;
                    } else {
                        float min2 = (float) Math.min(touchResponse.mMotionLayout.getWidth(), touchResponse.mMotionLayout.getHeight());
                        float[] fArr3 = touchResponse.mAnchorDpDt;
                        c3 = 1;
                        fArr3[1] = touchResponse.mTouchDirectionY * min2;
                        c4 = 0;
                        fArr3[0] = min2 * touchResponse.mTouchDirectionX;
                    }
                    float f18 = touchResponse.mTouchDirectionX;
                    float[] fArr4 = touchResponse.mAnchorDpDt;
                    if (((double) Math.abs(((touchResponse.mTouchDirectionY * fArr4[c3]) + (f18 * fArr4[c4])) * touchResponse.mDragScale)) < 0.01d) {
                        float[] fArr5 = touchResponse.mAnchorDpDt;
                        c6 = 0;
                        fArr5[0] = 0.01f;
                        c5 = 1;
                        fArr5[1] = 0.01f;
                    } else {
                        c6 = 0;
                        c5 = 1;
                    }
                    if (touchResponse.mTouchDirectionX != 0.0f) {
                        f3 = rawX2 / touchResponse.mAnchorDpDt[c6];
                    } else {
                        f3 = rawY2 / touchResponse.mAnchorDpDt[c5];
                    }
                    float max = Math.max(Math.min(f17 + f3, 1.0f), 0.0f);
                    MotionLayout motionLayout5 = touchResponse.mMotionLayout;
                    Objects.requireNonNull(motionLayout5);
                    if (max != motionLayout5.mTransitionLastPosition) {
                        touchResponse.mMotionLayout.setProgress(max);
                        myTracker3.tracker.computeCurrentVelocity(1000);
                        float xVelocity2 = myTracker3.tracker.getXVelocity();
                        float yVelocity2 = myTracker3.tracker.getYVelocity();
                        if (touchResponse.mTouchDirectionX != 0.0f) {
                            f4 = xVelocity2 / touchResponse.mAnchorDpDt[0];
                        } else {
                            f4 = yVelocity2 / touchResponse.mAnchorDpDt[1];
                        }
                        touchResponse.mMotionLayout.mLastVelocity = f4;
                    } else {
                        touchResponse.mMotionLayout.mLastVelocity = 0.0f;
                    }
                    touchResponse.mLastTouchX = motionEvent.getRawX();
                    touchResponse.mLastTouchY = motionEvent.getRawY();
                }
            }
        }
        motionScene2.mLastTouchX = motionEvent.getRawX();
        motionScene2.mLastTouchY = motionEvent.getRawY();
        if (motionEvent.getAction() != 1) {
            return true;
        }
        MyTracker myTracker4 = motionScene2.mVelocityTracker;
        if (myTracker4 != null) {
            myTracker4.tracker.recycle();
            myTracker4.tracker = null;
            motionScene2.mVelocityTracker = null;
            int i7 = this.mCurrentState;
            if (i7 != -1) {
                motionScene2.autoTransition(this, i7);
            }
        }
        return true;
    }

    public final void rebuildScene() {
        this.mModel.reEvaluateState();
        invalidate();
    }

    public final void requestLayout() {
        MotionScene motionScene;
        MotionScene.Transition transition;
        if (!this.mMeasureDuringTransition && this.mCurrentState == -1 && (motionScene = this.mScene) != null && (transition = motionScene.mCurrentTransition) != null) {
            Objects.requireNonNull(transition);
            if (transition.mLayoutDuringTransition == 0) {
                return;
            }
        }
        super.requestLayout();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005a, code lost:
        if ((((r15 * r3) - (((r2 * r3) * r3) / 2.0f)) + r13) > 1.0f) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0068, code lost:
        if ((((((r2 * r3) * r3) / 2.0f) + (r15 * r3)) + r13) < 0.0f) goto L_0x006c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void touchAnimateTo(int r13, float r14, float r15) {
        /*
            r12 = this;
            androidx.constraintlayout.motion.widget.MotionScene r0 = r12.mScene
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            float r0 = r12.mTransitionLastPosition
            int r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1))
            if (r0 != 0) goto L_0x000c
            return
        L_0x000c:
            r0 = 1
            r12.mTemporalInterpolator = r0
            long r1 = java.lang.System.nanoTime()
            r12.mAnimationStartTime = r1
            androidx.constraintlayout.motion.widget.MotionScene r1 = r12.mScene
            java.util.Objects.requireNonNull(r1)
            androidx.constraintlayout.motion.widget.MotionScene$Transition r2 = r1.mCurrentTransition
            if (r2 == 0) goto L_0x0021
            int r1 = r2.mDuration
            goto L_0x0023
        L_0x0021:
            int r1 = r1.mDefaultDuration
        L_0x0023:
            float r1 = (float) r1
            r2 = 1148846080(0x447a0000, float:1000.0)
            float r7 = r1 / r2
            r12.mTransitionDuration = r7
            r12.mTransitionGoalPosition = r14
            r12.mInTransition = r0
            r1 = 0
            r2 = 2
            r10 = 1065353216(0x3f800000, float:1.0)
            r11 = 0
            if (r13 == 0) goto L_0x00d6
            if (r13 == r0) goto L_0x00d6
            if (r13 == r2) goto L_0x00d6
            r2 = 4
            if (r13 == r2) goto L_0x00be
            r2 = 5
            if (r13 == r2) goto L_0x0041
            goto L_0x010f
        L_0x0041:
            float r13 = r12.mTransitionLastPosition
            androidx.constraintlayout.motion.widget.MotionScene r2 = r12.mScene
            float r2 = r2.getMaxAcceleration()
            int r3 = (r15 > r11 ? 1 : (r15 == r11 ? 0 : -1))
            r4 = 1073741824(0x40000000, float:2.0)
            if (r3 <= 0) goto L_0x005d
            float r3 = r15 / r2
            float r5 = r15 * r3
            float r2 = r2 * r3
            float r2 = r2 * r3
            float r2 = r2 / r4
            float r5 = r5 - r2
            float r5 = r5 + r13
            int r13 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r13 <= 0) goto L_0x006b
            goto L_0x006c
        L_0x005d:
            float r3 = -r15
            float r3 = r3 / r2
            float r5 = r15 * r3
            float r2 = r2 * r3
            float r2 = r2 * r3
            float r2 = r2 / r4
            float r2 = r2 + r5
            float r2 = r2 + r13
            int r13 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r13 >= 0) goto L_0x006b
            goto L_0x006c
        L_0x006b:
            r0 = r1
        L_0x006c:
            if (r0 == 0) goto L_0x0087
            androidx.constraintlayout.motion.widget.MotionLayout$DecelerateInterpolator r13 = r12.mDecelerateLogic
            float r14 = r12.mTransitionLastPosition
            androidx.constraintlayout.motion.widget.MotionScene r0 = r12.mScene
            float r0 = r0.getMaxAcceleration()
            java.util.Objects.requireNonNull(r13)
            r13.initalV = r15
            r13.currentP = r14
            r13.maxA = r0
            androidx.constraintlayout.motion.widget.MotionLayout$DecelerateInterpolator r13 = r12.mDecelerateLogic
            r12.mInterpolator = r13
            goto L_0x010f
        L_0x0087:
            androidx.constraintlayout.motion.utils.StopLogic r2 = r12.mStopLogic
            float r3 = r12.mTransitionLastPosition
            float r6 = r12.mTransitionDuration
            androidx.constraintlayout.motion.widget.MotionScene r13 = r12.mScene
            float r7 = r13.getMaxAcceleration()
            androidx.constraintlayout.motion.widget.MotionScene r13 = r12.mScene
            java.util.Objects.requireNonNull(r13)
            androidx.constraintlayout.motion.widget.MotionScene$Transition r13 = r13.mCurrentTransition
            if (r13 == 0) goto L_0x00a4
            androidx.constraintlayout.motion.widget.TouchResponse r13 = r13.mTouchResponse
            if (r13 == 0) goto L_0x00a4
            float r13 = r13.mMaxVelocity
            r8 = r13
            goto L_0x00a5
        L_0x00a4:
            r8 = r11
        L_0x00a5:
            r4 = r14
            r5 = r15
            r2.config(r3, r4, r5, r6, r7, r8)
            r12.mLastVelocity = r11
            int r13 = r12.mCurrentState
            int r14 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r14 != 0) goto L_0x00b3
            goto L_0x00b4
        L_0x00b3:
            r10 = r11
        L_0x00b4:
            r12.setProgress(r10)
            r12.mCurrentState = r13
            androidx.constraintlayout.motion.utils.StopLogic r13 = r12.mStopLogic
            r12.mInterpolator = r13
            goto L_0x010f
        L_0x00be:
            androidx.constraintlayout.motion.widget.MotionLayout$DecelerateInterpolator r13 = r12.mDecelerateLogic
            float r14 = r12.mTransitionLastPosition
            androidx.constraintlayout.motion.widget.MotionScene r0 = r12.mScene
            float r0 = r0.getMaxAcceleration()
            java.util.Objects.requireNonNull(r13)
            r13.initalV = r15
            r13.currentP = r14
            r13.maxA = r0
            androidx.constraintlayout.motion.widget.MotionLayout$DecelerateInterpolator r13 = r12.mDecelerateLogic
            r12.mInterpolator = r13
            goto L_0x010f
        L_0x00d6:
            if (r13 != r0) goto L_0x00da
            r14 = r11
            goto L_0x00dd
        L_0x00da:
            if (r13 != r2) goto L_0x00dd
            r14 = r10
        L_0x00dd:
            androidx.constraintlayout.motion.utils.StopLogic r3 = r12.mStopLogic
            float r4 = r12.mTransitionLastPosition
            androidx.constraintlayout.motion.widget.MotionScene r13 = r12.mScene
            float r8 = r13.getMaxAcceleration()
            androidx.constraintlayout.motion.widget.MotionScene r13 = r12.mScene
            java.util.Objects.requireNonNull(r13)
            androidx.constraintlayout.motion.widget.MotionScene$Transition r13 = r13.mCurrentTransition
            if (r13 == 0) goto L_0x00f8
            androidx.constraintlayout.motion.widget.TouchResponse r13 = r13.mTouchResponse
            if (r13 == 0) goto L_0x00f8
            float r13 = r13.mMaxVelocity
            r9 = r13
            goto L_0x00f9
        L_0x00f8:
            r9 = r11
        L_0x00f9:
            r5 = r14
            r6 = r15
            r3.config(r4, r5, r6, r7, r8, r9)
            int r13 = r12.mCurrentState
            int r14 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r14 != 0) goto L_0x0105
            goto L_0x0106
        L_0x0105:
            r10 = r11
        L_0x0106:
            r12.setProgress(r10)
            r12.mCurrentState = r13
            androidx.constraintlayout.motion.utils.StopLogic r13 = r12.mStopLogic
            r12.mInterpolator = r13
        L_0x010f:
            r12.mTransitionInstantly = r1
            long r13 = java.lang.System.nanoTime()
            r12.mAnimationStartTime = r13
            r12.invalidate()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.touchAnimateTo(int, float, float):void");
    }

    public final void init(AttributeSet attributeSet) {
        MotionScene motionScene;
        String str;
        String str2;
        IS_IN_EDIT_MODE = isInEditMode();
        int i = -1;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.MotionLayout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            boolean z = true;
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = obtainStyledAttributes.getIndex(i2);
                int i3 = 2;
                if (index == 2) {
                    this.mScene = new MotionScene(getContext(), this, obtainStyledAttributes.getResourceId(index, -1));
                } else if (index == 1) {
                    this.mCurrentState = obtainStyledAttributes.getResourceId(index, -1);
                } else if (index == 4) {
                    this.mTransitionGoalPosition = obtainStyledAttributes.getFloat(index, 0.0f);
                    this.mInTransition = true;
                } else if (index == 0) {
                    z = obtainStyledAttributes.getBoolean(index, z);
                } else if (index == 5) {
                    if (this.mDebugPath == 0) {
                        if (!obtainStyledAttributes.getBoolean(index, false)) {
                            i3 = 0;
                        }
                        this.mDebugPath = i3;
                    }
                } else if (index == 3) {
                    this.mDebugPath = obtainStyledAttributes.getInt(index, 0);
                }
            }
            obtainStyledAttributes.recycle();
            if (this.mScene == null) {
                Log.e("MotionLayout", "WARNING NO app:layoutDescription tag");
            }
            if (!z) {
                this.mScene = null;
            }
        }
        if (this.mDebugPath != 0) {
            MotionScene motionScene2 = this.mScene;
            if (motionScene2 == null) {
                Log.e("MotionLayout", "CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
            } else {
                int startId = motionScene2.getStartId();
                MotionScene motionScene3 = this.mScene;
                ConstraintSet constraintSet = motionScene3.getConstraintSet(motionScene3.getStartId());
                String name = Debug.getName(getContext(), startId);
                int childCount = getChildCount();
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt = getChildAt(i4);
                    int id = childAt.getId();
                    if (id == -1) {
                        StringBuilder m = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("CHECK: ", name, " ALL VIEWS SHOULD HAVE ID's ");
                        m.append(childAt.getClass().getName());
                        m.append(" does not!");
                        Log.w("MotionLayout", m.toString());
                    }
                    if (constraintSet.getConstraint(id) == null) {
                        StringBuilder m2 = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("CHECK: ", name, " NO CONSTRAINTS for ");
                        m2.append(Debug.getName(childAt));
                        Log.w("MotionLayout", m2.toString());
                    }
                }
                Objects.requireNonNull(constraintSet);
                Integer[] numArr = (Integer[]) constraintSet.mConstraints.keySet().toArray(new Integer[0]);
                int length = numArr.length;
                int[] iArr = new int[length];
                for (int i5 = 0; i5 < length; i5++) {
                    iArr[i5] = numArr[i5].intValue();
                }
                for (int i6 = 0; i6 < length; i6++) {
                    int i7 = iArr[i6];
                    String name2 = Debug.getName(getContext(), i7);
                    if (findViewById(iArr[i6]) == null) {
                        Log.w("MotionLayout", "CHECK: " + name + " NO View matches id " + name2);
                    }
                    if (constraintSet.get(i7).layout.mHeight == -1) {
                        Log.w("MotionLayout", "CHECK: " + name + "(" + name2 + ") no LAYOUT_HEIGHT");
                    }
                    if (constraintSet.get(i7).layout.mWidth == -1) {
                        Log.w("MotionLayout", "CHECK: " + name + "(" + name2 + ") no LAYOUT_HEIGHT");
                    }
                }
                SparseIntArray sparseIntArray = new SparseIntArray();
                SparseIntArray sparseIntArray2 = new SparseIntArray();
                MotionScene motionScene4 = this.mScene;
                Objects.requireNonNull(motionScene4);
                Iterator<MotionScene.Transition> it = motionScene4.mTransitionList.iterator();
                while (it.hasNext()) {
                    MotionScene.Transition next = it.next();
                    if (next == this.mScene.mCurrentTransition) {
                        Log.v("MotionLayout", "CHECK: CURRENT");
                    }
                    StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("CHECK: transition = ");
                    Context context = getContext();
                    Objects.requireNonNull(next);
                    if (next.mConstraintSetStart == -1) {
                        str = "null";
                    } else {
                        str = context.getResources().getResourceEntryName(next.mConstraintSetStart);
                    }
                    if (next.mConstraintSetEnd == -1) {
                        str2 = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, " -> null");
                    } else {
                        StringBuilder m4 = DebugInfo$$ExternalSyntheticOutline0.m2m(str, " -> ");
                        m4.append(context.getResources().getResourceEntryName(next.mConstraintSetEnd));
                        str2 = m4.toString();
                    }
                    m3.append(str2);
                    Log.v("MotionLayout", m3.toString());
                    Log.v("MotionLayout", "CHECK: transition.setDuration = " + next.mDuration);
                    if (next.mConstraintSetStart == next.mConstraintSetEnd) {
                        Log.e("MotionLayout", "CHECK: start and end constraint set should not be the same!");
                    }
                    int i8 = next.mConstraintSetStart;
                    int i9 = next.mConstraintSetEnd;
                    String name3 = Debug.getName(getContext(), i8);
                    String name4 = Debug.getName(getContext(), i9);
                    if (sparseIntArray.get(i8) == i9) {
                        Log.e("MotionLayout", "CHECK: two transitions with the same start and end " + name3 + "->" + name4);
                    }
                    if (sparseIntArray2.get(i9) == i8) {
                        Log.e("MotionLayout", "CHECK: you can't have reverse transitions" + name3 + "->" + name4);
                    }
                    sparseIntArray.put(i8, i9);
                    sparseIntArray2.put(i9, i8);
                    if (this.mScene.getConstraintSet(i8) == null) {
                        Log.e("MotionLayout", " no such constraintSetStart " + name3);
                    }
                    if (this.mScene.getConstraintSet(i9) == null) {
                        Log.e("MotionLayout", " no such constraintSetEnd " + name3);
                    }
                }
            }
        }
        if (this.mCurrentState == -1 && (motionScene = this.mScene) != null) {
            this.mCurrentState = motionScene.getStartId();
            this.mBeginState = this.mScene.getStartId();
            MotionScene motionScene5 = this.mScene;
            Objects.requireNonNull(motionScene5);
            MotionScene.Transition transition = motionScene5.mCurrentTransition;
            if (transition != null) {
                i = transition.mConstraintSetEnd;
            }
            this.mEndState = i;
        }
    }

    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof MotionHelper) {
            MotionHelper motionHelper = (MotionHelper) view;
            if (this.mTransitionListeners == null) {
                this.mTransitionListeners = new ArrayList<>();
            }
            this.mTransitionListeners.add(motionHelper);
            if (motionHelper.mUseOnShow) {
                if (this.mOnShowHelpers == null) {
                    this.mOnShowHelpers = new ArrayList<>();
                }
                this.mOnShowHelpers.add(motionHelper);
            }
            if (motionHelper.mUseOnHide) {
                if (this.mOnHideHelpers == null) {
                    this.mOnHideHelpers = new ArrayList<>();
                }
                this.mOnHideHelpers.add(motionHelper);
            }
        }
    }

    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
        if (arrayList != null) {
            arrayList.remove(view);
        }
        ArrayList<MotionHelper> arrayList2 = this.mOnHideHelpers;
        if (arrayList2 != null) {
            arrayList2.remove(view);
        }
        if (this.mScrollTarget == view) {
            this.mScrollTarget = null;
        }
    }

    public final void setTransition(MotionScene.Transition transition) {
        int i;
        TouchResponse touchResponse;
        MotionScene motionScene = this.mScene;
        Objects.requireNonNull(motionScene);
        motionScene.mCurrentTransition = transition;
        if (!(transition == null || (touchResponse = transition.mTouchResponse) == null)) {
            touchResponse.setRTL(motionScene.mRtl);
        }
        int i2 = this.mCurrentState;
        MotionScene motionScene2 = this.mScene;
        Objects.requireNonNull(motionScene2);
        MotionScene.Transition transition2 = motionScene2.mCurrentTransition;
        int i3 = -1;
        if (transition2 == null) {
            i = -1;
        } else {
            i = transition2.mConstraintSetEnd;
        }
        if (i2 == i) {
            this.mTransitionLastPosition = 1.0f;
            this.mTransitionPosition = 1.0f;
            this.mTransitionGoalPosition = 1.0f;
        } else {
            this.mTransitionLastPosition = 0.0f;
            this.mTransitionPosition = 0.0f;
            this.mTransitionGoalPosition = 0.0f;
        }
        this.mTransitionLastTime = -1;
        int startId = this.mScene.getStartId();
        MotionScene motionScene3 = this.mScene;
        Objects.requireNonNull(motionScene3);
        MotionScene.Transition transition3 = motionScene3.mCurrentTransition;
        if (transition3 != null) {
            i3 = transition3.mConstraintSetEnd;
        }
        if (startId != this.mBeginState || i3 != this.mEndState) {
            this.mBeginState = startId;
            this.mEndState = i3;
            this.mScene.setTransition(startId, i3);
            this.mModel.initFrom(this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
            Model model = this.mModel;
            int i4 = this.mBeginState;
            int i5 = this.mEndState;
            Objects.requireNonNull(model);
            model.mStartId = i4;
            model.mEndId = i5;
            this.mModel.reEvaluateState();
            rebuildScene();
            ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
            if (arrayList != null) {
                Iterator<TransitionListener> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().onTransitionStarted();
                }
            }
        }
    }

    public MotionLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public MotionLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }
}
