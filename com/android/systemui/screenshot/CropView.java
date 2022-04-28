package com.android.systemui.screenshot;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Range;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.widget.SeekBar;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$styleable;
import java.util.ArrayList;
import java.util.Objects;

public class CropView extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActivePointerId;
    public final Paint mContainerBackgroundPaint;
    public RectF mCrop;
    public CropInteractionListener mCropInteractionListener;
    public final float mCropTouchMargin;
    public CropBoundary mCurrentDraggingBoundary;
    public float mEntranceInterpolation;
    public final AccessibilityHelper mExploreByTouchHelper;
    public int mExtraBottomPadding;
    public int mExtraTopPadding;
    public final Paint mHandlePaint;
    public int mImageWidth;
    public Range<Float> mMotionRange;
    public float mMovementStartValue;
    public final Paint mShadePaint;
    public float mStartingX;
    public float mStartingY;

    public class AccessibilityHelper extends ExploreByTouchHelper {
        public static CropBoundary viewIdToBoundary(int i) {
            if (i == 1) {
                return CropBoundary.TOP;
            }
            if (i == 2) {
                return CropBoundary.BOTTOM;
            }
            if (i == 3) {
                return CropBoundary.LEFT;
            }
            if (i != 4) {
                return CropBoundary.NONE;
            }
            return CropBoundary.RIGHT;
        }

        public final void getVisibleVirtualViews(ArrayList arrayList) {
            arrayList.add(1);
            arrayList.add(3);
            arrayList.add(4);
            arrayList.add(2);
        }

        public AccessibilityHelper() {
            super(CropView.this);
        }

        public final int getVirtualViewAt(float f, float f2) {
            CropView cropView = CropView.this;
            float abs = Math.abs(f2 - ((float) cropView.fractionToVerticalPixels(cropView.mCrop.top)));
            CropView cropView2 = CropView.this;
            if (abs < cropView2.mCropTouchMargin) {
                return 1;
            }
            float abs2 = Math.abs(f2 - ((float) cropView2.fractionToVerticalPixels(cropView2.mCrop.bottom)));
            CropView cropView3 = CropView.this;
            if (abs2 < cropView3.mCropTouchMargin) {
                return 2;
            }
            if (f2 <= ((float) cropView3.fractionToVerticalPixels(cropView3.mCrop.top))) {
                return -1;
            }
            CropView cropView4 = CropView.this;
            if (f2 >= ((float) cropView4.fractionToVerticalPixels(cropView4.mCrop.bottom))) {
                return -1;
            }
            CropView cropView5 = CropView.this;
            float abs3 = Math.abs(f - ((float) cropView5.fractionToHorizontalPixels(cropView5.mCrop.left)));
            CropView cropView6 = CropView.this;
            if (abs3 < cropView6.mCropTouchMargin) {
                return 3;
            }
            if (Math.abs(f - ((float) cropView6.fractionToHorizontalPixels(cropView6.mCrop.right))) < CropView.this.mCropTouchMargin) {
                return 4;
            }
            return -1;
        }

        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 4096 && i2 != 8192) {
                return false;
            }
            CropBoundary viewIdToBoundary = viewIdToBoundary(i);
            CropView cropView = CropView.this;
            float pixelDistanceToFraction = cropView.pixelDistanceToFraction(cropView.mCropTouchMargin, viewIdToBoundary);
            if (i2 == 4096) {
                pixelDistanceToFraction = -pixelDistanceToFraction;
            }
            CropView cropView2 = CropView.this;
            cropView2.setBoundaryPosition(viewIdToBoundary, cropView2.getBoundaryPosition(viewIdToBoundary) + pixelDistanceToFraction);
            invalidateVirtualView(i);
            sendEventForVirtualView(i, 4);
            return true;
        }

        public final String getBoundaryContentDescription(CropBoundary cropBoundary) {
            int i;
            int ordinal = cropBoundary.ordinal();
            if (ordinal == 1) {
                i = C1777R.string.screenshot_top_boundary_pct;
            } else if (ordinal == 2) {
                i = C1777R.string.screenshot_bottom_boundary_pct;
            } else if (ordinal == 3) {
                i = C1777R.string.screenshot_left_boundary_pct;
            } else if (ordinal != 4) {
                return "";
            } else {
                i = C1777R.string.screenshot_right_boundary_pct;
            }
            Resources resources = CropView.this.getResources();
            CropView cropView = CropView.this;
            int i2 = CropView.$r8$clinit;
            return resources.getString(i, new Object[]{Integer.valueOf(Math.round(cropView.getBoundaryPosition(cropBoundary) * 100.0f))});
        }

        public final void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getBoundaryContentDescription(viewIdToBoundary(i)));
        }

        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Rect rect;
            CropBoundary viewIdToBoundary = viewIdToBoundary(i);
            accessibilityNodeInfoCompat.setContentDescription(getBoundaryContentDescription(viewIdToBoundary));
            if (CropView.isVertical(viewIdToBoundary)) {
                CropView cropView = CropView.this;
                float fractionToVerticalPixels = (float) cropView.fractionToVerticalPixels(cropView.getBoundaryPosition(viewIdToBoundary));
                CropView cropView2 = CropView.this;
                rect = new Rect(0, (int) (fractionToVerticalPixels - cropView2.mCropTouchMargin), cropView2.getWidth(), (int) (fractionToVerticalPixels + CropView.this.mCropTouchMargin));
                int i2 = rect.top;
                if (i2 < 0) {
                    rect.offset(0, -i2);
                }
            } else {
                CropView cropView3 = CropView.this;
                float fractionToHorizontalPixels = (float) cropView3.fractionToHorizontalPixels(cropView3.getBoundaryPosition(viewIdToBoundary));
                CropView cropView4 = CropView.this;
                CropView cropView5 = CropView.this;
                float f = cropView5.mCropTouchMargin;
                rect = new Rect((int) (fractionToHorizontalPixels - cropView4.mCropTouchMargin), (int) (((float) cropView4.fractionToVerticalPixels(cropView4.mCrop.top)) + f), (int) (fractionToHorizontalPixels + f), (int) (((float) cropView5.fractionToVerticalPixels(cropView5.mCrop.bottom)) - CropView.this.mCropTouchMargin));
            }
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            int[] iArr = new int[2];
            CropView.this.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            accessibilityNodeInfoCompat.mInfo.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            accessibilityNodeInfoCompat.addAction(4096);
            accessibilityNodeInfoCompat.addAction(8192);
        }
    }

    public enum CropBoundary {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public interface CropInteractionListener {
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public RectF mCrop;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mCrop = (RectF) parcel.readParcelable(ClassLoader.getSystemClassLoader());
        }

        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.mCrop, 0);
        }
    }

    public CropView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CropView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCrop = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        this.mCurrentDraggingBoundary = CropBoundary.NONE;
        this.mEntranceInterpolation = 1.0f;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.CropView, 0, 0);
        Paint paint = new Paint();
        this.mShadePaint = paint;
        paint.setColor(ColorUtils.setAlphaComponent(obtainStyledAttributes.getColor(4, 0), obtainStyledAttributes.getInteger(3, 255)));
        Paint paint2 = new Paint();
        this.mContainerBackgroundPaint = paint2;
        paint2.setColor(obtainStyledAttributes.getColor(0, 0));
        Paint paint3 = new Paint();
        this.mHandlePaint = paint3;
        paint3.setColor(obtainStyledAttributes.getColor(1, -16777216));
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStrokeWidth((float) obtainStyledAttributes.getDimensionPixelSize(2, 20));
        obtainStyledAttributes.recycle();
        this.mCropTouchMargin = getResources().getDisplayMetrics().density * 24.0f;
        AccessibilityHelper accessibilityHelper = new AccessibilityHelper();
        this.mExploreByTouchHelper = accessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, accessibilityHelper);
    }

    public static boolean isVertical(CropBoundary cropBoundary) {
        if (cropBoundary == CropBoundary.TOP || cropBoundary == CropBoundary.BOTTOM) {
            return true;
        }
        return false;
    }

    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.mExploreByTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent)) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r10) {
        /*
            r9 = this;
            com.android.systemui.screenshot.CropView$AccessibilityHelper r0 = r9.mExploreByTouchHelper
            java.util.Objects.requireNonNull(r0)
            int r1 = r10.getAction()
            r2 = 0
            r3 = 1
            if (r1 == r3) goto L_0x007b
            int r1 = r10.getKeyCode()
            r4 = 61
            r5 = 0
            if (r1 == r4) goto L_0x0064
            r4 = 66
            if (r1 == r4) goto L_0x004b
            switch(r1) {
                case 19: goto L_0x001e;
                case 20: goto L_0x001e;
                case 21: goto L_0x001e;
                case 22: goto L_0x001e;
                case 23: goto L_0x004b;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x007b
        L_0x001e:
            boolean r6 = r10.hasNoModifiers()
            if (r6 == 0) goto L_0x007b
            r6 = 19
            if (r1 == r6) goto L_0x0036
            r6 = 21
            if (r1 == r6) goto L_0x0033
            r6 = 22
            if (r1 == r6) goto L_0x0038
            r4 = 130(0x82, float:1.82E-43)
            goto L_0x0038
        L_0x0033:
            r4 = 17
            goto L_0x0038
        L_0x0036:
            r4 = 33
        L_0x0038:
            int r1 = r10.getRepeatCount()
            int r1 = r1 + r3
            r6 = r2
            r7 = r6
        L_0x003f:
            if (r6 >= r1) goto L_0x007c
            boolean r8 = r0.moveFocus(r4, r5)
            if (r8 == 0) goto L_0x007c
            int r6 = r6 + 1
            r7 = r3
            goto L_0x003f
        L_0x004b:
            boolean r1 = r10.hasNoModifiers()
            if (r1 == 0) goto L_0x007b
            int r1 = r10.getRepeatCount()
            if (r1 != 0) goto L_0x007b
            int r1 = r0.mKeyboardFocusedVirtualViewId
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r1 == r4) goto L_0x0062
            r4 = 16
            r0.onPerformActionForVirtualView(r1, r4, r5)
        L_0x0062:
            r7 = r3
            goto L_0x007c
        L_0x0064:
            boolean r1 = r10.hasNoModifiers()
            if (r1 == 0) goto L_0x0070
            r1 = 2
            boolean r7 = r0.moveFocus(r1, r5)
            goto L_0x007c
        L_0x0070:
            boolean r1 = r10.hasModifiers(r3)
            if (r1 == 0) goto L_0x007b
            boolean r7 = r0.moveFocus(r3, r5)
            goto L_0x007c
        L_0x007b:
            r7 = r2
        L_0x007c:
            if (r7 != 0) goto L_0x0084
            boolean r9 = super.dispatchKeyEvent(r10)
            if (r9 == 0) goto L_0x0085
        L_0x0084:
            r2 = r3
        L_0x0085:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.CropView.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public final int fractionToVerticalPixels(float f) {
        return (int) ((f * ((float) ((getHeight() - this.mExtraTopPadding) - this.mExtraBottomPadding))) + ((float) this.mExtraTopPadding));
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCrop = savedState.mCrop;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        CropBoundary cropBoundary;
        float f;
        float f2;
        CropBoundary cropBoundary2 = CropBoundary.NONE;
        int fractionToVerticalPixels = fractionToVerticalPixels(this.mCrop.top);
        int fractionToVerticalPixels2 = fractionToVerticalPixels(this.mCrop.bottom);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked != 5) {
                            if (actionMasked == 6 && this.mActivePointerId == motionEvent.getPointerId(motionEvent.getActionIndex()) && this.mCurrentDraggingBoundary != cropBoundary2) {
                                updateListener(1, motionEvent.getX(motionEvent.getActionIndex()));
                                return true;
                            }
                        } else if (this.mActivePointerId == motionEvent.getPointerId(motionEvent.getActionIndex()) && this.mCurrentDraggingBoundary != cropBoundary2) {
                            updateListener(0, motionEvent.getX(motionEvent.getActionIndex()));
                            return true;
                        }
                    }
                } else if (this.mCurrentDraggingBoundary != cropBoundary2) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex >= 0) {
                        if (isVertical(this.mCurrentDraggingBoundary)) {
                            f2 = motionEvent.getY(findPointerIndex);
                            f = this.mStartingY;
                        } else {
                            f2 = motionEvent.getX(findPointerIndex);
                            f = this.mStartingX;
                        }
                        setBoundaryPosition(this.mCurrentDraggingBoundary, this.mMotionRange.clamp(Float.valueOf(this.mMovementStartValue + pixelDistanceToFraction((float) ((int) (f2 - f)), this.mCurrentDraggingBoundary))).floatValue());
                        updateListener(2, motionEvent.getX(findPointerIndex));
                        invalidate();
                    }
                    return true;
                }
                return super.onTouchEvent(motionEvent);
            }
            if (this.mCurrentDraggingBoundary != cropBoundary2) {
                int i = this.mActivePointerId;
                if (i == motionEvent.getPointerId(i)) {
                    updateListener(1, motionEvent.getX(0));
                    return true;
                }
            }
            return super.onTouchEvent(motionEvent);
        }
        int fractionToHorizontalPixels = fractionToHorizontalPixels(this.mCrop.left);
        int fractionToHorizontalPixels2 = fractionToHorizontalPixels(this.mCrop.right);
        float f3 = (float) fractionToVerticalPixels;
        if (Math.abs(motionEvent.getY() - f3) < this.mCropTouchMargin) {
            cropBoundary = CropBoundary.TOP;
        } else {
            float f4 = (float) fractionToVerticalPixels2;
            if (Math.abs(motionEvent.getY() - f4) < this.mCropTouchMargin) {
                cropBoundary = CropBoundary.BOTTOM;
            } else {
                if (motionEvent.getY() > f3 || motionEvent.getY() < f4) {
                    if (Math.abs(motionEvent.getX() - ((float) fractionToHorizontalPixels)) < this.mCropTouchMargin) {
                        cropBoundary = CropBoundary.LEFT;
                    } else if (Math.abs(motionEvent.getX() - ((float) fractionToHorizontalPixels2)) < this.mCropTouchMargin) {
                        cropBoundary = CropBoundary.RIGHT;
                    }
                }
                cropBoundary = cropBoundary2;
            }
        }
        this.mCurrentDraggingBoundary = cropBoundary;
        if (cropBoundary != cropBoundary2) {
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mStartingY = motionEvent.getY();
            this.mStartingX = motionEvent.getX();
            this.mMovementStartValue = getBoundaryPosition(this.mCurrentDraggingBoundary);
            updateListener(0, motionEvent.getX());
            this.mMotionRange = getAllowedValues(this.mCurrentDraggingBoundary);
        }
        return true;
    }

    public final void updateListener(int i, float f) {
        float f2;
        boolean z;
        boolean z2;
        if (this.mCropInteractionListener != null && isVertical(this.mCurrentDraggingBoundary)) {
            float boundaryPosition = getBoundaryPosition(this.mCurrentDraggingBoundary);
            boolean z3 = false;
            float f3 = 0.0f;
            boolean z4 = true;
            if (i == 0) {
                CropInteractionListener cropInteractionListener = this.mCropInteractionListener;
                CropBoundary cropBoundary = this.mCurrentDraggingBoundary;
                int fractionToVerticalPixels = fractionToVerticalPixels(boundaryPosition);
                RectF rectF = this.mCrop;
                MagnifierView magnifierView = (MagnifierView) cropInteractionListener;
                Objects.requireNonNull(magnifierView);
                magnifierView.mCropBoundary = cropBoundary;
                magnifierView.mLastCenter = (rectF.left + rectF.right) / 2.0f;
                if (f <= ((float) (magnifierView.getParentWidth() / 2))) {
                    z4 = false;
                }
                if (z4) {
                    f2 = 0.0f;
                } else {
                    f2 = (float) (magnifierView.getParentWidth() - magnifierView.getWidth());
                }
                magnifierView.mLastCropPosition = boundaryPosition;
                magnifierView.setTranslationY((float) (fractionToVerticalPixels - (magnifierView.getHeight() / 2)));
                magnifierView.setPivotX((float) (magnifierView.getWidth() / 2));
                magnifierView.setPivotY((float) (magnifierView.getHeight() / 2));
                magnifierView.setScaleX(0.2f);
                magnifierView.setScaleY(0.2f);
                magnifierView.setAlpha(0.0f);
                magnifierView.setTranslationX((float) ((magnifierView.getParentWidth() - magnifierView.getWidth()) / 2));
                magnifierView.setVisibility(0);
                ViewPropertyAnimator scaleY = magnifierView.animate().alpha(1.0f).translationX(f2).scaleX(1.0f).scaleY(1.0f);
                magnifierView.mTranslationAnimator = scaleY;
                scaleY.setListener(magnifierView.mTranslationAnimatorListener);
                magnifierView.mTranslationAnimator.start();
            } else if (i == 1) {
                MagnifierView magnifierView2 = (MagnifierView) this.mCropInteractionListener;
                Objects.requireNonNull(magnifierView2);
                magnifierView2.animate().alpha(0.0f).translationX((float) ((magnifierView2.getParentWidth() - magnifierView2.getWidth()) / 2)).scaleX(0.2f).scaleY(0.2f).withEndAction(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(magnifierView2, 5)).start();
            } else if (i == 2) {
                CropInteractionListener cropInteractionListener2 = this.mCropInteractionListener;
                int fractionToVerticalPixels2 = fractionToVerticalPixels(boundaryPosition);
                float f4 = this.mCrop.left;
                MagnifierView magnifierView3 = (MagnifierView) cropInteractionListener2;
                Objects.requireNonNull(magnifierView3);
                if (f > ((float) (magnifierView3.getParentWidth() / 2))) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    f3 = (float) (magnifierView3.getParentWidth() - magnifierView3.getWidth());
                }
                if (Math.abs(f - ((float) (magnifierView3.getParentWidth() / 2))) < ((float) magnifierView3.getParentWidth()) / 10.0f) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (magnifierView3.getTranslationX() < ((float) ((magnifierView3.getParentWidth() - magnifierView3.getWidth()) / 2))) {
                    z3 = true;
                }
                if (!z2 && z3 != z && magnifierView3.mTranslationAnimator == null) {
                    ViewPropertyAnimator translationX = magnifierView3.animate().translationX(f3);
                    magnifierView3.mTranslationAnimator = translationX;
                    translationX.setListener(magnifierView3.mTranslationAnimatorListener);
                    magnifierView3.mTranslationAnimator.start();
                }
                magnifierView3.mLastCropPosition = boundaryPosition;
                magnifierView3.setTranslationY((float) (fractionToVerticalPixels2 - (magnifierView3.getHeight() / 2)));
                magnifierView3.invalidate();
            }
        }
    }

    public final void drawHorizontalHandle(Canvas canvas, float f, boolean z) {
        float f2;
        float fractionToVerticalPixels = (float) fractionToVerticalPixels(f);
        canvas.drawLine((float) fractionToHorizontalPixels(this.mCrop.left), fractionToVerticalPixels, (float) fractionToHorizontalPixels(this.mCrop.right), fractionToVerticalPixels, this.mHandlePaint);
        float f3 = getResources().getDisplayMetrics().density * 8.0f;
        float fractionToHorizontalPixels = (float) ((fractionToHorizontalPixels(this.mCrop.right) + fractionToHorizontalPixels(this.mCrop.left)) / 2);
        float f4 = fractionToHorizontalPixels - f3;
        float f5 = fractionToVerticalPixels - f3;
        float f6 = fractionToHorizontalPixels + f3;
        float f7 = fractionToVerticalPixels + f3;
        if (z) {
            f2 = 180.0f;
        } else {
            f2 = 0.0f;
        }
        canvas.drawArc(f4, f5, f6, f7, f2, 180.0f, true, this.mHandlePaint);
    }

    public final void drawShade(Canvas canvas, float f, float f2, float f3, float f4) {
        canvas.drawRect((float) fractionToHorizontalPixels(f), (float) fractionToVerticalPixels(f2), (float) fractionToHorizontalPixels(f3), (float) fractionToVerticalPixels(f4), this.mShadePaint);
    }

    public final void drawVerticalHandle(Canvas canvas, float f, boolean z) {
        float f2;
        float fractionToHorizontalPixels = (float) fractionToHorizontalPixels(f);
        canvas.drawLine(fractionToHorizontalPixels, (float) fractionToVerticalPixels(this.mCrop.top), fractionToHorizontalPixels, (float) fractionToVerticalPixels(this.mCrop.bottom), this.mHandlePaint);
        float f3 = getResources().getDisplayMetrics().density * 8.0f;
        float f4 = fractionToHorizontalPixels - f3;
        float fractionToVerticalPixels = (float) ((fractionToVerticalPixels(getBoundaryPosition(CropBoundary.BOTTOM)) + fractionToVerticalPixels(getBoundaryPosition(CropBoundary.TOP))) / 2);
        float f5 = fractionToVerticalPixels - f3;
        float f6 = fractionToHorizontalPixels + f3;
        float f7 = fractionToVerticalPixels + f3;
        if (z) {
            f2 = 90.0f;
        } else {
            f2 = 270.0f;
        }
        canvas.drawArc(f4, f5, f6, f7, f2, 180.0f, true, this.mHandlePaint);
    }

    public final int fractionToHorizontalPixels(float f) {
        int width = getWidth();
        int i = this.mImageWidth;
        return (int) ((f * ((float) i)) + ((float) ((width - i) / 2)));
    }

    public final Range getAllowedValues(CropBoundary cropBoundary) {
        int ordinal = cropBoundary.ordinal();
        if (ordinal == 1) {
            return new Range(Float.valueOf(0.0f), Float.valueOf(this.mCrop.bottom - pixelDistanceToFraction(this.mCropTouchMargin, CropBoundary.BOTTOM)));
        }
        if (ordinal == 2) {
            return new Range(Float.valueOf(pixelDistanceToFraction(this.mCropTouchMargin, CropBoundary.TOP) + this.mCrop.top), Float.valueOf(1.0f));
        } else if (ordinal == 3) {
            return new Range(Float.valueOf(0.0f), Float.valueOf(this.mCrop.right - pixelDistanceToFraction(this.mCropTouchMargin, CropBoundary.RIGHT)));
        } else {
            if (ordinal != 4) {
                return null;
            }
            return new Range(Float.valueOf(pixelDistanceToFraction(this.mCropTouchMargin, CropBoundary.LEFT) + this.mCrop.left), Float.valueOf(1.0f));
        }
    }

    public final float getBoundaryPosition(CropBoundary cropBoundary) {
        int ordinal = cropBoundary.ordinal();
        if (ordinal == 1) {
            return this.mCrop.top;
        }
        if (ordinal == 2) {
            return this.mCrop.bottom;
        }
        if (ordinal == 3) {
            return this.mCrop.left;
        }
        if (ordinal != 4) {
            return 0.0f;
        }
        return this.mCrop.right;
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lerp = MathUtils.lerp(this.mCrop.top, 0.0f, this.mEntranceInterpolation);
        float lerp2 = MathUtils.lerp(this.mCrop.bottom, 1.0f, this.mEntranceInterpolation);
        Canvas canvas2 = canvas;
        drawShade(canvas2, 0.0f, lerp, 1.0f, this.mCrop.top);
        drawShade(canvas2, 0.0f, this.mCrop.bottom, 1.0f, lerp2);
        RectF rectF = this.mCrop;
        drawShade(canvas2, 0.0f, rectF.top, rectF.left, rectF.bottom);
        RectF rectF2 = this.mCrop;
        drawShade(canvas2, rectF2.right, rectF2.top, 1.0f, rectF2.bottom);
        canvas.drawRect((float) fractionToHorizontalPixels(0.0f), (float) fractionToVerticalPixels(0.0f), (float) fractionToHorizontalPixels(1.0f), (float) fractionToVerticalPixels(lerp), this.mContainerBackgroundPaint);
        canvas.drawRect((float) fractionToHorizontalPixels(0.0f), (float) fractionToVerticalPixels(lerp2), (float) fractionToHorizontalPixels(1.0f), (float) fractionToVerticalPixels(1.0f), this.mContainerBackgroundPaint);
        this.mHandlePaint.setAlpha((int) (this.mEntranceInterpolation * 255.0f));
        drawHorizontalHandle(canvas, this.mCrop.top, true);
        drawHorizontalHandle(canvas, this.mCrop.bottom, false);
        drawVerticalHandle(canvas, this.mCrop.left, true);
        drawVerticalHandle(canvas, this.mCrop.right, false);
    }

    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        AccessibilityHelper accessibilityHelper = this.mExploreByTouchHelper;
        Objects.requireNonNull(accessibilityHelper);
        int i2 = accessibilityHelper.mKeyboardFocusedVirtualViewId;
        if (i2 != Integer.MIN_VALUE) {
            accessibilityHelper.clearKeyboardFocusForVirtualView(i2);
        }
        if (z) {
            accessibilityHelper.moveFocus(i, rect);
        }
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mCrop = this.mCrop;
        return savedState;
    }

    public final float pixelDistanceToFraction(float f, CropBoundary cropBoundary) {
        int i;
        if (isVertical(cropBoundary)) {
            i = (getHeight() - this.mExtraTopPadding) - this.mExtraBottomPadding;
        } else {
            i = this.mImageWidth;
        }
        return f / ((float) i);
    }

    public final void setBoundaryPosition(CropBoundary cropBoundary, float f) {
        float floatValue = ((Float) getAllowedValues(cropBoundary).clamp(Float.valueOf(f))).floatValue();
        int ordinal = cropBoundary.ordinal();
        if (ordinal == 0) {
            Log.w("CropView", "No boundary selected");
        } else if (ordinal == 1) {
            this.mCrop.top = floatValue;
        } else if (ordinal == 2) {
            this.mCrop.bottom = floatValue;
        } else if (ordinal == 3) {
            this.mCrop.left = floatValue;
        } else if (ordinal == 4) {
            this.mCrop.right = floatValue;
        }
        invalidate();
    }
}
