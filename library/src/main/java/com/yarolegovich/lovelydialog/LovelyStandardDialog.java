package com.yarolegovich.lovelydialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Button;

import static android.view.View.*;

/**
 * Created by yarolegovich on 16.04.2016.
 * If null is passed instead on click listener - dialog will be just closed on click.
 */
public class LovelyStandardDialog extends AbsLovelyDialog<LovelyStandardDialog> {

    private Button positiveButton;
    private Button negativeButton;
    private Button neutralButton;

    public LovelyStandardDialog(Context context) {
        super(context);
        positiveButton = findView(R.id.ld_btn_yes);
        negativeButton = findView(R.id.ld_btn_no);
        neutralButton = findView(R.id.ld_btn_neutral);
    }

    public LovelyStandardDialog setPositiveButton(String text, @Nullable OnClickListener listener) {
        positiveButton.setVisibility(VISIBLE);
        positiveButton.setText(text);
        positiveButton.setOnClickListener(new CloseOnClickDecorator(listener));
        return this;
    }

    public LovelyStandardDialog setNegativeButton(@StringRes int text, OnClickListener listener) {
        return setNegativeButton(getString(text), listener);
    }

    public LovelyStandardDialog setNegativeButton(String text, @Nullable OnClickListener listener) {
        negativeButton.setVisibility(VISIBLE);
        negativeButton.setText(text);
        negativeButton.setOnClickListener(new CloseOnClickDecorator(listener));
        return this;
    }

    public LovelyStandardDialog setNeutralButton(@StringRes int text, @Nullable OnClickListener listener) {
        return setNeutralButton(getString(text), listener);
    }

    public LovelyStandardDialog setNeutralButton(String text, @Nullable OnClickListener listener) {
        neutralButton.setVisibility(VISIBLE);
        neutralButton.setText(text);
        neutralButton.setOnClickListener(new CloseOnClickDecorator(listener));
        return this;
    }

    public LovelyStandardDialog setButtonsColor(int color) {
        positiveButton.setTextColor(color);
        negativeButton.setTextColor(color);
        neutralButton.setTextColor(color);
        return this;
    }

    public LovelyStandardDialog setPositiveButtonColor(int color) {
        positiveButton.setTextColor(color);
        return this;
    }

    public LovelyStandardDialog setNegativeButtonColor(int color) {
        negativeButton.setTextColor(color);
        return this;
    }

    public LovelyStandardDialog setNeutralButtonColor(int color) {
        neutralButton.setTextColor(color);
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_standard;
    }
}
