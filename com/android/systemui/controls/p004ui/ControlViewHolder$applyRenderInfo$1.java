package com.android.systemui.controls.p004ui;

import android.content.res.ColorStateList;
import android.service.controls.Control;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.controls.ui.ControlViewHolder$applyRenderInfo$1 */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$applyRenderInfo$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ Control $control;
    public final /* synthetic */ boolean $enabled;
    public final /* synthetic */ ColorStateList $fg;
    public final /* synthetic */ CharSequence $newText;
    public final /* synthetic */ RenderInfo $ri;
    public final /* synthetic */ ControlViewHolder this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlViewHolder$applyRenderInfo$1(ControlViewHolder controlViewHolder, boolean z, CharSequence charSequence, RenderInfo renderInfo, ColorStateList colorStateList, Control control) {
        super(0);
        this.this$0 = controlViewHolder;
        this.$enabled = z;
        this.$newText = charSequence;
        this.$ri = renderInfo;
        this.$fg = colorStateList;
        this.$control = control;
    }

    public final Object invoke() {
        ControlViewHolder controlViewHolder = this.this$0;
        boolean z = this.$enabled;
        CharSequence charSequence = this.$newText;
        RenderInfo renderInfo = this.$ri;
        Objects.requireNonNull(renderInfo);
        controlViewHolder.mo7828xcd94ff85(z, charSequence, renderInfo.icon, this.$fg, this.$control);
        return Unit.INSTANCE;
    }
}
