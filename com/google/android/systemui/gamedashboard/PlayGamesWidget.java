package com.google.android.systemui.gamedashboard;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ResultReceiver;
import java.util.Objects;

public final class PlayGamesWidget {
    public final Context mContext;
    public final Handler mMainHandler;
    public final GameDashboardUiEventLogger mUiEventLogger;
    public final ResultReceiver mWidgetResultReceiver;
    public final WidgetView mWidgetView;

    public class PlayGamesWidgetResultReceiver extends ResultReceiver {
        public PlayGamesWidgetResultReceiver(Handler handler) {
            super(handler);
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            PlayGamesWidget.this.updateFromData(bundle);
        }
    }

    public final void updateFromData(Bundle bundle) {
        PlayGamesWidget$$ExternalSyntheticLambda0 playGamesWidget$$ExternalSyntheticLambda0;
        Bitmap bitmap = (Bitmap) bundle.getParcelable("icon");
        String string = bundle.getString("title");
        String string2 = bundle.getString("tipText");
        String string3 = bundle.getString("description");
        PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("onClickPendingIntent");
        boolean z = bundle.getBoolean("isActive");
        if (z) {
            playGamesWidget$$ExternalSyntheticLambda0 = new PlayGamesWidget$$ExternalSyntheticLambda0(this, pendingIntent);
        } else {
            playGamesWidget$$ExternalSyntheticLambda0 = null;
        }
        WidgetView widgetView = this.mWidgetView;
        Objects.requireNonNull(widgetView);
        widgetView.mIcon.setImageBitmap(bitmap);
        widgetView.mTipText.setText(string2);
        widgetView.mTitle.setText(string);
        widgetView.mDescription.setText(string3);
        widgetView.setOnClickListener(playGamesWidget$$ExternalSyntheticLambda0);
        this.mWidgetView.setEnabled(z);
        this.mWidgetView.setLoading(false);
    }

    public PlayGamesWidget(Context context, WidgetView widgetView, Handler handler, GameDashboardUiEventLogger gameDashboardUiEventLogger) {
        this.mContext = context;
        this.mWidgetView = widgetView;
        this.mMainHandler = handler;
        this.mUiEventLogger = gameDashboardUiEventLogger;
        PlayGamesWidgetResultReceiver playGamesWidgetResultReceiver = new PlayGamesWidgetResultReceiver(handler);
        Parcel obtain = Parcel.obtain();
        playGamesWidgetResultReceiver.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        obtain.recycle();
        this.mWidgetResultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(obtain);
    }
}
