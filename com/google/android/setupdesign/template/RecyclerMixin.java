package com.google.android.setupdesign.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticOutline0;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.DividerItemDecoration;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.items.ItemHierarchy;
import com.google.android.setupdesign.items.ItemInflater;
import com.google.android.setupdesign.items.RecyclerItemAdapter;
import com.google.android.setupdesign.util.DrawableLayoutDirectionHelper;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.view.HeaderRecyclerView;
import java.util.Objects;

public final class RecyclerMixin implements Mixin {
    public Drawable defaultDivider;
    public InsetDrawable divider;
    public DividerItemDecoration dividerDecoration;
    public int dividerInsetEnd;
    public int dividerInsetStart;
    public View header;
    public boolean isDividerDisplay = true;
    public final RecyclerView recyclerView;
    public final TemplateLayout templateLayout;

    /* JADX INFO: finally extract failed */
    public final void parseAttributes(AttributeSet attributeSet) {
        boolean z;
        boolean z2;
        Context context = this.templateLayout.getContext();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudRecyclerMixin, 0, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            ItemInflater itemInflater = new ItemInflater(context);
            XmlResourceParser xml = itemInflater.resources.getXml(resourceId);
            try {
                Object inflate = itemInflater.inflate(xml);
                xml.close();
                ItemHierarchy itemHierarchy = (ItemHierarchy) inflate;
                TemplateLayout templateLayout2 = this.templateLayout;
                if (templateLayout2 instanceof GlifLayout) {
                    z2 = ((GlifLayout) templateLayout2).shouldApplyPartnerHeavyThemeResource();
                    z = ((GlifLayout) this.templateLayout).useFullDynamicColor();
                } else {
                    z2 = false;
                    z = false;
                }
                RecyclerItemAdapter recyclerItemAdapter = new RecyclerItemAdapter(itemHierarchy, z2, z);
                recyclerItemAdapter.setHasStableIds(obtainStyledAttributes.getBoolean(4, false));
                this.recyclerView.setAdapter(recyclerItemAdapter);
            } catch (Throwable th) {
                xml.close();
                throw th;
            }
        }
        if (!this.isDividerDisplay) {
            obtainStyledAttributes.recycle();
            return;
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        if (dimensionPixelSize != -1) {
            this.dividerInsetStart = dimensionPixelSize;
            this.dividerInsetEnd = 0;
            updateDivider();
        } else {
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(3, 0);
            int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(2, 0);
            if (PartnerStyleHelper.shouldApplyPartnerResource((View) this.templateLayout)) {
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    dimensionPixelSize2 = (int) FooterBarMixin$$ExternalSyntheticOutline0.m82m(context, context, partnerConfig, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
                if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                    dimensionPixelSize3 = (int) FooterBarMixin$$ExternalSyntheticOutline0.m82m(context, context, partnerConfig2, 0.0f);
                }
            }
            this.dividerInsetStart = dimensionPixelSize2;
            this.dividerInsetEnd = dimensionPixelSize3;
            updateDivider();
        }
        obtainStyledAttributes.recycle();
    }

    public final void updateDivider() {
        if (this.templateLayout.isLayoutDirectionResolved()) {
            if (this.defaultDivider == null) {
                DividerItemDecoration dividerItemDecoration = this.dividerDecoration;
                Objects.requireNonNull(dividerItemDecoration);
                this.defaultDivider = dividerItemDecoration.divider;
            }
            InsetDrawable createRelativeInsetDrawable = DrawableLayoutDirectionHelper.createRelativeInsetDrawable(this.defaultDivider, this.dividerInsetStart, this.dividerInsetEnd, this.templateLayout);
            this.divider = createRelativeInsetDrawable;
            DividerItemDecoration dividerItemDecoration2 = this.dividerDecoration;
            Objects.requireNonNull(dividerItemDecoration2);
            dividerItemDecoration2.dividerIntrinsicHeight = createRelativeInsetDrawable.getIntrinsicHeight();
            dividerItemDecoration2.divider = createRelativeInsetDrawable;
        }
    }

    public RecyclerMixin(TemplateLayout templateLayout2, RecyclerView recyclerView2) {
        boolean z = true;
        this.templateLayout = templateLayout2;
        this.dividerDecoration = new DividerItemDecoration(templateLayout2.getContext());
        this.recyclerView = recyclerView2;
        templateLayout2.getContext();
        recyclerView2.setLayoutManager(new LinearLayoutManager(1));
        if (recyclerView2 instanceof HeaderRecyclerView) {
            this.header = ((HeaderRecyclerView) recyclerView2).header;
        }
        if (PartnerStyleHelper.shouldApplyPartnerResource((View) templateLayout2)) {
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(recyclerView2.getContext());
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_ITEMS_DIVIDER_SHOWN;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                z = PartnerConfigHelper.get(recyclerView2.getContext()).getBoolean(recyclerView2.getContext(), partnerConfig, true);
            }
        }
        this.isDividerDisplay = z;
        if (z) {
            recyclerView2.addItemDecoration(this.dividerDecoration);
        }
    }
}
