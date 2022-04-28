package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;

class FastScroller extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    public static final int[] EMPTY_STATE_SET = new int[0];
    public static final int[] PRESSED_STATE_SET = {16842919};
    public int mAnimationState;
    public int mDragState = 0;
    public final C03181 mHideRunnable;
    public float mHorizontalDragX;
    public final int[] mHorizontalRange = new int[2];
    public int mHorizontalThumbCenterX;
    public final StateListDrawable mHorizontalThumbDrawable;
    public final int mHorizontalThumbHeight;
    public int mHorizontalThumbWidth;
    public final Drawable mHorizontalTrackDrawable;
    public final int mHorizontalTrackHeight;
    public final int mMargin;
    public boolean mNeedHorizontalScrollbar = false;
    public boolean mNeedVerticalScrollbar = false;
    public final C03192 mOnScrollListener;
    public RecyclerView mRecyclerView;
    public int mRecyclerViewHeight = 0;
    public int mRecyclerViewWidth = 0;
    public final int mScrollbarMinimumRange;
    public final ValueAnimator mShowHideAnimator;
    public int mState = 0;
    public float mVerticalDragY;
    public final int[] mVerticalRange = new int[2];
    public int mVerticalThumbCenterY;
    public final StateListDrawable mVerticalThumbDrawable;
    public int mVerticalThumbHeight;
    public final int mVerticalThumbWidth;
    public final Drawable mVerticalTrackDrawable;
    public final int mVerticalTrackWidth;

    public class AnimatorListener extends AnimatorListenerAdapter {
        public boolean mCanceled = false;

        public final void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        public AnimatorListener() {
        }

        public final void onAnimationEnd(Animator animator) {
            if (this.mCanceled) {
                this.mCanceled = false;
            } else if (((Float) FastScroller.this.mShowHideAnimator.getAnimatedValue()).floatValue() == 0.0f) {
                FastScroller fastScroller = FastScroller.this;
                fastScroller.mAnimationState = 0;
                fastScroller.setState(0);
            } else {
                FastScroller fastScroller2 = FastScroller.this;
                fastScroller2.mAnimationState = 2;
                fastScroller2.mRecyclerView.invalidate();
            }
        }
    }

    public class AnimatorUpdater implements ValueAnimator.AnimatorUpdateListener {
        public AnimatorUpdater() {
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            FastScroller.this.mVerticalThumbDrawable.setAlpha(floatValue);
            FastScroller.this.mVerticalTrackDrawable.setAlpha(floatValue);
            FastScroller fastScroller = FastScroller.this;
            Objects.requireNonNull(fastScroller);
            fastScroller.mRecyclerView.invalidate();
        }
    }

    public final void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public final void setState(int i) {
        if (i == 2 && this.mState != 2) {
            this.mVerticalThumbDrawable.setState(PRESSED_STATE_SET);
            this.mRecyclerView.removeCallbacks(this.mHideRunnable);
        }
        if (i == 0) {
            this.mRecyclerView.invalidate();
        } else {
            show();
        }
        if (this.mState == 2 && i != 2) {
            this.mVerticalThumbDrawable.setState(EMPTY_STATE_SET);
            this.mRecyclerView.removeCallbacks(this.mHideRunnable);
            this.mRecyclerView.postDelayed(this.mHideRunnable, (long) 1200);
        } else if (i == 1) {
            this.mRecyclerView.removeCallbacks(this.mHideRunnable);
            this.mRecyclerView.postDelayed(this.mHideRunnable, (long) 1500);
        }
        this.mState = i;
    }

    public void hide(int i) {
        int i2 = this.mAnimationState;
        if (i2 == 1) {
            this.mShowHideAnimator.cancel();
        } else if (i2 != 2) {
            return;
        }
        this.mAnimationState = 3;
        ValueAnimator valueAnimator = this.mShowHideAnimator;
        valueAnimator.setFloatValues(new float[]{((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f});
        this.mShowHideAnimator.setDuration((long) i);
        this.mShowHideAnimator.start();
    }

    public boolean isPointInsideHorizontalThumb(float f, float f2) {
        if (f2 >= ((float) (this.mRecyclerViewHeight - this.mHorizontalThumbHeight))) {
            int i = this.mHorizontalThumbCenterX;
            int i2 = this.mHorizontalThumbWidth;
            if (f < ((float) (i - (i2 / 2))) || f > ((float) ((i2 / 2) + i))) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isPointInsideVerticalThumb(float f, float f2) {
        boolean z;
        RecyclerView recyclerView = this.mRecyclerView;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api17Impl.getLayoutDirection(recyclerView) == 1) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (f > ((float) this.mVerticalThumbWidth)) {
                return false;
            }
        } else if (f < ((float) (this.mRecyclerViewWidth - this.mVerticalThumbWidth))) {
            return false;
        }
        int i = this.mVerticalThumbCenterY;
        int i2 = this.mVerticalThumbHeight / 2;
        if (f2 < ((float) (i - i2)) || f2 > ((float) (i2 + i))) {
            return false;
        }
        return true;
    }

    public boolean isVisible() {
        if (this.mState == 1) {
            return true;
        }
        return false;
    }

    public final void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        if (this.mRecyclerViewWidth != this.mRecyclerView.getWidth() || this.mRecyclerViewHeight != this.mRecyclerView.getHeight()) {
            this.mRecyclerViewWidth = this.mRecyclerView.getWidth();
            this.mRecyclerViewHeight = this.mRecyclerView.getHeight();
            setState(0);
        } else if (this.mAnimationState != 0) {
            if (this.mNeedVerticalScrollbar) {
                int i = this.mRecyclerViewWidth;
                int i2 = this.mVerticalThumbWidth;
                int i3 = i - i2;
                int i4 = this.mVerticalThumbCenterY;
                int i5 = this.mVerticalThumbHeight;
                int i6 = i4 - (i5 / 2);
                this.mVerticalThumbDrawable.setBounds(0, 0, i2, i5);
                this.mVerticalTrackDrawable.setBounds(0, 0, this.mVerticalTrackWidth, this.mRecyclerViewHeight);
                RecyclerView recyclerView2 = this.mRecyclerView;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                boolean z = true;
                if (ViewCompat.Api17Impl.getLayoutDirection(recyclerView2) != 1) {
                    z = false;
                }
                if (z) {
                    this.mVerticalTrackDrawable.draw(canvas);
                    canvas.translate((float) this.mVerticalThumbWidth, (float) i6);
                    canvas.scale(-1.0f, 1.0f);
                    this.mVerticalThumbDrawable.draw(canvas);
                    canvas.scale(-1.0f, 1.0f);
                    canvas.translate((float) (-this.mVerticalThumbWidth), (float) (-i6));
                } else {
                    canvas.translate((float) i3, 0.0f);
                    this.mVerticalTrackDrawable.draw(canvas);
                    canvas.translate(0.0f, (float) i6);
                    this.mVerticalThumbDrawable.draw(canvas);
                    canvas.translate((float) (-i3), (float) (-i6));
                }
            }
            if (this.mNeedHorizontalScrollbar) {
                int i7 = this.mRecyclerViewHeight;
                int i8 = this.mHorizontalThumbHeight;
                int i9 = i7 - i8;
                int i10 = this.mHorizontalThumbCenterX;
                int i11 = this.mHorizontalThumbWidth;
                int i12 = i10 - (i11 / 2);
                this.mHorizontalThumbDrawable.setBounds(0, 0, i11, i8);
                this.mHorizontalTrackDrawable.setBounds(0, 0, this.mRecyclerViewWidth, this.mHorizontalTrackHeight);
                canvas.translate(0.0f, (float) i9);
                this.mHorizontalTrackDrawable.draw(canvas);
                canvas.translate((float) i12, 0.0f);
                this.mHorizontalThumbDrawable.draw(canvas);
                canvas.translate((float) (-i12), (float) (-i9));
            }
        }
    }

    public final boolean onInterceptTouchEvent$1(MotionEvent motionEvent) {
        int i = this.mState;
        if (i == 1) {
            boolean isPointInsideVerticalThumb = isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean isPointInsideHorizontalThumb = isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() == 0 && (isPointInsideVerticalThumb || isPointInsideHorizontalThumb)) {
                if (isPointInsideHorizontalThumb) {
                    this.mDragState = 1;
                    this.mHorizontalDragX = (float) ((int) motionEvent.getX());
                } else if (isPointInsideVerticalThumb) {
                    this.mDragState = 2;
                    this.mVerticalDragY = (float) ((int) motionEvent.getY());
                }
                setState(2);
                return true;
            }
        } else if (i == 2) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00bf, code lost:
        if (r8 >= 0) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x011c, code lost:
        if (r5 >= 0) goto L_0x011e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onTouchEvent(android.view.MotionEvent r12) {
        /*
            r11 = this;
            int r0 = r11.mState
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            int r0 = r12.getAction()
            r1 = 2
            r2 = 1
            if (r0 != 0) goto L_0x0047
            float r0 = r12.getX()
            float r3 = r12.getY()
            boolean r0 = r11.isPointInsideVerticalThumb(r0, r3)
            float r3 = r12.getX()
            float r4 = r12.getY()
            boolean r3 = r11.isPointInsideHorizontalThumb(r3, r4)
            if (r0 != 0) goto L_0x0029
            if (r3 == 0) goto L_0x0127
        L_0x0029:
            if (r3 == 0) goto L_0x0036
            r11.mDragState = r2
            float r12 = r12.getX()
            int r12 = (int) r12
            float r12 = (float) r12
            r11.mHorizontalDragX = r12
            goto L_0x0042
        L_0x0036:
            if (r0 == 0) goto L_0x0042
            r11.mDragState = r1
            float r12 = r12.getY()
            int r12 = (int) r12
            float r12 = (float) r12
            r11.mVerticalDragY = r12
        L_0x0042:
            r11.setState(r1)
            goto L_0x0127
        L_0x0047:
            int r0 = r12.getAction()
            r3 = 0
            if (r0 != r2) goto L_0x005e
            int r0 = r11.mState
            if (r0 != r1) goto L_0x005e
            r12 = 0
            r11.mVerticalDragY = r12
            r11.mHorizontalDragX = r12
            r11.setState(r2)
            r11.mDragState = r3
            goto L_0x0127
        L_0x005e:
            int r0 = r12.getAction()
            if (r0 != r1) goto L_0x0127
            int r0 = r11.mState
            if (r0 != r1) goto L_0x0127
            r11.show()
            int r0 = r11.mDragState
            r4 = 1073741824(0x40000000, float:2.0)
            if (r0 != r2) goto L_0x00ca
            float r0 = r12.getX()
            int[] r5 = r11.mHorizontalRange
            int r6 = r11.mMargin
            r5[r3] = r6
            int r7 = r11.mRecyclerViewWidth
            int r7 = r7 - r6
            r5[r2] = r7
            r6 = r5[r3]
            float r6 = (float) r6
            r7 = r5[r2]
            float r7 = (float) r7
            float r0 = java.lang.Math.min(r7, r0)
            float r0 = java.lang.Math.max(r6, r0)
            int r6 = r11.mHorizontalThumbCenterX
            float r6 = (float) r6
            float r6 = r6 - r0
            float r6 = java.lang.Math.abs(r6)
            int r6 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r6 >= 0) goto L_0x009b
            goto L_0x00ca
        L_0x009b:
            float r6 = r11.mHorizontalDragX
            androidx.recyclerview.widget.RecyclerView r7 = r11.mRecyclerView
            int r7 = r7.computeHorizontalScrollRange()
            androidx.recyclerview.widget.RecyclerView r8 = r11.mRecyclerView
            int r8 = r8.computeHorizontalScrollOffset()
            int r9 = r11.mRecyclerViewWidth
            r10 = r5[r2]
            r5 = r5[r3]
            int r10 = r10 - r5
            if (r10 != 0) goto L_0x00b4
        L_0x00b2:
            r5 = r3
            goto L_0x00c1
        L_0x00b4:
            float r5 = r0 - r6
            float r6 = (float) r10
            float r5 = r5 / r6
            int r7 = r7 - r9
            float r6 = (float) r7
            float r5 = r5 * r6
            int r5 = (int) r5
            int r8 = r8 + r5
            if (r8 >= r7) goto L_0x00b2
            if (r8 < 0) goto L_0x00b2
        L_0x00c1:
            if (r5 == 0) goto L_0x00c8
            androidx.recyclerview.widget.RecyclerView r6 = r11.mRecyclerView
            r6.scrollBy(r5, r3)
        L_0x00c8:
            r11.mHorizontalDragX = r0
        L_0x00ca:
            int r0 = r11.mDragState
            if (r0 != r1) goto L_0x0127
            float r12 = r12.getY()
            int[] r0 = r11.mVerticalRange
            int r1 = r11.mMargin
            r0[r3] = r1
            int r5 = r11.mRecyclerViewHeight
            int r5 = r5 - r1
            r0[r2] = r5
            r1 = r0[r3]
            float r1 = (float) r1
            r5 = r0[r2]
            float r5 = (float) r5
            float r12 = java.lang.Math.min(r5, r12)
            float r12 = java.lang.Math.max(r1, r12)
            int r1 = r11.mVerticalThumbCenterY
            float r1 = (float) r1
            float r1 = r1 - r12
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 >= 0) goto L_0x00f8
            goto L_0x0127
        L_0x00f8:
            float r1 = r11.mVerticalDragY
            androidx.recyclerview.widget.RecyclerView r4 = r11.mRecyclerView
            int r4 = r4.computeVerticalScrollRange()
            androidx.recyclerview.widget.RecyclerView r5 = r11.mRecyclerView
            int r5 = r5.computeVerticalScrollOffset()
            int r6 = r11.mRecyclerViewHeight
            r2 = r0[r2]
            r0 = r0[r3]
            int r2 = r2 - r0
            if (r2 != 0) goto L_0x0111
        L_0x010f:
            r0 = r3
            goto L_0x011e
        L_0x0111:
            float r0 = r12 - r1
            float r1 = (float) r2
            float r0 = r0 / r1
            int r4 = r4 - r6
            float r1 = (float) r4
            float r0 = r0 * r1
            int r0 = (int) r0
            int r5 = r5 + r0
            if (r5 >= r4) goto L_0x010f
            if (r5 < 0) goto L_0x010f
        L_0x011e:
            if (r0 == 0) goto L_0x0125
            androidx.recyclerview.widget.RecyclerView r1 = r11.mRecyclerView
            r1.scrollBy(r3, r0)
        L_0x0125:
            r11.mVerticalDragY = r12
        L_0x0127:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.FastScroller.onTouchEvent(android.view.MotionEvent):void");
    }

    public final void show() {
        int i = this.mAnimationState;
        if (i != 0) {
            if (i == 3) {
                this.mShowHideAnimator.cancel();
            } else {
                return;
            }
        }
        this.mAnimationState = 1;
        ValueAnimator valueAnimator = this.mShowHideAnimator;
        valueAnimator.setFloatValues(new float[]{((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f});
        this.mShowHideAnimator.setDuration(500);
        this.mShowHideAnimator.setStartDelay(0);
        this.mShowHideAnimator.start();
    }

    public FastScroller(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i, int i2, int i3) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mShowHideAnimator = ofFloat;
        this.mAnimationState = 0;
        C03181 r0 = new Runnable() {
            public final void run() {
                FastScroller.this.hide(500);
            }
        };
        this.mHideRunnable = r0;
        C03192 r2 = new RecyclerView.OnScrollListener() {
            public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
                boolean z;
                boolean z2;
                FastScroller fastScroller = FastScroller.this;
                int computeHorizontalScrollOffset = recyclerView.computeHorizontalScrollOffset();
                int computeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset();
                Objects.requireNonNull(fastScroller);
                int computeVerticalScrollRange = fastScroller.mRecyclerView.computeVerticalScrollRange();
                int i3 = fastScroller.mRecyclerViewHeight;
                if (computeVerticalScrollRange - i3 <= 0 || i3 < fastScroller.mScrollbarMinimumRange) {
                    z = false;
                } else {
                    z = true;
                }
                fastScroller.mNeedVerticalScrollbar = z;
                int computeHorizontalScrollRange = fastScroller.mRecyclerView.computeHorizontalScrollRange();
                int i4 = fastScroller.mRecyclerViewWidth;
                if (computeHorizontalScrollRange - i4 <= 0 || i4 < fastScroller.mScrollbarMinimumRange) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                fastScroller.mNeedHorizontalScrollbar = z2;
                boolean z3 = fastScroller.mNeedVerticalScrollbar;
                if (z3 || z2) {
                    if (z3) {
                        float f = (float) i3;
                        fastScroller.mVerticalThumbCenterY = (int) ((((f / 2.0f) + ((float) computeVerticalScrollOffset)) * f) / ((float) computeVerticalScrollRange));
                        fastScroller.mVerticalThumbHeight = Math.min(i3, (i3 * i3) / computeVerticalScrollRange);
                    }
                    if (fastScroller.mNeedHorizontalScrollbar) {
                        float f2 = (float) computeHorizontalScrollOffset;
                        float f3 = (float) i4;
                        fastScroller.mHorizontalThumbCenterX = (int) ((((f3 / 2.0f) + f2) * f3) / ((float) computeHorizontalScrollRange));
                        fastScroller.mHorizontalThumbWidth = Math.min(i4, (i4 * i4) / computeHorizontalScrollRange);
                    }
                    int i5 = fastScroller.mState;
                    if (i5 == 0 || i5 == 1) {
                        fastScroller.setState(1);
                    }
                } else if (fastScroller.mState != 0) {
                    fastScroller.setState(0);
                }
            }
        };
        this.mOnScrollListener = r2;
        this.mVerticalThumbDrawable = stateListDrawable;
        this.mVerticalTrackDrawable = drawable;
        this.mHorizontalThumbDrawable = stateListDrawable2;
        this.mHorizontalTrackDrawable = drawable2;
        this.mVerticalThumbWidth = Math.max(i, stateListDrawable.getIntrinsicWidth());
        this.mVerticalTrackWidth = Math.max(i, drawable.getIntrinsicWidth());
        this.mHorizontalThumbHeight = Math.max(i, stateListDrawable2.getIntrinsicWidth());
        this.mHorizontalTrackHeight = Math.max(i, drawable2.getIntrinsicWidth());
        this.mScrollbarMinimumRange = i2;
        this.mMargin = i3;
        stateListDrawable.setAlpha(255);
        drawable.setAlpha(255);
        ofFloat.addListener(new AnimatorListener());
        ofFloat.addUpdateListener(new AnimatorUpdater());
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                recyclerView2.removeItemDecoration(this);
                RecyclerView recyclerView3 = this.mRecyclerView;
                Objects.requireNonNull(recyclerView3);
                recyclerView3.mOnItemTouchListeners.remove(this);
                if (recyclerView3.mInterceptingOnItemTouchListener == this) {
                    recyclerView3.mInterceptingOnItemTouchListener = null;
                }
                RecyclerView recyclerView4 = this.mRecyclerView;
                Objects.requireNonNull(recyclerView4);
                ArrayList arrayList = recyclerView4.mScrollListeners;
                if (arrayList != null) {
                    arrayList.remove(r2);
                }
                this.mRecyclerView.removeCallbacks(r0);
            }
            this.mRecyclerView = recyclerView;
            if (recyclerView != null) {
                recyclerView.addItemDecoration(this);
                RecyclerView recyclerView5 = this.mRecyclerView;
                Objects.requireNonNull(recyclerView5);
                recyclerView5.mOnItemTouchListeners.add(this);
                this.mRecyclerView.addOnScrollListener(r2);
            }
        }
    }

    public Drawable getHorizontalThumbDrawable() {
        return this.mHorizontalThumbDrawable;
    }

    public Drawable getHorizontalTrackDrawable() {
        return this.mHorizontalTrackDrawable;
    }

    public Drawable getVerticalThumbDrawable() {
        return this.mVerticalThumbDrawable;
    }

    public Drawable getVerticalTrackDrawable() {
        return this.mVerticalTrackDrawable;
    }
}
