package androidx.core.view;

import android.view.View;

public interface ViewPropertyAnimatorListener {
    void onAnimationCancel(View view);

    void onAnimationEnd();

    void onAnimationStart();
}
