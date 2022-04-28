package androidx.slice.widget;

import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okio.Okio;

public final class RowContent extends SliceContent {
    public final ArrayList<SliceItem> mEndItems = new ArrayList<>();
    public boolean mIsHeader;
    public int mLineCount = 0;
    public SliceItem mPrimaryAction;
    public SliceItem mRange;
    public SliceItem mSelection;
    public boolean mShowActionDivider;
    public boolean mShowBottomDivider;
    public boolean mShowTitleItems;
    public SliceItem mStartItem;
    public SliceItem mSubtitleItem;
    public SliceItem mSummaryItem;
    public SliceItem mTitleItem;
    public final ArrayList<SliceAction> mToggleItems = new ArrayList<>();

    public static boolean isValidRow(SliceItem sliceItem) {
        if (sliceItem == null) {
            return false;
        }
        if ("slice".equals(sliceItem.mFormat) || "action".equals(sliceItem.mFormat)) {
            List<SliceItem> items = sliceItem.getSlice().getItems();
            if (sliceItem.hasHint("see_more") && items.isEmpty()) {
                return true;
            }
            for (int i = 0; i < items.size(); i++) {
                if (isValidRowContent(sliceItem, items.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<SliceItem> filterInvalidItems(SliceItem sliceItem) {
        ArrayList<SliceItem> arrayList = new ArrayList<>();
        for (SliceItem next : sliceItem.getSlice().getItems()) {
            if (isValidRowContent(sliceItem, next)) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public static boolean isValidRowContent(SliceItem sliceItem, SliceItem sliceItem2) {
        if (sliceItem2.hasAnyHints("keywords", "ttl", "last_updated", "horizontal") || "content_description".equals(sliceItem2.mSubType) || "selection_option_key".equals(sliceItem2.mSubType) || "selection_option_value".equals(sliceItem2.mSubType)) {
            return false;
        }
        String str = sliceItem2.mFormat;
        if (!"image".equals(str) && !"text".equals(str) && !"long".equals(str) && !"action".equals(str) && !"input".equals(str) && !"slice".equals(str)) {
            if (!"int".equals(str)) {
                return false;
            }
            Objects.requireNonNull(sliceItem);
            if ("range".equals(sliceItem.mSubType)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public final SliceItem getInputRangeThumb() {
        SliceItem sliceItem = this.mRange;
        if (sliceItem == null) {
            return null;
        }
        List<SliceItem> items = sliceItem.getSlice().getItems();
        for (int i = 0; i < items.size(); i++) {
            SliceItem sliceItem2 = items.get(i);
            Objects.requireNonNull(sliceItem2);
            if ("image".equals(sliceItem2.mFormat)) {
                return items.get(i);
            }
        }
        return null;
    }

    public final boolean isDefaultSeeMore() {
        SliceItem sliceItem = this.mSliceItem;
        Objects.requireNonNull(sliceItem);
        if ("action".equals(sliceItem.mFormat)) {
            Slice slice = this.mSliceItem.getSlice();
            Objects.requireNonNull(slice);
            if (!Okio.contains(slice.mHints, "see_more") || !this.mSliceItem.getSlice().getItems().isEmpty()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final boolean isValid() {
        boolean z;
        if (this.mSliceItem != null) {
            z = true;
        } else {
            z = false;
        }
        if (!z || (this.mStartItem == null && this.mPrimaryAction == null && this.mTitleItem == null && this.mSubtitleItem == null && this.mEndItems.size() <= 0 && this.mRange == null && this.mSelection == null && !isDefaultSeeMore())) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x010f, code lost:
        if ("slice".equals(r8.mFormat) != false) goto L_0x0111;
     */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0145  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01a8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public RowContent(androidx.slice.SliceItem r11, int r12) {
        /*
            r10 = this;
            r10.<init>(r11, r12)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r10.mEndItems = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r10.mToggleItems = r0
            r0 = 0
            r10.mLineCount = r0
            r1 = 1
            if (r12 != 0) goto L_0x0019
            r12 = r1
            goto L_0x001a
        L_0x0019:
            r12 = r0
        L_0x001a:
            java.lang.String r2 = "end_of_section"
            boolean r2 = r11.hasHint(r2)
            if (r2 == 0) goto L_0x0024
            r10.mShowBottomDivider = r1
        L_0x0024:
            r10.mIsHeader = r12
            boolean r12 = isValidRow(r11)
            if (r12 != 0) goto L_0x0035
            java.lang.String r10 = "RowContent"
            java.lang.String r11 = "Provided SliceItem is invalid for RowContent"
            android.util.Log.w(r10, r11)
            goto L_0x02a6
        L_0x0035:
            java.lang.String r12 = "title"
            java.lang.String[] r2 = new java.lang.String[]{r12}
            r3 = 0
            java.lang.String[] r4 = new java.lang.String[]{r3}
            java.util.ArrayList r2 = androidx.slice.core.SliceQuery.findAll(r11, r3, r2, r4)
            int r4 = r2.size()
            java.lang.String r5 = "slice"
            java.lang.String r6 = "long"
            java.lang.String r7 = "action"
            if (r4 <= 0) goto L_0x008b
            java.lang.Object r4 = r2.get(r0)
            androidx.slice.SliceItem r4 = (androidx.slice.SliceItem) r4
            java.util.Objects.requireNonNull(r4)
            java.lang.String r4 = r4.mFormat
            boolean r8 = r7.equals(r4)
            java.lang.String r9 = "image"
            if (r8 == 0) goto L_0x0071
            java.lang.Object r8 = r2.get(r0)
            androidx.slice.SliceItem r8 = (androidx.slice.SliceItem) r8
            androidx.slice.SliceItem r8 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r8, (java.lang.String) r9, (java.lang.String[]) r3, (java.lang.String[]) r3)
            if (r8 != 0) goto L_0x0083
        L_0x0071:
            boolean r8 = r5.equals(r4)
            if (r8 != 0) goto L_0x0083
            boolean r8 = r6.equals(r4)
            if (r8 != 0) goto L_0x0083
            boolean r4 = r9.equals(r4)
            if (r4 == 0) goto L_0x008b
        L_0x0083:
            java.lang.Object r2 = r2.get(r0)
            androidx.slice.SliceItem r2 = (androidx.slice.SliceItem) r2
            r10.mStartItem = r2
        L_0x008b:
            java.lang.String r2 = "shortcut"
            java.lang.String[] r4 = new java.lang.String[]{r2, r12}
            java.util.ArrayList r8 = androidx.slice.core.SliceQuery.findAll(r11, r5, r4, r3)
            java.util.ArrayList r4 = androidx.slice.core.SliceQuery.findAll(r11, r7, r4, r3)
            r8.addAll(r4)
            boolean r4 = r8.isEmpty()
            if (r4 == 0) goto L_0x00bc
            java.lang.String r4 = r11.mFormat
            boolean r4 = r7.equals(r4)
            if (r4 == 0) goto L_0x00bc
            androidx.slice.Slice r4 = r11.getSlice()
            java.util.List r4 = r4.getItems()
            int r4 = r4.size()
            if (r4 != r1) goto L_0x00bc
            r10.mPrimaryAction = r11
            goto L_0x00e5
        L_0x00bc:
            androidx.slice.SliceItem r4 = r10.mStartItem
            if (r4 == 0) goto L_0x00d7
            int r4 = r8.size()
            if (r4 <= r1) goto L_0x00d7
            java.lang.Object r4 = r8.get(r0)
            androidx.slice.SliceItem r9 = r10.mStartItem
            if (r4 != r9) goto L_0x00d7
            java.lang.Object r4 = r8.get(r1)
            androidx.slice.SliceItem r4 = (androidx.slice.SliceItem) r4
            r10.mPrimaryAction = r4
            goto L_0x00e5
        L_0x00d7:
            int r4 = r8.size()
            if (r4 <= 0) goto L_0x00e5
            java.lang.Object r4 = r8.get(r0)
            androidx.slice.SliceItem r4 = (androidx.slice.SliceItem) r4
            r10.mPrimaryAction = r4
        L_0x00e5:
            java.util.ArrayList r4 = filterInvalidItems(r11)
            int r8 = r4.size()
            if (r8 != r1) goto L_0x0139
            java.lang.Object r8 = r4.get(r0)
            androidx.slice.SliceItem r8 = (androidx.slice.SliceItem) r8
            java.util.Objects.requireNonNull(r8)
            java.lang.String r8 = r8.mFormat
            boolean r8 = r7.equals(r8)
            if (r8 != 0) goto L_0x0111
            java.lang.Object r8 = r4.get(r0)
            androidx.slice.SliceItem r8 = (androidx.slice.SliceItem) r8
            java.util.Objects.requireNonNull(r8)
            java.lang.String r8 = r8.mFormat
            boolean r5 = r5.equals(r8)
            if (r5 == 0) goto L_0x0139
        L_0x0111:
            java.lang.Object r5 = r4.get(r0)
            androidx.slice.SliceItem r5 = (androidx.slice.SliceItem) r5
            java.lang.String[] r2 = new java.lang.String[]{r2, r12}
            boolean r2 = r5.hasAnyHints(r2)
            if (r2 != 0) goto L_0x0139
            java.lang.Object r2 = r4.get(r0)
            androidx.slice.SliceItem r2 = (androidx.slice.SliceItem) r2
            boolean r2 = isValidRow(r2)
            if (r2 == 0) goto L_0x0139
            java.lang.Object r11 = r4.get(r0)
            androidx.slice.SliceItem r11 = (androidx.slice.SliceItem) r11
            java.util.ArrayList r4 = filterInvalidItems(r11)
            r2 = r1
            goto L_0x013a
        L_0x0139:
            r2 = r0
        L_0x013a:
            java.lang.String r5 = r11.mSubType
            java.lang.String r8 = "range"
            boolean r5 = r8.equals(r5)
            if (r5 == 0) goto L_0x0195
            androidx.slice.SliceItem r5 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r11, (java.lang.String) r7, (java.lang.String) r8)
            if (r5 == 0) goto L_0x0193
            if (r2 == 0) goto L_0x014e
            goto L_0x0193
        L_0x014e:
            androidx.slice.SliceItem r2 = r10.mStartItem
            r4.remove(r2)
            int r2 = r4.size()
            if (r2 != r1) goto L_0x0179
            java.lang.Object r2 = r4.get(r0)
            androidx.slice.SliceItem r2 = (androidx.slice.SliceItem) r2
            boolean r2 = isValidRow(r2)
            if (r2 == 0) goto L_0x0195
            java.lang.Object r11 = r4.get(r0)
            androidx.slice.SliceItem r11 = (androidx.slice.SliceItem) r11
            java.util.ArrayList r4 = filterInvalidItems(r11)
            r10.mRange = r11
            androidx.slice.SliceItem r2 = r10.getInputRangeThumb()
            r4.remove(r2)
            goto L_0x0195
        L_0x0179:
            androidx.slice.SliceItem r2 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r11, (java.lang.String) r7, (java.lang.String) r8)
            r10.mRange = r2
            java.util.ArrayList r2 = filterInvalidItems(r2)
            androidx.slice.SliceItem r5 = r10.getInputRangeThumb()
            r2.remove(r5)
            androidx.slice.SliceItem r5 = r10.mRange
            r4.remove(r5)
            r4.addAll(r2)
            goto L_0x0195
        L_0x0193:
            r10.mRange = r11
        L_0x0195:
            java.lang.String r2 = r11.mSubType
            java.lang.String r5 = "selection"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x01a2
            r10.mSelection = r11
        L_0x01a2:
            int r11 = r4.size()
            if (r11 <= 0) goto L_0x02a3
            androidx.slice.SliceItem r11 = r10.mStartItem
            if (r11 == 0) goto L_0x01af
            r4.remove(r11)
        L_0x01af:
            androidx.slice.SliceItem r11 = r10.mPrimaryAction
            if (r11 == 0) goto L_0x01b6
            r4.remove(r11)
        L_0x01b6:
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            r2 = r0
        L_0x01bc:
            int r5 = r4.size()
            if (r2 >= r5) goto L_0x0212
            java.lang.Object r5 = r4.get(r2)
            androidx.slice.SliceItem r5 = (androidx.slice.SliceItem) r5
            java.util.Objects.requireNonNull(r5)
            java.lang.String r8 = r5.mFormat
            java.lang.String r9 = "text"
            boolean r8 = r9.equals(r8)
            if (r8 == 0) goto L_0x020c
            androidx.slice.SliceItem r8 = r10.mTitleItem
            java.lang.String r9 = "summary"
            if (r8 == 0) goto L_0x01e3
            boolean r8 = r8.hasHint(r12)
            if (r8 != 0) goto L_0x01f2
        L_0x01e3:
            boolean r8 = r5.hasHint(r12)
            if (r8 == 0) goto L_0x01f2
            boolean r8 = r5.hasHint(r9)
            if (r8 != 0) goto L_0x01f2
            r10.mTitleItem = r5
            goto L_0x020f
        L_0x01f2:
            androidx.slice.SliceItem r8 = r10.mSubtitleItem
            if (r8 != 0) goto L_0x01ff
            boolean r8 = r5.hasHint(r9)
            if (r8 != 0) goto L_0x01ff
            r10.mSubtitleItem = r5
            goto L_0x020f
        L_0x01ff:
            androidx.slice.SliceItem r8 = r10.mSummaryItem
            if (r8 != 0) goto L_0x020f
            boolean r8 = r5.hasHint(r9)
            if (r8 == 0) goto L_0x020f
            r10.mSummaryItem = r5
            goto L_0x020f
        L_0x020c:
            r11.add(r5)
        L_0x020f:
            int r2 = r2 + 1
            goto L_0x01bc
        L_0x0212:
            androidx.slice.SliceItem r12 = r10.mTitleItem
            java.lang.String r2 = "partial"
            if (r12 == 0) goto L_0x022a
            boolean r4 = r12.hasHint(r2)
            if (r4 != 0) goto L_0x0228
            java.lang.Object r12 = r12.mObj
            java.lang.CharSequence r12 = (java.lang.CharSequence) r12
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 != 0) goto L_0x022a
        L_0x0228:
            r12 = r1
            goto L_0x022b
        L_0x022a:
            r12 = r0
        L_0x022b:
            if (r12 == 0) goto L_0x0232
            int r12 = r10.mLineCount
            int r12 = r12 + r1
            r10.mLineCount = r12
        L_0x0232:
            androidx.slice.SliceItem r12 = r10.mSubtitleItem
            if (r12 == 0) goto L_0x0248
            boolean r2 = r12.hasHint(r2)
            if (r2 != 0) goto L_0x0246
            java.lang.Object r12 = r12.mObj
            java.lang.CharSequence r12 = (java.lang.CharSequence) r12
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 != 0) goto L_0x0248
        L_0x0246:
            r12 = r1
            goto L_0x0249
        L_0x0248:
            r12 = r0
        L_0x0249:
            if (r12 == 0) goto L_0x0250
            int r12 = r10.mLineCount
            int r12 = r12 + r1
            r10.mLineCount = r12
        L_0x0250:
            androidx.slice.SliceItem r12 = r10.mStartItem
            if (r12 == 0) goto L_0x025e
            java.lang.String r12 = r12.mFormat
            boolean r12 = r6.equals(r12)
            if (r12 == 0) goto L_0x025e
            r12 = r1
            goto L_0x025f
        L_0x025e:
            r12 = r0
        L_0x025f:
            r2 = r0
        L_0x0260:
            int r4 = r11.size()
            if (r2 >= r4) goto L_0x02a3
            java.lang.Object r4 = r11.get(r2)
            androidx.slice.SliceItem r4 = (androidx.slice.SliceItem) r4
            androidx.slice.SliceItem r5 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r4, (java.lang.String) r7, (java.lang.String[]) r3, (java.lang.String[]) r3)
            if (r5 == 0) goto L_0x0274
            r5 = r1
            goto L_0x0275
        L_0x0274:
            r5 = r0
        L_0x0275:
            java.util.Objects.requireNonNull(r4)
            java.lang.String r8 = r4.mFormat
            boolean r8 = r6.equals(r8)
            if (r8 == 0) goto L_0x0289
            if (r12 != 0) goto L_0x02a0
            java.util.ArrayList<androidx.slice.SliceItem> r12 = r10.mEndItems
            r12.add(r4)
            r12 = r1
            goto L_0x02a0
        L_0x0289:
            if (r5 == 0) goto L_0x029b
            androidx.slice.core.SliceActionImpl r5 = new androidx.slice.core.SliceActionImpl
            r5.<init>(r4)
            boolean r8 = r5.isToggle()
            if (r8 == 0) goto L_0x029b
            java.util.ArrayList<androidx.slice.core.SliceAction> r8 = r10.mToggleItems
            r8.add(r5)
        L_0x029b:
            java.util.ArrayList<androidx.slice.SliceItem> r5 = r10.mEndItems
            r5.add(r4)
        L_0x02a0:
            int r2 = r2 + 1
            goto L_0x0260
        L_0x02a3:
            r10.isValid()
        L_0x02a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.RowContent.<init>(androidx.slice.SliceItem, int):void");
    }

    public final int getHeight(SliceStyle sliceStyle, SliceViewPolicy sliceViewPolicy) {
        int i;
        int i2;
        int i3;
        SliceItem sliceItem;
        Objects.requireNonNull(sliceStyle);
        Objects.requireNonNull(sliceViewPolicy);
        int i4 = sliceViewPolicy.mMaxSmallHeight;
        if (i4 <= 0) {
            i4 = sliceStyle.mRowMaxHeight;
        }
        SliceItem sliceItem2 = this.mRange;
        SliceItem sliceItem3 = this.mSelection;
        if (sliceItem2 != null) {
            if (!this.mIsHeader || this.mShowTitleItems) {
                sliceItem = this.mStartItem;
            } else {
                sliceItem = null;
            }
            if (sliceItem != null) {
                return sliceStyle.mRowInlineRangeHeight;
            }
            int i5 = this.mLineCount;
            if (i5 == 0) {
                i2 = 0;
            } else if (i5 > 1) {
                i2 = sliceStyle.mRowTextWithRangeHeight;
            } else {
                i2 = sliceStyle.mRowSingleTextWithRangeHeight;
            }
            i = sliceStyle.mRowRangeHeight;
        } else if (sliceItem3 != null) {
            if (this.mLineCount > 1) {
                i3 = sliceStyle.mRowTextWithSelectionHeight;
            } else {
                i3 = sliceStyle.mRowSingleTextWithSelectionHeight;
            }
            i = sliceStyle.mRowSelectionHeight;
        } else {
            if (this.mLineCount <= 1 && !this.mIsHeader) {
                i4 = sliceStyle.mRowMinHeight;
            }
            return i4;
        }
        return i2 + i;
    }
}
