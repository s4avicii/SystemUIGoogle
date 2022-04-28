package androidx.slice.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.SliceSpec;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ListContent extends SliceContent {
    public RowContent mHeaderContent;
    public SliceActionImpl mPrimaryAction;
    public ArrayList<SliceContent> mRowItems = new ArrayList<>();
    public RowContent mSeeMoreContent;
    public ArrayList mSliceActions;

    public static int getRowType(SliceContent sliceContent, boolean z, List<SliceAction> list) {
        if (sliceContent == null) {
            return 0;
        }
        if (sliceContent instanceof GridContent) {
            return 1;
        }
        RowContent rowContent = (RowContent) sliceContent;
        SliceItem sliceItem = rowContent.mPrimaryAction;
        SliceActionImpl sliceActionImpl = null;
        if (sliceItem != null) {
            sliceActionImpl = new SliceActionImpl(sliceItem);
        }
        SliceItem sliceItem2 = rowContent.mRange;
        if (sliceItem2 != null) {
            Objects.requireNonNull(sliceItem2);
            if ("action".equals(sliceItem2.mFormat)) {
                return 4;
            }
            return 5;
        } else if (rowContent.mSelection != null) {
            return 6;
        } else {
            if (sliceActionImpl != null && sliceActionImpl.isToggle()) {
                return 3;
            }
            if (!z || list == null) {
                return rowContent.mToggleItems.size() > 0 ? 3 : 0;
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isToggle()) {
                    return 3;
                }
            }
            return 0;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0098, code lost:
        if (r11 != false) goto L_0x009c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ListContent(androidx.slice.Slice r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r17.<init>(r18)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r0.mRowItems = r2
            androidx.slice.SliceItem r2 = r0.mSliceItem
            if (r2 != 0) goto L_0x0013
            return
        L_0x0013:
            if (r1 != 0) goto L_0x0017
            goto L_0x01cb
        L_0x0017:
            java.lang.String r2 = "slice"
            java.lang.String r3 = "actions"
            r4 = 0
            androidx.slice.SliceItem r5 = androidx.slice.core.SliceQuery.find((androidx.slice.Slice) r1, (java.lang.String) r2, (java.lang.String) r3)
            java.lang.String r6 = "shortcut"
            java.lang.String[] r7 = new java.lang.String[]{r3, r6}
            if (r5 == 0) goto L_0x002f
            java.util.ArrayList r5 = androidx.slice.core.SliceQuery.findAll(r5, r2, r7, r4)
            goto L_0x0030
        L_0x002f:
            r5 = r4
        L_0x0030:
            r7 = 0
            if (r5 == 0) goto L_0x0054
            java.util.ArrayList r8 = new java.util.ArrayList
            int r9 = r5.size()
            r8.<init>(r9)
            r9 = r7
        L_0x003d:
            int r10 = r5.size()
            if (r9 >= r10) goto L_0x0055
            java.lang.Object r10 = r5.get(r9)
            androidx.slice.SliceItem r10 = (androidx.slice.SliceItem) r10
            androidx.slice.core.SliceActionImpl r11 = new androidx.slice.core.SliceActionImpl
            r11.<init>(r10)
            r8.add(r11)
            int r9 = r9 + 1
            goto L_0x003d
        L_0x0054:
            r8 = r4
        L_0x0055:
            r0.mSliceActions = r8
            java.lang.String r9 = "list_item"
            java.lang.String r10 = "shortcut"
            java.lang.String r11 = "actions"
            java.lang.String r12 = "keywords"
            java.lang.String r13 = "ttl"
            java.lang.String r14 = "last_updated"
            java.lang.String r15 = "horizontal"
            java.lang.String r16 = "selection_option"
            java.lang.String[] r5 = new java.lang.String[]{r9, r10, r11, r12, r13, r14, r15, r16}
            androidx.slice.SliceItem r5 = androidx.slice.core.SliceQuery.find((androidx.slice.Slice) r1, (java.lang.String) r2, (java.lang.String[]) r4, (java.lang.String[]) r5)
            java.lang.String r8 = "keywords"
            java.lang.String r9 = "see_more"
            r10 = 1
            if (r5 == 0) goto L_0x009b
            java.lang.String r11 = r5.mFormat
            boolean r11 = r2.equals(r11)
            if (r11 == 0) goto L_0x0097
            java.lang.String[] r11 = new java.lang.String[]{r3, r8, r9}
            boolean r11 = r5.hasAnyHints(r11)
            if (r11 != 0) goto L_0x0097
            java.lang.String r11 = "text"
            androidx.slice.SliceItem r11 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r5, (java.lang.String) r11, (java.lang.String) r4)
            if (r11 == 0) goto L_0x0097
            r11 = r10
            goto L_0x0098
        L_0x0097:
            r11 = r7
        L_0x0098:
            if (r11 == 0) goto L_0x009b
            goto L_0x009c
        L_0x009b:
            r5 = r4
        L_0x009c:
            if (r5 == 0) goto L_0x00aa
            androidx.slice.widget.RowContent r11 = new androidx.slice.widget.RowContent
            r11.<init>(r5, r7)
            r0.mHeaderContent = r11
            java.util.ArrayList<androidx.slice.widget.SliceContent> r5 = r0.mRowItems
            r5.add(r11)
        L_0x00aa:
            java.lang.String[] r5 = new java.lang.String[]{r9}
            androidx.slice.SliceItem r5 = androidx.slice.core.SliceQuery.findTopLevelItem(r1, r4, r4, r5)
            java.lang.String r11 = "action"
            if (r5 == 0) goto L_0x00e4
            java.lang.String r12 = r5.mFormat
            boolean r12 = r2.equals(r12)
            if (r12 == 0) goto L_0x00e4
            androidx.slice.Slice r12 = r5.getSlice()
            java.util.List r12 = r12.getItems()
            int r13 = r12.size()
            if (r13 != r10) goto L_0x00e5
            java.lang.Object r13 = r12.get(r7)
            androidx.slice.SliceItem r13 = (androidx.slice.SliceItem) r13
            java.util.Objects.requireNonNull(r13)
            java.lang.String r13 = r13.mFormat
            boolean r13 = r11.equals(r13)
            if (r13 == 0) goto L_0x00e5
            java.lang.Object r5 = r12.get(r7)
            androidx.slice.SliceItem r5 = (androidx.slice.SliceItem) r5
            goto L_0x00e5
        L_0x00e4:
            r5 = r4
        L_0x00e5:
            if (r5 == 0) goto L_0x00ef
            androidx.slice.widget.RowContent r12 = new androidx.slice.widget.RowContent
            r13 = -1
            r12.<init>(r5, r13)
            r0.mSeeMoreContent = r12
        L_0x00ef:
            java.util.List r1 = r18.getItems()
            r5 = r7
        L_0x00f4:
            int r12 = r1.size()
            if (r5 >= r12) goto L_0x015f
            java.lang.Object r12 = r1.get(r5)
            androidx.slice.SliceItem r12 = (androidx.slice.SliceItem) r12
            java.util.Objects.requireNonNull(r12)
            java.lang.String r13 = r12.mFormat
            java.lang.String r14 = "ttl"
            java.lang.String r15 = "last_updated"
            java.lang.String[] r14 = new java.lang.String[]{r3, r9, r8, r14, r15}
            boolean r14 = r12.hasAnyHints(r14)
            if (r14 != 0) goto L_0x015c
            boolean r14 = r11.equals(r13)
            if (r14 != 0) goto L_0x0120
            boolean r13 = r2.equals(r13)
            if (r13 == 0) goto L_0x015c
        L_0x0120:
            androidx.slice.widget.RowContent r13 = r0.mHeaderContent
            java.lang.String r14 = "list_item"
            if (r13 != 0) goto L_0x0139
            boolean r13 = r12.hasHint(r14)
            if (r13 != 0) goto L_0x0139
            androidx.slice.widget.RowContent r13 = new androidx.slice.widget.RowContent
            r13.<init>(r12, r7)
            r0.mHeaderContent = r13
            java.util.ArrayList<androidx.slice.widget.SliceContent> r12 = r0.mRowItems
            r12.add(r7, r13)
            goto L_0x015c
        L_0x0139:
            boolean r13 = r12.hasHint(r14)
            if (r13 == 0) goto L_0x015c
            java.lang.String r13 = "horizontal"
            boolean r13 = r12.hasHint(r13)
            if (r13 == 0) goto L_0x0152
            java.util.ArrayList<androidx.slice.widget.SliceContent> r13 = r0.mRowItems
            androidx.slice.widget.GridContent r14 = new androidx.slice.widget.GridContent
            r14.<init>(r12, r5)
            r13.add(r14)
            goto L_0x015c
        L_0x0152:
            java.util.ArrayList<androidx.slice.widget.SliceContent> r13 = r0.mRowItems
            androidx.slice.widget.RowContent r14 = new androidx.slice.widget.RowContent
            r14.<init>(r12, r5)
            r13.add(r14)
        L_0x015c:
            int r5 = r5 + 1
            goto L_0x00f4
        L_0x015f:
            androidx.slice.widget.RowContent r1 = r0.mHeaderContent
            if (r1 != 0) goto L_0x017a
            java.util.ArrayList<androidx.slice.widget.SliceContent> r1 = r0.mRowItems
            int r1 = r1.size()
            if (r1 < r10) goto L_0x017a
            java.util.ArrayList<androidx.slice.widget.SliceContent> r1 = r0.mRowItems
            java.lang.Object r1 = r1.get(r7)
            androidx.slice.widget.RowContent r1 = (androidx.slice.widget.RowContent) r1
            r0.mHeaderContent = r1
            java.util.Objects.requireNonNull(r1)
            r1.mIsHeader = r10
        L_0x017a:
            java.util.ArrayList<androidx.slice.widget.SliceContent> r1 = r0.mRowItems
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x01a3
            java.util.ArrayList<androidx.slice.widget.SliceContent> r1 = r0.mRowItems
            int r2 = r1.size()
            int r2 = r2 - r10
            java.lang.Object r1 = r1.get(r2)
            boolean r1 = r1 instanceof androidx.slice.widget.GridContent
            if (r1 == 0) goto L_0x01a3
            java.util.ArrayList<androidx.slice.widget.SliceContent> r1 = r0.mRowItems
            int r2 = r1.size()
            int r2 = r2 - r10
            java.lang.Object r1 = r1.get(r2)
            androidx.slice.widget.GridContent r1 = (androidx.slice.widget.GridContent) r1
            java.util.Objects.requireNonNull(r1)
            r1.mIsLastIndex = r10
        L_0x01a3:
            androidx.slice.widget.RowContent r1 = r0.mHeaderContent
            if (r1 == 0) goto L_0x01aa
            androidx.slice.SliceItem r1 = r1.mPrimaryAction
            goto L_0x01ab
        L_0x01aa:
            r1 = r4
        L_0x01ab:
            if (r1 != 0) goto L_0x01ba
            java.lang.String r1 = "title"
            java.lang.String[] r1 = new java.lang.String[]{r6, r1}
            androidx.slice.SliceItem r2 = r0.mSliceItem
            androidx.slice.SliceItem r1 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r2, (java.lang.String) r11, (java.lang.String[]) r1, (java.lang.String[]) r4)
        L_0x01ba:
            if (r1 != 0) goto L_0x01c2
            androidx.slice.SliceItem r1 = r0.mSliceItem
            androidx.slice.SliceItem r1 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r1, (java.lang.String) r11, (java.lang.String) r4)
        L_0x01c2:
            if (r1 == 0) goto L_0x01c9
            androidx.slice.core.SliceActionImpl r4 = new androidx.slice.core.SliceActionImpl
            r4.<init>(r1)
        L_0x01c9:
            r0.mPrimaryAction = r4
        L_0x01cb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.ListContent.<init>(androidx.slice.Slice):void");
    }

    public final SliceAction getShortcut(Context context) {
        SliceItem sliceItem;
        SliceItem sliceItem2;
        SliceActionImpl sliceActionImpl;
        IconCompat iconCompat;
        CharSequence charSequence;
        ApplicationInfo applicationInfo;
        Intent launchIntentForPackage;
        IconCompat iconCompat2;
        SliceActionImpl sliceActionImpl2 = this.mPrimaryAction;
        if (sliceActionImpl2 != null) {
            return sliceActionImpl2;
        }
        SliceItem sliceItem3 = this.mSliceItem;
        if (sliceItem3 != null) {
            int i = 5;
            SliceItem find = SliceQuery.find(sliceItem3, "action", new String[]{"title", "shortcut"}, (String[]) null);
            if (find != null) {
                sliceItem2 = SliceQuery.find(find, "image", "title");
                sliceItem = SliceQuery.find(find, "text", (String) null);
            } else {
                sliceItem2 = null;
                sliceItem = null;
            }
            if (find == null) {
                find = SliceQuery.find(this.mSliceItem, "action", (String) null);
            }
            if (sliceItem2 == null) {
                sliceItem2 = SliceQuery.find(this.mSliceItem, "image", "title");
            }
            if (sliceItem == null) {
                sliceItem = SliceQuery.find(this.mSliceItem, "text", "title");
            }
            if (sliceItem2 == null) {
                sliceItem2 = SliceQuery.find(this.mSliceItem, "image", (String) null);
            }
            if (sliceItem == null) {
                sliceItem = SliceQuery.find(this.mSliceItem, "text", (String) null);
            }
            if (sliceItem2 != null) {
                i = SliceActionImpl.parseImageMode(sliceItem2);
            }
            if (context != null) {
                SliceItem find2 = SliceQuery.find(this.mSliceItem, "slice", (String) null);
                if (find2 != null) {
                    Uri uri = find2.getSlice().getUri();
                    if (sliceItem2 != null) {
                        iconCompat = (IconCompat) sliceItem2.mObj;
                    } else {
                        iconCompat = null;
                    }
                    if (sliceItem != null) {
                        charSequence = (CharSequence) sliceItem.mObj;
                    } else {
                        charSequence = null;
                    }
                    PackageManager packageManager = context.getPackageManager();
                    ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(uri.getAuthority(), 0);
                    if (resolveContentProvider != null) {
                        applicationInfo = resolveContentProvider.applicationInfo;
                    } else {
                        applicationInfo = null;
                    }
                    if (applicationInfo != null) {
                        if (iconCompat == null) {
                            Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfo);
                            if (applicationIcon instanceof BitmapDrawable) {
                                iconCompat2 = IconCompat.createWithBitmap(((BitmapDrawable) applicationIcon).getBitmap());
                            } else {
                                Bitmap createBitmap = Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(createBitmap);
                                applicationIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                applicationIcon.draw(canvas);
                                iconCompat2 = IconCompat.createWithBitmap(createBitmap);
                            }
                            iconCompat = iconCompat2;
                            i = 2;
                        }
                        if (charSequence == null) {
                            charSequence = packageManager.getApplicationLabel(applicationInfo);
                        }
                        if (find == null && (launchIntentForPackage = packageManager.getLaunchIntentForPackage(applicationInfo.packageName)) != null) {
                            PendingIntent activity = PendingIntent.getActivity(context, 0, launchIntentForPackage, 67108864);
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = new ArrayList();
                            find = new SliceItem(activity, new Slice(arrayList, (String[]) arrayList2.toArray(new String[arrayList2.size()]), uri, (SliceSpec) null), (String) null, new String[0]);
                        }
                    }
                    if (find == null) {
                        find = new SliceItem(PendingIntent.getActivity(context, 0, new Intent(), 67108864), (Slice) null, (String) null, (String[]) null);
                    }
                    if (!(charSequence == null || iconCompat == null)) {
                        sliceActionImpl = new SliceActionImpl(find.getAction(), iconCompat, i, charSequence);
                    }
                }
            } else if (!(sliceItem2 == null || find == null || sliceItem == null)) {
                sliceActionImpl = new SliceActionImpl(find.getAction(), (IconCompat) sliceItem2.mObj, i, (CharSequence) sliceItem.mObj);
            }
            return sliceActionImpl;
        }
        return null;
    }

    public final boolean isValid() {
        boolean z;
        if (this.mSliceItem != null) {
            z = true;
        } else {
            z = false;
        }
        if (!z || this.mRowItems.size() <= 0) {
            return false;
        }
        return true;
    }

    public final int getHeight(SliceStyle sliceStyle, SliceViewPolicy sliceViewPolicy) {
        int i;
        boolean z;
        Objects.requireNonNull(sliceStyle);
        Objects.requireNonNull(sliceViewPolicy);
        int i2 = sliceViewPolicy.mMaxHeight;
        int listItemsHeight = sliceStyle.getListItemsHeight(this.mRowItems, sliceViewPolicy);
        if (i2 > 0) {
            i2 = Math.max(this.mHeaderContent.getHeight(sliceStyle, sliceViewPolicy), i2);
        }
        if (i2 > 0) {
            i = i2;
        } else {
            i = sliceStyle.mListLargeHeight;
        }
        if (listItemsHeight - i >= sliceStyle.mListMinScrollHeight) {
            z = true;
        } else {
            z = false;
        }
        if (z && !sliceStyle.mExpandToAvailableHeight) {
            return i;
        }
        if (i2 <= 0) {
            return listItemsHeight;
        }
        return Math.min(i, listItemsHeight);
    }
}
