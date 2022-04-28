package com.android.systemui.controls.p004ui;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$show$5 */
/* compiled from: ControlsUiControllerImpl.kt */
public /* synthetic */ class ControlsUiControllerImpl$show$5 extends FunctionReferenceImpl implements Function1<List<? extends SelectionItem>, Unit> {
    public ControlsUiControllerImpl$show$5(ControlsUiController controlsUiController) {
        super(1, controlsUiController, ControlsUiControllerImpl.class, "showControlsView", "showControlsView(Ljava/util/List;)V", 0);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: com.android.systemui.controls.ui.SelectionItem} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invoke(java.lang.Object r27) {
        /*
            r26 = this;
            r0 = r27
            java.util.List r0 = (java.util.List) r0
            r1 = r26
            java.lang.Object r1 = r1.receiver
            com.android.systemui.controls.ui.ControlsUiControllerImpl r1 = (com.android.systemui.controls.p004ui.ControlsUiControllerImpl) r1
            android.content.ComponentName r2 = com.android.systemui.controls.p004ui.ControlsUiControllerImpl.EMPTY_COMPONENT
            java.util.Objects.requireNonNull(r1)
            java.util.LinkedHashMap r2 = r1.controlViewsById
            r2.clear()
            r2 = 10
            int r2 = kotlin.collections.CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(r0, r2)
            int r2 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r2)
            r3 = 16
            if (r2 >= r3) goto L_0x0023
            r2 = r3
        L_0x0023:
            java.util.LinkedHashMap r3 = new java.util.LinkedHashMap
            r3.<init>(r2)
            java.util.Iterator r2 = r0.iterator()
        L_0x002c:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x0042
            java.lang.Object r4 = r2.next()
            r5 = r4
            com.android.systemui.controls.ui.SelectionItem r5 = (com.android.systemui.controls.p004ui.SelectionItem) r5
            java.util.Objects.requireNonNull(r5)
            android.content.ComponentName r5 = r5.componentName
            r3.put(r5, r4)
            goto L_0x002c
        L_0x0042:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.List<com.android.systemui.controls.controller.StructureInfo> r4 = r1.allStructures
            if (r4 != 0) goto L_0x004c
            r4 = 0
        L_0x004c:
            java.util.Iterator r4 = r4.iterator()
        L_0x0050:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L_0x0082
            java.lang.Object r6 = r4.next()
            com.android.systemui.controls.controller.StructureInfo r6 = (com.android.systemui.controls.controller.StructureInfo) r6
            java.util.Objects.requireNonNull(r6)
            android.content.ComponentName r7 = r6.componentName
            java.lang.Object r7 = r3.get(r7)
            com.android.systemui.controls.ui.SelectionItem r7 = (com.android.systemui.controls.p004ui.SelectionItem) r7
            if (r7 != 0) goto L_0x006b
            r6 = 0
            goto L_0x007b
        L_0x006b:
            java.lang.CharSequence r10 = r6.structure
            java.lang.CharSequence r9 = r7.appName
            android.graphics.drawable.Drawable r11 = r7.icon
            android.content.ComponentName r12 = r7.componentName
            int r13 = r7.uid
            com.android.systemui.controls.ui.SelectionItem r6 = new com.android.systemui.controls.ui.SelectionItem
            r8 = r6
            r8.<init>(r9, r10, r11, r12, r13)
        L_0x007b:
            if (r6 != 0) goto L_0x007e
            goto L_0x0050
        L_0x007e:
            r2.add(r6)
            goto L_0x0050
        L_0x0082:
            com.android.systemui.controls.ui.ControlsUiControllerImpl$special$$inlined$compareBy$1 r3 = r1.localeComparator
            kotlin.collections.CollectionsKt__MutableCollectionsJVMKt.sortWith(r2, r3)
            com.android.systemui.controls.controller.StructureInfo r3 = r1.selectedStructure
            java.util.Iterator r4 = r2.iterator()
        L_0x008d:
            boolean r6 = r4.hasNext()
            r7 = 1
            r8 = 0
            if (r6 == 0) goto L_0x00bc
            java.lang.Object r6 = r4.next()
            r9 = r6
            com.android.systemui.controls.ui.SelectionItem r9 = (com.android.systemui.controls.p004ui.SelectionItem) r9
            java.util.Objects.requireNonNull(r9)
            android.content.ComponentName r10 = r9.componentName
            java.util.Objects.requireNonNull(r3)
            android.content.ComponentName r11 = r3.componentName
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r10, r11)
            if (r10 == 0) goto L_0x00b8
            java.lang.CharSequence r9 = r9.structure
            java.lang.CharSequence r10 = r3.structure
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r10)
            if (r9 == 0) goto L_0x00b8
            r9 = r7
            goto L_0x00b9
        L_0x00b8:
            r9 = r8
        L_0x00b9:
            if (r9 == 0) goto L_0x008d
            goto L_0x00bd
        L_0x00bc:
            r6 = 0
        L_0x00bd:
            com.android.systemui.controls.ui.SelectionItem r6 = (com.android.systemui.controls.p004ui.SelectionItem) r6
            if (r6 != 0) goto L_0x00c8
            java.lang.Object r0 = r0.get(r8)
            r6 = r0
            com.android.systemui.controls.ui.SelectionItem r6 = (com.android.systemui.controls.p004ui.SelectionItem) r6
        L_0x00c8:
            com.android.systemui.controls.ControlsMetricsLogger r0 = r1.controlsMetricsLogger
            java.util.Objects.requireNonNull(r6)
            int r3 = r6.uid
            com.android.systemui.statusbar.policy.KeyguardStateController r4 = r1.keyguardStateController
            boolean r4 = r4.isUnlocked()
            r4 = r4 ^ r7
            r0.refreshBegin(r3, r4)
            android.content.Context r0 = r1.context
            android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r0)
            android.view.ViewGroup r3 = r1.parent
            if (r3 != 0) goto L_0x00e4
            r3 = 0
        L_0x00e4:
            r4 = 2131624060(0x7f0e007c, float:1.887529E38)
            r0.inflate(r4, r3, r7)
            android.view.ViewGroup r3 = r1.parent
            if (r3 != 0) goto L_0x00ef
            r3 = 0
        L_0x00ef:
            r4 = 2131427760(0x7f0b01b0, float:1.8477145E38)
            android.view.View r3 = r3.requireViewById(r4)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            com.android.systemui.controls.ui.ControlsUiControllerImpl$createListView$1$1 r4 = new com.android.systemui.controls.ui.ControlsUiControllerImpl$createListView$1$1
            r4.<init>(r1)
            r3.setOnClickListener(r4)
            r3.setVisibility(r8)
            android.content.Context r3 = r1.activityContext
            if (r3 != 0) goto L_0x0108
            r3 = 0
        L_0x0108:
            android.content.res.Resources r3 = r3.getResources()
            r4 = 2131492911(0x7f0c002f, float:1.8609287E38)
            int r4 = r3.getInteger(r4)
            r9 = 2131492912(0x7f0c0030, float:1.860929E38)
            int r9 = r3.getInteger(r9)
            android.util.TypedValue r10 = new android.util.TypedValue
            r10.<init>()
            r11 = 2131165580(0x7f07018c, float:1.7945381E38)
            r3.getValue(r11, r10, r7)
            float r10 = r10.getFloat()
            android.content.res.Configuration r3 = r3.getConfiguration()
            int r11 = r3.orientation
            if (r11 != r7) goto L_0x0133
            r11 = r7
            goto L_0x0134
        L_0x0133:
            r11 = r8
        L_0x0134:
            if (r11 == 0) goto L_0x0144
            int r11 = r3.screenWidthDp
            if (r11 == 0) goto L_0x0144
            if (r11 > r9) goto L_0x0144
            float r3 = r3.fontScale
            int r3 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r3 < 0) goto L_0x0144
            int r4 = r4 + -1
        L_0x0144:
            android.view.ViewGroup r3 = r1.parent
            if (r3 != 0) goto L_0x0149
            r3 = 0
        L_0x0149:
            r9 = 2131428026(0x7f0b02ba, float:1.8477685E38)
            android.view.View r3 = r3.requireViewById(r9)
            java.lang.String r9 = "null cannot be cast to non-null type android.view.ViewGroup"
            java.util.Objects.requireNonNull(r3, r9)
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            r10 = 2131624057(0x7f0e0079, float:1.8875283E38)
            android.view.View r11 = r0.inflate(r10, r3, r8)
            java.util.Objects.requireNonNull(r11, r9)
            android.view.ViewGroup r11 = (android.view.ViewGroup) r11
            r3.addView(r11)
            com.android.systemui.controls.controller.StructureInfo r12 = r1.selectedStructure
            java.util.Objects.requireNonNull(r12)
            java.util.List<com.android.systemui.controls.controller.ControlInfo> r12 = r12.controls
            java.util.Iterator r12 = r12.iterator()
        L_0x0171:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0211
            java.lang.Object r13 = r12.next()
            com.android.systemui.controls.controller.ControlInfo r13 = (com.android.systemui.controls.controller.ControlInfo) r13
            com.android.systemui.controls.ui.ControlKey r14 = new com.android.systemui.controls.ui.ControlKey
            com.android.systemui.controls.controller.StructureInfo r15 = r1.selectedStructure
            java.util.Objects.requireNonNull(r15)
            android.content.ComponentName r15 = r15.componentName
            java.util.Objects.requireNonNull(r13)
            java.lang.String r13 = r13.controlId
            r14.<init>(r15, r13)
            java.util.LinkedHashMap r13 = r1.controlsById
            java.lang.Object r13 = r13.get(r14)
            com.android.systemui.controls.ui.ControlWithState r13 = (com.android.systemui.controls.p004ui.ControlWithState) r13
            if (r13 != 0) goto L_0x019d
            r24 = r0
            r25 = r3
            goto L_0x0206
        L_0x019d:
            int r15 = r11.getChildCount()
            if (r15 != r4) goto L_0x01af
            android.view.View r11 = r0.inflate(r10, r3, r8)
            java.util.Objects.requireNonNull(r11, r9)
            android.view.ViewGroup r11 = (android.view.ViewGroup) r11
            r3.addView(r11)
        L_0x01af:
            r15 = 2131624044(0x7f0e006c, float:1.8875257E38)
            android.view.View r15 = r0.inflate(r15, r11, r8)
            java.util.Objects.requireNonNull(r15, r9)
            android.view.ViewGroup r15 = (android.view.ViewGroup) r15
            r11.addView(r15)
            int r10 = r11.getChildCount()
            if (r10 != r7) goto L_0x01d2
            android.view.ViewGroup$LayoutParams r10 = r15.getLayoutParams()
            java.lang.String r7 = "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams"
            java.util.Objects.requireNonNull(r10, r7)
            android.view.ViewGroup$MarginLayoutParams r10 = (android.view.ViewGroup.MarginLayoutParams) r10
            r10.setMarginStart(r8)
        L_0x01d2:
            com.android.systemui.controls.ui.ControlViewHolder r7 = new com.android.systemui.controls.ui.ControlViewHolder
            dagger.Lazy<com.android.systemui.controls.controller.ControlsController> r10 = r1.controlsController
            java.lang.Object r10 = r10.get()
            r18 = r10
            com.android.systemui.controls.controller.ControlsController r18 = (com.android.systemui.controls.controller.ControlsController) r18
            com.android.systemui.util.concurrency.DelayableExecutor r10 = r1.uiExecutor
            com.android.systemui.util.concurrency.DelayableExecutor r5 = r1.bgExecutor
            com.android.systemui.controls.ui.ControlActionCoordinator r8 = r1.controlActionCoordinator
            r24 = r0
            com.android.systemui.controls.ControlsMetricsLogger r0 = r1.controlsMetricsLogger
            r25 = r3
            int r3 = r6.uid
            r16 = r7
            r17 = r15
            r19 = r10
            r20 = r5
            r21 = r8
            r22 = r0
            r23 = r3
            r16.<init>(r17, r18, r19, r20, r21, r22, r23)
            r0 = 0
            r7.bindData(r13, r0)
            java.util.LinkedHashMap r0 = r1.controlViewsById
            r0.put(r14, r7)
        L_0x0206:
            r0 = r24
            r3 = r25
            r7 = 1
            r8 = 0
            r10 = 2131624057(0x7f0e0079, float:1.8875283E38)
            goto L_0x0171
        L_0x0211:
            com.android.systemui.controls.controller.StructureInfo r0 = r1.selectedStructure
            java.util.Objects.requireNonNull(r0)
            java.util.List<com.android.systemui.controls.controller.ControlInfo> r0 = r0.controls
            int r0 = r0.size()
            int r0 = r0 % r4
            if (r0 != 0) goto L_0x0221
            r0 = 0
            goto L_0x0223
        L_0x0221:
            int r0 = r4 - r0
        L_0x0223:
            android.content.Context r3 = r1.context
            android.content.res.Resources r3 = r3.getResources()
            r4 = 2131165544(0x7f070168, float:1.7945308E38)
            int r3 = r3.getDimensionPixelSize(r4)
        L_0x0230:
            if (r0 <= 0) goto L_0x024a
            android.widget.LinearLayout$LayoutParams r4 = new android.widget.LinearLayout$LayoutParams
            r5 = 1065353216(0x3f800000, float:1.0)
            r7 = 0
            r4.<init>(r7, r7, r5)
            r4.setMarginStart(r3)
            android.widget.Space r5 = new android.widget.Space
            android.content.Context r7 = r1.context
            r5.<init>(r7)
            r11.addView(r5, r4)
            int r0 = r0 + -1
            goto L_0x0230
        L_0x024a:
            java.util.Iterator r0 = r2.iterator()
        L_0x024e:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0269
            java.lang.Object r3 = r0.next()
            com.android.systemui.controls.ui.SelectionItem r3 = (com.android.systemui.controls.p004ui.SelectionItem) r3
            android.util.SparseArray<android.graphics.drawable.Drawable> r4 = com.android.systemui.controls.p004ui.RenderInfo.iconMap
            java.util.Objects.requireNonNull(r3)
            android.content.ComponentName r4 = r3.componentName
            android.graphics.drawable.Drawable r3 = r3.icon
            android.util.ArrayMap<android.content.ComponentName, android.graphics.drawable.Drawable> r5 = com.android.systemui.controls.p004ui.RenderInfo.appIconMap
            r5.put(r4, r3)
            goto L_0x024e
        L_0x0269:
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            com.android.systemui.controls.ui.ItemAdapter r3 = new com.android.systemui.controls.ui.ItemAdapter
            android.content.Context r4 = r1.context
            r3.<init>(r4)
            r3.addAll(r2)
            r0.element = r3
            android.view.ViewGroup r3 = r1.parent
            if (r3 != 0) goto L_0x027f
            r3 = 0
        L_0x027f:
            r4 = 2131427506(0x7f0b00b2, float:1.847663E38)
            android.view.View r3 = r3.requireViewById(r4)
            android.widget.TextView r3 = (android.widget.TextView) r3
            java.lang.CharSequence r4 = r6.getTitle()
            r3.setText(r4)
            android.graphics.drawable.Drawable r4 = r3.getBackground()
            java.lang.String r5 = "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable"
            java.util.Objects.requireNonNull(r4, r5)
            android.graphics.drawable.LayerDrawable r4 = (android.graphics.drawable.LayerDrawable) r4
            r5 = 0
            android.graphics.drawable.Drawable r4 = r4.getDrawable(r5)
            android.content.Context r5 = r3.getContext()
            android.content.res.Resources r5 = r5.getResources()
            r6 = 2131099790(0x7f06008e, float:1.7811943E38)
            r7 = 0
            int r5 = r5.getColor(r6, r7)
            r4.setTint(r5)
            int r2 = r2.size()
            r4 = 1
            if (r2 != r4) goto L_0x02bd
            r3.setBackground(r7)
            goto L_0x02d3
        L_0x02bd:
            android.view.ViewGroup r2 = r1.parent
            if (r2 != 0) goto L_0x02c2
            r2 = r7
        L_0x02c2:
            r3 = 2131427763(0x7f0b01b3, float:1.8477151E38)
            android.view.View r2 = r2.requireViewById(r3)
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            com.android.systemui.controls.ui.ControlsUiControllerImpl$createDropDown$2 r3 = new com.android.systemui.controls.ui.ControlsUiControllerImpl$createDropDown$2
            r3.<init>(r1, r2, r0)
            r2.setOnClickListener(r3)
        L_0x02d3:
            r0 = 2
            java.lang.String[] r0 = new java.lang.String[r0]
            android.content.Context r2 = r1.context
            android.content.res.Resources r2 = r2.getResources()
            r3 = 2131952194(0x7f130242, float:1.9540824E38)
            java.lang.String r2 = r2.getString(r3)
            r3 = 0
            r0[r3] = r2
            android.content.Context r2 = r1.context
            android.content.res.Resources r2 = r2.getResources()
            r3 = 2131952195(0x7f130243, float:1.9540826E38)
            java.lang.String r2 = r2.getString(r3)
            r3 = 1
            r0[r3] = r2
            kotlin.jvm.internal.Ref$ObjectRef r2 = new kotlin.jvm.internal.Ref$ObjectRef
            r2.<init>()
            android.widget.ArrayAdapter r3 = new android.widget.ArrayAdapter
            android.content.Context r4 = r1.context
            r5 = 2131624054(0x7f0e0076, float:1.8875277E38)
            r3.<init>(r4, r5, r0)
            r2.element = r3
            android.view.ViewGroup r0 = r1.parent
            if (r0 != 0) goto L_0x030d
            r5 = r7
            goto L_0x030e
        L_0x030d:
            r5 = r0
        L_0x030e:
            r0 = 2131427766(0x7f0b01b6, float:1.8477158E38)
            android.view.View r0 = r5.requireViewById(r0)
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1 r3 = new com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1
            r3.<init>(r1, r0, r2)
            r0.setOnClickListener(r3)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.p004ui.ControlsUiControllerImpl$show$5.invoke(java.lang.Object):java.lang.Object");
    }
}
