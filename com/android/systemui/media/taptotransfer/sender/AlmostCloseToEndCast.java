package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import com.android.p012wm.shell.C1777R;

/* compiled from: ChipStateSender.kt */
public final class AlmostCloseToEndCast extends ChipStateSender {
    public final String otherDeviceName;

    public final String getChipTextString(Context context) {
        return context.getString(C1777R.string.media_move_closer_to_end_cast, new Object[]{this.otherDeviceName});
    }

    public AlmostCloseToEndCast(String str, String str2) {
        super(str);
        this.otherDeviceName = str2;
    }
}
