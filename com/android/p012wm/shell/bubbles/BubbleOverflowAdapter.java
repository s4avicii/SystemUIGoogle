package com.android.p012wm.shell.bubbles;

import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.ContrastColorUtil;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BadgedImageView;
import com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda13;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleOverflowAdapter */
/* compiled from: BubbleOverflowContainerView */
public final class BubbleOverflowAdapter extends RecyclerView.Adapter<ViewHolder> {
    public List<Bubble> mBubbles;
    public Context mContext;
    public BubblePositioner mPositioner;
    public Consumer<Bubble> mPromoteBubbleFromOverflow;

    /* renamed from: com.android.wm.shell.bubbles.BubbleOverflowAdapter$ViewHolder */
    /* compiled from: BubbleOverflowContainerView */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public BadgedImageView iconView;
        public TextView textView;

        public ViewHolder(LinearLayout linearLayout, BubblePositioner bubblePositioner) {
            super(linearLayout);
            BadgedImageView badgedImageView = (BadgedImageView) linearLayout.findViewById(C1777R.C1779id.bubble_view);
            this.iconView = badgedImageView;
            badgedImageView.initialize(bubblePositioner);
            this.textView = (TextView) linearLayout.findViewById(C1777R.C1779id.bubble_view_name);
        }
    }

    public final int getItemCount() {
        return this.mBubbles.size();
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        CharSequence charSequence;
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        Bubble bubble = this.mBubbles.get(i);
        viewHolder2.iconView.setRenderedBubble(bubble);
        viewHolder2.iconView.removeDotSuppressionFlag(BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE);
        viewHolder2.iconView.setOnClickListener(new BubbleOverflowAdapter$$ExternalSyntheticLambda0(this, bubble));
        String str = bubble.mTitle;
        if (str == null) {
            str = this.mContext.getResources().getString(C1777R.string.notification_bubble_title);
        }
        viewHolder2.iconView.setContentDescription(this.mContext.getResources().getString(C1777R.string.bubble_content_description_single, new Object[]{str, bubble.mAppName}));
        viewHolder2.iconView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, BubbleOverflowAdapter.this.mContext.getResources().getString(C1777R.string.bubble_accessibility_action_add_back)));
            }
        });
        ShortcutInfo shortcutInfo = bubble.mShortcutInfo;
        if (shortcutInfo != null) {
            charSequence = shortcutInfo.getLabel();
        } else {
            charSequence = bubble.mAppName;
        }
        viewHolder2.textView.setText(charSequence);
    }

    public BubbleOverflowAdapter(Context context, ArrayList arrayList, NavigationBar$$ExternalSyntheticLambda13 navigationBar$$ExternalSyntheticLambda13, BubblePositioner bubblePositioner) {
        this.mContext = context;
        this.mBubbles = arrayList;
        this.mPromoteBubbleFromOverflow = navigationBar$$ExternalSyntheticLambda13;
        this.mPositioner = bubblePositioner;
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.bubble_overflow_view, recyclerView, false);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16844002, 16842806});
        int ensureTextContrast = ContrastColorUtil.ensureTextContrast(obtainStyledAttributes.getColor(1, -16777216), obtainStyledAttributes.getColor(0, -1), true);
        obtainStyledAttributes.recycle();
        ((TextView) linearLayout.findViewById(C1777R.C1779id.bubble_view_name)).setTextColor(ensureTextContrast);
        return new ViewHolder(linearLayout, this.mPositioner);
    }
}
