package androidx.transition;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import java.util.WeakHashMap;

public class ChangeClipBounds extends Transition {
    public static final String[] sTransitionProperties = {"android:clipBounds:clip"};

    public ChangeClipBounds() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: android.graphics.Rect} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.animation.Animator createAnimator(android.view.ViewGroup r7, androidx.transition.TransitionValues r8, androidx.transition.TransitionValues r9) {
        /*
            r6 = this;
            r6 = 0
            if (r8 == 0) goto L_0x007f
            if (r9 == 0) goto L_0x007f
            java.util.HashMap r7 = r8.values
            java.lang.String r0 = "android:clipBounds:clip"
            boolean r7 = r7.containsKey(r0)
            if (r7 == 0) goto L_0x007f
            java.util.HashMap r7 = r9.values
            boolean r7 = r7.containsKey(r0)
            if (r7 != 0) goto L_0x0018
            goto L_0x007f
        L_0x0018:
            java.util.HashMap r7 = r8.values
            java.lang.Object r7 = r7.get(r0)
            android.graphics.Rect r7 = (android.graphics.Rect) r7
            java.util.HashMap r1 = r9.values
            java.lang.Object r0 = r1.get(r0)
            android.graphics.Rect r0 = (android.graphics.Rect) r0
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x002e
            r3 = r1
            goto L_0x002f
        L_0x002e:
            r3 = r2
        L_0x002f:
            if (r7 != 0) goto L_0x0034
            if (r0 != 0) goto L_0x0034
            return r6
        L_0x0034:
            java.lang.String r4 = "android:clipBounds:bounds"
            if (r7 != 0) goto L_0x0041
            java.util.HashMap r7 = r8.values
            java.lang.Object r7 = r7.get(r4)
            android.graphics.Rect r7 = (android.graphics.Rect) r7
            goto L_0x004c
        L_0x0041:
            if (r0 != 0) goto L_0x004c
            java.util.HashMap r8 = r9.values
            java.lang.Object r8 = r8.get(r4)
            r0 = r8
            android.graphics.Rect r0 = (android.graphics.Rect) r0
        L_0x004c:
            boolean r8 = r7.equals(r0)
            if (r8 == 0) goto L_0x0053
            return r6
        L_0x0053:
            android.view.View r6 = r9.view
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r8 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api18Impl.setClipBounds(r6, r7)
            androidx.transition.RectEvaluator r6 = new androidx.transition.RectEvaluator
            android.graphics.Rect r8 = new android.graphics.Rect
            r8.<init>()
            r6.<init>(r8)
            android.view.View r8 = r9.view
            androidx.transition.ViewUtils$2 r4 = androidx.transition.ViewUtils.CLIP_BOUNDS
            r5 = 2
            android.graphics.Rect[] r5 = new android.graphics.Rect[r5]
            r5[r2] = r7
            r5[r1] = r0
            android.animation.ObjectAnimator r6 = android.animation.ObjectAnimator.ofObject(r8, r4, r6, r5)
            if (r3 == 0) goto L_0x007f
            android.view.View r7 = r9.view
            androidx.transition.ChangeClipBounds$1 r8 = new androidx.transition.ChangeClipBounds$1
            r8.<init>(r7)
            r6.addListener(r8)
        L_0x007f:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.ChangeClipBounds.createAnimator(android.view.ViewGroup, androidx.transition.TransitionValues, androidx.transition.TransitionValues):android.animation.Animator");
    }

    public ChangeClipBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() != 8) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            Rect clipBounds = ViewCompat.Api18Impl.getClipBounds(view);
            transitionValues.values.put("android:clipBounds:clip", clipBounds);
            if (clipBounds == null) {
                transitionValues.values.put("android:clipBounds:bounds", new Rect(0, 0, view.getWidth(), view.getHeight()));
            }
        }
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
