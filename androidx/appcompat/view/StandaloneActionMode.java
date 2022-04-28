package androidx.appcompat.view;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.core.view.ViewCompat;
import java.lang.ref.WeakReference;
import java.util.Objects;

public final class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
    public ActionMode.Callback mCallback;
    public Context mContext;
    public ActionBarContextView mContextView;
    public WeakReference<View> mCustomView;
    public boolean mFinished;
    public MenuBuilder mMenu;

    public final void setSubtitle(CharSequence charSequence) {
        ActionBarContextView actionBarContextView = this.mContextView;
        Objects.requireNonNull(actionBarContextView);
        actionBarContextView.mSubtitle = charSequence;
        actionBarContextView.initTitle();
    }

    public final void setTitle(CharSequence charSequence) {
        ActionBarContextView actionBarContextView = this.mContextView;
        Objects.requireNonNull(actionBarContextView);
        actionBarContextView.mTitle = charSequence;
        actionBarContextView.initTitle();
        ViewCompat.setAccessibilityPaneTitle(actionBarContextView, charSequence);
    }

    public final void finish() {
        if (!this.mFinished) {
            this.mFinished = true;
            this.mCallback.onDestroyActionMode(this);
        }
    }

    public final View getCustomView() {
        WeakReference<View> weakReference = this.mCustomView;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public final MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.mContextView.getContext());
    }

    public final CharSequence getSubtitle() {
        ActionBarContextView actionBarContextView = this.mContextView;
        Objects.requireNonNull(actionBarContextView);
        return actionBarContextView.mSubtitle;
    }

    public final CharSequence getTitle() {
        ActionBarContextView actionBarContextView = this.mContextView;
        Objects.requireNonNull(actionBarContextView);
        return actionBarContextView.mTitle;
    }

    public final void invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
    }

    public final boolean isTitleOptional() {
        ActionBarContextView actionBarContextView = this.mContextView;
        Objects.requireNonNull(actionBarContextView);
        return actionBarContextView.mTitleOptional;
    }

    public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.mCallback.onActionItemClicked(this, menuItem);
    }

    public final void setCustomView(View view) {
        WeakReference<View> weakReference;
        this.mContextView.setCustomView(view);
        if (view != null) {
            weakReference = new WeakReference<>(view);
        } else {
            weakReference = null;
        }
        this.mCustomView = weakReference;
    }

    public final void setTitleOptionalHint(boolean z) {
        this.mTitleOptionalHint = z;
        ActionBarContextView actionBarContextView = this.mContextView;
        Objects.requireNonNull(actionBarContextView);
        if (z != actionBarContextView.mTitleOptional) {
            actionBarContextView.requestLayout();
        }
        actionBarContextView.mTitleOptional = z;
    }

    public StandaloneActionMode(Context context, ActionBarContextView actionBarContextView, ActionMode.Callback callback) {
        this.mContext = context;
        this.mContextView = actionBarContextView;
        this.mCallback = callback;
        MenuBuilder menuBuilder = new MenuBuilder(actionBarContextView.getContext());
        menuBuilder.mDefaultShowAsAction = 1;
        this.mMenu = menuBuilder;
        menuBuilder.mCallback = this;
    }

    public final void onMenuModeChange(MenuBuilder menuBuilder) {
        invalidate();
        ActionBarContextView actionBarContextView = this.mContextView;
        Objects.requireNonNull(actionBarContextView);
        ActionMenuPresenter actionMenuPresenter = actionBarContextView.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.showOverflowMenu();
        }
    }

    public final void setSubtitle(int i) {
        setSubtitle((CharSequence) this.mContext.getString(i));
    }

    public final void setTitle(int i) {
        setTitle((CharSequence) this.mContext.getString(i));
    }

    public final MenuBuilder getMenu() {
        return this.mMenu;
    }
}
