package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;

final class RowContainerView extends LinearLayout {
    public Drawable mForeground;
    public boolean mForegroundBoundsChanged = true;

    public RowContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        setOrientation(1);
        LayoutInflater.from(context).inflate(C1777R.layout.lb_row_container, this);
        ViewGroup viewGroup = (ViewGroup) findViewById(C1777R.C1779id.lb_row_container_header_dock);
        setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void setForeground(Drawable drawable) {
        boolean z;
        this.mForeground = drawable;
        if (drawable == null) {
            z = true;
        } else {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
        Drawable drawable = this.mForeground;
        if (drawable != null) {
            if (this.mForegroundBoundsChanged) {
                this.mForegroundBoundsChanged = false;
                drawable.setBounds(0, 0, getWidth(), getHeight());
            }
            this.mForeground.draw(canvas);
        }
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mForegroundBoundsChanged = true;
    }

    public final Drawable getForeground() {
        return this.mForeground;
    }
}
