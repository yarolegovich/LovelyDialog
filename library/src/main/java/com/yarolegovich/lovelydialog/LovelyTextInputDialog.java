package com.yarolegovich.lovelydialog;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class LovelyTextInputDialog extends AbsLovelyDialog<LovelyTextInputDialog> {

    private EditText inputField;
    private TextView errorMessage;

    private TextFilter filter;

    public LovelyTextInputDialog(Context context) {
        super(context);
        inputField = findView(R.id.ld_text_input);
        errorMessage = findView(R.id.ld_error_message);
        inputField.addTextChangedListener(new HideErrorOnTextChanged());
    }

    public LovelyTextInputDialog setConfirmButton(@StringRes int text, OnTextInputConfirmedListener listener) {
        return setConfirmButton(getString(text), listener);
    }

    public LovelyTextInputDialog setConfirmButton(String text, OnTextInputConfirmedListener listener) {
        Button confirmButton = findView(R.id.od_btn_confirm);
        confirmButton.setText(text);
        confirmButton.setOnClickListener(new TextInputListener(listener));
        return this;
    }

    public LovelyTextInputDialog setInputFilter(TextFilter filter, @StringRes int errorMessage) {
        return setInputFilter(filter, getString(errorMessage));
    }

    public LovelyTextInputDialog setInputFilter(TextFilter filter, String errorMessage) {
        this.filter = filter;
        this.errorMessage.setText(errorMessage);
        return this;
    }

    public LovelyTextInputDialog addTextWatcher(TextWatcher textWatcher) {
        inputField.addTextChangedListener(textWatcher);
        return this;
    }

    private void setError() {
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        errorMessage.setVisibility(View.GONE);
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_text_input;
    }

    private class TextInputListener implements View.OnClickListener {

        private OnTextInputConfirmedListener wrapped;

        private TextInputListener(OnTextInputConfirmedListener wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void onClick(View v) {
            String text = inputField.getText().toString();

            if (filter != null) {
                boolean isWrongInput = !filter.check(text);
                if (isWrongInput) {
                    setError();
                    return;
                }
            }

            if (wrapped != null) {
                wrapped.onTextInputConfirmed(text);
            }

            dismiss();
        }
    }

    private class HideErrorOnTextChanged implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            hideError();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public interface OnTextInputConfirmedListener {
        void onTextInputConfirmed(String text);
    }

    public interface TextFilter {
        boolean check(String text);
    }
}
