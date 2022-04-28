package com.android.systemui.wallet.p011ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import androidx.cardview.widget.RoundRectDrawable;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* renamed from: com.android.systemui.wallet.ui.WalletCardView */
public class WalletCardView extends CardView {
    public final Paint mBorderPaint;

    public WalletCardView(Context context) {
        this(context, (AttributeSet) null);
    }

    public WalletCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mBorderPaint = paint;
        paint.setColor(context.getColor(C1777R.color.wallet_card_border));
        paint.setStrokeWidth(context.getResources().getDimension(C1777R.dimen.wallet_card_border_width));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
        CardView.C00931 r0 = this.mCardViewDelegate;
        Objects.requireNonNull(r0);
        RoundRectDrawable roundRectDrawable = (RoundRectDrawable) r0.mCardBackground;
        Objects.requireNonNull(roundRectDrawable);
        float f = roundRectDrawable.mRadius;
        canvas.drawRoundRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), f, f, this.mBorderPaint);
    }
}
