package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.screenshot.SwipeDismissHandler;

public class DraggableConstraintLayout extends ConstraintLayout {
    public Runnable mOnDismiss;
    public Runnable mOnInteraction;
    public final GestureDetector mSwipeDetector;
    public final SwipeDismissHandler mSwipeDismissHandler;

    public DraggableConstraintLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void onFinishInflate() {
    }

    public DraggableConstraintLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DraggableConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SwipeDismissHandler swipeDismissHandler = new SwipeDismissHandler(this.mContext, this, new SwipeDismissHandler.SwipeDismissCallbacks() {
            public final void onDismiss() {
                Runnable runnable = DraggableConstraintLayout.this.mOnDismiss;
                if (runnable != null) {
                    runnable.run();
                }
            }

            public final void onInteraction() {
                Runnable runnable = DraggableConstraintLayout.this.mOnInteraction;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        this.mSwipeDismissHandler = swipeDismissHandler;
        setOnTouchListener(swipeDismissHandler);
        GestureDetector gestureDetector = new GestureDetector(this.mContext, new GestureDetector.SimpleOnGestureListener() {
            public final Rect mActionsRect = new Rect();

            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                View findViewById = DraggableConstraintLayout.this.findViewById(C1777R.C1779id.actions_container);
                findViewById.getBoundsOnScreen(this.mActionsRect);
                if (!this.mActionsRect.contains((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY()) || !findViewById.canScrollHorizontally((int) f)) {
                    return true;
                }
                return false;
            }
        });
        this.mSwipeDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mSwipeDismissHandler.onTouch(this, motionEvent);
        }
        return this.mSwipeDetector.onTouchEvent(motionEvent);
    }
}
