package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;

public abstract class Visibility extends Transition {
    public static final String[] sTransitionProperties = {"android:visibility:visibility", "android:visibility:parent"};
    public int mMode = 3;

    public static class DisappearListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        public boolean mCanceled = false;
        public final int mFinalVisibility;
        public boolean mLayoutSuppressed;
        public final ViewGroup mParent;
        public final boolean mSuppressLayout;
        public final View mView;

        public final void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
        }

        public final void onTransitionCancel() {
        }

        public final void onTransitionPause() {
            suppressLayout(false);
        }

        public final void onTransitionResume() {
            suppressLayout(true);
        }

        public final void onTransitionStart(Transition transition) {
        }

        public final void onAnimationEnd(Animator animator) {
            if (!this.mCanceled) {
                View view = this.mView;
                int i = this.mFinalVisibility;
                ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                view.setTransitionVisibility(i);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            suppressLayout(false);
        }

        public final void onAnimationPause(Animator animator) {
            if (!this.mCanceled) {
                View view = this.mView;
                int i = this.mFinalVisibility;
                ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                view.setTransitionVisibility(i);
            }
        }

        public final void onAnimationResume(Animator animator) {
            if (!this.mCanceled) {
                View view = this.mView;
                ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                view.setTransitionVisibility(0);
            }
        }

        public final void onTransitionEnd(Transition transition) {
            if (!this.mCanceled) {
                View view = this.mView;
                int i = this.mFinalVisibility;
                ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                view.setTransitionVisibility(i);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            suppressLayout(false);
            transition.removeListener(this);
        }

        public final void suppressLayout(boolean z) {
            ViewGroup viewGroup;
            if (this.mSuppressLayout && this.mLayoutSuppressed != z && (viewGroup = this.mParent) != null) {
                this.mLayoutSuppressed = z;
                viewGroup.suppressLayout(z);
            }
        }

        public DisappearListener(View view, int i) {
            this.mView = view;
            this.mFinalVisibility = i;
            this.mParent = (ViewGroup) view.getParent();
            this.mSuppressLayout = true;
            suppressLayout(true);
        }
    }

    public static class VisibilityInfo {
        public ViewGroup mEndParent;
        public int mEndVisibility;
        public boolean mFadeIn;
        public ViewGroup mStartParent;
        public int mStartVisibility;
        public boolean mVisibilityChange;
    }

    public Visibility() {
    }

    public final boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null && transitionValues2 == null) {
            return false;
        }
        if (transitionValues != null && transitionValues2 != null && transitionValues2.values.containsKey("android:visibility:visibility") != transitionValues.values.containsKey("android:visibility:visibility")) {
            return false;
        }
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.mVisibilityChange) {
            return visibilityChangeInfo.mStartVisibility == 0 || visibilityChangeInfo.mEndVisibility == 0;
        }
        return false;
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues) {
        return null;
    }

    public static VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.mVisibilityChange = false;
        visibilityInfo.mFadeIn = false;
        if (transitionValues == null || !transitionValues.values.containsKey("android:visibility:visibility")) {
            visibilityInfo.mStartVisibility = -1;
            visibilityInfo.mStartParent = null;
        } else {
            visibilityInfo.mStartVisibility = ((Integer) transitionValues.values.get("android:visibility:visibility")).intValue();
            visibilityInfo.mStartParent = (ViewGroup) transitionValues.values.get("android:visibility:parent");
        }
        if (transitionValues2 == null || !transitionValues2.values.containsKey("android:visibility:visibility")) {
            visibilityInfo.mEndVisibility = -1;
            visibilityInfo.mEndParent = null;
        } else {
            visibilityInfo.mEndVisibility = ((Integer) transitionValues2.values.get("android:visibility:visibility")).intValue();
            visibilityInfo.mEndParent = (ViewGroup) transitionValues2.values.get("android:visibility:parent");
        }
        if (transitionValues != null && transitionValues2 != null) {
            int i = visibilityInfo.mStartVisibility;
            int i2 = visibilityInfo.mEndVisibility;
            if (i == i2 && visibilityInfo.mStartParent == visibilityInfo.mEndParent) {
                return visibilityInfo;
            }
            if (i != i2) {
                if (i == 0) {
                    visibilityInfo.mFadeIn = false;
                    visibilityInfo.mVisibilityChange = true;
                } else if (i2 == 0) {
                    visibilityInfo.mFadeIn = true;
                    visibilityInfo.mVisibilityChange = true;
                }
            } else if (visibilityInfo.mEndParent == null) {
                visibilityInfo.mFadeIn = false;
                visibilityInfo.mVisibilityChange = true;
            } else if (visibilityInfo.mStartParent == null) {
                visibilityInfo.mFadeIn = true;
                visibilityInfo.mVisibilityChange = true;
            }
        } else if (transitionValues == null && visibilityInfo.mEndVisibility == 0) {
            visibilityInfo.mFadeIn = true;
            visibilityInfo.mVisibilityChange = true;
        } else if (transitionValues2 == null && visibilityInfo.mStartVisibility == 0) {
            visibilityInfo.mFadeIn = false;
            visibilityInfo.mVisibilityChange = true;
        }
        return visibilityInfo;
    }

    public final void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put("android:visibility:visibility", Integer.valueOf(transitionValues.view.getVisibility()));
        transitionValues.values.put("android:visibility:parent", transitionValues.view.getParent());
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:visibility:screenLocation", iArr);
    }

    /* JADX WARNING: type inference failed for: r16v4, types: [android.view.ViewParent] */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003e, code lost:
        if (getVisibilityChangeInfo(getMatchedTransitionValues(r4, false), getTransitionValues(r4, false)).mVisibilityChange != false) goto L_0x0048;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x019b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.animation.Animator createAnimator(android.view.ViewGroup r20, androidx.transition.TransitionValues r21, androidx.transition.TransitionValues r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r21
            r3 = r22
            androidx.transition.Visibility$VisibilityInfo r4 = getVisibilityChangeInfo(r21, r22)
            boolean r5 = r4.mVisibilityChange
            if (r5 == 0) goto L_0x0257
            android.view.ViewGroup r5 = r4.mStartParent
            if (r5 != 0) goto L_0x0018
            android.view.ViewGroup r5 = r4.mEndParent
            if (r5 == 0) goto L_0x0257
        L_0x0018:
            boolean r5 = r4.mFadeIn
            r7 = 1
            r8 = 0
            if (r5 == 0) goto L_0x004a
            int r4 = r0.mMode
            r4 = r4 & r7
            if (r4 != r7) goto L_0x0048
            if (r3 != 0) goto L_0x0026
            goto L_0x0048
        L_0x0026:
            if (r2 != 0) goto L_0x0041
            android.view.View r4 = r3.view
            android.view.ViewParent r4 = r4.getParent()
            android.view.View r4 = (android.view.View) r4
            androidx.transition.TransitionValues r5 = r0.getMatchedTransitionValues(r4, r8)
            androidx.transition.TransitionValues r4 = r0.getTransitionValues(r4, r8)
            androidx.transition.Visibility$VisibilityInfo r4 = getVisibilityChangeInfo(r5, r4)
            boolean r4 = r4.mVisibilityChange
            if (r4 == 0) goto L_0x0041
            goto L_0x0048
        L_0x0041:
            android.view.View r4 = r3.view
            android.animation.Animator r6 = r0.onAppear(r1, r4, r2, r3)
            goto L_0x0049
        L_0x0048:
            r6 = 0
        L_0x0049:
            return r6
        L_0x004a:
            int r4 = r4.mEndVisibility
            int r5 = r0.mMode
            r9 = 2
            r5 = r5 & r9
            if (r5 == r9) goto L_0x0054
            goto L_0x0255
        L_0x0054:
            if (r2 != 0) goto L_0x0058
            goto L_0x0255
        L_0x0058:
            android.view.View r5 = r2.view
            if (r3 == 0) goto L_0x005f
            android.view.View r3 = r3.view
            goto L_0x0060
        L_0x005f:
            r3 = 0
        L_0x0060:
            r10 = 2131428729(0x7f0b0579, float:1.847911E38)
            java.lang.Object r11 = r5.getTag(r10)
            android.view.View r11 = (android.view.View) r11
            if (r11 == 0) goto L_0x0070
            r18 = r4
            r3 = 0
            goto L_0x01d0
        L_0x0070:
            if (r3 == 0) goto L_0x0081
            android.view.ViewParent r11 = r3.getParent()
            if (r11 != 0) goto L_0x0079
            goto L_0x0081
        L_0x0079:
            r11 = 4
            if (r4 != r11) goto L_0x007d
            goto L_0x007f
        L_0x007d:
            if (r5 != r3) goto L_0x0087
        L_0x007f:
            r11 = r8
            goto L_0x0089
        L_0x0081:
            if (r3 == 0) goto L_0x0087
            r11 = r3
            r12 = r8
            r3 = 0
            goto L_0x008b
        L_0x0087:
            r11 = r7
            r3 = 0
        L_0x0089:
            r12 = r11
            r11 = 0
        L_0x008b:
            if (r12 == 0) goto L_0x01c9
            android.view.ViewParent r12 = r5.getParent()
            if (r12 != 0) goto L_0x0099
            r18 = r4
            r11 = r5
            r7 = r8
            goto L_0x01d0
        L_0x0099:
            android.view.ViewParent r12 = r5.getParent()
            boolean r12 = r12 instanceof android.view.View
            if (r12 == 0) goto L_0x01c9
            android.view.ViewParent r12 = r5.getParent()
            android.view.View r12 = (android.view.View) r12
            androidx.transition.TransitionValues r13 = r0.getTransitionValues(r12, r7)
            androidx.transition.TransitionValues r14 = r0.getMatchedTransitionValues(r12, r7)
            androidx.transition.Visibility$VisibilityInfo r13 = getVisibilityChangeInfo(r13, r14)
            boolean r13 = r13.mVisibilityChange
            if (r13 != 0) goto L_0x01b4
            android.graphics.Matrix r11 = new android.graphics.Matrix
            r11.<init>()
            int r13 = r12.getScrollX()
            int r13 = -r13
            float r13 = (float) r13
            int r12 = r12.getScrollY()
            int r12 = -r12
            float r12 = (float) r12
            r11.setTranslate(r13, r12)
            androidx.transition.ViewUtilsApi29 r12 = androidx.transition.ViewUtils.IMPL
            r5.transformMatrixToGlobal(r11)
            r1.transformMatrixToLocal(r11)
            android.graphics.RectF r12 = new android.graphics.RectF
            int r13 = r5.getWidth()
            float r13 = (float) r13
            int r14 = r5.getHeight()
            float r14 = (float) r14
            r15 = 0
            r12.<init>(r15, r15, r13, r14)
            r11.mapRect(r12)
            float r13 = r12.left
            int r13 = java.lang.Math.round(r13)
            float r14 = r12.top
            int r14 = java.lang.Math.round(r14)
            float r15 = r12.right
            int r15 = java.lang.Math.round(r15)
            float r6 = r12.bottom
            int r6 = java.lang.Math.round(r6)
            android.widget.ImageView r10 = new android.widget.ImageView
            android.content.Context r9 = r5.getContext()
            r10.<init>(r9)
            android.widget.ImageView$ScaleType r9 = android.widget.ImageView.ScaleType.CENTER_CROP
            r10.setScaleType(r9)
            boolean r9 = r5.isAttachedToWindow()
            r9 = r9 ^ r7
            boolean r16 = r20.isAttachedToWindow()
            if (r9 == 0) goto L_0x0136
            if (r16 != 0) goto L_0x0120
            r17 = r3
            r18 = r4
            r0 = 0
            goto L_0x0199
        L_0x0120:
            android.view.ViewParent r16 = r5.getParent()
            r7 = r16
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            int r16 = r7.indexOfChild(r5)
            android.view.ViewGroupOverlay r8 = r20.getOverlay()
            r8.add(r5)
            r8 = r16
            goto L_0x0138
        L_0x0136:
            r7 = 0
            r8 = 0
        L_0x0138:
            float r16 = r12.width()
            r17 = r3
            int r3 = java.lang.Math.round(r16)
            float r16 = r12.height()
            r18 = r4
            int r4 = java.lang.Math.round(r16)
            if (r3 <= 0) goto L_0x018c
            if (r4 <= 0) goto L_0x018c
            r16 = 1233125376(0x49800000, float:1048576.0)
            int r0 = r3 * r4
            float r0 = (float) r0
            float r0 = r16 / r0
            r2 = 1065353216(0x3f800000, float:1.0)
            float r0 = java.lang.Math.min(r2, r0)
            float r2 = (float) r3
            float r2 = r2 * r0
            int r2 = java.lang.Math.round(r2)
            float r3 = (float) r4
            float r3 = r3 * r0
            int r3 = java.lang.Math.round(r3)
            float r4 = r12.left
            float r4 = -r4
            float r12 = r12.top
            float r12 = -r12
            r11.postTranslate(r4, r12)
            r11.postScale(r0, r0)
            android.graphics.Picture r0 = new android.graphics.Picture
            r0.<init>()
            android.graphics.Canvas r2 = r0.beginRecording(r2, r3)
            r2.concat(r11)
            r5.draw(r2)
            r0.endRecording()
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r0)
            goto L_0x018d
        L_0x018c:
            r0 = 0
        L_0x018d:
            if (r9 == 0) goto L_0x0199
            android.view.ViewGroupOverlay r2 = r20.getOverlay()
            r2.remove(r5)
            r7.addView(r5, r8)
        L_0x0199:
            if (r0 == 0) goto L_0x019e
            r10.setImageBitmap(r0)
        L_0x019e:
            int r0 = r15 - r13
            r2 = 1073741824(0x40000000, float:2.0)
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r2)
            int r3 = r6 - r14
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r2)
            r10.measure(r0, r2)
            r10.layout(r13, r14, r15, r6)
            r11 = r10
            goto L_0x01cd
        L_0x01b4:
            r17 = r3
            r18 = r4
            int r0 = r12.getId()
            android.view.ViewParent r2 = r12.getParent()
            if (r2 != 0) goto L_0x01cd
            r2 = -1
            if (r0 == r2) goto L_0x01cd
            r1.findViewById(r0)
            goto L_0x01cd
        L_0x01c9:
            r17 = r3
            r18 = r4
        L_0x01cd:
            r3 = r17
            r7 = 0
        L_0x01d0:
            if (r11 == 0) goto L_0x022a
            r0 = r21
            if (r7 != 0) goto L_0x0209
            java.util.HashMap r2 = r0.values
            java.lang.String r3 = "android:visibility:screenLocation"
            java.lang.Object r2 = r2.get(r3)
            int[] r2 = (int[]) r2
            r3 = 0
            r4 = r2[r3]
            r6 = 1
            r2 = r2[r6]
            r8 = 2
            int[] r8 = new int[r8]
            r1.getLocationOnScreen(r8)
            r3 = r8[r3]
            int r4 = r4 - r3
            int r3 = r11.getLeft()
            int r4 = r4 - r3
            r11.offsetLeftAndRight(r4)
            r3 = r8[r6]
            int r2 = r2 - r3
            int r3 = r11.getTop()
            int r2 = r2 - r3
            r11.offsetTopAndBottom(r2)
            android.view.ViewGroupOverlay r2 = r20.getOverlay()
            r2.add(r11)
        L_0x0209:
            r2 = r19
            android.animation.Animator r6 = r2.onDisappear(r1, r11, r0)
            if (r7 != 0) goto L_0x0256
            if (r6 != 0) goto L_0x021b
            android.view.ViewGroupOverlay r0 = r20.getOverlay()
            r0.remove(r11)
            goto L_0x0256
        L_0x021b:
            r0 = 2131428729(0x7f0b0579, float:1.847911E38)
            r5.setTag(r0, r11)
            androidx.transition.Visibility$1 r0 = new androidx.transition.Visibility$1
            r0.<init>(r1, r11, r5)
            r2.addListener(r0)
            goto L_0x0256
        L_0x022a:
            r2 = r19
            r0 = r21
            if (r3 == 0) goto L_0x0255
            int r4 = r3.getVisibility()
            androidx.transition.ViewUtilsApi29 r5 = androidx.transition.ViewUtils.IMPL
            r5 = 0
            r3.setTransitionVisibility(r5)
            android.animation.Animator r6 = r2.onDisappear(r1, r3, r0)
            if (r6 == 0) goto L_0x0251
            androidx.transition.Visibility$DisappearListener r0 = new androidx.transition.Visibility$DisappearListener
            r1 = r18
            r0.<init>(r3, r1)
            r6.addListener(r0)
            r6.addPauseListener(r0)
            r2.addListener(r0)
            goto L_0x0256
        L_0x0251:
            r3.setTransitionVisibility(r4)
            goto L_0x0256
        L_0x0255:
            r6 = 0
        L_0x0256:
            return r6
        L_0x0257:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.Visibility.createAnimator(android.view.ViewGroup, androidx.transition.TransitionValues, androidx.transition.TransitionValues):android.animation.Animator");
    }

    @SuppressLint({"RestrictedApi"})
    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.VISIBILITY_TRANSITION);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionVisibilityMode", 0, 0);
        obtainStyledAttributes.recycle();
        if (namedInt == 0) {
            return;
        }
        if ((namedInt & -4) == 0) {
            this.mMode = namedInt;
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }
}
