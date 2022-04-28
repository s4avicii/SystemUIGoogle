package com.android.systemui.privacy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.collections.EmptyList;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: OngoingPrivacyChip.kt */
public final class OngoingPrivacyChip extends FrameLayout {
    public int iconColor;
    public int iconMargin;
    public int iconSize;
    public LinearLayout iconsContainer;
    public List<PrivacyItem> privacyList;

    public OngoingPrivacyChip(Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.privacyList = EmptyList.INSTANCE;
    }

    public final void setPrivacyList(List<PrivacyItem> list) {
        this.privacyList = list;
        PrivacyChipBuilder privacyChipBuilder = new PrivacyChipBuilder(getContext(), this.privacyList);
        LinearLayout linearLayout = null;
        if (!this.privacyList.isEmpty()) {
            int i = 0;
            setContentDescription(getContext().getString(C1777R.string.ongoing_privacy_chip_content_multiple_apps, new Object[]{privacyChipBuilder.joinTypes()}));
            LinearLayout linearLayout2 = this.iconsContainer;
            if (linearLayout2 == null) {
                linearLayout2 = null;
            }
            linearLayout2.removeAllViews();
            Iterator it = privacyChipBuilder.generateIcons().iterator();
            while (it.hasNext()) {
                Object next = it.next();
                int i2 = i + 1;
                if (i >= 0) {
                    Drawable drawable = (Drawable) next;
                    drawable.mutate();
                    drawable.setTint(this.iconColor);
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageDrawable(drawable);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    int i3 = this.iconSize;
                    linearLayout2.addView(imageView, i3, i3);
                    if (i != 0) {
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                        marginLayoutParams.setMarginStart(this.iconMargin);
                        imageView.setLayoutParams(marginLayoutParams);
                    }
                    i = i2;
                } else {
                    SetsKt__SetsKt.throwIndexOverflow();
                    throw null;
                }
            }
        } else {
            LinearLayout linearLayout3 = this.iconsContainer;
            if (linearLayout3 != null) {
                linearLayout = linearLayout3;
            }
            linearLayout.removeAllViews();
        }
        requestLayout();
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.iconsContainer = (LinearLayout) requireViewById(C1777R.C1779id.icons_container);
        this.iconMargin = getContext().getResources().getDimensionPixelSize(C1777R.dimen.ongoing_appops_chip_icon_margin);
        this.iconSize = getContext().getResources().getDimensionPixelSize(C1777R.dimen.ongoing_appops_chip_icon_size);
        this.iconColor = Utils.getColorAttrDefaultColor(getContext(), 16843827);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(C1777R.dimen.ongoing_appops_chip_side_padding);
        LinearLayout linearLayout = this.iconsContainer;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            linearLayout = null;
        }
        linearLayout.setPaddingRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
        LinearLayout linearLayout3 = this.iconsContainer;
        if (linearLayout3 != null) {
            linearLayout2 = linearLayout3;
        }
        linearLayout2.setBackground(getContext().getDrawable(C1777R.C1778drawable.privacy_chip_bg));
    }
}
