package androidx.slice.widget;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.slice.SliceItem;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class RemoteInputView extends LinearLayout implements View.OnClickListener, TextWatcher {
    public static final Object VIEW_TAG = new Object();
    public SliceItem mAction;
    public RemoteEditText mEditText;
    public ProgressBar mProgressBar;
    public RemoteInput mRemoteInput;
    public RemoteInput[] mRemoteInputs;
    public boolean mResetting;
    public ImageButton mSendButton;

    public static class RemoteEditText extends EditText {
        public final Drawable mBackground = getBackground();
        public RemoteInputView mRemoteInputView;
        public boolean mShowImeOnInputConnection;

        public final boolean onKeyDown(int i, KeyEvent keyEvent) {
            if (i == 4) {
                return true;
            }
            return super.onKeyDown(i, keyEvent);
        }

        public final boolean onKeyUp(int i, KeyEvent keyEvent) {
            if (i != 4) {
                return super.onKeyUp(i, keyEvent);
            }
            defocusIfNeeded();
            return true;
        }

        public final void defocusIfNeeded() {
            if (this.mRemoteInputView != null || isTemporarilyDetached()) {
                isTemporarilyDetached();
            } else if (isFocusable() && isEnabled()) {
                setInnerFocusable(false);
                RemoteInputView remoteInputView = this.mRemoteInputView;
                if (remoteInputView != null) {
                    Objects.requireNonNull(remoteInputView);
                    remoteInputView.setVisibility(4);
                }
                this.mShowImeOnInputConnection = false;
            }
        }

        public RemoteEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final void getFocusedRect(Rect rect) {
            super.getFocusedRect(rect);
            rect.top = getScrollY();
            rect.bottom = (getBottom() - getTop()) + getScrollY();
        }

        public final void onCommitCompletion(CompletionInfo completionInfo) {
            clearComposingText();
            setText(completionInfo.getText());
            setSelection(getText().length());
        }

        public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.mShowImeOnInputConnection && onCreateInputConnection != null) {
                Object obj = ContextCompat.sLock;
                final InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
                if (inputMethodManager != null) {
                    post(new Runnable() {
                        public final void run() {
                            inputMethodManager.viewClicked(RemoteEditText.this);
                            inputMethodManager.showSoftInput(RemoteEditText.this, 0);
                        }
                    });
                }
            }
            return onCreateInputConnection;
        }

        public final void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            if (!z) {
                defocusIfNeeded();
            }
        }

        public final void onVisibilityChanged(View view, int i) {
            super.onVisibilityChanged(view, i);
            if (!isShown()) {
                defocusIfNeeded();
            }
        }

        public final void setInnerFocusable(boolean z) {
            setFocusableInTouchMode(z);
            setFocusable(z);
            setCursorVisible(z);
            if (z) {
                requestFocus();
                setBackground(this.mBackground);
                return;
            }
            setBackground((Drawable) null);
        }

        public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
            super.setCustomSelectionActionModeCallback(callback);
        }
    }

    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void reset() {
        this.mResetting = true;
        this.mEditText.getText().clear();
        this.mEditText.setEnabled(true);
        this.mSendButton.setVisibility(0);
        this.mProgressBar.setVisibility(4);
        updateSendButton();
        setVisibility(4);
        this.mResetting = false;
    }

    public final void onClick(View view) {
        if (view == this.mSendButton) {
            sendRemoteInput();
        }
    }

    public final boolean onRequestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (!this.mResetting || view != this.mEditText) {
            return super.onRequestSendAccessibilityEvent(view, accessibilityEvent);
        }
        return false;
    }

    public final void sendRemoteInput() {
        Bundle bundle = new Bundle();
        bundle.putString(this.mRemoteInput.getResultKey(), this.mEditText.getText().toString());
        Intent addFlags = new Intent().addFlags(268435456);
        RemoteInput.addResultsToIntent(this.mRemoteInputs, addFlags, bundle);
        this.mEditText.setEnabled(false);
        this.mSendButton.setVisibility(4);
        this.mProgressBar.setVisibility(0);
        this.mEditText.mShowImeOnInputConnection = false;
        try {
            SliceItem sliceItem = this.mAction;
            Context context = getContext();
            Objects.requireNonNull(sliceItem);
            sliceItem.fireActionInternal(context, addFlags);
            reset();
        } catch (PendingIntent.CanceledException e) {
            Log.i("RemoteInput", "Unable to send remote input result", e);
            Toast.makeText(getContext(), "Failure sending pending intent for inline reply :(", 0).show();
            reset();
        }
    }

    public final void updateSendButton() {
        boolean z;
        ImageButton imageButton = this.mSendButton;
        if (this.mEditText.getText().length() != 0) {
            z = true;
        } else {
            z = false;
        }
        imageButton.setEnabled(z);
    }

    public final void dispatchFinishTemporaryDetach() {
        if (isAttachedToWindow()) {
            RemoteEditText remoteEditText = this.mEditText;
            attachViewToParent(remoteEditText, 0, remoteEditText.getLayoutParams());
        } else {
            removeDetachedView(this.mEditText, false);
        }
        super.dispatchFinishTemporaryDetach();
    }

    public final void dispatchStartTemporaryDetach() {
        super.dispatchStartTemporaryDetach();
        detachViewFromParent(this.mEditText);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mProgressBar = (ProgressBar) findViewById(C1777R.C1779id.remote_input_progress);
        ImageButton imageButton = (ImageButton) findViewById(C1777R.C1779id.remote_input_send);
        this.mSendButton = imageButton;
        imageButton.setOnClickListener(this);
        RemoteEditText remoteEditText = (RemoteEditText) getChildAt(0);
        this.mEditText = remoteEditText;
        remoteEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            /* JADX WARNING: Removed duplicated region for block: B:31:0x0046  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final boolean onEditorAction(android.widget.TextView r4, int r5, android.view.KeyEvent r6) {
                /*
                    r3 = this;
                    r4 = 1
                    r0 = 0
                    if (r6 != 0) goto L_0x000f
                    r1 = 6
                    if (r5 == r1) goto L_0x000d
                    r1 = 5
                    if (r5 == r1) goto L_0x000d
                    r1 = 4
                    if (r5 != r1) goto L_0x000f
                L_0x000d:
                    r5 = r4
                    goto L_0x0010
                L_0x000f:
                    r5 = r0
                L_0x0010:
                    if (r6 == 0) goto L_0x0035
                    int r1 = r6.getKeyCode()
                    java.lang.Object r2 = androidx.slice.widget.RemoteInputView.VIEW_TAG
                    r2 = 23
                    if (r1 == r2) goto L_0x002a
                    r2 = 62
                    if (r1 == r2) goto L_0x002a
                    r2 = 66
                    if (r1 == r2) goto L_0x002a
                    r2 = 160(0xa0, float:2.24E-43)
                    if (r1 == r2) goto L_0x002a
                    r1 = r0
                    goto L_0x002b
                L_0x002a:
                    r1 = r4
                L_0x002b:
                    if (r1 == 0) goto L_0x0035
                    int r6 = r6.getAction()
                    if (r6 != 0) goto L_0x0035
                    r6 = r4
                    goto L_0x0036
                L_0x0035:
                    r6 = r0
                L_0x0036:
                    if (r5 != 0) goto L_0x003c
                    if (r6 == 0) goto L_0x003b
                    goto L_0x003c
                L_0x003b:
                    return r0
                L_0x003c:
                    androidx.slice.widget.RemoteInputView r5 = androidx.slice.widget.RemoteInputView.this
                    androidx.slice.widget.RemoteInputView$RemoteEditText r5 = r5.mEditText
                    int r5 = r5.length()
                    if (r5 <= 0) goto L_0x004b
                    androidx.slice.widget.RemoteInputView r3 = androidx.slice.widget.RemoteInputView.this
                    r3.sendRemoteInput()
                L_0x004b:
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.RemoteInputView.C03611.onEditorAction(android.widget.TextView, int, android.view.KeyEvent):boolean");
            }
        });
        this.mEditText.addTextChangedListener(this);
        this.mEditText.setInnerFocusable(false);
        this.mEditText.mRemoteInputView = this;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public final void afterTextChanged(Editable editable) {
        updateSendButton();
    }

    public RemoteInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
