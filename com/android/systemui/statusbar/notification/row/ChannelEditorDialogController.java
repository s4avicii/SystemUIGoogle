package com.android.systemui.statusbar.notification.row;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialog;
import com.android.systemui.statusbar.notification.row.NotificationInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.CollectionsKt__ReversedViewsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;

/* compiled from: ChannelEditorDialogController.kt */
public final class ChannelEditorDialogController {
    public Drawable appIcon;
    public String appName;
    public Boolean appNotificationsCurrentlyEnabled;
    public boolean appNotificationsEnabled = true;
    public Integer appUid;
    public final ArrayList channelGroupList = new ArrayList();
    public final Context context;
    public ChannelEditorDialog dialog;
    public final ChannelEditorDialog.Builder dialogBuilder;
    public final LinkedHashMap edits = new LinkedHashMap();
    public final HashMap<String, CharSequence> groupNameLookup = new HashMap<>();
    public final INotificationManager noMan;
    public OnChannelEditorDialogFinishedListener onFinishListener;
    public NotificationInfo.OnSettingsClickListener onSettingsClickListener;
    public String packageName;
    public final ArrayList paddedChannels = new ArrayList();
    public boolean prepared;
    public final ArrayList providedChannels = new ArrayList();
    public final int wmFlags = -2130444288;

