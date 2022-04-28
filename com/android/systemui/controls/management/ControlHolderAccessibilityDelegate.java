package com.android.systemui.controls.management;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.management.ControlsModel;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: ControlAdapter.kt */
public final class ControlHolderAccessibilityDelegate extends AccessibilityDelegateCompat {
    public boolean isFavorite;
    public final ControlsModel.MoveHelper moveHelper;
    public final Function0<Integer> positionRetriever;
    public final Function1<Boolean, CharSequence> stateRetriever;

    public ControlHolderAccessibilityDelegate(Function1<? super Boolean, ? extends CharSequence> function1, Function0<Integer> function0, ControlsModel.MoveHelper moveHelper2) {
        this.stateRetriever = function1;
        this.positionRetriever = function0;
        this.moveHelper = moveHelper2;
    }

    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        String str;
        boolean z;
        boolean z2;
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.mInfo.setContextClickable(false);
        if (this.isFavorite) {
            str = view.getContext().getString(C1777R.string.accessibility_control_change_unfavorite);
        } else {
            str = view.getContext().getString(C1777R.string.accessibility_control_change_favorite);
        }
        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, str));
        ControlsModel.MoveHelper moveHelper2 = this.moveHelper;
        if (moveHelper2 == null) {
            z = false;
        } else {
            z = moveHelper2.canMoveBefore(this.positionRetriever.invoke().intValue());
        }
        if (z) {
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat((int) C1777R.C1779id.accessibility_action_controls_move_before, view.getContext().getString(C1777R.string.accessibility_control_move, new Object[]{Integer.valueOf((this.positionRetriever.invoke().intValue() + 1) - 1)})));
            accessibilityNodeInfoCompat.mInfo.setContextClickable(true);
        }
        ControlsModel.MoveHelper moveHelper3 = this.moveHelper;
        if (moveHelper3 == null) {
            z2 = false;
        } else {
            z2 = moveHelper3.canMoveAfter(this.positionRetriever.invoke().intValue());
        }
        if (z2) {
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat((int) C1777R.C1779id.accessibility_action_controls_move_after, view.getContext().getString(C1777R.string.accessibility_control_move, new Object[]{Integer.valueOf(this.positionRetriever.invoke().intValue() + 1 + 1)})));
            accessibilityNodeInfoCompat.mInfo.setContextClickable(true);
        }
        accessibilityNodeInfoCompat.mInfo.setStateDescription(this.stateRetriever.invoke(Boolean.valueOf(this.isFavorite)));
        accessibilityNodeInfoCompat.setCollectionItemInfo((AccessibilityNodeInfoCompat.CollectionItemInfoCompat) null);
        Class<Switch> cls = Switch.class;
        accessibilityNodeInfoCompat.setClassName("android.widget.Switch");
    }

    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        if (i == C1777R.C1779id.accessibility_action_controls_move_before) {
            ControlsModel.MoveHelper moveHelper2 = this.moveHelper;
            if (moveHelper2 == null) {
                return true;
            }
            moveHelper2.moveBefore(this.positionRetriever.invoke().intValue());
            return true;
        } else if (i != C1777R.C1779id.accessibility_action_controls_move_after) {
            return false;
        } else {
            ControlsModel.MoveHelper moveHelper3 = this.moveHelper;
            if (moveHelper3 == null) {
                return true;
            }
            moveHelper3.moveAfter(this.positionRetriever.invoke().intValue());
            return true;
        }
    }
}
