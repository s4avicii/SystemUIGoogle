package com.android.p012wm.shell.legacysplitscreen;

import android.app.Activity;
import android.app.ActivityManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.wm.shell.legacysplitscreen.ForcedResizableInfoActivity */
public class ForcedResizableInfoActivity extends Activity implements View.OnTouchListener {
    public final C18671 mFinishRunnable = new Runnable() {
        public final void run() {
            ForcedResizableInfoActivity.this.finish();
        }
    };

    public final void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
    }

    public final void finish() {
        super.finish();
        overridePendingTransition(0, C1777R.anim.forced_resizable_exit);
    }

    public final void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        setContentView(C1777R.layout.forced_resizable_activity);
        TextView textView = (TextView) findViewById(16908299);
        int intExtra = getIntent().getIntExtra("extra_forced_resizeable_reason", -1);
        if (intExtra == 1) {
            str = getString(C1777R.string.dock_forced_resizable);
        } else if (intExtra == 2) {
            str = getString(C1777R.string.forced_resizable_secondary_display);
        } else {
            throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Unexpected forced resizeable reason: ", intExtra));
        }
        textView.setText(str);
        getWindow().setTitle(str);
        getWindow().getDecorView().setOnTouchListener(this);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        finish();
        return true;
    }

    public final void onStart() {
        super.onStart();
        getWindow().getDecorView().postDelayed(this.mFinishRunnable, 2500);
    }

    public final void onStop() {
        super.onStop();
        finish();
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        finish();
        return true;
    }
}
