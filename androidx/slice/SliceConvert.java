package androidx.slice;

import android.app.RemoteInput;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.app.slice.SliceSpec;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.collection.ArraySet;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import com.android.systemui.plugins.FalsingManager;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class SliceConvert {
    public static Slice unwrap(Slice slice) {
        SliceSpec sliceSpec = null;
        if (slice == null || slice.getUri() == null) {
            return null;
        }
        Uri uri = slice.getUri();
        SliceSpec sliceSpec2 = slice.mSpec;
        if (sliceSpec2 != null) {
            sliceSpec = new SliceSpec(sliceSpec2.mType, sliceSpec2.mRevision);
        }
        Slice.Builder builder = new Slice.Builder(uri, sliceSpec);
        builder.addHints(Arrays.asList(slice.mHints));
        for (SliceItem sliceItem : slice.mItems) {
            Objects.requireNonNull(sliceItem);
            String str = sliceItem.mFormat;
            Objects.requireNonNull(str);
            char c = 65535;
            switch (str.hashCode()) {
                case -1422950858:
                    if (str.equals("action")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1377881982:
                    if (str.equals("bundle")) {
                        c = 1;
                        break;
                    }
                    break;
                case 104431:
                    if (str.equals("int")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3327612:
                    if (str.equals("long")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3556653:
                    if (str.equals("text")) {
                        c = 4;
                        break;
                    }
                    break;
                case 100313435:
                    if (str.equals("image")) {
                        c = 5;
                        break;
                    }
                    break;
                case 100358090:
                    if (str.equals("input")) {
                        c = 6;
                        break;
                    }
                    break;
                case 109526418:
                    if (str.equals("slice")) {
                        c = 7;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    builder.addAction(sliceItem.getAction(), unwrap(sliceItem.getSlice()), sliceItem.mSubType);
                    break;
                case 1:
                    builder.addBundle((Bundle) sliceItem.mObj, sliceItem.mSubType, sliceItem.getHints());
                    break;
                case 2:
                    builder.addInt(sliceItem.getInt(), sliceItem.mSubType, sliceItem.getHints());
                    break;
                case 3:
                    builder.addLong(sliceItem.getLong(), sliceItem.mSubType, sliceItem.getHints());
                    break;
                case 4:
                    builder.addText((CharSequence) sliceItem.mObj, sliceItem.mSubType, sliceItem.getHints());
                    break;
                case 5:
                    IconCompat iconCompat = (IconCompat) sliceItem.mObj;
                    Objects.requireNonNull(iconCompat);
                    builder.addIcon(iconCompat.toIcon$1(), sliceItem.mSubType, sliceItem.getHints());
                    break;
                case FalsingManager.VERSION /*6*/:
                    builder.addRemoteInput((RemoteInput) sliceItem.mObj, sliceItem.mSubType, sliceItem.getHints());
                    break;
                case 7:
                    builder.addSubSlice(unwrap(sliceItem.getSlice()), sliceItem.mSubType);
                    break;
            }
        }
        return builder.build();
    }

    public static Slice wrap(Slice slice, Context context) {
        SliceSpec sliceSpec = null;
        if (slice == null || slice.getUri() == null) {
            return null;
        }
        Slice.Builder builder = new Slice.Builder(slice.getUri());
        List<String> hints = slice.getHints();
        builder.addHints((String[]) hints.toArray(new String[hints.size()]));
        SliceSpec spec = slice.getSpec();
        if (spec != null) {
            sliceSpec = new SliceSpec(spec.getType(), spec.getRevision());
        }
        builder.mSpec = sliceSpec;
        for (SliceItem next : slice.getItems()) {
            String format = next.getFormat();
            Objects.requireNonNull(format);
            char c = 65535;
            switch (format.hashCode()) {
                case -1422950858:
                    if (format.equals("action")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1377881982:
                    if (format.equals("bundle")) {
                        c = 1;
                        break;
                    }
                    break;
                case 104431:
                    if (format.equals("int")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3327612:
                    if (format.equals("long")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3556653:
                    if (format.equals("text")) {
                        c = 4;
                        break;
                    }
                    break;
                case 100313435:
                    if (format.equals("image")) {
                        c = 5;
                        break;
                    }
                    break;
                case 100358090:
                    if (format.equals("input")) {
                        c = 6;
                        break;
                    }
                    break;
                case 109526418:
                    if (format.equals("slice")) {
                        c = 7;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    builder.addAction(next.getAction(), wrap(next.getSlice(), context), next.getSubType());
                    break;
                case 1:
                    builder.addItem(new SliceItem((Object) next.getBundle(), next.getFormat(), next.getSubType(), next.getHints()));
                    break;
                case 2:
                    int i = next.getInt();
                    String subType = next.getSubType();
                    List<String> hints2 = next.getHints();
                    builder.addInt(i, subType, (String[]) hints2.toArray(new String[hints2.size()]));
                    break;
                case 3:
                    long j = next.getLong();
                    String subType2 = next.getSubType();
                    List<String> hints3 = next.getHints();
                    builder.addLong(j, subType2, (String[]) hints3.toArray(new String[hints3.size()]));
                    break;
                case 4:
                    CharSequence text = next.getText();
                    String subType3 = next.getSubType();
                    List<String> hints4 = next.getHints();
                    builder.addText(text, subType3, (String[]) hints4.toArray(new String[hints4.size()]));
                    break;
                case 5:
                    try {
                        IconCompat createFromIcon = IconCompat.createFromIcon(context, next.getIcon());
                        String subType4 = next.getSubType();
                        List<String> hints5 = next.getHints();
                        if (!Slice.isValidIcon(createFromIcon)) {
                            break;
                        } else {
                            builder.addIcon(createFromIcon, subType4, (String[]) hints5.toArray(new String[hints5.size()]));
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        Log.w("SliceConvert", "The icon resource isn't available.", e);
                        break;
                    } catch (Resources.NotFoundException e2) {
                        Log.w("SliceConvert", "The icon resource isn't available.", e2);
                        break;
                    }
                case FalsingManager.VERSION /*6*/:
                    RemoteInput remoteInput = next.getRemoteInput();
                    String subType5 = next.getSubType();
                    List<String> hints6 = next.getHints();
                    Objects.requireNonNull(remoteInput);
                    builder.mItems.add(new SliceItem((Object) remoteInput, "input", subType5, (String[]) hints6.toArray(new String[hints6.size()])));
                    break;
                case 7:
                    builder.addSubSlice(wrap(next.getSlice(), context), next.getSubType());
                    break;
            }
        }
        return builder.build();
    }

    public static ArraySet wrap(Set set) {
        SliceSpec sliceSpec;
        ArraySet arraySet = new ArraySet(0);
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                SliceSpec sliceSpec2 = (SliceSpec) it.next();
                if (sliceSpec2 == null) {
                    sliceSpec = null;
                } else {
                    sliceSpec = new SliceSpec(sliceSpec2.getType(), sliceSpec2.getRevision());
                }
                arraySet.add(sliceSpec);
            }
        }
        return arraySet;
    }

    public static ArraySet unwrap(ArraySet arraySet) {
        SliceSpec sliceSpec;
        ArraySet arraySet2 = new ArraySet(0);
        if (arraySet != null) {
            ArraySet.ElementIterator elementIterator = new ArraySet.ElementIterator();
            while (elementIterator.hasNext()) {
                SliceSpec sliceSpec2 = (SliceSpec) elementIterator.next();
                if (sliceSpec2 == null) {
                    sliceSpec = null;
                } else {
                    sliceSpec = new SliceSpec(sliceSpec2.mType, sliceSpec2.mRevision);
                }
                arraySet2.add(sliceSpec);
            }
        }
        return arraySet2;
    }
}
