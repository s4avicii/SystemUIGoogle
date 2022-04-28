package com.android.systemui.media;

import android.app.smartspace.SmartspaceAction;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda7 implements View.OnClickListener {
    public final /* synthetic */ MediaControlPanel f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ SmartspaceAction f$2;
    public final /* synthetic */ View f$3;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda7(MediaControlPanel mediaControlPanel, int i, SmartspaceAction smartspaceAction, ViewGroup viewGroup) {
        this.f$0 = mediaControlPanel;
        this.f$1 = i;
        this.f$2 = smartspaceAction;
        this.f$3 = viewGroup;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0029, code lost:
        if (r8.mSmartspaceMediaItemsCount > 3) goto L_0x002e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.view.View r8) {
        /*
            r7 = this;
            com.android.systemui.media.MediaControlPanel r8 = r7.f$0
            int r0 = r7.f$1
            android.app.smartspace.SmartspaceAction r1 = r7.f$2
            android.view.View r7 = r7.f$3
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.plugins.FalsingManager r2 = r8.mFalsingManager
            r3 = 1
            boolean r2 = r2.isFalseTap(r3)
            if (r2 == 0) goto L_0x0016
            goto L_0x00a5
        L_0x0016:
            r2 = 760(0x2f8, float:1.065E-42)
            com.android.systemui.media.MediaCarouselController r4 = r8.mMediaCarouselController
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.media.MediaCarouselScrollHandler r4 = r4.mediaCarouselScrollHandler
            java.util.Objects.requireNonNull(r4)
            boolean r4 = r4.qsExpanded
            if (r4 != 0) goto L_0x002c
            int r4 = r8.mSmartspaceMediaItemsCount
            r5 = 3
            if (r4 <= r5) goto L_0x002c
            goto L_0x002e
        L_0x002c:
            int r5 = r8.mSmartspaceMediaItemsCount
        L_0x002e:
            r8.logSmartspaceCardReported(r2, r3, r0, r5)
            r0 = 0
            if (r1 == 0) goto L_0x007b
            android.content.Intent r2 = r1.getIntent()
            if (r2 == 0) goto L_0x007b
            android.content.Intent r2 = r1.getIntent()
            android.os.Bundle r2 = r2.getExtras()
            if (r2 != 0) goto L_0x0045
            goto L_0x007b
        L_0x0045:
            android.content.Intent r2 = r1.getIntent()
            android.os.Bundle r2 = r2.getExtras()
            java.lang.String r4 = "com.google.android.apps.gsa.smartspace.extra.SMARTSPACE_INTENT"
            java.lang.String r2 = r2.getString(r4)
            if (r2 != 0) goto L_0x0056
            goto L_0x007b
        L_0x0056:
            android.content.Intent r4 = android.content.Intent.parseUri(r2, r3)     // Catch:{ URISyntaxException -> 0x0061 }
            java.lang.String r5 = "KEY_OPEN_IN_FOREGROUND"
            boolean r2 = r4.getBooleanExtra(r5, r0)     // Catch:{ URISyntaxException -> 0x0061 }
            goto L_0x007c
        L_0x0061:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to create intent from URI: "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            java.lang.String r5 = "MediaControlPanel"
            android.util.Log.wtf(r5, r2)
            r4.printStackTrace()
        L_0x007b:
            r2 = r0
        L_0x007c:
            if (r2 == 0) goto L_0x0093
            com.android.systemui.plugins.ActivityStarter r7 = r8.mActivityStarter
            android.content.Intent r1 = r1.getIntent()
            com.android.systemui.media.RecommendationViewHolder r2 = r8.mRecommendationViewHolder
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.util.animation.TransitionLayout r2 = r2.recommendations
            com.android.systemui.media.MediaControlPanel$1 r2 = com.android.systemui.media.MediaControlPanel.buildLaunchAnimatorController(r2)
            r7.postStartActivityDismissingKeyguard(r1, r0, r2)
            goto L_0x009e
        L_0x0093:
            android.content.Context r7 = r7.getContext()
            android.content.Intent r0 = r1.getIntent()
            r7.startActivity(r0)
        L_0x009e:
            com.android.systemui.media.MediaCarouselController r7 = r8.mMediaCarouselController
            java.util.Objects.requireNonNull(r7)
            r7.shouldScrollToActivePlayer = r3
        L_0x00a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda7.onClick(android.view.View):void");
    }
}
