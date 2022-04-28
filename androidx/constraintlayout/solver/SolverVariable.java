package androidx.constraintlayout.solver;

import java.util.Arrays;

public final class SolverVariable {
    public float computedValue;
    public int definitionId = -1;
    public float[] goalStrengthVector = new float[8];

    /* renamed from: id */
    public int f14id = -1;
    public boolean inGoal;
    public ArrayRow[] mClientEquations = new ArrayRow[8];
    public int mClientEquationsCount = 0;
    public Type mType;
    public int strength = 0;
    public float[] strengthVector = new float[8];
    public int usageInRowCount = 0;

    public enum Type {
        UNRESTRICTED,
        SLACK,
        ERROR,
        UNKNOWN
    }

    public final void addToRow(ArrayRow arrayRow) {
        int i = 0;
        while (true) {
            int i2 = this.mClientEquationsCount;
            if (i >= i2) {
                ArrayRow[] arrayRowArr = this.mClientEquations;
                if (i2 >= arrayRowArr.length) {
                    this.mClientEquations = (ArrayRow[]) Arrays.copyOf(arrayRowArr, arrayRowArr.length * 2);
                }
                ArrayRow[] arrayRowArr2 = this.mClientEquations;
                int i3 = this.mClientEquationsCount;
                arrayRowArr2[i3] = arrayRow;
                this.mClientEquationsCount = i3 + 1;
                return;
            } else if (this.mClientEquations[i] != arrayRow) {
                i++;
            } else {
                return;
            }
        }
    }

    public final String toString() {
        return "null";
    }

    public final void removeFromRow(ArrayRow arrayRow) {
        int i = this.mClientEquationsCount;
        int i2 = 0;
        while (i2 < i) {
            if (this.mClientEquations[i2] == arrayRow) {
                while (i2 < i - 1) {
                    ArrayRow[] arrayRowArr = this.mClientEquations;
                    int i3 = i2 + 1;
                    arrayRowArr[i2] = arrayRowArr[i3];
                    i2 = i3;
                }
                this.mClientEquationsCount--;
                return;
            }
            i2++;
        }
    }

    public final void reset() {
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.f14id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.inGoal = false;
        Arrays.fill(this.goalStrengthVector, 0.0f);
    }

    public final void updateReferencesWithNewDefinition(ArrayRow arrayRow) {
        int i = this.mClientEquationsCount;
        for (int i2 = 0; i2 < i; i2++) {
            this.mClientEquations[i2].updateFromRow(arrayRow);
        }
        this.mClientEquationsCount = 0;
    }

    public SolverVariable(Type type) {
        this.mType = type;
    }
}
