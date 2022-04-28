package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.ViewGroup;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarIconList;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class StatusBarIconControllerImpl extends StatusBarIconList implements TunerService.Tunable, ConfigurationController.ConfigurationListener, Dumpable, CommandQueue.Callbacks, StatusBarIconController, DemoMode {
    public Context mContext;
    public final ArrayList<StatusBarIconController.IconManager> mIconGroups = new ArrayList<>();
    public final ArraySet<String> mIconHideList = new ArraySet<>();

    public final void removeIcon(String str) {
        removeAllIconsForSlot(str);
    }

    public final void setIcon(String str, int i, String str2) {
        int slotIndex = getSlotIndex(str);
        StatusBarIconHolder icon = getIcon(slotIndex, 0);
        if (icon == null) {
            StatusBarIcon statusBarIcon = new StatusBarIcon(UserHandle.SYSTEM, this.mContext.getPackageName(), Icon.createWithResource(this.mContext, i), 0, 0, str2);
            StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder();
            statusBarIconHolder.mIcon = statusBarIcon;
            setIcon(slotIndex, statusBarIconHolder);
            return;
        }
        icon.mIcon.icon = Icon.createWithResource(this.mContext, i);
        icon.mIcon.contentDescription = str2;
        handleSet(slotIndex, icon);
    }

    public final void setIconVisibility(String str, boolean z) {
        setIconVisibility(str, z, 0);
    }

    public final void addIconGroup(StatusBarIconController.IconManager iconManager) {
        Iterator<StatusBarIconController.IconManager> it = this.mIconGroups.iterator();
        while (it.hasNext()) {
            if (it.next().mGroup == iconManager.mGroup) {
                Log.e("StatusBarIconController", "Adding new IconManager for the same ViewGroup. This could cause unexpected results.");
            }
        }
        this.mIconGroups.add(iconManager);
        ArrayList arrayList = new ArrayList(this.mSlots);
        for (int i = 0; i < arrayList.size(); i++) {
            StatusBarIconList.Slot slot = (StatusBarIconList.Slot) arrayList.get(i);
            ArrayList holderListInViewOrder = slot.getHolderListInViewOrder();
            boolean contains = this.mIconHideList.contains(slot.mName);
            Iterator it2 = holderListInViewOrder.iterator();
            while (it2.hasNext()) {
                StatusBarIconHolder statusBarIconHolder = (StatusBarIconHolder) it2.next();
                Objects.requireNonNull(statusBarIconHolder);
                iconManager.onIconAdded(getViewIndex(getSlotIndex(slot.mName), statusBarIconHolder.mTag), slot.mName, contains, statusBarIconHolder);
            }
        }
    }

    public final List<String> demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("status");
        return arrayList;
    }

    public final void dispatchDemoCommand(String str, Bundle bundle) {
        Iterator<StatusBarIconController.IconManager> it = this.mIconGroups.iterator();
        while (it.hasNext()) {
            StatusBarIconController.IconManager next = it.next();
            Objects.requireNonNull(next);
            next.mDemoStatusIcons.dispatchDemoCommand(str, bundle);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("StatusBarIconController state:");
        Iterator<StatusBarIconController.IconManager> it = this.mIconGroups.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            StatusBarIconController.IconManager next = it.next();
            Objects.requireNonNull(next);
            if (next.mShouldLog) {
                ViewGroup viewGroup = next.mGroup;
                int childCount = viewGroup.getChildCount();
                printWriter.println("  icon views: " + childCount);
                for (int i = 0; i < childCount; i++) {
                    printWriter.println("    [" + i + "] icon=" + ((StatusIconDisplayable) viewGroup.getChildAt(i)));
                }
            }
        }
        printWriter.println("StatusBarIconList state:");
        int size = this.mSlots.size();
        printWriter.println("  icon slots: " + size);
        for (int i2 = 0; i2 < size; i2++) {
            printWriter.printf("    %2d:%s\n", new Object[]{Integer.valueOf(i2), this.mSlots.get(i2).toString()});
        }
    }

    public final void onDemoModeFinished() {
        Iterator<StatusBarIconController.IconManager> it = this.mIconGroups.iterator();
        while (it.hasNext()) {
            StatusBarIconController.IconManager next = it.next();
            Objects.requireNonNull(next);
            next.onDemoModeFinished();
        }
    }

    public final void onDemoModeStarted() {
        Iterator<StatusBarIconController.IconManager> it = this.mIconGroups.iterator();
        while (it.hasNext()) {
            StatusBarIconController.IconManager next = it.next();
            Objects.requireNonNull(next);
            next.onDemoModeStarted();
        }
    }

    public final void onDensityOrFontScaleChanged() {
        int size = this.mIconGroups.size();
        while (true) {
            size--;
            if (size >= 0) {
                StatusBarIconController.IconManager iconManager = this.mIconGroups.get(size);
                removeIconGroup(iconManager);
                addIconGroup(iconManager);
            } else {
                return;
            }
        }
    }

    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            this.mIconHideList.clear();
            this.mIconHideList.addAll(StatusBarIconController.getIconHideList(this.mContext, str2));
            ArrayList arrayList = new ArrayList(this.mSlots);
            ArrayMap arrayMap = new ArrayMap();
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                StatusBarIconList.Slot slot = (StatusBarIconList.Slot) arrayList.get(size);
                Objects.requireNonNull(slot);
                ArrayList arrayList2 = new ArrayList();
                StatusBarIconHolder statusBarIconHolder = slot.mHolder;
                if (statusBarIconHolder != null) {
                    arrayList2.add(statusBarIconHolder);
                }
                ArrayList<StatusBarIconHolder> arrayList3 = slot.mSubSlots;
                if (arrayList3 != null) {
                    arrayList2.addAll(arrayList3);
                }
                arrayMap.put(slot, arrayList2);
                removeAllIconsForSlot(slot.mName);
            }
            for (int i = 0; i < arrayList.size(); i++) {
                StatusBarIconList.Slot slot2 = (StatusBarIconList.Slot) arrayList.get(i);
                List<StatusBarIconHolder> list = (List) arrayMap.get(slot2);
                if (list != null) {
                    for (StatusBarIconHolder icon : list) {
                        Objects.requireNonNull(slot2);
                        setIcon(getSlotIndex(slot2.mName), icon);
                    }
                }
            }
        }
    }

    public final void removeIcon(String str, int i) {
        removeIcon(getSlotIndex(str), i);
    }

    public final void setIconVisibility(String str, boolean z, int i) {
        boolean z2;
        boolean z3;
        int slotIndex = getSlotIndex(str);
        StatusBarIconHolder icon = getIcon(slotIndex, i);
        if (icon != null) {
            int i2 = icon.mType;
            if (i2 == 0) {
                z2 = icon.mIcon.visible;
            } else if (i2 == 1) {
                z2 = icon.mWifiState.visible;
            } else if (i2 != 2) {
                z2 = true;
            } else {
                z2 = icon.mMobileState.visible;
            }
            if (z2 != z) {
                if (i2 == 0) {
                    z3 = icon.mIcon.visible;
                } else if (i2 == 1) {
                    z3 = icon.mWifiState.visible;
                } else if (i2 != 2) {
                    z3 = true;
                } else {
                    z3 = icon.mMobileState.visible;
                }
                if (z3 != z) {
                    if (i2 == 0) {
                        icon.mIcon.visible = z;
                    } else if (i2 == 1) {
                        icon.mWifiState.visible = z;
                    } else if (i2 == 2) {
                        icon.mMobileState.visible = z;
                    }
                }
                handleSet(slotIndex, icon);
            }
        }
    }

    public StatusBarIconControllerImpl(Context context, CommandQueue commandQueue, DemoModeController demoModeController, ConfigurationController configurationController, TunerService tunerService, DumpManager dumpManager) {
        super(context.getResources().getStringArray(17236125));
        configurationController.addCallback(this);
        this.mContext = context;
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        tunerService.addTunable(this, "icon_blacklist");
        demoModeController.addCallback((DemoMode) this);
        dumpManager.registerDumpable("StatusBarIconControllerImpl", this);
    }

    public final void handleSet(int i, StatusBarIconHolder statusBarIconHolder) {
        Objects.requireNonNull(statusBarIconHolder);
        this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda4(getViewIndex(i, statusBarIconHolder.mTag), statusBarIconHolder));
    }

    public final void removeAllIconsForSlot(String str) {
        StatusBarIconList.Slot slot = getSlot(str);
        if (slot.hasIconsInSlot()) {
            int slotIndex = getSlotIndex(str);
            Iterator it = slot.getHolderListInViewOrder().iterator();
            while (it.hasNext()) {
                StatusBarIconHolder statusBarIconHolder = (StatusBarIconHolder) it.next();
                Objects.requireNonNull(statusBarIconHolder);
                int viewIndex = getViewIndex(slotIndex, statusBarIconHolder.mTag);
                int i = statusBarIconHolder.mTag;
                if (i == 0) {
                    slot.mHolder = null;
                } else {
                    int indexForTag = slot.getIndexForTag(i);
                    if (indexForTag != -1) {
                        slot.mSubSlots.remove(indexForTag);
                    }
                }
                this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda0(viewIndex));
            }
        }
    }

    public final void removeIcon(int i, int i2) {
        if (getIcon(i, i2) != null) {
            StatusBarIconList.Slot slot = this.mSlots.get(i);
            Objects.requireNonNull(slot);
            if (i2 == 0) {
                slot.mHolder = null;
            } else {
                int indexForTag = slot.getIndexForTag(i2);
                if (indexForTag != -1) {
                    slot.mSubSlots.remove(indexForTag);
                }
            }
            this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda1(getViewIndex(i, 0)));
        }
    }

    public final void removeIconGroup(StatusBarIconController.IconManager iconManager) {
        iconManager.destroy();
        this.mIconGroups.remove(iconManager);
    }

    public final void setCallStrengthIcons(String str, ArrayList arrayList) {
        StatusBarIconList.Slot slot = getSlot(str);
        int slotIndex = getSlotIndex(str);
        Collections.reverse(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            StatusBarSignalPolicy.CallIndicatorIconState callIndicatorIconState = (StatusBarSignalPolicy.CallIndicatorIconState) it.next();
            if (!callIndicatorIconState.isNoCalling) {
                StatusBarIconHolder holderForTag = slot.getHolderForTag(callIndicatorIconState.subId);
                if (holderForTag == null) {
                    setIcon(slotIndex, StatusBarIconHolder.fromCallIndicatorState(this.mContext, callIndicatorIconState));
                } else {
                    holderForTag.mIcon = new StatusBarIcon(UserHandle.SYSTEM, this.mContext.getPackageName(), Icon.createWithResource(this.mContext, callIndicatorIconState.callStrengthResId), 0, 0, callIndicatorIconState.callStrengthDescription);
                    setIcon(slotIndex, holderForTag);
                }
            }
            setIconVisibility(str, !callIndicatorIconState.isNoCalling, callIndicatorIconState.subId);
        }
    }

    public final void setExternalIcon(String str) {
        this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda3(getViewIndex(getSlotIndex(str), 0), this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.status_bar_icon_drawing_size)));
    }

    public final void setIconAccessibilityLiveRegion(String str, int i) {
        StatusBarIconList.Slot slot = getSlot(str);
        if (slot.hasIconsInSlot()) {
            int slotIndex = getSlotIndex(str);
            Iterator it = slot.getHolderListInViewOrder().iterator();
            while (it.hasNext()) {
                StatusBarIconHolder statusBarIconHolder = (StatusBarIconHolder) it.next();
                Objects.requireNonNull(statusBarIconHolder);
                this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda2(getViewIndex(slotIndex, statusBarIconHolder.mTag), i));
            }
        }
    }

    public final void setMobileIcons(String str, ArrayList arrayList) {
        StatusBarIconList.Slot slot = getSlot(str);
        int slotIndex = getSlotIndex(str);
        Collections.reverse(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            StatusBarSignalPolicy.MobileIconState mobileIconState = (StatusBarSignalPolicy.MobileIconState) it.next();
            StatusBarIconHolder holderForTag = slot.getHolderForTag(mobileIconState.subId);
            if (holderForTag == null) {
                StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder();
                statusBarIconHolder.mMobileState = mobileIconState;
                statusBarIconHolder.mType = 2;
                statusBarIconHolder.mTag = mobileIconState.subId;
                setIcon(slotIndex, statusBarIconHolder);
            } else {
                holderForTag.mMobileState = mobileIconState;
                handleSet(slotIndex, holderForTag);
            }
        }
    }

    public final void setNoCallingIcons(String str, ArrayList arrayList) {
        StatusBarIconList.Slot slot = getSlot(str);
        int slotIndex = getSlotIndex(str);
        Collections.reverse(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            StatusBarSignalPolicy.CallIndicatorIconState callIndicatorIconState = (StatusBarSignalPolicy.CallIndicatorIconState) it.next();
            if (callIndicatorIconState.isNoCalling) {
                StatusBarIconHolder holderForTag = slot.getHolderForTag(callIndicatorIconState.subId);
                if (holderForTag == null) {
                    setIcon(slotIndex, StatusBarIconHolder.fromCallIndicatorState(this.mContext, callIndicatorIconState));
                } else {
                    holderForTag.mIcon = new StatusBarIcon(UserHandle.SYSTEM, this.mContext.getPackageName(), Icon.createWithResource(this.mContext, callIndicatorIconState.noCallingResId), 0, 0, callIndicatorIconState.noCallingDescription);
                    setIcon(slotIndex, holderForTag);
                }
            }
            setIconVisibility(str, callIndicatorIconState.isNoCalling, callIndicatorIconState.subId);
        }
    }

    public final void setSignalIcon(String str, StatusBarSignalPolicy.WifiIconState wifiIconState) {
        int slotIndex = getSlotIndex(str);
        StatusBarIconHolder icon = getIcon(slotIndex, 0);
        if (icon == null) {
            StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder();
            statusBarIconHolder.mWifiState = wifiIconState;
            statusBarIconHolder.mType = 1;
            setIcon(slotIndex, statusBarIconHolder);
            return;
        }
        icon.mWifiState = wifiIconState;
        handleSet(slotIndex, icon);
    }

    public final void setIcon(String str, StatusBarIcon statusBarIcon) {
        int slotIndex = getSlotIndex(str);
        StatusBarIconList.Slot slot = this.mSlots.get(slotIndex);
        Objects.requireNonNull(slot);
        String str2 = slot.mName;
        if (statusBarIcon == null) {
            removeAllIconsForSlot(str2);
            return;
        }
        StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder();
        statusBarIconHolder.mIcon = statusBarIcon;
        setIcon(slotIndex, statusBarIconHolder);
    }

    public final void setIcon(int i, StatusBarIconHolder statusBarIconHolder) {
        Objects.requireNonNull(statusBarIconHolder);
        boolean z = getIcon(i, statusBarIconHolder.mTag) == null;
        StatusBarIconList.Slot slot = this.mSlots.get(i);
        Objects.requireNonNull(slot);
        int i2 = statusBarIconHolder.mTag;
        if (i2 == 0) {
            slot.mHolder = statusBarIconHolder;
        } else if (slot.mSubSlots == null) {
            ArrayList<StatusBarIconHolder> arrayList = new ArrayList<>();
            slot.mSubSlots = arrayList;
            arrayList.add(statusBarIconHolder);
        } else if (slot.getIndexForTag(i2) == -1) {
            slot.mSubSlots.add(statusBarIconHolder);
        }
        if (z) {
            StatusBarIconList.Slot slot2 = this.mSlots.get(i);
            Objects.requireNonNull(slot2);
            String str = slot2.mName;
            this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda5(getViewIndex(i, statusBarIconHolder.mTag), str, this.mIconHideList.contains(str), statusBarIconHolder));
            return;
        }
        handleSet(i, statusBarIconHolder);
    }
}
