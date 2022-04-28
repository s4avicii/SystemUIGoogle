package com.google.android.setupdesign.view;

import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

public interface TouchableMovementMethod {

    public static class TouchableLinkMovementMethod extends LinkMovementMethod implements TouchableMovementMethod {
        public MotionEvent lastEvent;
        public boolean lastEventResult = false;

        public final boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            boolean z;
            this.lastEvent = motionEvent;
            boolean onTouchEvent = super.onTouchEvent(textView, spannable, motionEvent);
            if (motionEvent.getAction() == 0) {
                if (Selection.getSelectionStart(spannable) != -1) {
                    z = true;
                } else {
                    z = false;
                }
                this.lastEventResult = z;
            } else {
                this.lastEventResult = onTouchEvent;
            }
            return onTouchEvent;
        }

        public final MotionEvent getLastTouchEvent() {
            return this.lastEvent;
        }

        public final boolean isLastTouchEventHandled() {
            return this.lastEventResult;
        }
    }

    MotionEvent getLastTouchEvent();

    boolean isLastTouchEventHandled();
}
