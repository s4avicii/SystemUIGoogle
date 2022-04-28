package com.android.systemui.wifi;

import android.util.EventLog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.android.p012wm.shell.C1777R;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiDebuggingActivity$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    public static final /* synthetic */ WifiDebuggingActivity$$ExternalSyntheticLambda0 INSTANCE = new WifiDebuggingActivity$$ExternalSyntheticLambda0();

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i = WifiDebuggingActivity.$r8$clinit;
        if ((motionEvent.getFlags() & 1) == 0 && (motionEvent.getFlags() & 2) == 0) {
            return false;
        }
        if (motionEvent.getAction() != 1) {
            return true;
        }
        EventLog.writeEvent(1397638484, "62187985");
        Toast.makeText(view.getContext(), C1777R.string.touch_filtered_warning, 0).show();
        return true;
    }
}
