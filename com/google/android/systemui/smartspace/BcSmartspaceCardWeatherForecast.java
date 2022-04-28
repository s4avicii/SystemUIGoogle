package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import com.android.p012wm.shell.C1777R;
import java.util.Locale;

public class BcSmartspaceCardWeatherForecast extends BcSmartspaceCardSecondary {
    public BcSmartspaceCardWeatherForecast(Context context) {
        super(context);
    }

    public BcSmartspaceCardWeatherForecast(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void hideIncompleteColumns(int i) {
        int i2;
        int i3 = 1;
        if (getChildCount() < 4) {
            Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing %d columns to update.", new Object[]{Integer.valueOf(4 - getChildCount())}));
            return;
        }
        int i4 = 3 - i;
        for (int i5 = 0; i5 < 4; i5++) {
            View childAt = getChildAt(i5);
            if (i5 <= i4) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            childAt.setVisibility(i2);
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((ConstraintLayout) getChildAt(0)).getLayoutParams();
        if (i != 0) {
            i3 = 0;
        }
        layoutParams.horizontalChainStyle = i3;
    }

    public final void onFinishInflate() {
        ConstraintLayout constraintLayout;
        ConstraintLayout constraintLayout2;
        super.onFinishInflate();
        ConstraintLayout[] constraintLayoutArr = new ConstraintLayout[4];
        for (int i = 0; i < 4; i++) {
            ConstraintLayout constraintLayout3 = (ConstraintLayout) View.inflate(getContext(), C1777R.layout.smartspace_card_weather_forecast_column, (ViewGroup) null);
            constraintLayout3.setId(View.generateViewId());
            constraintLayoutArr[i] = constraintLayout3;
        }
        for (int i2 = 0; i2 < 4; i2++) {
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, 0);
            ConstraintLayout constraintLayout4 = constraintLayoutArr[i2];
            if (i2 > 0) {
                constraintLayout = constraintLayoutArr[i2 - 1];
            } else {
                constraintLayout = null;
            }
            if (i2 < 3) {
                constraintLayout2 = constraintLayoutArr[i2 + 1];
            } else {
                constraintLayout2 = null;
            }
            if (i2 == 0) {
                layoutParams.startToStart = 0;
                layoutParams.horizontalChainStyle = 1;
            } else {
                layoutParams.startToEnd = constraintLayout.getId();
            }
            if (i2 == 3) {
                layoutParams.endToEnd = 0;
            } else {
                layoutParams.endToStart = constraintLayout2.getId();
            }
            layoutParams.topToTop = 0;
            layoutParams.bottomToBottom = 0;
            addView(constraintLayout4, layoutParams);
        }
    }

    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, CardPagerAdapter$$ExternalSyntheticLambda0 cardPagerAdapter$$ExternalSyntheticLambda0, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        Bundle bundle;
        boolean z;
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        if (baseAction == null) {
            bundle = null;
        } else {
            bundle = baseAction.getExtras();
        }
        if (bundle == null) {
            return false;
        }
        if (bundle.containsKey("temperatureValues")) {
            String[] stringArray = bundle.getStringArray("temperatureValues");
            if (stringArray == null) {
                Log.w("BcSmartspaceCardWeatherForecast", "Temperature values array is null.");
            } else if (getChildCount() < 4) {
                Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing %d temperature value view(s) to update.", new Object[]{Integer.valueOf(4 - getChildCount())}));
            } else {
                if (stringArray.length < 4) {
                    Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing %d temperature value(s). Hiding incomplete columns.", new Object[]{Integer.valueOf(4 - stringArray.length)}));
                    hideIncompleteColumns(4 - stringArray.length);
                }
                int min = Math.min(4, stringArray.length);
                int i = 0;
                while (true) {
                    if (i >= min) {
                        break;
                    }
                    TextView textView = (TextView) getChildAt(i).findViewById(C1777R.C1779id.temperature_value);
                    if (textView == null) {
                        Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing temperature value view to update at column: %d.", new Object[]{Integer.valueOf(i + 1)}));
                        break;
                    }
                    textView.setText(stringArray[i]);
                    i++;
                }
            }
            z = true;
        } else {
            z = false;
        }
        if (bundle.containsKey("weatherIcons")) {
            Bitmap[] bitmapArr = (Bitmap[]) bundle.get("weatherIcons");
            if (bitmapArr == null) {
                Log.w("BcSmartspaceCardWeatherForecast", "Weather icons array is null.");
            } else if (getChildCount() < 4) {
                Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing %d weather icon view(s) to update.", new Object[]{Integer.valueOf(4 - getChildCount())}));
            } else {
                if (bitmapArr.length < 4) {
                    Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing %d weather icon(s). Hiding incomplete columns.", new Object[]{Integer.valueOf(4 - bitmapArr.length)}));
                    hideIncompleteColumns(4 - bitmapArr.length);
                }
                int min2 = Math.min(4, bitmapArr.length);
                int i2 = 0;
                while (true) {
                    if (i2 >= min2) {
                        break;
                    }
                    ImageView imageView = (ImageView) getChildAt(i2).findViewById(C1777R.C1779id.weather_icon);
                    if (imageView == null) {
                        Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing weather logo view to update at column: %d.", new Object[]{Integer.valueOf(i2 + 1)}));
                        break;
                    }
                    imageView.setImageBitmap(bitmapArr[i2]);
                    i2++;
                }
            }
            z = true;
        }
        if (!bundle.containsKey("timestamps")) {
            return z;
        }
        String[] stringArray2 = bundle.getStringArray("timestamps");
        if (stringArray2 == null) {
            Log.w("BcSmartspaceCardWeatherForecast", "Timestamps array is null.");
        } else if (getChildCount() < 4) {
            Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing %d timestamp view(s) to update.", new Object[]{Integer.valueOf(4 - getChildCount())}));
        } else {
            if (stringArray2.length < 4) {
                Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing %d timestamp(s). Hiding incomplete columns.", new Object[]{Integer.valueOf(4 - stringArray2.length)}));
                hideIncompleteColumns(4 - stringArray2.length);
            }
            int min3 = Math.min(4, stringArray2.length);
            int i3 = 0;
            while (true) {
                if (i3 >= min3) {
                    break;
                }
                TextView textView2 = (TextView) getChildAt(i3).findViewById(C1777R.C1779id.timestamp);
                if (textView2 == null) {
                    Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, "Missing timestamp view to update at column: %d.", new Object[]{Integer.valueOf(i3 + 1)}));
                    break;
                }
                textView2.setText(stringArray2[i3]);
                i3++;
            }
        }
        return true;
    }
}
