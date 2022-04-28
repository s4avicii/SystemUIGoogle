package androidx.slice.builders.impl;

import android.net.Uri;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Pair;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.SliceSpec;
import androidx.slice.SystemClock;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public final class ListBuilderImpl extends TemplateBuilderImpl implements ListBuilder {
    public boolean mFirstRowChecked;
    public boolean mFirstRowHasText;
    public boolean mIsFirstRowTypeValid;
    public Slice mSliceHeader;

    public static class RowBuilderImpl extends TemplateBuilderImpl {
        public CharSequence mContentDescr;
        public final ArrayList<Slice> mEndItems = new ArrayList<>();
        public SliceAction mPrimaryAction;
        public SliceItem mTitleItem;

        public RowBuilderImpl(Slice.Builder builder) {
            super(builder, (SliceSpec) null);
        }

        public final void apply(Slice.Builder builder) {
            SliceItem sliceItem = this.mTitleItem;
            if (sliceItem != null) {
                builder.addItem(sliceItem);
            }
            for (int i = 0; i < this.mEndItems.size(); i++) {
                Slice slice = this.mEndItems.get(i);
                Objects.requireNonNull(builder);
                Objects.requireNonNull(slice);
                builder.addSubSlice(slice, (String) null);
            }
            CharSequence charSequence = this.mContentDescr;
            if (charSequence != null) {
                builder.addText(charSequence, "content_description", new String[0]);
            }
            SliceAction sliceAction = this.mPrimaryAction;
            if (sliceAction != null) {
                sliceAction.setPrimaryAction(builder);
            }
        }
    }

    public final void setHeader(ListBuilder.HeaderBuilder headerBuilder) {
        SliceItem sliceItem;
        this.mIsFirstRowTypeValid = true;
        this.mFirstRowHasText = true;
        this.mFirstRowChecked = true;
        Objects.requireNonNull(this);
        Slice.Builder builder = new Slice.Builder(this.mSliceBuilder);
        Uri uri = headerBuilder.mUri;
        if (uri != null) {
            builder = new Slice.Builder(uri);
        }
        SliceAction sliceAction = headerBuilder.mPrimaryAction;
        builder.addInt(0, "layout_direction", new String[0]);
        CharSequence charSequence = headerBuilder.mTitle;
        if (charSequence != null || headerBuilder.mTitleLoading) {
            boolean z = headerBuilder.mTitleLoading;
            sliceItem = new SliceItem((Object) charSequence, "text", (String) null, new String[]{"title"});
            if (z) {
                sliceItem.addHint();
            }
        } else {
            sliceItem = null;
        }
        Objects.requireNonNull(builder);
        builder.mSpec = null;
        if (sliceItem != null) {
            builder.addItem(sliceItem);
        }
        if (sliceAction != null) {
            sliceAction.setPrimaryAction(builder);
        }
        if (sliceItem != null) {
            this.mSliceHeader = builder.build();
            return;
        }
        throw new IllegalStateException("Header requires a title or subtitle to be set.");
    }

    public final void addRow(ListBuilder.RowBuilder rowBuilder) {
        boolean z;
        boolean z2;
        ListBuilder.RowBuilder rowBuilder2 = rowBuilder;
        RowBuilderImpl rowBuilderImpl = new RowBuilderImpl(new Slice.Builder(this.mSliceBuilder));
        Uri uri = rowBuilder2.mUri;
        if (uri != null) {
            rowBuilderImpl.mSliceBuilder = new Slice.Builder(uri);
        }
        rowBuilderImpl.mPrimaryAction = rowBuilder2.mPrimaryAction;
        CharSequence charSequence = rowBuilder2.mTitle;
        if (charSequence != null || rowBuilder2.mTitleLoading) {
            boolean z3 = rowBuilder2.mTitleLoading;
            SliceItem sliceItem = new SliceItem((Object) charSequence, "text", (String) null, new String[]{"title"});
            rowBuilderImpl.mTitleItem = sliceItem;
            if (z3) {
                sliceItem.addHint();
            }
        }
        CharSequence charSequence2 = rowBuilder2.mContentDescription;
        if (charSequence2 != null) {
            rowBuilderImpl.mContentDescr = charSequence2;
        }
        ArrayList arrayList = rowBuilder2.mEndItems;
        ArrayList arrayList2 = rowBuilder2.mEndTypes;
        ArrayList arrayList3 = rowBuilder2.mEndLoads;
        for (int i = 0; i < arrayList.size(); i++) {
            int intValue = ((Integer) arrayList2.get(i)).intValue();
            if (intValue == 0) {
                long longValue = ((Long) arrayList.get(i)).longValue();
                ArrayList<Slice> arrayList4 = rowBuilderImpl.mEndItems;
                Slice.Builder builder = rowBuilderImpl.mSliceBuilder;
                ArrayList arrayList5 = new ArrayList();
                ArrayList arrayList6 = new ArrayList();
                Objects.requireNonNull(builder);
                Uri.Builder appendPath = builder.mUri.buildUpon().appendPath("_gen");
                int i2 = builder.mChildId;
                builder.mChildId = i2 + 1;
                Uri build = appendPath.appendPath(String.valueOf(i2)).build();
                arrayList5.add(new SliceItem((Object) Long.valueOf(longValue), "long", (String) null, new String[0]));
                arrayList4.add(new Slice(arrayList5, (String[]) arrayList6.toArray(new String[arrayList6.size()]), build, (SliceSpec) null));
            } else if (intValue == 1) {
                Pair pair = (Pair) arrayList.get(i);
                IconCompat iconCompat = (IconCompat) pair.first;
                int intValue2 = ((Integer) pair.second).intValue();
                boolean booleanValue = ((Boolean) arrayList3.get(i)).booleanValue();
                Slice.Builder builder2 = rowBuilderImpl.mSliceBuilder;
                ArrayList arrayList7 = new ArrayList();
                ArrayList arrayList8 = new ArrayList();
                Objects.requireNonNull(builder2);
                Uri.Builder appendPath2 = builder2.mUri.buildUpon().appendPath("_gen");
                int i3 = builder2.mChildId;
                builder2.mChildId = i3 + 1;
                Uri build2 = appendPath2.appendPath(String.valueOf(i3)).build();
                ArrayList arrayList9 = new ArrayList();
                if (intValue2 == 6) {
                    arrayList9.add("show_label");
                }
                if (intValue2 != 0) {
                    arrayList9.add("no_tint");
                }
                if (intValue2 == 2 || intValue2 == 4) {
                    arrayList9.add("large");
                }
                if (intValue2 == 3 || intValue2 == 4) {
                    arrayList9.add("raw");
                }
                if (booleanValue) {
                    arrayList9.add("partial");
                }
                Objects.requireNonNull(iconCompat);
                if (Slice.isValidIcon(iconCompat)) {
                    String[] strArr = (String[]) arrayList9.toArray(new String[arrayList9.size()]);
                    Objects.requireNonNull(iconCompat);
                    if (Slice.isValidIcon(iconCompat)) {
                        arrayList7.add(new SliceItem((Object) iconCompat, "image", (String) null, strArr));
                    }
                }
                if (booleanValue) {
                    arrayList8.addAll(Arrays.asList(new String[]{"partial"}));
                }
                rowBuilderImpl.mEndItems.add(new Slice(arrayList7, (String[]) arrayList8.toArray(new String[arrayList8.size()]), build2, (SliceSpec) null));
            } else if (intValue == 2) {
                SliceAction sliceAction = (SliceAction) arrayList.get(i);
                boolean booleanValue2 = ((Boolean) arrayList3.get(i)).booleanValue();
                Slice.Builder builder3 = new Slice.Builder(rowBuilderImpl.mSliceBuilder);
                if (booleanValue2) {
                    builder3.addHints("partial");
                }
                ArrayList<Slice> arrayList10 = rowBuilderImpl.mEndItems;
                Objects.requireNonNull(sliceAction);
                SliceActionImpl sliceActionImpl = sliceAction.mSliceAction;
                Objects.requireNonNull(sliceActionImpl);
                builder3.addHints("shortcut");
                builder3.addAction(sliceActionImpl.mAction, sliceActionImpl.buildSliceContent(builder3).build(), sliceActionImpl.getSubtype());
                arrayList10.add(builder3.build());
            }
        }
        SliceItem sliceItem2 = rowBuilderImpl.mTitleItem;
        if (sliceItem2 == null) {
            z = false;
        } else {
            z = true;
        }
        if (!this.mFirstRowChecked) {
            this.mFirstRowChecked = true;
            this.mIsFirstRowTypeValid = true;
            this.mFirstRowHasText = z;
        }
        if (sliceItem2 == null) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!this.mFirstRowChecked) {
            this.mFirstRowChecked = true;
            this.mIsFirstRowTypeValid = true;
            this.mFirstRowHasText = z2;
        }
        rowBuilderImpl.mSliceBuilder.addHints("list_item");
        Slice.Builder builder4 = this.mSliceBuilder;
        Slice build3 = rowBuilderImpl.build();
        Objects.requireNonNull(builder4);
        builder4.addSubSlice(build3, (String) null);
    }

    public final void apply(Slice.Builder builder) {
        Objects.requireNonNull(this.mClock);
        builder.addLong(System.currentTimeMillis(), "millis", "last_updated");
        Slice slice = this.mSliceHeader;
        if (slice != null) {
            builder.addSubSlice(slice, (String) null);
        }
    }

    public final void setTtl() {
        Slice.Builder builder = this.mSliceBuilder;
        Objects.requireNonNull(builder);
        builder.mItems.add(new SliceItem((Object) -1L, "long", "millis", new String[]{"ttl"}));
    }

    public final Slice build() {
        boolean z;
        Slice build = super.build();
        boolean z2 = true;
        if (SliceQuery.find(build, (String) null, "partial") != null) {
            z = true;
        } else {
            z = false;
        }
        if (SliceQuery.find(build, "slice", "list_item") != null) {
            z2 = false;
        }
        String[] strArr = {"shortcut", "title"};
        SliceItem find = SliceQuery.find(build, "action", strArr, (String[]) null);
        ArrayList arrayList = new ArrayList();
        ArrayDeque arrayDeque = new ArrayDeque();
        Collections.addAll(arrayDeque, build.mItems);
        SliceQuery.findAll(arrayDeque, new SliceQuery.Filter<SliceItem>(strArr) {
            public final /* synthetic */ String val$format = "slice";
            public final /* synthetic */ String[] val$hints;
            public final /* synthetic */ String[] val$nonHints;

            public final boolean filter(SliceItem sliceItem) {
                if (!SliceQuery.checkFormat(sliceItem, this.val$format) || !SliceQuery.hasHints(sliceItem, this.val$hints) || SliceQuery.hasAnyHints(sliceItem, this.val$nonHints)) {
                    return false;
                }
                return true;
            }

            {
                this.val$hints = r2;
                this.val$nonHints = null;
            }
        }, arrayList);
        if (z || z2 || find != null || !arrayList.isEmpty()) {
            boolean z3 = this.mFirstRowChecked;
            if (z3 && !this.mIsFirstRowTypeValid) {
                throw new IllegalStateException("A slice cannot have the first row be constructed from a GridRowBuilder, consider using #setHeader.");
            } else if (!z3 || this.mFirstRowHasText) {
                return build;
            } else {
                throw new IllegalStateException("A slice requires the first row to have some text.");
            }
        } else {
            throw new IllegalStateException("A slice requires a primary action; ensure one of your builders has called #setPrimaryAction with a valid SliceAction.");
        }
    }

    public ListBuilderImpl(Slice.Builder builder, SliceSpec sliceSpec, SystemClock systemClock) {
        super(builder, sliceSpec, systemClock);
    }
}
