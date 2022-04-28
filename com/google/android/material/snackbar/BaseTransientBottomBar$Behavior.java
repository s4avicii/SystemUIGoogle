package com.google.android.material.snackbar;

import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.leanback.R$layout;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.util.Objects;

public class BaseTransientBottomBar$Behavior extends SwipeDismissBehavior<View> {
    public final R$layout delegate = new R$layout(this);

    public final boolean canSwipeDismissView(View view) {
        Objects.requireNonNull(this.delegate);
        return view instanceof BaseTransientBottomBar$SnackbarBaseLayout;
    }

    public final boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        Objects.requireNonNull(this.delegate);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1 || actionMasked == 3) {
                if (SnackbarManager.snackbarManager == null) {
                    SnackbarManager.snackbarManager = new SnackbarManager();
                }
                SnackbarManager snackbarManager = SnackbarManager.snackbarManager;
                Objects.requireNonNull(snackbarManager);
                synchronized (snackbarManager.lock) {
                }
            }
        } else if (coordinatorLayout.isPointInChildBounds(view, (int) motionEvent.getX(), (int) motionEvent.getY())) {
            if (SnackbarManager.snackbarManager == null) {
                SnackbarManager.snackbarManager = new SnackbarManager();
            }
            SnackbarManager snackbarManager2 = SnackbarManager.snackbarManager;
            Objects.requireNonNull(snackbarManager2);
            synchronized (snackbarManager2.lock) {
            }
        }
        return super.onInterceptTouchEvent(coordinatorLayout, view, motionEvent);
    }
}
