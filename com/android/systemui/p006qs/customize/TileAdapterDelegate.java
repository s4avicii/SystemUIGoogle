package com.android.systemui.p006qs.customize;

import androidx.core.view.AccessibilityDelegateCompat;

/* renamed from: com.android.systemui.qs.customize.TileAdapterDelegate */
public final class TileAdapterDelegate extends AccessibilityDelegateCompat {
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d5  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00d7  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fd  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onInitializeAccessibilityNodeInfo(android.view.View r8, androidx.core.view.accessibility.AccessibilityNodeInfoCompat r9) {
        /*
            r7 = this;
            super.onInitializeAccessibilityNodeInfo(r8, r9)
            java.lang.Object r7 = r8.getTag()
            com.android.systemui.qs.customize.TileAdapter$Holder r7 = (com.android.systemui.p006qs.customize.TileAdapter.Holder) r7
            r0 = 0
            r9.setCollectionItemInfo(r0)
            android.view.accessibility.AccessibilityNodeInfo r0 = r9.mInfo
            java.lang.String r1 = ""
            r0.setStateDescription(r1)
            if (r7 == 0) goto L_0x011e
            com.android.systemui.qs.customize.TileAdapter r0 = com.android.systemui.p006qs.customize.TileAdapter.this
            int r1 = r0.mAccessibilityAction
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x0020
            r1 = r3
            goto L_0x0021
        L_0x0020:
            r1 = r2
        L_0x0021:
            if (r1 != 0) goto L_0x0025
            goto L_0x011e
        L_0x0025:
            int r1 = r7.getLayoutPosition()
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mEditIndex
            if (r1 <= r0) goto L_0x0032
            r0 = r3
            goto L_0x0033
        L_0x0032:
            r0 = r2
        L_0x0033:
            r1 = 16
            if (r0 == 0) goto L_0x0043
            android.content.Context r0 = r8.getContext()
            r4 = 2131951776(0x7f1300a0, float:1.9539976E38)
            java.lang.String r0 = r0.getString(r4)
            goto L_0x0074
        L_0x0043:
            com.android.systemui.qs.customize.TileAdapter r0 = com.android.systemui.p006qs.customize.TileAdapter.this
            int r4 = r7.getLayoutPosition()
            java.util.Objects.requireNonNull(r0)
            java.util.List<java.lang.String> r5 = r0.mCurrentSpecs
            int r5 = r5.size()
            int r6 = r0.mMinNumTiles
            if (r5 <= r6) goto L_0x0058
            r5 = r3
            goto L_0x0059
        L_0x0058:
            r5 = r2
        L_0x0059:
            if (r5 == 0) goto L_0x0066
            int r0 = r0.mEditIndex
            if (r4 >= r0) goto L_0x0061
            r0 = r3
            goto L_0x0062
        L_0x0061:
            r0 = r2
        L_0x0062:
            if (r0 == 0) goto L_0x0066
            r0 = r3
            goto L_0x0067
        L_0x0066:
            r0 = r2
        L_0x0067:
            if (r0 == 0) goto L_0x007d
            android.content.Context r0 = r8.getContext()
            r4 = 2131951775(0x7f13009f, float:1.9539974E38)
            java.lang.String r0 = r0.getString(r4)
        L_0x0074:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r4 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            r4.<init>((int) r1, (java.lang.String) r0)
            r9.addAction((androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r4)
            goto L_0x00a0
        L_0x007d:
            java.util.List r0 = r9.getActionList()
            int r4 = r0.size()
            r5 = r2
        L_0x0086:
            if (r5 >= r4) goto L_0x00a0
            java.lang.Object r6 = r0.get(r5)
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r6 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r6
            int r6 = r6.getId()
            if (r6 != r1) goto L_0x009d
            java.lang.Object r6 = r0.get(r5)
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r6 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r6
            r9.removeAction(r6)
        L_0x009d:
            int r5 = r5 + 1
            goto L_0x0086
        L_0x00a0:
            com.android.systemui.qs.customize.TileAdapter r0 = com.android.systemui.p006qs.customize.TileAdapter.this
            int r1 = r7.getLayoutPosition()
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mEditIndex
            if (r1 <= r0) goto L_0x00af
            r0 = r3
            goto L_0x00b0
        L_0x00af:
            r0 = r2
        L_0x00b0:
            if (r0 == 0) goto L_0x00c8
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r0 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            r1 = 2131427364(0x7f0b0024, float:1.8476342E38)
            android.content.Context r4 = r8.getContext()
            r5 = 2131951781(0x7f1300a5, float:1.9539986E38)
            java.lang.String r4 = r4.getString(r5)
            r0.<init>((int) r1, (java.lang.String) r4)
            r9.addAction((androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r0)
        L_0x00c8:
            com.android.systemui.qs.customize.TileAdapter r0 = com.android.systemui.p006qs.customize.TileAdapter.this
            int r1 = r7.getLayoutPosition()
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mEditIndex
            if (r1 >= r0) goto L_0x00d7
            r0 = r3
            goto L_0x00d8
        L_0x00d7:
            r0 = r2
        L_0x00d8:
            if (r0 == 0) goto L_0x00f0
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r0 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            r1 = 2131427365(0x7f0b0025, float:1.8476344E38)
            android.content.Context r4 = r8.getContext()
            r5 = 2131951782(0x7f1300a6, float:1.9539988E38)
            java.lang.String r4 = r4.getString(r5)
            r0.<init>((int) r1, (java.lang.String) r4)
            r9.addAction((androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r0)
        L_0x00f0:
            com.android.systemui.qs.customize.TileAdapter r0 = com.android.systemui.p006qs.customize.TileAdapter.this
            int r1 = r7.getLayoutPosition()
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mEditIndex
            if (r1 >= r0) goto L_0x00ff
            r0 = r3
            goto L_0x0100
        L_0x00ff:
            r0 = r2
        L_0x0100:
            if (r0 == 0) goto L_0x011e
            android.content.Context r8 = r8.getContext()
            r0 = 2131951774(0x7f13009e, float:1.9539972E38)
            java.lang.Object[] r1 = new java.lang.Object[r3]
            int r7 = r7.getLayoutPosition()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r1[r2] = r7
            java.lang.String r7 = r8.getString(r0, r1)
            android.view.accessibility.AccessibilityNodeInfo r8 = r9.mInfo
            r8.setStateDescription(r7)
        L_0x011e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.customize.TileAdapterDelegate.onInitializeAccessibilityNodeInfo(android.view.View, androidx.core.view.accessibility.AccessibilityNodeInfoCompat):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean performAccessibilityAction(android.view.View r6, int r7, android.os.Bundle r8) {
        /*
            r5 = this;
            java.lang.Object r0 = r6.getTag()
            com.android.systemui.qs.customize.TileAdapter$Holder r0 = (com.android.systemui.p006qs.customize.TileAdapter.Holder) r0
            if (r0 == 0) goto L_0x00fa
            com.android.systemui.qs.customize.TileAdapter r1 = com.android.systemui.p006qs.customize.TileAdapter.this
            int r2 = r1.mAccessibilityAction
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x0012
            r2 = r4
            goto L_0x0013
        L_0x0012:
            r2 = r3
        L_0x0013:
            if (r2 != 0) goto L_0x0017
            goto L_0x00fa
        L_0x0017:
            r2 = 16
            if (r7 != r2) goto L_0x00a4
            int r5 = r0.getLayoutPosition()
            java.util.Objects.requireNonNull(r1)
            int r6 = r1.mEditIndex
            if (r5 <= r6) goto L_0x0028
            r5 = r4
            goto L_0x0029
        L_0x0028:
            r5 = r3
        L_0x0029:
            if (r5 == 0) goto L_0x0055
            com.android.systemui.qs.customize.TileAdapter r5 = com.android.systemui.p006qs.customize.TileAdapter.this
            int r6 = r0.getLayoutPosition()
            java.util.Objects.requireNonNull(r5)
            int r7 = r5.mEditIndex
            if (r6 <= r7) goto L_0x003a
            r8 = r4
            goto L_0x003b
        L_0x003a:
            r8 = r3
        L_0x003b:
            if (r8 != 0) goto L_0x003e
            goto L_0x0042
        L_0x003e:
            r5.move(r6, r7, r4)
            r3 = r4
        L_0x0042:
            if (r3 == 0) goto L_0x00a3
            android.view.View r5 = r0.itemView
            android.content.Context r6 = r5.getContext()
            r7 = 2131951778(0x7f1300a2, float:1.953998E38)
            java.lang.CharSequence r6 = r6.getText(r7)
            r5.announceForAccessibility(r6)
            goto L_0x00a3
        L_0x0055:
            com.android.systemui.qs.customize.TileAdapter r5 = com.android.systemui.p006qs.customize.TileAdapter.this
            int r6 = r0.getLayoutPosition()
            java.util.Objects.requireNonNull(r5)
            java.util.List<java.lang.String> r7 = r5.mCurrentSpecs
            int r7 = r7.size()
            int r8 = r5.mMinNumTiles
            if (r7 <= r8) goto L_0x006a
            r7 = r4
            goto L_0x006b
        L_0x006a:
            r7 = r3
        L_0x006b:
            if (r7 == 0) goto L_0x0078
            int r7 = r5.mEditIndex
            if (r6 >= r7) goto L_0x0073
            r7 = r4
            goto L_0x0074
        L_0x0073:
            r7 = r3
        L_0x0074:
            if (r7 == 0) goto L_0x0078
            r7 = r4
            goto L_0x0079
        L_0x0078:
            r7 = r3
        L_0x0079:
            if (r7 != 0) goto L_0x007c
            goto L_0x0091
        L_0x007c:
            java.util.ArrayList r7 = r5.mTiles
            java.lang.Object r7 = r7.get(r6)
            com.android.systemui.qs.customize.TileQueryHelper$TileInfo r7 = (com.android.systemui.p006qs.customize.TileQueryHelper.TileInfo) r7
            boolean r7 = r7.isSystem
            if (r7 == 0) goto L_0x008b
            int r7 = r5.mEditIndex
            goto L_0x008d
        L_0x008b:
            int r7 = r5.mTileDividerIndex
        L_0x008d:
            r5.move(r6, r7, r4)
            r3 = r4
        L_0x0091:
            if (r3 == 0) goto L_0x00a3
            android.view.View r5 = r0.itemView
            android.content.Context r6 = r5.getContext()
            r7 = 2131951780(0x7f1300a4, float:1.9539984E38)
            java.lang.CharSequence r6 = r6.getText(r7)
            r5.announceForAccessibility(r6)
        L_0x00a3:
            return r4
        L_0x00a4:
            r2 = 2131427365(0x7f0b0025, float:1.8476344E38)
            if (r7 != r2) goto L_0x00bd
            int r5 = r0.getLayoutPosition()
            java.util.Objects.requireNonNull(r1)
            r1.mAccessibilityFromIndex = r5
            r6 = 2
            r1.mAccessibilityAction = r6
            r1.mFocusIndex = r5
            r1.mNeedsFocus = r4
            r1.notifyDataSetChanged()
            return r4
        L_0x00bd:
            r2 = 2131427364(0x7f0b0024, float:1.8476342E38)
            if (r7 != r2) goto L_0x00f5
            int r5 = r0.getLayoutPosition()
            java.util.Objects.requireNonNull(r1)
            r1.mAccessibilityFromIndex = r5
            r1.mAccessibilityAction = r4
            java.util.ArrayList r5 = r1.mTiles
            int r6 = r1.mEditIndex
            int r7 = r6 + 1
            r1.mEditIndex = r7
            r7 = 0
            r5.add(r6, r7)
            int r5 = r1.mTileDividerIndex
            int r5 = r5 + r4
            r1.mTileDividerIndex = r5
            int r5 = r1.mEditIndex
            int r5 = r5 - r4
            r1.mFocusIndex = r5
            r1.mNeedsFocus = r4
            androidx.recyclerview.widget.RecyclerView r6 = r1.mRecyclerView
            if (r6 == 0) goto L_0x00f1
            com.android.systemui.qs.customize.TileAdapter$$ExternalSyntheticLambda0 r7 = new com.android.systemui.qs.customize.TileAdapter$$ExternalSyntheticLambda0
            r7.<init>(r1, r5)
            r6.post(r7)
        L_0x00f1:
            r1.notifyDataSetChanged()
            return r4
        L_0x00f5:
            boolean r5 = super.performAccessibilityAction(r6, r7, r8)
            return r5
        L_0x00fa:
            boolean r5 = super.performAccessibilityAction(r6, r7, r8)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.customize.TileAdapterDelegate.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
    }
}
