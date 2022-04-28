package androidx.fragment.app;

import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.p002os.CancellationSignal;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;

public final class DefaultSpecialEffectsController extends SpecialEffectsController {

    public static class AnimationInfo extends SpecialEffectsInfo {
        public FragmentAnim.AnimationOrAnimator mAnimation;
        public boolean mIsPop;
        public boolean mLoadedAnim = false;

        /* JADX WARNING: Removed duplicated region for block: B:37:0x0073  */
        /* JADX WARNING: Removed duplicated region for block: B:58:0x00bd  */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x00c9  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final androidx.fragment.app.FragmentAnim.AnimationOrAnimator getAnimation(android.content.Context r10) {
            /*
                r9 = this;
                boolean r0 = r9.mLoadedAnim
                if (r0 == 0) goto L_0x0007
                androidx.fragment.app.FragmentAnim$AnimationOrAnimator r9 = r9.mAnimation
                return r9
            L_0x0007:
                androidx.fragment.app.SpecialEffectsController$Operation r0 = r9.mOperation
                java.util.Objects.requireNonNull(r0)
                androidx.fragment.app.Fragment r0 = r0.mFragment
                androidx.fragment.app.SpecialEffectsController$Operation r1 = r9.mOperation
                java.util.Objects.requireNonNull(r1)
                androidx.fragment.app.SpecialEffectsController$Operation$State r1 = r1.mFinalState
                androidx.fragment.app.SpecialEffectsController$Operation$State r2 = androidx.fragment.app.SpecialEffectsController.Operation.State.VISIBLE
                r3 = 1
                r4 = 0
                if (r1 != r2) goto L_0x001d
                r1 = r3
                goto L_0x001e
            L_0x001d:
                r1 = r4
            L_0x001e:
                boolean r2 = r9.mIsPop
                java.util.Objects.requireNonNull(r0)
                androidx.fragment.app.Fragment$AnimationInfo r5 = r0.mAnimationInfo
                if (r5 != 0) goto L_0x0029
                r6 = r4
                goto L_0x002b
            L_0x0029:
                int r6 = r5.mNextTransition
            L_0x002b:
                if (r2 == 0) goto L_0x003c
                if (r1 == 0) goto L_0x0036
                if (r5 != 0) goto L_0x0033
            L_0x0031:
                r2 = r4
                goto L_0x0049
            L_0x0033:
                int r2 = r5.mPopEnterAnim
                goto L_0x0049
            L_0x0036:
                if (r5 != 0) goto L_0x0039
                goto L_0x0031
            L_0x0039:
                int r2 = r5.mPopExitAnim
                goto L_0x0049
            L_0x003c:
                if (r1 == 0) goto L_0x0044
                if (r5 != 0) goto L_0x0041
                goto L_0x0031
            L_0x0041:
                int r2 = r5.mEnterAnim
                goto L_0x0049
            L_0x0044:
                if (r5 != 0) goto L_0x0047
                goto L_0x0031
            L_0x0047:
                int r2 = r5.mExitAnim
            L_0x0049:
                r0.setAnimations(r4, r4, r4, r4)
                android.view.ViewGroup r5 = r0.mContainer
                r7 = 0
                if (r5 == 0) goto L_0x005f
                r8 = 2131429202(0x7f0b0752, float:1.848007E38)
                java.lang.Object r5 = r5.getTag(r8)
                if (r5 == 0) goto L_0x005f
                android.view.ViewGroup r5 = r0.mContainer
                r5.setTag(r8, r7)
            L_0x005f:
                android.view.ViewGroup r0 = r0.mContainer
                if (r0 == 0) goto L_0x006b
                android.animation.LayoutTransition r0 = r0.getLayoutTransition()
                if (r0 == 0) goto L_0x006b
                goto L_0x0109
            L_0x006b:
                if (r2 != 0) goto L_0x00c7
                if (r6 == 0) goto L_0x00c7
                r0 = 4097(0x1001, float:5.741E-42)
                if (r6 == r0) goto L_0x00bd
                r0 = 8194(0x2002, float:1.1482E-41)
                if (r6 == r0) goto L_0x00b3
                r0 = 8197(0x2005, float:1.1486E-41)
                if (r6 == r0) goto L_0x00a1
                r0 = 4099(0x1003, float:5.744E-42)
                if (r6 == r0) goto L_0x0097
                r0 = 4100(0x1004, float:5.745E-42)
                if (r6 == r0) goto L_0x0085
                r0 = -1
                goto L_0x00c6
            L_0x0085:
                if (r1 == 0) goto L_0x008f
                r0 = 16842936(0x10100b8, float:2.3694074E-38)
                int r0 = androidx.fragment.app.FragmentAnim.toActivityTransitResId(r10, r0)
                goto L_0x00c6
            L_0x008f:
                r0 = 16842937(0x10100b9, float:2.3694076E-38)
                int r0 = androidx.fragment.app.FragmentAnim.toActivityTransitResId(r10, r0)
                goto L_0x00c6
            L_0x0097:
                if (r1 == 0) goto L_0x009d
                r0 = 2130837509(0x7f020005, float:1.7279974E38)
                goto L_0x00c6
            L_0x009d:
                r0 = 2130837510(0x7f020006, float:1.7279976E38)
                goto L_0x00c6
            L_0x00a1:
                if (r1 == 0) goto L_0x00ab
                r0 = 16842938(0x10100ba, float:2.369408E-38)
                int r0 = androidx.fragment.app.FragmentAnim.toActivityTransitResId(r10, r0)
                goto L_0x00c6
            L_0x00ab:
                r0 = 16842939(0x10100bb, float:2.3694082E-38)
                int r0 = androidx.fragment.app.FragmentAnim.toActivityTransitResId(r10, r0)
                goto L_0x00c6
            L_0x00b3:
                if (r1 == 0) goto L_0x00b9
                r0 = 2130837507(0x7f020003, float:1.727997E38)
                goto L_0x00c6
            L_0x00b9:
                r0 = 2130837508(0x7f020004, float:1.7279972E38)
                goto L_0x00c6
            L_0x00bd:
                if (r1 == 0) goto L_0x00c3
                r0 = 2130837511(0x7f020007, float:1.7279978E38)
                goto L_0x00c6
            L_0x00c3:
                r0 = 2130837512(0x7f020008, float:1.727998E38)
            L_0x00c6:
                r2 = r0
            L_0x00c7:
                if (r2 == 0) goto L_0x0109
                android.content.res.Resources r0 = r10.getResources()
                java.lang.String r0 = r0.getResourceTypeName(r2)
                java.lang.String r1 = "anim"
                boolean r0 = r1.equals(r0)
                if (r0 == 0) goto L_0x00ea
                android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r10, r2)     // Catch:{ NotFoundException -> 0x00e8, RuntimeException -> 0x00ea }
                if (r1 == 0) goto L_0x00e6
                androidx.fragment.app.FragmentAnim$AnimationOrAnimator r5 = new androidx.fragment.app.FragmentAnim$AnimationOrAnimator     // Catch:{ NotFoundException -> 0x00e8, RuntimeException -> 0x00ea }
                r5.<init>((android.view.animation.Animation) r1)     // Catch:{ NotFoundException -> 0x00e8, RuntimeException -> 0x00ea }
                r7 = r5
                goto L_0x0109
            L_0x00e6:
                r4 = r3
                goto L_0x00ea
            L_0x00e8:
                r9 = move-exception
                throw r9
            L_0x00ea:
                if (r4 != 0) goto L_0x0109
                android.animation.Animator r1 = android.animation.AnimatorInflater.loadAnimator(r10, r2)     // Catch:{ RuntimeException -> 0x00f9 }
                if (r1 == 0) goto L_0x0109
                androidx.fragment.app.FragmentAnim$AnimationOrAnimator r4 = new androidx.fragment.app.FragmentAnim$AnimationOrAnimator     // Catch:{ RuntimeException -> 0x00f9 }
                r4.<init>((android.animation.Animator) r1)     // Catch:{ RuntimeException -> 0x00f9 }
                r7 = r4
                goto L_0x0109
            L_0x00f9:
                r1 = move-exception
                if (r0 != 0) goto L_0x0108
                android.view.animation.Animation r10 = android.view.animation.AnimationUtils.loadAnimation(r10, r2)
                if (r10 == 0) goto L_0x0109
                androidx.fragment.app.FragmentAnim$AnimationOrAnimator r7 = new androidx.fragment.app.FragmentAnim$AnimationOrAnimator
                r7.<init>((android.view.animation.Animation) r10)
                goto L_0x0109
            L_0x0108:
                throw r1
            L_0x0109:
                r9.mAnimation = r7
                r9.mLoadedAnim = r3
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.AnimationInfo.getAnimation(android.content.Context):androidx.fragment.app.FragmentAnim$AnimationOrAnimator");
        }

        public AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z) {
            super(operation, cancellationSignal);
            this.mIsPop = z;
        }
    }

    public static class SpecialEffectsInfo {
        public final SpecialEffectsController.Operation mOperation;
        public final CancellationSignal mSignal;

        public final void completeSpecialEffect() {
            SpecialEffectsController.Operation operation = this.mOperation;
            CancellationSignal cancellationSignal = this.mSignal;
            Objects.requireNonNull(operation);
            if (operation.mSpecialEffectsSignals.remove(cancellationSignal) && operation.mSpecialEffectsSignals.isEmpty()) {
                operation.complete();
            }
        }

        public final boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State state;
            SpecialEffectsController.Operation operation = this.mOperation;
            Objects.requireNonNull(operation);
            SpecialEffectsController.Operation.State from = SpecialEffectsController.Operation.State.from(operation.mFragment.mView);
            SpecialEffectsController.Operation operation2 = this.mOperation;
            Objects.requireNonNull(operation2);
            SpecialEffectsController.Operation.State state2 = operation2.mFinalState;
            if (from == state2 || (from != (state = SpecialEffectsController.Operation.State.VISIBLE) && state2 != state)) {
                return true;
            }
            return false;
        }

        public SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal) {
            this.mOperation = operation;
            this.mSignal = cancellationSignal;
        }
    }

    public static class TransitionInfo extends SpecialEffectsInfo {
        public final boolean mOverlapAllowed;
        public final Object mSharedElementTransition;
        public final Object mTransition;

        public final FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            FragmentTransitionCompat21 fragmentTransitionCompat21 = FragmentTransition.PLATFORM_IMPL;
            if (obj instanceof Transition) {
                return fragmentTransitionCompat21;
            }
            FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.SUPPORT_IMPL;
            if (fragmentTransitionImpl != null && fragmentTransitionImpl.canHandle(obj)) {
                return fragmentTransitionImpl;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Transition ");
            sb.append(obj);
            sb.append(" for fragment ");
            SpecialEffectsController.Operation operation = this.mOperation;
            Objects.requireNonNull(operation);
            sb.append(operation.mFragment);
            sb.append(" is not a valid framework Transition or AndroidX Transition");
            throw new IllegalArgumentException(sb.toString());
        }

        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
            if (r5 == androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION) goto L_0x004e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x001b, code lost:
            if (r5 == androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION) goto L_0x0023;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public TransitionInfo(androidx.fragment.app.SpecialEffectsController.Operation r4, androidx.core.p002os.CancellationSignal r5, boolean r6, boolean r7) {
            /*
                r3 = this;
                r3.<init>(r4, r5)
                androidx.fragment.app.SpecialEffectsController$Operation$State r5 = r4.mFinalState
                androidx.fragment.app.SpecialEffectsController$Operation$State r0 = androidx.fragment.app.SpecialEffectsController.Operation.State.VISIBLE
                r1 = 1
                r2 = 0
                if (r5 != r0) goto L_0x0036
                if (r6 == 0) goto L_0x001e
                androidx.fragment.app.Fragment r5 = r4.mFragment
                java.util.Objects.requireNonNull(r5)
                androidx.fragment.app.Fragment$AnimationInfo r5 = r5.mAnimationInfo
                if (r5 != 0) goto L_0x0017
                goto L_0x0023
            L_0x0017:
                java.lang.Object r5 = r5.mReenterTransition
                java.lang.Object r0 = androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION
                if (r5 != r0) goto L_0x0024
                goto L_0x0023
            L_0x001e:
                androidx.fragment.app.Fragment r5 = r4.mFragment
                java.util.Objects.requireNonNull(r5)
            L_0x0023:
                r5 = r2
            L_0x0024:
                r3.mTransition = r5
                if (r6 == 0) goto L_0x002e
                androidx.fragment.app.Fragment r5 = r4.mFragment
                java.util.Objects.requireNonNull(r5)
                goto L_0x0033
            L_0x002e:
                androidx.fragment.app.Fragment r5 = r4.mFragment
                java.util.Objects.requireNonNull(r5)
            L_0x0033:
                r3.mOverlapAllowed = r1
                goto L_0x0053
            L_0x0036:
                if (r6 == 0) goto L_0x0049
                androidx.fragment.app.Fragment r5 = r4.mFragment
                java.util.Objects.requireNonNull(r5)
                androidx.fragment.app.Fragment$AnimationInfo r5 = r5.mAnimationInfo
                if (r5 != 0) goto L_0x0042
                goto L_0x004e
            L_0x0042:
                java.lang.Object r5 = r5.mReturnTransition
                java.lang.Object r0 = androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION
                if (r5 != r0) goto L_0x004f
                goto L_0x004e
            L_0x0049:
                androidx.fragment.app.Fragment r5 = r4.mFragment
                java.util.Objects.requireNonNull(r5)
            L_0x004e:
                r5 = r2
            L_0x004f:
                r3.mTransition = r5
                r3.mOverlapAllowed = r1
            L_0x0053:
                if (r7 == 0) goto L_0x0074
                if (r6 == 0) goto L_0x006c
                androidx.fragment.app.Fragment r4 = r4.mFragment
                java.util.Objects.requireNonNull(r4)
                androidx.fragment.app.Fragment$AnimationInfo r4 = r4.mAnimationInfo
                if (r4 != 0) goto L_0x0061
                goto L_0x0069
            L_0x0061:
                java.lang.Object r4 = r4.mSharedElementReturnTransition
                java.lang.Object r5 = androidx.fragment.app.Fragment.USE_DEFAULT_TRANSITION
                if (r4 != r5) goto L_0x0068
                goto L_0x0069
            L_0x0068:
                r2 = r4
            L_0x0069:
                r3.mSharedElementTransition = r2
                goto L_0x0076
            L_0x006c:
                androidx.fragment.app.Fragment r4 = r4.mFragment
                java.util.Objects.requireNonNull(r4)
                r3.mSharedElementTransition = r2
                goto L_0x0076
            L_0x0074:
                r3.mSharedElementTransition = r2
            L_0x0076:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo.<init>(androidx.fragment.app.SpecialEffectsController$Operation, androidx.core.os.CancellationSignal, boolean, boolean):void");
        }
    }

    public static void captureTransitioningViews(ArrayList arrayList, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (!viewGroup.isTransitionGroup()) {
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (childAt.getVisibility() == 0) {
                        captureTransitioningViews(arrayList, childAt);
                    }
                }
            } else if (!arrayList.contains(view)) {
                arrayList.add(viewGroup);
            }
        } else if (!arrayList.contains(view)) {
            arrayList.add(view);
        }
    }

    public static void findNamedViews(ArrayMap arrayMap, View view) {
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        String transitionName = ViewCompat.Api21Impl.getTransitionName(view);
        if (transitionName != null) {
            arrayMap.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    findNamedViews(arrayMap, childAt);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:112:0x031a, code lost:
        r2 = (android.view.View) r10.getOrDefault(r7.get(r3), r8);
     */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x0623  */
    /* JADX WARNING: Removed duplicated region for block: B:235:0x06db  */
    /* JADX WARNING: Removed duplicated region for block: B:254:0x0779 A[LOOP:15: B:252:0x0773->B:254:0x0779, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void executeOperations(java.util.ArrayList r35, boolean r36) {
        /*
            r34 = this;
            r0 = r34
            r1 = r36
            androidx.fragment.app.SpecialEffectsController$Operation$State r2 = androidx.fragment.app.SpecialEffectsController.Operation.State.GONE
            androidx.fragment.app.SpecialEffectsController$Operation$State r3 = androidx.fragment.app.SpecialEffectsController.Operation.State.VISIBLE
            java.util.Iterator r4 = r35.iterator()
            r5 = 0
            r6 = 0
        L_0x000e:
            boolean r7 = r4.hasNext()
            r8 = 2
            r9 = 1
            if (r7 == 0) goto L_0x0041
            java.lang.Object r7 = r4.next()
            androidx.fragment.app.SpecialEffectsController$Operation r7 = (androidx.fragment.app.SpecialEffectsController.Operation) r7
            java.util.Objects.requireNonNull(r7)
            androidx.fragment.app.Fragment r10 = r7.mFragment
            android.view.View r10 = r10.mView
            androidx.fragment.app.SpecialEffectsController$Operation$State r10 = androidx.fragment.app.SpecialEffectsController.Operation.State.from((android.view.View) r10)
            androidx.fragment.app.SpecialEffectsController$Operation$State r11 = r7.mFinalState
            int r11 = r11.ordinal()
            if (r11 == 0) goto L_0x003b
            if (r11 == r9) goto L_0x0037
            if (r11 == r8) goto L_0x003b
            r8 = 3
            if (r11 == r8) goto L_0x003b
            goto L_0x000e
        L_0x0037:
            if (r10 == r3) goto L_0x000e
            r6 = r7
            goto L_0x000e
        L_0x003b:
            if (r10 != r3) goto L_0x000e
            if (r5 != 0) goto L_0x000e
            r5 = r7
            goto L_0x000e
        L_0x0041:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.ArrayList r8 = new java.util.ArrayList
            r9 = r35
            r8.<init>(r9)
            java.util.Iterator r9 = r35.iterator()
        L_0x0056:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x00a4
            java.lang.Object r10 = r9.next()
            androidx.fragment.app.SpecialEffectsController$Operation r10 = (androidx.fragment.app.SpecialEffectsController.Operation) r10
            androidx.core.os.CancellationSignal r11 = new androidx.core.os.CancellationSignal
            r11.<init>()
            java.util.Objects.requireNonNull(r10)
            r10.onStart()
            java.util.HashSet<androidx.core.os.CancellationSignal> r12 = r10.mSpecialEffectsSignals
            r12.add(r11)
            androidx.fragment.app.DefaultSpecialEffectsController$AnimationInfo r12 = new androidx.fragment.app.DefaultSpecialEffectsController$AnimationInfo
            r12.<init>(r10, r11, r1)
            r4.add(r12)
            androidx.core.os.CancellationSignal r11 = new androidx.core.os.CancellationSignal
            r11.<init>()
            r10.onStart()
            java.util.HashSet<androidx.core.os.CancellationSignal> r12 = r10.mSpecialEffectsSignals
            r12.add(r11)
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r12 = new androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo
            if (r1 == 0) goto L_0x008e
            if (r10 != r5) goto L_0x0092
            goto L_0x0090
        L_0x008e:
            if (r10 != r6) goto L_0x0092
        L_0x0090:
            r13 = 1
            goto L_0x0093
        L_0x0092:
            r13 = 0
        L_0x0093:
            r12.<init>(r10, r11, r1, r13)
            r7.add(r12)
            androidx.fragment.app.DefaultSpecialEffectsController$1 r11 = new androidx.fragment.app.DefaultSpecialEffectsController$1
            r11.<init>(r8, r10)
            java.util.ArrayList r10 = r10.mCompletionListeners
            r10.add(r11)
            goto L_0x0056
        L_0x00a4:
            java.util.HashMap r9 = new java.util.HashMap
            r9.<init>()
            java.util.Iterator r10 = r7.iterator()
            r11 = 0
        L_0x00ae:
            boolean r12 = r10.hasNext()
            if (r12 == 0) goto L_0x0134
            java.lang.Object r12 = r10.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r12 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r12
            boolean r13 = r12.isVisibilityUnchanged()
            if (r13 == 0) goto L_0x00c1
            goto L_0x00ae
        L_0x00c1:
            java.lang.Object r13 = r12.mTransition
            androidx.fragment.app.FragmentTransitionImpl r13 = r12.getHandlingImpl(r13)
            java.lang.Object r14 = r12.mSharedElementTransition
            androidx.fragment.app.FragmentTransitionImpl r14 = r12.getHandlingImpl(r14)
            java.lang.String r15 = " returned Transition "
            java.lang.String r16 = "Mixing framework transitions and AndroidX transitions is not allowed. Fragment "
            if (r13 == 0) goto L_0x0102
            if (r14 == 0) goto L_0x0102
            if (r13 != r14) goto L_0x00d8
            goto L_0x0102
        L_0x00d8:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r16)
            androidx.fragment.app.SpecialEffectsController$Operation r2 = r12.mOperation
            java.util.Objects.requireNonNull(r2)
            androidx.fragment.app.Fragment r2 = r2.mFragment
            r1.append(r2)
            r1.append(r15)
            java.lang.Object r2 = r12.mTransition
            r1.append(r2)
            java.lang.String r2 = " which uses a different Transition  type than its shared element transition "
            r1.append(r2)
            java.lang.Object r2 = r12.mSharedElementTransition
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0102:
            if (r13 == 0) goto L_0x0105
            goto L_0x0106
        L_0x0105:
            r13 = r14
        L_0x0106:
            if (r11 != 0) goto L_0x010a
            r11 = r13
            goto L_0x00ae
        L_0x010a:
            if (r13 == 0) goto L_0x00ae
            if (r11 != r13) goto L_0x010f
            goto L_0x00ae
        L_0x010f:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r16)
            androidx.fragment.app.SpecialEffectsController$Operation r2 = r12.mOperation
            java.util.Objects.requireNonNull(r2)
            androidx.fragment.app.Fragment r2 = r2.mFragment
            r1.append(r2)
            r1.append(r15)
            java.lang.Object r2 = r12.mTransition
            r1.append(r2)
            java.lang.String r2 = " which uses a different Transition  type than other Fragments."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0134:
            java.lang.String r10 = "FragmentManager"
            if (r11 != 0) goto L_0x015f
            java.util.Iterator r1 = r7.iterator()
        L_0x013c:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0156
            java.lang.Object r3 = r1.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r3 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r3
            java.util.Objects.requireNonNull(r3)
            androidx.fragment.app.SpecialEffectsController$Operation r5 = r3.mOperation
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            r9.put(r5, r6)
            r3.completeSpecialEffect()
            goto L_0x013c
        L_0x0156:
            r13 = r2
            r22 = r4
            r24 = r8
            r2 = r9
            r9 = r10
            goto L_0x0551
        L_0x015f:
            android.view.View r15 = new android.view.View
            android.view.ViewGroup r12 = r0.mContainer
            android.content.Context r12 = r12.getContext()
            r15.<init>(r12)
            android.graphics.Rect r14 = new android.graphics.Rect
            r14.<init>()
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>()
            r22 = r4
            androidx.collection.ArrayMap r4 = new androidx.collection.ArrayMap
            r4.<init>()
            java.util.Iterator r18 = r7.iterator()
            r16 = 0
            r17 = 0
            r19 = 0
            r20 = r3
            r3 = r5
            r24 = r8
            r35 = r10
            r10 = r16
            r23 = r17
            r8 = r6
        L_0x0196:
            boolean r16 = r18.hasNext()
            if (r16 == 0) goto L_0x036e
            java.lang.Object r16 = r18.next()
            r25 = r2
            r2 = r16
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r2 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r2
            java.util.Objects.requireNonNull(r2)
            java.lang.Object r2 = r2.mSharedElementTransition
            if (r2 == 0) goto L_0x01b0
            r16 = 1
            goto L_0x01b2
        L_0x01b0:
            r16 = 0
        L_0x01b2:
            if (r16 == 0) goto L_0x035c
            if (r3 == 0) goto L_0x035c
            if (r8 == 0) goto L_0x035c
            java.lang.Object r2 = r11.cloneTransition(r2)
            java.lang.Object r10 = r11.wrapTransitionInSet(r2)
            androidx.fragment.app.Fragment r2 = r8.mFragment
            java.util.Objects.requireNonNull(r2)
            androidx.fragment.app.Fragment$AnimationInfo r2 = r2.mAnimationInfo
            if (r2 == 0) goto L_0x01cd
            java.util.ArrayList<java.lang.String> r2 = r2.mSharedElementSourceNames
            if (r2 != 0) goto L_0x01d2
        L_0x01cd:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
        L_0x01d2:
            r21 = r7
            androidx.fragment.app.Fragment r7 = r3.mFragment
            java.util.Objects.requireNonNull(r7)
            androidx.fragment.app.Fragment$AnimationInfo r7 = r7.mAnimationInfo
            if (r7 == 0) goto L_0x01e1
            java.util.ArrayList<java.lang.String> r7 = r7.mSharedElementSourceNames
            if (r7 != 0) goto L_0x01e6
        L_0x01e1:
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
        L_0x01e6:
            r26 = r9
            androidx.fragment.app.Fragment r9 = r3.mFragment
            java.util.Objects.requireNonNull(r9)
            androidx.fragment.app.Fragment$AnimationInfo r9 = r9.mAnimationInfo
            if (r9 == 0) goto L_0x01f5
            java.util.ArrayList<java.lang.String> r9 = r9.mSharedElementTargetNames
            if (r9 != 0) goto L_0x01fa
        L_0x01f5:
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
        L_0x01fa:
            r16 = 0
            r17 = r15
            r15 = r16
            r16 = r14
        L_0x0202:
            int r14 = r9.size()
            r27 = r10
            r10 = -1
            if (r15 >= r14) goto L_0x0223
            java.lang.Object r14 = r9.get(r15)
            int r14 = r2.indexOf(r14)
            if (r14 == r10) goto L_0x021e
            java.lang.Object r10 = r7.get(r15)
            java.lang.String r10 = (java.lang.String) r10
            r2.set(r14, r10)
        L_0x021e:
            int r15 = r15 + 1
            r10 = r27
            goto L_0x0202
        L_0x0223:
            androidx.fragment.app.Fragment r7 = r8.mFragment
            java.util.Objects.requireNonNull(r7)
            androidx.fragment.app.Fragment$AnimationInfo r7 = r7.mAnimationInfo
            if (r7 == 0) goto L_0x0230
            java.util.ArrayList<java.lang.String> r7 = r7.mSharedElementTargetNames
            if (r7 != 0) goto L_0x0235
        L_0x0230:
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
        L_0x0235:
            if (r1 != 0) goto L_0x0242
            androidx.fragment.app.Fragment r9 = r3.mFragment
            java.util.Objects.requireNonNull(r9)
            androidx.fragment.app.Fragment r9 = r8.mFragment
            java.util.Objects.requireNonNull(r9)
            goto L_0x024c
        L_0x0242:
            androidx.fragment.app.Fragment r9 = r3.mFragment
            java.util.Objects.requireNonNull(r9)
            androidx.fragment.app.Fragment r9 = r8.mFragment
            java.util.Objects.requireNonNull(r9)
        L_0x024c:
            int r9 = r2.size()
            r10 = 0
        L_0x0251:
            if (r10 >= r9) goto L_0x0265
            java.lang.Object r14 = r2.get(r10)
            java.lang.String r14 = (java.lang.String) r14
            java.lang.Object r15 = r7.get(r10)
            java.lang.String r15 = (java.lang.String) r15
            r4.put(r14, r15)
            int r10 = r10 + 1
            goto L_0x0251
        L_0x0265:
            androidx.collection.ArrayMap r9 = new androidx.collection.ArrayMap
            r9.<init>()
            androidx.fragment.app.Fragment r10 = r3.mFragment
            android.view.View r10 = r10.mView
            findNamedViews(r9, r10)
            r9.retainAll(r2)
            java.util.Set r10 = r9.keySet()
            r4.retainAll(r10)
            androidx.collection.ArrayMap r10 = new androidx.collection.ArrayMap
            r10.<init>()
            androidx.fragment.app.Fragment r14 = r8.mFragment
            android.view.View r14 = r14.mView
            findNamedViews(r10, r14)
            r10.retainAll(r7)
            java.util.Collection r14 = r4.values()
            r10.retainAll(r14)
            androidx.fragment.app.FragmentTransitionCompat21 r14 = androidx.fragment.app.FragmentTransition.PLATFORM_IMPL
            int r14 = r4.mSize
        L_0x0295:
            int r14 = r14 + -1
            if (r14 < 0) goto L_0x02a9
            java.lang.Object r15 = r4.valueAt(r14)
            java.lang.String r15 = (java.lang.String) r15
            boolean r15 = r10.containsKey(r15)
            if (r15 != 0) goto L_0x0295
            r4.removeAt(r14)
            goto L_0x0295
        L_0x02a9:
            java.util.Set r14 = r4.keySet()
            retainMatchingViews(r9, r14)
            java.util.Collection r14 = r4.values()
            retainMatchingViews(r10, r14)
            boolean r14 = r4.isEmpty()
            if (r14 == 0) goto L_0x02cf
            r13.clear()
            r12.clear()
            r2 = 0
            r10 = r2
            r7 = r12
            r14 = r13
            r9 = r16
            r2 = r17
            r15 = r26
            goto L_0x0363
        L_0x02cf:
            androidx.fragment.app.Fragment r8 = r8.mFragment
            androidx.fragment.app.Fragment r3 = r3.mFragment
            if (r1 == 0) goto L_0x02d9
            java.util.Objects.requireNonNull(r3)
            goto L_0x02dc
        L_0x02d9:
            java.util.Objects.requireNonNull(r8)
        L_0x02dc:
            android.view.ViewGroup r3 = r0.mContainer
            androidx.fragment.app.DefaultSpecialEffectsController$6 r8 = new androidx.fragment.app.DefaultSpecialEffectsController$6
            r8.<init>(r5, r1, r10)
            androidx.core.view.OneShotPreDrawListener.add(r3, r8)
            java.util.Collection r3 = r9.values()
            r13.addAll(r3)
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L_0x0309
            r3 = 0
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            r8 = 0
            java.lang.Object r2 = r9.getOrDefault(r2, r8)
            android.view.View r2 = (android.view.View) r2
            r9 = r27
            r11.setEpicenter((java.lang.Object) r9, (android.view.View) r2)
            r23 = r2
            goto L_0x030d
        L_0x0309:
            r9 = r27
            r3 = 0
            r8 = 0
        L_0x030d:
            java.util.Collection r2 = r10.values()
            r12.addAll(r2)
            boolean r2 = r7.isEmpty()
            if (r2 != 0) goto L_0x0338
            java.lang.Object r2 = r7.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r2 = r10.getOrDefault(r2, r8)
            android.view.View r2 = (android.view.View) r2
            if (r2 == 0) goto L_0x0338
            android.view.ViewGroup r3 = r0.mContainer
            androidx.fragment.app.DefaultSpecialEffectsController$7 r7 = new androidx.fragment.app.DefaultSpecialEffectsController$7
            r8 = r16
            r7.<init>(r2, r8)
            androidx.core.view.OneShotPreDrawListener.add(r3, r7)
            r2 = 1
            r19 = r2
            goto L_0x033a
        L_0x0338:
            r8 = r16
        L_0x033a:
            r2 = r17
            r11.setSharedElementTargets(r9, r2, r13)
            r14 = 0
            r15 = 0
            r7 = r12
            r12 = r11
            r3 = r13
            r13 = r9
            r16 = r9
            r17 = r7
            r12.scheduleRemoveTargets(r13, r14, r15, r16, r17)
            java.lang.Boolean r10 = java.lang.Boolean.TRUE
            r15 = r26
            r15.put(r5, r10)
            r15.put(r6, r10)
            r14 = r3
            r3 = r5
            r10 = r9
            r9 = r8
            r8 = r6
            goto L_0x0363
        L_0x035c:
            r21 = r7
            r7 = r12
            r2 = r15
            r15 = r9
            r9 = r14
            r14 = r13
        L_0x0363:
            r12 = r7
            r13 = r14
            r7 = r21
            r14 = r9
            r9 = r15
            r15 = r2
            r2 = r25
            goto L_0x0196
        L_0x036e:
            r25 = r2
            r21 = r7
            r7 = r12
            r2 = r15
            r15 = r9
            r9 = r14
            r14 = r13
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Iterator r5 = r21.iterator()
            r12 = 0
            r13 = 0
            r33 = r13
            r13 = r12
            r12 = r33
        L_0x0387:
            boolean r16 = r5.hasNext()
            if (r16 == 0) goto L_0x04c2
            java.lang.Object r16 = r5.next()
            r36 = r5
            r5 = r16
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r5 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r5
            boolean r16 = r5.isVisibilityUnchanged()
            if (r16 == 0) goto L_0x03c0
            r16 = r12
            androidx.fragment.app.SpecialEffectsController$Operation r12 = r5.mOperation
            r17 = r13
            java.lang.Boolean r13 = java.lang.Boolean.FALSE
            r15.put(r12, r13)
            r5.completeSpecialEffect()
            r32 = r2
            r18 = r4
            r26 = r7
            r31 = r14
            r2 = r15
            r12 = r16
            r29 = r17
            r15 = r20
            r4 = r23
            r13 = r25
            goto L_0x04ad
        L_0x03c0:
            r16 = r12
            r17 = r13
            java.lang.Object r12 = r5.mTransition
            java.lang.Object r13 = r11.cloneTransition(r12)
            androidx.fragment.app.SpecialEffectsController$Operation r12 = r5.mOperation
            if (r10 == 0) goto L_0x03d4
            if (r12 == r3) goto L_0x03d2
            if (r12 != r8) goto L_0x03d4
        L_0x03d2:
            r8 = 1
            goto L_0x03d5
        L_0x03d4:
            r8 = 0
        L_0x03d5:
            if (r13 != 0) goto L_0x03f6
            if (r8 != 0) goto L_0x03e1
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            r15.put(r12, r8)
            r5.completeSpecialEffect()
        L_0x03e1:
            r32 = r2
            r18 = r4
            r26 = r7
            r31 = r14
            r2 = r15
            r12 = r16
            r29 = r17
            r15 = r20
            r4 = r23
            r13 = r25
            goto L_0x04ac
        L_0x03f6:
            r18 = r4
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Objects.requireNonNull(r12)
            r26 = r15
            androidx.fragment.app.Fragment r15 = r12.mFragment
            android.view.View r15 = r15.mView
            captureTransitioningViews(r4, r15)
            if (r8 == 0) goto L_0x0414
            if (r12 != r3) goto L_0x0411
            r4.removeAll(r14)
            goto L_0x0414
        L_0x0411:
            r4.removeAll(r7)
        L_0x0414:
            boolean r8 = r4.isEmpty()
            if (r8 == 0) goto L_0x042e
            r11.addTarget(r13, r2)
            r32 = r2
            r8 = r12
            r12 = r13
            r31 = r14
            r29 = r17
            r13 = r25
            r2 = r26
            r26 = r7
            r7 = r16
            goto L_0x047f
        L_0x042e:
            r11.addTargets(r13, r4)
            r8 = 0
            r27 = 0
            r28 = r12
            r15 = r16
            r12 = r11
            r30 = r13
            r29 = r17
            r31 = r14
            r14 = r30
            r32 = r2
            r2 = r26
            r26 = r7
            r7 = r15
            r15 = r4
            r16 = r8
            r17 = r27
            r12.scheduleRemoveTargets(r13, r14, r15, r16, r17)
            r8 = r28
            androidx.fragment.app.SpecialEffectsController$Operation$State r12 = r8.mFinalState
            r13 = r25
            if (r12 != r13) goto L_0x047d
            r12 = r24
            r12.remove(r8)
            java.util.ArrayList r14 = new java.util.ArrayList
            r14.<init>(r4)
            androidx.fragment.app.Fragment r15 = r8.mFragment
            android.view.View r15 = r15.mView
            r14.remove(r15)
            androidx.fragment.app.Fragment r15 = r8.mFragment
            android.view.View r15 = r15.mView
            r12 = r30
            r11.scheduleHideFragmentView(r12, r15, r14)
            android.view.ViewGroup r14 = r0.mContainer
            androidx.fragment.app.DefaultSpecialEffectsController$8 r15 = new androidx.fragment.app.DefaultSpecialEffectsController$8
            r15.<init>(r4)
            androidx.core.view.OneShotPreDrawListener.add(r14, r15)
            goto L_0x047f
        L_0x047d:
            r12 = r30
        L_0x047f:
            androidx.fragment.app.SpecialEffectsController$Operation$State r14 = r8.mFinalState
            r15 = r20
            if (r14 != r15) goto L_0x0490
            r1.addAll(r4)
            if (r19 == 0) goto L_0x048d
            r11.setEpicenter((java.lang.Object) r12, (android.graphics.Rect) r9)
        L_0x048d:
            r4 = r23
            goto L_0x0495
        L_0x0490:
            r4 = r23
            r11.setEpicenter((java.lang.Object) r12, (android.view.View) r4)
        L_0x0495:
            java.lang.Boolean r14 = java.lang.Boolean.TRUE
            r2.put(r8, r14)
            boolean r5 = r5.mOverlapAllowed
            if (r5 == 0) goto L_0x04a3
            java.lang.Object r12 = r11.mergeTransitionsTogether(r7, r12)
            goto L_0x04ac
        L_0x04a3:
            r5 = r29
            java.lang.Object r5 = r11.mergeTransitionsTogether(r5, r12)
            r29 = r5
            r12 = r7
        L_0x04ac:
            r8 = r6
        L_0x04ad:
            r5 = r36
            r23 = r4
            r25 = r13
            r20 = r15
            r4 = r18
            r7 = r26
            r13 = r29
            r14 = r31
            r15 = r2
            r2 = r32
            goto L_0x0387
        L_0x04c2:
            r18 = r4
            r26 = r7
            r7 = r12
            r5 = r13
            r31 = r14
            r2 = r15
            r13 = r25
            java.lang.Object r4 = r11.mergeTransitionsInSequence(r7, r5, r10)
            java.util.Iterator r5 = r21.iterator()
        L_0x04d5:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x0545
            java.lang.Object r7 = r5.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r7 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r7
            boolean r8 = r7.isVisibilityUnchanged()
            if (r8 == 0) goto L_0x04e8
            goto L_0x04d5
        L_0x04e8:
            java.lang.Object r8 = r7.mTransition
            androidx.fragment.app.SpecialEffectsController$Operation r9 = r7.mOperation
            if (r10 == 0) goto L_0x04f4
            if (r9 == r3) goto L_0x04f2
            if (r9 != r6) goto L_0x04f4
        L_0x04f2:
            r12 = 1
            goto L_0x04f5
        L_0x04f4:
            r12 = 0
        L_0x04f5:
            if (r8 != 0) goto L_0x04fd
            if (r12 == 0) goto L_0x04fa
            goto L_0x04fd
        L_0x04fa:
            r9 = r35
            goto L_0x0542
        L_0x04fd:
            android.view.ViewGroup r8 = r0.mContainer
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r12 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r8 = androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r8)
            if (r8 != 0) goto L_0x0531
            r8 = 2
            boolean r8 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r8)
            if (r8 == 0) goto L_0x052b
            java.lang.String r8 = "SpecialEffectsController: Container "
            java.lang.StringBuilder r8 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r8)
            android.view.ViewGroup r12 = r0.mContainer
            r8.append(r12)
            java.lang.String r12 = " has not been laid out. Completing operation "
            r8.append(r12)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r9 = r35
            android.util.Log.v(r9, r8)
            goto L_0x052d
        L_0x052b:
            r9 = r35
        L_0x052d:
            r7.completeSpecialEffect()
            goto L_0x0542
        L_0x0531:
            r9 = r35
            androidx.fragment.app.SpecialEffectsController$Operation r8 = r7.mOperation
            java.util.Objects.requireNonNull(r8)
            androidx.core.os.CancellationSignal r8 = r7.mSignal
            androidx.fragment.app.DefaultSpecialEffectsController$9 r12 = new androidx.fragment.app.DefaultSpecialEffectsController$9
            r12.<init>()
            r11.setListenerForTransitionEnd(r4, r8, r12)
        L_0x0542:
            r35 = r9
            goto L_0x04d5
        L_0x0545:
            r9 = r35
            android.view.ViewGroup r3 = r0.mContainer
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r5 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r3 = androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r3)
            if (r3 != 0) goto L_0x0558
        L_0x0551:
            r1 = 0
            r35 = r9
            r25 = r13
            goto L_0x0607
        L_0x0558:
            r3 = 4
            androidx.fragment.app.FragmentTransition.setViewVisibility(r1, r3)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            int r5 = r26.size()
            r6 = 0
        L_0x0566:
            if (r6 >= r5) goto L_0x0580
            r7 = r26
            java.lang.Object r8 = r7.get(r6)
            android.view.View r8 = (android.view.View) r8
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r12 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            java.lang.String r12 = androidx.core.view.ViewCompat.Api21Impl.getTransitionName(r8)
            r3.add(r12)
            r12 = 0
            androidx.core.view.ViewCompat.Api21Impl.setTransitionName(r8, r12)
            int r6 = r6 + 1
            goto L_0x0566
        L_0x0580:
            r7 = r26
            android.view.ViewGroup r5 = r0.mContainer
            r11.beginDelayedTransition(r5, r4)
            android.view.ViewGroup r4 = r0.mContainer
            int r5 = r7.size()
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r8 = 0
        L_0x0593:
            if (r8 >= r5) goto L_0x05e5
            r12 = r31
            java.lang.Object r14 = r12.get(r8)
            android.view.View r14 = (android.view.View) r14
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r15 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            java.lang.String r15 = androidx.core.view.ViewCompat.Api21Impl.getTransitionName(r14)
            r6.add(r15)
            if (r15 != 0) goto L_0x05ad
            r35 = r9
            r25 = r13
            goto L_0x05dc
        L_0x05ad:
            r25 = r13
            r13 = 0
            androidx.core.view.ViewCompat.Api21Impl.setTransitionName(r14, r13)
            r14 = r18
            java.lang.Object r13 = r14.getOrDefault(r15, r13)
            java.lang.String r13 = (java.lang.String) r13
            r16 = 0
            r14 = r16
        L_0x05bf:
            r35 = r9
            if (r14 >= r5) goto L_0x05dc
            java.lang.Object r9 = r3.get(r14)
            boolean r9 = r13.equals(r9)
            if (r9 == 0) goto L_0x05d7
            java.lang.Object r9 = r7.get(r14)
            android.view.View r9 = (android.view.View) r9
            androidx.core.view.ViewCompat.Api21Impl.setTransitionName(r9, r15)
            goto L_0x05dc
        L_0x05d7:
            int r14 = r14 + 1
            r9 = r35
            goto L_0x05bf
        L_0x05dc:
            int r8 = r8 + 1
            r9 = r35
            r31 = r12
            r13 = r25
            goto L_0x0593
        L_0x05e5:
            r35 = r9
            r25 = r13
            r12 = r31
            androidx.fragment.app.FragmentTransitionImpl$1 r8 = new androidx.fragment.app.FragmentTransitionImpl$1
            r16 = r8
            r17 = r5
            r18 = r7
            r19 = r3
            r20 = r12
            r21 = r6
            r16.<init>(r17, r18, r19, r20, r21)
            androidx.core.view.OneShotPreDrawListener.add(r4, r8)
            r3 = 0
            androidx.fragment.app.FragmentTransition.setViewVisibility(r1, r3)
            r11.swapSharedElementTargets(r10, r12, r7)
            r1 = r3
        L_0x0607:
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            boolean r3 = r2.containsValue(r3)
            android.view.ViewGroup r0 = r0.mContainer
            android.content.Context r10 = r0.getContext()
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.Iterator r12 = r22.iterator()
            r4 = r1
        L_0x061d:
            boolean r5 = r12.hasNext()
            if (r5 == 0) goto L_0x06cd
            java.lang.Object r5 = r12.next()
            r13 = r5
            androidx.fragment.app.DefaultSpecialEffectsController$AnimationInfo r13 = (androidx.fragment.app.DefaultSpecialEffectsController.AnimationInfo) r13
            boolean r5 = r13.isVisibilityUnchanged()
            if (r5 == 0) goto L_0x0634
            r13.completeSpecialEffect()
            goto L_0x0645
        L_0x0634:
            androidx.fragment.app.FragmentAnim$AnimationOrAnimator r5 = r13.getAnimation(r10)
            if (r5 != 0) goto L_0x063e
            r13.completeSpecialEffect()
            goto L_0x0645
        L_0x063e:
            android.animation.Animator r14 = r5.animator
            if (r14 != 0) goto L_0x0648
            r11.add(r13)
        L_0x0645:
            r15 = r35
            goto L_0x0683
        L_0x0648:
            androidx.fragment.app.SpecialEffectsController$Operation r8 = r13.mOperation
            java.util.Objects.requireNonNull(r8)
            androidx.fragment.app.Fragment r5 = r8.mFragment
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            java.lang.Object r7 = r2.get(r8)
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0686
            r6 = 2
            boolean r6 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r6)
            if (r6 == 0) goto L_0x067e
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Ignoring Animator set on "
            r6.append(r7)
            r6.append(r5)
            java.lang.String r5 = " as this Fragment was involved in a Transition."
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r15 = r35
            android.util.Log.v(r15, r5)
            goto L_0x0680
        L_0x067e:
            r15 = r35
        L_0x0680:
            r13.completeSpecialEffect()
        L_0x0683:
            r35 = r15
            goto L_0x061d
        L_0x0686:
            r15 = r35
            androidx.fragment.app.SpecialEffectsController$Operation$State r4 = r8.mFinalState
            r9 = r25
            if (r4 != r9) goto L_0x0691
            r4 = 1
            r7 = r4
            goto L_0x0692
        L_0x0691:
            r7 = r1
        L_0x0692:
            r6 = r24
            if (r7 == 0) goto L_0x0699
            r6.remove(r8)
        L_0x0699:
            android.view.View r5 = r5.mView
            r0.startViewTransition(r5)
            androidx.fragment.app.DefaultSpecialEffectsController$2 r4 = new androidx.fragment.app.DefaultSpecialEffectsController$2
            r34 = r4
            r35 = r5
            r5 = r0
            r16 = r6
            r6 = r35
            r17 = r9
            r9 = r13
            r4.<init>(r5, r6, r7, r8, r9)
            r14.addListener(r4)
            r4 = r35
            r14.setTarget(r4)
            r14.start()
            androidx.core.os.CancellationSignal r4 = r13.mSignal
            androidx.fragment.app.DefaultSpecialEffectsController$3 r5 = new androidx.fragment.app.DefaultSpecialEffectsController$3
            r5.<init>(r14)
            r4.setOnCancelListener(r5)
            r4 = 1
            r35 = r15
            r24 = r16
            r25 = r17
            goto L_0x061d
        L_0x06cd:
            r15 = r35
            r16 = r24
            java.util.Iterator r1 = r11.iterator()
        L_0x06d5:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x076f
            java.lang.Object r2 = r1.next()
            androidx.fragment.app.DefaultSpecialEffectsController$AnimationInfo r2 = (androidx.fragment.app.DefaultSpecialEffectsController.AnimationInfo) r2
            java.util.Objects.requireNonNull(r2)
            androidx.fragment.app.SpecialEffectsController$Operation r5 = r2.mOperation
            java.util.Objects.requireNonNull(r5)
            androidx.fragment.app.Fragment r6 = r5.mFragment
            java.lang.String r7 = "Ignoring Animation set on "
            if (r3 == 0) goto L_0x0711
            r5 = 2
            boolean r5 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r5)
            if (r5 == 0) goto L_0x070d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            r5.append(r6)
            java.lang.String r6 = " as Animations cannot run alongside Transitions."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r15, r5)
        L_0x070d:
            r2.completeSpecialEffect()
            goto L_0x06d5
        L_0x0711:
            if (r4 == 0) goto L_0x0735
            r5 = 2
            boolean r5 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r5)
            if (r5 == 0) goto L_0x0731
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            r5.append(r6)
            java.lang.String r6 = " as Animations cannot run alongside Animators."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r15, r5)
        L_0x0731:
            r2.completeSpecialEffect()
            goto L_0x06d5
        L_0x0735:
            android.view.View r6 = r6.mView
            androidx.fragment.app.FragmentAnim$AnimationOrAnimator r7 = r2.getAnimation(r10)
            java.util.Objects.requireNonNull(r7)
            android.view.animation.Animation r7 = r7.animation
            java.util.Objects.requireNonNull(r7)
            androidx.fragment.app.SpecialEffectsController$Operation$State r5 = r5.mFinalState
            androidx.fragment.app.SpecialEffectsController$Operation$State r8 = androidx.fragment.app.SpecialEffectsController.Operation.State.REMOVED
            if (r5 == r8) goto L_0x0750
            r6.startAnimation(r7)
            r2.completeSpecialEffect()
            goto L_0x0763
        L_0x0750:
            r0.startViewTransition(r6)
            androidx.fragment.app.FragmentAnim$EndViewTransitionAnimation r5 = new androidx.fragment.app.FragmentAnim$EndViewTransitionAnimation
            r5.<init>(r7, r0, r6)
            androidx.fragment.app.DefaultSpecialEffectsController$4 r7 = new androidx.fragment.app.DefaultSpecialEffectsController$4
            r7.<init>(r0, r6, r2)
            r5.setAnimationListener(r7)
            r6.startAnimation(r5)
        L_0x0763:
            androidx.core.os.CancellationSignal r5 = r2.mSignal
            androidx.fragment.app.DefaultSpecialEffectsController$5 r7 = new androidx.fragment.app.DefaultSpecialEffectsController$5
            r7.<init>(r6, r0, r2)
            r5.setOnCancelListener(r7)
            goto L_0x06d5
        L_0x076f:
            java.util.Iterator r0 = r16.iterator()
        L_0x0773:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x078c
            java.lang.Object r1 = r0.next()
            androidx.fragment.app.SpecialEffectsController$Operation r1 = (androidx.fragment.app.SpecialEffectsController.Operation) r1
            java.util.Objects.requireNonNull(r1)
            androidx.fragment.app.Fragment r2 = r1.mFragment
            android.view.View r2 = r2.mView
            androidx.fragment.app.SpecialEffectsController$Operation$State r1 = r1.mFinalState
            r1.applyState(r2)
            goto L_0x0773
        L_0x078c:
            r16.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.executeOperations(java.util.ArrayList, boolean):void");
    }

    public static void retainMatchingViews(ArrayMap arrayMap, Collection collection) {
        Iterator it = ((ArrayMap.EntrySet) arrayMap.entrySet()).iterator();
        while (true) {
            ArrayMap.MapIterator mapIterator = (ArrayMap.MapIterator) it;
            if (mapIterator.hasNext()) {
                mapIterator.next();
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!collection.contains(ViewCompat.Api21Impl.getTransitionName((View) mapIterator.getValue()))) {
                    mapIterator.remove();
                }
            } else {
                return;
            }
        }
    }

    public DefaultSpecialEffectsController(ViewGroup viewGroup) {
        super(viewGroup);
    }
}
