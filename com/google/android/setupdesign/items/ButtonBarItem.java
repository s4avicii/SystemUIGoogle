package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupdesign.items.ItemInflater;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ButtonBarItem extends AbstractItem implements ItemInflater.ItemParent {
    public final ArrayList<ButtonItem> buttons = new ArrayList<>();
    public boolean visible = true;

    public ButtonBarItem() {
    }

    public final int getLayoutResource() {
        return C1777R.layout.sud_items_button_bar;
    }

    public final boolean isEnabled() {
        return false;
    }

    public final void onBindView(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        linearLayout.removeAllViews();
        Iterator<ButtonItem> it = this.buttons.iterator();
        while (it.hasNext()) {
            ButtonItem next = it.next();
            Objects.requireNonNull(next);
            Button button = next.button;
            if (button == null) {
                Context context = linearLayout.getContext();
                if (next.theme != 0) {
                    context = new ContextThemeWrapper(context, next.theme);
                }
                Button button2 = (Button) LayoutInflater.from(context).inflate(C1777R.layout.sud_button, (ViewGroup) null, false);
                next.button = button2;
                button2.setOnClickListener(next);
            } else if (button.getParent() instanceof ViewGroup) {
                ((ViewGroup) next.button.getParent()).removeView(next.button);
            }
            next.button.setEnabled(next.enabled);
            next.button.setText(next.text);
            next.button.setId(next.f137id);
            linearLayout.addView(next.button);
        }
        view.setId(this.f137id);
    }

    public final void addChild(ItemHierarchy itemHierarchy) {
        if (itemHierarchy instanceof ButtonItem) {
            this.buttons.add((ButtonItem) itemHierarchy);
            return;
        }
        throw new UnsupportedOperationException("Cannot add non-button item to Button Bar");
    }

    public ButtonBarItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final int getCount() {
        return this.visible ? 1 : 0;
    }
}
