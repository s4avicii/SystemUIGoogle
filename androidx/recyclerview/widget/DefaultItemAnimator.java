package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public final class DefaultItemAnimator extends SimpleItemAnimator {
    public static TimeInterpolator sDefaultInterpolator;
    public ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList<>();
    public ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList<>();
    public ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList<>();
    public ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList<>();
    public ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList<>();
    public ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList<>();
    public ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList<>();
    public ArrayList<ChangeInfo> mPendingChanges = new ArrayList<>();
    public ArrayList<MoveInfo> mPendingMoves = new ArrayList<>();
    public ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<>();
    public ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();

    public static class ChangeInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;
        public int toX;
        public int toY;

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ChangeInfo{oldHolder=");
            m.append(this.oldHolder);
            m.append(", newHolder=");
            m.append(this.newHolder);
            m.append(", fromX=");
            m.append(this.fromX);
            m.append(", fromY=");
            m.append(this.fromY);
            m.append(", toX=");
            m.append(this.toX);
            m.append(", toY=");
            return Insets$$ExternalSyntheticOutline0.m11m(m, this.toY, '}');
        }

        public ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
            this.oldHolder = viewHolder;
            this.newHolder = viewHolder2;
            this.fromX = i;
            this.fromY = i2;
            this.toX = i3;
            this.toY = i4;
        }
    }

    public static class MoveInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder holder;
        public int toX;
        public int toY;

        public MoveInfo(RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4) {
            this.holder = viewHolder;
            this.fromX = i;
            this.fromY = i2;
            this.toX = i3;
            this.toY = i4;
        }
    }

    public final boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
        if (viewHolder == viewHolder2) {
            return animateMove(viewHolder, i, i2, i3, i4);
        }
        float translationX = viewHolder.itemView.getTranslationX();
        float translationY = viewHolder.itemView.getTranslationY();
        float alpha = viewHolder.itemView.getAlpha();
        resetAnimation(viewHolder);
        viewHolder.itemView.setTranslationX(translationX);
        viewHolder.itemView.setTranslationY(translationY);
        viewHolder.itemView.setAlpha(alpha);
        resetAnimation(viewHolder2);
        viewHolder2.itemView.setTranslationX((float) (-((int) (((float) (i3 - i)) - translationX))));
        viewHolder2.itemView.setTranslationY((float) (-((int) (((float) (i4 - i2)) - translationY))));
        viewHolder2.itemView.setAlpha(0.0f);
        this.mPendingChanges.add(new ChangeInfo(viewHolder, viewHolder2, i, i2, i3, i4));
        return true;
    }

    public final boolean animateMove(RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4) {
        View view = viewHolder.itemView;
        int translationX = i + ((int) view.getTranslationX());
        int translationY = i2 + ((int) viewHolder.itemView.getTranslationY());
        resetAnimation(viewHolder);
        int i5 = i3 - translationX;
        int i6 = i4 - translationY;
        if (i5 == 0 && i6 == 0) {
            dispatchAnimationFinished(viewHolder);
            return false;
        }
        if (i5 != 0) {
            view.setTranslationX((float) (-i5));
        }
        if (i6 != 0) {
            view.setTranslationY((float) (-i6));
        }
        this.mPendingMoves.add(new MoveInfo(viewHolder, translationX, translationY, i3, i4));
        return true;
    }

    public final void endAnimation(RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        view.animate().cancel();
        int size = this.mPendingMoves.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else if (this.mPendingMoves.get(size).holder == viewHolder) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                dispatchAnimationFinished(viewHolder);
                this.mPendingMoves.remove(size);
            }
        }
        endChangeAnimation(this.mPendingChanges, viewHolder);
        if (this.mPendingRemovals.remove(viewHolder)) {
            view.setAlpha(1.0f);
            dispatchAnimationFinished(viewHolder);
        }
        if (this.mPendingAdditions.remove(viewHolder)) {
            view.setAlpha(1.0f);
            dispatchAnimationFinished(viewHolder);
        }
        int size2 = this.mChangesList.size();
        while (true) {
            size2--;
            if (size2 < 0) {
                break;
            }
            ArrayList arrayList = this.mChangesList.get(size2);
            endChangeAnimation(arrayList, viewHolder);
            if (arrayList.isEmpty()) {
                this.mChangesList.remove(size2);
            }
        }
        int size3 = this.mMovesList.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            ArrayList arrayList2 = this.mMovesList.get(size3);
            int size4 = arrayList2.size();
            while (true) {
                size4--;
                if (size4 < 0) {
                    break;
                } else if (((MoveInfo) arrayList2.get(size4)).holder == viewHolder) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    dispatchAnimationFinished(viewHolder);
                    arrayList2.remove(size4);
                    if (arrayList2.isEmpty()) {
                        this.mMovesList.remove(size3);
                    }
                }
            }
        }
        int size5 = this.mAdditionsList.size();
        while (true) {
            size5--;
            if (size5 >= 0) {
                ArrayList arrayList3 = this.mAdditionsList.get(size5);
                if (arrayList3.remove(viewHolder)) {
                    view.setAlpha(1.0f);
                    dispatchAnimationFinished(viewHolder);
                    if (arrayList3.isEmpty()) {
                        this.mAdditionsList.remove(size5);
                    }
                }
            } else {
                this.mRemoveAnimations.remove(viewHolder);
                this.mAddAnimations.remove(viewHolder);
                this.mChangeAnimations.remove(viewHolder);
                this.mMoveAnimations.remove(viewHolder);
                dispatchFinishedWhenDone();
                return;
            }
        }
    }

    public final void endAnimations() {
        int size = this.mPendingMoves.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            MoveInfo moveInfo = this.mPendingMoves.get(size);
            View view = moveInfo.holder.itemView;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            dispatchAnimationFinished(moveInfo.holder);
            this.mPendingMoves.remove(size);
        }
        int size2 = this.mPendingRemovals.size();
        while (true) {
            size2--;
            if (size2 < 0) {
                break;
            }
            dispatchAnimationFinished(this.mPendingRemovals.get(size2));
            this.mPendingRemovals.remove(size2);
        }
        int size3 = this.mPendingAdditions.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            RecyclerView.ViewHolder viewHolder = this.mPendingAdditions.get(size3);
            viewHolder.itemView.setAlpha(1.0f);
            dispatchAnimationFinished(viewHolder);
            this.mPendingAdditions.remove(size3);
        }
        int size4 = this.mPendingChanges.size();
        while (true) {
            size4--;
            if (size4 < 0) {
                break;
            }
            ChangeInfo changeInfo = this.mPendingChanges.get(size4);
            RecyclerView.ViewHolder viewHolder2 = changeInfo.oldHolder;
            if (viewHolder2 != null) {
                endChangeAnimationIfNecessary(changeInfo, viewHolder2);
            }
            RecyclerView.ViewHolder viewHolder3 = changeInfo.newHolder;
            if (viewHolder3 != null) {
                endChangeAnimationIfNecessary(changeInfo, viewHolder3);
            }
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            int size5 = this.mMovesList.size();
            while (true) {
                size5--;
                if (size5 < 0) {
                    break;
                }
                ArrayList arrayList = this.mMovesList.get(size5);
                int size6 = arrayList.size();
                while (true) {
                    size6--;
                    if (size6 >= 0) {
                        MoveInfo moveInfo2 = (MoveInfo) arrayList.get(size6);
                        View view2 = moveInfo2.holder.itemView;
                        view2.setTranslationY(0.0f);
                        view2.setTranslationX(0.0f);
                        dispatchAnimationFinished(moveInfo2.holder);
                        arrayList.remove(size6);
                        if (arrayList.isEmpty()) {
                            this.mMovesList.remove(arrayList);
                        }
                    }
                }
            }
            int size7 = this.mAdditionsList.size();
            while (true) {
                size7--;
                if (size7 < 0) {
                    break;
                }
                ArrayList arrayList2 = this.mAdditionsList.get(size7);
                int size8 = arrayList2.size();
                while (true) {
                    size8--;
                    if (size8 >= 0) {
                        RecyclerView.ViewHolder viewHolder4 = (RecyclerView.ViewHolder) arrayList2.get(size8);
                        viewHolder4.itemView.setAlpha(1.0f);
                        dispatchAnimationFinished(viewHolder4);
                        arrayList2.remove(size8);
                        if (arrayList2.isEmpty()) {
                            this.mAdditionsList.remove(arrayList2);
                        }
                    }
                }
            }
            int size9 = this.mChangesList.size();
            while (true) {
                size9--;
                if (size9 >= 0) {
                    ArrayList arrayList3 = this.mChangesList.get(size9);
                    int size10 = arrayList3.size();
                    while (true) {
                        size10--;
                        if (size10 >= 0) {
                            ChangeInfo changeInfo2 = (ChangeInfo) arrayList3.get(size10);
                            RecyclerView.ViewHolder viewHolder5 = changeInfo2.oldHolder;
                            if (viewHolder5 != null) {
                                endChangeAnimationIfNecessary(changeInfo2, viewHolder5);
                            }
                            RecyclerView.ViewHolder viewHolder6 = changeInfo2.newHolder;
                            if (viewHolder6 != null) {
                                endChangeAnimationIfNecessary(changeInfo2, viewHolder6);
                            }
                            if (arrayList3.isEmpty()) {
                                this.mChangesList.remove(arrayList3);
                            }
                        }
                    }
                } else {
                    cancelAll(this.mRemoveAnimations);
                    cancelAll(this.mMoveAnimations);
                    cancelAll(this.mAddAnimations);
                    cancelAll(this.mChangeAnimations);
                    dispatchAnimationsFinished();
                    return;
                }
            }
        }
    }

    public final boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder viewHolder) {
        if (changeInfo.newHolder == viewHolder) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder != viewHolder) {
            return false;
        } else {
            changeInfo.oldHolder = null;
        }
        viewHolder.itemView.setAlpha(1.0f);
        viewHolder.itemView.setTranslationX(0.0f);
        viewHolder.itemView.setTranslationY(0.0f);
        dispatchAnimationFinished(viewHolder);
        return true;
    }

    public final boolean isRunning() {
        if (!this.mPendingAdditions.isEmpty() || !this.mPendingChanges.isEmpty() || !this.mPendingMoves.isEmpty() || !this.mPendingRemovals.isEmpty() || !this.mMoveAnimations.isEmpty() || !this.mRemoveAnimations.isEmpty() || !this.mAddAnimations.isEmpty() || !this.mChangeAnimations.isEmpty() || !this.mMovesList.isEmpty() || !this.mAdditionsList.isEmpty() || !this.mChangesList.isEmpty()) {
            return true;
        }
        return false;
    }

    public final void resetAnimation(RecyclerView.ViewHolder viewHolder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        viewHolder.itemView.animate().setInterpolator(sDefaultInterpolator);
        endAnimation(viewHolder);
    }

    public final void runPendingAnimations() {
        long j;
        long j2;
        boolean z = !this.mPendingRemovals.isEmpty();
        boolean z2 = !this.mPendingMoves.isEmpty();
        boolean z3 = !this.mPendingChanges.isEmpty();
        boolean z4 = !this.mPendingAdditions.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator<RecyclerView.ViewHolder> it = this.mPendingRemovals.iterator();
            while (it.hasNext()) {
                final RecyclerView.ViewHolder next = it.next();
                final View view = next.itemView;
                final ViewPropertyAnimator animate = view.animate();
                this.mRemoveAnimations.add(next);
                animate.setDuration(this.mRemoveDuration).alpha(0.0f).setListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        animate.setListener((Animator.AnimatorListener) null);
                        view.setAlpha(1.0f);
                        DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                        RecyclerView.ViewHolder viewHolder = next;
                        Objects.requireNonNull(defaultItemAnimator);
                        defaultItemAnimator.dispatchAnimationFinished(viewHolder);
                        DefaultItemAnimator.this.mRemoveAnimations.remove(next);
                        DefaultItemAnimator.this.dispatchFinishedWhenDone();
                    }

                    public final void onAnimationStart(Animator animator) {
                        Objects.requireNonNull(DefaultItemAnimator.this);
                    }
                }).start();
            }
            this.mPendingRemovals.clear();
            if (z2) {
                final ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.mPendingMoves);
                this.mMovesList.add(arrayList);
                this.mPendingMoves.clear();
                C03091 r6 = new Runnable() {
                    public final void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            MoveInfo moveInfo = (MoveInfo) it.next();
                            DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                            RecyclerView.ViewHolder viewHolder = moveInfo.holder;
                            int i = moveInfo.fromX;
                            int i2 = moveInfo.fromY;
                            int i3 = moveInfo.toX;
                            int i4 = moveInfo.toY;
                            Objects.requireNonNull(defaultItemAnimator);
                            View view = viewHolder.itemView;
                            int i5 = i3 - i;
                            int i6 = i4 - i2;
                            if (i5 != 0) {
                                view.animate().translationX(0.0f);
                            }
                            if (i6 != 0) {
                                view.animate().translationY(0.0f);
                            }
                            ViewPropertyAnimator animate = view.animate();
                            defaultItemAnimator.mMoveAnimations.add(viewHolder);
                            animate.setDuration(defaultItemAnimator.mMoveDuration).setListener(new AnimatorListenerAdapter(viewHolder, i5, view, i6, animate) {
                                public final /* synthetic */ ViewPropertyAnimator val$animation;
                                public final /* synthetic */ int val$deltaX;
                                public final /* synthetic */ int val$deltaY;
                                public final /* synthetic */ RecyclerView.ViewHolder val$holder;
                                public final /* synthetic */ View val$view;

                                {
                                    this.val$holder = r2;
                                    this.val$deltaX = r3;
                                    this.val$view = r4;
                                    this.val$deltaY = r5;
                                    this.val$animation = r6;
                                }

                                public final void onAnimationCancel(Animator animator) {
                                    if (this.val$deltaX != 0) {
                                        this.val$view.setTranslationX(0.0f);
                                    }
                                    if (this.val$deltaY != 0) {
                                        this.val$view.setTranslationY(0.0f);
                                    }
                                }

                                public final void onAnimationEnd(Animator animator) {
                                    this.val$animation.setListener((Animator.AnimatorListener) null);
                                    DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                                    RecyclerView.ViewHolder viewHolder = this.val$holder;
                                    Objects.requireNonNull(defaultItemAnimator);
                                    defaultItemAnimator.dispatchAnimationFinished(viewHolder);
                                    DefaultItemAnimator.this.mMoveAnimations.remove(this.val$holder);
                                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                }

                                public final void onAnimationStart(Animator animator) {
                                    Objects.requireNonNull(DefaultItemAnimator.this);
                                }
                            }).start();
                        }
                        arrayList.clear();
                        DefaultItemAnimator.this.mMovesList.remove(arrayList);
                    }
                };
                if (z) {
                    View view2 = ((MoveInfo) arrayList.get(0)).holder.itemView;
                    long j3 = this.mRemoveDuration;
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postOnAnimationDelayed(view2, r6, j3);
                } else {
                    r6.run();
                }
            }
            if (z3) {
                final ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(this.mPendingChanges);
                this.mChangesList.add(arrayList2);
                this.mPendingChanges.clear();
                C03102 r62 = new Runnable() {
                    public final void run() {
                        View view;
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            ChangeInfo changeInfo = (ChangeInfo) it.next();
                            DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                            Objects.requireNonNull(defaultItemAnimator);
                            RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
                            View view2 = null;
                            if (viewHolder == null) {
                                view = null;
                            } else {
                                view = viewHolder.itemView;
                            }
                            RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
                            if (viewHolder2 != null) {
                                view2 = viewHolder2.itemView;
                            }
                            if (view != null) {
                                ViewPropertyAnimator duration = view.animate().setDuration(defaultItemAnimator.mChangeDuration);
                                defaultItemAnimator.mChangeAnimations.add(changeInfo.oldHolder);
                                duration.translationX((float) (changeInfo.toX - changeInfo.fromX));
                                duration.translationY((float) (changeInfo.toY - changeInfo.fromY));
                                duration.alpha(0.0f).setListener(new AnimatorListenerAdapter(changeInfo, duration, view) {
                                    public final /* synthetic */ ChangeInfo val$changeInfo;
                                    public final /* synthetic */ ViewPropertyAnimator val$oldViewAnim;
                                    public final /* synthetic */ View val$view;

                                    {
                                        this.val$changeInfo = r2;
                                        this.val$oldViewAnim = r3;
                                        this.val$view = r4;
                                    }

                                    public final void onAnimationEnd(Animator animator) {
                                        this.val$oldViewAnim.setListener((Animator.AnimatorListener) null);
                                        this.val$view.setAlpha(1.0f);
                                        this.val$view.setTranslationX(0.0f);
                                        this.val$view.setTranslationY(0.0f);
                                        DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                                        RecyclerView.ViewHolder viewHolder = this.val$changeInfo.oldHolder;
                                        Objects.requireNonNull(defaultItemAnimator);
                                        defaultItemAnimator.dispatchAnimationFinished(viewHolder);
                                        DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.oldHolder);
                                        DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                    }

                                    public final void onAnimationStart(Animator animator) {
                                        DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                                        RecyclerView.ViewHolder viewHolder = this.val$changeInfo.oldHolder;
                                        Objects.requireNonNull(defaultItemAnimator);
                                    }
                                }).start();
                            }
                            if (view2 != null) {
                                ViewPropertyAnimator animate = view2.animate();
                                defaultItemAnimator.mChangeAnimations.add(changeInfo.newHolder);
                                animate.translationX(0.0f).translationY(0.0f).setDuration(defaultItemAnimator.mChangeDuration).alpha(1.0f).setListener(new AnimatorListenerAdapter(changeInfo, animate, view2) {
                                    public final /* synthetic */ ChangeInfo val$changeInfo;
                                    public final /* synthetic */ View val$newView;
                                    public final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;

                                    {
                                        this.val$changeInfo = r2;
                                        this.val$newViewAnimation = r3;
                                        this.val$newView = r4;
                                    }

                                    public final void onAnimationEnd(Animator animator) {
                                        this.val$newViewAnimation.setListener((Animator.AnimatorListener) null);
                                        this.val$newView.setAlpha(1.0f);
                                        this.val$newView.setTranslationX(0.0f);
                                        this.val$newView.setTranslationY(0.0f);
                                        DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                                        RecyclerView.ViewHolder viewHolder = this.val$changeInfo.newHolder;
                                        Objects.requireNonNull(defaultItemAnimator);
                                        defaultItemAnimator.dispatchAnimationFinished(viewHolder);
                                        DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.newHolder);
                                        DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                    }

                                    public final void onAnimationStart(Animator animator) {
                                        DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                                        RecyclerView.ViewHolder viewHolder = this.val$changeInfo.newHolder;
                                        Objects.requireNonNull(defaultItemAnimator);
                                    }
                                }).start();
                            }
                        }
                        arrayList2.clear();
                        DefaultItemAnimator.this.mChangesList.remove(arrayList2);
                    }
                };
                if (z) {
                    View view3 = ((ChangeInfo) arrayList2.get(0)).oldHolder.itemView;
                    long j4 = this.mRemoveDuration;
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postOnAnimationDelayed(view3, r62, j4);
                } else {
                    r62.run();
                }
            }
            if (z4) {
                final ArrayList arrayList3 = new ArrayList();
                arrayList3.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(arrayList3);
                this.mPendingAdditions.clear();
                C03113 r5 = new Runnable() {
                    public final void run() {
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) it.next();
                            DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                            Objects.requireNonNull(defaultItemAnimator);
                            View view = viewHolder.itemView;
                            ViewPropertyAnimator animate = view.animate();
                            defaultItemAnimator.mAddAnimations.add(viewHolder);
                            animate.alpha(1.0f).setDuration(defaultItemAnimator.mAddDuration).setListener(new AnimatorListenerAdapter(viewHolder, view, animate) {
                                public final /* synthetic */ ViewPropertyAnimator val$animation;
                                public final /* synthetic */ RecyclerView.ViewHolder val$holder;
                                public final /* synthetic */ View val$view;

                                {
                                    this.val$holder = r2;
                                    this.val$view = r3;
                                    this.val$animation = r4;
                                }

                                public final void onAnimationCancel(Animator animator) {
                                    this.val$view.setAlpha(1.0f);
                                }

                                public final void onAnimationEnd(Animator animator) {
                                    this.val$animation.setListener((Animator.AnimatorListener) null);
                                    DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                                    RecyclerView.ViewHolder viewHolder = this.val$holder;
                                    Objects.requireNonNull(defaultItemAnimator);
                                    defaultItemAnimator.dispatchAnimationFinished(viewHolder);
                                    DefaultItemAnimator.this.mAddAnimations.remove(this.val$holder);
                                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                }

                                public final void onAnimationStart(Animator animator) {
                                    Objects.requireNonNull(DefaultItemAnimator.this);
                                }
                            }).start();
                        }
                        arrayList3.clear();
                        DefaultItemAnimator.this.mAdditionsList.remove(arrayList3);
                    }
                };
                if (z || z2 || z3) {
                    long j5 = 0;
                    if (z) {
                        j = this.mRemoveDuration;
                    } else {
                        j = 0;
                    }
                    if (z2) {
                        j2 = this.mMoveDuration;
                    } else {
                        j2 = 0;
                    }
                    if (z3) {
                        j5 = this.mChangeDuration;
                    }
                    View view4 = ((RecyclerView.ViewHolder) arrayList3.get(0)).itemView;
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postOnAnimationDelayed(view4, r5, Math.max(j2, j5) + j);
                    return;
                }
                r5.run();
            }
        }
    }

    public static void cancelAll(ArrayList arrayList) {
        int size = arrayList.size();
        while (true) {
            size--;
            if (size >= 0) {
                ((RecyclerView.ViewHolder) arrayList.get(size)).itemView.animate().cancel();
            } else {
                return;
            }
        }
    }

    public final void animateAdd(RecyclerView.ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        viewHolder.itemView.setAlpha(0.0f);
        this.mPendingAdditions.add(viewHolder);
    }

    public final void animateRemove(RecyclerView.ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        this.mPendingRemovals.add(viewHolder);
    }

    public final boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder, List<Object> list) {
        if (!list.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, list)) {
            return true;
        }
        return false;
    }

    public final void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public final void endChangeAnimation(ArrayList arrayList, RecyclerView.ViewHolder viewHolder) {
        int size = arrayList.size();
        while (true) {
            size--;
            if (size >= 0) {
                ChangeInfo changeInfo = (ChangeInfo) arrayList.get(size);
                if (endChangeAnimationIfNecessary(changeInfo, viewHolder) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                    arrayList.remove(changeInfo);
                }
            } else {
                return;
            }
        }
    }
}
