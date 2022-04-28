package androidx.transition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import java.util.Objects;
import java.util.WeakHashMap;

public class ChangeBounds extends Transition {
    public static final C03844 BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class) {
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            View view = (View) obj;
            return null;
        }

        public final void set(Object obj, Object obj2) {
            View view = (View) obj;
            PointF pointF = (PointF) obj2;
            int left = view.getLeft();
            int top = view.getTop();
            int round = Math.round(pointF.x);
            int round2 = Math.round(pointF.y);
            ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
            view.setLeftTopRightBottom(left, top, round, round2);
        }
    };
    public static final C03833 BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class) {
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            ViewBounds viewBounds = (ViewBounds) obj;
            return null;
        }

        public final void set(Object obj, Object obj2) {
            ViewBounds viewBounds = (ViewBounds) obj;
            PointF pointF = (PointF) obj2;
            Objects.requireNonNull(viewBounds);
            viewBounds.mRight = Math.round(pointF.x);
            int round = Math.round(pointF.y);
            viewBounds.mBottom = round;
            int i = viewBounds.mBottomRightCalls + 1;
            viewBounds.mBottomRightCalls = i;
            if (viewBounds.mTopLeftCalls == i) {
                View view = viewBounds.mView;
                int i2 = viewBounds.mLeft;
                int i3 = viewBounds.mTop;
                int i4 = viewBounds.mRight;
                ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                view.setLeftTopRightBottom(i2, i3, i4, round);
                viewBounds.mTopLeftCalls = 0;
                viewBounds.mBottomRightCalls = 0;
            }
        }
    };
    public static final C03811 DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class) {
        public Rect mBounds = new Rect();

        public final Object get(Object obj) {
            ((Drawable) obj).copyBounds(this.mBounds);
            Rect rect = this.mBounds;
            return new PointF((float) rect.left, (float) rect.top);
        }

        public final void set(Object obj, Object obj2) {
            Drawable drawable = (Drawable) obj;
            PointF pointF = (PointF) obj2;
            drawable.copyBounds(this.mBounds);
            this.mBounds.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
            drawable.setBounds(this.mBounds);
        }
    };
    public static final C03866 POSITION_PROPERTY = new Property<View, PointF>(PointF.class) {
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            View view = (View) obj;
            return null;
        }

        public final void set(Object obj, Object obj2) {
            View view = (View) obj;
            PointF pointF = (PointF) obj2;
            int round = Math.round(pointF.x);
            int round2 = Math.round(pointF.y);
            ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
            view.setLeftTopRightBottom(round, round2, view.getWidth() + round, view.getHeight() + round2);
        }
    };
    public static final C03855 TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class) {
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            View view = (View) obj;
            return null;
        }

        public final void set(Object obj, Object obj2) {
            View view = (View) obj;
            PointF pointF = (PointF) obj2;
            int round = Math.round(pointF.x);
            int round2 = Math.round(pointF.y);
            int right = view.getRight();
            int bottom = view.getBottom();
            ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
            view.setLeftTopRightBottom(round, round2, right, bottom);
        }
    };
    public static final C03822 TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class) {
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            ViewBounds viewBounds = (ViewBounds) obj;
            return null;
        }

        public final void set(Object obj, Object obj2) {
            ViewBounds viewBounds = (ViewBounds) obj;
            PointF pointF = (PointF) obj2;
            Objects.requireNonNull(viewBounds);
            viewBounds.mLeft = Math.round(pointF.x);
            int round = Math.round(pointF.y);
            viewBounds.mTop = round;
            int i = viewBounds.mTopLeftCalls + 1;
            viewBounds.mTopLeftCalls = i;
            if (i == viewBounds.mBottomRightCalls) {
                View view = viewBounds.mView;
                int i2 = viewBounds.mLeft;
                int i3 = viewBounds.mRight;
                int i4 = viewBounds.mBottom;
                ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                view.setLeftTopRightBottom(i2, round, i3, i4);
                viewBounds.mTopLeftCalls = 0;
                viewBounds.mBottomRightCalls = 0;
            }
        }
    };
    public static RectEvaluator sRectEvaluator = new RectEvaluator();
    public static final String[] sTransitionProperties = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    public boolean mResizeClip;
    public int[] mTempLocation;

    public ChangeBounds() {
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
    }

    public static class ViewBounds {
        public int mBottom;
        public int mBottomRightCalls;
        public int mLeft;
        public int mRight;
        public int mTop;
        public int mTopLeftCalls;
        public View mView;

        public ViewBounds(View view) {
            this.mView = view;
        }
    }

    public final void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api19Impl.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            transitionValues.values.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            transitionValues.values.put("android:changeBounds:parent", transitionValues.view.getParent());
            if (this.mResizeClip) {
                transitionValues.values.put("android:changeBounds:clip", ViewCompat.Api18Impl.getClipBounds(view));
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x01ae  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.animation.Animator createAnimator(android.view.ViewGroup r20, androidx.transition.TransitionValues r21, androidx.transition.TransitionValues r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r21
            r2 = r22
            if (r1 == 0) goto L_0x01c4
            if (r2 != 0) goto L_0x000c
            goto L_0x01c4
        L_0x000c:
            java.util.HashMap r4 = r1.values
            java.util.HashMap r5 = r2.values
            java.lang.String r6 = "android:changeBounds:parent"
            java.lang.Object r4 = r4.get(r6)
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            java.lang.Object r5 = r5.get(r6)
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            if (r4 == 0) goto L_0x01c2
            if (r5 != 0) goto L_0x0024
            goto L_0x01c2
        L_0x0024:
            android.view.View r4 = r2.view
            java.util.HashMap r5 = r1.values
            java.lang.String r6 = "android:changeBounds:bounds"
            java.lang.Object r5 = r5.get(r6)
            android.graphics.Rect r5 = (android.graphics.Rect) r5
            java.util.HashMap r7 = r2.values
            java.lang.Object r6 = r7.get(r6)
            android.graphics.Rect r6 = (android.graphics.Rect) r6
            int r7 = r5.left
            int r9 = r6.left
            int r8 = r5.top
            int r10 = r6.top
            int r11 = r5.right
            int r12 = r6.right
            int r5 = r5.bottom
            int r13 = r6.bottom
            int r6 = r11 - r7
            int r14 = r5 - r8
            int r15 = r12 - r9
            int r3 = r13 - r10
            java.util.HashMap r1 = r1.values
            r16 = r4
            java.lang.String r4 = "android:changeBounds:clip"
            java.lang.Object r1 = r1.get(r4)
            android.graphics.Rect r1 = (android.graphics.Rect) r1
            java.util.HashMap r2 = r2.values
            java.lang.Object r2 = r2.get(r4)
            android.graphics.Rect r2 = (android.graphics.Rect) r2
            if (r6 == 0) goto L_0x0068
            if (r14 != 0) goto L_0x006c
        L_0x0068:
            if (r15 == 0) goto L_0x007d
            if (r3 == 0) goto L_0x007d
        L_0x006c:
            if (r7 != r9) goto L_0x0074
            if (r8 == r10) goto L_0x0071
            goto L_0x0074
        L_0x0071:
            r17 = 0
            goto L_0x0076
        L_0x0074:
            r17 = 1
        L_0x0076:
            if (r11 != r12) goto L_0x007a
            if (r5 == r13) goto L_0x007f
        L_0x007a:
            int r17 = r17 + 1
            goto L_0x007f
        L_0x007d:
            r17 = 0
        L_0x007f:
            if (r1 == 0) goto L_0x0087
            boolean r18 = r1.equals(r2)
            if (r18 == 0) goto L_0x008b
        L_0x0087:
            if (r1 != 0) goto L_0x008d
            if (r2 == 0) goto L_0x008d
        L_0x008b:
            int r17 = r17 + 1
        L_0x008d:
            r4 = r17
            if (r4 <= 0) goto L_0x01c0
            r17 = r2
            boolean r2 = r0.mResizeClip
            r18 = r1
            r1 = 2
            if (r2 != 0) goto L_0x0124
            androidx.transition.ViewUtilsApi29 r2 = androidx.transition.ViewUtils.IMPL
            r2 = r16
            r2.setLeftTopRightBottom(r7, r8, r11, r5)
            if (r4 != r1) goto L_0x00f9
            if (r6 != r15) goto L_0x00b9
            if (r14 != r3) goto L_0x00b9
            androidx.transition.PathMotion r1 = r0.mPathMotion
            float r3 = (float) r7
            float r4 = (float) r8
            float r5 = (float) r9
            float r6 = (float) r10
            android.graphics.Path r1 = r1.getPath(r3, r4, r5, r6)
            androidx.transition.ChangeBounds$6 r3 = POSITION_PROPERTY
            r4 = 0
            android.animation.ObjectAnimator r1 = android.animation.ObjectAnimator.ofObject(r2, r3, r4, r1)
            goto L_0x0121
        L_0x00b9:
            androidx.transition.ChangeBounds$ViewBounds r3 = new androidx.transition.ChangeBounds$ViewBounds
            r3.<init>(r2)
            androidx.transition.PathMotion r4 = r0.mPathMotion
            float r6 = (float) r7
            float r7 = (float) r8
            float r8 = (float) r9
            float r9 = (float) r10
            android.graphics.Path r4 = r4.getPath(r6, r7, r8, r9)
            androidx.transition.ChangeBounds$2 r6 = TOP_LEFT_PROPERTY
            r7 = 0
            android.animation.ObjectAnimator r4 = android.animation.ObjectAnimator.ofObject(r3, r6, r7, r4)
            androidx.transition.PathMotion r6 = r0.mPathMotion
            float r8 = (float) r11
            float r5 = (float) r5
            float r9 = (float) r12
            float r10 = (float) r13
            android.graphics.Path r5 = r6.getPath(r8, r5, r9, r10)
            androidx.transition.ChangeBounds$3 r6 = BOTTOM_RIGHT_PROPERTY
            android.animation.ObjectAnimator r5 = android.animation.ObjectAnimator.ofObject(r3, r6, r7, r5)
            android.animation.AnimatorSet r6 = new android.animation.AnimatorSet
            r6.<init>()
            android.animation.Animator[] r1 = new android.animation.Animator[r1]
            r7 = 0
            r1[r7] = r4
            r4 = 1
            r1[r4] = r5
            r6.playTogether(r1)
            androidx.transition.ChangeBounds$7 r1 = new androidx.transition.ChangeBounds$7
            r1.<init>(r3)
            r6.addListener(r1)
            r1 = r6
            goto L_0x0121
        L_0x00f9:
            if (r7 != r9) goto L_0x0110
            if (r8 == r10) goto L_0x00fe
            goto L_0x0110
        L_0x00fe:
            androidx.transition.PathMotion r1 = r0.mPathMotion
            float r3 = (float) r11
            float r4 = (float) r5
            float r5 = (float) r12
            float r6 = (float) r13
            android.graphics.Path r1 = r1.getPath(r3, r4, r5, r6)
            androidx.transition.ChangeBounds$4 r3 = BOTTOM_RIGHT_ONLY_PROPERTY
            r4 = 0
            android.animation.ObjectAnimator r1 = android.animation.ObjectAnimator.ofObject(r2, r3, r4, r1)
            goto L_0x0121
        L_0x0110:
            r4 = 0
            androidx.transition.PathMotion r1 = r0.mPathMotion
            float r3 = (float) r7
            float r5 = (float) r8
            float r6 = (float) r9
            float r7 = (float) r10
            android.graphics.Path r1 = r1.getPath(r3, r5, r6, r7)
            androidx.transition.ChangeBounds$5 r3 = TOP_LEFT_ONLY_PROPERTY
            android.animation.ObjectAnimator r1 = android.animation.ObjectAnimator.ofObject(r2, r3, r4, r1)
        L_0x0121:
            r4 = 1
            goto L_0x01a6
        L_0x0124:
            r2 = r16
            int r4 = java.lang.Math.max(r6, r15)
            int r5 = java.lang.Math.max(r14, r3)
            int r4 = r4 + r7
            int r5 = r5 + r8
            androidx.transition.ViewUtilsApi29 r11 = androidx.transition.ViewUtils.IMPL
            r2.setLeftTopRightBottom(r7, r8, r4, r5)
            if (r7 != r9) goto L_0x013c
            if (r8 == r10) goto L_0x013a
            goto L_0x013c
        L_0x013a:
            r4 = 0
            goto L_0x014d
        L_0x013c:
            androidx.transition.PathMotion r4 = r0.mPathMotion
            float r5 = (float) r7
            float r7 = (float) r8
            float r8 = (float) r9
            float r11 = (float) r10
            android.graphics.Path r4 = r4.getPath(r5, r7, r8, r11)
            androidx.transition.ChangeBounds$6 r5 = POSITION_PROPERTY
            r7 = 0
            android.animation.ObjectAnimator r4 = android.animation.ObjectAnimator.ofObject(r2, r5, r7, r4)
        L_0x014d:
            if (r18 != 0) goto L_0x0156
            android.graphics.Rect r5 = new android.graphics.Rect
            r7 = 0
            r5.<init>(r7, r7, r6, r14)
            goto L_0x0159
        L_0x0156:
            r7 = 0
            r5 = r18
        L_0x0159:
            if (r17 != 0) goto L_0x0161
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>(r7, r7, r15, r3)
            goto L_0x0163
        L_0x0161:
            r6 = r17
        L_0x0163:
            boolean r3 = r5.equals(r6)
            if (r3 != 0) goto L_0x018c
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api18Impl.setClipBounds(r2, r5)
            androidx.transition.RectEvaluator r3 = sRectEvaluator
            java.lang.Object[] r8 = new java.lang.Object[r1]
            r8[r7] = r5
            r5 = 1
            r8[r5] = r6
            java.lang.String r5 = "clipBounds"
            android.animation.ObjectAnimator r3 = android.animation.ObjectAnimator.ofObject(r2, r5, r3, r8)
            androidx.transition.ChangeBounds$8 r5 = new androidx.transition.ChangeBounds$8
            r6 = r5
            r7 = r2
            r8 = r17
            r11 = r12
            r12 = r13
            r6.<init>(r7, r8, r9, r10, r11, r12)
            r3.addListener(r5)
            goto L_0x018d
        L_0x018c:
            r3 = 0
        L_0x018d:
            if (r4 != 0) goto L_0x0191
            r1 = r3
            goto L_0x0121
        L_0x0191:
            if (r3 != 0) goto L_0x0195
            r1 = r4
            goto L_0x0121
        L_0x0195:
            android.animation.AnimatorSet r5 = new android.animation.AnimatorSet
            r5.<init>()
            android.animation.Animator[] r1 = new android.animation.Animator[r1]
            r6 = 0
            r1[r6] = r4
            r4 = 1
            r1[r4] = r3
            r5.playTogether(r1)
            r1 = r5
        L_0x01a6:
            android.view.ViewParent r3 = r2.getParent()
            boolean r3 = r3 instanceof android.view.ViewGroup
            if (r3 == 0) goto L_0x01bf
            android.view.ViewParent r2 = r2.getParent()
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            r2.suppressLayout(r4)
            androidx.transition.ChangeBounds$9 r3 = new androidx.transition.ChangeBounds$9
            r3.<init>(r2)
            r0.addListener(r3)
        L_0x01bf:
            return r1
        L_0x01c0:
            r0 = 0
            return r0
        L_0x01c2:
            r0 = 0
            return r0
        L_0x01c4:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.ChangeBounds.createAnimator(android.view.ViewGroup, androidx.transition.TransitionValues, androidx.transition.TransitionValues):android.animation.Animator");
    }

    @SuppressLint({"RestrictedApi"})
    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempLocation = new int[2];
        boolean z = false;
        this.mResizeClip = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_BOUNDS);
        z = TypedArrayUtils.hasAttribute((XmlResourceParser) attributeSet, "resizeClip") ? obtainStyledAttributes.getBoolean(0, false) : z;
        obtainStyledAttributes.recycle();
        this.mResizeClip = z;
    }

    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }
}
