package com.android.systemui.statusbar.p007tv.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.systemui.statusbar.tv.notifications.TvNotificationAdapter */
public final class TvNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public SparseArray<StatusBarNotification> mNotifications;

    /* renamed from: com.android.systemui.statusbar.tv.notifications.TvNotificationAdapter$TvNotificationViewHolder */
    public static class TvNotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mDetails;
        public PendingIntent mPendingIntent;
        public final TextView mTitle;

        public final void onClick(View view) {
            try {
                PendingIntent pendingIntent = this.mPendingIntent;
                if (pendingIntent != null) {
                    pendingIntent.send();
                }
            } catch (PendingIntent.CanceledException unused) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Pending intent canceled for : ");
                m.append(this.mPendingIntent);
                Log.d("TvNotificationAdapter", m.toString());
            }
        }

        public TvNotificationViewHolder(View view) {
            super(view);
            this.mTitle = (TextView) view.findViewById(C1777R.C1779id.tv_notification_title);
            this.mDetails = (TextView) view.findViewById(C1777R.C1779id.tv_notification_details);
            view.setOnClickListener(this);
        }
    }

    public final int getItemCount() {
        SparseArray<StatusBarNotification> sparseArray = this.mNotifications;
        if (sparseArray == null) {
            return 0;
        }
        return sparseArray.size();
    }

    public final long getItemId(int i) {
        return (long) this.mNotifications.keyAt(i);
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        SparseArray<StatusBarNotification> sparseArray = this.mNotifications;
        if (sparseArray == null) {
            Log.e("TvNotificationAdapter", "Could not bind view holder because the notification is missing");
            return;
        }
        TvNotificationViewHolder tvNotificationViewHolder = (TvNotificationViewHolder) viewHolder;
        Notification notification = sparseArray.valueAt(i).getNotification();
        tvNotificationViewHolder.mTitle.setText(notification.extras.getString("android.title"));
        tvNotificationViewHolder.mDetails.setText(notification.extras.getString("android.text"));
        tvNotificationViewHolder.mPendingIntent = notification.contentIntent;
    }

    public TvNotificationAdapter() {
        setHasStableIds(true);
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new TvNotificationViewHolder(LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.tv_notification_item, recyclerView, false));
    }
}
