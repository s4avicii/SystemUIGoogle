package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

public abstract class VirtualLayout extends ConstraintHelper {
    public boolean mApplyElevationOnAttach;
    public boolean mApplyVisibilityOnAttach;

    public VirtualLayout(Context context) {
        super(context);
    }

    public void onMeasure(androidx.constraintlayout.solver.widgets.VirtualLayout virtualLayout, int i, int i2) {
    }

    public VirtualLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VirtualLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 6) {
                    this.mApplyVisibilityOnAttach = true;
                } else if (index == 13) {
                    this.mApplyElevationOnAttach = true;
                }
            }
        }
    }

    public final void onAttachedToWindow() {
        ViewParent parent;
        super.onAttachedToWindow();
        if ((this.mApplyVisibilityOnAttach || this.mApplyElevationOnAttach) && (parent = getParent()) != null && (parent instanceof ConstraintLayout)) {
            ConstraintLayout constraintLayout = (ConstraintLayout) parent;
            int visibility = getVisibility();
            float elevation = getElevation();
            for (int i = 0; i < this.mCount; i++) {
                View viewById = constraintLayout.getViewById(this.mIds[i]);
                if (viewById != null) {
                    if (this.mApplyVisibilityOnAttach) {
                        viewById.setVisibility(visibility);
                    }
                    if (this.mApplyElevationOnAttach && elevation > 0.0f) {
                        viewById.setTranslationZ(viewById.getTranslationZ() + elevation);
                    }
                }
            }
        }
    }

    public final void setElevation(float f) {
        super.setElevation(f);
        applyLayoutFeatures$1();
    }

    public final void setVisibility(int i) {
        super.setVisibility(i);
        applyLayoutFeatures$1();
    }
}
