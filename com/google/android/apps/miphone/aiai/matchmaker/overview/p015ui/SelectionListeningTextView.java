package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint({"AppCompatCustomView"})
/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.SelectionListeningTextView */
class SelectionListeningTextView extends TextView {
    public SelectionListeningTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        setTextIsSelectable(true);
    }

    public final void onSelectionChanged(int i, int i2) {
        super.onSelectionChanged(i, i2);
    }
}
