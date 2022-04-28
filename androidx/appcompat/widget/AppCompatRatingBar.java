package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class AppCompatRatingBar extends RatingBar {
    public final AppCompatProgressBarHelper mAppCompatProgressBarHelper;

    public final synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        AppCompatProgressBarHelper appCompatProgressBarHelper = this.mAppCompatProgressBarHelper;
        Objects.requireNonNull(appCompatProgressBarHelper);
        Bitmap bitmap = appCompatProgressBarHelper.mSampleTile;
        if (bitmap != null) {
            setMeasuredDimension(View.resolveSizeAndState(bitmap.getWidth() * getNumStars(), i, 0), getMeasuredHeight());
        }
    }

    public AppCompatRatingBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, C1777R.attr.ratingBarStyle);
        ThemeUtils.checkAppCompatTheme(this, getContext());
        AppCompatProgressBarHelper appCompatProgressBarHelper = new AppCompatProgressBarHelper(this);
        this.mAppCompatProgressBarHelper = appCompatProgressBarHelper;
        appCompatProgressBarHelper.loadFromAttributes(attributeSet, C1777R.attr.ratingBarStyle);
    }
}
