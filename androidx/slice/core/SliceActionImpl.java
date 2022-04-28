package androidx.slice.core;

import android.app.PendingIntent;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceItem;

public final class SliceActionImpl implements SliceAction {
    public PendingIntent mAction;
    public SliceItem mActionItem;
    public String mActionKey;
    public ActionType mActionType;
    public CharSequence mContentDescription;
    public long mDateTimeMillis;
    public IconCompat mIcon;
    public int mImageMode;
    public boolean mIsActivity;
    public boolean mIsChecked;
    public int mPriority;
    public SliceItem mSliceItem;
    public CharSequence mTitle;

    public enum ActionType {
        DEFAULT,
        TOGGLE,
        DATE_PICKER,
        TIME_PICKER
    }

    public SliceActionImpl(PendingIntent pendingIntent, IconCompat iconCompat, int i, CharSequence charSequence) {
        this.mActionType = ActionType.DEFAULT;
        this.mPriority = -1;
        this.mDateTimeMillis = -1;
        this.mAction = pendingIntent;
        this.mIcon = iconCompat;
        this.mTitle = charSequence;
        this.mImageMode = i;
    }

    public final Slice.Builder buildSliceContent(Slice.Builder builder) {
        String[] strArr;
        Slice.Builder builder2 = new Slice.Builder(builder);
        IconCompat iconCompat = this.mIcon;
        if (iconCompat != null) {
            int i = this.mImageMode;
            if (i == 6) {
                strArr = new String[]{"show_label"};
            } else if (i == 0) {
                strArr = new String[0];
            } else {
                strArr = new String[]{"no_tint"};
            }
            builder2.addIcon(iconCompat, (String) null, strArr);
        }
        CharSequence charSequence = this.mTitle;
        if (charSequence != null) {
            builder2.addText(charSequence, (String) null, "title");
        }
        CharSequence charSequence2 = this.mContentDescription;
        if (charSequence2 != null) {
            builder2.addText(charSequence2, "content_description", new String[0]);
        }
        long j = this.mDateTimeMillis;
        if (j != -1) {
            builder2.addLong(j, "millis", new String[0]);
        }
        if (this.mActionType == ActionType.TOGGLE && this.mIsChecked) {
            builder2.addHints("selected");
        }
        int i2 = this.mPriority;
        if (i2 != -1) {
            builder2.addInt(i2, "priority", new String[0]);
        }
        String str = this.mActionKey;
        if (str != null) {
            builder2.addText(str, "action_key", new String[0]);
        }
        if (this.mIsActivity) {
            builder.addHints("activity");
        }
        return builder2;
    }

    public final String getSubtype() {
        int ordinal = this.mActionType.ordinal();
        if (ordinal == 1) {
            return "toggle";
        }
        if (ordinal == 2) {
            return "date_picker";
        }
        if (ordinal != 3) {
            return null;
        }
        return "time_picker";
    }

    public final boolean isDefaultToggle() {
        if (this.mActionType == ActionType.TOGGLE && this.mIcon == null) {
            return true;
        }
        return false;
    }

    public final boolean isToggle() {
        if (this.mActionType == ActionType.TOGGLE) {
            return true;
        }
        return false;
    }

