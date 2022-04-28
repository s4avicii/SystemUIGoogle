package com.android.systemui.controls.controller;

import android.content.ComponentName;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$addFavorite$1 implements Runnable {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ ControlInfo $controlInfo;
    public final /* synthetic */ CharSequence $structureName;
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$addFavorite$1(ComponentName componentName, CharSequence charSequence, ControlInfo controlInfo, ControlsControllerImpl controlsControllerImpl) {
        this.$componentName = componentName;
        this.$structureName = charSequence;
        this.$controlInfo = controlInfo;
        this.this$0 = controlsControllerImpl;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: com.android.systemui.controls.controller.StructureInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: com.android.systemui.controls.controller.StructureInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: com.android.systemui.controls.controller.StructureInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: com.android.systemui.controls.controller.StructureInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: com.android.systemui.controls.controller.StructureInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: com.android.systemui.controls.controller.StructureInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: com.android.systemui.controls.controller.StructureInfo} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r8 = this;
            java.util.Map<android.content.ComponentName, ? extends java.util.List<com.android.systemui.controls.controller.StructureInfo>> r0 = com.android.systemui.controls.controller.Favorites.favMap
            android.content.ComponentName r0 = r8.$componentName
            java.lang.CharSequence r1 = r8.$structureName
            com.android.systemui.controls.controller.ControlInfo r2 = r8.$controlInfo
            java.util.ArrayList r3 = com.android.systemui.controls.controller.Favorites.getControlsForComponent(r0)
            boolean r4 = r3.isEmpty()
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0015
            goto L_0x0034
        L_0x0015:
            java.util.Iterator r3 = r3.iterator()
        L_0x0019:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0034
            java.lang.Object r4 = r3.next()
            com.android.systemui.controls.controller.ControlInfo r4 = (com.android.systemui.controls.controller.ControlInfo) r4
            java.util.Objects.requireNonNull(r4)
            java.lang.String r4 = r4.controlId
            java.lang.String r7 = r2.controlId
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r7)
            if (r4 == 0) goto L_0x0019
            r3 = r5
            goto L_0x0035
        L_0x0034:
            r3 = r6
        L_0x0035:
            if (r3 == 0) goto L_0x0039
            r5 = r6
            goto L_0x007f
        L_0x0039:
            java.util.Map<android.content.ComponentName, ? extends java.util.List<com.android.systemui.controls.controller.StructureInfo>> r3 = com.android.systemui.controls.controller.Favorites.favMap
            java.lang.Object r3 = r3.get(r0)
            java.util.List r3 = (java.util.List) r3
            r4 = 0
            if (r3 != 0) goto L_0x0045
            goto L_0x0064
        L_0x0045:
            java.util.Iterator r3 = r3.iterator()
        L_0x0049:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x0062
            java.lang.Object r6 = r3.next()
            r7 = r6
            com.android.systemui.controls.controller.StructureInfo r7 = (com.android.systemui.controls.controller.StructureInfo) r7
            java.util.Objects.requireNonNull(r7)
            java.lang.CharSequence r7 = r7.structure
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r1)
            if (r7 == 0) goto L_0x0049
            r4 = r6
        L_0x0062:
            com.android.systemui.controls.controller.StructureInfo r4 = (com.android.systemui.controls.controller.StructureInfo) r4
        L_0x0064:
            if (r4 != 0) goto L_0x006d
            com.android.systemui.controls.controller.StructureInfo r4 = new com.android.systemui.controls.controller.StructureInfo
            kotlin.collections.EmptyList r3 = kotlin.collections.EmptyList.INSTANCE
            r4.<init>(r0, r1, r3)
        L_0x006d:
            java.util.List<com.android.systemui.controls.controller.ControlInfo> r0 = r4.controls
            java.util.ArrayList r0 = kotlin.collections.CollectionsKt___CollectionsKt.plus((java.util.Collection) r0, (java.lang.Object) r2)
            android.content.ComponentName r1 = r4.componentName
            java.lang.CharSequence r2 = r4.structure
            com.android.systemui.controls.controller.StructureInfo r3 = new com.android.systemui.controls.controller.StructureInfo
            r3.<init>(r1, r2, r0)
            com.android.systemui.controls.controller.Favorites.replaceControls(r3)
        L_0x007f:
            if (r5 == 0) goto L_0x008c
            com.android.systemui.controls.controller.ControlsControllerImpl r8 = r8.this$0
            com.android.systemui.controls.controller.ControlsFavoritePersistenceWrapper r8 = r8.persistenceWrapper
            java.util.ArrayList r0 = com.android.systemui.controls.controller.Favorites.getAllStructures()
            r8.storeFavorites(r0)
        L_0x008c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.controller.ControlsControllerImpl$addFavorite$1.run():void");
    }
}
