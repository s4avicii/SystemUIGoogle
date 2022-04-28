package androidx.appcompat.view;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import com.airbnb.lottie.C0438L;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class ViewPropertyAnimatorCompatSet {
    public final ArrayList<ViewPropertyAnimatorCompat> mAnimators = new ArrayList<>();
    public long mDuration = -1;
    public Interpolator mInterpolator;
    public boolean mIsStarted;
    public ViewPropertyAnimatorListener mListener;
    public final C00421 mProxyListener = new C0438L() {
        public int mProxyEndCount = 0;
        public boolean mProxyStarted = false;

        public final void onAnimationEnd() {
            int i = this.mProxyEndCount + 1;
            this.mProxyEndCount = i;
            if (i == ViewPropertyAnimatorCompatSet.this.mAnimators.size()) {
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = ViewPropertyAnimatorCompatSet.this.mListener;
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationEnd();
                }
                this.mProxyEndCount = 0;
                this.mProxyStarted = false;
                ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = ViewPropertyAnimatorCompatSet.this;
                Objects.requireNonNull(viewPropertyAnimatorCompatSet);
                viewPropertyAnimatorCompatSet.mIsStarted = false;
            }
        }

        public final void onAnimationStart() {
            if (!this.mProxyStarted) {
                this.mProxyStarted = true;
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = ViewPropertyAnimatorCompatSet.this.mListener;
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationStart();
                }
            }
        }
    };

    public final void cancel() {
        if (this.mIsStarted) {
            Iterator<ViewPropertyAnimatorCompat> it = this.mAnimators.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            this.mIsStarted = false;
        }
    }

    public final void start() {
        if (!this.mIsStarted) {
            Iterator<ViewPropertyAnimatorCompat> it = this.mAnimators.iterator();
            while (it.hasNext()) {
                ViewPropertyAnimatorCompat next = it.next();
                long j = this.mDuration;
                if (j >= 0) {
                    next.setDuration(j);
                }
                Interpolator interpolator = this.mInterpolator;
                if (interpolator != null) {
                    Objects.requireNonNull(next);
                    View view = next.mView.get();
                    if (view != null) {
                        view.animate().setInterpolator(interpolator);
                    }
                }
                if (this.mListener != null) {
                    next.setListener(this.mProxyListener);
                }
                Objects.requireNonNull(next);
                View view2 = next.mView.get();
                if (view2 != null) {
                    view2.animate().start();
                }
            }
            this.mIsStarted = true;
        }
    }
}
