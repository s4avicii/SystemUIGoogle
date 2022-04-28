package com.android.systemui.navigationbar.buttons;

import androidx.exifinterface.media.C0155xe8491b12;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Objects;

public final class ContextualButtonGroup extends ButtonDispatcher {
    public final ArrayList mButtonData = new ArrayList();

    public final void addButton(ContextualButton contextualButton) {
        contextualButton.setVisibility(4);
        contextualButton.mGroup = this;
        this.mButtonData.add(new ButtonData(contextualButton));
    }

    public final int getContextButtonIndex(int i) {
        for (int i2 = 0; i2 < this.mButtonData.size(); i2++) {
            ContextualButton contextualButton = ((ButtonData) this.mButtonData.get(i2)).button;
            Objects.requireNonNull(contextualButton);
            if (contextualButton.mId == i) {
                return i2;
            }
        }
        return -1;
    }

    public static final class ButtonData {
        public ContextualButton button;
        public boolean markedVisible = false;

        public ButtonData(ContextualButton contextualButton) {
            this.button = contextualButton;
        }
    }

    public ContextualButtonGroup() {
        super(C1777R.C1779id.menu_container);
    }

    public final int setButtonVisibility(int i, boolean z) {
        int contextButtonIndex = getContextButtonIndex(i);
        if (contextButtonIndex != -1) {
            setVisibility(4);
            ((ButtonData) this.mButtonData.get(contextButtonIndex)).markedVisible = z;
            boolean z2 = false;
            for (int size = this.mButtonData.size() - 1; size >= 0; size--) {
                ButtonData buttonData = (ButtonData) this.mButtonData.get(size);
                if (z2 || !buttonData.markedVisible) {
                    Objects.requireNonNull(buttonData);
                    buttonData.button.setVisibility(4);
                } else {
                    buttonData.button.setVisibility(0);
                    setVisibility(0);
                    z2 = true;
                }
            }
            return ((ButtonData) this.mButtonData.get(contextButtonIndex)).button.getVisibility();
        }
        throw new RuntimeException(C0155xe8491b12.m16m("Cannot find the button id of ", i, " in context group"));
    }
}
