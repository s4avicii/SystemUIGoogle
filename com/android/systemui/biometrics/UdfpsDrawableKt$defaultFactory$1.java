package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.PathParser;
import com.android.p012wm.shell.C1777R;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: UdfpsDrawable.kt */
public final class UdfpsDrawableKt$defaultFactory$1 extends Lambda implements Function1<Context, ShapeDrawable> {
    public static final UdfpsDrawableKt$defaultFactory$1 INSTANCE = new UdfpsDrawableKt$defaultFactory$1();

    public UdfpsDrawableKt$defaultFactory$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(PathParser.createPathFromPathData(((Context) obj).getResources().getString(C1777R.string.config_udfpsIcon)), 72.0f, 72.0f));
        shapeDrawable.mutate();
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        shapeDrawable.getPaint().setStrokeCap(Paint.Cap.ROUND);
        shapeDrawable.getPaint().setStrokeWidth(3.0f);
        return shapeDrawable;
    }
}
