package com.android.systemui.media;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.util.animation.TransitionLayout;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: RecommendationViewHolder.kt */
public final class RecommendationViewHolder {
    public static final Set<Integer> controlsIds = SetsKt__SetsKt.setOf(Integer.valueOf(C1777R.C1779id.recommendation_card_icon), Integer.valueOf(C1777R.C1779id.recommendation_card_text), Integer.valueOf(C1777R.C1779id.media_cover1), Integer.valueOf(C1777R.C1779id.media_cover2), Integer.valueOf(C1777R.C1779id.media_cover3), Integer.valueOf(C1777R.C1779id.media_cover4), Integer.valueOf(C1777R.C1779id.media_cover5), Integer.valueOf(C1777R.C1779id.media_cover6), Integer.valueOf(C1777R.C1779id.media_cover1_container), Integer.valueOf(C1777R.C1779id.media_cover2_container), Integer.valueOf(C1777R.C1779id.media_cover3_container), Integer.valueOf(C1777R.C1779id.media_cover4_container), Integer.valueOf(C1777R.C1779id.media_cover5_container), Integer.valueOf(C1777R.C1779id.media_cover6_container));
    public static final Set<Integer> gutsIds = SetsKt__SetsKt.setOf(Integer.valueOf(C1777R.C1779id.remove_text), Integer.valueOf(C1777R.C1779id.cancel), Integer.valueOf(C1777R.C1779id.dismiss), Integer.valueOf(C1777R.C1779id.settings));
    public final View cancel;
    public final ImageView cardIcon;
    public final TextView cardText;
    public final ViewGroup dismiss;
    public final View dismissLabel;
    public final TextView longPressText;
    public final List<ViewGroup> mediaCoverContainers;
    public final List<Integer> mediaCoverContainersResIds = SetsKt__SetsKt.listOf(Integer.valueOf(C1777R.C1779id.media_cover1_container), Integer.valueOf(C1777R.C1779id.media_cover2_container), Integer.valueOf(C1777R.C1779id.media_cover3_container), Integer.valueOf(C1777R.C1779id.media_cover4_container), Integer.valueOf(C1777R.C1779id.media_cover5_container), Integer.valueOf(C1777R.C1779id.media_cover6_container));
    public final List<ImageView> mediaCoverItems;
    public final List<Integer> mediaCoverItemsResIds = SetsKt__SetsKt.listOf(Integer.valueOf(C1777R.C1779id.media_cover1), Integer.valueOf(C1777R.C1779id.media_cover2), Integer.valueOf(C1777R.C1779id.media_cover3), Integer.valueOf(C1777R.C1779id.media_cover4), Integer.valueOf(C1777R.C1779id.media_cover5), Integer.valueOf(C1777R.C1779id.media_cover6));
    public final TransitionLayout recommendations;
    public final View settings;
    public final TextView settingsText;

    public RecommendationViewHolder(View view) {
        View view2 = view;
        TransitionLayout transitionLayout = (TransitionLayout) view2;
        this.recommendations = transitionLayout;
        this.cardIcon = (ImageView) view2.requireViewById(C1777R.C1779id.recommendation_card_icon);
        this.cardText = (TextView) view2.requireViewById(C1777R.C1779id.recommendation_card_text);
        this.mediaCoverItems = SetsKt__SetsKt.listOf((ImageView) view2.requireViewById(C1777R.C1779id.media_cover1), (ImageView) view2.requireViewById(C1777R.C1779id.media_cover2), (ImageView) view2.requireViewById(C1777R.C1779id.media_cover3), (ImageView) view2.requireViewById(C1777R.C1779id.media_cover4), (ImageView) view2.requireViewById(C1777R.C1779id.media_cover5), (ImageView) view2.requireViewById(C1777R.C1779id.media_cover6));
        List<ViewGroup> listOf = SetsKt__SetsKt.listOf((ViewGroup) view2.requireViewById(C1777R.C1779id.media_cover1_container), (ViewGroup) view2.requireViewById(C1777R.C1779id.media_cover2_container), (ViewGroup) view2.requireViewById(C1777R.C1779id.media_cover3_container), (ViewGroup) view2.requireViewById(C1777R.C1779id.media_cover4_container), (ViewGroup) view2.requireViewById(C1777R.C1779id.media_cover5_container), (ViewGroup) view2.requireViewById(C1777R.C1779id.media_cover6_container));
        this.mediaCoverContainers = listOf;
        this.longPressText = (TextView) view2.requireViewById(C1777R.C1779id.remove_text);
        this.cancel = view2.requireViewById(C1777R.C1779id.cancel);
        ViewGroup viewGroup = (ViewGroup) view2.requireViewById(C1777R.C1779id.dismiss);
        this.dismiss = viewGroup;
        this.dismissLabel = viewGroup.getChildAt(0);
        this.settings = view2.requireViewById(C1777R.C1779id.settings);
        this.settingsText = (TextView) view2.requireViewById(C1777R.C1779id.settings_text);
        Drawable background = transitionLayout.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type com.android.systemui.media.IlluminationDrawable");
        IlluminationDrawable illuminationDrawable = (IlluminationDrawable) background;
        for (ViewGroup registerLightSource : listOf) {
            illuminationDrawable.registerLightSource((View) registerLightSource);
        }
        illuminationDrawable.registerLightSource(this.cancel);
        illuminationDrawable.registerLightSource((View) this.dismiss);
        illuminationDrawable.registerLightSource(this.dismissLabel);
        illuminationDrawable.registerLightSource(this.settings);
    }
}
