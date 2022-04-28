package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.management.ControlsModel;
import com.android.systemui.controls.p004ui.RenderInfo;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: ControlAdapter.kt */
public final class ControlHolder extends Holder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ControlHolderAccessibilityDelegate accessibilityDelegate;
    public final CheckBox favorite;
    public final Function2<String, Boolean, Unit> favoriteCallback;
    public final String favoriteStateDescription;
    public final ImageView icon;
    public final ControlsModel.MoveHelper moveHelper;
    public final String notFavoriteStateDescription;
    public final TextView removed;
    public final TextView subtitle;
    public final TextView title;

    public final void bindData(ElementWrapper elementWrapper) {
        CharSequence charSequence;
        ControlInterface controlInterface = (ControlInterface) elementWrapper;
        ComponentName component = controlInterface.getComponent();
        int deviceType = controlInterface.getDeviceType();
        SparseArray<Drawable> sparseArray = RenderInfo.iconMap;
        RenderInfo lookup = RenderInfo.Companion.lookup(this.itemView.getContext(), component, deviceType, 0);
        this.title.setText(controlInterface.getTitle());
        this.subtitle.setText(controlInterface.getSubtitle());
        updateFavorite(controlInterface.getFavorite());
        TextView textView = this.removed;
        if (controlInterface.getRemoved()) {
            charSequence = this.itemView.getContext().getText(C1777R.string.controls_removed);
        } else {
            charSequence = "";
        }
        textView.setText(charSequence);
        this.itemView.setOnClickListener(new ControlHolder$bindData$1(this, elementWrapper));
        Context context = this.itemView.getContext();
        ColorStateList colorStateList = context.getResources().getColorStateList(lookup.foreground, context.getTheme());
        Unit unit = null;
        this.icon.setImageTintList((ColorStateList) null);
        Icon customIcon = controlInterface.getCustomIcon();
        if (customIcon != null) {
            this.icon.setImageIcon(customIcon);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            this.icon.setImageDrawable(lookup.icon);
            if (controlInterface.getDeviceType() != 52) {
                this.icon.setImageTintList(colorStateList);
            }
        }
    }

    public final String stateDescription(boolean z) {
        if (!z) {
            return this.notFavoriteStateDescription;
        }
        if (this.moveHelper == null) {
            return this.favoriteStateDescription;
        }
        return this.itemView.getContext().getString(C1777R.string.accessibility_control_favorite_position, new Object[]{Integer.valueOf(getLayoutPosition() + 1)});
    }

    public final void updateFavorite(boolean z) {
        this.favorite.setChecked(z);
        ControlHolderAccessibilityDelegate controlHolderAccessibilityDelegate = this.accessibilityDelegate;
        Objects.requireNonNull(controlHolderAccessibilityDelegate);
        controlHolderAccessibilityDelegate.isFavorite = z;
        this.itemView.setStateDescription(stateDescription(z));
    }

    public ControlHolder(View view, ControlsModel.MoveHelper moveHelper2, Function2<? super String, ? super Boolean, Unit> function2) {
        super(view);
        this.moveHelper = moveHelper2;
        this.favoriteCallback = function2;
        this.favoriteStateDescription = view.getContext().getString(C1777R.string.accessibility_control_favorite);
        this.notFavoriteStateDescription = view.getContext().getString(C1777R.string.accessibility_control_not_favorite);
        this.icon = (ImageView) view.requireViewById(C1777R.C1779id.icon);
        this.title = (TextView) view.requireViewById(C1777R.C1779id.title);
        this.subtitle = (TextView) view.requireViewById(C1777R.C1779id.subtitle);
        this.removed = (TextView) view.requireViewById(C1777R.C1779id.status);
        CheckBox checkBox = (CheckBox) view.requireViewById(C1777R.C1779id.favorite);
        checkBox.setVisibility(0);
        this.favorite = checkBox;
        ControlHolderAccessibilityDelegate controlHolderAccessibilityDelegate = new ControlHolderAccessibilityDelegate(new ControlHolder$accessibilityDelegate$1(this), new ControlHolder$accessibilityDelegate$2(this), moveHelper2);
        this.accessibilityDelegate = controlHolderAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(view, controlHolderAccessibilityDelegate);
    }
}
