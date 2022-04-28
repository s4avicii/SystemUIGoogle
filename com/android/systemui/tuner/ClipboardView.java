package com.android.systemui.tuner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;

public class ClipboardView extends ImageView implements ClipboardManager.OnPrimaryClipChangedListener {
    public final ClipboardManager mClipboardManager;
    public ClipData mCurrentClip;

    public final void onPrimaryClipChanged() {
        int i;
        ClipData primaryClip = this.mClipboardManager.getPrimaryClip();
        this.mCurrentClip = primaryClip;
        if (primaryClip != null) {
            i = C1777R.C1778drawable.clipboard_full;
        } else {
            i = C1777R.C1778drawable.clipboard_empty;
        }
        setImageResource(i);
    }

    public ClipboardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClipboardManager = (ClipboardManager) context.getSystemService(ClipboardManager.class);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mClipboardManager.addPrimaryClipChangedListener(this);
        onPrimaryClipChanged();
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mClipboardManager.removePrimaryClipChangedListener(this);
    }

    public final boolean onDragEvent(DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action == 3) {
            this.mClipboardManager.setPrimaryClip(dragEvent.getClipData());
        } else if (action != 4) {
            if (action == 5) {
                setBackgroundColor(1308622847);
                return true;
            } else if (action != 6) {
                return true;
            }
        }
        setBackgroundColor(0);
        return true;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        ClipData clipData;
        if (motionEvent.getActionMasked() == 0 && (clipData = this.mCurrentClip) != null) {
            startDragAndDrop(clipData, new View.DragShadowBuilder(this), (Object) null, 256);
        }
        return super.onTouchEvent(motionEvent);
    }
}
