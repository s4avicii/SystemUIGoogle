package androidx.preference;

import android.os.Bundle;
import android.view.View;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import java.util.Objects;

@Deprecated
public final class PreferenceRecyclerViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
    public final RecyclerViewAccessibilityDelegate.ItemDelegate mDefaultItemDelegate = this.mItemDelegate;
    public final C03041 mItemDelegate = new AccessibilityDelegateCompat() {
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            PreferenceRecyclerViewAccessibilityDelegate.this.mDefaultItemDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            Objects.requireNonNull(PreferenceRecyclerViewAccessibilityDelegate.this.mRecyclerView);
            int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
            RecyclerView recyclerView = PreferenceRecyclerViewAccessibilityDelegate.this.mRecyclerView;
            Objects.requireNonNull(recyclerView);
            RecyclerView.Adapter adapter = recyclerView.mAdapter;
            if (adapter instanceof PreferenceGroupAdapter) {
                ((PreferenceGroupAdapter) adapter).getItem(childAdapterPosition);
            }
        }

        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return PreferenceRecyclerViewAccessibilityDelegate.this.mDefaultItemDelegate.performAccessibilityAction(view, i, bundle);
        }
    };
    public final RecyclerView mRecyclerView;

    public PreferenceRecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        super(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    public final AccessibilityDelegateCompat getItemDelegate() {
        return this.mItemDelegate;
    }
}
