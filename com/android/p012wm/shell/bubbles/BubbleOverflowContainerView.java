package com.android.p012wm.shell.bubbles;

import android.app.PendingIntent;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleData;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleOverflowContainerView */
public class BubbleOverflowContainerView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BubbleOverflowAdapter mAdapter;
    public BubbleController mController;
    public final C18021 mDataListener;
    public LinearLayout mEmptyState;
    public ImageView mEmptyStateImage;
    public TextView mEmptyStateSubtitle;
    public TextView mEmptyStateTitle;
    public BubbleOverflowContainerView$$ExternalSyntheticLambda0 mKeyListener;
    public ArrayList mOverflowBubbles;
    public RecyclerView mRecyclerView;

    public BubbleOverflowContainerView(Context context) {
        this(context, (AttributeSet) null);
    }

    /* renamed from: com.android.wm.shell.bubbles.BubbleOverflowContainerView$OverflowGridLayoutManager */
    public class OverflowGridLayoutManager extends GridLayoutManager {
        public final int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
            int itemCount = state.getItemCount();
            int columnCountForAccessibility = super.getColumnCountForAccessibility(recycler, state);
            if (itemCount < columnCountForAccessibility) {
                return itemCount;
            }
            return columnCountForAccessibility;
        }

        public OverflowGridLayoutManager(int i) {
            super(i);
        }
    }

    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void updateEmptyStateVisibility() {
        int i;
        LinearLayout linearLayout = this.mEmptyState;
        int i2 = 0;
        if (this.mOverflowBubbles.isEmpty()) {
            i = 0;
        } else {
            i = 8;
        }
        linearLayout.setVisibility(i);
        RecyclerView recyclerView = this.mRecyclerView;
        if (this.mOverflowBubbles.isEmpty()) {
            i2 = 8;
        }
        recyclerView.setVisibility(i2);
    }

    public final void updateFontSize() {
        float dimensionPixelSize = (float) this.mContext.getResources().getDimensionPixelSize(17105569);
        this.mEmptyStateTitle.setTextSize(0, dimensionPixelSize);
        this.mEmptyStateSubtitle.setTextSize(0, dimensionPixelSize);
    }

    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        BubbleController bubbleController = this.mController;
        if (bubbleController != null) {
            bubbleController.updateWindowFlagsForBackpress(true);
        }
        setOnKeyListener(this.mKeyListener);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BubbleController bubbleController = this.mController;
        if (bubbleController != null) {
            bubbleController.updateWindowFlagsForBackpress(false);
        }
        setOnKeyListener((View.OnKeyListener) null);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mRecyclerView = (RecyclerView) findViewById(C1777R.C1779id.bubble_overflow_recycler);
        this.mEmptyState = (LinearLayout) findViewById(C1777R.C1779id.bubble_overflow_empty_state);
        this.mEmptyStateTitle = (TextView) findViewById(C1777R.C1779id.bubble_overflow_empty_title);
        this.mEmptyStateSubtitle = (TextView) findViewById(C1777R.C1779id.bubble_overflow_empty_subtitle);
        this.mEmptyStateImage = (ImageView) findViewById(C1777R.C1779id.bubble_overflow_empty_state_image);
    }

    public BubbleOverflowContainerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOverflowBubbles = new ArrayList();
        this.mKeyListener = new BubbleOverflowContainerView$$ExternalSyntheticLambda0(this);
        this.mDataListener = new BubbleData.Listener() {
            public final void applyUpdate(BubbleData.Update update) {
                Bubble bubble = update.removedOverflowBubble;
                if (bubble != null) {
                    BubbleExpandedView bubbleExpandedView = bubble.mExpandedView;
                    if (bubbleExpandedView != null) {
                        bubbleExpandedView.cleanUpExpandedState();
                        bubble.mExpandedView = null;
                    }
                    PendingIntent pendingIntent = bubble.mIntent;
                    if (pendingIntent != null) {
                        pendingIntent.unregisterCancelListener(bubble.mIntentCancelListener);
                    }
                    bubble.mIntentActive = false;
                    bubble.mIconView = null;
                    int indexOf = BubbleOverflowContainerView.this.mOverflowBubbles.indexOf(bubble);
                    BubbleOverflowContainerView.this.mOverflowBubbles.remove(bubble);
                    BubbleOverflowAdapter bubbleOverflowAdapter = BubbleOverflowContainerView.this.mAdapter;
                    Objects.requireNonNull(bubbleOverflowAdapter);
                    bubbleOverflowAdapter.mObservable.notifyItemRangeRemoved(indexOf, 1);
                }
                Bubble bubble2 = update.addedOverflowBubble;
                if (bubble2 != null) {
                    int indexOf2 = BubbleOverflowContainerView.this.mOverflowBubbles.indexOf(bubble2);
                    if (indexOf2 > 0) {
                        BubbleOverflowContainerView.this.mOverflowBubbles.remove(bubble2);
                        BubbleOverflowContainerView.this.mOverflowBubbles.add(0, bubble2);
                        BubbleOverflowContainerView.this.mAdapter.notifyItemMoved(indexOf2, 0);
                    } else {
                        BubbleOverflowContainerView.this.mOverflowBubbles.add(0, bubble2);
                        BubbleOverflowAdapter bubbleOverflowAdapter2 = BubbleOverflowContainerView.this.mAdapter;
                        Objects.requireNonNull(bubbleOverflowAdapter2);
                        bubbleOverflowAdapter2.mObservable.notifyItemRangeInserted(0, 1);
                    }
                }
                BubbleOverflowContainerView.this.updateEmptyStateVisibility();
            }
        };
        setFocusableInTouchMode(true);
    }
}
