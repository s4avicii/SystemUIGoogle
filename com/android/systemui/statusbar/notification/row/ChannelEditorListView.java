package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ChannelEditorListView.kt */
public final class ChannelEditorListView extends LinearLayout {
    public AppControlView appControlRow;
    public Drawable appIcon;
    public String appName;
    public final ArrayList channelRows = new ArrayList();
    public List<NotificationChannel> channels = new ArrayList();
    public ChannelEditorDialogController controller;

    /* JADX WARNING: Removed duplicated region for block: B:79:0x0140  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0149  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateRows() {
        /*
            r11 = this;
            com.android.systemui.statusbar.notification.row.ChannelEditorDialogController r0 = r11.controller
            r1 = 0
            if (r0 == 0) goto L_0x0006
            goto L_0x0007
        L_0x0006:
            r0 = r1
        L_0x0007:
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.appNotificationsEnabled
            android.transition.AutoTransition r2 = new android.transition.AutoTransition
            r2.<init>()
            r3 = 200(0xc8, double:9.9E-322)
            r2.setDuration(r3)
            com.android.systemui.statusbar.notification.row.ChannelEditorListView$updateRows$1 r3 = new com.android.systemui.statusbar.notification.row.ChannelEditorListView$updateRows$1
            r3.<init>(r11)
            r2.addListener(r3)
            android.transition.TransitionManager.beginDelayedTransition(r11, r2)
            java.util.ArrayList r2 = r11.channelRows
            java.util.Iterator r2 = r2.iterator()
        L_0x0027:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0037
            java.lang.Object r3 = r2.next()
            com.android.systemui.statusbar.notification.row.ChannelRow r3 = (com.android.systemui.statusbar.notification.row.ChannelRow) r3
            r11.removeView(r3)
            goto L_0x0027
        L_0x0037:
            java.util.ArrayList r2 = r11.channelRows
            r2.clear()
            com.android.systemui.statusbar.notification.row.AppControlView r2 = r11.appControlRow
            if (r2 != 0) goto L_0x0041
            r2 = r1
        L_0x0041:
            java.util.Objects.requireNonNull(r2)
            android.widget.ImageView r2 = r2.iconView
            if (r2 == 0) goto L_0x0049
            goto L_0x004a
        L_0x0049:
            r2 = r1
        L_0x004a:
            android.graphics.drawable.Drawable r3 = r11.appIcon
            r2.setImageDrawable(r3)
            com.android.systemui.statusbar.notification.row.AppControlView r2 = r11.appControlRow
            if (r2 != 0) goto L_0x0054
            r2 = r1
        L_0x0054:
            java.util.Objects.requireNonNull(r2)
            android.widget.TextView r2 = r2.channelName
            if (r2 == 0) goto L_0x005c
            goto L_0x005d
        L_0x005c:
            r2 = r1
        L_0x005d:
            android.content.Context r3 = r11.getContext()
            android.content.res.Resources r3 = r3.getResources()
            r4 = 2131952888(0x7f1304f8, float:1.9542231E38)
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]
            java.lang.String r7 = r11.appName
            r8 = 0
            r6[r8] = r7
            java.lang.String r3 = r3.getString(r4, r6)
            r2.setText(r3)
            com.android.systemui.statusbar.notification.row.AppControlView r2 = r11.appControlRow
            if (r2 != 0) goto L_0x007c
            r2 = r1
        L_0x007c:
            java.util.Objects.requireNonNull(r2)
            android.widget.Switch r2 = r2.f169switch
            if (r2 == 0) goto L_0x0084
            goto L_0x0085
        L_0x0084:
            r2 = r1
        L_0x0085:
            r2.setChecked(r0)
            com.android.systemui.statusbar.notification.row.AppControlView r2 = r11.appControlRow
            if (r2 != 0) goto L_0x008d
            r2 = r1
        L_0x008d:
            java.util.Objects.requireNonNull(r2)
            android.widget.Switch r2 = r2.f169switch
            if (r2 == 0) goto L_0x0095
            goto L_0x0096
        L_0x0095:
            r2 = r1
        L_0x0096:
            com.android.systemui.statusbar.notification.row.ChannelEditorListView$updateAppControlRow$1 r3 = new com.android.systemui.statusbar.notification.row.ChannelEditorListView$updateAppControlRow$1
            r3.<init>(r11)
            r2.setOnCheckedChangeListener(r3)
            if (r0 == 0) goto L_0x0157
            android.content.Context r0 = r11.getContext()
            android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r0)
            java.util.List<android.app.NotificationChannel> r2 = r11.channels
            java.util.Iterator r2 = r2.iterator()
        L_0x00ae:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0157
            java.lang.Object r3 = r2.next()
            android.app.NotificationChannel r3 = (android.app.NotificationChannel) r3
            r4 = 2131624322(0x7f0e0182, float:1.887582E38)
            android.view.View r4 = r0.inflate(r4, r1)
            java.lang.String r6 = "null cannot be cast to non-null type com.android.systemui.statusbar.notification.row.ChannelRow"
            java.util.Objects.requireNonNull(r4, r6)
            com.android.systemui.statusbar.notification.row.ChannelRow r4 = (com.android.systemui.statusbar.notification.row.ChannelRow) r4
            com.android.systemui.statusbar.notification.row.ChannelEditorDialogController r6 = r11.controller
            if (r6 == 0) goto L_0x00cd
            goto L_0x00ce
        L_0x00cd:
            r6 = r1
        L_0x00ce:
            r4.controller = r6
            r4.channel = r3
            if (r3 != 0) goto L_0x00d5
            goto L_0x00d8
        L_0x00d5:
            r3.getImportance()
        L_0x00d8:
            android.app.NotificationChannel r3 = r4.channel
            if (r3 != 0) goto L_0x00de
            goto L_0x014d
        L_0x00de:
            android.widget.TextView r6 = r4.channelName
            if (r6 != 0) goto L_0x00e3
            r6 = r1
        L_0x00e3:
            java.lang.CharSequence r7 = r3.getName()
            java.lang.String r9 = ""
            if (r7 != 0) goto L_0x00ec
            r7 = r9
        L_0x00ec:
            r6.setText(r7)
            java.lang.String r6 = r3.getGroup()
            if (r6 != 0) goto L_0x00f6
            goto L_0x0113
        L_0x00f6:
            android.widget.TextView r7 = r4.channelDescription
            if (r7 != 0) goto L_0x00fb
            r7 = r1
        L_0x00fb:
            com.android.systemui.statusbar.notification.row.ChannelEditorDialogController r10 = r4.controller
            if (r10 == 0) goto L_0x0100
            goto L_0x0101
        L_0x0100:
            r10 = r1
        L_0x0101:
            java.util.Objects.requireNonNull(r10)
            java.util.HashMap<java.lang.String, java.lang.CharSequence> r10 = r10.groupNameLookup
            java.lang.Object r6 = r10.get(r6)
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            if (r6 != 0) goto L_0x010f
            goto L_0x0110
        L_0x010f:
            r9 = r6
        L_0x0110:
            r7.setText(r9)
        L_0x0113:
            java.lang.String r6 = r3.getGroup()
            if (r6 == 0) goto L_0x0132
            android.widget.TextView r6 = r4.channelDescription
            if (r6 != 0) goto L_0x011e
            r6 = r1
        L_0x011e:
            java.lang.CharSequence r6 = r6.getText()
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 == 0) goto L_0x0129
            goto L_0x0132
        L_0x0129:
            android.widget.TextView r6 = r4.channelDescription
            if (r6 != 0) goto L_0x012e
            r6 = r1
        L_0x012e:
            r6.setVisibility(r8)
            goto L_0x013c
        L_0x0132:
            android.widget.TextView r6 = r4.channelDescription
            if (r6 != 0) goto L_0x0137
            r6 = r1
        L_0x0137:
            r7 = 8
            r6.setVisibility(r7)
        L_0x013c:
            android.widget.Switch r6 = r4.f170switch
            if (r6 != 0) goto L_0x0141
            r6 = r1
        L_0x0141:
            int r3 = r3.getImportance()
            if (r3 == 0) goto L_0x0149
            r3 = r5
            goto L_0x014a
        L_0x0149:
            r3 = r8
        L_0x014a:
            r6.setChecked(r3)
        L_0x014d:
            java.util.ArrayList r3 = r11.channelRows
            r3.add(r4)
            r11.addView(r4)
            goto L_0x00ae
        L_0x0157:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.ChannelEditorListView.updateRows():void");
    }

    public ChannelEditorListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.appControlRow = (AppControlView) findViewById(C1777R.C1779id.app_control);
    }
}
