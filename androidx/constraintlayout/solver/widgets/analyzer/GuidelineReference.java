package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;
import java.util.Objects;

public final class GuidelineReference extends WidgetRun {
    public final boolean supportsWrapComputation() {
        return false;
    }

    public final void addDependency(DependencyNode dependencyNode) {
        this.start.dependencies.add(dependencyNode);
        dependencyNode.targets.add(this.start);
    }

    public final void apply() {
        Guideline guideline = (Guideline) this.widget;
        Objects.requireNonNull(guideline);
        int i = guideline.mRelativeBegin;
        int i2 = guideline.mRelativeEnd;
        if (guideline.mOrientation == 1) {
            if (i != -1) {
                this.start.targets.add(this.widget.mParent.horizontalRun.start);
                this.widget.mParent.horizontalRun.start.dependencies.add(this.start);
                this.start.margin = i;
            } else if (i2 != -1) {
                this.start.targets.add(this.widget.mParent.horizontalRun.end);
                this.widget.mParent.horizontalRun.end.dependencies.add(this.start);
                this.start.margin = -i2;
            } else {
                DependencyNode dependencyNode = this.start;
                dependencyNode.delegateToWidgetRun = true;
                dependencyNode.targets.add(this.widget.mParent.horizontalRun.end);
                this.widget.mParent.horizontalRun.end.dependencies.add(this.start);
            }
            addDependency(this.widget.horizontalRun.start);
            addDependency(this.widget.horizontalRun.end);
            return;
        }
        if (i != -1) {
            this.start.targets.add(this.widget.mParent.verticalRun.start);
            this.widget.mParent.verticalRun.start.dependencies.add(this.start);
            this.start.margin = i;
        } else if (i2 != -1) {
            this.start.targets.add(this.widget.mParent.verticalRun.end);
            this.widget.mParent.verticalRun.end.dependencies.add(this.start);
            this.start.margin = -i2;
        } else {
            DependencyNode dependencyNode2 = this.start;
            dependencyNode2.delegateToWidgetRun = true;
            dependencyNode2.targets.add(this.widget.mParent.verticalRun.end);
            this.widget.mParent.verticalRun.end.dependencies.add(this.start);
        }
        addDependency(this.widget.verticalRun.start);
        addDependency(this.widget.verticalRun.end);
    }

    public final void applyToWidget() {
        Guideline guideline = (Guideline) this.widget;
        Objects.requireNonNull(guideline);
        if (guideline.mOrientation == 1) {
            ConstraintWidget constraintWidget = this.widget;
            int i = this.start.value;
            Objects.requireNonNull(constraintWidget);
            constraintWidget.f15mX = i;
            return;
        }
        ConstraintWidget constraintWidget2 = this.widget;
        int i2 = this.start.value;
        Objects.requireNonNull(constraintWidget2);
        constraintWidget2.f16mY = i2;
    }

    public final void clear() {
        this.start.clear();
    }

    public final void update(Dependency dependency) {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve && !dependencyNode.resolved) {
            Guideline guideline = (Guideline) this.widget;
            Objects.requireNonNull(guideline);
            this.start.resolve((int) ((((float) ((DependencyNode) dependencyNode.targets.get(0)).value) * guideline.mRelativePercent) + 0.5f));
        }
    }

    public GuidelineReference(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        constraintWidget.horizontalRun.clear();
        constraintWidget.verticalRun.clear();
        this.orientation = ((Guideline) constraintWidget).mOrientation;
    }
}
