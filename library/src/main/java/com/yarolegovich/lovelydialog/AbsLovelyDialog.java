package com.yarolegovich.lovelydialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yarolegovich on 16.04.2016.
 */
@SuppressWarnings("unchecked")
public abstract class AbsLovelyDialog<T extends AbsLovelyDialog> {

    private Dialog dialog;
    private View dialogView;

    public AbsLovelyDialog(Context context) {
        dialogView = LayoutInflater.from(context).inflate(getLayout(), null);
        dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();
    }

    @LayoutRes
    protected abstract int getLayout();

    public T setMessage(@StringRes int message) {
        return setMessage(getString(message));
    }

    public T setMessage(String message) {
        TextView messageView = findView(R.id.ld_message);
        messageView.setVisibility(View.VISIBLE);
        messageView.setText(message);
        return (T) this;
    }

    public T setTitle(@StringRes int title) {
        return setTitle(getString(title));
    }

    public T setTitle(String title) {
        TextView titleView = findView(R.id.ld_title);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(title);
        return (T) this;
    }

    public T setIcon(Drawable drawable) {
        findIconImageView().setImageDrawable(drawable);
        return (T) this;
    }

    public T setIcon(Bitmap bitmap) {
        findIconImageView().setImageBitmap(bitmap);
        return (T) this;
    }

    public T setIcon(@DrawableRes int iconRes) {
        findIconImageView().setImageResource(iconRes);
        return (T) this;
    }

    private ImageView findIconImageView() {
        ImageView icon = findView(R.id.ld_icon);
        icon.setVisibility(View.VISIBLE);
        return icon;
    }

    public T setIconTintColor(int color) {
        findIconImageView().setColorFilter(color);
        return (T) this;
    }

    public T setTopColor(int color) {
        findView(R.id.ld_color_area).setBackgroundColor(color);
        return (T) this;
    }

    public T setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return (T) this;
    }

    public Dialog create() {
        return dialog;
    }

    public Dialog show() {
        dialog.show();
        return dialog;
    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public void dismiss() {
        dialog.dismiss();
    }

    protected String getString(@StringRes int res) {
        return dialogView.getContext().getString(res);
    }

    protected Context getContext() {
        return dialogView.getContext();
    }

    protected <ViewClass extends View>ViewClass findView(int id) {
        ViewClass view = (ViewClass) dialogView.findViewById(id);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    protected class CloseOnClickDecorator implements View.OnClickListener {

        private View.OnClickListener clickListener;

        protected CloseOnClickDecorator(View.OnClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v);
            }
            dismiss();
        }
    }
}
