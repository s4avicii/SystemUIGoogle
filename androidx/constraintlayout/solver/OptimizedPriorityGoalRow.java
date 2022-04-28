package androidx.constraintlayout.solver;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public final class OptimizedPriorityGoalRow extends ArrayRow {
    public GoalVariableAccessor accessor = new GoalVariableAccessor();
    public SolverVariable[] arrayGoals = new SolverVariable[128];
    public Cache mCache;
    public int numGoals = 0;
    public SolverVariable[] sortArray = new SolverVariable[128];

    public class GoalVariableAccessor implements Comparable {
        public SolverVariable variable;

        public GoalVariableAccessor() {
        }

        public final int compareTo(Object obj) {
            return this.variable.f14id - ((SolverVariable) obj).f14id;
        }

        public final String toString() {
            String str = "[ ";
            if (this.variable != null) {
                for (int i = 0; i < 8; i++) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(str);
                    m.append(this.variable.goalStrengthVector[i]);
                    m.append(" ");
                    str = m.toString();
                }
            }
            StringBuilder m2 = DebugInfo$$ExternalSyntheticOutline0.m2m(str, "] ");
            m2.append(this.variable);
            return m2.toString();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        if (r9 < r8) goto L_0x005f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.constraintlayout.solver.SolverVariable getPivotCandidate(boolean[] r12) {
        /*
            r11 = this;
            r0 = -1
            r1 = 0
            r3 = r0
            r2 = r1
        L_0x0004:
            int r4 = r11.numGoals
            if (r2 >= r4) goto L_0x0065
            androidx.constraintlayout.solver.SolverVariable[] r4 = r11.arrayGoals
            r4 = r4[r2]
            int r5 = r4.f14id
            boolean r5 = r12[r5]
            if (r5 == 0) goto L_0x0013
            goto L_0x0062
        L_0x0013:
            androidx.constraintlayout.solver.OptimizedPriorityGoalRow$GoalVariableAccessor r5 = r11.accessor
            java.util.Objects.requireNonNull(r5)
            r5.variable = r4
            r4 = 7
            r5 = 1
            if (r3 != r0) goto L_0x003d
            androidx.constraintlayout.solver.OptimizedPriorityGoalRow$GoalVariableAccessor r6 = r11.accessor
        L_0x0020:
            if (r4 < 0) goto L_0x0036
            androidx.constraintlayout.solver.SolverVariable r7 = r6.variable
            float[] r7 = r7.goalStrengthVector
            r7 = r7[r4]
            r8 = 0
            int r9 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r9 <= 0) goto L_0x002e
            goto L_0x0039
        L_0x002e:
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 >= 0) goto L_0x0033
            goto L_0x003a
        L_0x0033:
            int r4 = r4 + -1
            goto L_0x0020
        L_0x0036:
            java.util.Objects.requireNonNull(r6)
        L_0x0039:
            r5 = r1
        L_0x003a:
            if (r5 == 0) goto L_0x0062
            goto L_0x0061
        L_0x003d:
            androidx.constraintlayout.solver.OptimizedPriorityGoalRow$GoalVariableAccessor r6 = r11.accessor
            androidx.constraintlayout.solver.SolverVariable[] r7 = r11.arrayGoals
            r7 = r7[r3]
            java.util.Objects.requireNonNull(r6)
        L_0x0046:
            if (r4 < 0) goto L_0x005e
            float[] r8 = r7.goalStrengthVector
            r8 = r8[r4]
            androidx.constraintlayout.solver.SolverVariable r9 = r6.variable
            float[] r9 = r9.goalStrengthVector
            r9 = r9[r4]
            int r10 = (r9 > r8 ? 1 : (r9 == r8 ? 0 : -1))
            if (r10 != 0) goto L_0x0059
            int r4 = r4 + -1
            goto L_0x0046
        L_0x0059:
            int r4 = (r9 > r8 ? 1 : (r9 == r8 ? 0 : -1))
            if (r4 >= 0) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r5 = r1
        L_0x005f:
            if (r5 == 0) goto L_0x0062
        L_0x0061:
            r3 = r2
        L_0x0062:
            int r2 = r2 + 1
            goto L_0x0004
        L_0x0065:
            if (r3 != r0) goto L_0x0069
            r11 = 0
            return r11
        L_0x0069:
            androidx.constraintlayout.solver.SolverVariable[] r11 = r11.arrayGoals
            r11 = r11[r3]
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.OptimizedPriorityGoalRow.getPivotCandidate(boolean[]):androidx.constraintlayout.solver.SolverVariable");
    }

    public final void removeGoal(SolverVariable solverVariable) {
        int i = 0;
        while (i < this.numGoals) {
            if (this.arrayGoals[i] == solverVariable) {
                while (true) {
                    int i2 = this.numGoals;
                    if (i < i2 - 1) {
                        SolverVariable[] solverVariableArr = this.arrayGoals;
                        int i3 = i + 1;
                        solverVariableArr[i] = solverVariableArr[i3];
                        i = i3;
                    } else {
                        this.numGoals = i2 - 1;
                        solverVariable.inGoal = false;
                        return;
                    }
                }
            } else {
                i++;
            }
        }
    }

    public final void addToGoal(SolverVariable solverVariable) {
        int i;
        int i2 = this.numGoals + 1;
        SolverVariable[] solverVariableArr = this.arrayGoals;
        if (i2 > solverVariableArr.length) {
            SolverVariable[] solverVariableArr2 = (SolverVariable[]) Arrays.copyOf(solverVariableArr, solverVariableArr.length * 2);
            this.arrayGoals = solverVariableArr2;
            this.sortArray = (SolverVariable[]) Arrays.copyOf(solverVariableArr2, solverVariableArr2.length * 2);
        }
        SolverVariable[] solverVariableArr3 = this.arrayGoals;
        int i3 = this.numGoals;
        solverVariableArr3[i3] = solverVariable;
        int i4 = i3 + 1;
        this.numGoals = i4;
        if (i4 > 1 && solverVariableArr3[i4 - 1].f14id > solverVariable.f14id) {
            int i5 = 0;
            while (true) {
                i = this.numGoals;
                if (i5 >= i) {
                    break;
                }
                this.sortArray[i5] = this.arrayGoals[i5];
                i5++;
            }
            Arrays.sort(this.sortArray, 0, i, new Comparator<SolverVariable>() {
                public final int compare(Object obj, Object obj2) {
                    return ((SolverVariable) obj).f14id - ((SolverVariable) obj2).f14id;
                }
            });
            for (int i6 = 0; i6 < this.numGoals; i6++) {
                this.arrayGoals[i6] = this.sortArray[i6];
            }
        }
        solverVariable.inGoal = true;
        solverVariable.addToRow(this);
    }

    public final String toString() {
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("", " goal -> (");
        m.append(this.constantValue);
        m.append(") : ");
        String sb = m.toString();
        for (int i = 0; i < this.numGoals; i++) {
            SolverVariable solverVariable = this.arrayGoals[i];
            GoalVariableAccessor goalVariableAccessor = this.accessor;
            Objects.requireNonNull(goalVariableAccessor);
            goalVariableAccessor.variable = solverVariable;
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(sb);
            m2.append(this.accessor);
            m2.append(" ");
            sb = m2.toString();
        }
        return sb;
    }

    public final void updateFromRow(ArrayRow arrayRow) {
        ArrayRow arrayRow2 = arrayRow;
        SolverVariable solverVariable = arrayRow2.variable;
        if (solverVariable != null) {
            ArrayLinkedVariables arrayLinkedVariables = arrayRow2.variables;
            Objects.requireNonNull(arrayLinkedVariables);
            int i = arrayLinkedVariables.mHead;
            ArrayLinkedVariables arrayLinkedVariables2 = arrayRow2.variables;
            Objects.requireNonNull(arrayLinkedVariables2);
            int i2 = arrayLinkedVariables2.currentSize;
            while (i != -1 && i2 > 0) {
                ArrayLinkedVariables arrayLinkedVariables3 = arrayRow2.variables;
                Objects.requireNonNull(arrayLinkedVariables3);
                int i3 = arrayLinkedVariables3.mArrayIndices[i];
                ArrayLinkedVariables arrayLinkedVariables4 = arrayRow2.variables;
                Objects.requireNonNull(arrayLinkedVariables4);
                float f = arrayLinkedVariables4.mArrayValues[i];
                SolverVariable solverVariable2 = this.mCache.mIndexedVariables[i3];
                GoalVariableAccessor goalVariableAccessor = this.accessor;
                Objects.requireNonNull(goalVariableAccessor);
                goalVariableAccessor.variable = solverVariable2;
                GoalVariableAccessor goalVariableAccessor2 = this.accessor;
                Objects.requireNonNull(goalVariableAccessor2);
                boolean z = true;
                if (goalVariableAccessor2.variable.inGoal) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        float[] fArr = goalVariableAccessor2.variable.goalStrengthVector;
                        fArr[i4] = (solverVariable.goalStrengthVector[i4] * f) + fArr[i4];
                        if (Math.abs(fArr[i4]) < 1.0E-4f) {
                            goalVariableAccessor2.variable.goalStrengthVector[i4] = 0.0f;
                        } else {
                            z = false;
                        }
                    }
                    if (z) {
                        OptimizedPriorityGoalRow.this.removeGoal(goalVariableAccessor2.variable);
                    }
                    z = false;
                } else {
                    for (int i5 = 0; i5 < 8; i5++) {
                        float f2 = solverVariable.goalStrengthVector[i5];
                        if (f2 != 0.0f) {
                            float f3 = f2 * f;
                            if (Math.abs(f3) < 1.0E-4f) {
                                f3 = 0.0f;
                            }
                            goalVariableAccessor2.variable.goalStrengthVector[i5] = f3;
                        } else {
                            goalVariableAccessor2.variable.goalStrengthVector[i5] = 0.0f;
                        }
                    }
                }
                if (z) {
                    addToGoal(solverVariable2);
                }
                this.constantValue = (arrayRow2.constantValue * f) + this.constantValue;
                ArrayLinkedVariables arrayLinkedVariables5 = arrayRow2.variables;
                Objects.requireNonNull(arrayLinkedVariables5);
                i = arrayLinkedVariables5.mArrayNextIndices[i];
            }
            removeGoal(solverVariable);
        }
    }

    public OptimizedPriorityGoalRow(Cache cache) {
        super(cache);
        this.mCache = cache;
    }
}
