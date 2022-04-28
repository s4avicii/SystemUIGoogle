package com.android.systemui.p006qs;

/* renamed from: com.android.systemui.qs.FgsManagerController$updateAppItemsLocked$3 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$updateAppItemsLocked$3 implements Runnable {
    public final /* synthetic */ FgsManagerController this$0;

    public FgsManagerController$updateAppItemsLocked$3(FgsManagerController fgsManagerController) {
        this.this$0 = fgsManagerController;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00bf, code lost:
        if (r7[(r15 + 1) + r8] > r7[(r15 - 1) + r8]) goto L_0x00d0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0284  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00fb A[LOOP:3: B:29:0x00ed->B:35:0x00fb, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01f1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r22 = this;
            r0 = r22
            com.android.systemui.qs.FgsManagerController r0 = r0.this$0
            com.android.systemui.qs.FgsManagerController$AppListAdapter r1 = r0.appListAdapter
            android.util.ArrayMap<com.android.systemui.qs.FgsManagerController$UserPackage, com.android.systemui.qs.FgsManagerController$RunningApp> r0 = r0.runningApps
            java.util.Collection r0 = r0.values()
            java.util.List r0 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r0)
            com.android.systemui.qs.FgsManagerController$updateAppItemsLocked$3$run$$inlined$sortedByDescending$1 r2 = new com.android.systemui.qs.FgsManagerController$updateAppItemsLocked$3$run$$inlined$sortedByDescending$1
            r2.<init>()
            java.util.List r0 = kotlin.collections.CollectionsKt___CollectionsKt.sortedWith(r0, r2)
            java.util.Objects.requireNonNull(r1)
            kotlin.jvm.internal.Ref$ObjectRef r2 = new kotlin.jvm.internal.Ref$ObjectRef
            r2.<init>()
            java.util.List<com.android.systemui.qs.FgsManagerController$RunningApp> r3 = r1.data
            r2.element = r3
            r1.data = r0
            com.android.systemui.qs.FgsManagerController$AppListAdapter$setData$1 r3 = new com.android.systemui.qs.FgsManagerController$AppListAdapter$setData$1
            r3.<init>(r2, r0)
            int r0 = r3.getOldListSize()
            int r2 = r3.getNewListSize()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            androidx.recyclerview.widget.DiffUtil$Range r6 = new androidx.recyclerview.widget.DiffUtil$Range
            r6.<init>(r0, r2)
            r5.add(r6)
            int r0 = r0 + r2
            r2 = 1
            int r0 = r0 + r2
            r6 = 2
            int r0 = r0 / r6
            int r0 = r0 * r6
            int r0 = r0 + r2
            int[] r7 = new int[r0]
            int r8 = r0 / 2
            int[] r0 = new int[r0]
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
        L_0x0058:
            boolean r10 = r5.isEmpty()
            if (r10 != 0) goto L_0x0294
            int r10 = r5.size()
            int r10 = r10 - r2
            java.lang.Object r10 = r5.remove(r10)
            androidx.recyclerview.widget.DiffUtil$Range r10 = (androidx.recyclerview.widget.DiffUtil.Range) r10
            java.util.Objects.requireNonNull(r10)
            int r13 = r10.oldListEnd
            int r14 = r10.oldListStart
            int r15 = r13 - r14
            if (r15 < r2) goto L_0x01e6
            int r11 = r10.newListEnd
            int r12 = r10.newListStart
            int r11 = r11 - r12
            if (r11 >= r2) goto L_0x007d
            goto L_0x01e6
        L_0x007d:
            int r11 = r11 + r15
            int r11 = r11 + r2
            int r11 = r11 / r6
            int r12 = r2 + r8
            r7[r12] = r14
            r0[r12] = r13
            r12 = 0
        L_0x0087:
            if (r12 >= r11) goto L_0x01e6
            int r13 = r10.oldListEnd
            int r14 = r10.oldListStart
            int r13 = r13 - r14
            int r14 = r10.newListEnd
            int r15 = r10.newListStart
            int r14 = r14 - r15
            int r13 = r13 - r14
            int r13 = java.lang.Math.abs(r13)
            int r13 = r13 % r6
            if (r13 != r2) goto L_0x009d
            r13 = r2
            goto L_0x009e
        L_0x009d:
            r13 = 0
        L_0x009e:
            int r14 = r10.oldListEnd
            int r15 = r10.oldListStart
            int r14 = r14 - r15
            int r15 = r10.newListEnd
            int r6 = r10.newListStart
            int r15 = r15 - r6
            int r14 = r14 - r15
            int r6 = -r12
            r15 = r6
        L_0x00ab:
            if (r15 > r12) goto L_0x0139
            if (r15 == r6) goto L_0x00ce
            if (r15 == r12) goto L_0x00c2
            int r16 = r15 + 1
            int r16 = r16 + r8
            r2 = r7[r16]
            int r16 = r15 + -1
            int r16 = r16 + r8
            r17 = r11
            r11 = r7[r16]
            if (r2 <= r11) goto L_0x00c4
            goto L_0x00d0
        L_0x00c2:
            r17 = r11
        L_0x00c4:
            int r2 = r15 + -1
            int r2 = r2 + r8
            r2 = r7[r2]
            int r11 = r2 + 1
            r16 = r1
            goto L_0x00d8
        L_0x00ce:
            r17 = r11
        L_0x00d0:
            int r2 = r15 + 1
            int r2 = r2 + r8
            r2 = r7[r2]
            r16 = r1
            r11 = r2
        L_0x00d8:
            int r1 = r10.newListStart
            r18 = r5
            int r5 = r10.oldListStart
            int r5 = r11 - r5
            int r5 = r5 + r1
            int r5 = r5 - r15
            if (r12 == 0) goto L_0x00ea
            if (r11 == r2) goto L_0x00e7
            goto L_0x00ea
        L_0x00e7:
            int r1 = r5 + -1
            goto L_0x00eb
        L_0x00ea:
            r1 = r5
        L_0x00eb:
            r19 = r9
        L_0x00ed:
            int r9 = r10.oldListEnd
            if (r11 >= r9) goto L_0x0100
            int r9 = r10.newListEnd
            if (r5 >= r9) goto L_0x0100
            boolean r9 = r3.areItemsTheSame(r11, r5)
            if (r9 == 0) goto L_0x0100
            int r11 = r11 + 1
            int r5 = r5 + 1
            goto L_0x00ed
        L_0x0100:
            int r9 = r15 + r8
            r7[r9] = r11
            if (r13 == 0) goto L_0x0128
            int r9 = r14 - r15
            r20 = r13
            int r13 = r6 + 1
            if (r9 < r13) goto L_0x012a
            int r13 = r12 + -1
            if (r9 > r13) goto L_0x012a
            int r9 = r9 + r8
            r9 = r0[r9]
            if (r9 > r11) goto L_0x012a
            androidx.recyclerview.widget.DiffUtil$Snake r9 = new androidx.recyclerview.widget.DiffUtil$Snake
            r9.<init>()
            r9.startX = r2
            r9.startY = r1
            r9.endX = r11
            r9.endY = r5
            r1 = 0
            r9.reverse = r1
            goto L_0x0142
        L_0x0128:
            r20 = r13
        L_0x012a:
            int r15 = r15 + 2
            r1 = r16
            r11 = r17
            r5 = r18
            r9 = r19
            r13 = r20
            r2 = 1
            goto L_0x00ab
        L_0x0139:
            r16 = r1
            r18 = r5
            r19 = r9
            r17 = r11
            r9 = 0
        L_0x0142:
            if (r9 == 0) goto L_0x0149
            r11 = r9
            r20 = r10
            goto L_0x01ef
        L_0x0149:
            int r1 = r10.oldListEnd
            int r2 = r10.oldListStart
            int r1 = r1 - r2
            int r2 = r10.newListEnd
            int r5 = r10.newListStart
            int r2 = r2 - r5
            int r1 = r1 - r2
            int r2 = r1 % 2
            if (r2 != 0) goto L_0x015a
            r2 = 1
            goto L_0x015b
        L_0x015a:
            r2 = 0
        L_0x015b:
            r5 = r6
        L_0x015c:
            if (r5 > r12) goto L_0x01cf
            if (r5 == r6) goto L_0x0177
            if (r5 == r12) goto L_0x016f
            int r9 = r5 + 1
            int r9 = r9 + r8
            r9 = r0[r9]
            int r11 = r5 + -1
            int r11 = r11 + r8
            r11 = r0[r11]
            if (r9 >= r11) goto L_0x016f
            goto L_0x0177
        L_0x016f:
            int r9 = r5 + -1
            int r9 = r9 + r8
            r9 = r0[r9]
            int r11 = r9 + -1
            goto L_0x017d
        L_0x0177:
            int r9 = r5 + 1
            int r9 = r9 + r8
            r9 = r0[r9]
            r11 = r9
        L_0x017d:
            int r13 = r10.newListEnd
            int r14 = r10.oldListEnd
            int r14 = r14 - r11
            int r14 = r14 - r5
            int r13 = r13 - r14
            if (r12 == 0) goto L_0x018c
            if (r11 == r9) goto L_0x0189
            goto L_0x018c
        L_0x0189:
            int r14 = r13 + 1
            goto L_0x018d
        L_0x018c:
            r14 = r13
        L_0x018d:
            int r15 = r10.oldListStart
            if (r11 <= r15) goto L_0x01a6
            int r15 = r10.newListStart
            if (r13 <= r15) goto L_0x01a6
            int r15 = r11 + -1
            r20 = r10
            int r10 = r13 + -1
            boolean r21 = r3.areItemsTheSame(r15, r10)
            if (r21 == 0) goto L_0x01a8
            r13 = r10
            r11 = r15
            r10 = r20
            goto L_0x018d
        L_0x01a6:
            r20 = r10
        L_0x01a8:
            int r10 = r5 + r8
            r0[r10] = r11
            if (r2 == 0) goto L_0x01ca
            int r10 = r1 - r5
            if (r10 < r6) goto L_0x01ca
            if (r10 > r12) goto L_0x01ca
            int r10 = r10 + r8
            r10 = r7[r10]
            if (r10 < r11) goto L_0x01ca
            androidx.recyclerview.widget.DiffUtil$Snake r1 = new androidx.recyclerview.widget.DiffUtil$Snake
            r1.<init>()
            r1.startX = r11
            r1.startY = r13
            r1.endX = r9
            r1.endY = r14
            r2 = 1
            r1.reverse = r2
            goto L_0x01d2
        L_0x01ca:
            int r5 = r5 + 2
            r10 = r20
            goto L_0x015c
        L_0x01cf:
            r20 = r10
            r1 = 0
        L_0x01d2:
            if (r1 == 0) goto L_0x01d6
            r11 = r1
            goto L_0x01ef
        L_0x01d6:
            int r12 = r12 + 1
            r1 = r16
            r11 = r17
            r5 = r18
            r9 = r19
            r10 = r20
            r2 = 1
            r6 = 2
            goto L_0x0087
        L_0x01e6:
            r16 = r1
            r18 = r5
            r19 = r9
            r20 = r10
            r11 = 0
        L_0x01ef:
            if (r11 == 0) goto L_0x0284
            int r1 = r11.diagonalSize()
            if (r1 <= 0) goto L_0x023d
            int r1 = r11.endY
            int r2 = r11.startY
            int r1 = r1 - r2
            int r5 = r11.endX
            int r6 = r11.startX
            int r5 = r5 - r6
            if (r1 == r5) goto L_0x0205
            r9 = 1
            goto L_0x0206
        L_0x0205:
            r9 = 0
        L_0x0206:
            if (r9 == 0) goto L_0x0235
            boolean r9 = r11.reverse
            if (r9 == 0) goto L_0x0216
            androidx.recyclerview.widget.DiffUtil$Diagonal r1 = new androidx.recyclerview.widget.DiffUtil$Diagonal
            int r5 = r11.diagonalSize()
            r1.<init>(r6, r2, r5)
            goto L_0x023a
        L_0x0216:
            if (r1 <= r5) goto L_0x021a
            r12 = 1
            goto L_0x021b
        L_0x021a:
            r12 = 0
        L_0x021b:
            if (r12 == 0) goto L_0x0229
            androidx.recyclerview.widget.DiffUtil$Diagonal r1 = new androidx.recyclerview.widget.DiffUtil$Diagonal
            int r2 = r2 + 1
            int r5 = r11.diagonalSize()
            r1.<init>(r6, r2, r5)
            goto L_0x023a
        L_0x0229:
            androidx.recyclerview.widget.DiffUtil$Diagonal r1 = new androidx.recyclerview.widget.DiffUtil$Diagonal
            int r6 = r6 + 1
            int r5 = r11.diagonalSize()
            r1.<init>(r6, r2, r5)
            goto L_0x023a
        L_0x0235:
            androidx.recyclerview.widget.DiffUtil$Diagonal r1 = new androidx.recyclerview.widget.DiffUtil$Diagonal
            r1.<init>(r6, r2, r5)
        L_0x023a:
            r4.add(r1)
        L_0x023d:
            boolean r1 = r19.isEmpty()
            if (r1 == 0) goto L_0x024b
            androidx.recyclerview.widget.DiffUtil$Range r1 = new androidx.recyclerview.widget.DiffUtil$Range
            r1.<init>()
            r2 = r19
            goto L_0x0259
        L_0x024b:
            int r1 = r19.size()
            r2 = 1
            int r1 = r1 - r2
            r2 = r19
            java.lang.Object r1 = r2.remove(r1)
            androidx.recyclerview.widget.DiffUtil$Range r1 = (androidx.recyclerview.widget.DiffUtil.Range) r1
        L_0x0259:
            r10 = r20
            int r5 = r10.oldListStart
            r1.oldListStart = r5
            int r5 = r10.newListStart
            r1.newListStart = r5
            int r5 = r11.startX
            r1.oldListEnd = r5
            int r5 = r11.startY
            r1.newListEnd = r5
            r5 = r18
            r5.add(r1)
            int r1 = r10.oldListEnd
            r10.oldListEnd = r1
            int r1 = r10.newListEnd
            r10.newListEnd = r1
            int r1 = r11.endX
            r10.oldListStart = r1
            int r1 = r11.endY
            r10.newListStart = r1
            r5.add(r10)
            goto L_0x028d
        L_0x0284:
            r5 = r18
            r2 = r19
            r10 = r20
            r2.add(r10)
        L_0x028d:
            r9 = r2
            r1 = r16
            r2 = 1
            r6 = 2
            goto L_0x0058
        L_0x0294:
            r16 = r1
            androidx.recyclerview.widget.DiffUtil$1 r1 = androidx.recyclerview.widget.DiffUtil.DIAGONAL_COMPARATOR
            java.util.Collections.sort(r4, r1)
            androidx.recyclerview.widget.DiffUtil$DiffResult r1 = new androidx.recyclerview.widget.DiffUtil$DiffResult
            r1.<init>(r3, r4, r7, r0)
            androidx.recyclerview.widget.AdapterListUpdateCallback r0 = new androidx.recyclerview.widget.AdapterListUpdateCallback
            r2 = r16
            r0.<init>(r2)
            boolean r2 = r0 instanceof androidx.recyclerview.widget.BatchingListUpdateCallback
            if (r2 == 0) goto L_0x02ae
            androidx.recyclerview.widget.BatchingListUpdateCallback r0 = (androidx.recyclerview.widget.BatchingListUpdateCallback) r0
            goto L_0x02b4
        L_0x02ae:
            androidx.recyclerview.widget.BatchingListUpdateCallback r2 = new androidx.recyclerview.widget.BatchingListUpdateCallback
            r2.<init>(r0)
            r0 = r2
        L_0x02b4:
            int r2 = r1.mOldListSize
            java.util.ArrayDeque r3 = new java.util.ArrayDeque
            r3.<init>()
            int r5 = r1.mOldListSize
            int r6 = r1.mNewListSize
            int r4 = r4.size()
            r7 = 1
            int r4 = r4 - r7
        L_0x02c5:
            if (r4 < 0) goto L_0x0389
            java.util.List<androidx.recyclerview.widget.DiffUtil$Diagonal> r7 = r1.mDiagonals
            java.lang.Object r7 = r7.get(r4)
            androidx.recyclerview.widget.DiffUtil$Diagonal r7 = (androidx.recyclerview.widget.DiffUtil.Diagonal) r7
            java.util.Objects.requireNonNull(r7)
            int r8 = r7.f24x
            int r9 = r7.size
            int r8 = r8 + r9
            int r10 = r7.f25y
            int r10 = r10 + r9
        L_0x02da:
            if (r5 <= r8) goto L_0x031a
            int r5 = r5 + -1
            int[] r9 = r1.mOldItemStatuses
            r9 = r9[r5]
            r11 = r9 & 12
            if (r11 == 0) goto L_0x0313
            int r11 = r9 >> 4
            r12 = 0
            androidx.recyclerview.widget.DiffUtil$PostponedUpdate r11 = androidx.recyclerview.widget.DiffUtil.DiffResult.getPostponedUpdate(r3, r11, r12)
            if (r11 == 0) goto L_0x0306
            int r11 = r11.currentPos
            int r11 = r2 - r11
            r12 = 1
            int r11 = r11 - r12
            r0.onMoved(r5, r11)
            r9 = r9 & 4
            if (r9 == 0) goto L_0x02da
            androidx.recyclerview.widget.DiffUtil$Callback r9 = r1.mCallback
            java.util.Objects.requireNonNull(r9)
            r9 = 0
            r0.onChanged(r11, r12, r9)
            goto L_0x02da
        L_0x0306:
            r12 = 1
            androidx.recyclerview.widget.DiffUtil$PostponedUpdate r9 = new androidx.recyclerview.widget.DiffUtil$PostponedUpdate
            int r11 = r2 - r5
            int r11 = r11 - r12
            r9.<init>(r5, r11, r12)
            r3.add(r9)
            goto L_0x02da
        L_0x0313:
            r12 = 1
            r0.onRemoved(r5, r12)
            int r2 = r2 + -1
            goto L_0x02da
        L_0x031a:
            if (r6 <= r10) goto L_0x035a
            int r6 = r6 + -1
            int[] r8 = r1.mNewItemStatuses
            r8 = r8[r6]
            r9 = r8 & 12
            if (r9 == 0) goto L_0x0352
            int r9 = r8 >> 4
            r11 = 1
            androidx.recyclerview.widget.DiffUtil$PostponedUpdate r9 = androidx.recyclerview.widget.DiffUtil.DiffResult.getPostponedUpdate(r3, r9, r11)
            if (r9 != 0) goto L_0x033b
            androidx.recyclerview.widget.DiffUtil$PostponedUpdate r8 = new androidx.recyclerview.widget.DiffUtil$PostponedUpdate
            int r9 = r2 - r5
            r12 = 0
            r8.<init>(r6, r9, r12)
            r3.add(r8)
            goto L_0x031a
        L_0x033b:
            r12 = 0
            int r9 = r9.currentPos
            int r9 = r2 - r9
            int r9 = r9 - r11
            r0.onMoved(r9, r5)
            r8 = r8 & 4
            if (r8 == 0) goto L_0x031a
            androidx.recyclerview.widget.DiffUtil$Callback r8 = r1.mCallback
            java.util.Objects.requireNonNull(r8)
            r8 = 0
            r0.onChanged(r5, r11, r8)
            goto L_0x031a
        L_0x0352:
            r11 = 1
            r12 = 0
            r0.onInserted(r5, r11)
            int r2 = r2 + 1
            goto L_0x031a
        L_0x035a:
            r12 = 0
            int r5 = r7.f24x
            r6 = r5
            r5 = r12
        L_0x035f:
            int r8 = r7.size
            if (r5 >= r8) goto L_0x037e
            int[] r8 = r1.mOldItemStatuses
            r8 = r8[r6]
            r8 = r8 & 15
            r9 = 2
            if (r8 != r9) goto L_0x0377
            androidx.recyclerview.widget.DiffUtil$Callback r8 = r1.mCallback
            java.util.Objects.requireNonNull(r8)
            r8 = 0
            r10 = 1
            r0.onChanged(r6, r10, r8)
            goto L_0x0379
        L_0x0377:
            r8 = 0
            r10 = 1
        L_0x0379:
            int r6 = r6 + 1
            int r5 = r5 + 1
            goto L_0x035f
        L_0x037e:
            r8 = 0
            r9 = 2
            r10 = 1
            int r5 = r7.f24x
            int r6 = r7.f25y
            int r4 = r4 + -1
            goto L_0x02c5
        L_0x0389:
            r0.dispatchLastEvent()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.FgsManagerController$updateAppItemsLocked$3.run():void");
    }
}
