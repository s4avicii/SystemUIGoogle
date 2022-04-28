package androidx.appcompat.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import java.util.ArrayList;
import java.util.Objects;

public final class SupportActionModeWrapper extends ActionMode {
    public final Context mContext;
    public final ActionMode mWrappedObject;

    public static class CallbackWrapper implements ActionMode.Callback {
        public final ArrayList<SupportActionModeWrapper> mActionModes = new ArrayList<>();
        public final Context mContext;
        public final SimpleArrayMap<Menu, Menu> mMenus = new SimpleArrayMap<>();
        public final ActionMode.Callback mWrappedCallback;

        public final SupportActionModeWrapper getActionModeWrapper(ActionMode actionMode) {
            int size = this.mActionModes.size();
            for (int i = 0; i < size; i++) {
                SupportActionModeWrapper supportActionModeWrapper = this.mActionModes.get(i);
                if (supportActionModeWrapper != null && supportActionModeWrapper.mWrappedObject == actionMode) {
                    return supportActionModeWrapper;
                }
            }
            SupportActionModeWrapper supportActionModeWrapper2 = new SupportActionModeWrapper(this.mContext, actionMode);
            this.mActionModes.add(supportActionModeWrapper2);
            return supportActionModeWrapper2;
        }

        public final Menu getMenuWrapper(MenuBuilder menuBuilder) {
            SimpleArrayMap<Menu, Menu> simpleArrayMap = this.mMenus;
            Objects.requireNonNull(simpleArrayMap);
            Menu orDefault = simpleArrayMap.getOrDefault(menuBuilder, null);
            if (orDefault != null) {
                return orDefault;
            }
            MenuWrapperICS menuWrapperICS = new MenuWrapperICS(this.mContext, menuBuilder);
            this.mMenus.put(menuBuilder, menuWrapperICS);
            return menuWrapperICS;
        }

        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrappedCallback.onActionItemClicked(getActionModeWrapper(actionMode), new MenuItemWrapperICS(this.mContext, (SupportMenuItem) menuItem));
        }

        public final boolean onCreateActionMode(ActionMode actionMode, MenuBuilder menuBuilder) {
            return this.mWrappedCallback.onCreateActionMode(getActionModeWrapper(actionMode), getMenuWrapper(menuBuilder));
        }

        public final void onDestroyActionMode(ActionMode actionMode) {
            this.mWrappedCallback.onDestroyActionMode(getActionModeWrapper(actionMode));
        }

        public final boolean onPrepareActionMode(ActionMode actionMode, MenuBuilder menuBuilder) {
            return this.mWrappedCallback.onPrepareActionMode(getActionModeWrapper(actionMode), getMenuWrapper(menuBuilder));
        }

        public CallbackWrapper(Context context, ActionMode.Callback callback) {
            this.mContext = context;
            this.mWrappedCallback = callback;
        }
    }

    public final void setSubtitle(CharSequence charSequence) {
        this.mWrappedObject.setSubtitle(charSequence);
    }

    public final void setTitle(CharSequence charSequence) {
        this.mWrappedObject.setTitle(charSequence);
    }

    public final void finish() {
        this.mWrappedObject.finish();
    }

    public final View getCustomView() {
        return this.mWrappedObject.getCustomView();
    }

    public final Menu getMenu() {
        return new MenuWrapperICS(this.mContext, this.mWrappedObject.getMenu());
    }

    public final MenuInflater getMenuInflater() {
        return this.mWrappedObject.getMenuInflater();
    }

    public final CharSequence getSubtitle() {
        return this.mWrappedObject.getSubtitle();
    }

    public final Object getTag() {
        ActionMode actionMode = this.mWrappedObject;
        Objects.requireNonNull(actionMode);
        return actionMode.mTag;
    }

    public final CharSequence getTitle() {
        return this.mWrappedObject.getTitle();
    }

    public final boolean getTitleOptionalHint() {
        ActionMode actionMode = this.mWrappedObject;
        Objects.requireNonNull(actionMode);
        return actionMode.mTitleOptionalHint;
    }

    public final void invalidate() {
        this.mWrappedObject.invalidate();
    }

    public final boolean isTitleOptional() {
        return this.mWrappedObject.isTitleOptional();
    }

    public final void setCustomView(View view) {
        this.mWrappedObject.setCustomView(view);
    }

    public final void setSubtitle(int i) {
        this.mWrappedObject.setSubtitle(i);
    }

    public final void setTag(Object obj) {
        ActionMode actionMode = this.mWrappedObject;
        Objects.requireNonNull(actionMode);
        actionMode.mTag = obj;
    }

    public final void setTitle(int i) {
        this.mWrappedObject.setTitle(i);
    }

    public final void setTitleOptionalHint(boolean z) {
        this.mWrappedObject.setTitleOptionalHint(z);
    }

    public SupportActionModeWrapper(Context context, ActionMode actionMode) {
        this.mContext = context;
        this.mWrappedObject = actionMode;
    }
}
