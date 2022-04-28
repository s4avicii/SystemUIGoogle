package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.graphics.ColorUtils;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public final class AppCompatDrawableManager {
    public static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    public static AppCompatDrawableManager INSTANCE;
    public ResourceManagerInternal mResourceManager;

    public final synchronized Drawable getDrawable(Context context, int i) {
        return this.mResourceManager.getDrawable(context, i);
    }

    public static synchronized AppCompatDrawableManager get() {
        AppCompatDrawableManager appCompatDrawableManager;
        synchronized (AppCompatDrawableManager.class) {
            if (INSTANCE == null) {
                preload();
            }
            appCompatDrawableManager = INSTANCE;
        }
        return appCompatDrawableManager;
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        synchronized (AppCompatDrawableManager.class) {
            porterDuffColorFilter = ResourceManagerInternal.getPorterDuffColorFilter(i, mode);
        }
        return porterDuffColorFilter;
    }

    public static synchronized void preload() {
        synchronized (AppCompatDrawableManager.class) {
            if (INSTANCE == null) {
                AppCompatDrawableManager appCompatDrawableManager = new AppCompatDrawableManager();
                INSTANCE = appCompatDrawableManager;
                appCompatDrawableManager.mResourceManager = ResourceManagerInternal.get();
                ResourceManagerInternal resourceManagerInternal = INSTANCE.mResourceManager;
                C00621 r2 = new ResourceManagerInternal.ResourceManagerHooks() {
                    public final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY = {C1777R.C1778drawable.abc_popup_background_mtrl_mult, C1777R.C1778drawable.abc_cab_background_internal_bg, C1777R.C1778drawable.abc_menu_hardkey_panel_mtrl_mult};
                    public final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED = {C1777R.C1778drawable.abc_textfield_activated_mtrl_alpha, C1777R.C1778drawable.abc_textfield_search_activated_mtrl_alpha, C1777R.C1778drawable.abc_cab_background_top_mtrl_alpha, C1777R.C1778drawable.abc_text_cursor_material, C1777R.C1778drawable.abc_text_select_handle_left_mtrl, C1777R.C1778drawable.abc_text_select_handle_middle_mtrl, C1777R.C1778drawable.abc_text_select_handle_right_mtrl};
                    public final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL = {C1777R.C1778drawable.abc_textfield_search_default_mtrl_alpha, C1777R.C1778drawable.abc_textfield_default_mtrl_alpha, C1777R.C1778drawable.abc_ab_share_pack_mtrl_alpha};
                    public final int[] TINT_CHECKABLE_BUTTON_LIST = {C1777R.C1778drawable.abc_btn_check_material, C1777R.C1778drawable.abc_btn_radio_material, C1777R.C1778drawable.abc_btn_check_material_anim, C1777R.C1778drawable.abc_btn_radio_material_anim};
                    public final int[] TINT_COLOR_CONTROL_NORMAL = {C1777R.C1778drawable.abc_ic_commit_search_api_mtrl_alpha, C1777R.C1778drawable.abc_seekbar_tick_mark_material, C1777R.C1778drawable.abc_ic_menu_share_mtrl_alpha, C1777R.C1778drawable.abc_ic_menu_copy_mtrl_am_alpha, C1777R.C1778drawable.abc_ic_menu_cut_mtrl_alpha, C1777R.C1778drawable.abc_ic_menu_selectall_mtrl_alpha, C1777R.C1778drawable.abc_ic_menu_paste_mtrl_am_alpha};
                    public final int[] TINT_COLOR_CONTROL_STATE_LIST = {C1777R.C1778drawable.abc_tab_indicator_material, C1777R.C1778drawable.abc_textfield_search_material};

                    public static boolean arrayContains(int[] iArr, int i) {
                        for (int i2 : iArr) {
                            if (i2 == i) {
                                return true;
                            }
                        }
                        return false;
                    }

                    public static ColorStateList createButtonColorStateList(Context context, int i) {
                        int themeAttrColor = ThemeUtils.getThemeAttrColor(context, C1777R.attr.colorControlHighlight);
                        int disabledThemeAttrColor = ThemeUtils.getDisabledThemeAttrColor(context, C1777R.attr.colorButtonNormal);
                        return new ColorStateList(new int[][]{ThemeUtils.DISABLED_STATE_SET, ThemeUtils.PRESSED_STATE_SET, ThemeUtils.FOCUSED_STATE_SET, ThemeUtils.EMPTY_STATE_SET}, new int[]{disabledThemeAttrColor, ColorUtils.compositeColors(themeAttrColor, i), ColorUtils.compositeColors(themeAttrColor, i), i});
                    }

                    public static LayerDrawable getRatingBarLayerDrawable(ResourceManagerInternal resourceManagerInternal, Context context, int i) {
                        BitmapDrawable bitmapDrawable;
                        BitmapDrawable bitmapDrawable2;
                        BitmapDrawable bitmapDrawable3;
                        int dimensionPixelSize = context.getResources().getDimensionPixelSize(i);
                        Drawable drawable = resourceManagerInternal.getDrawable(context, C1777R.C1778drawable.abc_star_black_48dp);
                        Drawable drawable2 = resourceManagerInternal.getDrawable(context, C1777R.C1778drawable.abc_star_half_black_48dp);
                        if ((drawable instanceof BitmapDrawable) && drawable.getIntrinsicWidth() == dimensionPixelSize && drawable.getIntrinsicHeight() == dimensionPixelSize) {
                            bitmapDrawable2 = (BitmapDrawable) drawable;
                            bitmapDrawable = new BitmapDrawable(bitmapDrawable2.getBitmap());
                        } else {
                            Bitmap createBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(createBitmap);
                            drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                            drawable.draw(canvas);
                            bitmapDrawable2 = new BitmapDrawable(createBitmap);
                            bitmapDrawable = new BitmapDrawable(createBitmap);
                        }
                        bitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
                        if ((drawable2 instanceof BitmapDrawable) && drawable2.getIntrinsicWidth() == dimensionPixelSize && drawable2.getIntrinsicHeight() == dimensionPixelSize) {
                            bitmapDrawable3 = (BitmapDrawable) drawable2;
                        } else {
                            Bitmap createBitmap2 = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
                            Canvas canvas2 = new Canvas(createBitmap2);
                            drawable2.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                            drawable2.draw(canvas2);
                            bitmapDrawable3 = new BitmapDrawable(createBitmap2);
                        }
                        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bitmapDrawable2, bitmapDrawable3, bitmapDrawable});
                        layerDrawable.setId(0, 16908288);
                        layerDrawable.setId(1, 16908303);
                        layerDrawable.setId(2, 16908301);
                        return layerDrawable;
                    }

                    public static void setPorterDuffColorFilter(Drawable drawable, int i, PorterDuff.Mode mode) {
                        if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                            drawable = drawable.mutate();
                        }
                        if (mode == null) {
                            mode = AppCompatDrawableManager.DEFAULT_MODE;
                        }
                        drawable.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(i, mode));
                    }

                    public final ColorStateList getTintListForDrawableRes(Context context, int i) {
                        if (i == C1777R.C1778drawable.abc_edit_text_material) {
                            return AppCompatResources.getColorStateList(context, C1777R.color.abc_tint_edittext);
                        }
                        if (i == C1777R.C1778drawable.abc_switch_track_mtrl_alpha) {
                            return AppCompatResources.getColorStateList(context, C1777R.color.abc_tint_switch_track);
                        }
                        if (i == C1777R.C1778drawable.abc_switch_thumb_material) {
                            int[][] iArr = new int[3][];
                            int[] iArr2 = new int[3];
                            ColorStateList themeAttrColorStateList = ThemeUtils.getThemeAttrColorStateList(context, C1777R.attr.colorSwitchThumbNormal);
                            if (themeAttrColorStateList == null || !themeAttrColorStateList.isStateful()) {
                                iArr[0] = ThemeUtils.DISABLED_STATE_SET;
                                iArr2[0] = ThemeUtils.getDisabledThemeAttrColor(context, C1777R.attr.colorSwitchThumbNormal);
                                iArr[1] = ThemeUtils.CHECKED_STATE_SET;
                                iArr2[1] = ThemeUtils.getThemeAttrColor(context, C1777R.attr.colorControlActivated);
                                iArr[2] = ThemeUtils.EMPTY_STATE_SET;
                                iArr2[2] = ThemeUtils.getThemeAttrColor(context, C1777R.attr.colorSwitchThumbNormal);
                            } else {
                                iArr[0] = ThemeUtils.DISABLED_STATE_SET;
                                iArr2[0] = themeAttrColorStateList.getColorForState(iArr[0], 0);
                                iArr[1] = ThemeUtils.CHECKED_STATE_SET;
                                iArr2[1] = ThemeUtils.getThemeAttrColor(context, C1777R.attr.colorControlActivated);
                                iArr[2] = ThemeUtils.EMPTY_STATE_SET;
                                iArr2[2] = themeAttrColorStateList.getDefaultColor();
                            }
                            return new ColorStateList(iArr, iArr2);
                        } else if (i == C1777R.C1778drawable.abc_btn_default_mtrl_shape) {
                            return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, C1777R.attr.colorButtonNormal));
                        } else {
                            if (i == C1777R.C1778drawable.abc_btn_borderless_material) {
                                return createButtonColorStateList(context, 0);
                            }
                            if (i == C1777R.C1778drawable.abc_btn_colored_material) {
                                return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, C1777R.attr.colorAccent));
                            }
                            if (i == C1777R.C1778drawable.abc_spinner_mtrl_am_alpha || i == C1777R.C1778drawable.abc_spinner_textfield_background_material) {
                                return AppCompatResources.getColorStateList(context, C1777R.color.abc_tint_spinner);
                            }
                            if (arrayContains(this.TINT_COLOR_CONTROL_NORMAL, i)) {
                                return ThemeUtils.getThemeAttrColorStateList(context, C1777R.attr.colorControlNormal);
                            }
                            if (arrayContains(this.TINT_COLOR_CONTROL_STATE_LIST, i)) {
                                return AppCompatResources.getColorStateList(context, C1777R.color.abc_tint_default);
                            }
                            if (arrayContains(this.TINT_CHECKABLE_BUTTON_LIST, i)) {
                                return AppCompatResources.getColorStateList(context, C1777R.color.abc_tint_btn_checkable);
                            }
                            if (i == C1777R.C1778drawable.abc_seekbar_thumb_material) {
                                return AppCompatResources.getColorStateList(context, C1777R.color.abc_tint_seek_thumb);
                            }
                            return null;
                        }
                    }
                };
                Objects.requireNonNull(resourceManagerInternal);
                synchronized (resourceManagerInternal) {
                    resourceManagerInternal.mHooks = r2;
                }
            }
        }
    }

    public static void tintDrawable(Drawable drawable, TintInfo tintInfo, int[] iArr) {
        ColorStateList colorStateList;
        PorterDuff.Mode mode;
        PorterDuff.Mode mode2 = ResourceManagerInternal.DEFAULT_MODE;
        if (!DrawableUtils.canSafelyMutateDrawable(drawable) || drawable.mutate() == drawable) {
            boolean z = tintInfo.mHasTintList;
            if (z || tintInfo.mHasTintMode) {
                PorterDuffColorFilter porterDuffColorFilter = null;
                if (z) {
                    colorStateList = tintInfo.mTintList;
                } else {
                    colorStateList = null;
                }
                if (tintInfo.mHasTintMode) {
                    mode = tintInfo.mTintMode;
                } else {
                    mode = ResourceManagerInternal.DEFAULT_MODE;
                }
                if (!(colorStateList == null || mode == null)) {
                    porterDuffColorFilter = ResourceManagerInternal.getPorterDuffColorFilter(colorStateList.getColorForState(iArr, 0), mode);
                }
                drawable.setColorFilter(porterDuffColorFilter);
                return;
            }
            drawable.clearColorFilter();
            return;
        }
        Log.d("ResourceManagerInternal", "Mutated drawable is not the same instance as the input.");
    }
}
