package com.android.systemui.controls.p004ui;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.service.controls.templates.ThumbnailTemplate;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ThumbnailBehavior$bind$1 */
/* compiled from: ThumbnailBehavior.kt */
public final class ThumbnailBehavior$bind$1 implements Runnable {
    public final /* synthetic */ ClipDrawable $clipLayer;
    public final /* synthetic */ int $colorOffset;
    public final /* synthetic */ ThumbnailBehavior this$0;

    public ThumbnailBehavior$bind$1(ThumbnailBehavior thumbnailBehavior, ClipDrawable clipDrawable, int i) {
        this.this$0 = thumbnailBehavior;
        this.$clipLayer = clipDrawable;
        this.$colorOffset = i;
    }

    public final void run() {
        ThumbnailBehavior thumbnailBehavior = this.this$0;
        Objects.requireNonNull(thumbnailBehavior);
        ThumbnailTemplate thumbnailTemplate = thumbnailBehavior.template;
        if (thumbnailTemplate == null) {
            thumbnailTemplate = null;
        }
        Icon thumbnail = thumbnailTemplate.getThumbnail();
        ControlViewHolder cvh = this.this$0.getCvh();
        Objects.requireNonNull(cvh);
        final Drawable loadDrawable = thumbnail.loadDrawable(cvh.context);
        ControlViewHolder cvh2 = this.this$0.getCvh();
        Objects.requireNonNull(cvh2);
        DelayableExecutor delayableExecutor = cvh2.uiExecutor;
        final ThumbnailBehavior thumbnailBehavior2 = this.this$0;
        final ClipDrawable clipDrawable = this.$clipLayer;
        final int i = this.$colorOffset;
        delayableExecutor.execute(new Runnable() {
            public final void run() {
                ControlViewHolder cvh = thumbnailBehavior2.getCvh();
                Objects.requireNonNull(cvh);
                clipDrawable.setDrawable(new CornerDrawable(loadDrawable, (float) cvh.context.getResources().getDimensionPixelSize(C1777R.dimen.control_corner_radius)));
                ClipDrawable clipDrawable = clipDrawable;
                ControlViewHolder cvh2 = thumbnailBehavior2.getCvh();
                Objects.requireNonNull(cvh2);
                clipDrawable.setColorFilter(new BlendModeColorFilter(cvh2.context.getResources().getColor(C1777R.color.control_thumbnail_tint), BlendMode.LUMINOSITY));
                ControlViewHolder cvh3 = thumbnailBehavior2.getCvh();
                ThumbnailBehavior thumbnailBehavior = thumbnailBehavior2;
                Objects.requireNonNull(thumbnailBehavior);
                ThumbnailTemplate thumbnailTemplate = thumbnailBehavior.template;
                if (thumbnailTemplate == null) {
                    thumbnailTemplate = null;
                }
                cvh3.mo7819x3918d5b8(thumbnailTemplate.isActive(), i, true);
            }
        });
    }
}
