package androidx.core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import java.lang.ref.WeakReference;

public final class ViewPropertyAnimatorCompat {
    public WeakReference<View> mView;

    public final ViewPropertyAnimatorCompat alpha(float f) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().alpha(f);
        }
        return this;
    }

    public final void cancel() {
        View view = this.mView.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public final ViewPropertyAnimatorCompat setDuration(long j) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().setDuration(j);
        }
        return this;
    }

    public final ViewPropertyAnimatorCompat setListener(final ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        final View view = this.mView.get();
        if (view != null) {
            if (viewPropertyAnimatorListener != null) {
                view.animate().setListener(new AnimatorListenerAdapter() {
                    public final void onAnimationCancel(Animator animator) {
                        ViewPropertyAnimatorListener.this.onAnimationCancel(view);
                    }

                    public final void onAnimationEnd(Animator animator) {
                        ViewPropertyAnimatorListener.this.onAnimationEnd();
                    }

                    public final void onAnimationStart(Animator animator) {
                        ViewPropertyAnimatorListener.this.onAnimationStart();
                    }
                });
            } else {
                view.animate().setListener((Animator.AnimatorListener) null);
            }
        }
        return this;
    }

    public final ViewPropertyAnimatorCompat translationY(float f) {
        View view = this.mView.get();
        if (view != null) {
            view.animate().translationY(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat(View view) {
        this.mView = new WeakReference<>(view);
    }
}
