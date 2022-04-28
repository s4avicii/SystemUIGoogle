package com.android.p012wm.shell.bubbles;

import android.view.View;

/* renamed from: com.android.wm.shell.bubbles.RelativeTouchListener$onTouch$1 */
/* compiled from: RelativeTouchListener.kt */
public final class RelativeTouchListener$onTouch$1 implements Runnable {

    /* renamed from: $v */
    public final /* synthetic */ View f121$v;
    public final /* synthetic */ RelativeTouchListener this$0;

    public RelativeTouchListener$onTouch$1(View view, RelativeTouchListener relativeTouchListener) {
        this.f121$v = view;
        this.this$0 = relativeTouchListener;
    }

    public final void run() {
        if (this.f121$v.isLongClickable()) {
            this.this$0.performedLongClick = this.f121$v.performLongClick();
        }
    }
}
