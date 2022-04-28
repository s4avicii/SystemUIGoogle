package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintLayout;

public class VirtualLayout extends HelperWidget {
    public BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
    public int mMeasuredHeight = 0;
    public int mMeasuredWidth = 0;
    public BasicMeasure.Measurer mMeasurer = null;
    public boolean mNeedsCallFromSolver = false;
    public int mPaddingBottom = 0;
    public int mPaddingEnd = 0;
    public int mPaddingStart = 0;
    public int mPaddingTop = 0;
    public int mResolvedPaddingLeft = 0;
    public int mResolvedPaddingRight = 0;

    public void measure(int i, int i2, int i3, int i4) {
    }

    public final void updateConstraints() {
        for (int i = 0; i < this.mWidgetsCount; i++) {
            ConstraintWidget constraintWidget = this.mWidgets[i];
        }
    }

    public final void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
        BasicMeasure.Measurer measurer;
        boolean z;
        ConstraintWidget constraintWidget2;
        while (true) {
            measurer = this.mMeasurer;
            if (measurer != null || (constraintWidget2 = this.mParent) == null) {
                BasicMeasure.Measure measure = this.mMeasure;
                measure.horizontalBehavior = dimensionBehaviour;
                measure.verticalBehavior = dimensionBehaviour2;
                measure.horizontalDimension = i;
                measure.verticalDimension = i2;
                ((ConstraintLayout.Measurer) measurer).measure(constraintWidget, measure);
                constraintWidget.setWidth(this.mMeasure.measuredWidth);
                constraintWidget.setHeight(this.mMeasure.measuredHeight);
                BasicMeasure.Measure measure2 = this.mMeasure;
                constraintWidget.hasBaseline = measure2.measuredHasBaseline;
                int i3 = measure2.measuredBaseline;
                constraintWidget.mBaselineDistance = i3;
            } else {
                this.mMeasurer = ((ConstraintWidgetContainer) constraintWidget2).mMeasurer;
            }
        }
        BasicMeasure.Measure measure3 = this.mMeasure;
        measure3.horizontalBehavior = dimensionBehaviour;
        measure3.verticalBehavior = dimensionBehaviour2;
        measure3.horizontalDimension = i;
        measure3.verticalDimension = i2;
        ((ConstraintLayout.Measurer) measurer).measure(constraintWidget, measure3);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        BasicMeasure.Measure measure22 = this.mMeasure;
        constraintWidget.hasBaseline = measure22.measuredHasBaseline;
        int i32 = measure22.measuredBaseline;
        constraintWidget.mBaselineDistance = i32;
        if (i32 > 0) {
            z = true;
        } else {
            z = false;
        }
        constraintWidget.hasBaseline = z;
    }
}
