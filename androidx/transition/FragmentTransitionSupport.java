package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.p002os.CancellationSignal;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.transition.Transition;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressLint({"RestrictedApi"})
public class FragmentTransitionSupport extends FragmentTransitionImpl {
    public final void setEpicenter(Object obj, View view) {
        if (view != null) {
            final Rect rect = new Rect();
            FragmentTransitionImpl.getBoundsOnScreen(view, rect);
            ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback() {
                public final Rect onGetEpicenter() {
                    return rect;
                }
            });
        }
    }

    public final void addTarget(Object obj, View view) {
        ((Transition) obj).addTarget(view);
    }

    public final void addTargets(Object obj, ArrayList<View> arrayList) {
        Transition transition;
        Transition transition2 = (Transition) obj;
        if (transition2 != null) {
            int i = 0;
            if (transition2 instanceof TransitionSet) {
                TransitionSet transitionSet = (TransitionSet) transition2;
                int size = transitionSet.mTransitions.size();
                while (i < size) {
                    if (i < 0 || i >= transitionSet.mTransitions.size()) {
                        transition = null;
                    } else {
                        transition = transitionSet.mTransitions.get(i);
                    }
                    addTargets(transition, arrayList);
                    i++;
                }
            } else if (!hasSimpleTarget(transition2) && FragmentTransitionImpl.isNullOrEmpty(transition2.mTargets)) {
                int size2 = arrayList.size();
                while (i < size2) {
                    transition2.addTarget(arrayList.get(i));
                    i++;
                }
            }
        }
    }

    public final void beginDelayedTransition(ViewGroup viewGroup, Object obj) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition) obj);
    }

    public final Object cloneTransition(Object obj) {
        if (obj != null) {
            return ((Transition) obj).clone();
        }
        return null;
    }

    public final Object mergeTransitionsInSequence(Object obj, Object obj2, Object obj3) {
        Transition transition = (Transition) obj;
        Transition transition2 = (Transition) obj2;
        Transition transition3 = (Transition) obj3;
        if (transition != null && transition2 != null) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(transition);
            transitionSet.addTransition(transition2);
            transitionSet.setOrdering(1);
            transition = transitionSet;
        } else if (transition == null) {
            if (transition2 != null) {
                transition = transition2;
            } else {
                transition = null;
            }
        }
        if (transition3 == null) {
            return transition;
        }
        TransitionSet transitionSet2 = new TransitionSet();
        if (transition != null) {
            transitionSet2.addTransition(transition);
        }
        transitionSet2.addTransition(transition3);
        return transitionSet2;
    }

    public final Object mergeTransitionsTogether(Object obj, Object obj2) {
        TransitionSet transitionSet = new TransitionSet();
        if (obj != null) {
            transitionSet.addTransition((Transition) obj);
        }
        transitionSet.addTransition((Transition) obj2);
        return transitionSet;
    }

    public final void replaceTargets(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        int i;
        Transition transition;
        Transition transition2 = (Transition) obj;
        int i2 = 0;
        if (transition2 instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition2;
            Objects.requireNonNull(transitionSet);
            int size = transitionSet.mTransitions.size();
            while (i2 < size) {
                if (i2 < 0 || i2 >= transitionSet.mTransitions.size()) {
                    transition = null;
                } else {
                    transition = transitionSet.mTransitions.get(i2);
                }
                replaceTargets(transition, arrayList, arrayList2);
                i2++;
            }
        } else if (!hasSimpleTarget(transition2)) {
            ArrayList<View> arrayList3 = transition2.mTargets;
            if (arrayList3.size() == arrayList.size() && arrayList3.containsAll(arrayList)) {
                if (arrayList2 == null) {
                    i = 0;
                } else {
                    i = arrayList2.size();
                }
                while (i2 < i) {
                    transition2.addTarget(arrayList2.get(i2));
                    i2++;
                }
                int size2 = arrayList.size();
                while (true) {
                    size2--;
                    if (size2 >= 0) {
                        transition2.removeTarget(arrayList.get(size2));
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public final void scheduleHideFragmentView(Object obj, final View view, final ArrayList<View> arrayList) {
        ((Transition) obj).addListener(new Transition.TransitionListener() {
            public final void onTransitionCancel() {
            }

            public final void onTransitionPause() {
            }

            public final void onTransitionResume() {
            }

            public final void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                view.setVisibility(8);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((View) arrayList.get(i)).setVisibility(0);
                }
            }

            public final void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                transition.addListener(this);
            }
        });
    }

    public final void scheduleRemoveTargets(Object obj, Object obj2, ArrayList arrayList, Object obj3, ArrayList arrayList2) {
        final Object obj4 = obj2;
        final ArrayList arrayList3 = arrayList;
        final Object obj5 = obj3;
        final ArrayList arrayList4 = arrayList2;
        ((Transition) obj).addListener(new TransitionListenerAdapter() {
            public final /* synthetic */ Object val$exitTransition = null;
            public final /* synthetic */ ArrayList val$exitingViews = null;

            public final void onTransitionStart(Transition transition) {
                Object obj = obj4;
                if (obj != null) {
                    FragmentTransitionSupport.this.replaceTargets(obj, arrayList3, (ArrayList<View>) null);
                }
                Object obj2 = this.val$exitTransition;
                if (obj2 != null) {
                    FragmentTransitionSupport.this.replaceTargets(obj2, this.val$exitingViews, (ArrayList<View>) null);
                }
                Object obj3 = obj5;
                if (obj3 != null) {
                    FragmentTransitionSupport.this.replaceTargets(obj3, arrayList4, (ArrayList<View>) null);
                }
            }

            public final void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
            }
        });
    }

    public final void setListenerForTransitionEnd(Object obj, CancellationSignal cancellationSignal, final DefaultSpecialEffectsController.C01679 r3) {
        final Transition transition = (Transition) obj;
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            public final void onCancel() {
                Transition.this.cancel();
            }
        });
        transition.addListener(new Transition.TransitionListener() {
            public final void onTransitionCancel() {
            }

            public final void onTransitionPause() {
            }

            public final void onTransitionResume() {
            }

            public final void onTransitionStart(Transition transition) {
            }

            public final void onTransitionEnd(Transition transition) {
                r3.run();
            }
        });
    }

    public final void setSharedElementTargets(Object obj, View view, ArrayList<View> arrayList) {
        TransitionSet transitionSet = (TransitionSet) obj;
        Objects.requireNonNull(transitionSet);
        ArrayList<View> arrayList2 = transitionSet.mTargets;
        arrayList2.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            FragmentTransitionImpl.bfsAddViewChildren(arrayList2, arrayList.get(i));
        }
        arrayList2.add(view);
        arrayList.add(view);
        addTargets(transitionSet, arrayList);
    }

    public final void swapSharedElementTargets(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        TransitionSet transitionSet = (TransitionSet) obj;
        if (transitionSet != null) {
            transitionSet.mTargets.clear();
            transitionSet.mTargets.addAll(arrayList2);
            replaceTargets(transitionSet, arrayList, arrayList2);
        }
    }

    public final Object wrapTransitionInSet(Object obj) {
        if (obj == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition) obj);
        return transitionSet;
    }

    public static boolean hasSimpleTarget(Transition transition) {
        Objects.requireNonNull(transition);
        if (!FragmentTransitionImpl.isNullOrEmpty(transition.mTargetIds) || !FragmentTransitionImpl.isNullOrEmpty((List) null) || !FragmentTransitionImpl.isNullOrEmpty((List) null)) {
            return true;
        }
        return false;
    }

    public final void setEpicenter(Object obj, final Rect rect) {
        ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback() {
            public final Rect onGetEpicenter() {
                Rect rect = rect;
                if (rect == null || rect.isEmpty()) {
                    return null;
                }
                return rect;
            }
        });
    }

    public final boolean canHandle(Object obj) {
        return obj instanceof Transition;
    }
}
