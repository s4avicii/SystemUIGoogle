package com.android.p012wm.shell.bubbles;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda21 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda21 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda21(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0210  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x022e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r10 = this;
            int r0 = r10.$r8$classId
            r1 = 1
            r2 = 2
            r3 = 0
            r4 = 0
            switch(r0) {
                case 0: goto L_0x0161;
                case 1: goto L_0x0152;
                case 2: goto L_0x013a;
                case 3: goto L_0x00eb;
                case 4: goto L_0x009c;
                case 5: goto L_0x0061;
                case 6: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x0177
        L_0x000b:
            java.lang.Object r0 = r10.f$0
            com.google.android.systemui.gamedashboard.PlayGamesWidget r0 = (com.google.android.systemui.gamedashboard.PlayGamesWidget) r0
            java.lang.Object r10 = r10.f$1
            java.lang.String r10 = (java.lang.String) r10
            java.util.Objects.requireNonNull(r0)
            java.lang.String r1 = "PlayGamesWidget"
            android.os.Bundle r3 = new android.os.Bundle
            r3.<init>()
            java.lang.String r5 = "foregroundAppPackageName"
            r3.putString(r5, r10)
            android.os.ResultReceiver r10 = r0.mWidgetResultReceiver
            java.lang.String r5 = "resultReceiver"
            r3.putParcelable(r5, r10)
            android.content.Context r10 = r0.mContext     // Catch:{ Exception -> 0x005b }
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch:{ Exception -> 0x005b }
            java.lang.String r5 = "com.google.android.play.games.dashboard.tile.provider"
            java.lang.String r6 = "getGameMenuWidgetInfo"
            android.os.Bundle r10 = r10.call(r5, r6, r4, r3)     // Catch:{ Exception -> 0x005b }
            if (r10 != 0) goto L_0x0040
            java.lang.String r10 = "Play Games widget provider returns no data."
            android.util.Log.v(r1, r10)
            goto L_0x0060
        L_0x0040:
            java.lang.String r3 = "resultCode"
            int r3 = r10.getInt(r3)
            if (r3 != r2) goto L_0x004f
            java.lang.String r10 = "Play Games widget provider issues asynchronous update."
            android.util.Log.v(r1, r10)
            goto L_0x0060
        L_0x004f:
            if (r3 != 0) goto L_0x0057
            java.lang.String r10 = "Play Games widget provider returns UNKNOWN"
            android.util.Log.w(r1, r10)
            goto L_0x0060
        L_0x0057:
            r0.updateFromData(r10)
            goto L_0x0060
        L_0x005b:
            java.lang.String r10 = "Failed to call Play Games widget provider."
            android.util.Log.w(r1, r10)
        L_0x0060:
            return
        L_0x0061:
            java.lang.Object r0 = r10.f$0
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController r0 = (com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController) r0
            java.lang.Object r10 = r10.f$1
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView$Mode r10 = (com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView.Mode) r10
            java.util.Objects.requireNonNull(r0)
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView$Mode r1 = r0.getMode()
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView r2 = r0.mEdgeLightsView
            r1.onNewModeRequest(r2, r10)
            com.android.systemui.assist.AssistLogger r0 = r0.mAssistLogger
            boolean r1 = r10 instanceof com.google.android.systemui.assist.uihints.edgelights.mode.Gone
            if (r1 == 0) goto L_0x007e
            com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent r1 = com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent.ASSISTANT_SESSION_MODE_GONE
            goto L_0x0095
        L_0x007e:
            boolean r1 = r10 instanceof com.google.android.systemui.assist.uihints.edgelights.mode.FullListening
            if (r1 == 0) goto L_0x0085
            com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent r1 = com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent.ASSISTANT_SESSION_MODE_FULL_LISTENING
            goto L_0x0095
        L_0x0085:
            boolean r1 = r10 instanceof com.google.android.systemui.assist.uihints.edgelights.mode.FulfillBottom
            if (r1 == 0) goto L_0x008c
            com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent r1 = com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent.ASSISTANT_SESSION_MODE_FULFILL_BOTTOM
            goto L_0x0095
        L_0x008c:
            boolean r1 = r10 instanceof com.google.android.systemui.assist.uihints.edgelights.mode.FulfillPerimeter
            if (r1 == 0) goto L_0x0093
            com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent r1 = com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent.ASSISTANT_SESSION_MODE_FULFILL_PERIMETER
            goto L_0x0095
        L_0x0093:
            com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent r1 = com.google.android.systemui.assist.uihints.edgelights.mode.AssistantModeChangeEvent.ASSISTANT_SESSION_MODE_UNKNOWN
        L_0x0095:
            r0.reportAssistantSessionEvent(r1)
            r10.logState()
            return
        L_0x009c:
            java.lang.Object r0 = r10.f$0
            com.android.wm.shell.draganddrop.DragAndDropController$DragAndDropImpl r0 = (com.android.p012wm.shell.draganddrop.DragAndDropController.DragAndDropImpl) r0
            java.lang.Object r10 = r10.f$1
            android.content.res.Configuration r10 = (android.content.res.Configuration) r10
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.draganddrop.DragAndDropController r0 = com.android.p012wm.shell.draganddrop.DragAndDropController.this
            java.util.Objects.requireNonNull(r0)
            r4 = r3
        L_0x00ad:
            android.util.SparseArray<com.android.wm.shell.draganddrop.DragAndDropController$PerDisplay> r5 = r0.mDisplayDropTargets
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x00ea
            android.util.SparseArray<com.android.wm.shell.draganddrop.DragAndDropController$PerDisplay> r5 = r0.mDisplayDropTargets
            java.lang.Object r5 = r5.get(r4)
            com.android.wm.shell.draganddrop.DragAndDropController$PerDisplay r5 = (com.android.p012wm.shell.draganddrop.DragAndDropController.PerDisplay) r5
            com.android.wm.shell.draganddrop.DragLayout r5 = r5.dragLayout
            java.util.Objects.requireNonNull(r5)
            int r6 = r10.orientation
            if (r6 != r2) goto L_0x00d5
            int r6 = r5.getOrientation()
            if (r6 == 0) goto L_0x00d5
            r5.setOrientation(r3)
            int r6 = r10.orientation
            r5.updateContainerMargins(r6)
            goto L_0x00e7
        L_0x00d5:
            int r6 = r10.orientation
            if (r6 != r1) goto L_0x00e7
            int r6 = r5.getOrientation()
            if (r6 == r1) goto L_0x00e7
            r5.setOrientation(r1)
            int r6 = r10.orientation
            r5.updateContainerMargins(r6)
        L_0x00e7:
            int r4 = r4 + 1
            goto L_0x00ad
        L_0x00ea:
            return
        L_0x00eb:
            java.lang.Object r0 = r10.f$0
            com.android.systemui.util.service.ObservableServiceConnection r0 = (com.android.systemui.util.service.ObservableServiceConnection) r0
            java.lang.Object r10 = r10.f$1
            com.android.systemui.util.service.ObservableServiceConnection$Callback r10 = (com.android.systemui.util.service.ObservableServiceConnection.Callback) r10
            boolean r1 = com.android.systemui.util.service.ObservableServiceConnection.DEBUG
            java.util.Objects.requireNonNull(r0)
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.systemui.util.service.ObservableServiceConnection$Callback<T>>> r1 = r0.mCallbacks
            java.util.Iterator r1 = r1.iterator()
        L_0x00fe:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0111
            java.lang.Object r2 = r1.next()
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2
            java.lang.Object r2 = r2.get()
            if (r2 != r10) goto L_0x00fe
            goto L_0x0139
        L_0x0111:
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.systemui.util.service.ObservableServiceConnection$Callback<T>>> r1 = r0.mCallbacks
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference
            r2.<init>(r10)
            r1.add(r2)
            T r1 = r0.mProxy
            if (r1 == 0) goto L_0x0123
            r10.onConnected(r1)
            goto L_0x0139
        L_0x0123:
            java.util.Optional<java.lang.Integer> r1 = r0.mLastDisconnectReason
            boolean r1 = r1.isPresent()
            if (r1 == 0) goto L_0x0139
            java.util.Optional<java.lang.Integer> r0 = r0.mLastDisconnectReason
            java.lang.Object r0 = r0.get()
            java.lang.Integer r0 = (java.lang.Integer) r0
            r0.intValue()
            r10.onDisconnected()
        L_0x0139:
            return
        L_0x013a:
            java.lang.Object r0 = r10.f$0
            com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback r0 = (com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback) r0
            java.lang.Object r10 = r10.f$1
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r10 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout) r10
            java.util.Objects.requireNonNull(r0)
            android.view.View r1 = r0.mPendingWorkRemoteInputView
            r1.callOnClick()
            r0.mPendingWorkRemoteInputView = r4
            java.util.Objects.requireNonNull(r10)
            r10.mFinishScrollingCallback = r4
            return
        L_0x0152:
            java.lang.Object r0 = r10.f$0
            com.android.systemui.shared.plugins.PluginActionManager r0 = (com.android.systemui.shared.plugins.PluginActionManager) r0
            java.lang.Object r10 = r10.f$1
            com.android.systemui.shared.plugins.PluginInstance r10 = (com.android.systemui.shared.plugins.PluginInstance) r10
            java.util.Objects.requireNonNull(r0)
            r0.onPluginDisconnected(r10)
            return
        L_0x0161:
            java.lang.Object r0 = r10.f$0
            com.android.wm.shell.bubbles.BubbleStackView r0 = (com.android.p012wm.shell.bubbles.BubbleStackView) r0
            java.lang.Object r10 = r10.f$1
            java.util.function.Consumer r10 = (java.util.function.Consumer) r10
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1 r1 = new com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1
            r2 = 9
            r1.<init>(r10, r2)
            r0.post(r1)
            return
        L_0x0177:
            java.lang.Object r0 = r10.f$0
            com.google.android.systemui.smartspace.SmartSpaceController r0 = (com.google.android.systemui.smartspace.SmartSpaceController) r0
            java.lang.Object r10 = r10.f$1
            com.google.android.systemui.smartspace.NewCardInfo r10 = (com.google.android.systemui.smartspace.NewCardInfo) r10
            boolean r2 = com.google.android.systemui.smartspace.SmartSpaceController.DEBUG
            java.util.Objects.requireNonNull(r0)
            android.content.Context r2 = r0.mContext
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.smartspace.nano.SmartspaceProto$CardWrapper r5 = new com.android.systemui.smartspace.nano.SmartspaceProto$CardWrapper
            r5.<init>()
            com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceUpdate$SmartspaceCard r6 = r10.mCard
            com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceUpdate$SmartspaceCard$Image r6 = r6.icon
            if (r6 != 0) goto L_0x0196
            goto L_0x020d
        L_0x0196:
            java.lang.String r7 = r6.key
            android.content.Intent r8 = r10.mIntent
            boolean r9 = android.text.TextUtils.isEmpty(r7)
            if (r9 != 0) goto L_0x01a5
            android.os.Parcelable r7 = r8.getParcelableExtra(r7)
            goto L_0x01a6
        L_0x01a5:
            r7 = r4
        L_0x01a6:
            android.graphics.Bitmap r7 = (android.graphics.Bitmap) r7
            if (r7 == 0) goto L_0x01ab
            goto L_0x020e
        L_0x01ab:
            java.lang.String r7 = r6.uri     // Catch:{ Exception -> 0x01ee }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x01ee }
            if (r7 != 0) goto L_0x01c2
            java.lang.String r7 = r6.uri     // Catch:{ Exception -> 0x01ee }
            android.net.Uri r7 = android.net.Uri.parse(r7)     // Catch:{ Exception -> 0x01ee }
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ Exception -> 0x01ee }
            android.graphics.Bitmap r7 = android.provider.MediaStore.Images.Media.getBitmap(r2, r7)     // Catch:{ Exception -> 0x01ee }
            goto L_0x020e
        L_0x01c2:
            java.lang.String r7 = r6.gsaResourceName     // Catch:{ Exception -> 0x01ee }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x01ee }
            if (r7 != 0) goto L_0x020d
            android.content.Intent$ShortcutIconResource r7 = new android.content.Intent$ShortcutIconResource     // Catch:{ Exception -> 0x01ee }
            r7.<init>()     // Catch:{ Exception -> 0x01ee }
            java.lang.String r8 = "com.google.android.googlequicksearchbox"
            r7.packageName = r8     // Catch:{ Exception -> 0x01ee }
            java.lang.String r8 = r6.gsaResourceName     // Catch:{ Exception -> 0x01ee }
            r7.resourceName = r8     // Catch:{ Exception -> 0x01ee }
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch:{ Exception -> 0x01ee }
            java.lang.String r6 = r7.packageName     // Catch:{ Exception -> 0x020d }
            android.content.res.Resources r2 = r2.getResourcesForApplication(r6)     // Catch:{ Exception -> 0x020d }
            if (r2 == 0) goto L_0x020d
            java.lang.String r6 = r7.resourceName     // Catch:{ Exception -> 0x020d }
            int r6 = r2.getIdentifier(r6, r4, r4)     // Catch:{ Exception -> 0x020d }
            android.graphics.Bitmap r7 = android.graphics.BitmapFactory.decodeResource(r2, r6)     // Catch:{ Exception -> 0x020d }
            goto L_0x020e
        L_0x01ee:
            java.lang.String r2 = "retrieving bitmap uri="
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            java.lang.String r7 = r6.uri
            r2.append(r7)
            java.lang.String r7 = " gsaRes="
            r2.append(r7)
            java.lang.String r6 = r6.gsaResourceName
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            java.lang.String r6 = "NewCardInfo"
            android.util.Log.e(r6, r2)
        L_0x020d:
            r7 = r4
        L_0x020e:
            if (r7 == 0) goto L_0x0222
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            android.graphics.Bitmap$CompressFormat r6 = android.graphics.Bitmap.CompressFormat.PNG
            r8 = 100
            r7.compress(r6, r8, r2)
            byte[] r2 = r2.toByteArray()
            r5.icon = r2
        L_0x0222:
            com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceUpdate$SmartspaceCard r2 = r10.mCard
            r5.card = r2
            long r6 = r10.mPublishTime
            r5.publishTime = r6
            android.content.pm.PackageInfo r2 = r10.mPackageInfo
            if (r2 == 0) goto L_0x0236
            int r6 = r2.versionCode
            r5.gsaVersionCode = r6
            long r6 = r2.lastUpdateTime
            r5.gsaUpdateTime = r6
        L_0x0236:
            boolean r2 = r0.mHidePrivateData
            if (r2 == 0) goto L_0x023e
            boolean r2 = r0.mHideWorkData
            if (r2 != 0) goto L_0x025d
        L_0x023e:
            com.google.android.systemui.smartspace.ProtoStore r2 = r0.mStore
            java.lang.String r6 = "smartspace_"
            java.lang.StringBuilder r6 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r6)
            int r7 = r0.mCurrentUserId
            r6.append(r7)
            java.lang.String r7 = "_"
            r6.append(r7)
            boolean r7 = r10.mIsPrimary
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r2.store(r5, r6)
        L_0x025d:
            com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceUpdate$SmartspaceCard r2 = r10.mCard
            if (r2 == 0) goto L_0x0267
            boolean r2 = r2.shouldDiscard
            if (r2 == 0) goto L_0x0266
            goto L_0x0267
        L_0x0266:
            r1 = r3
        L_0x0267:
            if (r1 == 0) goto L_0x026a
            goto L_0x0272
        L_0x026a:
            android.content.Context r1 = r0.mContext
            boolean r2 = r10.mIsPrimary
            com.google.android.systemui.smartspace.SmartSpaceCard r4 = com.google.android.systemui.smartspace.SmartSpaceCard.fromWrapper(r1, r5, r2)
        L_0x0272:
            android.os.Handler r1 = r0.mUiHandler
            com.google.android.systemui.smartspace.SmartSpaceController$$ExternalSyntheticLambda1 r2 = new com.google.android.systemui.smartspace.SmartSpaceController$$ExternalSyntheticLambda1
            r2.<init>(r0, r10, r4)
            r1.post(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda21.run():void");
    }
}
