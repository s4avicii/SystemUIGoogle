package com.android.systemui.accessibility.floatingmenu;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.android.p012wm.shell.C1777R;
import java.lang.ref.WeakReference;

public final class ItemDelegateCompat extends RecyclerViewAccessibilityDelegate.ItemDelegate {
    public final WeakReference<AccessibilityFloatingMenuView> mMenuViewRef;

    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (this.mMenuViewRef.get() == null) {
            return super.performAccessibilityAction(view, i, bundle);
        }
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.mMenuViewRef.get();
        accessibilityFloatingMenuView.fadeIn();
        Rect availableBounds = accessibilityFloatingMenuView.getAvailableBounds();
        if (i == C1777R.C1779id.action_move_top_left) {
            accessibilityFloatingMenuView.setShapeType(0);
            accessibilityFloatingMenuView.snapToLocation(availableBounds.left, availableBounds.top);
            return true;
        } else if (i == C1777R.C1779id.action_move_top_right) {
            accessibilityFloatingMenuView.setShapeType(0);
            accessibilityFloatingMenuView.snapToLocation(availableBounds.right, availableBounds.top);
            return true;
        } else if (i == C1777R.C1779id.action_move_bottom_left) {
            accessibilityFloatingMenuView.setShapeType(0);
            accessibilityFloatingMenuView.snapToLocation(availableBounds.left, availableBounds.bottom);
            return true;
        } else if (i == C1777R.C1779id.action_move_bottom_right) {
            accessibilityFloatingMenuView.setShapeType(0);
            accessibilityFloatingMenuView.snapToLocation(availableBounds.right, availableBounds.bottom);
            return true;
        } else if (i == C1777R.C1779id.action_move_to_edge_and_hide) {
            accessibilityFloatingMenuView.setShapeType(1);
            return true;
        } else if (i != C1777R.C1779id.action_move_out_edge_and_show) {
            return super.performAccessibilityAction(view, i, bundle);
        } else {
            accessibilityFloatingMenuView.setShapeType(0);
            return true;
        }
    }

    public ItemDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate, AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        super(recyclerViewAccessibilityDelegate);
        this.mMenuViewRef = new WeakReference<>(accessibilityFloatingMenuView);
    }

    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        boolean z;
        int i;
        int i2;
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        if (this.mMenuViewRef.get() != null) {
            AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.mMenuViewRef.get();
            Resources resources = accessibilityFloatingMenuView.getResources();
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat((int) C1777R.C1779id.action_move_top_left, resources.getString(C1777R.string.accessibility_floating_button_action_move_top_left)));
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat((int) C1777R.C1779id.action_move_top_right, resources.getString(C1777R.string.accessibility_floating_button_action_move_top_right)));
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat((int) C1777R.C1779id.action_move_bottom_left, resources.getString(C1777R.string.accessibility_floating_button_action_move_bottom_left)));
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat((int) C1777R.C1779id.action_move_bottom_right, resources.getString(C1777R.string.accessibility_floating_button_action_move_bottom_right)));
            int i3 = accessibilityFloatingMenuView.mShapeType;
            boolean z2 = true;
            if (i3 == 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                i = C1777R.C1779id.action_move_to_edge_and_hide;
            } else {
                i = C1777R.C1779id.action_move_out_edge_and_show;
            }
            if (i3 != 0) {
                z2 = false;
            }
            if (z2) {
                i2 = C1777R.string.f96x82520f4a;
            } else {
                i2 = C1777R.string.accessibility_floating_button_action_move_out_edge_and_show;
            }
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i, resources.getString(i2)));
        }
    }
}
