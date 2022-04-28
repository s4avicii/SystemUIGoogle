package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.management.FavoritesModel$itemTouchHelperCallback$1;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public final class ItemTouchHelper extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener {
    public int mActionState = 0;
    public int mActivePointerId = -1;
    public Callback mCallback;
    public ArrayList mDistances;
    public long mDragScrollStartTimeInMs;
    public float mDx;
    public float mDy;
    public GestureDetectorCompat mGestureDetector;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public ItemTouchHelperGestureListener mItemTouchHelperGestureListener;
    public float mMaxSwipeVelocity;
    public final C03222 mOnItemTouchListener = new RecyclerView.OnItemTouchListener() {
        public final boolean onInterceptTouchEvent$1(MotionEvent motionEvent) {
            int findPointerIndex;
            ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            RecoverAnimation recoverAnimation = null;
            if (actionMasked == 0) {
                ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(0);
                ItemTouchHelper.this.mInitialTouchX = motionEvent.getX();
                ItemTouchHelper.this.mInitialTouchY = motionEvent.getY();
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                Objects.requireNonNull(itemTouchHelper);
                VelocityTracker velocityTracker = itemTouchHelper.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                }
                itemTouchHelper.mVelocityTracker = VelocityTracker.obtain();
                ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                if (itemTouchHelper2.mSelected == null) {
                    if (!itemTouchHelper2.mRecoverAnimations.isEmpty()) {
                        View findChildView = itemTouchHelper2.findChildView(motionEvent);
                        int size = itemTouchHelper2.mRecoverAnimations.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            RecoverAnimation recoverAnimation2 = itemTouchHelper2.mRecoverAnimations.get(size);
                            if (recoverAnimation2.mViewHolder.itemView == findChildView) {
                                recoverAnimation = recoverAnimation2;
                                break;
                            }
                            size--;
                        }
                    }
                    if (recoverAnimation != null) {
                        ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                        itemTouchHelper3.mInitialTouchX -= recoverAnimation.f26mX;
                        itemTouchHelper3.mInitialTouchY -= recoverAnimation.f27mY;
                        itemTouchHelper3.endRecoverAnimation(recoverAnimation.mViewHolder, true);
                        if (ItemTouchHelper.this.mPendingCleanup.remove(recoverAnimation.mViewHolder.itemView)) {
                            ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                            itemTouchHelper4.mCallback.clearView(itemTouchHelper4.mRecyclerView, recoverAnimation.mViewHolder);
                        }
                        ItemTouchHelper.this.select(recoverAnimation.mViewHolder, recoverAnimation.mActionState);
                        ItemTouchHelper itemTouchHelper5 = ItemTouchHelper.this;
                        itemTouchHelper5.updateDxDy(motionEvent, itemTouchHelper5.mSelectedFlags, 0);
                    }
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                ItemTouchHelper itemTouchHelper6 = ItemTouchHelper.this;
                itemTouchHelper6.mActivePointerId = -1;
                itemTouchHelper6.select((RecyclerView.ViewHolder) null, 0);
            } else {
                int i = ItemTouchHelper.this.mActivePointerId;
                if (i != -1 && (findPointerIndex = motionEvent.findPointerIndex(i)) >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe(actionMasked, motionEvent, findPointerIndex);
                }
            }
            VelocityTracker velocityTracker2 = ItemTouchHelper.this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent);
            }
            if (ItemTouchHelper.this.mSelected != null) {
                return true;
            }
            return false;
        }

        public final void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (z) {
                ItemTouchHelper.this.select((RecyclerView.ViewHolder) null, 0);
            }
        }

        public final void onTouchEvent(MotionEvent motionEvent) {
            ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
            VelocityTracker velocityTracker = ItemTouchHelper.this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            if (ItemTouchHelper.this.mActivePointerId != -1) {
                int actionMasked = motionEvent.getActionMasked();
                int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                if (findPointerIndex >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe(actionMasked, motionEvent, findPointerIndex);
                }
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                RecyclerView.ViewHolder viewHolder = itemTouchHelper.mSelected;
                if (viewHolder != null) {
                    int i = 0;
                    if (actionMasked != 1) {
                        if (actionMasked != 2) {
                            if (actionMasked == 3) {
                                VelocityTracker velocityTracker2 = itemTouchHelper.mVelocityTracker;
                                if (velocityTracker2 != null) {
                                    velocityTracker2.clear();
                                }
                            } else if (actionMasked == 6) {
                                int actionIndex = motionEvent.getActionIndex();
                                int pointerId = motionEvent.getPointerId(actionIndex);
                                ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                                if (pointerId == itemTouchHelper2.mActivePointerId) {
                                    if (actionIndex == 0) {
                                        i = 1;
                                    }
                                    itemTouchHelper2.mActivePointerId = motionEvent.getPointerId(i);
                                    ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                                    itemTouchHelper3.updateDxDy(motionEvent, itemTouchHelper3.mSelectedFlags, actionIndex);
                                    return;
                                }
                                return;
                            } else {
                                return;
                            }
                        } else if (findPointerIndex >= 0) {
                            itemTouchHelper.updateDxDy(motionEvent, itemTouchHelper.mSelectedFlags, findPointerIndex);
                            ItemTouchHelper.this.moveIfNecessary(viewHolder);
                            ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                            itemTouchHelper4.mRecyclerView.removeCallbacks(itemTouchHelper4.mScrollRunnable);
                            ItemTouchHelper.this.mScrollRunnable.run();
                            ItemTouchHelper.this.mRecyclerView.invalidate();
                            return;
                        } else {
                            return;
                        }
                    }
                    ItemTouchHelper.this.select((RecyclerView.ViewHolder) null, 0);
                    ItemTouchHelper.this.mActivePointerId = -1;
                }
            }
        }
    };
    public View mOverdrawChild = null;
    public final ArrayList mPendingCleanup = new ArrayList();
    public List<RecoverAnimation> mRecoverAnimations = new ArrayList();
    public RecyclerView mRecyclerView;
    public final C03211 mScrollRunnable = new Runnable() {
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0089  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00cc  */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x00e5  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x0100  */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x0103 A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x010f  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r16 = this;
                r0 = r16
                androidx.recyclerview.widget.ItemTouchHelper r1 = androidx.recyclerview.widget.ItemTouchHelper.this
                androidx.recyclerview.widget.RecyclerView$ViewHolder r2 = r1.mSelected
                if (r2 == 0) goto L_0x0134
                r3 = 0
                r4 = -9223372036854775808
                if (r2 != 0) goto L_0x0011
                r1.mDragScrollStartTimeInMs = r4
                goto L_0x0117
            L_0x0011:
                long r6 = java.lang.System.currentTimeMillis()
                long r8 = r1.mDragScrollStartTimeInMs
                int r2 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r2 != 0) goto L_0x001e
                r8 = 0
                goto L_0x0020
            L_0x001e:
                long r8 = r6 - r8
            L_0x0020:
                androidx.recyclerview.widget.RecyclerView r2 = r1.mRecyclerView
                java.util.Objects.requireNonNull(r2)
                androidx.recyclerview.widget.RecyclerView$LayoutManager r2 = r2.mLayout
                android.graphics.Rect r10 = r1.mTmpRect
                if (r10 != 0) goto L_0x0032
                android.graphics.Rect r10 = new android.graphics.Rect
                r10.<init>()
                r1.mTmpRect = r10
            L_0x0032:
                androidx.recyclerview.widget.RecyclerView$ViewHolder r10 = r1.mSelected
                android.view.View r10 = r10.itemView
                android.graphics.Rect r11 = r1.mTmpRect
                r2.calculateItemDecorationsForChild(r10, r11)
                boolean r10 = r2.canScrollHorizontally()
                r11 = 0
                if (r10 == 0) goto L_0x0082
                float r10 = r1.mSelectedStartX
                float r12 = r1.mDx
                float r10 = r10 + r12
                int r10 = (int) r10
                android.graphics.Rect r12 = r1.mTmpRect
                int r12 = r12.left
                int r12 = r10 - r12
                androidx.recyclerview.widget.RecyclerView r13 = r1.mRecyclerView
                int r13 = r13.getPaddingLeft()
                int r12 = r12 - r13
                float r13 = r1.mDx
                int r14 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
                if (r14 >= 0) goto L_0x005e
                if (r12 >= 0) goto L_0x005e
                goto L_0x0080
            L_0x005e:
                int r12 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
                if (r12 <= 0) goto L_0x0082
                androidx.recyclerview.widget.RecyclerView$ViewHolder r12 = r1.mSelected
                android.view.View r12 = r12.itemView
                int r12 = r12.getWidth()
                int r12 = r12 + r10
                android.graphics.Rect r10 = r1.mTmpRect
                int r10 = r10.right
                int r12 = r12 + r10
                androidx.recyclerview.widget.RecyclerView r10 = r1.mRecyclerView
                int r10 = r10.getWidth()
                androidx.recyclerview.widget.RecyclerView r13 = r1.mRecyclerView
                int r13 = r13.getPaddingRight()
                int r10 = r10 - r13
                int r12 = r12 - r10
                if (r12 <= 0) goto L_0x0082
            L_0x0080:
                r13 = r12
                goto L_0x0083
            L_0x0082:
                r13 = r3
            L_0x0083:
                boolean r2 = r2.canScrollVertically()
                if (r2 == 0) goto L_0x00c9
                float r2 = r1.mSelectedStartY
                float r10 = r1.mDy
                float r2 = r2 + r10
                int r2 = (int) r2
                android.graphics.Rect r10 = r1.mTmpRect
                int r10 = r10.top
                int r10 = r2 - r10
                androidx.recyclerview.widget.RecyclerView r12 = r1.mRecyclerView
                int r12 = r12.getPaddingTop()
                int r10 = r10 - r12
                float r12 = r1.mDy
                int r14 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
                if (r14 >= 0) goto L_0x00a5
                if (r10 >= 0) goto L_0x00a5
                goto L_0x00c7
            L_0x00a5:
                int r10 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
                if (r10 <= 0) goto L_0x00c9
                androidx.recyclerview.widget.RecyclerView$ViewHolder r10 = r1.mSelected
                android.view.View r10 = r10.itemView
                int r10 = r10.getHeight()
                int r10 = r10 + r2
                android.graphics.Rect r2 = r1.mTmpRect
                int r2 = r2.bottom
                int r10 = r10 + r2
                androidx.recyclerview.widget.RecyclerView r2 = r1.mRecyclerView
                int r2 = r2.getHeight()
                androidx.recyclerview.widget.RecyclerView r11 = r1.mRecyclerView
                int r11 = r11.getPaddingBottom()
                int r2 = r2 - r11
                int r10 = r10 - r2
                if (r10 <= 0) goto L_0x00c9
            L_0x00c7:
                r2 = r10
                goto L_0x00ca
            L_0x00c9:
                r2 = r3
            L_0x00ca:
                if (r13 == 0) goto L_0x00e2
                androidx.recyclerview.widget.ItemTouchHelper$Callback r10 = r1.mCallback
                androidx.recyclerview.widget.RecyclerView r11 = r1.mRecyclerView
                androidx.recyclerview.widget.RecyclerView$ViewHolder r12 = r1.mSelected
                android.view.View r12 = r12.itemView
                int r12 = r12.getWidth()
                androidx.recyclerview.widget.RecyclerView r14 = r1.mRecyclerView
                r14.getWidth()
                r14 = r8
                int r13 = r10.interpolateOutOfBoundsScroll(r11, r12, r13, r14)
            L_0x00e2:
                r14 = r13
                if (r2 == 0) goto L_0x0100
                androidx.recyclerview.widget.ItemTouchHelper$Callback r10 = r1.mCallback
                androidx.recyclerview.widget.RecyclerView r11 = r1.mRecyclerView
                androidx.recyclerview.widget.RecyclerView$ViewHolder r12 = r1.mSelected
                android.view.View r12 = r12.itemView
                int r12 = r12.getHeight()
                androidx.recyclerview.widget.RecyclerView r13 = r1.mRecyclerView
                r13.getHeight()
                r13 = r2
                r2 = r14
                r14 = r8
                int r8 = r10.interpolateOutOfBoundsScroll(r11, r12, r13, r14)
                r13 = r2
                r2 = r8
                goto L_0x0101
            L_0x0100:
                r13 = r14
            L_0x0101:
                if (r13 != 0) goto L_0x0109
                if (r2 == 0) goto L_0x0106
                goto L_0x0109
            L_0x0106:
                r1.mDragScrollStartTimeInMs = r4
                goto L_0x0117
            L_0x0109:
                long r8 = r1.mDragScrollStartTimeInMs
                int r3 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r3 != 0) goto L_0x0111
                r1.mDragScrollStartTimeInMs = r6
            L_0x0111:
                androidx.recyclerview.widget.RecyclerView r1 = r1.mRecyclerView
                r1.scrollBy(r13, r2)
                r3 = 1
            L_0x0117:
                if (r3 == 0) goto L_0x0134
                androidx.recyclerview.widget.ItemTouchHelper r1 = androidx.recyclerview.widget.ItemTouchHelper.this
                androidx.recyclerview.widget.RecyclerView$ViewHolder r2 = r1.mSelected
                if (r2 == 0) goto L_0x0122
                r1.moveIfNecessary(r2)
            L_0x0122:
                androidx.recyclerview.widget.ItemTouchHelper r1 = androidx.recyclerview.widget.ItemTouchHelper.this
                androidx.recyclerview.widget.RecyclerView r2 = r1.mRecyclerView
                androidx.recyclerview.widget.ItemTouchHelper$1 r1 = r1.mScrollRunnable
                r2.removeCallbacks(r1)
                androidx.recyclerview.widget.ItemTouchHelper r1 = androidx.recyclerview.widget.ItemTouchHelper.this
                androidx.recyclerview.widget.RecyclerView r1 = r1.mRecyclerView
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                androidx.core.view.ViewCompat.Api16Impl.postOnAnimation(r1, r0)
            L_0x0134:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ItemTouchHelper.C03211.run():void");
        }
    };
    public RecyclerView.ViewHolder mSelected = null;
    public int mSelectedFlags;
    public float mSelectedStartX;
    public float mSelectedStartY;
    public int mSlop;
    public ArrayList mSwapTargets;
    public float mSwipeEscapeVelocity;
    public final float[] mTmpPosition = new float[2];
    public Rect mTmpRect;
    public VelocityTracker mVelocityTracker;

    public static abstract class Callback {
        public static final C03251 sDragScrollInterpolator = new Interpolator() {
            public final float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        };
        public static final C03262 sDragViewScrollCapInterpolator = new Interpolator() {
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };
        public int mCachedMaxScrollSpeed = -1;

        public static int makeMovementFlags(int i, int i2) {
            return (i << 16) | (i2 << 8) | ((i2 | i) << 0);
        }

        public abstract boolean canDropOver(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2);

        public abstract int getMovementFlags(RecyclerView.ViewHolder viewHolder);

        public boolean isItemViewSwipeEnabled() {
            return !(this instanceof FavoritesModel$itemTouchHelperCallback$1);
        }

        public abstract boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2);

        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
        }

        public abstract void onSwiped();

        public static void onChildDraw(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, boolean z) {
            View view = viewHolder.itemView;
            if (z && view.getTag(C1777R.C1779id.item_touch_helper_previous_elevation) == null) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                Float valueOf = Float.valueOf(ViewCompat.Api21Impl.getElevation(view));
                int childCount = recyclerView.getChildCount();
                float f3 = 0.0f;
                for (int i = 0; i < childCount; i++) {
                    View childAt = recyclerView.getChildAt(i);
                    if (childAt != view) {
                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        float elevation = ViewCompat.Api21Impl.getElevation(childAt);
                        if (elevation > f3) {
                            f3 = elevation;
                        }
                    }
                }
                ViewCompat.Api21Impl.setElevation(view, f3 + 1.0f);
                view.setTag(C1777R.C1779id.item_touch_helper_previous_elevation, valueOf);
            }
            view.setTranslationX(f);
            view.setTranslationY(f2);
        }

        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            View view = viewHolder.itemView;
            Object tag = view.getTag(C1777R.C1779id.item_touch_helper_previous_elevation);
            if (tag instanceof Float) {
                float floatValue = ((Float) tag).floatValue();
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api21Impl.setElevation(view, floatValue);
            }
            view.setTag(C1777R.C1779id.item_touch_helper_previous_elevation, (Object) null);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
        }

        public final int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, long j) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(C1777R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            int i3 = this.mCachedMaxScrollSpeed;
            float f = 1.0f;
            int interpolation = (int) (sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (((float) Math.abs(i2)) * 1.0f) / ((float) i))) * ((float) (((int) Math.signum((float) i2)) * i3)));
            if (j <= 2000) {
                f = ((float) j) / 2000.0f;
            }
            int i4 = (int) (f * f * f * f * f * ((float) interpolation));
            if (i4 != 0) {
                return i4;
            }
            if (i2 > 0) {
                return 1;
            }
            return -1;
        }
    }

    public class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean mShouldReactToLongPress = true;

        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public ItemTouchHelperGestureListener() {
        }

        public final void onLongPress(MotionEvent motionEvent) {
            View findChildView;
            RecyclerView.ViewHolder childViewHolder;
            int i;
            int i2;
            if (this.mShouldReactToLongPress && (findChildView = ItemTouchHelper.this.findChildView(motionEvent)) != null && (childViewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(findChildView)) != null) {
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                Callback callback = itemTouchHelper.mCallback;
                RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
                Objects.requireNonNull(callback);
                int movementFlags = callback.getMovementFlags(childViewHolder);
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(recyclerView);
                int i3 = movementFlags & 3158064;
                boolean z = true;
                if (i3 != 0) {
                    int i4 = movementFlags & (~i3);
                    if (layoutDirection == 0) {
                        i2 = i3 >> 2;
                    } else {
                        int i5 = i3 >> 1;
                        i4 |= -3158065 & i5;
                        i2 = (i5 & 3158064) >> 2;
                    }
                    movementFlags = i4 | i2;
                }
                if ((16711680 & movementFlags) == 0) {
                    z = false;
                }
                if (z && motionEvent.getPointerId(0) == (i = ItemTouchHelper.this.mActivePointerId)) {
                    int findPointerIndex = motionEvent.findPointerIndex(i);
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                    itemTouchHelper2.mInitialTouchX = x;
                    itemTouchHelper2.mInitialTouchY = y;
                    itemTouchHelper2.mDy = 0.0f;
                    itemTouchHelper2.mDx = 0.0f;
                    Objects.requireNonNull(itemTouchHelper2.mCallback);
                    ItemTouchHelper.this.select(childViewHolder, 2);
                }
            }
        }
    }

    public static class RecoverAnimation implements Animator.AnimatorListener {
        public final int mActionState;
        public boolean mEnded = false;
        public float mFraction;
        public boolean mIsPendingCleanup;
        public boolean mOverridden = false;
        public final float mStartDx;
        public final float mStartDy;
        public final float mTargetX;
        public final float mTargetY;
        public final ValueAnimator mValueAnimator;
        public final RecyclerView.ViewHolder mViewHolder;

        /* renamed from: mX */
        public float f26mX;

        /* renamed from: mY */
        public float f27mY;

        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
        }

        public final void onAnimationCancel(Animator animator) {
            this.mFraction = 1.0f;
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        public RecoverAnimation(RecyclerView.ViewHolder viewHolder, int i, float f, float f2, float f3, float f4) {
            this.mActionState = i;
            this.mViewHolder = viewHolder;
            this.mStartDx = f;
            this.mStartDy = f2;
            this.mTargetX = f3;
            this.mTargetY = f4;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mValueAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RecoverAnimation recoverAnimation = RecoverAnimation.this;
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    Objects.requireNonNull(recoverAnimation);
                    recoverAnimation.mFraction = animatedFraction;
                }
            });
            ofFloat.setTarget(viewHolder.itemView);
            ofFloat.addListener(this);
            this.mFraction = 0.0f;
        }
    }

    public static abstract class SimpleCallback extends Callback {
        public int mDefaultDragDirs = 0;
        public int mDefaultSwipeDirs = 0;
    }

    public interface ViewDropHandler {
        void prepareForDrop(View view, View view2);
    }

    public final void onChildViewAttachedToWindow(View view) {
    }

    public static boolean hitTest(View view, float f, float f2, float f3, float f4) {
        if (f < f3 || f > f3 + ((float) view.getWidth()) || f2 < f4 || f2 > f4 + ((float) view.getHeight())) {
            return false;
        }
        return true;
    }

    public final void attachToRecyclerView(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                recyclerView2.removeItemDecoration(this);
                RecyclerView recyclerView3 = this.mRecyclerView;
                C03222 r1 = this.mOnItemTouchListener;
                Objects.requireNonNull(recyclerView3);
                recyclerView3.mOnItemTouchListeners.remove(r1);
                if (recyclerView3.mInterceptingOnItemTouchListener == r1) {
                    recyclerView3.mInterceptingOnItemTouchListener = null;
                }
                RecyclerView recyclerView4 = this.mRecyclerView;
                Objects.requireNonNull(recyclerView4);
                ArrayList arrayList = recyclerView4.mOnChildAttachStateListeners;
                if (arrayList != null) {
                    arrayList.remove(this);
                }
                int size = this.mRecoverAnimations.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(0);
                    Objects.requireNonNull(recoverAnimation);
                    recoverAnimation.mValueAnimator.cancel();
                    this.mCallback.clearView(this.mRecyclerView, recoverAnimation.mViewHolder);
                }
                this.mRecoverAnimations.clear();
                this.mOverdrawChild = null;
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                ItemTouchHelperGestureListener itemTouchHelperGestureListener = this.mItemTouchHelperGestureListener;
                if (itemTouchHelperGestureListener != null) {
                    itemTouchHelperGestureListener.mShouldReactToLongPress = false;
                    this.mItemTouchHelperGestureListener = null;
                }
                if (this.mGestureDetector != null) {
                    this.mGestureDetector = null;
                }
            }
            this.mRecyclerView = recyclerView;
            Resources resources = recyclerView.getResources();
            this.mSwipeEscapeVelocity = resources.getDimension(C1777R.dimen.item_touch_helper_swipe_escape_velocity);
            this.mMaxSwipeVelocity = resources.getDimension(C1777R.dimen.item_touch_helper_swipe_escape_max_velocity);
            this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
            this.mRecyclerView.addItemDecoration(this);
            RecyclerView recyclerView5 = this.mRecyclerView;
            C03222 r0 = this.mOnItemTouchListener;
            Objects.requireNonNull(recyclerView5);
            recyclerView5.mOnItemTouchListeners.add(r0);
            RecyclerView recyclerView6 = this.mRecyclerView;
            Objects.requireNonNull(recyclerView6);
            if (recyclerView6.mOnChildAttachStateListeners == null) {
                recyclerView6.mOnChildAttachStateListeners = new ArrayList();
            }
            recyclerView6.mOnChildAttachStateListeners.add(this);
            this.mItemTouchHelperGestureListener = new ItemTouchHelperGestureListener();
            this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), this.mItemTouchHelperGestureListener);
        }
    }

    public final int checkHorizontalSwipe(int i) {
        int i2;
        if ((i & 12) == 0) {
            return 0;
        }
        int i3 = 8;
        if (this.mDx > 0.0f) {
            i2 = 8;
        } else {
            i2 = 4;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null && this.mActivePointerId > -1) {
            Callback callback = this.mCallback;
            float f = this.mMaxSwipeVelocity;
            Objects.requireNonNull(callback);
            velocityTracker.computeCurrentVelocity(1000, f);
            float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
            float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
            if (xVelocity <= 0.0f) {
                i3 = 4;
            }
            float abs = Math.abs(xVelocity);
            if ((i3 & i) != 0 && i2 == i3) {
                Callback callback2 = this.mCallback;
                float f2 = this.mSwipeEscapeVelocity;
                Objects.requireNonNull(callback2);
                if (abs >= f2 && abs > Math.abs(yVelocity)) {
                    return i3;
                }
            }
        }
        Objects.requireNonNull(this.mCallback);
        float width = ((float) this.mRecyclerView.getWidth()) * 0.5f;
        if ((i & i2) == 0 || Math.abs(this.mDx) <= width) {
            return 0;
        }
        return i2;
    }

    public final void checkSelectForSwipe(int i, MotionEvent motionEvent, int i2) {
        int i3;
        View findChildView;
        if (this.mSelected == null && i == 2 && this.mActionState != 2 && this.mCallback.isItemViewSwipeEnabled()) {
            RecyclerView recyclerView = this.mRecyclerView;
            Objects.requireNonNull(recyclerView);
            if (recyclerView.mScrollState != 1) {
                RecyclerView recyclerView2 = this.mRecyclerView;
                Objects.requireNonNull(recyclerView2);
                RecyclerView.LayoutManager layoutManager = recyclerView2.mLayout;
                int i4 = this.mActivePointerId;
                RecyclerView.ViewHolder viewHolder = null;
                if (i4 != -1) {
                    int findPointerIndex = motionEvent.findPointerIndex(i4);
                    float abs = Math.abs(motionEvent.getX(findPointerIndex) - this.mInitialTouchX);
                    float abs2 = Math.abs(motionEvent.getY(findPointerIndex) - this.mInitialTouchY);
                    float f = (float) this.mSlop;
                    if ((abs >= f || abs2 >= f) && ((abs <= abs2 || !layoutManager.canScrollHorizontally()) && ((abs2 <= abs || !layoutManager.canScrollVertically()) && (findChildView = findChildView(motionEvent)) != null))) {
                        viewHolder = this.mRecyclerView.getChildViewHolder(findChildView);
                    }
                }
                if (viewHolder != null) {
                    Callback callback = this.mCallback;
                    RecyclerView recyclerView3 = this.mRecyclerView;
                    Objects.requireNonNull(callback);
                    int movementFlags = callback.getMovementFlags(viewHolder);
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(recyclerView3);
                    int i5 = movementFlags & 3158064;
                    if (i5 != 0) {
                        int i6 = movementFlags & (~i5);
                        if (layoutDirection == 0) {
                            i3 = i5 >> 2;
                        } else {
                            int i7 = i5 >> 1;
                            i6 |= -3158065 & i7;
                            i3 = (i7 & 3158064) >> 2;
                        }
                        movementFlags = i6 | i3;
                    }
                    int i8 = (movementFlags & 65280) >> 8;
                    if (i8 != 0) {
                        float x = motionEvent.getX(i2);
                        float y = motionEvent.getY(i2);
                        float f2 = x - this.mInitialTouchX;
                        float f3 = y - this.mInitialTouchY;
                        float abs3 = Math.abs(f2);
                        float abs4 = Math.abs(f3);
                        float f4 = (float) this.mSlop;
                        if (abs3 >= f4 || abs4 >= f4) {
                            if (abs3 > abs4) {
                                if (f2 < 0.0f && (i8 & 4) == 0) {
                                    return;
                                }
                                if (f2 > 0.0f && (i8 & 8) == 0) {
                                    return;
                                }
                            } else if (f3 < 0.0f && (i8 & 1) == 0) {
                                return;
                            } else {
                                if (f3 > 0.0f && (i8 & 2) == 0) {
                                    return;
                                }
                            }
                            this.mDy = 0.0f;
                            this.mDx = 0.0f;
                            this.mActivePointerId = motionEvent.getPointerId(0);
                            select(viewHolder, 1);
                        }
                    }
                }
            }
        }
    }

    public final int checkVerticalSwipe(int i) {
        int i2;
        if ((i & 3) == 0) {
            return 0;
        }
        int i3 = 2;
        if (this.mDy > 0.0f) {
            i2 = 2;
        } else {
            i2 = 1;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null && this.mActivePointerId > -1) {
            Callback callback = this.mCallback;
            float f = this.mMaxSwipeVelocity;
            Objects.requireNonNull(callback);
            velocityTracker.computeCurrentVelocity(1000, f);
            float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
            float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
            if (yVelocity <= 0.0f) {
                i3 = 1;
            }
            float abs = Math.abs(yVelocity);
            if ((i3 & i) != 0 && i3 == i2) {
                Callback callback2 = this.mCallback;
                float f2 = this.mSwipeEscapeVelocity;
                Objects.requireNonNull(callback2);
                if (abs >= f2 && abs > Math.abs(xVelocity)) {
                    return i3;
                }
            }
        }
        Objects.requireNonNull(this.mCallback);
        float height = ((float) this.mRecyclerView.getHeight()) * 0.5f;
        if ((i & i2) == 0 || Math.abs(this.mDy) <= height) {
            return 0;
        }
        return i2;
    }

    public final void endRecoverAnimation(RecyclerView.ViewHolder viewHolder, boolean z) {
        RecoverAnimation recoverAnimation;
        int size = this.mRecoverAnimations.size();
        do {
            size--;
            if (size >= 0) {
                recoverAnimation = this.mRecoverAnimations.get(size);
            } else {
                return;
            }
        } while (recoverAnimation.mViewHolder != viewHolder);
        recoverAnimation.mOverridden |= z;
        if (!recoverAnimation.mEnded) {
            recoverAnimation.mValueAnimator.cancel();
        }
        this.mRecoverAnimations.remove(size);
    }

    public final void getSelectedDxDy(float[] fArr) {
        if ((this.mSelectedFlags & 12) != 0) {
            fArr[0] = (this.mSelectedStartX + this.mDx) - ((float) this.mSelected.itemView.getLeft());
        } else {
            fArr[0] = this.mSelected.itemView.getTranslationX();
        }
        if ((this.mSelectedFlags & 3) != 0) {
            fArr[1] = (this.mSelectedStartY + this.mDy) - ((float) this.mSelected.itemView.getTop());
        } else {
            fArr[1] = this.mSelected.itemView.getTranslationY();
        }
    }

    public final void moveIfNecessary(RecyclerView.ViewHolder viewHolder) {
        ArrayList arrayList;
        int bottom;
        int abs;
        int top;
        int abs2;
        int left;
        int abs3;
        int right;
        int abs4;
        int i;
        int i2;
        RecyclerView.ViewHolder viewHolder2 = viewHolder;
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            Objects.requireNonNull(this.mCallback);
            int i3 = (int) (this.mSelectedStartX + this.mDx);
            int i4 = (int) (this.mSelectedStartY + this.mDy);
            if (((float) Math.abs(i4 - viewHolder2.itemView.getTop())) >= ((float) viewHolder2.itemView.getHeight()) * 0.5f || ((float) Math.abs(i3 - viewHolder2.itemView.getLeft())) >= ((float) viewHolder2.itemView.getWidth()) * 0.5f) {
                ArrayList arrayList2 = this.mSwapTargets;
                if (arrayList2 == null) {
                    this.mSwapTargets = new ArrayList();
                    this.mDistances = new ArrayList();
                } else {
                    arrayList2.clear();
                    this.mDistances.clear();
                }
                Objects.requireNonNull(this.mCallback);
                int round = Math.round(this.mSelectedStartX + this.mDx) - 0;
                int round2 = Math.round(this.mSelectedStartY + this.mDy) - 0;
                int width = viewHolder2.itemView.getWidth() + round + 0;
                int height = viewHolder2.itemView.getHeight() + round2 + 0;
                int i5 = (round + width) / 2;
                int i6 = (round2 + height) / 2;
                RecyclerView recyclerView = this.mRecyclerView;
                Objects.requireNonNull(recyclerView);
                RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
                int childCount = layoutManager.getChildCount();
                int i7 = 0;
                while (i7 < childCount) {
                    View childAt = layoutManager.getChildAt(i7);
                    if (childAt != viewHolder2.itemView && childAt.getBottom() >= round2 && childAt.getTop() <= height && childAt.getRight() >= round && childAt.getLeft() <= width) {
                        RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
                        i2 = round;
                        if (this.mCallback.canDropOver(this.mSelected, childViewHolder)) {
                            int abs5 = Math.abs(i5 - ((childAt.getRight() + childAt.getLeft()) / 2));
                            int abs6 = Math.abs(i6 - ((childAt.getBottom() + childAt.getTop()) / 2));
                            int i8 = (abs6 * abs6) + (abs5 * abs5);
                            int size = this.mSwapTargets.size();
                            i = round2;
                            int i9 = 0;
                            int i10 = 0;
                            while (i9 < size) {
                                int i11 = size;
                                if (i8 <= ((Integer) this.mDistances.get(i9)).intValue()) {
                                    break;
                                }
                                i10++;
                                i9++;
                                size = i11;
                            }
                            this.mSwapTargets.add(i10, childViewHolder);
                            this.mDistances.add(i10, Integer.valueOf(i8));
                            i7++;
                            round = i2;
                            round2 = i;
                        }
                    } else {
                        i2 = round;
                    }
                    i = round2;
                    i7++;
                    round = i2;
                    round2 = i;
                }
                ArrayList arrayList3 = this.mSwapTargets;
                if (arrayList3.size() != 0) {
                    Objects.requireNonNull(this.mCallback);
                    int width2 = viewHolder2.itemView.getWidth() + i3;
                    int height2 = viewHolder2.itemView.getHeight() + i4;
                    int left2 = i3 - viewHolder2.itemView.getLeft();
                    int top2 = i4 - viewHolder2.itemView.getTop();
                    int size2 = arrayList3.size();
                    int i12 = -1;
                    RecyclerView.ViewHolder viewHolder3 = null;
                    int i13 = 0;
                    while (i13 < size2) {
                        RecyclerView.ViewHolder viewHolder4 = (RecyclerView.ViewHolder) arrayList3.get(i13);
                        if (left2 <= 0 || (right = viewHolder4.itemView.getRight() - width2) >= 0) {
                            arrayList = arrayList3;
                        } else {
                            arrayList = arrayList3;
                            if (viewHolder4.itemView.getRight() > viewHolder2.itemView.getRight() && (abs4 = Math.abs(right)) > i12) {
                                i12 = abs4;
                                viewHolder3 = viewHolder4;
                            }
                        }
                        if (left2 < 0 && (left = viewHolder4.itemView.getLeft() - i3) > 0 && viewHolder4.itemView.getLeft() < viewHolder2.itemView.getLeft() && (abs3 = Math.abs(left)) > i12) {
                            i12 = abs3;
                            viewHolder3 = viewHolder4;
                        }
                        if (top2 < 0 && (top = viewHolder4.itemView.getTop() - i4) > 0 && viewHolder4.itemView.getTop() < viewHolder2.itemView.getTop() && (abs2 = Math.abs(top)) > i12) {
                            i12 = abs2;
                            viewHolder3 = viewHolder4;
                        }
                        if (top2 > 0 && (bottom = viewHolder4.itemView.getBottom() - height2) < 0 && viewHolder4.itemView.getBottom() > viewHolder2.itemView.getBottom() && (abs = Math.abs(bottom)) > i12) {
                            i12 = abs;
                            viewHolder3 = viewHolder4;
                        }
                        i13++;
                        arrayList3 = arrayList;
                    }
                    if (viewHolder3 == null) {
                        this.mSwapTargets.clear();
                        this.mDistances.clear();
                        return;
                    }
                    int absoluteAdapterPosition = viewHolder3.getAbsoluteAdapterPosition();
                    viewHolder.getAbsoluteAdapterPosition();
                    if (this.mCallback.onMove(viewHolder2, viewHolder3)) {
                        Callback callback = this.mCallback;
                        RecyclerView recyclerView2 = this.mRecyclerView;
                        Objects.requireNonNull(callback);
                        Objects.requireNonNull(recyclerView2);
                        RecyclerView.LayoutManager layoutManager2 = recyclerView2.mLayout;
                        if (layoutManager2 instanceof ViewDropHandler) {
                            ((ViewDropHandler) layoutManager2).prepareForDrop(viewHolder2.itemView, viewHolder3.itemView);
                            return;
                        }
                        if (layoutManager2.canScrollHorizontally()) {
                            if (layoutManager2.getDecoratedLeft(viewHolder3.itemView) <= recyclerView2.getPaddingLeft()) {
                                recyclerView2.scrollToPosition(absoluteAdapterPosition);
                            }
                            if (layoutManager2.getDecoratedRight(viewHolder3.itemView) >= recyclerView2.getWidth() - recyclerView2.getPaddingRight()) {
                                recyclerView2.scrollToPosition(absoluteAdapterPosition);
                            }
                        }
                        if (layoutManager2.canScrollVertically()) {
                            if (layoutManager2.getDecoratedTop(viewHolder3.itemView) <= recyclerView2.getPaddingTop()) {
                                recyclerView2.scrollToPosition(absoluteAdapterPosition);
                            }
                            if (layoutManager2.getDecoratedBottom(viewHolder3.itemView) >= recyclerView2.getHeight() - recyclerView2.getPaddingBottom()) {
                                recyclerView2.scrollToPosition(absoluteAdapterPosition);
                            }
                        }
                    }
                }
            }
        }
    }

    public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
        float f;
        float f2 = 0.0f;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            float[] fArr = this.mTmpPosition;
            float f3 = fArr[0];
            f2 = fArr[1];
            f = f3;
        } else {
            f = 0.0f;
        }
        Callback callback = this.mCallback;
        RecyclerView.ViewHolder viewHolder = this.mSelected;
        List<RecoverAnimation> list = this.mRecoverAnimations;
        Objects.requireNonNull(callback);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            RecoverAnimation recoverAnimation = list.get(i);
            Objects.requireNonNull(recoverAnimation);
            float f4 = recoverAnimation.mStartDx;
            float f5 = recoverAnimation.mTargetX;
            if (f4 == f5) {
                recoverAnimation.f26mX = recoverAnimation.mViewHolder.itemView.getTranslationX();
            } else {
                recoverAnimation.f26mX = MotionController$$ExternalSyntheticOutline0.m7m(f5, f4, recoverAnimation.mFraction, f4);
            }
            float f6 = recoverAnimation.mStartDy;
            float f7 = recoverAnimation.mTargetY;
            if (f6 == f7) {
                recoverAnimation.f27mY = recoverAnimation.mViewHolder.itemView.getTranslationY();
            } else {
                recoverAnimation.f27mY = MotionController$$ExternalSyntheticOutline0.m7m(f7, f6, recoverAnimation.mFraction, f6);
            }
            int save = canvas.save();
            Callback.onChildDraw(recyclerView, recoverAnimation.mViewHolder, recoverAnimation.f26mX, recoverAnimation.f27mY, false);
            canvas.restoreToCount(save);
        }
        if (viewHolder != null) {
            int save2 = canvas.save();
            Callback.onChildDraw(recyclerView, viewHolder, f, f2, true);
            canvas.restoreToCount(save2);
        }
    }

    public final void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        boolean z = false;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            float[] fArr = this.mTmpPosition;
            float f = fArr[0];
            float f2 = fArr[1];
        }
        Callback callback = this.mCallback;
        RecyclerView.ViewHolder viewHolder = this.mSelected;
        List<RecoverAnimation> list = this.mRecoverAnimations;
        Objects.requireNonNull(callback);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int save = canvas.save();
            View view = list.get(i).mViewHolder.itemView;
            canvas.restoreToCount(save);
        }
        if (viewHolder != null) {
            canvas.restoreToCount(canvas.save());
        }
        for (int i2 = size - 1; i2 >= 0; i2--) {
            RecoverAnimation recoverAnimation = list.get(i2);
            boolean z2 = recoverAnimation.mEnded;
            if (z2 && !recoverAnimation.mIsPendingCleanup) {
                list.remove(i2);
            } else if (!z2) {
                z = true;
            }
        }
        if (z) {
            recyclerView.invalidate();
        }
    }

    public final void removeChildDrawingOrderCallbackIfNecessary(View view) {
        if (view == this.mOverdrawChild) {
            this.mOverdrawChild = null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b2, code lost:
        if (r0 == 0) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b7, code lost:
        r0 = r1 << 1;
        r2 = r2 | (r0 & -789517);
        r0 = (r0 & 789516) << 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c5, code lost:
        if (r2 > 0) goto L_0x00e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e5, code lost:
        if (r0 == 0) goto L_0x00b4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0205  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x020f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void select(androidx.recyclerview.widget.RecyclerView.ViewHolder r24, int r25) {
        /*
            r23 = this;
            r11 = r23
            r12 = r24
            r13 = r25
            androidx.recyclerview.widget.RecyclerView$ViewHolder r0 = r11.mSelected
            if (r12 != r0) goto L_0x000f
            int r0 = r11.mActionState
            if (r13 != r0) goto L_0x000f
            return
        L_0x000f:
            r0 = -9223372036854775808
            r11.mDragScrollStartTimeInMs = r0
            int r4 = r11.mActionState
            r14 = 1
            r11.endRecoverAnimation(r12, r14)
            r11.mActionState = r13
            r15 = 2
            if (r13 != r15) goto L_0x002d
            if (r12 == 0) goto L_0x0025
            android.view.View r0 = r12.itemView
            r11.mOverdrawChild = r0
            goto L_0x002d
        L_0x0025:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Must pass a ViewHolder when dragging"
            r0.<init>(r1)
            throw r0
        L_0x002d:
            int r0 = r13 * 8
            r10 = 8
            int r0 = r0 + r10
            int r0 = r14 << r0
            int r16 = r0 + -1
            androidx.recyclerview.widget.RecyclerView$ViewHolder r9 = r11.mSelected
            r17 = -3158065(0xffffffffffcfcfcf, float:NaN)
            r18 = 3158064(0x303030, float:4.42539E-39)
            r8 = 0
            if (r9 == 0) goto L_0x01aa
            android.view.View r0 = r9.itemView
            android.view.ViewParent r0 = r0.getParent()
            r7 = 0
            if (r0 == 0) goto L_0x0197
            if (r4 != r15) goto L_0x004f
            r6 = r8
            goto L_0x00ea
        L_0x004f:
            int r0 = r11.mActionState
            if (r0 != r15) goto L_0x0055
            goto L_0x00e8
        L_0x0055:
            androidx.recyclerview.widget.ItemTouchHelper$Callback r0 = r11.mCallback
            int r0 = r0.getMovementFlags(r9)
            androidx.recyclerview.widget.ItemTouchHelper$Callback r1 = r11.mCallback
            androidx.recyclerview.widget.RecyclerView r2 = r11.mRecyclerView
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r2 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r2)
            java.util.Objects.requireNonNull(r1)
            r1 = r0 & r18
            if (r1 != 0) goto L_0x006e
            r1 = r0
            goto L_0x007b
        L_0x006e:
            int r3 = ~r1
            r3 = r3 & r0
            if (r2 != 0) goto L_0x0073
            goto L_0x0079
        L_0x0073:
            int r1 = r1 >> r14
            r2 = r1 & r17
            r3 = r3 | r2
            r1 = r1 & r18
        L_0x0079:
            int r1 = r1 >> r15
            r1 = r1 | r3
        L_0x007b:
            r2 = 65280(0xff00, float:9.1477E-41)
            r1 = r1 & r2
            int r1 = r1 >> r10
            if (r1 != 0) goto L_0x0084
            goto L_0x00e8
        L_0x0084:
            r0 = r0 & r2
            int r0 = r0 >> r10
            float r2 = r11.mDx
            float r2 = java.lang.Math.abs(r2)
            float r3 = r11.mDy
            float r3 = java.lang.Math.abs(r3)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            r3 = -789517(0xfffffffffff3f3f3, float:NaN)
            r5 = 789516(0xc0c0c, float:1.106348E-39)
            if (r2 <= 0) goto L_0x00c8
            int r2 = r11.checkHorizontalSwipe(r1)
            if (r2 <= 0) goto L_0x00c1
            r0 = r0 & r2
            if (r0 != 0) goto L_0x00e9
            androidx.recyclerview.widget.RecyclerView r0 = r11.mRecyclerView
            int r0 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r0)
            r1 = r2 & r5
            if (r1 != 0) goto L_0x00b0
            goto L_0x00e9
        L_0x00b0:
            int r6 = ~r1
            r2 = r2 & r6
            if (r0 != 0) goto L_0x00b7
        L_0x00b4:
            int r0 = r1 << 2
            goto L_0x00be
        L_0x00b7:
            int r0 = r1 << 1
            r1 = r0 & r3
            r2 = r2 | r1
            r0 = r0 & r5
            int r0 = r0 << r15
        L_0x00be:
            r0 = r0 | r2
            r2 = r0
            goto L_0x00e9
        L_0x00c1:
            int r2 = r11.checkVerticalSwipe(r1)
            if (r2 <= 0) goto L_0x00e8
            goto L_0x00e9
        L_0x00c8:
            int r2 = r11.checkVerticalSwipe(r1)
            if (r2 <= 0) goto L_0x00cf
            goto L_0x00e9
        L_0x00cf:
            int r2 = r11.checkHorizontalSwipe(r1)
            if (r2 <= 0) goto L_0x00e8
            r0 = r0 & r2
            if (r0 != 0) goto L_0x00e9
            androidx.recyclerview.widget.RecyclerView r0 = r11.mRecyclerView
            int r0 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r0)
            r1 = r2 & r5
            if (r1 != 0) goto L_0x00e3
            goto L_0x00e9
        L_0x00e3:
            int r6 = ~r1
            r2 = r2 & r6
            if (r0 != 0) goto L_0x00b7
            goto L_0x00b4
        L_0x00e8:
            r2 = r8
        L_0x00e9:
            r6 = r2
        L_0x00ea:
            android.view.VelocityTracker r0 = r11.mVelocityTracker
            if (r0 == 0) goto L_0x00f3
            r0.recycle()
            r11.mVelocityTracker = r7
        L_0x00f3:
            r0 = 4
            r1 = 0
            if (r6 == r14) goto L_0x011d
            if (r6 == r15) goto L_0x011d
            if (r6 == r0) goto L_0x010a
            if (r6 == r10) goto L_0x010a
            r2 = 16
            if (r6 == r2) goto L_0x010a
            r2 = 32
            if (r6 == r2) goto L_0x010a
            r19 = r1
            r20 = r19
            goto L_0x012f
        L_0x010a:
            float r2 = r11.mDx
            float r2 = java.lang.Math.signum(r2)
            androidx.recyclerview.widget.RecyclerView r3 = r11.mRecyclerView
            int r3 = r3.getWidth()
            float r3 = (float) r3
            float r2 = r2 * r3
            r20 = r1
            r19 = r2
            goto L_0x012f
        L_0x011d:
            float r2 = r11.mDy
            float r2 = java.lang.Math.signum(r2)
            androidx.recyclerview.widget.RecyclerView r3 = r11.mRecyclerView
            int r3 = r3.getHeight()
            float r3 = (float) r3
            float r2 = r2 * r3
            r19 = r1
            r20 = r2
        L_0x012f:
            if (r4 != r15) goto L_0x0133
            r5 = r10
            goto L_0x0138
        L_0x0133:
            if (r6 <= 0) goto L_0x0137
            r5 = r15
            goto L_0x0138
        L_0x0137:
            r5 = r0
        L_0x0138:
            float[] r0 = r11.mTmpPosition
            r11.getSelectedDxDy(r0)
            float[] r0 = r11.mTmpPosition
            r21 = r0[r8]
            r22 = r0[r14]
            androidx.recyclerview.widget.ItemTouchHelper$3 r3 = new androidx.recyclerview.widget.ItemTouchHelper$3
            r0 = r3
            r1 = r23
            r2 = r9
            r14 = r3
            r3 = r5
            r15 = r5
            r5 = r21
            r21 = r6
            r6 = r22
            r13 = r7
            r7 = r19
            r13 = r8
            r8 = r20
            r20 = r9
            r9 = r21
            r13 = r10
            r10 = r20
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            androidx.recyclerview.widget.ItemTouchHelper$Callback r0 = r11.mCallback
            androidx.recyclerview.widget.RecyclerView r1 = r11.mRecyclerView
            java.util.Objects.requireNonNull(r0)
            java.util.Objects.requireNonNull(r1)
            androidx.recyclerview.widget.RecyclerView$ItemAnimator r0 = r1.mItemAnimator
            if (r0 != 0) goto L_0x0178
            if (r15 != r13) goto L_0x0175
            r0 = 200(0xc8, double:9.9E-322)
            goto L_0x017f
        L_0x0175:
            r0 = 250(0xfa, double:1.235E-321)
            goto L_0x017f
        L_0x0178:
            if (r15 != r13) goto L_0x017d
            long r0 = r0.mMoveDuration
            goto L_0x017f
        L_0x017d:
            long r0 = r0.mRemoveDuration
        L_0x017f:
            android.animation.ValueAnimator r2 = r14.mValueAnimator
            r2.setDuration(r0)
            java.util.List<androidx.recyclerview.widget.ItemTouchHelper$RecoverAnimation> r0 = r11.mRecoverAnimations
            r0.add(r14)
            r0 = r20
            r1 = 0
            r0.setIsRecyclable(r1)
            android.animation.ValueAnimator r0 = r14.mValueAnimator
            r0.start()
            r0 = 0
            r8 = 1
            goto L_0x01a7
        L_0x0197:
            r0 = r9
            r13 = r10
            android.view.View r1 = r0.itemView
            r11.removeChildDrawingOrderCallbackIfNecessary(r1)
            androidx.recyclerview.widget.ItemTouchHelper$Callback r1 = r11.mCallback
            androidx.recyclerview.widget.RecyclerView r2 = r11.mRecyclerView
            r1.clearView(r2, r0)
            r0 = 0
            r8 = 0
        L_0x01a7:
            r11.mSelected = r0
            goto L_0x01ac
        L_0x01aa:
            r13 = r10
            r8 = 0
        L_0x01ac:
            if (r12 == 0) goto L_0x01fc
            androidx.recyclerview.widget.ItemTouchHelper$Callback r0 = r11.mCallback
            androidx.recyclerview.widget.RecyclerView r1 = r11.mRecyclerView
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.getMovementFlags(r12)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r1 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r1)
            r2 = r0 & r18
            if (r2 != 0) goto L_0x01c4
            goto L_0x01d4
        L_0x01c4:
            int r3 = ~r2
            r0 = r0 & r3
            if (r1 != 0) goto L_0x01ca
            r1 = 2
            goto L_0x01d2
        L_0x01ca:
            r1 = 2
            r3 = 1
            int r2 = r2 >> r3
            r3 = r2 & r17
            r0 = r0 | r3
            r2 = r2 & r18
        L_0x01d2:
            int r2 = r2 >> r1
            r0 = r0 | r2
        L_0x01d4:
            r0 = r0 & r16
            int r1 = r11.mActionState
            int r1 = r1 * r13
            int r0 = r0 >> r1
            r11.mSelectedFlags = r0
            android.view.View r0 = r12.itemView
            int r0 = r0.getLeft()
            float r0 = (float) r0
            r11.mSelectedStartX = r0
            android.view.View r0 = r12.itemView
            int r0 = r0.getTop()
            float r0 = (float) r0
            r11.mSelectedStartY = r0
            r11.mSelected = r12
            r0 = r25
            r1 = 2
            if (r0 != r1) goto L_0x01fc
            android.view.View r0 = r12.itemView
            r1 = 0
            r0.performHapticFeedback(r1)
            goto L_0x01fd
        L_0x01fc:
            r1 = 0
        L_0x01fd:
            androidx.recyclerview.widget.RecyclerView r0 = r11.mRecyclerView
            android.view.ViewParent r0 = r0.getParent()
            if (r0 == 0) goto L_0x020d
            androidx.recyclerview.widget.RecyclerView$ViewHolder r2 = r11.mSelected
            if (r2 == 0) goto L_0x020a
            r1 = 1
        L_0x020a:
            r0.requestDisallowInterceptTouchEvent(r1)
        L_0x020d:
            if (r8 != 0) goto L_0x021c
            androidx.recyclerview.widget.RecyclerView r0 = r11.mRecyclerView
            java.util.Objects.requireNonNull(r0)
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r0.mLayout
            java.util.Objects.requireNonNull(r0)
            r1 = 1
            r0.mRequestedSimpleAnimations = r1
        L_0x021c:
            androidx.recyclerview.widget.ItemTouchHelper$Callback r0 = r11.mCallback
            androidx.recyclerview.widget.RecyclerView$ViewHolder r1 = r11.mSelected
            int r2 = r11.mActionState
            r0.onSelectedChanged(r1, r2)
            androidx.recyclerview.widget.RecyclerView r0 = r11.mRecyclerView
            r0.invalidate()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ItemTouchHelper.select(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
    }

    public ItemTouchHelper(Callback callback) {
        this.mCallback = callback;
    }

    public final View findChildView(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        RecyclerView.ViewHolder viewHolder = this.mSelected;
        if (viewHolder != null) {
            View view = viewHolder.itemView;
            if (hitTest(view, x, y, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
                return view;
            }
        }
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(size);
            View view2 = recoverAnimation.mViewHolder.itemView;
            if (hitTest(view2, x, y, recoverAnimation.f26mX, recoverAnimation.f27mY)) {
                return view2;
            }
        }
        RecyclerView recyclerView = this.mRecyclerView;
        Objects.requireNonNull(recyclerView);
        int childCount = recyclerView.mChildHelper.getChildCount();
        while (true) {
            childCount--;
            if (childCount < 0) {
                return null;
            }
            View childAt = recyclerView.mChildHelper.getChildAt(childCount);
            float translationX = childAt.getTranslationX();
            float translationY = childAt.getTranslationY();
            if (x >= ((float) childAt.getLeft()) + translationX && x <= ((float) childAt.getRight()) + translationX && y >= ((float) childAt.getTop()) + translationY && y <= ((float) childAt.getBottom()) + translationY) {
                return childAt;
            }
        }
    }

    public final void onChildViewDetachedFromWindow(View view) {
        removeChildDrawingOrderCallbackIfNecessary(view);
        RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(view);
        if (childViewHolder != null) {
            RecyclerView.ViewHolder viewHolder = this.mSelected;
            if (viewHolder == null || childViewHolder != viewHolder) {
                endRecoverAnimation(childViewHolder, false);
                if (this.mPendingCleanup.remove(childViewHolder.itemView)) {
                    this.mCallback.clearView(this.mRecyclerView, childViewHolder);
                    return;
                }
                return;
            }
            select((RecyclerView.ViewHolder) null, 0);
        }
    }

    public final void updateDxDy(MotionEvent motionEvent, int i, int i2) {
        float x = motionEvent.getX(i2);
        float y = motionEvent.getY(i2);
        float f = x - this.mInitialTouchX;
        this.mDx = f;
        this.mDy = y - this.mInitialTouchY;
        if ((i & 4) == 0) {
            this.mDx = Math.max(0.0f, f);
        }
        if ((i & 8) == 0) {
            this.mDx = Math.min(0.0f, this.mDx);
        }
        if ((i & 1) == 0) {
            this.mDy = Math.max(0.0f, this.mDy);
        }
        if ((i & 2) == 0) {
            this.mDy = Math.min(0.0f, this.mDy);
        }
    }

    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.setEmpty();
    }
}
