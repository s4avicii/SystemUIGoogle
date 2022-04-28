package com.android.systemui.p006qs.customize;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSEditEvent;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.p006qs.customize.TileQueryHelper;
import com.android.systemui.p006qs.external.CustomTile;
import com.android.systemui.p006qs.tileimpl.QSIconViewImpl;
import com.android.systemui.p006qs.tileimpl.QSTileViewImpl;
import com.android.systemui.plugins.p005qs.QSTile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* renamed from: com.android.systemui.qs.customize.TileAdapter */
public final class TileAdapter extends RecyclerView.Adapter<Holder> implements TileQueryHelper.TileStateListener {
    public int mAccessibilityAction = 0;
    public final TileAdapterDelegate mAccessibilityDelegate;
    public int mAccessibilityFromIndex;
    public List<TileQueryHelper.TileInfo> mAllTiles;
    public final C10215 mCallbacks;
    public final Context mContext;
    public Holder mCurrentDrag;
    public List<String> mCurrentSpecs;
    public TileItemDecoration mDecoration;
    public int mEditIndex;
    public int mFocusIndex;
    public final Handler mHandler = new Handler();
    public final QSTileHost mHost;
    public final ItemTouchHelper mItemTouchHelper;
    public final MarginTileDecoration mMarginDecoration;
    public final int mMinNumTiles;
    public boolean mNeedsFocus;
    public int mNumColumns;
    public ArrayList mOtherTiles;
    public RecyclerView mRecyclerView;
    public final C10204 mSizeLookup;
    public int mTileDividerIndex;
    public final ArrayList mTiles = new ArrayList();
    public final UiEventLogger mUiEventLogger;

    /* renamed from: com.android.systemui.qs.customize.TileAdapter$Holder */
    public class Holder extends RecyclerView.ViewHolder {
        public QSTileViewImpl mTileView;

        public Holder(View view) {
            super(view);
            if (view instanceof FrameLayout) {
                QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) ((FrameLayout) view).getChildAt(0);
                this.mTileView = qSTileViewImpl;
                Objects.requireNonNull(qSTileViewImpl);
                qSTileViewImpl._icon.disableAnimation();
                this.mTileView.setTag(this);
                ViewCompat.setAccessibilityDelegate(this.mTileView, TileAdapter.this.mAccessibilityDelegate);
            }
        }

