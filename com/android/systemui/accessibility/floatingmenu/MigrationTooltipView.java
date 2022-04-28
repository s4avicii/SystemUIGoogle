package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda11;

public final class MigrationTooltipView extends BaseTooltipView {
    public static final /* synthetic */ int $r8$clinit = 0;

    public MigrationTooltipView(Context context, AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        super(context, accessibilityFloatingMenuView);
        Intent intent = new Intent("android.settings.ACCESSIBILITY_DETAILS_SETTINGS");
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", AccessibilityShortcutController.ACCESSIBILITY_BUTTON_COMPONENT_NAME.flattenToShortString());
        AnnotationLinkSpan.LinkInfo linkInfo = new AnnotationLinkSpan.LinkInfo(new ScreenshotView$$ExternalSyntheticLambda11(this, intent, 1));
        this.mTextView.setText(AnnotationLinkSpan.linkify(getContext().getText(C1777R.string.accessibility_floating_button_migration_tooltip), linkInfo));
        this.mTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
