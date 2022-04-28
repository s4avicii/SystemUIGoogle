package com.android.systemui.controls.management;

import android.content.ComponentName;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.ControlsModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FavoritesModel.kt */
public final class FavoritesModel implements ControlsModel {
    public RecyclerView.Adapter<?> adapter;
    public final ComponentName componentName;
    public final CustomIconCache customIconCache;
    public int dividerPosition;
    public final ArrayList elements;
    public final FavoritesModelCallback favoritesModelCallback;
    public final FavoritesModel$itemTouchHelperCallback$1 itemTouchHelperCallback;
    public boolean modified;
    public final FavoritesModel$moveHelper$1 moveHelper = new FavoritesModel$moveHelper$1(this);

    /* compiled from: FavoritesModel.kt */
    public interface FavoritesModelCallback extends ControlsModel.ControlsModelCallback {
        void onNoneChanged(boolean z);
    }

    public final void changeFavoriteStatus(String str, boolean z) {
        boolean z2;
        Iterator it = this.elements.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            ElementWrapper elementWrapper = (ElementWrapper) it.next();
            if (!(elementWrapper instanceof ControlInterface) || !Intrinsics.areEqual(((ControlInterface) elementWrapper).getControlId(), str)) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                break;
            }
            i++;
        }
        if (i != -1) {
            int i2 = this.dividerPosition;
            if (i < i2 && z) {
                return;
            }
            if (i > i2 && !z) {
                return;
            }
            if (z) {
                onMoveItemInternal(i, i2);
            } else {
                onMoveItemInternal(i, this.elements.size() - 1);
            }
        }
    }

    public final ArrayList getFavorites() {
        List<ElementWrapper> take = CollectionsKt___CollectionsKt.take(this.elements, this.dividerPosition);
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(take, 10));
        for (ElementWrapper elementWrapper : take) {
            ControlInfoWrapper controlInfoWrapper = (ControlInfoWrapper) elementWrapper;
            Objects.requireNonNull(controlInfoWrapper);
            arrayList.add(controlInfoWrapper.controlInfo);
        }
        return arrayList;
    }

    public final void onMoveItemInternal(int i, int i2) {
        RecyclerView.Adapter<?> adapter2;
        RecyclerView.Adapter<?> adapter3;
        boolean z;
        int i3 = this.dividerPosition;
        if (i != i3) {
            boolean z2 = false;
            if ((i < i3 && i2 >= i3) || (i > i3 && i2 <= i3)) {
                if (i < i3 && i2 >= i3) {
                    ControlInfoWrapper controlInfoWrapper = (ControlInfoWrapper) this.elements.get(i);
                    Objects.requireNonNull(controlInfoWrapper);
                    controlInfoWrapper.favorite = false;
                } else if (i > i3 && i2 <= i3) {
                    ControlInfoWrapper controlInfoWrapper2 = (ControlInfoWrapper) this.elements.get(i);
                    Objects.requireNonNull(controlInfoWrapper2);
                    controlInfoWrapper2.favorite = true;
                }
                int i4 = this.dividerPosition;
                if (i >= i4 || i2 < i4) {
                    if (i > i4 && i2 <= i4) {
                        int i5 = i4 + 1;
                        this.dividerPosition = i5;
                        if (i5 == 1) {
                            DividerWrapper dividerWrapper = (DividerWrapper) this.elements.get(i4);
                            Objects.requireNonNull(dividerWrapper);
                            dividerWrapper.showNone = false;
                            this.favoritesModelCallback.onNoneChanged(false);
                            z = true;
                        } else {
                            z = false;
                        }
                        if (this.dividerPosition == this.elements.size() - 1) {
                            DividerWrapper dividerWrapper2 = (DividerWrapper) this.elements.get(i4);
                            Objects.requireNonNull(dividerWrapper2);
                            dividerWrapper2.showDivider = false;
                        } else {
                            z2 = z;
                        }
                    }
                    if (z2 && (adapter3 = this.adapter) != null) {
                        adapter3.notifyItemChanged(i4);
                    }
                    z2 = true;
                } else {
                    int i6 = i4 - 1;
                    this.dividerPosition = i6;
                    if (i6 == 0) {
                        DividerWrapper dividerWrapper3 = (DividerWrapper) this.elements.get(i4);
                        Objects.requireNonNull(dividerWrapper3);
                        dividerWrapper3.showNone = true;
                        this.favoritesModelCallback.onNoneChanged(true);
                        z2 = true;
                    }
                    if (this.dividerPosition == this.elements.size() - 2) {
                        DividerWrapper dividerWrapper4 = (DividerWrapper) this.elements.get(i4);
                        Objects.requireNonNull(dividerWrapper4);
                        dividerWrapper4.showDivider = true;
                    }
                    adapter3.notifyItemChanged(i4);
                    z2 = true;
                }
                z2 = true;
                adapter3.notifyItemChanged(i4);
                z2 = true;
            }
            if (i < i2) {
                int i7 = i;
                while (i7 < i2) {
                    int i8 = i7 + 1;
                    Collections.swap(this.elements, i7, i8);
                    i7 = i8;
                }
            } else {
                int i9 = i2 + 1;
                if (i9 <= i) {
                    int i10 = i;
                    while (true) {
                        int i11 = i10 - 1;
                        Collections.swap(this.elements, i10, i11);
                        if (i10 == i9) {
                            break;
                        }
                        i10 = i11;
                    }
                }
            }
            RecyclerView.Adapter<?> adapter4 = this.adapter;
            if (adapter4 != null) {
                adapter4.notifyItemMoved(i, i2);
            }
            if (z2 && (adapter2 = this.adapter) != null) {
                adapter2.mObservable.notifyItemRangeChanged(i2, 1, new Object());
            }
            if (!this.modified) {
                this.modified = true;
                this.favoritesModelCallback.onFirstChange();
            }
        }
    }

    public FavoritesModel(CustomIconCache customIconCache2, ComponentName componentName2, List list, ControlsEditingActivity$favoritesModelCallback$1 controlsEditingActivity$favoritesModelCallback$1) {
        this.customIconCache = customIconCache2;
        this.componentName = componentName2;
        this.favoritesModelCallback = controlsEditingActivity$favoritesModelCallback$1;
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ControlInfoWrapper(this.componentName, (ControlInfo) it.next(), new FavoritesModel$elements$1$1(this.customIconCache)));
        }
        ArrayList plus = CollectionsKt___CollectionsKt.plus((Collection) arrayList, (Object) new DividerWrapper(0));
        this.elements = plus;
        this.dividerPosition = plus.size() - 1;
        this.itemTouchHelperCallback = new FavoritesModel$itemTouchHelperCallback$1(this);
    }

    public final List<ElementWrapper> getElements() {
        return this.elements;
    }

    public final ControlsModel.MoveHelper getMoveHelper() {
        return this.moveHelper;
    }
}
