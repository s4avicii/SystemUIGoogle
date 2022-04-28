package com.android.systemui;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import androidx.preference.R$id;
import com.android.systemui.globalactions.C0828x58d1e4b3;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

public abstract class MultiListLayout extends LinearLayout {
    public MultiListAdapter mAdapter;
    public int mRotation;
    public RotationListener mRotationListener;

    public static abstract class MultiListAdapter extends BaseAdapter {
    }

    public interface RotationListener {
    }

    public abstract ViewGroup getListView();

    public abstract ViewGroup getSeparatedView();

    public MultiListLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRotation = R$id.getRotation(context);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int rotation = R$id.getRotation(this.mContext);
        if (rotation != this.mRotation) {
            RotationListener rotationListener = this.mRotationListener;
            if (rotationListener != null) {
                ((C0828x58d1e4b3) rotationListener).onRotate();
            }
            this.mRotation = rotation;
        }
    }

    public void onUpdateList() {
        removeAllItems();
        MultiListAdapter multiListAdapter = this.mAdapter;
        Objects.requireNonNull(multiListAdapter);
        boolean z = true;
        int i = 0;
        if (((GlobalActionsDialogLite.MyAdapter) multiListAdapter).countItems(true) <= 0) {
            z = false;
        }
        ViewGroup separatedView = getSeparatedView();
        if (separatedView != null) {
            if (!z) {
                i = 8;
            }
            separatedView.setVisibility(i);
        }
    }

    public void removeAllItems() {
        removeAllListViews();
        ViewGroup separatedView = getSeparatedView();
        if (separatedView != null) {
            separatedView.removeAllViews();
        }
    }

    public void removeAllListViews() {
        ViewGroup listView = getListView();
        if (listView != null) {
            listView.removeAllViews();
        }
    }
}
