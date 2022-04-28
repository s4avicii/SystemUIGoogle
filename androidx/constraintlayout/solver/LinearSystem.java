package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.OptimizedPriorityGoalRow;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import java.util.Arrays;
import java.util.Objects;

public final class LinearSystem {
    public static int POOL_SIZE = 1000;
    public int TABLE_SIZE;
    public boolean[] mAlreadyTestedCandidates;
    public final Cache mCache;
    public OptimizedPriorityGoalRow mGoal;
    public int mMaxColumns;
    public int mMaxRows;
    public int mNumColumns;
    public int mNumRows;
    public SolverVariable[] mPoolVariables;
    public int mPoolVariablesCount;
    public ArrayRow[] mRows;
    public final ArrayRow mTempGoal;
    public int mVariablesID;
    public boolean newgraphOptimizer;

    public interface Row {
        SolverVariable getPivotCandidate(boolean[] zArr);
    }

    public final ArrayRow addEquality(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow createRow = createRow();
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            createRow.constantValue = (float) i;
        }
        if (!z) {
            createRow.variables.put(solverVariable, -1.0f);
            createRow.variables.put(solverVariable2, 1.0f);
        } else {
            createRow.variables.put(solverVariable, 1.0f);
            createRow.variables.put(solverVariable2, -1.0f);
        }
        if (i2 != 7) {
            createRow.addError(this, i2);
        }
        addConstraint(createRow);
        return createRow;
    }

    public final SolverVariable createObjectVariable(Object obj) {
        SolverVariable solverVariable = null;
        if (obj == null) {
            return null;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        if (obj instanceof ConstraintAnchor) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) obj;
            solverVariable = constraintAnchor.mSolverVariable;
            if (solverVariable == null) {
                constraintAnchor.resetSolverVariable();
                solverVariable = constraintAnchor.mSolverVariable;
            }
            int i = solverVariable.f14id;
            if (i == -1 || i > this.mVariablesID || this.mCache.mIndexedVariables[i] == null) {
                if (i != -1) {
                    solverVariable.reset();
                }
                int i2 = this.mVariablesID + 1;
                this.mVariablesID = i2;
                this.mNumColumns++;
                solverVariable.f14id = i2;
                solverVariable.mType = SolverVariable.Type.UNRESTRICTED;
                this.mCache.mIndexedVariables[i2] = solverVariable;
            }
        }
        return solverVariable;
    }

    public final void reset() {
        Cache cache;
        int i = 0;
        while (true) {
            cache = this.mCache;
            SolverVariable[] solverVariableArr = cache.mIndexedVariables;
            if (i >= solverVariableArr.length) {
                break;
            }
            SolverVariable solverVariable = solverVariableArr[i];
            if (solverVariable != null) {
                solverVariable.reset();
            }
            i++;
        }
        Pools$SimplePool pools$SimplePool = cache.solverVariablePool;
        SolverVariable[] solverVariableArr2 = this.mPoolVariables;
        int i2 = this.mPoolVariablesCount;
        Objects.requireNonNull(pools$SimplePool);
        if (i2 > solverVariableArr2.length) {
            i2 = solverVariableArr2.length;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable solverVariable2 = solverVariableArr2[i3];
            int i4 = pools$SimplePool.mPoolSize;
            Object[] objArr = pools$SimplePool.mPool;
            if (i4 < objArr.length) {
                objArr[i4] = solverVariable2;
                pools$SimplePool.mPoolSize = i4 + 1;
            }
        }
        this.mPoolVariablesCount = 0;
        Arrays.fill(this.mCache.mIndexedVariables, (Object) null);
        this.mVariablesID = 0;
        OptimizedPriorityGoalRow optimizedPriorityGoalRow = this.mGoal;
        Objects.requireNonNull(optimizedPriorityGoalRow);
        optimizedPriorityGoalRow.numGoals = 0;
        optimizedPriorityGoalRow.constantValue = 0.0f;
        this.mNumColumns = 1;
        for (int i5 = 0; i5 < this.mNumRows; i5++) {
            Objects.requireNonNull(this.mRows[i5]);
        }
        int i6 = 0;
        while (true) {
            ArrayRow[] arrayRowArr = this.mRows;
            if (i6 < arrayRowArr.length) {
                ArrayRow arrayRow = arrayRowArr[i6];
                if (arrayRow != null) {
                    Pools$SimplePool pools$SimplePool2 = this.mCache.arrayRowPool;
                    Objects.requireNonNull(pools$SimplePool2);
                    int i7 = pools$SimplePool2.mPoolSize;
                    Object[] objArr2 = pools$SimplePool2.mPool;
                    if (i7 < objArr2.length) {
                        objArr2[i7] = arrayRow;
                        pools$SimplePool2.mPoolSize = i7 + 1;
                    }
                }
                this.mRows[i6] = null;
                i6++;
            } else {
                this.mNumRows = 0;
                return;
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: androidx.constraintlayout.solver.SolverVariable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.constraintlayout.solver.SolverVariable acquireSolverVariable(androidx.constraintlayout.solver.SolverVariable.Type r6) {
        /*
            r5 = this;
            androidx.constraintlayout.solver.Cache r0 = r5.mCache
            androidx.constraintlayout.solver.Pools$SimplePool r0 = r0.solverVariablePool
            java.util.Objects.requireNonNull(r0)
            int r1 = r0.mPoolSize
            r2 = 0
            if (r1 <= 0) goto L_0x0017
            int r1 = r1 + -1
            java.lang.Object[] r3 = r0.mPool
            r4 = r3[r1]
            r3[r1] = r2
            r0.mPoolSize = r1
            r2 = r4
        L_0x0017:
            androidx.constraintlayout.solver.SolverVariable r2 = (androidx.constraintlayout.solver.SolverVariable) r2
            if (r2 != 0) goto L_0x0023
            androidx.constraintlayout.solver.SolverVariable r2 = new androidx.constraintlayout.solver.SolverVariable
            r2.<init>(r6)
            r2.mType = r6
            goto L_0x0028
        L_0x0023:
            r2.reset()
            r2.mType = r6
        L_0x0028:
            int r6 = r5.mPoolVariablesCount
            int r0 = POOL_SIZE
            if (r6 < r0) goto L_0x003c
            int r0 = r0 * 2
            POOL_SIZE = r0
            androidx.constraintlayout.solver.SolverVariable[] r6 = r5.mPoolVariables
            java.lang.Object[] r6 = java.util.Arrays.copyOf(r6, r0)
            androidx.constraintlayout.solver.SolverVariable[] r6 = (androidx.constraintlayout.solver.SolverVariable[]) r6
            r5.mPoolVariables = r6
        L_0x003c:
            androidx.constraintlayout.solver.SolverVariable[] r6 = r5.mPoolVariables
            int r0 = r5.mPoolVariablesCount
            int r1 = r0 + 1
            r5.mPoolVariablesCount = r1
            r6[r0] = r2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.LinearSystem.acquireSolverVariable(androidx.constraintlayout.solver.SolverVariable$Type):androidx.constraintlayout.solver.SolverVariable");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:67:0x012c, code lost:
        if (r4.usageInRowCount <= 1) goto L_0x0138;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0136, code lost:
        if (r4.usageInRowCount <= 1) goto L_0x0138;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x013b, code lost:
        r18 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x015b, code lost:
        if (r4.usageInRowCount <= 1) goto L_0x0167;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0165, code lost:
        if (r4.usageInRowCount <= 1) goto L_0x0167;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x016a, code lost:
        r18 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x017f A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0123  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void addConstraint(androidx.constraintlayout.solver.ArrayRow r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            androidx.constraintlayout.solver.SolverVariable$Type r2 = androidx.constraintlayout.solver.SolverVariable.Type.UNRESTRICTED
            int r3 = r0.mNumRows
            r4 = 1
            int r3 = r3 + r4
            int r5 = r0.mMaxRows
            if (r3 >= r5) goto L_0x0015
            int r3 = r0.mNumColumns
            int r3 = r3 + r4
            int r5 = r0.mMaxColumns
            if (r3 < r5) goto L_0x0018
        L_0x0015:
            r19.increaseTableSize()
        L_0x0018:
            boolean r3 = r1.isSimpleDefinition
            if (r3 != 0) goto L_0x024c
            int r3 = r0.mNumRows
            r6 = 981668463(0x3a83126f, float:0.001)
            r7 = 0
            r8 = -1
            if (r3 <= 0) goto L_0x009f
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r1.variables
            androidx.constraintlayout.solver.ArrayRow[] r9 = r0.mRows
            java.util.Objects.requireNonNull(r3)
            int r10 = r3.mHead
        L_0x002e:
            r11 = 0
        L_0x002f:
            if (r10 == r8) goto L_0x0097
            int r12 = r3.currentSize
            if (r11 >= r12) goto L_0x0097
            androidx.constraintlayout.solver.Cache r12 = r3.mCache
            androidx.constraintlayout.solver.SolverVariable[] r12 = r12.mIndexedVariables
            int[] r13 = r3.mArrayIndices
            r13 = r13[r10]
            r12 = r12[r13]
            int r13 = r12.definitionId
            if (r13 == r8) goto L_0x0090
            float[] r11 = r3.mArrayValues
            r10 = r11[r10]
            r3.remove(r12, r4)
            int r11 = r12.definitionId
            r11 = r9[r11]
            boolean r12 = r11.isSimpleDefinition
            if (r12 != 0) goto L_0x0076
            androidx.constraintlayout.solver.ArrayLinkedVariables r12 = r11.variables
            int r13 = r12.mHead
            r14 = 0
        L_0x0057:
            if (r13 == r8) goto L_0x0076
            int r15 = r12.currentSize
            if (r14 >= r15) goto L_0x0076
            androidx.constraintlayout.solver.Cache r15 = r3.mCache
            androidx.constraintlayout.solver.SolverVariable[] r15 = r15.mIndexedVariables
            int[] r5 = r12.mArrayIndices
            r5 = r5[r13]
            r5 = r15[r5]
            float[] r15 = r12.mArrayValues
            r15 = r15[r13]
            float r15 = r15 * r10
            r3.add(r5, r15, r4)
            int[] r5 = r12.mArrayNextIndices
            r13 = r5[r13]
            int r14 = r14 + 1
            goto L_0x0057
        L_0x0076:
            float r5 = r1.constantValue
            float r12 = r11.constantValue
            float r12 = r12 * r10
            float r12 = r12 + r5
            r1.constantValue = r12
            float r5 = java.lang.Math.abs(r12)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 >= 0) goto L_0x0088
            r1.constantValue = r7
        L_0x0088:
            androidx.constraintlayout.solver.SolverVariable r5 = r11.variable
            r5.removeFromRow(r1)
            int r10 = r3.mHead
            goto L_0x002e
        L_0x0090:
            int[] r5 = r3.mArrayNextIndices
            r10 = r5[r10]
            int r11 = r11 + 1
            goto L_0x002f
        L_0x0097:
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r1.variables
            int r3 = r3.currentSize
            if (r3 != 0) goto L_0x009f
            r1.isSimpleDefinition = r4
        L_0x009f:
            androidx.constraintlayout.solver.SolverVariable r3 = r1.variable
            if (r3 != 0) goto L_0x00b1
            float r3 = r1.constantValue
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 != 0) goto L_0x00b1
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r1.variables
            int r3 = r3.currentSize
            if (r3 != 0) goto L_0x00b1
            r3 = r4
            goto L_0x00b2
        L_0x00b1:
            r3 = 0
        L_0x00b2:
            if (r3 == 0) goto L_0x00b5
            return
        L_0x00b5:
            float r3 = r1.constantValue
            int r5 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x00dc
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r3 = r3 * r5
            r1.constantValue = r3
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r1.variables
            java.util.Objects.requireNonNull(r3)
            int r9 = r3.mHead
            r10 = 0
        L_0x00c8:
            if (r9 == r8) goto L_0x00dc
            int r11 = r3.currentSize
            if (r10 >= r11) goto L_0x00dc
            float[] r11 = r3.mArrayValues
            r12 = r11[r9]
            float r12 = r12 * r5
            r11[r9] = r12
            int[] r11 = r3.mArrayNextIndices
            r9 = r11[r9]
            int r10 = r10 + 1
            goto L_0x00c8
        L_0x00dc:
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r1.variables
            java.util.Objects.requireNonNull(r3)
            int r5 = r3.mHead
            r13 = r7
            r15 = r13
            r10 = 0
            r11 = 0
            r12 = 0
            r14 = 0
            r16 = 0
        L_0x00eb:
            if (r5 == r8) goto L_0x0189
            int r8 = r3.currentSize
            if (r10 >= r8) goto L_0x0189
            float[] r8 = r3.mArrayValues
            r17 = r8[r5]
            androidx.constraintlayout.solver.Cache r9 = r3.mCache
            androidx.constraintlayout.solver.SolverVariable[] r9 = r9.mIndexedVariables
            int[] r4 = r3.mArrayIndices
            r4 = r4[r5]
            r4 = r9[r4]
            int r9 = (r17 > r7 ? 1 : (r17 == r7 ? 0 : -1))
            if (r9 >= 0) goto L_0x0112
            r9 = -1165815185(0xffffffffba83126f, float:-0.001)
            int r9 = (r17 > r9 ? 1 : (r17 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x011f
            r8[r5] = r7
            androidx.constraintlayout.solver.ArrayRow r8 = r3.mRow
            r4.removeFromRow(r8)
            goto L_0x011d
        L_0x0112:
            int r9 = (r17 > r6 ? 1 : (r17 == r6 ? 0 : -1))
            if (r9 >= 0) goto L_0x011f
            r8[r5] = r7
            androidx.constraintlayout.solver.ArrayRow r8 = r3.mRow
            r4.removeFromRow(r8)
        L_0x011d:
            r17 = r7
        L_0x011f:
            int r8 = (r17 > r7 ? 1 : (r17 == r7 ? 0 : -1))
            if (r8 == 0) goto L_0x017f
            androidx.constraintlayout.solver.SolverVariable$Type r8 = r4.mType
            if (r8 != r2) goto L_0x0150
            if (r12 != 0) goto L_0x012f
            int r8 = r4.usageInRowCount
            r9 = 1
            if (r8 > r9) goto L_0x013b
            goto L_0x0138
        L_0x012f:
            r9 = 1
            int r8 = (r13 > r17 ? 1 : (r13 == r17 ? 0 : -1))
            if (r8 <= 0) goto L_0x0140
            int r8 = r4.usageInRowCount
            if (r8 > r9) goto L_0x013b
        L_0x0138:
            r18 = r9
            goto L_0x013d
        L_0x013b:
            r18 = 0
        L_0x013d:
            r14 = r18
            goto L_0x014c
        L_0x0140:
            if (r14 != 0) goto L_0x017f
            int r8 = r4.usageInRowCount
            if (r8 > r9) goto L_0x0148
            r8 = 1
            goto L_0x0149
        L_0x0148:
            r8 = 0
        L_0x0149:
            if (r8 == 0) goto L_0x017f
            r14 = 1
        L_0x014c:
            r12 = r4
            r13 = r17
            goto L_0x017f
        L_0x0150:
            if (r12 != 0) goto L_0x017f
            int r8 = (r17 > r7 ? 1 : (r17 == r7 ? 0 : -1))
            if (r8 >= 0) goto L_0x017f
            if (r11 != 0) goto L_0x015e
            int r8 = r4.usageInRowCount
            r9 = 1
            if (r8 > r9) goto L_0x016a
            goto L_0x0167
        L_0x015e:
            r9 = 1
            int r8 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r8 <= 0) goto L_0x016f
            int r8 = r4.usageInRowCount
            if (r8 > r9) goto L_0x016a
        L_0x0167:
            r18 = r9
            goto L_0x016c
        L_0x016a:
            r18 = 0
        L_0x016c:
            r16 = r18
            goto L_0x017c
        L_0x016f:
            if (r16 != 0) goto L_0x017f
            int r8 = r4.usageInRowCount
            if (r8 > r9) goto L_0x0177
            r8 = 1
            goto L_0x0178
        L_0x0177:
            r8 = 0
        L_0x0178:
            if (r8 == 0) goto L_0x017f
            r16 = 1
        L_0x017c:
            r11 = r4
            r15 = r17
        L_0x017f:
            int[] r4 = r3.mArrayNextIndices
            r5 = r4[r5]
            int r10 = r10 + 1
            r4 = 1
            r8 = -1
            goto L_0x00eb
        L_0x0189:
            if (r12 == 0) goto L_0x018c
            r11 = r12
        L_0x018c:
            if (r11 != 0) goto L_0x0190
            r3 = 1
            goto L_0x0194
        L_0x0190:
            r1.pivot(r11)
            r3 = 0
        L_0x0194:
            androidx.constraintlayout.solver.ArrayLinkedVariables r4 = r1.variables
            int r4 = r4.currentSize
            if (r4 != 0) goto L_0x019e
            r4 = 1
            r1.isSimpleDefinition = r4
            goto L_0x019f
        L_0x019e:
            r4 = 1
        L_0x019f:
            if (r3 == 0) goto L_0x0234
            int r3 = r0.mNumColumns
            int r3 = r3 + r4
            int r5 = r0.mMaxColumns
            if (r3 < r5) goto L_0x01ab
            r19.increaseTableSize()
        L_0x01ab:
            androidx.constraintlayout.solver.SolverVariable$Type r3 = androidx.constraintlayout.solver.SolverVariable.Type.SLACK
            androidx.constraintlayout.solver.SolverVariable r3 = r0.acquireSolverVariable(r3)
            int r5 = r0.mVariablesID
            int r5 = r5 + r4
            r0.mVariablesID = r5
            int r6 = r0.mNumColumns
            int r6 = r6 + r4
            r0.mNumColumns = r6
            r3.f14id = r5
            androidx.constraintlayout.solver.Cache r4 = r0.mCache
            androidx.constraintlayout.solver.SolverVariable[] r4 = r4.mIndexedVariables
            r4[r5] = r3
            r1.variable = r3
            r19.addRow(r20)
            androidx.constraintlayout.solver.ArrayRow r4 = r0.mTempGoal
            java.util.Objects.requireNonNull(r4)
            r5 = 0
            r4.variable = r5
            androidx.constraintlayout.solver.ArrayLinkedVariables r5 = r4.variables
            r5.clear()
            r5 = 0
        L_0x01d6:
            androidx.constraintlayout.solver.ArrayLinkedVariables r6 = r1.variables
            int r8 = r6.currentSize
            if (r5 >= r8) goto L_0x0207
            androidx.constraintlayout.solver.SolverVariable r6 = r6.getVariable(r5)
            androidx.constraintlayout.solver.ArrayLinkedVariables r8 = r1.variables
            java.util.Objects.requireNonNull(r8)
            int r9 = r8.mHead
            r10 = 0
        L_0x01e8:
            r11 = -1
            if (r9 == r11) goto L_0x01fd
            int r11 = r8.currentSize
            if (r10 >= r11) goto L_0x01fd
            if (r10 != r5) goto L_0x01f6
            float[] r8 = r8.mArrayValues
            r8 = r8[r9]
            goto L_0x01fe
        L_0x01f6:
            int[] r11 = r8.mArrayNextIndices
            r9 = r11[r9]
            int r10 = r10 + 1
            goto L_0x01e8
        L_0x01fd:
            r8 = r7
        L_0x01fe:
            androidx.constraintlayout.solver.ArrayLinkedVariables r9 = r4.variables
            r10 = 1
            r9.add(r6, r8, r10)
            int r5 = r5 + 1
            goto L_0x01d6
        L_0x0207:
            androidx.constraintlayout.solver.ArrayRow r4 = r0.mTempGoal
            r0.optimize(r4)
            int r4 = r3.definitionId
            r5 = -1
            if (r4 != r5) goto L_0x0231
            androidx.constraintlayout.solver.SolverVariable r4 = r1.variable
            if (r4 != r3) goto L_0x0221
            androidx.constraintlayout.solver.ArrayLinkedVariables r4 = r1.variables
            r5 = 0
            androidx.constraintlayout.solver.SolverVariable r3 = r4.getPivotCandidate(r5, r3)
            if (r3 == 0) goto L_0x0221
            r1.pivot(r3)
        L_0x0221:
            boolean r3 = r1.isSimpleDefinition
            if (r3 != 0) goto L_0x022a
            androidx.constraintlayout.solver.SolverVariable r3 = r1.variable
            r3.updateReferencesWithNewDefinition(r1)
        L_0x022a:
            int r3 = r0.mNumRows
            r9 = 1
            int r3 = r3 - r9
            r0.mNumRows = r3
            goto L_0x0232
        L_0x0231:
            r9 = 1
        L_0x0232:
            r3 = r9
            goto L_0x0236
        L_0x0234:
            r9 = r4
            r3 = 0
        L_0x0236:
            androidx.constraintlayout.solver.SolverVariable r4 = r1.variable
            if (r4 == 0) goto L_0x0246
            androidx.constraintlayout.solver.SolverVariable$Type r4 = r4.mType
            if (r4 == r2) goto L_0x0244
            float r2 = r1.constantValue
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 < 0) goto L_0x0246
        L_0x0244:
            r4 = r9
            goto L_0x0247
        L_0x0246:
            r4 = 0
        L_0x0247:
            if (r4 != 0) goto L_0x024a
            return
        L_0x024a:
            r5 = r3
            goto L_0x024d
        L_0x024c:
            r5 = 0
        L_0x024d:
            if (r5 != 0) goto L_0x0252
            r19.addRow(r20)
        L_0x0252:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.LinearSystem.addConstraint(androidx.constraintlayout.solver.ArrayRow):void");
    }

    public final void addRow(ArrayRow arrayRow) {
        ArrayRow[] arrayRowArr = this.mRows;
        int i = this.mNumRows;
        if (arrayRowArr[i] != null) {
            Pools$SimplePool pools$SimplePool = this.mCache.arrayRowPool;
            ArrayRow arrayRow2 = arrayRowArr[i];
            Objects.requireNonNull(pools$SimplePool);
            int i2 = pools$SimplePool.mPoolSize;
            Object[] objArr = pools$SimplePool.mPool;
            if (i2 < objArr.length) {
                objArr[i2] = arrayRow2;
                pools$SimplePool.mPoolSize = i2 + 1;
            }
        }
        ArrayRow[] arrayRowArr2 = this.mRows;
        int i3 = this.mNumRows;
        arrayRowArr2[i3] = arrayRow;
        SolverVariable solverVariable = arrayRow.variable;
        solverVariable.definitionId = i3;
        this.mNumRows = i3 + 1;
        solverVariable.updateReferencesWithNewDefinition(arrayRow);
    }

    public final SolverVariable createErrorVariable(int i) {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable acquireSolverVariable = acquireSolverVariable(SolverVariable.Type.ERROR);
        int i2 = this.mVariablesID + 1;
        this.mVariablesID = i2;
        this.mNumColumns++;
        acquireSolverVariable.f14id = i2;
        acquireSolverVariable.strength = i;
        this.mCache.mIndexedVariables[i2] = acquireSolverVariable;
        OptimizedPriorityGoalRow optimizedPriorityGoalRow = this.mGoal;
        Objects.requireNonNull(optimizedPriorityGoalRow);
        OptimizedPriorityGoalRow.GoalVariableAccessor goalVariableAccessor = optimizedPriorityGoalRow.accessor;
        Objects.requireNonNull(goalVariableAccessor);
        goalVariableAccessor.variable = acquireSolverVariable;
        OptimizedPriorityGoalRow.GoalVariableAccessor goalVariableAccessor2 = optimizedPriorityGoalRow.accessor;
        Objects.requireNonNull(goalVariableAccessor2);
        Arrays.fill(goalVariableAccessor2.variable.goalStrengthVector, 0.0f);
        acquireSolverVariable.goalStrengthVector[acquireSolverVariable.strength] = 1.0f;
        optimizedPriorityGoalRow.addToGoal(acquireSolverVariable);
        return acquireSolverVariable;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: androidx.constraintlayout.solver.ArrayRow} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.constraintlayout.solver.ArrayRow createRow() {
        /*
            r5 = this;
            androidx.constraintlayout.solver.Cache r0 = r5.mCache
            androidx.constraintlayout.solver.Pools$SimplePool r0 = r0.arrayRowPool
            java.util.Objects.requireNonNull(r0)
            int r1 = r0.mPoolSize
            r2 = 0
            if (r1 <= 0) goto L_0x0017
            int r1 = r1 + -1
            java.lang.Object[] r3 = r0.mPool
            r4 = r3[r1]
            r3[r1] = r2
            r0.mPoolSize = r1
            goto L_0x0018
        L_0x0017:
            r4 = r2
        L_0x0018:
            androidx.constraintlayout.solver.ArrayRow r4 = (androidx.constraintlayout.solver.ArrayRow) r4
            if (r4 != 0) goto L_0x0024
            androidx.constraintlayout.solver.ArrayRow r4 = new androidx.constraintlayout.solver.ArrayRow
            androidx.constraintlayout.solver.Cache r5 = r5.mCache
            r4.<init>(r5)
            goto L_0x0031
        L_0x0024:
            r4.variable = r2
            androidx.constraintlayout.solver.ArrayLinkedVariables r5 = r4.variables
            r5.clear()
            r5 = 0
            r4.constantValue = r5
            r5 = 0
            r4.isSimpleDefinition = r5
        L_0x0031:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.LinearSystem.createRow():androidx.constraintlayout.solver.ArrayRow");
    }

    public final SolverVariable createSlackVariable() {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            increaseTableSize();
        }
        SolverVariable acquireSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK);
        int i = this.mVariablesID + 1;
        this.mVariablesID = i;
        this.mNumColumns++;
        acquireSolverVariable.f14id = i;
        this.mCache.mIndexedVariables[i] = acquireSolverVariable;
        return acquireSolverVariable;
    }

    public final void increaseTableSize() {
        int i = this.TABLE_SIZE * 2;
        this.TABLE_SIZE = i;
        this.mRows = (ArrayRow[]) Arrays.copyOf(this.mRows, i);
        Cache cache = this.mCache;
        cache.mIndexedVariables = (SolverVariable[]) Arrays.copyOf(cache.mIndexedVariables, this.TABLE_SIZE);
        int i2 = this.TABLE_SIZE;
        this.mAlreadyTestedCandidates = new boolean[i2];
        this.mMaxColumns = i2;
        this.mMaxRows = i2;
    }

    public final void minimizeGoal(OptimizedPriorityGoalRow optimizedPriorityGoalRow) throws Exception {
        float f;
        int i;
        boolean z;
        SolverVariable.Type type = SolverVariable.Type.UNRESTRICTED;
        int i2 = 0;
        while (true) {
            f = 0.0f;
            i = 1;
            if (i2 >= this.mNumRows) {
                z = false;
                break;
            }
            ArrayRow[] arrayRowArr = this.mRows;
            if (arrayRowArr[i2].variable.mType != type && arrayRowArr[i2].constantValue < 0.0f) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            boolean z2 = false;
            int i3 = 0;
            while (!z2) {
                i3 += i;
                float f2 = Float.MAX_VALUE;
                int i4 = -1;
                int i5 = -1;
                int i6 = 0;
                int i7 = 0;
                while (i6 < this.mNumRows) {
                    ArrayRow arrayRow = this.mRows[i6];
                    if (arrayRow.variable.mType != type && !arrayRow.isSimpleDefinition && arrayRow.constantValue < f) {
                        int i8 = i;
                        while (i8 < this.mNumColumns) {
                            SolverVariable solverVariable = this.mCache.mIndexedVariables[i8];
                            float f3 = arrayRow.variables.get(solverVariable);
                            if (f3 > f) {
                                for (int i9 = 0; i9 < 8; i9++) {
                                    float f4 = solverVariable.strengthVector[i9] / f3;
                                    if ((f4 < f2 && i9 == i7) || i9 > i7) {
                                        i7 = i9;
                                        f2 = f4;
                                        i4 = i6;
                                        i5 = i8;
                                    }
                                }
                            }
                            i8++;
                            f = 0.0f;
                        }
                    }
                    i6++;
                    f = 0.0f;
                    i = 1;
                }
                if (i4 != -1) {
                    ArrayRow arrayRow2 = this.mRows[i4];
                    arrayRow2.variable.definitionId = -1;
                    arrayRow2.pivot(this.mCache.mIndexedVariables[i5]);
                    SolverVariable solverVariable2 = arrayRow2.variable;
                    solverVariable2.definitionId = i4;
                    solverVariable2.updateReferencesWithNewDefinition(arrayRow2);
                } else {
                    z2 = true;
                }
                if (i3 > this.mNumColumns / 2) {
                    z2 = true;
                }
                f = 0.0f;
                i = 1;
            }
        }
        optimize(optimizedPriorityGoalRow);
        for (int i10 = 0; i10 < this.mNumRows; i10++) {
            ArrayRow arrayRow3 = this.mRows[i10];
            arrayRow3.variable.computedValue = arrayRow3.constantValue;
        }
    }

    public final int optimize(ArrayRow arrayRow) {
        boolean z;
        ArrayRow arrayRow2 = arrayRow;
        int i = 0;
        for (int i2 = 0; i2 < this.mNumColumns; i2++) {
            this.mAlreadyTestedCandidates[i2] = false;
        }
        boolean z2 = false;
        int i3 = 0;
        while (!z2) {
            i3++;
            if (i3 >= this.mNumColumns * 2) {
                return i3;
            }
            Objects.requireNonNull(arrayRow);
            SolverVariable solverVariable = arrayRow2.variable;
            if (solverVariable != null) {
                this.mAlreadyTestedCandidates[solverVariable.f14id] = true;
            }
            SolverVariable pivotCandidate = arrayRow2.getPivotCandidate(this.mAlreadyTestedCandidates);
            if (pivotCandidate != null) {
                boolean[] zArr = this.mAlreadyTestedCandidates;
                int i4 = pivotCandidate.f14id;
                if (zArr[i4]) {
                    return i3;
                }
                zArr[i4] = true;
            }
            if (pivotCandidate != null) {
                float f = Float.MAX_VALUE;
                int i5 = i;
                int i6 = -1;
                while (i5 < this.mNumRows) {
                    ArrayRow arrayRow3 = this.mRows[i5];
                    if (arrayRow3.variable.mType != SolverVariable.Type.UNRESTRICTED && !arrayRow3.isSimpleDefinition) {
                        ArrayLinkedVariables arrayLinkedVariables = arrayRow3.variables;
                        Objects.requireNonNull(arrayLinkedVariables);
                        int i7 = arrayLinkedVariables.mHead;
                        if (i7 != -1) {
                            int i8 = i;
                            while (true) {
                                if (i7 == -1 || i8 >= arrayLinkedVariables.currentSize) {
                                    break;
                                } else if (arrayLinkedVariables.mArrayIndices[i7] == pivotCandidate.f14id) {
                                    z = true;
                                    break;
                                } else {
                                    i7 = arrayLinkedVariables.mArrayNextIndices[i7];
                                    i8++;
                                }
                            }
                        }
                        z = false;
                        if (z) {
                            float f2 = arrayRow3.variables.get(pivotCandidate);
                            if (f2 < 0.0f) {
                                float f3 = (-arrayRow3.constantValue) / f2;
                                if (f3 < f) {
                                    i6 = i5;
                                    f = f3;
                                }
                            }
                        }
                    }
                    i5++;
                    i = 0;
                }
                if (i6 > -1) {
                    ArrayRow arrayRow4 = this.mRows[i6];
                    arrayRow4.variable.definitionId = -1;
                    arrayRow4.pivot(pivotCandidate);
                    SolverVariable solverVariable2 = arrayRow4.variable;
                    solverVariable2.definitionId = i6;
                    solverVariable2.updateReferencesWithNewDefinition(arrayRow4);
                }
            } else {
                z2 = true;
            }
            i = 0;
        }
        return i3;
    }

    public LinearSystem() {
        int i = 0;
        this.mVariablesID = 0;
        this.TABLE_SIZE = 32;
        this.mMaxColumns = 32;
        this.mRows = null;
        this.newgraphOptimizer = false;
        this.mAlreadyTestedCandidates = new boolean[32];
        this.mNumColumns = 1;
        this.mNumRows = 0;
        this.mMaxRows = 32;
        this.mPoolVariables = new SolverVariable[POOL_SIZE];
        this.mPoolVariablesCount = 0;
        this.mRows = new ArrayRow[32];
        while (true) {
            ArrayRow[] arrayRowArr = this.mRows;
            if (i < arrayRowArr.length) {
                ArrayRow arrayRow = arrayRowArr[i];
                if (arrayRow != null) {
                    Pools$SimplePool pools$SimplePool = this.mCache.arrayRowPool;
                    Objects.requireNonNull(pools$SimplePool);
                    int i2 = pools$SimplePool.mPoolSize;
                    Object[] objArr = pools$SimplePool.mPool;
                    if (i2 < objArr.length) {
                        objArr[i2] = arrayRow;
                        pools$SimplePool.mPoolSize = i2 + 1;
                    }
                }
                this.mRows[i] = null;
                i++;
            } else {
                Cache cache = new Cache();
                this.mCache = cache;
                this.mGoal = new OptimizedPriorityGoalRow(cache);
                this.mTempGoal = new ArrayRow(cache);
                return;
            }
        }
    }

    public static int getObjectVariableValue(ConstraintAnchor constraintAnchor) {
        Objects.requireNonNull(constraintAnchor);
        SolverVariable solverVariable = constraintAnchor.mSolverVariable;
        if (solverVariable != null) {
            return (int) (solverVariable.computedValue + 0.5f);
        }
        return 0;
    }

    public final void addCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int i, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int i2, int i3) {
        ArrayRow createRow = createRow();
        if (solverVariable2 == solverVariable3) {
            createRow.variables.put(solverVariable, 1.0f);
            createRow.variables.put(solverVariable4, 1.0f);
            createRow.variables.put(solverVariable2, -2.0f);
        } else if (f == 0.5f) {
            createRow.variables.put(solverVariable, 1.0f);
            createRow.variables.put(solverVariable2, -1.0f);
            createRow.variables.put(solverVariable3, -1.0f);
            createRow.variables.put(solverVariable4, 1.0f);
            if (i > 0 || i2 > 0) {
                createRow.constantValue = (float) ((-i) + i2);
            }
        } else if (f <= 0.0f) {
            createRow.variables.put(solverVariable, -1.0f);
            createRow.variables.put(solverVariable2, 1.0f);
            createRow.constantValue = (float) i;
        } else if (f >= 1.0f) {
            createRow.variables.put(solverVariable4, -1.0f);
            createRow.variables.put(solverVariable3, 1.0f);
            createRow.constantValue = (float) (-i2);
        } else {
            float f2 = 1.0f - f;
            createRow.variables.put(solverVariable, f2 * 1.0f);
            createRow.variables.put(solverVariable2, f2 * -1.0f);
            createRow.variables.put(solverVariable3, -1.0f * f);
            createRow.variables.put(solverVariable4, 1.0f * f);
            if (i > 0 || i2 > 0) {
                createRow.constantValue = (((float) i2) * f) + (((float) (-i)) * f2);
            }
        }
        if (i3 != 7) {
            createRow.addError(this, i3);
        }
        addConstraint(createRow);
    }

    public final void addGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowGreaterThan(solverVariable, solverVariable2, createSlackVariable, i);
        if (i2 != 7) {
            createRow.variables.put(createErrorVariable(i2), (float) ((int) (createRow.variables.get(createSlackVariable) * -1.0f)));
        }
        addConstraint(createRow);
    }

    public final void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i, int i2) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowLowerThan(solverVariable, solverVariable2, createSlackVariable, i);
        if (i2 != 7) {
            createRow.variables.put(createErrorVariable(i2), (float) ((int) (createRow.variables.get(createSlackVariable) * -1.0f)));
        }
        addConstraint(createRow);
    }

    public final void addEquality(SolverVariable solverVariable, int i) {
        int i2 = solverVariable.definitionId;
        if (i2 != -1) {
            ArrayRow arrayRow = this.mRows[i2];
            if (arrayRow.isSimpleDefinition) {
                arrayRow.constantValue = (float) i;
            } else if (arrayRow.variables.currentSize == 0) {
                arrayRow.isSimpleDefinition = true;
                arrayRow.constantValue = (float) i;
            } else {
                ArrayRow createRow = createRow();
                if (i < 0) {
                    createRow.constantValue = (float) (i * -1);
                    createRow.variables.put(solverVariable, 1.0f);
                } else {
                    createRow.constantValue = (float) i;
                    createRow.variables.put(solverVariable, -1.0f);
                }
                addConstraint(createRow);
            }
        } else {
            ArrayRow createRow2 = createRow();
            createRow2.variable = solverVariable;
            float f = (float) i;
            solverVariable.computedValue = f;
            createRow2.constantValue = f;
            createRow2.isSimpleDefinition = true;
            addConstraint(createRow2);
        }
    }
}
