package androidx.recyclerview.widget;

import androidx.core.util.Pools$SimplePool;
import androidx.recyclerview.widget.OpReorderer;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;

public final class AdapterHelper implements OpReorderer.Callback {
    public final Callback mCallback;
    public int mExistingUpdateTypes = 0;
    public final OpReorderer mOpReorderer;
    public final ArrayList<UpdateOp> mPendingUpdates = new ArrayList<>();
    public final ArrayList<UpdateOp> mPostponedList = new ArrayList<>();
    public Pools$SimplePool mUpdateOpPool = new Pools$SimplePool(30);

    public interface Callback {
    }

    public static final class UpdateOp {
        public int cmd;
        public int itemCount;
        public Object payload;
        public int positionStart;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UpdateOp)) {
                return false;
            }
            UpdateOp updateOp = (UpdateOp) obj;
            int i = this.cmd;
            if (i != updateOp.cmd) {
                return false;
            }
            if (i == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == updateOp.positionStart && this.positionStart == updateOp.itemCount) {
                return true;
            }
            if (this.itemCount != updateOp.itemCount || this.positionStart != updateOp.positionStart) {
                return false;
            }
            Object obj2 = this.payload;
            if (obj2 != null) {
                if (!obj2.equals(updateOp.payload)) {
                    return false;
                }
            } else if (updateOp.payload != null) {
                return false;
            }
            return true;
        }

        public final int hashCode() {
            return (((this.cmd * 31) + this.positionStart) * 31) + this.itemCount;
        }

        public final String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append("[");
            int i = this.cmd;
            if (i == 1) {
                str = "add";
            } else if (i == 2) {
                str = "rm";
            } else if (i == 4) {
                str = "up";
            } else if (i != 8) {
                str = "??";
            } else {
                str = "mv";
            }
            sb.append(str);
            sb.append(",s:");
            sb.append(this.positionStart);
            sb.append("c:");
            sb.append(this.itemCount);
            sb.append(",p:");
            sb.append(this.payload);
            sb.append("]");
            return sb.toString();
        }

        public UpdateOp(int i, int i2, int i3, Object obj) {
            this.cmd = i;
            this.positionStart = i2;
            this.itemCount = i3;
            this.payload = obj;
        }
    }

    public final boolean canFindInPreLayout(int i) {
        int size = this.mPostponedList.size();
        for (int i2 = 0; i2 < size; i2++) {
            UpdateOp updateOp = this.mPostponedList.get(i2);
            int i3 = updateOp.cmd;
            if (i3 == 8) {
                if (findPositionOffset(updateOp.itemCount, i2 + 1) == i) {
                    return true;
                }
            } else if (i3 == 1) {
                int i4 = updateOp.positionStart;
                int i5 = updateOp.itemCount + i4;
                while (i4 < i5) {
                    if (findPositionOffset(i4, i2 + 1) == i) {
                        return true;
                    }
                    i4++;
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    public final void consumePostponedUpdates() {
        int size = this.mPostponedList.size();
        for (int i = 0; i < size; i++) {
            RecyclerView.C03376 r3 = (RecyclerView.C03376) this.mCallback;
            Objects.requireNonNull(r3);
            r3.dispatchUpdate(this.mPostponedList.get(i));
        }
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    public final void dispatchAndUpdateViewHolders(UpdateOp updateOp) {
        int i;
        boolean z;
        int i2 = updateOp.cmd;
        if (i2 == 1 || i2 == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int updatePositionWithPostponed = updatePositionWithPostponed(updateOp.positionStart, i2);
        int i3 = updateOp.positionStart;
        int i4 = updateOp.cmd;
        if (i4 == 2) {
            i = 0;
        } else if (i4 == 4) {
            i = 1;
        } else {
            throw new IllegalArgumentException("op should be remove or update." + updateOp);
        }
        int i5 = 1;
        for (int i6 = 1; i6 < updateOp.itemCount; i6++) {
            int updatePositionWithPostponed2 = updatePositionWithPostponed((i * i6) + updateOp.positionStart, updateOp.cmd);
            int i7 = updateOp.cmd;
            if (i7 == 2 ? updatePositionWithPostponed2 != updatePositionWithPostponed : !(i7 == 4 && updatePositionWithPostponed2 == updatePositionWithPostponed + 1)) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                i5++;
            } else {
                UpdateOp obtainUpdateOp = obtainUpdateOp(i7, updatePositionWithPostponed, i5, updateOp.payload);
                dispatchFirstPassAndUpdateViewHolders(obtainUpdateOp, i3);
                obtainUpdateOp.payload = null;
                this.mUpdateOpPool.release(obtainUpdateOp);
                if (updateOp.cmd == 4) {
                    i3 += i5;
                }
                i5 = 1;
                updatePositionWithPostponed = updatePositionWithPostponed2;
            }
        }
        Object obj = updateOp.payload;
        updateOp.payload = null;
        this.mUpdateOpPool.release(updateOp);
        if (i5 > 0) {
            UpdateOp obtainUpdateOp2 = obtainUpdateOp(updateOp.cmd, updatePositionWithPostponed, i5, obj);
            dispatchFirstPassAndUpdateViewHolders(obtainUpdateOp2, i3);
            obtainUpdateOp2.payload = null;
            this.mUpdateOpPool.release(obtainUpdateOp2);
        }
    }

    public final void dispatchFirstPassAndUpdateViewHolders(UpdateOp updateOp, int i) {
        RecyclerView.C03376 r0 = (RecyclerView.C03376) this.mCallback;
        Objects.requireNonNull(r0);
        r0.dispatchUpdate(updateOp);
        int i2 = updateOp.cmd;
        if (i2 == 2) {
            Callback callback = this.mCallback;
            int i3 = updateOp.itemCount;
            RecyclerView.C03376 r2 = (RecyclerView.C03376) callback;
            Objects.requireNonNull(r2);
            RecyclerView.this.offsetPositionRecordsForRemove(i, i3, true);
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.mItemsAddedOrRemoved = true;
            recyclerView.mState.mDeletedInvisibleItemCountSincePreviousLayout += i3;
        } else if (i2 == 4) {
            ((RecyclerView.C03376) this.mCallback).markViewHoldersUpdated(i, updateOp.itemCount, updateOp.payload);
        } else {
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
    }

    public final int findPositionOffset(int i, int i2) {
        int size = this.mPostponedList.size();
        while (i2 < size) {
            UpdateOp updateOp = this.mPostponedList.get(i2);
            int i3 = updateOp.cmd;
            if (i3 == 8) {
                int i4 = updateOp.positionStart;
                if (i4 == i) {
                    i = updateOp.itemCount;
                } else {
                    if (i4 < i) {
                        i--;
                    }
                    if (updateOp.itemCount <= i) {
                        i++;
                    }
                }
            } else {
                int i5 = updateOp.positionStart;
                if (i5 > i) {
                    continue;
                } else if (i3 == 2) {
                    int i6 = updateOp.itemCount;
                    if (i < i5 + i6) {
                        return -1;
                    }
                    i -= i6;
                } else if (i3 == 1) {
                    i += updateOp.itemCount;
                }
            }
            i2++;
        }
        return i;
    }

    public final boolean hasPendingUpdates() {
        if (this.mPendingUpdates.size() > 0) {
            return true;
        }
        return false;
    }

    public final UpdateOp obtainUpdateOp(int i, int i2, int i3, Object obj) {
        UpdateOp updateOp = (UpdateOp) this.mUpdateOpPool.acquire();
        if (updateOp == null) {
            return new UpdateOp(i, i2, i3, obj);
        }
        updateOp.cmd = i;
        updateOp.positionStart = i2;
        updateOp.itemCount = i3;
        updateOp.payload = obj;
        return updateOp;
    }

    public final void postponeAndUpdateViewHolders(UpdateOp updateOp) {
        this.mPostponedList.add(updateOp);
        int i = updateOp.cmd;
        if (i == 1) {
            ((RecyclerView.C03376) this.mCallback).offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
        } else if (i == 2) {
            Callback callback = this.mCallback;
            int i2 = updateOp.positionStart;
            int i3 = updateOp.itemCount;
            RecyclerView.C03376 r4 = (RecyclerView.C03376) callback;
            Objects.requireNonNull(r4);
            RecyclerView.this.offsetPositionRecordsForRemove(i2, i3, false);
            RecyclerView.this.mItemsAddedOrRemoved = true;
        } else if (i == 4) {
            ((RecyclerView.C03376) this.mCallback).markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
        } else if (i == 8) {
            ((RecyclerView.C03376) this.mCallback).offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
        } else {
            throw new IllegalArgumentException("Unknown update op type for " + updateOp);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:182:0x0009 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x013f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void preProcess() {
        /*
            r16 = this;
            r0 = r16
            androidx.recyclerview.widget.OpReorderer r1 = r0.mOpReorderer
            java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r2 = r0.mPendingUpdates
            java.util.Objects.requireNonNull(r1)
        L_0x0009:
            int r3 = r2.size()
            r4 = 1
            int r3 = r3 - r4
            r6 = 0
        L_0x0010:
            r7 = 8
            r8 = -1
            if (r3 < 0) goto L_0x0026
            java.lang.Object r9 = r2.get(r3)
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r9 = (androidx.recyclerview.widget.AdapterHelper.UpdateOp) r9
            int r9 = r9.cmd
            if (r9 != r7) goto L_0x0022
            if (r6 == 0) goto L_0x0023
            goto L_0x0027
        L_0x0022:
            r6 = r4
        L_0x0023:
            int r3 = r3 + -1
            goto L_0x0010
        L_0x0026:
            r3 = r8
        L_0x0027:
            r6 = 4
            r9 = 2
            r10 = 0
            if (r3 == r8) goto L_0x01df
            int r7 = r3 + 1
            java.lang.Object r11 = r2.get(r3)
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r11 = (androidx.recyclerview.widget.AdapterHelper.UpdateOp) r11
            java.lang.Object r12 = r2.get(r7)
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r12 = (androidx.recyclerview.widget.AdapterHelper.UpdateOp) r12
            int r13 = r12.cmd
            if (r13 == r4) goto L_0x01b5
            if (r13 == r9) goto L_0x00b1
            if (r13 == r6) goto L_0x0043
            goto L_0x0009
        L_0x0043:
            int r5 = r11.itemCount
            int r8 = r12.positionStart
            if (r5 >= r8) goto L_0x004e
            int r8 = r8 + -1
            r12.positionStart = r8
            goto L_0x0064
        L_0x004e:
            int r9 = r12.itemCount
            int r8 = r8 + r9
            if (r5 >= r8) goto L_0x0064
            int r9 = r9 + -1
            r12.itemCount = r9
            androidx.recyclerview.widget.OpReorderer$Callback r5 = r1.mCallback
            int r8 = r11.positionStart
            java.lang.Object r9 = r12.payload
            androidx.recyclerview.widget.AdapterHelper r5 = (androidx.recyclerview.widget.AdapterHelper) r5
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r4 = r5.obtainUpdateOp(r6, r8, r4, r9)
            goto L_0x0065
        L_0x0064:
            r4 = r10
        L_0x0065:
            int r5 = r11.positionStart
            int r8 = r12.positionStart
            if (r5 > r8) goto L_0x0070
            int r8 = r8 + 1
            r12.positionStart = r8
            goto L_0x0088
        L_0x0070:
            int r9 = r12.itemCount
            int r8 = r8 + r9
            if (r5 >= r8) goto L_0x0088
            int r8 = r8 - r5
            androidx.recyclerview.widget.OpReorderer$Callback r9 = r1.mCallback
            int r5 = r5 + 1
            java.lang.Object r13 = r12.payload
            androidx.recyclerview.widget.AdapterHelper r9 = (androidx.recyclerview.widget.AdapterHelper) r9
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r5 = r9.obtainUpdateOp(r6, r5, r8, r13)
            int r6 = r12.itemCount
            int r6 = r6 - r8
            r12.itemCount = r6
            goto L_0x0089
        L_0x0088:
            r5 = r10
        L_0x0089:
            r2.set(r7, r11)
            int r6 = r12.itemCount
            if (r6 <= 0) goto L_0x0094
            r2.set(r3, r12)
            goto L_0x00a5
        L_0x0094:
            r2.remove(r3)
            androidx.recyclerview.widget.OpReorderer$Callback r6 = r1.mCallback
            androidx.recyclerview.widget.AdapterHelper r6 = (androidx.recyclerview.widget.AdapterHelper) r6
            java.util.Objects.requireNonNull(r6)
            r12.payload = r10
            androidx.core.util.Pools$SimplePool r6 = r6.mUpdateOpPool
            r6.release(r12)
        L_0x00a5:
            if (r4 == 0) goto L_0x00aa
            r2.add(r3, r4)
        L_0x00aa:
            if (r5 == 0) goto L_0x0009
            r2.add(r3, r5)
            goto L_0x0009
        L_0x00b1:
            int r6 = r11.positionStart
            int r8 = r11.itemCount
            if (r6 >= r8) goto L_0x00c6
            int r13 = r12.positionStart
            if (r13 != r6) goto L_0x00c4
            int r13 = r12.itemCount
            int r6 = r8 - r6
            if (r13 != r6) goto L_0x00c4
            r5 = r4
            r6 = 0
            goto L_0x00d6
        L_0x00c4:
            r5 = 0
            goto L_0x00d2
        L_0x00c6:
            int r13 = r12.positionStart
            int r14 = r8 + 1
            if (r13 != r14) goto L_0x00d4
            int r13 = r12.itemCount
            int r6 = r6 - r8
            if (r13 != r6) goto L_0x00d4
            r5 = r4
        L_0x00d2:
            r6 = r5
            goto L_0x00d6
        L_0x00d4:
            r6 = r4
            r5 = 0
        L_0x00d6:
            int r13 = r12.positionStart
            if (r8 >= r13) goto L_0x00df
            int r13 = r13 + -1
            r12.positionStart = r13
            goto L_0x0103
        L_0x00df:
            int r14 = r12.itemCount
            int r13 = r13 + r14
            if (r8 >= r13) goto L_0x0103
            int r14 = r14 + -1
            r12.itemCount = r14
            r11.cmd = r9
            r11.itemCount = r4
            int r3 = r12.itemCount
            if (r3 != 0) goto L_0x0009
            r2.remove(r7)
            androidx.recyclerview.widget.OpReorderer$Callback r3 = r1.mCallback
            androidx.recyclerview.widget.AdapterHelper r3 = (androidx.recyclerview.widget.AdapterHelper) r3
            java.util.Objects.requireNonNull(r3)
            r12.payload = r10
            androidx.core.util.Pools$SimplePool r3 = r3.mUpdateOpPool
            r3.release(r12)
            goto L_0x0009
        L_0x0103:
            int r4 = r11.positionStart
            int r8 = r12.positionStart
            if (r4 > r8) goto L_0x010e
            int r8 = r8 + 1
            r12.positionStart = r8
            goto L_0x0126
        L_0x010e:
            int r13 = r12.itemCount
            int r8 = r8 + r13
            if (r4 >= r8) goto L_0x0126
            int r8 = r8 - r4
            androidx.recyclerview.widget.OpReorderer$Callback r13 = r1.mCallback
            int r4 = r4 + 1
            androidx.recyclerview.widget.AdapterHelper r13 = (androidx.recyclerview.widget.AdapterHelper) r13
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r4 = r13.obtainUpdateOp(r9, r4, r8, r10)
            int r8 = r11.positionStart
            int r9 = r12.positionStart
            int r8 = r8 - r9
            r12.itemCount = r8
            goto L_0x0127
        L_0x0126:
            r4 = r10
        L_0x0127:
            if (r5 == 0) goto L_0x013f
            r2.set(r3, r12)
            r2.remove(r7)
            androidx.recyclerview.widget.OpReorderer$Callback r3 = r1.mCallback
            androidx.recyclerview.widget.AdapterHelper r3 = (androidx.recyclerview.widget.AdapterHelper) r3
            java.util.Objects.requireNonNull(r3)
            r11.payload = r10
            androidx.core.util.Pools$SimplePool r3 = r3.mUpdateOpPool
            r3.release(r11)
            goto L_0x0009
        L_0x013f:
            if (r6 == 0) goto L_0x0170
            if (r4 == 0) goto L_0x0159
            int r5 = r11.positionStart
            int r6 = r4.positionStart
            if (r5 <= r6) goto L_0x014e
            int r6 = r4.itemCount
            int r5 = r5 - r6
            r11.positionStart = r5
        L_0x014e:
            int r5 = r11.itemCount
            int r6 = r4.positionStart
            if (r5 <= r6) goto L_0x0159
            int r6 = r4.itemCount
            int r5 = r5 - r6
            r11.itemCount = r5
        L_0x0159:
            int r5 = r11.positionStart
            int r6 = r12.positionStart
            if (r5 <= r6) goto L_0x0164
            int r6 = r12.itemCount
            int r5 = r5 - r6
            r11.positionStart = r5
        L_0x0164:
            int r5 = r11.itemCount
            int r6 = r12.positionStart
            if (r5 <= r6) goto L_0x019e
            int r6 = r12.itemCount
            int r5 = r5 - r6
            r11.itemCount = r5
            goto L_0x019e
        L_0x0170:
            if (r4 == 0) goto L_0x0188
            int r5 = r11.positionStart
            int r6 = r4.positionStart
            if (r5 < r6) goto L_0x017d
            int r6 = r4.itemCount
            int r5 = r5 - r6
            r11.positionStart = r5
        L_0x017d:
            int r5 = r11.itemCount
            int r6 = r4.positionStart
            if (r5 < r6) goto L_0x0188
            int r6 = r4.itemCount
            int r5 = r5 - r6
            r11.itemCount = r5
        L_0x0188:
            int r5 = r11.positionStart
            int r6 = r12.positionStart
            if (r5 < r6) goto L_0x0193
            int r6 = r12.itemCount
            int r5 = r5 - r6
            r11.positionStart = r5
        L_0x0193:
            int r5 = r11.itemCount
            int r6 = r12.positionStart
            if (r5 < r6) goto L_0x019e
            int r6 = r12.itemCount
            int r5 = r5 - r6
            r11.itemCount = r5
        L_0x019e:
            r2.set(r3, r12)
            int r5 = r11.positionStart
            int r6 = r11.itemCount
            if (r5 == r6) goto L_0x01ab
            r2.set(r7, r11)
            goto L_0x01ae
        L_0x01ab:
            r2.remove(r7)
        L_0x01ae:
            if (r4 == 0) goto L_0x0009
            r2.add(r3, r4)
            goto L_0x0009
        L_0x01b5:
            int r4 = r11.itemCount
            int r6 = r12.positionStart
            if (r4 >= r6) goto L_0x01bd
            r5 = r8
            goto L_0x01be
        L_0x01bd:
            r5 = 0
        L_0x01be:
            int r8 = r11.positionStart
            if (r8 >= r6) goto L_0x01c4
            int r5 = r5 + 1
        L_0x01c4:
            if (r6 > r8) goto L_0x01cb
            int r6 = r12.itemCount
            int r8 = r8 + r6
            r11.positionStart = r8
        L_0x01cb:
            int r6 = r12.positionStart
            if (r6 > r4) goto L_0x01d4
            int r8 = r12.itemCount
            int r4 = r4 + r8
            r11.itemCount = r4
        L_0x01d4:
            int r6 = r6 + r5
            r12.positionStart = r6
            r2.set(r3, r12)
            r2.set(r7, r11)
            goto L_0x0009
        L_0x01df:
            java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r1 = r0.mPendingUpdates
            int r1 = r1.size()
            r2 = 0
        L_0x01e6:
            if (r2 >= r1) goto L_0x02ed
            java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r3 = r0.mPendingUpdates
            java.lang.Object r3 = r3.get(r2)
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r3 = (androidx.recyclerview.widget.AdapterHelper.UpdateOp) r3
            int r11 = r3.cmd
            if (r11 == r4) goto L_0x02e3
            if (r11 == r9) goto L_0x0273
            if (r11 == r6) goto L_0x0201
            if (r11 == r7) goto L_0x01fc
            goto L_0x02e6
        L_0x01fc:
            r0.postponeAndUpdateViewHolders(r3)
            goto L_0x02e6
        L_0x0201:
            int r11 = r3.positionStart
            int r12 = r3.itemCount
            int r12 = r12 + r11
            r15 = r8
            r13 = r11
            r14 = 0
        L_0x0209:
            if (r11 >= r12) goto L_0x0256
            androidx.recyclerview.widget.AdapterHelper$Callback r5 = r0.mCallback
            androidx.recyclerview.widget.RecyclerView$6 r5 = (androidx.recyclerview.widget.RecyclerView.C03376) r5
            java.util.Objects.requireNonNull(r5)
            androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
            androidx.recyclerview.widget.RecyclerView$ViewHolder r7 = r7.findViewHolderForPosition(r11, r4)
            if (r7 != 0) goto L_0x021c
        L_0x021a:
            r7 = r10
            goto L_0x0229
        L_0x021c:
            androidx.recyclerview.widget.RecyclerView r5 = androidx.recyclerview.widget.RecyclerView.this
            androidx.recyclerview.widget.ChildHelper r5 = r5.mChildHelper
            android.view.View r8 = r7.itemView
            boolean r5 = r5.isHidden(r8)
            if (r5 == 0) goto L_0x0229
            goto L_0x021a
        L_0x0229:
            if (r7 != 0) goto L_0x0241
            boolean r5 = r0.canFindInPreLayout(r11)
            if (r5 == 0) goto L_0x0232
            goto L_0x0241
        L_0x0232:
            if (r15 != r4) goto L_0x023f
            java.lang.Object r5 = r3.payload
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r5 = r0.obtainUpdateOp(r6, r13, r14, r5)
            r0.postponeAndUpdateViewHolders(r5)
            r13 = r11
            r14 = 0
        L_0x023f:
            r15 = 0
            goto L_0x024f
        L_0x0241:
            if (r15 != 0) goto L_0x024e
            java.lang.Object r5 = r3.payload
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r5 = r0.obtainUpdateOp(r6, r13, r14, r5)
            r0.dispatchAndUpdateViewHolders(r5)
            r13 = r11
            r14 = 0
        L_0x024e:
            r15 = r4
        L_0x024f:
            int r14 = r14 + r4
            int r11 = r11 + 1
            r7 = 8
            r8 = -1
            goto L_0x0209
        L_0x0256:
            int r5 = r3.itemCount
            if (r14 == r5) goto L_0x0267
            java.lang.Object r5 = r3.payload
            r3.payload = r10
            androidx.core.util.Pools$SimplePool r7 = r0.mUpdateOpPool
            r7.release(r3)
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r3 = r0.obtainUpdateOp(r6, r13, r14, r5)
        L_0x0267:
            if (r15 != 0) goto L_0x026e
            r0.dispatchAndUpdateViewHolders(r3)
            goto L_0x02e6
        L_0x026e:
            r0.postponeAndUpdateViewHolders(r3)
            goto L_0x02e6
        L_0x0273:
            int r5 = r3.positionStart
            int r7 = r3.itemCount
            int r7 = r7 + r5
            r8 = r5
            r11 = 0
            r12 = -1
        L_0x027b:
            if (r8 >= r7) goto L_0x02ca
            androidx.recyclerview.widget.AdapterHelper$Callback r13 = r0.mCallback
            androidx.recyclerview.widget.RecyclerView$6 r13 = (androidx.recyclerview.widget.RecyclerView.C03376) r13
            java.util.Objects.requireNonNull(r13)
            androidx.recyclerview.widget.RecyclerView r14 = androidx.recyclerview.widget.RecyclerView.this
            androidx.recyclerview.widget.RecyclerView$ViewHolder r14 = r14.findViewHolderForPosition(r8, r4)
            if (r14 != 0) goto L_0x028e
        L_0x028c:
            r14 = r10
            goto L_0x029b
        L_0x028e:
            androidx.recyclerview.widget.RecyclerView r13 = androidx.recyclerview.widget.RecyclerView.this
            androidx.recyclerview.widget.ChildHelper r13 = r13.mChildHelper
            android.view.View r15 = r14.itemView
            boolean r13 = r13.isHidden(r15)
            if (r13 == 0) goto L_0x029b
            goto L_0x028c
        L_0x029b:
            if (r14 != 0) goto L_0x02b2
            boolean r13 = r0.canFindInPreLayout(r8)
            if (r13 == 0) goto L_0x02a4
            goto L_0x02b2
        L_0x02a4:
            if (r12 != r4) goto L_0x02af
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r12 = r0.obtainUpdateOp(r9, r5, r11, r10)
            r0.postponeAndUpdateViewHolders(r12)
            r12 = r4
            goto L_0x02b0
        L_0x02af:
            r12 = 0
        L_0x02b0:
            r13 = 0
            goto L_0x02bf
        L_0x02b2:
            if (r12 != 0) goto L_0x02bd
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r12 = r0.obtainUpdateOp(r9, r5, r11, r10)
            r0.dispatchAndUpdateViewHolders(r12)
            r12 = r4
            goto L_0x02be
        L_0x02bd:
            r12 = 0
        L_0x02be:
            r13 = r4
        L_0x02bf:
            if (r12 == 0) goto L_0x02c5
            int r8 = r8 - r11
            int r7 = r7 - r11
            r11 = r4
            goto L_0x02c7
        L_0x02c5:
            int r11 = r11 + 1
        L_0x02c7:
            int r8 = r8 + r4
            r12 = r13
            goto L_0x027b
        L_0x02ca:
            int r7 = r3.itemCount
            if (r11 == r7) goto L_0x02d9
            r3.payload = r10
            androidx.core.util.Pools$SimplePool r7 = r0.mUpdateOpPool
            r7.release(r3)
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r3 = r0.obtainUpdateOp(r9, r5, r11, r10)
        L_0x02d9:
            if (r12 != 0) goto L_0x02df
            r0.dispatchAndUpdateViewHolders(r3)
            goto L_0x02e6
        L_0x02df:
            r0.postponeAndUpdateViewHolders(r3)
            goto L_0x02e6
        L_0x02e3:
            r0.postponeAndUpdateViewHolders(r3)
        L_0x02e6:
            int r2 = r2 + 1
            r7 = 8
            r8 = -1
            goto L_0x01e6
        L_0x02ed:
            java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r0 = r0.mPendingUpdates
            r0.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.AdapterHelper.preProcess():void");
    }

    public final int updatePositionWithPostponed(int i, int i2) {
        int i3;
        int i4;
        for (int size = this.mPostponedList.size() - 1; size >= 0; size--) {
            UpdateOp updateOp = this.mPostponedList.get(size);
            int i5 = updateOp.cmd;
            if (i5 == 8) {
                int i6 = updateOp.positionStart;
                int i7 = updateOp.itemCount;
                if (i6 < i7) {
                    i4 = i6;
                    i3 = i7;
                } else {
                    i3 = i6;
                    i4 = i7;
                }
                if (i < i4 || i > i3) {
                    if (i < i6) {
                        if (i2 == 1) {
                            updateOp.positionStart = i6 + 1;
                            updateOp.itemCount = i7 + 1;
                        } else if (i2 == 2) {
                            updateOp.positionStart = i6 - 1;
                            updateOp.itemCount = i7 - 1;
                        }
                    }
                } else if (i4 == i6) {
                    if (i2 == 1) {
                        updateOp.itemCount = i7 + 1;
                    } else if (i2 == 2) {
                        updateOp.itemCount = i7 - 1;
                    }
                    i++;
                } else {
                    if (i2 == 1) {
                        updateOp.positionStart = i6 + 1;
                    } else if (i2 == 2) {
                        updateOp.positionStart = i6 - 1;
                    }
                    i--;
                }
            } else {
                int i8 = updateOp.positionStart;
                if (i8 <= i) {
                    if (i5 == 1) {
                        i -= updateOp.itemCount;
                    } else if (i5 == 2) {
                        i += updateOp.itemCount;
                    }
                } else if (i2 == 1) {
                    updateOp.positionStart = i8 + 1;
                } else if (i2 == 2) {
                    updateOp.positionStart = i8 - 1;
                }
            }
        }
        for (int size2 = this.mPostponedList.size() - 1; size2 >= 0; size2--) {
            UpdateOp updateOp2 = this.mPostponedList.get(size2);
            if (updateOp2.cmd == 8) {
                int i9 = updateOp2.itemCount;
                if (i9 == updateOp2.positionStart || i9 < 0) {
                    this.mPostponedList.remove(size2);
                    updateOp2.payload = null;
                    this.mUpdateOpPool.release(updateOp2);
                }
            } else if (updateOp2.itemCount <= 0) {
                this.mPostponedList.remove(size2);
                updateOp2.payload = null;
                this.mUpdateOpPool.release(updateOp2);
            }
        }
        return i;
    }

    public AdapterHelper(RecyclerView.C03376 r3) {
        this.mCallback = r3;
        this.mOpReorderer = new OpReorderer(this);
    }

    public final void consumeUpdatesInOnePass() {
        consumePostponedUpdates();
        int size = this.mPendingUpdates.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = this.mPendingUpdates.get(i);
            int i2 = updateOp.cmd;
            if (i2 == 1) {
                RecyclerView.C03376 r4 = (RecyclerView.C03376) this.mCallback;
                Objects.requireNonNull(r4);
                r4.dispatchUpdate(updateOp);
                ((RecyclerView.C03376) this.mCallback).offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
            } else if (i2 == 2) {
                RecyclerView.C03376 r42 = (RecyclerView.C03376) this.mCallback;
                Objects.requireNonNull(r42);
                r42.dispatchUpdate(updateOp);
                Callback callback = this.mCallback;
                int i3 = updateOp.positionStart;
                int i4 = updateOp.itemCount;
                RecyclerView.C03376 r43 = (RecyclerView.C03376) callback;
                Objects.requireNonNull(r43);
                RecyclerView.this.offsetPositionRecordsForRemove(i3, i4, true);
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.mItemsAddedOrRemoved = true;
                recyclerView.mState.mDeletedInvisibleItemCountSincePreviousLayout += i4;
            } else if (i2 == 4) {
                RecyclerView.C03376 r44 = (RecyclerView.C03376) this.mCallback;
                Objects.requireNonNull(r44);
                r44.dispatchUpdate(updateOp);
                ((RecyclerView.C03376) this.mCallback).markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
            } else if (i2 == 8) {
                RecyclerView.C03376 r45 = (RecyclerView.C03376) this.mCallback;
                Objects.requireNonNull(r45);
                r45.dispatchUpdate(updateOp);
                ((RecyclerView.C03376) this.mCallback).offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
            }
        }
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.mExistingUpdateTypes = 0;
    }

    public final void recycleUpdateOpsAndClearList(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = (UpdateOp) arrayList.get(i);
            updateOp.payload = null;
            this.mUpdateOpPool.release(updateOp);
        }
        arrayList.clear();
    }
}
