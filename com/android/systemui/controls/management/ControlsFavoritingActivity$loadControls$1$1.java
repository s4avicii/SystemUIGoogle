package com.android.systemui.controls.management;

import android.animation.AnimatorSet;
import android.content.res.Resources;
import android.widget.TextView;
import androidx.mediarouter.R$bool;
import androidx.viewpager2.widget.FakeDrag;
import androidx.viewpager2.widget.ScrollEventAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$loadControls$1$1<T> implements Consumer {
    public final /* synthetic */ CharSequence $emptyZoneString;
    public final /* synthetic */ ControlsFavoritingActivity this$0;

    public ControlsFavoritingActivity$loadControls$1$1(ControlsFavoritingActivity controlsFavoritingActivity, CharSequence charSequence) {
        this.this$0 = controlsFavoritingActivity;
        this.$emptyZoneString = charSequence;
    }

    public final void accept(Object obj) {
        ControlsController.LoadData loadData = (ControlsController.LoadData) obj;
        List<ControlStatus> allControls = loadData.getAllControls();
        List<String> favoritesIds = loadData.getFavoritesIds();
        final boolean errorOnLoad = loadData.getErrorOnLoad();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T next : allControls) {
            ControlStatus controlStatus = (ControlStatus) next;
            Objects.requireNonNull(controlStatus);
            Object structure = controlStatus.control.getStructure();
            if (structure == null) {
                structure = "";
            }
            Object obj2 = linkedHashMap.get(structure);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(structure, obj2);
            }
            ((List) obj2).add(next);
        }
        ControlsFavoritingActivity controlsFavoritingActivity = this.this$0;
        CharSequence charSequence = this.$emptyZoneString;
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            arrayList.add(new StructureContainer((CharSequence) entry.getKey(), new AllModel((List) entry.getValue(), favoritesIds, charSequence, controlsFavoritingActivity.controlsModelCallback)));
        }
        ControlsFavoritingActivity$onCreate$$inlined$compareBy$1 controlsFavoritingActivity$onCreate$$inlined$compareBy$1 = this.this$0.comparator;
        if (controlsFavoritingActivity$onCreate$$inlined$compareBy$1 == null) {
            controlsFavoritingActivity$onCreate$$inlined$compareBy$1 = null;
        }
        controlsFavoritingActivity.listOfStructures = CollectionsKt___CollectionsKt.sortedWith(arrayList, controlsFavoritingActivity$onCreate$$inlined$compareBy$1);
        ControlsFavoritingActivity controlsFavoritingActivity2 = this.this$0;
        Iterator<StructureContainer> it = controlsFavoritingActivity2.listOfStructures.iterator();
        final int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            StructureContainer next2 = it.next();
            Objects.requireNonNull(next2);
            if (Intrinsics.areEqual(next2.structureName, controlsFavoritingActivity2.structureExtra)) {
                break;
            }
            i++;
        }
        if (i == -1) {
            i = 0;
        }
        if (this.this$0.getIntent().getBooleanExtra("extra_single_structure", false)) {
            ControlsFavoritingActivity controlsFavoritingActivity3 = this.this$0;
            controlsFavoritingActivity3.listOfStructures = Collections.singletonList(controlsFavoritingActivity3.listOfStructures.get(i));
        }
        final ControlsFavoritingActivity controlsFavoritingActivity4 = this.this$0;
        controlsFavoritingActivity4.executor.execute(new Runnable() {
            public final void run() {
                ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                ViewPager2 viewPager2 = controlsFavoritingActivity.structurePager;
                TextView textView = null;
                if (viewPager2 == null) {
                    viewPager2 = null;
                }
                viewPager2.setAdapter(new StructureAdapter(controlsFavoritingActivity.listOfStructures));
                ViewPager2 viewPager22 = ControlsFavoritingActivity.this.structurePager;
                if (viewPager22 == null) {
                    viewPager22 = null;
                }
                int i = i;
                Objects.requireNonNull(viewPager22);
                FakeDrag fakeDrag = viewPager22.mFakeDragger;
                Objects.requireNonNull(fakeDrag);
                ScrollEventAdapter scrollEventAdapter = fakeDrag.mScrollEventAdapter;
                Objects.requireNonNull(scrollEventAdapter);
                if (!scrollEventAdapter.mFakeDragging) {
                    viewPager22.setCurrentItemInternal(i);
                    int i2 = 0;
                    if (errorOnLoad) {
                        ControlsFavoritingActivity controlsFavoritingActivity2 = ControlsFavoritingActivity.this;
                        TextView textView2 = controlsFavoritingActivity2.statusText;
                        if (textView2 == null) {
                            textView2 = null;
                        }
                        Resources resources = controlsFavoritingActivity2.getResources();
                        Object[] objArr = new Object[1];
                        Object obj = ControlsFavoritingActivity.this.appName;
                        if (obj == null) {
                            obj = "";
                        }
                        objArr[0] = obj;
                        textView2.setText(resources.getString(C1777R.string.controls_favorite_load_error, objArr));
                        TextView textView3 = ControlsFavoritingActivity.this.subtitleView;
                        if (textView3 != null) {
                            textView = textView3;
                        }
                        textView.setVisibility(8);
                    } else if (ControlsFavoritingActivity.this.listOfStructures.isEmpty()) {
                        ControlsFavoritingActivity controlsFavoritingActivity3 = ControlsFavoritingActivity.this;
                        TextView textView4 = controlsFavoritingActivity3.statusText;
                        if (textView4 == null) {
                            textView4 = null;
                        }
                        textView4.setText(controlsFavoritingActivity3.getResources().getString(C1777R.string.controls_favorite_load_none));
                        TextView textView5 = ControlsFavoritingActivity.this.subtitleView;
                        if (textView5 != null) {
                            textView = textView5;
                        }
                        textView.setVisibility(8);
                    } else {
                        TextView textView6 = ControlsFavoritingActivity.this.statusText;
                        if (textView6 == null) {
                            textView6 = null;
                        }
                        textView6.setVisibility(8);
                        ControlsFavoritingActivity controlsFavoritingActivity4 = ControlsFavoritingActivity.this;
                        ManagementPageIndicator managementPageIndicator = controlsFavoritingActivity4.pageIndicator;
                        if (managementPageIndicator == null) {
                            managementPageIndicator = null;
                        }
                        managementPageIndicator.setNumPages(controlsFavoritingActivity4.listOfStructures.size());
                        ManagementPageIndicator managementPageIndicator2 = ControlsFavoritingActivity.this.pageIndicator;
                        if (managementPageIndicator2 == null) {
                            managementPageIndicator2 = null;
                        }
                        managementPageIndicator2.setLocation(0.0f);
                        ControlsFavoritingActivity controlsFavoritingActivity5 = ControlsFavoritingActivity.this;
                        ManagementPageIndicator managementPageIndicator3 = controlsFavoritingActivity5.pageIndicator;
                        if (managementPageIndicator3 == null) {
                            managementPageIndicator3 = null;
                        }
                        if (controlsFavoritingActivity5.listOfStructures.size() <= 1) {
                            i2 = 4;
                        }
                        managementPageIndicator3.setVisibility(i2);
                        ManagementPageIndicator managementPageIndicator4 = ControlsFavoritingActivity.this.pageIndicator;
                        if (managementPageIndicator4 == null) {
                            managementPageIndicator4 = null;
                        }
                        AnimatorSet enterAnimation = R$bool.enterAnimation(managementPageIndicator4);
                        enterAnimation.addListener(new ControlsFavoritingActivity$loadControls$1$1$2$1$1(ControlsFavoritingActivity.this));
                        enterAnimation.start();
                        ViewPager2 viewPager23 = ControlsFavoritingActivity.this.structurePager;
                        if (viewPager23 != null) {
                            textView = viewPager23;
                        }
                        R$bool.enterAnimation(textView).start();
                    }
                } else {
                    throw new IllegalStateException("Cannot change current item when ViewPager2 is fake dragging");
                }
            }
        });
    }
}