    public static int parseImageMode(SliceItem sliceItem) {
        if (sliceItem.hasHint("show_label")) {
            return 6;
        }
        if (!sliceItem.hasHint("no_tint")) {
            return 0;
        }
        if (sliceItem.hasHint("raw")) {
            if (sliceItem.hasHint("large")) {
                return 4;
            }
            return 3;
        } else if (sliceItem.hasHint("large")) {
            return 2;
        } else {
            return 1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d5  */
    @android.annotation.SuppressLint({"InlinedApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SliceActionImpl(androidx.slice.SliceItem r9) {
        /*
            r8 = this;
            r8.<init>()
            r0 = 5
            r8.mImageMode = r0
            androidx.slice.core.SliceActionImpl$ActionType r0 = androidx.slice.core.SliceActionImpl.ActionType.DEFAULT
            r8.mActionType = r0
            r1 = -1
            r8.mPriority = r1
            r2 = -1
            r8.mDateTimeMillis = r2
            r8.mSliceItem = r9
            java.lang.String r2 = "action"
            r3 = 0
            androidx.slice.SliceItem r9 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r9, (java.lang.String) r2, (java.lang.String[]) r3, (java.lang.String[]) r3)
            if (r9 != 0) goto L_0x001d
            return
        L_0x001d:
            r8.mActionItem = r9
            android.app.PendingIntent r2 = r9.getAction()
            r8.mAction = r2
            androidx.slice.Slice r2 = r9.getSlice()
            java.lang.String r4 = "image"
            androidx.slice.SliceItem r2 = androidx.slice.core.SliceQuery.find((androidx.slice.Slice) r2, (java.lang.String) r4, (java.lang.String[]) r3, (java.lang.String[]) r3)
            if (r2 == 0) goto L_0x003d
            java.lang.Object r3 = r2.mObj
            androidx.core.graphics.drawable.IconCompat r3 = (androidx.core.graphics.drawable.IconCompat) r3
            r8.mIcon = r3
            int r2 = parseImageMode(r2)
            r8.mImageMode = r2
        L_0x003d:
            androidx.slice.Slice r2 = r9.getSlice()
            java.lang.String r3 = "text"
            java.lang.String r4 = "title"
            androidx.slice.SliceItem r2 = androidx.slice.core.SliceQuery.find((androidx.slice.Slice) r2, (java.lang.String) r3, (java.lang.String) r4)
            if (r2 == 0) goto L_0x0053
            java.lang.CharSequence r2 = r2.getSanitizedText()
            r8.mTitle = r2
        L_0x0053:
            androidx.slice.Slice r2 = r9.getSlice()
            java.lang.String r4 = "content_description"
            androidx.slice.SliceItem r2 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.Slice) r2, (java.lang.String) r3, (java.lang.String) r4)
            if (r2 == 0) goto L_0x0065
            java.lang.Object r2 = r2.mObj
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r8.mContentDescription = r2
        L_0x0065:
            java.lang.String r2 = r9.mSubType
            if (r2 != 0) goto L_0x006d
            r8.mActionType = r0
            goto L_0x00e2
        L_0x006d:
            int r4 = r2.hashCode()
            r5 = -868304044(0xffffffffcc3ebb54, float:-4.9999184E7)
            r6 = 2
            r7 = 1
            if (r4 == r5) goto L_0x009a
            r5 = 759128640(0x2d3f6240, float:1.0878909E-11)
            if (r4 == r5) goto L_0x008e
            r5 = 1250407999(0x4a87b63f, float:4447007.5)
            if (r4 == r5) goto L_0x0083
            goto L_0x00a3
        L_0x0083:
            java.lang.String r4 = "date_picker"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x008c
            goto L_0x00a3
        L_0x008c:
            r2 = r6
            goto L_0x00a6
        L_0x008e:
            java.lang.String r4 = "time_picker"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x0098
            goto L_0x00a3
        L_0x0098:
            r2 = r7
            goto L_0x00a6
        L_0x009a:
            java.lang.String r4 = "toggle"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x00a5
        L_0x00a3:
            r2 = r1
            goto L_0x00a6
        L_0x00a5:
            r2 = 0
        L_0x00a6:
            java.lang.String r4 = "millis"
            java.lang.String r5 = "long"
            if (r2 == 0) goto L_0x00d5
            if (r2 == r7) goto L_0x00c4
            if (r2 == r6) goto L_0x00b3
            r8.mActionType = r0
            goto L_0x00e2
        L_0x00b3:
            androidx.slice.core.SliceActionImpl$ActionType r0 = androidx.slice.core.SliceActionImpl.ActionType.DATE_PICKER
            r8.mActionType = r0
            androidx.slice.SliceItem r0 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r9, (java.lang.String) r5, (java.lang.String) r4)
            if (r0 == 0) goto L_0x00e2
            long r4 = r0.getLong()
            r8.mDateTimeMillis = r4
            goto L_0x00e2
        L_0x00c4:
            androidx.slice.core.SliceActionImpl$ActionType r0 = androidx.slice.core.SliceActionImpl.ActionType.TIME_PICKER
            r8.mActionType = r0
            androidx.slice.SliceItem r0 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r9, (java.lang.String) r5, (java.lang.String) r4)
            if (r0 == 0) goto L_0x00e2
            long r4 = r0.getLong()
            r8.mDateTimeMillis = r4
            goto L_0x00e2
        L_0x00d5:
            androidx.slice.core.SliceActionImpl$ActionType r0 = androidx.slice.core.SliceActionImpl.ActionType.TOGGLE
            r8.mActionType = r0
            java.lang.String r0 = "selected"
            boolean r0 = r9.hasHint(r0)
            r8.mIsChecked = r0
        L_0x00e2:
            androidx.slice.SliceItem r0 = r8.mSliceItem
            java.lang.String r2 = "activity"
            boolean r0 = r0.hasHint(r2)
            r8.mIsActivity = r0
            androidx.slice.Slice r0 = r9.getSlice()
            java.lang.String r2 = "int"
            java.lang.String r4 = "priority"
            androidx.slice.SliceItem r0 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.Slice) r0, (java.lang.String) r2, (java.lang.String) r4)
            if (r0 == 0) goto L_0x00fe
            int r1 = r0.getInt()
        L_0x00fe:
            r8.mPriority = r1
            androidx.slice.Slice r9 = r9.getSlice()
            java.lang.String r0 = "action_key"
            androidx.slice.SliceItem r9 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.Slice) r9, (java.lang.String) r3, (java.lang.String) r0)
            if (r9 == 0) goto L_0x0116
            java.lang.Object r9 = r9.mObj
            java.lang.CharSequence r9 = (java.lang.CharSequence) r9
            java.lang.String r9 = r9.toString()
            r8.mActionKey = r9
        L_0x0116:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.core.SliceActionImpl.<init>(androidx.slice.SliceItem):void");
    }

    public final int getPriority() {
        return this.mPriority;
    }
}
