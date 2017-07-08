package com.yarolegovich.lovelydialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by yarolegovich on 16.04.2016.
 * Modified by blakelee on 7.7.2017
 */
public class LovelyProgressDialog extends AbsLovelyDialog<LovelyProgressDialog> {

    private ProgressBar progressBar;
    private TextView progressText;
    private boolean autoClose = true;
    private boolean horizontal = false;
    private Integer max = null;
    private ProgressObserver progressObserver = null;
    private LayoutInflater inflater = LayoutInflater.from(getContext());
    private @LayoutRes int id_normal = R.layout.dialog_progress;
    private @LayoutRes int id_horizontal = R.layout.dialog_progress_horizontal;
    private ViewGroup parent;
    private View view_normal;
    private View view_horizontal;

    private class ProgressObserver implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            setProgress((int)arg);
        }
    }

    public LovelyProgressDialog(Context context) {
        super(context);
    }

    public LovelyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    {
        setCancelable(false);
        parent = findView(R.id.ld_custom_view_container);
        view_normal = inflater.inflate(id_normal, null);
        view_horizontal = inflater.inflate(id_horizontal, null);

        parent.addView(view_normal);
    }

    public LovelyProgressDialog setHorizontal(boolean value) {
        this.horizontal = value;
        if (value) {
            if (view_normal.getParent() == parent)
                parent.removeView(view_normal);

            if (view_horizontal.getParent() != parent)
                parent.addView(view_horizontal);

            progressBar = findView(R.id.ld_progress);
            progressText = findView(R.id.ld_progress_text);
        } else {
            if (view_horizontal.getParent() == parent)
                parent.removeView(view_horizontal);

            if (view_normal.getParent() != parent)
                parent.addView(view_normal);

            progressBar = null;
            progressText = null;
        }

        return this;
    }

    //Sets the max value of a horizontal progress bar
    public LovelyProgressDialog setMax(int max) {
        if (!horizontal)
            throw new IllegalStateException(string(R.string.ex_msg_dialog_progress_not_horizontal));

        if (max < 0)
            throw new IllegalStateException(string(R.string.ex_msg_dialog_progress_no_max));

        this.max = max;
        progressBar.setMax(this.max);

        return this;
    }

    //Sets the current value out of max value for the horizontal progresss bar
    private void setProgress(int progress) {
        if (progress >= 0) {
            this.progressBar.setProgress(progress);
            this.progressText.setText(String.format(Locale.getDefault(), "%d / %d", progress, max));

            if (progress >= max && autoClose) {
                this.dismiss(); //You never see the dialog hit 100% without a delay
            }
        }
    }

    //Have the dialog automatically close when the progress reaches the max value
    public LovelyProgressDialog closeOnComplete(boolean value) {
        if (!horizontal)
            throw new IllegalStateException(string(R.string.ex_msg_dialog_progress_not_horizontal));

        this.autoClose = value;
        return this;
    }

    //Observer for horizontal dialog
    public LovelyProgressDialog setProgressObservable(LovelyProgressObservable observable) {
        if (!horizontal)
            throw new IllegalStateException(string(R.string.ex_msg_dialog_progress_not_horizontal));

        progressObserver = new ProgressObserver();
        observable.addObserver(progressObserver);
        return this;
    }


    @Override
    protected int getLayout() { return R.layout.dialog_custom; }

    @Override
    public Dialog show() {

        if (horizontal) {
            if (progressObserver == null)
                throw new IllegalStateException(string(R.string.ex_msg_dialog_no_observer));

            if (max == null)
                throw new IllegalStateException(string(R.string.ex_msg_dialog_progress_no_max));

        }

        return super.show();
    }
}
