package androidx.slice;

import android.content.Context;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import java.util.ArrayList;

public final class SliceMetadata {
    public Context mContext;
    public long mExpiry;
    public RowContent mHeaderContent;
    public long mLastUpdated;
    public ListContent mListContent;
    public SliceActionImpl mPrimaryAction;
    public Slice mSlice;
    public ArrayList mSliceActions;

    public final int getLoadingState() {
        boolean z;
        if (SliceQuery.find(this.mSlice, (String) null, "partial") != null) {
            z = true;
        } else {
            z = false;
        }
        if (!this.mListContent.isValid()) {
            return 0;
        }
        if (z) {
            return 1;
        }
        return 2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SliceMetadata(android.content.Context r5, androidx.slice.Slice r6) {
        /*
            r4 = this;
            r4.<init>()
            r4.mSlice = r6
            r4.mContext = r5
            java.lang.String r5 = "long"
            java.lang.String r0 = "ttl"
            androidx.slice.SliceItem r0 = androidx.slice.core.SliceQuery.find((androidx.slice.Slice) r6, (java.lang.String) r5, (java.lang.String) r0)
            if (r0 == 0) goto L_0x0018
            long r0 = r0.getLong()
            r4.mExpiry = r0
        L_0x0018:
            java.lang.String r0 = "last_updated"
            androidx.slice.SliceItem r5 = androidx.slice.core.SliceQuery.find((androidx.slice.Slice) r6, (java.lang.String) r5, (java.lang.String) r0)
            if (r5 == 0) goto L_0x0026
            long r0 = r5.getLong()
            r4.mLastUpdated = r0
        L_0x0026:
            java.lang.String r5 = "bundle"
            java.lang.String r0 = "host_extras"
            androidx.slice.SliceItem r5 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.Slice) r6, (java.lang.String) r5, (java.lang.String) r0)
            if (r5 == 0) goto L_0x0039
            java.lang.Object r5 = r5.mObj
            boolean r0 = r5 instanceof android.os.Bundle
            if (r0 == 0) goto L_0x0039
            android.os.Bundle r5 = (android.os.Bundle) r5
            goto L_0x003b
        L_0x0039:
            android.os.Bundle r5 = android.os.Bundle.EMPTY
        L_0x003b:
            androidx.slice.widget.ListContent r5 = new androidx.slice.widget.ListContent
            r5.<init>(r6)
            r4.mListContent = r5
            androidx.slice.widget.RowContent r6 = r5.mHeaderContent
            r4.mHeaderContent = r6
            java.util.Objects.requireNonNull(r5)
            androidx.slice.widget.RowContent r6 = r5.mHeaderContent
            java.util.ArrayList r5 = r5.mSliceActions
            r0 = 1
            androidx.slice.widget.ListContent.getRowType(r6, r0, r5)
            androidx.slice.widget.ListContent r5 = r4.mListContent
            android.content.Context r6 = r4.mContext
            androidx.slice.core.SliceAction r5 = r5.getShortcut(r6)
            androidx.slice.core.SliceActionImpl r5 = (androidx.slice.core.SliceActionImpl) r5
            r4.mPrimaryAction = r5
            androidx.slice.widget.ListContent r5 = r4.mListContent
            java.util.Objects.requireNonNull(r5)
            java.util.ArrayList r5 = r5.mSliceActions
            r4.mSliceActions = r5
            if (r5 != 0) goto L_0x00b5
            androidx.slice.widget.RowContent r5 = r4.mHeaderContent
            if (r5 == 0) goto L_0x00b5
            androidx.slice.SliceItem r5 = r5.mSliceItem
            java.lang.String r6 = "list_item"
            java.lang.String[] r6 = new java.lang.String[]{r6}
            boolean r5 = androidx.slice.core.SliceQuery.hasHints(r5, r6)
            if (r5 == 0) goto L_0x00b5
            androidx.slice.widget.RowContent r5 = r4.mHeaderContent
            java.util.Objects.requireNonNull(r5)
            java.util.ArrayList<androidx.slice.SliceItem> r5 = r5.mEndItems
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r0 = 0
        L_0x0087:
            int r1 = r5.size()
            if (r0 >= r1) goto L_0x00ad
            java.lang.Object r1 = r5.get(r0)
            androidx.slice.SliceItem r1 = (androidx.slice.SliceItem) r1
            java.lang.String r2 = "action"
            r3 = 0
            androidx.slice.SliceItem r1 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r1, (java.lang.String) r2, (java.lang.String[]) r3, (java.lang.String[]) r3)
            if (r1 == 0) goto L_0x00aa
            androidx.slice.core.SliceActionImpl r1 = new androidx.slice.core.SliceActionImpl
            java.lang.Object r2 = r5.get(r0)
            androidx.slice.SliceItem r2 = (androidx.slice.SliceItem) r2
            r1.<init>(r2)
            r6.add(r1)
        L_0x00aa:
            int r0 = r0 + 1
            goto L_0x0087
        L_0x00ad:
            int r5 = r6.size()
            if (r5 <= 0) goto L_0x00b5
            r4.mSliceActions = r6
        L_0x00b5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.SliceMetadata.<init>(android.content.Context, androidx.slice.Slice):void");
    }

    public final boolean isExpired() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.mExpiry;
        if (j == 0 || j == -1 || currentTimeMillis <= j) {
            return false;
        }
        return true;
    }
}
