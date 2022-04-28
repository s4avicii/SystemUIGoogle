package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupdesign.R$styleable;

public class SwitchItem extends Item implements CompoundButton.OnCheckedChangeListener {
    public boolean checked = false;

    public SwitchItem() {
    }

    public int getDefaultLayoutResource() {
        return C1777R.layout.sud_items_switch;
    }

    public SwitchItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudSwitchItem);
        this.checked = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }

    public void onBindView(View view) {
        super.onBindView(view);
        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(C1777R.C1779id.sud_items_switch);
        switchCompat.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        switchCompat.setChecked(this.checked);
        switchCompat.setOnCheckedChangeListener(this);
        switchCompat.setEnabled(this.enabled);
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.checked = z;
    }
}
