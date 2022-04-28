package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.R$styleable;

public class MockView extends View {
    public int mDiagonalsColor;
    public boolean mDrawDiagonals = true;
    public boolean mDrawLabel = true;
    public int mMargin;
    public Paint mPaintDiagonals = new Paint();
    public Paint mPaintText = new Paint();
    public Paint mPaintTextBackground = new Paint();
    public String mText = null;
    public int mTextBackgroundColor;
    public Rect mTextBounds = new Rect();
    public int mTextColor;

    public MockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDiagonalsColor = Color.argb(255, 0, 0, 0);
        this.mTextColor = Color.argb(255, 200, 200, 200);
        this.mTextBackgroundColor = Color.argb(255, 50, 50, 50);
        this.mMargin = 4;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MockView);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 1) {
                    this.mText = obtainStyledAttributes.getString(index);
                } else if (index == 4) {
                    this.mDrawDiagonals = obtainStyledAttributes.getBoolean(index, this.mDrawDiagonals);
                } else if (index == 0) {
                    this.mDiagonalsColor = obtainStyledAttributes.getColor(index, this.mDiagonalsColor);
                } else if (index == 2) {
                    this.mTextBackgroundColor = obtainStyledAttributes.getColor(index, this.mTextBackgroundColor);
                } else if (index == 3) {
                    this.mTextColor = obtainStyledAttributes.getColor(index, this.mTextColor);
                } else if (index == 5) {
                    this.mDrawLabel = obtainStyledAttributes.getBoolean(index, this.mDrawLabel);
                }
            }
        }
        if (this.mText == null) {
            try {
                this.mText = context.getResources().getResourceEntryName(getId());
            } catch (Exception unused) {
            }
        }
        this.mPaintDiagonals.setColor(this.mDiagonalsColor);
        this.mPaintDiagonals.setAntiAlias(true);
        this.mPaintText.setColor(this.mTextColor);
        this.mPaintText.setAntiAlias(true);
        this.mPaintTextBackground.setColor(this.mTextBackgroundColor);
        this.mMargin = Math.round((getResources().getDisplayMetrics().xdpi / 160.0f) * ((float) this.mMargin));
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (this.mDrawDiagonals) {
            width--;
            height--;
            float f = (float) width;
            float f2 = (float) height;
            canvas.drawLine(0.0f, 0.0f, f, f2, this.mPaintDiagonals);
            Canvas canvas2 = canvas;
            float f3 = f;
            canvas2.drawLine(0.0f, f2, f3, 0.0f, this.mPaintDiagonals);
            canvas2.drawLine(0.0f, 0.0f, f3, 0.0f, this.mPaintDiagonals);
            float f4 = f;
            float f5 = f2;
            canvas2.drawLine(f4, 0.0f, f3, f5, this.mPaintDiagonals);
            float f6 = f2;
            canvas2.drawLine(f4, f6, 0.0f, f5, this.mPaintDiagonals);
            canvas2.drawLine(0.0f, f6, 0.0f, 0.0f, this.mPaintDiagonals);
        }
        String str = this.mText;
        if (str != null && this.mDrawLabel) {
            this.mPaintText.getTextBounds(str, 0, str.length(), this.mTextBounds);
            float width2 = ((float) (width - this.mTextBounds.width())) / 2.0f;
            float height2 = (((float) (height - this.mTextBounds.height())) / 2.0f) + ((float) this.mTextBounds.height());
            this.mTextBounds.offset((int) width2, (int) height2);
            Rect rect = this.mTextBounds;
            int i = rect.left;
            int i2 = this.mMargin;
            rect.set(i - i2, rect.top - i2, rect.right + i2, rect.bottom + i2);
            canvas.drawRect(this.mTextBounds, this.mPaintTextBackground);
            canvas.drawText(this.mText, width2, height2, this.mPaintText);
        }
    }
}
