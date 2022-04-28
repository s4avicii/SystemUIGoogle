package com.android.systemui.p006qs.customize;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.policy.SystemBarUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSDetailClipper;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.plugins.p005qs.QSContainerController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.util.Utils;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.customize.QSCustomizer */
public class QSCustomizer extends LinearLayout {
    public boolean isShown;
    public final QSDetailClipper mClipper;
    public final C10111 mCollapseAnimationListener = new AnimatorListenerAdapter() {
        public final void onAnimationCancel(Animator animator) {
            QSCustomizer qSCustomizer = QSCustomizer.this;
            if (!qSCustomizer.isShown) {
                qSCustomizer.setVisibility(8);
            }
            QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
        }

        public final void onAnimationEnd(Animator animator) {
            QSCustomizer qSCustomizer = QSCustomizer.this;
            if (!qSCustomizer.isShown) {
                qSCustomizer.setVisibility(8);
            }
            QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
        }
    };
    public boolean mCustomizing;
    public boolean mIsShowingNavBackdrop;
    public boolean mOpening;
    public C0961QS mQs;
    public QSContainerController mQsContainerController;
    public final RecyclerView mRecyclerView;
    public final View mTransparentView;

    /* renamed from: mX */
    public int f72mX;

    /* renamed from: mY */
    public int f73mY;

    /* renamed from: com.android.systemui.qs.customize.QSCustomizer$ExpandAnimatorListener */
    public class ExpandAnimatorListener extends AnimatorListenerAdapter {
        public final TileAdapter mTileAdapter;

        public ExpandAnimatorListener(TileAdapter tileAdapter) {
            this.mTileAdapter = tileAdapter;
        }

        public final void onAnimationCancel(Animator animator) {
            QSCustomizer qSCustomizer = QSCustomizer.this;
            qSCustomizer.mOpening = false;
            qSCustomizer.mQs.notifyCustomizeChanged();
            QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
        }

        public final void onAnimationEnd(Animator animator) {
            QSCustomizer qSCustomizer = QSCustomizer.this;
            if (qSCustomizer.isShown) {
                qSCustomizer.mCustomizing = true;
                qSCustomizer.mQs.notifyCustomizeChanged();
            }
            QSCustomizer qSCustomizer2 = QSCustomizer.this;
            qSCustomizer2.mOpening = false;
            qSCustomizer2.mQsContainerController.setCustomizerAnimating(false);
            QSCustomizer.this.mRecyclerView.setAdapter(this.mTileAdapter);
        }
    }

    public final boolean isCustomizing() {
        if (this.mCustomizing || this.mOpening) {
            return true;
        }
        return false;
    }

    public final void updateNavColors(LightBarController lightBarController) {
        boolean z;
        if (!this.mIsShowingNavBackdrop || !this.isShown) {
            z = false;
        } else {
            z = true;
        }
        Objects.requireNonNull(lightBarController);
        if (lightBarController.mQsCustomizing != z) {
            lightBarController.mQsCustomizing = z;
            lightBarController.reevaluate();
        }
    }

    public final void updateResources() {
        int i;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTransparentView.getLayoutParams();
        Context context = this.mContext;
        Resources resources = context.getResources();
        if (Utils.shouldUseSplitNotificationShade(resources)) {
            i = resources.getDimensionPixelSize(C1777R.dimen.qs_header_system_icons_area_height);
        } else {
            i = SystemBarUtils.getQuickQsOffsetHeight(context);
        }
        layoutParams.height = i;
        this.mTransparentView.setLayoutParams(layoutParams);
    }

    public QSCustomizer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(getContext()).inflate(C1777R.layout.qs_customize_panel_content, this);
        this.mClipper = new QSDetailClipper(findViewById(C1777R.C1779id.customize_container));
        Toolbar toolbar = (Toolbar) findViewById(16908729);
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(16843531, typedValue, true);
        toolbar.setNavigationIcon(getResources().getDrawable(typedValue.resourceId, this.mContext.getTheme()));
        toolbar.getMenu().add(0, 1, 0, this.mContext.getString(17041371));
        toolbar.setTitle(C1777R.string.qs_edit);
        RecyclerView recyclerView = (RecyclerView) findViewById(16908298);
        this.mRecyclerView = recyclerView;
        this.mTransparentView = findViewById(C1777R.C1779id.customizer_transparent_view);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.mMoveDuration = 150;
        Objects.requireNonNull(recyclerView);
        RecyclerView.ItemAnimator itemAnimator = recyclerView.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
            RecyclerView.ItemAnimator itemAnimator2 = recyclerView.mItemAnimator;
            Objects.requireNonNull(itemAnimator2);
            itemAnimator2.mListener = null;
        }
        recyclerView.mItemAnimator = defaultItemAnimator;
        defaultItemAnimator.mListener = recyclerView.mItemAnimatorListener;
    }

    public final void updateNavBackDrop(Configuration configuration, LightBarController lightBarController) {
        boolean z;
        View findViewById = findViewById(C1777R.C1779id.nav_bar_background);
        int i = 0;
        if (configuration.smallestScreenWidthDp >= 600 || configuration.orientation != 2) {
            z = true;
        } else {
            z = false;
        }
        this.mIsShowingNavBackdrop = z;
        if (findViewById != null) {
            if (!z) {
                i = 8;
            }
            findViewById.setVisibility(i);
        }
        updateNavColors(lightBarController);
    }

    public final boolean isShown() {
        return this.isShown;
    }
}
