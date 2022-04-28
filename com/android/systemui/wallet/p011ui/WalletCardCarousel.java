package com.android.systemui.wallet.p011ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.android.p012wm.shell.C1777R;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel */
public class WalletCardCarousel extends RecyclerView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mCardCenterToScreenCenterDistancePx;
    public float mCardEdgeToCenterDistance;
    public int mCardHeightPx;
    public int mCardMarginPx;
    public OnCardScrollListener mCardScrollListener;
    public int mCardWidthPx;
    public int mCenteredAdapterPosition;
    public float mCornerRadiusPx;
    public float mEdgeToCenterDistance;
    public int mExpectedViewWidth;
    public OnSelectionListener mSelectionListener;
    public final Rect mSystemGestureExclusionZone;
    public int mTotalCardWidth;
    public final WalletCardCarouselAdapter mWalletCardCarouselAdapter;

    /* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel$CardCarouselAccessibilityDelegate */
    public class CardCarouselAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
        public CardCarouselAccessibilityDelegate(RecyclerView recyclerView) {
            super(recyclerView);
        }

        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() == 32768) {
                WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
                Objects.requireNonNull(walletCardCarousel);
                walletCardCarousel.scrollToPosition(RecyclerView.getChildAdapterPosition(view));
            }
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
    }

    /* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel$CardCarouselScrollListener */
    public class CardCarouselScrollListener extends RecyclerView.OnScrollListener {
        public int mOldState = -1;

        public CardCarouselScrollListener() {
        }

        public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 0 && i != this.mOldState) {
                WalletCardCarousel.this.performHapticFeedback(1);
            }
            this.mOldState = i;
        }

        public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
            WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
            int i3 = -1;
            walletCardCarousel.mCenteredAdapterPosition = -1;
            walletCardCarousel.mEdgeToCenterDistance = Float.MAX_VALUE;
            walletCardCarousel.mCardCenterToScreenCenterDistancePx = Float.MAX_VALUE;
            for (int i4 = 0; i4 < WalletCardCarousel.this.getChildCount(); i4++) {
                WalletCardCarousel walletCardCarousel2 = WalletCardCarousel.this;
                walletCardCarousel2.updateCardView(walletCardCarousel2.getChildAt(i4));
            }
            WalletCardCarousel walletCardCarousel3 = WalletCardCarousel.this;
            int i5 = walletCardCarousel3.mCenteredAdapterPosition;
            if (i5 != -1 && i != 0) {
                if (walletCardCarousel3.mEdgeToCenterDistance > 0.0f) {
                    i3 = 1;
                }
                int i6 = i5 + i3;
                if (i6 >= 0 && i6 < walletCardCarousel3.mWalletCardCarouselAdapter.mData.size()) {
                    WalletCardCarousel walletCardCarousel4 = WalletCardCarousel.this;
                    float abs = Math.abs(WalletCardCarousel.this.mEdgeToCenterDistance);
                    WalletCardCarousel walletCardCarousel5 = WalletCardCarousel.this;
                    ((WalletView) walletCardCarousel5.mCardScrollListener).onCardScroll(walletCardCarousel4.mWalletCardCarouselAdapter.mData.get(walletCardCarousel4.mCenteredAdapterPosition), WalletCardCarousel.this.mWalletCardCarouselAdapter.mData.get(i6), abs / walletCardCarousel5.mCardEdgeToCenterDistance);
                }
            }
        }
    }

    /* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel$CarouselSnapHelper */
    public class CarouselSnapHelper extends PagerSnapHelper {
        public CarouselSnapHelper() {
        }

        public final RecyclerView.SmoothScroller createScroller(final RecyclerView.LayoutManager layoutManager) {
            return new LinearSmoothScroller(WalletCardCarousel.this.getContext()) {
                public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 200.0f / ((float) displayMetrics.densityDpi);
                }

                public final void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
                    int[] calculateDistanceToFinalSnap = CarouselSnapHelper.this.calculateDistanceToFinalSnap(layoutManager, view);
                    int i = calculateDistanceToFinalSnap[0];
                    int i2 = calculateDistanceToFinalSnap[1];
                    int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(i2)));
                    if (calculateTimeForDeceleration > 0) {
                        action.update(i, i2, calculateTimeForDeceleration, this.mDecelerateInterpolator);
                    }
                }

                public final int calculateTimeForScrolling(int i) {
                    return Math.min(80, super.calculateTimeForScrolling(i));
                }
            };
        }

        public final View findSnapView(RecyclerView.LayoutManager layoutManager) {
            View findSnapView = super.findSnapView(layoutManager);
            if (findSnapView == null) {
                return null;
            }
            WalletCardViewInfo walletCardViewInfo = ((WalletCardViewHolder) findSnapView.getTag()).mCardViewInfo;
            ((WalletScreenController) WalletCardCarousel.this.mSelectionListener).onCardSelected(walletCardViewInfo);
            ((WalletView) WalletCardCarousel.this.mCardScrollListener).onCardScroll(walletCardViewInfo, walletCardViewInfo, 0.0f);
            return findSnapView;
        }
    }

    /* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel$OnCardScrollListener */
    public interface OnCardScrollListener {
    }

    /* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel$OnSelectionListener */
    public interface OnSelectionListener {
    }

    /* renamed from: com.android.systemui.wallet.ui.WalletCardCarousel$WalletCardCarouselAdapter */
    public class WalletCardCarouselAdapter extends RecyclerView.Adapter<WalletCardViewHolder> {
        public List<WalletCardViewInfo> mData = Collections.EMPTY_LIST;

        public WalletCardCarouselAdapter() {
        }

        public final int getItemCount() {
            return this.mData.size();
        }

        public final long getItemId(int i) {
            return (long) this.mData.get(i).getCardId().hashCode();
        }

        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            WalletCardViewHolder walletCardViewHolder = (WalletCardViewHolder) viewHolder;
            WalletCardViewInfo walletCardViewInfo = this.mData.get(i);
            walletCardViewHolder.mCardViewInfo = walletCardViewInfo;
            if (walletCardViewInfo.getCardId().isEmpty()) {
                walletCardViewHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER);
            }
            walletCardViewHolder.mImageView.setImageDrawable(walletCardViewInfo.getCardDrawable());
            walletCardViewHolder.mCardView.setContentDescription(walletCardViewInfo.getContentDescription());
            walletCardViewHolder.mCardView.setOnClickListener(new C1746x7bd3973a(this, i, walletCardViewInfo));
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            View inflate = LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.wallet_card_view, recyclerView, false);
            WalletCardViewHolder walletCardViewHolder = new WalletCardViewHolder(inflate);
            CardView cardView = walletCardViewHolder.mCardView;
            cardView.setRadius(WalletCardCarousel.this.mCornerRadiusPx);
            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
            Objects.requireNonNull(walletCardCarousel);
            layoutParams.width = walletCardCarousel.mCardWidthPx;
            WalletCardCarousel walletCardCarousel2 = WalletCardCarousel.this;
            Objects.requireNonNull(walletCardCarousel2);
            layoutParams.height = walletCardCarousel2.mCardHeightPx;
            inflate.setTag(walletCardViewHolder);
            return walletCardViewHolder;
        }
    }

    public WalletCardCarousel(Context context) {
        this(context, (AttributeSet) null);
    }

    public WalletCardCarousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSystemGestureExclusionZone = new Rect();
        this.mCenteredAdapterPosition = -1;
        this.mEdgeToCenterDistance = Float.MAX_VALUE;
        this.mCardCenterToScreenCenterDistancePx = Float.MAX_VALUE;
        setLayoutManager(new LinearLayoutManager(0));
        addOnScrollListener(new CardCarouselScrollListener());
        new CarouselSnapHelper().attachToRecyclerView(this);
        WalletCardCarouselAdapter walletCardCarouselAdapter = new WalletCardCarouselAdapter();
        this.mWalletCardCarouselAdapter = walletCardCarouselAdapter;
        walletCardCarouselAdapter.setHasStableIds(true);
        setAdapter(walletCardCarouselAdapter);
        ViewCompat.setAccessibilityDelegate(this, new CardCarouselAccessibilityDelegate(this));
        addItemDecoration(new DotIndicatorDecoration(getContext()));
    }

    public final void updatePadding(int i) {
        int i2;
        RecyclerView.ViewHolder findViewHolderForAdapterPosition;
        int max = Math.max(0, ((i - this.mTotalCardWidth) / 2) - this.mCardMarginPx);
        setPadding(max, getPaddingTop(), max, getPaddingBottom());
        WalletCardCarouselAdapter walletCardCarouselAdapter = this.mWalletCardCarouselAdapter;
        if (walletCardCarouselAdapter != null && walletCardCarouselAdapter.getItemCount() > 0 && (i2 = this.mCenteredAdapterPosition) != -1 && (findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(i2)) != null) {
            View view = findViewHolderForAdapterPosition.itemView;
            scrollBy(((view.getRight() + view.getLeft()) / 2) - ((getRight() + getLeft()) / 2), 0);
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int width = getWidth();
        if (this.mWalletCardCarouselAdapter.getItemCount() > 1 && ((double) width) < ((double) this.mTotalCardWidth) * 1.5d) {
            this.mSystemGestureExclusionZone.set(0, 0, width, getHeight());
            setSystemGestureExclusionRects(Collections.singletonList(this.mSystemGestureExclusionZone));
        }
        if (width != this.mExpectedViewWidth) {
            updatePadding(width);
        }
    }

    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int i = this.mCardMarginPx;
        layoutParams.leftMargin = i;
        layoutParams.rightMargin = i;
        view.addOnLayoutChangeListener(new WalletCardCarousel$$ExternalSyntheticLambda0(this, view));
    }

    public final void scrollToPosition(int i) {
        super.scrollToPosition(i);
        ((WalletScreenController) this.mSelectionListener).onCardSelected(this.mWalletCardCarouselAdapter.mData.get(i));
    }

    public final void updateCardView(View view) {
        int i;
        CardView cardView = ((WalletCardViewHolder) view.getTag()).mCardView;
        float width = ((float) getWidth()) / 2.0f;
        float left = ((float) (view.getLeft() + view.getRight())) / 2.0f;
        float f = left - width;
        float max = Math.max(0.83f, 1.0f - Math.abs(f / ((float) view.getWidth())));
        cardView.setScaleX(max);
        cardView.setScaleY(max);
        if (left < width) {
            i = view.getRight() + this.mCardMarginPx;
        } else {
            i = view.getLeft() - this.mCardMarginPx;
        }
        if (Math.abs(f) < this.mCardCenterToScreenCenterDistancePx && RecyclerView.getChildAdapterPosition(view) != -1) {
            this.mCenteredAdapterPosition = RecyclerView.getChildAdapterPosition(view);
            this.mEdgeToCenterDistance = ((float) i) - width;
            this.mCardCenterToScreenCenterDistancePx = Math.abs(f);
        }
    }
}
