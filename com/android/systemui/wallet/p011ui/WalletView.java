package com.android.systemui.wallet.p011ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.wallet.p011ui.WalletCardCarousel;
import java.util.Objects;

/* renamed from: com.android.systemui.wallet.ui.WalletView */
public class WalletView extends FrameLayout implements WalletCardCarousel.OnCardScrollListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Button mActionButton;
    public final float mAnimationTranslationX;
    public final Button mAppButton;
    public final WalletCardCarousel mCardCarousel;
    public final ViewGroup mCardCarouselContainer;
    public final TextView mCardLabel;
    public View.OnClickListener mDeviceLockedActionOnClickListener;
    public final ViewGroup mEmptyStateView;
    public final TextView mErrorView;
    public FalsingCollector mFalsingCollector;
    public final ImageView mIcon;
    public boolean mIsDeviceLocked;
    public boolean mIsUdfpsEnabled;
    public final Interpolator mOutInterpolator;
    public View.OnClickListener mShowWalletAppOnClickListener;
    public final Button mToolbarAppButton;

    public WalletView(Context context) {
        this(context, (AttributeSet) null);
    }

    public WalletView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsDeviceLocked = false;
        this.mIsUdfpsEnabled = false;
        View.inflate(context, C1777R.layout.wallet_fullscreen, this);
        this.mCardCarouselContainer = (ViewGroup) requireViewById(C1777R.C1779id.card_carousel_container);
        WalletCardCarousel walletCardCarousel = (WalletCardCarousel) requireViewById(C1777R.C1779id.card_carousel);
        this.mCardCarousel = walletCardCarousel;
        Objects.requireNonNull(walletCardCarousel);
        walletCardCarousel.mCardScrollListener = this;
        this.mIcon = (ImageView) requireViewById(C1777R.C1779id.icon);
        this.mCardLabel = (TextView) requireViewById(C1777R.C1779id.label);
        this.mAppButton = (Button) requireViewById(C1777R.C1779id.wallet_app_button);
        this.mToolbarAppButton = (Button) requireViewById(C1777R.C1779id.wallet_toolbar_app_button);
        this.mActionButton = (Button) requireViewById(C1777R.C1779id.wallet_action_button);
        this.mErrorView = (TextView) requireViewById(C1777R.C1779id.error_view);
        this.mEmptyStateView = (ViewGroup) requireViewById(C1777R.C1779id.wallet_empty_state);
        this.mOutInterpolator = AnimationUtils.loadInterpolator(context, 17563650);
        Objects.requireNonNull(walletCardCarousel);
        this.mAnimationTranslationX = ((float) walletCardCarousel.mCardWidthPx) / 4.0f;
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        FalsingCollector falsingCollector = this.mFalsingCollector;
        if (falsingCollector != null) {
            falsingCollector.onTouchEvent(motionEvent);
        }
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        FalsingCollector falsingCollector2 = this.mFalsingCollector;
        if (falsingCollector2 != null) {
            falsingCollector2.onMotionEventComplete();
        }
        return dispatchTouchEvent;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mCardCarousel.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent)) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:47:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void showCardCarousel(java.util.List<com.android.systemui.wallet.p011ui.WalletCardViewInfo> r10, int r11, boolean r12, boolean r13) {
        /*
            r9 = this;
            com.android.systemui.wallet.ui.WalletCardCarousel r0 = r9.mCardCarousel
            boolean r1 = r9.mIsDeviceLocked
            r2 = 0
            r3 = 1
            if (r1 == r12) goto L_0x000a
            r1 = r3
            goto L_0x000b
        L_0x000a:
            r1 = r2
        L_0x000b:
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.wallet.ui.WalletCardCarousel$WalletCardCarouselAdapter r4 = r0.mWalletCardCarouselAdapter
            java.util.Objects.requireNonNull(r4)
            java.util.List<com.android.systemui.wallet.ui.WalletCardViewInfo> r5 = r4.mData
            r4.mData = r10
            if (r1 != 0) goto L_0x0048
            int r1 = r5.size()
            int r6 = r10.size()
            if (r1 == r6) goto L_0x0024
            goto L_0x003d
        L_0x0024:
            r1 = r2
        L_0x0025:
            int r6 = r10.size()
            if (r1 >= r6) goto L_0x0042
            java.lang.Object r6 = r5.get(r1)
            com.android.systemui.wallet.ui.WalletCardViewInfo r6 = (com.android.systemui.wallet.p011ui.WalletCardViewInfo) r6
            java.lang.Object r7 = r10.get(r1)
            com.android.systemui.wallet.ui.WalletCardViewInfo r7 = (com.android.systemui.wallet.p011ui.WalletCardViewInfo) r7
            boolean r6 = r6.isUiEquivalent(r7)
            if (r6 != 0) goto L_0x003f
        L_0x003d:
            r1 = r2
            goto L_0x0043
        L_0x003f:
            int r1 = r1 + 1
            goto L_0x0025
        L_0x0042:
            r1 = r3
        L_0x0043:
            if (r1 != 0) goto L_0x0046
            goto L_0x0048
        L_0x0046:
            r1 = r2
            goto L_0x004c
        L_0x0048:
            r4.notifyDataSetChanged()
            r1 = r3
        L_0x004c:
            r0.scrollToPosition(r11)
            java.lang.Object r4 = r10.get(r11)
            com.android.systemui.wallet.ui.WalletCardViewInfo r4 = (com.android.systemui.wallet.p011ui.WalletCardViewInfo) r4
            com.android.systemui.wallet.ui.WalletCardCarousel$OnCardScrollListener r0 = r0.mCardScrollListener
            com.android.systemui.wallet.ui.WalletView r0 = (com.android.systemui.wallet.p011ui.WalletView) r0
            r5 = 0
            r0.onCardScroll(r4, r4, r5)
            r9.mIsDeviceLocked = r12
            r9.mIsUdfpsEnabled = r13
            android.view.ViewGroup r13 = r9.mCardCarouselContainer
            r13.setVisibility(r2)
            com.android.systemui.wallet.ui.WalletCardCarousel r13 = r9.mCardCarousel
            r13.setVisibility(r2)
            android.widget.TextView r13 = r9.mErrorView
            r0 = 8
            r13.setVisibility(r0)
            android.view.ViewGroup r13 = r9.mEmptyStateView
            r13.setVisibility(r0)
            android.widget.ImageView r13 = r9.mIcon
            android.content.Context r4 = r9.mContext
            java.lang.Object r6 = r10.get(r11)
            com.android.systemui.wallet.ui.WalletCardViewInfo r6 = (com.android.systemui.wallet.p011ui.WalletCardViewInfo) r6
            android.graphics.drawable.Drawable r6 = r6.getIcon()
            if (r6 == 0) goto L_0x0091
            r7 = 17956900(0x1120024, float:2.6816066E-38)
            int r4 = com.android.settingslib.Utils.getColorAttrDefaultColor(r4, r7)
            r6.setTint(r4)
        L_0x0091:
            r13.setImageDrawable(r6)
            android.widget.TextView r13 = r9.mCardLabel
            java.lang.Object r4 = r10.get(r11)
            com.android.systemui.wallet.ui.WalletCardViewInfo r4 = (com.android.systemui.wallet.p011ui.WalletCardViewInfo) r4
            java.lang.CharSequence r6 = r4.getLabel()
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = "\\n"
            java.lang.String[] r6 = r6.split(r7)
            int r7 = r6.length
            r8 = 2
            if (r7 != r8) goto L_0x00b1
            r4 = r6[r2]
            goto L_0x00b5
        L_0x00b1:
            java.lang.CharSequence r4 = r4.getLabel()
        L_0x00b5:
            r13.setText(r4)
            android.content.res.Resources r13 = r9.getResources()
            android.content.res.Configuration r13 = r13.getConfiguration()
            int r13 = r13.orientation
            r4 = 2131427889(0x7f0b0231, float:1.8477407E38)
            if (r13 != r3) goto L_0x00e5
            android.widget.Button r13 = r9.mAppButton
            r13.setVisibility(r2)
            android.widget.Button r13 = r9.mToolbarAppButton
            r13.setVisibility(r0)
            android.widget.TextView r13 = r9.mCardLabel
            r13.setVisibility(r2)
            android.view.View r13 = r9.requireViewById(r4)
            r13.setVisibility(r2)
            android.widget.Button r13 = r9.mAppButton
            android.view.View$OnClickListener r0 = r9.mShowWalletAppOnClickListener
            r13.setOnClickListener(r0)
            goto L_0x0104
        L_0x00e5:
            if (r13 != r8) goto L_0x0104
            android.widget.Button r13 = r9.mToolbarAppButton
            r13.setVisibility(r2)
            android.widget.Button r13 = r9.mAppButton
            r13.setVisibility(r0)
            android.widget.TextView r13 = r9.mCardLabel
            r13.setVisibility(r0)
            android.view.View r13 = r9.requireViewById(r4)
            r13.setVisibility(r0)
            android.widget.Button r13 = r9.mToolbarAppButton
            android.view.View$OnClickListener r0 = r9.mShowWalletAppOnClickListener
            r13.setOnClickListener(r0)
        L_0x0104:
            com.android.systemui.wallet.ui.WalletCardCarousel r13 = r9.mCardCarousel
            java.util.Objects.requireNonNull(r13)
            com.android.systemui.wallet.ui.WalletCardCarousel$WalletCardCarouselAdapter r0 = r13.mWalletCardCarouselAdapter
            r13.setAdapter(r0)
            android.view.ViewGroup r13 = r9.mCardCarouselContainer
            android.view.ViewGroup$LayoutParams r13 = r13.getLayoutParams()
            boolean r0 = r13 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r0 == 0) goto L_0x0127
            android.view.ViewGroup$MarginLayoutParams r13 = (android.view.ViewGroup.MarginLayoutParams) r13
            android.content.res.Resources r0 = r9.getResources()
            r4 = 2131167316(0x7f070854, float:1.7948902E38)
            int r0 = r0.getDimensionPixelSize(r4)
            r13.topMargin = r0
        L_0x0127:
            java.lang.Object r10 = r10.get(r11)
            com.android.systemui.wallet.ui.WalletCardViewInfo r10 = (com.android.systemui.wallet.p011ui.WalletCardViewInfo) r10
            boolean r11 = r9.mIsUdfpsEnabled
            r9.renderActionButton(r10, r12, r11)
            if (r1 == 0) goto L_0x0166
            r10 = 3
            android.view.View[] r11 = new android.view.View[r10]
            android.widget.ImageView r12 = r9.mIcon
            r11[r2] = r12
            android.widget.TextView r12 = r9.mCardLabel
            r11[r3] = r12
            android.widget.Button r9 = r9.mActionButton
            r11[r8] = r9
        L_0x0143:
            if (r2 >= r10) goto L_0x0166
            r9 = r11[r2]
            int r12 = r9.getVisibility()
            if (r12 != 0) goto L_0x0163
            r9.setAlpha(r5)
            android.view.ViewPropertyAnimator r9 = r9.animate()
            r12 = 1065353216(0x3f800000, float:1.0)
            android.view.ViewPropertyAnimator r9 = r9.alpha(r12)
            r12 = 100
            android.view.ViewPropertyAnimator r9 = r9.setDuration(r12)
            r9.start()
        L_0x0163:
            int r2 = r2 + 1
            goto L_0x0143
        L_0x0166:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallet.p011ui.WalletView.showCardCarousel(java.util.List, int, boolean, boolean):void");
    }

    public final void showEmptyStateView(Drawable drawable, CharSequence charSequence, CharSequence charSequence2, WalletScreenController$$ExternalSyntheticLambda0 walletScreenController$$ExternalSyntheticLambda0) {
        this.mEmptyStateView.setVisibility(0);
        this.mErrorView.setVisibility(8);
        this.mCardCarousel.setVisibility(8);
        this.mIcon.setImageDrawable(drawable);
        this.mIcon.setContentDescription(charSequence);
        this.mCardLabel.setText(C1777R.string.wallet_empty_state_label);
        ((ImageView) this.mEmptyStateView.requireViewById(C1777R.C1779id.empty_state_icon)).setImageDrawable(this.mContext.getDrawable(C1777R.C1778drawable.ic_qs_plus));
        ((TextView) this.mEmptyStateView.requireViewById(C1777R.C1779id.empty_state_title)).setText(charSequence2);
        this.mEmptyStateView.setOnClickListener(walletScreenController$$ExternalSyntheticLambda0);
    }

    public final void onCardScroll(WalletCardViewInfo walletCardViewInfo, WalletCardViewInfo walletCardViewInfo2, float f) {
        CharSequence charSequence;
        CharSequence[] split = walletCardViewInfo.getLabel().toString().split("\\n");
        if (split.length == 2) {
            charSequence = split[0];
        } else {
            charSequence = walletCardViewInfo.getLabel();
        }
        Context context = this.mContext;
        Drawable icon = walletCardViewInfo.getIcon();
        if (icon != null) {
            icon.setTint(Utils.getColorAttrDefaultColor(context, 17956900));
        }
        renderActionButton(walletCardViewInfo, this.mIsDeviceLocked, this.mIsUdfpsEnabled);
        if (walletCardViewInfo.isUiEquivalent(walletCardViewInfo2)) {
            this.mCardLabel.setAlpha(1.0f);
            this.mIcon.setAlpha(1.0f);
            this.mActionButton.setAlpha(1.0f);
            return;
        }
        this.mCardLabel.setText(charSequence);
        this.mIcon.setImageDrawable(icon);
        this.mCardLabel.setAlpha(f);
        this.mIcon.setAlpha(f);
        this.mActionButton.setAlpha(f);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        WalletCardCarousel walletCardCarousel = this.mCardCarousel;
        int width = getWidth();
        Objects.requireNonNull(walletCardCarousel);
        if (walletCardCarousel.mExpectedViewWidth != width) {
            walletCardCarousel.mExpectedViewWidth = width;
            Resources resources = walletCardCarousel.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            int round = Math.round(((float) Math.min(width, Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels))) * 0.69f);
            walletCardCarousel.mCardWidthPx = round;
            walletCardCarousel.mCardHeightPx = Math.round(((float) round) / 1.5909091f);
            float f = (float) walletCardCarousel.mCardWidthPx;
            walletCardCarousel.mCornerRadiusPx = 0.035714287f * f;
            walletCardCarousel.mCardMarginPx = Math.round(f * -0.03f);
            int dimensionPixelSize = (resources.getDimensionPixelSize(C1777R.dimen.card_margin) * 2) + walletCardCarousel.mCardWidthPx;
            walletCardCarousel.mTotalCardWidth = dimensionPixelSize;
            walletCardCarousel.mCardEdgeToCenterDistance = ((float) dimensionPixelSize) / 2.0f;
            walletCardCarousel.updatePadding(width);
            WalletCardCarousel.OnSelectionListener onSelectionListener = walletCardCarousel.mSelectionListener;
            if (onSelectionListener != null) {
                ((WalletScreenController) onSelectionListener).queryWalletCards();
            }
        }
    }

    public final void renderActionButton(WalletCardViewInfo walletCardViewInfo, boolean z, boolean z2) {
        String str;
        View.OnClickListener onClickListener;
        String[] split = walletCardViewInfo.getLabel().toString().split("\\n");
        if (split.length == 2) {
            str = split[1];
        } else {
            str = null;
        }
        if (z2 || str == null) {
            this.mActionButton.setVisibility(8);
            return;
        }
        this.mActionButton.setVisibility(0);
        this.mActionButton.setText(str);
        Button button = this.mActionButton;
        if (z) {
            onClickListener = this.mDeviceLockedActionOnClickListener;
        } else {
            onClickListener = new WalletView$$ExternalSyntheticLambda0(walletCardViewInfo, 0);
        }
        button.setOnClickListener(onClickListener);
    }

    @VisibleForTesting
    public ViewGroup getCardCarouselContainer() {
        return this.mCardCarouselContainer;
    }

    @VisibleForTesting
    public TextView getCardLabel() {
        return this.mCardLabel;
    }

    @VisibleForTesting
    public ViewGroup getEmptyStateView() {
        return this.mEmptyStateView;
    }

    @VisibleForTesting
    public TextView getErrorView() {
        return this.mErrorView;
    }
}
