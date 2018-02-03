package com.yarolegovich.lovelydialog;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;

import static android.view.View.*;

/**
 * Created by yarolegovich on 16.04.2016.
 * If null is passed instead on click listener - dialog will be just closed on click.
 */
@SuppressWarnings("WeakerAccess")
public class LovelyStandardDialog extends AbsLovelyDialog<LovelyStandardDialog> {

    public static final int POSITIVE_BUTTON = R.id.ld_btn_yes;
    public static final int NEGATIVE_BUTTON = R.id.ld_btn_no;
    public static final int NEUTRAL_BUTTON = R.id.ld_btn_neutral;

    private Button positiveButton;
    private Button negativeButton;
    private Button neutralButton;

    public LovelyStandardDialog(Context context) {
        super(context);
    }

    public LovelyStandardDialog(Context context, int theme) {
        super(context, theme);
    }

    public LovelyStandardDialog(Context context, ButtonLayout buttonLayout) {
        super(context, 0, buttonLayout.layoutRes);
    }

    public LovelyStandardDialog(Context context, int theme, ButtonLayout buttonLayout) {
        super(context, theme, buttonLayout.layoutRes);
    }


    {
        positiveButton = findView(R.id.ld_btn_yes);
        negativeButton = findView(R.id.ld_btn_no);
        neutralButton = findView(R.id.ld_btn_neutral);
    }

    public LovelyStandardDialog setPositiveButton(@StringRes int text, OnClickListener listener) {
        return setPositiveButton(string(text), listener);
    }

    public LovelyStandardDialog setPositiveButton(String text, @Nullable OnClickListener listener) {
        positiveButton.setVisibility(VISIBLE);
        positiveButton.setText(text);
        positiveButton.setOnClickListener(new ClickListenerDecorator(listener, true));
        return this;
    }

    public LovelyStandardDialog setNegativeButtonText(@StringRes int text) {
        return setNegativeButton(string(text), null);
    }

    public LovelyStandardDialog setNegativeButtonText(String text) {
        return setNegativeButton(text, null);
    }

    public LovelyStandardDialog setNegativeButton(@StringRes int text, OnClickListener listener) {
        return setNegativeButton(string(text), listener);
    }

    public LovelyStandardDialog setNegativeButton(String text, @Nullable OnClickListener listener) {
        negativeButton.setVisibility(VISIBLE);
        negativeButton.setText(text);
        negativeButton.setOnClickListener(new ClickListenerDecorator(listener, true));
        return this;
    }

    public LovelyStandardDialog setNeutralButtonText(@StringRes int text) {
        return setNeutralButton(string(text), null);
    }

    public LovelyStandardDialog setNeutralButtonText(String text) {
        return setNeutralButton(text, null);
    }

    public LovelyStandardDialog setNeutralButton(@StringRes int text, @Nullable OnClickListener listener) {
        return setNeutralButton(string(text), listener);
    }

    public LovelyStandardDialog setNeutralButton(String text, @Nullable OnClickListener listener) {
        neutralButton.setVisibility(VISIBLE);
        neutralButton.setText(text);
        neutralButton.setOnClickListener(new ClickListenerDecorator(listener, true));
        return this;
    }

    public LovelyStandardDialog setButtonsColor(@ColorInt int color) {
        positiveButton.setTextColor(color);
        negativeButton.setTextColor(color);
        neutralButton.setTextColor(color);
        return this;
    }

    public LovelyStandardDialog setButtonsColorRes(@ColorRes int colorRes) {
        return setButtonsColor(color(colorRes));
    }

    public LovelyStandardDialog setOnButtonClickListener(View.OnClickListener listener) {
        return setOnButtonClickListener(true, listener);
    }

    public LovelyStandardDialog setOnButtonClickListener(boolean closeOnClick, View.OnClickListener listener) {
        View.OnClickListener clickHandler = new ClickListenerDecorator(listener, closeOnClick);
        positiveButton.setOnClickListener(clickHandler);
        neutralButton.setOnClickListener(clickHandler);
        negativeButton.setOnClickListener(clickHandler);
        return this;
    }

    public LovelyStandardDialog setPositiveButtonText(@StringRes int text) {
        return setPositiveButton(string(text), null);
    }

    public LovelyStandardDialog setPositiveButtonText(String text) {
        return setPositiveButton(text, null);
    }

    public LovelyStandardDialog setPositiveButtonColor(@ColorInt int color) {
        positiveButton.setTextColor(color);
        return this;
    }

    public LovelyStandardDialog setNegativeButtonColor(@ColorInt int color) {
        negativeButton.setTextColor(color);
        return this;
    }

    public LovelyStandardDialog setNeutralButtonColor(@ColorInt int color) {
        neutralButton.setTextColor(color);
        return this;
    }

    public LovelyStandardDialog setPositiveButtonColorRes(@ColorRes int colorRes) {
        return setPositiveButtonColor(color(colorRes));
    }

    public LovelyStandardDialog setNegativeButtonColorRes(@ColorRes int colorRes) {
        return setNegativeButtonColor(color(colorRes));
    }

    public LovelyStandardDialog setNeutralButtonColorRes(@ColorRes int colorRes) {
        return setNeutralButtonColor(color(colorRes));
    }

    @Override
    protected int getLayout() {
        return ButtonLayout.HORIZONTAL.layoutRes;
    }

    public enum ButtonLayout {
        HORIZONTAL(R.layout.dialog_standard),
        VERTICAL(R.layout.dialog_standard_vertical);
        final @LayoutRes int layoutRes;
        ButtonLayout(@LayoutRes int layoutRes) {
            this.layoutRes = layoutRes;
        }
    }
}
