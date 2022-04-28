package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import androidx.mediarouter.R$bool;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.p004ui.ControlsUiController;
import com.android.systemui.util.LifecycleActivity;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsEditingActivity.kt */
public final class ControlsEditingActivity extends LifecycleActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ComponentName component;
    public final ControlsControllerImpl controller;
    public final ControlsEditingActivity$currentUserTracker$1 currentUserTracker;
    public final CustomIconCache customIconCache;
    public final ControlsEditingActivity$favoritesModelCallback$1 favoritesModelCallback = new ControlsEditingActivity$favoritesModelCallback$1(this);
    public FavoritesModel model;
    public View saveButton;
    public CharSequence structure;
    public TextView subtitle;
    public final ControlsUiController uiController;

    public final void onDestroy() {
        this.currentUserTracker.stopTracking();
        super.onDestroy();
    }

    public ControlsEditingActivity(ControlsControllerImpl controlsControllerImpl, BroadcastDispatcher broadcastDispatcher, CustomIconCache customIconCache2, ControlsUiController controlsUiController) {
        this.controller = controlsControllerImpl;
        this.customIconCache = customIconCache2;
        this.uiController = controlsUiController;
        this.currentUserTracker = new ControlsEditingActivity$currentUserTracker$1(this, broadcastDispatcher);
    }

    public final void onBackPressed() {
        R$bool.exitAnimation((ViewGroup) requireViewById(C1777R.C1779id.controls_management_root), new ControlsEditingActivity$animateExitAndFinish$1(this)).start();
    }

    public final void onCreate(Bundle bundle) {
        Unit unit;
        Unit unit2;
        super.onCreate(bundle);
        ComponentName componentName = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        CharSequence charSequence = null;
        if (componentName == null) {
            unit = null;
        } else {
            this.component = componentName;
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            finish();
        }
        CharSequence charSequenceExtra = getIntent().getCharSequenceExtra("extra_structure");
        if (charSequenceExtra == null) {
            unit2 = null;
        } else {
            this.structure = charSequenceExtra;
            unit2 = Unit.INSTANCE;
        }
        if (unit2 == null) {
            finish();
        }
        setContentView(C1777R.layout.controls_management);
        this.lifecycle.addObserver(new ControlsAnimations$observerForAnimations$1(getIntent(), (ViewGroup) requireViewById(C1777R.C1779id.controls_management_root), getWindow()));
        ViewStub viewStub = (ViewStub) requireViewById(C1777R.C1779id.stub);
        viewStub.setLayoutResource(C1777R.layout.controls_management_editing);
        viewStub.inflate();
        TextView textView = (TextView) requireViewById(C1777R.C1779id.title);
        CharSequence charSequence2 = this.structure;
        if (charSequence2 == null) {
            charSequence2 = null;
        }
        textView.setText(charSequence2);
        CharSequence charSequence3 = this.structure;
        if (charSequence3 != null) {
            charSequence = charSequence3;
        }
        setTitle(charSequence);
        TextView textView2 = (TextView) requireViewById(C1777R.C1779id.subtitle);
        textView2.setText(C1777R.string.controls_favorite_rearrange);
        this.subtitle = textView2;
        View requireViewById = requireViewById(C1777R.C1779id.done);
        Button button = (Button) requireViewById;
        button.setEnabled(false);
        button.setText(C1777R.string.save);
        button.setOnClickListener(new ControlsEditingActivity$bindButtons$1$1(this));
        this.saveButton = requireViewById;
    }

    public final void onStart() {
        Object obj;
        List list;
        super.onStart();
        ControlsControllerImpl controlsControllerImpl = this.controller;
        ComponentName componentName = this.component;
        FavoritesModel favoritesModel = null;
        if (componentName == null) {
            componentName = null;
        }
        CharSequence charSequence = this.structure;
        if (charSequence == null) {
            charSequence = null;
        }
        Objects.requireNonNull(controlsControllerImpl);
        Map<ComponentName, ? extends List<StructureInfo>> map = Favorites.favMap;
        EmptyList emptyList = EmptyList.INSTANCE;
        Iterator it = Favorites.getStructuresForComponent(componentName).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            StructureInfo structureInfo = (StructureInfo) obj;
            Objects.requireNonNull(structureInfo);
            if (Intrinsics.areEqual(structureInfo.structure, charSequence)) {
                break;
            }
        }
        StructureInfo structureInfo2 = (StructureInfo) obj;
        if (structureInfo2 == null) {
            list = null;
        } else {
            list = structureInfo2.controls;
        }
        if (list == null) {
            list = EmptyList.INSTANCE;
        }
        CustomIconCache customIconCache2 = this.customIconCache;
        ComponentName componentName2 = this.component;
        if (componentName2 == null) {
            componentName2 = null;
        }
        this.model = new FavoritesModel(customIconCache2, componentName2, list, this.favoritesModelCallback);
        float f = getResources().getFloat(C1777R.dimen.control_card_elevation);
        RecyclerView recyclerView = (RecyclerView) requireViewById(C1777R.C1779id.list);
        recyclerView.setAlpha(0.0f);
        ControlAdapter controlAdapter = new ControlAdapter(f);
        controlAdapter.registerAdapterDataObserver(new ControlsEditingActivity$setUpList$adapter$1$1(recyclerView));
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.controls_card_margin);
        MarginItemDecorator marginItemDecorator = new MarginItemDecorator(dimensionPixelSize, dimensionPixelSize);
        recyclerView.setAdapter(controlAdapter);
        recyclerView.getContext();
        ControlsEditingActivity$setUpList$1$1 controlsEditingActivity$setUpList$1$1 = new ControlsEditingActivity$setUpList$1$1();
        controlsEditingActivity$setUpList$1$1.mSpanSizeLookup = controlAdapter.spanSizeLookup;
        recyclerView.setLayoutManager(controlsEditingActivity$setUpList$1$1);
        recyclerView.addItemDecoration(marginItemDecorator);
        FavoritesModel favoritesModel2 = this.model;
        if (favoritesModel2 == null) {
            favoritesModel2 = null;
        }
        controlAdapter.model = favoritesModel2;
        controlAdapter.notifyDataSetChanged();
        FavoritesModel favoritesModel3 = this.model;
        if (favoritesModel3 == null) {
            favoritesModel3 = null;
        }
        Objects.requireNonNull(favoritesModel3);
        favoritesModel3.adapter = controlAdapter;
        FavoritesModel favoritesModel4 = this.model;
        if (favoritesModel4 != null) {
            favoritesModel = favoritesModel4;
        }
        Objects.requireNonNull(favoritesModel);
        new ItemTouchHelper(favoritesModel.itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        this.currentUserTracker.startTracking();
    }

    public final void onStop() {
        super.onStop();
        this.currentUserTracker.stopTracking();
    }
}
