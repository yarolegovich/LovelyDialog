package com.yarolegovich.lovelydialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

/**
 * Created by yarolegovich on 16.04.2016.
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public abstract class AbsLovelyDialog<T extends AbsLovelyDialog> {

    private static final String KEY_SAVED_STATE_TOKEN = "key_saved_state_token";

    private Dialog dialog;
    private View dialogView;

    private ImageView iconView;
    private TextView topTitleView;
    private TextView titleView;
    private TextView messageView;

    public AbsLovelyDialog(Context context) {
        this(context, 0);
    }

    public AbsLovelyDialog(Context context, int theme) {
        this(context, theme, 0);
    }

    public AbsLovelyDialog(Context context, int theme, int layoutRes) {
        if (layoutRes == 0) {
            layoutRes = getLayout();
        }
        if (theme == 0) {
            init(new AlertDialog.Builder(context), layoutRes);
        } else {
            init(new AlertDialog.Builder(context, theme), layoutRes);
        }
    }

    private void init(AlertDialog.Builder dialogBuilder, @LayoutRes int res) {
        dialogView = LayoutInflater.from(dialogBuilder.getContext()).inflate(res, null);
        dialog = dialogBuilder.setView(dialogView).create();

        iconView = findView(R.id.ld_icon);
        titleView = findView(R.id.ld_title);
        messageView = findView(R.id.ld_message);
        topTitleView = findView(R.id.ld_top_title);
    }

    @LayoutRes
    protected abstract int getLayout();

    public T configureView(@NonNull ViewConfigurator<View> viewViewConfigurator) {
        viewViewConfigurator.configureView(dialogView);
        return (T) this;
    }

    public T configureTitleView(@NonNull ViewConfigurator<TextView> viewConfigurator) {
        viewConfigurator.configureView(titleView);
        return (T) this;
    }

    public T configureMessageView(@NonNull ViewConfigurator<TextView> viewConfigurator) {
        viewConfigurator.configureView(messageView);
        return (T) this;
    }

    public T setMessage(@StringRes int message) {
        return setMessage(string(message));
    }

    public T setMessage(CharSequence message) {
        messageView.setVisibility(View.VISIBLE);
        messageView.setText(message);
        return (T) this;
    }

    public T setTitle(@StringRes int title) {
        return setTitle(string(title));
    }

    public T setTopTitle(@StringRes int title) {
        return setTopTitle(string(title));
    }

    public T setTitle(CharSequence title) {
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(title);
        return (T) this;
    }

    public T setTopTitle(CharSequence title) {
        topTitleView.setVisibility(View.VISIBLE);
        topTitleView.setText(title);
        return (T) this;
    }

    public T setTopTitleColor(int color) {
        topTitleView.setTextColor(color);
        return (T) this;
    }

    public T setIcon(Bitmap bitmap) {
        iconView.setVisibility(View.VISIBLE);
        iconView.setImageBitmap(bitmap);
        return (T) this;
    }

    public T setIcon(Drawable drawable) {
        iconView.setVisibility(View.VISIBLE);
        iconView.setImageDrawable(drawable);
        return (T) this;
    }

    public T setIcon(@DrawableRes int iconRes) {
        iconView.setVisibility(View.VISIBLE);
        iconView.setImageResource(iconRes);
        return (T) this;
    }

    public T setIconTintColor(int iconTintColor) {
        iconView.setColorFilter(iconTintColor);
        return (T) this;
    }

    public T setTitleGravity(int gravity) {
        titleView.setGravity(gravity);
        return (T) this;
    }

    public T setMessageGravity(int gravity) {
        messageView.setGravity(gravity);
        return (T) this;
    }

    public T setTopColor(@ColorInt int topColor) {
        findView(R.id.ld_color_area).setBackgroundColor(topColor);
        return (T) this;
    }

    public T setTopColorRes(@ColorRes int topColoRes) {
        return setTopColor(color(topColoRes));
    }

    /*
     * You should call method saveInstanceState on handler object and then use saved info to restore
     * your dialog in onRestoreInstanceState. Static methods wasDialogOnScreen and getDialogId will
     * help you in this.
     */
    public T setInstanceStateHandler(int id, LovelySaveStateHandler handler) {
        handler.handleDialogStateSave(id, this);
        return (T) this;
    }

    public T setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return (T) this;
    }

    public T setSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean hasSavedStateHere =
                    savedInstanceState.keySet().contains(KEY_SAVED_STATE_TOKEN) &&
                            savedInstanceState.getSerializable(KEY_SAVED_STATE_TOKEN) == getClass();
            if (hasSavedStateHere) {
                restoreState(savedInstanceState);
            }
        }
        return (T) this;
    }

    public Dialog show() {
        dialog.show();
        return dialog;
    }

    public Dialog create() {
        return dialog;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_SAVED_STATE_TOKEN, getClass());
    }

    void restoreState(Bundle savedState) {
    }

    boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    protected String string(@StringRes int res) {
        return dialogView.getContext().getString(res);
    }

    protected int color(@ColorRes int colorRes) {
        return ContextCompat.getColor(getContext(), colorRes);
    }

    protected Context getContext() {
        return dialogView.getContext();
    }

    protected <ViewClass extends View> ViewClass findView(int id) {
        return (ViewClass) dialogView.findViewById(id);
    }

    protected class ClickListenerDecorator implements View.OnClickListener {

        private View.OnClickListener clickListener;
        private boolean closeOnClick;

        protected ClickListenerDecorator(View.OnClickListener clickListener, boolean closeOnClick) {
            this.clickListener = clickListener;
            this.closeOnClick = closeOnClick;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                if (clickListener instanceof LovelyDialogCompat.DialogOnClickListenerAdapter) {
                    LovelyDialogCompat.DialogOnClickListenerAdapter listener =
                            (LovelyDialogCompat.DialogOnClickListenerAdapter) clickListener;
                    listener.onClick(dialog, v.getId());
                } else {
                    clickListener.onClick(v);
                }
            }
            if (closeOnClick) {
                dismiss();
            }
        }
    }
}