    @VisibleForTesting
    /* renamed from: getGroupNameLookup$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m74xe31ae654() {
    }

    @VisibleForTesting
    /* renamed from: getPaddedChannels$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m75x59c1b43c() {
    }

    public final void done() {
        ChannelEditorDialog channelEditorDialog = null;
        this.appIcon = null;
        this.appUid = null;
        this.packageName = null;
        this.appName = null;
        this.appNotificationsCurrentlyEnabled = null;
        this.edits.clear();
        this.paddedChannels.clear();
        this.providedChannels.clear();
        this.groupNameLookup.clear();
        ChannelEditorDialog channelEditorDialog2 = this.dialog;
        if (channelEditorDialog2 != null) {
            channelEditorDialog = channelEditorDialog2;
        }
        channelEditorDialog.dismiss();
    }

    @VisibleForTesting
    public final void apply() {
        for (Map.Entry entry : this.edits.entrySet()) {
            NotificationChannel notificationChannel = (NotificationChannel) entry.getKey();
            int intValue = ((Number) entry.getValue()).intValue();
            if (notificationChannel.getImportance() != intValue) {
                try {
                    notificationChannel.setImportance(intValue);
                    INotificationManager iNotificationManager = this.noMan;
                    String str = this.packageName;
                    Intrinsics.checkNotNull(str);
                    Integer num = this.appUid;
                    Intrinsics.checkNotNull(num);
                    iNotificationManager.updateNotificationChannelForPackage(str, num.intValue(), notificationChannel);
                } catch (Exception e) {
                    Log.e("ChannelDialogController", "Unable to update notification importance", e);
                }
            }
        }
        if (!Intrinsics.areEqual(Boolean.valueOf(this.appNotificationsEnabled), this.appNotificationsCurrentlyEnabled)) {
            boolean z = this.appNotificationsEnabled;
            try {
                INotificationManager iNotificationManager2 = this.noMan;
                String str2 = this.packageName;
                Intrinsics.checkNotNull(str2);
                Integer num2 = this.appUid;
                Intrinsics.checkNotNull(num2);
                iNotificationManager2.setNotificationsEnabledForPackage(str2, num2.intValue(), z);
            } catch (Exception e2) {
                Log.e("ChannelDialogController", "Error calling NoMan", e2);
            }
        }
    }

    @VisibleForTesting
    public final void launchSettings(View view) {
        NotificationChannel notificationChannel;
        if (this.providedChannels.size() == 1) {
            notificationChannel = (NotificationChannel) this.providedChannels.get(0);
        } else {
            notificationChannel = null;
        }
        NotificationInfo.OnSettingsClickListener onSettingsClickListener2 = this.onSettingsClickListener;
        if (onSettingsClickListener2 != null) {
            Integer num = this.appUid;
            Intrinsics.checkNotNull(num);
            onSettingsClickListener2.onClick(notificationChannel, num.intValue());
        }
    }

    public final void prepareDialogForApp(String str, String str2, int i, Set<NotificationChannel> set, Drawable drawable, NotificationInfo.OnSettingsClickListener onSettingsClickListener2) {
        boolean z;
        List list;
        this.appName = str;
        this.packageName = str2;
        this.appUid = Integer.valueOf(i);
        this.appIcon = drawable;
        try {
            INotificationManager iNotificationManager = this.noMan;
            String str3 = this.packageName;
            Intrinsics.checkNotNull(str3);
            Integer num = this.appUid;
            Intrinsics.checkNotNull(num);
            z = iNotificationManager.areNotificationsEnabledForPackage(str3, num.intValue());
        } catch (Exception e) {
            Log.e("ChannelDialogController", "Error calling NoMan", e);
            z = false;
        }
        this.appNotificationsEnabled = z;
        this.onSettingsClickListener = onSettingsClickListener2;
        this.appNotificationsCurrentlyEnabled = Boolean.valueOf(z);
        this.channelGroupList.clear();
        ArrayList arrayList = this.channelGroupList;
        ChannelEditorDialog channelEditorDialog = null;
        try {
            INotificationManager iNotificationManager2 = this.noMan;
            String str4 = this.packageName;
            Intrinsics.checkNotNull(str4);
            Integer num2 = this.appUid;
            Intrinsics.checkNotNull(num2);
            list = iNotificationManager2.getNotificationChannelGroupsForPackage(str4, num2.intValue(), false).getList();
            if (!(list instanceof List)) {
                list = null;
            }
            if (list == null) {
                list = EmptyList.INSTANCE;
            }
        } catch (Exception e2) {
            Log.e("ChannelDialogController", "Error fetching channel groups", e2);
            list = EmptyList.INSTANCE;
        }
        arrayList.addAll(list);
        Iterator it = this.channelGroupList.iterator();
        while (it.hasNext()) {
            NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) it.next();
            if (notificationChannelGroup.getId() != null) {
                this.groupNameLookup.put(notificationChannelGroup.getId(), notificationChannelGroup.getName());
            }
        }
        this.providedChannels.clear();
        this.providedChannels.addAll(set);
        this.paddedChannels.clear();
        CollectionsKt__ReversedViewsKt.addAll((Collection) this.paddedChannels, SequencesKt___SequencesKt.take(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(set), 4));
        CollectionsKt__ReversedViewsKt.addAll((Collection) this.paddedChannels, SequencesKt___SequencesKt.take(SequencesKt___SequencesKt.distinct(new FilteringSequence(new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.channelGroupList), ChannelEditorDialogController$getDisplayableChannels$channels$1.INSTANCE), new C1306x78bbb58a()), false, new ChannelEditorDialogController$padToFourChannels$1(this))), 4 - this.paddedChannels.size()));
        if (this.paddedChannels.size() == 1 && Intrinsics.areEqual("miscellaneous", ((NotificationChannel) this.paddedChannels.get(0)).getId())) {
            this.paddedChannels.clear();
        }
        ChannelEditorDialog.Builder builder = this.dialogBuilder;
        Context context2 = this.context;
        Objects.requireNonNull(builder);
        builder.context = context2;
        ChannelEditorDialog.Builder builder2 = this.dialogBuilder;
        Objects.requireNonNull(builder2);
        Context context3 = builder2.context;
        if (context3 == null) {
            context3 = null;
        }
        ChannelEditorDialog channelEditorDialog2 = new ChannelEditorDialog(context3);
        this.dialog = channelEditorDialog2;
        Window window = channelEditorDialog2.getWindow();
        if (window != null) {
            window.requestFeature(1);
        }
        ChannelEditorDialog channelEditorDialog3 = this.dialog;
        if (channelEditorDialog3 == null) {
            channelEditorDialog3 = null;
        }
        channelEditorDialog3.setTitle(" ");
        ChannelEditorDialog channelEditorDialog4 = this.dialog;
        if (channelEditorDialog4 != null) {
            channelEditorDialog = channelEditorDialog4;
        }
        channelEditorDialog.setContentView(C1777R.layout.notif_half_shelf);
        channelEditorDialog.setCanceledOnTouchOutside(true);
        channelEditorDialog.setOnDismissListener(new ChannelEditorDialogController$initDialog$1$1(this));
        ChannelEditorListView channelEditorListView = (ChannelEditorListView) channelEditorDialog.findViewById(C1777R.C1779id.half_shelf_container);
        if (channelEditorListView != null) {
            channelEditorListView.controller = this;
            channelEditorListView.appIcon = this.appIcon;
            channelEditorListView.appName = this.appName;
            channelEditorListView.channels = this.paddedChannels;
            channelEditorListView.updateRows();
        }
        channelEditorDialog.setOnShowListener(new ChannelEditorDialogController$initDialog$1$3(this, channelEditorListView));
        TextView textView = (TextView) channelEditorDialog.findViewById(C1777R.C1779id.done_button);
        if (textView != null) {
            textView.setOnClickListener(new ChannelEditorDialogController$initDialog$1$4(this));
        }
        TextView textView2 = (TextView) channelEditorDialog.findViewById(C1777R.C1779id.see_more_button);
        if (textView2 != null) {
            textView2.setOnClickListener(new ChannelEditorDialogController$initDialog$1$5(this));
        }
        Window window2 = channelEditorDialog.getWindow();
        if (window2 != null) {
            window2.setBackgroundDrawable(new ColorDrawable(0));
            window2.addFlags(this.wmFlags);
            window2.setType(2017);
            window2.setWindowAnimations(16973910);
            WindowManager.LayoutParams attributes = window2.getAttributes();
            attributes.format = -3;
            attributes.setTitle("ChannelEditorDialogController");
            attributes.gravity = 81;
            attributes.setFitInsetsTypes(window2.getAttributes().getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
            attributes.width = -1;
            attributes.height = -2;
            window2.setAttributes(attributes);
        }
        this.prepared = true;
    }

    public ChannelEditorDialogController(Context context2, INotificationManager iNotificationManager, ChannelEditorDialog.Builder builder) {
        this.noMan = iNotificationManager;
        this.dialogBuilder = builder;
        this.context = context2.getApplicationContext();
    }
}
