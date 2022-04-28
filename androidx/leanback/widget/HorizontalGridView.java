package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.leanback.R$styleable;
import java.util.Objects;
import java.util.WeakHashMap;

public class HorizontalGridView extends BaseGridView {
    public HorizontalGridView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HorizontalGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HorizontalGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        new Paint();
        new Rect();
        this.mLayoutManager.setOrientation(0);
        initBaseGridViewAttributes(context, attributeSet);
        int[] iArr = R$styleable.lbHorizontalGridView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        if (obtainStyledAttributes.peekValue(1) != null) {
            this.mLayoutManager.setRowHeight(obtainStyledAttributes.getLayoutDimension(1, 0));
            requestLayout();
        }
        int i2 = obtainStyledAttributes.getInt(0, 1);
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if (i2 >= 0) {
            gridLayoutManager.mNumRowsRequested = i2;
            requestLayout();
            obtainStyledAttributes.recycle();
            setLayerType(0, (Paint) null);
            setWillNotDraw(true);
            new Paint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            return;
        }
        Objects.requireNonNull(gridLayoutManager);
        throw new IllegalArgumentException();
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
