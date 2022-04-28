package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.collection.ArrayMap;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.p012wm.shell.C1777R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

public final class TransitionManager {
    public static AutoTransition sDefaultTransition = new AutoTransition();
    public static ArrayList<ViewGroup> sPendingTransitions = new ArrayList<>();
    public static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> sRunningTransitions = new ThreadLocal<>();

    public static class MultiListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        public ViewGroup mSceneRoot;
        public Transition mTransition;

        public final void onViewAttachedToWindow(View view) {
        }

        /* JADX WARNING: Removed duplicated region for block: B:105:0x0241  */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x005c  */
        /* JADX WARNING: Removed duplicated region for block: B:141:0x01ef A[EDGE_INSN: B:141:0x01ef->B:89:0x01ef ?: BREAK  , SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x009f  */
        /* JADX WARNING: Removed duplicated region for block: B:92:0x01f4  */
        /* JADX WARNING: Removed duplicated region for block: B:99:0x0215  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean onPreDraw() {
            /*
                r16 = this;
                r0 = r16
                android.view.ViewGroup r1 = r0.mSceneRoot
                android.view.ViewTreeObserver r1 = r1.getViewTreeObserver()
                r1.removeOnPreDrawListener(r0)
                android.view.ViewGroup r1 = r0.mSceneRoot
                r1.removeOnAttachStateChangeListener(r0)
                java.util.ArrayList<android.view.ViewGroup> r1 = androidx.transition.TransitionManager.sPendingTransitions
                android.view.ViewGroup r2 = r0.mSceneRoot
                boolean r1 = r1.remove(r2)
                r2 = 1
                if (r1 != 0) goto L_0x001c
                return r2
            L_0x001c:
                androidx.collection.ArrayMap r1 = androidx.transition.TransitionManager.getRunningTransitions()
                android.view.ViewGroup r3 = r0.mSceneRoot
                r4 = 0
                java.lang.Object r3 = r1.getOrDefault(r3, r4)
                java.util.ArrayList r3 = (java.util.ArrayList) r3
                if (r3 != 0) goto L_0x0036
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
                android.view.ViewGroup r5 = r0.mSceneRoot
                r1.put(r5, r3)
                goto L_0x0042
            L_0x0036:
                int r5 = r3.size()
                if (r5 <= 0) goto L_0x0042
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>(r3)
                goto L_0x0043
            L_0x0042:
                r5 = r4
            L_0x0043:
                androidx.transition.Transition r6 = r0.mTransition
                r3.add(r6)
                androidx.transition.Transition r3 = r0.mTransition
                androidx.transition.TransitionManager$MultiListener$1 r6 = new androidx.transition.TransitionManager$MultiListener$1
                r6.<init>(r1)
                r3.addListener(r6)
                androidx.transition.Transition r1 = r0.mTransition
                android.view.ViewGroup r3 = r0.mSceneRoot
                r6 = 0
                r1.captureValues(r3, r6)
                if (r5 == 0) goto L_0x0072
                java.util.Iterator r1 = r5.iterator()
            L_0x0060:
                boolean r3 = r1.hasNext()
                if (r3 == 0) goto L_0x0072
                java.lang.Object r3 = r1.next()
                androidx.transition.Transition r3 = (androidx.transition.Transition) r3
                android.view.ViewGroup r5 = r0.mSceneRoot
                r3.resume(r5)
                goto L_0x0060
            L_0x0072:
                androidx.transition.Transition r1 = r0.mTransition
                android.view.ViewGroup r8 = r0.mSceneRoot
                java.util.Objects.requireNonNull(r1)
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                r1.mStartValuesList = r0
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                r1.mEndValuesList = r0
                androidx.transition.TransitionValuesMaps r0 = r1.mStartValues
                androidx.transition.TransitionValuesMaps r3 = r1.mEndValues
                androidx.collection.ArrayMap r5 = new androidx.collection.ArrayMap
                androidx.collection.ArrayMap<android.view.View, androidx.transition.TransitionValues> r7 = r0.mViewValues
                r5.<init>(r7)
                androidx.collection.ArrayMap r7 = new androidx.collection.ArrayMap
                androidx.collection.ArrayMap<android.view.View, androidx.transition.TransitionValues> r9 = r3.mViewValues
                r7.<init>(r9)
                r9 = r6
            L_0x009a:
                int[] r10 = r1.mMatchOrder
                int r11 = r10.length
                if (r9 >= r11) goto L_0x01ef
                r10 = r10[r9]
                if (r10 == r2) goto L_0x01b3
                r11 = 2
                if (r10 == r11) goto L_0x0160
                r11 = 3
                if (r10 == r11) goto L_0x010f
                r11 = 4
                if (r10 == r11) goto L_0x00ae
                goto L_0x01e8
            L_0x00ae:
                androidx.collection.LongSparseArray<android.view.View> r10 = r0.mItemIdValues
                androidx.collection.LongSparseArray<android.view.View> r11 = r3.mItemIdValues
                int r12 = r10.size()
                r13 = r6
            L_0x00b7:
                if (r13 >= r12) goto L_0x01e8
                java.lang.Object r14 = r10.valueAt(r13)
                android.view.View r14 = (android.view.View) r14
                if (r14 == 0) goto L_0x0106
                boolean r15 = r1.isValidTarget(r14)
                if (r15 == 0) goto L_0x0106
                boolean r15 = r10.mGarbage
                if (r15 == 0) goto L_0x00ce
                r10.mo1471gc()
            L_0x00ce:
                long[] r15 = r10.mKeys
                r16 = r3
                r2 = r15[r13]
                java.util.Objects.requireNonNull(r11)
                java.lang.Object r2 = r11.get(r2, r4)
                android.view.View r2 = (android.view.View) r2
                if (r2 == 0) goto L_0x0108
                boolean r3 = r1.isValidTarget(r2)
                if (r3 == 0) goto L_0x0108
                java.lang.Object r3 = r5.getOrDefault(r14, r4)
                androidx.transition.TransitionValues r3 = (androidx.transition.TransitionValues) r3
                java.lang.Object r15 = r7.getOrDefault(r2, r4)
                androidx.transition.TransitionValues r15 = (androidx.transition.TransitionValues) r15
                if (r3 == 0) goto L_0x0108
                if (r15 == 0) goto L_0x0108
                java.util.ArrayList<androidx.transition.TransitionValues> r6 = r1.mStartValuesList
                r6.add(r3)
                java.util.ArrayList<androidx.transition.TransitionValues> r3 = r1.mEndValuesList
                r3.add(r15)
                r5.remove(r14)
                r7.remove(r2)
                goto L_0x0108
            L_0x0106:
                r16 = r3
            L_0x0108:
                int r13 = r13 + 1
                r3 = r16
                r2 = 1
                r6 = 0
                goto L_0x00b7
            L_0x010f:
                r16 = r3
                android.util.SparseArray<android.view.View> r2 = r0.mIdValues
                android.util.SparseArray<android.view.View> r6 = r3.mIdValues
                int r10 = r2.size()
                r11 = 0
            L_0x011a:
                if (r11 >= r10) goto L_0x01e8
                java.lang.Object r12 = r2.valueAt(r11)
                android.view.View r12 = (android.view.View) r12
                if (r12 == 0) goto L_0x015c
                boolean r13 = r1.isValidTarget(r12)
                if (r13 == 0) goto L_0x015c
                int r13 = r2.keyAt(r11)
                java.lang.Object r13 = r6.get(r13)
                android.view.View r13 = (android.view.View) r13
                if (r13 == 0) goto L_0x015c
                boolean r14 = r1.isValidTarget(r13)
                if (r14 == 0) goto L_0x015c
                java.lang.Object r14 = r5.getOrDefault(r12, r4)
                androidx.transition.TransitionValues r14 = (androidx.transition.TransitionValues) r14
                java.lang.Object r15 = r7.getOrDefault(r13, r4)
                androidx.transition.TransitionValues r15 = (androidx.transition.TransitionValues) r15
                if (r14 == 0) goto L_0x015c
                if (r15 == 0) goto L_0x015c
                java.util.ArrayList<androidx.transition.TransitionValues> r4 = r1.mStartValuesList
                r4.add(r14)
                java.util.ArrayList<androidx.transition.TransitionValues> r4 = r1.mEndValuesList
                r4.add(r15)
                r5.remove(r12)
                r7.remove(r13)
            L_0x015c:
                int r11 = r11 + 1
                r4 = 0
                goto L_0x011a
            L_0x0160:
                androidx.collection.ArrayMap<java.lang.String, android.view.View> r2 = r0.mNameValues
                androidx.collection.ArrayMap<java.lang.String, android.view.View> r4 = r3.mNameValues
                java.util.Objects.requireNonNull(r2)
                int r6 = r2.mSize
                r10 = 0
            L_0x016a:
                if (r10 >= r6) goto L_0x01e8
                java.lang.Object r11 = r2.valueAt(r10)
                android.view.View r11 = (android.view.View) r11
                if (r11 == 0) goto L_0x01b0
                boolean r12 = r1.isValidTarget(r11)
                if (r12 == 0) goto L_0x01b0
                java.lang.Object r12 = r2.keyAt(r10)
                java.util.Objects.requireNonNull(r4)
                r13 = 0
                java.lang.Object r12 = r4.getOrDefault(r12, r13)
                android.view.View r12 = (android.view.View) r12
                if (r12 == 0) goto L_0x01b0
                boolean r14 = r1.isValidTarget(r12)
                if (r14 == 0) goto L_0x01b0
                java.lang.Object r14 = r5.getOrDefault(r11, r13)
                androidx.transition.TransitionValues r14 = (androidx.transition.TransitionValues) r14
                java.lang.Object r15 = r7.getOrDefault(r12, r13)
                androidx.transition.TransitionValues r15 = (androidx.transition.TransitionValues) r15
                if (r14 == 0) goto L_0x01b0
                if (r15 == 0) goto L_0x01b0
                java.util.ArrayList<androidx.transition.TransitionValues> r13 = r1.mStartValuesList
                r13.add(r14)
                java.util.ArrayList<androidx.transition.TransitionValues> r13 = r1.mEndValuesList
                r13.add(r15)
                r5.remove(r11)
                r7.remove(r12)
            L_0x01b0:
                int r10 = r10 + 1
                goto L_0x016a
            L_0x01b3:
                int r2 = r5.mSize
            L_0x01b5:
                int r2 = r2 + -1
                if (r2 < 0) goto L_0x01e8
                java.lang.Object r4 = r5.keyAt(r2)
                android.view.View r4 = (android.view.View) r4
                if (r4 == 0) goto L_0x01b5
                boolean r6 = r1.isValidTarget(r4)
                if (r6 == 0) goto L_0x01b5
                java.lang.Object r4 = r7.remove(r4)
                androidx.transition.TransitionValues r4 = (androidx.transition.TransitionValues) r4
                if (r4 == 0) goto L_0x01b5
                android.view.View r6 = r4.view
                boolean r6 = r1.isValidTarget(r6)
                if (r6 == 0) goto L_0x01b5
                java.lang.Object r6 = r5.removeAt(r2)
                androidx.transition.TransitionValues r6 = (androidx.transition.TransitionValues) r6
                java.util.ArrayList<androidx.transition.TransitionValues> r10 = r1.mStartValuesList
                r10.add(r6)
                java.util.ArrayList<androidx.transition.TransitionValues> r6 = r1.mEndValuesList
                r6.add(r4)
                goto L_0x01b5
            L_0x01e8:
                int r9 = r9 + 1
                r2 = 1
                r4 = 0
                r6 = 0
                goto L_0x009a
            L_0x01ef:
                r0 = 0
            L_0x01f0:
                int r2 = r5.mSize
                if (r0 >= r2) goto L_0x0210
                java.lang.Object r2 = r5.valueAt(r0)
                androidx.transition.TransitionValues r2 = (androidx.transition.TransitionValues) r2
                android.view.View r3 = r2.view
                boolean r3 = r1.isValidTarget(r3)
                if (r3 == 0) goto L_0x020d
                java.util.ArrayList<androidx.transition.TransitionValues> r3 = r1.mStartValuesList
                r3.add(r2)
                java.util.ArrayList<androidx.transition.TransitionValues> r2 = r1.mEndValuesList
                r3 = 0
                r2.add(r3)
            L_0x020d:
                int r0 = r0 + 1
                goto L_0x01f0
            L_0x0210:
                r0 = 0
            L_0x0211:
                int r2 = r7.mSize
                if (r0 >= r2) goto L_0x0231
                java.lang.Object r2 = r7.valueAt(r0)
                androidx.transition.TransitionValues r2 = (androidx.transition.TransitionValues) r2
                android.view.View r3 = r2.view
                boolean r3 = r1.isValidTarget(r3)
                if (r3 == 0) goto L_0x022e
                java.util.ArrayList<androidx.transition.TransitionValues> r3 = r1.mEndValuesList
                r3.add(r2)
                java.util.ArrayList<androidx.transition.TransitionValues> r2 = r1.mStartValuesList
                r3 = 0
                r2.add(r3)
            L_0x022e:
                int r0 = r0 + 1
                goto L_0x0211
            L_0x0231:
                androidx.collection.ArrayMap r0 = androidx.transition.Transition.getRunningAnimators()
                int r2 = r0.mSize
                androidx.transition.ViewUtilsApi29 r3 = androidx.transition.ViewUtils.IMPL
                android.view.WindowId r3 = r8.getWindowId()
                r4 = 1
                int r2 = r2 - r4
            L_0x023f:
                if (r2 < 0) goto L_0x02b7
                java.lang.Object r4 = r0.keyAt(r2)
                android.animation.Animator r4 = (android.animation.Animator) r4
                if (r4 == 0) goto L_0x02b3
                r5 = 0
                java.lang.Object r6 = r0.getOrDefault(r4, r5)
                androidx.transition.Transition$AnimationInfo r6 = (androidx.transition.Transition.AnimationInfo) r6
                if (r6 == 0) goto L_0x02b3
                android.view.View r5 = r6.mView
                if (r5 == 0) goto L_0x02b3
                androidx.transition.WindowIdImpl r5 = r6.mWindowId
                boolean r7 = r5 instanceof androidx.transition.WindowIdApi18
                if (r7 == 0) goto L_0x0268
                androidx.transition.WindowIdApi18 r5 = (androidx.transition.WindowIdApi18) r5
                android.view.WindowId r5 = r5.mWindowId
                boolean r5 = r5.equals(r3)
                if (r5 == 0) goto L_0x0268
                r5 = 1
                goto L_0x0269
            L_0x0268:
                r5 = 0
            L_0x0269:
                if (r5 == 0) goto L_0x02b3
                androidx.transition.TransitionValues r5 = r6.mValues
                android.view.View r7 = r6.mView
                r9 = 1
                androidx.transition.TransitionValues r10 = r1.getTransitionValues(r7, r9)
                androidx.transition.TransitionValues r11 = r1.getMatchedTransitionValues(r7, r9)
                if (r10 != 0) goto L_0x028c
                if (r11 != 0) goto L_0x028c
                androidx.transition.TransitionValuesMaps r9 = r1.mEndValues
                androidx.collection.ArrayMap<android.view.View, androidx.transition.TransitionValues> r9 = r9.mViewValues
                java.util.Objects.requireNonNull(r9)
                r12 = 0
                java.lang.Object r7 = r9.getOrDefault(r7, r12)
                r11 = r7
                androidx.transition.TransitionValues r11 = (androidx.transition.TransitionValues) r11
                goto L_0x028d
            L_0x028c:
                r12 = 0
            L_0x028d:
                if (r10 != 0) goto L_0x0291
                if (r11 == 0) goto L_0x029b
            L_0x0291:
                androidx.transition.Transition r6 = r6.mTransition
                boolean r5 = r6.isTransitionRequired(r5, r11)
                if (r5 == 0) goto L_0x029b
                r5 = 1
                goto L_0x029c
            L_0x029b:
                r5 = 0
            L_0x029c:
                if (r5 == 0) goto L_0x02b4
                boolean r5 = r4.isRunning()
                if (r5 != 0) goto L_0x02af
                boolean r5 = r4.isStarted()
                if (r5 == 0) goto L_0x02ab
                goto L_0x02af
            L_0x02ab:
                r0.remove(r4)
                goto L_0x02b4
            L_0x02af:
                r4.cancel()
                goto L_0x02b4
            L_0x02b3:
                r12 = 0
            L_0x02b4:
                int r2 = r2 + -1
                goto L_0x023f
            L_0x02b7:
                androidx.transition.TransitionValuesMaps r9 = r1.mStartValues
                androidx.transition.TransitionValuesMaps r10 = r1.mEndValues
                java.util.ArrayList<androidx.transition.TransitionValues> r11 = r1.mStartValuesList
                java.util.ArrayList<androidx.transition.TransitionValues> r12 = r1.mEndValuesList
                r7 = r1
                r7.createAnimators(r8, r9, r10, r11, r12)
                r1.runAnimators()
                r0 = 1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.transition.TransitionManager.MultiListener.onPreDraw():boolean");
        }

        public final void onViewDetachedFromWindow(View view) {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
            TransitionManager.sPendingTransitions.remove(this.mSceneRoot);
            ArrayList orDefault = TransitionManager.getRunningTransitions().getOrDefault(this.mSceneRoot, null);
            if (orDefault != null && orDefault.size() > 0) {
                Iterator it = orDefault.iterator();
                while (it.hasNext()) {
                    ((Transition) it.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

        public MultiListener(Transition transition, ViewGroup viewGroup) {
            this.mTransition = transition;
            this.mSceneRoot = viewGroup;
        }
    }

    public static void beginDelayedTransition(ViewGroup viewGroup, Transition transition) {
        if (!sPendingTransitions.contains(viewGroup)) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api19Impl.isLaidOut(viewGroup)) {
                sPendingTransitions.add(viewGroup);
                if (transition == null) {
                    transition = sDefaultTransition;
                }
                Transition clone = transition.clone();
                ArrayList orDefault = getRunningTransitions().getOrDefault(viewGroup, null);
                if (orDefault != null && orDefault.size() > 0) {
                    Iterator it = orDefault.iterator();
                    while (it.hasNext()) {
                        ((Transition) it.next()).pause(viewGroup);
                    }
                }
                if (clone != null) {
                    clone.captureValues(viewGroup, true);
                }
                if (((Scene) viewGroup.getTag(C1777R.C1779id.transition_current_scene)) == null) {
                    viewGroup.setTag(C1777R.C1779id.transition_current_scene, (Object) null);
                    if (clone != null) {
                        MultiListener multiListener = new MultiListener(clone, viewGroup);
                        viewGroup.addOnAttachStateChangeListener(multiListener);
                        viewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
                        return;
                    }
                    return;
                }
                throw null;
            }
        }
    }

    public static ArrayMap<ViewGroup, ArrayList<Transition>> getRunningTransitions() {
        ArrayMap<ViewGroup, ArrayList<Transition>> arrayMap;
        WeakReference weakReference = sRunningTransitions.get();
        if (weakReference != null && (arrayMap = (ArrayMap) weakReference.get()) != null) {
            return arrayMap;
        }
        ArrayMap<ViewGroup, ArrayList<Transition>> arrayMap2 = new ArrayMap<>();
        sRunningTransitions.set(new WeakReference(arrayMap2));
        return arrayMap2;
    }
}
