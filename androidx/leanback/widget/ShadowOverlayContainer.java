package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;

public class ShadowOverlayContainer extends FrameLayout {
    public ShadowOverlayContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        getResources().getDimension(C1777R.dimen.lb_material_shadow_normal_z);
        getResources().getDimension(C1777R.dimen.lb_material_shadow_focused_z);
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    static {
        new Rect();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
