package com.google.android.setupdesign.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;

public class NavigationBar extends LinearLayout implements View.OnClickListener {
    public NavigationBar(Context context) {
        super(getThemedContext(context));
        init();
    }

    public static ContextThemeWrapper getThemedContext(Context context) {
        int i;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{C1777R.attr.sudNavBarTheme, 16842800, 16842801});
        boolean z = false;
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId == 0) {
            float[] fArr = new float[3];
            float[] fArr2 = new float[3];
            Color.colorToHSV(obtainStyledAttributes.getColor(1, 0), fArr);
            Color.colorToHSV(obtainStyledAttributes.getColor(2, 0), fArr2);
            if (fArr[2] > fArr2[2]) {
                z = true;
            }
            if (z) {
                i = C1777R.style.SudNavBarThemeDark;
            } else {
                i = C1777R.style.SudNavBarThemeLight;
            }
            resourceId = i;
        }
        obtainStyledAttributes.recycle();
        return new ContextThemeWrapper(context, resourceId);
    }

    public final void onClick(View view) {
    }

    public NavigationBar(Context context, AttributeSet attributeSet) {
        super(getThemedContext(context), attributeSet);
        init();
    }

    public final void init() {
        if (!isInEditMode()) {
            View.inflate(getContext(), C1777R.layout.sud_navbar_view, this);
            Button button = (Button) findViewById(C1777R.C1779id.sud_navbar_next);
            Button button2 = (Button) findViewById(C1777R.C1779id.sud_navbar_back);
            Button button3 = (Button) findViewById(C1777R.C1779id.sud_navbar_more);
        }
    }

    @TargetApi(11)
    public NavigationBar(Context context, AttributeSet attributeSet, int i) {
        super(getThemedContext(context), attributeSet, i);
        init();
    }
}
