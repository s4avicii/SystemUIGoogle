package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.HashMap;
import java.util.Objects;

public final class Barrier extends HelperWidget {
    public boolean mAllowsGoneWidget = true;
    public int mBarrierType = 0;
    public int mMargin = 0;

    public final boolean allowedInBarrier() {
        return true;
    }

    public final void addToSolver(LinearSystem linearSystem) {
        ConstraintAnchor[] constraintAnchorArr;
        boolean z;
        int i;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        ConstraintAnchor[] constraintAnchorArr2 = this.mListAnchors;
        constraintAnchorArr2[0] = this.mLeft;
        constraintAnchorArr2[2] = this.mTop;
        constraintAnchorArr2[1] = this.mRight;
        constraintAnchorArr2[3] = this.mBottom;
        int i2 = 0;
        while (true) {
            constraintAnchorArr = this.mListAnchors;
            if (i2 >= constraintAnchorArr.length) {
                break;
            }
            constraintAnchorArr[i2].mSolverVariable = linearSystem.createObjectVariable(constraintAnchorArr[i2]);
            i2++;
        }
        int i3 = this.mBarrierType;
        if (i3 >= 0) {
            int i4 = 4;
            if (i3 < 4) {
                ConstraintAnchor constraintAnchor = constraintAnchorArr[i3];
                int i5 = 0;
                while (true) {
                    if (i5 >= this.mWidgetsCount) {
                        z = false;
                        break;
                    }
                    ConstraintWidget constraintWidget = this.mWidgets[i5];
                    if (this.mAllowsGoneWidget || constraintWidget.allowedInBarrier()) {
                        int i6 = this.mBarrierType;
                        if (i6 == 0 || i6 == 1) {
                            Objects.requireNonNull(constraintWidget);
                            if (!(constraintWidget.mListDimensionBehaviors[0] != dimensionBehaviour || constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget == null)) {
                                break;
                            }
                        }
                        int i7 = this.mBarrierType;
                        if (i7 == 2 || i7 == 3) {
                            Objects.requireNonNull(constraintWidget);
                            if (!(constraintWidget.mListDimensionBehaviors[1] != dimensionBehaviour || constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget == null)) {
                                break;
                            }
                        }
                    }
                    i5++;
                }
                z = true;
                for (int i8 = 0; i8 < this.mWidgetsCount; i8++) {
                    ConstraintWidget constraintWidget2 = this.mWidgets[i8];
                    if (this.mAllowsGoneWidget || constraintWidget2.allowedInBarrier()) {
                        SolverVariable createObjectVariable = linearSystem.createObjectVariable(constraintWidget2.mListAnchors[this.mBarrierType]);
                        ConstraintAnchor[] constraintAnchorArr3 = constraintWidget2.mListAnchors;
                        int i9 = this.mBarrierType;
                        constraintAnchorArr3[i9].mSolverVariable = createObjectVariable;
                        if (constraintAnchorArr3[i9].mTarget == null || constraintAnchorArr3[i9].mTarget.mOwner != this) {
                            i = 0;
                        } else {
                            i = constraintAnchorArr3[i9].mMargin + 0;
                        }
                        if (i9 == 0 || i9 == 2) {
                            SolverVariable solverVariable = constraintAnchor.mSolverVariable;
                            int i10 = this.mMargin - i;
                            ArrayRow createRow = linearSystem.createRow();
                            SolverVariable createSlackVariable = linearSystem.createSlackVariable();
                            createSlackVariable.strength = 0;
                            createRow.createRowLowerThan(solverVariable, createObjectVariable, createSlackVariable, i10);
                            linearSystem.addConstraint(createRow);
                        } else {
                            SolverVariable solverVariable2 = constraintAnchor.mSolverVariable;
                            int i11 = this.mMargin + i;
                            ArrayRow createRow2 = linearSystem.createRow();
                            SolverVariable createSlackVariable2 = linearSystem.createSlackVariable();
                            createSlackVariable2.strength = 0;
                            createRow2.createRowGreaterThan(solverVariable2, createObjectVariable, createSlackVariable2, i11);
                            linearSystem.addConstraint(createRow2);
                        }
                    }
                }
                if (!z) {
                    i4 = 5;
                }
                int i12 = this.mBarrierType;
                if (i12 == 0) {
                    linearSystem.addEquality(this.mRight.mSolverVariable, this.mLeft.mSolverVariable, 0, 7);
                    linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, i4);
                    linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 0);
                } else if (i12 == 1) {
                    linearSystem.addEquality(this.mLeft.mSolverVariable, this.mRight.mSolverVariable, 0, 7);
                    linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, i4);
                    linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 0);
                } else if (i12 == 2) {
                    linearSystem.addEquality(this.mBottom.mSolverVariable, this.mTop.mSolverVariable, 0, 7);
                    linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, i4);
                    linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 0);
                } else if (i12 == 3) {
                    linearSystem.addEquality(this.mTop.mSolverVariable, this.mBottom.mSolverVariable, 0, 7);
                    linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, i4);
                    linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 0);
                }
            }
        }
    }

    public final void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        Barrier barrier = (Barrier) constraintWidget;
        this.mBarrierType = barrier.mBarrierType;
        this.mAllowsGoneWidget = barrier.mAllowsGoneWidget;
        this.mMargin = barrier.mMargin;
    }
}
