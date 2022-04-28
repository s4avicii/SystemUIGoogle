package com.google.android.setupdesign;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticOutline0;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupdesign.template.DescriptionMixin;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.template.IconMixin;
import com.google.android.setupdesign.template.IllustrationProgressMixin;
import com.google.android.setupdesign.template.ProgressBarMixin;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.view.BottomScrollView;
import java.util.Objects;

public class GlifLayout extends PartnerCustomizationLayout {
    public boolean applyPartnerHeavyThemeResource;
    public ColorStateList backgroundBaseColor;
    public boolean backgroundPatterned;
    public ColorStateList primaryColor;

    public GlifLayout(Context context) {
        this(context, 0, 0);
    }

    public GlifLayout(Context context, int i) {
        this(context, i, 0);
    }

    private void init(AttributeSet attributeSet, int i) {
        boolean z;
        ScrollView scrollView;
        Class<ProgressBarMixin> cls = ProgressBarMixin.class;
        if (!isInEditMode()) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudGlifLayout, i, 0);
            boolean z2 = obtainStyledAttributes.getBoolean(4, false);
            if (!shouldApplyPartnerResource() || !z2) {
                z = false;
            } else {
                z = true;
            }
            this.applyPartnerHeavyThemeResource = z;
            registerMixin(HeaderMixin.class, new HeaderMixin(this, attributeSet, i));
            registerMixin(DescriptionMixin.class, new DescriptionMixin(this, attributeSet, i));
            registerMixin(IconMixin.class, new IconMixin(this, attributeSet, i));
            registerMixin(cls, new ProgressBarMixin(this, attributeSet, i));
            registerMixin(IllustrationProgressMixin.class, new IllustrationProgressMixin(this));
            registerMixin(RequireScrollMixin.class, new RequireScrollMixin());
            View findManagedViewById = findManagedViewById(C1777R.C1779id.sud_scroll_view);
            if (findManagedViewById instanceof ScrollView) {
                scrollView = (ScrollView) findManagedViewById;
            } else {
                scrollView = null;
            }
            if (scrollView != null) {
                if (scrollView instanceof BottomScrollView) {
                    BottomScrollView bottomScrollView = (BottomScrollView) scrollView;
                } else {
                    Log.w("ScrollViewDelegate", "Cannot set non-BottomScrollView. Found=" + scrollView);
                }
            }
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(2);
            if (colorStateList != null) {
                this.primaryColor = colorStateList;
                updateBackground();
                ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(cls);
                Objects.requireNonNull(progressBarMixin);
                progressBarMixin.color = colorStateList;
                ProgressBar peekProgressBar = progressBarMixin.peekProgressBar();
                if (peekProgressBar != null) {
                    peekProgressBar.setIndeterminateTintList(colorStateList);
                    peekProgressBar.setProgressBackgroundTintList(colorStateList);
                }
            }
            if (shouldApplyPartnerHeavyThemeResource() && !useFullDynamicColor()) {
                getRootView().setBackgroundColor(PartnerConfigHelper.get(getContext()).getColor(getContext(), PartnerConfig.CONFIG_LAYOUT_BACKGROUND_COLOR));
            }
            View findManagedViewById2 = findManagedViewById(C1777R.C1779id.sud_layout_content);
            if (findManagedViewById2 != null) {
                if (shouldApplyPartnerResource()) {
                    LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(findManagedViewById2);
                }
                if (!(this instanceof GlifPreferenceLayout)) {
                    tryApplyPartnerCustomizationContentPaddingTopStyle(findManagedViewById2);
                }
            }
            updateLandscapeMiddleHorizontalSpacing();
            this.backgroundBaseColor = obtainStyledAttributes.getColorStateList(0);
            updateBackground();
            this.backgroundPatterned = obtainStyledAttributes.getBoolean(1, true);
            updateBackground();
            int resourceId = obtainStyledAttributes.getResourceId(3, 0);
            if (resourceId != 0) {
                ViewStub viewStub = (ViewStub) findManagedViewById(C1777R.C1779id.sud_layout_sticky_header);
                viewStub.setLayoutResource(resourceId);
                viewStub.inflate();
            }
            obtainStyledAttributes.recycle();
        }
    }

    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = C1777R.C1779id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0092, code lost:
        r2 = (int) r4.getResources().getDimension(com.android.p012wm.shell.C1777R.dimen.sud_horizontal_icon_height);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onFinishInflate() {
        /*
            r24 = this;
            r0 = r24
            com.google.android.setupcompat.partnerconfig.PartnerConfig r10 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY
            com.google.android.setupcompat.partnerconfig.PartnerConfig r11 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_DESCRIPTION_FONT_FAMILY
            com.google.android.setupcompat.partnerconfig.PartnerConfig r12 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_DESCRIPTION_TEXT_SIZE
            com.google.android.setupcompat.partnerconfig.PartnerConfig r13 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_DESCRIPTION_LINK_TEXT_COLOR
            com.google.android.setupcompat.partnerconfig.PartnerConfig r14 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_DESCRIPTION_TEXT_COLOR
            super.onFinishInflate()
            java.lang.Class<com.google.android.setupdesign.template.IconMixin> r1 = com.google.android.setupdesign.template.IconMixin.class
            com.google.android.setupcompat.template.Mixin r1 = r0.getMixin(r1)
            com.google.android.setupdesign.template.IconMixin r1 = (com.google.android.setupdesign.template.IconMixin) r1
            java.util.Objects.requireNonNull(r1)
            com.google.android.setupcompat.internal.TemplateLayout r2 = r1.templateLayout
            boolean r2 = com.google.android.setupdesign.util.PartnerStyleHelper.shouldApplyPartnerResource((android.view.View) r2)
            r3 = 0
            if (r2 == 0) goto L_0x00cc
            android.widget.ImageView r2 = r1.getView()
            com.google.android.setupcompat.internal.TemplateLayout r1 = r1.templateLayout
            r4 = 2131428978(0x7f0b0672, float:1.8479616E38)
            android.view.View r1 = r1.findManagedViewById(r4)
            android.widget.FrameLayout r1 = (android.widget.FrameLayout) r1
            if (r2 == 0) goto L_0x00cc
            if (r1 != 0) goto L_0x0038
            goto L_0x00cc
        L_0x0038:
            android.content.Context r4 = r2.getContext()
            int r5 = com.google.android.setupdesign.util.PartnerStyleHelper.getLayoutGravity(r4)
            if (r5 == 0) goto L_0x0055
            android.view.ViewGroup$LayoutParams r6 = r2.getLayoutParams()
            boolean r6 = r6 instanceof android.widget.FrameLayout.LayoutParams
            if (r6 == 0) goto L_0x0055
            android.view.ViewGroup$LayoutParams r6 = r2.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r6 = (android.widget.FrameLayout.LayoutParams) r6
            r6.gravity = r5
            r2.setLayoutParams(r6)
        L_0x0055:
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r4)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r6 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_ICON_SIZE
            boolean r5 = r5.isPartnerConfigAvailable(r6)
            if (r5 == 0) goto L_0x00a6
            android.view.ViewTreeObserver r5 = r2.getViewTreeObserver()
            com.google.android.setupdesign.util.HeaderAreaStyler$1 r7 = new com.google.android.setupdesign.util.HeaderAreaStyler$1
            r7.<init>(r2)
            r5.addOnPreDrawListener(r7)
            android.view.ViewGroup$LayoutParams r5 = r2.getLayoutParams()
            float r6 = com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticOutline0.m82m(r4, r4, r6, r3)
            int r6 = (int) r6
            r5.height = r6
            r6 = -2
            r5.width = r6
            android.widget.ImageView$ScaleType r6 = android.widget.ImageView.ScaleType.FIT_CENTER
            r2.setScaleType(r6)
            android.graphics.drawable.Drawable r2 = r2.getDrawable()
            if (r2 == 0) goto L_0x00a6
            int r6 = r2.getIntrinsicWidth()
            int r2 = r2.getIntrinsicHeight()
            int r2 = r2 * 2
            if (r6 <= r2) goto L_0x00a6
            android.content.res.Resources r2 = r4.getResources()
            r6 = 2131167163(0x7f0707bb, float:1.7948592E38)
            float r2 = r2.getDimension(r6)
            int r2 = (int) r2
            int r6 = r5.height
            if (r6 <= r2) goto L_0x00a6
            int r6 = r6 - r2
            r5.height = r2
            goto L_0x00a7
        L_0x00a6:
            r6 = 0
        L_0x00a7:
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r2 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r4)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r5 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_ICON_MARGIN_TOP
            boolean r2 = r2.isPartnerConfigAvailable(r5)
            if (r2 == 0) goto L_0x00cc
            boolean r2 = r1 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r2 == 0) goto L_0x00cc
            android.view.ViewGroup$MarginLayoutParams r1 = (android.view.ViewGroup.MarginLayoutParams) r1
            float r2 = com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticOutline0.m82m(r4, r4, r5, r3)
            int r2 = (int) r2
            int r2 = r2 + r6
            int r4 = r1.leftMargin
            int r5 = r1.rightMargin
            int r6 = r1.bottomMargin
            r1.setMargins(r4, r2, r5, r6)
        L_0x00cc:
            java.lang.Class<com.google.android.setupdesign.template.HeaderMixin> r1 = com.google.android.setupdesign.template.HeaderMixin.class
            com.google.android.setupcompat.template.Mixin r1 = r0.getMixin(r1)
            com.google.android.setupdesign.template.HeaderMixin r1 = (com.google.android.setupdesign.template.HeaderMixin) r1
            java.util.Objects.requireNonNull(r1)
            com.google.android.setupcompat.internal.TemplateLayout r2 = r1.templateLayout
            r4 = 2131428954(0x7f0b065a, float:1.8479567E38)
            android.view.View r2 = r2.findManagedViewById(r4)
            android.widget.TextView r2 = (android.widget.TextView) r2
            com.google.android.setupcompat.internal.TemplateLayout r4 = r1.templateLayout
            boolean r4 = com.google.android.setupdesign.util.PartnerStyleHelper.shouldApplyPartnerResource((android.view.View) r4)
            if (r4 == 0) goto L_0x0156
            com.google.android.setupcompat.internal.TemplateLayout r4 = r1.templateLayout
            r5 = 2131428976(0x7f0b0670, float:1.8479612E38)
            android.view.View r4 = r4.findManagedViewById(r5)
            com.google.android.setupdesign.util.LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(r4)
            if (r2 != 0) goto L_0x00f9
            goto L_0x0118
        L_0x00f9:
            com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs r5 = new com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs
            com.google.android.setupcompat.partnerconfig.PartnerConfig r16 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_HEADER_TEXT_COLOR
            r17 = 0
            com.google.android.setupcompat.partnerconfig.PartnerConfig r18 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_HEADER_TEXT_SIZE
            com.google.android.setupcompat.partnerconfig.PartnerConfig r19 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_HEADER_FONT_FAMILY
            r20 = 0
            com.google.android.setupcompat.partnerconfig.PartnerConfig r21 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_HEADER_TEXT_MARGIN_TOP
            com.google.android.setupcompat.partnerconfig.PartnerConfig r22 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_HEADER_TEXT_MARGIN_BOTTOM
            android.content.Context r6 = r2.getContext()
            int r23 = com.google.android.setupdesign.util.PartnerStyleHelper.getLayoutGravity(r6)
            r15 = r5
            r15.<init>(r16, r17, r18, r19, r20, r21, r22, r23)
            com.google.android.setupdesign.util.TextViewPartnerStyler.applyPartnerCustomizationStyle(r2, r5)
        L_0x0118:
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            if (r4 != 0) goto L_0x011d
            goto L_0x0156
        L_0x011d:
            android.content.Context r5 = r4.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r6 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r5)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r7 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_HEADER_AREA_BACKGROUND_COLOR
            int r6 = r6.getColor(r5, r7)
            r4.setBackgroundColor(r6)
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r6 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r5)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r7 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_HEADER_CONTAINER_MARGIN_BOTTOM
            boolean r6 = r6.isPartnerConfigAvailable(r7)
            if (r6 == 0) goto L_0x0156
            android.view.ViewGroup$LayoutParams r6 = r4.getLayoutParams()
            boolean r8 = r6 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r8 == 0) goto L_0x0156
            r8 = r6
            android.view.ViewGroup$MarginLayoutParams r8 = (android.view.ViewGroup.MarginLayoutParams) r8
            float r3 = com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticOutline0.m82m(r5, r5, r7, r3)
            int r3 = (int) r3
            int r5 = r8.leftMargin
            int r7 = r8.topMargin
            int r9 = r8.rightMargin
            r8.setMargins(r5, r7, r9, r3)
            r4.setLayoutParams(r6)
        L_0x0156:
            r1.tryUpdateAutoTextSizeFlagWithPartnerConfig()
            boolean r3 = r1.autoTextSizeEnabled
            if (r3 == 0) goto L_0x0160
            r1.autoAdjustTextSize(r2)
        L_0x0160:
            java.lang.Class<com.google.android.setupdesign.template.DescriptionMixin> r1 = com.google.android.setupdesign.template.DescriptionMixin.class
            com.google.android.setupcompat.template.Mixin r1 = r0.getMixin(r1)
            com.google.android.setupdesign.template.DescriptionMixin r1 = (com.google.android.setupdesign.template.DescriptionMixin) r1
            java.util.Objects.requireNonNull(r1)
            com.google.android.setupcompat.internal.TemplateLayout r2 = r1.templateLayout
            r3 = 2131428985(0x7f0b0679, float:1.847963E38)
            android.view.View r2 = r2.findManagedViewById(r3)
            r15 = r2
            android.widget.TextView r15 = (android.widget.TextView) r15
            if (r15 == 0) goto L_0x01a1
            com.google.android.setupcompat.internal.TemplateLayout r1 = r1.templateLayout
            boolean r1 = com.google.android.setupdesign.util.PartnerStyleHelper.shouldApplyPartnerResource((android.view.View) r1)
            if (r1 == 0) goto L_0x01a1
            com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs r9 = new com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs
            com.google.android.setupcompat.partnerconfig.PartnerConfig r7 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_TOP
            com.google.android.setupcompat.partnerconfig.PartnerConfig r8 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_BOTTOM
            android.content.Context r1 = r15.getContext()
            int r16 = com.google.android.setupdesign.util.PartnerStyleHelper.getLayoutGravity(r1)
            r1 = r9
            r2 = r14
            r3 = r13
            r4 = r12
            r5 = r11
            r6 = r10
            r17 = r10
            r10 = r9
            r9 = r16
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            com.google.android.setupdesign.util.TextViewPartnerStyler.applyPartnerCustomizationStyle(r15, r10)
            goto L_0x01a3
        L_0x01a1:
            r17 = r10
        L_0x01a3:
            java.lang.Class<com.google.android.setupdesign.template.ProgressBarMixin> r1 = com.google.android.setupdesign.template.ProgressBarMixin.class
            com.google.android.setupcompat.template.Mixin r1 = r0.getMixin(r1)
            com.google.android.setupdesign.template.ProgressBarMixin r1 = (com.google.android.setupdesign.template.ProgressBarMixin) r1
            java.util.Objects.requireNonNull(r1)
            android.widget.ProgressBar r2 = r1.peekProgressBar()
            boolean r3 = r1.useBottomProgressBar
            if (r3 == 0) goto L_0x0253
            if (r2 != 0) goto L_0x01ba
            goto L_0x0253
        L_0x01ba:
            com.google.android.setupcompat.internal.TemplateLayout r1 = r1.templateLayout
            boolean r3 = r1 instanceof com.google.android.setupdesign.GlifLayout
            if (r3 != 0) goto L_0x01c2
            r1 = 0
            goto L_0x01c8
        L_0x01c2:
            com.google.android.setupdesign.GlifLayout r1 = (com.google.android.setupdesign.GlifLayout) r1
            boolean r1 = r1.shouldApplyPartnerHeavyThemeResource()
        L_0x01c8:
            r3 = 2131167195(0x7f0707db, float:1.7948657E38)
            r4 = 2131167196(0x7f0707dc, float:1.7948659E38)
            if (r1 == 0) goto L_0x022c
            android.content.Context r1 = r2.getContext()
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            boolean r5 = r2 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r5 == 0) goto L_0x0253
            android.view.ViewGroup$MarginLayoutParams r2 = (android.view.ViewGroup.MarginLayoutParams) r2
            int r5 = r2.topMargin
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r6 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r1)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r7 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_TOP
            boolean r6 = r6.isPartnerConfigAvailable(r7)
            if (r6 == 0) goto L_0x01fd
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r1)
            android.content.res.Resources r6 = r1.getResources()
            float r4 = r6.getDimension(r4)
            float r4 = r5.getDimension(r1, r7, r4)
            int r5 = (int) r4
        L_0x01fd:
            int r4 = r2.bottomMargin
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r6 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r1)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r7 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_BOTTOM
            boolean r6 = r6.isPartnerConfigAvailable(r7)
            if (r6 == 0) goto L_0x021c
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r4 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r1)
            android.content.res.Resources r6 = r1.getResources()
            float r3 = r6.getDimension(r3)
            float r1 = r4.getDimension(r1, r7, r3)
            int r4 = (int) r1
        L_0x021c:
            int r1 = r2.topMargin
            if (r5 != r1) goto L_0x0224
            int r1 = r2.bottomMargin
            if (r4 == r1) goto L_0x0253
        L_0x0224:
            int r1 = r2.leftMargin
            int r3 = r2.rightMargin
            r2.setMargins(r1, r5, r3, r4)
            goto L_0x0253
        L_0x022c:
            android.content.Context r1 = r2.getContext()
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            boolean r5 = r2 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r5 == 0) goto L_0x0253
            android.content.res.Resources r5 = r1.getResources()
            float r4 = r5.getDimension(r4)
            int r4 = (int) r4
            android.content.res.Resources r1 = r1.getResources()
            float r1 = r1.getDimension(r3)
            int r1 = (int) r1
            android.view.ViewGroup$MarginLayoutParams r2 = (android.view.ViewGroup.MarginLayoutParams) r2
            int r3 = r2.leftMargin
            int r5 = r2.rightMargin
            r2.setMargins(r3, r4, r5, r1)
        L_0x0253:
            r1 = 2131428975(0x7f0b066f, float:1.847961E38)
            android.view.View r1 = r0.findManagedViewById(r1)
            r9 = r1
            android.widget.TextView r9 = (android.widget.TextView) r9
            if (r9 == 0) goto L_0x02a2
            boolean r1 = r0.applyPartnerHeavyThemeResource
            if (r1 == 0) goto L_0x027d
            com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs r10 = new com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs
            android.content.Context r0 = r9.getContext()
            int r8 = com.google.android.setupdesign.util.PartnerStyleHelper.getLayoutGravity(r0)
            r6 = 0
            r7 = 0
            r0 = r10
            r1 = r14
            r2 = r13
            r3 = r12
            r4 = r11
            r5 = r17
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            com.google.android.setupdesign.util.TextViewPartnerStyler.applyPartnerCustomizationStyle(r9, r10)
            goto L_0x02a2
        L_0x027d:
            boolean r0 = r24.shouldApplyPartnerResource()
            if (r0 == 0) goto L_0x02a2
            com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs r0 = new com.google.android.setupdesign.util.TextViewPartnerStyler$TextPartnerConfigs
            android.content.Context r1 = r9.getContext()
            int r1 = com.google.android.setupdesign.util.PartnerStyleHelper.getLayoutGravity(r1)
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r10 = r0
            r18 = r1
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18)
            com.google.android.setupdesign.util.TextViewPartnerStyler.applyPartnerCustomizationVerticalMargins(r9, r0)
            r9.setGravity(r1)
        L_0x02a2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.GlifLayout.onFinishInflate():void");
    }

    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = C1777R.layout.sud_glif_template;
        }
        return inflateTemplate(layoutInflater, 2132017784, i);
    }

    public final boolean shouldApplyPartnerHeavyThemeResource() {
        if (this.applyPartnerHeavyThemeResource || (shouldApplyPartnerResource() && PartnerConfigHelper.shouldApplyExtendedPartnerConfig(getContext()))) {
            return true;
        }
        return false;
    }

    public GlifLayout(Context context, int i, int i2) {
        super(context, i, i2);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init((AttributeSet) null, C1777R.attr.sudLayoutTheme);
    }

    @TargetApi(17)
    public final void tryApplyPartnerCustomizationContentPaddingTopStyle(View view) {
        int m;
        Context context = view.getContext();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_CONTENT_PADDING_TOP;
        boolean isPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
        if (shouldApplyPartnerResource() && isPartnerConfigAvailable && (m = (int) FooterBarMixin$$ExternalSyntheticOutline0.m82m(context, context, partnerConfig, 0.0f)) != view.getPaddingTop()) {
            view.setPadding(view.getPaddingStart(), m, view.getPaddingEnd(), view.getPaddingBottom());
        }
    }

    public final void updateBackground() {
        Drawable drawable;
        if (findManagedViewById(C1777R.C1779id.suc_layout_status) != null) {
            int i = 0;
            ColorStateList colorStateList = this.backgroundBaseColor;
            if (colorStateList != null) {
                i = colorStateList.getDefaultColor();
            } else {
                ColorStateList colorStateList2 = this.primaryColor;
                if (colorStateList2 != null) {
                    i = colorStateList2.getDefaultColor();
                }
            }
            if (this.backgroundPatterned) {
                drawable = new GlifPatternDrawable(i);
            } else {
                drawable = new ColorDrawable(i);
            }
            ((StatusBarMixin) getMixin(StatusBarMixin.class)).setStatusBarBackground(drawable);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00e2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateLandscapeMiddleHorizontalSpacing() {
        /*
            r9 = this;
            android.content.res.Resources r0 = r9.getResources()
            r1 = 2131167149(0x7f0707ad, float:1.7948563E38)
            int r0 = r0.getDimensionPixelSize(r1)
            boolean r1 = r9.shouldApplyPartnerResource()
            r2 = 0
            if (r1 == 0) goto L_0x0036
            android.content.Context r1 = r9.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r1 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r1)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r3 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAND_MIDDLE_HORIZONTAL_SPACING
            boolean r1 = r1.isPartnerConfigAvailable(r3)
            if (r1 == 0) goto L_0x0036
            android.content.Context r0 = r9.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r0 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r0)
            android.content.Context r1 = r9.getContext()
            java.util.Objects.requireNonNull(r0)
            float r0 = r0.getDimension(r1, r3, r2)
            int r0 = (int) r0
        L_0x0036:
            r1 = 2131428971(0x7f0b066b, float:1.8479602E38)
            android.view.View r1 = r9.findManagedViewById(r1)
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L_0x0095
            boolean r5 = r9.shouldApplyPartnerResource()
            if (r5 == 0) goto L_0x006c
            android.content.Context r5 = r9.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r5)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r6 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAYOUT_MARGIN_END
            boolean r5 = r5.isPartnerConfigAvailable(r6)
            if (r5 == 0) goto L_0x006c
            android.content.Context r5 = r9.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r5)
            android.content.Context r7 = r9.getContext()
            java.util.Objects.requireNonNull(r5)
            float r5 = r5.getDimension(r7, r6, r2)
            int r5 = (int) r5
            goto L_0x0083
        L_0x006c:
            android.content.Context r5 = r9.getContext()
            int[] r6 = new int[r3]
            r7 = 2130969887(0x7f04051f, float:1.7548469E38)
            r6[r4] = r7
            android.content.res.TypedArray r5 = r5.obtainStyledAttributes(r6)
            int r6 = r5.getDimensionPixelSize(r4, r4)
            r5.recycle()
            r5 = r6
        L_0x0083:
            int r6 = r0 / 2
            int r6 = r6 - r5
            int r5 = r1.getPaddingStart()
            int r7 = r1.getPaddingTop()
            int r8 = r1.getPaddingBottom()
            r1.setPadding(r5, r7, r6, r8)
        L_0x0095:
            r5 = 2131428970(0x7f0b066a, float:1.84796E38)
            android.view.View r5 = r9.findManagedViewById(r5)
            if (r5 == 0) goto L_0x00f5
            boolean r6 = r9.shouldApplyPartnerResource()
            if (r6 == 0) goto L_0x00c9
            android.content.Context r6 = r9.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r6 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r6)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r7 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAYOUT_MARGIN_START
            boolean r6 = r6.isPartnerConfigAvailable(r7)
            if (r6 == 0) goto L_0x00c9
            android.content.Context r3 = r9.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r3 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r3)
            android.content.Context r9 = r9.getContext()
            java.util.Objects.requireNonNull(r3)
            float r9 = r3.getDimension(r9, r7, r2)
            int r9 = (int) r9
            goto L_0x00e0
        L_0x00c9:
            android.content.Context r9 = r9.getContext()
            int[] r2 = new int[r3]
            r3 = 2130969888(0x7f040520, float:1.754847E38)
            r2[r4] = r3
            android.content.res.TypedArray r9 = r9.obtainStyledAttributes(r2)
            int r2 = r9.getDimensionPixelSize(r4, r4)
            r9.recycle()
            r9 = r2
        L_0x00e0:
            if (r1 == 0) goto L_0x00e6
            int r0 = r0 / 2
            int r4 = r0 - r9
        L_0x00e6:
            int r9 = r5.getPaddingTop()
            int r0 = r5.getPaddingEnd()
            int r1 = r5.getPaddingBottom()
            r5.setPadding(r4, r9, r0, r1)
        L_0x00f5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.GlifLayout.updateLandscapeMiddleHorizontalSpacing():void");
    }

    public GlifLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init(attributeSet, C1777R.attr.sudLayoutTheme);
    }

    @TargetApi(11)
    public GlifLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init(attributeSet, i);
    }
}
