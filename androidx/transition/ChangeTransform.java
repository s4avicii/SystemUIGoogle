package androidx.transition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

public class ChangeTransform extends Transition {
    public static final C03941 NON_TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, float[]>() {
        {
            Class<float[]> cls = float[].class;
        }

        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            PathAnimatorMatrix pathAnimatorMatrix = (PathAnimatorMatrix) obj;
            return null;
        }

        public final void set(Object obj, Object obj2) {
            PathAnimatorMatrix pathAnimatorMatrix = (PathAnimatorMatrix) obj;
            float[] fArr = (float[]) obj2;
            Objects.requireNonNull(pathAnimatorMatrix);
            System.arraycopy(fArr, 0, pathAnimatorMatrix.mValues, 0, fArr.length);
            pathAnimatorMatrix.setAnimationMatrix();
        }
    };
    public static final boolean SUPPORTS_VIEW_REMOVAL_SUPPRESSION = true;
    public static final C03952 TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, PointF>(PointF.class) {
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            PathAnimatorMatrix pathAnimatorMatrix = (PathAnimatorMatrix) obj;
            return null;
        }

        public final void set(Object obj, Object obj2) {
            PathAnimatorMatrix pathAnimatorMatrix = (PathAnimatorMatrix) obj;
            PointF pointF = (PointF) obj2;
            Objects.requireNonNull(pathAnimatorMatrix);
            pathAnimatorMatrix.mTranslationX = pointF.x;
            pathAnimatorMatrix.mTranslationY = pointF.y;
            pathAnimatorMatrix.setAnimationMatrix();
        }
    };
    public static final String[] sTransitionProperties = {"android:changeTransform:matrix", "android:changeTransform:transforms", "android:changeTransform:parentMatrix"};
    public boolean mReparent = true;
    public Matrix mTempMatrix = new Matrix();
    public boolean mUseOverlay = true;

    public static class GhostListener extends TransitionListenerAdapter {
        public GhostView mGhostView;
        public View mView;

        public final void onTransitionPause() {
            this.mGhostView.setVisibility(4);
        }

        public final void onTransitionResume() {
            this.mGhostView.setVisibility(0);
        }

        public GhostListener(View view, GhostViewPort ghostViewPort) {
            this.mView = view;
            this.mGhostView = ghostViewPort;
        }

        public final void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
            View view = this.mView;
            int i = GhostViewPort.$r8$clinit;
            GhostViewPort ghostViewPort = (GhostViewPort) view.getTag(C1777R.C1779id.ghost_view);
            if (ghostViewPort != null) {
                int i2 = ghostViewPort.mReferences - 1;
                ghostViewPort.mReferences = i2;
                if (i2 <= 0) {
                    ((GhostViewHolder) ghostViewPort.getParent()).removeView(ghostViewPort);
                }
            }
            this.mView.setTag(C1777R.C1779id.transition_transform, (Object) null);
            this.mView.setTag(C1777R.C1779id.parent_matrix, (Object) null);
        }
    }

    public static class PathAnimatorMatrix {
        public final Matrix mMatrix = new Matrix();
        public float mTranslationX;
        public float mTranslationY;
        public final float[] mValues;
        public final View mView;

        public final void setAnimationMatrix() {
            float[] fArr = this.mValues;
            fArr[2] = this.mTranslationX;
            fArr[5] = this.mTranslationY;
            this.mMatrix.setValues(fArr);
            View view = this.mView;
            Matrix matrix = this.mMatrix;
            ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
            view.setAnimationMatrix(matrix);
        }

        public PathAnimatorMatrix(View view, float[] fArr) {
            this.mView = view;
            float[] fArr2 = (float[]) fArr.clone();
            this.mValues = fArr2;
            this.mTranslationX = fArr2[2];
            this.mTranslationY = fArr2[5];
            setAnimationMatrix();
        }
    }

    public static class Transforms {
        public final float mRotationX;
        public final float mRotationY;
        public final float mRotationZ;
        public final float mScaleX;
        public final float mScaleY;
        public final float mTranslationX;
        public final float mTranslationY;
        public final float mTranslationZ;

        public final boolean equals(Object obj) {
            if (!(obj instanceof Transforms)) {
                return false;
            }
            Transforms transforms = (Transforms) obj;
            if (transforms.mTranslationX == this.mTranslationX && transforms.mTranslationY == this.mTranslationY && transforms.mTranslationZ == this.mTranslationZ && transforms.mScaleX == this.mScaleX && transforms.mScaleY == this.mScaleY && transforms.mRotationX == this.mRotationX && transforms.mRotationY == this.mRotationY && transforms.mRotationZ == this.mRotationZ) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            float f = this.mTranslationX;
            int i8 = 0;
            if (f != 0.0f) {
                i = Float.floatToIntBits(f);
            } else {
                i = 0;
            }
            int i9 = i * 31;
            float f2 = this.mTranslationY;
            if (f2 != 0.0f) {
                i2 = Float.floatToIntBits(f2);
            } else {
                i2 = 0;
            }
            int i10 = (i9 + i2) * 31;
            float f3 = this.mTranslationZ;
            if (f3 != 0.0f) {
                i3 = Float.floatToIntBits(f3);
            } else {
                i3 = 0;
            }
            int i11 = (i10 + i3) * 31;
            float f4 = this.mScaleX;
            if (f4 != 0.0f) {
                i4 = Float.floatToIntBits(f4);
            } else {
                i4 = 0;
            }
            int i12 = (i11 + i4) * 31;
            float f5 = this.mScaleY;
            if (f5 != 0.0f) {
                i5 = Float.floatToIntBits(f5);
            } else {
                i5 = 0;
            }
            int i13 = (i12 + i5) * 31;
            float f6 = this.mRotationX;
            if (f6 != 0.0f) {
                i6 = Float.floatToIntBits(f6);
            } else {
                i6 = 0;
            }
            int i14 = (i13 + i6) * 31;
            float f7 = this.mRotationY;
            if (f7 != 0.0f) {
                i7 = Float.floatToIntBits(f7);
            } else {
                i7 = 0;
            }
            int i15 = (i14 + i7) * 31;
            float f8 = this.mRotationZ;
            if (f8 != 0.0f) {
                i8 = Float.floatToIntBits(f8);
            }
            return i15 + i8;
        }

        public Transforms(View view) {
            this.mTranslationX = view.getTranslationX();
            this.mTranslationY = view.getTranslationY();
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.mTranslationZ = ViewCompat.Api21Impl.getTranslationZ(view);
            this.mScaleX = view.getScaleX();
            this.mScaleY = view.getScaleY();
            this.mRotationX = view.getRotationX();
            this.mRotationY = view.getRotationY();
            this.mRotationZ = view.getRotation();
        }
    }

    public ChangeTransform() {
    }

    public final void captureValues(TransitionValues transitionValues) {
        Matrix matrix;
        View view = transitionValues.view;
        if (view.getVisibility() != 8) {
            transitionValues.values.put("android:changeTransform:parent", view.getParent());
            transitionValues.values.put("android:changeTransform:transforms", new Transforms(view));
            Matrix matrix2 = view.getMatrix();
            if (matrix2 == null || matrix2.isIdentity()) {
                matrix = null;
            } else {
                matrix = new Matrix(matrix2);
            }
            transitionValues.values.put("android:changeTransform:matrix", matrix);
            if (this.mReparent) {
                Matrix matrix3 = new Matrix();
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                ViewUtilsApi29 viewUtilsApi29 = ViewUtils.IMPL;
                viewGroup.transformMatrixToGlobal(matrix3);
                matrix3.preTranslate((float) (-viewGroup.getScrollX()), (float) (-viewGroup.getScrollY()));
                transitionValues.values.put("android:changeTransform:parentMatrix", matrix3);
                transitionValues.values.put("android:changeTransform:intermediateMatrix", view.getTag(C1777R.C1779id.transition_transform));
                transitionValues.values.put("android:changeTransform:intermediateParentMatrix", view.getTag(C1777R.C1779id.parent_matrix));
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v18, resolved type: android.view.View} */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x027c, code lost:
        if (r14.getZ() > r2.getZ()) goto L_0x02ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x02a5, code lost:
        if (r3.size() == r4) goto L_0x02ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x02a8, code lost:
        r2 = false;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0324  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00e6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.animation.Animator createAnimator(android.view.ViewGroup r26, androidx.transition.TransitionValues r27, androidx.transition.TransitionValues r28) {
        /*
            r25 = this;
            r7 = r25
            r8 = r26
            r9 = r27
            r10 = r28
            if (r9 == 0) goto L_0x032c
            if (r10 == 0) goto L_0x032c
            java.util.HashMap r0 = r9.values
            java.lang.String r12 = "android:changeTransform:parent"
            boolean r0 = r0.containsKey(r12)
            if (r0 == 0) goto L_0x032c
            java.util.HashMap r0 = r10.values
            boolean r0 = r0.containsKey(r12)
            if (r0 != 0) goto L_0x0020
            goto L_0x032c
        L_0x0020:
            java.util.HashMap r0 = r9.values
            java.lang.Object r0 = r0.get(r12)
            r13 = r0
            android.view.ViewGroup r13 = (android.view.ViewGroup) r13
            java.util.HashMap r0 = r10.values
            java.lang.Object r0 = r0.get(r12)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            boolean r1 = r7.mReparent
            r15 = 1
            if (r1 == 0) goto L_0x0058
            boolean r1 = r7.isValidTarget(r13)
            if (r1 == 0) goto L_0x004e
            boolean r1 = r7.isValidTarget(r0)
            if (r1 != 0) goto L_0x0043
            goto L_0x004e
        L_0x0043:
            androidx.transition.TransitionValues r1 = r7.getMatchedTransitionValues(r13, r15)
            if (r1 == 0) goto L_0x0052
            android.view.View r1 = r1.view
            if (r0 != r1) goto L_0x0052
            goto L_0x0050
        L_0x004e:
            if (r13 != r0) goto L_0x0052
        L_0x0050:
            r0 = r15
            goto L_0x0053
        L_0x0052:
            r0 = 0
        L_0x0053:
            if (r0 != 0) goto L_0x0058
            r16 = r15
            goto L_0x005a
        L_0x0058:
            r16 = 0
        L_0x005a:
            java.util.HashMap r0 = r9.values
            java.lang.String r1 = "android:changeTransform:intermediateMatrix"
            java.lang.Object r0 = r0.get(r1)
            android.graphics.Matrix r0 = (android.graphics.Matrix) r0
            java.lang.String r1 = "android:changeTransform:matrix"
            if (r0 == 0) goto L_0x006d
            java.util.HashMap r2 = r9.values
            r2.put(r1, r0)
        L_0x006d:
            java.util.HashMap r0 = r9.values
            java.lang.String r2 = "android:changeTransform:intermediateParentMatrix"
            java.lang.Object r0 = r0.get(r2)
            android.graphics.Matrix r0 = (android.graphics.Matrix) r0
            java.lang.String r6 = "android:changeTransform:parentMatrix"
            if (r0 == 0) goto L_0x0080
            java.util.HashMap r2 = r9.values
            r2.put(r6, r0)
        L_0x0080:
            if (r16 == 0) goto L_0x00bc
            java.util.HashMap r0 = r10.values
            java.lang.Object r0 = r0.get(r6)
            android.graphics.Matrix r0 = (android.graphics.Matrix) r0
            android.view.View r2 = r10.view
            r3 = 2131428565(0x7f0b04d5, float:1.8478778E38)
            r2.setTag(r3, r0)
            android.graphics.Matrix r2 = r7.mTempMatrix
            r2.reset()
            r0.invert(r2)
            java.util.HashMap r0 = r9.values
            java.lang.Object r0 = r0.get(r1)
            android.graphics.Matrix r0 = (android.graphics.Matrix) r0
            if (r0 != 0) goto L_0x00ae
            android.graphics.Matrix r0 = new android.graphics.Matrix
            r0.<init>()
            java.util.HashMap r3 = r9.values
            r3.put(r1, r0)
        L_0x00ae:
            java.util.HashMap r3 = r9.values
            java.lang.Object r3 = r3.get(r6)
            android.graphics.Matrix r3 = (android.graphics.Matrix) r3
            r0.postConcat(r3)
            r0.postConcat(r2)
        L_0x00bc:
            java.util.HashMap r0 = r9.values
            java.lang.Object r0 = r0.get(r1)
            android.graphics.Matrix r0 = (android.graphics.Matrix) r0
            java.util.HashMap r2 = r10.values
            java.lang.Object r1 = r2.get(r1)
            android.graphics.Matrix r1 = (android.graphics.Matrix) r1
            if (r0 != 0) goto L_0x00d0
            androidx.transition.MatrixUtils$1 r0 = androidx.transition.MatrixUtils.IDENTITY_MATRIX
        L_0x00d0:
            if (r1 != 0) goto L_0x00d4
            androidx.transition.MatrixUtils$1 r1 = androidx.transition.MatrixUtils.IDENTITY_MATRIX
        L_0x00d4:
            r3 = r1
            boolean r1 = r0.equals(r3)
            r5 = 1065353216(0x3f800000, float:1.0)
            r4 = 0
            if (r1 == 0) goto L_0x00e6
            r11 = r6
            r23 = r13
            r5 = 0
            r22 = 2
            goto L_0x017b
        L_0x00e6:
            java.util.HashMap r1 = r10.values
            java.lang.String r11 = "android:changeTransform:transforms"
            java.lang.Object r1 = r1.get(r11)
            r11 = r1
            androidx.transition.ChangeTransform$Transforms r11 = (androidx.transition.ChangeTransform.Transforms) r11
            android.view.View r1 = r10.view
            r1.setTranslationX(r4)
            r1.setTranslationY(r4)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r18 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api21Impl.setTranslationZ(r1, r4)
            r1.setScaleX(r5)
            r1.setScaleY(r5)
            r1.setRotationX(r4)
            r1.setRotationY(r4)
            r1.setRotation(r4)
            r4 = 9
            float[] r5 = new float[r4]
            r0.getValues(r5)
            float[] r0 = new float[r4]
            r3.getValues(r0)
            androidx.transition.ChangeTransform$PathAnimatorMatrix r15 = new androidx.transition.ChangeTransform$PathAnimatorMatrix
            r15.<init>(r1, r5)
            androidx.transition.ChangeTransform$1 r14 = NON_TRANSLATIONS_PROPERTY
            androidx.transition.FloatArrayEvaluator r2 = new androidx.transition.FloatArrayEvaluator
            float[] r4 = new float[r4]
            r2.<init>(r4)
            r22 = r1
            r4 = 2
            float[][] r1 = new float[r4][]
            r21 = 0
            r1[r21] = r5
            r20 = 1
            r1[r20] = r0
            android.animation.PropertyValuesHolder r1 = android.animation.PropertyValuesHolder.ofObject(r14, r2, r1)
            androidx.transition.PathMotion r2 = r7.mPathMotion
            r14 = r5[r4]
            r23 = 5
            r5 = r5[r23]
            r24 = r6
            r6 = r0[r4]
            r0 = r0[r23]
            android.graphics.Path r0 = r2.getPath(r14, r5, r6, r0)
            androidx.transition.ChangeTransform$2 r2 = TRANSLATIONS_PROPERTY
            r5 = 0
            android.animation.PropertyValuesHolder r0 = android.animation.PropertyValuesHolder.ofObject(r2, r5, r0)
            android.animation.PropertyValuesHolder[] r2 = new android.animation.PropertyValuesHolder[r4]
            r5 = 0
            r2[r5] = r1
            r1 = 1
            r2[r1] = r0
            android.animation.ObjectAnimator r14 = android.animation.ObjectAnimator.ofPropertyValuesHolder(r15, r2)
            androidx.transition.ChangeTransform$3 r6 = new androidx.transition.ChangeTransform$3
            r0 = r6
            r5 = r22
            r1 = r25
            r22 = r4
            r2 = r16
            r23 = r13
            r13 = 0
            r4 = r5
            r5 = r11
            r13 = r6
            r11 = r24
            r6 = r15
            r0.<init>(r2, r3, r4, r5, r6)
            r14.addListener(r13)
            r14.addPauseListener(r13)
            r5 = r14
        L_0x017b:
            if (r16 == 0) goto L_0x0320
            if (r5 == 0) goto L_0x0320
            boolean r0 = r7.mUseOverlay
            if (r0 == 0) goto L_0x0320
            android.view.View r0 = r10.view
            java.util.HashMap r1 = r10.values
            java.lang.Object r1 = r1.get(r11)
            android.graphics.Matrix r1 = (android.graphics.Matrix) r1
            android.graphics.Matrix r2 = new android.graphics.Matrix
            r2.<init>(r1)
            androidx.transition.ViewUtilsApi29 r1 = androidx.transition.ViewUtils.IMPL
            r8.transformMatrixToLocal(r2)
            int r1 = androidx.transition.GhostViewPort.$r8$clinit
            android.view.ViewParent r1 = r0.getParent()
            boolean r1 = r1 instanceof android.view.ViewGroup
            if (r1 == 0) goto L_0x0318
            int r1 = androidx.transition.GhostViewHolder.$r8$clinit
            r1 = 2131428021(0x7f0b02b5, float:1.8477675E38)
            java.lang.Object r1 = r8.getTag(r1)
            androidx.transition.GhostViewHolder r1 = (androidx.transition.GhostViewHolder) r1
            r3 = 2131428020(0x7f0b02b4, float:1.8477673E38)
            java.lang.Object r3 = r0.getTag(r3)
            androidx.transition.GhostViewPort r3 = (androidx.transition.GhostViewPort) r3
            if (r3 == 0) goto L_0x01c6
            android.view.ViewParent r4 = r3.getParent()
            androidx.transition.GhostViewHolder r4 = (androidx.transition.GhostViewHolder) r4
            if (r4 == r1) goto L_0x01c6
            int r6 = r3.mReferences
            r4.removeView(r3)
            r11 = 0
            goto L_0x01c8
        L_0x01c6:
            r11 = r3
            r6 = 0
        L_0x01c8:
            if (r11 != 0) goto L_0x02de
            androidx.transition.GhostViewPort r11 = new androidx.transition.GhostViewPort
            r11.<init>(r0)
            r11.mMatrix = r2
            if (r1 != 0) goto L_0x01d9
            androidx.transition.GhostViewHolder r1 = new androidx.transition.GhostViewHolder
            r1.<init>(r8)
            goto L_0x01ef
        L_0x01d9:
            boolean r2 = r1.mAttached
            if (r2 == 0) goto L_0x02d6
            android.view.ViewGroup r2 = r1.mParent
            android.view.ViewGroupOverlay r2 = r2.getOverlay()
            r2.remove(r1)
            android.view.ViewGroup r2 = r1.mParent
            android.view.ViewGroupOverlay r2 = r2.getOverlay()
            r2.add(r1)
        L_0x01ef:
            androidx.transition.GhostViewPort.copySize(r8, r1)
            androidx.transition.GhostViewPort.copySize(r8, r11)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            android.view.View r3 = r11.mView
            androidx.transition.GhostViewHolder.getParents(r3, r2)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            int r4 = r1.getChildCount()
            r8 = 1
            int r4 = r4 - r8
            r8 = 0
        L_0x020b:
            if (r8 > r4) goto L_0x02c3
            int r13 = r8 + r4
            int r13 = r13 / 2
            android.view.View r14 = r1.getChildAt(r13)
            androidx.transition.GhostViewPort r14 = (androidx.transition.GhostViewPort) r14
            android.view.View r14 = r14.mView
            androidx.transition.GhostViewHolder.getParents(r14, r3)
            boolean r14 = r2.isEmpty()
            if (r14 != 0) goto L_0x02aa
            boolean r14 = r3.isEmpty()
            if (r14 != 0) goto L_0x02aa
            r14 = 0
            java.lang.Object r15 = r2.get(r14)
            r16 = r4
            java.lang.Object r4 = r3.get(r14)
            if (r15 == r4) goto L_0x0239
            r26 = r2
            goto L_0x02ae
        L_0x0239:
            int r4 = r2.size()
            int r15 = r3.size()
            int r4 = java.lang.Math.min(r4, r15)
            r15 = 1
        L_0x0246:
            if (r15 >= r4) goto L_0x029f
            java.lang.Object r17 = r2.get(r15)
            r14 = r17
            android.view.View r14 = (android.view.View) r14
            java.lang.Object r17 = r3.get(r15)
            r26 = r2
            r2 = r17
            android.view.View r2 = (android.view.View) r2
            if (r14 == r2) goto L_0x0297
            android.view.ViewParent r4 = r14.getParent()
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            int r15 = r4.getChildCount()
            float r17 = r14.getZ()
            float r19 = r2.getZ()
            int r17 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1))
            if (r17 == 0) goto L_0x027f
            float r4 = r14.getZ()
            float r2 = r2.getZ()
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x02a8
            goto L_0x02ae
        L_0x027f:
            r7 = 0
        L_0x0280:
            if (r7 >= r15) goto L_0x02ae
            r17 = r15
            int r15 = r4.getChildDrawingOrder(r7)
            android.view.View r15 = r4.getChildAt(r15)
            if (r15 != r14) goto L_0x028f
            goto L_0x02a8
        L_0x028f:
            if (r15 != r2) goto L_0x0292
            goto L_0x02ae
        L_0x0292:
            int r7 = r7 + 1
            r15 = r17
            goto L_0x0280
        L_0x0297:
            int r15 = r15 + 1
            r14 = 0
            r7 = r25
            r2 = r26
            goto L_0x0246
        L_0x029f:
            r26 = r2
            int r2 = r3.size()
            if (r2 != r4) goto L_0x02a8
            goto L_0x02ae
        L_0x02a8:
            r2 = 0
            goto L_0x02af
        L_0x02aa:
            r26 = r2
            r16 = r4
        L_0x02ae:
            r2 = 1
        L_0x02af:
            if (r2 == 0) goto L_0x02b7
            int r13 = r13 + 1
            r8 = r13
            r4 = r16
            goto L_0x02ba
        L_0x02b7:
            int r13 = r13 + -1
            r4 = r13
        L_0x02ba:
            r3.clear()
            r7 = r25
            r2 = r26
            goto L_0x020b
        L_0x02c3:
            if (r8 < 0) goto L_0x02d0
            int r2 = r1.getChildCount()
            if (r8 < r2) goto L_0x02cc
            goto L_0x02d0
        L_0x02cc:
            r1.addView(r11, r8)
            goto L_0x02d3
        L_0x02d0:
            r1.addView(r11)
        L_0x02d3:
            r11.mReferences = r6
            goto L_0x02e0
        L_0x02d6:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "This GhostViewHolder is detached!"
            r0.<init>(r1)
            throw r0
        L_0x02de:
            r11.mMatrix = r2
        L_0x02e0:
            int r1 = r11.mReferences
            r2 = 1
            int r1 = r1 + r2
            r11.mReferences = r1
            java.util.HashMap r1 = r9.values
            java.lang.Object r1 = r1.get(r12)
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            android.view.View r2 = r9.view
            r11.mStartParent = r1
            r11.mStartView = r2
            r1 = r25
        L_0x02f6:
            androidx.transition.TransitionSet r2 = r1.mParent
            if (r2 == 0) goto L_0x02fc
            r1 = r2
            goto L_0x02f6
        L_0x02fc:
            androidx.transition.ChangeTransform$GhostListener r2 = new androidx.transition.ChangeTransform$GhostListener
            r2.<init>(r0, r11)
            r1.addListener(r2)
            boolean r1 = SUPPORTS_VIEW_REMOVAL_SUPPRESSION
            if (r1 == 0) goto L_0x032b
            android.view.View r1 = r9.view
            android.view.View r2 = r10.view
            if (r1 == r2) goto L_0x0312
            r2 = 0
            r1.setTransitionAlpha(r2)
        L_0x0312:
            r1 = 1065353216(0x3f800000, float:1.0)
            r0.setTransitionAlpha(r1)
            goto L_0x032b
        L_0x0318:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Ghosted views must be parented by a ViewGroup"
            r0.<init>(r1)
            throw r0
        L_0x0320:
            boolean r0 = SUPPORTS_VIEW_REMOVAL_SUPPRESSION
            if (r0 != 0) goto L_0x032b
            android.view.View r0 = r9.view
            r1 = r23
            r1.endViewTransition(r0)
        L_0x032b:
            return r5
        L_0x032c:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.ChangeTransform.createAnimator(android.view.ViewGroup, androidx.transition.TransitionValues, androidx.transition.TransitionValues):android.animation.Animator");
    }

    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
            ((ViewGroup) transitionValues.view.getParent()).startViewTransition(transitionValues.view);
        }
    }

    @SuppressLint({"RestrictedApi"})
    public ChangeTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        boolean z;
        boolean z2 = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_TRANSFORM);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, "reparentWithOverlay")) {
            z = true;
        } else {
            z = obtainStyledAttributes.getBoolean(1, true);
        }
        this.mUseOverlay = z;
        this.mReparent = TypedArrayUtils.hasAttribute(xmlPullParser, "reparent") ? obtainStyledAttributes.getBoolean(0, true) : z2;
        obtainStyledAttributes.recycle();
    }

    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }
}
