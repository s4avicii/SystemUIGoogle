package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.setupdesign.DividerItemDecoration;
import com.google.android.setupdesign.R$styleable;
import java.util.Objects;

public class HeaderRecyclerView extends RecyclerView {
    public View header;
    public int headerRes;
    public boolean shouldHandleActionUp = false;

    public static class HeaderAdapter<CVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public final RecyclerView.Adapter<CVH> adapter;
        public View header;
        public final C21551 observer;

        public final int getItemCount() {
            int itemCount = this.adapter.getItemCount();
            if (this.header != null) {
                return itemCount + 1;
            }
            return itemCount;
        }

        public final long getItemId(int i) {
            if (this.header != null) {
                i--;
            }
            if (i < 0) {
                return Long.MAX_VALUE;
            }
            return this.adapter.getItemId(i);
        }

        public final int getItemViewType(int i) {
            if (this.header != null) {
                i--;
            }
            if (i < 0) {
                return Integer.MAX_VALUE;
            }
            return this.adapter.getItemViewType(i);
        }

        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            View view = this.header;
            if (view != null) {
                i--;
            }
            if (!(viewHolder instanceof HeaderViewHolder)) {
                this.adapter.onBindViewHolder(viewHolder, i);
            } else if (view != null) {
                if (view.getParent() != null) {
                    ((ViewGroup) this.header.getParent()).removeView(this.header);
                }
                ((FrameLayout) viewHolder.itemView).addView(this.header);
            } else {
                throw new IllegalStateException("HeaderViewHolder cannot find mHeader");
            }
        }

        public HeaderAdapter(RecyclerView.Adapter<CVH> adapter2) {
            C21551 r0 = new RecyclerView.AdapterDataObserver() {
                public final void onChanged() {
                    HeaderAdapter.this.notifyDataSetChanged();
                }

                public final void onItemRangeChanged(int i, int i2) {
                    HeaderAdapter headerAdapter = HeaderAdapter.this;
                    if (headerAdapter.header != null) {
                        i++;
                    }
                    RecyclerView.AdapterDataObservable adapterDataObservable = headerAdapter.mObservable;
                    Objects.requireNonNull(adapterDataObservable);
                    adapterDataObservable.notifyItemRangeChanged(i, i2, (Object) null);
                }

                public final void onItemRangeInserted(int i, int i2) {
                    HeaderAdapter headerAdapter = HeaderAdapter.this;
                    if (headerAdapter.header != null) {
                        i++;
                    }
                    headerAdapter.notifyItemRangeInserted(i, i2);
                }

                public final void onItemRangeMoved(int i, int i2) {
                    if (HeaderAdapter.this.header != null) {
                        i++;
                        i2++;
                    }
                    for (int i3 = 0; i3 < 1; i3++) {
                        HeaderAdapter.this.notifyItemMoved(i + i3, i2 + i3);
                    }
                }

                public final void onItemRangeRemoved(int i, int i2) {
                    HeaderAdapter headerAdapter = HeaderAdapter.this;
                    if (headerAdapter.header != null) {
                        i++;
                    }
                    headerAdapter.notifyItemRangeRemoved(i, i2);
                }
            };
            this.observer = r0;
            this.adapter = adapter2;
            adapter2.registerAdapterDataObserver(r0);
            setHasStableIds(adapter2.mHasStableIds);
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            if (i != Integer.MAX_VALUE) {
                return this.adapter.onCreateViewHolder(recyclerView, i);
            }
            FrameLayout frameLayout = new FrameLayout(recyclerView.getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
            return new HeaderViewHolder(frameLayout);
        }
    }

    public HeaderRecyclerView(Context context) {
        super(context);
        init((AttributeSet) null, 0);
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View findFocus;
        boolean z = false;
        if (this.shouldHandleActionUp && keyEvent.getAction() == 1) {
            this.shouldHandleActionUp = false;
            z = true;
        } else if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 19) {
                if (keyCode == 20 && (findFocus = findFocus()) != null) {
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    findFocus.getLocationInWindow(iArr);
                    getLocationInWindow(iArr2);
                    int measuredHeight = (findFocus.getMeasuredHeight() + iArr[1]) - (getMeasuredHeight() + iArr2[1]);
                    if (measuredHeight > 0) {
                        smoothScrollBy$1(0, Math.min((int) (((float) getMeasuredHeight()) * 0.7f), measuredHeight), false);
                    }
                }
                this.shouldHandleActionUp = z;
            } else {
                View findFocus2 = findFocus();
                if (findFocus2 != null) {
                    int[] iArr3 = new int[2];
                    int[] iArr4 = new int[2];
                    findFocus2.getLocationInWindow(iArr3);
                    getLocationInWindow(iArr4);
                    int i = iArr3[1] - iArr4[1];
                    if (i < 0) {
                        smoothScrollBy$1(0, Math.max((int) (((float) getMeasuredHeight()) * -0.7f), i), false);
                    }
                }
                this.shouldHandleActionUp = z;
            }
            z = true;
            this.shouldHandleActionUp = z;
        }
        if (z) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void setAdapter(RecyclerView.Adapter adapter) {
        if (!(this.header == null || adapter == null)) {
            HeaderAdapter headerAdapter = new HeaderAdapter(adapter);
            headerAdapter.header = this.header;
            adapter = headerAdapter;
        }
        super.setAdapter(adapter);
    }

    public final void init(AttributeSet attributeSet, int i) {
        if (!isInEditMode()) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudHeaderRecyclerView, i, 0);
            this.headerRes = obtainStyledAttributes.getResourceId(0, 0);
            obtainStyledAttributes.recycle();
        }
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int i;
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.header != null) {
            i = 1;
        } else {
            i = 0;
        }
        accessibilityEvent.setItemCount(accessibilityEvent.getItemCount() - i);
        accessibilityEvent.setFromIndex(Math.max(accessibilityEvent.getFromIndex() - i, 0));
        accessibilityEvent.setToIndex(Math.max(accessibilityEvent.getToIndex() - i, 0));
    }

    public final void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        if (layoutManager != null && this.header == null && this.headerRes != 0) {
            this.header = LayoutInflater.from(getContext()).inflate(this.headerRes, this, false);
        }
    }

    public HeaderRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public HeaderRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder implements DividerItemDecoration.DividedViewHolder {
        public final boolean isDividerAllowedAbove() {
            return false;
        }

        public final boolean isDividerAllowedBelow() {
            return false;
        }

        public HeaderViewHolder(FrameLayout frameLayout) {
            super(frameLayout);
        }
    }
}
