package com.google.android.material.textfield;

import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

public final class PasswordToggleEndIconDelegate extends EndIconDelegate {
    public final C21172 onEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener() {
        public final void onEditTextAttached(TextInputLayout textInputLayout) {
            Objects.requireNonNull(textInputLayout);
            EditText editText = textInputLayout.editText;
            textInputLayout.setEndIconVisible(true);
            CheckableImageButton checkableImageButton = textInputLayout.endIconView;
            Objects.requireNonNull(checkableImageButton);
            if (!checkableImageButton.checkable) {
                checkableImageButton.checkable = true;
                checkableImageButton.sendAccessibilityEvent(0);
            }
            PasswordToggleEndIconDelegate passwordToggleEndIconDelegate = PasswordToggleEndIconDelegate.this;
            passwordToggleEndIconDelegate.endIconView.setChecked(!PasswordToggleEndIconDelegate.access$000(passwordToggleEndIconDelegate));
            editText.removeTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
            editText.addTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
        }
    };
    public final C21183 onEndIconChangedListener = new TextInputLayout.OnEndIconChangedListener() {
        public final void onEndIconChanged(TextInputLayout textInputLayout, int i) {
            Objects.requireNonNull(textInputLayout);
            final EditText editText = textInputLayout.editText;
            if (editText != null && i == 1) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                editText.post(new Runnable() {
                    public final void run() {
                        editText.removeTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
                    }
                });
            }
        }
    };
    public final C21161 textWatcher = new TextWatcherAdapter() {
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            PasswordToggleEndIconDelegate passwordToggleEndIconDelegate = PasswordToggleEndIconDelegate.this;
            passwordToggleEndIconDelegate.endIconView.setChecked(!PasswordToggleEndIconDelegate.access$000(passwordToggleEndIconDelegate));
        }
    };

    public final void initialize() {
        boolean z;
        TextInputLayout textInputLayout = this.textInputLayout;
        int i = this.customEndIcon;
        if (i == 0) {
            i = C1777R.C1778drawable.design_password_eye;
        }
        textInputLayout.setEndIconDrawable(i);
        TextInputLayout textInputLayout2 = this.textInputLayout;
        textInputLayout2.setEndIconContentDescription(textInputLayout2.getResources().getText(C1777R.string.password_toggle_content_description));
        TextInputLayout textInputLayout3 = this.textInputLayout;
        C21204 r1 = new View.OnClickListener() {
            public final void onClick(View view) {
                TextInputLayout textInputLayout = PasswordToggleEndIconDelegate.this.textInputLayout;
                Objects.requireNonNull(textInputLayout);
                EditText editText = textInputLayout.editText;
                if (editText != null) {
                    int selectionEnd = editText.getSelectionEnd();
                    if (PasswordToggleEndIconDelegate.access$000(PasswordToggleEndIconDelegate.this)) {
                        editText.setTransformationMethod((TransformationMethod) null);
                    } else {
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                    if (selectionEnd >= 0) {
                        editText.setSelection(selectionEnd);
                    }
                    TextInputLayout textInputLayout2 = PasswordToggleEndIconDelegate.this.textInputLayout;
                    Objects.requireNonNull(textInputLayout2);
                    textInputLayout2.refreshIconDrawableState(textInputLayout2.endIconView, textInputLayout2.endIconTintList);
                }
            }
        };
        Objects.requireNonNull(textInputLayout3);
        CheckableImageButton checkableImageButton = textInputLayout3.endIconView;
        View.OnLongClickListener onLongClickListener = textInputLayout3.endIconOnLongClickListener;
        checkableImageButton.setOnClickListener(r1);
        TextInputLayout.setIconClickable(checkableImageButton, onLongClickListener);
        TextInputLayout textInputLayout4 = this.textInputLayout;
        C21172 r12 = this.onEditTextAttachedListener;
        Objects.requireNonNull(textInputLayout4);
        textInputLayout4.editTextAttachedListeners.add(r12);
        if (textInputLayout4.editText != null) {
            r12.onEditTextAttached(textInputLayout4);
        }
        TextInputLayout textInputLayout5 = this.textInputLayout;
        C21183 r13 = this.onEndIconChangedListener;
        Objects.requireNonNull(textInputLayout5);
        textInputLayout5.endIconChangedListeners.add(r13);
        TextInputLayout textInputLayout6 = this.textInputLayout;
        Objects.requireNonNull(textInputLayout6);
        EditText editText = textInputLayout6.editText;
        if (editText == null || !(editText.getInputType() == 16 || editText.getInputType() == 128 || editText.getInputType() == 144 || editText.getInputType() == 224)) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public PasswordToggleEndIconDelegate(TextInputLayout textInputLayout, int i) {
        super(textInputLayout, i);
    }

    public static boolean access$000(PasswordToggleEndIconDelegate passwordToggleEndIconDelegate) {
        Objects.requireNonNull(passwordToggleEndIconDelegate);
        TextInputLayout textInputLayout = passwordToggleEndIconDelegate.textInputLayout;
        Objects.requireNonNull(textInputLayout);
        EditText editText = textInputLayout.editText;
        if (editText == null || !(editText.getTransformationMethod() instanceof PasswordTransformationMethod)) {
            return false;
        }
        return true;
    }
}
