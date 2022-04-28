package com.google.android.systemui.gamedashboard;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class YouTubeLiveWidget$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ YouTubeLiveWidget f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ YouTubeLiveWidget$$ExternalSyntheticLambda0(YouTubeLiveWidget youTubeLiveWidget, String str) {
        this.f$0 = youTubeLiveWidget;
        this.f$1 = str;
    }

    public final void onClick(View view) {
        YouTubeLiveWidget youTubeLiveWidget = this.f$0;
        String str = this.f$1;
        Objects.requireNonNull(youTubeLiveWidget);
        youTubeLiveWidget.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_YOUTUBE_LIVE_WIDGET);
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM").setPackage("com.google.android.youtube");
        intent.putExtra("android.intent.extra.REFERRER", new Uri.Builder().scheme("android-app").appendPath(youTubeLiveWidget.mContext.getPackageName()).build());
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("GAME_PACKAGE_NAME", str);
            PackageManager packageManager = youTubeLiveWidget.mContext.getPackageManager();
            ApplicationInfo applicationInfo = null;
            try {
                applicationInfo = packageManager.getApplicationInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                MotionLayout$$ExternalSyntheticOutline0.m9m("Fail to query application info for ", str, "YouTubeLiveWidget");
            }
            if (applicationInfo != null) {
                intent.putExtra("GAME_TITLE", packageManager.getApplicationLabel(applicationInfo).toString());
            }
        }
        intent.putExtra("CAPTURE_MODE", "SCREEN");
        ((Activity) youTubeLiveWidget.mContext).startActivityForResult(intent, 0);
    }
}
