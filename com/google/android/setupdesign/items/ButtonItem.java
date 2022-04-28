package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupdesign.R$styleable;

public class ButtonItem extends AbstractItem implements View.OnClickListener {
    public Button button;
    public boolean enabled = true;
    public CharSequence text;
    public int theme = C1777R.style.SudButtonItem;

    public ButtonItem() {
    }

    public final int getCount() {
        return 0;
    }

    public final int getLayoutResource() {
        return 0;
    }

    public final void onClick(View view) {
    }

    public final void onBindView(View view) {
        throw new UnsupportedOperationException("Cannot bind to ButtonItem's view");
    }

    public ButtonItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudButtonItem);
        this.enabled = obtainStyledAttributes.getBoolean(1, true);
        this.text = obtainStyledAttributes.getText(3);
        this.theme = obtainStyledAttributes.getResourceId(0, C1777R.style.SudButtonItem);
        obtainStyledAttributes.recycle();
    }

    public final boolean isEnabled() {
        return this.enabled;
    }
}