        public final void stopDrag() {
            this.itemView.animate().setDuration(100).scaleX(1.0f).scaleY(1.0f);
        }
    }

    /* renamed from: com.android.systemui.qs.customize.TileAdapter$TileItemDecoration */
    public class TileItemDecoration extends RecyclerView.ItemDecoration {
        public final Drawable mDrawable;

        public TileItemDecoration(Context context) {
            this.mDrawable = context.getDrawable(C1777R.C1778drawable.qs_customize_tile_decoration);
        }

        public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
            int childCount = recyclerView.getChildCount();
            int width = recyclerView.getWidth();
            int bottom = recyclerView.getBottom();
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
                if (childViewHolder != TileAdapter.this.mCurrentDrag) {
                    Objects.requireNonNull(childViewHolder);
                    if (childViewHolder.getBindingAdapterPosition() != 0 && (childViewHolder.getBindingAdapterPosition() >= TileAdapter.this.mEditIndex || (childAt instanceof TextView))) {
                        int top = childAt.getTop();
                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        this.mDrawable.setBounds(0, Math.round(childAt.getTranslationY()) + top, width, bottom);
                        this.mDrawable.draw(canvas);
                        return;
                    }
                }
            }
        }
    }

    public final void onDetachedFromRecyclerView() {
        this.mRecyclerView = null;
    }

    public final void updateDividerLocations() {
        this.mEditIndex = -1;
        this.mTileDividerIndex = this.mTiles.size();
        for (int i = 1; i < this.mTiles.size(); i++) {
            if (this.mTiles.get(i) == null) {
                if (this.mEditIndex == -1) {
                    this.mEditIndex = i;
                } else {
                    this.mTileDividerIndex = i;
                }
            }
        }
        int size = this.mTiles.size() - 1;
        int i2 = this.mTileDividerIndex;
        if (size == i2) {
            notifyItemChanged(i2);
        }
    }

    /* renamed from: com.android.systemui.qs.customize.TileAdapter$MarginTileDecoration */
    public static class MarginTileDecoration extends RecyclerView.ItemDecoration {
        public int mHalfMargin;

        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            Objects.requireNonNull(recyclerView);
            RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
            if (layoutManager != null) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                int i = layoutParams.mSpanIndex;
                if (view instanceof TextView) {
                    super.getItemOffsets(rect, view, recyclerView, state);
                } else if (i != 0 && i != gridLayoutManager.mSpanCount - 1) {
                    int i2 = this.mHalfMargin;
                    rect.left = i2;
                    rect.right = i2;
                } else if (recyclerView.isLayoutRtl()) {
                    if (i == 0) {
                        rect.left = this.mHalfMargin;
                        rect.right = 0;
                        return;
                    }
                    rect.left = 0;
                    rect.right = this.mHalfMargin;
                } else if (i == 0) {
                    rect.left = 0;
                    rect.right = this.mHalfMargin;
                } else {
                    rect.left = this.mHalfMargin;
                    rect.right = 0;
                }
            }
        }

        public MarginTileDecoration(int i) {
        }
    }

    public static String strip(TileQueryHelper.TileInfo tileInfo) {
        String str = tileInfo.spec;
        if (str.startsWith("custom(")) {
            return CustomTile.getComponentFromSpec(str).getPackageName();
        }
        return str;
    }

    public final int getItemCount() {
        return this.mTiles.size();
    }

    public final int getItemViewType(int i) {
        if (i == 0) {
            return 3;
        }
        if (this.mAccessibilityAction == 1 && i == this.mEditIndex - 1) {
            return 2;
        }
        if (i == this.mTileDividerIndex) {
            return 4;
        }
        if (this.mTiles.get(i) == null) {
            return 1;
        }
        return 0;
    }

    public final void move(int i, int i2, boolean z) {
        if (i2 != i) {
            ArrayList arrayList = this.mTiles;
            arrayList.add(i2, arrayList.remove(i));
            if (z) {
                notifyItemMoved(i, i2);
            }
            updateDividerLocations();
            int i3 = this.mEditIndex;
            if (i2 >= i3) {
                this.mUiEventLogger.log(QSEditEvent.QS_EDIT_REMOVE, 0, strip((TileQueryHelper.TileInfo) this.mTiles.get(i2)));
            } else if (i >= i3) {
                this.mUiEventLogger.log(QSEditEvent.QS_EDIT_ADD, 0, strip((TileQueryHelper.TileInfo) this.mTiles.get(i2)));
            } else {
                this.mUiEventLogger.log(QSEditEvent.QS_EDIT_MOVE, 0, strip((TileQueryHelper.TileInfo) this.mTiles.get(i2)));
            }
            saveSpecs(this.mHost);
        }
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        boolean z;
        boolean z2;
        int i2;
        boolean z3;
        String str;
        boolean z4;
        final Holder holder = (Holder) viewHolder;
        int i3 = holder.mItemViewType;
        int i4 = 4;
        boolean z5 = false;
        if (i3 == 3) {
            View view = holder.itemView;
            if (this.mAccessibilityAction == 0) {
                z5 = true;
            }
            view.setFocusable(z5);
            if (z5) {
                i4 = 1;
            }
            view.setImportantForAccessibility(i4);
            view.setFocusableInTouchMode(z5);
        } else if (i3 == 4) {
            View view2 = holder.itemView;
            if (this.mTileDividerIndex < this.mTiles.size() - 1) {
                i4 = 0;
            }
            view2.setVisibility(i4);
        } else if (i3 == 1) {
            Resources resources = this.mContext.getResources();
            if (this.mCurrentDrag == null) {
                str = resources.getString(C1777R.string.drag_to_add_tiles);
            } else {
                if (this.mCurrentSpecs.size() > this.mMinNumTiles) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (!z4) {
                    Holder holder2 = this.mCurrentDrag;
                    Objects.requireNonNull(holder2);
                    if (holder2.getBindingAdapterPosition() < this.mEditIndex) {
                        str = resources.getString(C1777R.string.drag_to_remove_disabled, new Object[]{Integer.valueOf(this.mMinNumTiles)});
                    }
                }
                str = resources.getString(C1777R.string.drag_to_remove_tiles);
            }
            ((TextView) holder.itemView.findViewById(16908310)).setText(str);
            View view3 = holder.itemView;
            if (this.mAccessibilityAction == 0) {
                z5 = true;
            }
            view3.setFocusable(z5);
            if (z5) {
                i4 = 1;
            }
            view3.setImportantForAccessibility(i4);
            view3.setFocusableInTouchMode(z5);
        } else if (i3 == 2) {
            holder.mTileView.setClickable(true);
            holder.mTileView.setFocusable(true);
            holder.mTileView.setFocusableInTouchMode(true);
            holder.mTileView.setVisibility(0);
            holder.mTileView.setImportantForAccessibility(1);
            holder.mTileView.setContentDescription(this.mContext.getString(C1777R.string.accessibility_qs_edit_tile_add_to_position, new Object[]{Integer.valueOf(i)}));
            holder.mTileView.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    TileAdapter.m214$$Nest$mselectPosition(TileAdapter.this, holder.getLayoutPosition());
                }
            });
            if (this.mNeedsFocus) {
                holder.mTileView.requestLayout();
                holder.mTileView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        Holder.this.mTileView.removeOnLayoutChangeListener(this);
                        Holder.this.mTileView.requestAccessibilityFocus();
                    }
                });
                this.mNeedsFocus = false;
                this.mFocusIndex = -1;
            }
        } else {
            TileQueryHelper.TileInfo tileInfo = (TileQueryHelper.TileInfo) this.mTiles.get(i);
            if (i <= 0 || i >= this.mEditIndex) {
                z = false;
            } else {
                z = true;
            }
            if (z && this.mAccessibilityAction == 1) {
                tileInfo.state.contentDescription = this.mContext.getString(C1777R.string.accessibility_qs_edit_tile_add_to_position, new Object[]{Integer.valueOf(i)});
            } else if (!z || this.mAccessibilityAction != 2) {
                QSTile.State state = tileInfo.state;
                state.contentDescription = state.label;
            } else {
                tileInfo.state.contentDescription = this.mContext.getString(C1777R.string.accessibility_qs_edit_tile_move_to_position, new Object[]{Integer.valueOf(i)});
            }
            tileInfo.state.expandedAccessibilityClassName = "";
            CustomizeTileView customizeTileView = (CustomizeTileView) holder.mTileView;
            Objects.requireNonNull(customizeTileView, "The holder must have a tileView");
            customizeTileView.handleStateChanged(tileInfo.state);
            if (i <= this.mEditIndex || tileInfo.isSystem) {
                z2 = false;
            } else {
                z2 = true;
            }
            customizeTileView.showAppLabel = z2;
            TextView secondaryLabel = customizeTileView.getSecondaryLabel();
            CharSequence text = customizeTileView.getSecondaryLabel().getText();
            if (!customizeTileView.showAppLabel || TextUtils.isEmpty(text)) {
                i2 = 8;
            } else {
                i2 = 0;
            }
            secondaryLabel.setVisibility(i2);
            if (i < this.mEditIndex || tileInfo.isSystem) {
                z3 = true;
            } else {
                z3 = false;
            }
            customizeTileView.showSideView = z3;
            if (!z3) {
                customizeTileView.getSideView().setVisibility(8);
            }
            holder.mTileView.setSelected(true);
            holder.mTileView.setImportantForAccessibility(1);
            holder.mTileView.setClickable(true);
            holder.mTileView.setOnClickListener((View.OnClickListener) null);
            holder.mTileView.setFocusable(true);
            holder.mTileView.setFocusableInTouchMode(true);
            if (this.mAccessibilityAction != 0) {
                holder.mTileView.setClickable(z);
                holder.mTileView.setFocusable(z);
                holder.mTileView.setFocusableInTouchMode(z);
                QSTileViewImpl qSTileViewImpl = holder.mTileView;
                if (z) {
                    i4 = 1;
                }
                qSTileViewImpl.setImportantForAccessibility(i4);
                if (z) {
                    holder.mTileView.setOnClickListener(new View.OnClickListener() {
                        public final void onClick(View view) {
                            int layoutPosition = holder.getLayoutPosition();
                            if (layoutPosition != -1) {
                                TileAdapter tileAdapter = TileAdapter.this;
                                if (tileAdapter.mAccessibilityAction != 0) {
                                    TileAdapter.m214$$Nest$mselectPosition(tileAdapter, layoutPosition);
                                }
                            }
                        }
                    });
                }
            }
            if (i == this.mFocusIndex && this.mNeedsFocus) {
                holder.mTileView.requestLayout();
                holder.mTileView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        Holder.this.mTileView.removeOnLayoutChangeListener(this);
                        Holder.this.mTileView.requestAccessibilityFocus();
                    }
                });
                this.mNeedsFocus = false;
                this.mFocusIndex = -1;
            }
        }
    }

    public final boolean onFailedToRecycleView(RecyclerView.ViewHolder viewHolder) {
        Holder holder = (Holder) viewHolder;
        holder.stopDrag();
        holder.itemView.clearAnimation();
        holder.itemView.setScaleX(1.0f);
        holder.itemView.setScaleY(1.0f);
        return true;
    }

    public final void recalcSpecs() {
        TileQueryHelper.TileInfo tileInfo;
        if (this.mCurrentSpecs != null && this.mAllTiles != null) {
            this.mOtherTiles = new ArrayList(this.mAllTiles);
            this.mTiles.clear();
            this.mTiles.add((Object) null);
            int i = 0;
            for (int i2 = 0; i2 < this.mCurrentSpecs.size(); i2++) {
                String str = this.mCurrentSpecs.get(i2);
                int i3 = 0;
                while (true) {
                    if (i3 >= this.mOtherTiles.size()) {
                        tileInfo = null;
                        break;
                    } else if (((TileQueryHelper.TileInfo) this.mOtherTiles.get(i3)).spec.equals(str)) {
                        tileInfo = (TileQueryHelper.TileInfo) this.mOtherTiles.remove(i3);
                        break;
                    } else {
                        i3++;
                    }
                }
                if (tileInfo != null) {
                    this.mTiles.add(tileInfo);
                }
            }
            this.mTiles.add((Object) null);
            while (i < this.mOtherTiles.size()) {
                TileQueryHelper.TileInfo tileInfo2 = (TileQueryHelper.TileInfo) this.mOtherTiles.get(i);
                if (tileInfo2.isSystem) {
                    this.mOtherTiles.remove(i);
                    this.mTiles.add(tileInfo2);
                    i--;
                }
                i++;
            }
            this.mTileDividerIndex = this.mTiles.size();
            this.mTiles.add((Object) null);
            this.mTiles.addAll(this.mOtherTiles);
            updateDividerLocations();
            notifyDataSetChanged();
        }
    }

    public final void saveSpecs(QSTileHost qSTileHost) {
        ArrayList arrayList = new ArrayList();
        this.mNeedsFocus = false;
        int i = 1;
        if (this.mAccessibilityAction == 1) {
            ArrayList arrayList2 = this.mTiles;
            int i2 = this.mEditIndex - 1;
            this.mEditIndex = i2;
            arrayList2.remove(i2);
            notifyDataSetChanged();
        }
        this.mAccessibilityAction = 0;
        while (i < this.mTiles.size() && this.mTiles.get(i) != null) {
            arrayList.add(((TileQueryHelper.TileInfo) this.mTiles.get(i)).spec);
            i++;
        }
        qSTileHost.changeTiles(this.mCurrentSpecs, arrayList);
        this.mCurrentSpecs = arrayList;
    }

    /* renamed from: -$$Nest$mselectPosition  reason: not valid java name */
    public static void m214$$Nest$mselectPosition(TileAdapter tileAdapter, int i) {
        Objects.requireNonNull(tileAdapter);
        if (tileAdapter.mAccessibilityAction == 1) {
            ArrayList arrayList = tileAdapter.mTiles;
            int i2 = tileAdapter.mEditIndex;
            tileAdapter.mEditIndex = i2 - 1;
            arrayList.remove(i2);
        }
        tileAdapter.mAccessibilityAction = 0;
        tileAdapter.move(tileAdapter.mAccessibilityFromIndex, i, false);
        tileAdapter.mFocusIndex = i;
        tileAdapter.mNeedsFocus = true;
        tileAdapter.notifyDataSetChanged();
    }

    public TileAdapter(Context context, QSTileHost qSTileHost, UiEventLogger uiEventLogger) {
        C10204 r1 = new GridLayoutManager.SpanSizeLookup() {
            public final int getSpanSize(int i) {
                int itemViewType = TileAdapter.this.getItemViewType(i);
                if (itemViewType == 1 || itemViewType == 4 || itemViewType == 3) {
                    return TileAdapter.this.mNumColumns;
                }
                return 1;
            }
        };
        this.mSizeLookup = r1;
        C10215 r2 = new ItemTouchHelper.Callback() {
            public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                ((Holder) viewHolder).stopDrag();
                super.clearView(recyclerView, viewHolder);
            }

            public final boolean isItemViewSwipeEnabled() {
                return false;
            }

            public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
                boolean z;
                if (i != 2) {
                    viewHolder = null;
                }
                Holder holder = TileAdapter.this.mCurrentDrag;
                if (viewHolder != holder) {
                    if (holder != null) {
                        int bindingAdapterPosition = holder.getBindingAdapterPosition();
                        if (bindingAdapterPosition != -1) {
                            TileQueryHelper.TileInfo tileInfo = (TileQueryHelper.TileInfo) TileAdapter.this.mTiles.get(bindingAdapterPosition);
                            TileAdapter tileAdapter = TileAdapter.this;
                            CustomizeTileView customizeTileView = (CustomizeTileView) tileAdapter.mCurrentDrag.mTileView;
                            int i2 = 0;
                            if (bindingAdapterPosition <= tileAdapter.mEditIndex || tileInfo.isSystem) {
                                z = false;
                            } else {
                                z = true;
                            }
                            Objects.requireNonNull(customizeTileView);
                            customizeTileView.showAppLabel = z;
                            TextView secondaryLabel = customizeTileView.getSecondaryLabel();
                            CharSequence text = customizeTileView.getSecondaryLabel().getText();
                            if (!customizeTileView.showAppLabel || TextUtils.isEmpty(text)) {
                                i2 = 8;
                            }
                            secondaryLabel.setVisibility(i2);
                            TileAdapter.this.mCurrentDrag.stopDrag();
                            TileAdapter.this.mCurrentDrag = null;
                        } else {
                            return;
                        }
                    }
                    if (viewHolder != null) {
                        Holder holder2 = (Holder) viewHolder;
                        TileAdapter.this.mCurrentDrag = holder2;
                        holder2.itemView.animate().setDuration(100).scaleX(1.2f).scaleY(1.2f);
                    }
                    TileAdapter.this.mHandler.post(new Runnable() {
                        public final void run() {
                            TileAdapter tileAdapter = TileAdapter.this;
                            tileAdapter.notifyItemChanged(tileAdapter.mEditIndex);
                        }
                    });
                }
            }

            public final void onSwiped() {
            }

            public final boolean canDropOver(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                boolean z;
                Objects.requireNonNull(viewHolder2);
                int bindingAdapterPosition = viewHolder2.getBindingAdapterPosition();
                if (!(bindingAdapterPosition == 0 || bindingAdapterPosition == -1)) {
                    TileAdapter tileAdapter = TileAdapter.this;
                    Objects.requireNonNull(tileAdapter);
                    if (tileAdapter.mCurrentSpecs.size() > tileAdapter.mMinNumTiles) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z) {
                        Objects.requireNonNull(viewHolder);
                        int bindingAdapterPosition2 = viewHolder.getBindingAdapterPosition();
                        int i = TileAdapter.this.mEditIndex;
                        if (bindingAdapterPosition2 < i) {
                            if (bindingAdapterPosition < i) {
                                return true;
                            }
                            return false;
                        }
                    }
                    if (bindingAdapterPosition <= TileAdapter.this.mEditIndex + 1) {
                        return true;
                    }
                }
                return false;
            }

            public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                Objects.requireNonNull(viewHolder);
                int i = viewHolder.mItemViewType;
                if (i == 1 || i == 3 || i == 4) {
                    return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                }
                return ItemTouchHelper.Callback.makeMovementFlags(15, 0);
            }

            public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                Objects.requireNonNull(viewHolder);
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                if (bindingAdapterPosition == 0 || bindingAdapterPosition == -1 || bindingAdapterPosition2 == 0 || bindingAdapterPosition2 == -1) {
                    return false;
                }
                TileAdapter tileAdapter = TileAdapter.this;
                Objects.requireNonNull(tileAdapter);
                tileAdapter.move(bindingAdapterPosition, bindingAdapterPosition2, true);
                return true;
            }
        };
        this.mCallbacks = r2;
        this.mContext = context;
        this.mHost = qSTileHost;
        this.mUiEventLogger = uiEventLogger;
        this.mItemTouchHelper = new ItemTouchHelper(r2);
        this.mDecoration = new TileItemDecoration(context);
        this.mMarginDecoration = new MarginTileDecoration(0);
        this.mMinNumTiles = context.getResources().getInteger(C1777R.integer.quick_settings_min_num_tiles);
        this.mNumColumns = context.getResources().getInteger(C1777R.integer.quick_settings_num_columns);
        this.mAccessibilityDelegate = new TileAdapterDelegate();
        r1.mCacheSpanIndices = true;
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        Context context = recyclerView.getContext();
        LayoutInflater from = LayoutInflater.from(context);
        if (i == 3) {
            return new Holder(from.inflate(C1777R.layout.qs_customize_header, recyclerView, false));
        }
        if (i == 4) {
            return new Holder(from.inflate(C1777R.layout.qs_customize_tile_divider, recyclerView, false));
        }
        if (i == 1) {
            return new Holder(from.inflate(C1777R.layout.qs_customize_divider, recyclerView, false));
        }
        FrameLayout frameLayout = (FrameLayout) from.inflate(C1777R.layout.qs_customize_tile_frame, recyclerView, false);
        frameLayout.addView(new CustomizeTileView(context, new QSIconViewImpl(context)));
        return new Holder(frameLayout);
    }

    public final void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }
}
