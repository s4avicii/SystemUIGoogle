package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import androidx.preference.R$color;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: ControlsModel.kt */
final /* synthetic */ class ControlInfoWrapper$customIconGetter$1 extends FunctionReferenceImpl implements Function2<ComponentName, String, Icon> {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new ControlInfoWrapper$customIconGetter$1();
    }

    public ControlInfoWrapper$customIconGetter$1() {
        super(2, R$color.class, "nullIconGetter", "nullIconGetter(Landroid/content/ComponentName;Ljava/lang/String;)Landroid/graphics/drawable/Icon;", 1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        ComponentName componentName = (ComponentName) obj;
        String str = (String) obj2;
        return null;
    }
}
