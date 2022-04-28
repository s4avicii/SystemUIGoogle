package com.google.android.setupcompat.template;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$array;
import com.android.systemui.R$id;
import com.android.systemui.plugins.FalsingManager;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.FooterButtonPartnerConfig;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterButton;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class FooterBarMixin implements Mixin {
    public static final AtomicInteger nextGeneratedId = new AtomicInteger(1);
    public final boolean applyDynamicColor;
    public final boolean applyPartnerResources;
    public LinearLayout buttonContainer;
    public final Context context;
    public int defaultPadding;
    public int footerBarPaddingBottom;
    public int footerBarPaddingEnd;
    public int footerBarPaddingStart;
    public int footerBarPaddingTop;
    public final int footerBarPrimaryBackgroundColor;
    public final int footerBarSecondaryBackgroundColor;
    public final ViewStub footerStub;
    public boolean isSecondaryButtonInPrimaryStyle = false;
    public final FooterBarMixinMetrics metrics;
    public FooterButton primaryButton;
    public int primaryButtonId;
    public FooterButtonPartnerConfig primaryButtonPartnerConfigForTesting;
    public FooterButton secondaryButton;
    public int secondaryButtonId;
    public FooterButtonPartnerConfig secondaryButtonPartnerConfigForTesting;
    public final boolean useFullDynamicColor;

    public final LinearLayout ensureFooterInflated() {
        if (this.buttonContainer == null) {
            if (this.footerStub != null) {
                this.footerStub.setLayoutInflater(LayoutInflater.from(new ContextThemeWrapper(this.context, 2132017682)));
                this.footerStub.setLayoutResource(C1777R.layout.suc_footer_button_bar);
                LinearLayout linearLayout = (LinearLayout) this.footerStub.inflate();
                this.buttonContainer = linearLayout;
                if (linearLayout != null) {
                    linearLayout.setId(View.generateViewId());
                    linearLayout.setPadding(this.footerBarPaddingStart, this.footerBarPaddingTop, this.footerBarPaddingEnd, this.footerBarPaddingBottom);
                    if (isFooterButtonAlignedEnd()) {
                        linearLayout.setGravity(8388629);
                    }
                }
                LinearLayout linearLayout2 = this.buttonContainer;
                if (linearLayout2 != null && this.applyPartnerResources) {
                    if (!this.useFullDynamicColor) {
                        linearLayout2.setBackgroundColor(PartnerConfigHelper.get(this.context).getColor(this.context, PartnerConfig.CONFIG_FOOTER_BAR_BG_COLOR));
                    }
                    PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(this.context);
                    PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_TOP;
                    if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(this.context);
                        Context context2 = this.context;
                        Objects.requireNonNull(partnerConfigHelper2);
                        this.footerBarPaddingTop = (int) partnerConfigHelper2.getDimension(context2, partnerConfig, 0.0f);
                    }
                    PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(this.context);
                    PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_BOTTOM;
                    if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig2)) {
                        PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(this.context);
                        Context context3 = this.context;
                        Objects.requireNonNull(partnerConfigHelper4);
                        this.footerBarPaddingBottom = (int) partnerConfigHelper4.getDimension(context3, partnerConfig2, 0.0f);
                    }
                    PartnerConfigHelper partnerConfigHelper5 = PartnerConfigHelper.get(this.context);
                    PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_START;
                    if (partnerConfigHelper5.isPartnerConfigAvailable(partnerConfig3)) {
                        PartnerConfigHelper partnerConfigHelper6 = PartnerConfigHelper.get(this.context);
                        Context context4 = this.context;
                        Objects.requireNonNull(partnerConfigHelper6);
                        this.footerBarPaddingStart = (int) partnerConfigHelper6.getDimension(context4, partnerConfig3, 0.0f);
                    }
                    PartnerConfigHelper partnerConfigHelper7 = PartnerConfigHelper.get(this.context);
                    PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_END;
                    if (partnerConfigHelper7.isPartnerConfigAvailable(partnerConfig4)) {
                        PartnerConfigHelper partnerConfigHelper8 = PartnerConfigHelper.get(this.context);
                        Context context5 = this.context;
                        Objects.requireNonNull(partnerConfigHelper8);
                        this.footerBarPaddingEnd = (int) partnerConfigHelper8.getDimension(context5, partnerConfig4, 0.0f);
                    }
                    linearLayout2.setPadding(this.footerBarPaddingStart, this.footerBarPaddingTop, this.footerBarPaddingEnd, this.footerBarPaddingBottom);
                    PartnerConfigHelper partnerConfigHelper9 = PartnerConfigHelper.get(this.context);
                    PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_FOOTER_BAR_MIN_HEIGHT;
                    if (partnerConfigHelper9.isPartnerConfigAvailable(partnerConfig5)) {
                        PartnerConfigHelper partnerConfigHelper10 = PartnerConfigHelper.get(this.context);
                        Context context6 = this.context;
                        Objects.requireNonNull(partnerConfigHelper10);
                        int dimension = (int) partnerConfigHelper10.getDimension(context6, partnerConfig5, 0.0f);
                        if (dimension > 0) {
                            linearLayout2.setMinimumHeight(dimension);
                        }
                    }
                }
            } else {
                throw new IllegalStateException("Footer stub is not found in this template");
            }
        }
        return this.buttonContainer;
    }

    public int getPaddingBottom() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout != null) {
            return linearLayout.getPaddingBottom();
        }
        return this.footerStub.getPaddingBottom();
    }

    public int getPaddingTop() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout != null) {
            return linearLayout.getPaddingTop();
        }
        return this.footerStub.getPaddingTop();
    }

    public Button getPrimaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.primaryButtonId);
    }

    public Button getSecondaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.secondaryButtonId);
    }

    public int getVisibility() {
        return this.buttonContainer.getVisibility();
    }

    public final FooterActionButton inflateButton(FooterButton footerButton, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        FooterActionButton footerActionButton = (FooterActionButton) LayoutInflater.from(new ContextThemeWrapper(this.context, footerButtonPartnerConfig.partnerTheme)).inflate(C1777R.layout.suc_button, (ViewGroup) null, false);
        footerActionButton.setId(View.generateViewId());
        Objects.requireNonNull(footerButton);
        footerActionButton.setText(footerButton.text);
        footerActionButton.setOnClickListener(footerButton);
        footerActionButton.setVisibility(0);
        footerActionButton.setEnabled(footerButton.enabled);
        footerActionButton.footerButton = footerButton;
        final int id = footerActionButton.getId();
        footerButton.buttonListener = new FooterButton.OnButtonEventListener() {
        };
        return footerActionButton;
    }

    public final boolean isFooterButtonAlignedEnd() {
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(this.context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ALIGNED_END;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            return PartnerConfigHelper.get(this.context).getBoolean(this.context, partnerConfig, false);
        }
        return false;
    }

    @TargetApi(29)
    public final void onFooterButtonApplyPartnerResource(FooterActionButton footerActionButton, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        boolean z;
        int i;
        RippleDrawable rippleDrawable;
        int i2;
        GradientDrawable gradientDrawable;
        Drawable drawable;
        Drawable drawable2;
        if (this.applyPartnerResources) {
            Context context2 = this.context;
            boolean z2 = this.applyDynamicColor;
            if (footerActionButton.getId() == this.primaryButtonId) {
                z = true;
            } else {
                z = false;
            }
            FooterButtonStyleUtils.defaultTextColor.put(Integer.valueOf(footerActionButton.getId()), footerActionButton.getTextColors());
            if (!z2) {
                if (footerActionButton.isEnabled()) {
                    int color = PartnerConfigHelper.get(context2).getColor(context2, footerButtonPartnerConfig.buttonTextColorConfig);
                    if (color != 0) {
                        footerActionButton.setTextColor(ColorStateList.valueOf(color));
                    }
                } else {
                    FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(context2, footerActionButton, footerButtonPartnerConfig.buttonDisableTextColorConfig);
                }
                PartnerConfig partnerConfig = footerButtonPartnerConfig.buttonBackgroundConfig;
                PartnerConfig partnerConfig2 = footerButtonPartnerConfig.buttonDisableAlphaConfig;
                PartnerConfig partnerConfig3 = footerButtonPartnerConfig.buttonDisableBackgroundConfig;
                int color2 = PartnerConfigHelper.get(context2).getColor(context2, partnerConfig);
                float fraction = PartnerConfigHelper.get(context2).getFraction(context2, partnerConfig2);
                int color3 = PartnerConfigHelper.get(context2).getColor(context2, partnerConfig3);
                int[] iArr = {-16842910};
                int[] iArr2 = new int[0];
                if (color2 != 0) {
                    if (fraction <= 0.0f) {
                        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(new int[]{16842803});
                        fraction = obtainStyledAttributes.getFloat(0, 0.26f);
                        obtainStyledAttributes.recycle();
                    }
                    if (color3 == 0) {
                        color3 = color2;
                    }
                    ColorStateList colorStateList = new ColorStateList(new int[][]{iArr, iArr2}, new int[]{Color.argb((int) (fraction * 255.0f), Color.red(color3), Color.green(color3), Color.blue(color3)), color2});
                    footerActionButton.getBackground().mutate().setState(new int[0]);
                    footerActionButton.refreshDrawableState();
                    footerActionButton.setBackgroundTintList(colorStateList);
                }
            }
            PartnerConfig partnerConfig4 = footerButtonPartnerConfig.buttonTextColorConfig;
            PartnerConfig partnerConfig5 = footerButtonPartnerConfig.buttonRippleColorAlphaConfig;
            if (z2) {
                i = footerActionButton.getTextColors().getDefaultColor();
            } else {
                i = PartnerConfigHelper.get(context2).getColor(context2, partnerConfig4);
            }
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context2);
            Objects.requireNonNull(partnerConfigHelper);
            float fraction2 = partnerConfigHelper.getFraction(context2, partnerConfig5);
            Drawable background = footerActionButton.getBackground();
            if (background instanceof InsetDrawable) {
                rippleDrawable = (RippleDrawable) ((InsetDrawable) background).getDrawable();
            } else if (background instanceof RippleDrawable) {
                rippleDrawable = (RippleDrawable) background;
            } else {
                rippleDrawable = null;
            }
            if (rippleDrawable != null) {
                int argb = Color.argb((int) (fraction2 * 255.0f), Color.red(i), Color.green(i), Color.blue(i));
                rippleDrawable.setColor(new ColorStateList(new int[][]{new int[]{16842919}, new int[]{16842908}, StateSet.NOTHING}, new int[]{argb, argb, 0}));
            }
            PartnerConfig partnerConfig6 = footerButtonPartnerConfig.buttonMarginStartConfig;
            ViewGroup.LayoutParams layoutParams = footerActionButton.getLayoutParams();
            if (PartnerConfigHelper.get(context2).isPartnerConfigAvailable(partnerConfig6) && (layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.setMargins((int) FooterBarMixin$$ExternalSyntheticOutline0.m82m(context2, context2, partnerConfig6, 0.0f), marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            }
            float m = FooterBarMixin$$ExternalSyntheticOutline0.m82m(context2, context2, footerButtonPartnerConfig.buttonTextSizeConfig, 0.0f);
            if (m > 0.0f) {
                footerActionButton.setTextSize(0, m);
            }
            PartnerConfig partnerConfig7 = footerButtonPartnerConfig.buttonMinHeightConfig;
            if (PartnerConfigHelper.get(context2).isPartnerConfigAvailable(partnerConfig7)) {
                float m2 = FooterBarMixin$$ExternalSyntheticOutline0.m82m(context2, context2, partnerConfig7, 0.0f);
                if (m2 > 0.0f) {
                    footerActionButton.setMinHeight((int) m2);
                }
            }
            PartnerConfig partnerConfig8 = footerButtonPartnerConfig.buttonTextTypeFaceConfig;
            PartnerConfig partnerConfig9 = footerButtonPartnerConfig.buttonTextStyleConfig;
            String string = PartnerConfigHelper.get(context2).getString(context2, partnerConfig8);
            if (PartnerConfigHelper.get(context2).isPartnerConfigAvailable(partnerConfig9)) {
                i2 = PartnerConfigHelper.get(context2).getInteger(context2, partnerConfig9);
            } else {
                i2 = 0;
            }
            Typeface create = Typeface.create(string, i2);
            if (create != null) {
                footerActionButton.setTypeface(create);
            }
            float m3 = FooterBarMixin$$ExternalSyntheticOutline0.m82m(context2, context2, footerButtonPartnerConfig.buttonRadiusConfig, 0.0f);
            Drawable background2 = footerActionButton.getBackground();
            if (background2 instanceof InsetDrawable) {
                gradientDrawable = (GradientDrawable) ((LayerDrawable) ((InsetDrawable) background2).getDrawable()).getDrawable(0);
            } else if (background2 instanceof RippleDrawable) {
                RippleDrawable rippleDrawable2 = (RippleDrawable) background2;
                if (rippleDrawable2.getDrawable(0) instanceof GradientDrawable) {
                    gradientDrawable = (GradientDrawable) rippleDrawable2.getDrawable(0);
                } else {
                    gradientDrawable = (GradientDrawable) ((InsetDrawable) rippleDrawable2.getDrawable(0)).getDrawable();
                }
            } else {
                gradientDrawable = null;
            }
            if (gradientDrawable != null) {
                gradientDrawable.setCornerRadius(m3);
            }
            PartnerConfig partnerConfig10 = footerButtonPartnerConfig.buttonIconConfig;
            if (partnerConfig10 != null) {
                drawable = PartnerConfigHelper.get(context2).getDrawable(context2, partnerConfig10);
            } else {
                drawable = null;
            }
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            if (z) {
                drawable2 = drawable;
                drawable = null;
            } else {
                drawable2 = null;
            }
            footerActionButton.setCompoundDrawablesRelative(drawable, (Drawable) null, drawable2, (Drawable) null);
            if (!this.applyDynamicColor) {
                PartnerConfig partnerConfig11 = footerButtonPartnerConfig.buttonTextColorConfig;
                PartnerConfig partnerConfig12 = footerButtonPartnerConfig.buttonDisableTextColorConfig;
                if (footerActionButton.isEnabled()) {
                    Context context3 = this.context;
                    int color4 = PartnerConfigHelper.get(context3).getColor(context3, partnerConfig11);
                    if (color4 != 0) {
                        footerActionButton.setTextColor(ColorStateList.valueOf(color4));
                        return;
                    }
                    return;
                }
                FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(this.context, footerActionButton, partnerConfig12);
            }
        }
    }

    public final void onFooterButtonInflated(FooterActionButton footerActionButton, int i) {
        boolean z;
        if (i != 0) {
            HashMap<Integer, ColorStateList> hashMap = FooterButtonStyleUtils.defaultTextColor;
            footerActionButton.getBackground().mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
        this.buttonContainer.addView(footerActionButton);
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        boolean z2 = true;
        int i2 = 0;
        if (primaryButtonView == null || primaryButtonView.getVisibility() != 0) {
            z = false;
        } else {
            z = true;
        }
        if (secondaryButtonView == null || secondaryButtonView.getVisibility() != 0) {
            z2 = false;
        }
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout != null) {
            if (!z && !z2) {
                i2 = 8;
            }
            linearLayout.setVisibility(i2);
        }
    }

    public final void setPrimaryButton(FooterButton footerButton) {
        FooterButton footerButton2 = footerButton;
        R$array.ensureOnMainThread("setPrimaryButton");
        ensureFooterInflated();
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR;
        FooterButtonPartnerConfig footerButtonPartnerConfig = new FooterButtonPartnerConfig(getPartnerTheme(footerButton2, 2132017679, partnerConfig), partnerConfig, PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA, PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR, PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR, getDrawablePartnerConfig(footerButton2.buttonType), PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR, PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_MARGIN_START, PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE, PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT, PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY, PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE, PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS, PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA);
        FooterActionButton inflateButton = inflateButton(footerButton2, footerButtonPartnerConfig);
        this.primaryButtonId = inflateButton.getId();
        inflateButton.isPrimaryButtonStyle = true;
        this.primaryButton = footerButton2;
        this.primaryButtonPartnerConfigForTesting = footerButtonPartnerConfig;
        onFooterButtonInflated(inflateButton, this.footerBarPrimaryBackgroundColor);
        onFooterButtonApplyPartnerResource(inflateButton, footerButtonPartnerConfig);
        repopulateButtons();
    }

    public final void setSecondaryButton(FooterButton footerButton) {
        FooterButton footerButton2 = footerButton;
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_BG_COLOR;
        R$array.ensureOnMainThread("setSecondaryButton");
        this.isSecondaryButtonInPrimaryStyle = false;
        ensureFooterInflated();
        FooterButtonPartnerConfig footerButtonPartnerConfig = r2;
        FooterButtonPartnerConfig footerButtonPartnerConfig2 = new FooterButtonPartnerConfig(getPartnerTheme(footerButton2, 2132017680, partnerConfig), partnerConfig, PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA, PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR, PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_DISABLED_TEXT_COLOR, getDrawablePartnerConfig(footerButton2.buttonType), PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_TEXT_COLOR, PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_MARGIN_START, PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE, PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT, PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY, PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE, PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS, PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA);
        FooterActionButton inflateButton = inflateButton(footerButton2, footerButtonPartnerConfig2);
        this.secondaryButtonId = inflateButton.getId();
        inflateButton.isPrimaryButtonStyle = false;
        this.secondaryButton = footerButton2;
        this.secondaryButtonPartnerConfigForTesting = footerButtonPartnerConfig2;
        onFooterButtonInflated(inflateButton, this.footerBarSecondaryBackgroundColor);
        onFooterButtonApplyPartnerResource(inflateButton, footerButtonPartnerConfig2);
        repopulateButtons();
    }

    /* JADX INFO: finally extract failed */
    public FooterBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        String str;
        FooterBarMixinMetrics footerBarMixinMetrics = new FooterBarMixinMetrics();
        this.metrics = footerBarMixinMetrics;
        Context context2 = templateLayout.getContext();
        this.context = context2;
        this.footerStub = (ViewStub) templateLayout.findManagedViewById(C1777R.C1779id.suc_layout_footer);
        boolean z3 = templateLayout instanceof PartnerCustomizationLayout;
        boolean z4 = true;
        if (!z3 || !((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource()) {
            z = false;
        } else {
            z = true;
        }
        this.applyPartnerResources = z;
        if (!z3 || !((PartnerCustomizationLayout) templateLayout).shouldApplyDynamicColor()) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.applyDynamicColor = z2;
        this.useFullDynamicColor = (!z3 || !((PartnerCustomizationLayout) templateLayout).useFullDynamicColor()) ? false : z4;
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R$id.SucFooterBarMixin, i, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(11, 0);
        this.defaultPadding = dimensionPixelSize;
        this.footerBarPaddingTop = obtainStyledAttributes.getDimensionPixelSize(10, dimensionPixelSize);
        this.footerBarPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(7, this.defaultPadding);
        this.footerBarPaddingStart = obtainStyledAttributes.getDimensionPixelSize(9, 0);
        this.footerBarPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        this.footerBarPrimaryBackgroundColor = obtainStyledAttributes.getColor(12, 0);
        this.footerBarSecondaryBackgroundColor = obtainStyledAttributes.getColor(14, 0);
        int resourceId = obtainStyledAttributes.getResourceId(13, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(15, 0);
        obtainStyledAttributes.recycle();
        FooterButtonInflater footerButtonInflater = new FooterButtonInflater(context2);
        String str2 = "VisibleUsingXml";
        if (resourceId2 != 0) {
            XmlResourceParser xml = context2.getResources().getXml(resourceId2);
            try {
                FooterButton inflate = footerButtonInflater.inflate(xml);
                xml.close();
                setSecondaryButton(inflate);
                if (footerBarMixinMetrics.primaryButtonVisibility.equals("Unknown")) {
                    str = str2;
                } else {
                    str = footerBarMixinMetrics.primaryButtonVisibility;
                }
                footerBarMixinMetrics.primaryButtonVisibility = str;
            } catch (Throwable th) {
                xml.close();
                throw th;
            }
        }
        if (resourceId != 0) {
            XmlResourceParser xml2 = context2.getResources().getXml(resourceId);
            try {
                FooterButton inflate2 = footerButtonInflater.inflate(xml2);
                xml2.close();
                setPrimaryButton(inflate2);
                footerBarMixinMetrics.secondaryButtonVisibility = !footerBarMixinMetrics.secondaryButtonVisibility.equals("Unknown") ? footerBarMixinMetrics.secondaryButtonVisibility : str2;
            } catch (Throwable th2) {
                xml2.close();
                throw th2;
            }
        }
        FooterButtonStyleUtils.defaultTextColor.clear();
    }

    public static PartnerConfig getDrawablePartnerConfig(int i) {
        switch (i) {
            case 1:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_ADD_ANOTHER;
            case 2:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CANCEL;
            case 3:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CLEAR;
            case 4:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_DONE;
            case 5:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_NEXT;
            case FalsingManager.VERSION:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_OPT_IN;
            case 7:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_SKIP;
            case 8:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_STOP;
            default:
                return null;
        }
    }

    public final View addSpace() {
        LinearLayout ensureFooterInflated = ensureFooterInflated();
        View view = new View(this.context);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1.0f));
        view.setVisibility(4);
        ensureFooterInflated.addView(view);
        return view;
    }

    public final int getPartnerTheme(FooterButton footerButton, int i, PartnerConfig partnerConfig) {
        Objects.requireNonNull(footerButton);
        int i2 = footerButton.theme;
        if (i2 != 0 && !this.applyPartnerResources) {
            i = i2;
        }
        if (!this.applyPartnerResources) {
            return i;
        }
        int color = PartnerConfigHelper.get(this.context).getColor(this.context, partnerConfig);
        if (color == 0) {
            return 2132017680;
        }
        if (color != 0) {
            return 2132017679;
        }
        return i;
    }

    public boolean isPrimaryButtonVisible() {
        if (getPrimaryButtonView() == null || getPrimaryButtonView().getVisibility() != 0) {
            return false;
        }
        return true;
    }

    public boolean isSecondaryButtonVisible() {
        if (getSecondaryButtonView() == null || getSecondaryButtonView().getVisibility() != 0) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void repopulateButtons() {
        /*
            r10 = this;
            android.widget.LinearLayout r0 = r10.ensureFooterInflated()
            android.widget.Button r1 = r10.getPrimaryButtonView()
            android.widget.Button r2 = r10.getSecondaryButtonView()
            r0.removeAllViews()
            boolean r3 = r10.isSecondaryButtonInPrimaryStyle
            r4 = 1
            r5 = 0
            if (r3 != 0) goto L_0x0017
        L_0x0015:
            r3 = r5
            goto L_0x005e
        L_0x0017:
            android.content.Context r3 = r10.context
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r3)
            android.content.Context r3 = r10.context
            android.content.res.Resources r3 = r3.getResources()
            android.content.res.Configuration r3 = r3.getConfiguration()
            int r3 = r3.smallestScreenWidthDp
            r6 = 600(0x258, float:8.41E-43)
            if (r3 < r6) goto L_0x0015
            android.content.Context r3 = r10.context
            android.os.Bundle r6 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyNeutralButtonStyleBundle
            java.lang.String r7 = "isNeutralButtonStyleEnabled"
            if (r6 != 0) goto L_0x004e
            r6 = 0
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ IllegalArgumentException | SecurityException -> 0x0044 }
            android.net.Uri r8 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.getContentUri()     // Catch:{ IllegalArgumentException | SecurityException -> 0x0044 }
            android.os.Bundle r3 = r3.call(r8, r7, r6, r6)     // Catch:{ IllegalArgumentException | SecurityException -> 0x0044 }
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyNeutralButtonStyleBundle = r3     // Catch:{ IllegalArgumentException | SecurityException -> 0x0044 }
            goto L_0x004e
        L_0x0044:
            java.lang.String r3 = "PartnerConfigHelper"
            java.lang.String r7 = "Neutral button style supporting status unknown; return as false."
            android.util.Log.w(r3, r7)
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyNeutralButtonStyleBundle = r6
            goto L_0x005a
        L_0x004e:
            android.os.Bundle r3 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyNeutralButtonStyleBundle
            if (r3 == 0) goto L_0x005a
            boolean r3 = r3.getBoolean(r7, r5)
            if (r3 == 0) goto L_0x005a
            r3 = r4
            goto L_0x005b
        L_0x005a:
            r3 = r5
        L_0x005b:
            if (r3 == 0) goto L_0x0015
            r3 = r4
        L_0x005e:
            android.content.Context r6 = r10.context
            android.content.res.Resources r6 = r6.getResources()
            android.content.res.Configuration r6 = r6.getConfiguration()
            int r6 = r6.orientation
            r7 = 2
            if (r6 != r7) goto L_0x006e
            goto L_0x006f
        L_0x006e:
            r4 = r5
        L_0x006f:
            if (r4 == 0) goto L_0x007c
            if (r3 == 0) goto L_0x007c
            boolean r6 = r10.isFooterButtonAlignedEnd()
            if (r6 == 0) goto L_0x007c
            r10.addSpace()
        L_0x007c:
            if (r2 == 0) goto L_0x0098
            boolean r6 = r10.isSecondaryButtonInPrimaryStyle
            if (r6 == 0) goto L_0x0095
            int r6 = r0.getPaddingRight()
            int r7 = r0.getPaddingTop()
            int r8 = r0.getPaddingRight()
            int r9 = r0.getPaddingBottom()
            r0.setPadding(r6, r7, r8, r9)
        L_0x0095:
            r0.addView(r2)
        L_0x0098:
            boolean r6 = r10.isFooterButtonAlignedEnd()
            if (r6 != 0) goto L_0x00a7
            if (r3 == 0) goto L_0x00a4
            if (r3 == 0) goto L_0x00a7
            if (r4 == 0) goto L_0x00a7
        L_0x00a4:
            r10.addSpace()
        L_0x00a7:
            if (r1 == 0) goto L_0x00ac
            r0.addView(r1)
        L_0x00ac:
            if (r1 == 0) goto L_0x00d3
            if (r2 == 0) goto L_0x00d3
            if (r3 == 0) goto L_0x00d3
            android.view.ViewGroup$LayoutParams r10 = r1.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r10 = (android.widget.LinearLayout.LayoutParams) r10
            r0 = 1065353216(0x3f800000, float:1.0)
            if (r10 == 0) goto L_0x00c3
            r10.width = r5
            r10.weight = r0
            r1.setLayoutParams(r10)
        L_0x00c3:
            android.view.ViewGroup$LayoutParams r10 = r2.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r10 = (android.widget.LinearLayout.LayoutParams) r10
            if (r10 == 0) goto L_0x00f7
            r10.width = r5
            r10.weight = r0
            r2.setLayoutParams(r10)
            goto L_0x00f7
        L_0x00d3:
            r10 = 0
            r0 = -2
            if (r1 == 0) goto L_0x00e6
            android.view.ViewGroup$LayoutParams r3 = r1.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r3 = (android.widget.LinearLayout.LayoutParams) r3
            if (r3 == 0) goto L_0x00e6
            r3.width = r0
            r3.weight = r10
            r1.setLayoutParams(r3)
        L_0x00e6:
            if (r2 == 0) goto L_0x00f7
            android.view.ViewGroup$LayoutParams r1 = r2.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r1 = (android.widget.LinearLayout.LayoutParams) r1
            if (r1 == 0) goto L_0x00f7
            r1.width = r0
            r1.weight = r10
            r2.setLayoutParams(r1)
        L_0x00f7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupcompat.template.FooterBarMixin.repopulateButtons():void");
    }

    public LinearLayout getButtonContainer() {
        return this.buttonContainer;
    }
}
